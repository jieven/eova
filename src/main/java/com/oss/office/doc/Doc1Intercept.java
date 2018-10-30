package com.oss.office.doc;

import java.util.HashMap;

import com.eova.template.office.OfficeIntercept;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Record;

/**
 * 组长评分情况
 * 
 * @author Jieven
 *
 */
public class Doc1Intercept extends OfficeIntercept {

	@Override
	public void init(Controller ctrl, HashMap<String, Object> data) throws Exception {

		// ctrl.getPara(1);// 只支持索引取参数

		Record r = new Record();
		r.set("name", "陆家嘴软件园");
		r.set("zm", "100000");
		r.set("jm", "90000");
		r.set("c1", Y);
		r.set("c2", Y);
		r.set("c3", Y);
		r.set("c4", Y);
		r.set("c5", N);
		r.set("c6", N);
		r.set("c7", Y);
		r.set("info",
				"陆家嘴软件园位于上海市峨山路91弄98号。上海陆家嘴软件园（以下简称“LSP”）位于浦东新区的中心位置，紧邻世纪大道、浦东新区行政文化中心和竹园商贸区。LSP成立于2001年9月29日，原为上海浦东软件园的分园之一，于2007年5月升级为“上海市软件产业基地”。LSP由上海陆家嘴金融贸易区开发股份有限公司投资开发，委托上海浦东陆家嘴软件产业发展有限公司（以下称“LSPC”）经营和管理。 LSP规划占地面积43公顷，建筑面积约55万平方米，截至2007年10月，已建成并投入使用的研发办公楼建筑面积已达到12万平方米。根据园区的规划，以后每年将有约3-5万平方米的研发楼投入使用。目前园区内已引进了欧特克中国研发中心、富士施乐研发中心、华为上海研究所、法国船级社中国总部、穴吹等知名软件开发、技术研发机构入住。 陆家嘴软件园为园区标志性建筑，位于园区东南侧。层高4.5米，承重500KG；大空间，适合IT研发、测试以及办公。");

		data.put("x", r);
	}
}
