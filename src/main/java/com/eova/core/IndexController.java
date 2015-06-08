/**
 * Copyright (c) 2013-2015, Jieven. All rights reserved.
 *
 * Licensed under the GPL license: http://www.gnu.org/licenses/gpl.txt
 * To use it on other terms please contact us at 1623736450@qq.com
 */
package com.eova.core;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.eova.common.Easy;
import com.eova.common.utils.xx;
import com.eova.model.Menu;
import com.eova.model.User;
import com.eova.service.sm;
import com.jfinal.core.Controller;
import com.jfinal.ext.render.CaptchaRender;

/**
 * Eova入口
 *
 * @author Jieven
 * @date 2015-1-6
 */
public class IndexController extends Controller {

	private final String LOGINID = "loginId";
	private final String LOGINPWD = "loginPwd";

	public void toMain() {
		render("/eova/main.html");
	}

	public void toIndex() {

		render("/eova/index.html");
	}

	public void toHeader() {
		User user = getSessionAttr("user");
		setAttr("user", user);
		render("/eova/header.html");
	}

	public void toIcon() {
		render("/eova/icon.html");
	}
	public void toUe() {
		render("/eova/uedemo.html");
	}

	public void toTest() {
		render("/eova/icon.html");
	}

	
	public void toLogin() {
		render("/eova/login.html");
	}

	/**
	 * 修改密码
	 */
	public void toUpdatePwd() {
		User eu = getSessionAttr("user");
		if (xx.isEmpty(eu)) {
			setAttr("msg", "请先登录");
			toLogin();
			return;
		}
		render("/eova/updatePwd.html");
	}

	public void index() {
		User user = getSessionAttr("user");
		// 已经登录
		if (user != null) {
			int rid = user.getInt("rid");
			// 获取根节点作为模块
			List<Menu> cacheList = Menu.dao.queryRoot();
			// 防止迭代影响Cache，复制一个List
			List<Menu> rootList = new ArrayList<Menu>(cacheList);
			// 过滤当前角没有权限的模块
			// List迭代
			Iterator<Menu> it = rootList.iterator();
			while (it.hasNext()) {
				Menu x = it.next();
				// long count = sm.auth.countByParentId(x.getInt("id"), rid);
				// if (count == 0) {
				// 清除没有授权的根节点
				boolean flag = sm.auth.isExistsAuthByPidRid(x.getInt("id"), rid);
				if (!flag) {
					it.remove();
				}
			}
			setAttr("rootList", rootList);
			toIndex();
			return;
		}
		// 未登录
		toLogin();
	}

	public void doExit() {
		// 清除登录状态
		removeSessionAttr("user");
		toLogin();
	}

	public void doLogin() {
		String loginId = getPara(LOGINID);
		String loginPwd = getPara(LOGINPWD);
//		if (loginId.equals("admin")) {
//			renderJson(new Easy());
//		}
		// 登录校验
		// validateRequiredString("loginId", "msg", "请输入用户名");
		// validateRequiredString("loginPwd", "msg", "请输入密码");
		//
		// c.keepPara("loginId","loginPwd");
		// c.render("/eova/login.html");

		User user = sm.user.getUserByLoginId(loginId);
		if (user == null) {
			setAttr("msg", "用户名不存在");
			toLogin();
			return;
		}
		if (!user.getStr(LOGINPWD).equals(loginPwd)) {
			setAttr("msg", "密码错误");
			keepPara(LOGINID);
			toLogin();
			return;
		}
		// 登录成功
		setSessionAttr("user", user);
		// 重定向到首页
		redirect("/");

	}

	/**
	 * 修改密码
	 */
	public void updatePwd() {
		// 当前用户
		User eu = getSessionAttr("user");
		String pwd = eu.getStr("loginPwd");
		// 旧密码
		String oldPwd = getPara("oldPwd");
		// 旧密码是否正确
		if (!oldPwd.equals(pwd)) {
			renderJson(new Easy("旧密码输入错误"));
			return;
		}
		// 新密码
		String newPwd = getPara("newPwd");
		// 确认密码
		String confirm = getPara("confirm");
		// 新密码和确认密码是否一致
		if (!newPwd.equals(confirm)) {
			renderJson(new Easy("新密码两次输入不一致"));
			return;
		}

		// 修改密码
		eu.set("loginPwd", newPwd).update();

		renderJson(new Easy());
	}

	/**
	 * 获取菜单JSON
	 */
	@SuppressWarnings("rawtypes")
	public void showTree() {
		// 获取父节点
		Integer rootId = getParaToInt(0);
		if (rootId == null) {
			renderJson("系统异常");
			return;
		}
		// 获取登录用户的角色
		User user = getSessionAttr("user");
		int rid = user.getInt("rid");

		// 根据角色获取已授权菜单Code
		List<String> authMenuCodeList = sm.auth.queryMenuCodeByRid(rid);
		// 获取菜单
		List<Menu> menuList = sm.auth.queryMenuByParentId(rootId);

		// 所有菜单节点
		LinkedHashMap<Integer, Menu> allMenu = new LinkedHashMap<Integer, Menu>();
		// 获取已授权的子菜单
		List<Menu> authList = new ArrayList<Menu>();
		for (Menu menu : menuList) {
			allMenu.put(menu.getInt("id"), menu);
			// 是否已授权
			if (authMenuCodeList.contains(menu.getStr("code"))) {
				authList.add(menu);
			}
		}

		// 添加根节点
		allMenu.put(rootId, new Menu());

		// 已授权子菜单的所有上级
		HashMap<Integer, Menu> authParent = new HashMap<Integer, Menu>();

		// 查找已授权子菜单的所有父节点
		for (Menu menu : authList) {
			getParent(allMenu, authParent, menu);
		}

		// 根节点不显示排除
		authParent.remove(rootId);

		// 父节点按序号正序排序(TreeMap默认排序方式)
		TreeMap<String, Menu> sortMap = new TreeMap<String, Menu>();
		Iterator iter1 = authParent.entrySet().iterator();
		while (iter1.hasNext()) {
			Map.Entry entry = (Map.Entry) iter1.next();
			Menu x = (Menu) entry.getValue();
			// 格式化序号,用于字符串排序
			DecimalFormat df = new java.text.DecimalFormat("00000");
			// 序号+ID 作为索引进行排序(防止不同节点的数据会互相覆盖)
			sortMap.put(df.format(x.getInt("indexNum")) + x.getInt("id"), x);
		}
		// System.out.println(sortMap);

		StringBuilder sb = new StringBuilder("[");
		// 显示已授权菜单父节点
		Iterator iter2 = sortMap.entrySet().iterator();
		while (iter2.hasNext()) {
			Map.Entry entry = (Map.Entry) iter2.next();
			Menu x = (Menu) entry.getValue();
			showMenu(sb, x);
			// System.out.println(entry.getKey() + x.getStr("name"));
		}
		// 显示已授权菜单
		for (Menu x : authList) {
			showMenu(sb, x);
		}

		sb.append("]");

		renderJson(sb.toString());
	}

	private void getParent(HashMap<Integer, Menu> allMenu, HashMap<Integer, Menu> authParent, Menu menu) {
		// 获取上级父节点
		Integer pid = menu.getInt("parentId");
		// 上级不存在，说明到了Root
		if (xx.isEmpty(pid)) {
			return;
		}
		Menu pm = allMenu.get(pid);
		authParent.put(pid, pm);
		// 递归上上级
		getParent(allMenu, authParent, pm);
	}

	/**
	 * 移除没有子节点的空菜单
	 * 
	 * @param menu
	 */
	private void showMenu(StringBuilder sb, Menu menu) {
		// 第二个JSON开始加分隔符
		if (sb.length() > 2) {
			sb.append(",");
		}

		String icon = menu.getStr("icon");
		String state = "open";
		// 是否默认折叠
		boolean isCollapse = menu.getBoolean("isCollapse");
		if (isCollapse) {
			state = "closed";
		}
		if (xx.isEmpty(icon)) {
			// 默认图标
			icon = "ext-icon-application";
		}
		sb.append("{");
		sb.append("\"attributes\": {");
		// 目录不显示URL
		if(!menu.getStr("type").equals(Menu.TYPE_DIR) ){
			sb.append("\"url\": \"" + menu.getUrl() + "\"");
//			System.out.println(menu.getUrl());
		}
		sb.append("},\n");
		sb.append("\"checked\": false,");
		sb.append("\"iconCls\": \"" + icon + "\",");
		sb.append("\"id\": \"" + menu.getInt("id") + "\", ");
		int pid = menu.getInt("parentId");
		if (pid != 0) {
			sb.append("\"pid\": \"" + pid + "\",");
		}
		sb.append("\"state\": \"" + state + "\", \n");
		sb.append("\"text\": \"" + menu.getStr("name") + "\"");

		sb.append("}");

	}

	/**
	 * 加载树
	 * 
	 * @param menuList
	 * @return
	 */
	@SuppressWarnings("unused")
	private String loadTree(List<Menu> menuList) {
		Menu root = getTree(menuList);
		StringBuilder sb = new StringBuilder();

		for (Menu parent : root.getChildList()) {
			sb.append("<div class=\"accordionHeader\">");
			sb.append("<h2>");
			sb.append("<span>Folder</span>");
			sb.append(parent.getStr("name"));
			sb.append("</h2>");
			sb.append("</div>");

			sb.append("<div class=\"accordionContent\">");
			sb.append("<ul class=\"tree treeFolder\">");// treeFolder文件夹节点
														// treeCheck复选框节点
														// expand展开所有子项

			sb.append("<li>");
			for (Menu child : parent.getChildList()) {
				expandTree(child, sb);
			}
			sb.append("</li>");
			sb.append("</ul>");
			sb.append("</div>");
		}
		return sb.toString();
	}

	/**
	 * 展开节点
	 * 
	 * @param menu
	 * @param sb
	 */
	private void expandTree(Menu menu, StringBuilder sb) {
		// 树形菜单的属性
		// collapse expand
		if (menu.getChildList() == null) {
			sb.append("<li><a href=\"" + menu.getStr("urlCmd") + "\" target=\"navTab\" rel=\"");
			sb.append(menu.get("code"));
			sb.append("\">" + menu.getStr("name") + "</a>").append("</li>");
		}

		if (menu.getChildList() != null && menu.getChildList().size() != 0) {
			sb.append("<li><a>" + menu.getStr("name") + "</a>");
			sb.append("<ul>");
			for (Menu node : menu.getChildList()) {
				expandTree(node, sb);
			}
			sb.append("</ul>");
			sb.append("</li>");
		}
	}

	/**
	 * List转换Tree Map
	 * 
	 * @param menuList
	 * @return Root
	 */
	private Menu getTree(List<Menu> menuList) {

		// 将List变成 key-value的Map(使用Linked保证排序)
		LinkedHashMap<Integer, Menu> temp = new LinkedHashMap<Integer, Menu>();
		for (Menu menu : menuList) {
			temp.put(menu.getInt("id"), menu);
		}
		// 添加根节点
		temp.put(0, new Menu());

		// 将temp整理成数型结构
		for (Map.Entry<Integer, Menu> map : temp.entrySet()) {
			// 跳过Root节点
			if (map.getKey() == 0) {
				continue;
			}

			Integer pid = map.getValue().getInt("parentId");
			Menu menu = map.getValue();

			// 添加节点到父节点
			// System.out.println(map.toString());
			List<Menu> childList = temp.get(pid).getChildList();
			if (childList == null) {
				childList = new ArrayList<Menu>();
			}
			childList.add(menu);
			temp.get(pid).setChildList(childList);
		}
		// 返回根节点
		return temp.get(0);
	}

	/**
	 * 生成验证码
	 */
	public void vcodeImg() {
		render(new CaptchaRender("VCODEKEY"));
	}

	public static void main(String[] args) {
		// TreeMap<String, String> m = new TreeMap<String, String>();
		// m.put("1_a", "a");
		// m.put("10_a", "b");
		// m.put("2_a", "c");
		// System.out.println(m);
		java.text.DecimalFormat df = new java.text.DecimalFormat("00000");
		int i = 10, j = 6;
		System.out.println("i=" + df.format(i) + "\nj=" + df.format(j));
	}
}