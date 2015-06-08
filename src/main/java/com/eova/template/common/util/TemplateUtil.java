/**
 * Copyright (c) 2013-2015, Jieven. All rights reserved.
 *
 * Licensed under the GPL license: http://www.gnu.org/licenses/gpl.txt
 * To use it on other terms please contact us at 1623736450@qq.com
 */
package com.eova.template.common.util;

import java.util.ArrayList;
import java.util.List;

import com.eova.model.MetaItem;
import com.jfinal.plugin.activerecord.Record;

public class TemplateUtil {

	/**
	 * 获取键值对Map
	 * 
	 * @param itemList 字段集
	 * @param list 结果集
	 * @return
	 */
	@Deprecated
	public static List<List<String>> getValueList(List<MetaItem> itemList, List<Record> dataList) {
		List<List<String>> list = new ArrayList<List<String>>();
		// 获取字段的值
		for (Record record : dataList) {
			List<String> temp = new ArrayList<String>();
			// 获取字段名
			for (MetaItem item : itemList) {
				temp.add(record.get(item.getStr("en")).toString());
			}
			list.add(temp);
		}
		return list;
	}

	/**
	 * 获取字段名集合
	 * 
	 * @param itemList 对象详情集合
	 * @return
	 */
	@Deprecated
	public static List<String> getKeyList(List<MetaItem> itemList) {
		List<String> list = new ArrayList<String>();
		for (MetaItem item : itemList) {
			list.add(item.getStr("en"));
		}
		return list;
	}
}