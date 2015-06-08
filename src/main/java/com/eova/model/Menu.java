/**
 * Copyright (c) 2013-2015, Jieven. All rights reserved.
 *
 * Licensed under the GPL license: http://www.gnu.org/licenses/gpl.txt
 * To use it on other terms please contact us at 1623736450@qq.com
 */
package com.eova.model;

import java.util.List;

import com.eova.common.base.BaseModel;
import com.eova.common.utils.xx;

public class Menu extends BaseModel<Menu> {

	private static final long serialVersionUID = 7072369370299999169L;

	public static final Menu dao = new Menu();
	
	/**菜单类型-目录**/
	public static final String TYPE_DIR = "dir";
	/**菜单类型-自定义**/
	public static final String TYPE_DIY = "diy";
	/**菜单类型-单表**/
	public static final String TYPE_SINGLEGRID = "singleGrid";

	private List<Menu> childList;

	public List<Menu> getChildList() {
		return childList;
	}

	public void setChildList(List<Menu> childList) {
		this.childList = childList;
	}
	
	/**
	 * 获取访问URL 
	 */
	public String getUrl(){
		String url = this.getStr("url");
		if (xx.isEmpty(url)) {
			return '/' + this.getStr("type") + "/list/" +this.getStr("code");
		}
		return url;
	}
	
	public Menu findByCode(String code){
		String sql = "select * from eova_menu where code = ?";
		return Menu.dao.findFirst(sql, code);
	}
	
	/**
	 * 获取根节点
	 * @return
	 */
	public List<Menu> queryRoot() {
		return super.queryByCache("select * from eova_menu where parentId = 0 order by indexNum");
	}

	/**
	 * 获取所有节点
	 * @return
	 */
	@Override
	public List<Menu> queryAll() {
		return super.queryByCache("select * from eova_menu order by indexNum");
	}

	/**
	 * 获取菜单
	 * @param parentId 根节点
	 * @return
	 */
	// public List<Menu> queryByParentId(int parentId) {
	// return super.queryByCache("select * from eova_menu where parentId != 0 and FIND_IN_SET(id, queryChild(?)) order by parentId,indexNum ", parentId);
	// }

	// public List<Menu> queryByParentId(int parentId, int rid) {
//		StringBuilder sb = new StringBuilder();
//		sb.append("select * from eova_menu where parentId != 0 and FIND_IN_SET(id, queryChild(?)) and urlCmd = ''");
//		sb.append(" or code in (select menuCode from eova_button where name = '查询' and id in (select bid from eova_role_btn where rid = ?)");
//		sb.append(") order by indexNum");
//		sb.append("");
	// String sql = "select * from eova_menu where parentId != 0 and FIND_IN_SET(id, queryChild(?))";
	// return super.queryByCache(sql, parentId, rid);
	// }

	/**
	 * 获取某节点所有父子数据（废弃）
	 * 
	 * @return
	 */
	// public Map<Integer, Menu> queryMenuByParentId(int parentId) {
	// List<Menu> menus = Menu.dao.find("select * from eova_menu order by parentId,indexNum");
	//
	// // List转有序Map
	// LinkedHashMap<Integer, Menu> temp = new LinkedHashMap<Integer, Menu>();
	// for (Menu x : menus) {
	// temp.put(x.getInt("id"), x);
	// }
	//
	// // 获取某节点下数据
	// LinkedHashMap<Integer, Menu> result = new LinkedHashMap<Integer, Menu>();
	//
	// getNode(temp, parentId, result);
	//
	// return result;
	// }
	
	/**
	 * 递归查找子节点
	 * @param src
	 * @param parentId
	 * @param result
	 */
	// private void getNode(Map<Integer, Menu> src, int parentId, Map<Integer, Menu> result) {
	// for (Map.Entry<Integer, Menu> map : src.entrySet()) {
	// if (map.getKey() == parentId) {
	// result.put(map.getKey(), map.getValue());
	// // 子ID递归找孙节点
	// getNode(src, map.getKey(), result);
	// }
	// }
	// }

//	public int compare(Menu o1, Menu o2) {
//		if (o1.getInt("indexNum") < o2.getInt("indexNum")) {
//			return -1;
//		} else if (o1.getInt("indexNum") < o2.getInt("indexNum")) {
//			return 0;
//		} else
//			return 1;
//	}

}