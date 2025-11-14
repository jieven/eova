/**
 * Copyright (c) 2015-2026 EOVA.CN. All rights reserved.
 * Licensed under the LGPL-3.0 license
 * For authorization, please contact: admin@eova.cn
 */
package cn.eova.meta.ctrl;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.eova.aop.AopContext;
import cn.eova.common.Ds;
import cn.eova.common.base.BaseController;
import cn.eova.common.render.CsvRender;
import cn.eova.common.render.XlsxRender;
import cn.eova.hook.EovaMetaHook;
import cn.eova.hook.HookRegistry;
import cn.eova.model.Menu;
import cn.eova.model.MetaObject;
import cn.eova.service.biz;
import cn.eova.service.sm;
import cn.eova.tools.x;
import cn.eova.widget.WidgetManager;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import org.ttzero.excel.reader.ExcelReader;

/**
 * 数据导入
 *
 * @author Jieven
 */
public class ImportController extends BaseController {

    final Controller ctrl = this;

    public void imports() {
        String code = get(0);

        Record template = Db.use(Ds.EOVA).findFirst("select * from eova_import_template where code = ?", code);
        set("template", template);
        render("/excel/import/app.html");
    }

    public void doImport() {
        String code = get("code");
        String type = get("type");
        String file = get("file");
        String oldFile = get("old_file");


        // 记录导入记录
        Record log = new Record();
        log.set("status", 1);
        log.set("code", code);
        log.set("file_name", oldFile);
        log.set("user_id", UID());
        log.set("company_id", getUser().getCompanyId());
        log.set("start_time", new Date());
        log.set("info", type.equals("add") ? "仅新增模式" : "仅更新模式");

        Db.use(Ds.EOVA).save("eova_import", log);

        // 文件根目录
        String baseDir = x.conf.get("file.dir.base");
        String importDir = x.conf.get("file.dir.excel", "excel");

        String path = String.format("%s%s/%s", baseDir, importDir, file);
        System.out.println(path);
        // 异步执行
        biz.imports.start(UID(), code, type, path, log);

        OK();
    }

    public void read() {
        try (ExcelReader reader = ExcelReader.read(Paths.get("F:\\excel\\read.xlsx"))) {
            // 按行读取第1个Sheet并打印
            reader.sheet(0).rows().forEach(System.out::println);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        OK();
    }

    public void export() throws Exception {

        long t = x.time.now();

        String objectCode = get(0);
        String menuCode = get("biz");// TODO 菜单 演变为业务编码, 不仅是菜单, 可以任意业务搭配 业务场景.
        String type = get("type", "xlsx");

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
        List<Record> data = Db.use(object.getDs()).find(String.format("%s %s limit 0, 1000000", select, sql), parmList.toArray());

        if (type.equals("xlsx")) {
            render(new XlsxRender(object.getFields(), data, object.getName()));
        } else if (type.equals("csv")) {
            render(new CsvRender(object.getFields(), data, object.getName()));
        }
        x.time.costTime(t);
    }

//        String fileName = t + ".xlsx";
//        String baseDir = x.conf.get("file.dir.base");
//        String path = String.format("%s/%s", baseDir, fileName);

//        ExcePlusUtil.export(path, object.getFields(), data);
//        System.out.println("导出成功:" + path);

//        render(new XlsxRender(object.getFields(), data, object.getName()));
//        render(new DownloadRender(object.getName(), "xlsx", path));

}