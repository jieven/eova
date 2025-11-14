/**
 * Copyright (c) 2015-2026 EOVA.CN. All rights reserved.
 * Licensed under the LGPL-3.0 license
 * For authorization, please contact: admin@eova.cn
 */
package cn.eova.engine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.eova.common.utils.db.SqlUtil;
import cn.eova.config.EovaConfig;
import cn.eova.model.EovaOption;
import cn.eova.model.MetaField;
import cn.eova.model.MetaObject;
import cn.eova.tools.x;
import com.alibaba.druid.sql.SQLUtils;
import com.alibaba.druid.sql.ast.SQLExpr;
import com.alibaba.druid.sql.ast.expr.SQLIdentifierExpr;
import com.alibaba.druid.sql.ast.statement.SQLSelectItem;
import com.alibaba.druid.sql.ast.statement.SQLSelectOrderByItem;
import com.alibaba.druid.sql.ast.statement.SQLTableSource;
import com.jfinal.kit.Kv;
import com.jfinal.kit.LogKit;

/**
 * Eova表达式解析器
 *
 * @author Jieven
 *
 */
public class EovaExp {

    public String ds;
    public String sql;
    public String select;
    public String from;
    public String table;
    public String where = "";
    public String order = "";
    public String pk;
    public String cn;
    private String simpleSelect;
    private EovaOption option;

    private HashMap<String, String> param = new HashMap<>();

    protected SqlParse sp;

    public EovaExp() {
    }


    public EovaExp(EovaOption ops) {
        try {
            this.option = ops;
            this.ds = ops.getDs();
            this.pk = ops.getFieldVal();
            this.cn = ops.getFieldTxt();
            this.sql = ops.getSql();

            // 初始化SQL解析器
            this.sp = new SqlParse(EovaConfig.EOVA_DBTYPE, sql);

            // 解析Sql
            initParse();

        } catch (Exception e) {
            LogKit.error("Eova MetaExp 解析异常:" + ops.getSql());
        }
    }

    public EovaExp(String exp) {
        try {
            // 无参
            if (!exp.contains(";")) {
                this.sql = exp;
            }
            // 有参
            else {
                String[] strs = exp.split(";");
                this.sql = strs[0];
                // 除了SQL后续皆为参数键值对
                int i = 0;
                for (String s : strs) {
                    i++;
                    if (i == 1) {
                        continue;
                    }
                    s = s.trim();
                    String[] ss = s.split("=");
                    param.put(ss[0].trim(), ss[1].trim());
                }
            }

            this.ds = get(EovaExpParam.DS);

            // 初始化SQL解析器
            sp = new SqlParse(EovaConfig.EOVA_DBTYPE, sql);

            // 解析Sql
            initParse();

            buildField();

        } catch (Exception e) {
            LogKit.error("Eova Exp 解析异常:" + exp);
        }
    }

    public void initParse() {
        formatSql();

        buildSelect();
        buildSimpleSelect();
        buildFrom();
        buildTable();
        buildWhere();
        buildOrder();
    }


    private void formatSql() {
        // SQL转小写,常用查询关键字全大写,业务部分不动
        sql = sql.trim();
        String[] keyword = {"SELECT ", "FROM ", " WHERE ", " AND ", " OR ", " IN ", " ORDER BY ", " DESC", "NOT"};
        for (String s : keyword) {
            sql = sql.replaceAll(s, s.toLowerCase());
        }
        // TODO 不方便获取数据源,暂时写Mysql,原始需求:常用查询关键字全小写,业务部分不动
        //			sql = sql.trim();
        //			sql = SqlUtil.notNewLine(SQLUtils.formatMySql(sql, new FormatOption(false)));
    }

    private void buildField() {
        List<SQLSelectItem> items = sp.getSelectItem();
        try {
            // 第1列默认为主键
            this.pk = SqlParse.getExprName(items.get(0).getExpr());
            // 获取自定义CN字段名
            String cname = get(EovaExpParam.CNAME);
            if (x.isEmpty(cname)) {
                // 否则取第2列默认为CN键
                cname = SqlParse.getExprName(items.get(1).getExpr());
            }
            this.cn = cname;
        } catch (Exception e) {
            LogKit.debug("EovaExp initParse Select Item Error:" + sp.getSelectItem());
        }
    }

    private void buildSelect() {
        this.select = "select " + sp.getSelectItem().toString().replaceAll("\\[|\\]", "");
    }

    private void buildSimpleSelect() {
        StringBuilder sb = new StringBuilder("select ");
        for (SQLSelectItem item : sp.getSelectItem()) {
            sb.append(item.getExpr()).append(", ");
        }
        sb.delete(sb.length() - 2, sb.length() - 1);
        this.simpleSelect = sb.toString().trim();
    }

    private void buildFrom() {
        SQLTableSource ts = sp.query.getFrom();
        String alias = ts.getAlias();

        String s = " from " + ts.toString();
        if (alias != null) {
            s += ' ' + alias;
        }

        this.from = SqlUtil.notNewLine(s);
    }

    private void buildTable() {
        this.table = this.from.toLowerCase().replace(" from ", "");
    }

    private void buildWhere() {
        SQLExpr exp = sp.query.getWhere();
        if (exp == null) {
            this.where = "";
            return;
        }
        String s = " where " + SQLUtils.toSQLString(exp);
        this.where = SqlUtil.notNewLine(s);
    }

    private void buildOrder() {
        StringBuilder sb = new StringBuilder(" order by ");

        List<SQLSelectOrderByItem> items = sp.getOrderItem();
        if (items == null) {
            return;
        }
        for (SQLSelectOrderByItem o : items) {
            // SQLIdentifierExpr exp = (SQLIdentifierExpr) o.getExpr();
            // sb.append(exp.getName());
            // if (o.getType() != null) {
            // sb.append(' ' + o.getType().name());
            // }
            sb.append(SQLUtils.toSQLString(o));
            sb.append(',');
        }
        sb.delete(sb.length() - 1, sb.length());

        this.order = sb.toString();
    }

    public String getSelect() {
        String s = this.simpleSelect;
        // 支持去重
        if (this.sql.toUpperCase().startsWith("SELECT DISTINCT")) {
            s = s.replaceFirst("select", "SELECT DISTINCT");
        }
        return s;
    }

    /**
     * 构建元对象
     *
     * @return
     */
    public MetaObject getObject() {
        // 获取元对象模版
        MetaObject eo = MetaObject.dao.getTemplate();
        eo.put("data_source", ds);
        eo.put("name", "");
        // 获取第一的值作为主键
        eo.put("pk_name", pk.toLowerCase());
        // 获取第二列的值作为CN
        eo.put("cn", cn.toLowerCase());

        return eo;
    }

    /**
     * 构建元字段属性
     *
     * @return
     */
    public List<MetaField> getFields() {
        try {
            List<MetaField> fields = new ArrayList<MetaField>();
            int index = 0;
            List<SQLSelectItem> items = sp.getSelectItem();
            for (SQLSelectItem item : items) {
                index++;

                SQLIdentifierExpr expr = (SQLIdentifierExpr) item.getExpr();

                // 字段名->字段名
                String en = expr.getName();
                // 字段别名->字段列名
                String cn = item.getAlias();

                // 首列之后的默认都可以查询
                boolean isQuery = true;
                if (index == 1) {
                    isQuery = false;
                }
                fields.add(buildItem(index, en, cn, isQuery, this.option));
            }
            return fields;
        } catch (Exception e) {
            throw new RuntimeException("Eova表达式构建虚拟元字段异常:" + e.getMessage());
        }
    }

    /**
     * 获取自定义参数
     * @param key 参数名
     * @param defaultValue 默认值
     * @return
     */
    public String get(String key, String defaultValue) {
        String val = get(key);
        if (x.isEmpty(val)) {
            return defaultValue;
        }
        return val;
    }

    public String get(String key) {
        return param.get(key);
    }

    public String get(EovaExpParam eep) {
        return get(eep.getVal(), eep.getDef());
    }

    /**
     * 表达式动态变更后的SQL
     */
    public String toString() {
        return this.select + this.from + this.where + this.order;
    }

    /** 静态方法 **/

    /**
     * 手工组装字段元数据
     *
     * @param index   排序
     * @param en      英文名
     * @param cn      中文名
     * @param isQuery 是否可查询
     * @param option
     * @return
     */
    public static MetaField buildItem(int index, String en, String cn, boolean isQuery, EovaOption option) {
        if (x.isEmpty(cn))
            cn = en;

        // 自动小写
        en = en.toLowerCase();

        // 获取元模版字段
        MetaField ei = MetaField.dao.getTemplate();

        // 默认宽度智能计算,表格列头越长,宽度越大
        int width = 150;// 默认宽度 可在eova_option 中配置宽度，无需通过字数计算了.
        // 如果不配置宽度则会自适应分配总宽度.
        // 获取表达式宽度配置
        try {
            if (option != null) {
                Kv fields = option.getConfObj("field_width");
                if (fields != null) {
                    for (Object key : fields.keySet()) {
                        if (key.equals(en)) {
                            width = fields.getInt(key);
                            break;
                        }
                    }
                }
            }
        } catch (Exception e) {
            x.log.error("EovaOption field_width 配置读取异常：" + option.toJson());
        }
        // width += (cn.length() * 20);
//		if (cn.indexOf("_WIDTH") != -1) {
//			String WDAT = "_WIDTHAUTO";
//			if (cn.endsWith(WDAT)) {
//				cn = x.str.delEnd(cn, WDAT);
//				width = 0;
//			} else {
//				String regex = "_WIDTH([0-9]*)";
//				String[] values = RegexUtil.getMatcherValue(regex, cn);
//				width = EovaTool.toInt(values[0]);
//				cn = cn.replaceAll(regex, "");
//			}
//		}
        // 图片格式化显示
        if (cn.indexOf("_IMG") != -1) {
//			ei.put("formatter", "function(value, row, index, field) { return '<img src=\"'+ value +'\">'; }");
            ei.put("formatter", "function(value,row,index,field){if(value){return `<img src=`+ value +` />`}return value}");
            cn = x.str.delEnd(cn, "_IMG");
        }

        ei.remove("id");
        ei.put("num", index);
        ei.put("en", en);
        ei.put("cn", x.isEmpty(cn) ? en : cn);
        ei.put("type", "文本框");
        ei.put("is_query", isQuery);

        ei.put("width", width);// 自适应 "undefined"
        // 第一列如果没有别名隐藏:如不想显示ID或UUID
        if (en.equals(cn)) {
            ei.put("is_query", false);
            ei.put("is_show", false);
        }
        return ei;
    }

    /**
     * 是否查询空表
     * @return
     */
    public static boolean isFormNull(String exp) {
        if (x.isEmpty(exp)) {
            return true;
        }
        // Exp 自定义URI时, 仅需要构建列, 所以要求 from null, 防止翻译多事.
        if (exp.toLowerCase().contains("from null")) {
            return true;
        }
        return false;
    }

}