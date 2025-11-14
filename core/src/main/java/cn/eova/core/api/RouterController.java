package cn.eova.core.api;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import cn.eova.tools.x;
import com.alibaba.fastjson.JSONObject;
import cn.eova.common.base.BaseController;
import cn.eova.common.utils.EncryptUtil;
import cn.eova.common.utils.web.WebUtil;
import com.jfinal.aop.Clear;
import com.jfinal.core.Action;
import com.jfinal.kit.LogKit;

@Clear
public class RouterController extends BaseController {

    private static final HashMap<String, String> APP_CONFIG = new HashMap<>();
    private static HashMap<String, BaseApi> routes = new HashMap<>();

    protected Map<String, Action> mapping = new LinkedHashMap<String, Action>(2048, 0.5F);

    // 心跳检查
    public void live() {
        OK("200");
    }

    public void initApp() {
        if (!APP_CONFIG.isEmpty()) {
            return;
        }

        String appsConfig = x.conf.get("eova.api.apps");
        if (x.isEmpty(appsConfig)) {
            LogKit.debug("eova.api.apps 为空, 可能无法使用API");
            return;
        }
        String[] apps = appsConfig.split(";");
        for (String app : apps) {
            String[] ss = app.split(":");
            APP_CONFIG.put(ss[0], ss[1]);
        }

    }

    // 网关入口
    public void index() {
        JSONObject json = (JSONObject) getJson();
        String appKey = json.getString("app_key");
        String method = json.getString("method");
        String timestamp = json.getString("timestamp");
        String sign = json.getString("sign");

        if (x.isOneEmpty(appKey, method, timestamp, sign)) {
            NO("公共参数缺失, 请检查!");
            return;
        }

        if (!signCheck(appKey, method, timestamp, sign)) {
            String ip = WebUtil.getRealIp(getRequest());
            String env = x.conf.get("env");
            if (env.equalsIgnoreCase("DEV")) {
                System.err.println("鉴权跳过, 开发环境免鉴权");
            } else {
                NO("鉴权失败, 非法请求");
                return;
            }
        }

        // 后期加入 ver 参数, API可以实现分版本.

        for (String ak : mapping.keySet()) {
            Action action = mapping.get(ak);
            try {
                Object target = null;
                Object[] args = new Object[100];
                action.getMethod().invoke(target, args);
            } catch (InvocationTargetException e) {
                Throwable t = e.getTargetException();
                if (t == null) {
                    t = e;
                }
                throw t instanceof RuntimeException ? (RuntimeException) t : new RuntimeException(t);
            } catch (RuntimeException e) {
                throw e;
            } catch (Throwable t) {
                throw new RuntimeException(t);
            }

        }

        OK();
    }

    /**
     * 签名校验
     */
    public boolean signCheck(String appKey, String method, String timestamp, String sign) {
        String appSecret = APP_CONFIG.get(appKey);
        // 简单签名, 未来校验所有业务参数
        String md5 = EncryptUtil.getMd5(appKey + appSecret + method + timestamp);
        return md5.equalsIgnoreCase(sign);
    }

}
