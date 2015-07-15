/**
 * Copyright (c) 2013-2015, Jieven. All rights reserved.
 *
 * Licensed under the GPL license: http://www.gnu.org/licenses/gpl.txt
 * To use it on other terms please contact us at 1623736450@qq.com
 */
package com.eova.template.common.util;

import java.util.ArrayList;
import java.util.List;

import com.eova.common.utils.xx;
import com.eova.model.MetaField;
import com.eova.template.common.config.TemplateConfig;
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
	public static List<List<String>> getValueList(List<MetaField> itemList, List<Record> dataList) {
		List<List<String>> list = new ArrayList<List<String>>();
		// 获取字段的值
		for (Record record : dataList) {
			List<String> temp = new ArrayList<String>();
			// 获取字段名
			for (MetaField item : itemList) {
				temp.add(record.get(item.getEn()).toString());
			}
			list.add(temp);
		}
		return list;
	}
	
	/**
	 * 值的类型转换
	 * @param item 元字段
	 * @param value
	 * @return
	 */
	public static Object convertValue(MetaField item, Object value){
		// 控件类型
		String type = item.getStr("type");
		// 数据类型
		String dataType = item.getStr("data_type");
		// 复选框需要特转换值
		if (type.equals(MetaField.TYPE_CHECK)) {
			if (xx.isEmpty(value)) {
				return "0";
			} else {
				return "1";
			}
		}
		
		// Oracle Date格式化
		if (xx.isOracle() && dataType.equals(TemplateConfig.DATATYPE_TIME)) {
			return java.sql.Timestamp.valueOf(value.toString());
		}
		return value;
	}
	
}