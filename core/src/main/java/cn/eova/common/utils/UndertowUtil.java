/**
 * Copyright (c) 2015-2026 EOVA.CN. All rights reserved.
 * Licensed under the LGPL-3.0 license
 * For authorization, please contact: admin@eova.cn
 */
package cn.eova.common.utils;

import java.io.File;

import cn.eova.tools.x;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.file.FileAppender;
import cn.eova.config.EovaSystem;

public class UndertowUtil {

    /**
     * 是否存在服务实例
     * @return
     */
    public static boolean isServer() {
        return EovaSystem.server != null;
    }

    /**
     * 重启当前服务(热加载)
     */
    public static void restart() {
        if (!isServer()) {
            System.err.println("启动类未正常配置, 无法重启");
            return;
        }

        if (!EovaSystem.server.isDevMode()) {
            System.err.println("Undertow 未开启热加载, 请手工重启服务! 配置undertow.txt: undertow.devMode=true");
            return;
        }

        if (!x.conf.getBool("devMode", false)) {
            System.err.println("未开启开发模式, 请手工重启服务! devMode = true");
            return;
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    System.err.println("Undetow Server Restarting ......");
                    Thread.sleep(500);// 延迟500ms, 避免Web来不及返回信息
                    EovaSystem.server.restart();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

        // hotSwap();
    }

    /**
     * <pre>
     * 主动触发热加载
     * 原理:通过修改某个Java文件来触发热加载 @see HotSwapWatcher
     * 前置条件:
     * 配置:hotswap.trigger = HotSwapTrigger
     * 配置文件自动更新:Window -> Preferences -> General -> Workspace |勾选| Refresh using native hooks or polling
     * </pre>
     */
    public static void hotSwap() {
        String trigger = x.conf.get("hotswap.trigger");
        if (!x.isEmpty(trigger)) {
            String project = System.getProperty("user.dir");
            String path = String.format("%s/src/main/java/dev/%s.java", project, trigger);
            // System.out.println(path);
            boolean isInit = false;
            if (!FileUtil.exist(path)) {
                isInit = true;
            }
            FileAppender appender = new FileAppender(new File(path), 1, true);
            if (isInit) {
                appender.append(String.format("package dev;public class %s {}", trigger));
            }
            appender.append("// " + x.time.now());
            appender.flush();
            System.err.println("EOVA 主动热加载...");
        }
    }

}