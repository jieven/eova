/**
 * Copyright (c) 2015-2026 EOVA.CN. All rights reserved.
 * Licensed under the LGPL-3.0 license
 * For authorization, please contact: admin@eova.cn
 */
package cn.eova.sql.ddl;

import java.util.HashMap;

import cn.eova.sql.ddl.dialect.DefineDialect;
import cn.eova.sql.ddl.dialect.MysqlDefineDialect;

/**
 * DDL(data define language)数据查询方言方言工厂
 * @author Jieven
 *
 */
public class DefineDialectFactory {

    private static HashMap<String, DefineDialect> defineDialectMap = new HashMap<>();

    public static DefineDialect getDialect(String ds) {
        DefineDialect dd = defineDialectMap.get(ds);
        if (dd == null) {
            // 为了方面测试, 默认指定为Mysql
            dd = new MysqlDefineDialect();
        }
        return dd;
    }

    public static void addDialect(String ds, DefineDialect defineDialect) {
        defineDialectMap.put(ds, defineDialect);
    }

    private DefineDialect dialect = null;
    private String ds = null;

    public DefineDialectFactory(String ds) {
        this.ds = ds;
        this.dialect = getDialect(ds);
    }

    /**
     * 执行脚本
     * @param tables
     */
//	public void createTable(List<DefineTable> tables) {
//
//		// 生成DDL方言
//		List<String> sqls = new ArrayList<String>();
//		tables.forEach(t -> {
//			String s = dialect.create(t);
//			sqls.add("DROP TABLE IF EXISTS `" + t.getEn() + "`;");
//			sqls.add(s);
//		});
//
//		// 执行SQL
//		sqls.forEach(x -> System.out.println(x));
//		Db.use(ds).batch(sqls, sqls.size());
//	}

}