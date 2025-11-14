/**
 * Copyright (c) 2015-2026 EOVA.CN. All rights reserved.
 * Licensed under the LGPL-3.0 license
 * For authorization, please contact: admin@eova.cn
 */
package cn.eova.auth;

import java.util.Set;

import cn.eova.tools.x;
import cn.eova.common.base.BaseController;
import cn.eova.common.utils.util.AntPathMatcher;
import cn.eova.common.utils.xx;
import cn.eova.config.EovaConfig;
import cn.eova.config.EovaFieldAuth;
import cn.eova.interceptor.LoginInterceptor;
import cn.eova.model.User;
import cn.eova.ops.OpsConst;
import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.kit.JsonKit;
import com.jfinal.kit.Ret;

/**
 * 权限验证
 *
 * @author Jieven
 * @date 2014-9-18
 */
public class AuthInterceptor implements Interceptor {

    /** 上一次更新时间(字段授权)**/
    public static final String UPDATE_FIELD_AUTH = "update_field_auth";

    @Override
    public void intercept(Invocation inv) {
        BaseController ctrl = (BaseController) inv.getController();
        User user = ctrl.getUser();

        // 免登录URI和超管 免鉴权
        if (user == null || user.isAdmin()) {
            inv.invoke();
            return;
        }

        // 服务暂不可用 => HTTP 503
        if (OpsConst.WAITING) {
            ctrl.set("info", OpsConst.WAIT_INFO);
            ctrl.renderError(503);
            inv.invoke();
            return;
        }

        String uri = inv.getController().getRequest().getRequestURI();

        AntPathMatcher pm = new AntPathMatcher();

        // 免登录免鉴权
        for (String pattern : LoginInterceptor.excludes) {
            if (pm.match(pattern, uri)) {
                inv.invoke();
                return;
            }
        }

        // 登录后免鉴权
        for (String pattern : AuthUri.loginAuth) {
            if (pm.match(pattern, uri)) {
                inv.invoke();
                return;
            }
        }

        // 当前角色分配授权
        Set<String> authUriPattern = user.get("auths");
        if (x.isEmpty(authUriPattern)) {
            ((BaseController) inv.getController()).renderMsg("用户未分配权限，请获得授权后<a href=\"logout\">重新登录</a>");
            return;
        }

        // 每5s更新字段权限
        if (xx.isTimeout(user.getLong(UPDATE_FIELD_AUTH), 5)) {
            user.setDisableFields(EovaFieldAuth.getDisableFields(user));
            x.log.debug("{}更新用户列权限:{}", user.getId(), JsonKit.toJson(user.getDisableFields()));
            user.put(UPDATE_FIELD_AUTH, System.currentTimeMillis());
        }

        // 当前角色自定义授权
        Set<String> temp = EovaConfig.getAuthUris().get(user.getRid());
        if (!x.isEmpty(temp)) {
            authUriPattern.addAll(temp);
        }
        // 所有角色公共授权
//        temp = EovaConfig.getAuthUris().get(0);
//        if (!x.isEmpty(temp)) {
//            authUriPattern.addAll(temp);
//        }
        // 检查授权
        for (String pattern : authUriPattern) {
            if (pm.match(pattern, uri)) {
                inv.invoke();
                return;
            }
        }

        x.log.warn("403 访问未授权资源: {} , User[id={} rid={}]", uri, user.getId(), user.getRid());

        // 无权限时, 自动刷新权限
        AuthUri.build(user);

        if (uri.startsWith("/api/table/query")) {
            // Ajax友好提示
            inv.getController().renderJson(Ret.fail("未授权资源, 请检查权限配置:" + uri));
            return;
        }

        ctrl.set("info", uri);
        ctrl.renderError(403);
    }

}