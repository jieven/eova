/**
 * Copyright (c) 2015-2026 EOVA.CN. All rights reserved.
 * Licensed under the LGPL-3.0 license
 * For authorization, please contact: admin@eova.cn
 */
package cn.eova.meta;

import java.util.HashMap;

import cn.eova.common.Ds;
import cn.eova.config.EovaConfig;
import cn.eova.config.EovaSystem;
import cn.eova.model.EovaLog;
import cn.eova.tools.x;
import com.jfinal.config.Constants;
import com.jfinal.config.Interceptors;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.server.undertow.UndertowServer;

public class AppConfig extends EovaConfig {

    public static void main(String[] args) {
        EovaSystem.server = UndertowServer.create(AppConfig.class)
                // 支持SSE长连接
                .onDeploy((cl, di) -> di.getFilters().get("jfinal").setAsyncSupported(true))
                // 开发环境 HotSwapClassLoader.parent() 强制指定由父级加载
                .addSystemClassPrefix("com.eova.config.EovaSystem")     // 全局静态
                // 开发环境 HotSwapClassLoader 强制指定为本级加载
                .addHotSwapClassPrefix("cn.eova.")                      // 迎合热代码
                .addHotSwapClassPrefix("com.eova.");                     // 迎合热代码
        //.addHotSwapClassPrefix("org.beetl.");                   // 迎合热代码

//        EovaSystem.server.configWeb(builder -> {
//            MimeMapping vueMimeMapping = new MimeMapping("vue", "text/html");
//            builder.getDeploymentInfo().addMimeMapping(vueMimeMapping);
//        });

        EovaSystem.server.start();

    }

    @Override
    public void onStart() {
        super.onStart();

    }

    /**
     * 自定义路由
     *
     * @param me
     */
    @Override
    protected void route(Routes me) {

        // 自定义入口Ctrl(PS 不能移入其他Routes, 否则无法覆盖)
        me.add(EovaConfig.EOVA_INDEX, AppController.class);
//        me.add(EovaConfig.EOVA_INDEX_H5, H5Controller.class);

        me.add(new AppRoutes());
        me.add(new AppApiRoutes());

        long t = x.time.now();

        // Db.find(sql) 看看实际执行时间

        x.time.costTime(t);
    }

//    /**
//     * 自定义API路由
//     * @param me
//     */
//    public void configApiRoute(ApiRoutes me){
//        super.configApiRoute(me);
//
//        me.add(new AppApiRoutes());
//    }

    /**
     * 自定义Main数据源Model映射
     *
     * @param arps
     */
    @Override
    protected void mapping(HashMap<String, ActiveRecordPlugin> arps) {
        // 获取主数据源的ARP
        @SuppressWarnings("unused")
        ActiveRecordPlugin main = arps.get(Ds.MAIN);

        // 自定义业务Model映射往这里加
        //		main.addMapping("user_info", UserInfo.class);
        //		main.addMapping("users", Users.class);
        //		main.addMapping("address", Address.class);
        //		main.addMapping("orders", Orders.class);

        // main.addSqlTemplate("x.sql");

        // EOVA数据源
        ActiveRecordPlugin eova = arps.get(Ds.EOVA);
        eova.addMapping("eova_log", EovaLog.class);//  默认日志
    }

    /**
     * 自定义插件
     */
    @Override
    protected void plugin(Plugins plugins) {

    }

    @Override
    protected void authUri() {
        super.authUri();

        // 全角色 所有URI 白名单
        // addAuthUri("/**/**->0");

        // 全角色 指定URI 白名单
        // addAuthUri("/eova/api/**->0");

        // 管理员(ID=1),测试主管(ID=2) 订单列表  白名单
        // addAuthUri("/order/list/*->1,2");

        // URI配置语法规则咋写的?
        // @see AntPathMatcher

        // 租户管理员不限制字段权限
        // EovaFieldAuth.adminRole.add("2");
    }

    @Override
    public void configEova() {

        // 注册元对象钩子
        new AppMetaHooks().config();
        // 注册数据导入钩子
        new AppImportHooks().config();

        /*
         * 自定义Eova全局拦截器
         * 全局的查询拦截,可快速集中解决系统的查询数据权限,严谨,高效!
         */
        // setEovaIntercept(new GlobalEovaIntercept());
        /*
         * 默认元对象业务拦截器:未配置元对象业务拦截器会默认命中此拦截器
         * 自定义元对象拦截器时自行考虑是否需要继承默认拦截器
         */
        // setDefaultMetaObjectIntercept(new BaseMetaObjectIntercept());
        /*
         * 用户会话拦截器
         */
        // setUserSessionIntercept(new MyUserSessionIntercept());

        /**
         * 上传拦截器(实现自定义上传, 例如上传到OSS, 七牛云)
         */
        // setUploadIntercept(new MyUploadIntercept());

    }

    @Override
    public void configInterceptor(Interceptors me) {
        // 临时授权跨域授权
        // me.addGlobalActionInterceptor(new CrossDomainInterceptor());
        super.configInterceptor(me);
    }

    @Override
    public void configConstant(Constants me) {
        super.configConstant(me);
    }

}