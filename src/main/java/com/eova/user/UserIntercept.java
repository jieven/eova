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
import com.oss.model.UserInfo;

public class UserIntercept extends MetaObjectIntercept {

	@Override
	public String addBefore(AopContext ac) throws Exception {
		// 新增时密码加密储存
		String str = ac.record.getStr("login_pwd");
		ac.record.set("login_pwd", EncryptUtil.getSM32(str));
		return null;
	}


	@Override
	public String addAfter(AopContext ac) throws Exception {
		// 初始化用户详细信息，将角色ID冗余，方便高级业务查询
		UserInfo me = new UserInfo();
		me.set("id", ac.record.get("id"));
		me.set("rid", ac.record.getInt("rid"));
		me.save();
		return null;
	}

	@Override
	public String updateBefore(AopContext ac) throws Exception {
		UserInfo me = new UserInfo();
		me.set("id", ac.record.get("id"));
		me.set("rid", ac.record.getInt("rid"));
		me.update();
		
		return null;
	}

	
}