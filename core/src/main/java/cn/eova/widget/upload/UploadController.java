/**
 * Copyright (c) 2015-2026 EOVA.CN. All rights reserved.
 * Licensed under the LGPL-3.0 license
 * For authorization, please contact: admin@eova.cn
 */
package cn.eova.widget.upload;

import java.util.Collections;
import java.util.List;

import cn.eova.tools.x;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import cn.eova.common.Ds;
import cn.eova.common.base.BaseController;
import cn.eova.common.utils.io.FileUtil;
import cn.eova.common.utils.xx;
import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 上传组件
 *
 * @author Jieven
 *
 */
public class UploadController extends BaseController {

    private static final Logger log = LoggerFactory.getLogger(UploadController.class);

    // 异步传图
    public void img() {
        // 上传使用编码+预配置的方式获取目录(防止用户构造恶意目录)
        String code = get(0);
        String dir = x.conf.get(String.format("file.dir.%s", code), "/");// 为降低新人理解成本默认为更目录
        Ret r = UploadUtil.upload(this, "img", get("name"), dir);
        renderJson(r);
    }

    // 异步传文件
    public void file() {
        // 上传使用编码+预配置的方式获取目录(防止用户构造恶意目录)
        String code = get(0);
        String dir = x.conf.get(String.format("file.dir.%s", code), "/");// 为降低新人理解成本默认为更目录
        Ret rt = UploadUtil.upload(this, "file", get("name"), dir);
        renderJson(rt);
    }

    // 异步传临时文件
    public void temp() {
//        String dir = x.conf.get(String.format("file.dir.%s", code), "/file");
        Ret rt = UploadUtil.upload(this, "file", get("name"), "/temp");
        renderJson(rt);
    }

    // 编辑器上传图片(wangEditor)
    public void editor() {
        // 开始上传
        Ret rt = UploadUtil.upload(this, "img", null, "/editor");
        if (rt.isFail()) {
            renderText("error|" + rt.getStr("msg"));
            return;
        }
        // 获取最终上传目录
        String uploadDir = rt.getStr("uploadDir");
        // 获取图片服务域名
        String domain = x.conf.get("domain_img");
        if (x.isEmpty(domain)) {
            throw new RuntimeException("图片上传异常,请先配置图片服务域名!配置项:domain.config domain_img=图片服务域名");
        }

        uploadDir = FileUtil.formatWebPath(uploadDir);

        String url = String.format("%s/%s/%s", x.str.delEnd(domain, "/"), xx.delStartEnd(uploadDir, "/"), rt.getStr("fileName"));

        JSONObject o = new JSONObject();
        JSONArray urls = new JSONArray();
        urls.add(0, url);
        o.put("errno", 0);
        o.put("data", urls);

        renderJson(o);
    }

    /**
     * 文件上传记录
     * 用于根据文件名翻译
     */
    public void query() {
        JSONArray files = getJsonObj().getJSONArray("files");
        // 生成?占位符
        String marks = String.join(",", Collections.nCopies(files.size(), "?"));
        String sql = String.format("SELECT * FROM `eova_file` where code in (%s)", marks);
        List<Record> list = Db.use(Ds.EOVA).find(sql, files.toArray());

        Ret ret = Ret.ok().set("data", list);

        renderJson(ret);
    }
}