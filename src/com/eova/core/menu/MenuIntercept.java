/**
 * Copyright (c) 2013-2015, Jieven. All rights reserved.
 *
 * Licensed under the GPL license: http://www.gnu.org/licenses/gpl.txt
 * To use it on other terms please contact us at 1623736450@qq.com
 */
package com.eova.core.menu;

import com.eova.model.Eova_Button;
import com.eova.model.Eova_Menu;
import com.eova.model.Eova_Menu_Object;
import com.eova.model.Eova_Role_Btn;
import com.eova.template.crud.CrudIntercept;
import com.jfinal.plugin.activerecord.Record;

public class MenuIntercept implements CrudIntercept {

	public void addBefore(Record record) throws Exception {
		// TODO Auto-generated method stub
		
	}

	public void addAfter(Record record) throws Exception {
		// TODO Auto-generated method stub
		
	}

	public void addSucceed(Record record) throws Exception {
		// TODO Auto-generated method stub
		
	}

	public void deleteBefore(String pkValues) throws Exception {
		Eova_Menu menu = Eova_Menu.dao.findById(pkValues); 
		
		String code = menu.getStr("code");
		
		// 删除菜单按钮关联权限
		Eova_Role_Btn.dao.deleteByMenuCode(code);
		
		// 删除菜单关联按钮
		Eova_Button.dao.deleteByMenuCode(code);
		
		// 删除菜单关联对象
		Eova_Menu_Object.dao.deleteByMenuCode(code);
	}

	public void deleteAfter(String pkValues) throws Exception {
		// TODO Auto-generated method stub
		
	}

	public void deleteSucceed(String pkValues) throws Exception {
		// TODO Auto-generated method stub
		
	}

	public void updateBefore(Record record) throws Exception {
		// TODO Auto-generated method stub
		
	}

	public void updateAfter(Record record) throws Exception {
		// TODO Auto-generated method stub
		
	}

	public void updateSucceed(Record record) throws Exception {
		// TODO Auto-generated method stub
		
	}
}