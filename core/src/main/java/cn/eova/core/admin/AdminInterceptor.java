/**
 * Copyright (c) 2015-2026 EOVA.CN. All rights reserved.
 * Licensed under the LGPL-3.0 license
 * For authorization, please contact: admin@eova.cn
 */
package cn.eova.core.admin;

import cn.eova.common.base.BaseController;
import cn.eova.model.User;
import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;

/**
 * 超级管理员拦截器
 * @author Jieven
 *
 */
public class AdminInterceptor implements Interceptor {

    @Override
    public void intercept(Invocation inv) {

        BaseController ctrl = (BaseController) inv.getController();
//		if (!EovaConfig.isDevMode) {
//			inv.getController().renderText("请开启开发者模式: devMode=true 编辑配置文件 app.config");
//			return;
//		}
        User user = ctrl.getUser();
        if (!user.isAdmin()) {
            inv.getController().renderText("无权限操作, 需要开发者权限!");
            return;
        }

        inv.invoke();
    }
}