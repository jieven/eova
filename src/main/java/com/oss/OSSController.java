/**
 * Copyright (c) 2013-2016, Jieven. All rights reserved.
 *
 * Licensed under the GPL license: http://www.gnu.org/licenses/gpl.txt
 * To use it on other terms please contact us at 1623736450@qq.com
 */
package com.oss;

import com.eova.common.Easy;
import com.eova.common.utils.EncryptUtil;
import com.eova.common.utils.xx;
import com.eova.config.EovaConfig;
import com.eova.config.EovaConst;
import com.eova.core.IndexController;
import com.eova.model.User;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

/**
 * 自定义 新增或重写 登录 注册 等各种默认系统业务！！！
 *
 * @author Jieven
 * @date 2016-05-11
 */
public class OSSController extends IndexController {

	@Override
	public void toIndex() {
		System.out.println("简单才是高科技，因为简单所以更快，降低70%开发成本");
		render("/eova/index.html");
	}

	@Override
	protected void loginInit(Controller ctrl, User user) throws Exception {
		super.loginInit(ctrl, user);

		// 添加自定义业务信息到当前用户中
		// UserInfo info = UserInfo.dao.findById(user.get("id"));
		// if (info != null) {
		// user.put("info", info);
		// 	页面或表达式 调用用户信息 ${user.info.nickname}
		// }
	}

	@Override
	public void toLogin() {
		// 新手部署错误引导
		int port = getRequest().getServerPort();
		String name = getRequest().getServerName();
		String project = getRequest().getServletContext().getContextPath();
		if (!project.equals("")) {
			System.out.println("Eova不支持子项目(目录)方式访问,如需同时使用多个项目请使用不同的端口部署Web服务!");
			String ctx = "http://" + name + ':' + port + project;
			setAttr("ctx", ctx);
			render("/eova/520.html");
			return;
		}

		// 初始化帐号密码和提示
		setAttr("login_id", "eova");
		setAttr("login_pwd", "000000");
		setAttr("msg", "角色：超管,帐号:eova,密码:000000");
		// 方便第一次使用的新手和开发快速测试,不需要注释即可!

		super.toLogin();
	}

	public void testing() {
		setAttr("id", "testGrid");
		setAttr("objectCode", getPara(0));
		render("/eova/test.html");
	}
	
	// 临时覆盖,修复BUG,Beta5 Fix
	@Override
	public void doLogin() {
		String loginId = getPara("loginId");
		String loginPwd = getPara("loginPwd");

		boolean isCaptcha = xx.toBoolean(EovaConfig.getProps().get("isCaptcha"), true);
		if (isCaptcha && !super.validateCaptcha("captcha")) {
			setAttr("msg", "验证码错误，请重新输入！");
			toLogin();
			return;
		}

		String userDs = xx.getConfig("login.user.ds", xx.DS_EOVA);
		String userTable = xx.getConfig("login.user.table", "eova_user");
		String userId = xx.getConfig("login.user.id", "id");
		String userAccount = xx.getConfig("login.user.account", "login_id");
		String userPassword = xx.getConfig("login.user.password", "login_pwd");
		String userRid = xx.getConfig("login.user.rid", "rid");

		Record r = Db.use(userDs).findFirst(String.format("select * from %s where %s = ?", userTable, userAccount), loginId);
		if (r == null) {
			setAttr("msg", "用户名不存在");
			toLogin();
			return;
		}
		if (!r.getStr(userPassword).equals(EncryptUtil.getSM32(loginPwd))) {
			setAttr("msg", "密码错误");
			keepPara("loginId");
			toLogin();
			return;
		}

		User user = new User();
		user.set("id", r.get(userId));
		user.set("rid", r.getInt(userRid));
		user.put(userAccount, r.get(userAccount));

		try {
			loginInit(this, user);
			user.init();
		} catch (Exception e) {
			e.printStackTrace();
			setAttr("msg", e.getMessage());
			keepPara("loginId");
			toLogin();
			return;
		}
		setSessionAttr(EovaConst.USER, user);

		// 重定向到首页
		redirect("/");
	}

	// 临时覆盖,修复BUG,Beta5 Fix
	@Override
	public void updatePwd() {
		String oldPwd = getPara("oldPwd");
		String newPwd = getPara("newPwd");
		String confirm = getPara("confirm");

		if (xx.isOneEmpty(oldPwd, newPwd, confirm)) {
			renderJson(new Easy("三个密码都不能为空"));
			return;
		}

		// 新密码和确认密码是否一致
		if (!newPwd.equals(confirm)) {
			renderJson(new Easy("新密码两次输入不一致"));
			return;
		}

		String userDs = xx.getConfig("login.user.ds", xx.DS_EOVA);
		String userTable = xx.getConfig("login.user.table", "eova_user");
		String userId = xx.getConfig("login.user.id", "id");
		String userPassword = xx.getConfig("login.user.password", "login_pwd");

		User user = getSessionAttr(EovaConst.USER);
		Record r = Db.use(userDs).findFirst(String.format("select %s,%s from %s where %s = ?", userId, userPassword, userTable, userId), user.getInt("id"));
		String pwd = r.getStr(userPassword);

		// 旧密码是否正确
		if (!pwd.equals(EncryptUtil.getSM32(oldPwd))) {
			renderJson(new Easy("密码错误"));
			return;
		}

		// 修改密码
		r.set(userPassword, EncryptUtil.getSM32(newPwd));
		Db.use(userDs).update(userTable, r);

		renderJson(new Easy());
	}

}