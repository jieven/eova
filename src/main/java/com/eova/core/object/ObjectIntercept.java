/**
 * Copyright (c) 2013-2015, Jieven. All rights reserved.
 *
 * Licensed under the GPL license: http://www.gnu.org/licenses/gpl.txt
 * To use it on other terms please contact us at 1623736450@qq.com
 */
package com.eova.core.object;

import com.eova.model.MetaField;
import com.eova.template.crud.CrudIntercept;
import com.jfinal.core.Controller;

public class ObjectIntercept extends CrudIntercept {

	@Override
	public void deleteBefore(Controller ctrl, String pkValues) throws Exception {
		// 删除对象关联属性
		MetaField.dao.deleteByObjectCode(pkValues);
	}

}