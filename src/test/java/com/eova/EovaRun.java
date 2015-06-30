/**
 * Copyright (c) 2013-2015, Jieven. All rights reserved.
 *
 * Licensed under the GPL license: http://www.gnu.org/licenses/gpl.txt
 * To use it on other terms please contact us at 1623736450@qq.com
 */
package com.eova;

import java.io.File;
import java.net.ConnectException;

import net.lingala.zip4j.exception.ZipException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.eova.common.utils.string.CompressUtil;
import com.eova.common.utils.string.DownloadUtils;
import com.eova.constant.SystemConfig;
import com.eova.constant.SystemTips;
import com.jfinal.core.JFinal;

public class EovaRun {
	/** 日志打印对象 **/
	private static final Logger log = LoggerFactory.getLogger(EovaRun.class);

	/**
	 * Run as Eova Application
	 */
	public static void main(String[] args) {
		log.info(SystemTips.TIPS_PROJECT_START);
		preStart();
		
		JFinal.start("src/main/webapp", 520, "/", 5);
	}

	/**
	 * 加载static资源
	 */
	private static void preStart() {
		// 取得根目录路径 .../eova/src/main/webapp/WEB-INF/classes/
		String rootPath = EovaRun.class.getResource("/").getFile().toString();
		// 处理路径为 .../eova/src/main/webapp/
		String filePath = rootPath.substring(1, rootPath.length() - 16);
		// 下载文件为zip文件
		downloadFile(filePath);
		// 解压文件
		unzipFile(filePath);
		// 删除压缩文件
		delZipFile(filePath);

	}

	/**
	 * 删除压缩文件
	 * 
	 * @param filePath 压缩文件路径
	 */
	private static void delZipFile(String filePath) {
		File zipfile = new File(filePath + SystemConfig.FILENAME
				+ SystemConfig.POSTFIX);
		if (zipfile.exists()) {
			log.info(SystemTips.TIPS_STATIC_DEL);
			zipfile.delete();
			log.info(SystemTips.TIPS_LOAD_STATIC_SUCCESS);
		}

	}

	/**
	 * 解压缩文件
	 * 
	 * @param filePath zip文件路径
	 */
	private static void unzipFile(String filePath) {
		try {
			// 查检文件是否存在 //static资源包更新 会存在bug
			if (checkFileEmpty(filePath + SystemConfig.FILENAME)) {
				log.info(SystemTips.TIPS_FILE_EXIST2);
				log.info(SystemTips.TIPS_LOAD_STATIC_SUCCESS);
				return;
			}
			// 判断压缩文件是否存在 不存在返回
			if (!checkFileEmpty(filePath + SystemConfig.FILENAME
					+ SystemConfig.POSTFIX)) {
				log.info("静态资源不存在");
				// 下载zip文件
				// downloadFile(filePath);
				return;
			}
			log.info(SystemTips.TIPS_UNZIPPING);
			// 解压
			CompressUtil.unzip(filePath + SystemConfig.FILENAME
					+ SystemConfig.POSTFIX, filePath, null);
			log.info(SystemTips.TIPS_UNZIP_SUCCESS);
		} catch (ZipException e) {
			log.error(SystemTips.TIPS_UNZIP_FAIL);
		}

	}

	/**
	 * 下载static资源
	 * 
	 * @param filePath 下载文件路径
	 */
	private static void downloadFile(String filePath) {
		// 是否存在
		try {
			// 查检文件是否存在
			if (checkFileEmpty(filePath + SystemConfig.FILENAME)) {
				log.info(SystemTips.TIPS_FILE_EXIST1);
				return;
			}
			log.info(SystemTips.TIPS_DOWNLOADING);
			// 下载文件
			DownloadUtils.download(SystemConfig.FILEPATH, filePath
					+ SystemConfig.FILENAME + SystemConfig.POSTFIX);
			log.info(SystemTips.TIPS_DOWNLOAD_SUCCESS + filePath
					+ SystemConfig.FILENAME + SystemConfig.POSTFIX);
		} catch (ConnectException e) {
			log.error(SystemTips.TIPS_CONNECTEXCEPTION);
		} catch (Exception e) {
			log.error(SystemTips.TIPS_DOWNLOAD_FAIL);
			// e.printStackTrace();
		}

	}

	/**
	 * 查检文件是否存在
	 * 
	 * @param file 文件路径
	 * @return 如果 存在 并且不为空则返回true
	 */
	private static boolean checkFileEmpty(String file) {
		// 判断目录下面是否已经存在此文件，并且不为空
		File unZipFile = new File(file);
		// 如果存在 并且 长度大于0 不用解压了
		if (unZipFile.exists() && unZipFile.length() > 0) {
			return true;
		}
		return false;
	}
}