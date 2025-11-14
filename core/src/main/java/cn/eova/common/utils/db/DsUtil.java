/**
 * Copyright (c) 2015-2026 EOVA.CN. All rights reserved.
 * Licensed under the LGPL-3.0 license
 * For authorization, please contact: admin@eova.cn
 */
package cn.eova.common.utils.db;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import cn.eova.tools.x;
import com.alibaba.druid.DbType;
import com.alibaba.druid.stat.TableStat.Name;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import cn.eova.common.utils.xx;
import cn.eova.config.EovaDataSource;
import com.jfinal.kit.LogKit;
import com.jfinal.plugin.activerecord.Config;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.DbKit;
import com.jfinal.plugin.activerecord.Record;

/**
 * 数据源工具类
 *
 * @author Jieven
 * @date 2015-6-27
 */
public class DsUtil {

    public static final String TABLE = "Table";
    public static final String VIEW = "View";

    /**
     * 单例获取连接, 防止忘记关闭, 导致连接池耗尽
     */
    public static Connection conn = null;

    public static Connection getConnection(String ds) throws SQLException {
        Config config = DbKit.getConfig(ds);
        if (config == null) {
            throw new SQLException(ds + " datasrouce can not get config");
        }
        return config.getDataSource().getConnection();
    }

    /**
     * 获得元数据对象
     *
     * @param ds 数据源
     * @param props 连接配置
     * @return
     */
    public static DatabaseMetaData getDatabaseMetaData(String ds, Properties props) {
        Connection conn = null;
        try {
            conn = getConnection(ds);

            // TODO Mysql Test is OK!
            if (props != null) {
                conn.setClientInfo(props);
            }
            DatabaseMetaData md = conn.getMetaData();
            return md;
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            closeConn(conn);
        }
    }

    public static DatabaseMetaData getDatabaseMetaData(String ds) {
        return getDatabaseMetaData(ds, null);
    }

    /**
     * 获取数据源的数据库名
     *
     * @param ds 数据源
     * @return
     */
    public static String getDbNameByConfigName(String ds) {
        Connection conn = null;
        try {
            conn = getConnection(ds);
            DbType dbType = EovaDataSource.getDbType(ds);
            if (dbType == DbType.dm) {
                // 一用户一模式(作为DB名)
                return conn.getSchema();
            }
            return conn.getCatalog();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConn(conn);
        }

        return null;
    }

    /**
     * 获取自增长字段名
     * @param ds
     * @param tableName
     * @return
     */
    public static String getAutoColumn(String ds, String tableName) {
        JSONArray cols = getColumnInfoByConfigName(ds, tableName);
        for (int i = 0; i < cols.size(); i++) {
            JSONObject o = cols.getJSONObject(i);
            String name = o.getString("COLUMN_NAME");
            String auto = o.getString("IS_AUTOINCREMENT");
            if ("YES".equalsIgnoreCase(auto)) {
                return name;
            }
        }
        return null;
    }

    /**
     * 获取数据源的用户名
     *
     * @param ds 数据源
     * @return
     */
    public static String getUserNameByConfigName(String ds) {
        try {
            DatabaseMetaData databaseMetaData = getDatabaseMetaData(ds);
            return databaseMetaData.getUserName();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取数据源中表/视图的名字列表
     *
     * @param ds 数据源
     * @param type DsUtil.TABLE/VIEW
     * @param tableNamePattern TODO
     * @return
     */
    public static List<String> getTableNamesByConfigName(String ds, String type, String tableNamePattern) {

        if (tableNamePattern == null) {
            tableNamePattern = "%";
        }

        List<String> tables = new ArrayList<String>();
        ResultSet rs = null;
        try {
            DatabaseMetaData md = getDatabaseMetaData(ds);
            String schema = getSchemaPattern(ds);
            if (EovaDataSource.getDbType(ds) == DbType.mysql) {
                // 经测试Mysql8 表前缀使用的是第一个参数catalog, 第二个为空参数
                rs = md.getTables(schema, null, tableNamePattern, new String[]{type.toUpperCase()});
            } else {
                rs = md.getTables(null, schema, tableNamePattern, new String[]{type.toUpperCase()});
            }
            while (rs.next()) {
                tables.add(rs.getString("TABLE_NAME"));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            closeResultSet(rs);
        }
        return tables;
    }

    /**
     * 根据当前数据源获取实例内所有库名
     * @param ds
     */
    public static List<String> getAllSchemas(String ds) {
        try {
            DatabaseMetaData metaData = getDatabaseMetaData(ds);
            // ResultSet rs = metaData.getSchemas();
            ResultSet rs = metaData.getCatalogs();
            List<String> dbs = new ArrayList<>();
            while (rs.next()) {
                String db = rs.getString("TABLE_CAT");
                // System.out.println(tableSchem);
                // 排除系统库
                if (!db.endsWith("schema") && !db.equals("mysql") && !db.equals("__recycle_bin__")) {
                    dbs.add(db);
                }
            }
            return dbs;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 根据数据源获取库名schema
     * @param ds
     * @return
     */
    public static String getSchemaPattern(String ds) {
        DbType dbType = EovaDataSource.getDbType(ds);
        String dbName = getDbNameByConfigName(ds);

        String schemaPattern = dbName;
        // mysql dm 默认schama = DB名
        if (dbType == DbType.oracle) {
            // Oracle 按用户名区分
            return getUserNameByConfigName(ds).toUpperCase();
        }

        return schemaPattern;
    }

    public static String getPkName(String ds, String table) {
        ResultSet rs = null;
        try {
            DatabaseMetaData md = getDatabaseMetaData(ds);
            String schema = getSchemaPattern(ds);
            if (EovaDataSource.getDbType(ds) == DbType.mysql) {
                // 经测试Mysql8 表前缀使用的是第一个参数catalog, 第二个为空参数
                rs = md.getPrimaryKeys(schema, null, table);
            } else {
                // oracle dm 均需 schama
                rs = md.getPrimaryKeys(null, schema, table);
            }

            if (rs == null) {
                return null;
            }
            while (rs.next()) {
                return rs.getString("COLUMN_NAME");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            closeResultSet(rs);
        }
        return null;
    }

    /**
     * 自动识别DB类型,进行大小写转换
     * @param ds 数据源
     * @param s 待转换字符串
     * @return
     */
    public static String autoCase(String ds, String s) {
        DbType dbType = EovaDataSource.getDbType(ds);

        if (dbType == DbType.mysql || dbType == DbType.postgresql) {
            return s.toLowerCase();
        }
        if (dbType == DbType.oracle || dbType == DbType.dm) {
            return s.toUpperCase();
        }
        // JdbcConstants.SQL_SERVER;可混写
        return s;
    }

    public static JSONArray getColumnInfoByConfigName(String ds, String tableNamePattern) {
        return getColumnInfoByConfigName(ds, tableNamePattern, false);
    }

    /**
     * 获取数据源中元数据Column信息
     *
     * @param ds 数据源
     * @param tableNamePattern 表名
     * @param isView 是否为视图
     * @return
     */
    public static JSONArray getColumnInfoByConfigName(String ds, String tableNamePattern, boolean isView) {
        // G001 根据DB类型 进行大小写敏感的处理
        tableNamePattern = autoCase(ds, tableNamePattern);

        DbType dbType = EovaDataSource.getDbType(ds);

        JSONArray array = new JSONArray();
        ResultSet rs = null;
        try {
            Properties props = null;
            props = new Properties();
            if (dbType == DbType.mysql) {
                props.setProperty("REMARKS", "true");// 获取注释
                props.setProperty("COLUMN_DEF", "true");// 获取默认值
            }
            DatabaseMetaData md = getDatabaseMetaData(ds, props);
            String tableSchema = getSchemaPattern(ds);
            if (dbType == DbType.mysql) {
                // 经测试Mysql8 表前缀使用的是第一个参数catalog, 第二个为空参数
                rs = md.getColumns(tableSchema, null, tableNamePattern, null);
            } else {
                rs = md.getColumns(null, tableSchema, tableNamePattern, null);
            }
            // 获取列数
            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();
            xx.info(String.format("元字段数量=%s", columnCount));
            // 遍历ResultSet中的每条数据
            while (rs.next()) {
                // System.out.println("Remarks: "+ rs.getObject(12));
                JSONObject json = new JSONObject();
                // 遍历每一列
                for (int i = 1; i <= columnCount; i++) {
                    String columnName = metaData.getColumnLabel(i);
                    // System.out.println(columnName);
                    String value = rs.getString(columnName);
                    json.put(columnName, value);
                }
                array.add(json);
            }

            // Oracle单独获取注释 TODO 元数据暂无，应该换Oracle驱动类中的方法获取
            // 必须在ResultSet迭代完成之后进行其它读库操作, 否则可能导致读取值异常:java.sql.SQLException: 流已被关闭
            if (dbType == DbType.oracle) {
                List<Record> comments = buildOracleComments(ds, tableNamePattern, isView);
                ;
                if (!x.isEmpty(comments)) {
                    for (int i = 0; i < array.size(); i++) {
                        JSONObject col = array.getJSONObject(i);
                        // 必须在内部完成
                        col.put("REMARKS", getOracleRemark(comments, col.getString("COLUMN_NAME")));
                    }
                }
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            closeResultSet(rs);
        }
        return array;
    }

    /**
     * 因为 Oralce 配置参数 DatabaseMetaData 无法获取注释，手工从表中查询字段注释, 一切都是因为Oracle没按JDBC套路出牌
     * @param ds
     * @param tableNamePattern
     * @param isView
     * @return
     */
    private static List<Record> buildOracleComments(String ds, String tableNamePattern, boolean isView) {
        List<Record> comments = new ArrayList<>();
        try {
            // 获取用户名 String userName = DsUtil.getUserNameByConfigName(ds);  TODO DS 已经相当于owner 条件了 废弃掉 待稳定后删除
            if (isView) {
                // 获取视图的SQL
                String viewSql = Db.use(ds).queryStr("select text from user_views where view_name = ?", tableNamePattern);
                if (x.isEmpty(viewSql)) {
                    throw new RuntimeException("视图不存在,请检查视图名称是否正确,Oracle视图必须大写:" + tableNamePattern);
                }
                // 获取视图里的表名
                Set<Name> tableNames = SqlUtil.getTableNames(DbType.oracle, viewSql);
                // 把当前视图所用表的注释都获取到
                for (Name name : tableNames) {
                    String sql = "select column_name,comments from user_col_comments where table_name = ?";
                    comments.addAll(Db.use(ds).find(sql, name.getName()));
                }
            } else {
                // 获取当前表的所有字段的注释
                String sql = "select column_name,comments from user_col_comments where table_name = ?";
                comments = Db.use(ds).find(sql, tableNamePattern);
            }
        } catch (Exception e) {
            LogKit.error("尝试读取Oracle字段注释发生异常", e);
        }
        return comments;
    }

    /**
     * 获取表中文注释
     * @param ds
     * @param table
     * @return
     */
    public static String getTableComment(String ds, String table) {
        DbType dbType = EovaDataSource.getDbType(ds);
        // Oracle和Mysql 获取表名注释方式不一样
        if (dbType == DbType.oracle) {
            return table;
        }
        String tableSchema = getSchemaPattern(ds);
        return Db.queryStr("select TABLE_COMMENT FROM information_schema.TABLES WHERE TABLE_SCHEMA = ? AND TABLE_NAME = ?", tableSchema, table);
    }

    private static void closeConn(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException ignore) {
            }
        }
    }

    private static void closeResultSet(ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException ignore) {
            }
        }
    }

    /**
     * 获取Oracle注释
     * @param comments 表注释集合
     * @param en 字段名
     * @return
     */
    private static String getOracleRemark(List<Record> comments, String en) {
        for (Record o : comments) {
            if (en.equalsIgnoreCase(o.getStr("column_name"))) {
                String s = o.getStr("comments");
                if (x.isEmpty(s)) {
                    return null;
                }
                // System.out.println(s);
                return s;
            }
        }
        return null;
    }
}