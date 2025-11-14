/**
 * Copyright (c) 2015-2026 EOVA.CN. All rights reserved.
 * Licensed under the LGPL-3.0 license
 * For authorization, please contact: admin@eova.cn
 */
package cn.eova.common.render;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;

import com.jfinal.kit.Kv;
import com.jfinal.render.Render;
import com.jfinal.render.RenderException;

public class Html2XlsRender extends Render {

    private final static String CONTENT_TYPE = "application/msexcel;charset=" + getEncoding();

    private final String xls;
    private final String fileName;

    /**
     * 渲染Xls
     * @param fileName 下载文件名
     * @param path XLS文件路径
     * @param kv 模版参数
     */
    public Html2XlsRender(String fileName, String path, Kv kv) {
        this.fileName = fileName;
        this.xls = RenderUtil.renderFile(path, kv);
    }

    @Override
    public void render() {
        PrintWriter writer = null;
        try {
            response.setHeader("Pragma", "no-cache"); // HTTP/1.0 caches might not implement Cache-Control and might only implement Pragma: no-cache
            response.setHeader("Cache-Control", "no-cache");
            response.setHeader("Content-disposition", "attachment; filename=" + URLEncoder.encode(fileName, "UTF-8"));
            response.setDateHeader("Expires", 0);
            response.setContentType(CONTENT_TYPE);
            response.setCharacterEncoding(getEncoding());

            writer = response.getWriter();
            writer.write(xls);
            writer.flush();
        } catch (IOException e) {
            throw new RenderException(e);
        } finally {
            if (writer != null)
                writer.close();
        }
    }

}