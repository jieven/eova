/**
 * Copyright (c) 2013-2015, Jieven. All rights reserved.
 *
 * Licensed under the GPL license: http://www.gnu.org/licenses/gpl.txt
 * To use it on other terms please contact us at 1623736450@qq.com
 */
package com.eova;

import com.jfinal.core.JFinal;

public class EovaMain {
	/**
	 * Run as Eova Application
	 */
	public static void main(String[] args) {
		JFinal.start("src/main/webapp", 80, "/", 10);
	}
}