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
            object = sm.meta.getMeta(objectCode);
            fields = object.getFields();
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
            // 表单元字段预处理
            biz.meta.buildMetaFieldDiy(diy, getUser());
            // 构造个性化配置
            field.put("diy", diy);
            // 构造字段配置(必须手工构造 否则转json后无法获取)
            field.put("conf", field.getConf());
            x.log.info("{} : {}", field.getEn(), diy.getStr("defaulter"));
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
        x.log.info("Form Data JSON:" + record);

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
}