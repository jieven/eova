/**
 * Copyright (c) 2015-2026 EOVA.CN. All rights reserved.
 * Licensed under the LGPL-3.0 license
 * For authorization, please contact: admin@eova.cn
 */
package cn.eova.ops;

/**
 * 运维配置
 * 集群模式需要分别更新每一个实例的信息
 *
 * @author Jieven
 */
public class OpsConst {

    /** 系统维护 **/
    public static boolean WAITING = false;
    /** 系统维护文案 **/
    public static String WAIT_INFO = "系统临时维护，请稍候再试！";

    /** 部署时间 **/
    public static String DEPLOY_TIME = "";
    /** 部署版本 **/
    public static String DEPLOY_VER = "";
    /** GIT版本 HEAD **/
    public static String DEPLOY_GIT = "";

}