/**
 * Copyright (c) 2015-2026 EOVA.CN. All rights reserved.
 * Licensed under the LGPL-3.0 license
 * For authorization, please contact: admin@eova.cn
 */
package cn.eova.meta;

import cn.eova.hook.BizHook;
import cn.eova.hook.EovaHookType;
import cn.eova.hook.HookRegistry;
import cn.eova.meta.hook.imports.HotelImportHook;

/**
 * 导入业务钩子注册
 *
 * @author Jieven
 */
public class AppImportHooks extends HookRegistry {

    public static void add(String code, BizHook hook) {
        add(EovaHookType.IMPORT, code, hook);
    }

    @Override
    public void config() {
        add("sys_hotel", new HotelImportHook());
        //add("sys_user", new UserImportHook());
    }
}