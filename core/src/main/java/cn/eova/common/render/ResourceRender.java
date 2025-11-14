/**
 * Copyright (c) 2015-2026 EOVA.CN. All rights reserved.
 * Licensed under the LGPL-3.0 license
 * For authorization, please contact: admin@eova.cn
 */
package cn.eova.common.render;

import com.jfinal.kit.Kv;
import com.jfinal.kit.PathKit;
import com.jfinal.render.HtmlRender;

public class ResourceRender extends HtmlRender {

    public ResourceRender(Object object, String view, Kv attr) {
        super(RenderUtil.renderClasspath(buildResource(object, view), attr));
    }

    private static String buildResource(Object object, String filePath) {
        // 获取当前方法的上上级 也就是 调用
        // StackTraceElement[] ss = Thread.currentThread().getStackTrace();
        // StackTraceElement a = (StackTraceElement)ss[4];
        // String txt = Utils.readFromResource(filePath);
        String pack = PathKit.getPackagePath(object);
        return String.format("%s/resources/%s", pack, filePath);
    }

}