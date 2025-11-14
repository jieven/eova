/**
 * Copyright (c) 2015-2026 EOVA.CN. All rights reserved.
 * Licensed under the LGPL-3.0 license
 * For authorization, please contact: admin@eova.cn
 */
package cn.eova.config;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import cn.eova.tools.x;
import com.alibaba.druid.DbType;
import com.alibaba.druid.filter.logging.Log4jFilter;
import com.alibaba.druid.filter.stat.StatFilter;
import com.alibaba.druid.util.JdbcUtils;
import com.alibaba.druid.wall.WallConfig;
import com.alibaba.druid.wall.WallFilter;
import cn.eova.common.Ds;
import cn.eova.common.utils.io.ClassUtil;
import cn.eova.common.utils.string.AESUtil;
import cn.eova.common.utils.xx;
import cn.eova.core.type.Convertor;
import cn.eova.core.type.MysqlConvertor;
import cn.eova.ext.jfinal.EovaDbPro;
import cn.eova.ext.jfinal.EovaOracleDialect;
import cn.eova.ext.jfinal.LinkedCaseInsensitiveContainerFactory;
import cn.eova.sql.ddl.DefineDialectFactory;
import cn.eova.sql.ddl.dialect.DefineDialect;
import cn.eova.sql.ddl.dialect.MysqlDefineDialect;
import cn.eova.sql.dql.dialect.EovaMysqlDialect;
import cn.eova.sql.dql.dialect.MysqlQueryDialect;
import cn.eova.sql.dql.dialect.QueryDialect;
import com.jfinal.config.Plugins;
import com.jfinal.kit.LogKit;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.activerecord.CaseInsensitiveContainerFactory;
import com.jfinal.plugin.activerecord.DbPro;
import com.jfinal.plugin.activerecord.IDataSourceProvider;
import com.jfinal.plugin.activerecord.IDbProFactory;
import com.jfinal.plugin.activerecord.dialect.Dialect;
import com.jfinal.plugin.activerecord.dialect.PostgreSqlDialect;
import com.jfinal.plugin.activerecord.dialect.SqlServerDialect;
import com.jfinal.plugin.druid.DruidPlugin;

public class EovaDataSource {

    /** 数据源列表<数据源名, 数据源DB类型> **/
    private static final Map<String, DbType> dataSources = new HashMap<>();

    public static Map<String, DbType> map() {
        return dataSources;
    }

    /**
     * 获取数据库类型
     * @param ds
     * @return
     */
    public static DbType getDbType(String ds) {
        return dataSources.get(ds);
    }

    /**
     * 注册数据源(支持多数据源)
     * @param plugins
     */
    public static void create(Plugins plugins) {
        // 多数据源支持
        String datasource = x.conf.get("db.datasource");
        if (x.isEmpty(datasource)) {
            throw new RuntimeException("数据源配置项不存在,请检查配置jdbc.config 配置项[db.datasource]");
        }
        for (String ds : datasource.split(",")) {
            ds = ds.trim();

            String url = x.conf.get(ds + ".url");
            String user = x.conf.get(ds + ".user");
            String pwd = x.conf.get(ds + ".pwd");
            String driver = x.conf.get(ds + ".driver");
            String filters = x.conf.get(ds + ".filters", "log4j,stat,wall");
            if (x.isEmpty(url)) {
                throw new RuntimeException(String.format("数据源[%s]配置异常,请检查请检查配置jdbc.config", ds));
            }

            // JDBC密码加密
            if (x.conf.getBool("db.pwd.encrypt", false)) {
                pwd = AESUtil.decrypt(pwd);
            }

            DruidPlugin dp = EovaDataSource.initDruidPlugin(url, user, pwd, driver, filters);
            ActiveRecordPlugin arp = EovaDataSource.initActiveRecordPlugin(url, ds, dp);
            xx.info("create ds[%s] %s > %s", ds, user, url);

            try {
                dataSources.put(ds, JdbcUtils.getDbTypeRaw(url, JdbcUtils.getDriverClassName(url)));
            } catch (SQLException e) {
                e.printStackTrace();
            }
            EovaConfig.arps.put(ds, arp);
            plugins.add(dp).add(arp);
        }

    }

    /**
     * init Druid
     *
     * @param url JDBC
     * @param username 数据库用户
     * @param password 数据库密码
     * @param driverClass
     * @return
     */
    public static DruidPlugin initDruidPlugin(String url, String username, String password, String driverClass, String filters) {

        if (x.isEmpty(filters)) {
            filters = "log4j,stat,wall";
        }

        // 设置方言
        String dbType = null;
        try {
            dbType = JdbcUtils.getDbType(url, JdbcUtils.getDriverClassName(url));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        DruidPlugin dp = new DruidPlugin(url, username, password);
//		if (url.contains("ver=8.0")) {
//			dp.setDriverClass("com.mysql.cj.jdbc.Driver");
//		} else if (url.startsWith("jdbc:mariadb")) {
//			dp.setDriverClass("org.mariadb.jdbc.Driver");
//		} else {
//			dp.setDriverClass("com.mysql.jdbc.Driver");
//		}
        if (!x.isEmpty(driverClass)) {
            dp.setDriverClass(driverClass);
        }
        dp.addFilter(new StatFilter());

        dp.setMaxWait(x.conf.getInt("db.conn.timeout", -1));// 无限等待, 防止获取连接异常
        dp.setInitialSize(x.conf.getInt("db.conn.init", 1));// 初始化1个
        dp.setMinIdle(x.conf.getInt("db.conn.min", 2));// 最少空闲2个/ 连接上限32个
        dp.setMaxActive(x.conf.getInt("db.conn.max", 32));

        // dp.setTestWhileIdle(false);// 屏蔽连接回收警告.

        WallFilter wall = new WallFilter();
        wall.setDbType(dbType);

        // 开发者模式 降低SQL安全策略, 保证应用商店安装卸载需要开发者模式.
        boolean devMode = x.conf.getBool("devMode", false);
        if (devMode) {

            // 开发环境安全降级(支持EovaMod)
            // https://github.com/alibaba/druid/wiki/%E9%85%8D%E7%BD%AE-wallfilter
            WallConfig config = new WallConfig();
            config.setMultiStatementAllow(true);// 一次执行多条语句
            config.setNoneBaseStatementAllow(false);// 非基本DDL语句
            //config.setConditionAndAlwayTrueAllow(true);// 允许查询条件永真 1=1
            //config.setConditionAndAlwayFalseAllow(true);// 允许查询条件永假 1=2
            wall.setConfig(config);
            wall.setThrowException(false);// SQL注入抛出SQLException
            wall.setLogViolation(true);/// SQL注入输出日志

            // 日志过滤器
            if (filters.contains("log4j")) {
                // 配置log插件
                Log4jFilter logFilter = new Log4jFilter();
                logFilter.setStatementLogEnabled(false);
                logFilter.setStatementLogErrorEnabled(true);
                logFilter.setStatementExecutableSqlLogEnable(true);
                dp.addFilter(logFilter);
                // log4j.logger.druid.sql.Statement=DEBUG 输出完整SQL日志
            }
        }

        // SQL防火墙
        if (filters.contains("wall")) {
            dp.addFilter(wall);
        }


        return dp;
    }

    /**
     * init ActiveRecord
     *
     * @param url JDBC
     * @param ds DataSource
     * @param dp Druid
     * @return
     */
    public static ActiveRecordPlugin initActiveRecordPlugin(String url, String ds, IDataSourceProvider dp) {
        // 提升事务级别保证事务回滚 MYSQL TRANSACTION_REPEATABLE_READ=4
        int lv = x.conf.getInt("db.transaction_level", 4);
        // 是否输出SQL日志
        boolean isShowSql = x.conf.getBool("db.showsql", true);
        // 字段是否转小写
        boolean isLowerCase = x.conf.getBool("db.islowercase", true);
        // 字段是否保持顺序
        boolean isOrderly = x.conf.getBool("db.isorderly", false);

        ActiveRecordPlugin arp = new ActiveRecordPlugin(ds, dp);

        // 自定义Eova专用Db代理
        arp.setDbProFactory(new IDbProFactory() {
            public DbPro getDbPro(String configName) {
                return new EovaDbPro(configName);
            }
        });

        DbType dbType = DbType.mysql;
        // 获取DB类型
        try {
            dbType = JdbcUtils.getDbTypeRaw(url, JdbcUtils.getDriverClassName(url));
            if (ds.equalsIgnoreCase(Ds.EOVA)) {
                // Eova的数据库类型
                EovaConfig.EOVA_DBTYPE = dbType;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        // 构建方言
        Dialect dialect = buildDialect(dbType, ds, (DruidPlugin) dp);
        arp.setDialect(dialect);
        arp.setShowSql(isShowSql);
        arp.setTransactionLevel(lv);

        // 保持字段原始顺序
        if (isOrderly) {
            arp.setContainerFactory(new LinkedCaseInsensitiveContainerFactory(isLowerCase));
        }
        // 无序
        else {
            arp.setContainerFactory(new CaseInsensitiveContainerFactory(isLowerCase));
        }

        return arp;
    }

    /**
     * 构建其它非Mysql方言
     *
     * @param dbType
     * @param ds
     */
    static Dialect buildDialect(DbType dbType, String ds, DruidPlugin dp) {
        // 默认为Mysql方言
        Dialect dialect = new EovaMysqlDialect();
        QueryDialect queryDialect = new MysqlQueryDialect();
        DefineDialect defineDialect = new MysqlDefineDialect();
        Convertor convertor = new MysqlConvertor();

        // 初始化基础方言
        if (dbType == DbType.oracle) {
            ((DruidPlugin) dp).setValidationQuery("select 1 FROM DUAL");
            dialect = new EovaOracleDialect();
        } else if (dbType == DbType.postgresql) {
            dialect = new PostgreSqlDialect();
        } else if (dbType == DbType.sqlserver) {
            dialect = new SqlServerDialect();
        }

        // 初始化Eova平台业务兼容方言
        try {
            if (dbType == DbType.oracle) {
                convertor = ClassUtil.newClass("com.eova.mod.eova.oracle.OracleConvertor");
                queryDialect = ClassUtil.newClass("com.eova.mod.eova.oracle.OracleQueryDialect");
                defineDialect = ClassUtil.newClass("com.eova.mod.eova.oracle.OracleDefineDialect");
            } else if (dbType == DbType.postgresql) {
                convertor = ClassUtil.newClass("com.eova.mod.eova.postgresql.PostgreSqlConvertor");
                queryDialect = ClassUtil.newClass("com.eova.mod.eova.postgresql.PostgreSqlQueryDialect");
                defineDialect = ClassUtil.newClass("com.eova.mod.eova.postgresql.PostgreSqlDefineDialect");
            } else if (dbType == DbType.sqlserver) {
                convertor = ClassUtil.newClass("com.eova.mod.eova.sqlserver.SqlServerConvertor");
                queryDialect = ClassUtil.newClass("com.eova.mod.eova.sqlserver.SqlServerQueryDialect");
                defineDialect = ClassUtil.newClass("com.eova.mod.eova.sqlserver.SqlServerDefineDialect");
            } else if (dbType == DbType.dm) {
                convertor = ClassUtil.newClass("com.eova.mod.eova.dm.DmConvertor");
                queryDialect = ClassUtil.newClass("com.eova.mod.eova.dm.DmQueryDialect");
                defineDialect = ClassUtil.newClass("com.eova.mod.eova.dm.DmDefineDialect");

                dialect = ClassUtil.newClass("com.eova.mod.eova.dm.DmDialect");
            }
        } catch (Exception e) {
            if (x.conf.getBool("db.support.info", true)) {
                LogKit.error("如果仅添加数据源通过JFinal查库，则无需Eova兼容方言支持，可主动屏蔽本异常提示：db.support.info=false");
                LogKit.error("EovaDB兼容方言加载异常：Eova默认仅提供Mysql支持，如需在其它DB下使用Eova，请升级服务 https://www.eova.cn/eovapro#commerces", e);
            }
        }

        EovaConfig.addQueryDialect(ds, queryDialect);
        EovaConfig.addConvertor(ds, convertor);
        DefineDialectFactory.addDialect(ds, defineDialect);
        return dialect;
    }

}