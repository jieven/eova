/**
 * Copyright (c) 2013-2015, Jieven. All rights reserved.
 *
 * Licensed under the GPL license: http://www.gnu.org/licenses/gpl.txt
 * To use it on other terms please contact us at 1623736450@qq.com
 */
package com.eova.core.object;

import com.eova.core.meta.MetaObjectIntercept;
import com.eova.model.MetaField;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Record;

public class ObjectIntercept extends MetaObjectIntercept {

	@Override
	public void deleteBefore(Controller ctrl, Record record) throws Exception {
		// 删除对象关联元字段属性
		MetaField.dao.deleteByObjectId(record.getInt("id"));
	}
	
}