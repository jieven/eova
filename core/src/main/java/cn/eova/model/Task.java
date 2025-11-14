/**
 * Copyright (c) 2015-2026 EOVA.CN. All rights reserved.
 * Licensed under the LGPL-3.0 license
 * For authorization, please contact: admin@eova.cn
 */
package cn.eova.model;

import java.util.List;

import cn.eova.tools.x;
import cn.eova.common.Ds;
import cn.eova.common.base.BaseModel;
import com.jfinal.plugin.activerecord.Db;

/**
 * 定时任务
 *
 * @author Jieven
 * @date 2014-9-10
 */
public class Task extends BaseModel<Task> {

    private static final long serialVersionUID = 4254060861819273244L;

    public static final Task dao = new Task();

    /** 停用 **/
    public static final int STATE_STOP = 0;
    /** 启用 **/
    public static final int STATE_START = 1;

    /**
     * 获取全部任务(因为在线管控, 需要先加载插件)
     * @return
     */
    public List<Task> findTask() {
        String sql = "select * from eova_task";
        String jobBiz = x.conf.get("job.biz");
        if (!x.isEmpty(jobBiz)) {
            sql += String.format(" where biz = '%s'", jobBiz);
        }
        return this.find(sql);
    }


    public int updateState(int id, int state) {
        return Db.use(Ds.EOVA).update("update eova_task set state = ? where id = ?", state, id);
    }
}