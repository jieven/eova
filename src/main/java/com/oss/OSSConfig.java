/**
 * Copyright (c) 2013-2016, Jieven. All rights reserved.
 *
 * Licensed under the GPL license: http://www.gnu.org/licenses/gpl.txt
 * To use it on other terms please contact us at 1623736450@qq.com
 */
package com.oss;

import java.util.HashMap;
import java.util.HashSet;

import com.eova.config.EovaConfig;
import com.eova.core.vip.VipController;
import com.eova.interceptor.LoginInterceptor;
import com.eova.user.UserController;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.oss.product.ProductController;
import com.oss.test.XlsController;

public class OSSConfig extends EovaConfig {

	/**
	 * 自定义路由
	 *
	 * @param me
	 */
	@Override
	protected void route(Routes me) {
		// 自定义的路由配置往这里加。。。
		me.add("/user", UserController.class);

		me.add("/", OSSController.class);
		me.add("/product", ProductController.class);

		me.add("/xls", XlsController.class);
		LoginInterceptor.excludes.add("/xls/**");

		me.add("/vip", VipController.class);
		LoginInterceptor.excludes.add("/vip/**");

		// 排除不需要登录拦截的URI 语法同SpringMVC拦截器配置 @see com.eova.common.utils.util.AntPathMatcher
		LoginInterceptor.excludes.add("/init");
		LoginInterceptor.excludes.add("/code");
		LoginInterceptor.excludes.add("/test");
		// LoginInterceptor.excludes.add("/xxxx/**");
	}

	/**
	 * 自定义Main数据源Model映射
	 *
	 * @param arp
	 */
	@Override
	protected void mapping(HashMap<String, ActiveRecordPlugin> arps) {
		// 获取主数据源的ARP
		// ActiveRecordPlugin main = arps.get(xx.DS_MAIN);
		// 自定义业务Model映射往这里加
		// main.addMapping("user_info", UserInfo.class);
		// main.addMapping("users", Users.class);
		// main.addMapping("address", Address.class);
		// main.addMapping("orders", Orders.class);

		// 获取其它数据源的ARP
		// ActiveRecordPlugin xxx = arps.get("xxx");
	}

	/**
	 * 自定义插件
	 */
	@Override
	protected void plugin(Plugins plugins) {
		/** 新增自定义数据源start **/
		// ActiveRecordPlugin arp = addDataSource(plugins, "oss", JdbcUtils.MYSQL);
		// arp.addMapping("xxx", Xxx.class);
		/** 新增自定义数据源end **/

		// 添加自动扫描插件

		// ...
	}

	/**
	 * 自定义表达式(主要用于级联)
	 */
	@Override
	protected void exp() {
		super.exp();
		// 区域级联查询
		exps.put("selectAreaByLv2AndPid", "select id ID,name CN from area where lv = 2 and pid = ?");
		exps.put("selectAreaByLv3AndPid", "select id ID,name CN from area where lv = 3 and pid = ?");
		exps.put("selectEovaMenu", "select id,parent_id pid, name, iconskip from eova_menu;ds=eova");
		// 用法，级联动态在页面改变SQL和参数
		// $xxx.eovacombo({exp : 'selectAreaByLv2AndPid,aaa,10'}).reload();
		// $xxx.eovafind({exp : 'selectAreaByLv2AndPid,aaa,10'});
		// $xxx.eovatree({exp : 'selectAreaByLv2AndPid,10'});
	}

	@Override
	protected void authUri() {
		super.authUri();
		HashSet<String> uris = new HashSet<String>();
		uris.add("/xxx/**");
		// auths.put(角色ID, uris);
	}

}