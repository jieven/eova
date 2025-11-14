/**
 * Copyright (c) 2015-2026 EOVA.CN. All rights reserved.
 * Licensed under the LGPL-3.0 license
 * For authorization, please contact: admin@eova.cn
 */
package cn.eova.meta.api;

import java.sql.SQLException;
import java.util.List;

import cn.eova.aop.AopContext;
import cn.eova.common.base.BaseController;
import cn.eova.hook.EovaMetaHook;
import cn.eova.hook.HookRegistry;
import cn.eova.model.MetaObject;
import cn.eova.service.sm;
import cn.eova.tools.x;
import cn.eova.widget.WidgetManager;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.IAtom;
import com.jfinal.plugin.activerecord.Record;

/**
 * 通用表单持久化逻辑
 */
public class FormControler extends BaseController {

    final Controller ctrl = this;
    private String msg = "";

    // 远程校验测试
    public void validate() {
        try {
            String val = get("val");
            if (val.equals("秦始皇")) {
                renderText("");
            } else {
                renderText("赢麻了, 必填秦始皇!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            renderText("校验服务错误");
        }

    }

    // 新增
    public void add() throws Exception {

        String objectCode = this.get(0);
        // 获取查询数据(JSON) TODO EovaMeta Form
        Record record = getJsonToRecord();


        x.log.info("Form Data JSON:" + record);

        MetaObject object = sm.meta.getMeta(objectCode);
        EovaMetaHook hook = HookRegistry.getMeta(objectCode);
        AopContext ac = new AopContext(ctrl, object, record);

        // 事务(默认为TRANSACTION_READ_COMMITTED)
        boolean isSucceed = Db.use(object.getDs()).tx(new IAtom() {
            @Override
            public boolean run() throws SQLException {
                try {
                    if (hook != null) {
                        msg = hook.invoke(EovaMetaHook.Action.ADD_BEFORE, ac);
                        if (!x.isEmpty(msg)) return false;
                    }

                    if (!x.isEmpty(object.getTable())) {
                        // 剥离虚拟字段
                        Record t = WidgetManager.peelVirtual(record);
                        Db.use(object.getDs()).save(object.getTable(), object.getPk(), record);
                        // 还原虚拟字段
                        record.setColumns(t);
                    } else {
                        // update view
                        // 视图无法自动操作，请自定义元对象业务拦截完成持久化逻辑！
                    }

                    if (hook != null) {
                        msg = hook.invoke(EovaMetaHook.Action.ADD_AFTER, ac);
                        if (!x.isEmpty(msg)) return false;
                    }
                } catch (Exception e) {
                    x.log.error(e, "新增拦截异常");
                    msg = "服务异常:" + e.getMessage();
                    return false;
                }
                return true;
            }
        });

        // 操作成功之后
        if (isSucceed && hook != null) {
            try {
                msg = hook.invoke(EovaMetaHook.Action.ADD_SUCCEED, ac);
            } catch (Exception e) {
                x.log.error(e, "新增成功拦截异常");
                msg = "服务异常:" + e.getMessage();
            }
        }

        if (!x.isEmpty(msg)) {
            NO(msg);
            return;
        }

        OK();
    }

    // 更新
    public void update() throws Exception {

        String objectCode = this.get(0);

        // 获取查询数据(JSON) TODO EovaMeta Form
        Record record = getJsonToRecord();
        x.log.info("Form Data JSON:" + record);

        MetaObject object = sm.meta.getMeta(objectCode);
        EovaMetaHook hook = HookRegistry.getMeta(objectCode);
        AopContext ac = new AopContext(ctrl, object, record);

        // 事务(默认为TRANSACTION_READ_COMMITTED)
        boolean isSucceed = Db.use(object.getDs()).tx(new IAtom() {
            @Override
            public boolean run() throws SQLException {
                try {

                    if (hook != null) {
                        msg = hook.invoke(EovaMetaHook.Action.UPDATE_BEFORE, ac);
                        if (!x.isEmpty(msg)) return false;
                    }

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

                    if (hook != null) {
                        msg = hook.invoke(EovaMetaHook.Action.UPDATE_AFTER, ac);
                        if (!x.isEmpty(msg)) return false;
                    }
                } catch (Exception e) {
                    x.log.error(e, "更新拦截异常");
                    msg = "服务异常:" + e.getMessage();
                    return false;
                }
                return true;
            }
        });

        // 操作成功之后
        if (isSucceed && hook != null) {
            try {
                msg = hook.invoke(EovaMetaHook.Action.UPDATE_SUCCEED, ac);
            } catch (Exception e) {
                x.log.error(e, "更新成功拦截异常");
                msg = "服务异常:" + e.getMessage();
            }
        }

        if (!x.isEmpty(msg)) {
            NO(msg);
            return;
        }

        OK();
    }

    // 获取数据
    public void data() throws Exception {
        String objectCode = this.get(0);
        String pk = get("pk");

        Record record = sm.meta.getDataByMetaObject(objectCode, pk);

        MetaObject object = sm.meta.getMeta(objectCode);
        EovaMetaHook hook = HookRegistry.getMeta(objectCode);
        AopContext ac = new AopContext(ctrl, object, record);

        if (hook != null) {
            hook.invoke(EovaMetaHook.Action.GET_DATA, ac);
        }

        // 固定值, 覆盖表单数据 TODO, 新增和修改 需要处理 INIT
        // 构建关联参数值
//         Record fixed = WidgetManager.getRef(this);
//        setAttr("fixed", fixed);

        renderJson(record.toJson());
    }

    // 物理删除
    public void delete() throws Exception {
        deleteOrHide(true);
    }

    // 逻辑删除
    public void hide() throws Exception {
        deleteOrHide(false);
    }

    private void deleteOrHide(boolean isDel) throws Exception {
        String objectCode = this.get(0);

        List<Record> records = getJsonToRecords("rows");
        System.out.println(records);

        MetaObject object = sm.meta.getMeta(objectCode);
        EovaMetaHook hook = HookRegistry.getMeta(objectCode);
        AopContext ac = new AopContext(ctrl, object, records);

        // 事务
        boolean isSucceed = Db.use(object.getDs()).tx(new IAtom() {
            @Override
            public boolean run() throws SQLException {

                try {
                    for (Record record : records) {
                        // 当前操作数据
                        ac.record = record;
                        // 前置任务
                        if (hook != null) {
                            if (isDel) {
                                msg = hook.invoke(EovaMetaHook.Action.DELETE_BEFORE, ac);
                            } else {
                                msg = hook.invoke(EovaMetaHook.Action.HIDE_BEFORE, ac);
                            }
                            if (!x.isEmpty(msg)) return false;
                        }

                        if (!x.isEmpty(object.getTable())) {
                            String pk = object.getPk();
                            String pkValue = record.get(pk).toString();

                            if (isDel) {
                                // 删除数据
                                Db.use(object.getDs()).deleteById(object.getTable(), pk, object.buildPkValue(pkValue));
                            } else {
                                // 隐藏数据
                                String hideFieldName = x.conf.get("hide_field_name", "is_hide");
                                String sql = String.format("update %s set %s = 1 where %s = ?", object.getTable(), hideFieldName, pk);
                                Db.use(object.getDs()).update(sql, pkValue);
                            }
                        } else {
                            System.err.println("视图无法自动删除，请自定义元对象业务拦截完成删除逻辑！");
                        }

                        // 后置任务
                        if (hook != null) {
                            if (isDel) {
                                msg = hook.invoke(EovaMetaHook.Action.DELETE_AFTER, ac);
                            } else {
                                msg = hook.invoke(EovaMetaHook.Action.HIDE_AFTER, ac);
                            }
                            if (!x.isEmpty(msg)) return false;
                        }
                    }
                } catch (Exception e) {
                    x.log.error(e, "删除拦截异常");
                    msg = "服务异常:" + e.getMessage();
                    return false;
                }
                return true;
            }
        });

        // 删除成功之后
        if (isSucceed && hook != null) {
            try {
                if (isDel) {
                    msg = hook.invoke(EovaMetaHook.Action.DELETE_SUCCEED, ac);
                } else {
                    msg = hook.invoke(EovaMetaHook.Action.HIDE_SUCCEED, ac);
                }
            } catch (Exception e) {
                x.log.error(e, "删除成功拦截异常");
                msg = "服务异常:" + e.getMessage();
            }
        }

        if (!x.isEmpty(msg)) {
            NO(msg);
            return;
        }

        OK();
    }


}