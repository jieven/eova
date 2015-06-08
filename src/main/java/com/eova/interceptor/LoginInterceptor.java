/**
 * Copyright (c) 2013-2015, Jieven. All rights reserved.
 *
 * Licensed under the GPL license: http://www.gnu.org/licenses/gpl.txt
 * To use it on other terms please contact us at 1623736450@qq.com
 */
package com.eova.interceptor;

import com.eova.model.User;
import com.jfinal.aop.Interceptor;
import com.jfinal.core.ActionInvocation;

/**
 * 常量加载拦截器
 * @author Jieven
 *
 */
public class LoginInterceptor implements Interceptor{

	public void intercept(ActionInvocation ai) {
		if (ai.getActionKey().equals("/toLogin")) {
			ai.invoke();
			return;
		}
		if (ai.getActionKey().equals("/vcodeImg")) {
			ai.invoke();
			return;
		}
		if (ai.getActionKey().equals("/doLogin")) {
			ai.invoke();
			return;
		}
		// 获取登录用户的角色
		User user = ai.getController().getSessionAttr("user");
		if (user == null) {
			ai.getController().redirect("/toLogin");
			return;
		}
		
		ai.invoke();
	}

}