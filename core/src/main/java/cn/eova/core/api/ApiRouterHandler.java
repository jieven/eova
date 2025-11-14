/**
 * Copyright (c) 2015-2026 EOVA.CN. All rights reserved.
 * <p>
 *
 * Licensed under the LGPL-3.0 license
 * Software copyright registration number:2018SR1012969
 * For authorization, please contact: admin@eova.cn
 */
package cn.eova.core.api;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;

import cn.eova.tools.tool.SignTool;
import cn.eova.tools.x;
import com.alibaba.fastjson.JSONObject;
import cn.eova.common.utils.web.WebUtil;
import cn.eova.common.utils.xx;
import com.jfinal.handler.Handler;
import com.jfinal.kit.HttpKit;
import com.jfinal.render.RenderManager;

/**
 * API网关
 *
 * @创建者：Jieven
 * @创建时间：2017-2-20 下午3:14:06
 */
public class ApiRouterHandler extends Handler {

    private static HashMap<String, String> APP_CONFIG = new HashMap<>();

    public static HashMap<String, String> getAppConfig() {
        return APP_CONFIG;
    }

    public static void addAppConfig(String appKey, String appSec) {
        APP_CONFIG.put(appKey, appSec);
    }

    public static final String ROUTER = "/router";

    public void handle(String target, HttpServletRequest request, HttpServletResponse response, boolean[] isHandled) {
        // 普通路由
        if (!target.equals(ROUTER)) {
            // 禁止直接访问API
            if (target.startsWith(ROUTER + "/")) {
                renderJson(request, response, isHandled, ApiResponse.NO(403, "禁止访问"));
                return;
            }

            next.handle(target, request, response, isHandled);
            return;
        }

        // 入参 GET Query
        String appKey = request.getParameter("app_key");
        String method = request.getParameter("method");
        String timestamp = request.getParameter("timestamp");
        String sign = request.getParameter("sign");
        // v API协议版本，可选值：2.0 APP/PDA应用, 新旧版本共存.

        if (x.isOneEmpty(appKey, method, timestamp, sign)) {
            renderJson(request, response, isHandled, ApiResponse.NO(400, "参数缺失"));
            return;
        }

        String appSecret = APP_CONFIG.get(appKey);
        if (appSecret == null) {
            renderJson(request, response, isHandled, ApiResponse.NO(401, "鉴权失败, 未知的appKey"));
            return;
        }

        // 取报文
        String s = HttpKit.readData(request);
        if (s.isEmpty()) {
            renderJson(request, response, isHandled, ApiResponse.NO(400, "参数缺失"));
            return;
        }

        if (!signCheck(appKey, appSecret, method, timestamp, sign, s)) {
            String ip = WebUtil.getRealIp(request);
            String env = x.conf.get("env");
            if (env.equalsIgnoreCase("DEV") && sign.startsWith("dev")) {
                System.err.println("鉴权跳过, 开发环境免鉴权");
            } else {
                renderJson(request, response, isHandled, ApiResponse.NO(401, "鉴权失败"));
                return;
            }
        }

        // 路由重定向转发 /router?app_key=10000&method=eova.demo.test
        // /router/eova/demo/test
        target = String.format("%s/%s", ROUTER, method.replaceAll("\\.", "/"));

        // 业务参数续传(后续可能取不到)
        request.setAttribute("_data_str", s);

        xx.info(String.format("%s:\n%s", method, s));

        try {
            next.handle(target, request, response, isHandled);
        } catch (Exception e) {
            renderJson(request, response, isHandled, ApiResponse.NO(500, "请求失败:" + e.getMessage()));
        }

    }

    private static void renderJson(HttpServletRequest request, HttpServletResponse response, boolean[] isHandled, ApiResponse res) {
        isHandled[0] = true;
        // 忽略 data = null
        RenderManager.me().getRenderFactory().getJsonRender(JSONObject.toJSONString(res)).setContext(request, response).render();
    }

    /**
     * 签名校验
     */
    public boolean signCheck(String appKey, String appSecret, String method, String timestamp, String sign, String data) {
        // 参数+报文签名
        SignTool.SignUrl su = new SignTool(SignTool.SIGN_HMAC, appKey, appSecret, null).generate(method, data, Long.parseLong(timestamp));

        return sign.equalsIgnoreCase(su.getSign());
    }

}