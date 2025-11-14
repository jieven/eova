/**
 * Copyright (c) 2015-2026 EOVA.CN. All rights reserved.
 * Licensed under the LGPL-3.0 license
 * For authorization, please contact: admin@eova.cn
 */
package cn.eova.widget;

import java.util.ArrayList;
import java.util.List;

import cn.eova.tools.x;
import com.alibaba.fastjson.JSONObject;
import cn.eova.aop.eova.EovaContext;
import cn.eova.common.Easy;
import cn.eova.common.base.BaseController;
import cn.eova.common.utils.db.SqlUtil;
import cn.eova.config.EovaConfig;
import cn.eova.config.PageConst;
import cn.eova.core.menu.config.TreeConfig;
import cn.eova.engine.EovaExp;
import cn.eova.engine.EovaExpBuilder;
import cn.eova.engine.EovaExpParam;
import cn.eova.engine.ExpUtil;
import cn.eova.i18n.I18NBuilder;
import cn.eova.model.EovaOption;
import cn.eova.model.MetaField;
import cn.eova.model.MetaObject;
import com.jfinal.kit.JsonKit;
import com.jfinal.kit.LogKit;
import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

/**
 * EOVA 控件
 *
 * @author Jieven
 *
 */
public class WidgetCtrl extends BaseController {

    /**
     * 查找框Dialog
     */
    public void find() throws Exception {

        String url = "";
        String exp = get("exp");
        String code = get(PageConst.EOVA_CODE);
        String field = get(PageConst.EOVA_FIELD);
        boolean isMultiple = getParaToBoolean("multiple", false);

        String search = get("search", "");// 搜索关键字
        String async = get("async");// 异步模式(直接带回值)

        // 准备数据查询参数根据元字段获取
        if (!x.isEmpty(field)) {
            url += String.format("%s=%s&%s=%s", PageConst.EOVA_CODE, code, PageConst.EOVA_FIELD, field);
        } else {
            url += "exp=" + exp;
        }

        // 构建表达式
        EovaExpBuilder eeb = new EovaExpBuilder(this.getUser(), exp, code, field);
        EovaExp se = eeb.build();

        // 异步模式, 直接给值
        if (!x.isEmpty(async) && !EovaExp.isFormNull(se.from)) {
            List<Object> paras = eeb.getExpParas();

            // 全局拦截过滤
            String fromSql = WidgetManager.buildExpSQL(this, se, paras, buildSearch(se, search, paras));

            // 获取分页参数
            int pageNumber = getParaToInt(PageConst.PAGENUM, 1);
            int pageSize = getParaToInt(PageConst.PAGESIZE, 1);
            Page<Record> page = Db.use(se.ds).paginate(pageNumber, pageSize, se.getSelect(), fromSql, paras.toArray());
            // 命中唯一值, 直接带回
            if (page.getTotalRow() == 1) {
                Record e = page.getList().get(0);
                renderJson(Ret.ok().set("pk", e.get(se.pk)).set("cn", e.get(se.cn)).set("data", e));
                return;
            }

            renderJson(Ret.fail());
            return;
        }

        MetaObject mo = se.getObject();
        List<MetaField> mfs = se.getFields();
        I18NBuilder.models(mfs, "cn");
        if (isMultiple) {
            mo.set("is_single", false);
        }

        url = "/widget/findJson?" + url;
        // 自定义数据查询
        String dataUri = se.get(EovaExpParam.URI);
        if (!x.isEmpty(dataUri)) {
            url = dataUri;
        }
        setAttr("action", url);
        // 用于Grid呈现
        setAttr("objectJson", JsonKit.toJson(mo));
        setAttr("fieldsJson", JsonKit.toJson(mfs));
        // 用于query条件
        setAttr("itemList", mfs);
        setAttr("pk", se.pk);
        setAttr("search", search);

        int mod = getInt("mod", 0);
        if (mod == 1) {
            // 选择提交模式
            render("/eova/widget/find/select.html");
        } else if (mod == 2) {
            // 自定义回调模式
            render("/eova/widget/find/select_callbak.html");
        } else {
            // 查找框模式
            render("/eova/widget/find/find.html");
        }
    }

    /**
     * 构建搜索条件
     * @param se
     * @param search
     * @param paras
     * @return
     */
    private String buildSearch(EovaExp se, String search, List<Object> paras) {
        MetaField nameField = se.getFields().get(1);
        paras.add("%" + search + "%");
        return nameField.getEn() + " like ?";
    }

    /**
     * Find Dialog Grid Get JSON
     */
    public void findJson() {

        String exp = get("exp");
        String code = get(PageConst.EOVA_CODE);
        String field = get(PageConst.EOVA_FIELD);

        try {

            // 构建表达式
            EovaExpBuilder eeb = new EovaExpBuilder(this.getUser(), exp, code, field);
            EovaExp se = eeb.build();
            List<Object> paras = eeb.getExpParas();

            // 查找框有表单条件搜索
            String sql = WidgetManager.buildExpSQL(this, se, paras, null);

            // 获取分页参数
            int pageNumber = getParaToInt(PageConst.PAGENUM, 1);
            int pageSize = getParaToInt(PageConst.PAGESIZE, 15);
            Page<Record> page = Db.use(se.ds).paginate(pageNumber, pageSize, se.getSelect(), sql, paras.toArray());

            I18NBuilder.records(page.getList(), se.cn);

            // 构建JSON数据
            String ui = x.conf.get("ui", "layui");
            if (ui.equals("easyui")) {
                ui = "{\"total\":%s,\"rows\": %s}";
            } else if (ui.equals("layui")) {
                ui = "{\"code\": 0, \"msg\": \"\", \"count\":\"%s\",\"data\": %s}";
            }

            renderJson(String.format(ui, page.getTotalRow(), JsonKit.toJson(page.getList())));
        } catch (Exception e) {
            String msg = exp + " : 查找框查询数据异常:" + e.getMessage();
            LogKit.error(msg, e);
            renderJson(Easy.fail(msg));
        }
    }

    /**
     * Find get CN by value
     */
    public void findCnByEn() {

        String value = get("val");

        String code = get(PageConst.EOVA_CODE);
        String field = get(PageConst.EOVA_FIELD);
        String exp = get("exp");

        // 构建表达式
        EovaExpBuilder eeb = new EovaExpBuilder(this.getUser(), exp, code, field);
        EovaExp se = eeb.build();
        List<Object> paras = eeb.getExpParas();

        String ds = se.ds;

        // 查询本次所有翻译值
        StringBuilder sb = new StringBuilder();
        if (!x.isEmpty(value)) {
            sb.append(se.pk);
            sb.append(" in(");
            // 根据当前页数据value列查询外表name列
            for (String id : value.split(",")) {
                // TODO There might be a sb injection risk warning
                sb.append(x.str.format(id)).append(",");
            }
            sb.deleteCharAt(sb.length() - 1);
            sb.append(")");
        }
        // System.out.println(sb.toString());

        // 根据表达式查询获得翻译的值
        String sql = WidgetManager.addWhere(se, sb.toString());

        List<Record> txts = Db.use(ds).find(sql, x.toArray(paras));
        // 没有翻译值，直接返回原值
        if (x.isEmpty(txts)) {
            JSONObject json = new JSONObject();
            json.put("code", 0);
            json.put("data", value);
            renderJson(json.toJSONString());
            return;
        }

        I18NBuilder.records(txts, se.cn);

        JSONObject json = new JSONObject();
        json.put("code", 1);
        json.put("text_field", se.cn);// 文本字段名
        json.put("data", JsonKit.toJson(txts));// 翻译字典数据
        renderJson(json.toJSONString());
    }


    /**
     * Combo Load Data Get JSON
     */
    @Deprecated
    public void comboJson() {
        String objectCode = get(0);
        String en = get(1);
        String exp = get("exp");

        try {
            List<Record> list = getFieldData(objectCode, en, exp);
            // 直接返回了数据, 未经过Ret 统一包装, 为了兼容历史暂时保留.
            renderJson(list);
        } catch (Exception e) {
            String msg = exp + " : 下拉框查询数据异常:" + e.getMessage();
            LogKit.error(msg, e);
            renderJson(Easy.fail(msg));
        }
    }


    /**
     * 获取表达式数据
     * @param option
     * @param paras
     * @return
     * @throws Exception
     */
    private List<Record> getData(EovaOption option, List<Object> paras) throws Exception {

//        EovaOption option = EovaOption.dao.getByCode(optionCode);
        String exp = option.getSql();
        EovaExp se = new EovaExp(option);

        // 构建表达式
        EovaExpBuilder eeb = new EovaExpBuilder(this.getUser(), option.getSql());
//        EovaExp se = eeb.build();
        // 获取表达式动态参数
//        List<Object> paras = DynamicParse.buildExpPara(exp); //eeb.getExpParas();

        // 全局拦截过滤
        filterExp(se);

        // 获取表达式变更后的最终SQL
        String sql = se.toString();

        // 缓存配置
        String cache = option.getCache();//se.get(EovaExpParam.CACHE);
        List<Record> list = null;
        if (x.isEmpty(cache)) {
            list = Db.use(se.ds).find(sql, paras.toArray());
        } else {
            list = Db.use(se.ds).findByCache(cache, sql, sql, paras.toArray());
        }

        // 兼容处理
        String val = !x.isEmpty(option.getFieldVal()) ? option.getFieldVal() : "id";
        String txt = !x.isEmpty(option.getFieldTxt()) ? option.getFieldTxt() : "cn";
        list.forEach(r -> {
            // 获取显示数据
            r.set("val", r.get(val));
            r.set("txt", r.get(txt));
            r.remove(val, txt);
        });

        I18NBuilder.records(list, txt);
        return list;
    }

    /**
     * 获取字段数据
     * @param objectCode
     * @param en
     * @param exp
     * @return
     * @throws Exception
     */
    private List<Record> getFieldData(String objectCode, String en, String exp) throws Exception {
        // 构建表达式
        EovaExpBuilder eeb = new EovaExpBuilder(this.getUser(), exp, objectCode, en);
        EovaExp se = eeb.build();
        List<Object> paras = eeb.getExpParas();

        // 全局拦截过滤
        filterExp(se);

        // 获取表达式变更后的最终SQL
        String sql = se.toString();

        // 缓存配置
        String cache = se.get(EovaExpParam.CACHE);
        List<Record> list = null;
        if (x.isEmpty(cache)) {
            list = Db.use(se.ds).find(sql, x.toArray(paras));
        } else {
            list = Db.use(se.ds).findByCache(cache, sql, sql, paras.toArray());
        }

        I18NBuilder.records(list, "cn");
        return list;
    }

    /**
     * ComboTree Load Data Get JSON
     */
    public void comboTreeJson() {

        String objectCode = get(0);
        String en = get(1);
        String exp = get("exp");

        try {
            // 构建表达式
            EovaExpBuilder eeb = new EovaExpBuilder(this.getUser(), exp, objectCode, en);
            EovaExp se = eeb.build();
            List<Object> paras = eeb.getExpParas();

            // 全局数据拦截条件
            filterExp(se);

            // 获取表达式变更后的最终SQL
            String sql = se.toString();

            // 缓存配置
            String cache = se.get(EovaExpParam.CACHE);
            List<Record> list = null;
            if (x.isEmpty(cache)) {
                list = Db.use(se.ds).find(sql, x.toArray(paras));
            } else {
                list = Db.use(se.ds).findByCache(cache, sql, sql, paras.toArray());
            }
            TreeConfig treeConfig = new TreeConfig();
            treeConfig.setIdField("id");
            treeConfig.setTreeField("name");
            treeConfig.setParentField("pid");
            treeConfig.setRootPid(se.get(EovaExpParam.ROOT));// 获取表达式自定义参数中rootid,默认为0
            // treeConfig.setIconField("icon"); TODO 暂不支持自定义Tree图标

            // 有条件时，自动方向查找父节点数据
            if (!x.isEmpty(sql.toLowerCase().concat("where"))) {
                // 向上查找父节点数据
                WidgetManager.findParent(treeConfig, se.ds, se.select, se.table, se.pk, list, list);
            }
            renderJson(list);
        } catch (Exception e) {
            String msg = exp + " : 下拉树查询数据异常:" + e.getMessage();
            LogKit.error(msg, e);
            renderJson(Easy.fail(msg));
        }
    }


    /**
     * 全局表达式拦截过滤
     * @param se
     * @throws Exception
     */
    private void filterExp(EovaExp se) throws Exception {
        // 全局数据拦截条件
        if (EovaConfig.getEovaIntercept() != null) {
            EovaContext ec = new EovaContext(this);
            ec.exp = se;

            // 获取全局拦截器表达式公共条件
            String condition = EovaConfig.getEovaIntercept().filterExp(ec);
            // 动态变更表达式条件
            se.where = SqlUtil.appendWhereCondition(se.where, condition);
        }
    }


    /* TODO EovaMeta Widget New */


    /**
     * 获取业务数据
     * 需要动态生成令牌
     */
    public void data() {
        try {
            // 数据类型
//            String type = get("type", "combo");

            String optionCode = get("option");// 控件选项编码
            // 参数校验
            if (x.isEmpty(optionCode)) {
                NO("EovaOption编码不能为空");
                return;
            }

            // 自动纠错 去掉多余符号
            if (optionCode.endsWith(";")) {
                optionCode = optionCode.substring(0, optionCode.length() - 1);
            }

            EovaOption option = EovaOption.dao.getByCode(optionCode);
            if (option == null) {
                NO("EovaOption不存在，编码=" + optionCode);
                return;
            }

            List<Object> paras = new ArrayList<>();

            // 表达式编码;参数1,参数2
            String FG = ";";
            if (optionCode.contains(FG)) {
                // 表达式编码;参数1,参数2
                String[] ss = optionCode.split(FG);
                optionCode = ss[0];
                String[] pms = ss[1].split(",");

                // 获取参数
                paras = ExpUtil.buildExpPara(pms);
            }

            // TODO 未来还需支持自定义解析规则.
            // 接口规范    返回                             编码                SQL参数
            // public EovaOption diyEovaOption(String optionCode, List<Object> paras)

            // 允许缓存3秒（减少前端重复请求）
            getResponse().setHeader("Cache-Control", "public, max-age=1");


            List<Record> list = getData(option, paras);
            renderJson(Ret.data(list).setOk());

//            if (type.equals("combo")) {
//                /**
//                 * object => 元对象编码
//                 * field => 元字段名称
//                 * exp => EovaExp
//                 * multiple => true/false
//                 */
//                String object = get("object");
//                String field = get("field");
//                String exp = get("exp");
//
//            } else if (type.equals("find")) {
//                xx.info("暂不支持的数据模式");
//            }

        } catch (Exception e) {
            String msg = "EovaOption数据获取异常:" + e.getMessage();
            LogKit.error(msg, e);

            NO(msg);
        }
    }

    /**
     * 字段值翻译
     * /widget/text/?value={{value}}&option={{option}}
     * eg. value=1,2,3
     * eg. text=德马
     */
    public void text() {

        String value = get("value");// 控件值
        String code = get("option");// 控件选项编码


        EovaOption option = EovaOption.dao.getByCode(code);
        EovaExp se = new EovaExp(option);

        List<Record> list = new ArrayList<>();
        // 查询需要翻译的数据集(可能为空)
        if (!x.isEmpty(value)) {
            List<Object> paras = new ArrayList<>();//eeb.getExpParas(); TODO 表达式动态传参

            StringBuilder sb = new StringBuilder();
            sb.append(se.pk);
            sb.append(" in(");
            // 根据当前页数据value列查询外表name列
            for (String val : value.split(",")) {
                // TODO There might be a sb injection risk warning
                sb.append(x.str.format(val)).append(",");
            }
            sb.deleteCharAt(sb.length() - 1);
            sb.append(")");

            // 根据表达式查询获得翻译的值
            String sql = WidgetManager.addWhere(se, sb.toString());

            list = Db.use(se.ds).find(sql, paras.toArray());
        }

        // 重新组合
//		Kv data = new Kv();
//		for (Record r : list) {
//			data.put(r.get(se.pk), r.get(se.cn));
//		}
        // 仅字符串
//		String data = "";
//		for (Record r : list) {
//			data += r.getStr(se.cn) + ",";
//		}

        // {"data":"德玛西亚之力2,卡牌大师,冯提莫","state":"ok"}
        // 返回完整查询列数据集, 方便前端丰富显示(例如 TODO Tag 显示)
        Ret ret = Ret.ok().set("data", list).set("field_val", se.pk).set("field_txt", se.cn);

        // 允许缓存3秒（减少前端重复请求）
        getResponse().setHeader("Cache-Control", "public, max-age=1");

        renderJson(ret);
    }

    /**
     * Find 分页查询 TODO EovaMeta New
     */
    public void query() {

        //String exp = get("exp");
        String code = get(0);
        //String field = get(PageConst.EOVA_FIELD);

        try {

            // 构建表达式
            //EovaExpBuilder eeb = new EovaExpBuilder(this.getUser(), exp, code, field);
//			EovaExpBuilder eeb = MetaConst.getExp(this.getUser(), code);
//			EovaExp se = eeb.build();
            String optionCode = MetaConst.getCode(code);
            EovaOption query = EovaOption.dao.getByCode(optionCode);
            EovaExp se = new EovaExp(query);

            List<Object> paras = new ArrayList<>();//eeb.getExpParas(); TODO 表达式动态传参

            // 查找框有表单条件搜索
            String sql = WidgetManager.buildExpSQL(this, se, paras, null);

            // 获取分页参数
            int pageNumber = getParaToInt(PageConst.PAGENUM, 1);
            int pageSize = getParaToInt(PageConst.PAGESIZE, 15);
            Page<Record> page = Db.use(se.ds).paginate(pageNumber, pageSize, se.getSelect(), sql, paras.toArray());

            //I18NBuilder.records(page.getList(), se.cn);

            // 构建JSON数据
            String ui = x.conf.get("ui", "layui");
            if (ui.equals("easyui")) {
                ui = "{\"total\":%s,\"rows\": %s}";
            } else if (ui.equals("layui")) {
                ui = "{\"code\": 0, \"msg\": \"\", \"count\":\"%s\",\"data\": %s}";
            }

            renderJson(String.format(ui, page.getTotalRow(), JsonKit.toJson(page.getList())));
        } catch (Exception e) {
            String msg = "查找框查询数据异常:" + e.getMessage();
            LogKit.error(msg, e);
            renderJson(Easy.fail(msg));
        }
    }

}