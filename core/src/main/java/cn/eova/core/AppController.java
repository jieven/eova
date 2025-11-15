/**
 * Copyright (c) 2015-2026 EOVA.CN. All rights reserved.
 * Licensed under the LGPL-3.0 license
 * For authorization, please contact: admin@eova.cn
 */
package cn.eova.core;

import java.util.List;

import cn.eova.config.EovaConst;
import cn.eova.model.Button;
import cn.eova.model.Menu;
import cn.eova.model.MetaObject;
import cn.eova.model.User;
import cn.eova.service.sm;
import cn.eova.tools.x;
import cn.eova.widget.WidgetManager;
import com.jfinal.aop.Clear;
import com.jfinal.core.Const;
import com.jfinal.kit.Kv;
import com.jfinal.plugin.activerecord.Record;

/**
 * EOVA模版应用
 *
 * @author Jieven
 */
public class AppController extends IndexController {

    // 运维:心跳监测
    @Clear
    public void live() {
        renderText("200");
    }

    // 运行:实例状态查看
    @Clear
    public void status() {

        String token = get("token");
        String token_ = x.conf.get("app.status.token", "");

        if (x.isEmpty(token)) {
            NO("token is null");
            return;
        }
        if (!token.equals(token_)) {
            NO("token is fail");
            return;
        }

        Kv kv = new Kv();
        kv.set("start_time", EovaConst.START_TIME);// TODO 不方便可以放long
        kv.set("eova", EovaConst.getEovaVer());
        kv.set("jfinal", Const.JFINAL_VERSION);
        // kv.set("jfinal_encoding", Const.DEFAULT_ENCODING);
        // System.out.println(kv);

        renderJson(kv);
    }

    /**
     *
     * 模版渲染入口
     * 模版命名:
     * table 单表
     * table_tree 树表
     * tree 树+表单
     */
    public void index() {
        String menuCode = get(0);


        Menu menu = Menu.dao.findByCode(menuCode);
        String templdate = menu.getTemplate();

        String objectCode = menu.getMenuConfig().getStr("object_code");
        MetaObject object = sm.meta.getMeta(objectCode);

        // 根据权限获取功能按钮
        User user = this.getUser();
        if (user == null) {
            renderMsg("请先登录");
            return;
        }
        List<Button> btnList = Button.dao.queryByMenuCode(menuCode, user.getRid());


        set("object", object);
        set("menu", menu);
        set("menuCode", menuCode);
        setAttr("btnList", btnList);

        renderEnjoy(String.format("/eova/_view/template/%s/index.html", templdate));
    }

    @Clear
    public void errors() {
        Integer status = getInt(0);

        System.out.println("error test");

        renderError(status);
    }

    /**
     * 表单新增
     */
    public void add() {
        String objectCode = get(0);
        String biz = get("biz", "");
        if (x.isEmpty(objectCode)) {
            renderError(404);
            return;
        }
        MetaObject object = sm.meta.getMeta(objectCode);

//        sm.meta.buildMetaField();

        // 依赖参数获取
        Record fixed = WidgetManager.getRef(this);
        System.out.println(fixed);

        set("biz", biz);
        set("object", object);
        set("pk", "id");
        set("fixed", fixed.toJson());

        renderEnjoy("/eova/_view/template/form/add/index.html");
    }

    /**
     * 表单更新
     */
    public void update() {
        String objectCode = get(0);
        String biz = get("biz", "");
        if (x.isEmpty(objectCode)) {
            renderError(404);
            return;
        }
        String id = get("id");

        MetaObject o = sm.meta.getMeta(objectCode);

        // TODO 临时初始化Query
//        List<MetaFieldDiy> list = sm.meta.getFieldDiy(objectCode, "query");
//        if (list.isEmpty()) {
//            for (MetaField f : o.getFields()) {
//                sm.meta.initMetaFieldDiy(objectCode, f);
//            }
//        }

        set("biz", biz);
        set("object", o);
        set("id", id);
        renderEnjoy("/eova/_view/template/form/update/index.html");
    }


    /**
     * 表单查看
     */
    public void detail() {
        String objectCode = get(0);
        String biz = get("biz", "");
        if (x.isEmpty(objectCode)) {
            renderError(404);
            return;
        }
        String id = get("id");

        MetaObject o = sm.meta.getMeta(objectCode);

        set("biz", biz);
        set("object", o);
        set("id", id);
        renderEnjoy("/eova/_view/template/form/detail/index.html");
    }


}