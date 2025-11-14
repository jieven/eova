/**
 * Copyright (c) 2015-2026 EOVA.CN. All rights reserved.
 * Licensed under the LGPL-3.0 license
 * For authorization, please contact: admin@eova.cn
 */
package cn.eova.config;

import com.jfinal.server.undertow.UndertowServer;

/**
 * 全局系统配置
 * 启动时由主ClassLoader负责加载
 * @author Jieven
 *
 */
public class EovaSystem {

    /**
     * 当前启动服务全局共享
     */
    public static UndertowServer server = null;

}