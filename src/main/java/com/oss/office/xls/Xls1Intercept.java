package com.oss.office.xls;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.eova.common.utils.util.RandomUtil;
import com.eova.template.office.OfficeIntercept;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Record;

/**
 * Office Excel Demo
 * @author Jieven
 *
 */
public class Xls1Intercept extends OfficeIntercept {

	@Override
	public void init(Controller ctrl, HashMap<String, Object> data) throws Exception {

		String[] ss1 = { "北京", "上海", "深圳" };
		String[] ss2 = { "软件园", "高新科技园", "孵化器" };

		List<Record> list = new ArrayList<>();
		for (String s1 : ss1) {
			for (String s2 : ss2) {
				Record r = new Record();
				r.set("name", s1 + s2);
				int a = RandomUtil.nextInt(100, 200);
				int b = RandomUtil.nextInt(100, 200);
				int c = RandomUtil.nextInt(100, 200);
				r.set("a", a);
				r.set("b", b);
				r.set("c", c);
				r.set("total", a + b + c);
				list.add(r);
			}
		}

		data.put("list", list);

	}
}
