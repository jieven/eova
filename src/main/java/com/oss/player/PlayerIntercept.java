/**
 * Copyright (c) 2013-2015, Jieven. All rights reserved.
 *
 * Licensed under the GPL license: http://www.gnu.org/licenses/gpl.txt
 * To use it on other terms please contact us at 1623736450@qq.com
 */
package com.oss.player;

import java.util.List;

import com.eova.template.crud.CrudIntercept;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Record;

public class PlayerIntercept implements CrudIntercept {

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
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteAfter(Controller ctrl, String pkValues) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteSucceed(Controller ctrl, String pkValues) throws Exception {
		// TODO Auto-generated method stub

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
		// TODO Auto-generated method stub

	}

	@Override
	public void importBefore(Controller ctrl, List<Record> records) throws Exception {
		for (Record record : records) {
			if (record.getStr("status").equals("3")) {
				throw new Exception("数据状态异常");
			}
		}
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