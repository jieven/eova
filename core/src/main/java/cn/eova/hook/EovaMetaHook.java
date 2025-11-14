/**
 * Copyright (c) 2015-2026 EOVA.CN. All rights reserved.
 * Licensed under the LGPL-3.0 license
 * For authorization, please contact: admin@eova.cn
 */
package cn.eova.hook;

import cn.eova.aop.AopContext;

/**
 * 元对象钩子
 *
 * @author Jieven
 */
public interface EovaMetaHook extends Hook {

    enum Action {
        /** 新增前置 **/
        ADD_BEFORE,
        /** 新增后置 **/
        ADD_AFTER,
        /** 新增成功 **/
        ADD_SUCCEED,

        /** 修改前置 **/
        UPDATE_BEFORE,
        /** 修改后置 **/
        UPDATE_AFTER,
        /** 修改成功 **/
        UPDATE_SUCCEED,


        /** 删除前置 **/
        DELETE_BEFORE,
        /** 删除后置 **/
        DELETE_AFTER,
        /** 删除成功 **/
        DELETE_SUCCEED,

        /** 逻辑删除前置 **/
        HIDE_BEFORE,
        /** 逻辑删除后置 **/
        HIDE_AFTER,
        /** 逻辑删除成功 **/
        HIDE_SUCCEED,

        /** 查询前置 **/
        QUERY_BEFORE,
        /** 查询后置 **/
        QUERY_AFTER,

        /** 编辑前置 **/
        EDIT_BEFORE,
        /** 编辑进行时 **/
        EDIT_ING,
        /** 编辑后置 **/
        EDIT_AFTER,

        /** 获取数据(修改/详情) **/
        GET_DATA

    }

    String invoke(Action action, AopContext ac) throws Exception;

}
