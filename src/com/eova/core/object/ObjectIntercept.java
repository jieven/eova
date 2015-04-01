/**
 * Copyright (c) 2013-2015, Jieven. All rights reserved.
 *
 * Licensed under the GPL license: http://www.gnu.org/licenses/gpl.txt
 * To use it on other terms please contact us at 1623736450@qq.com
 */
package com.eova.core.object;

import com.eova.model.Eova_Item;
import com.eova.template.crud.CrudIntercept;
import com.jfinal.plugin.activerecord.Record;

public class ObjectIntercept implements CrudIntercept {

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
		// 删除对象关联属性
		Eova_Item.dao.deleteByObjectCode(pkValues);
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