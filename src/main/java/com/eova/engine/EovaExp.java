/**
 * Copyright (c) 2013-2015, Jieven. All rights reserved.
 *
 * Licensed under the GPL license: http://www.gnu.org/licenses/gpl.txt
 * To use it on other terms please contact us at 1623736450@qq.com
 */
package com.eova.engine;

import java.util.ArrayList;
import java.util.List;

import com.eova.model.MetaItem;
import com.eova.model.MetaObject;
import com.eova.common.utils.xx;

public class EovaExp {
	public static void main(String[] args) {
//		String exp = "select id ID,nickName 昵称, loginId 帐号 from eova_user where id = ?;ds=eova";
		String exp = "select id ID,name 昵称 from game where 1=1;ds=main";
//		String exp = "select value ID,name 状态 from `dict` where `table` = 'game' and field = 'status';ds=main";

		System.out.println("select="+getSelectNoAlias(exp));
		System.out.println("from="+getFrom(exp));
		System.out.println("where="+getWhere(exp));
		System.out.println("ds="+getDs(exp));
		System.out.println("pk="+getPk(exp));
		System.out.println("cn="+getCn(exp));
		
		System.out.println("sa="+getSelectItem(exp));
		
	}

	public static String getWhere(String exp){
		int where = exp.indexOf("where");
		if (where == -1) {
			return "";
		}
		int end = exp.indexOf(";");
		return exp.substring(where, end);
	}
	
	/**
	 * 获取数据源
	 * @return
	 */
	public static String getDs(String exp){
		int a = exp.indexOf(";ds=");
		if (a == -1) {
			return xx.DS_MAIN;
		}
		a += 4;
		return exp.substring(a, exp.length());
	}
	
	/**
	 * 获取主键
	 * @return
	 */
	public static String getPk(String exp){
		String[] items = getItemNoAlias(exp).split(",");
		return items[0];
	}

	/**
	 * 获取中文名, 第二列为CN
	 * @return
	 */
	public static String getCn(String exp){
		String[] items = getItemNoAlias(exp).split(",");
		return items[1];
	}
	
	/**
	 * 获取完整的select 部分
	 * eg:select id,name,value
	 * @param exp
	 * @return
	 */
	public static String getSelect(String exp){
		return "select " + getSelectItem(exp) +" ";
	}
	
	/**
	 * 获取没有别名的select 部分
	 * eg:select id,name,value
	 * @param exp
	 * @return
	 */
	public static String getSelectNoAlias(String exp){
		return "select " + getItemNoAlias(exp) +" ";
	}
	
	/**
	 * 获取过滤别名后的select项
	 * eg: id,name
	 * @param exp
	 * @return
	 */
	private static String getItemNoAlias(String exp){
		StringBuilder sb = new StringBuilder("");
		// 截取select的Item
		exp = getSelectItem(exp);
		// 过滤掉别名
		for(String str : exp.split(",")){
//			System.out.println(str.trim());
			String[] strs = str.trim().split(" ");
			String en = strs[0];
			//System.out.println(en);
			sb.append(en).append(",");
		}
		sb.delete(sb.length() - 1, sb.length());
		return sb.toString();
	}
	
	/**
	 * 截取select的Item
	 * eg: id xx,name xx
	 * @param exp
	 * @return
	 */
	private static String getSelectItem(String exp){
		int select = exp.indexOf("select") + 7;
		int from = exp.indexOf("from");
		exp = exp.substring(select, from);
		return exp;
	}
	
	public static String getFrom(String exp){
		int from = exp.indexOf("from");
		int where = exp.indexOf("where");
		return exp.substring(from, where); 
	}
	
	/**
	 * 构建元对象
	 * @param exp 表达式
	 * @return
	 */
	public static MetaObject getEo(String exp) {
		MetaObject eo = new MetaObject();
		eo.put("dataSource", getDs(exp));
//		eo.put("code", code);
		eo.put("name", "");
		eo.put("table", "");
		eo.put("isDefaultPkDesc", false);
		// 获取第一的值作为主键
		eo.put("pkName", getPk(exp));
		// 获取第二列的值作为CN
		eo.put("cn", getCn(exp));
		return eo;
	}
	
	/**
	 * 构建元字段属性
	 * @param exp 表达式
	 * @return
	 */
	public static List<MetaItem> getEis(String exp) {

		// select id ID,nickName 昵称,loginId 帐号 from eova_user where id = ?
		int a = 7;
		int b = exp.indexOf("from");

		// id ID,nickName 昵称,loginId 帐号
		String items = exp.substring(a, b);

		// MetaItem ei = new MetaItem();

		List<MetaItem> eis = new ArrayList<MetaItem>();
		int index = 0;
		for (String item : items.split(",")) {
			index++;
			String[] strs = item.split(" ");
			String en = strs[0];
			String cn = strs[1];

			// ei.put(key, value);
			boolean isQuery = true;
			if (index == 1) {
				isQuery = false;
			}
			eis.add(buildItem(index, en, cn, isQuery));

//			System.out.print("en=" + en);
//			System.out.print(",");
//			System.out.println("cn=" + cn);
		}
		return eis;
	}

	/**
	 * 手工组装字段元数据
	 * @param index 排序
	 * @param en 英文名
	 * @param cn 中文名
	 * @param isQuery 是否可查询
	 * @return
	 */
	public static MetaItem buildItem(int index, String en, String cn, boolean isQuery) {
		MetaItem ei = new MetaItem();
		ei.put("indexNum", index);
		//ei.put("objectCode", code);
		ei.put("en", en);
		ei.put("cn", cn);
		ei.put("dataType", "string");
		ei.put("type", "文本框");
		ei.put("isQuery", isQuery);
		return ei;
	}
}