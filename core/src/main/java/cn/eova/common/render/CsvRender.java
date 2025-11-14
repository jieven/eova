/**
 * Copyright (c) 2015-2026 EOVA.CN. All rights reserved.
 * Licensed under the LGPL-3.0 license
 * For authorization, please contact: admin@eova.cn
 */
package cn.eova.common.render;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import cn.eova.common.utils.excel.ExceUtil;
import cn.eova.model.MetaField;
import com.jfinal.kit.LogKit;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.render.Render;
import com.jfinal.render.RenderException;

public class CsvRender extends Render {

    private final List<MetaField> fields;
    private final List<Record> data;

    private final String fileName;

    public CsvRender(List<MetaField> fields, List<Record> data, String fileName) {
        this.fields = fields;
        this.data = data;
        this.fileName = fileName + ".csv";
    }

    @Override
    public void render() {
        response.reset();
        try {
            response.setHeader("Content-disposition", "attachment; filename=" + URLEncoder.encode(fileName, getEncoding()));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        response.setContentType("text/csv");
        OutputStream os = null;
        try {
            os = response.getOutputStream();
            ExceUtil.exportCsv(fields, data, os);
        } catch (Exception e) {
            throw new RenderException(e);
        } finally {
            try {
                if (os != null) {
                    os.flush();
                    os.close();
                }
            } catch (IOException e) {
                LogKit.error(e.getMessage(), e);
            }

        }
    }

}