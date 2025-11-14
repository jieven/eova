/**
 * Copyright (c) 2015-2026 EOVA.CN. All rights reserved.
 * Licensed under the LGPL-3.0 license
 * For authorization, please contact: admin@eova.cn
 */
package cn.eova.engine;

/**
 * Eova表达式系统参数
 *
 * @author Jieven
 *
 */
public enum EovaExpParam {
    URI("uri", "", "自定义数据查询"), // V3.7.1 Add 使用场景:自定义查找列表数据查询
    CNAME("cname", "", "文本字段名"), // V1.6.1 Add 使用场景:订单编码
    ROOT("root", "0", "下拉树根节点的值"), // V1.6.0 Add 使用场景:下拉树指定根节点
    CACHE("cache", "", "缓存KEY"),
    DS("ds", "main", "数据源KEY");

    private String val;// 参数Key
    private String def;// 参数默认值
    private String txt;// 参数描述

    EovaExpParam(String val, String def, String txt) {
        this.val = val;
        this.def = def;
        this.txt = txt;
    }

    public String getVal() {
        return val;
    }

    public String getTxt() {
        return txt;
    }

    public String getDef() {
        return def;
    }

    public void setDef(String def) {
        this.def = def;
    }

}