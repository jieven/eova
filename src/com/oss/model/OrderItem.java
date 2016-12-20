/**
 * Copyright (c) 2013-2016, Jieven. All rights reserved.
 *
 * Licensed under the GPL license: http://www.gnu.org/licenses/gpl.txt
 * To use it on other terms please contact us at 1623736450@qq.com
 */
package com.oss.model;

import java.util.List;

import com.eova.common.base.BaseModel;

/**
 * 订单项
 * @author Jieven
 *
 */
public class OrderItem extends BaseModel<OrderItem> {

	private static final long serialVersionUID = 1064291771401662738L;

	public static final OrderItem dao = new OrderItem();
	
	public List<OrderItem> findOrderItemByOrderId(int orderId){
		return this.find("select * from order_item where order_id = ?", orderId);
	}
}