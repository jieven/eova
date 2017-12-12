/**
 * Copyright (c) 2013-2016, Jieven. All rights reserved.
 *
 * Licensed under the GPL license: http://www.gnu.org/licenses/gpl.txt
 * To use it on other terms please contact us at 1623736450@qq.com
 */
package com.oss.test;

import com.eova.common.base.BaseController;

/**
 * 测试Ctrl
 * @author Jieven
 *
 */
public class TestController extends BaseController {

	public void remote() {
		String name = getPara("name");
		if (name.indexOf("SB") != -1) {
			renderText("请不要说脏话" + name);
			return;
		} 
		renderText("");
	}

}