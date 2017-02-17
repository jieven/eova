/**
 * Copyright (c) 2013-2016, Jieven. All rights reserved.
 *
 * Licensed under the GPL license: http://www.gnu.org/licenses/gpl.txt
 * To use it on other terms please contact us at 1623736450@qq.com
 */
package com.oss.order;

import java.util.Date;

import com.eova.aop.AopContext;
import com.eova.aop.MetaObjectIntercept;
import com.eova.common.utils.jfinal.RecordUtil;
import com.jfinal.kit.LogKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.oss.model.Address;
import com.oss.model.Orders;
import com.oss.model.Users;

public class OrderIntercept extends MetaObjectIntercept {

	@Override
	public String deleteBefore(AopContext ac) throws Exception {
		Integer id = ac.record.getInt("id");
		Db.deleteById("orders", id);
		// 其它关联表数据是否要删自行判定

		return super.deleteBefore(ac);
	}

	/**
	 * 推荐使用Model来实现业务逻辑，万物皆对象，不要问我为什么！
	 */
	@Override
	public String addBefore(AopContext ac) throws Exception {
		Record data = ac.record;

		// 获取用户信息 保存
		Users user = RecordUtil.peelModel(Users.class, data, "login_id", "nickname", "info");
		user.save();

		int uid = user.getInt("id");
		LogKit.info("新增用户成功：" + user.getInt("id"));

		// 获取收货信息 保存
		Address address = RecordUtil.peelModel(Address.class, data, "name", "full", "mobilephone");
		address.save();

		int aid = address.getInt("id");
		LogKit.info("新增收获信息成功：" + aid);

		Orders order = RecordUtil.peelModel(Orders.class, data);
		order.set("address_id", aid);
		order.set("create_user_id", uid);
		order.set("update_user_id", uid);
		order.set("create_time", new Date());
		order.set("update_time", new Date());
		order.save();
		LogKit.info("新增订单信息成功：");

		return super.addBefore(ac);
	}

	/**
	 * 懒人做法Record，一条龙全部搞定，简单粗暴！
	 */
	@Override
	public String updateBefore(AopContext ac) throws Exception {
		Record r = ac.record;

		// 获取用户信息 更新
		Record user = RecordUtil.peel(r, "create_user_id -> id", "login_id", "nickname", "info");
		Db.update("users", user);
		
		int uid = user.getInt("id");
		LogKit.info("更新用户成功：" + uid);

		// 获取收获信息 更新
		Record address = RecordUtil.peel(r, "address_id -> id", "name", "full", "mobilephone");
		Db.update("address", address);

		int aid = address.getInt("id");
		LogKit.info("更新收获信息成功：" + aid);

		Db.update("orders", r);
		LogKit.info("更新订单信息成功：");

		return super.updateBefore(ac);
	}

}