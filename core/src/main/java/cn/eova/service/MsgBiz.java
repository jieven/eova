/**
 * Copyright (c) 2015-2026 EOVA.CN. All rights reserved.
 * Licensed under the LGPL-3.0 license
 * For authorization, please contact: admin@eova.cn
 */
package cn.eova.service;

import cn.eova.common.Ds;
import cn.eova.common.utils.web.SseKit;
import cn.eova.model.MsgType;
import com.jfinal.kit.Kv;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

/**
 * 消息服务
 *
 * @author Jieven
 */
public class MsgBiz {


    /**
     * 发送消息推送
     * @param formUid 发信人UID
     * @param toUid 收信人UID
     * @param title 标题
     * @param info 内容
     * @param type 弹出类型
     */
    public void send(int formUid, int toUid, String title, String info, MsgType type) {
        Record r = new Record();
        r.set("type", title);
        r.set("from_uid", formUid);
        r.set("to_uid", toUid);
        r.set("info", info);
        Db.use(Ds.EOVA).save("eova_msg", r);

        SseKit.pushMsg(toUid, Kv.of("title", title).set("info", info).set("type", type.toString().toLowerCase()));
    }

}