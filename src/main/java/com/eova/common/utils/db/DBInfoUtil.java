package com.eova.common.utils.db;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jfinal.plugin.activerecord.Config;
import com.jfinal.plugin.activerecord.DbKit;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Mysql工具类
 *
 * @author Jieven
 * @date 2014-9-12
 */
public class DBInfoUtil {

    /**
     * 获取JDBC URL中的数据库名
     *
     * @param configName
     * @return
     */
    public static String getDbNameByConfigName(String configName) {
        String dbName = null;
        Connection conn = null;
        try {
            Config config = DbKit.getConfig(configName);
            if (config == null) {
                throw new SQLException(configName + " not started");
            }
            conn = config.getDataSource().getConnection();
            dbName = conn.getCatalog();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ignore) {
                }
            }
        }
        return dbName;
    }


    /**
     * 获取JDBC URL中的driverName
     *
     * @param configName
     * @return driverName
     */
    public static List<String> getTableNamesByConfigName(String configName, String type) {
        List<String> tables = new ArrayList<String>();
        ResultSet rs = null;
        Connection conn = null;
        try {
            Config config = DbKit.getConfig(configName);
            if (config == null) {
                throw new SQLException(configName + " not started");
            }
            conn = config.getDataSource().getConnection();
            DatabaseMetaData md = conn.getMetaData();
            rs = md.getTables(null, null, "%", new String[]{type.toUpperCase()});
            while (rs.next()) {
                tables.add(rs.getString("TABLE_NAME"));
            }
            rs.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException ignore) {
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ignore) {
                }
            }
        }
        return tables;
    }

    /**
     * 获取JDBC URL中的driverName
     *
     * @param configName
     * @return driverName
     */
    public static JSONArray getColumnInfoByConfigName(String configName, String catalog, String schemaPattern,
                                                      String tableNamePattern) {
        // json数组
        JSONArray array = new JSONArray();
        ResultSet rs = null;
        Connection conn = null;
        try {
            Config config = DbKit.getConfig(configName);
            if (config == null) {
                throw new SQLException(configName + " not started");
            }
            conn = config.getDataSource().getConnection();
            DatabaseMetaData md = conn.getMetaData();
            rs = md.getColumns(null, null, tableNamePattern, null);
            // 获取列数
            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();
            // 遍历ResultSet中的每条数据
            while (rs.next()) {
                JSONObject jsonObj = new JSONObject();
                // 遍历每一列
                for (int i = 1; i <= columnCount; i++) {
                    String columnName = metaData.getColumnLabel(i);
                    String value = rs.getString(columnName);
                    jsonObj.put(columnName, value);
                }
                array.add(jsonObj);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException ignore) {
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ignore) {
                }
            }
        }
        return array;
    }
}
