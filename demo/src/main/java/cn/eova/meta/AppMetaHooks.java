/**
 * Copyright (c) 2015-2026 EOVA.CN. All rights reserved.
 * Licensed under the LGPL-3.0 license
 * For authorization, please contact: admin@eova.cn
 */
package cn.eova.meta;

import cn.eova.hook.HookRegistry;
import cn.eova.meta.hook.meta.GoodsStyleMetaHook;
import cn.eova.meta.hook.meta.LinkMetaHook;
import cn.eova.meta.hook.meta.ProductMetaHook;

/**
 * 应用元对象钩子注册
 *
 * @author Jieven
 */
public class AppMetaHooks extends HookRegistry {

    @Override
    public void config() {

        addMeta("goods_style", new GoodsStyleMetaHook());
        addMeta("meta_product", new ProductMetaHook());
        // 元对象编码不能弄错, 必须与实际一致.
        addMeta("test_links2", new LinkMetaHook());

    }
}