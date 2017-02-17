package com.demo;

import java.util.Date;

import com.eova.aop.AopContext;
import com.eova.aop.MetaObjectIntercept;
import com.eova.common.Easy;
import com.eova.common.utils.xx;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

/**
 * 
 * 演示业务拦截器
 * 
 * @author Jieven
 * 
 */
public class DemoIntercept extends MetaObjectIntercept {

	/**
	 * 名词解释<br>
	 * 虚拟字段：DB中实际不存在该字段,元字段禁止新增，查询等任何持久化操作
	 * 
	 */

	// 返回提示：
	// return Easy.info("弹出一个提示消息-人之门");
	// return Easy.warn("弹出一个警告消息-地之门");
	// return Easy.error("弹出一个错误消息-天之门");
	// return "弹出一个默认提示";
	// throw new Exception("抛出一个业务异常！！");

	/**
	 * 查询前置 DIY查询条件
	 */
	@Override
	public void queryBefore(AopContext ac) throws Exception {
		// DIY查询，名称有值，用名称里面的值来查状态列
		String name = ac.ctrl.getPara("query_name");
		if (!xx.isEmpty(name)) {
			ac.condition = " state = " + name;
		}

		// 同理：想添加自定义查询条件，直接添加元字段
		// 然后配置为 可查询，禁止编辑，禁止新增，然后获取值
		// 利用本AOP方法可自定义查询sql即可
		// 应用场景：like or 子查询 范围查询 外表关联查询...

		// 追加条件
		// ac.condition = " and id < ?";
		// ac.params.add(999);

		// 覆盖条件
		// ac.where = " where id < ?";
		// ac.params.add(5);

		// 覆盖排序
		// ac.sort = " order by id desc";
	}

	/**
	 * 查询后置 计算总额
	 */
	@Override
	public void queryAfter(AopContext ac) throws Exception {

		double total = 0;

		for (Record record : ac.records) {

			// 累加
			double price = record.getBigDecimal("pay_price").doubleValue();
			total += price;

			// 格式化RMB,也可以通过元字段-格式化器JS进行更丰富的格式化
			record.set("v_total", xx.toDouble(String.format("%.2f", total)));
			// v_total 为虚拟字段
		}

	}

	/**
	 * 新增前置 自动获取当前时间作为注册时间，自动获取当前登录用户公司ID
	 */
	@Override
	public String addBefore(AopContext ac) throws Exception {

		String name = ac.record.getStr("name");

		String sql = "select * from table111 where name = ?";
		Record r = Db.use(xx.DS_MAIN).findFirst(sql, name);
		if (r != null) {
			return Easy.error("名字不能重复");
		}

		// 自动获取值
		ac.record.set("reg_time", new Date());
		ac.record.set("company_id", ac.user.get("company_id"));

		// 如果很多字段都需要添加，可以讲业务写在父类中，然后批量元对象的拦截器设置为父类

		return super.addBefore(ac);
	}

	/**
	 * 新增成功 清理缓存
	 */
	@Override
	public String addSucceed(AopContext ac) throws Exception {
		// 新增数据使缓存失效
		// BaseCache.delSer(xxx Data Cache Key);
		Integer id = ac.record.get("id");
		System.out.println("新增数据:" + id);
		return super.addSucceed(ac);
	}

	/**
	 * 删除主表之前,删除子表数据,子表删除异常，事务回滚
	 */
	@Override
	public String deleteBefore(AopContext ac) throws Exception {
		String id = ac.record.getStr("id");

		String sql = "delete from xxx_table where order_id = ?";
		// Db.use(xx.DS_MAIN).update(sql, id);

		System.out.println("级联删除数据:" + sql + id);

		// 同理：级联删除、更新、备份、记日志...

		return super.deleteBefore(ac);
	}

	/**
	 * 更新前置 自动设置更新时间(将update_time字段设为禁止修改，由此提供数据)
	 */
	@Override
	public String updateBefore(AopContext ac) throws Exception {

		ac.record.set("update_time", new Date());

		return super.updateBefore(ac);
	}

	@Override
	public void addInit(AopContext ac) throws Exception {
		ac.fixed.set("name", "系统管理员");// 新增页，默认强制指定 name=系统管理员
		// 实际上推荐使用禁用字段，然后addBefore();ac.record.set("reg_time", new Date());
	}
	
}
