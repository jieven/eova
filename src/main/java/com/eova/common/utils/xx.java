package com.eova.common.utils;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * What：高频常用方法集合<br>
 * Where：xx.xxx简短快捷的输入操作<br>
 * Why：整合高频常用方法,编码速度+50%,代码量-70%
 * @author Jieven
 * 
 */
public class xx {
	
	/**默认数据源名称**/
	public static final String DS_MAIN = "main";
	/**EOVA数据源名称**/
	public static final String DS_EOVA = "eova";
	
	/**
	 * 对象是否为空
	 * @param obj String,List,Map,Object[],int[],long[]
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static boolean isEmpty(Object o) {
		if (o == null) {
			return true;
		}
		if (o instanceof String) {
			if (o.toString().trim().equals("")) {
				return true;
			}
		} else if (o instanceof List) {
			if (((List) o).size() == 0) {
				return true;
			}
		} else if (o instanceof Map) {
			if (((Map) o).size() == 0) {
				return true;
			}
		} else if (o instanceof Set) {
			if (((Set) o).size() == 0) {
				return true;
			}
		} else if (o instanceof Object[]) {
			if (((Object[]) o).length == 0) {
				return true;
			}
		} else if (o instanceof int[]) {
			if (((int[]) o).length == 0) {
				return true;
			}
		} else if (o instanceof long[]) {
			if (((long[]) o).length == 0) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 对象组中是否存在 Empty Object
	 * @param os 对象组
	 * @return
	 */
	public static boolean isOneEmpty(Object... os) {
		for (Object o : os) {
			return isEmpty(o);
		}
		return false;
	}

	/**
	 * 对象组中是否全是 Empty Object
	 * @param os
	 * @return
	 */
	public static boolean isAllEmpty(Object... os) {
		for (Object o : os) {
			if (!isEmpty(o)) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * 是否为数字
	 * @param obj
	 * @return
	 */
	public static boolean isNum(Object obj) {
		try {
			Integer.parseInt(obj.toString());
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	/**
	 * 格式化字符串->'str'
	 * @param str
	 * @return
	 */
	public static String format(Object str) {
		return "'" + str.toString() + "'";
	}

	/**
	 * 强转->int
	 * @param obj
	 * @return
	 */
	public static int toInt(Object obj) {
		return Integer.parseInt(obj.toString());
	}

	/**
	 * 强转->int
	 * @param obj
	 * @param defaultValue
	 * @return
	 */
	public static int toInt(Object obj, int defaultValue) {
		if (isEmpty(obj)) {
			return defaultValue;
		}
		return toInt(obj);
	}

	/**
	 * 强转->long
	 * @param obj
	 * @return
	 */
	public static long toLong(Object obj) {
		return Long.parseLong(obj.toString());
	}

	/**
	 * 强转->long
	 * @param obj
	 * @param defaultValue
	 * @return
	 */
	public static long toLong(Object obj, long defaultValue) {
		if (isEmpty(obj)) {
			return defaultValue;
		}
		return toLong(obj);
	}

	/**
	 * 强转->double
	 * @param obj
	 * @return
	 */
	public static double toDouble(Object obj) {
		return Double.parseDouble(obj.toString());
	}

	/**
	 * 判断字符串是否存在
	 * @param str 原始字符串
	 * @param target 目标字符
	 * @return
	 */
	public static boolean contains(String str, String target){
		if (str.indexOf(target) != -1) {
			return true;
		}
		return false;
	}
}