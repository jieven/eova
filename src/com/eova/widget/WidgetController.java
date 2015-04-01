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
import com.jfinal.core.Controller;
import com.jfinal.kit.JsonKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.rzyunyou.common.xx;

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
		String en = getPara("en");
		// 自定义表达式
		if (xx.isEmpty(exp)) {
			// 根据表达式获取ei
			Eova_Item ei = Eova_Item.dao.getByObjectCodeAndEn(code, en);
			exp = ei.getStr("exp");
			url += "code=" + code +"&en=" + en;
		} else {
			url += "exp=" + exp;
		}

		// 根据表达式手工构建Eova_Object
		Eova_Object eo = EovaExp.getEo(exp);
		// 根据表达式手工构建Eova_Item
		List<Eova_Item> eis = EovaExp.getEis(exp);

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
		String en = getPara("en");
		if (xx.isEmpty(exp)) {
			// 根据表达式获取ei
			Eova_Item ei = Eova_Item.dao.getByObjectCodeAndEn(code, en);
			exp = ei.getStr("exp");
		}

		// 根据表达式手工构建Eova_Item
		List<Eova_Item> eis = EovaExp.getEis(exp);
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
		System.out.println(json);

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
		Eova_Item ei = Eova_Item.dao.getByObjectCodeAndEn(code, en);
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
		Eova_Item ei = Eova_Item.dao.getByObjectCodeAndEn(objectCode, en);
		String exp = ei.getStr("exp");

		// 根据表达式手工构建Eova_Item
		List<Eova_Item> eis = EovaExp.getEis(exp);
		
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
		re.set("ID", "-1");
		re.set("CN", "请选择数据");
		jsList.add(0, re);

		// 转换成JSON
		String json = JsonKit.toJson(jsList);
		// json = "[value,name]";
		System.out.println(json);
		renderJson(json);
	}

	/**
	 * 获取列表数据JSON
	 */
	public void gridJson() {

		// Get Eova_Object Code
		String code = getPara(0);

		// Get Eova_Object and Eova_Item List
		Eova_Object eo = Eova_Object.dao.getByCode(code);
		List<Eova_Item> eis = Eova_Item.dao.queryByObjectCode(code);

		// 获取分页参数
		int pageNumber = getParaToInt(PageConst.PAGENUM, 1);
		int pageSize = getParaToInt(PageConst.PAGESIZE, 95);

		// 获取条件
		List<String> parmList = new ArrayList<String>();
		String where = WidgetManager.getWhere(this, eis, parmList, "");
		// 转换SQL参数为Obj[]
		Object[] parm = new Object[parmList.size()];
		parmList.toArray(parm);
		// 获取排序
		String sort = WidgetManager.getSort(this, eo);

		// 分页查询Grid数据
		String view = eo.getView();
		String sql = "from " + view + where + sort;
		Page<Record> page = Db.use(eo.getStr("dataSource")).paginate(pageNumber, pageSize, "select *", sql, parm);

		// 创建主键列副本
		WidgetUtil.copyPkValue(page.getList(), eo.getStr("pkName"));
		// 根据表达式将数据中的值翻译成汉字
		WidgetManager.buildExpValue(eis, page.getList());

		// 将分页数据转换成JSON
		String json = JsonKit.toJson(page.getList());
		json = "{\"total\":" + page.getTotalRow() + ",\"rows\":" + json + "}";
		System.out.println(json);

		renderJson(json);
	}

	/**
	 * 获取树形数据JSON
	 */
	public void treeJson() {


		// Get Eova_Object Code
		String code = getPara(0);
		// Get Tree Pid
		String pid = getPara(1);

		// Get Eova_Object and Eova_Item List
		Eova_Object eo = Eova_Object.dao.getByCode(code);
//		List<Eova_Item> eis = Eova_Item.dao.queryByObjectCode(code);

		// 分页查询Grid数据
//		String view = eo.getView();
		String sql = "select * from eova_menu where FIND_IN_SET(id, queryChild(?))";
		List<Record> list = Db.use(eo.getStr("dataSource")).find(sql, pid);
		
		// 创建主键列副本
//		WidgetUtil.copyPkValue(list, eo.getStr("pkName"));
		// 根据表达式将数据中的值翻译成汉字
//		WidgetManager.buildExpValue(eis, list);

//		 将分页数据转换成JSON
		String json = WidgetUtil.toTreeJson(list);
		System.out.println(json);
		
//		StringBuilder sb = new StringBuilder("[");
//		for (Record menu : list) {
//			String icon = menu.getStr("icon");;
//			String state = "open";
//			// 是否默认折叠
//			boolean isCollapse = menu.getBoolean("isCollapse");
//			if (isCollapse) {
//				state = "closed";
//			}
//			if (xx.isEmpty(icon)) {
//				// 默认图标
//				icon = "ext-icon-application";
//			}
//			sb.append("{");
//			sb.append("\"attributes\": {");
//			sb.append("\"target\": \"\",");
//			sb.append("\"url\": \"" + menu.getStr("urlCmd") + "\"");
//			sb.append("},\n");
//			sb.append("\"checked\": false,");
//			sb.append("\"iconCls\": \""+ icon + "\",");
//			sb.append("\"id\": \"" + menu.getInt("id") + "\", ");
//			int parentId = menu.getInt("parentId");
//			if (parentId != 0) {
//				sb.append("\"pid\": \"" + parentId + "\",");
//			}
//			sb.append("\"state\": \""+ state +"\", \n");
//			sb.append("\"text\": \"" + menu.getStr("name") + "\"");
//
//			sb.append("},");
////			System.out.println(menu.getStr("name") + ":" + menu.getStr("urlCmd"));
//		}
//		sb.delete(sb.length() - 1, sb.length());
//		sb.append("]");
//		System.out.println(sb.toString());
		renderJson(json);
	
	}

}