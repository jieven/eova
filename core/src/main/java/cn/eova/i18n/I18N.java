/**
 * Copyright (c) 2015-2026 EOVA.CN. All rights reserved.
 * Licensed under the LGPL-3.0 license
 * For authorization, please contact: admin@eova.cn
 */
package cn.eova.i18n;

import java.util.HashMap;

import cn.eova.tools.x;

public class I18N extends HashMap<String, String> {

    private static final long serialVersionUID = 1L;

    public String get(String key) {
        String s = super.get(key);
        if (x.isEmpty(s)) {
            return key;
        }
        return s;
    }

} 