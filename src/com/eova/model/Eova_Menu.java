/**
 * Copyright (c) 2013-2015, Jieven. All rights reserved.
 *
 * Licensed under the GPL license: http://www.gnu.org/licenses/gpl.txt
 * To use it on other terms please contact us at 1623736450@qq.com
 */
package com.eova.model;

import java.util.List;

import com.rzyunyou.base.BaseModel;
import com.rzyunyou.common.xx;

public class Eova_Menu extends BaseModel<Eova_Menu> {

	private static final long serialVersionUID = 7072369370299999169L;

	public static final Eova_Menu dao = new Eova_Menu();
	
	/**菜单类型-目录**/
	public static final String TYPE_DIR = "dir";
	/**菜单类型-自定义**/
	public static final String TYPE_DIY = "diy";
	/**菜单类型-单表**/
	public static final String TYPE_SINGLEGRID = "singleGrid";

	private List<Eova_Menu> childList;

	public List<Eova_Menu> getChildList() {
		return childList;
	}

	public void setChildList(List<Eova_Menu> childList) {
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
	
	public Eova_Menu findByCode(String code){
		String sql = "select * from eova_menu where code = ?";
		return Eova_Menu.dao.findFirst(sql, code);
	}
	
	/**
	 * 获取根节点
	 * @return
	 */
	public List<Eova_Menu> queryRoot() {
		return super.queryByCache("select * from eova_menu where parentId = 0 order by indexNum");
	}

	/**
	 * 获取所有节点
	 * @return
	 */
	public List<Eova_Menu> queryAll() {
		return super.queryByCache("select * from eova_menu order by indexNum");
	}

	/**
	 * 获取菜单
	 * @param parentId 根节点
	 * @return
	 */
	public List<Eova_Menu> queryByParentId(int parentId) {
		return super.queryByCache("select * from eova_menu where parentId != 0 and FIND_IN_SET(id, queryChild(?)) order by parentId,indexNum ", parentId);
	}

	
	/**
	 * 根据当前角色获取菜单
	 * @param parentId 根节点
	 * @param rid 角色
	 * @return
	 */
	// public List<Eova_Menu> queryByParentId(int parentId, int rid) {
	// // StringBuilder sb = new StringBuilder();
	// // sb.append("select * from eova_menu where id != ?");
	// //
	// sb.append(" and code in (select b.menuCode from eova_button b where b.isBase = 1");
	// // sb.append(" and id in (select bid from eova_role_btn where rid = ?)");
	// //
	// sb.append(") and FIND_IN_SET(id, queryChild(?)) order by parentId,indexNum");
	// // return super.queryByCache(sb.toString(), parentId, rid, parentId);
	// return super
	// .queryByCache(
	// "select * from eova_menu where id != ? and id in (select bid from eova_role_btn where isBtn = 0 and rid = ?) and FIND_IN_SET(id, queryChild(?)) order by indexNum",
	// parentId, rid, parentId);
	// }

	public List<Eova_Menu> queryByParentId(int parentId, int rid) {
//		StringBuilder sb = new StringBuilder();
//		sb.append("select * from eova_menu where parentId != 0 and FIND_IN_SET(id, queryChild(?)) and urlCmd = ''");
//		sb.append(" or code in (select menuCode from eova_button where name = '查询' and id in (select bid from eova_role_btn where rid = ?)");
//		sb.append(") order by indexNum");
//		sb.append("");
		String sql = "select * from eova_menu where parentId != 0 and FIND_IN_SET(id, queryChild(?))";
		return super.queryByCache(sql, parentId, rid);
	}

//	public int compare(Eova_Menu o1, Eova_Menu o2) {
//		if (o1.getInt("indexNum") < o2.getInt("indexNum")) {
//			return -1;
//		} else if (o1.getInt("indexNum") < o2.getInt("indexNum")) {
//			return 0;
//		} else
//			return 1;
//	}

}