/**
 * Copyright (c) 2015-2026 EOVA.CN. All rights reserved.
 * Licensed under the LGPL-3.0 license
 * For authorization, please contact: admin@eova.cn
 */
package cn.eova.common.utils.check;

import cn.eova.common.utils.util.RegexUtil;

public class ValidaUtil {
    private static String regex = "[$^&<>'/]";

    /**
     * 是否安全字符串(不包含$^&<>'/)
     * @param str
     * @return
     */
    public static boolean isSecStr(String str) {
        return !RegexUtil.isExist(regex, str);
    }
}