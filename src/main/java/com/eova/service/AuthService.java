/**
 * Copyright (c) 2013-2015, Jieven. All rights reserved.
 *
 * Licensed under the GPL license: http://www.gnu.org/licenses/gpl.txt
 * To use it on other terms please contact us at 1623736450@qq.com
 */
package com.eova.service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.eova.common.base.BaseCache;
import com.eova.common.base.BaseService;
import com.eova.common.utils.xx;
import com.eova.config.EovaConst;
import com.eova.model.Menu;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

/**
 * 权限数据访问
 * 
 * @author Jieven
 * 
 */
public class AuthService extends BaseService {

	/**
	 * 根据角色ID获取已授权查询的菜单Code
	 * 
	 * @param rid 角色ID
	 * @return
	 */
	public List<String> queryMenuCodeByRid(int rid) {
		String sql = "select DISTINCT(b.menuCode) from eova_role_btn rf LEFT JOIN eova_button b on rf.bid = b.id where b.name = '查询' and rf.rid = ?";
		return Db.use(xx.DS_EOVA).query(sql, rid);
	}

	/**
	 * 查询某棵树下是否存在已授权的功能
	 * 
	 * @param parentId 父节点ID
	 * @param rid　角色ID
	 * @return
	 */
	public boolean isExistsAuthByPidRid(int parentId, int rid) {
		// 根据角色ID获取已授权查询的菜单Code
		List<String> menuCodes = queryMenuCodeByRid(rid);
		LinkedHashMap<Integer, Record> result = (LinkedHashMap<Integer, Record>) queryByParentId(parentId);
		for (String menuCode : menuCodes) {
			for (Map.Entry<Integer, Record> map : result.entrySet()) {
				if (map.getValue().getStr("code").equals(menuCode)) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 根据父ID获取该节点及下属所有数据
	 * 
	 * @param parentId 父节点ID
	 * @return
	 */
	public List<Menu> queryMenuByParentId(int parentId) {
		LinkedHashMap<Integer, Record> result = (LinkedHashMap<Integer, Record>) queryByParentId(parentId);
		List<Menu> menus = new ArrayList<Menu>();
		for (Map.Entry<Integer, Record> map : result.entrySet()) {
			Menu menu = new Menu();
			menu.setAttrs(map.getValue().getColumns());
			menus.add(menu);
		}
		return menus;
	}

	/**
	 * 获取某节点所有父子数据
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Map<Integer, Record> queryByParentId(int parentId) {
		LinkedHashMap<Integer, Record> temp = null;

		// 取Cache，没有再查库
		Object obj = BaseCache.getSer(EovaConst.ALL_MENU);
		if (xx.isEmpty(obj)) {
			// 获取所有菜单信息
			String sql = "select * from eova_menu order by parentId,indexNum";
			List<Record> records = Db.use(xx.DS_EOVA).find(sql);

			// List转有序Map
			temp = new LinkedHashMap<Integer, Record>();
			for (Record x : records) {
				// 获取EasyUI所需ICON字段名
				String icon = x.get("icon");
				if (xx.isEmpty(icon)) {
					icon = "icon-application";
				}
				x.set("icon", icon);

				temp.put(x.getInt("id"), x);
			}

			// Cache 转换后的菜单信息
			// BaseCache.putSer(EovaConst.ALL_MENU, temp);
		} else {
			temp = new LinkedHashMap<Integer, Record>();
			temp.putAll((LinkedHashMap<Integer, Record>) obj);
		}

		// 获取某节点下所有数据
		LinkedHashMap<Integer, Record> result = new LinkedHashMap<Integer, Record>();
		// 递归获取子节点
		getChildren(temp, parentId, result);

		return result;
	}

	/**
	 * 递归查找子节点
	 * 
	 * @param all 所有菜单
	 * @param parentId 父节点ID
	 * @param result 节点下所有数据(包括父)
	 */
	private void getChildren(Map<Integer, Record> all, int parentId, Map<Integer, Record> result) {
		for (Map.Entry<Integer, Record> map : all.entrySet()) {
			// 获取父节点
			if (map.getKey() == parentId) {
				result.put(map.getKey(), map.getValue());
			}
			// 获取子节点
			if (map.getValue().getInt("parentId") == parentId) {
				result.put(map.getKey(), map.getValue());
				// 子ID递归找孙节点
				getChildren(all, map.getKey(), result);
			}
		}
	}
}