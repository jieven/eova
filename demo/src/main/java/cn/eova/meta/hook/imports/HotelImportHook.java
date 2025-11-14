/**
 * Copyright (c) 2015-2026 EOVA.CN. All rights reserved.
 * Licensed under the LGPL-3.0 license
 * For authorization, please contact: admin@eova.cn
 */
package cn.eova.meta.hook.imports;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.eova.hook.BizHook;
import cn.eova.tools.x;
import com.jfinal.kit.Kv;
import com.jfinal.plugin.activerecord.Record;

public class HotelImportHook implements BizHook {

    @Override
    public void invoke(Kv kv) throws Exception {
        System.out.println("HotelImportHook自定义业务逻辑");

        // 导入模式 add update 根据需要自定义,默认表达式(dict_eova_import_type)
        String type = kv.getStr("type");
        // 导入记录 可获取 eova_import 详细记录
        Record log = (Record) kv.get("log");
        // 导入表头
        Map<String, Object> head = (Map<String, Object>) kv.get("head");
        // 导入数据
        List<Record> records = (ArrayList<Record>) kv.get("data");

        System.out.println(head);
        for (Record r : records) {
            System.out.println(r.toJson());
        }

        System.out.println(log.toJson());

        for (int i = 0; i < 3; i++) {
            // 场景1：循环处理需检查中断
            Thread.sleep(1000);
            x.log.info("正在执行导入持久化逻辑....");
        }


        // 异常提示(会写入导入记录, 方便用户根据错误信息对导入数据进行纠错.)
        // throw new Exception("SKU不存在A1001,A1002");
    }

}

