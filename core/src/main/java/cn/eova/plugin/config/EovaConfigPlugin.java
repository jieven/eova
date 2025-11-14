/**
 * Copyright (c) 2015-2026 EOVA.CN. All rights reserved.
 * Licensed under the LGPL-3.0 license
 * For authorization, please contact: admin@eova.cn
 */
package cn.eova.plugin.config;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import cn.eova.tools.x;
import cn.eova.common.Ds;
import cn.eova.config.EovaConst;
import com.jfinal.kit.LogKit;
import com.jfinal.plugin.IPlugin;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

/**
 * 加载EOVA数据库配置
 * @author Jieven
 *
 */
public class EovaConfigPlugin implements IPlugin {

    /**
     * 前端可用配置名
     */
    public static final Set<String> UI_CONF_KEYS = new HashSet<String>();

    @Override
    public boolean start() {
        try {
            List<Record> list = Db.use(Ds.EOVA).find("select * from eova_config where status = 1");
            x.log.info("load eova config：enabled = " + list.size());
            // 优先读取测试值, 否则取默认值
            list.forEach(o -> {
                String value = o.getStr("value");
                // 非线上优先使用测试值
                if (!x.conf.get("env", "DEV").equals("PRD")) {
                    String test = o.getStr("test");
                    if (!x.isEmpty(test)) {
                        value = test;
                    }
                }
                // EovaConfig.addConfig(o.getStr("code"), value);
                // 切换为 x.ConfigTool

                x.conf.addConfig(o.getStr("code"), value);
                // 记录UI配置项
                if (!o.getBoolean("is_server")) {
                    UI_CONF_KEYS.add(o.getStr("code"));
                    // System.out.println("前端配置项:" + o.getStr("code"));
                }
            });

            // 读取DB配置后进行配置初始化(动态更新某些静态变量)
            initConfig();
        } catch (Exception e) {
            LogKit.warn("读取eova_config异常:" + e.getMessage());
        }
        return true;
    }

    @Override
    public boolean stop() {
        return true;
    }

    /**
     * 初始化全局配置项
     */
    public void initConfig() {
        // EovaConst.ADMIN_UID = x.conf.get("admin_uid", "1");
        EovaConst.ADMIN_RID = x.conf.getInt("admin_rid", 1);
        EovaConst.SYS_ADMIN_UID = x.conf.getInt("sys_admin_uid", 2);
    }
}