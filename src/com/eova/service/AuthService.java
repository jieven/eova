/**
 * Copyright (c) 2013-2015, Jieven. All rights reserved.
 *
 * Licensed under the GPL license: http://www.gnu.org/licenses/gpl.txt
 * To use it on other terms please contact us at 1623736450@qq.com
 */
package com.eova.service;

import java.util.List;

import com.jfinal.plugin.activerecord.Db;
import com.rzyunyou.base.BaseService;
import com.rzyunyou.common.xx;

/**
 * 权限数据访问
 * 
 * @author Jieven
 * 
 */
public class AuthService extends BaseService {

	/**
	 * 获取角色授权信息
	 * 
	 * @param rid 角色ID
	 * @return
	 */
	public List<String> queryMenuCodeByRid(int rid) {
		return Db.use(xx.DS_EOVA).query("select DISTINCT(b.menuCode) from eova_button b where b.id in (select bid from eova_role_btn where rid = ?)", rid);
	}

	/**
	 * 当前角色模块已授权数量
	 * @param parentId
	 * @param rid
	 * @return
	 */
	public long countByParentId(int parentId, int rid) {
		StringBuilder sb = new StringBuilder();
		sb.append("select count(*) from eova_menu where id != ? and FIND_IN_SET(id, queryChild(?)) and code in (");
		sb.append("select b.menuCode from eova_role_btn rf LEFT JOIN eova_button b on rf.bid = b.id where rf.rid = ?");
		sb.append(")");
		return Db.use(xx.DS_EOVA).queryLong(sb.toString(), parentId, parentId, rid);
	}
}