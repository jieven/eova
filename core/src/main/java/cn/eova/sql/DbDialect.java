/**
 * Copyright (c) 2015-2026 EOVA.CN. All rights reserved.
 * Licensed under the LGPL-3.0 license
 * For authorization, please contact: admin@eova.cn
 */
package cn.eova.sql;

import com.alibaba.druid.DbType;
import com.alibaba.druid.util.JdbcUtils;

public class DbDialect {
    /**
     * Mysql SQL 转为其它DB可执行兼容SQL
     * @param dbType
     * @param sql
     * @return
     */
    public static String escape(DbType dbType, String sql) {

        // 去掉Mysql转义符
        sql = sql.replaceAll("`", getEscape(dbType));

        // Mysql[\'] 反斜杠转义单引号
        // Mysql[\"] 反斜杠转义双引号
        if (JdbcUtils.isOracleDbType(dbType) || JdbcUtils.isPgsqlDbType(dbType)) {
            // Oracle[''] 单引号转义
            sql = sql.replaceAll("\\\\'", "''");
            // Oracle["] 支持双引号
            sql = sql.replaceAll("\\\\\"", "\"");
        }
        // Oracle 和 Pgsql 转义规则相同, 下面加E 实测可以, 但是可能配置方法不同!!
        // 参考 https://www.cnblogs.com/xiangnan/p/6506449.html
//		else if (JdbcUtils.POSTGRESQL.equalsIgnoreCase(dbType)) {
//			// Pgsql[E'] 字符串前面加上E(E就是Escape)进行转义, 单双引号一波带走
//			// 偷懒做法, 如果存在多重转义,则还需要手工处理
//			sql = sql.replaceAll("\\\\'", "'").replaceFirst("'", "E'");
//		}

        // else if (JdbcUtils.SQL_SERVER.equalsIgnoreCase(dbType)) {
        // SqlServer 待测试
        //			sql = sql.replaceAll("\\\\'", "'").replaceFirst("'", "E'");
        //		}
        return sql;
    }


    //	/**
    //	 * Mysql单引号转义 \' 转其它DB
    //	 * @param ds
    //	 * @return
    //	 */
    //	public static String escape(String dbType, String sql) {
    //		EovaDataSource.getEscape(ds)
    //
    //		// -- Mysql --> \' --> 单反斜杠转义单引号
    //		if (!sql.contains("\\'")) {
    //			return sql;
    //		}
    //		if (JdbcUtils.ORACLE.equalsIgnoreCase(dbType)) {
    //			// -- Oracle --> '' --> 单引号转义单引号
    //			// -- UPDATE eova_xxx SET a = '	select * from eova_dict where object = ''eova_field''		';
    //			return sql.replaceAll("\\\\'", "''");
    //		}
    //		if (JdbcUtils.POSTGRESQL.equalsIgnoreCase(dbType)) {
    //			// -- Pgsql --> '' --> 字符串前面加上E(E就是Escape)进行转义
    //			// -- UPDATE eova_xxx SET a = E'	select * from eova_dict where object = 'eova_field'		';
    //			// 偷懒做法, 如果存在多重转义,则还需要手工处理
    //			return sql.replaceAll("\\\\'", "'").replaceFirst("'", "E'");
    //		}
    //		// SqlServer 待测试
    //		//		else if (JdbcUtils.SQL_SERVER.equalsIgnoreCase(dbType)) {
    //		//			return sql;
    //		//		}
    //		return sql;
    //	}

    /**
     * 获取数据库字段表名转义符
     * @return
     */
    public static String getEscape(DbType dbType) {
        if (JdbcUtils.isMysqlDbType(dbType)) {
            return "`";
        } else if (JdbcUtils.isOracleDbType(dbType)) {
            return "";// 待确定Oracle转义符
        } else if (JdbcUtils.isPgsqlDbType(dbType)) {
            return "";
        } else if (JdbcUtils.isSqlserverDbType(dbType)) {
            return "";
        }
        return "";
    }
}