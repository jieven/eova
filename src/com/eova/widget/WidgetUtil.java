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

import com.eova.config.PageConst;
import com.jfinal.kit.JsonKit;
import com.jfinal.plugin.activerecord.Record;
import com.rzyunyou.common.xx;

public class WidgetUtil {
	/**
	 * 创建主键列副本，原主键列可能被自动翻译成了字典值，如需使用唯一标识，使用主键副本的值即可。
	 * @param reList Grid数据集
	 * @param pkName 当前Eova_Object的主键名
	 */
	public static void copyPkValue(List<Record> reList, String pkName) {
		// 复制主键列
		for (Record re : reList) {
			re.set(PageConst.GRID_PK, re.get(pkName).toString());
			// System.out.println("pk:"+re.getStr("pk") );
		}
	}

	/**
	 * 转化为Tree所需JSON结构
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
			Boolean isCollapse = x.getBoolean("isCollapse");
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
}