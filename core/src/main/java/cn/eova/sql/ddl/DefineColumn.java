/**
 * Copyright (c) 2015-2026 EOVA.CN. All rights reserved.
 * Licensed under the LGPL-3.0 license
 * For authorization, please contact: admin@eova.cn
 */
package cn.eova.sql.ddl;

import cn.eova.sql.ddl.dialect.EovaType;

public class DefineColumn {

    private String cn = "";
    private String en;
    private EovaType type;
    private int size;
    private int decimal = 0;
    private String defaultValue = null;
    private boolean isNull = true;
    private boolean isAuto = false;
    //	private boolean isPrimary = false;

    public DefineColumn(String en, String type, int size, int decimal, String cn) {
        super();
        this.en = en;
        this.cn = cn;
        this.type = EovaType.getEnum(type.toUpperCase());
        this.size = size;
        this.decimal = decimal;
    }

    /**
     * 定义字段
     * @param en 字段名
     * @param type 字段类型
     * @param size 字段长度
     * @param decimal 字段精度
     * @param cn 字段备注
     */
    public DefineColumn(String en, EovaType type, int size, int decimal, String cn) {
        super();
        this.en = en;
        this.cn = cn;
        this.type = type;
        this.size = size;
        this.decimal = decimal;
    }

    //	public DbColumn isPrimary() {
    //		this.isPrimary = true;
    //		return this;
    //	}
    //
    public DefineColumn auto() {
        this.isAuto = true;
        return this;
    }

    public DefineColumn notNull() {
        this.isNull = false;
        return this;
    }

    public DefineColumn setDefault(String defaultvalue) {
        this.defaultValue = defaultvalue;
        return this;
    }

    public EovaType getType() {
        return type;
    }

    public void setType(EovaType type) {
        this.type = type;
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

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getDecimal() {
        return decimal;
    }

    public void setDecimal(int decimal) {
        this.decimal = decimal;
    }

    public boolean isNull() {
        return isNull;
    }

    public String getDefault() {
        return defaultValue;
    }

    public boolean isAuto() {
        return isAuto;
    }

    public void setNull(boolean isNull) {
        this.isNull = isNull;
    }

    public void setAuto(boolean isAuto) {
        this.isAuto = isAuto;
    }

}
