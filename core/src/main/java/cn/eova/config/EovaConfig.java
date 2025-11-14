/**
 * Copyright (c) 2015-2026 EOVA.CN. All rights reserved.
 * Licensed under the LGPL-3.0 license
 * For authorization, please contact: admin@eova.cn
 */
package cn.eova.config;

import javax.servlet.http.HttpServletRequest;
import java.net.URLClassLoader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import cn.eova.tools.x;
import com.alibaba.druid.DbType;
import cn.eova.EovaApiRoutes;
import cn.eova.EovaMetaHooks;
import cn.eova.EovaWebRoutes;
import cn.eova.aop.MetaObjectIntercept;
import cn.eova.aop.UploadIntercept;
import cn.eova.aop.UserSessionIntercept;
import cn.eova.aop.eova.EovaIntercept;
import cn.eova.auth.AuthInterceptor;
import cn.eova.common.Ds;
import cn.eova.common.base.BaseSharedMethod;
import cn.eova.common.utils.web.RequestUtil;
import cn.eova.core.AppController;
import cn.eova.core.IndexController;
import cn.eova.core.api.ApiRouterHandler;
import cn.eova.core.type.Convertor;
import cn.eova.ext.jfinal.DbCaptchaCache;
import cn.eova.ext.jfinal.EovaRenderSourceFactory;
import cn.eova.ext.jfinal.directive.JsonDirective;
import cn.eova.handler.UrlBanHandler;
import cn.eova.interceptor.ExceptionInterceptor;
import cn.eova.interceptor.LoginInterceptor;
import cn.eova.mod.EovaModConfig;
import cn.eova.mod.EovaModPlugin;
import cn.eova.mod.EovaModUtil;
import cn.eova.model.Button;
import cn.eova.model.EovaOption;
import cn.eova.model.EovaProps;
import cn.eova.model.EovaTemplate;
import cn.eova.model.Menu;
import cn.eova.model.MetaField;
import cn.eova.model.MetaFieldDiy;
import cn.eova.model.MetaObject;
import cn.eova.model.Mod;
import cn.eova.model.Role;
import cn.eova.model.RoleBtn;
import cn.eova.model.Session;
import cn.eova.model.Task;
import cn.eova.model.User;
import cn.eova.model.Widget;
import cn.eova.plugin.config.EovaConfigPlugin;
import cn.eova.plugin.cron4j.EovaCronPlugin;
import cn.eova.service.LoginService;
import cn.eova.service.biz;
import cn.eova.service.sm;
import cn.eova.sql.dql.dialect.QueryDialect;
import com.jfinal.config.Constants;
import com.jfinal.config.Handlers;
import com.jfinal.config.Interceptors;
import com.jfinal.config.JFinalConfig;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.core.ActionReporter;
import com.jfinal.json.MixedJsonFactory;
import com.jfinal.kit.LogKit;
import com.jfinal.kit.Prop;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.druid.DruidStatViewHandler;
import com.jfinal.plugin.druid.IDruidStatViewAuth;
import com.jfinal.plugin.ehcache.EhCachePlugin;
import com.jfinal.render.ViewType;
import com.jfinal.template.Engine;

public class EovaConfig extends JFinalConfig {

    public static boolean isDevMode = true;

    // 应用信息
    public static String APP_ID = "";
    public static String APP_SECRET = "";
    public static String EOVA_INDEX = "/";
    public static String EOVA_INDEX_H5 = "/h5";

    /**Eova Mod加载器**/
    public static URLClassLoader modLoader = null;

    /** EOVA所在数据库的类型 **/
    public static DbType EOVA_DBTYPE = DbType.mysql;// EOVA_DBTYPE.toString();
    /** 数据库命名规则-是否自动小写 **/
    public static boolean isLowerCase = true;
    /** DB类型转换器 **/
    private static HashMap<String, Convertor> convertorMap = new HashMap<>();
    /** DQL方言 **/
    private static HashMap<String, QueryDialect> queryDialectMap = new HashMap<>();
    /** 数据类型转换器, 默认转换器指定为EOVA所在源 **/
    // private static Convertor convertor = getConvertor(Ds.EOVA);

    /** Eova配置属性 有x.conf 代替 **/
    // protected static Map<String, String> props = new HashMap<String, String>();
    /** Eova表达式集合 **/
    protected static Map<String, String> exps = new HashMap<String, String>();

    /** URI授权集合<角色ID,URI> **/
    protected static Map<Integer, Set<String>> authUris = new HashMap<Integer, Set<String>>();

    /** ActiveRecord Map **/
    static HashMap<String, ActiveRecordPlugin> arps = new HashMap<>();

    /**全局查询拦截器**/
    private static EovaIntercept eovaIntercept = null;
    /**默认元对象业务拦截器**/
    private static MetaObjectIntercept defaultMetaObjectIntercept = null;
    /**用户会话处理拦截器**/
    private static UserSessionIntercept userSessionIntercept = null;
    /**上传拦截器**/
    private static UploadIntercept uploadIntercept = null;

    /**Beetl模版对象**/
//    private static GroupTemplate beetl = null;
    /**Beetl模版工程**/
//    public static BeetlEovaRenderFactory rf;
    /**Beetl EovaTag 自定义实现**/
    protected static HashMap<String, String> eovaTags = new HashMap<>();

    /**
     * 系统启动之后
     */
    @Override
    public void onStart() {
        System.out.println(String.format("Starting EovaMeta %s -> The Super Easy LowCode Platform", EovaConst.getEovaVer()));

        // 初始化配置Eova业务
        configEova();
        // 初始化ServiceManager
        biz.init();
        // Eova表达式
        exp();
        // 平台字段授权
        authField();
        // 企业字段授权
        EovaFieldAuth.authReload(0);

        // EovaAPI初始化
        EovaInit.initEovaApiAppCofing();
        // configApiRoute(routes);
        // EovaInit.initApiActionMapping();

        // 初始化Eova Hook
        new EovaMetaHooks().config();


        // 回调Eova Mod Start
//		try {
//			for (EovaModConfig mc : EovaModPlugin.getModConfigs()) {
//				if (mc != null) {
//					mc.afterEovaStart();
//				}
//			}
//		} catch (Exception e) {
//			LogKit.error(String.format("eova mod start error:%s", e.getMessage()));
//		}

        EovaConst.START_TIME = x.time.formatNowTimes();
    }

    /**
     * 系统停止之前
     */
    @Override
    public void onStop() {
//		try {
//			for (EovaModConfig mc : EovaModPlugin.getModConfigs()) {
//				if (mc != null) {
//					mc.beforeEovaStop();
//				}
//			}
//		} catch (Exception e) {
//			LogKit.error(String.format("eova mod stop error:%s", e.getMessage()));
//		}
    }

    /**
     * 配置常量
     */
    @Override
    public void configConstant(Constants me) {
        x.log.info("Config Constants Starting");

        me.setEncoding("UTF-8");
        me.setToJavaAwtHeadless();


        // 多环境配置加载(优先级 开发<测试<预生产<灰度<生产)
        Prop prop = PropKit.useFirstFound("eova/dev.txt", "eova/test.txt", "eova/pre.txt", "eova/pro.txt", "eova/prd.txt");
        // EovaTools配置加载
        x.conf.addProp(prop.getProperties());

        // 初始化Mod
        modLoader = EovaModUtil.initLoader();

        // 开发模式
        isDevMode = x.conf.getBool("devMode", true);
        me.setDevMode(isDevMode);
        if (isDevMode && "PRD".equals(x.conf.get("env"))) {
            LogKit.warn("当前环境为生产环境, 并且开启了开发者模式, 如无必要请立即关闭, 避免对线上造成不可逆的后果!");
            LogKit.info("当前环境为生产环境, 并且开启了开发者模式, 如无必要请立即关闭, 避免对线上造成不可逆的后果!");
        }

        // POST内容最大500M(安装包上传)
        me.setMaxPostSize(1024 * 1024 * 500);

        // 配置视图类型，默认使用 jfinal enjoy 模板引擎
        me.setViewType(ViewType.JFINAL_TEMPLATE);

        // 开启解析 json 请求，5.0.0 版本新增功能
        me.setResolveJsonRequest(true);

        // 日志格式化
        ActionReporter.setTitle(String.format("\nEovaMeta-%s action report - ", EovaConst.getEovaVer()));

        // me.setError401View("/eova/401.html");// 同步->登录页, 异步->弹窗
        me.setError403View("/eova/error/403.html");// 无权限, 禁止访问
        me.setError404View("/eova/error/404.html");// 找不到资源
        me.setError500View("/eova/error/500.html");// 服务器异常
        me.setErrorView(400, "/eova/error/404.html");// 错误请求
        me.setErrorView(503, "/eova/error/503.html");// 服务不可用

        me.setBaseUploadPath(x.conf.get("file.dir.base"));
        me.setBaseDownloadPath(x.conf.get("file.dir.base"));
        me.setJsonFactory(MixedJsonFactory.me());
        // me.setJsonDatePattern("yyyy-MM-dd");// PS:LocalDateTime 丢失时分秒，所以使用默认策略

        // 关闭autoType 2.x 不再提供此API
        // ParserConfig.getGlobalInstance().setSafeMode(true);
        // 注册分布式验证码
        me.setCaptchaCache(new DbCaptchaCache());
        // 插件顺序调整到configConstant()之后
        me.setConfigPluginOrder(1);

        // Beetl视图(暂时保留兼容老业务, 未来全部屏蔽掉)
        //        boolean webappMode = x.conf.getBool("webappMode", false);
        //        rf = new BeetlEovaRenderFactory();
        //        rf.config(webappMode);
        //        beetl = rf.groupTemplate;
        //        me.setRenderFactory(rf);// 不设置默认

    }

    /**
     * 配置路由
     */
    @Override
    public void configRoute(Routes me) {
        System.err.println("Config Routes Starting...");

        // 首页入口
        EOVA_INDEX = x.conf.get("eova.index", "/");
        EOVA_INDEX_H5 = x.conf.get("eova.index.h5", "/h5");

        // 应用入口
        me.add("/app", AppController.class);

        // 首页Ctrl会被继承 需要支持注册父类Action
        me.setMappingSuperClass(true);

        // Eova路由
        me.add(new EovaWebRoutes());
        me.add(new EovaApiRoutes());

        // URI授权
        authUri();

        // 登录验证(为自定义添加拦截器)
        me.addInterceptor(new LoginInterceptor());
        // 权限验证拦截
        me.addInterceptor(new AuthInterceptor());

        // 自定义路由
        route(me);

        // 是否需要默认注册系统路由
        List<Routes.Route> routeList = me.getRouteItemList();
        if (routeList.stream().noneMatch(x -> x.getControllerPath().equals(EOVA_INDEX))) {
            // 添加到第一个路由EovaWebRoutes
            me.add(EOVA_INDEX, IndexController.class);
        }

        // load eova module route
        try {
            for (EovaModConfig mc : EovaModPlugin.getModConfigs()) {
                if (mc != null) {
                    me.add(EovaModPlugin.moduleRoutes(mc));
                }
            }
        } catch (Exception e) {
            LogKit.error(String.format("load eova module routes error:%s", e.getMessage()));
        }
    }

    @Override
    public void configEngine(Engine me) {

        // 无根路径
//		me.setBaseTemplatePath(null);
        // 从 class path 和 jar 包加载模板配置
//		me.setToClassPathSourceFactory();
        me.setSourceFactory(new EovaRenderSourceFactory());

        // 模版常用共享方法
        me.addSharedMethod(new BaseSharedMethod());

        // me.addSharedFunction("/WEB-INF/_layout/pager.html");
        me.addDirective("json", JsonDirective.class);
        // 共享常量
//        EovaConst.getPageConst().forEach((k, v) -> {
//            me.addSharedObject(k, v);
////            beetl.getSharedVars().put(k, v);
//        });

        // 共享配置(#(conf_xxx_xxx))
//        Map<String, String> props = x.conf.getProps();
//        for (String cf : props.keySet()) {
//            String val = props.get(cf);
//            String key = "conf_" + cf.replaceAll("\\.", "_");
//            me.addSharedObject(key, val);
////            beetl.getSharedVars().put(key, val);
//        }

        // shareds.put("INDEX", EOVA_INDEX);
        // sharedVars.put("I18N", I18NBuilder.I18N);

        // Load Template Const
        // PageConst.init(sharedVars); 无用 待废弃

        // TODO 三方逐步废弃Beetl, 添加Enjoy

        // 启用新的, 基于 weui 系列模版
//		me.addSharedFunction("/_view/_layout/list.html");

    }

    /**
     * 配置插件
     */
    @Override
    public void configPlugin(Plugins plugins) {
        System.err.println("Config Plugins Starting...");

        // 初始化数据源
        EovaDataSource.create(plugins);

        // 初始化EOVA DB配置
        plugins.add(new EovaConfigPlugin());

        /*
         * 特别说明, configPlugin{} 暂时无法使用DB和参数
         * 如果需要使用, 必须封装为Plugin, 并且排在下面
         */

        // 延迟添加Model映射, 可能会导致初始化数据源卡住
        // eova model mapping
        mappingEova(arps.get(Ds.EOVA));

        // diy model mapping
        mapping(arps);

        // 构建类型转换方言
        // EovaDataSource.buildConvertor();

        // 配置EhCachePlugin插件
        plugins.add(new EhCachePlugin());

        // 配置定时调度  默认不启动
        boolean isJob = x.conf.getBool("job.enable", false);
        if (isJob) {
            plugins.add(new EovaCronPlugin());
//            plugins.add(new QuartzPlugin());
        } else {
            // 提醒配置开关, 不需要此提示可以配置为false
            if (x.isEmpty(x.conf.get("job.enable"))) {
                x.log.info("定时任务暂未配置, 如需开启请配置 job.enable=true");
            }
        }

        // Eova Mod 初始化并注册Model
//		plugins.add(new EovaModPlugin());

        // 自定义插件
        plugin(plugins);
    }

    /**
     * 配置全局拦截器
     */
    @Override
    public void configInterceptor(Interceptors me) {
        System.err.println("Config Interceptors Starting...");
        // 全局异常拦截
        me.addGlobalActionInterceptor(new ExceptionInterceptor());
        // JFinal.me().getServletContext().setAttribute("EOVA", "简单才是高科技");
        // 登录验证
//		me.addGlobalActionInterceptor(new LoginInterceptor());
        // 权限验证拦截
//		me.addGlobalActionInterceptor(new AuthInterceptor());
        // move to WebRoutes
    }

    /**配置Eova业务**/
    public void configEova() {
    }

    /**
     * 配置处理器
     */
    @Override
    public void configHandler(Handlers me) {
        System.err.println("Config Handlers Starting...");
        // 添加DruidHandler
        DruidStatViewHandler dvh = new DruidStatViewHandler("/druid", new IDruidStatViewAuth() {
            @Override
            public boolean isPermitted(HttpServletRequest request) {
                String sid = RequestUtil.getCookieStr(request, LoginService.CKSID, null);
                if (sid == null) {
                    return false;
                }
                User user = sm.login.getLoginUser(sid);
                if (user == null) {
                    return false;
                }
                return user.isAdmin();
            }
        });
        me.add(dvh);
        // 过滤禁止访问资源
        me.add(new UrlBanHandler(".*\\.(html|tag|sql)", false));
        // API路由(默认开启, 不需要可以屏蔽)
        boolean isRouter = x.conf.getBool("eova.api.router", true);
        if (isRouter) {
            me.add(new ApiRouterHandler());
        }
    }

    /**
     * Eova Data Source Model Mapping
     *
     * @param arp
     */
    private void mappingEova(ActiveRecordPlugin arp) {
        arp.addMapping("eova_session", Session.class);
        arp.addMapping("eova_object", MetaObject.class);
        arp.addMapping("eova_field", MetaField.class);
        arp.addMapping("eova_field_diy", MetaFieldDiy.class);
        arp.addMapping("eova_button", Button.class);
        arp.addMapping("eova_menu", Menu.class);
        arp.addMapping("eova_user", User.class);
        arp.addMapping("eova_role", Role.class);
        arp.addMapping("eova_role_btn", RoleBtn.class);
        arp.addMapping("eova_task", Task.class);
        arp.addMapping("eova_widget", Widget.class);
        arp.addMapping("eova_mod", Mod.class);
        arp.addMapping("eova_option", EovaOption.class);
        arp.addMapping("eova_template", EovaTemplate.class);
        arp.addMapping("eova_props", EovaProps.class);
    }

    /**
     * Diy Data Source Model Mapping
     * @param arps 数据源key->ActiveRecordPlugin
     */
    protected void mapping(HashMap<String, ActiveRecordPlugin> arps) {
    }

    /**
     * Custom Route
     *
     * @param me
     */
    protected void route(Routes me) {
    }

    /**
     * Custom Plugin
     *
     * @param plugins
     * @return
     */
    protected void plugin(Plugins plugins) {
    }

    /**
     * Eova Expression Mapping
     */
    protected void exp() {
        // Eova 系统功能需要的Exp
        exps.put("selectEovaFieldByObjectCode", "select en Field,cn Name from eova_field where object_code = ?;ds=eova");
        // 列表可显示字段
        exps.put("comboFieldByObject", "select en ID,cn CN from eova_field where object_code = ? and is_show = 1 order by num;ds=eova");
        exps.put("selectEovaUser", "select id,name 姓名, login_id 帐号 from eova_user;ds=eova");
        exps.put("selectEovaRole", "select id,name cn from eova_role;ds=eova");
        // Eova Flow 根据用户或角色过滤
        exps.put("selectEovaUserByUid", "select id,name 姓名, login_id 帐号 from eova_user where id in %s;ds=eova");
        exps.put("selectEovaUserByRid", "select id,name 姓名, login_id 帐号 from eova_user where rid in %s;ds=eova");

        // 隐藏玩法の软硬结合
        // exps.put("selectEovaUser", "select id ID,name 姓名, login_id 帐号 from eova_user where id in %s and id < ?;ds=eova");
        // exp=selectEovaUser;@(1,2,3);10000
    }

    /**
     * URI授权配置
     */
    protected void authUri() {


//        authUris.put(0, AuthUri.whiteList);

        // 首页全局免鉴权
//        getAuthUris().get(0).add(EOVA_INDEX);
//        getAuthUris().get(0).add(EOVA_INDEX + '/');
//        getAuthUris().get(0).add(EOVA_INDEX_H5);
//        getAuthUris().get(0).add(EOVA_INDEX_H5 + '/');

    }

    /**
     * 字段授权
     */
    protected void authField() {
        // 系统角色字段授权
        EovaFieldAuth.authRole("eova_role_code", "lv", "1,2");// 解释:eova_role_code对象的lv字段 只有角色1和角色2 可见
    }

    /**
     * 添加URI授权规则<br>
     * 语法:URI->角色1ID,角色2ID
     *
     * @param rule
     */
    protected static void addAuthUri(String rule) {
        String[] ss = rule.split("->");
        String uri = ss[0];
        String s1 = ss[1];

        String[] rids = s1.split(",");
        for (String s : rids) {
            Integer rid = x.toInt(s.trim());
            Set<String> set = authUris.get(rid);
            if (set == null) {
                set = new HashSet<>();
            }
            set.addAll(Arrays.asList(uri.split(",")));
            authUris.put(rid, set);
        }
    }

    // 默认从配置中读取授权密钥
    protected void license() {
        APP_ID = x.conf.get("app_id").trim();
        APP_SECRET = x.conf.get("app_secret").trim();
        // 默认从配置中读取license,为了私密也可以写在代码中,就不用向需求方解释这玩意了.三方无感!
        // 同理可以藏到任意别人找不到的地方.
    }

    public static EovaIntercept getEovaIntercept() {
        return eovaIntercept;
    }

    public static void setEovaIntercept(EovaIntercept eovaIntercept) {
        EovaConfig.eovaIntercept = eovaIntercept;
    }

    public static MetaObjectIntercept getDefaultMetaObjectIntercept() {
        return defaultMetaObjectIntercept;
    }

    public static void setDefaultMetaObjectIntercept(MetaObjectIntercept defaultMetaObjectIntercept) {
        EovaConfig.defaultMetaObjectIntercept = defaultMetaObjectIntercept;
    }

    public static UserSessionIntercept getUserSessionIntercept() {
        return userSessionIntercept;
    }

    public static void setUserSessionIntercept(UserSessionIntercept userSessionIntercept) {
        EovaConfig.userSessionIntercept = userSessionIntercept;
    }

    public static UploadIntercept getUploadIntercept() {
        return uploadIntercept;
    }

    public static void setUploadIntercept(UploadIntercept uploadIntercept) {
        EovaConfig.uploadIntercept = uploadIntercept;
    }

    public static HashMap<String, ActiveRecordPlugin> getArps() {
        return arps;
    }

    public static void setArps(HashMap<String, ActiveRecordPlugin> arps) {
        EovaConfig.arps = arps;
    }

    public static String getExp(String key) {
        return exps.get(key);
    }

    public static Map<Integer, Set<String>> getAuthUris() {
        return authUris;
    }


    public static QueryDialect getQueryDialect(String ds) {
        return queryDialectMap.get(ds);
    }

    public static QueryDialect addQueryDialect(String ds, QueryDialect qd) {
        return queryDialectMap.put(ds, qd);
    }

    public static Convertor getConvertor(String ds) {
        return convertorMap.get(ds);
    }

    public static Convertor addConvertor(String ds, Convertor cv) {
        return convertorMap.put(ds, cv);
    }

    public static HashMap<String, String> getEovaTags() {
        return eovaTags;
    }

}