package com.oss.global;

import com.eova.aop.eova.EovaContext;
import com.eova.aop.eova.EovaIntercept;

/**
 * 全局Eova业务拦截器
 * @author Jieven
 *
 */
public class GlobalEovaIntercept extends EovaIntercept {

	@Override
	public String filterQuery(EovaContext ec) throws Exception {
		System.out.println("Query过滤");
		//		if (!ec.object.getCode().startsWith("eova_")) {
		//			return " and id < 999999 or id = 10086";
		//		}
		return "";
	}

	@Override
	public String filterExp(EovaContext ec) throws Exception {
		System.out.println("Exp过滤");
		//		if (ec.exp.table.startsWith("eova_")) {
		//			// 根据表前缀
		//		}
		//		for (MetaField f : ec.exp.getFields()) {
		//			// 根据表达式元字段
		//		}
		//		if (ec.exp.simpleSelect.indexOf("xxx_name") != -1) {
		//			// 根据查询项 关键字
		//		}

		//		return " and id < 9999";
		return "";
	}

}
