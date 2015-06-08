/**
 * Copyright (c) 2013-2015, Jieven. All rights reserved.
 *
 * Licensed under the GPL license: http://www.gnu.org/licenses/gpl.txt
 * To use it on other terms please contact us at 1623736450@qq.com
 */
package com.eova.service;

import java.util.List;

import com.eova.model.Menu;
import com.eova.common.base.BaseService;

public class IndexService extends BaseService {


	public List<Menu> queryMenu() {
		return Menu.dao.find("select * from eova_menu order by parentId,indexNum desc");
	}
}