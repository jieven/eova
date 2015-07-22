/**
 * Copyright (c) 2013-2015, Jieven. All rights reserved.
 *
 * Licensed under the GPL license: http://www.gnu.org/licenses/gpl.txt
 * To use it on other terms please contact us at 1623736450@qq.com
 */
package com.eova.config;

import java.io.File;

import com.jfinal.kit.PathKit;

/**
 * 系统配置
 * 
 * @author Jieven
 * @date 2014-5-15
 */
public class EovaConst {
	/** 默认 数据源名称 **/
	public static final String DS_MAIN = "main";
	/** EOVA 数据源名称 **/
	public static final String DS_EOVA = "eova";
	/** Oracle 默认Sequence前缀 **/
	public static final String SEQ_ = "seq_";

	/** 登录用户标识 **/
	public static final String USER = "user";
	
	/** 基本通用功能-新增 **/
	public static final String FUN_QUERY = "查询";
	/** 基本通用功能-新增 **/
	public static final String FUN_ADD = "新增";
	public static final String FUN_ADD_UI = "/eova/template/crud/btn/add.html";
	public static final String FUN_ADD_BS = "crud/add";
	/** 基本通用功能-修改 **/
	public static final String FUN_UPDATE = "修改";
	public static final String FUN_UPDATE_UI = "/eova/template/crud/btn/update.html";
	public static final String FUN_UPDATE_BS = "crud/update";
	/** 基本通用功能-删除 **/
	public static final String FUN_DELETE = "删除";
	public static final String FUN_DELETE_UI = "/eova/template/crud/btn/dels.html";
	public static final String FUN_DELETE_BS = "crud/delete";
	/** 基本通用功能-导入 **/
	public static final String FUN_IMPORT = "导入";
	public static final String FUN_IMPORT_UI = "/eova/template/crud/btn/import.html";
	public static final String FUN_IMPORT_BS = "crud/import";

	/** Cache Key 所有菜单信息 **/
	public static final String ALL_MENU = "all_menu";

	/** 默认超级管理员角色(创建菜单自动给角色授权) **/
	public static final int DEFAULT_RID = 1;

	/** 项目根目录 **/
	public static final String DIR_PROJECT = new File(PathKit.getWebRootPath()).getParentFile().getParentFile().getParentFile().getPath() + File.separator;
	/** WebApp 根目录 **/
	public static final String DIR_WEB = PathKit.getWebRootPath() + File.separator;
	/** 默认图片上传目录 **/
	public static final String DIR_IMG = PathKit.getWebRootPath() + File.separator + "upimg" + File.separator;
	/** 下载临时目录 **/
	public static final String DIR_UPLOAD = PathKit.getWebRootPath() + File.separator + "upload" + File.separator;
	/** 插件目录 **/
	public static final String DIR_PLUGINS = PathKit.getWebRootPath() + File.separator + "plugins" + File.separator;

	/** Eova插件包URL **/
	public static final String PLUGINS_URL = "http://7xign9.com1.z0.glb.clouddn.com/eova_plugins.zip";

}