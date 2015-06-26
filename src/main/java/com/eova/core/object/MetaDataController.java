/**
 * Copyright (c) 2013-2015, Jieven. All rights reserved.
 * <p/>
 * Licensed under the GPL license: http://www.gnu.org/licenses/gpl.txt
 * To use it on other terms please contact us at 1623736450@qq.com
 */
package com.eova.core.object;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.eova.common.Easy;
import com.eova.common.utils.db.DBInfoUtil;
import com.eova.common.utils.xx;
import com.eova.engine.EovaExp;
import com.eova.model.MetaItem;
import com.eova.model.MetaObject;
import com.eova.template.common.config.TemplateConfig;
import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.kit.JsonKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.activerecord.TableMapping;
import com.jfinal.plugin.activerecord.tx.Tx;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        dbMap.put(eova, DBInfoUtil.getDbNameByConfigName(eova));
        dbMap.put(main, DBInfoUtil.getDbNameByConfigName(main));
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
        List<String> tables = DBInfoUtil.getTableNamesByConfigName(ds, type);
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
        String db = DBInfoUtil.getDbNameByConfigName(ds);
        String type = getPara("type");
        String table = getPara("table");
        String name = getPara("name");
        String code = getPara("code");

        String pkName = "";
        JSONArray list = DBInfoUtil.getColumnInfoByConfigName(ds, db, null, table);

        // 导入元字段
        for (int i = 0; i < list.size(); i++) {
            JSONObject jo = list.getJSONObject(i);
            Record re = new Record();
            re.set("en", jo.getString("COLUMN_NAME"));
            re.set("cn", jo.getString("COLUMN_COMMENT"));
            re.set("indexNum", jo.getIntValue("ORDINAL_POSITION"));
            re.set("length", jo.getIntValue("COLUMN_SIZE"));
            re.set("isAuto", "YES".equalsIgnoreCase(jo.getString("IS_AUTOINCREMENT")) ? "1" : "0");
            re.set("isNotNull", "YES".equalsIgnoreCase(jo.getString("IS_NULLABLE")) ? "1" : "0");
            re.set("COLUMN_KEY", jo.getString("COLUMN_KEY"));
            re.set("DATA_TYPE", jo.getString("TYPE_NAME"));
            re.set("valueExp", jo.getString("REMARKS"));

            // 对象编码
            re.set("objectCode", code);
            // 数据类型
            re.set("dataType", getDataType(re.getStr("DATA_TYPE")));
            // 控件类型
            re.set("type", getType(re));
            // 将注释作为CN,若为空使用EN
            if (xx.isEmpty(re.getStr("cn"))) {
                re.set("cn", re.getStr("en"));
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
        //pkName = list.get(0).getStr("en");

        // 导入元对象
        MetaObject eo = new MetaObject();
        // 编码
        eo.set("code", code);
        // 名称
        eo.set("name", name);
        // 主键
        pkName = TableMapping.me().getTable(eo.getClass()).getPrimaryKey();
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