/**
 * Copyright (c) 2013-2015, Jieven. All rights reserved.
 *
 * Licensed under the GPL license: http://www.gnu.org/licenses/gpl.txt
 * To use it on other terms please contact us at 1623736450@qq.com
 */
package com.eova.template.common.util;

import java.util.List;

import com.eova.model.MetaItem;
import com.eova.model.MetaObject;
import com.jfinal.plugin.activerecord.Record;

/**
 * UI生成工具类
 * 
 * @author Jieven
 * @date 2013-1-2
 */
public class UIUtil {

	/**
	 * 获取Table内容部分
	 * 
	 * @param obj 对象
	 * @param itemList 字段集合
	 * @param dataList 数据集合
	 * @return
	 */
	public static String getTableBody(MetaObject obj, List<MetaItem> itemList, List<Record> dataList) {
		StringBuilder sb = new StringBuilder();

		// 遍历数据集
		for (Record record : dataList) {
			// 主键
			String pkName = obj.get("pkName");
			// 获取当前数据对象的主键值
			String pkValue = record.get(pkName).toString();

			sb.append("<tr target=\"" + pkName + "\" rel=\"" + pkValue + "\">");
			// 是否允许批量删除
			boolean isBatchDelete = obj.get("isBatchDelete");
			if (isBatchDelete) {
				sb.append("<td><input name=\"ids\" \" type=\"checkbox\" value=\"" + pkValue + "\"></td>");
			}

			for (MetaItem item : itemList) {
				// 是否显示字段
				boolean isShow = item.getBoolean("isShow");
				if (isShow) {
					String itemKey = item.getStr("en");
					sb.append("<td>" + record.get(itemKey).toString() + "</td>");
				}
			}
			sb.append("</tr>");
		}

		return sb.toString();
	}

	public static String getToolButton() {
		return "";

	}
}