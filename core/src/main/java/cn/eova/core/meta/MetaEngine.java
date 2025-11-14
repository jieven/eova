/**
 * Copyright (c) 2015-2026 EOVA.CN. All rights reserved.
 * Licensed under the LGPL-3.0 license
 * For authorization, please contact: admin@eova.cn
 */
package cn.eova.core.meta;

import cn.eova.model.MetaField;

/**
 * 元数据引擎-人工智能初始化数据
 *
 * @author Jieven
 *
 */
public class MetaEngine {

    /**
     * 人工智能初始化元字段数据
     *
     * @param mi 元字段
     */
    public static void build(MetaField field) {
        String cn = field.getCn().trim().toLowerCase();
        //String en = field.getEn();
        /* 1.根据字段名含义处理 */
        // 控件类型
        if (cn.contains("图片") || cn.contains("头像")) {
            field.set("type", MetaField.TYPE_IMG);
        } else if (cn.contains("简介")) {
            field.set("type", MetaField.TYPE_TEXTS);
        } else if (cn.startsWith("是否")) {
            field.set("type", MetaField.TYPE_BOOL);
        }

        // 校验规则
        if (cn.equals("qq")) {
            field.set("validator", "qq;");
        } else if (cn.equals("email") || cn.contains("邮箱")) {
            field.set("validator", "email;");
        } else if (cn.equals("url") || cn.contains("网址")) {
            field.set("validator", "url;");
        }
    }

}