/**
 * Copyright (c) 2015-2026 EOVA.CN. All rights reserved.
 * Licensed under the LGPL-3.0 license
 * For authorization, please contact: admin@eova.cn
 */
package cn.eova.model;

import java.util.ArrayList;
import java.util.List;

import cn.eova.tools.x;
import cn.eova.common.base.BaseModel;


/**
 * 用户角色
 *
 * @author Jieven
 * @date 2014-9-10
 */
public class Role extends BaseModel<Role> {

    private static final long serialVersionUID = -1794335434198017392L;

    public static final Role dao = new Role();

    /**
     * 获取下级角色
     * @return
     */
    public List<Role> findSubRole(User user) {

        List<Object> paras = new ArrayList<>();
        int lv = user.getRole().getInt("lv");
        String sql = "select * from eova_role where lv > ?";
        paras.add(lv);
        String companyField = x.conf.get("login.user.company_id", "company_id");
        // 自动按企业过滤
        if (this._getTable().getColumnNameSet().contains(companyField)) {
            Object companyValue = user.get(companyField);
            if (companyValue != null) {
                sql += String.format(" and %s = ?", companyField);
                paras.add(companyValue);
            }
        }
        return this.find(sql, paras.toArray());
    }

    @Override
    public List<Role> findAll() {
        return this.find("select * from eova_role order by lv");
    }

}