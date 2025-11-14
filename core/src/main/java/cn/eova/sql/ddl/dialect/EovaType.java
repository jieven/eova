/**
 * Copyright (c) 2015-2026 EOVA.CN. All rights reserved.
 * Licensed under the LGPL-3.0 license
 * For authorization, please contact: admin@eova.cn
 */
package cn.eova.sql.ddl.dialect;

/**
 * EOVA数据类型
 * 约定几种通用类型, 基本可以满足日常使用, 屏蔽多DB个性化影响.
 */
public enum EovaType {

    DATE("DATE", "日期"),
    TIME("TIME", "时间"),
    DATETIME("DATETIME", "时间"),
    VARCHAR("VARCHAR", "字符串"),
    CHAR("CHAR", "字符"),
    NUMBER("NUMBER", "数字"),
    BOOL("BOOL", "布尔");

    private String val;
    private String txt;

    EovaType(String val, String txt) {
        this.val = val;
        this.txt = txt;
    }

    public String getVal() {
        return this.val;
    }

    public String getTxt() {
        return this.txt;
    }

    public static EovaType getEnum(String val) {
        EovaType[] values = EovaType.values();
        for (EovaType v : values) {
            if (v.getVal().equals(val)) {
                return v;
            }
        }
        return null;
    }
}
