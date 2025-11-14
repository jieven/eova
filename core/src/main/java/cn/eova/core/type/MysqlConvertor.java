/**
 * Copyright (c) 2015-2026 EOVA.CN. All rights reserved.
 * Licensed under the LGPL-3.0 license
 * For authorization, please contact: admin@eova.cn
 */
package cn.eova.core.type;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

import cn.eova.model.MetaField;

/**
 * Mysql数据类型转换器
 *
 * @author Jieven
 *
 */

public class MysqlConvertor extends Convertor {

    /**
     * <pre>
     * 参考：http://dev.mysql.com/doc/connector-j/5.1/en/connector-j-reference-type-conversions.html
     * 特殊处理1:
     * DATE TIME DATETIME转为 java.util.Date 保证时间可以达到“秒精度”,而不是使用默认的java.sql.Date
     * 特殊处理2:
     * INT UNSIGNED 这里强制指定为 Integer 因为大部分人不知道应该为Long
     * 特殊处理3:
     * TINYINT类型,如长度=1,自动转换为Boolean
     * </pre>
     */
    private final static Map<String, Class> map = new HashMap<String, Class>() {
        {
            put("BIT", Boolean.class);
            put("TEXT", String.class);
            put("LONGTEXT", String.class);
            put("TINYTEXT", String.class);
            put("JSON", String.class);

            put("DATETIME", java.util.Date.class);
            put("TIMESTAMP", java.sql.Timestamp.class);
            put("DATE", java.util.Date.class);// 特殊处理
            put("TIME", java.sql.Time.class);

            put("TINYINT", Integer.class);
            put("SMALLINT", Integer.class);
            put("MEDIUMINT", Integer.class);
            put("INT", Integer.class);
            put("BIGINT", Long.class);

            put("SMALLINT UNSIGNED", Integer.class);
            put("MEDIUMINT UNSIGNED", Integer.class);
            put("TINYINT UNSIGNED", Integer.class);
            put("INT UNSIGNED", Integer.class);
            put("BIGINT UNSIGNED", BigInteger.class);
            put("DOUBLE UNSIGNED", Double.class);
            put("DECIMAL UNSIGNED", BigDecimal.class);

            put("FLOAT", Float.class);
            put("DOUBLE", Double.class);
            put("DECIMAL", BigDecimal.class);

            put("CHAR", String.class);
            put("VARCHAR", String.class);
            put("BINARY", Byte[].class);
            put("VARBINARY", Byte[].class);
            put("TINYBLOB", Byte[].class);
            put("BLOB", Byte[].class);
            put("MEDIUMBLOB", Byte[].class);
            put("LONGBLOB", Byte[].class);
        }
    };

    @Override
    public Map<String, Class> mapping() {
        return map;
    }

    @Override
    public Class getJavaType(MetaField field) {
        String type = field.getDataTypeName();// DB字段类型
        Integer size = field.getInt("data_size");// DB字段长度

        // Eova Mysql 定制规则 重新定义布尔
        if (type.equalsIgnoreCase("TINYINT") && size == 1) {
            return Boolean.class;
        }

        return super.getJavaType(field);
    }

    @Override
    public Object convert(MetaField field, Object o) {
        if (o == null) {
            return null;
        }

        return rule(o, getJavaType(field));
    }


    @Override
    public Object convertValue(Object o, int type) {
        // TODO Auto-generated method stub
        return null;
    }

}