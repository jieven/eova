package cn.eova.meta.job;

import cn.eova.tools.x;
import cn.eova.plugin.cron4j.BaseTask;
import com.jfinal.kit.Kv;
import it.sauronsoftware.cron4j.TaskExecutionContext;

public class MyDemoTask extends BaseTask {

    @Override
    protected void process(TaskExecutionContext ac) throws Exception {
        System.out.println("Eova Task: 当前时间：" + x.time.formatNowTimes());

        // 复用Task, 干多件事, 例如 备份Task, 传入不同表, 进行备份.
        Kv param = this.getParam();
        if (param != null) {
            System.out.println(param.getStr("type"));
        }

        // 夜间休眠, 节省体力
        sleepMode(18, 20);
    }
}