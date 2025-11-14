/**
 * Copyright (c) 2015-2026 EOVA.CN. All rights reserved.
 * Licensed under the LGPL-3.0 license
 * For authorization, please contact: admin@eova.cn
 */
package cn.eova.core.meta;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import cn.eova.tools.x;
import com.alibaba.druid.DbType;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import cn.eova.aop.AopContext;
import cn.eova.aop.MetaObjectIntercept;
import cn.eova.common.Ds;
import cn.eova.common.Easy;
import cn.eova.common.base.BaseController;
import cn.eova.common.utils.db.DbUtil;
import cn.eova.common.utils.db.DsUtil;
import cn.eova.common.utils.xx;
import cn.eova.config.EovaConst;
import cn.eova.config.EovaDataSource;
import cn.eova.engine.EovaExp;
import cn.eova.model.EovaOption;
import cn.eova.model.Menu;
import cn.eova.model.MetaField;
import cn.eova.model.MetaFieldDiy;
import cn.eova.model.MetaObject;
import cn.eova.model.User;
import cn.eova.service.sm;
import cn.eova.template.common.util.TemplateUtil;
import com.jfinal.aop.Before;
import com.jfinal.core.NotAction;
import com.jfinal.kit.JsonKit;
import com.jfinal.kit.Kv;
import com.jfinal.kit.LogKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.activerecord.tx.Tx;
import com.jfinal.plugin.activerecord.tx.TxConfig;

/**
 * 元数据相关获取
 *
 * @author Jieven
 *
 */
public class MetaController extends BaseController {

    /** 元对象业务拦截器 **/
    protected MetaObjectIntercept intercept = null;

    // 获取元对象
    public void object() {
        String code = get(0);
        MetaObject mo = MetaObject.dao.getByCode(code);

        // 安全保护，去掉界面不需要的字段
        mo.remove("filter");
        mo.remove("table_name");
        mo.remove("view_name");
        mo.remove("view_sql");
        renderJson(JsonKit.toJson(mo));
    }

    // 获取元字段集
    public void fields() throws Exception {
        String code = get(0);
        User user = getUser();
        List<MetaField> fields = MetaField.dao.queryFields(code, user);

        MetaObject object = MetaObject.dao.getByCode(code);
        intercept = TemplateUtil.initMetaObjectIntercept(object.getBizIntercept());
        // 元数据拦截器
        if (intercept != null) {
            AopContext ac = new AopContext(this);
            ac.object = object;// 只读
            ac.fields = fields;// 读写
            intercept.metadata(ac);
        }

        // 安全保护，去掉界面不需要的字段
        for (MetaField f : fields) {
            f.remove("exp");
            f.remove("table_name");
            f.remove("data_type");
            f.remove("data_type_name");
            f.remove("data_size");
            f.remove("data_decimal");
        }
        renderJson(JsonKit.toJson(fields));
    }

    // 获取元字段字典数据
    public void dicts() {
        String code = get(0);
        String field = get(1);

        MetaObject mo = MetaObject.dao.getByCode(code);

        String dictTable = x.conf.get("main_dict_table");
        String sql = String.format("select value id,name cn from %s where object = '%s' and field = '%s'", dictTable, mo.getView(), field);
        List<Record> list = Db.use(mo.getDs()).find(sql);

        renderJson(list);
    }

    // 编辑元数据
//	public void edit() {
//		String objectCode = get(0);
//		setAttr("objectCode", objectCode);
//		render("/eova/meta/edit.html");
//	}

    // 单元格编辑
    public void edit() {
        String objectCode = get("object");
        if (x.isEmpty(objectCode)) {
            renderError(500);
            return;
        }

        MetaObject object = sm.meta.getMeta(objectCode);
        set("object", object);

        String mode = get("mode");

        set("where", String.format("{ object_code: '%s' }", objectCode));

//        if (x.isEmpty(mode)) {
////            set("object", "eova_field_code");
//
//        } else {
////            set("object", "eova_field_diy");
//            set("where", String.format("{ object_code: '%s', mode: '%s' }", objectCode, mode));
//        }

        renderEnjoy("/eova/_view/meta/edit/app.html");
    }


    // 元字段个性化
    public void field() {

        String objectCode = get("object");
        String mode = get("mode", "query");
        set("mode", mode);

        Menu menu = Menu.dao.findByCode("eova_object");
        set("menu", menu);

        MetaObject object = sm.meta.getMeta(objectCode);
        set("object", object);

        renderEnjoy("/eova/_view/meta/field/index.html");
    }

    // 重新排序
    public void reorder() {
        String objectCode = get("object");
        String biz = get("biz", "field");// 场景

        set("biz", biz);

        // 元字段排序
        if (biz.equals("field")) {
            List<MetaField> list = sm.meta.getMetaField(objectCode);
            List<Kv> tps = list.stream()
                    .map(o -> Kv.of("id", o.getId()).set("name", o.getCn()).set("num", o.getNum())) // 创建新的MetaField实例
                    .collect(Collectors.toList());
            set("data", tps);
        }
        // 元字段排序
        else if (biz.equals("field_diy")) {
            String mode = get("mode");// 模式
            List<MetaFieldDiy> list = sm.meta.getMetaFieldDiy(objectCode, mode);
            List<Kv> tps = list.stream()
                    .map(o -> Kv.of("id", o.getInt("id")).set("name", o.getStr("cn")).set("num", o.getInt("num"))) // 创建新的MetaField实例
                    .collect(Collectors.toList());
            set("data", tps);
            set("mode", mode);
        }


        renderEnjoy("/eova/_view/meta/reorder/app.html");
    }

    // 获取需要排序的数据
    @Before(Tx.class)
    public void updateReorder() {
        String objectCode = get("object");
        String biz = get("biz", "field");

        List<Record> list = getRecords();
        List<Record> tps = new ArrayList<>();

        String sql = "";
        // 元字段
        if (biz.equals("field")) {
            sql = "update eova_field set num = ? where id = ?";
        }
        // 元字段个性化
        else if (biz.equals("field_diy")) {
            sql = "update eova_field_diy set num = ? where id = ?";
        }

        try {

            int num = 0;
            for (Record r : list) {
                num++;
                // 如果只进行了上半部分变更, 则剩下的序无需更新.
                if (r.getInt("num") != num) {
                    r.set("num", num);
                    tps.add(r);
                }
            }

            int[] result = Db.use(Ds.EOVA).batch(sql, "num, id", tps, 500);
            x.log.info("重新排序成功:" + result.length);
        } catch (Exception e) {
            NO("更新排序失败:" + e.getMessage());
            x.log.error("排序更新失败", e);
            return;
        }

        OK();
    }


    // 导入页面
    public void imports() {
        setAttr("dataSources", EovaDataSource.map());
        renderEnjoy("/eova/_view/meta/import/app.html");
    }

    // 查找表结构表头
    public void find() {

        String ds = get(0);
        String type = get(1);
        // 根据表达式手工构建Eova_Object
        MetaObject eo = MetaObject.dao.getTemplate();
        eo.put("data_source", ds);
        // 第1列名
        eo.put("pk_name", "table_name");
        // 第2列名
        eo.put("cn", "table_name");

        // 根据表达式手工构建Eova_Item
        List<MetaField> eis = new ArrayList<MetaField>();
//		eis.add(EovaExp.buildItem(1, "table_name", "编码_WIDTH300", false));
        eis.add(EovaExp.buildItem(1, "table_name", "表名_WIDTH400", true, null));

        setAttr("objectJson", JsonKit.toJson(eo));
        setAttr("fieldsJson", JsonKit.toJson(eis));
        setAttr("itemList", eis);
        setAttr("pk", "pk_name");

        setAttr("action", "/meta/findJson/" + ds + '-' + type);
        setAttr("isPaging", false);

        render("/eova/widget/find/find.html");
    }

    // 查找表结构数据
    public void findJson() {

        // 获取数据库
        String ds = get(0);
        String type = get(1);

        // 表名过滤
        String tableNamePattern = get("query_table_name");
        if (!x.isEmpty(tableNamePattern)) {
            tableNamePattern = "%" + tableNamePattern + "%";
        }

        List<String> tables = DsUtil.getTableNamesByConfigName(ds, type, tableNamePattern);
        JSONArray tableArray = new JSONArray();
        for (String tableName : tables) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("table_name", tableName);
            tableArray.add(jsonObject);
        }

        // 构建JSON数据
        String ui = x.conf.get("ui", "layui");
        if (ui.equals("easyui")) {
            ui = "{\"total\":%s,\"rows\": %s}";
        } else if (ui.equals("layui")) {
            ui = "{\"code\": 0, \"msg\": \"\", \"count\":\"%s\",\"data\": %s}";
        }

        renderJson(String.format(ui, tableArray.size(), JsonKit.toJson(tableArray)));
    }

    // 一键导入
    @Before(Tx.class)
    @TxConfig(Ds.EOVA)
    public void importAll() {

        if (!get(0, "").equals("eova")) {
            renderJson(new Easy("请输入校验码，防止误操作！！！！！"));
            return;
        }

        boolean isUpgrade = x.conf.getBool("isUpgrade", false);
        if (!isUpgrade) {
            renderText("未开启升级模式，请启动配置 isUpgrade = true");
            return;
        }

        String ds = Ds.MAIN;
        String type = DsUtil.TABLE;

        // DB名
        String db = DsUtil.getDbNameByConfigName(ds);

        // 获取所有表名
        List<String> tables = DsUtil.getTableNamesByConfigName(ds, type, null);

        for (String table : tables) {

            if (table.startsWith("eova_") || table.equals("dicts") || table.equals("user_info")) {
                continue;
            }
            // if (!table.equals("dicts")) {
            // continue;
            // }
            System.out.println(table);

            String name = table;
            String code = db + "_" + table;

            if (EovaDataSource.getDbType(ds) == DbType.mysql) {
                // 自动获取表名注释
                String s = Db.queryStr("select TABLE_COMMENT FROM information_schema.TABLES WHERE TABLE_SCHEMA = ? AND TABLE_NAME = ?", db, table);
                if (!x.isEmpty(s)) {
                    name = s;
                }
            }

            // 导入元数据
            String msg = importMeta(ds, type, table, name, code, "id");
            if (!x.isEmpty(msg)) {
                LogKit.error(msg);
            }
        }

        renderJson(new Easy());
    }

    // 导入元数据
    @Before(Tx.class)
    @TxConfig(Ds.EOVA)
    public void doImports() {

        String ds = get("ds");
        String type = get("type");
        String table = get("table");
        String name = get("name");
        String code = get("code");
        String pk = "id";//get("pk");

        if (x.isOneEmpty(ds, type, table, name, code)) {
            NO("参数都必须填写！");
            return;
        }

        MetaObject o = MetaObject.dao.getByCode(code);
        if (o != null) {
            NO(String.format("对象编码[%s]已经被其它对象使用了，请修改对象编码！", code));
            return;
        }

        // 导入元数据
        String msg = importMeta(ds, type, table, name, code, pk);
        if (!x.isEmpty(msg)) {
            NO(msg);
            return;
        }

        OK();
    }

    // 导出选中元数据
    public void doExport() {
        String ids = get(0);

        StringBuilder sb = new StringBuilder();

        String sql1 = "select * from eova_object where id in (" + ids + ")";
        List<Record> objects = Db.use(Ds.EOVA).find(sql1);

        // 删除语句
        objects.forEach(x -> {
            sb.append("--").append(x.getStr("name")).append("\n");

            String code = x.getStr("code");
            sb.append(String.format("delete from eova_object where code = '%s';\n", code));
            sb.append(String.format("delete from eova_field where object_code = '%s';\n", code));
            sb.append("\n");
        });
        sb.append("\n");

        // 新增语句
        sb.append("-- 元对象\n");
        DbUtil.generateSql(Ds.EOVA, "eova_object", objects, "id", sb);

        sb.append("\n");

        sb.append("-- 元字段\n");
        String sql2 = "select * from eova_field where object_code in (select code from eova_object where id in (" + ids + "))";
        List<Record> fields = Db.use(Ds.EOVA).find(sql2);
        // 新增语句
        DbUtil.generateSql(Ds.EOVA, "eova_field", fields, "id", sb);

        // 导出元字段表达式
        sb.append("-- 元字段关联表达式\n");
        for (Record object : objects) {
            List<Record> options = Db.use(Ds.EOVA).find("SELECT * FROM eova_option where code in (SELECT exp FROM eova_field where object_code = ?)", object.getStr("code"));
            DbUtil.generateSql(Ds.EOVA, "eova_option", options, "id", sb);
        }

        sb.append("-- 元字段个性化\n");
        String sql3 = "select * from eova_field_diy where object_code in (select code from eova_object where id in (" + ids + "))";
        List<Record> fieldDiys = Db.use(Ds.EOVA).find(sql3);
        DbUtil.generateSql(Ds.EOVA, "eova_field_diy", fieldDiys, "id", sb);

        renderText(sb.toString());
    }

    // 覆盖同步元字段(主键需要更新元对象)
    @Before(Tx.class)
    @TxConfig(Ds.EOVA)
    public void override() {
        String code = get(0);

        MetaObject o = MetaObject.dao.getByCode(code);

        // 删除所有元字段
        sm.meta.deleteMetaField(code);

        // 重新导入元数据
        importMetaField(o.getDs(), o.getView(), o.getCode(), o.isView());

        OK();
    }

    // 增量同步元字段
    @Before(Tx.class)
    @TxConfig(Ds.EOVA)
    public void syncnew() {

        // 根据元对象编码同步
        String code = get(0);
        MetaObject mo = MetaObject.dao.getByCode(code);

        buildSyncNew(mo);

        // 批量同步
//		String str = get(0);
//		String[] ids = str.split(",");
//		for (String id : ids) {
//		}

        OK();
    }

    private void buildSyncNew(MetaObject mo) {
        List<MetaField> fields = MetaField.dao.queryByObjectCode(mo.getCode());

        String ds = mo.getDs();
        String table = mo.getView();

        JSONArray list = DsUtil.getColumnInfoByConfigName(ds, table, mo.isView());

        // 如果当前元字段中不存在就新增
        for (int i = 0; i < list.size(); i++) {
            JSONObject o = list.getJSONObject(i);
            String name = o.getString("COLUMN_NAME");

            // 是否新字段
            boolean isNew = true;
            for (MetaField field : fields) {
                if (name.equalsIgnoreCase(field.getEn())) {
                    isNew = false;
                }
            }

            // 新字段进行导入
            if (isNew) {
                ColumnMeta col = new ColumnMeta(ds, table, o);
                MetaField mi = new MetaField(mo.getCode(), col);
                mi.save();

                autoBindDict(table, mo.getCode(), o.getString("REMARKS"), mi.getEn(), ds);

                sm.meta.addMetaFieldDiy(mo.getCode(), mi.getEn());
            }
        }
    }

    // 复制元数据
    @Before(Tx.class)
    @TxConfig(Ds.EOVA)
    public void copy() {
        String id = get(0);
        String code = get(1);

        MetaObject object = MetaObject.dao.findById(id);
        List<MetaField> fields = MetaField.dao.queryByObjectCode(object.getCode());

        // 使用新的对象编码,生成一份元数据
        for (MetaField f : fields) {
            f.remove("id");
            f.set("object_code", code);
            f.save();
        }
        object.remove("id");
        object.set("code", code);
        object.save();

        renderJson(new Easy());
    }

    /**
     * 导入元数据
     *
     * @param ds 数据源
     * @param type 表还是视图
     * @param table 表名
     * @param name 对象名
     * @param code 对象编码
     * @param pk 主键名
     */
    @NotAction
    public String importMeta(String ds, String type, String table, String name, String code, String pk) {

        boolean isView = type.equalsIgnoreCase(DsUtil.VIEW);
        // table自动获取主键
        if (!isView) {
            pk = DsUtil.getPkName(ds, table);
            if (x.isEmpty(pk)) {
                return "表的主键不能为空，请为当前表设置主键！";
            }
            // G001 表名+主键 小写
            pk = pk.toLowerCase();
        }

        // G001 表名+主键 小写
        table = table.toLowerCase();

        // 导入元字段
        importMetaField(ds, table, code, isView);
        // 导入元对象
        importMetaObject(ds, type, table, name, code, pk);

        // 云端人工智能预处理元数据
        // EovaCloud.buildMeta(code);

        return null;
    }

    /**
     * 导入元字段
     * @param ds 数据源
     * @param table 表名
     * @param code 对象编码
     * @param isView 是否视图
     */
    private void importMetaField(String ds, String table, String code, boolean isView) {
        JSONArray list = DsUtil.getColumnInfoByConfigName(ds, table, isView);

        for (int i = 0; i < list.size(); i++) {
            JSONObject o = list.getJSONObject(i);
            // System.out.println(o.toJSONString());
            ColumnMeta col = new ColumnMeta(ds, table, o);
            MetaField mi = new MetaField(code, col);
            mi.save();

            autoBindDict(table, code, o.getString("REMARKS"), mi.getEn(), ds);

            // 初始化元字段个性化
            sm.meta.initMetaFieldDiy(code, mi);
        }

    }

    /**
     * 自动根据字典 将对应的字段 设置成 下拉框 并生成表达式
     *
     * @param tableName
     * @param objectCode
     * @param remarks
     * @param fieldName
     * @param ds
     */
    private void autoBindDict(String tableName, String objectCode, String remarks, String fieldName, String ds) {
        if (x.isEmpty(remarks)) {
            return;
        }
        if ((remarks.contains(":") || remarks.contains("：")) && remarks.contains("=")) {
            String dictTable = x.conf.get("main_dict_table");
            if (ds.equals(Ds.EOVA)) {
                dictTable = "eova_dict";
            }
            //String exp = String.format("select value ID,name CN from %s where object = '%s' and field = '%s';ds=%s", dictTable, tableName, fieldName, ds);
//			String sql = "update eova_field set type = '下拉框', exp = ? where object_code = ? and en = ?";
//			Db.use(Ds.EOVA).update(sql, exp, objectCode, fieldName);

            // TODO Exp 统一转移到 eova_option
			/*
				表达式命名规范
				系统:
				字典Exp: dict_数据源_对象_字段

				自定义:
				自定义Exp DIY 无限制
			 */

            String expCode = String.format("dict_%s_%s", objectCode, fieldName);
            String exp = String.format("select value,name from %s where object = '%s' and field = '%s'", dictTable, tableName, fieldName);

            EovaOption option = EovaOption.create(expCode, ds, exp, "value", "name");
            option.set("name", String.format("%s_%s", tableName, fieldName));
            option.set("cache", "eova");
            option.save();

            String sql = "update eova_field set type = '下拉框', exp = ? where object_code = ? and en = ?";
            Db.use(Ds.EOVA).update(sql, expCode, objectCode, fieldName);

            LogKit.info("自动绑定字典成功");
        }
    }

    /**
     * 导入元对象
     *
     * @param ds
     * @param type
     * @param table
     * @param name
     * @param code
     * @param pkName
     */
    private void importMetaObject(String ds, String type, String table, String name, String code, String pkName) {

        MetaObject mo = new MetaObject();
        // 编码
        mo.set("code", code);
        // 名称
        mo.set("name", name);
        // 主键
        mo.set("pk_name", pkName);
        // 数据源
        mo.set("data_source", ds);
        // 表或视图
        if (type.equalsIgnoreCase(DsUtil.TABLE)) {
            mo.set("table_name", table);
        } else {
            mo.set("view_name", table);
        }

        mo.save();
    }

    // 刷新元字段数据类型
    @Before(Tx.class)
    @TxConfig(Ds.EOVA)
    public void syncField() {

        List<MetaObject> os = MetaObject.dao.find("select * from eova_object");
        for (MetaObject o : os) {
            List<MetaField> fs = MetaField.dao.queryByObjectCode(o.getCode());

            String ds = o.getDs();
            String table = o.getView();

            JSONArray list = DsUtil.getColumnInfoByConfigName(ds, table, o.isView());
            for (int i = 0; i < list.size(); i++) {
                JSONObject json = list.getJSONObject(i);
                String name = json.getString("COLUMN_NAME");
                for (MetaField f : fs) {
                    // 更新数据类型
                    if (f.getEn().equalsIgnoreCase(name)) {
                        ColumnMeta col = new ColumnMeta(ds, table, json);
                        f.set("data_type", col.dataType);
                        f.set("data_type_name", col.dataTypeName);
                        f.set("data_size", col.dataSize);
                        f.set("data_decimal", col.dataDecimal);
                        // f.set("defaulter", col.defaultValue);
                        f.set("is_auto", col.isAuto);
                        // 自动纠正易错时间配置
                        if (col.dataTypeName.contains("TIMESTAMP") && col.dataTypeName.contains("DATETIME")) {
                            f.set("type", "时间框");
                        } else if (col.dataTypeName.contains("DATE")) {
                            f.set("type", "日期框");
                        }
                        f.update();
                    }
                }
            }
            LogKit.info("元数据刷新成功：" + o.getCode());
        }

        renderJson(new Easy());
    }

    // 拖拽改变序号
    @Before(Tx.class)
    @TxConfig(Ds.EOVA)
    public void swap() {
//		String type = get("type", "meta");
        String code = get(0);// 对象编码
        String s = get(1);// 源字段
        String t = get(2);// 目标字段
        // 不相邻的,直接放到目标的索引+1
        MetaField.dao.updateOrderNum(code, s, t);

        OK();
    }

    @Before(Tx.class)
    @TxConfig(Ds.EOVA)
    public void swapdiy() {
        Integer type = getInt("type", 1);
        String code = get("code");// 对象编码
        String s = get("source");// 源字段
        String t = get("target");// 目标字段

        List<Record> fields = Db.use(Ds.EOVA).find("select id, num, cn from eova_diy where uid = ? and type = ? and object_code = ? order by num", UID(), type, code);
        if (fields.isEmpty()) {
            OK();
            return;
        }

        int t1 = -1;
        int t2 = -1;
        for (Record f : fields) {
            // 找到第一之后的节点
            if (t1 > -1) {
                t2 = f.getInt("num");
                break;
            }
            String cn = f.getStr("cn");
            if (cn.equals(t)) {
                t1 = f.getInt("num");
            }
        }

        // 目标后无节点
        int num = t1 + (t1 / 2);
        if (t2 != -1) {
            // 目标后有节点
            num = (t1 + t2) / 2;
        }

        Db.use(Ds.EOVA).update("update eova_diy set num = ? where uid = ? and type = ? and object_code = ? and cn = ?", num, UID(), type, code, s);

        OK();
    }

    // 添加虚拟元字段
    public void addVirtualField() {
        String en = getInputValue();
        String code = get("object_code");// 场景1:先取快速编辑的参数
        if (x.isEmpty(code)) {
            // 场景2:然后取元数据管理按钮参数
//			code = getSelectValue("code");
            NO("元对象编码不能为空");
            return;
        }

        String fieldName = "v_" + en;

        MetaField template = MetaField.dao.getTemplate();
        template.set("object_code", code);
        template.set("table_name", EovaConst.VIRTUAL);// 通过这个来标识虚拟字段
        template.set("en", fieldName);// 虚拟字段统一前缀,方便识别
        template.set("cn", en);
        template.set("num", 999);
        template.set("formatter", "function(value,row,index,field){return value}");
        template.set("is_order", 0);// 禁止排序
        template.set("is_edit", 0);// 禁止编辑
        template.save();

        // 同步添加DIY字段
        sm.meta.addMetaFieldDiy(code, fieldName);

        OK();
    }

    /********************Eova DataBase Sql Converter**********************/
    public void oracle() {
        boolean isUpgrade = x.conf.getBool("isUpgrade", false);
        if (!isUpgrade) {
            renderText("未开启升级模式，请启动配置 isUpgrade = true");
            return;
        }

        if (!xx.isMysql()) {
            renderText("当前Eova数据源不是Mysql, 无法生成脚本");
            return;
        }

        StringBuilder sb = null;

        System.err.println("正在生成oracle eova sql ing...");
        sb = DbUtil.createOracleSql(Ds.EOVA, "EOVA%");

        sb.append("\n\n\n");

        System.err.println("正在生成oracle  main sql ing...");
        sb.append(DbUtil.createOracleSql(Ds.MAIN, "%"));

        renderText(sb.toString());
    }

    public void pgsql() {
        boolean isUpgrade = x.conf.getBool("isUpgrade", false);
        if (!isUpgrade) {
            renderText("未开启升级模式，请启动配置 isUpgrade = true");
            return;
        }

        if (!xx.isMysql()) {
            renderText("当前Eova数据源不是Mysql, 无法生成脚本");
            return;
        }

        StringBuilder sb = null;

        System.err.println("正在生成PostgreSql eova sql ing...");
        sb = DbUtil.createPGSql(Ds.EOVA, "EOVA%");

        sb.append("\n\n\n");

        System.err.println("正在生成PostgreSql  main sql ing...");
        sb.append(DbUtil.createPGSql(Ds.MAIN, "%"));

        renderText(sb.toString());
    }

    /********************Eova DataBase Sql Converter**********************/

    // 用户个性化设置(type 1=显示,2=查询)
    public void diy() {

        int type = getInt("type", 1);
        String objectCode = get(0);
        set("objectCode", objectCode);

        // 表格可设置可显示的列 默认排序
        String sql = "select * from eova_field where is_show = 1 and object_code = ? order by num";
        // 搜索可设置可查询的列 默认排序
        if (type == 2) {
            sql = "select * from eova_field where is_query = 1 and object_code = ? order by num";
        }
        List<Record> fields = Db.use(Ds.EOVA).find(sql, objectCode);
        List<String> diyField = Db.use(Ds.EOVA).query("select en from eova_diy where uid = ? and type = ? and object_code = ?", UID(), type, objectCode);

        // 补充新字段
        int pos = 0;
        for (Record f : fields) {
            String en = f.getStr("en");
            // 已禁用字段排除(例如字段黑名单策略)
            if (getUser().getDisableFields().contains(objectCode + "." + en)) {
                continue;
            }
            if (!diyField.contains(en)) {

                // 间隔, 后续取中值, 防止序号并列
                pos = pos + 10000;

                // 表格和搜索字段不一样
                String isOpenField = type == 1 ? "is_show" : "is_query";

                Record r = new Record();
                r.set("type", type);
                r.set("uid", UID());
                r.set("object_code", objectCode);
                r.set("en", en);
                r.set("cn", f.getStr("cn"));
                r.set("width", f.getInt("width"));
                r.set("num", (f.getInt("num") + pos));
                r.set("is_open", f.getBoolean(isOpenField));
                Db.use(Ds.EOVA).save("eova_diy", r);
            }
        }
        // 删除旧字段
        MetaUtil.removeUserDiy(objectCode, fields, diyField, UID());

        set("type", type);

        render("/eova/meta/diy.html");
    }

    /**
     * 同步自定义配置
     * 当元数据变更后(手工或脚本), 应手工进行业务对象用户个性化数据的配置
     * 1.不可显示的字段, 清理个性化配置
     * 2.不可查询的字段, 清理个性化配置
     * 3.字段文案变化, 同步更新, 保持一致性.
     */
    public void syncDiy() {
        String objectCode = getSelectValue("code");

        // 已DIY字段
        List<String> diyField = Db.use(Ds.EOVA).query("select DISTINCT(en) from eova_diy where type = 1 and object_code = ?", objectCode);

        // 不可显示字段 清理DIY
        List<String> noShowFields = Db.use(Ds.EOVA).query("select en from eova_field where is_show = 0 and object_code = ?", objectCode);
        Db.use(Ds.EOVA).delete(String.format("delete from eova_diy where type = 1 and object_code = ? and en in (%s)", xx.join(noShowFields, "'", ",")), objectCode);

        // 不可查询字段 清理DIY
        List<String> noQueryFields = Db.use(Ds.EOVA).query("select en from eova_field where is_query = 0 and object_code = ?", objectCode);
        Db.use(Ds.EOVA).delete(String.format("delete from eova_diy where type = 2 and object_code = ? and en in (%s)", xx.join(noQueryFields, "'", ",")), objectCode);

        // 个性化与元字段文案保持同步
        Db.use(Ds.EOVA).update("UPDATE eova_diy di JOIN eova_field ef ON di.en = ef.en SET di.cn = ef.cn WHERE di.cn <> ef.cn and ef.object_code = ?", objectCode);

        OK();
    }


    /* Meta 新增方法*/

    // 刷新当前对象的表达式为EovaOption
    public void option() {
        String object_code = get(0);

        MetaObject object = sm.meta.getMeta(object_code);

        // 仅处理 select 开头的表达式
        String sql = "select * from eova_field where object_code = ? and exp like 'select%'";

        StringBuilder sb = new StringBuilder("EovaExp => EovaOption:\n\n");

        List<Record> list = Db.use(Ds.EOVA).find(sql, object_code);
        for (Record r : list) {
            // select value ID,name CN from eova_dict where object = 'eova_sms' and field = 'status';ds=eova
            String en = r.getStr("en");
            String cn = r.getStr("cn");
            String exp = r.getStr("exp");
            EovaExp ee = new EovaExp(exp);

            String sql1 = ee.sql;

            String code = String.format("%s_%s", object_code, en);
            if (exp.contains("dict")) {
                code = "dict_" + code;
                if (sql1.contains("value ID")) {
                    sql1 = sql1.replaceAll("value ID", "value").replaceAll("name CN", "name");
                }
            }

            List<MetaField> fields = ee.getFields();
            String val = fields.get(0).getStr("en");
            String txt = fields.get(1).getStr("en");

            EovaOption option = EovaOption.create(code, ee.ds, sql1, val, txt);
            option.set("name", String.format("%s_%s", object.getView(), en));
            String cache = ee.get("cache");
            if (!x.isEmpty(cache)) {
                option.set("cache", cache);
            }

            option.save();

            sb.append(String.format("EovaOption转换成功: %s => %s \n", code, exp));

            // 更新表达式
            r.set("exp", code);
            Db.use(Ds.EOVA).update("eova_field", r);
        }

        renderText(sb.toString());
    }
}