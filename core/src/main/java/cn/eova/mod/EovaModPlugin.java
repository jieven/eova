/**
 * Copyright (c) 2015-2026 EOVA.CN. All rights reserved.
 * <p>
 *
 * Licensed under the LGPL-3.0 license
 * Software copyright registration number:2018SR1012969
 * For authorization, please contact: admin@eova.cn
 */
package cn.eova.mod;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.eova.config.EovaConfig;
import cn.eova.model.Mod;
import com.jfinal.aop.Interceptor;
import com.jfinal.core.Controller;
import com.jfinal.kit.LogKit;
import com.jfinal.plugin.IPlugin;
import com.jfinal.plugin.activerecord.DbKit;
import com.jfinal.plugin.activerecord.Table;
import com.jfinal.plugin.activerecord.TableBuilder;

/**
 * Eova Mod 插件
 * 1.获取本地Mod列表
 * 2.加载Mod Jar
 * 3.加载Mod Model(必须需等待ActiveRecordPlugin start之后)
 *
 * @author Jieven
 */
public class EovaModPlugin implements IPlugin {
    // 当前启动Mod配置集
    private static List<EovaModConfig> modConfigs = new ArrayList<>();
    // 当前启动Mod路由集
    private static List<String> modRoutes = new ArrayList<>();

    @Override
    public boolean start() {
        List<Mod> mods = Mod.dao.findByEnabled();
        for (Mod mod : mods) {
            LogKit.info(String.format("Start eova mod [%s]", mod));
            String groupCode = mod.getStr("group_code");
            String code = mod.getStr("code");
            try {
                String cs = String.format("com.eova.mod.%s.%s.ModConfig", groupCode, code);
                Class clazz = EovaConfig.modLoader.loadClass(cs);
                EovaModConfig mc = (EovaModConfig) clazz.newInstance();
                if (mc != null) {
                    // 暂存配置
                    modConfigs.add(mc);

                    // 注册Model
                    HashMap<String, List<Table>> mapping = new HashMap<>();
                    mc.configModel(mapping);
                    // Add JFinal 4.9
                    mapping.forEach((k, v) -> {
                        new TableBuilder().build(v, DbKit.getConfig(k));
                    });
                    // TableMapping.me().putTable(new Table("eova_flow", modelClass));
                    // CPI.addModelToConfigMapping(modelClass, config);
                    // EovaDataSource.mod(); 需要JFinal 4.9 支持
                }
            } catch (ClassNotFoundException e) {
                System.err.println(String.format("Class not found exception:%s", e.getMessage()));
                System.err.println(String.format("Please check if exists:src/main/webapp/WEB-INF/mod/%s-%s-%s.jar", groupCode, code, mod.getVersion()));
            } catch (Exception e) {
                LogKit.error(String.format("init eova mod error:%s", e.getMessage()));
            }
            /*
             * 必须关闭，在Windows下卸载插件的时候删除jar包
             * 否则一旦被ClassLoader load之后，无法被删除
             * 提前关了, 后续还有路由, 拦截器等需要用到, NoClassDefFoundError
             * 由卸载的地方进行关闭 xx.close(loader);
             */
        }

        return true;
    }

    @Override
    public boolean stop() {
        return true;
    }

    public static List<EovaModConfig> getModConfigs() {
        return modConfigs;
    }

    public static List<String> getModRoutes() {
        return modRoutes;
    }

    public static void addRoute(String route) {
//        EovaModPlugin.modRoutes.add(route);
    }

    /**
     * 注册路由规则:/组织名/路由名
     *
     * @param module
     * @return
     */
    public static EovaModRoutes moduleRoutes(EovaModConfig module) {
        // 子应用路由
        EovaModRoutes routes = new EovaModRoutes();
        // 个人或组织名
        String groupKey = module.GROUP();
        // mod在安装时会解压到/_mod 方便读取静态资源文件(开发时和运行时保持一致)
        routes.setBaseViewPath("/_mod/" + groupKey);

        EovaModRoute appRoute = new EovaModRoute();

        module.configRoute(appRoute);
        // 加载路由(路由Key强制加上用户名)
        for (String key : appRoute.getRouteMap().keySet()) {
            Class<? extends Controller> cs = appRoute.getRouteMap().get(key);
            String ctrlKey = String.format("/%s%s", groupKey, key);
            routes.add(ctrlKey, cs, key);
            // 登记Mod Ctrl key
            addRoute(ctrlKey);
        }
        // 加载路由拦截器
        for (Interceptor i : appRoute.getInterceptors()) {
            routes.addInterceptor(i);
        }
        return routes;
    }

}