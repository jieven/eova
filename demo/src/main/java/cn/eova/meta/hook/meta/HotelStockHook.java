/**
 * Copyright (c) 2015-2026 EOVA.CN. All rights reserved.
 * Licensed under the LGPL-3.0 license
 * For authorization, please contact: admin@eova.cn
 */
package cn.eova.meta.hook.meta;

import cn.eova.aop.AopContext;
import cn.eova.hook.EovaMetaHook;

public class HotelStockHook implements EovaMetaHook {

    @Override
    public String invoke(Action action, AopContext ac) throws Exception {
        switch (action) {
            case ADD_BEFORE:
                return addBefore(ac);
        }
        return null;
    }

    private String addBefore(AopContext ac) throws Exception {
        System.out.println("新增前置:" + ac.object.getStr("name"));

        // 获取自定义sessionStorage 参数，自己新增前传即可
        // 自定义带参用法2：
        // sessionStorage.setItem('hotel_stock', JSON.stringify({
        //     hotel_id: row.value.id
        // }));

        String hotelId = ac.ctrl.get("hotel_id");
        System.out.println("自定义会话参数：" + hotelId);
        // 强制塞给 新增对象
        ac.record.set("hotel_id", hotelId);

        return null;
    }

}

