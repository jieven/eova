/**
 * Copyright (c) 2015-2026 EOVA.CN. All rights reserved.
 * Licensed under the LGPL-3.0 license
 * For authorization, please contact: admin@eova.cn
 */
package cn.eova.aop;

import cn.eova.model.MetaFieldConfig;
import com.jfinal.kit.Ret;
import com.jfinal.upload.UploadFile;

/**
 * 上传拦截器
 * @author Jieven
 *
 */
public interface UploadIntercept {

    public Ret upload(String code, String en, MetaFieldConfig config, String newFileName, String uploadDir, UploadFile file);

}