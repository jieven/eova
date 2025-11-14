/**
 * Copyright (c) 2015-2026 EOVA.CN. All rights reserved.
 * Licensed under the LGPL-3.0 license
 * For authorization, please contact: admin@eova.cn
 */
package cn.eova.engine;

import java.util.ArrayList;
import java.util.List;

import cn.eova.common.Ds;
import cn.eova.common.utils.xx;
import cn.eova.model.MetaField;
import cn.eova.model.User;
import cn.eova.tools.x;
import com.jfinal.kit.Kv;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

/**
 * Eova表达式构建器
 * <pre>
 * #标准SQL select id ID, name CN from users
 * #自定义数据获取 select id ID, name CN from null;uri=/xxx
 * #预配置表达式Key selectEovaRole,1
 * #固定表达式 1=苹果 2=香蕉 3=荔枝
 * </pre>
 * @author Jieven
 */
public class EovaExpBuilder {

    private User user;// 用户
    private String exp;// 表达式
    private String objectCode;// 元对象编码
    private String fieldCode;// 元字段编码

    private List<Object> expParas = new ArrayList<>();// 表达式参数

    public EovaExpBuilder(String exp) {
        if (exp != null) {
            this.exp = exp.trim();
        }
    }

    public EovaExpBuilder(User user, String exp) {
        this(exp);
        this.user = user;
    }

    public EovaExpBuilder(User user, String exp, String objectCode, String fieldCode) {
        this(user, exp);
        this.objectCode = objectCode;
        this.fieldCode = fieldCode;
    }

    /**
     * 构建EovaExp
     * @return
     */
    public EovaExp build() {
        return new EovaExp(buildExp());
    }

    /**
     * 动态解析表达式并输出SQL
     * @return
     */
    private String parseExp() {
        if (user == null) {
            return exp;
        }
        // 动态解析(主要是获取用户域数据)
        return ExpUtil.parseSql(exp, Kv.of("user", this.user));
    }

    /**
     * 构建表达式
     * @return
     */
    public String buildExp() {
        // 表达式为空, 默认根据元字段获取表达式
        if (exp == null) {
            MetaField ei = MetaField.dao.getByObjectCodeAndEn(objectCode, fieldCode);
            exp = ei.getExp();
            // 如果不是标准SQL表达式, 说明是预配置表达式
            if (!isSqlExp()) {
                parseConfigExp();
            }
            return parseExp();
        }

        // 动态获取审批业务控件表达式
        if (exp.startsWith("EOVA_FLOW_WIDGET")) {
            String[] ss = exp.split(",");
            Record e = Db.use(Ds.EOVA).findById("eova_flow_widget", ss[1]);
            exp = e.getStr("exp");// 限定字段,缩小注入范围
            return parseExp();
        }

        // 如果是标准SQL表达式直接解析
        if (isSqlExp()) {
            if (x.conf.getBool("safe_sql_param", false)) {
                throw new RuntimeException("安全模式：禁止将SQL作为参数, 存在SQL注入风险! 关闭安全模式配置项:safe_sql_param=false");
            }
            return parseExp();
        }

        // 构建预配置表达式
        parseConfigExp();

        return parseExp();
    }

    /**
     * 获取固定值
     * 1.固定值 1=xx 2=xxx 3=xx
     * 2.sql表达式查询
     * @return
     */
    public List<Record> buildFixedItem() {
        if (x.isEmpty(exp)) {
            return null;
        }

        // 有固定值 1=xxx 2=xxx
        if (isFixedItem()) {
            List<Record> arrs = new ArrayList<>();

            String[] ss = xx.splitBlank(exp);
            for (String s : ss) {
                String[] item = s.split("=");
                arrs.add(new Record().set("id", item[0]).set("cn", item[1]));
            }

            return arrs;
        }

        // 根据表达式取值 构建数据 (比如选择控件)
        EovaExp se = build();
        return Db.use(se.ds).find(se.sql);
    }

    /**
     * 是否存在固定值项
     * 1=xx 2=xx 3=xxx
     * @return
     */
    public boolean isFixedItem() {
        return !exp.toLowerCase().startsWith("select") && exp.contains("=") && !exp.contains(";");
    }


    private void parseConfigExp() {
        this.expParas = ExpUtil.buildSqlPara(exp);
        // Meta 重构20250421

//        try {
//            // 如果有;号, 说明要进行软硬解析, 优先
//            String FG = exp.indexOf(";") == -1 ? "," : ";";
//            String[] strs = exp.split(FG);
//
//            // 第一位参数是SQL表达式Key
//            exp = EovaConfig.getExp(strs[0]);
//            if (exp == null) {
//                System.err.println(String.format("无法获取到表达式,请检查表达式配置,表达式Key=%s,添加新的表达式后重启服务才能生效!", strs[0]));
//                throw new RuntimeException();
//            }
//
//            // 硬解析参数 -> 主要解决 in (1,2,3)
//            ArrayList<Object> formatParm = new ArrayList<>();
//            // 存在硬编码参数(必须)
//            if (exp.indexOf("%s") != -1) {
//                for (int i = 1; i < strs.length; i++) {
//                    String x = strs[i].trim();
//                    if (x.startsWith("@")) {
//                        formatParm.add(x.str.delStart(x, "@"));
//                    }
//                }
//                // 代入硬编码参数
//                exp = String.format(exp, EovaTool.toArray(formatParm));
//            }
//
//            // 软解析
//            this.expParas = new ArrayList<>();
//            for (int i = 1; i < strs.length; i++) {
//                if (strs[i].startsWith("@")) {
//                    continue;
//                }
//                expParas.add(getSqlParam(strs[i]));
//            }
//        } catch (Exception e) {
//            throw new RuntimeException("预处理自定义查找框表达式异常，Exp=" + exp);
//        }
    }


    /**
     * 是否为标准SQL表达式
     * @return
     */
    public boolean isSqlExp() {
        String tp = exp.toLowerCase();
        return tp.startsWith("select") && tp.contains("from") && tp.contains(" ");
    }

    /**
     * 获取表达式参数
     * @return
     */
    public List<Object> getExpParas() {
        return expParas;
    }
}
