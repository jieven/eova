/**
 * Copyright (c) 2013-2015, Jieven. All rights reserved.
 *
 * Licensed under the GPL license: http://www.gnu.org/licenses/gpl.txt
 * To use it on other terms please contact us at 1623736450@qq.com
 */
package com.eova.core.menu;

import java.util.List;

import com.eova.common.base.BaseCache;
import com.eova.config.EovaConst;
import com.eova.model.Button;
import com.eova.model.Menu;
import com.eova.model.RoleBtn;
import com.eova.template.crud.CrudIntercept;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Record;

public class MenuIntercept implements CrudIntercept {

	@Override
	public void addBefore(Controller ctrl, Record record) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addAfter(Controller ctrl, Record record) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addSucceed(Controller ctrl, Record record) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteBefore(Controller ctrl, String pkValues) throws Exception {
		Menu menu = Menu.dao.findById(pkValues); 
		
		String code = menu.getStr("code");
		
		// 删除菜单按钮关联权限
		RoleBtn.dao.deleteByMenuCode(code);
		
		// 删除菜单关联按钮
		Button.dao.deleteByMenuCode(code);
		
		// 删除菜单关联对象,不能删除对象，因为对象可能被多个菜单用
		// MenuObject.dao.deleteByMenuCode(code);

	}

	@Override
	public void deleteAfter(Controller ctrl, String pkValues) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteSucceed(Controller ctrl, String pkValues) throws Exception {
		// 新增菜单使缓存失效 
		BaseCache.delSer(EovaConst.ALL_MENU);
	}

	@Override
	public void updateBefore(Controller ctrl, Record record) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateAfter(Controller ctrl, Record record) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateSucceed(Controller ctrl, Record record) throws Exception {
		// 新增菜单使缓存失效 
		BaseCache.delSer(EovaConst.ALL_MENU);
	}

	@Override
	public void importBefore(Controller ctrl, List<Record> records) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void importAfter(Controller ctrl, List<Record> records) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void importSucceed(Controller ctrl, List<Record> records) throws Exception {
		// TODO Auto-generated method stub

	}
}