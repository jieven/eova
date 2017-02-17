/**
 * Copyright (c) 2013-2016, Jieven. All rights reserved.
 *
 * Licensed under the GPL license: http://www.gnu.org/licenses/gpl.txt
 * To use it on other terms please contact us at 1623736450@qq.com
 */
package com.oss;

import java.io.InputStream;
import java.net.URL;

import com.eova.config.EovaConfig;
import com.eova.core.IndexController;
import com.eova.model.User;
import com.jfinal.core.Controller;
import com.jfinal.kit.LogKit;
import com.oss.model.UserInfo;

/**
 * 自定义 新增或重写 登录 注册 等各种默认系统业务！！！
 *
 * @author Jieven
 * @date 2016-05-11
 */
public class OSSController extends IndexController {

	@SuppressWarnings("null")
	@Override
	public void toIndex() {
		System.out.println("我是来自草原的狼，自由奔跑在EOVA上，想干嘛就干嘛！");
		String CDN = EovaConfig.getProps().get("domain_cdn");
		InputStream in = null;
		try {
//			if (!xx.isEmpty(CDN)) {
//				URL url = new URL(CDN + "/plugins/easyui/jquery.easyui.min.js");
//				LogKit.info("CDN检测：" + url);
//				in = url.openStream();
//			}
		} catch (Exception e1) {
			renderText("CDN域名已配置，但是无法访问相关静态资源，请检查CDN服务是否正常！ domain_cdn=" + CDN);
			return;
		} finally {
			try {
				in.close();
			} catch (Exception e) {
			}
		}
		render("/eova/index.html");
	}

	@Override
	protected void loginInit(Controller ctrl, User user) throws Exception {
		super.loginInit(ctrl, user);

		// 添加自定义业务信息到当前用户中
		UserInfo info = UserInfo.dao.findById(user.get("id"));
		if (info != null) {
			user.put("info", info);
			// 页面或表达式 调用用户信息 ${user.info.nickname}
		}

		// 还可以将相关信息放入session中
		// ctrl.setSessionAttr("UserInfo", info);
	}

	@Override
	public void toLogin() {

		// TODO 新手部署错误引导页(熟悉之后可以去掉本方法的重写！)
		int port = getRequest().getServerPort();
		String name = getRequest().getServerName();
		String ctx = "http://" + name + ':' + port;
		InputStream in = null;
		try {
			URL url = new URL(ctx + "/ui/css/common.css");
			LogKit.info("部署检测：" + url);
			in = url.openStream();
		} catch (Exception e1) {
			System.out.println("资源无法访问，请检查Web容器配置!");
			setAttr("ctx", ctx);
			render("/eova/520.html");
			return;
		} finally {
			try {
				in.close();
			} catch (Exception e) {
			}
		}

		super.toLogin();
	}

}