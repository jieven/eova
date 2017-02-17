/**
 * Copyright (c) 2013-2016, Jieven. All rights reserved.
 *
 * Licensed under the GPL license: http://www.gnu.org/licenses/gpl.txt
 * To use it on other terms please contact us at 1623736450@qq.com
 */
package com.oss.test;

import com.eova.aop.AopContext;
import com.eova.aop.MetaObjectIntercept;
import com.jfinal.plugin.activerecord.Record;

public class TestIntercept extends MetaObjectIntercept {

	@Override
	public String updateBefore(AopContext ac) throws Exception {
		Record r = ac.record;
		System.out.println(r.toJson());
		System.out.println("id=" + r.getInt("id"));
		System.out.println("status=" + r.getInt("status"));
		System.out.println("age=" + r.getInt("age"));
		System.out.println("name=" + r.getStr("name"));
		System.out.println("age" + r.getInt("age"));
		System.out.println("delete_flag=" + r.getBoolean("delete_flag"));
		System.out.println("update_time=" + r.getDate("update_time"));
		System.out.println("create_time=" + r.getTimestamp("create_time"));
		System.out.println("date time=" + r.getDate("test7"));
		return null;
	}

}