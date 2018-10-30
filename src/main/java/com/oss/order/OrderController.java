/**
 * Copyright (c) 2013-2015, Jieven. All rights reserved.
 *
 * Licensed under the GPL license: http://www.gnu.org/licenses/gpl.txt
 * To use it on other terms please contact us at 1623736450@qq.com
 */
package com.oss.order;

import com.jfinal.core.Controller;

public class OrderController extends Controller {

	public void test() throws Exception {

		renderText("test");
	}

	public void order() throws Exception {
		renderText("order");
	}
}