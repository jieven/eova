/**
 * Copyright (c) 2015-2026 EOVA.CN. All rights reserved.
 * Licensed under the LGPL-3.0 license
 * For authorization, please contact: admin@eova.cn
 */
package cn.eova.mod;

import java.io.File;
import java.util.HashMap;
import java.util.List;

import com.jfinal.plugin.activerecord.Table;

/**
 * 扩展模块配置入口
 * @author Jieven
 *
 */
public abstract class EovaModConfig {

    /**组织编码**/
    public abstract String GROUP();

    /**模块编码**/
    public abstract String CODE();

    /**
     * 获取Mod View 目录
     * @return
     */
    protected String getViewPath() {
        return String.format("%s%s%s%s", EovaModConst.DIR_MOD_VIEW, GROUP(), File.separator, CODE());
    }

    public abstract void afterEovaStart();

    public abstract void beforeEovaStop();

    public abstract void configRoute(EovaModRoute me);

    public abstract void configModel(HashMap<String, List<Table>> mapping);

    // 试运行阶段暂时不开放如下权限

    //	public abstract void configConstant(Constants me);
    //
    //	public abstract void configEngine(Engine me);
    //
    //	public abstract void configPlugin(Plugins me);
    //
    // public abstract void configInterceptor(Interceptors me);
    //
    //	public abstract void configHandler(Handlers me);

    /**
     * 安装时
     */
    public abstract void onInstall();

    /**
     * 卸载时
     */
    public abstract void onUninstall();

    /**
     * 升级时
     */
    public abstract void onUpgrade();

    public String toString() {
        return String.format("%s-%s", GROUP(), CODE());
    }
}