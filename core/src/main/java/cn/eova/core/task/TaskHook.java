package cn.eova.core.task;

import cn.eova.aop.AopContext;
import cn.eova.hook.EovaMetaHook;
import cn.eova.plugin.cron4j.EovaCronPlugin;

public class TaskHook implements EovaMetaHook {

    @Override
    public String invoke(Action action, AopContext ac) throws Exception {
        switch (action) {

            case DELETE_BEFORE:
                deleteBefore(ac);
                break;
            case ADD_SUCCEED:
                addSucceed(ac);
                break;

            case UPDATE_BEFORE:
                updateBefore(ac);
                break;

        }
        return null;
    }


    public String deleteBefore(AopContext ac) throws Exception {
        int taskId = ac.record.getInt("id");

        // 先停止任务
        EovaCronPlugin.get(taskId).stop();

        return null;
    }

    public String addSucceed(AopContext ac) throws Exception {
        int taskId = ac.record.getInt("id");
        String name = ac.record.getStr("name");
        String className = ac.record.getStr("clazz");
        String cronExp = ac.record.getStr("exp");
        int state = ac.record.getInt("state");
        String params = ac.record.getStr("params");

        // 创建任务(默认禁用)
        EovaCronPlugin.createTask(taskId, name, className, cronExp, params, false);

        return null;
    }

    public String updateBefore(AopContext ac) throws Exception {
        int taskId = ac.record.getInt("id");
        String name = ac.record.getStr("name");
        String className = ac.record.getStr("clazz");
        String cronExp = ac.record.getStr("exp");
        boolean state = ac.record.getBoolean("state");
        String params = ac.record.getStr("params");

        // 删除任务
        EovaCronPlugin.remove(taskId);

        // 禁用 例如类发生变化
        ac.record.set("state", false);
        //Task.dao.updateState(taskId, Task.STATE_STOP);
        // 重新初始化任务(任务参数有变化)
        EovaCronPlugin.createTask(taskId, name, className, cronExp, params, false);

        return null;
    }

}

