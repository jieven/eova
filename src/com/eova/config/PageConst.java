/**
 * Copyright (c) 2013-2015, Jieven. All rights reserved.
 *
 * Licensed under the GPL license: http://www.gnu.org/licenses/gpl.txt
 * To use it on other terms please contact us at 1623736450@qq.com
 */
package com.eova.config;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import org.beetl.ext.jfinal.BeetlRenderFactory;

/**
 * 页面常量
 * 
 * @author Jieven
 * 
 */
public class PageConst {

	/** 表单名-当前页码 **/
	public static final String PAGENUM = "page";
	/** 表单名-显示数量 **/
	public static final String PAGESIZE = "rows";
	/** 表单名-表单排序Key **/
	public static final String SORT = "sort";
	/** 表单名-表单排序方式Key **/
	public static final String ORDER = "order";
	/** 表单名-查询表单前缀 **/
	public static final String QUERY = "query_";
	/** Griad主键名-原主键列的副本 **/
	public static final String GRID_PK = "grid_pk";

	/**
	 * 系统启动初始化加载 将常量全局化
	 */
	public static void init() {
		long time = System.currentTimeMillis();
		System.err.println("Load FreeMarker Const Starting:");
		Field[] fds = PageConst.class.getDeclaredFields();
		Map<String, Object> sharedVars = new HashMap<String, Object>();
		for (Field fd : fds) {
			String key = fd.getName();
			try {
				// 设置为FreeMarker全局变量
				// FreeMarkerRender.getConfiguration().setSharedVariable(key, fd.get(key).toString());
				// JFinal.me().getServletContext().setAttribute(key, fd.get(key).toString());
				// 设置全局变量
				sharedVars.put(key, fd.get(key).toString());
				System.err.println(key + " = [" + fd.get(null).toString() + "]");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		BeetlRenderFactory.groupTemplate.setSharedVars(sharedVars);
		System.err.println("Load Cost Time:" + (System.currentTimeMillis() - time) + "ms\n");
	}
}