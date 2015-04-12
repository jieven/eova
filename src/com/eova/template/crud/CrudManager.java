/**
 * Copyright (c) 2013-2015, Jieven. All rights reserved.
 *
 * Licensed under the GPL license: http://www.gnu.org/licenses/gpl.txt
 * To use it on other terms please contact us at 1623736450@qq.com
 */
package com.eova.template.crud;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.eova.config.PageConst;
import com.eova.model.Eova_Item;
import com.eova.model.Eova_Object;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.rzyunyou.common.xx;

/**
 * CRUD模板 业务
 * 
 * @author Jieven
 * 
 */
public class CrudManager {

	/**
	 * 通过Form构建数据
	 * 
	 * @param c 控制器
	 * @param eis 对象属性
	 * @param record 主对象数据集
	 * @param pkName 主键字段名
	 * @return 其它对象数据集
	 */
	public static Map<String, Record> buildData(Controller c, List<Eova_Item> eis, Record record, String pkName) {
		Map<String, Record> reMap = new HashMap<String, Record>();
		// 获取字段当前的值
		for (Eova_Item item : eis) {
			// 控件类型
			String type = item.getStr("type");
			// 字段名
			String key = item.getStr("en");
			// 获当前字段更新后的值,默认空值
			String value = c.getPara(key, "");
			
			// 新增跳过自增长字段(新增时为空)
			if (xx.isEmpty(value) && type.equals(Eova_Item.TYPE_AUTO)) {
				continue;
			}
			// 复选框需要特转换值
			if (type.equals(Eova_Item.TYPE_CHECK)) {
				if (xx.isEmpty(value)) {
					value = "0";
				} else {
					value = "1";
				}
			}

			// 当前字段的持久化对象
			String objectCode = item.getStr("poCode");
			// 当前字段的持久化关联字段
			if (!xx.isEmpty(objectCode)) {
				Record re = reMap.get(objectCode);
				if (re == null) {
					re = new Record();
				}
				re.set(key, value);
				reMap.put(objectCode, re);
				continue;
			}
			record.set(key, value);
		}
		return reMap;
	}

	/**
	 * 更新对象(只能根据主对象主键值更新关联对象数据)
	 * 
	 * @param objectCode 对象编码
	 * @param reMap 对象Map
	 * @param pkValue 主对象主键值
	 */
	public static void updateRecordByCode(String objectCode, Map<String, Record> reMap, Object pkValue, boolean isUpdate) {
		Eova_Object eo = Eova_Object.dao.getByCode(objectCode);
		Record re = reMap.get(objectCode);
		// 设置主键值
		String pkName = eo.getStr("pkName");
		re.set(pkName, pkValue);
		// 保存的数据值
		String table = eo.getStr("table");
		// 主键是否有值
		if (isUpdate) {
			// 更新数据到对应的表
			Db.use(eo.getStr("dataSource")).update(table, pkName, re);
		} else {
			// 保存数据到对应的表
			Db.use(eo.getStr("dataSource")).save(table, pkName, re);
		}
	}

	/**
	 * 获得排序参数
	 * 
	 * @param orderField 本次需要排序的字段
	 * @param orderLast 上次排序记录
	 * @return
	 */
	public static String getOrder(Controller c, Crud crud, String orderField, String orderLast) {
		String order = "";
		if (!orderField.equals("")) {
			String nowOrder = orderField + " desc";
			// 如果上次排序和本次排序不同 || 上次未排序
			if ((!orderLast.equals(nowOrder)) || orderLast.equals("")) {
				order = " order by " + nowOrder;
				// 保存上一次排序方式
				c.setAttr("orderLast", nowOrder);
				c.keepPara("orderField");
			}
		}
		// 默认根据主键从小到大排列
		if (order.equals("") && crud.getObject().getBoolean("isDefaultPkDesc")) {
			return " order by " + crud.getObject().getStr("pkName") + " desc";
		}
		return order;
	}

	/**
	 * 获取排序
	 * 
	 * @param c Controller
	 * @param crud CRUDVO
	 * @return
	 */
	public static String getSort(Controller c, Crud crud) {
		// 获取排序字段
		String sort = c.getPara(PageConst.SORT, "");
		if (xx.isEmpty(sort)) {
			// 初始默认主键排序
			if (xx.isEmpty(sort) && crud.getObject().getBoolean("isDefaultPkDesc")) {
				return " order by " + crud.getPkName() + " desc";
			}
			return " ";
		}
		// 获取排序方式
		String order = c.getPara(PageConst.ORDER, "");
		return " order by " + sort + ' ' + order;
	}

}