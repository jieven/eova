/**
 * Copyright (c) 2013-2015, Jieven. All rights reserved.
 *
 * Licensed under the GPL license: http://www.gnu.org/licenses/gpl.txt
 * To use it on other terms please contact us at 1623736450@qq.com
 */
package com.eova.core.menu;

import com.eova.common.Easy;
import com.eova.common.base.BaseCache;
import com.eova.common.utils.xx;
import com.eova.config.EovaConst;
import com.eova.model.Button;
import com.eova.model.Menu;
import com.eova.model.MenuObject;
import com.eova.model.RoleBtn;
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
		setAttr("menuCode", menuCode);

		setAttr("isAdd", Button.dao.isExistButton(menuCode, EovaConst.FUN_ADD_BS));
		setAttr("isUpdate", Button.dao.isExistButton(menuCode, EovaConst.FUN_UPDATE_BS));
		setAttr("isDelete", Button.dao.isExistButton(menuCode, EovaConst.FUN_DELETE_BS));
		setAttr("isImport", Button.dao.isExistButton(menuCode, EovaConst.FUN_IMPORT_BS));

		render("/eova/menu/menuFun.html");
	}

	/**
	 * 新增菜单
	 */
	@Before(Tx.class)
	public void add() {
		
		String menuCode = getPara("code");
		String type = getPara("type");
		
		Menu menu = new Menu();
		menu.set("parentId", getPara("parentId"));
		menu.set("icon", getPara("icon",""));
		menu.set("name", getPara("name"));
		menu.set("code", menuCode);
		menu.set("indexNum", getPara("indexNum"));
		menu.set("type", type);
		menu.set("bizIntercept", getPara("bizIntercept", ""));
		menu.set("url", getPara("url", ""));
		menu.save();
		
		// 新增菜单使缓存失效 
		BaseCache.delSer(EovaConst.ALL_MENU);
		
		// 如果是父级目录菜单没有按钮也无需关联对象
		if (type.equals(Menu.TYPE_DIR)) {
			renderJson(new Easy());
			return;
		}
		
		// 初始化查询按钮
		{
			Button btn = new Button();
			btn.set("menuCode", menuCode);
			btn.set("name", EovaConst.FUN_QUERY);
			btn.save();
			// 自动授权给超级管理员
			RoleBtn rf = new RoleBtn();
			rf.set("rid", EovaConst.DEFAULT_RID);
			rf.set("bid", btn.getInt("id"));
			rf.save();
		}
		// 添加
		{
			Button btn = new Button();
			btn.set("menuCode", menuCode);
			btn.set("name", EovaConst.FUN_ADD);
			btn.set("ui", EovaConst.FUN_ADD_UI);
			btn.set("bs", EovaConst.FUN_ADD_BS);
			btn.save();
			// 自动授权给超级管理员
			RoleBtn rf = new RoleBtn();
			rf.set("rid", EovaConst.DEFAULT_RID);
			rf.set("bid", btn.getInt("id"));
			rf.save();
		}
		// 修改
		{
			Button btn = new Button();
			btn.set("menuCode", menuCode);
			btn.set("name", EovaConst.FUN_UPDATE);
			btn.set("ui", EovaConst.FUN_UPDATE_UI);
			btn.set("bs", EovaConst.FUN_UPDATE_BS);
			btn.save();
			// 自动授权给超级管理员
			RoleBtn rf = new RoleBtn();
			rf.set("rid", EovaConst.DEFAULT_RID);
			rf.set("bid", btn.getInt("id"));
			rf.save();
		}
		// 删除
		{
			Button btn = new Button();
			btn.set("menuCode", menuCode);
			btn.set("name", EovaConst.FUN_DELETE);
			btn.set("ui", EovaConst.FUN_DELETE_UI);
			btn.set("bs", EovaConst.FUN_DELETE_BS);
			btn.save();
			// 自动授权给超级管理员
			RoleBtn rf = new RoleBtn();
			rf.set("rid", EovaConst.DEFAULT_RID);
			rf.set("bid", btn.getInt("id"));
			rf.save();
		}
		
		// 单表-菜单关联对象
		if (type.equals(Menu.TYPE_SINGLEGRID)) {
			// 单表只有一个对象
			MenuObject mo = new MenuObject();
			mo.set("menuCode", menuCode);
			mo.set("objectCode", getPara("objectCode"));
			mo.save();
		}
		
		// TODO 其它业务模版
		
		renderJson(new Easy());
	}

	/**
	 * 菜单功能管理
	 */
	public void menuFun() {
		String menuCode = getPara(0);

		String isAdd = getPara("isAdd");
		String isUpdate = getPara("isUpdate");
		String isDelete = getPara("isDelete");
		String isImport = getPara("isImport");

		// 删除当前菜单的基本按钮然后重新添加
		Button.dao.deleteFunByMenuCode(menuCode);

		// 添加
		if (!xx.isEmpty(isAdd)) {
			Button btn = new Button();
			btn.set("menuCode", menuCode);
			btn.set("name", EovaConst.FUN_ADD);
			btn.set("ui", EovaConst.FUN_ADD_UI);
			btn.set("bs", EovaConst.FUN_ADD_BS);
			btn.save();
		}
		// 修改
		if (!xx.isEmpty(isUpdate)) {
			Button btn = new Button();
			btn.set("menuCode", menuCode);
			btn.set("name", EovaConst.FUN_UPDATE);
			btn.set("ui", EovaConst.FUN_UPDATE_UI);
			btn.set("bs", EovaConst.FUN_UPDATE_BS);
			btn.save();
		}
		// 删除
		if (!xx.isEmpty(isDelete)) {
			Button btn = new Button();
			btn.set("menuCode", menuCode);
			btn.set("name", EovaConst.FUN_DELETE);
			btn.set("ui", EovaConst.FUN_DELETE_UI);
			btn.set("bs", EovaConst.FUN_DELETE_BS);
			btn.save();
		}
		// 导入
		if (!xx.isEmpty(isImport)) {
			Button btn = new Button();
			btn.set("menuCode", menuCode);
			btn.set("name", EovaConst.FUN_IMPORT);
			btn.set("ui", EovaConst.FUN_IMPORT_UI);
			btn.set("bs", EovaConst.FUN_IMPORT_BS);
			btn.save();
		}

		renderJson(new Easy());
	}

}