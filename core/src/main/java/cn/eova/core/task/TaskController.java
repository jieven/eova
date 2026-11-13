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
import cn.eova.tools.x;
import com.jfinal.kit.Kv;
import com.jfinal.plugin.cron4j.Cron4jPlugin;

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
            Cron4jPlugin cp = EovaCronPlugin.get(id);
            if (cp != null) {
                cp.start();
                Task.dao.updateState(id, Task.STATE_START);
            } else {
                x.log.info("任务实例不存在：" + id);
            }
        } catch (Exception e) {
            x.log.error(e, "实例停止失败");
            NO("实例启动失败：" + e.getMessage());
        }

        OK();
    }

    // 停用
    public void stop() {
        int id = getInt("id");

        try {
            Cron4jPlugin cp = EovaCronPlugin.get(id);
            if (cp != null) {
                cp.stop();
                Task.dao.updateState(id, Task.STATE_STOP);
            } else {
                x.log.info("任务实例不存在：" + id);
            }
        } catch (Exception e) {
            x.log.error(e, "实例停止失败");
            NO("实例停止失败：" + e.getMessage());
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
            x.log.error(e, "实例运行失败");
            NO("实例运行失败：" + e.getMessage());
        }

        OK();
    }


}