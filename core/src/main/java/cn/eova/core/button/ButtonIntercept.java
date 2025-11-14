/**
 * Copyright (c) 2015-2026 EOVA.CN. All rights reserved.
 * Licensed under the LGPL-3.0 license
 * For authorization, please contact: admin@eova.cn
 */
package cn.eova.core.button;

import cn.eova.aop.AopContext;
import cn.eova.aop.MetaObjectIntercept;
import cn.eova.model.RoleBtn;

public class ButtonIntercept extends MetaObjectIntercept {

    @Override
    public String deleteBefore(AopContext ac) throws Exception {
        int id = ac.record.getInt("id");

        // 删除菜单按钮关联权限
        RoleBtn.dao.deleteByBid(id);

        return null;
    }

}