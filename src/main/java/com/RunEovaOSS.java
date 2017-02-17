package com;

import com.jfinal.core.JFinal;

/**
 * 鼠标右键->Run As Java Application
 *
 */
public class RunEovaOSS {
	public static void main(String[] args) {
		JFinal.start("src/main/webapp", 80, "/", 0);
	}
}
