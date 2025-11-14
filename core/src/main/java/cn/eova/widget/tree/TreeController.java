/**
 * Copyright (c) 2015-2026 EOVA.CN. All rights reserved.
 * Licensed under the LGPL-3.0 license
 * For authorization, please contact: admin@eova.cn
 */
package cn.eova.widget.tree;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import cn.eova.tools.x;
import cn.eova.aop.AopContext;
import cn.eova.aop.MetaObjectIntercept;
import cn.eova.common.base.BaseController;
import cn.eova.core.menu.config.TreeConfig;
import cn.eova.model.Menu;
import cn.eova.model.MetaObject;
import cn.eova.service.sm;
import cn.eova.template.common.util.TemplateUtil;
import cn.eova.widget.WidgetManager;
import com.jfinal.core.Controller;
import com.jfinal.kit.JsonKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

/**
 * Tree组件
 *
 * @author Jieven
 *
 */
public class TreeController extends BaseController {

    final Controller ctrl = this;

    /** 元对象业务拦截器 **/
    protected MetaObjectIntercept intercept = null;

    public void query() throws Exception {

        String objectCode = get(0);
        String menuCode = get(1);

        MetaObject object = sm.meta.getMeta(objectCode);
        Menu menu = Menu.dao.findByCode(menuCode);
        TreeConfig tc = null;

        intercept = TemplateUtil.initMetaObjectIntercept(object.getBizIntercept());

        // 构建查询
        List<Object> parmList = new ArrayList<Object>();
        String sql = WidgetManager.buildQuerySQL(ctrl, menu, object, parmList, false, "");

        // 默认查询所有字段(如果想显示图标字段必须叫icon)
        String selelct = "select *";
        // 如果存在配置仅查询配置项
        if (menu != null) {
            // 菜单内的树通过菜单配置获取Tree参数
            tc = menu.getConfig().getTree();
            // 根据Tree配置构造查询项
            selelct = MessageFormat.format("select {0},{1},{2}", tc.getIdField(), tc.getParentField(), tc.getTreeField());
            // 如果有图标
            if (!x.isEmpty(tc.getIconField())) {
                selelct += ',' + tc.getIconField();
            }
            // 如果有非ID外键(eg.tree&table)
            if (!x.isEmpty(tc.getObjectField()) && !tc.getObjectField().equals(tc.getIdField())) {
                selelct += ',' + tc.getObjectField();
            }
        }
        // TODO tree.tag 提交 必须字段,防止浪费查询性能

        // 转换SQL参数
        Object[] paras = new Object[parmList.size()];
        parmList.toArray(paras);
        List<Record> list = Db.use(object.getDs()).find(selelct + " " + sql, paras);

        // 有条件时，自动反向查找父节点数据 (条件会导致父节点丢失)

        if (tc != null && sql.toLowerCase().contains("where")) {
            // 向上查找父节点数据
            WidgetManager.findParent(tc, object.getDs(), "select *", object.getView(), tc.getIdField(), list, list);
        }

        // 查询后置任务
        if (intercept != null) {
            AopContext ac = new AopContext(ctrl, list);
            ac.object = object;
            intercept.queryAfter(ac);
//			list.forEach(i -> {
//
//				i.set("name", String.format("&lt;i class='eova-icon-app'&gt;&lt;/i&gt;%s", i.getStr("name"))) ;
//			});
        }

        renderJson(JsonKit.toJson(list));
    }

}