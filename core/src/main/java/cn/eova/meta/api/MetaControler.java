/**
 * Copyright (c) 2015-2026 EOVA.CN. All rights reserved.
 * Licensed under the LGPL-3.0 license
 * For authorization, please contact: admin@eova.cn
 */
package cn.eova.meta.api;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import cn.eova.common.Ds;
import cn.eova.common.base.BaseCache;
import cn.eova.common.base.BaseController;
import cn.eova.engine.EovaExp;
import cn.eova.model.Button;
import cn.eova.model.EovaOption;
import cn.eova.model.EovaProps;
import cn.eova.model.MetaField;
import cn.eova.model.MetaFieldDiy;
import cn.eova.model.MetaObject;
import cn.eova.model.User;
import cn.eova.service.biz;
import cn.eova.service.sm;
import cn.eova.tools.x;
import cn.eova.widget.MetaConst;
import cn.eova.widget.WidgetManager;
import com.jfinal.core.Controller;
import com.jfinal.kit.Kv;
import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.ehcache.CacheKit;

/**
 * 元数据处理
 */
public class MetaControler extends BaseController {

    final Controller ctrl = this;

    public void table() {
        String objectCode = get(0);

        MetaObject object = null;
        List<MetaField> fields = null;

        if (objectCode.startsWith("eova_find")) {

            // Find元对象 获取虚拟元数据
            String code = MetaConst.getCode(objectCode);
            EovaOption option = EovaOption.dao.getByCode(code);
            EovaExp se = new EovaExp(option);

            object = se.getObject();
            fields = se.getFields();
        } else {
            User user = getUser();
            if (user == null) {
                NO("用户未登录，无法获取数据");
                return;
            }
            // 分别获取元对象与元字段
            object = MetaObject.dao.getByCode(objectCode);
            fields = MetaField.dao.queryFields(objectCode, user);
        }

        Ret ret = Ret.ok().set("object", object).set("fields", fields);

        renderJson(ret);
    }

    public void form() {
        String objectCode = get(0);
        String mode = get("mode");

        MetaObject object = sm.meta.getMeta(objectCode);
        // 表单个性化数据
        List<MetaFieldDiy> diys = sm.meta.getMetaFieldDiy(objectCode, mode);
        if (x.isEmpty(diys)) {
            throw new RuntimeException(String.format("元字段个性化数据缺失:[%s-%s]", objectCode, mode));
        }
        Map<String, MetaFieldDiy> map = diys.stream().collect(Collectors.toMap(m -> m.getStr("en"), m -> m));
        for (MetaField field : object.getFields()) {
            MetaFieldDiy diy = map.get(field.getEn());
            if (diy == null) {
                throw new RuntimeException(String.format("元字段个性化数据缺失:[%s-%s]", objectCode, field.getEn()));
            }
            // 表单元字段预处理
            biz.meta.buildMetaFieldDiy(diy, getUser());
            // 构造个性化配置
            field.put("diy", diy);
            // 构造字段配置(必须手工构造 否则转json后无法获取)
            field.put("conf", field.getConf());
            // x.log.info("{} : {}", field.getEn(), diy.getStr("defaulter"));
        }


        renderJson(Ret.ok()
                .set("object", object)
                .set("fields", object.getFields())
        );
    }

    public void option() throws Exception {
        User user = getUser();
        if (user == null) {
            throw new RuntimeException("用户不存在, Beetl Query 需要根据用户角色权限获取元字段!");
        }

        String code = get(0);
        EovaOption option = EovaOption.dao.getByCode(code);
        EovaExp se = new EovaExp(option);

        // 可查询字段(默认仅查文本字段)
        String queryField = option.getConf("field_query", option.getFieldTxt());
        List<String> querys = Arrays.asList(queryField.split(","));
        List<MetaField> fields = se.getFields().stream()
                .filter(f -> querys.contains(f.getStr("en")))
                .collect(Collectors.toList());

        Ret ret = Ret.ok().set("option", option).set("fields", fields);

        renderJson(ret);
    }

    public void setting() throws Exception {


        String bizCode = get("biz");
        List<EovaProps> templateSets = EovaProps.dao.findTemplateByCode(bizCode);

        Kv templateKv = new Kv();
        for (EovaProps option : templateSets) {
            Object value = option.get("value");
            templateKv.set(option.get("option"), value);
        }

//        Ret ret = Ret.ok().set("data", templateKv);

        renderJson(templateKv);
    }

    public void getQueryField() {

        // 查询时, 默认使用类型对应的控件, 特殊情况下需要 需要替换成别的控件 例如编辑框得使用普通文本框进行搜索.
        HashMap<String, String> queryFromTypes = new HashMap<>();
        queryFromTypes.put("精准框", "文本框");
        queryFromTypes.put("文本框", "文本框");
        queryFromTypes.put("文本域", "文本框");
        queryFromTypes.put("编辑框", "文本框");
        queryFromTypes.put("多图框", "文本框");
        queryFromTypes.put("图片框", "文本框");
        queryFromTypes.put("文件框", "文本框");
        queryFromTypes.put("图片", "文本框");
        queryFromTypes.put("文件", "文本框");

        queryFromTypes.put("日期框", "日期");// TODO dateFmt: 'yyyy-MM-dd'
        queryFromTypes.put("时间框", "时间");// TODO startDate:'%y-%M-%d %H:00:00', dateFmt: 'yyyy-MM-dd HH:mm:ss'

    }

    // Post JSON
    public void add() {
        String table = get("table");

        Ret ret = getJsonToRet();

        Db.save(table, new Record().setColumns(ret));

        OK();
    }

    /*
        Form 编辑
     */

    public void formUpdate() throws Exception {

        String objectCode = this.get(0);

        final MetaObject object = sm.meta.getMeta(objectCode);

        // 获取查询数据(JSON) TODO EovaMeta Form
        Record record = getJsonToRecord();
        x.log.debug("Form Data JSON:" + record);

//        Record record = Json.getJson().parse(json.toJSONString(), Record.class);

        if (!x.isEmpty(object.getTable())) {
            // 剥离虚拟字段
            Record t = WidgetManager.peelVirtual(record);
            Db.use(object.getDs()).update(object.getTable(), object.getPk(), record);
            // 还原虚拟字段
            record.setColumns(t);
        } else {
            // update view
            // WidgetManager.operateView(TemplateConfig.UPDATE, object, record);
            // 视图无法自动操作，请自定义元对象业务拦截完成持久化逻辑！
        }

        OK();
    }

    public void auth() {
        // 业务 默认为菜单编码(业务不仅限于菜单, 例如一个子弹出的功能界面, 也可能是自定义的, 也可以进行资源统一授权.)
        String biz = this.get("biz");

        // 根据权限获取功能按钮
        User user = this.getUser();
        List<Button> btnList = Button.dao.queryByMenuCode(biz, user.getRid());


        renderJson(btnList);
    }

    /**
     * 移除字段，更新元数据
     */
    public void removeField() {
        String object = get("object");
        String field = get("field");
        String mode = get("mode");

        if (x.isEmpty(object) || x.isEmpty(field) || x.isEmpty(mode)) {
            NO("参数不完整");
            return;
        }

        if (mode.equals("table")) {
            Db.use(Ds.EOVA).update("update eova_field set is_show = 0 where object_code = ? and en = ?", object, field);
        } else if (mode.equals("query") || mode.equals("create") || mode.equals("update") || mode.equals("read")) {
            // 1正常 2只读 3隐藏 4禁用
            Db.use(Ds.EOVA).update("update eova_field_diy set status = 4 where object_code = ? and en = ? and mode = ?", object, field, mode);
        } else {
            NO("不支持的业务场景");
            return;
        }

        OK();
    }

    /**
     * 移除工具栏按钮（逻辑删除）。查询按钮不可删。
     * /api/meta/removeButton?menu=xxx&id=xxx
     */
    public void removeButton() {
        String menu = get("menu");
        String id = get("id");

        if (x.isEmpty(menu) || x.isEmpty(id)) {
            NO("参数不完整");
            return;
        }

        Button btn = Button.dao.findById(id);
        if (btn == null || !menu.equals(btn.getStr("menu_code"))) {
            NO("按钮不存在");
            return;
        }
        if (Button.FUN_QUERY_UI.equals(btn.getStr("ui")) || Button.FUN_QUERY_NAME.equals(btn.getStr("name"))) {
            NO("查询按钮不可删除");
            return;
        }

        int rows = Db.use(Ds.EOVA).update("update eova_button set is_hide = 1 where id = ? and menu_code = ?", id, menu);
        if (rows < 1) {
            NO("按钮删除失败");
            return;
        }

        // 清掉按角色缓存的按钮列表，刷新即可看到结果
        User user = getUser();
        if (user != null) {
            String findRoleBtn = "select bid from eova_role_btn where rid = ?";
            String sql = "select * from eova_button where is_hide <> 1 and menu_code = ? and id in (" + findRoleBtn + ") order by num";
            CacheKit.remove(BaseCache.SER, sql + "_" + menu + "_" + user.getRid());
        }
        OK();
    }

    /**
     * 修改字段中文名。表单与表格共用 eova_field.cn
     * /api/meta/renameField?object=xxx&field=xxx&cn=xxx
     */
    public void renameField() {
        String object = get("object");
        String field = get("field");
        String cn = get("cn");

        if (x.isEmpty(object) || x.isEmpty(field) || x.isEmpty(cn)) {
            NO("参数不完整");
            return;
        }

        int rows = Db.use(Ds.EOVA).update("update eova_field set cn = ? where object_code = ? and en = ?", cn, object, field);
        if (rows < 1) {
            NO("字段不存在");
            return;
        }
        Db.use(Ds.EOVA).update("update eova_field_diy set cn = ? where object_code = ? and en = ?", cn, object, field);
        clearFieldCache(object);
        OK();
    }

    /** 字段名变更后清掉列表/表单用的短缓存，刷新即可看到新中文名 */
    private void clearFieldCache(String object) {
        CacheKit.remove(BaseCache.SER, "select * from eova_field where object_code = ? order by num_" + object);
        CacheKit.remove(BaseCache.SER, "select * from eova_field where object_code = ? and is_show = 1 order by fieldnum,num_" + object);
        for (String mode : new String[]{"query", "create", "update", "read"}) {
            CacheKit.remove(BaseCache.SER, "select * from eova_field_diy where object_code = ? and mode = ? order by num_" + object + "_" + mode);
        }
    }

}