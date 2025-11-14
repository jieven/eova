/**
 * Copyright (c) 2015-2026 EOVA.CN. All rights reserved.
 * Licensed under the LGPL-3.0 license
 * For authorization, please contact: admin@eova.cn
 */
package cn.eova.engine;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.eova.config.EovaConfig;
import cn.eova.tools.EovaTool;
import cn.eova.tools.x;
import com.jfinal.kit.Kv;
import com.jfinal.template.Engine;

/**
 * 动态表达式解析器
 * 变量+逻辑运算
 * @author Jieven
 *
 */
public class ExpUtil {

    /**
     * 字符串模版解析
     * @param template 字符串模版
     * @param pms 对象参数
     * @return
     */
    public static String parse(String template, Map<String, String> pms) {
        if (x.isEmpty(template) || pms == null) {
            return template;
        }

        return Engine.use().getTemplateByString(template, true).renderToString(pms);
    }
//
//    public static String parse(String template, Kv kv) {
//        if (x.isEmpty(template) || kv == null) {
//            return template;
//        }
//
//        return parse(template, kv);
//    }

    /**
     * SOL动态解析
     * @param exp
     * @param kv
     * @return
     */
    public static String parseSql(String exp, Kv kv) {
        if (x.isEmpty(exp)) {
            return exp;
        }

        exp = exp.replace("\n\t", "");
        exp = exp.replace("\r", "");
        exp = exp.replace("\t", " ");

        return parse(exp, kv);
    }

    /**
     * 字符串模版解析
     * @param path 模版文件路径
     * @param kv 对象参数
     * @return
     */
    public static String parseTemplate(String path, Kv kv) {
        if (x.isEmpty(path) || kv == null) {
            return path;
        }

        return Engine.use().getTemplate(path).renderToString(kv);
    }

    /**
     * 解析EovaOption 表达式与参数
     * 支持软解析
     * <pre>
     * 案例1 = 单参
     * SQL = "select_xxx1 : select * from xxx where id = ?"
     * EXP = "selectEovaUser;10";
     * 案例1 in 多参
     * SQL = "select_xxx2 : select * from xxx where id in (?, ?)"
     * EXP = "selectEovaUser;10,20,30";
     * </pre>
     */
    public static List<Object> buildExpPara(String[] pms) {
        List<Object> list = new ArrayList<>();

        for (String pm : pms) {
            list.add(pm.trim());
        }

        return list;
    }

    /**
     * 解析预配置表达式和参数
     * 支持 硬解析+软解析 并存
     * <pre>
     * 案例:
     * SQL = "select * from table where uid in %s"
     * EXP = "selectEovaUser;@(1,2,3);10000";
     * </pre>
     */
    public static List<Object> buildSqlPara(String exp) {
        List<Object> paras = new ArrayList<>();

        try {
            // 如果有;号, 说明要进行软硬解析, 优先
            String FG = exp.indexOf(";") == -1 ? "," : ";";
            String[] strs = exp.split(FG);

            // 第一位参数是SQL表达式Key
            exp = EovaConfig.getExp(strs[0]);
            if (exp == null) {
                System.err.println(String.format("无法获取到表达式,请检查表达式配置,表达式Key=%s,添加新的表达式后重启服务才能生效!", strs[0]));
                throw new RuntimeException();
            }

            // 硬解析参数 -> 主要解决 in (1,2,3)
            ArrayList<Object> formatParm = new ArrayList<>();
            // 存在硬编码参数(必须)
            if (exp.indexOf("%s") != -1) {
                for (int i = 1; i < strs.length; i++) {
                    String str = strs[i].trim();
                    if (str.startsWith("@")) {
                        formatParm.add(x.str.delStart(str, "@"));
                    }
                }
                // 代入硬编码参数
                exp = String.format(exp, EovaTool.toArray(formatParm));
            }

            // 软解析
            for (int i = 1; i < strs.length; i++) {
                if (strs[i].startsWith("@")) {
                    continue;
                }
                paras.add(getSqlParam(strs[i]));
            }
        } catch (Exception e) {
            throw new RuntimeException("预处理自定义查找框表达式异常，Exp=" + exp);
        }

        return paras;
    }

    /**
     * 获取SQL参数，优先Integer，不能转就当String
     *
     * @param str
     * @return
     */
    public static Object getSqlParam(String str) {
        try {
            return Integer.parseInt(str);
        } catch (Exception e) {
            return str;
        }
    }

}