/**
 * Copyright (c) 2013-2016, Jieven. All rights reserved.
 *
 * Licensed under the GPL license: http://www.gnu.org/licenses/gpl.txt
 * To use it on other terms please contact us at 1623736450@qq.com
 */
package com.eova.user;

import com.eova.aop.AopContext;
import com.eova.aop.MetaObjectIntercept;
import com.eova.common.Easy;
import com.oss.model.UserInfo;

public class UserInfoIntercept extends MetaObjectIntercept {

	@Override
	public String addBefore(AopContext ac) throws Exception {
		UserInfo info = UserInfo.dao.findById(ac.record.get("id"));
		if (info != null) {
			return Easy.error("已经存在用户详细信息,禁止重复添加！");
		}
		return null;
	}
	
}