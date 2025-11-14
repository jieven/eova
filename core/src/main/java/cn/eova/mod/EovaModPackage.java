/**
 * Copyright (c) 2015-2026 EOVA.CN. All rights reserved.
 * Licensed under the LGPL-3.0 license
 * For authorization, please contact: admin@eova.cn
 */
package cn.eova.mod;

import java.io.File;
import java.io.IOException;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.ZipUtil;
import com.jfinal.kit.PathKit;

/**
 * Eova Mod Jar 打包工具
 * @author Jieven
 *
 */
public class EovaModPackage {
    public static void start(String group, String code, String version) {
        System.out.println("Eova Mod Package ing...");
        System.out.println();

        String classPath = PathKit.getRootClassPath();
        String projectPath = classPath.replace("\\target\\classes", "");
        System.out.println("Project path " + projectPath);
        System.out.println();

        try {
            //			@SuppressWarnings("resource")
//			Scanner input = new Scanner(System.in);
            //			System.out.println("Please enter the group code：(eg.jieven)");
            //			String group = input.next();

            //			System.out.println("Please enter the app code：(eg.batch)");
            //			String code = input.next();

            //			System.out.println("Please enter the app version：(eg.1.0.0)");
            //			String version = input.next();

            //			System.out.println();

            // 创建临时打包文件夹
            File temp = cn.eova.common.utils.io.FileUtil.createTempDir();
            System.out.println("Create temp " + temp.getPath());

            // 复制class
            String a = String.format("%s/com/eova/mod/%s/%s", classPath, group, code).replace("/", File.separator);
            String ta = String.format("%s/com/eova/mod/%s", temp.getPath(), group);// 上面已经包含 应用编码
            System.out.println("Copying class " + a);
            FileUtil.copy(a, ta, true);

            // 复制前端
            String b = String.format("%s/src/main/webapp/_mod/%s/%s", projectPath, group, code).replace("/", File.separator);
            if (cn.eova.common.utils.io.FileUtil.isDir(b)) {
                String tb = String.format("%s/%s", temp.getPath(), group);// 上面已经包含 应用编码
                FileUtil.copy(b, tb, true);
                System.out.println("Copying resource " + b);
            }

            // 打包
            File zip = ZipUtil.zip(temp);

            // 输出jar
            String jar = String.format("%s/mod/%s-%s-%s.jar", projectPath, group, code, version).replace("/", File.separator);
            FileUtil.move(zip, new File(jar), true);

            System.err.println();
            System.err.println("BUILD SUCCESS " + jar);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}