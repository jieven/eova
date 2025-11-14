/**
 * Copyright (c) 2015-2026 EOVA.CN. All rights reserved.
 *
 * Licensed under the LGPL-3.0 license
 * For authorization, please contact: admin@eova.cn
 */
package cn.eova.aop.impl;

import java.util.Map;

import cn.eova.aop.AopContext;
import cn.eova.aop.MetaObjectIntercept;
import cn.eova.engine.ExpUtil;
import com.jfinal.kit.Kv;

/**
 * 通用SQL查询解析
 * <pre>
 * eova_object.view_sql 中配置SQL
 * </pre>
 * @author Jieven
 *
 */
public class SqlQueryIntercept extends MetaObjectIntercept {

    @Override
    public void queryBefore(AopContext ac) throws Exception {

        String sql = ac.object.getStr("view_sql");

        // 语法
        // FROM xxx where vsi.sid = ${sid} and '${start_v_day} 00:00:00' <= update_time and update_time < '${end_v_day} 23:59:59'");

        // 循环撸参
        Map<String, String[]> paraMap = ac.ctrl.getParaMap();

        Kv kv = new Kv();
        paraMap.forEach((key, value) -> {
            kv.set(key, value[0]);
        });
        // 循环参数
        kv.set("user", ac.user);

        // 解析查询值 + 用户值
        sql = ExpUtil.parseSql(sql, kv);

        ac.sql = sql;
    }


}