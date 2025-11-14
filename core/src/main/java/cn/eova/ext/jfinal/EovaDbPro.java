/**
 * Copyright (c) 2015-2026 EOVA.CN. All rights reserved.
 * Licensed under the LGPL-3.0 license
 * For authorization, please contact: admin@eova.cn
 */
package cn.eova.ext.jfinal;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import cn.eova.tools.x;
import com.alibaba.druid.DbType;
import cn.eova.common.utils.db.SqlUtil;
import cn.eova.config.EovaDataSource;
import cn.eova.sql.ddl.DefineDialectFactory;
import cn.eova.sql.ddl.dialect.DefineDialect;
import com.jfinal.plugin.activerecord.Config;
import com.jfinal.plugin.activerecord.DbPro;
import com.jfinal.plugin.activerecord.Record;

/**
 * 拓展默认Db操作<br>
 * 1.常用聚合API自动类型转换,使用更顺滑<br>
 * 2.Eova Oracle序列的自动指定
 * 3.全局DB关键字冲突优化
 *
 * @author Jieven
 *
 */
public class EovaDbPro extends DbPro {

    public EovaDbPro(String configName) {
        super(configName);
    }

    @Override
    public boolean save(String tableName, String primaryKey, Record record) {
        String ds = this.getConfig().getName();
        boolean isOracle = EovaDataSource.getDbType(ds) == DbType.oracle;

        // Oracle && 单主键 && 主键没值 -> 指定Sequence
        if (isOracle && !primaryKey.contains(",") && record.get(primaryKey) == null) {
            record.set(primaryKey, SqlUtil.getSequence(ds, tableName));
        }
        return super.save(tableName, primaryKey, record);
    }

    @Override
    public Record findByIds(String tableName, String primaryKey, Object... idValues) {

        String ds = this.getConfig().getName();

        if (EovaDataSource.getDbType(ds) == DbType.postgresql) {
            // PostgreSql:operator does not exist: integer = character varying
            // PGSQL为严格强类型, 数值类型的主键传字符串值就会异常, 为了使用顺滑再次做自动类型强转
            // 特别约束: 主键类型为INT 则必须值为数字(Seq), 类型为VARCHAR 则必须为字符串(UUID)
            // 反之 例如数字10001 就不能使用VARCHAR
            for (int i = 0; i < idValues.length; i++) {
                try {
                    idValues[i] = Integer.parseInt(idValues[i].toString());
                } catch (Exception e) {
                }
            }
        }
        return super.findByIds(tableName, primaryKey, idValues);
    }

    @Override
    protected List<Record> find(Config config, Connection conn, String sql, Object... paras) throws SQLException {
        return super.find(config, conn, escape(config, sql), paras);
    }

    @Override
    protected <T> List<T> query(Config config, Connection conn, String sql, Object... paras) throws SQLException {
        return super.query(config, conn, escape(config, sql), paras);
    }

    @Override
    protected int update(Config config, Connection conn, String sql, Object... paras) throws SQLException {
        return super.update(config, conn, escape(config, sql), paras);
    }

    /**
     * SQL关键字转义处理
     * @param config
     * @param sql
     * @return
     */
    private String escape(Config config, String sql) {
        String ds = this.getConfig().getName();
        // 获取DDL方言
        DefineDialect dd = DefineDialectFactory.getDialect(ds);

        // 系统关键字转义
        if (EovaDataSource.getDbType(ds) == DbType.dm) {
            String[] fields = {"eova_dict.object", "dicts.object"};
            for (String field : fields) {
                sql = SqlUtil.escapeSqlKeyword(dd, sql, field);
            }
        } else if (EovaDataSource.getDbType(ds) == DbType.h2) {
            String[] fields = {"eova_dict.value", "eova_config.value", "eova_widget.value", "dicts.value"};
            for (String field : fields) {
                sql = SqlUtil.escapeSqlKeyword(dd, sql, field);
            }
        }

        // 用户自定义数据源关键字
        String s = x.conf.get("db.keyword");
        if (!x.isEmpty(s)) {
            String[] fields = s.split(",");
            for (String field : fields) {
                sql = SqlUtil.escapeSqlKeyword(dd, sql, field);
            }
        }

        return sql;
    }


}