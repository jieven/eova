/**
 * Copyright (c) 2013-2015, Jieven. All rights reserved.
 *
 * Licensed under the GPL license: http://www.gnu.org/licenses/gpl.txt
 * To use it on other terms please contact us at 1623736450@qq.com
 */
package com.eova.model;

import java.util.List;

import com.jfinal.plugin.activerecord.Db;
import com.eova.common.base.BaseModel;
import com.eova.common.utils.xx;

/**
 * 角色已授权功能点
 * 
 * @author Jieven
 * @date 2014-9-10
 */
public class RoleBtn extends BaseModel<RoleBtn> {

	private static final long serialVersionUID = -1794335434198017392L;

	public static final RoleBtn dao = new RoleBtn();

	/**
	 * 获取角色授权信息
	 * 
	 * @param rid 角色ID
	 * @return
	 */
	public List<RoleBtn> queryByRid(int rid) {
		return super.find("select * from eova_role_btn where rid = ?", rid);
	}

	/**
	 * 删除菜单功能关联的权限
	 * @param menuCode
	 */
	public void deleteByMenuCode(String menuCode){
		String sql = "delete from eova_role_btn where bid in (select id from eova_button where menuCode = ?)";
		Db.use(xx.DS_EOVA).update(sql, menuCode);
	}
	
}