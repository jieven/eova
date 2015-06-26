/**
 * Copyright (c) 2013-2015, Jieven. All rights reserved.
 * <p/>
 * Licensed under the GPL license: http://www.gnu.org/licenses/gpl.txt
 * To use it on other terms please contact us at 1623736450@qq.com
 */
package com.eova.config;

import com.alibaba.druid.filter.stat.StatFilter;
import com.alibaba.druid.util.JdbcUtils;
import com.alibaba.druid.wall.WallFilter;
import com.eova.common.utils.db.AttrsCaseInsensitiveContainerFactory;
import com.eova.common.utils.xx;
import com.eova.core.IndexController;
import com.eova.core.auth.AuthController;
import com.eova.core.menu.MenuController;
import com.eova.core.object.MetaDataController;
import com.eova.interceptor.AuthInterceptor;
import com.eova.interceptor.LoginInterceptor;
import com.eova.model.*;
import com.eova.service.ServiceManager;
import com.eova.template.crud.CrudConfig;
import com.eova.template.crud.CrudController;
import com.eova.widget.WidgetController;
import com.eova.widget.grid.GridController;
import com.jfinal.config.*;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.activerecord.CaseInsensitiveContainerFactory;
import com.jfinal.plugin.activerecord.dialect.*;
import com.jfinal.plugin.druid.DruidPlugin;
import com.jfinal.plugin.druid.DruidStatViewHandler;
import com.jfinal.plugin.ehcache.EhCachePlugin;
import org.beetl.core.GroupTemplate;
import org.beetl.ext.jfinal.BeetlRenderFactory;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class EovaConfig extends JFinalConfig {

    private long startTime = 0;

    /**
     * 系统启动之后
     */
    @Override
    public void afterJFinalStart() {
        System.err.println("JFinal Started\n");
        // Load Cost Time
        costTime(startTime);
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

        // 注册函数
        // group.registerFunction("isTrue", new IsTrueFun());
        // group.registerFunction("format", new JsFormatFun());

        // 设置全局变量
        Map<String, Object> sharedVars = new HashMap<String, Object>();
        String CDN = getProperty("domain_cdn", "http://127.0.0.1");
        sharedVars.put("CDN", CDN);

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
//		me.add("/user", UserController.class);

    }

    private DruidPlugin createDruidPlugin(String url, String username, String password) {
        // 设置方言
        WallFilter wall = new WallFilter();
        String driverType = null;
        try {
            driverType = JdbcUtils.getDbType(url, JdbcUtils.getDriverClassName(url));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        wall.setDbType(driverType);
        // DruidPlugin Default DataSource
        DruidPlugin dp = new DruidPlugin(url, username, password);
        dp.addFilter(new StatFilter());
        dp.addFilter(wall);
        return dp;

    }

    private ActiveRecordPlugin createActiveRecordPlugin(String url, String dsName, DruidPlugin dp) {
        // 配置ActiveRecord插件(默认主数据)
        ActiveRecordPlugin arp = new ActiveRecordPlugin(dsName, dp);
        String driverType = null;
        try {
            driverType = JdbcUtils.getDbType(url, JdbcUtils.getDriverClassName(url));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        Dialect dialect = new AnsiSqlDialect();
        if (JdbcUtils.MYSQL.equalsIgnoreCase(driverType)) {
            dialect = new MysqlDialect();
        } else if (JdbcUtils.H2.equalsIgnoreCase(driverType)) {
            dialect = new MysqlDialect();
            //字段名称大小写不敏感
            arp.setContainerFactory(new AttrsCaseInsensitiveContainerFactory(true));
        } else if (JdbcUtils.ORACLE.equalsIgnoreCase(driverType)) {
            dialect = new OracleDialect();
        } else if (JdbcUtils.POSTGRESQL.equalsIgnoreCase(driverType)) {
            dialect = new PostgreSqlDialect();
            arp.setContainerFactory(new CaseInsensitiveContainerFactory(true));
        }
        arp.setDialect(dialect);
        arp.setShowSql(true);
        return arp;
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
        DruidPlugin dp = createDruidPlugin(url, user, pwd);
        ActiveRecordPlugin arp = createActiveRecordPlugin(url, xx.DS_MAIN, dp);
        plugins.add(dp).add(arp);

        System.out.println("load data source:" + url + "/" + user + "/" + pwd);


        // DruidPlugin Eova DataSource
        DruidPlugin eovaDP = createDruidPlugin(eova_url, eova_user, eova_pwd);
        ActiveRecordPlugin eovaARP = createActiveRecordPlugin(eova_url, xx.DS_EOVA, eovaDP);
        // 配置ActiveRecord插件
        eovaARP.addMapping("eova_object", MetaObject.class);
        eovaARP.addMapping("eova_item", MetaItem.class);
        eovaARP.addMapping("eova_button", Button.class);
        eovaARP.addMapping("eova_user", User.class);
        eovaARP.addMapping("eova_menu", Menu.class);
        eovaARP.addMapping("eova_menu_object", MenuObject.class);
        eovaARP.addMapping("eova_role", Role.class);
        eovaARP.addMapping("eova_role_btn", RoleBtn.class);
        eovaARP.addMapping("eova_log", EovaLog.class);
        plugins.add(eovaDP).add(eovaARP);
        System.out.println("load eova datasource:" + eova_url + "/" + eova_user + "/" + eova_pwd);

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