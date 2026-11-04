/**
 * Copyright (c) 2015-2026 EOVA.CN. All rights reserved.
 * Licensed under the LGPL-3.0 license
 * For authorization, please contact: admin@eova.cn
 */
package cn.eova.core.meta;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import cn.eova.common.Ds;
import cn.eova.common.utils.db.DbUtil;
import cn.eova.common.utils.db.DsUtil;
import cn.eova.common.utils.xx;
import cn.eova.model.MetaField;
import cn.eova.model.MetaObject;
import cn.eova.tools.x;
import com.jfinal.kit.Kv;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

public class MetaUtil {

    /**
     * 转换数据类型
     *
     * @param typeName DB数据类型
     * @return
     */
    public static String getDataType(String typeName) {
        typeName = typeName.toLowerCase();
        if (typeName.contains("int") || typeName.contains("bit") || typeName.equals("number") || typeName.equals("double") || typeName.equals("float")) {
            return MetaField.DATATYPE_NUMBER;
        } else if (typeName.contains("time") || typeName.contains("date")) {
            return MetaField.DATATYPE_TIME;
        } else {
            return MetaField.DATATYPE_STRING;
        }
    }

    /**
     * 获取表单类型
     *
     * @param isAuto 是否自增
     * @param typeName 类型
     * @param size 长度
     * @return
     */
    public static String getFormType(boolean isAuto, String typeName, int size) {
        typeName = typeName.toLowerCase();
        if (typeName.contains("time") || typeName.contains("date")) {
            return MetaField.TYPE_TIME;
        } else if (size > 255) {
            return MetaField.TYPE_TEXTS;
        } else if (size > 500) {
            return MetaField.TYPE_EDIT;
        } else if (size == 1 && (typeName.equals("tinyint") || typeName.equals("char"))) {
            return MetaField.TYPE_BOOL;
        } else {
            // 默认都是文本框
            return MetaField.TYPE_TEXT;
        }
    }

    /**
     * 添加虚拟元对象
     * @param sql 根据 SQL select 自动构建虚拟元对象
     * @param code 菜单编码
     * @param name 菜单名称
     * @param ds SQL执行数据源
     */
    public static void addVirtualObject(String sql, String code, String name, String ds) throws Exception {
        int i1 = sql.toLowerCase().indexOf("select") + 6;
        int i2 = sql.toLowerCase().indexOf("from");

        if (i1 == -1) {
            throw new Exception("缺少select关键字, 请检查SQL语句");
        }
        if (i2 == -1) {
            throw new Exception("缺少from关键字, 请检查SQL语句");
        }

        code = "v_" + code;

        // 根据表达式手工构建Eova_Object
        MetaObject eo = MetaObject.dao.getTemplate();
        eo.remove("id");
        eo.set("code", code);
        eo.set("name", name);
        eo.set("data_source", ds);
        eo.set("table_name", "virtual");
        eo.set("is_first_load", 0);
        eo.set("view_sql", sql);
        eo.save();

        // 根据表达式手工构建Eova_Item
        String select = sql.trim().toLowerCase().substring(i1, i2);
        String[] ss = select.split(",");
//		if (ss.length < 2) {
//		    throw new Exception("自定义查询最少要有2列");
//		}
        int i = 10;
        for (String s : ss) {
            // num1, num2
            String item = s.trim();
            //  (sum1-sum2) total
            if (item.indexOf(" ") != -1) {
                // 取最后一项
                String[] items = item.split(" ");
                item = items[items.length - 1];
            }

            MetaField ei = MetaField.dao.getTemplate();
            ei.remove("id");
            ei.put("num", i);
            ei.put("en", item);
            ei.put("cn", item);
            ei.put("type", "文本框");
            ei.put("is_show", 1);
            ei.put("width", 100);
            ei.set("object_code", code);
            ei.save();

            i++;
        }

    }


    /**
     * 删除用户个性化设置
     * @param objectCode 目标元对象
     * @param fields 允许个性化的字段
     * @param diyField 当前个性化字段
     */
    public static void removeUserDiy(String objectCode, List<Record> fields, List<String> diyField, Integer uid) {
        x.log.info("删除用户个性化设置:" + objectCode);
        // 删除旧字段
        for (String s : diyField) {
            boolean flag = false;
            for (Record f : fields) {
                if (s.equals(f.getStr("en"))) {
                    flag = true;
                    break;
                }
            }
            if (!flag) {
                String sql = "delete from eova_diy where object_code = ? and en = ?";
                if (uid != null) {
                    sql += " and uid = " + uid;
                }
                Db.use(Ds.EOVA).delete(sql, objectCode, s);
            }
        }
    }

    /**
     * 删除角色个性化设置
     * @param object 元对象
     * @param fields 元字段
     * @param roles 角色IDs
     * @param type 字段授权策略
     */
    public static void removeRoleDiy(String object, String fields, String roles, int type) {
        if (x.isOneEmpty(object, fields)) {
            return;
        }

        x.log.info("删除角色个性化设置:" + object);
        for (String field : fields.split(",")) {
            String sql = "delete from eova_diy where object_code = ? and en = ?";

            String userDs = x.conf.get("login.user.ds", Ds.EOVA);
            String userTable = x.conf.get("login.user.table", "eova_user");
            String userRid = x.conf.get("login.user.rid", "rid");

            List<String> uids = Db.use(userDs).query(String.format("select id from %s where %s in (%s)", userTable, userRid, roles));
            // 角色黑名单 当前角色全移除
            if (type == 2) {
                // 如果没有对应用户, 跳过当前字段
                if (x.isEmpty(uids)) {
                    return;
                }

                sql += String.format(" and uid in (%s)", xx.join(uids));
            }
            // 角色白名单 其他角色全移除
            else if (type == 1) {
                // 如果没有对应用户, 该字段全部清理(无需条件)
                if (!x.isEmpty(uids)) {
                    sql += String.format(" and uid not in (%s)", xx.join(uids));
                }
            }

            Db.use(Ds.EOVA).delete(sql, object, field);
        }
    }

    // ---------------- 元数据待上线 / 版本比对 ----------------

    /** 纳入待上线管理的表定义 */
    private static final List<Kv> META_TABLES = new ArrayList<>();

    static {
        META_TABLES.add(Kv.by("table", "eova_object").set("label", "元对象").set("ds", Ds.EOVA)
                .set("cols", "id, code, name, updated"));
        META_TABLES.add(Kv.by("table", "eova_field").set("label", "元字段").set("ds", Ds.EOVA)
                .set("cols", "id, object_code, en, cn, updated"));
        META_TABLES.add(Kv.by("table", "eova_field_diy").set("label", "元字段个性化").set("ds", Ds.EOVA)
                .set("cols", "id, object_code, mode, en, cn, updated"));
        META_TABLES.add(Kv.by("table", "eova_option").set("label", "表达式").set("ds", Ds.EOVA)
                .set("cols", "id, code, name, updated"));
        META_TABLES.add(Kv.by("table", "eova_menu").set("label", "菜单").set("ds", Ds.EOVA)
                .set("cols", "id, code, name, updated"));
        META_TABLES.add(Kv.by("table", "eova_button").set("label", "按钮").set("ds", Ds.EOVA)
                .set("cols", "id, menu_code, name, event, updated"));
        META_TABLES.add(Kv.by("table", "dicts").set("label", "业务字典").set("ds", Ds.MAIN)
                .set("cols", "id, value, name, object, field, updated"));
    }

    /**
     * 查询待上线元数据（按表分组）
     * <pre>
     * 先取 eova_version 最后一条 created，各表统一用 业务.updated &gt; created 判定待导出
     * 无版本记录时视为全部待导出；期初基线由手工写入 eova_version
     * </pre>
     *
     * @return [{table, label, rows:[{id,key,name}]}]
     */
    public static List<Kv> findUpdatedMeta() {
        Date lastCreated = queryLastVersionCreated();
        return buildMetaGroups(queryUpdatedRows(null, lastCreated));
    }

    /**
     * 按版本时间窗回看历史：version.updated &lt; 业务.updated &lt;= version.created
     *
     * @param start 对应版本 updated（时间窗起点）
     * @param end   对应版本 created（时间窗终点）
     */
    public static List<Kv> findUpdatedMeta(Date start, Date end) {
        if (start == null || end == null) {
            return findUpdatedMeta();
        }
        return buildMetaGroups(queryUpdatedRows(start, end));
    }

    /** 最近 N 个有效版本（默认 5） */
    public static List<Kv> listVersions(int limit) {
        if (limit <= 0) {
            limit = 5;
        }
        List<Record> list = Db.use(Ds.EOVA).find(
                "SELECT ver, updated, created FROM eova_version WHERE status = 1 ORDER BY id DESC LIMIT ?",
                limit);
        List<Kv> result = new ArrayList<>();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (Record r : list) {
            Date start = r.getDate("updated");
            Date end = r.getDate("created");
            // 前端沿用 start/end 字段名表示时间窗
            result.add(Kv.by("ver", r.getStr("ver"))
                    .set("start", start == null ? null : df.format(start))
                    .set("end", end == null ? null : df.format(end)));
        }
        return result;
    }

    /** 最后一次有效导出的 created（时间窗终点） */
    public static Date queryLastVersionCreated() {
        return Db.use(Ds.EOVA).queryDate(
                "SELECT created FROM eova_version WHERE status = 1 ORDER BY id DESC LIMIT 1");
    }

    private static List<Kv> buildMetaGroups(Map<String, List<Record>> byTable) {
        List<Kv> groups = new ArrayList<>();
        for (Kv def : META_TABLES) {
            String table = def.getStr("table");
            List<Record> list = byTable.getOrDefault(table, new ArrayList<>());
            List<Kv> rows = new ArrayList<>();
            for (Record r : list) {
                // 元字段 / 元字段个性化：名称固定取 cn
                String name = ("eova_field".equals(table) || "eova_field_diy".equals(table))
                        ? r.getStr("cn")
                        : buildPendingName(table, r);
                rows.add(Kv.by("id", r.get("id")).set("key", buildPendingKey(table, r)).set("name", name));
            }
            groups.add(Kv.by("table", table).set("label", def.getStr("label")).set("rows", rows));
        }
        return groups;
    }

    /**
     * 按时间条件查询各表变更行
     *
     * @param start 有值时：updated &gt; start；null 表示不设下界（待上线仅用 end）
     * @param end   有值时：待上线用 updated &gt; end；历史用 updated &lt;= end
     */
    public static Map<String, List<Record>> queryUpdatedRows(Date start, Date end) {
        Map<String, List<Record>> map = new LinkedHashMap<>();
        boolean history = start != null && end != null;
        for (Kv def : META_TABLES) {
            String table = def.getStr("table");
            String ds = def.getStr("ds");
            String cols = def.getStr("cols");
            List<Record> list;
            if (history) {
                list = Db.use(ds).find(
                        String.format("SELECT %s FROM `%s` t WHERE t.updated > ? AND t.updated <= ? ORDER BY t.id",
                                cols, table),
                        start, end);
            } else if (end == null) {
                list = Db.use(ds).find(String.format("SELECT %s FROM `%s` t ORDER BY t.id", cols, table));
            } else {
                list = Db.use(ds).find(
                        String.format("SELECT %s FROM `%s` t WHERE t.updated > ? ORDER BY t.id", cols, table),
                        end);
            }
            map.put(table, list);
        }
        return map;
    }

    public static String buildPendingKey(String table, Record r) {
        switch (table) {
            case "eova_object":
            case "eova_option":
            case "eova_menu":
                return r.getStr("code");
            case "eova_field":
                return r.getStr("object_code") + "." + r.getStr("en");
            case "eova_field_diy":
                return r.getStr("object_code") + "." + r.getStr("mode") + "." + r.getStr("en");
            case "eova_button": {
                String event = r.getStr("event");
                return r.getStr("menu_code") + "." + (x.isEmpty(event) ? r.getStr("name") : event);
            }
            case "dicts":
                return r.getStr("object") + "." + r.getStr("field") + "." + r.getStr("value");
            default:
                return String.valueOf(r.get("id"));
        }
    }

    public static String buildPendingName(String table, Record r) {
        switch (table) {
            case "eova_field":
            case "eova_field_diy":
                return r.getStr("cn");
            default:
                return r.getStr("name");
        }
    }

    /** 版本号 yyyyMMddHHmmss */
    public static String buildVer() {
        return new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
    }

    public static String getTableDs(String table) {
        for (Kv def : META_TABLES) {
            if (table.equals(def.getStr("table"))) {
                return def.getStr("ds");
            }
        }
        return Ds.EOVA;
    }

    public static String getTableLabel(String table) {
        for (Kv def : META_TABLES) {
            if (table.equals(def.getStr("table"))) {
                return def.getStr("label");
            }
        }
        return table;
    }

    /**
     * 导出选中元数据：生成 SQL；待上线导出时写入一条 eova_version（updated/created 定时间窗）
     *
     * @param items        [{table_name, row_id}]
     * @param writeVersion true=打新版本；false=仅重生 SQL（历史回看）
     * @param rangeStart   时间窗起点；null 则取上一版 created
     * @param rangeEnd     时间窗终点；null 则取当前时间
     * @return {ver, sql, count}
     */
    public static Kv exportMeta(List<Kv> items, boolean writeVersion, Date rangeStart, Date rangeEnd) {
        if (x.isEmpty(items)) {
            throw new IllegalArgumentException("没有可导出的数据");
        }

        String ver = buildVer();
        // 时间窗：updated=上一版 created（起点），created=本次导出时刻（终点）
        if (rangeStart == null) {
            rangeStart = queryLastVersionCreated();
            if (rangeStart == null) {
                rangeStart = new Date(0L);
            }
        }
        if (rangeEnd == null) {
            rangeEnd = new Date();
        }
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        // 按 META_TABLES 顺序分组
        Map<String, List<Integer>> byTable = new LinkedHashMap<>();
        for (Kv def : META_TABLES) {
            byTable.put(def.getStr("table"), new ArrayList<>());
        }
        for (Kv it : items) {
            String table = it.getStr("table_name");
            Integer rowId = it.getInt("row_id");
            if (x.isEmpty(table) || rowId == null) {
                continue;
            }
            List<Integer> ids = byTable.get(table);
            if (ids == null) {
                continue;
            }
            if (!ids.contains(rowId)) {
                ids.add(rowId);
            }
        }

        StringBuilder sb = new StringBuilder();
        sb.append("-- EovaMeta version: ").append(ver).append("\n");
        sb.append("-- range: ").append(df.format(rangeStart)).append(" ~ ").append(df.format(rangeEnd)).append("\n\n");

        int rowCount = 0;

        for (Kv def : META_TABLES) {
            String table = def.getStr("table");
            List<Integer> ids = byTable.get(table);
            if (x.isEmpty(ids)) {
                continue;
            }
            String ds = def.getStr("ds");
            String idIn = ids.stream().map(String::valueOf).collect(Collectors.joining(","));
            List<Record> list = Db.use(ds).find(String.format("SELECT * FROM `%s` WHERE id IN (%s) ORDER BY id", table, idIn));
            if (x.isEmpty(list)) {
                continue;
            }

            // 非 eova 数据源加库名，便于一次执行（如 main.dicts）
            String tableRef = resolveTableRef(ds, table);
            sb.append("-- ========== ").append(getTableLabel(table));
            sb.append(" (").append(tableRef).append(", ds=").append(ds).append(") ==========\n");
            appendDeleteSql(tableRef, table, list, sb);
            sb.append("\n");

            // 导出 SQL 不带 updated；版本表只记时间窗，不存行级快照
            List<Record> insertList = new ArrayList<>();
            for (Record r : list) {
                Record copy = new Record();
                copy.setColumns(r.getColumns());
                copy.remove("updated");
                insertList.add(copy);
                rowCount++;
            }
            DbUtil.generateSql(ds, tableRef, insertList, "id", sb);
            sb.append("\n");
        }

        if (rowCount == 0) {
            throw new IllegalArgumentException("未找到可导出的数据行");
        }

        if (writeVersion) {
            Record verRow = new Record();
            verRow.set("status", 1);
            verRow.set("ver", ver);
            verRow.set("updated", rangeStart);
            verRow.set("created", rangeEnd);
            Db.use(Ds.EOVA).save("eova_version", verRow);
        }

        return Kv.by("ver", ver).set("sql", sb.toString()).set("count", rowCount);
    }

    public static Kv exportMeta(List<Kv> items) {
        return exportMeta(items, true, null, null);
    }

    public static Kv exportMeta(List<Kv> items, boolean writeVersion) {
        return exportMeta(items, writeVersion, null, null);
    }

    /**
     * eova 库不加前缀；其它数据源加 catalog 库名（如 demo.dicts）
     */
    public static String resolveTableRef(String ds, String table) {
        if (Ds.EOVA.equals(ds) || x.isEmpty(ds)) {
            return table;
        }
        String db = DsUtil.getDbNameByConfigName(ds);
        if (x.isEmpty(db)) {
            return table;
        }
        return db + "." + table;
    }

    /** 按业务键生成 DELETE，保证线上幂等覆盖；tableRef 可带库名 */
    public static void appendDeleteSql(String tableRef, String table, List<Record> list, StringBuilder sb) {
        for (Record r : list) {
            switch (table) {
                case "eova_object":
                    sb.append(String.format("DELETE FROM %s WHERE code = '%s';\n", tableRef, esc(r.getStr("code"))));
                    break;
                case "eova_field":
                    sb.append(String.format("DELETE FROM %s WHERE object_code = '%s' AND en = '%s';\n",
                            tableRef, esc(r.getStr("object_code")), esc(r.getStr("en"))));
                    break;
                case "eova_field_diy":
                    sb.append(String.format(
                            "DELETE FROM %s WHERE object_code = '%s' AND mode = '%s' AND en = '%s';\n",
                            tableRef, esc(r.getStr("object_code")), esc(r.getStr("mode")), esc(r.getStr("en"))));
                    break;
                case "eova_option":
                    sb.append(String.format("DELETE FROM %s WHERE code = '%s';\n", tableRef, esc(r.getStr("code"))));
                    break;
                case "eova_menu":
                    sb.append(String.format("DELETE FROM %s WHERE code = '%s';\n", tableRef, esc(r.getStr("code"))));
                    break;
                case "eova_button": {
                    String event = r.getStr("event");
                    if (x.isEmpty(event)) {
                        sb.append(String.format(
                                "DELETE FROM %s WHERE menu_code = '%s' AND name = '%s' AND (event IS NULL OR event = '');\n",
                                tableRef, esc(r.getStr("menu_code")), esc(r.getStr("name"))));
                    } else {
                        sb.append(String.format(
                                "DELETE FROM %s WHERE menu_code = '%s' AND name = '%s' AND event = '%s';\n",
                                tableRef, esc(r.getStr("menu_code")), esc(r.getStr("name")), esc(event)));
                    }
                    break;
                }
                case "dicts":
                    sb.append(String.format(
                            "DELETE FROM %s WHERE object = '%s' AND field = '%s' AND value = '%s';\n",
                            tableRef, esc(r.getStr("object")), esc(r.getStr("field")), esc(r.getStr("value"))));
                    break;
                default:
                    break;
            }
        }
    }

    private static String esc(String s) {
        if (s == null) {
            return "";
        }
        return s.replace("\\", "\\\\").replace("'", "\\'");
    }

}