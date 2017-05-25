/**
 * Copyright (c) 2013-2016, Jieven. All rights reserved.
 *
 * Licensed under the GPL license: http://www.gnu.org/licenses/gpl.txt
 * To use it on other terms please contact us at 1623736450@qq.com
 */
package com.oss.test;

import java.io.File;
import java.util.HashMap;
import java.util.List;

import com.eova.common.render.Html2XlsRender;
import com.jfinal.core.Controller;
import com.jfinal.kit.PathKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

/**
 * <pre>
 * Excel模版导出-极简方案-导出Excel和写个JSP一样简单
 * 缺点:Excel中不能有图等多媒体资源,一般也用不上,此方案能满足90%的常规需求,复杂的Excel还是使用POI
 * 第一步:在WPS中编辑好Excel模版
 * 第二步:将模版另存为test.html 
 * 第三部:读取test.html,给值,然后输出xls,浏览器弹下载!
 * </pre>
 * @author Jieven
 */
public class XlsController extends Controller {

	public void index() {
		String templateName = "test.html";
		
		// 假设把模版放到\eova-oss\src\main\resources\xls\test.html
		String path = PathKit.getRootClassPath() + File.separator + "xls" + File.separator + templateName;

		// 捞数据
		List<Record> list = Db.find("select * from users");

		// 给参数
		HashMap<String, Object> paras = new HashMap<>();
		paras.put("list", list);
		paras.put("a", "最简单的模版导出");
		paras.put("b", "Eova开发者");
		paras.put("c", "666");
		paras.put("code", "eova0001");
		paras.put("total", 1024);

		render(new Html2XlsRender("eova-so-easy.xls", path, paras));
	}

}