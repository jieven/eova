/**
 * Copyright (c) 2015-2026 EOVA.CN. All rights reserved.
 * Licensed under the LGPL-3.0 license
 * For authorization, please contact: admin@eova.cn
 */
package cn.eova.template.common.util;

import cn.eova.tools.x;
import cn.eova.common.utils.io.ClassUtil;
import cn.eova.common.utils.util.ExceptionUtil;
import cn.eova.common.utils.xx;
import cn.eova.config.EovaConfig;
import cn.eova.model.Button;
import cn.eova.model.MetaField;
import com.jfinal.kit.LogKit;

public class TemplateUtil {

    /**
     * 值的类型转换
     *
     * @param item 元字段
     * @param value
     * @return
     */
    public static Object buildValue(MetaField item, Object value) {
        if (x.isEmpty(value)) {
            return value;
        }
        // 获取字符串忽略两端空格
        String val = value.toString().trim();
        // 控件类型
        String type = item.getStr("type");
        // 数据类型
        // String dataType = item.getDataTypeName();
        // 布尔框需要特转换值
        if (type.equals(MetaField.TYPE_BOOL)) {
            if (xx.isTrue(val)) {
                return 1;
            } else {
                return 0;
            }
        }
        // JSON框去掉空格
        else if (type.equals(MetaField.TYPE_JSON)) {
            // 去换行 去Tab 去空格*4
            return val = val.replaceAll("\t|\r|\n|    ", "");
        }

        return val;
    }

    /**
     * 构建异常信息为HTML
     *
     * @param e
     * @return
     */
    public static String buildException(Exception e) {
        LogKit.error("Eova common operation exception:" + e.getMessage(), e);

        String type = e.getClass().getName();
        type = type.equals("java.lang.Exception") ? e.getMessage() : type;
        return "<br/><p style=\"color:red\" title=\"" + ExceptionUtil.getStackTrace(e) + "\">" + type + " [查看异常]</p>";
    }

    /**
     * 初始化元对象拦截器
     *
     * @param bizIntercept
     * @return
     * @throws Exception
     */
    public static <T> T initMetaObjectIntercept(String bizIntercept) throws Exception {
        Object o = ClassUtil.newClass(bizIntercept);
        if (o == null) {
            // 命中默认拦截器(如果有)
            return (T) EovaConfig.getDefaultMetaObjectIntercept();
        }
        return (T) o;
    }

    /**
     * 默认查询按钮
     * @return
     */
    public static Button getQueryButton() {
        Button btn = new Button();
        btn.set("name", "查询");
        btn.set("ui", "query");
        return btn;
    }
}