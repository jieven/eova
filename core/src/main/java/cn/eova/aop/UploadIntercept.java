/**
 * Copyright (c) 2015-2026 EOVA.CN. All rights reserved.
 * Licensed under the LGPL-3.0 license
 * For authorization, please contact: admin@eova.cn
 */
package cn.eova.aop;

import java.util.List;

import cn.eova.model.MetaFieldConfig;
import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.upload.UploadFile;

/**
 * 上传拦截器
 * @author Jieven
 *
 */
public interface UploadIntercept {

    /**
     * 上传文件
     * @param code 元对象编码
     * @param en 元字段
     * @param config 元字段配置
     * @param newFileName 上传文件名
     * @param uploadDir 上传目录
     * @param file 文件
     * @return
     */
    public Ret upload(String code, String en, MetaFieldConfig config, String newFileName, String uploadDir, UploadFile file);

    /**
     * 查询文件
     * @param list 文件记录
     * @return
     */
    public Ret query(List<Record> list);
}