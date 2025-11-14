/**
 * Copyright (c) 2015-2026 EOVA.CN. All rights reserved.
 * Licensed under the LGPL-3.0 license
 * For authorization, please contact: admin@eova.cn
 */
package cn.eova.core.role;

import cn.eova.aop.AopContext;
import cn.eova.hook.EovaMetaHook;

public class RoleHook implements EovaMetaHook {

    @Override
    public String invoke(EovaMetaHook.Action action, AopContext ac) throws Exception {
        switch (action) {
            case ADD_BEFORE:
                return addBefore(ac);
            case UPDATE_BEFORE:
                return updateBefore(ac);
        }
        return null;
    }

    public String addBefore(AopContext ac) throws Exception {
        Integer lv = ac.record.getInt("lv");
        Integer roleLv = ac.user.getRole().getInt("lv");
        if (lv <= roleLv) {
            return "权限级别必须大于：" + roleLv;
        }
        return null;
    }

    public String updateBefore(AopContext ac) throws Exception {
        return addBefore(ac);
    }

}