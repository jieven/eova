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

import com.jfinal.core.Const;
import com.jfinal.core.Controller;
import com.jfinal.kit.JsonKit;
import com.jfinal.kit.Kv;
import com.jfinal.kit.LogKit;

/**
 * SSE消息发送工具类 v1.0.1
 * @author 杜福忠
 */
@SuppressWarnings({"unused", "UnusedReturnValue"})
public class SseKit {
    private static final Map<String, AsyncContext> sseMap = new ConcurrentHashMap<>();

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
        response.setContentType("text/event-stream");
        response.setCharacterEncoding(Const.DEFAULT_ENCODING);
        response.setHeader("Content-Type", "text/event-stream; charset:utf-8");
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Connection", "keep-alive");
        AsyncContext ac = c.getRequest().startAsync();
        // 默认1小时超时
        ac.setTimeout(60 * 60 * 1000);
        remove(user);
        sseMap.put(user, ac);
        ac.addListener(new AsyncListener() {
            @Override
            public void onComplete(AsyncEvent event) {
                SseKit.remove(user);
            }

            @Override
            public void onTimeout(AsyncEvent event) {
            }

            @Override
            public void onError(AsyncEvent event) {
                SseKit.remove(user);
            }

            @Override
            public void onStartAsync(AsyncEvent event) {
            }
        });
        return ac;
    }

    public static void remove(String user) {
        if (user != null) {
            AsyncContext ac = sseMap.remove(user);
            if (ac != null) {
                ac.complete();
            }
        }
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
        if (ac != null) {
            try {
                PrintWriter writer = ac.getResponse().getWriter();
                writer.write(dataStr);
                writer.flush();
                return true;
            } catch (IOException e) {
                LogKit.error(e.getMessage(), e);
            }
        }
        return false;
    }
}