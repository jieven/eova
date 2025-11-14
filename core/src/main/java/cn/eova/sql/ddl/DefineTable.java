/**
 * Copyright (c) 2015-2026 EOVA.CN. All rights reserved.
 * Licensed under the LGPL-3.0 license
 * For authorization, please contact: admin@eova.cn
 */
package cn.eova.sql.ddl;

import java.util.List;

public class DefineTable {

    public static final String NUMBER = "number";
    public static final String STRING = "string";
    public static final String DATE = "date";
    public static final String DATETIME = "datetime";
    public static final String TIMESTAMP = "timestamp";

    private String cn;
    private String en;
    private String pk;
    private List<DefineColumn> cols;

    public DefineTable(String cn, String en, String pk, List<DefineColumn> cols) {
        super();
        this.cn = cn;
        this.en = en;
        this.pk = pk;
        this.cols = cols;
    }

    public String getCn() {
        return cn;
    }

    public void setCn(String cn) {
        this.cn = cn;
    }

    public String getEn() {
        return en;
    }

    public void setEn(String en) {
        this.en = en;
    }

    public String getPk() {
        return pk;
    }

    public void setPk(String pk) {
        this.pk = pk;
    }

    public List<DefineColumn> getFields() {
        return cols;
    }

    public void setFields(List<DefineColumn> fields) {
        this.cols = fields;
    }

}
