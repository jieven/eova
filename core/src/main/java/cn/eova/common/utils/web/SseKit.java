package cn.eova.common.utils.web;

import javax.servlet.AsyncContext;
import javax.servlet.AsyncEvent;
import javax.servlet.AsyncListener;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.jfinal.core.Const;
import com.jfinal.core.Controller;
import com.jfinal.kit.JsonKit;
import com.jfinal.kit.Kv;
import com.jfinal.kit.LogKit;

/**
 * SSE消息发送工具类 v1.1.0
 * @author 杜福忠
 */
@SuppressWarnings({"unused", "UnusedReturnValue"})
public class SseKit {
    private static final Map<String, AsyncContext> sseMap = new ConcurrentHashMap<>();

    /** 心跳间隔（秒），防止 Nginx / 代理空闲断开 */
    private static final int HEARTBEAT_SECONDS = 15;

    private static final ScheduledExecutorService HEARTBEAT = Executors.newSingleThreadScheduledExecutor(r -> {
        Thread t = new Thread(r, "sse-heartbeat");
        t.setDaemon(true);
        return t;
    });

    static {
        HEARTBEAT.scheduleAtFixedRate(SseKit::heartbeat, HEARTBEAT_SECONDS, HEARTBEAT_SECONDS, TimeUnit.SECONDS);
    }

    public static AsyncContext get(String user) {
        return sseMap.get(user);
    }

    public static Set<String> getUsers() {
        return sseMap.keySet();
    }

    /**
     * 开启sse连接
     * @param user  用户名
     * @param c  Controller
     * @return AsyncContext
     */
    public static AsyncContext startAsync(String user, Controller c) {
        Objects.requireNonNull(user, "user can not be null");
        c.renderNull();
        HttpServletResponse response = c.getResponse();
        response.setCharacterEncoding(Const.DEFAULT_ENCODING);
        response.setContentType("text/event-stream; charset=UTF-8");
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Connection", "keep-alive");
        // 关闭 Nginx 代理缓冲，否则 SSE 会被攒包或空闲掐断
        response.setHeader("X-Accel-Buffering", "no");

        AsyncContext ac = c.getRequest().startAsync();
        // 默认1小时超时
        ac.setTimeout(60 * 60 * 1000L);

        // 替换旧连接：先登记新连接，再关闭旧连接，避免 onComplete 误删新连接
        AsyncContext old = sseMap.put(user, ac);
        if (old != null) {
            completeQuietly(old);
        }

        ac.addListener(new AsyncListener() {
            @Override
            public void onComplete(AsyncEvent event) {
                // 只清理“当前仍是自己”的登记，避免新连接被旧连接的回调踢掉
                sseMap.remove(user, ac);
            }

            @Override
            public void onTimeout(AsyncEvent event) {
                sseMap.remove(user, ac);
                completeQuietly(ac);
            }

            @Override
            public void onError(AsyncEvent event) {
                sseMap.remove(user, ac);
            }

            @Override
            public void onStartAsync(AsyncEvent event) {
            }
        });

        // 立即写出并 flush，确认链路打通（注释行，前端不触发事件）
        if (!writeRaw(ac, ": connected\n\n")) {
            sseMap.remove(user, ac);
            completeQuietly(ac);
            return null;
        }
        return ac;
    }

    public static void remove(String user) {
        if (user == null) {
            return;
        }
        AsyncContext ac = sseMap.remove(user);
        completeQuietly(ac);
    }

    /**
     * 向用户端推送消息
     * @param uid 用户ID
     * @param kv 消息内容
     * @return
     */
    public static boolean pushMsg(int uid, Kv kv) {
        String s = String.format("event: %s\ndata: %s\n\n", "msg", kv.toJson());
        return sendMessage(uid + "", s);
    }

    public static boolean sendJsonMessage(String user, Object data) {
        String dataStr = String.format("data: %s\n\n", toJson(data));
        return sendMessage(user, dataStr);
    }

    public static boolean sendJsonMessage(String user, String event, Object data) {
        String dataStr = String.format("event: %s\ndata: %s\n\n", event, toJson(data));
        return sendMessage(user, dataStr);
    }

    public static boolean sendJsonMessage(String user, Integer id, Object data) {
        String dataStr = String.format("id: %d\ndata: %s\n\n", id, toJson(data));
        return sendMessage(user, dataStr);
    }

    /**
     * 发送消息
     * @param user  接收者
     * @param id  消息 ID
     * @param event  事件
     * @param data  json消息内容
     * @return 发送成功返回true，失败返回false
     */
    public static boolean sendJsonMessage(String user, Integer id, String event, Object data) {
        String dataStr = String.format("id: %d\nevent: %s\ndata: %s\n\n", id, event, toJson(data));
        return sendMessage(user, dataStr);
    }

    private static String toJson(Object data) {
        if (data == null) {
            return "";
        }
        return data instanceof String ? (String) data : JsonKit.toJson(data);
    }

    /**
     * 发送消息
     * @param user 用户
     * @param dataStr 消息内容（需做格式化）
     * @return 发送成功返回true，失败返回false
     */
    public static boolean sendMessage(String user, String dataStr) {
        AsyncContext ac = get(user);
        if (ac == null) {
            return false;
        }
        if (!writeRaw(ac, dataStr)) {
            // 写出失败：连接已死，清理登记
            if (sseMap.remove(user, ac)) {
                completeQuietly(ac);
            }
            return false;
        }
        return true;
    }

    private static void heartbeat() {
        for (Map.Entry<String, AsyncContext> e : sseMap.entrySet()) {
            String user = e.getKey();
            AsyncContext ac = e.getValue();
            if (!writeRaw(ac, ": ping\n\n")) {
                if (sseMap.remove(user, ac)) {
                    completeQuietly(ac);
                }
            }
        }
    }

    private static boolean writeRaw(AsyncContext ac, String dataStr) {
        try {
            PrintWriter writer = ac.getResponse().getWriter();
            writer.write(dataStr);
            writer.flush();
            return !writer.checkError();
        } catch (IOException | IllegalStateException e) {
            LogKit.error(e.getMessage());
            return false;
        }
    }

    private static void completeQuietly(AsyncContext ac) {
        if (ac == null) {
            return;
        }
        try {
            ac.complete();
        } catch (IllegalStateException ignored) {
            // 已完成 / 已超时
        }
    }
}
