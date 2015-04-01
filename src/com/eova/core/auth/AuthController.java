/**
 * Copyright (c) 2013-2015, Jieven. All rights reserved.
 *
 * Licensed under the GPL license: http://www.gnu.org/licenses/gpl.txt
 * To use it on other terms please contact us at 1623736450@qq.com
 */
package com.eova.core.auth;

import java.util.List;

import com.eova.common.Easy;
import com.eova.model.Eova_Button;
import com.eova.model.Eova_Menu;
import com.eova.model.Eova_Role_Btn;
import com.eova.widget.WidgetUtil;
import com.jfinal.core.Controller;
import com.jfinal.kit.JsonKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.rzyunyou.common.xx;

/**
 * 元数据操作
 * Eova_Object+Eova_Item
 *
 * @author Jieven
 * @date 2014-9-11
 */
public class AuthController extends Controller {

	/**
	 * 菜单基本功能管理
	 */
	public void toRoleChoose() {
		setAttr("rid", getPara(0));
 
		render("/eova/auth/roleChoose.html");
	}
	
	/**
	 * 获取功能JSON
	 */
	public void getFunJson(){
		StringBuilder sb = new StringBuilder();
		sb.append("select m.id,m.parentId,m.name,m.code,m.icon,m.indexNum,GROUP_CONCAT(b.name) btnName,GROUP_CONCAT(b.id) btnId");
		sb.append(" from eova_menu m left join eova_button b on m.code = b.menuCode");
		sb.append(" where FIND_IN_SET(m.id, queryChild(0)) group by m.code order by m.indexNum,b.indexNum");
		
		List<Record> list = Db.use(xx.DS_EOVA).find(sb.toString());
		
		String json = WidgetUtil.toTreeJson(list);
		
		renderJson(json);
	}
	
	/**
	 * 获取角色已分配功能JSON
	 */
	public void getRoleFunJson(){
		int rid = getParaToInt(0);
		if (xx.isEmpty(rid)) {
			renderJson(new Easy("参数缺失!"));
			return;
		}
		List<Eova_Role_Btn> list = Eova_Role_Btn.dao.queryByRid(rid);
		String json = JsonKit.toJson(list);
		System.out.println(json);
		renderJson(json);
	}

	private boolean isExist(List<Eova_Role_Btn> funList, boolean isBtn, int id) {
		for (Eova_Role_Btn x : funList) {
			int fid = x.getInt("bid");
			if (isBtn && x.getBoolean("isBtn")) {
				if (fid == id) {
					return true;
				}
			} else {
				if (fid == id) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 显示已授权功能点
	 */
	public void showFunTree() {
		int rid = getParaToInt(0);
		if (xx.isEmpty(rid)) {
			renderJson(new Easy("参数缺失!"));
			return;
		}
		// 获取角色授权
		List<Eova_Role_Btn> funList = Eova_Role_Btn.dao.queryByRid(rid);
		// String[] ids = role.getStr("fun").split(",");
		// 已授权集合
		// List<String> funList = Arrays.asList(ids);

		// 获取菜单
		List<Eova_Menu> menuList = Eova_Menu.dao.queryByParentId(0);
		// 获取所有按钮
		List<Eova_Button> btnList = Eova_Button.dao.queryAllByCache();

		StringBuilder sb = new StringBuilder("[");
		for (Eova_Menu menu : menuList) {
			int id = menu.getInt("id");
			// System.out.println(id);
			String code = menu.getStr("code");
			String icon = menu.getStr("icon");
			String state = "open";
			if (xx.isEmpty(icon)) {
				// 默认图标
				icon = "ext-icon-application";
			}
			sb.append("{");
			sb.append("\"attributes\": {");
			sb.append("\"target\": \"\",");
			sb.append("\"url\": \"" + menu.getStr("urlCmd") + "\"");
			sb.append("},\n");
			sb.append("\"iconCls\": \"" + icon + "\",");
			sb.append("\"id\": \"" + id + "\", ");
			// 是否已授权
			boolean isCheck = isExist(funList, false, id);
			sb.append("\"checked\": " + isCheck + ",");

			int pid = menu.getInt("parentId");
			if (pid != 0) {
				sb.append("\"pid\": \"" + pid + "\",");
			}
			sb.append("\"state\": \"" + state + "\", \n");
			sb.append("\"text\": \"" + menu.getStr("name") + "\"");

			sb.append("},");

			for (Eova_Button btn : btnList) {
				if (code.equals(btn.getStr("menuCode"))) {
					sb.append("{");
					sb.append("\"attributes\": {");
					sb.append("\"target\": \"\",");
					sb.append("\"url\": \"#\"");
					sb.append("},\n");
					sb.append("\"iconCls\": \"ext-icon-shape_square\",");
					sb.append("\"id\": \"btn_" + btn.getInt("id") + "\", ");
					sb.append("\"pid\": \"" + id + "\",");
					sb.append("\"state\": \"" + state + "\", \n");
					sb.append("\"text\": \"" + btn.getStr("name") + "\",");

					boolean isCheckBtn = isExist(funList, true, btn.getInt("id"));
					sb.append("\"checked\": " + isCheckBtn);

					sb.append("},");
				}
			}
		}
		sb.delete(sb.length() - 1, sb.length());
		sb.append("]");

		renderJson(sb.toString());
	}

	/**
	 * 授权
	 */
	public void roleChoose() {
		int rid = getParaToInt(0);
		if (xx.isEmpty(rid)) {
			renderJson(new Easy("参数缺失!"));
			return;
		}
		// 获取选中功能点
		String checks = getPara("checks");
		// 删除历史授权
		Db.use(xx.DS_EOVA).update("delete from eova_role_btn where rid = ?", rid);
		if (xx.isEmpty(checks)) {
			renderJson(new Easy());
			return;
		}
		String[] ids = checks.split(",");
		for (String id : ids) {
			Eova_Role_Btn rf = new Eova_Role_Btn();
			rf.set("rid", rid);
			if (id.startsWith("btn_")) {
				rf.set("isBtn", 1);
				id = id.replace("btn_", "");
			}
			rf.set("bid", id);
			rf.save();
		}

		// 保存授权
		// Eova_Role role = Eova_Role.dao.findById(rid);
		// role.set("fun", checks);
		// role.update();

		renderJson(new Easy());
	}
}