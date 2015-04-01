/**
 * Copyright (c) 2013-2015, Jieven. All rights reserved.
 *
 * Licensed under the GPL license: http://www.gnu.org/licenses/gpl.txt
 * To use it on other terms please contact us at 1623736450@qq.com
 */
package com.eova.service;

import java.util.List;

import com.eova.model.Eova_User;

public class UserService {

	public List<Eova_User> queryUser(String nickName){
		List<Eova_User> list = Eova_User.dao.find("select * from eova_user where nickName = ?", nickName);
		return list;
	}
	
	/**
	 * 根据账号获取用户
	 * @param loginId
	 * @return
	 */
	public Eova_User getUserByLoginId(String loginId) {
		return Eova_User.dao.findFirst("select * from eova_user where loginId = ?", loginId);
	}
}