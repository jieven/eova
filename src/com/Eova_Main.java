/**
 * Copyright (c) 2013-2015, Jieven. All rights reserved.
 *
 * Licensed under the GPL license: http://www.gnu.org/licenses/gpl.txt
 * To use it on other terms please contact us at 1623736450@qq.com
 */
package com;

import com.jfinal.core.JFinal;

public class Eova_Main {
	/**
	 * Run as Eova Application
	 */
	public static void main(String[] args) {
		JFinal.start("WebRoot", 80, "/", 5);
		System.out.println("");
	}
}