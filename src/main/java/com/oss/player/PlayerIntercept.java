/**
 * Copyright (c) 2013-2016, Jieven. All rights reserved.
 *
 * Licensed under the GPL license: http://www.gnu.org/licenses/gpl.txt
 * To use it on other terms please contact us at 1623736450@qq.com
 */
package com.oss.player;

import com.eova.aop.AopContext;
import com.eova.template.single.SingleIntercept;
import com.jfinal.plugin.activerecord.Record;

public class PlayerIntercept extends SingleIntercept {

	@Override
	public void importBefore(AopContext ac) throws Exception {
		for (Record record : ac.records) {
			if (record.getStr("status").equals("3")) {
				throw new Exception("数据状态异常");
			}
		}
	}

}