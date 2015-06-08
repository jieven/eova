/**
 * Copyright (c) 2013-2015, Jieven. All rights reserved.
 *
 * Licensed under the GPL license: http://www.gnu.org/licenses/gpl.txt
 * To use it on other terms please contact us at 1623736450@qq.com
 */
package com.eova.service;


/**
 * 数据访问集中管理
 * @author Jieven
 *
 */
public class ServiceManager {
	/**初始数据访问**/
	public static IndexService index;
	/**用户数据访问**/
	public static UserService user;
	/**EOVA数据访问**/
	public static EovaService eova;
	/**权限数据访问**/
	public static AuthService auth;
	
	public static void init(){
		index = new IndexService();
		user = new UserService();
		eova = new EovaService();
		auth = new AuthService();
	}
}