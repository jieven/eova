/**
 * Copyright (c) 2013-2015, Jieven. All rights reserved.
 *
 * Licensed under the GPL license: http://www.gnu.org/licenses/gpl.txt
 * To use it on other terms please contact us at 1623736450@qq.com
 */
package com.eova.core.object;

import java.util.ArrayList;
import java.util.List;

import com.eova.common.Easy;
import com.eova.common.utils.xx;
import com.eova.config.EovaConst;
import com.eova.config.PageConst;
import com.eova.engine.EovaExp;
import com.eova.model.MetaItem;
import com.eova.model.MetaObject;
import com.eova.template.common.config.TemplateConfig;
import com.eova.widget.WidgetManager;
import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.kit.JsonKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.activerecord.tx.Tx;

/**
 * 元数据操作 MetaObject+MetaItem
 * 
 * @author Jieven
 * @date 2014-9-11
 */
public class MetaDataController extends Controller {

	/**
	 * 导入页面
	 */
	public void toImport() {
		// 获取当前配置数据库
		setAttr("dbMap", EovaConst.DBMAP);
		render("/eova/metadata/importMetaData.html");
	}

	// 查找表结构表头
	public void find() {

		String ds = getPara(0);
		String db = EovaConst.DBMAP.get(ds);
		String type = getPara(1);

		// 根据表达式获取ei
		String exp = "select table_name 编码,table_name 表名  from information_schema." + type + "s where table_schema = '" + db + "';ds=eova";

		// 根据表达式手工构建Eova_Object
		MetaObject eo = EovaExp.getEo(exp);
		// 根据表达式手工构建Eova_Item
		List<MetaItem> eis = EovaExp.getEis(exp);

		setAttr("obj", eo);
		setAttr("itemList", eis);
		setAttr("action", "/metadata/findJson/" + db + '-' + type);

		render("/eova/dialog/find.html");
	}

	// 查找表结构数据
	public void findJson() {

		// 获取数据库
		String db = getPara(0);
		String type = getPara(1);

		String exp = "select table_name 编码,table_name 表名  from information_schema." + type + "s where table_schema = '" + db + "'";
		if (type.equals("table")) {
			exp += " AND TABLE_TYPE = 'BASE TABLE' ";
		}
		exp += ";ds=eova";

		// 根据表达式手工构建Eova_Object
		// MetaObject eo = EovaExp.getEo(code, exp);
		// 根据表达式手工构建Eova_Item
		List<MetaItem> eis = EovaExp.getEis(exp);
		// System.out.println(eis.toString());
		// 根据表达式获取SQL进行查询
		String select = EovaExp.getSelectNoAlias(exp);
		String from = EovaExp.getFrom(exp);
		String where = EovaExp.getWhere(exp);

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
		Page<Record> page = Db.use(xx.DS_EOVA).paginate(pageNumber, pageSize, select, sql, parm);

		// 将分页数据转换成JSON
		String json = JsonKit.toJson(page.getList());
		json = "{\"total\":" + page.getTotalRow() + ",\"rows\":" + json + "}";
		System.out.println(json);

		renderJson(json);
	}

	// 导入元数据
	@Before(Tx.class)
	public void importData() {

		String ds = getPara("ds");
		String db = EovaConst.DBMAP.get(ds);
		String type = getPara("type");
		String table = getPara("table");
		String name = getPara("name");
		String code = getPara("code");

		String pkName = "";

		// 查询元字段
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT COLUMN_NAME en,COLUMN_COMMENT cn,ORDINAL_POSITION indexNum,COLUMN_KEY,DATA_TYPE,CHARACTER_MAXIMUM_LENGTH length,");
		sb.append(" if(EXTRA='auto_increment','1','0') isAuto,");
		sb.append(" if(IS_NULLABLE='YES','1','0') isNotNull,COLUMN_DEFAULT valueExp");
		sb.append(" FROM information_schema.COLUMNS WHERE TABLE_SCHEMA = ? and");
		sb.append(" TABLE_NAME = ? ORDER BY ORDINAL_POSITION;");
		List<Record> list = Db.use(xx.DS_EOVA).find(sb.toString(), db, table);
		// 导入元字段
		for (Record re : list) {
			String en = re.getStr("en");
			// 是否主键
			if (re.getStr("COLUMN_KEY").equals("PRI")) {
				pkName = en;
			}
			// 对象编码
			re.set("objectCode", code);
			// 数据类型
			re.set("dataType", getDataType(re.getStr("DATA_TYPE")));
			// 控件类型
			re.set("type", getType(re));
			// 将注释作为CN,若为空使用EN
			if (xx.isEmpty(re.getStr("cn"))) {
				re.set("cn", en);
			}
			// 默认值
			if (xx.isEmpty(re.getStr("valueExp"))) {
				re.set("valueExp", "");
			}

			// 移除不需要的VO字段
			re.remove("COLUMN_KEY");
			re.remove("DATA_TYPE");
			re.remove("length");

			Db.use(xx.DS_EOVA).save("eova_item", re);
		}

		// 导入视图默认第一列为主键
		pkName = list.get(0).getStr("en");

		// 导入元对象
		MetaObject eo = new MetaObject();
		// 编码
		eo.set("code", code);
		// 名称
		eo.set("name", name);
		// 主键
		eo.set("pkName", pkName);
		// 数据源
		eo.set("dataSource", ds);
		// 表或视图
		if (type.equals("table")) {
			eo.set("table", table);
		} else {
			eo.set("view", table);
		}
		eo.save();

		renderJson(new Easy());
	}

	/**
	 * 转换数据类型
	 * 
	 * @param type DB数据类型
	 * @return
	 */
	private String getDataType(String type) {
		if (type.indexOf("int") != -1) {
			return TemplateConfig.DATATYPE_NUMBER;
		} else if (type.indexOf("time") != -1) {
			return TemplateConfig.DATATYPE_TIME;
		} else {
			return TemplateConfig.DATATYPE_STRING;
		}
	}

	/**
	 * 获取控件类型
	 * 
	 * @param re
	 * @return
	 */
	private String getType(Record re) {
		long length = xx.toLong(re.get("length"), 0);

		if (re.getStr("DATA_TYPE").contains("time")) {
			return MetaItem.TYPE_TIME;
		} else if (re.getStr("isAuto").equals("1")) {
			return MetaItem.TYPE_AUTO;
		} else if (length > 255) {
			return MetaItem.TYPE_TEXTS;
		} else if (length > 500) {
			return MetaItem.TYPE_EDIT;
		} else {
			// 默认都是文本框
			return MetaItem.TYPE_TEXT;
		}
	}

}