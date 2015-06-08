package com.eova.common.utils.db;

/**
 * Mysql工具类
 *
 * @author Jieven
 * @date 2014-9-12
 */
public class MysqlUtil {

	/**
	 * 获取JDBC URL中的数据库名
	 * @param url
	 * @return
	 */
	public static String getDbNameByUrl(String url) {
		url = url.substring(url.lastIndexOf('/') + 1, url.lastIndexOf('?'));
		return url;
	}

	public static void main(String[] args) {
		String url = "jdbc:mysql://127.0.0.1/yygms?characterEncoding=UTF-8&zeroDateTimeBehavior=convertToNull";
		System.out.println(getDbNameByUrl(url));;
	}
}
