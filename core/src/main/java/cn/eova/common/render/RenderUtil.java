/**
 * Copyright (c) 2015-2026 EOVA.CN. All rights reserved.
 * Licensed under the LGPL-3.0 license
 * For authorization, please contact: admin@eova.cn
 */
package cn.eova.common.render;

import cn.eova.engine.ExpUtil;
import com.jfinal.kit.Kv;
import com.jfinal.template.Engine;
import com.jfinal.template.source.ClassPathSourceFactory;

public class RenderUtil {

    /**
     * 渲染指定文件
     * @param path 文件路径
     * @param kv 动态参数
     * @return
     */
    public static String renderFile(String path, Kv kv) {
        return ExpUtil.parseTemplate(path, kv);
    }

    /**
     * 渲染指定资源文件(比如Jar内资源文件)
     * @param classpath 资源文件路径
     * @param kv 动态参数
     * @return
     */
    public static String renderClasspath(String classpath, Kv kv) {
        return Engine.use().setSourceFactory(new ClassPathSourceFactory()).getTemplate(classpath).renderToString(kv);
//
//        String text = "";
//        InputStream in = null;
//        try {
//            in = Thread.currentThread().getContextClassLoader().getResourceAsStream(resource);
//            if (in == null) {
//                in = RenderUtil.class.getResourceAsStream(resource);
//            }
//            if (in == null) {
//                return null;
//            }
//            text = TxtUtil.read(in);
//        } finally {
//            StreamUtil.close(in);
//        }
//        return RenderUtil.renderFile(text, kv);
    }

}