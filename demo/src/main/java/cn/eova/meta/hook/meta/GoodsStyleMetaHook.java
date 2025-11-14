/**
 * Copyright (c) 2015-2026 EOVA.CN. All rights reserved.
 * Licensed under the LGPL-3.0 license
 * For authorization, please contact: admin@eova.cn
 */
package cn.eova.meta.hook.meta;

import cn.eova.aop.AopContext;
import cn.eova.hook.EovaMetaHook;

public class GoodsStyleMetaHook implements EovaMetaHook {

    @Override
    public String invoke(Action action, AopContext ac) throws Exception {
        switch (action) {
            case UPDATE_BEFORE:
                return updateBefore(ac);
            case UPDATE_SUCCEED:
                return updateSucceed(ac);
            case UPDATE_AFTER:
        }
        return null;
    }

    private String updateBefore(AopContext ac) throws Exception {
        String catName = ac.record.getStr("cat_name");
        System.out.println(catName);
        if (!catName.equals("秦始皇")) {
            // 秦始皇吃花椒
            return "updatefore:" + catName;

        }
        return null;
    }

    private String updateSucceed(AopContext ac) throws Exception {
        return "新增成功:" + ac.object.getStr("name");
    }

//    private void queryBefore(AopContext ac) throws Exception {
//        String objectCode = ac.ctrl.get("query_v_object_code");
//        if (!x.isEmpty(objectCode)) {
//            MetaObject o = MetaObject.dao.getByCode(objectCode);
//            ac.condition = " and object = ?";
//            ac.params.add(o.getTable());
//        }
//    }

}

