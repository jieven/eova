/**
 * Copyright (c) 2015-2026 EOVA.CN. All rights reserved.
 * Licensed under the LGPL-3.0 license
 * For authorization, please contact: admin@eova.cn
 */
package cn.eova.common.utils.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

import cn.eova.tools.x;

/**
 * 图片工具类
 *
 * @author Jieven
 * @date 2014-5-12
 */
public class ImageUtil {

    public static void main(String[] args) {
        System.out.println(isImage("c:\\1.png"));
        System.out.println(isImage("c:\\1.gif"));
        System.out.println(isImage("c:\\1.jpg"));
        System.out.println(isImage("c:\\1.bmp"));
    }

    /**
     * 是否为标准规范图片(仅支持jpg|gif|png|bmp)
     * @param imagePath 图片路径
     * @return 是否图片
     */
    public static boolean isImage(String imagePath) {

        // 可配置关闭检查, 默认开启
        boolean checkHex = x.conf.getBool("upload_check_hex", true);
        if (!checkHex) {
            return true;
        }

        InputStream is = null;
        try {
            // 验证图片格式是否正确
            boolean isImg = FileUtil.checkFileType(imagePath, true);
            if (isImg) {
                // 验证图片头是否规范
                File image = new File(imagePath);
                is = new FileInputStream(image);
                byte[] bt = new byte[2];
                is.read(bt);
                // 获取16进制文件头
                String hex = bytesToHex(bt);
                String fileType = FileUtil.getFileType(imagePath).toLowerCase();

                String[] types = {".jpg", ".jpeg", ".gif", ".png", ".bmp"};
                String[] hexs = {"ffd8", "4749", "8950", "424d"};
                // 判断图片, 必须是常用4种图片格式
                if (Arrays.asList(types).contains(fileType) && Arrays.asList(hexs).contains(hex)) {
                    return true;
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     * 获取前2个16进制数
     * @param bt
     * @return
     */
    private static String bytesToHex(byte[] bt) {
        StringBuilder stringBuilder = new StringBuilder();
        if (bt == null || bt.length <= 0) {
            return null;
        }
        for (int i = 0; i < bt.length; i++) {
            int v = bt[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }

}