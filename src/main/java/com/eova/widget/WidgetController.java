/**
 * Copyright (c) 2013-2015, Jieven. All rights reserved.
 *
 * Licensed under the GPL license: http://www.gnu.org/licenses/gpl.txt
 * To use it on other terms please contact us at 1623736450@qq.com
 */
package com.eova.widget;

import java.util.ArrayList;
import java.util.List;

import com.eova.common.utils.xx;
import com.eova.config.PageConst;
import com.eova.engine.EovaExp;
import com.eova.model.MetaField;
import com.eova.model.MetaObject;
import com.jfinal.core.Controller;
import com.jfinal.kit.JsonKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

/**
 * EOVA 组件
 * 
 * @author Jieven
 * 
 */
public class WidgetController extends Controller {

	/**
	 * 查找Dialog
	 */
	public void toFind() {
		render("/eova/dialog/find.html");
	}

	/**
	 * 查找框Dialog
	 */
	public void find() {

		String url = "/widget/findJson?";
		
		String exp = getPara("exp");
		String code = getPara("code");
		String field = getPara("field");
		// 自定义表达式
		if (xx.isEmpty(exp)) {
			// 根据表达式获取ei
			MetaField ei = MetaField.dao.getByObjectCodeAndEn(code, field);
			exp = ei.getStr("exp");
			url += "code=" + code + "&field=" + field;
		} else {
			url += "exp=" + exp;
		}

		// 根据表达式手工构建Eova_Object
		MetaObject eo = EovaExp.getEo(exp);
		// 根据表达式手工构建Eova_Item
		List<MetaField> eis = EovaExp.getEis(exp);

		setAttr("obj", eo);
		setAttr("itemList", eis);
		setAttr("action", url);

		toFind();
	}

	/**
	 * Find Dialog Grid Get JSON
	 */
	public void findJson() {
		
		String exp = getPara("exp");
		String code = getPara("code");
		String en = getPara("field");
		if (xx.isEmpty(exp)) {
			// 根据表达式获取ei
			MetaField ei = MetaField.dao.getByObjectCodeAndEn(code, en);
			exp = ei.getStr("exp");
		}

		// 根据表达式手工构建Eova_Item
		List<MetaField> eis = EovaExp.getEis(exp);
		// 根据表达式获取SQL进行查询
		String select = EovaExp.getSelectNoAlias(exp);
		String from = EovaExp.getFrom(exp);
		String where = EovaExp.getWhere(exp);
		String ds = EovaExp.getDs(exp);

		// 获取分页参数
		int pageNumber = getParaToInt(PageConst.PAGENUM, 1);
		int pageSize = getParaToInt(PageConst.PAGESIZE, 15);

		// 获取条件
		List<String> parmList = new ArrayList<String>();
		where = WidgetManager.getWhere(this, eis, parmList, where);
		// 转换SQL参数为Obj[]
		Object[] parm = new Object[parmList.size()];
		parmList.toArray(parm);

		// 获取排序
		String sort = WidgetManager.getSort(this);

		// 分页查询Grid数据
		String sql = from + where + sort;
		Page<Record> page = Db.use(ds).paginate(pageNumber, pageSize, select, sql, parm);

		// 将分页数据转换成JSON
		String json = JsonKit.toJson(page.getList());
		json = "{\"total\":" + page.getTotalRow() + ",\"rows\":" + json + "}";
		// System.out.println(json);

		renderJson(json);
	}

	/**
	 * Find get CN by value
	 */
	public void findCnByEn() {
		String code = getPara(0);
		String en = getPara(1);
		String value = getPara(2);

		// 根据表达式获取ei
		MetaField ei = MetaField.dao.getByObjectCodeAndEn(code, en);
		// 获取查找框表达式
		String exp = ei.getStr("exp");
		// 根据表达式获取SQL进行查询
		String select = EovaExp.getSelectNoAlias(exp);
		String from = EovaExp.getFrom(exp);
		String where = EovaExp.getWhere(exp);
		String ds = EovaExp.getDs(exp);
		// 获取第一列主键名
		String pk = EovaExp.getPk(exp);
		// 获取第二列中文列名
		String cn = EovaExp.getCn(exp);

		// 根据表达式查询获得翻译的值
		String sql = select + from + where + " and " + pk + " = ?";
		Record re = Db.use(ds).findFirst(sql, value);
		// 没有翻译值，直接返回原值
		if (xx.isEmpty(re)) {
			renderJson(value);
			return;
		}
		renderJson(re.getStr(cn));
	}

	/**
	 * Combo Load Data Get JSON
	 */
	public void comboJson() {
		String objectCode = getPara(0);
		String en = getPara(1);

		// 根据Code和字段获取表达式
		MetaField ei = MetaField.dao.getByObjectCodeAndEn(objectCode, en);
		String exp = ei.getStr("exp");

		// 根据表达式手工构建Eova_Item
		List<MetaField> eis = EovaExp.getEis(exp);
		
		// 根据表达式获取SQL进行查询
		// 下拉框表达式 别名固定为 ID,CN,否则页面无法获取
		String select = EovaExp.getSelect(exp);
		String from = EovaExp.getFrom(exp);
		String where = EovaExp.getWhere(exp);
		String ds = EovaExp.getDs(exp);

		// 获取条件
		List<String> parmList = new ArrayList<String>();
		where = WidgetManager.getWhere(this, eis, parmList, where);
		// 转换SQL参数为Obj[]
		Object[] parm = new Object[parmList.size()];
		parmList.toArray(parm);

		// 获取排序
		String sort = WidgetManager.getSort(this);

		// 分页查询Grid数据
		String sql = select + from + where + sort;
		List<Record> jsList = Db.use(ds).find(sql, parm);
		
		// 添加下拉框默认值
		Record re = new Record();
		re.set("ID", "");
		re.set("CN", "");
		jsList.add(0, re);

		// 转换成JSON
		String json = JsonKit.toJson(jsList);
		// json = "[value,name]";
		System.out.println(json);
		renderJson(json);
	}
	
}