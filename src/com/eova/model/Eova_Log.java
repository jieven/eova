/**
 * Copyright (c) 2013-2015, Jieven. All rights reserved.
 *
 * Licensed under the GPL license: http://www.gnu.org/licenses/gpl.txt
 * To use it on other terms please contact us at 1623736450@qq.com
 */
package com.eova.model;

import com.jfinal.core.Controller;
import com.rzyunyou.base.BaseModel;
import com.rzyunyou.common.web.RequestUtil;

/**
 * 系统操作日志
 *
 * @author Jieven
 * @date 2014-9-10
 */
public class Eova_Log extends BaseModel<Eova_Log> {

	private static final long serialVersionUID = -1592533967096109392L;

	public static final Eova_Log dao = new Eova_Log();

	/**新增**/
	public static final int ADD = 1;
	/**修改**/
	public static final int UPDATE = 2;
	/**删除**/
	public static final int DELETE = 3;
	
	/**
	 * 操作日志
	 * @param con
	 * @param info 日志详情
	 */
	public void info(Controller con, int type, String info){
		Eova_Log el = new Eova_Log();
		// TYPE
		el.set("type", type);
		// UID
		Eova_User user = con.getSessionAttr("user");
		el.set("uid", user.getInt("id"));
		// IP
		String ip = RequestUtil.getIp(con.getRequest());
		el.set("ip", ip);
		el.set("info", info);
		el.save();
	}
}