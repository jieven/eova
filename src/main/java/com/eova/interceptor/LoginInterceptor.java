/**
 * Copyright (c) 2013-2015, Jieven. All rights reserved.
 *
 * Licensed under the GPL license: http://www.gnu.org/licenses/gpl.txt
 * To use it on other terms please contact us at 1623736450@qq.com
 */
package com.eova.interceptor;

import java.util.ArrayList;

import com.eova.model.User;
import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;

/**
 * 常量加载拦截器
 * 
 * @author Jieven
 * 
 */
public class LoginInterceptor implements Interceptor {
	
	private static ArrayList<String> urls = new ArrayList<String>();
	static{
		urls.add("/toLogin");
		urls.add("/vcodeImg");
		urls.add("/doLogin");
		urls.add("/init");
		urls.add("/doInit");
		urls.add("/toTest");
		urls.add("/form");
		urls.add("/doForm");
	}

	@Override
	public void intercept(Invocation inv) {
		if (urls.contains(inv.getActionKey())) {
			inv.invoke();
			return;
		}

		// 获取登录用户的角色
		User user = inv.getController().getSessionAttr("user");
		if (user == null) {
			inv.getController().redirect("/toLogin");
			return;
		}

		inv.invoke();
	}

}