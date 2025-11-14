/**
 * Copyright (c) 2015-2026 EOVA.CN. All rights reserved.
 * Licensed under the LGPL-3.0 license
 * For authorization, please contact: admin@eova.cn
 */
package cn.eova.ext.jfinal;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.eova.common.utils.util.RegexUtil;
import cn.eova.config.EovaConfig;
import com.jfinal.plugin.activerecord.CPI;
import com.jfinal.plugin.activerecord.Config;
import com.jfinal.plugin.activerecord.ModelBuilder;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.activerecord.RecordBuilder;

/**
 * Oracle专用Record构建器
 */
public class OracleRecordBuilder extends RecordBuilder {

    public static final OracleRecordBuilder me = new OracleRecordBuilder();

    public List<Record> build(Config config, ResultSet rs) throws SQLException {
        List<Record> result = new ArrayList<Record>();
        ResultSetMetaData rsmd = rs.getMetaData();
        int columnCount = rsmd.getColumnCount();
        String[] labelNames = new String[columnCount + 1];
        int[] types = new int[columnCount + 1];
        buildLabelNamesAndTypes(rsmd, labelNames, types);
        while (rs.next()) {
            Record record = new Record();
            CPI.setColumnsMap(record, config.getContainerFactory().getColumnsMap());
            Map<String, Object> columns = record.getColumns();

            // 重新构建获得精准类型值
            buildValue(config.getName(), rs, rsmd, columnCount, labelNames, types, columns);

            result.add(record);
        }
        return result;
    }

    /**
     * 通过对长度和精度的判断, 获得精准类型
     * @param rs
     * @param rsmd
     * @param columnCount
     * @param labelNames
     * @param types
     * @param columns
     * @throws SQLException
     */
    public static void buildValue(String ds, ResultSet rs, ResultSetMetaData rsmd, int columnCount, String[] labelNames, int[] types, Map<String, Object> columns) throws SQLException {
        for (int i = 1; i <= columnCount; i++) {
            Object value = rs.getObject(i);
            if (value == null) {
                continue;
            }

            if (types[i] == Types.NUMERIC) {
                int p = rsmd.getPrecision(i);// 整数位
                int s = rsmd.getScale(i);// 小数

                // 聚合函数获取数据，Precision=0，Scale=0，会出现丢失精度问题, 所以需要通过字符串值获取精度
                String val = value.toString();
                if (RegexUtil.isTrue("^\\d+\\.\\d+$", val)) {
                    String[] ss = val.split("\\.");
                    p = ss[0].length();
                    s = ss[1].length();
                }

                if (s == 0) {
                    if (p <= 10) {
                        value = rs.getInt(i);
                    } else if (p <= 18) {
                        value = rs.getLong(i);
                    } else {
                        value = rs.getBigDecimal(i);
                    }
                } else {
                    if (p + s <= 8) {
                        value = rs.getFloat(i);
                    } else if (p + s <= 16) {
                        value = rs.getDouble(i);
                    } else {
                        value = rs.getBigDecimal(i);
                    }
                }
            } else {
                if (types[i] == Types.TIMESTAMP) {// Oracle Date dbtype=93=timestamp
                    // 只能在前台根据控件做格式化显示
                    value = rs.getTimestamp(i);
                } else if (types[i] == Types.DATE) {
                    value = rs.getDate(i);
                } else if (types[i] == Types.CLOB) {
                    value = ModelBuilder.me.handleClob(rs.getClob(i));
                } else if (types[i] == Types.NCLOB) {
                    value = ModelBuilder.me.handleClob(rs.getNClob(i));
                } else if (types[i] == Types.BLOB) {
                    value = ModelBuilder.me.handleBlob(rs.getBlob(i));
                } else {
                    // Eova Oracle 特殊处理
                    value = EovaConfig.getConvertor(ds).convertValue(rs.getObject(i), types[i]);
                }
            }

            columns.put(labelNames[i], value);
        }
    }
}