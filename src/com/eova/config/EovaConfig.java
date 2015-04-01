/**
 * Copyright (c) 2013-2015, Jieven. All rights reserved.
 *
 * Licensed under the GPL license: http://www.gnu.org/licenses/gpl.txt
 * To use it on other terms please contact us at 1623736450@qq.com
 */
package com.eova.config;

import org.beetl.core.GroupTemplate;
import org.beetl.ext.jfinal.BeetlRenderFactory;

import com.alibaba.druid.filter.stat.StatFilter;
import com.alibaba.druid.wall.WallFilter;
import com.eova.beetl.IsTrueFun;
import com.eova.core.IndexController;
import com.eova.core.auth.AuthController;
import com.eova.core.menu.MenuController;
import com.eova.core.object.MetaDataController;
import com.eova.interceptor.AuthInterceptor;
import com.eova.interceptor.LoginInterceptor;
import com.eova.model.Eova_Button;
import com.eova.model.Eova_Item;
import com.eova.model.Eova_Log;
import com.eova.model.Eova_Menu;
import com.eova.model.Eova_Menu_Object;
import com.eova.model.Eova_Object;
import com.eova.model.Eova_Role;
import com.eova.model.Eova_Role_Btn;
import com.eova.model.Eova_User;
import com.eova.service.ServiceManager;
import com.eova.template.crud.CrudConfig;
import com.eova.template.crud.CrudController;
import com.eova.template.crudtg.CrudTgController;
import com.eova.widget.WidgetController;
import com.jfinal.config.Constants;
import com.jfinal.config.Handlers;
import com.jfinal.config.Interceptors;
import com.jfinal.config.JFinalConfig;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.druid.DruidPlugin;
import com.jfinal.plugin.druid.DruidStatViewHandler;
import com.jfinal.plugin.ehcache.EhCachePlugin;
import com.rzyunyou.common.xx;
import com.rzyunyou.common.db.MysqlUtil;

public class EovaConfig extends JFinalConfig {

	private long startTime = 0;

	/**
	 * 系统启动之后  
	 */
	public void afterJFinalStart() {
		System.err.println("JFinal Started\n");
		// Load FreeMarker Const
		PageConst.init();
		// Load Cost Time
		costTime(startTime);
	}

	/**
	 * 系统停止之前
	 */
	public void beforeJFinalStop() {
	}

	/**
	 * 配置常量
	 */
	public void configConstant(Constants me) {
		startTime = System.currentTimeMillis();

		System.err.println("Config Constants Starting...");
		// 加载配置文件
		loadPropertyFile("eova.config");
		// 开发模式
		me.setDevMode(getPropertyToBoolean("devMode", true));
		// 默认主视图
		// me.setViewType(ViewType.FREE_MARKER);
		// 设置主视图为Beetl
		me.setMainRenderFactory(new BeetlRenderFactory());
		// POST内容最大500M(安装包上传)
		me.setMaxPostSize(1024 * 1024 * 500);

		GroupTemplate group = BeetlRenderFactory.groupTemplate;
		// 注册函数
		group.registerFunction("isTrue", new IsTrueFun());
		// 设置全局变量
		// Map<String, Object> sharedVars = new HashMap<String, Object>();
		// sharedVars.put("app_name", "日照市云游网络科技有限公司");
		// BeetlRenderFactory.groupTemplate.setSharedVars(sharedVars);
	}

	/**
	 * 配置路由
	 */
	public void configRoute(Routes me) {
		System.err.println("Config Routes Starting...");
		me.add("/", IndexController.class);
		me.add(CrudConfig.contro, CrudController.class);
		me.add("/crudtg", CrudTgController.class);
		me.add("/widget", WidgetController.class);
		me.add("/metadata", MetaDataController.class);
		me.add("/menu", MenuController.class);
		me.add("/auth", AuthController.class);

		/* 自定义业务 */
//		me.add("/user", UserController.class);
//		me.add("/msg", MsgController.class);
//		me.add("/player", PlayerController.class);
//		me.add("/gift", GiftController.class);
//		me.add("/game", GameController.class);
//		me.add("/gms", GmsController.class);
//		me.add("/bbs", BbsController.class);
	}

	/**
	 * 配置插件
	 */
	public void configPlugin(Plugins me) {
		System.err.println("Config Plugins Starting...");

		// 设置Mysql方言
		WallFilter wall = new WallFilter();
		wall.setDbType("mysql");

		// DruidPlugin Default DataSource
		DruidPlugin main = new DruidPlugin(getProperty("url"), getProperty("user"), getProperty("pwd"));
		main.addFilter(new StatFilter());
		main.addFilter(wall);
		me.add(main);

		// 配置ActiveRecord插件(默认主数据)
		ActiveRecordPlugin arp = new ActiveRecordPlugin(xx.DS_MAIN, main);
		arp.setShowSql(true);
		me.add(arp);

		// 记录数据库名称
		EovaConst.DBMAP.put(xx.DS_MAIN, MysqlUtil.getDbNameByUrl(getProperty("url")));

		System.out.println("load main datasource:" + getProperty("url") + "/" + getProperty("user") + "/" + getProperty("pwd"));

		// DruidPlugin Eova DataSource
		DruidPlugin eova = new DruidPlugin(getProperty("eova_url"), getProperty("eova_user"), getProperty("eova_pwd"));
		eova.addFilter(new StatFilter());
		eova.addFilter(wall);
		me.add(eova);

		// 配置ActiveRecord插件
		ActiveRecordPlugin eova_arp = new ActiveRecordPlugin(xx.DS_EOVA, eova);
		eova_arp.setShowSql(true);
		eova_arp.addMapping("eova_object", Eova_Object.class);
		eova_arp.addMapping("eova_item", Eova_Item.class);
		eova_arp.addMapping("eova_button", Eova_Button.class);
		eova_arp.addMapping("eova_user", Eova_User.class);
		eova_arp.addMapping("eova_menu", Eova_Menu.class);
		eova_arp.addMapping("eova_menu_object", Eova_Menu_Object.class);
		eova_arp.addMapping("eova_role", Eova_Role.class);
		eova_arp.addMapping("eova_role_btn", Eova_Role_Btn.class);
		eova_arp.addMapping("eova_log", Eova_Log.class);
		me.add(eova_arp);

		// 记录数据库名称
		EovaConst.DBMAP.put(xx.DS_EOVA, MysqlUtil.getDbNameByUrl(getProperty("eova_url")));

		System.out.println("load eova datasource:" + getProperty("eova_url") + "/" + getProperty("eova_user") + "/" + getProperty("eova_pwd"));

		// 初始化ServiceManager
		ServiceManager.init();

		// 配置EhCachePlugin插件
		me.add(new EhCachePlugin());
	}

	/**
	 * 配置全局拦截器
	 */
	public void configInterceptor(Interceptors me) {
		System.err.println("Config Interceptors Starting...");
		// JFinal.me().getServletContext().setAttribute("KING", "我笑了");
		// 登录验证
		me.add(new LoginInterceptor());
		// 权限验证拦截
		me.add(new AuthInterceptor());
	}

	/**
	 * 配置处理器
	 */
	public void configHandler(Handlers me) {
		System.err.println("Config Handlers Starting...");
		// 添加DruidHandler
		DruidStatViewHandler dvh = new DruidStatViewHandler("/druid");
		me.add(dvh);
	}

	private void costTime(long time) {
		System.err.println("Load Cost Time:" + (System.currentTimeMillis() - time) + "ms\n");
	}
}