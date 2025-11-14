/**
 * Copyright (c) 2015-2026 EOVA.CN. All rights reserved.
 * Licensed under the LGPL-3.0 license
 * For authorization, please contact: admin@eova.cn
 */
package cn.eova.aop;

import com.jfinal.kit.Kv;

/**
 * <pre>
 * 元对象拦截器
 *
 * 背景：业务不仅仅是单表的增删改查
 * 作用：对元对象持久化进行拦截切入，从而进行业务能力拓展
 *
 * AOP如何弹窗提示？
 * return null;// 逻辑正常，没有消息就是好消息
 * return info("弹出一个提示消息");
 * return warn("弹出一个警告消息");
 * return error("弹出一个错误消息");
 * return "弹出一个默认提示";
 * throw new Exception("抛出一个业务异常！！");// 事务回滚
 * </pre>
 *
 * @author Jieven
 * @date 2014-8-29
 */
public class MetaObjectIntercept {

    /**
     * 查询前置任务(DIY复杂查询条件)
     *
     * <pre>
     * 用法：获取查询条件值
     * ac.ctrl.get("query_查询条件字段名");
     *
     * 用法：追加条件
     * ac.condition = "and id < ?";
     * ac.params.add(999);
     *
     * 用法：覆盖条件
     * ac.where = "where id < ?";
     * ac.params.add(5);
     *
     * 用法：覆盖排序
     * ac.sort = "order by id desc";
     *
     * 用法：覆盖整个SQL语句
     * ac.sql = "select * from table";
     * </pre>
     */
    public void queryBefore(AopContext ac) throws Exception {
    }

    /**
     * 查询后置任务
     * <pre>
     * ac.records    获取查询数据集合
     * -------------
     * // 遍历数据集，进行数据操作
     * for (Record r : ac.records){
     *
     *    r.set("total", r.getInt("a") + r.getInt("b"));// 动态计算，total为虚拟字段
     *    r.set("price", String.format("%.2f", price)); // RMB格式化
     *
     * }
     * </pre>
     */
    public void queryAfter(AopContext ac) throws Exception {
    }

    /**
     * 查询汇总合计行
     * <pre>
     * 元对象配置开关 {"totalRow": true}
     *
     * ac.records    获取查询数据集合
     * -------------
     * double sum = 0;
     * for (Record e : ac.records) {
     *     sum += e.getDouble("num");
     * }
     * return new Kv().set("Table的某列", "汇总:(单位/元)").set("Table的某列", sum);
     * </pre>
     */
    public Kv queryFooter(AopContext ac) throws Exception {
        return null;
    }

    /**
     * 新增页初始化
     * <pre>
     * ac.fixed        当前操作对象固定初始值
     * -------------
     * 用法：初始化默认值
     * ac.fixed.set("reply", "您好，");// 回复内容给统一前缀
     * </pre>
     */
    public void addInit(AopContext ac) throws Exception {
    }

    /**
     * 新增前置任务(事务内)
     *
     * <pre>
     * ac.record 当前操作数据
     * -------------
     * 用法：自动赋值
     * ac.record.set("create_time", TimestampUtil.getNow());
     * ac.record.set("create_uid", ac.UID);
     *
     * 用法：唯一值判定
     * int count = Db.queryInt("select count(*) from users where name = ?", ac.record.getStr("name"));
     * if (count > 0) {
     *     return Easy.error("名字不能重复");
     * }
     * </pre>
     */
    public String addBefore(AopContext ac) throws Exception {
        return null;
    }

    /**
     * 新增后置任务(事务内)
     * <pre>
     * ac.record 当前操作数据
     * -------------
     * 用法：级联新增，需在同事务内完成
     * int id = ac.record.getInt("id");// 获取当前对象主键值，进行级联新增
     * </pre>
     */
    public String addAfter(AopContext ac) throws Exception {
        return null;
    }

    /**
     * 新增成功之后(事务外)
     * <pre>
     * ac.records 当前操作数据集：Form模式只有一个对象，Grid模式会有多个对象
     * -------------
     * 用法：关联操作
     * 例：重置缓存，记录日志...
     * </pre>
     */
    public String addSucceed(AopContext ac) throws Exception {
        return null;
    }

    /**
     * 删除前置任务(事务内)
     * <pre>
     * ac.record    当前删除对象数据
     * -------------
     * 用法：删除前置检查
     * </pre>
     */
    public String deleteBefore(AopContext ac) throws Exception {
        return null;
    }

    /**
     * 删除后置任务(事务内)
     * <pre>
     * ac.record    当前删除对象数据
     * -------------
     * 用法：级联删除，父对象删除时级联删除子对象，同一个事务内
     * </pre>
     */
    public String deleteAfter(AopContext ac) throws Exception {
        return null;
    }

    /**
     * 删除成功之后(事务外)<br>
     *
     * ac.records 当前操作数据集：Form模式只有一个对象，Grid模式会有多个对象
     *
     */
    public String deleteSucceed(AopContext ac) throws Exception {
        return null;
    }

    /**
     * 隐藏前置任务(事务内)
     * <pre>
     * ac.record    当前隐藏对象数据
     * -------------
     * 用法：隐藏前置检查
     * </pre>
     */
    public String hideBefore(AopContext ac) throws Exception {
        return null;
    }

    /**
     * 隐藏后置任务(事务内)
     * <pre>
     * ac.record    当前隐藏对象数据
     * -------------
     * 用法：级联隐藏，父对象隐藏时级联隐藏子对象，同一个事务内
     * </pre>
     */
    public String hideAfter(AopContext ac) throws Exception {
        return null;
    }

    /**
     * 隐藏成功之后(事务外)<br>
     *
     * ac.records 当前操作数据集：Form模式只有一个对象，Grid模式会有多个对象
     *
     */
    public String hideSucceed(AopContext ac) throws Exception {
        return null;
    }

    /**
     * 修改页初始化
     * <pre>
     * ac.record    当前操作对象数据
     * ac.fixed        当前操作对象固定初始值
     * -------------
     * 用法：初始化默认值
     * ac.fixed.set("update_time", new Date());// 自动填写当前时间
     * </pre>
     */
    public void updateInit(AopContext ac) throws Exception {
    }

    /**
     * 更新前置任务(事务内)
     * <pre>
     * ac.record 当前操作数据
     * -------------
     * 用法：自动赋值
     * ac.record.set("update_time", new Date());
     * ac.record.set("user_id", ac.user.get("id"));
     *
     * 用法：唯一值判定
     * int count = Db.queryInt("select count(*) from users where name = ?", ac.record.getStr("name"));
     * if (count > 0) {
     *     return Easy.error("名字被占用了");
     * }
     * </pre>
     */
    public String updateBefore(AopContext ac) throws Exception {
        return null;
    }

    /**
     * 更新后置任务(事务内)<br>
     * <pre>
     * ac.record 当前操作数据
     * -------------
     * 用法：级联修改，需在同事务内完成
     * </pre>
     */
    public String updateAfter(AopContext ac) throws Exception {
        return null;
    }

    /**
     * 更新成功之后(事务外)<br>
     * <pre>
     * ac.records 当前操作数据集：Form模式只有一个对象，Grid模式会有多个对象
     * -------------
     * 用法：关联操作
     * 例：重置缓存，记录日志...
     * </pre>
     */
    public String updateSucceed(AopContext ac) throws Exception {
        return null;
    }

    /**
     * 单元格编辑前置
     * <pre>
     * String pk = ac.record.getStr("pk");// 当前行主键值
     * String value = ac.record.getStr("value");// 当前编辑字段名
     * String field = ac.record.getStr("field");// 当前输入值
     * -------------
     * 返回错误信息: return "不能为空";
     * </pre>
     */
    public String updateCellBefore(AopContext ac) throws Exception {
        return null;
    }

    /**
     * 视图单元格编辑
     * <pre>
     * ac.object.getTable(); 获取表名
     * String pk = ac.record.getStr("pk");// 当前行主键值
     * String value = ac.record.getStr("value");// 当前编辑字段名
     * String field = ac.record.getStr("field");// 当前输入值
     * -------------
     * 返回错误信息: return "不能为空";
     * </pre>
     */
    public String updateCell(AopContext ac) throws Exception {
        return "视图默认无法更新, 需要实现 MetaObjectIntercept.updateCell()";
    }

    /**
     * 单元格编辑后置
     * <pre>
     * String pk = ac.record.getStr("pk");// 当前行主键值
     * String value = ac.record.getStr("value");// 当前编辑字段名
     * String field = ac.record.getStr("field");// 当前输入值
     * -------------
     * 返回错误信息: return "不能为空";
     * </pre>
     */
    public String updateCellAfter(AopContext ac) throws Exception {
        return null;
    }

    /**
     * 详情页初始化
     * <pre>
     * ac.record    当前操作对象数据
     * </pre>
     */
    public void detailInit(AopContext ac) throws Exception {
    }

    /**
     * 元字段
     * <pre>
     * ac.object    当前操作元对象(只读)
     * ac.fields    当前操作元字段(读写)
     * </pre>
     */
    public void metadata(AopContext ac) throws Exception {
    }


    protected String info(String msg) {
        return "info:" + msg;
    }

    protected static String warn(String msg) {
        return "warn:" + msg;
    }

    protected static String error(String msg) {
        return "error:" + msg;
    }

}