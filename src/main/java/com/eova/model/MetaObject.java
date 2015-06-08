/**
 * Copyright (c) 2013-2015, Jieven. All rights reserved.
 *
 * Licensed under the GPL license: http://www.gnu.org/licenses/gpl.txt
 * To use it on other terms please contact us at 1623736450@qq.com
 */
package com.eova.model;

import com.eova.common.base.BaseModel;
import com.eova.common.utils.xx;

public class MetaObject extends BaseModel<MetaObject> {

	private static final long serialVersionUID = 1635722346260249629L;

	public static final MetaObject dao = new MetaObject();

	/**
	 * 获取数据源
	 * 
	 * @return
	 */
	public String getDs() {
		return this.getStr("dataSource");
	}

	/**
	 * 获取table
	 * 
	 * @return
	 */
	public String getTable() {
		return this.getStr("table");
	}

	/**
	 * 获取主键名
	 * 
	 * @return
	 */
	public String getPk() {
		return this.getStr("pkName");
	}

	/**
	 * 获取View(没有视图用Table)
	 * 
	 * @return
	 */
	public String getView() {
		String view = this.getStr("view");
		if (xx.isEmpty(view)) {
			view = getTable();
		}
		return view;
	}

	/**
	 * 根据Code获得对象
	 * @param code 对象Code
	 * @return 对象
	 */
	public MetaObject getByCode(String code) {
		return MetaObject.dao.findFirst("select * from eova_object where code = ?", code);
	}
}