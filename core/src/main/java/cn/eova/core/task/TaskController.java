/**
 * Copyright (c) 2015-2026 EOVA.CN. All rights reserved.
 * Licensed under the LGPL-3.0 license
 * For authorization, please contact: admin@eova.cn
 */
package cn.eova.core.task;

import cn.eova.common.base.BaseController;
import cn.eova.common.utils.util.JsonUtil;
import cn.eova.model.Task;
import cn.eova.plugin.cron4j.EovaCronPlugin;
import com.jfinal.kit.Kv;

/**
 * 定时任务
 *
 * @author Jieven
 *
 */
public class TaskController extends BaseController {

    // 启用
    public void start() {
        int id = getInt("id");

        try {
            EovaCronPlugin.get(id).start();

            Task.dao.updateState(id, Task.STATE_START);
        } catch (Exception e) {
            e.printStackTrace();
            OK();
        }

        OK();
    }

    // 停用
    public void stop() {
        int id = getInt("id");

        try {
            EovaCronPlugin.get(id).stop();

            Task.dao.updateState(id, Task.STATE_STOP);
        } catch (Exception e) {
            e.printStackTrace();
            OK();
        }

        OK();
    }

    // 立即运行一次任务
    public void run() {
        int id = getInt("id");
        String className = get("clazz");
        String params = get("params");

        try {
            Kv kv = JsonUtil.toKv(params);

            // 立即运行一次
            EovaCronPlugin.runTask(className, kv);
        } catch (Exception e) {
            e.printStackTrace();

            NO("任务运行失败！");
        }

        OK();
    }


}