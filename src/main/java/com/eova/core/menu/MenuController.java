/**
 * Copyright (c) 2013-2015, Jieven. All rights reserved.
 *
 * Licensed under the GPL license: http://www.gnu.org/licenses/gpl.txt
 * To use it on other terms please contact us at 1623736450@qq.com
 */
package com.eova.core.menu;

import java.util.ArrayList;

import com.eova.common.Easy;
import com.eova.common.base.BaseCache;
import com.eova.config.EovaConst;
import com.eova.model.Button;
import com.eova.model.Menu;
import com.eova.model.MenuConfig;
import com.eova.model.RoleBtn;
import com.eova.template.common.config.TemplateConfig;
import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.tx.Tx;

/**
 * 菜单管理
 * 
 * @author Jieven
 * @date 2014-9-11
 */
public class MenuController extends Controller {

	public void toAdd() {
		render("/eova/menu/form.html");
	}

	public void toUpdate() {
		int pkValue = getParaToInt(1);
		Menu menu = Menu.dao.findById(pkValue);

		setAttr("menu", menu);

		render("/eova/menu/form.html");
	}

	/**
	 * 菜单基本功能管理
	 */
	public void toMenuFun() {
		String menuCode = getPara(0);
		Menu menu = Menu.dao.findByCode(menuCode);
		
		setAttr("menu", menu);

		setAttr("isAdd", Button.dao.isExistButton(menuCode, Button.FUN_ADD_BS, 0));
		setAttr("isUpdate", Button.dao.isExistButton(menuCode, Button.FUN_UPDATE_BS, 0));
		setAttr("isDelete", Button.dao.isExistButton(menuCode, Button.FUN_DELETE_BS, 0));
		setAttr("isImport", Button.dao.isExistButton(menuCode, Button.FUN_IMPORT_BS, 0));
		
		setAttr("isSlaveAdd", Button.dao.isExistButton(menuCode, Button.FUN_ADD_BS, 1));
		setAttr("isSlaveUpdate", Button.dao.isExistButton(menuCode, Button.FUN_UPDATE_BS, 1));
		setAttr("isSlaveDelete", Button.dao.isExistButton(menuCode, Button.FUN_DELETE_BS, 1));

		render("/eova/menu/menuFun.html");
	}

	/**
	 * 新增菜单
	 */
	@Before(Tx.class)
	public void add() {

		String menuCode = getPara("code");
		String type = getPara("type");
		
		Menu temp = Menu.dao.findByCode(menuCode);
		if (temp != null) {
			renderJson(new Easy("菜单编码不能重复"));
			return;
		}

		try {
			Menu menu = new Menu();
			menu.set("parentId", getPara("parentId"));
			menu.set("icon", getPara("icon", ""));
			menu.set("name", getPara("name"));
			menu.set("code", menuCode);
			menu.set("order_num", getPara("indexNum"));
			menu.set("type", type);
			menu.set("biz_intercept", getPara("bizIntercept", ""));
			menu.set("url", getPara("url", ""));

			// 菜单配置
			MenuConfig config = new MenuConfig();
			buildConfig(type, config);
			menu.setConfig(config);
			menu.save();
		} catch (Exception e) {
			e.printStackTrace();
			renderJson(new Easy("新增菜单失败,请认真填写！"));
			return;
		}

		// 新增菜单使缓存失效
		BaseCache.delSer(EovaConst.ALL_MENU);

		// 如果是父级目录菜单没有按钮也无需关联对象
		if (type.equals(Menu.TYPE_DIR)) {
			renderJson(new Easy());
			return;
		}

		// 初始化查询按钮
		// 查询
		initQuery(menuCode);
		// 添加
		initAdd(menuCode);
		// 修改
		initUpdate(menuCode);
		// 删除
		initDelete(menuCode);
		
		// 主子模版，添加子的CRUD
		if (type.equals(TemplateConfig.MASTERSLAVEGRID)) {
			// 添加
			initSlaveAdd(menuCode);
			// 修改
			initSlaveUpdate(menuCode);
			// 删除
			initSlaveDelete(menuCode);
		}

		renderJson(new Easy());
	}

	/**
	 * 自动授权给超级管理员
	 * 
	 * @param btn
	 */
	private void autoToAdmin(int bid) {
		RoleBtn rf = new RoleBtn();
		rf.set("rid", EovaConst.DEFAULT_RID);
		rf.set("bid", bid);
		rf.save();
	}

	/**
	 * 配置菜单
	 * 
	 * @param type 模版类型
	 * @param config
	 */
	private void buildConfig(String type, MenuConfig config) {
		if (type.equals(TemplateConfig.SINGLEGRID)) {
			// 单表
			config.setObjectCode(getPara("objectCode"));
		} else if (type.equals(TemplateConfig.MASTERSLAVEGRID)) {
			// 主子表
			String masterObjectCode = getPara("masterObjectCode");
			String slaveObjectCode = getPara("slaveObjectCode");
			String masterFieldCode = getPara("masterFieldCode");
			String slaveFieldCode = getPara("slaveFieldCode");

			// 主
			config.setObjectCode(masterObjectCode);
			config.setObjectField(masterFieldCode);

			// 子
			ArrayList<String> objects = new ArrayList<String>();
			objects.add(slaveObjectCode);
			config.setObjects(objects);
			ArrayList<String> fields = new ArrayList<String>();
			fields.add(slaveFieldCode);
			config.setFields(fields);
		}
	}

	/**
	 * 菜单功能管理
	 */
	@Before(Tx.class)
	public void menuFun() {
		String menuCode = getPara(0);

		boolean isAdd = getParaToBoolean("isAdd", false);
		boolean isUpdate = getParaToBoolean("isUpdate", false);
		boolean isDelete = getParaToBoolean("isDelete", false);
		boolean isImport = getParaToBoolean("isImport", false);
		
		boolean isSlaveAdd  = getParaToBoolean("isSlaveAdd", false);
		boolean isSlaveUpdate  = getParaToBoolean("isSlaveUpdate", false);
		boolean isSlaveDelete  = getParaToBoolean("isSlaveDelete", false);

		// 删除当前菜单的基本按钮然后重新添加
		Button.dao.deleteFunByMenuCode(menuCode);

		// 添加
		if (isAdd)
			initAdd(menuCode);
		// 修改
		if (isUpdate)
			initUpdate(menuCode);
		// 删除
		if (isDelete)
			initDelete(menuCode);
		// 导入
		if (isImport)
			initImport(menuCode);
		
		// 子添加
		if (isSlaveAdd)
			initSlaveAdd(menuCode);
		// 子修改
		if (isSlaveUpdate)
			initSlaveUpdate(menuCode);
		// 子删除
		if (isSlaveDelete)
			initSlaveDelete(menuCode);

		renderJson(new Easy());
	}

	private void initQuery(String menuCode) {
		Button btn = new Button(menuCode, Button.FUN_QUERY);
		btn.set("is_base", false);
		btn.save();
		
		autoToAdmin(btn.getInt("id"));
	}

	private void initAdd(String menuCode) {
		Button btn = new Button(menuCode, Button.FUN_ADD);
		btn.save();

		autoToAdmin(btn.getInt("id"));
	}

	private void initUpdate(String menuCode) {
		Button btn = new Button(menuCode, Button.FUN_UPDATE);
		btn.save();

		autoToAdmin(btn.getInt("id"));
	}

	private void initDelete(String menuCode) {
		Button btn = new Button(menuCode, Button.FUN_DELETE);
		btn.save();
		
		autoToAdmin(btn.getInt("id"));
	}

	private void initImport(String menuCode) {
		Button btn = new Button(menuCode, Button.FUN_IMPORT);
		btn.save();
		
		autoToAdmin(btn.getInt("id"));
	}

	private void initSlaveDelete(String menuCode) {
		Button btn = new Button(menuCode, Button.FUN_DELETE);
		btn.set("name", "子删除");
		btn.set("group_num", 1);
		btn.save();
		autoToAdmin(btn.getInt("id"));
	}

	private void initSlaveUpdate(String menuCode) {
		Button btn = new Button(menuCode, Button.FUN_UPDATE);
		btn.set("group_num", 1);
		btn.set("name", "子修改");
		btn.save();
		autoToAdmin(btn.getInt("id"));
	}

	private void initSlaveAdd(String menuCode) {
		Button btn = new Button(menuCode, Button.FUN_ADD);
		btn.set("name", "子添加");
		btn.set("group_num", 1);
		btn.save();
		autoToAdmin(btn.getInt("id"));
	}
}