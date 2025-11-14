/**
 * Copyright (c) 2015-2026 EOVA.CN. All rights reserved.
 * Licensed under the LGPL-3.0 license
 * For authorization, please contact: admin@eova.cn
 */
package cn.eova.common.utils.db;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import cn.eova.tools.x;
import com.alibaba.druid.DbType;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import cn.eova.common.Ds;
import cn.eova.common.utils.io.TxtUtil;
import cn.eova.common.utils.xx;
import cn.eova.config.EovaDataSource;
import cn.eova.sql.DbDialect;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

public class DbUtil {

    private static String mysql2OracleType(JSONObject o) {
        // 字段类型(大写)
        String type = o.getString("TYPE_NAME").toUpperCase();
        // 字段长度
        int size = o.getIntValue("COLUMN_SIZE");
        int decimal = o.getIntValue("DECIMAL_DIGITS");
        // 纠错
        if (size == 0) {
            size = 1;
        } else if (size > 4000) {
            size = 4000;
        }

        String dataType = null;

        if (type.contains("INT")) {
            dataType = String.format("NUMBER(%d)", size - 1);// 获取最兼容的长度
        } else if (type.equals("FLOAT")) {
            dataType = String.format("NUMBER(%d,%d)", size, decimal);
        } else if (type.equals("DOUBLE")) {
            dataType = String.format("NUMBER(%d,%d)", size, decimal);
        } else if (type.equals("DECIMAL")) {
            dataType = String.format("NUMBER(%d,%d)", size, decimal);
        } else if (type.equals("BIT")) {
            dataType = "CHAR(1)";
        } else if (type.equals("CHAR")) {
            dataType = String.format("CHAR(%d)", size);
        } else if (type.indexOf("DATE") != -1) {
            dataType = "DATE";
        } else if (type.indexOf("TIME") != -1) {
            dataType = "TIMESTAMP(6)";
        } else {
            dataType = String.format("VARCHAR2(%d)", size);
        }

        return dataType;
    }

    private static String mysql2PgsqlType(JSONObject o) {
        // 字段类型(大写)
        String type = o.getString("TYPE_NAME").toUpperCase();
        // 字段长度
        int size = o.getIntValue("COLUMN_SIZE");
        int decimal = o.getIntValue("DECIMAL_DIGITS");

        // 纠错
        if (size == 0) {
            size = 1;
        } else if (size > 4000) {
            size = 4000;
        }

        String dataType = null;

        if (type.contains("INT") && size <= 2) {
            dataType = "INT2";// smallint	2 字节	小范围整数	-32768 到 +32767
        } else if (type.contains("INT") && size <= 11) {// Mysql int(11)
            dataType = "INT4";// integer	4 字节	常用的整数	-2147483648 到 +2147483647
        } else if (type.contains("INT")) {
            dataType = "INT8";// bigint	8 字节	大范围的整数	-9223372036854775808 到 9223372036854775807
        } else if (type.equals("FLOAT")) {
            dataType = "FLOAT4";
        } else if (type.equals("DOUBLE")) {
            dataType = "FLOAT8";
        } else if (type.equals("DECIMAL")) {
            dataType = String.format("DECIMAL(%d,%d)", size, decimal);
        } else if (type.equals("BIT")) {
            dataType = "INT2";
        } else if (type.indexOf("DATE") != -1) {
            dataType = "DATE";
        } else if (type.indexOf("TIME") != -1) {
            dataType = "TIMESTAMP";
        } else {
            dataType = String.format("VARCHAR(%d)", size);
        }

        return dataType;
    }

    public static StringBuilder createOracleSql(String ds, String tableNamePattern) {

        StringBuilder dropTable = new StringBuilder();
        StringBuilder dropSeq = new StringBuilder();
        StringBuilder createSeq = new StringBuilder();
        StringBuilder createTable = new StringBuilder();

        List<String> tables = DsUtil.getTableNamesByConfigName(ds, DsUtil.TABLE, tableNamePattern);

        for (String table : tables) {

            String pk = DsUtil.getPkName(ds, table);

            // 获取表中最大值
            String sql = "select max(" + pk + ") from " + table;
            Object val = Db.use(ds).queryColumn(sql);

            dropTable.append(String.format("drop table %s;\n", table));

            // 如果不是数字说明是UUID,不生成序列
            if (x.isNum(val)) {
                long max = xx.toLong(val);
                //@formatter:off
				dropSeq.append(		String.format("drop sequence seq_%s;\n", table));
				createSeq.append(	String.format("create sequence seq_%s increment by 1 start with %s maxvalue 9999999999;\n", table, max + 1));
				//@formatter:on
            }

            JSONArray list = DsUtil.getColumnInfoByConfigName(ds, table);

            StringBuilder sbTable = new StringBuilder();
            StringBuilder sbRemarks = new StringBuilder();
            StringBuilder sbDefault = new StringBuilder();

            sbTable.append("create table " + table);
            sbTable.append("(\n");

            for (int i = 0; i < list.size(); i++) {
                JSONObject o = list.getJSONObject(i);

                Record re = new Record();
                re.set("en", o.getString("COLUMN_NAME"));
                re.set("cn", o.getString("REMARKS"));
                re.set("num", o.getIntValue("ORDINAL_POSITION"));
                re.set("is_required", "YES".equalsIgnoreCase(o.getString("IS_NULLABLE")) ? false : true);

                // 是否自增
                boolean isAuto = "YES".equalsIgnoreCase(o.getString("IS_AUTOINCREMENT")) ? true : false;
                re.set("is_auto", isAuto);

                // 默认值
                String def = o.getString("COLUMN_DEF");
                re.set("defaulter", def);

                String dataType = mysql2OracleType(o);
                // Mysql类型->Oracle类型
                sbTable.append(String.format("    %s %s", re.getStr("en"), dataType));
                if (re.getBoolean("is_required")) {
                    sbTable.append(" NOT NULL");
                }
                sbTable.append(",\n");

                // create remarks
                String remarks = o.getString("REMARKS");
                if (!x.isEmpty(remarks)) {
                    sbRemarks.append(String.format("comment on column %s.%s is '%s';\n", table, re.getStr("en"), remarks));
                }

                // add default
                {
                    if (!x.isEmpty(def)) {
                        String str = "alter table %s modify %s default %s;\n";
                        if (def.equals("CURRENT_TIMESTAMP")) {
                            sbDefault.append(String.format(str, table, re.getStr("en"), "sysdate"));
                        } else {
                            if (!dataType.contains("NUMBER")) {
                                def = x.str.format(def);
                            }
                            sbDefault.append(String.format(str, table, re.getStr("en"), def));
                        }
                    }

                }

            }
            sbTable.delete(sbTable.length() - 2, sbTable.length() - 1);
            sbTable.append(");\n");

            // 导入视图默认第一列为主键
            String pkName = DsUtil.getPkName(ds, table);
            if (!x.isEmpty(pkName)) {
                String str = "\nalter table %s add constraint pk_%s primary key(%s);\n";
                sbRemarks.insert(0, String.format(str, table, table, pkName));
            }

            createTable.append(sbTable);
            createTable.append(sbRemarks);
            createTable.append(sbDefault);
            createTable.append("\n");
        }

        return dropTable.append("\n\n").append(dropSeq).append("\n\n").append(createSeq).append("\n\n").append(createTable);
    }

    public static StringBuilder createPGSql(String ds, String tableNamePattern) {

        StringBuilder dropTable = new StringBuilder();
        StringBuilder dropSeq = new StringBuilder();
        StringBuilder createSeq = new StringBuilder();
        StringBuilder createTable = new StringBuilder();

        List<String> tables = DsUtil.getTableNamesByConfigName(ds, DsUtil.TABLE, tableNamePattern);

        for (String table : tables) {

            String pk = DsUtil.getPkName(ds, table);

            // 获取表中最大值
            String sql = "select max(" + pk + ") from " + table;
            Object val = Db.use(ds).queryColumn(sql);

            dropTable.append(String.format("drop table %s;\n", table));

            // 如果不是数字说明是UUID,不生成序列
            if (x.isNum(val)) {
                long max = xx.toLong(val);
                //@formatter:off
				dropSeq.append(		String.format("drop sequence seq_%s;\n", table));
				createSeq.append(	String.format("create sequence seq_%s increment by 1 minvalue 1 no maxvalue start with %s;\n", table, max + 1));
				//@formatter:on
            }

            JSONArray list = DsUtil.getColumnInfoByConfigName(ds, table);

            StringBuilder sbTable = new StringBuilder();
            StringBuilder sbRemarks = new StringBuilder();
            StringBuilder sbDefault = new StringBuilder();

            sbTable.append("create table " + table);
            sbTable.append("(\n");

            for (int i = 0; i < list.size(); i++) {
                JSONObject o = list.getJSONObject(i);

                Record re = new Record();
                re.set("en", o.getString("COLUMN_NAME"));
                re.set("cn", o.getString("REMARKS"));
                re.set("num", o.getIntValue("ORDINAL_POSITION"));
                re.set("is_required", "YES".equalsIgnoreCase(o.getString("IS_NULLABLE")) ? false : true);

                // 是否自增
                boolean isAuto = "YES".equalsIgnoreCase(o.getString("IS_AUTOINCREMENT")) ? true : false;
                re.set("is_auto", isAuto);

                // 默认值
                String def = o.getString("COLUMN_DEF");
                re.set("defaulter", def);

                String dataType = mysql2PgsqlType(o);
                String en = re.getStr("en");

                // Mysql类型->Oracle类型
                sbTable.append(String.format("    \"%s\" %s", en, dataType));
                if (re.getBoolean("is_required")) {
                    sbTable.append(" NOT NULL");
                }

                // 默认序列
                if (isAuto) {
                    def = String.format("nextval('seq_%s'::regclass)", table);
                }
                // 无默认值
                else if (def == null) {
                    def = "NULL";
                }
                // 默认时间
                else if (def.equals("CURRENT_TIMESTAMP")) {
                    def = "now()";
                }
                // 默认字符串
                else if (!x.isNum(def)) {
                    def = x.str.format(def);
                }
                sbTable.append(" DEFAULT " + def);

                sbTable.append(",\n");

                // create remarks
                String remarks = o.getString("REMARKS");
                if (!x.isEmpty(remarks)) {
                    sbRemarks.append(String.format("COMMENT ON COLUMN \"%s\".\"%s\" IS '%s';\n", table, re.getStr("en"), remarks));
                }
            }
            sbTable.delete(sbTable.length() - 2, sbTable.length() - 1);
            sbTable.append(");\n");

            // 主键
            String pkName = DsUtil.getPkName(ds, table);
            if (!x.isEmpty(pkName)) {
                String str = "\nALTER TABLE \"%s\" ADD CONSTRAINT \"%s_pkey\" PRIMARY KEY (\"%s\");\n";
                sbRemarks.insert(0, String.format(str, table, table, pkName));
            }

            createTable.append(sbTable);
            createTable.append(sbRemarks);
            createTable.append(sbDefault);
            createTable.append("\n");
        }

        return dropTable.append("\n\n").append(dropSeq).append("\n\n").append(createSeq).append("\n\n").append(createTable);
    }

    /**
     * 格式化Oracle Date
     * @param value
     * @return
     */
    public static String buildDateValue(Object value) {
        return "to_date('" + value + "','yyyy-mm-dd HH24:MI:SS')";
    }

    /**
     * 将数据变成Mysql插入脚本
     * @param list 待生成数据集
     * @param table 表名
     * @param auto 自增列
     * @param sb
     */
    public static void generateSql(String ds, String table, List<Record> list, String auto, StringBuilder sb) {
        String EP = DbDialect.getEscape(EovaDataSource.getDbType(ds));

        Set<String> updatePid = new HashSet<>();
        for (Record r : list) {
            appendSql(EP, ds, table, auto, sb, r);
            if (table.equals("eova_menu")) {
                Integer pid = r.getInt("parent_id");
                if (pid == 0) {
                    continue;
                }
                // 查找当前父的Code
                String findPCode = "select code from eova_menu where id = ?";
                String pcode = Db.use(Ds.EOVA).queryStr(findPCode, pid);
                // 迁移后自动按Code 更新关系
                //				String sql = String.format("SET @pid := 3;SELECT id INTO @pid FROM eova_menu b WHERE b.CODE = '%s';UPDATE eova_menu m SET m.parent_id = @pid WHERE m.CODE = '%s'", pcode,
                //						r.getStr("code"));
                String sql = String.format("UPDATE eova_menu m SET m.parent_id = (select t.id from (SELECT id FROM eova_menu b WHERE b.CODE = '%s') t) WHERE m.CODE = '%s'", pcode, r.getStr("code"));
                if (xx.isOracle()) {
                    sql = String.format("UPDATE EOVA_MENU m SET m.parent_id = ( SELECT id FROM EOVA_MENU b WHERE b.CODE = '%s' ) WHERE  m.code = '%s'", pcode, r.getStr("code"));
                }
                updatePid.add(sql);
            }
        }

        sb.append("\n");

        for (String s : updatePid) {
            sb.append(s);
            sb.append(";\n");
        }
    }

    /**
     * 生成通用SQL
     * @param ds
     * @param table
     * @param list
     */
    public static StringBuilder dataToSql(String ds, String table, List<Record> list) {
        String EP = DbDialect.getEscape(EovaDataSource.getDbType(ds));

        StringBuilder sb = new StringBuilder();
        for (Record r : list) {
            appendSql(EP, ds, table, null, sb, r);
        }
        sb.append("\n");

        return sb;
    }

    private static void appendSql(String EP, String ds, String table, String auto, StringBuilder sb, Record r) {

        sb.append("INSERT INTO " + table + " (");
        String[] names = r.getColumnNames();
        for (String n : names) {
            if (EovaDataSource.getDbType(ds) == DbType.mysql && n.equals(auto)) {
                // mysql 自增, 无需添加
                continue;
            }
            sb.append(String.format("%s%s%s", EP, n, EP)).append(',');
        }
        sb.deleteCharAt(sb.length() - 1);
        sb.append(")  VALUES (");
        for (String n : names) {
            if (n.equals(auto)) {
                if (EovaDataSource.getDbType(ds) == DbType.oracle) {
                    sb.append(String.format("seq_%s.nextval,", table));
                }
            } else {
                sb.append(formatVal(r.get(n))).append(',');
            }
        }
        sb.deleteCharAt(sb.length() - 1);
        sb.append(");");
        sb.append("\n");
    }

    public static String format(DbType type, String name) {
        if (type == DbType.mysql) {
            return '`' + name + '`';
        }
        return name;
    }

    private static Object formatVal(Object o) {
        if (o == null) {
            return o;
        }
        String s = o.toString();
        if (s.equals("true")) {
            return 1;
        }
        if (s.equals("false")) {
            return 0;
        }
        // ps:regex 4*\ = java 2*\
        if (s.indexOf("\\") != -1) {
            // 自动转义 富文本中JS转义符(比如js代码)
            s = s.replaceAll("\\\\", "\\\\\\\\");
        }
        if (s.indexOf("'") != -1) {
            // 自动转义引号
            s = s.replaceAll("'", "\\\\'");
        }
        return x.str.format(s);
    }

    /**
     * SQL表名比较,自动忽略符号,空格
     * @param full 全称 db.table.id
     * @param tableName 简称 db.table
     * @return
     */
    public static boolean compareTable(String full, String tableName) {
        full = full.replace("`", "").replace(" ", "").toLowerCase();
        tableName = tableName.replace("`", "").replace(" ", "").toLowerCase();

        // 格式:table vs table
        if (full.equals(tableName)) {
            return true;
        }

        String[] ss = full.split("\\.");
        // 格式:table.filed vs table
        if (ss.length == 2) {
            return ss[0].equals(tableName);
        }
        // 格式:db.table.filed vs db.table
        if (ss.length == 3) {
            return (ss[0] + "." + ss[1]).equals(tableName);
        }

        return false;
    }

    /**
     * SQL字段比较,自动忽略符号,空格
     * @param full 全称 db.table.filed
     * @param fieldName 字段名
     * @return
     */
    public static boolean compareField(String full, String fieldName) {
        full = full.replace("`", "").replace(" ", "").toLowerCase();
        fieldName = fieldName.replace("`", "").replace(" ", "").toLowerCase();

        // 格式:filed vs filed
        if (full.equals(fieldName)) {
            return true;
        }

        String[] ss = full.split("\\.");
        // 格式:table.filed vs filed
        if (ss.length == 2) {
            return ss[1].equals(fieldName);
        }
        // 格式:db.table.filed vs table.filed
        if (ss.length == 3) {
            return (ss[1] + "." + ss[2]).equals(fieldName) || ss[2].equals(fieldName);
        }

        return false;
    }

    public static void main(String[] args) {
        //		{
        //			boolean s = compare("`demo`.`hotel_stock`.`hotel_id`", "demo.hotel");
        //			System.out.println(s);
        //		}
        {
            boolean s = compareTable("`demo`.`hotel`.`id`", "demo.hotel");
            System.out.println(s);
        }
    }

    /**
     * 获取字段名
     * 例：`hotel`.`id`
     * 值：id
     * @param full 库名.表名.字段名
     * @return
     */
    public static String getEndName(String full) {
        full = full.replace("`", "").replace(" ", "");

        if (full.contains(".")) {
            String[] ss = full.split("\\.");
            return ss[ss.length - 1];
        }
        return full;
    }

    public static String simplify(String full) {
        full = full.replace("`", "").replace(" ", "");
        return null;
    }

    /**
     * 优先使用 loadSqls
     * @param sqlFilePath SQL脚本文件
     * @return List<sql> 返回所有 SQL 语句的 List
     * @throws Exception
     */
    @Deprecated
    public static List<String> loadSql(String sqlFilePath) {
        List<String> sqlList = new ArrayList<String>();

        try {
            String txt = TxtUtil.getTxt(sqlFilePath);

            String[] sqls = txt.split("\r|\n");
            for (String sql : sqls) {
                // 屏蔽空行和注释行.replaceAll("--.*", "")
                if (x.isEmpty(sql) || sql.startsWith("--")) {
                    continue;
                }
                sqlList.add(sql.trim());
            }
            return sqlList;
        } catch (Exception e) {
            xx.info("加载文件[%s]异常: + %s", sqlFilePath, e.getMessage());
        }
        return null;
    }

    /**
     * 读取 SQL 文件，获取 SQL 语句
     * @param sqlFilePath SQL脚本文件
     * @return String[]] 返回所有SQL语句
     * @throws Exception
     */
    public static String[] loadSqls(String sqlFilePath) {
        try {
            String txt = TxtUtil.getTxt(sqlFilePath);

            StringBuilder sb = new StringBuilder();
            // 统一换行符
            txt = txt.replace("\r\n", "\n");
            // 按行切割
            String[] ts = txt.split("\n");
            // 去掉注释行
            for (String s : ts) {
                if (x.isEmpty(s) || s.startsWith("--")) {
                    sb.append(" ");// 去掉注释行替换成空格
                    continue;
                }
                // 换行符替换为空格(不再保持换行格式)
                sb.append(s).append(" ");
            }

            // 多空格替换为一个空格 去首位空格
            String t = sb.toString().replaceAll("\\s+", " ").replace("; ", ";");
            return t.split(";");
        } catch (Exception e) {
            xx.info("加载文件[%s]异常: + %s", sqlFilePath, e.getMessage());
        }
        return null;
    }

    /**
     * 是否存在
     *
     * @param sql
     * @param paras
     * @return
     */
    public boolean isExist(String ds, String sql, Object... paras) {
        Long count = Db.use(ds).queryLong(sql, paras);
        return count != null && count != 0;
    }
}