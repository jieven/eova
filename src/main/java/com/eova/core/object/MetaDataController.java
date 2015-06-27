/**
 * Copyright (c) 2013-2015, Jieven. All rights reserved.
 * <p/>
 * Licensed under the GPL license: http://www.gnu.org/licenses/gpl.txt
 * To use it on other terms please contact us at 1623736450@qq.com
 */
package com.eova.core.object;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.eova.common.Easy;
import com.eova.common.utils.xx;
import com.eova.common.utils.db.DsUtil;
import com.eova.engine.EovaExp;
import com.eova.model.MetaItem;
import com.eova.model.MetaObject;
import com.eova.template.common.config.TemplateConfig;
import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.kit.JsonKit;
import com.jfinal.plugin.activerecord.Db;
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
        Map<String, String> dbMap = new HashMap<String, String>();
        String eova = xx.DS_EOVA;
        String main = xx.DS_MAIN;
        dbMap.put(eova, DsUtil.getDbNameByConfigName(eova));
        dbMap.put(main, DsUtil.getDbNameByConfigName(main));
        setAttr("dbMap", dbMap);
        render("/eova/metadata/importMetaData.html");
    }

    // 查找表结构表头
    public void find() {

        String ds = getPara(0);
        String type = getPara(1);
        // 根据表达式手工构建Eova_Object
        MetaObject eo = new MetaObject();
        eo.put("dataSource", ds);
        eo.put("name", "");
        eo.put("table", "");
        eo.put("isDefaultPkDesc", false);
        // 获取第一的值作为主键
        eo.put("pkName", "table_name");
        // 获取第二列的值作为CN
        eo.put("cn", "table_name");
        // 根据表达式手工构建Eova_Item
        List<MetaItem> eis = new ArrayList<MetaItem>();
        eis.add(EovaExp.buildItem(1, "table_name", "编码", false));
        eis.add(EovaExp.buildItem(2, "table_name", "表名", true));
        setAttr("obj", eo);
        setAttr("itemList", eis);
        setAttr("action", "/metadata/findJson/" + ds + '-' + type);

        render("/eova/dialog/find.html");
    }

    // 查找表结构数据
    public void findJson() {

        // 获取数据库
        String ds = getPara(0);
        String type = getPara(1);
        List<String> tables = DsUtil.getTableNamesByConfigName(ds, type);
        JSONArray tableArray = new JSONArray();
        for (String tableName : tables) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("table_name", tableName);
            tableArray.add(jsonObject);
        }
        // 将分页数据转换成JSON
        String json = JsonKit.toJson(tableArray);
        json = "{\"total\":" + tableArray.size() + ",\"rows\":" + json + "}";
        renderJson(json);
    }

    // 导入元数据
    @Before(Tx.class)
    public void importData() {

        String ds = getPara("ds");
        String type = getPara("type");
        String table = getPara("table");
        String name = getPara("name");
        String code = getPara("code");

		JSONArray list = DsUtil.getColumnInfoByConfigName(ds, table);

		// 导入元字段
		importMetaField(code, list);

		// 导入视图默认第一列为主键
		String pkName = DsUtil.getPkName(ds, table);
		if (xx.isEmpty(pkName)) {
			pkName = list.getJSONObject(0).getString("en");
		}

		// 导入元对象
		importMetaObject(ds, type, table, name, code, pkName);

		renderJson(new Easy());
	}

	// 导入元字段
	private void importMetaField(String code, JSONArray list) {
        for (int i = 0; i < list.size(); i++) {
            JSONObject o = list.getJSONObject(i);
            Record re = new Record();
            re.set("en", o.getString("COLUMN_NAME"));
			re.set("cn", o.getString("REMARKS"));
            re.set("indexNum", o.getIntValue("ORDINAL_POSITION"));
            re.set("isNotNull", "YES".equalsIgnoreCase(o.getString("IS_NULLABLE")) ? "1" : "0");

			// 是否自增
			boolean isAuto = "YES".equalsIgnoreCase(o.getString("IS_AUTOINCREMENT")) ? true : false;
			re.set("isAuto", isAuto);
			// 字段类型
			String typeName = o.getString("TYPE_NAME");
			re.set("dataType", getDataType(typeName));
			// 字段长度
			int size = o.getIntValue("COLUMN_SIZE");
			// 默认值
			String def = o.getString("COLUMN_DEF");
			re.set("valueExp", def);

            // 控件类型
			re.set("type", getFormType(isAuto, typeName, size));
            // 将注释作为CN,若为空使用EN
            if (xx.isEmpty(re.getStr("cn"))) {
                re.set("cn", re.getStr("en"));
            }
            // 默认值
            if (xx.isEmpty(re.getStr("valueExp"))) {
                re.set("valueExp", "");
            }
			// 对象编码
			re.set("objectCode", code);

            Db.use(xx.DS_EOVA).save("eova_item", re);
        }
	}

	// 导入元对象
	private void importMetaObject(String ds, String type, String table, String name, String code, String pkName) {
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
	}

    /**
	 * 转换数据类型
	 * 
	 * @param typeName DB数据类型
	 * @return
	 */
	private String getDataType(String typeName) {
		if (typeName.indexOf("int") != -1) {
            return TemplateConfig.DATATYPE_NUMBER;
		} else if (typeName.indexOf("time") != -1) {
            return TemplateConfig.DATATYPE_TIME;
        } else {
            return TemplateConfig.DATATYPE_STRING;
        }
    }

	/**
	 * 获取表单类型
	 * 
	 * @param isAuto 是否自增
	 * @param typeName 类型
	 * @param size 长度
	 * @return
	 */
	private String getFormType(boolean isAuto, String typeName, int size) {
		if (typeName.contains("time")) {
            return MetaItem.TYPE_TIME;
		} else if (isAuto) {
            return MetaItem.TYPE_AUTO;
		} else if (size > 255) {
            return MetaItem.TYPE_TEXTS;
		} else if (size > 500) {
            return MetaItem.TYPE_EDIT;
        } else {
            // 默认都是文本框
            return MetaItem.TYPE_TEXT;
        }
    }

}