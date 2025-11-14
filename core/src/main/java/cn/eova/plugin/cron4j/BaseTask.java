package cn.eova.plugin.cron4j;

import java.util.Calendar;

import cn.eova.tools.x;
import com.jfinal.kit.Kv;
import com.jfinal.kit.LogKit;
import it.sauronsoftware.cron4j.Task;
import it.sauronsoftware.cron4j.TaskExecutionContext;

public abstract class BaseTask extends Task {

    private Kv kv = null;

    /**
     * 设置任务参数
     * @param kv
     */
    public void setParam(Kv kv) {
        this.kv = kv;
    }

    /**
     * 获取任务参数
     * @return
     */
    public Kv getParam() {
        return this.kv;
    }

    @Override
    public void execute(TaskExecutionContext context) throws RuntimeException {
        String name = this.getClass().getName();
        try {
            process(context);
        } catch (Exception e) {
            x.log.error(name + ":" + e.getMessage(), e);
        }
    }

    /**
     * 业务处理
     *
     * @param context 任务上下文
     */
    protected abstract void process(TaskExecutionContext context) throws Exception;


    /**
     * 睡眠模式(24H)
     * eg. 2-8 1点到7点 为睡眠时间
     * @param start 开始睡眠时间点
     * @param end 停止睡眠时间点
     * @return 是否为睡眠状态
     */
    protected static boolean sleepMode(int start, int end) {
        Calendar now = Calendar.getInstance();
        int hour = now.get(Calendar.HOUR_OF_DAY);

        boolean flag = start <= hour && hour <= end;
        if (flag) {
            LogKit.info(String.format("Sleeping[%s-%s]: Now Hour=%s", start, end, hour));
        }
        return flag;
    }
}