package com.demo;

import com.eova.aop.AopContext;
import com.eova.aop.MetaObjectIntercept;
import com.eova.common.Easy;

/**
 * 
 * 演示业务拦截器
 * 
 * @author Jieven
 * 
 */
public class Demo1Intercept extends MetaObjectIntercept {

	/**
	 * 查询前置 DIY查询条件
	 */
	@Override
	public void queryBefore(AopContext ac) throws Exception {
		// 追加条件
		ac.condition = " and id < ?";
		ac.params.add(999);

		// 覆盖条件
		// ac.where = " where id < ?";
		// ac.params.add(5);
	}

	@Override
	public String updateBefore(AopContext ac) throws Exception {

		int id = 1;

		if (id == 1) {
			// return Easy.info("弹出一个提示消息-人之门");
			return Easy.warn("弹出一个警告消息-地之门");
			// return Easy.error("弹出一个错误消息-天之门");
			// return "弹出一个默认提示";
			// throw new Exception("抛出一个业务异常！！");
		}

		return super.updateBefore(ac);
	}

	@Override
	public String addSucceed(AopContext ac) throws Exception {
		Long id = ac.records.get(0).get("id");
		System.out.println("新增数据:" + id);

		return Easy.info("新增成功");
	}

}
