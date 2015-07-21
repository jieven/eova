/**
 * Copyright (c) 2013-2015, Jieven. All rights reserved.
 *
 * Licensed under the GPL license: http://www.gnu.org/licenses/gpl.txt
 * To use it on other terms please contact us at 1623736450@qq.com
 */
package com.eova.widget;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.eova.common.utils.xx;
import com.eova.config.PageConst;
import com.eova.engine.EovaExp;
import com.eova.model.MetaField;
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
public class WidgetManager {

	/**
	 * 获取排序
	 * 
	 * @param c Controller
	 * @param crud CRUDVO
	 * @return
	 */
	public static String getSort(Controller c, MetaObject eo) {
		// 获取排序字段
		String sort = c.getPara(PageConst.SORT, "");
		if (xx.isEmpty(sort)) {
			if (eo == null) {
				return " ";
			}
			// 初始默认主键排序
			if (xx.isEmpty(sort) && eo.getBoolean("is_default_pk_desc")) {
				return " order by " + eo.getPk() + " desc";
			}
			return " ";
		}
		// 获取排序方式
		String order = c.getPara(PageConst.ORDER, "");
		return " order by " + sort + ' ' + order;
	}

	public static String getSort(Controller c) {
		return getSort(c, null);
	}

	/**
	 * 获取条件
	 * 
	 * @param c
	 * @param eo
	 * @param eis
	 * @param parmList SQL参数
	 * @return
	 */
	public static String getWhere(Controller c, List<MetaField> eis, List<String> parmList, String where) {

		StringBuilder sb = new StringBuilder();
		
		boolean isWhere = true;
		for (MetaField ei : eis) {
			// sql where 初始化
			if (isWhere) {
				// 存在初始过滤条件
				if (!xx.isEmpty(where)) {
					// 补where
					if (!where.toLowerCase().contains("where")) {
						sb.insert(0, " where ");
					} else {
						sb.append(" ");
					}
					sb.append(where + " ");
				} else {
					sb.append(" where 1=1 ");
				}
				isWhere = false;
			}

			String key = ei.getEn();
			// 给查询表单添加前缀，防止和系统级别字段重名
			String value = c.getPara(PageConst.QUERY + key, "").trim();
			String startTime = c.getPara(PageConst.START + key, "").trim();
			String endTime = c.getPara(PageConst.END + key, "").trim();
			// 当前字段 既无文本值 也无时间值，说明没填，直接跳过
			if ((xx.isEmpty(value) || value.equals("-1")) && xx.isOneEmpty(startTime, endTime)) {
				continue;
			}
			
			// 复选框需要转换值
			if (ei.getStr("type").equals(MetaField.TYPE_CHECK)) {
				if (xx.isEmpty(value)) {
					value = "0";
				} else {
					value = "1";
				}
			}
			// 文本框查询条件为模糊匹配
			if (ei.getStr("type").equals(MetaField.TYPE_TEXT)) {
				sb.append(" and " + key + " like ?");
				parmList.add("%" + value + "%");
			} else if (ei.getStr("type").equals(MetaField.TYPE_TIME)) {
				sb.append(" and date(" + key + ") > ? and date(" + key + ") < ?");
				parmList.add(startTime);
				parmList.add(endTime);
			} else {
				sb.append(" and " + key + " = ?");
				parmList.add(value);
			}
			
			// 保持条件值回显
			ei.put("value", value);
		}
		return sb.toString();
	}

	/**
	 * 向SQL添加where关键字
	 * 
	 * @param where
	 * @return
	 */
	public static String addWhere(String sql) {
		if (!sql.toLowerCase().contains("where")) {
			return " where 1=1 ";
		}
		return "";
	}

	public static void convertValueByExp(List<MetaField> eis, List<Record> reList) {
		// 根据表达式翻译显示CN(获取当前字段所有的 查询结果值，拼接成 字符串 用于 结合表达式进行 in()查询获得cn 然后替换之)
		for (MetaField ei : eis) {
			// 获取控件表达式
			String exp = ei.getStr("exp");
			if (xx.isEmpty(exp)) {
				continue;
			}
			// 获取存在表达式的列名
			String en = ei.getEn();
			// System.out.println(en + " EovaExp:" + exp);
			// in 条件值
			Set<String> ids = new HashSet<String>();
			if (!xx.isEmpty(reList)) {
				for (Record re : reList) {
					ids.add(re.get(en).toString());
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
				for (String id : ids) {
					sql.append(xx.format(id));
					sql.append(",");
				}
				sql.delete(sql.length() - 1, sql.length());
				sql.append(")");
			}
			// System.out.println("convertValueByExp：" + sql.toString());

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