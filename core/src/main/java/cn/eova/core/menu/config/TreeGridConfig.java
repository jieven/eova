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
public class TreeGridConfig {

    // 图标字段
    private String iconField;
    // 树形字段
    private String treeField;
    // ID字段
    private String idField;
    // 父ID字段
    private String parentField;

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

}