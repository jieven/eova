/**
 * Copyright (c) 2013-2015, Jieven. All rights reserved.
 *
 * Licensed under the GPL license: http://www.gnu.org/licenses/gpl.txt
 * To use it on other terms please contact us at 1623736450@qq.com
 */
package com.eova.widget;

import java.util.ArrayList;
import java.util.List;

import com.eova.config.PageConst;
import com.eova.engine.EovaExp;
import com.eova.model.Eova_Item;
import com.eova.model.Eova_Object;
import com.eova.template.common.config.TemplateConfig;
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
public class WidgetManager {

	/**
	 * 获取排序
	 * @param c Controller
	 * @param crud CRUDVO
	 * @return
	 */
	public static String getSort(Controller c, Eova_Object eo) {
		// 获取排序字段
		String sort = c.getPara(PageConst.SORT, "");
		if (xx.isEmpty(sort)) {
			return "";
		}
		// if (!xx.isEmpty(eo) ) {
		// if (xx.isEmpty(sort)) {
		// // 初始默认主键排序
		// if (xx.isEmpty(sort) && eo.getBoolean("isDefaultPkDesc")) {
		// return " order by " + eo.getStr("pkName") + " desc";
		// }
		// return " ";
		// }
		// }
		// 获取排序方式
		String order = c.getPara(PageConst.ORDER, "desc");
		return " order by " + sort + ' ' + order;
	}

	public static String getSort(Controller c) {
		return getSort(c, null);
	}

	/**
	 * 获取条件
	 * @param c 
	 * @param eo
	 * @param eis
	 * @param parmList SQL参数
	 * @return
	 */
	public static String getWhere(Controller c, List<Eova_Item> eis, List<String> parmList, String where) {

		boolean isWhere = true;
		for (Eova_Item ei : eis) {

			// 给查询表单添加前缀，防止和系统级别字段重名
			String key = ei.getStr("en");
			String value = c.getPara(PageConst.QUERY + key, "").trim();
			if ( (!value.equals("")) && (!value.equals("-1"))) {
				if (isWhere) {
					if (!xx.contains(where.toLowerCase(), "where")) {
						where += " where 1=1 ";
					} else {
						where += " ";
					}
					isWhere = false;
				}
				// 复选框需要特转换值
				if (ei.getStr("type").equals("复选框")) {
					if (xx.isEmpty(value)) {
						value = "0";
					} else {
						value = "1";
					}
				}
				if (ei.getStr("dataType").equals(TemplateConfig.DATATYPE_STRING)) {
					where += "and " + key + " like ? ";
					parmList.add("%"+ value +"%");
				}
				if (ei.getStr("dataType").equals(TemplateConfig.DATATYPE_NUMBER)) {
					where += "and " + key + " = ? ";
					parmList.add(value);
				}
				if (ei.getStr("dataType").equals(TemplateConfig.DATATYPE_TIME)) {
					where += "and " + key + " = ? ";
					parmList.add(value);
				}
				// 保持条件值
				ei.put("value", value);
			}
		}
		return where;
	}

	/**
	 * 向SQL添加where关键字
	 * @param where
	 * @return
	 */
	public static String addWhere(String sql) {
		if (!xx.contains(sql.toLowerCase(), "where")) {
			return " where 1=1 ";
		}
		return "";
	}

	public static void buildExpValue(List<Eova_Item> eis, List<Record> reList) {
		// 根据表达式翻译显示CN(获取当前字段所有的 查询结果值，拼接成 字符串 用于 结合表达式进行 in()查询获得cn 然后替换之)
		for (Eova_Item ei : eis) {
			// 获取控件表达式
			String exp = ei.getStr("exp");
			if (xx.isEmpty(exp)) {
				continue;
			}
			// 获取存在表达式的列名
			String en = ei.get("en").toString();
			System.out.println(en + " EovaExp:" + exp);
			// in 条件值
			List<String> ids = new ArrayList<String>();
			if (!xx.isEmpty(reList)) {
				for (Record re : reList) {
					String id = re.get(en).toString();
					// 如果存在则不进行添加
					if (ids.contains(id)) {
						continue;
					}
					ids.add(id);
				}
			}

			// 查询本次所有翻译值
			StringBuilder sql = new StringBuilder();
			// 根据表达式获取SQL进行查询
			String select = EovaExp.getSelectNoAlias(exp);
			String from = EovaExp.getFrom(exp);
			String where = EovaExp.getWhere(exp);
			String ds = EovaExp.getDs(exp);
			// 中文列名
			String pk = EovaExp.getPk(exp);
			String cn = EovaExp.getCn(exp);

			sql.append(select);
			sql.append(from);
			sql.append(where);
			sql.append(addWhere(sql.toString()));
			if (!xx.isEmpty(ids)) {
				sql.append(" and ").append(pk);
				sql.append(" in(");
				// 根据当前页数据主键查询外键显示值
				for(String id : ids){
					sql.append(xx.format(id));
					sql.append(",");
				}
				sql.delete(sql.length() -1, sql.length());
				sql.append(")");
			}
			System.out.println(sql.toString());

			List<Record> findList = Db.use(ds).find(sql.toString());

			for (Record re : reList) {
				for (Record find : findList) {
					// 假如Find结果数据中的主键的值，和当前要翻译的列的值相同，说明需要翻译
					if (re.get(en).toString().equals(find.get(pk).toString())) {
						// 将en的值替换成对应的文字
						// System.out.println(re);
						// re.put("pk", re.get(en).toString());

						re.set(en, find.get(cn));
						// System.out.println(en);
						// System.out.println(find.get(cn));
						// System.out.println(re);
					}
				}
			}

		}
	}

}