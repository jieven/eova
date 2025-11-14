/**
 * Copyright (c) 2015-2026 EOVA.CN. All rights reserved.
 * Licensed under the LGPL-3.0 license
 * For authorization, please contact: admin@eova.cn
 */
package cn.eova.meta.api;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import cn.eova.tools.x;
import com.alibaba.druid.DbType;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import cn.eova.aop.AopContext;
import cn.eova.aop.MetaObjectIntercept;
import cn.eova.common.Easy;
import cn.eova.common.base.BaseController;
import cn.eova.common.render.CsvRender;
import cn.eova.common.render.XlsxRender;
import cn.eova.config.EovaConfig;
import cn.eova.config.EovaDataSource;
import cn.eova.config.PageConst;
import cn.eova.hook.EovaMetaHook;
import cn.eova.hook.HookRegistry;
import cn.eova.model.Menu;
import cn.eova.model.MetaField;
import cn.eova.model.MetaObject;
import cn.eova.service.sm;
import cn.eova.template.common.util.TemplateUtil;
import cn.eova.widget.WidgetManager;
import cn.eova.widget.WidgetUtil;
import com.jfinal.core.Controller;
import com.jfinal.kit.LogKit;
import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.IAtom;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.activerecord.dialect.MysqlDialect;

/**
 * Table
 *
 * @author Jieven
 *
 */
public class TableController extends BaseController {

    final Controller ctrl = this;

    /** 元对象业务拦截器 **/
    protected MetaObjectIntercept intercept = null;

    /** 异常信息 **/
    private String errorInfo = "";
    /** 异常信息 **/
    private String msg = "";

    /**
     * 数据查询
     *
     * @throws Exception
     */
    public void query() throws Exception {

        String objectCode = get(0);
        String menuCode = get("biz");// TODO 菜单 演变为业务编码, 不仅是菜单, 可以任意业务搭配 业务场景.
        int pageNumber = getParaToInt(PageConst.PAGENUM, 1);
        int pageSize = getParaToInt(PageConst.PAGESIZE, 100000);

        MetaObject object = sm.meta.getMeta(objectCode);
        Menu menu = Menu.dao.findByCode(menuCode);

        EovaMetaHook hook = HookRegistry.getMeta(objectCode);
        AopContext ac = new AopContext(ctrl, object);
        ac.fields = object.getFields();

        // 构建查询
        List<Object> parmList = new ArrayList<Object>();

        String select = "select " + WidgetManager.buildSelect(object, getUser());
        String sql = null;
        try {
            sql = WidgetManager.buildQuerySQL(ctrl, menu, object, parmList, true, select);
        } catch (Exception e) {
            x.log.error("查询条件异常:", e);
            NO("查询条件错误, 请检查查询条件!");
            return;
        }
        // 自定义全SQL, 去除包装Form并查全部字段, 敏感字段自行控制.
        if (sql.contains(") EVIEW")) {
            select = "select *";
        }

        if (sql.equals("from virtual")) {
            NO("这是一个虚拟元对象, 请在MetaObjectIntercept.queryBefore()中编写SQL查询逻辑");
            return;
        }

        Page<Record> page = Db.use(object.getDs()).paginate(pageNumber, pageSize, select, sql, parmList.toArray());

        // 查询后置任务
//		if (intercept != null) {
//			AopContext ac = new AopContext(ctrl, page.getList());
//			ac.object = object;
//			intercept.queryAfter(ac);
//		}
        if (hook != null) {
            msg = hook.invoke(EovaMetaHook.Action.QUERY_AFTER, ac);
            if (!x.isEmpty(msg)) {
                NO(msg);
                return;
            }
        }

        // 备份Value列，然后将值列转换成Key列
        WidgetUtil.copyValueColumn(page.getList(), object.getPk(), object.getFields());
        // 根据表达式将数据中的值翻译成汉字
        WidgetManager.convertValueByExp(this, object.getFields(), page.getList());

        // 构建JSON数据
//        String ui = ui = "{\"code\": 0, \"msg\": \"\", \"count\":\"%s\",\"data\": %s}";
//
//        StringBuilder sb = new StringBuilder(String.format(ui, page.getTotalRow(), JsonKit.toJson(page.getList())));

        // Footer
//		if (intercept != null) {
//			AopContext ac = new AopContext(ctrl, page.getList());
//			ac.object = object;
//			Kv footer = intercept.queryFooter(ac);
//			if (footer != null) {
//				sb.insert(sb.length() - 1, String.format(",\"footer\":[%s]", footer.toJson()));
//			}
//		}
        renderJson(Ret.ok().set("count", page.getTotalRow()).set("data", page.getList()));
    }

    /**
     * 已废弃 @see ImportCtrl
     *
     * @throws Exception
     */
    @Deprecated
    public void export() throws Exception {

        String objectCode = get(0);
        String menuCode = get("menu_code");
        String type = get("type", "csv");// xls csv

        MetaObject object = sm.meta.getMeta(objectCode);
        Menu menu = Menu.dao.findByCode(menuCode);

        intercept = TemplateUtil.initMetaObjectIntercept(object.getBizIntercept());
        // 元数据拦截器
        if (intercept != null) {
            AopContext ac = new AopContext(this);
            ac.object = object;// 只读
            ac.fields = object.getFields();// 读写
            intercept.metadata(ac);
        }

        // 构建查询
        List<Object> parmList = new ArrayList<Object>();
        String sql = WidgetManager.buildQuerySQL(ctrl, menu, object, parmList, true, "");

        try {
            // count 过滤 order by
            sql = new MysqlDialect().replaceOrderBy(sql);
            Long count = Db.use(object.getDs()).queryLong("select count(*) " + sql, parmList.toArray());
            int maxXls = x.conf.getInt("export.max.xls", 1 * 10000);
            int maxCsv = x.conf.getInt("export.max.csv", 10 * 10000);
            if (count != null && count > maxXls) {
                if (type.equals("xls")) {
                    // 如需导出大数据, 请采用其它方案自定义实现. 比如POI, 分布式导出等.
                    renderText(String.format("[导出XLS]一次最多只能导出%s条, 超过%s条请使用[导出CSV]", maxXls, maxXls));
                    return;
                } else if (type.equals("csv") && count > maxCsv) {
                    // 大数据导出非常占用内存, 所以也建议单独一个项目实现大数据导致, 避免宕机影响其它业务.
                    renderText(String.format("[导出CSV]一次最多只能导出%s条, 超过%s条请联系管理员DB导出", maxCsv, maxCsv));
                    return;
                }
            }
        } catch (Exception e) {
            LogKit.error("导出计算总量异常:" + e.getMessage());
        }

        List<Record> data = Db.use(object.getDs()).find("select * " + sql, parmList.toArray());

        // 查询后置任务
        if (intercept != null) {
            AopContext ac = new AopContext(ctrl, data);
            ac.object = object;
            intercept.queryAfter(ac);
        }

        List<MetaField> fields = object.getFields();

        // 根据表达式将数据中的值翻译成汉字
        WidgetManager.convertValueByExp(this, fields, data);

        Iterator<MetaField> it = fields.iterator();
        while (it.hasNext()) {
            MetaField f = it.next();
            if (!f.getBoolean("is_show")) {
                it.remove();
            }
        }

        try {
            if (type.equals("xls")) {
                render(new XlsxRender(fields, data, object.getName()));
            } else if (type.equals("csv")) {
                render(new CsvRender(fields, data, object.getName()));
            } else {
                renderText("仅支持导出xls和csv");
            }
        } catch (Exception e) {
            LogKit.error("导出异常:" + e.getMessage());
            renderText("导出失败，如果数据过多，请联系管理员导出数据！");
        }
    }


//    private void NO(String msg) {
//        renderJson("{\"code\": 1, \"msg\": \"" + msg + "\", \"count\":\"0\",\"data\": []}");
//    }

    // 更新表格列宽
    public void width() throws Exception {
        String objectCode = get("object");
        String field = get("field");
        int width = getInt("width");

        sm.meta.upodateFieldWdith(objectCode, field, width);

        OK();
    }

    /**
     * json转List
     *
     * @param json
     * @param pkName
     * @param ds
     * @return
     */
    private static List<Record> getRecordsByJson(String json, List<MetaField> items, String pkName, String ds) {
        List<Record> records = new ArrayList<Record>();

        List<JSONObject> list = JSON.parseArray(json, JSONObject.class);
        for (JSONObject o : list) {
            Map<String, Object> map = JSON.parseObject(o + "", new TypeReference<Map<String, Object>>() {
            });
            Record re = new Record();
            re.setColumns(map);
            // 将Text翻译成Value,然后删除val字段
            for (MetaField f : items) {
                String en = f.getEn();// 字段名
                String exp = f.getStr("exp");// 表达式
                Object value = re.get(en);// 值

                if (!x.isEmpty(exp)) {
                    String valField = en + "_val";
                    // 获取值列中的值
                    value = re.get(valField);
                    // 获得值之后删除值列防止持久化报错
                    re.remove(valField);
                }

                re.set(en, EovaConfig.getConvertor(ds).convert(f, value));
            }
            // 删除主键备份值列
            re.remove("pk_val");
            // 删除Orcle分页产生的rownum_
            if (EovaDataSource.getDbType(ds) == DbType.oracle) {
                re.remove("rownum_");
            }
            records.add(re);
        }

        return records;
    }

    /**
     * 单元格编辑
     * @throws Exception
     */
    public void edit() throws Exception {
        String code = get("code");
        String pk = get("pk");
        String field = get("field");
        String val = get("val");// 前端需判定不能为空

        final MetaObject object = sm.meta.getMeta(code);

        // 默认校验:非字符串不能为空值
        msg = strNotEmpty(field, val, object);
        if (!x.isEmpty(msg)) {
            NO(msg);
            return;
        }

        // 参数合并
        Record r = new Record();
        r.set("pk", pk);
        r.set("field", field);
        r.set("value", val);

        EovaMetaHook hook = HookRegistry.getMeta(code);
        AopContext ac = new AopContext(this, object, r);

        // 前置拦截
        if (hook != null) {
            msg = hook.invoke(EovaMetaHook.Action.EDIT_BEFORE, ac);
        }

        // 事务(默认为TRANSACTION_READ_COMMITTED)
        boolean isSucceed = Db.use(object.getDs()).tx(new IAtom() {
            @Override
            public boolean run() throws SQLException {
                try {

                    // 视图编辑: 为保证最大限度灵活性 视图需手工编写持久化逻辑
                    if (object.isView()) {
                        if (hook != null) {
                            msg = hook.invoke(EovaMetaHook.Action.EDIT_ING, ac);
                        } else {
                            x.log.error("视图编辑, 需手工实现持久化逻辑, 请补充MetaObjectHook.Action.EDIT_ING");
                        }
                    } else {
                        Object pkValue = object.buildPkValue(pk);
                        Object fieldValue = object.buildFieldValue(object.getDs(), field, ac.record.getStr("value"));
                        Db.use(object.getDs()).update(String.format("update %s set %s = ? where %s = ?", object.getTable(), field, object.getPk()), fieldValue, pkValue);
                    }

                    if (hook != null) {
                        msg = hook.invoke(EovaMetaHook.Action.EDIT_AFTER, ac);
                    }
                } catch (Exception e) {
                    x.log.error("表格编辑异常", e);
                    msg = "服务异常:" + e.getMessage();
                    return false;
                }
                return true;
            }
        });

        if (!x.isEmpty(msg)) {
            NO(msg);
            return;
        }

        OK();
    }

    public void updateCell() throws Exception {
        String code = get("code");
        String pk = get("pk");
        String field = get("field");
        String val = get("val");// 前端需判定不能为空

        final MetaObject object = sm.meta.getMeta(code);

        Record cell = new Record();
        cell.set("pk", pk);
        cell.set("field", field);
        cell.set("value", val);


        // 默认校验:非字符串不能为空值
        errorInfo = strNotEmpty(field, val, object);

//        intercept = TemplateUtil.initMetaObjectIntercept(object.getBizIntercept());
        EovaMetaHook hook = HookRegistry.getMeta(code);
        try {
//            AopContext ac = new AopContext(this, cell);
            AopContext ac = new AopContext(this, object, cell);

            // 前置拦截
            if (intercept != null) {
                errorInfo = intercept.updateCellBefore(ac);
                // 弹出提示
                if (!x.isEmpty(errorInfo)) {
                    renderJson(Easy.fail(errorInfo));
                    return;
                }
                // 重新获取值(允许拦截器内改变值)
                val = cell.getStr("value");
            }


            // 视图拦截
            if (object.isView() && intercept != null) {
                // 视图必须手工实现持久化
                errorInfo = intercept.updateCell(ac);
            } else {
                // 表直接更新
                Object pkValue = object.buildPkValue(pk);
                Object fieldValue = object.buildFieldValue(object.getDs(), field, val);
                Db.use(object.getDs()).update(String.format("update %s set %s = ? where %s = ?", object.getTable(), field, object.getPk()), fieldValue, pkValue);
            }

            // 后置拦截
            if (intercept != null) {
                errorInfo = intercept.updateCellAfter(ac);
            }

        } catch (Exception e) {
            errorInfo = TemplateUtil.buildException(e);
        }

        if (!x.isEmpty(errorInfo)) {
            renderJson(Easy.fail(errorInfo));
            return;
        }

        renderJson(Easy.sucess());
    }

    private String strNotEmpty(String field, String val, final MetaObject object) {
        if (x.isEmpty(val)) {
            List<MetaField> fields = object.getFields();
            for (MetaField o : fields) {
                if (o.getEn().equals(field)) {
                    Class type = EovaConfig.getConvertor(object.getDs()).getJavaType(o);
                    if (type != String.class) {
                        return "非字符类型, 值不能为空";
                    }
                }
            }
        }
        return null;
    }


}