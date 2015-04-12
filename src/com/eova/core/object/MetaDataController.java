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
import com.eova.config.EovaConst;
import com.eova.config.PageConst;
import com.eova.engine.EovaExp;
import com.eova.model.Eova_Button;
import com.eova.model.Eova_Item;
import com.eova.model.Eova_Object;
import com.eova.template.common.config.TemplateConfig;
import com.eova.widget.WidgetManager;
import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.kit.JsonKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.activerecord.tx.Tx;
import com.rzyunyou.common.xx;

/**
 * 元数据操作 Eova_Object+Eova_Item
 * 
 * @author Jieven
 * @date 2014-9-11
 */
public class MetaDataController extends Controller {

	/**
	 * 菜单基本功能管理
	 */
	public void toMenuFun() {
		String menuCode = getPara(0);
		setAttr("menuCode", menuCode);

		setAttr("isAdd", Eova_Button.dao.isExistButton(menuCode, EovaConst.FUN_ADD_BS));
		setAttr("isUpdate", Eova_Button.dao.isExistButton(menuCode, EovaConst.FUN_UPDATE_BS));
		setAttr("isDelete", Eova_Button.dao.isExistButton(menuCode, EovaConst.FUN_DELETE_BS));

		render("/eova/metadata/menuFun.html");
	}

	/**
	 * 导入页面
	 */
	public void toImport() {
		// 获取当前配置数据库
		setAttr("dbMap", EovaConst.DBMAP);
		render("/eova/metadata/importMetaData.html");
	}

	/**
	 * 菜单功能管理
	 */
	public void menuFun() {
		String menuCode = getPara(0);

		String isAdd = getPara("isAdd");
		String isUpdate = getPara("isUpdate");
		String isDelete = getPara("isDelete");

		// 删除当前菜单的基本按钮然后重新添加
		Eova_Button.dao.deleteFunByMenuCode(menuCode);

		// 添加查询(有功能点子节点时，Tree无法单选父节点,所以添加空的查询节点 让菜单能保持选中状态)
		// 取消Tree级联选择不存在该问题
		// if (!xx.isEmpty(isAdd) || !xx.isEmpty(isUpdate) ||
		// !xx.isEmpty(isDelete)) {
		// Eova_Button btn = new Eova_Button();
		// btn.set("menuCode", menuCode);
		// btn.set("name", "查询");
		// btn.set("ui", "");
		// btn.set("bs", "");
		// btn.save();
		// }

		// 添加
		if (!xx.isEmpty(isAdd)) {
			Eova_Button btn = new Eova_Button();
			btn.set("menuCode", menuCode);
			btn.set("name", EovaConst.FUN_ADD);
			btn.set("ui", EovaConst.FUN_ADD_UI);
			btn.set("bs", EovaConst.FUN_ADD_BS);
			btn.save();
		}
		// 修改
		if (!xx.isEmpty(isUpdate)) {
			Eova_Button btn = new Eova_Button();
			btn.set("menuCode", menuCode);
			btn.set("name", EovaConst.FUN_UPDATE);
			btn.set("ui", EovaConst.FUN_UPDATE_UI);
			btn.set("bs", EovaConst.FUN_UPDATE_BS);
			btn.save();
		}
		// 删除
		if (!xx.isEmpty(isDelete)) {
			Eova_Button btn = new Eova_Button();
			btn.set("menuCode", menuCode);
			btn.set("name", EovaConst.FUN_DELETE);
			btn.set("ui", EovaConst.FUN_DELETE_UI);
			btn.set("bs", EovaConst.FUN_DELETE_BS);
			btn.save();
		}

		renderJson(new Easy());
	}

	// 查找表结构
	public void find() {

		String ds = getPara(0);
		String db = EovaConst.DBMAP.get(ds);
		String type = getPara(1);

		// 根据表达式获取ei
		String exp = "select table_name 编码,table_name 表名  from information_schema." + type + "s where table_schema = '" + db + "';ds=eova";

		// 根据表达式手工构建Eova_Object
		Eova_Object eo = EovaExp.getEo(exp);
		// 根据表达式手工构建Eova_Item
		List<Eova_Item> eis = EovaExp.getEis(exp);

		setAttr("obj", eo);
		setAttr("itemList", eis);
		setAttr("action", "/metadata/findJson/" + db + '-' + type);

		render("/eova/dialog/find.html");
	}

	// 查找视图结构
	public void findJson() {

		// 获取数据库
		String db = getPara(0);
		String type = getPara(1);

		String exp = "select table_name 编码,table_name 表名  from information_schema." + type + "s where table_schema = '" + db + "';ds=eova";

		// 根据表达式手工构建Eova_Object
		// Eova_Object eo = EovaExp.getEo(code, exp);
		// 根据表达式手工构建Eova_Item
		List<Eova_Item> eis = EovaExp.getEis(exp);
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
		sb.append("SELECT COLUMN_NAME en,COLUMN_COMMENT cn,ORDINAL_POSITION indexNum,COLUMN_KEY,DATA_TYPE,");
		sb.append("if(EXTRA='auto_increment','1','0') isAuto,");
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
			re.remove("COLUMN_KEY");
			re.remove("DATA_TYPE");
			Db.use(xx.DS_EOVA).save("eova_item", re);
		}

		// 导入元对象
		Eova_Object eo = new Eova_Object();
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
		if (re.getStr("DATA_TYPE").indexOf("time") != -1) {
			return Eova_Item.TYPE_TIME;
		} else if (re.getStr("isAuto").equals("1")) {
			return Eova_Item.TYPE_AUTO;
		} else {
			// 默认都是文本框
			return Eova_Item.TYPE_TEXT;
		}
	}

}