/**
 * Copyright (c) 2015-2026 EOVA.CN. All rights reserved.
 * Licensed under the LGPL-3.0 license
 * For authorization, please contact: admin@eova.cn
 */
package cn.eova.core.menu.config;

/**
 * 菜单表树配置
 *
 * @author Jieven
 *
 */
public class TreeConfig {

    // 对象编码
    private String objectCode;
    // 外键字段
    private String objectField;

    // 树形字段
    private String treeField;
    // ID字段
    private String idField = "id";
    // 父ID字段
    private String parentField = "parent_id";
    // 根节点的值
    private String rootPid = "0";
    // 图标字段
    private String iconField;
    // 排序字段
    private String orderField;

    public String getIconField() {
        return iconField;
    }

    public void setIconField(String iconField) {
        this.iconField = iconField;
    }

    public String getTreeField() {
        return treeField;
    }

    public void setTreeField(String treeField) {
        this.treeField = treeField;
    }

    public String getParentField() {
        return parentField;
    }

    public void setParentField(String parentField) {
        this.parentField = parentField;
    }

    public String getIdField() {
        return idField;
    }

    public void setIdField(String idField) {
        this.idField = idField;
    }

    public String getObjectCode() {
        return objectCode;
    }

    public void setObjectCode(String objectCode) {
        this.objectCode = objectCode;
    }

    public String getObjectField() {
        return objectField;
    }

    public void setObjectField(String objectField) {
        this.objectField = objectField;
    }

    public String getRootPid() {
        return rootPid;
    }

    public void setRootPid(String rootPid) {
        this.rootPid = rootPid;
    }

    public String getOrderField() {
        return orderField;
    }

    public void setOrderField(String orderField) {
        this.orderField = orderField;
    }

}