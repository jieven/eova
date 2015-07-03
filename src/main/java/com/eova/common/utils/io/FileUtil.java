package com.eova.common.utils.io;

import java.io.File;

/**
 * 文件操作工具类
 * 
 * @author Jieven
 * 
 */
public class FileUtil {

	public static boolean isExists(String path) {
		File file = new File(path);
		if (file.exists() && file.length() > 0) {
			return true;
		}
		return false;
	}

	public static void delete(String path) {
		delete(new File(path));
	}

	public static void delete(File file) {
		if (file.exists()) {
			file.delete();
		}
	}
}
