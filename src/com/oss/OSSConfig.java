/**
 * Copyright (c) 2013-2016, Jieven. All rights reserved.
 *
 * Licensed under the GPL license: http://www.gnu.org/licenses/gpl.txt
 * To use it on other terms please contact us at 1623736450@qq.com
 */
package com.oss;

import com.eova.config.EovaConfig;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.core.JFinal;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.oss.model.Hotel;
import com.oss.model.OrderItem;
import com.oss.model.Orders;
import com.oss.model.Product;

public class OSSConfig extends EovaConfig {

	/**
	 * 自定义路由
	 * 
	 * @param me
	 */
	@Override
	protected void route(Routes me) {
		// 自定义的路由配置往这里加。。。
		// me.add("/xxx", XxxController.class);
	}

	/**
	 * 自定义Main数据源Model映射
	 * 
	 * @param arp
	 */
	@Override
	protected void mapping(ActiveRecordPlugin arp) {
		arp.addMapping("hotel", Hotel.class);
		arp.addMapping("product", Product.class);
		arp.addMapping("orders", Orders.class);
		arp.addMapping("order_item", OrderItem.class);
		// 自定义的Model映射往这里加。。。
	}

	/**
	 * 自定义插件
	 */
	@Override
	protected void plugin(Plugins plugins) {
		// 添加数据源
		// 添加自动扫描插件
		// ...
	}

	/**
	 * Run Server
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		JFinal.start("webapp", 80, "/", 0);
	}

}