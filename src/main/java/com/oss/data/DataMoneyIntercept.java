package com.oss.data;

import com.eova.aop.AopContext;
import com.eova.aop.MetaObjectIntercept;
import com.jfinal.kit.Kv;
import com.jfinal.plugin.activerecord.Record;

/**
 * 为Grid添加Footer汇总统计行
 * @author Jieven
 *
 */
public class DataMoneyIntercept extends MetaObjectIntercept {

	@Override
	public Kv queryFooter(AopContext ac) throws Exception {
		double sum = 0;
		double sum1 = 0;
		double sum2 = 0;

		for (Record record : ac.records) {
			sum += record.getDouble("num");
			sum1 += record.getDouble("num1");
			sum2 += record.getDouble("num2");
		}

		return new Kv().set("moon", "汇总:(单位/元)").set("num", sum).set("num1", sum1).set("num2", sum2);
	}

}