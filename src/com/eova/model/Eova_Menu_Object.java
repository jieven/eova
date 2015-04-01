/**
 * Copyright (c) 2013-2015, Jieven. All rights reserved.
 *
 * Licensed under the GPL license: http://www.gnu.org/licenses/gpl.txt
 * To use it on other terms please contact us at 1623736450@qq.com
 */
package com.eova.model;

import java.util.List;

import com.jfinal.plugin.activerecord.Db;
import com.rzyunyou.base.BaseModel;
import com.rzyunyou.common.xx;

/**
 * 菜单关联对象
 * 
 * @author Jieven
 * @date 2014-9-18
 */
public class Eova_Menu_Object extends BaseModel<Eova_Menu_Object> {

	private static final long serialVersionUID = 9176734392973431592L;

	public static final Eova_Menu_Object dao = new Eova_Menu_Object();

	/**
	 * 获取菜单关联对象
	 * @param menuCode
	 * @return
	 */
	public List<Eova_Menu_Object> queryByMenuCode(String menuCode) {
		return Eova_Menu_Object.dao.queryByCache("select objectCode from eova_menu_object where menuCode = ?", menuCode);
	}
	
	/**
	 * 删除菜单关联数据对象
	 * @param menuCode
	 */
	public void deleteByMenuCode(String menuCode) {
		Db.use(xx.DS_EOVA).update("delete from eova_menu_object where menuCode = ?", menuCode);
	}
	
}