/**
 * Copyright (c) 2015-2026 EOVA.CN. All rights reserved.
 * Licensed under the LGPL-3.0 license
 * For authorization, please contact: admin@eova.cn
 */
package cn.eova.sql.dql.dialect;

import com.jfinal.plugin.activerecord.dialect.MysqlDialect;

public class EovaMysqlDialect extends MysqlDialect {

    @Override
    public String forTableBuilderDoBuild(String tableName) {
        // 修复 8.0.26+ 版本 where 1 = 2 导致异常(连Mysql5.7的数据库才会触发), 比较隐蔽,绕开此问题.
        // where 1 = 3 也会导致5.1.30 与 8.0.26 双版本并存时 5.1.30 注册Model时执行本SQL阻塞
        return "select * from `" + tableName + "` where false";
    }
}
