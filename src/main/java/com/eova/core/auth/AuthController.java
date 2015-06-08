/**
 * Copyright (c) 2013-2015, Jieven. All rights reserved.
 *
 * Licensed under the GPL license: http://www.gnu.org/licenses/gpl.txt
 * To use it on other terms please contact us at 1623736450@qq.com
 */
package com.eova.core.auth;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.eova.common.Easy;
import com.eova.common.utils.xx;
import com.eova.model.Button;
import com.eova.model.Menu;
import com.eova.model.RoleBtn;
import com.eova.service.sm;
import com.eova.widget.WidgetUtil;
import com.jfinal.core.Controller;
import com.jfinal.kit.JsonKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

/**
 * 元数据操作 MetaObject+MetaItem
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
	public void getFunJson() {
		// Mysql 5.1 需手工转换GROUP_CONCAT结果类型
		// StringBuilder sb = new StringBuilder();
		// sb.append("select m.id,m.parentId,m.name,m.code,m.icon,m.indexNum,GROUP_CONCAT(b.name) btnName,GROUP_CONCAT(CAST(b.id AS CHAR(11))) btnId");
		// sb.append(" from eova_menu m left join eova_button b on m.code = b.menuCode");
		// sb.append(" where FIND_IN_SET(m.id, queryChild(0)) group by m.code order by m.indexNum,b.indexNum");
		// List<Record> list = Db.use(xx.DS_EOVA).find(sb.toString());
		// String json = WidgetUtil.toTreeJson(list);

		// 因为递归函数兼容性问题，改用Java实现递归查询菜单

		// 获取所有菜单信息
		LinkedHashMap<Integer, Record> menus = (LinkedHashMap<Integer, Record>) sm.auth.queryByParentId(0);
		// 获取所有按钮信息
		List<Button> btns = Button.dao.find("select * from eova_button order by menuCode,indexNum");

		// 构建菜单对应功能点 eg. [玩家管理] 口查询 口新增 口修改 口删除
		for (Map.Entry<Integer, Record> map : menus.entrySet()) {
			buildBtn(map.getValue(), btns);
		}

		// Map 转 Tree Json
		String json = WidgetUtil.maptoTreeJsons(menus);

		renderJson(json);
	}

	private void buildBtn(Record record, List<Button> btns) {
		String code = record.getStr("code");
		if (code.equals(Menu.TYPE_DIR) || code.equals(Menu.TYPE_DIY)) {
			// 忽略自定义URL和目录
			return;
		}

		String btnId = "", btnName = "";
		for (Button btn : btns) {
			if (btn.getStr("menuCode").equals(code)) {
				btnId += btn.getInt("id") + ",";
				btnName += btn.getStr("name") + ",";
			}
		}
		if (xx.isEmpty(btnId)) {
			return;
		}
		if (btnId.endsWith(",")) {
			btnId = btnId.substring(0, btnId.length() - 1);
			btnName = btnName.substring(0, btnName.length() - 1);
		}
		record.set("btnId", btnId);
		record.set("btnName", btnName);
	}

	/**
	 * 获取角色已分配功能JSON
	 */
	public void getRoleFunJson() {
		int rid = getParaToInt(0);
		if (xx.isEmpty(rid)) {
			renderJson(new Easy("参数缺失!"));
			return;
		}
		List<RoleBtn> list = RoleBtn.dao.queryByRid(rid);
		String json = JsonKit.toJson(list);
		System.out.println(json);
		renderJson(json);
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
			RoleBtn rf = new RoleBtn();
			rf.set("rid", rid);
			// if (id.startsWith("btn_")) {
			// rf.set("isBtn", 1);
			// id = id.replace("btn_", "");
			// }
			rf.set("bid", id);
			rf.save();
		}

		// 保存授权
		// Role role = Role.dao.findById(rid);
		// role.set("fun", checks);
		// role.update();

		renderJson(new Easy());
	}

	/**
	 * 显示已授权功能点(废弃)
	 */
	// public void showFunTree() {
	// int rid = getParaToInt(0);
	// if (xx.isEmpty(rid)) {
	// renderJson(new Easy("参数缺失!"));
	// return;
	// }
	// // 获取角色授权
	// List<RoleBtn> funList = RoleBtn.dao.queryByRid(rid);
	// // String[] ids = role.getStr("fun").split(",");
	// // 已授权集合
	// // List<String> funList = Arrays.asList(ids);
	//
	// // 获取菜单
	// // List<Menu> menuList = Menu.dao.queryByParentId(0);
	// // 获取所有菜单信息
	// LinkedHashMap<Integer, Record> menus = (LinkedHashMap<Integer, Record>) sm.eova.queryByParentId(0);
	//
	// // 获取所有按钮
	// List<Button> btnList = Button.dao.queryAllByCache();
	//
	// StringBuilder sb = new StringBuilder("[");
	// for (Map.Entry<Integer, Record> map : menus.entrySet()) {
	// Menu menu = new Menu();
	// menu.setAttrs(map.getValue().getColumns());
	//
	// int id = menu.getInt("id");
	// String code = menu.getStr("code");
	// String icon = menu.getStr("icon");
	// String state = "open";
	//
	// sb.append("{");
	// sb.append("\"attributes\": {");
	// sb.append("\"target\": \"\",");
	// sb.append("\"url\": \"" + menu.getStr("urlCmd") + "\"");
	// sb.append("},\n");
	// sb.append("\"iconCls\": \"" + icon + "\",");
	// sb.append("\"id\": \"" + id + "\", ");
	// // 是否已授权
	// boolean isCheck = isExist(funList, false, id);
	// sb.append("\"checked\": " + isCheck + ",");
	//
	// int pid = menu.getInt("parentId");
	// if (pid != 0) {
	// sb.append("\"pid\": \"" + pid + "\",");
	// }
	// sb.append("\"state\": \"" + state + "\", \n");
	// sb.append("\"text\": \"" + menu.getStr("name") + "\"");
	//
	// sb.append("},");
	//
	// for (Button btn : btnList) {
	// if (code.equals(btn.getStr("menuCode"))) {
	// sb.append("{");
	// sb.append("\"attributes\": {");
	// sb.append("\"target\": \"\",");
	// sb.append("\"url\": \"#\"");
	// sb.append("},\n");
	// sb.append("\"iconCls\": \"ext-icon-shape_square\",");
	// sb.append("\"id\": \"btn_" + btn.getInt("id") + "\", ");
	// sb.append("\"pid\": \"" + id + "\",");
	// sb.append("\"state\": \"" + state + "\", \n");
	// sb.append("\"text\": \"" + btn.getStr("name") + "\",");
	//
	// boolean isCheckBtn = isExist(funList, true, btn.getInt("id"));
	// sb.append("\"checked\": " + isCheckBtn);
	//
	// sb.append("},");
	// }
	// }
	// }
	// sb.delete(sb.length() - 1, sb.length());
	// sb.append("]");
	//
	// renderJson(sb.toString());
	// }

	// private boolean isExist(List<RoleBtn> funList, boolean isBtn, int id) {
	// for (RoleBtn x : funList) {
	// int fid = x.getInt("bid");
	// if (isBtn && x.getBoolean("isBtn")) {
	// if (fid == id) {
	// return true;
	// }
	// } else {
	// if (fid == id) {
	// return true;
	// }
	// }
	// }
	// return false;
	// }
}