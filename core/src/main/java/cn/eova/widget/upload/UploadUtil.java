/**
 * Copyright (c) 2015-2026 EOVA.CN. All rights reserved.
 * Licensed under the LGPL-3.0 license
 * For authorization, please contact: admin@eova.cn
 */
package cn.eova.widget.upload;

import java.io.File;
import java.util.List;

import cn.eova.tools.x;
import cn.hutool.core.util.RandomUtil;
import cn.eova.aop.UploadIntercept;
import cn.eova.common.Ds;
import cn.eova.common.base.BaseController;
import cn.eova.common.utils.io.FileUtil;
import cn.eova.common.utils.io.ImageUtil;
import cn.eova.common.utils.util.RegexUtil;
import cn.eova.config.EovaConfig;
import cn.eova.model.MetaField;
import cn.eova.model.MetaFieldConfig;
import com.jfinal.kit.LogKit;
import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.upload.UploadFile;

/**
 * 上传
 *
 * @author Jieven
 *
 */
public class UploadUtil {

    /**
     * 上传文件
     * @param uploadType 上传类型:file/img
     * @param name 文件控件name
     * @param defaultDir 文件保存目录
     * @return
     */
    public static Ret upload(BaseController ctrl, String uploadType, String name, String defaultDir) {
        // 编码
        String code = ctrl.get("code");
        String en = ctrl.get("en");

        // 保持文件名
        boolean isOriginal = false;

        MetaFieldConfig config = null;
        // 获取特定源配置 EOVA_FLOW_WIDGET,ID
        if (code != null && code.startsWith("EOVA_FLOW_WIDGET")) {
            String[] ss = code.split(",");
            Record e = Db.use(Ds.EOVA).findById("eova_flow_widget", ss[1]);
            String json = e.getStr("config");// 限定字段,缩小注入范围
            config = MetaField.parseConfig(json);
        }
        // 获取元字段配置
        else if (code != null && en != null) {
            MetaField field = MetaField.dao.getByObjectCodeAndEn(code, en);
            config = field.getConfig();
        }

        if (config != null && config.getFilename() != null && config.getFilename().equals("ORIGINAL")) {
            isOriginal = true;
        }

        // 文件大小限制(KB) 默认50M
        int maxKB = x.conf.getInt("upload_size", 50 * 1024);
        if (uploadType.equals("img")) {
            // 图片10M
            maxKB = x.conf.getInt("upload_img_size", 10 * 1024);
        }

        String uploadDir = null;

        // 构建上传目录
        uploadDir = buildUploadDir(config, defaultDir);

        String newFileName = null;
        UploadFile file = null;
        try {

            // 预创建上传目录, 避免多文件上传并发, 导致异常:not exists and can not create directory.
            String baseDir = x.conf.get("file.dir.base");

            cn.hutool.core.io.FileUtil.mkdir(baseDir + uploadDir);

            if (name != null) {
                // 按参数名获取上传文件
                file = ctrl.getFile(name, uploadDir);
            } else {
                // 获取第一个上传文件
                List<UploadFile> files = ctrl.getFiles(uploadDir);
                if (x.isEmpty(files)) {
                    return Ret.fail("请选择一个文件");
                }
                file = files.get(0);
            }
            if (x.isEmpty(file)) {
                return Ret.fail("请选择一个文件");
            }
            if (FileUtil.checkFileSize(file.getFile(), maxKB)) {
                return Ret.fail(String.format("文件大小不能超过%sKB", maxKB));
            }

            String filePath = file.getFile().getPath();
            // 图片检查
            if (uploadType.equalsIgnoreCase("img")) {
                // 图片合法性严格检查(图片后缀+图片头)
                if (!ImageUtil.isImage(filePath)) {
                    return Ret.fail("该文件不是标准的图片文件格式，请勿手工修改文件格式");
                }
            }
            // 文件白名单检查
            else {
                // 验证图片格式是否正确
                if (!FileUtil.checkFileType(filePath, false)) {
                    return Ret.fail("该文件格式禁止上传");
                }
            }

            // 获取新文件名
            newFileName = buildFileName(config, file);

            // 上传文件拦截
            UploadIntercept intercept = EovaConfig.getUploadIntercept();
            if (intercept != null) {
                return intercept.upload(code, en, config, newFileName, uploadDir, file);
            }

            // 新文件 Path
            String path = file.getUploadPath() + File.separator + newFileName;

            // 文件储存Path 上传目录/新文件名
            String pathCode = newFileName;
//            if (!x.isEmpty(defaultDir) && defaultDir.length() > 1) {
//                pathCode = defaultDir + '/' + newFileName;
//            }

            // 保存文件上传记录
            Record r = new Record();
            r.set("code", pathCode);
            r.set("kb", file.getFile().length());
            r.set("name", file.getFile().getName());
            r.set("user_id", ctrl.UID());
            r.set("company_id", ctrl.CID());
            Db.use(Ds.EOVA).save("eova_file", r);

            // 文件另存为
            FileUtil.rename(file.getFile().getPath(), path);
            LogKit.info(file.getFile().getPath() + " -> " + newFileName);

            // 保留原文件, 无需改名
//            if (!isOriginal) {
//                /*
//                 * 如果文件存在,先删除.
//                 * 1.指定文件名,一般为覆盖文件,否则不应指定文件名!如不能覆盖应自定义上传逻辑
//                 * 2.未指定文件名,随机文件名,理论上不会存在重名,如巧合,应删之
//                 */
//                if (FileUtil.isExists(path)) {
//                    FileUtil.delete(path);
//                }
//
//                FileUtil.rename(file.getFile().getPath(), path);
//                LogKit.info(file.getFile().getPath() + " -> " + newFileName);
//            }


        } catch (Exception e) {
            e.printStackTrace();
            return Ret.fail("系统异常：文件上传失败,请稍后再试");
        } finally {
            // 上传异常, 垃圾回收, 保留原文件禁止回收
            if (!isOriginal) {
                FileUtil.delete(file.getFile());
            }
        }
        return Ret.ok().set("fileName", newFileName).set("oldFileName", file.getFile().getName()).set("uploadDir", uploadDir);
    }

    // yyyy 按年, yyyyMM 按月 , yyyyMMdd 按天, 其它自己脑补
    private static String parseTimePath(String path) {
        // String path = "/abc/{yyyy}/{MMdd}";
        for (String s : path.split("/")) {
            if (!x.isEmpty(s) && s.startsWith("{")) {
                s = s.replaceAll("\\{", "").replaceAll("\\}", "");
                // String time = DateTime.now().toString(x);
                String time = x.time.formatNow(s);
                path = RegexUtil.replaceAll(String.format("\\{%s\\}", s), time, path, -1);
            }
        }
        return path;
    }

    /**
     * 构建上传目录
     * @param config 元字段配置
     * @param defaultDir 缺省目录配置
     * @return
     */
    private static String buildUploadDir(MetaFieldConfig config, String defaultDir) {

        String s = null;

        // 构建自定义上传目录
        if (config != null) {
            String fileDirConfig = config.getFiledir();
            // 自定义目录
            if (!x.isEmpty(fileDirConfig)) {
                // 按时间自动分目录/
                if (fileDirConfig.indexOf("{") != -1) {
                    s = parseTimePath(fileDirConfig);
                } else {
                    // 固定目录
                    s = fileDirConfig;
                }
            }
        }

        // 默认目录
        if (s == null && !x.isEmpty(defaultDir)) {
            s = defaultDir;
        }

        // 兜底的临时目录(既没有自定义目录, 也没有缺省目录)
        if (s == null) {
            s = x.conf.get("eova_upload_temp", "/");
        }

        // 格式化路径
        return FileUtil.formatPath(s);
    }

    /**
     * <pre>
     * 构建保存的文件名
     * 1.固定文件名
     * 2.保持原文件名(慎用)
     * 3.按时间格式策略分目录
     * 4.随机文件名(默认)
     * </pre>
     * @param config 元字段配置
     * @param file 上传的文件
     * @return
     */
    private static String buildFileName(MetaFieldConfig config, UploadFile file) {
        if (config != null) {
            // 文件名配置
            String fileNameConfig = config.getFilename();
            if (!x.isEmpty(fileNameConfig)) {
                // 原文件名_时间戳(用于保持原文件+不重名)
                if (fileNameConfig.equals("ORIGINAL_TIME")) {
                    return System.currentTimeMillis() + "@" + file.getOriginalFileName();
                }
                // 保持原文件名(保持原文件名, 重名的覆盖)
                if (fileNameConfig.equals("ORIGINAL")) {
                    return file.getOriginalFileName();
                }
                // 固定文件名
                return fileNameConfig;
            }
        }

        // 默认随机文件名(增量随机数防止并发重名异常)
        return System.currentTimeMillis() + RandomUtil.randomNumbers(5) + FileUtil.getFileType(file.getFileName());
    }

}