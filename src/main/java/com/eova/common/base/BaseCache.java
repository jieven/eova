package com.eova.common.base;

import com.jfinal.plugin.ehcache.CacheKit;

/**
 * Cache 公共实现
 * @author Jieven
 *
 */
public class BaseCache {

	/**本系统默认CacheName-空闲30Min超时,60Min有效,最少使用策略**/
	public static final String SYS = "sysCache";
	/**Service CacheName-30s有效,最近最少使用策略**/
	public static final String SER = "serviceCache";
	/**Service CacheName-30min有效,最近最少使用策略**/
	public static final String BASE = "baseCache";
	/**Player CacheName-空闲30Min超时,永久有效,最少使用策略**/
	public static final String PLAYER = "playerCache";

	// System Cache方法
	public static Object get(String key) {
		return CacheKit.get(SYS, key);
	}
	public static void put(String key, Object value) {
		CacheKit.put(SYS, key, value);
	}
	public static void del(String key) {
		CacheKit.remove(SYS, key);
	}

	// Service Cache方法
	public static Object getSer(String key) {
		return CacheKit.get(SER, key);
	}
	public static void putSer(String key, Object value) {
		CacheKit.put(SER, key, value);
	}
	public static void delSer(String key) {
		CacheKit.remove(SER, key);
	}
}
