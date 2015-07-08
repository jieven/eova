/**
 * Copyright (c) 2013-2015, Jieven. All rights reserved.
 *
 * Licensed under the GPL license: http://www.gnu.org/licenses/gpl.txt
 * To use it on other terms please contact us at 1623736450@qq.com
 */
package com.eova.interceptor;

import java.util.List;

import com.eova.model.Menu;
import com.eova.model.User;
import com.jfinal.aop.Interceptor;
import com.jfinal.core.ActionInvocation;

/**
 * 权限验证
 * 
 * @author Jieven
 * @date 2014-9-18
 */
public class AuthInterceptor implements Interceptor {

	@Override
	@SuppressWarnings("unused")
	public void intercept(ActionInvocation ai) {
		String actionKey = ai.getActionKey();
		String uri = ai.getController().getRequest().getRequestURI();

		// 查询当前角色已授权菜单关联有效对象的集合
		if (actionKey.startsWith("/crud/toList")) {
			User user = ai.getController().getSessionAttr("user");
			int rid = user.getInt("rid");

			// 查询当前角色可以访问的URI集合
			// 角色已授权菜单
//			String sql = "select urlCmd from eova_menu where";
			// sql += " code in (select menu_code from eova_button b where b.isBase = 1 AND b.id in (select bid from eova_role_btn where rid = ?))";
//			String sql = "select m.urlCmd from eova_menu m where m.urlCmd != ''";
//			sql +=" and m.id in(select bid from eova_role_btn where rid = ?)";
			
			// 查询当前角色已授权菜单URL
			StringBuilder sb = new StringBuilder();
			sb.append("select m.urlCmd from eova_button b");
			sb.append(" LEFT JOIN eova_role_btn rf on rf.bid = b.id");
			sb.append(" LEFT JOIN eova_menu m on b.menu_code = m.code");
			sb.append(" where rid = ? and b.name = '查询'");
			List<Menu> menus = Menu.dao.find(sb.toString(), rid);
			
			// 角色已授权按钮
			// sql = "select b.bs from eova_button b where b.bs != ''";
			// sql
			// +=" and b.id in (select bid from eova_role_btn where isBtn = 1 and rid = ?)";
			// List<Button> btns = Button.dao.find(sql, rid);
			
			// TODO 因为URI 参数变更，后续按照 用户已授权对象进行操作
//			boolean flag = false;
//			System.out.println(uri);
//			for (Menu x : menus) {
//				if (x.getStr("urlCmd").equals(uri)) {
//					flag = true;
//					break;
//				}
//			}
//			if (!flag) {
			// ai.getController().renderText("对不起，您没有权限进行该操作！");
//				return;
//			}
		}

		ai.invoke();
		// if (actionKey.startsWith("/crud/") ||
		// actionKey.startsWith("/widget/")) {
		// System.out.println("权限验证！");
		// }

		// 自定义功能-过滤URI权限

		// /crud/ + /widget/
		// 访问权限
		//
		// 查询
		// /crud/toList/menuCode=sys_auth_user&objectCode=eova_user_code
		// 修改
		// /crud/update/eova_user_code
		// 新增
		// /crud/add/eova_user_code
		//
		//
		// 数据权限
		//
		// 翻译值
		// /widget/findCnByEn/eova_user_code-rid-12
		// JSON数据
		// /grid/query/eova_user_code

	}

}