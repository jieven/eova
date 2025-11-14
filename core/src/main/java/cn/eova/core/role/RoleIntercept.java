/**
 * Copyright (c) 2015-2026 EOVA.CN. All rights reserved.
 * Licensed under the LGPL-3.0 license
 * For authorization, please contact: admin@eova.cn
 */
package cn.eova.core.role;

import cn.eova.aop.AopContext;
import cn.eova.aop.MetaObjectIntercept;

public class RoleIntercept extends MetaObjectIntercept {

    @Override
    public String addBefore(AopContext ac) throws Exception {
        Integer lv = ac.record.getInt("lv");
        Integer roleLv = ac.user.getRole().getInt("lv");
        if (lv <= roleLv) {
            return error("权限级别必须大于：" + roleLv);
        }
        return null;
    }

    @Override
    public String updateBefore(AopContext ac) throws Exception {
        return addBefore(ac);
    }


}