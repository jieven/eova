/**
 * Copyright (c) 2015-2026 EOVA.CN. All rights reserved.
 * Licensed under the LGPL-3.0 license
 * For authorization, please contact: admin@eova.cn
 */
package cn.eova.service;

/**
 * 本地业务注册中心
 * service 留给远程服务（本地的叫Biz）
 * @author Jieven
 *
 */
public class biz {

    /** 登录服务 **/
    public static LoginService login;
    /** 权限服务 **/
    public static AuthService auth;
    /** 元服务 **/
    public static MetaService meta;
    /** 动态表单服务 **/
    public static FormService form;
    /** 导入服务 **/
    public static ImportBiz imports;
    /** 导入服务 **/
    public static MsgBiz msg;

    /** 文件服务 **/
    // public static FileService file;
    public static void init() {
        login = new LoginService();
        auth = new AuthService();
        meta = new MetaService();
        form = new FormService();
        imports = new ImportBiz();
        msg = new MsgBiz();
        // file = new FileService();
    }
}