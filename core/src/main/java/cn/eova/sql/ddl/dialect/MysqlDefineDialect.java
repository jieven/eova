/**
 * Copyright (c) 2015-2026 EOVA.CN. All rights reserved.
 * <p>
 *
 * Licensed under the LGPL-3.0 license
 * Software copyright registration number:2018SR1012969
 * For authorization, please contact: admin@eova.cn
 */
package cn.eova.sql.ddl.dialect;

import cn.eova.tools.x;
import cn.eova.sql.ddl.DefineColumn;
import cn.eova.sql.ddl.DefineTable;

import static cn.eova.sql.ddl.dialect.EovaType.BOOL;
import static cn.eova.sql.ddl.dialect.EovaType.CHAR;
import static cn.eova.sql.ddl.dialect.EovaType.DATE;
import static cn.eova.sql.ddl.dialect.EovaType.DATETIME;
import static cn.eova.sql.ddl.dialect.EovaType.NUMBER;
import static cn.eova.sql.ddl.dialect.EovaType.TIME;
import static cn.eova.sql.ddl.dialect.EovaType.VARCHAR;

public class MysqlDefineDialect extends DefineDialect {

    @Override
    public String escape(String name) {
        return '`' + name + '`';
    }

    @Override
    public EovaType toEovaType(String dbType, int size) {
        if (dbType.contains("INT")) {
            return NUMBER;
        } else if (dbType.equals("FLOAT")) {
            return NUMBER;
        } else if (dbType.equals("DOUBLE")) {
            return NUMBER;
        } else if (dbType.equals("DECIMAL")) {
            return NUMBER;
        } else if (dbType.equals("BIT")) {
            return BOOL;
        } else if (dbType.equals("DATE")) {
            return DATE;
        } else if (dbType.equals("DATETIME")) {
            return DATETIME;
        } else if (dbType.equals("TIMESTAMP")) {
            return TIME;
        } else if (dbType.equals("CHAR")) {
            return CHAR;
        }
        // 默认都是VARCHAR
        return VARCHAR;
    }

    @Override
    public String toDbType(EovaType eovaType, int size, int decimal) {
        switch (eovaType) {
            case BOOL:
                return "TINYINT(1)";
            case NUMBER:
                return numberType(size, decimal);
            case CHAR:
                return String.format("CHAR(%s)", size);
            case VARCHAR:
                // 长字段用TEXT避免错误
                if (size > 2000) {
                    return "TEXT";
                }
                return String.format("VARCHAR(%s)", size);
            case DATE:
                return "DATE";
            case TIME:// 可享受默认值的便利
                return "TIMESTAMP";
        }
        return null;
    }

    private String numberType(int size, int decimal) {
        // 整数
        if (decimal == 0) {
            if (size <= 4)
                return String.format("TINYINT(%s)", size);
            else if (size <= 11)// 此处按照使用习惯优先，大多数人的认知是int <= 10位
                return String.format("INT(%s)", size);
            else
                return String.format("BIGINT(%s)", size);
        }
        // 小数
        else {
            if (size + decimal <= 8)
                return String.format("FLOAT(%s,%s)", size, decimal);
            if (size + decimal <= 16)
                return String.format("DOUBLE(%s,%s)", size, decimal);
            return String.format("DECIMAL(%s,%s)", size, decimal);
        }
    }

    @Override
    public String create(DefineTable tables) {
        StringBuilder sb = new StringBuilder();

        // + System.currentTimeMillis()
        sb.append(String.format("CREATE TABLE %s (\n", escape(tables.getEn())));
        for (DefineColumn o : tables.getFields()) {
            sb.append(buildColumn(o)).append(",\n");
        }
        if (!x.isEmpty(tables.getPk())) {
            sb.append(String.format(" PRIMARY KEY (%s)\n", escape(tables.getPk())));
        }
        sb.append(String.format(") COMMENT='%s';", tables.getCn()));
        sb.append("\n");

        return sb.toString();
    }

    @Override
    public String rename(String tableName, String newTableName) {
        return String.format("ALTER TABLE %s RENAME %s;", escape(tableName), escape(newTableName));
    }

    @Override
    public String addColumn(String tableName, DefineColumn o) {
        return String.format("ALTER TABLE %s ADD COLUMN %s;", escape(tableName), buildColumn(o));
    }

    @Override
    public String updateColumn(String tableName, DefineColumn o) {
        return String.format("ALTER TABLE %s CHANGE COLUMN %s;", escape(tableName), buildColumn(o));
    }

    @Override
    public String updateColumn(String tableName, String oldColumnName, String newColumnName) {
        return null;
    }

    /**
     * 构建字段语法
     * @param o
     * @return
     */
    private String buildColumn(DefineColumn o) {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format(" %s", escape(o.getEn())));
        sb.append(" ").append(toDbType(o.getType(), o.getSize(), o.getDecimal()));
        sb.append(o.isNull() ? "" : " NOT NULL");
        sb.append(o.getDefault() != null ? String.format(" DEFAULT %s", formatDefault(o)) : "");
        sb.append(o.isAuto() ? " AUTO_INCREMENT" : "");
        sb.append(String.format(" COMMENT '%s'", o.getCn()));
        return sb.toString();
    }


}
