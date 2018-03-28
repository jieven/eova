/**
 * Copyright (c) 2013-2016, Jieven. All rights reserved.
 *
 * Licensed under the GPL license: http://www.gnu.org/licenses/gpl.txt
 * To use it on other terms please contact us at 1623736450@qq.com
 */
package com.eova.user;

import com.eova.aop.AopContext;
import com.eova.aop.MetaObjectIntercept;
import com.eova.common.utils.EncryptUtil;

/**
 * 自定义用户管理拦截器
 * @author Jieven
 *
 */
public class UserIntercept extends MetaObjectIntercept {

	@Override
	public String addBefore(AopContext ac) throws Exception {
		// 新增时密码加密储存
		String str = ac.record.getStr("login_pwd");
		ac.record.set("login_pwd", EncryptUtil.getSM32(str));
		return null;
	}

}