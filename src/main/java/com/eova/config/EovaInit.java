/**
 * Copyright (c) 2013-2015, Jieven. All rights reserved.
 * <p/>
 * Licensed under the GPL license: http://www.gnu.org/licenses/gpl.txt
 * To use it on other terms please contact us at 1623736450@qq.com
 */
package com.eova.config;

import java.io.File;
import java.io.IOException;

import com.eova.common.utils.db.DbUtil;
import com.eova.common.utils.io.FileUtil;
import com.eova.common.utils.io.NetUtil;
import com.eova.common.utils.io.ZipUtil;
import com.jfinal.kit.PathKit;

public class EovaInit {

	/**
	 * 异步初始化JS插件包<br>
	 * 1.通过网络自动下载plugins.zip <br>
	 * 2.解压到webapp/plugins/ <br>
	 * 3.删除下载临时文件 <br>
	 */
	public static void initPlugins() {
		// 异步下载插件包
		Thread t = new Thread() {
			@Override
			public void run() {
				try {
					// 下载到Web根目录
					String zipPath = EovaConst.DIR_WEB + "plugins.zip";

					if (!FileUtil.isExists(EovaConst.DIR_PLUGINS)) {
						System.err.println("正在下载：" + EovaConst.PLUGINS_URL);
						NetUtil.download(EovaConst.PLUGINS_URL, zipPath);

						System.err.println("开始解压：" + zipPath);
						ZipUtil.unzip(zipPath, EovaConst.DIR_PLUGINS, null);
						System.err.println("已解压到：" + EovaConst.DIR_PLUGINS);

						FileUtil.delete(zipPath);
						System.err.println("清理下载Zip");
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};
		t.start();
	}

	public static void initCreateSql() {
		// 异步下载插件包
		Thread t = new Thread() {
			@Override
			public void run() {
				System.out.println("正在生成Oracle Sql ing...");
				DbUtil.createOracleSql();
				System.out.println("生成成功:/sql/oracle/*.sql");
			}
		};
		t.start();
	}

	public static void main(String[] args) throws IOException {
		System.out.println(PathKit.getRootClassPath());
		System.out.println(PathKit.getWebRootPath());
		System.out.println(new File(PathKit.getWebRootPath()).getParentFile().getParentFile().getCanonicalPath());
		System.out.println(PathKit.class.getResource("/").getFile());
	}
}