/**
 * Copyright (c) 2015-2026 EOVA.CN. All rights reserved.
 * Licensed under the LGPL-3.0 license
 * For authorization, please contact: admin@eova.cn
 */
package cn.eova.meta.ctrl;

import java.util.List;

import cn.eova.common.Ds;
import cn.eova.common.base.BaseController;
import cn.eova.model.MsgType;
import cn.eova.service.biz;
import cn.eova.tools.x;
import com.jfinal.aop.Clear;
import com.jfinal.kit.JsonKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

public class TestController extends BaseController {

    public void sse() {
        System.out.println("SSE 连接中..." + UID());
        render("/eova/sse/index.html");
    }

    public void msg() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 5; i++) {
                    try {
                        // 发送消息的支持。
                        int toUID = UID();
                        biz.msg.send(UID(), toUID, "导入消息", "数据已导入完成!" + i, MsgType.OK);
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }).start();

        OK();
    }

    public void index() {
        x.log.info("111");
        renderText("test index...");
    }

    @Clear
    public void go() {
        renderText("test go...");
    }

    public void datetime() {

        List<Record> list = Db.use(Ds.EOVA).find("select * from eova_import");

        System.out.println(JsonKit.toJson(list));

        renderJson(list);
    }

}