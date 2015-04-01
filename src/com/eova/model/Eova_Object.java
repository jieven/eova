/**
 * Copyright (c) 2013-2015, Jieven. All rights reserved.
 *
 * Licensed under the GPL license: http://www.gnu.org/licenses/gpl.txt
 * To use it on other terms please contact us at 1623736450@qq.com
 */
package com.eova.model;

import com.rzyunyou.base.BaseModel;
import com.rzyunyou.common.xx;

public class Eova_Object extends BaseModel<Eova_Object> {

	private static final long serialVersionUID = 1635722346260249629L;

	public static final Eova_Object dao = new Eova_Object();

	/**
	 * 获取view,没有视图取table
	 * @return
	 */
	public String getView() {
		String view = this.getStr("view");
		if (xx.isEmpty(view)) {
			view = this.getStr("table");
		}
		return view;
	}

	/**
	 * 根据Code获得对象
	 * @param code 对象Code
	 * @return 对象
	 */
	public Eova_Object getByCode(String code) {
		return Eova_Object.dao.findFirst("select * from eova_object where code = ?", code);
	}
}