/**
 * Copyright (c) 2013-2016, Jieven. All rights reserved.
 *
 * Licensed under the GPL license: http://www.gnu.org/licenses/gpl.txt
 * To use it on other terms please contact us at 1623736450@qq.com
 */
package com.eova.user;

import com.eova.common.Easy;
import com.eova.common.utils.EncryptUtil;
import com.eova.common.utils.xx;
import com.eova.model.User;
import com.jfinal.core.Controller;

/**
 * 自定义用户管理
 * 
 * @author Jieven
 * @date 2014-9-11
 */
public class UserController extends Controller {

	// 修改密码
	public void pwd() {
		int id = getParaToInt(0);
		String str = getPara(1);

		if (xx.isEmpty(str)) {
			renderJson(new Easy("密码不能为空！"));
			return;
		}
		if (str.length() < 6) {
			renderJson(new Easy("密码不能少于6位！"));
			return;
		}

		User user = new User();
		user.set("id", id);
		user.set("login_pwd", EncryptUtil.getSM32(str));
		user.update();

		renderJson(new Easy());
	}

}