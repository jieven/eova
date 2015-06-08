/**
 * Copyright (c) 2013-2015, Jieven. All rights reserved.
 *
 * Licensed under the GPL license: http://www.gnu.org/licenses/gpl.txt
 * To use it on other terms please contact us at 1623736450@qq.com
 */
package com.eova.model;

import java.util.List;

import com.eova.config.EovaConst;
import com.jfinal.plugin.activerecord.Db;
import com.eova.common.base.BaseModel;
import com.eova.common.utils.xx;

/**
 * 功能按钮
 *
 * @author Jieven
 * @date 2014-9-10
 */
public class Button extends BaseModel<Button> {

	private static final long serialVersionUID = 3481288366186459644L;

	public static final Button dao = new Button();

	/**
	 * 根据权限获取功能按钮
	 * @param menuCode
	 * @param rid
	 * @return
	 */
	public List<Button> queryByMenuCode(String menuCode, int rid) {
		return Button.dao.find("select * from eova_button where menuCode = ? and ui != '' and id in (select bid from eova_role_btn where rid = ?) order by indexNum", menuCode, rid);
	}
	
	/**
	 * 是否存在功能按钮
	 * @param menuCode 菜单编码
	 * @param bs 服务端
	 * @return 是否存在该按钮
	 */
	public boolean isExistButton(String menuCode, String bs){
		String sql = "select count(*) from eova_button where menuCode = ? and bs = ?";
		long count = Db.use(xx.DS_EOVA).queryLong(sql, menuCode, bs);
		if (count != 0) {
			return true;
		}
		return false;
	}
	
	/**
	 * 删除功能按钮(除查询按钮外)
	 * @param menuCode
	 */
	public void deleteFunByMenuCode(String menuCode){
		String sql = "delete from eova_button where name != ? and menuCode = ?";
		Db.use(xx.DS_EOVA).update(sql, EovaConst.FUN_QUERY, menuCode);
	}
	
	/**
	 * 删除菜单下所有按钮
	 * @param menuCode
	 */
	public void deleteByMenuCode(String menuCode){
		String sql = "delete from eova_button where menuCode = ?";
		Db.use(xx.DS_EOVA).update(sql, menuCode);
	}

}