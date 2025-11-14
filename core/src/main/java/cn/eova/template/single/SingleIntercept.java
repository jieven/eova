/**
 * Copyright (c) 2015-2026 EOVA.CN. All rights reserved.
 * Licensed under the LGPL-3.0 license
 * For authorization, please contact: admin@eova.cn
 */
package cn.eova.template.single;

import cn.eova.aop.AopContext;

/**
 * 单表模版业务拦截器<br>
 * 前置任务和后置任务(事务未提交)<br>
 * 成功之后(事务已提交)<br>
 *
 * @author Jieven
 * @date 2014-8-29
 */
public class SingleIntercept {

    /**
     * 导入前置任务(事务内)
     *
     */
    public void importBefore(AopContext ac) throws Exception {
    }

    /**
     * 导入后置任务(事务内)
     *
     */
    public void importAfter(AopContext ac) throws Exception {
    }

    /**
     * 导入成功之后(事务外)
     *
     */
    public void importSucceed(AopContext ac) throws Exception {
    }
}