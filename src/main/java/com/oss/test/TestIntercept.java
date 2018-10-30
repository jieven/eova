/**
 * Copyright (c) 2013-2016, Jieven. All rights reserved.
 *
 * Licensed under the GPL license: http://www.gnu.org/licenses/gpl.txt
 * To use it on other terms please contact us at 1623736450@qq.com
 */
package com.oss.test;

import org.joda.time.DateTime;

import com.eova.aop.AopContext;
import com.eova.aop.MetaObjectIntercept;
import com.eova.common.utils.xx;
import com.eova.common.utils.time.TimestampUtil;
import com.jfinal.plugin.activerecord.Record;

public class TestIntercept extends MetaObjectIntercept {

	@Override
	public void queryBefore(AopContext ac) throws Exception {
		String s = ac.ctrl.getPara("query_v_year");
		if (!xx.isEmpty(s)) {
			int age = DateTime.now().getYear() - xx.toInt(s);

			ac.condition = "and age = ?";
			ac.params.add(age);
		}
	}

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
		System.out.println("type1 bl=" + r.getBoolean("type1"));
		System.out.println("type2 int" + r.getInt("type2"));
		System.out.println("type3 long=" + r.getLong("type3"));
		System.out.println("type4 BIG=" + r.getBigDecimal("type4"));
		System.out.println("type5 float=" + r.getFloat("type5"));
		System.out.println("type6 double=" + r.getDouble("type6"));
		System.out.println("type7 BIG=" + r.getBigDecimal("type7"));
		return null;
	}

	@Override
	public void addInit(AopContext ac) throws Exception {
		ac.fixed.set("name", "你好,");
		ac.fixed.set("update_time", TimestampUtil.getNow());
	}

	@Override
	public void updateInit(AopContext ac) throws Exception {
//		ac.fixed.set("update_time", new Date());
		// new Date(); 当前日期
		// TimestampUtil.getNow(); 当前时间
		// DateTime.now().minusDays(1).toDate(); 减1天
	}

	@Override
	public String updateSucceed(AopContext ac) throws Exception {
		//		try {
		//			String[] a = { "1" };
		//			System.out.println(a[3]);
		//		} catch (Exception e) {
		//			throw new Exception("抛出一个异常给开发人员看");
		//			// return Easy.error("修改错误，请联系技术支持解决！");
		//		}
		return super.updateSucceed(ac);
	}

}