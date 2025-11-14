/**
 * Copyright (c) 2015-2026 EOVA.CN. All rights reserved.
 * Licensed under the LGPL-3.0 license
 * For authorization, please contact: admin@eova.cn
 */
package cn.eova.sql.ddl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import cn.eova.mod.EovaModUtil;
import cn.eova.sql.ddl.dialect.DefineDialect;

public abstract class DataDefine {

    protected abstract String getDs();

    protected abstract List<DefineTable> createTable();

    protected abstract Set<String> dropTable();

    // protected abstract void createTableBefore(DefineDialect dd, List<String> sqls);

    // protected abstract void createTableAfter(DefineDialect dd, List<String> sqls);

    /**
     * 建表
     * @return
     */
    public void create() {
        List<String> sqls = new ArrayList<>();

        // 获取DDL方言
        DefineDialect dd = DefineDialectFactory.getDialect(getDs());

        // 建表
        createTable().forEach(t -> {
            sqls.add(dd.create(t));
        });

        EovaModUtil.executeSql(getDs(), sqls);
    }

    /**
     * 删表
     * @return
     */
    public void drop() {
        List<String> sqls = new ArrayList<>();
        DefineDialect dd = DefineDialectFactory.getDialect(getDs());

        for (String tableName : dropTable()) {
            sqls.add(dd.dropTable(tableName));
        }

        EovaModUtil.executeSql(getDs(), sqls);
    }

    /**
     * 生成建表DDL
     * @return
     */
    public void generateCreateDDL() {
        List<String> sqls = new ArrayList<>();

        // 获取DDL方言
        DefineDialect dd = DefineDialectFactory.getDialect(getDs());

        // 建表
        createTable().forEach(t -> {
            sqls.add(dd.create(t));
        });

        // 生成SQL日志
        StringBuffer sb = new StringBuffer();
        sqls.forEach(x -> sb.append(x).append("\n"));// 空行格式化

        System.out.println(sb.toString());
    }

    /**
     * 生成删表DDL
     * @return
     */
    public void generateDropDDL() {
        DefineDialect dd = DefineDialectFactory.getDialect(getDs());
        for (String tableName : dropTable()) {
            System.out.println(dd.dropTable(tableName));
        }
    }

}
