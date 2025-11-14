/**
 * Copyright (c) 2015-2026 EOVA.CN. All rights reserved.
 * Licensed under the LGPL-3.0 license
 * For authorization, please contact: admin@eova.cn
 */
package cn.eova.common.render;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.HashMap;

import com.jfinal.kit.Kv;
import com.jfinal.render.Render;
import com.jfinal.render.RenderException;

public class OfficeRender extends Render {

    private final static String CONTENT_TYPE_EXCEL = "application/msexcel;charset=" + getEncoding();
    private final static String CONTENT_TYPE_WROD = "application/msword;charset=" + getEncoding();
    private final static String CONTENT_TYPE_PDF = "application/pdf;charset=" + getEncoding();

    private final static String FILE_TYPE_XLS = "xls";
    private final static String FILE_TYPE_DOC = "doc";
    private final static String FILE_TYPE_PDF = "pdf";

    private final String file;
    private final String fileType;
    private final String fileName;

    /**
     * 渲染
     * @param fileType 文件格式: xls/doc/pdf
     * @param fileName 下载文件名
     * @param path 模版文件路径
     * @param pms 模版参数
     */
    public OfficeRender(String fileType, String fileName, String path, HashMap<String, Object> pms) {
        this.fileType = fileType;
        this.fileName = fileName;
        Kv kv = new Kv();
        kv.putAll(pms);
        this.file = RenderUtil.renderFile(path, kv);
    }

    @Override
    public void render() {
        PrintWriter writer = null;
        try {
            response.setHeader("Pragma", "no-cache"); // HTTP/1.0 caches might not implement Cache-Control and might only implement Pragma: no-cache
            response.setHeader("Cache-Control", "no-cache");
            response.setHeader("Content-disposition", "attachment; filename=" + URLEncoder.encode(fileName, getEncoding()));
            response.setDateHeader("Expires", 0);
            if (fileType.equalsIgnoreCase(FILE_TYPE_XLS)) {
                response.setContentType(CONTENT_TYPE_EXCEL);
            } else if (fileType.equalsIgnoreCase(FILE_TYPE_DOC)) {
                response.setContentType(CONTENT_TYPE_WROD);
            } else if (fileType.equalsIgnoreCase(FILE_TYPE_PDF)) {
                response.setContentType(CONTENT_TYPE_PDF);
            }

            response.setCharacterEncoding(getEncoding());

            writer = response.getWriter();
            writer.write(file);
            writer.flush();
        } catch (IOException e) {
            throw new RenderException(e);
        } finally {
            if (writer != null)
                writer.close();
        }
    }
}