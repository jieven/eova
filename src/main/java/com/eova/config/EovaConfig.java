/**
 * Copyright (c) 2013-2015, Jieven. All rights reserved.
 * <p/>
 * Licensed under the GPL license: http://www.gnu.org/licenses/gpl.txt
 * To use it on other terms please contact us at 1623736450@qq.com
 */
package com.eova.config;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.beetl.core.GroupTemplate;
import org.beetl.ext.jfinal.BeetlRenderFactory;

import com.alibaba.druid.filter.stat.StatFilter;
import com.alibaba.druid.util.JdbcUtils;
import com.alibaba.druid.wall.WallFilter;
import com.eova.common.utils.xx;
import com.eova.core.IndexController;
import com.eova.core.auth.AuthController;
import com.eova.core.menu.MenuController;
import com.eova.core.object.MetaDataController;
import com.eova.interceptor.AuthInterceptor;
import com.eova.interceptor.LoginInterceptor;
import com.eova.model.Button;
import com.eova.model.EovaLog;
import com.eova.model.Menu;
import com.eova.model.MenuObject;
import com.eova.model.MetaField;
import com.eova.model.MetaObject;
import com.eova.model.Role;
import com.eova.model.RoleBtn;
import com.eova.model.User;
import com.eova.service.ServiceManager;
import com.eova.template.crud.CrudConfig;
import com.eova.template.crud.CrudController;
import com.eova.widget.WidgetController;
import com.eova.widget.grid.GridController;
import com.jfinal.config.Constants;
import com.jfinal.config.Handlers;
import com.jfinal.config.Interceptors;
import com.jfinal.config.JFinalConfig;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.activerecord.CaseInsensitiveContainerFactory;
import com.jfinal.plugin.activerecord.IDataSourceProvider;
import com.jfinal.plugin.activerecord.dialect.AnsiSqlDialect;
import com.jfinal.plugin.activerecord.dialect.Dialect;
import com.jfinal.plugin.activerecord.dialect.MysqlDialect;
import com.jfinal.plugin.activerecord.dialect.OracleDialect;
import com.jfinal.plugin.activerecord.dialect.PostgreSqlDialect;
import com.jfinal.plugin.druid.DruidPlugin;
import com.jfinal.plugin.druid.DruidStatViewHandler;
import com.jfinal.plugin.ehcache.EhCachePlugin;

public class EovaConfig extends JFinalConfig {

	private long startTime = 0;

	/** EOVA所在数据库的类型 **/
	public static String EOVA_DBTYPE = "mysql";

	/**
	 * 系统启动之后
	 */
	@Override
	public void afterJFinalStart() {
		System.err.println("JFinal Started\n");
		// Load Cost Time
		costTime(startTime);

		EovaInit.initPlugins();

		// System.out.println(DsUtil.getDbNameByConfigName(xx.DS_MAIN));
		// System.out.println(DsUtil.getPkName(xx.DS_EOVA, "eova_field"));
		// System.out.println(DsUtil.getTableNamesByConfigName(xx.DS_EOVA, DsUtil.TABLE));
		// System.out.println(DsUtil.getColumnInfoByConfigName(xx.DS_EOVA, "EOVA_USER"));
		// DbUtil.createOracleSql();
	}

	/**
	 * 系统停止之前
	 */
	@Override
	public void beforeJFinalStop() {
	}

	/**
	 * 配置常量
	 */
	@Override
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

		@SuppressWarnings("unused")
		GroupTemplate group = BeetlRenderFactory.groupTemplate;

		// 设置全局变量
		Map<String, Object> sharedVars = new HashMap<String, Object>();
		String CDN = getProperty("domain_cdn");
		if (!xx.isEmpty(CDN)) {
			sharedVars.put("CDN", CDN);
		}

		// Load Template Const
		PageConst.init(sharedVars);

		BeetlRenderFactory.groupTemplate.setSharedVars(sharedVars);
	}

	/**
	 * 配置路由
	 */
	@Override
	public void configRoute(Routes me) {
		System.err.println("Config Routes Starting...");
		me.add("/", IndexController.class);
		me.add(CrudConfig.contro, CrudController.class);
		me.add("/widget", WidgetController.class);
		me.add("/grid", GridController.class);
		me.add("/metadata", MetaDataController.class);
		me.add("/menu", MenuController.class);
		me.add("/auth", AuthController.class);

		/* 自定义业务 */
		// me.add("/user", UserController.class);

	}

	/**
	 * 配置插件
	 */
	@Override
	public void configPlugin(Plugins plugins) {
		System.err.println("Config Plugins Starting...");

		String url, user, pwd;
		String eova_url, eova_user, eova_pwd;
		if (isLocal()) {
			// 本地环境
			url = getProperty("local_url");
			user = getProperty("local_user");
			pwd = getProperty("local_pwd");

			eova_url = getProperty("local_eova_url");
			eova_user = getProperty("local_eova_user");
			eova_pwd = getProperty("local_eova_pwd");
		} else {
			// 正式环境
			url = getProperty("url");
			user = getProperty("user");
			pwd = getProperty("pwd");

			eova_url = getProperty("eova_url");
			eova_user = getProperty("eova_user");
			eova_pwd = getProperty("eova_pwd");
		}

		// 默认数据源
		{
			DruidPlugin dp = initDruidPlugin(url, user, pwd);
			// C3p0Plugin dp = initC3p0Plugin(url, user, pwd);
			ActiveRecordPlugin arp = initActiveRecordPlugin(url, xx.DS_MAIN, dp);
			plugins.add(dp).add(arp);

			System.out.println("load data source:" + url + "/" + user + "/" + pwd);
		}
		// Eova数据源
		{
			DruidPlugin dp = initDruidPlugin(eova_url, eova_user, eova_pwd);
			// C3p0Plugin dp = initC3p0Plugin(eova_url, eova_user, eova_pwd);
			ActiveRecordPlugin arp = initActiveRecordPlugin(eova_url, xx.DS_EOVA, dp);
			// 配置ActiveRecord插件
			arp.addMapping("eova_user", User.class);
			arp.addMapping("eova_object", MetaObject.class);
			arp.addMapping("eova_field", MetaField.class);
			arp.addMapping("eova_button", Button.class);
			arp.addMapping("eova_menu", Menu.class);
			arp.addMapping("eova_menu_object", MenuObject.class);
			arp.addMapping("eova_role", Role.class);
			arp.addMapping("eova_role_btn", RoleBtn.class);
			arp.addMapping("eova_log", EovaLog.class);
			plugins.add(dp).add(arp);
			System.out.println("load eova datasource:" + eova_url + "/" + eova_user + "/" + eova_pwd);

			try {
				// Eova的数据库类型
				EOVA_DBTYPE = JdbcUtils.getDbType(eova_url, JdbcUtils.getDriverClassName(eova_url));
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		// 初始化ServiceManager
		ServiceManager.init();

		// 配置EhCachePlugin插件
		plugins.add(new EhCachePlugin());
	}

	/**
	 * 配置全局拦截器
	 */
	@Override
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
	@Override
	public void configHandler(Handlers me) {
		System.err.println("Config Handlers Starting...");
		// 添加DruidHandler
		DruidStatViewHandler dvh = new DruidStatViewHandler("/druid");
		me.add(dvh);
	}

	/**
	 * init Druid
	 * 
	 * @param url JDBC
	 * @param username 数据库用户
	 * @param password 数据库密码
	 * @return
	 */
	private DruidPlugin initDruidPlugin(String url, String username, String password) {
		// 设置方言
		WallFilter wall = new WallFilter();
		String dbType = null;
		try {
			dbType = JdbcUtils.getDbType(url, JdbcUtils.getDriverClassName(url));
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		wall.setDbType(dbType);

		DruidPlugin dp = new DruidPlugin(url, username, password);
		dp.addFilter(new StatFilter());
		dp.addFilter(wall);
		return dp;

	}

	/**
	 * init ActiveRecord
	 * 
	 * @param url JDBC
	 * @param ds DataSource
	 * @param dp Druid
	 * @return
	 */
	private ActiveRecordPlugin initActiveRecordPlugin(String url, String ds, IDataSourceProvider dp) {
		ActiveRecordPlugin arp = new ActiveRecordPlugin(ds, dp);

		String dbType;
		try {
			dbType = JdbcUtils.getDbType(url, JdbcUtils.getDriverClassName(url));
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		Dialect dialect;
		if (JdbcUtils.MYSQL.equalsIgnoreCase(dbType) || JdbcUtils.H2.equalsIgnoreCase(dbType)) {
			dialect = new MysqlDialect();
		} else if (JdbcUtils.ORACLE.equalsIgnoreCase(dbType)) {
			dialect = new OracleDialect();
			// 默认小写
			arp.setContainerFactory(new CaseInsensitiveContainerFactory(true));
			((DruidPlugin) dp).setValidationQuery("select 1 FROM DUAL");
		} else if (JdbcUtils.POSTGRESQL.equalsIgnoreCase(dbType)) {
			dialect = new PostgreSqlDialect();
			// 默认小写
			arp.setContainerFactory(new CaseInsensitiveContainerFactory(true));
		} else {
			// 默认使用标准SQL方言
			dialect = new AnsiSqlDialect();
		}
		arp.setDialect(dialect);

		// 是否显示SQL
		Boolean isShow = getPropertyToBoolean("showSql");
		if (isShow != null) {
			arp.setShowSql(isShow);
		}

		return arp;
	}

	/**
	 * 是否本地环境(假设本地为Windows环境)
	 * 
	 * @return
	 */
	private boolean isLocal() {
		String osName = System.getProperty("os.name");
		return osName.indexOf("Windows") != -1;
	}

	private void costTime(long time) {
		System.err.println("Load Cost Time:" + (System.currentTimeMillis() - time) + "ms\n");
	}
}