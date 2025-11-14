/**
 * Copyright (c) 2015-2026 EOVA.CN. All rights reserved.
 * Licensed under the LGPL-3.0 license
 * For authorization, please contact: admin@eova.cn
 */
package cn.eova.core.dict;

import cn.eova.tools.x;
import cn.eova.aop.AopContext;
import cn.eova.aop.MetaObjectIntercept;
import cn.eova.model.MetaObject;

public class DictIntercept extends MetaObjectIntercept {

    @Override
    public void queryBefore(AopContext ac) throws Exception {
        String objectCode = ac.ctrl.get("query_v_object_code");
        if (!x.isEmpty(objectCode)) {
            MetaObject o = MetaObject.dao.getByCode(objectCode);
            ac.condition = " and object = ?";
            ac.params.add(o.getTable());
        }

        super.queryBefore(ac);
    }


}