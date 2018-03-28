/**
 * Copyright (c) 2013-2015, Jieven. All rights reserved.
 *
 * Licensed under the GPL license: http://www.gnu.org/licenses/gpl.txt
 * To use it on other terms please contact us at 1623736450@qq.com
 */
package com.oss.product;

import java.util.List;

import com.eova.common.Easy;
import com.eova.common.base.BaseController;
import com.eova.common.utils.xx;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

/**
 * 产品管理
 * 
 * @author Jieven
 * 
 */
public class ProductController extends BaseController {

	// 清空库存 -> 二次确认按钮
	public void clean() throws Exception {
		Integer id = getSelectValueToInt("id");

		Db.update("update product set stock = 0 where id = ?", id);

		renderJson(Easy.sucess());
	}

	// 添加积分 -> 前端输入按钮
	public void score() throws Exception {
		Integer id = getSelectValueToInt("id");
		String val = getInputValue();

		Db.update("update product set score = score + ? where id = ?", xx.toInt(val), id);

		renderJson(Easy.sucess());
	}

	// 更新产品 -> 后台执行按钮
	public void refresh() throws Exception {

		List<Record> list = Db.find("select * from product");
		for (Record r : list) {
			r.set("name", r.getStr("name") + 1);
			Db.update("product", r);
		}

		renderJson(Easy.sucess());
	}

}