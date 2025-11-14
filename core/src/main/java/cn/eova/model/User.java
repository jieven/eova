/**
 * Copyright (c) 2015-2026 EOVA.CN. All rights reserved.
 * Licensed under the LGPL-3.0 license
 * For authorization, please contact: admin@eova.cn
 */
package cn.eova.model;

import java.util.HashSet;
import java.util.Set;

import cn.eova.common.base.BaseModel;
import cn.eova.config.EovaConst;
import com.jfinal.plugin.activerecord.Record;

public class User extends BaseModel<User> {

    private static final long serialVersionUID = 1064291771401662738L;

    /**
     * 用户禁用字段
     */
    private Set<String> disableFields = new HashSet<>();

    public static final User dao = new User().dao();

    public Role role;
    public Record data;// 登录源用户数据

    public Object getId() {
        return this.get("id");
    }

    public int getRid() {
        // 优先获取临时切换角色
        Integer suRid = this.getInt("su_rid");
        if (suRid != null) {
            return suRid;
        }
        // TODO 多角色支持，变成字符串
        return this.getInt("rid");
    }

    /**
     * 是否超级管理员
     * @return
     */
    public boolean isAdmin() {
        return getIsAdmin();
    }

    // 为兼容模版取值
    public boolean getIsAdmin() {
        // 兼容多角色
        if (this.get("rid").toString().equals(EovaConst.ADMIN_RID + "")) {
            return true;
        }
        return false;
    }

    public void initRole() {
        this.role = Role.dao.findById(getRid());
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getName() {
        return this.getStr("name");
    }

    public int getOrgId() {
        return this.getInt("org_id");
    }

    public int getCompanyId() {
        if (this.getInt("company_id") == null) {
            return 0;
        }
        return this.getInt("company_id");
    }

    /**
     * 获取登录源用户数据
     * @return
     */
    public Record getData() {
        return data;
    }

    public void setData(Record data) {
        this.data = data;
    }

    public Set<String> getDisableFields() {
        return disableFields;
    }

    public void setDisableFields(Set<String> disableFields) {
        this.disableFields = disableFields;
    }
}