/**
 * Copyright (c) 2013-2015, Jieven. All rights reserved.
 *
 * Licensed under the GPL license: http://www.gnu.org/licenses/gpl.txt
 * To use it on other terms please contact us at 1623736450@qq.com
 */
package com.eova.template.masterslave;

import java.util.ArrayList;
import java.util.List;

import com.eova.model.Button;
import com.eova.model.Menu;
import com.eova.model.MenuConfig;
import com.eova.model.MetaField;
import com.eova.model.MetaObject;
import com.eova.model.User;
import com.jfinal.core.Controller;

/**
 * 主子表业务模版:Grid
 * 
 * @author Jieven
 * 
 */
public class MasterSlaveController extends Controller {

	public void list() {

		String menuCode = this.getPara(0);

		Menu menu = Menu.dao.findByCode(menuCode);
		setAttr("menu", menu);

		MenuConfig config = menu.getConfig();
		String objectCode = config.getObjectCode();
		String slaveCode = config.getObjects().get(0);

		List<MetaField> fields = MetaField.dao.queryByObjectCode(objectCode);
		setAttr("itemList", fields);

		setAttr("config", config);

		setAttr("objectCode", objectCode);
		setAttr("isQuery", MetaObject.dao.isExistQuery(objectCode));

		// 根据权限获取功能按钮
		User user = this.getSessionAttr("user");
		List<Button> btnList = Button.dao.queryByMenuCode(menuCode, user.getInt("rid"));

		List<Button> btns = new ArrayList<Button>();
		List<Button> btns1 = new ArrayList<Button>();
		for (Button b : btnList) {
			if (b.getInt("group_num") == 1) {
				btns1.add(b);
			} else {
				btns.add(b);
			}
		}

		setAttr("btns", btns);
		setAttr("btns1", btns1);

		setAttr("object", MetaObject.dao.getByCode(objectCode));
		setAttr("slaveObject", MetaObject.dao.getByCode(slaveCode));

		render("/eova/template/masterslave/list.html");
	}

}