/**
 * Copyright (c) 2015-2026 EOVA.CN. All rights reserved.
 * Licensed under the LGPL-3.0 license
 * For authorization, please contact: admin@eova.cn
 */
package cn.eova.mod.emi;

import cn.eova.common.utils.io.ClassUtil;

/**
 * Eova Mod Invoke Loader
 * @author Jieven
 */
public class EMILoader {

    public static EMI load(String group, String code, String className) {
        try {
            return (EMI) ClassUtil.newClass(String.format("com.eova.mod.%s.%s.%s", group, code, className));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static <T> T instance(String group, String code, String className) {
        try {
            return (T) ClassUtil.newClass(String.format("com.eova.mod.%s.%s.%s", group, code, className));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}