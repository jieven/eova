/**
 * Copyright (c) 2013-2015, Jieven. All rights reserved.
 *
 * Licensed under the GPL license: http://www.gnu.org/licenses/gpl.txt
 * To use it on other terms please contact us at 1623736450@qq.com
 */
package com.eova.widget;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.eova.common.utils.xx;
import com.eova.model.MetaField;
import com.jfinal.kit.JsonKit;
import com.jfinal.plugin.activerecord.Record;

public class WidgetUtil {
	/**
	 * 原值被自动转成了中文显示，所以转换前先备份值为"字段_val"
	 * 
	 * @param reList Grid数据集
	 * @param pkName 当前Eova_Object的主键名
	 */
	public static void copyValueColumn(List<Record> reList, String pkName, List<MetaField> eis) {
		// 复制主键列
		for (Record re : reList) {
			for (MetaField x : eis) {
				// 如果有表达式，说明会被翻译，所以需要备份列
				String exp = x.getStr("exp");
				if (!xx.isEmpty(exp)) {
					String en = x.getEn();
					// 备份被转换的列的值
					re.set(en + "_val", re.get(en).toString());
				}
			}
			// 复制主键值列
			re.set("pk_val", re.get(pkName).toString());
		}
	}

	/**
	 * 转化为Tree所需JSON结构
	 * 
	 * @param reList
	 * @return
	 */
	public static String toTreeJson(List<Record> reList) {
		// 将List变成 key-value的Map(使用Linked保证排序)
		LinkedHashMap<Integer, Record> temp = new LinkedHashMap<Integer, Record>();
		for (Record x : reList) {
			// 获取EasyUI所需ICON字段名
			x.set("iconCls", x.get("icon"));
			// 是否默认折叠
			String state = "open";
			Boolean isCollapse = x.getBoolean("is_collapse");
			if (!xx.isEmpty(isCollapse) && isCollapse) {
				state = "closed";
			}
			x.set("state", state);
			temp.put(x.getInt("id"), x);
		}
		// 添加根节点
		temp.put(0, new TreeNode());

		// 将temp整理成Tree结构
		for (Map.Entry<Integer, Record> map : temp.entrySet()) {
			// 跳过Root节点
			if (map.getKey() == 0) {
				continue;
			}

			Record re = map.getValue();
			Integer pid = re.getInt("parentId");

			Record pr = temp.get(pid);
			// 获取父节点子集
			List<Record> childList = pr.get("children");
			if (childList == null) {
				childList = new ArrayList<Record>();
				temp.get(pid).set("children", childList);
			}
			// 将当前节点加入父节点子集
			childList.add(re);
		}

		// 组装Tree Json
		StringBuilder sb = new StringBuilder("[");
		// 获取根节点
		List<Record> childList = temp.get(0).get("children");
		for (Record x : childList) {
			sb.append(JsonKit.toJson(x));
			sb.append(",");
		}
		sb.delete(sb.length()-1, sb.length());
		sb.append("]");
		System.out.println(sb.toString());
		return sb.toString();
	}
	
	/**
	 * 转化为Tree所需JSON结构
	 * 
	 * @param reList
	 * @return
	 */
	public static String maptoTreeJsons(LinkedHashMap<Integer, Record> temp) {
		for (Map.Entry<Integer, Record> map : temp.entrySet()) {
			Record x = map.getValue();
			// 是否默认折叠
			String state = "open";
			Boolean isCollapse = x.getBoolean("is_collapse");
			if (!xx.isEmpty(isCollapse) && isCollapse) {
				state = "closed";
			}
			x.set("state", state);
			x.set("iconCls", x.getStr("icon"));
		}
		
		// 添加根节点
		temp.put(0, new TreeNode());

		// 将temp整理成Tree结构
		for (Map.Entry<Integer, Record> map : temp.entrySet()) {
			
			// 跳过Root节点
			if (map.getKey() == 0) {
				continue;
			}

			Record re = map.getValue();
			Integer pid = re.getInt("parentId");

			Record pr = temp.get(pid);
			// 获取父节点子集
			List<Record> childList = pr.get("children");
			if (childList == null) {
				childList = new ArrayList<Record>();
				temp.get(pid).set("children", childList);
			}
			// 将当前节点加入父节点子集
			childList.add(re);
		}

		// 组装Tree Json
		StringBuilder sb = new StringBuilder("[");
		// 获取根节点
		List<Record> childList = temp.get(0).get("children");
		for (Record x : childList) {
			sb.append(JsonKit.toJson(x));
			sb.append(",");
		}
		sb.delete(sb.length()-1, sb.length());
		sb.append("]");
		System.out.println(sb.toString());
		
		// 大小写与EasyUI兼容问题：
		String json = sb.toString();
		json = json.replaceAll("iconcls", "iconCls");
		
		return json;
	}
	
}