/**
 * Copyright (c) 2015-2026 EOVA.CN. All rights reserved.
 * Licensed under the LGPL-3.0 license
 * For authorization, please contact: admin@eova.cn
 */
package cn.eova.sql.ddl.dialect;

import cn.eova.sql.ddl.DefineColumn;
import cn.eova.sql.ddl.DefineTable;

/**
 * DDL(Data Define Language)数据定义方言
 * 关键字：create, drop, alter, truncat(冷门暂不实现) 等
 * @author Jieven
 *
 */
public abstract class DefineDialect {

    /**
     * 对表名或字段名进行转义
     * @param name
     * @return
     */
    public abstract String escape(String name);

    /**
     * DbType 转 EovaType
     * @param dbType 数据库类型
     * @param size 字段长度
     * @return
     */
    public abstract EovaType toEovaType(String dbType, int size);

    /**
     * EovaType 转 DbType
     * @param eovaType
     * @param size
     * @param decimal
     * @return
     */
    public abstract String toDbType(EovaType eovaType, int size, int decimal);

    /**
     * 创建表
     * @param defineTable
     * @return
     */
    public abstract String create(DefineTable defineTable);

    /**
     * 重命名表
     * @param tableName
     * @param newTableName
     * @return
     */
    public abstract String rename(String tableName, String newTableName);

    /**
     * 添加字段
     * @param tableName
     * @param o
     * @return
     */
    public abstract String addColumn(String tableName, DefineColumn o);

    /**
     * 字段属性更新
     * @param tableName
     * @param o
     * @return
     */
    public abstract String updateColumn(String tableName, DefineColumn o);

    /**
     * 字段改名
     * @param tableName
     * @param oldColumnName
     * @param newColumnName
     * @return
     */
    public abstract String updateColumn(String tableName, String oldColumnName, String newColumnName);


    /**
     * 截断表
     * @param tableName
     * @return
     */
    public String truncate(String tableName) {
        return "TRUNCATE TABLE " + escape(tableName) + ";";
    }

    /**
     * 删除表
     * @param tableName
     * @return
     */
    public String dropTable(String tableName) {
        return "DROP TABLE " + escape(tableName) + ";";
    }

    /**
     * 删除字段
     * @param tableName
     * @param columnName
     * @return
     */
    public String dropColumn(String tableName, String columnName) {
        return String.format("ALTER TABLE %s DROP COLUMN %s;", escape(tableName), escape(columnName));
    }

    /**
     * 添加主键(暂时仅支持Mysql, Oracle待实现)
     * @param tableName
     * @param pk
     * @return
     */
    public String addPrimary(String tableName, String pk) {
        return String.format("ALTER TABLE %s ADD PRIMARY KEY (%s);", escape(tableName), escape(pk));
    }


    /**
     * 格式化默认值
     * @param o
     * @return
     */
    protected String formatDefault(DefineColumn o) {
        String def = o.getDefault();

        // 字符串格式化
        if (o.getType() == EovaType.VARCHAR || o.getType() == EovaType.CHAR) {
            return "'" + def + "'";
        }

        return def;
    }
}
