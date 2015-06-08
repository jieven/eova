/**
 * Copyright (c) 2013-2015, Jieven. All rights reserved.
 *
 * Licensed under the GPL license: http://www.gnu.org/licenses/gpl.txt
 * To use it on other terms please contact us at 1623736450@qq.com
 */
package com.eova.template.crud;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.eova.common.utils.xx;
import com.eova.config.PageConst;
import com.eova.model.MetaItem;
import com.eova.model.MetaObject;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

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
	public static Map<String, Record> buildData(Controller c, List<MetaItem> eis, Record record, String pkName, boolean isInsert) {
		Map<String, Record> reMap = new HashMap<String, Record>();

		// 获取字段当前的值
		for (MetaItem item : eis) {
			// 控件类型
			String type = item.getStr("type");
			// 字段名
			String key = item.getStr("en");
			// 获当前字段更新后的值,默认空值
			String value = c.getPara(key, "");

			// 新增跳过自增长字段(新增时为空)
			if (xx.isEmpty(value) && type.equals(MetaItem.TYPE_AUTO)) {
				continue;
			}
			
			// 新增时，移除禁止新增的字段
			boolean isAdd = item.getBoolean("isAdd");
			if (isInsert && !isAdd) {
				record.remove(key);
				continue;
			}
			// 更新时，移除禁止更新的字段
			boolean isUpdate = item.getBoolean("isUpdate");
			if (!isInsert && !isUpdate) {
				record.remove(key);
				continue;
			}

			// 复选框需要特转换值
			if (type.equals(MetaItem.TYPE_CHECK)) {
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
		MetaObject eo = MetaObject.dao.getByCode(objectCode);

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
	 * 更新/插入 View
	 * 
	 * @param viewPkName 视图主键名
	 * @param reMap 视图对象集
	 * @param isUpdate 是否更新操作
	 */
	@SuppressWarnings("rawtypes")
	public static void operateView(String viewPkName, Map<String, Record> reMap, String operate) {
		// 主键值
		Object pkValue = null;

		// 获取主键值
		Iterator iter = reMap.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry entry = (Map.Entry) iter.next();
			Record record = (Record) entry.getValue();
			// 如果当前对象存在主键字段，说明是主对象
			List cols = Arrays.asList(record.getColumnNames());
			if (cols.contains(viewPkName)) {
				pkValue = record.get(viewPkName);
			}
		}

		// 获取主对象
		iter = reMap.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry entry = (Map.Entry) iter.next();
			Record record = (Record) entry.getValue();

			// 获取对象信息
			String objectCode = entry.getKey().toString();
			MetaObject eo = MetaObject.dao.getByCode(objectCode);

			// 设置主键值
			record.set(eo.getPk(), pkValue);

			if (operate.equals(CrudConfig.UPDATE)) {
				// 更新
				Db.use(eo.getDs()).update(eo.getTable(), eo.getPk(), record);
			} else if (operate.equals(CrudConfig.ADD)) {
				// 新增
				Db.use(eo.getDs()).save(eo.getTable(), eo.getPk(), record);
				// 新增之后产生主键值
				pkValue = record.get(eo.getPk());
			}
		}
	}

	/**
	 * 自动删除视图关联对象数据
	 * 
	 * @param objectCode 视图对象Code
	 * @param pkValue 删除选中值
	 */
	public static void deleteView(String objectCode, String pkValue) {

		// 查询视图所属包含的对象Code
		List<MetaItem> poCodes = MetaItem.dao.queryPoCodeByObjectCode(objectCode);
		for (MetaItem x : poCodes) {
			// 获取持久化源对象Code
			String poCode = x.getStr("poCode");
			MetaObject eo = MetaObject.dao.getByCode(poCode);
			Db.use(eo.getDs()).deleteById(eo.getTable(), eo.getPk(), pkValue);
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