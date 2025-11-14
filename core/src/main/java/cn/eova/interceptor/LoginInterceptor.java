/**
 * Copyright (c) 2015-2026 EOVA.CN. All rights reserved.
 * Licensed under the LGPL-3.0 license
 * For authorization, please contact: admin@eova.cn
 */
package cn.eova.interceptor;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;

import cn.eova.tools.x;
import cn.eova.common.base.BaseController;
import cn.eova.common.utils.util.AntPathMatcher;
import cn.eova.common.utils.web.WebUtil;
import cn.eova.config.EovaConfig;
import cn.eova.config.EovaConst;
import cn.eova.i18n.I18NBuilder;
import cn.eova.model.User;
import cn.eova.service.LoginService;
import cn.eova.service.sm;
import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;

/**
 * 登录拦截器
 * @author Jieven
 *
 */
public class LoginInterceptor implements Interceptor {

    /**
     * 登录拦截排除URI<br>
     * ?  匹配任何单字符<br>
     * *  匹配0或者任意数量的字符<br>
     * ** 匹配0或者更多的目录 <br>
     */
    public static ArrayList<String> excludes = new ArrayList<String>();

    static {
        // excludes.add(EovaConfig.EOVA_INDEX);
        excludes.add("/user/captcha");
        excludes.add("/user/login");
        excludes.add("/user/doLogin");
        excludes.add("/user/logout");
    }

    /**
     * 允许跨越
     */
    public static void setCross(HttpServletResponse response) {
        // System.err.println("允许跨域模式");
        response.addHeader("Access-Control-Allow-Origin", "*");
        response.addHeader("Access-Control-Allow-Credentials", "true");
    }

    @Override
    public void intercept(Invocation inv) {

//        String uri = inv.getActionKey();

        String uri = inv.getController().getRequest().getRequestURI();

        AntPathMatcher pm = new AntPathMatcher();
        for (String pattern : excludes) {
            if (pm.match(pattern, uri)) {
                inv.invoke();
                return;
            }
        }

        BaseController ctrl = (BaseController) inv.getController();
        User user = ctrl.getUser();
        if (user == null) {
            String ip = WebUtil.getRealIp(ctrl.getRequest());
            user = sm.login.loginBySid(ctrl.SID(), ip);
            if (user == null) {
                // SID登录失败，销毁Cookie
                ctrl.removeCookie(LoginService.CKSID);

                // 异步请求 返回 401 状态码(未获得登录授权)
                if (WebUtil.isAjax(ctrl.getRequest())) {
                    ctrl.renderError(401);
                    return;
                }

                // 本地模式
                String loginUrl = "/user/login";
                // 获取来源页
                StringBuffer url = ctrl.getRequest().getRequestURL();
                if (url != null) {
                    loginUrl += "?back=" + url.toString();
                }
                //
                ctrl.redirect(loginUrl);
                return;

            }
            // 登录初始化
            if (EovaConfig.getUserSessionIntercept() != null) {
                EovaConfig.getUserSessionIntercept().login(user);
                // 更新用户缓存
                ctrl.updateUser(user);
            }
        }

        // 续传 登录用户 用于其他场景 比如模版的Web域
        ctrl.set(LoginService.USER, user);

        syncThreadLocal(inv);

        inv.invoke();
    }

    /**
     * 线程数据同步
     * PS:服务器网络模型不同导致新建线程策略不同, 可能每次请求都会被一个新线程处理, 需要再此续传.
     * @param inv
     */
    public void syncThreadLocal(Invocation inv) {
        if (x.conf.getBool("isI18N", false)) {
            String local = (String) inv.getController().getCookie(EovaConst.LOCAL);
            if (!x.isEmpty(local)) {
                I18NBuilder.setLocal(local);
                inv.getController().set("LOCAL", local);
            }
        }

    }
} 