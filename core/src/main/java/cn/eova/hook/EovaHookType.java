/**
 * Copyright (c) 2015-2026 EOVA.CN. All rights reserved.
 * Licensed under the LGPL-3.0 license
 * For authorization, please contact: admin@eova.cn
 */
package cn.eova.hook;

/**
 * 钩子业务领域
 */
public enum EovaHookType {
    /** 元数据 **/
    META,
    /** 控制器 **/
//    ACTION,
    /** 业务逻辑 **/
//    BIZ,
    /** 用户 **/
    USER,
    /** 审批 **/
    FLOW_ACTION,
    /** 导入 **/
    IMPORT,
    /** 自定义 **/
    DIY;

}
