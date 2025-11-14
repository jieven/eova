/**
 * Copyright (c) 2015-2026 EOVA.CN. All rights reserved.
 * Licensed under the LGPL-3.0 license
 * For authorization, please contact: admin@eova.cn
 */
package cn.eova.common.utils.util;

public class SysUtil {
    /**
     * 是否为Windows系统
     *
     * @return
     */
    public static boolean isWindows() {
        String osName = System.getProperty("os.name");
        return osName.contains("Windows");
    }
}