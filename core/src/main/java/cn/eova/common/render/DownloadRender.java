/**
 * Copyright (c) 2015-2026 EOVA.CN. All rights reserved.
 * Licensed under the LGPL-3.0 license
 * For authorization, please contact: admin@eova.cn
 */
package cn.eova.common.render;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.HashMap;

import com.jfinal.render.Render;
import com.jfinal.render.RenderException;

/**
 * 通用文件下载渲染器
 * 自动根据文件类型进行MIME识别
 *
 * @author Jieven
 */
public class DownloadRender extends Render {

    private final String fileName;
    private final String fileType;
    private final String path;

    public static HashMap<String, String> mimes() {
        HashMap<String, String> map = new HashMap<>();
        map.put("xls", "application/vnd.ms-excel");
        map.put("xlsx", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        map.put("doc", "application/msword");
        map.put("docx", "application/vnd.openxmlformats-officedocument.wordprocessingml.document");
        map.put("ppt", "application/vnd.ms-powerpoint");
        map.put("pptx", "application/vnd.openxmlformats-officedocument.presentationml.presentation");

        map.put("pdf", "application/pdf");
        map.put("jpg", "image/jpeg");
        map.put("jpeg", "image/jpeg");
        map.put("png", "image/png");

        map.put("apk", "application/vnd.android.package-archive");
        map.put("txt", "text/plain");
        map.put("csv", "text/csv");
        map.put("svg", "image/svg+xml");

        map.put("zip", "application/zip");
        map.put("rar", "application/vnd.rar");
        map.put("7z", "application/x-7z-compressed");

        map.put("mp3", "audio/mpeg");
        map.put("wav", "audio/wav");
        map.put("ogg", "audio/ogg");
        map.put("flac", "audio/flac");

        map.put("mp4", "video/mp4");
        map.put("avi", "video/x-msvideo");
        map.put("mkv", "video/x-matroska");
        map.put("mov", "video/quicktime");
        map.put("flv", "video/x-flv");

        return map;
    }

    /**
     * 文件下载渲染器
     * @param fileName 下载文件名
     * @param fileType 下载文件类型
     * @param path 文件路径
     */
    public DownloadRender(String fileName, String fileType, String path) {
        this.fileName = fileName;
        this.fileType = fileType;
        this.path = path;
    }

    @Override
    public void render() {

        File file = new File(path);
        if (!file.exists()) {
            throw new RenderException("文件不存在");
        }

        try (FileInputStream fis = new FileInputStream(file); OutputStream os = response.getOutputStream()) {

            String downName = String.format("%s.%s", fileName, fileType);

            response.reset();
            response.setContentType(mimes().get(fileType));
            response.setHeader("Content-disposition", "attachment; filename=" + URLEncoder.encode(downName, getEncoding()));
            response.setContentLength((int) file.length());


            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = fis.read(buffer)) != -1) {
                os.write(buffer, 0, bytesRead);
            }

            os.flush();
        } catch (IOException e) {
            throw new RenderException(e);
        }
    }

}