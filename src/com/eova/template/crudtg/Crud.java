/**
 * Copyright (c) 2013-2015, Jieven. All rights reserved.
 *
 * Licensed under the GPL license: http://www.gnu.org/licenses/gpl.txt
 * To use it on other terms please contact us at 1623736450@qq.com
 */
package com.eova.template.crudtg;

import java.util.List;

import com.eova.model.Eova_Button;
import com.eova.model.Eova_Item;
import com.eova.model.Eova_Object;
import com.eova.model.Eova_User;
import com.eova.template.common.vo.UrlCmd;
import com.jfinal.core.Controller;
import com.rzyunyou.common.xx;

/**
 * CRUD模板 VO
 * 
 * @author Jieven
 * 
 */
public class Crud {

	private Eova_Object obj; // 对象模型
	private List<Eova_Item> itemList; // 字段属性
	private List<Eova_Button> btnList; // 功能按钮
	private UrlCmd cmd; // 参数对象
	private String url; // 参数URL
	private String table; // DataTable
	private String view; // DataView

	/**
	 * 获取对象数据源
	 * @return
	 */
	public String getDs() {
		return obj.getStr("dataSource");
	}

	/***
	 * 获取主键Key
	 * @return
	 */
	public String getPkName() {
		return obj.getStr("pkName");
	}

	/**
	 * 构造CRUD
	 * @param c Controller
	 */
	public Crud(Controller c) {
		init(c);
	}

	/**
	 * 构造CRUD
	 * @param c Controller
	 * @param actionMethod CRUD方法
	 */
	public Crud(Controller c, String actionMethod) {
		init(c, actionMethod);
	}

	/**
	 * 初始化CRUD(生成ActionURL)
	 * @param c
	 * @param actionMethod
	 */
	private void init(Controller c, String actionMethod) {
		init(c);
		// 获取ActionURL=/控制器名/Action方法名/默认参数
		String action = '/' + CrudConfig.contro + '/' + actionMethod + '/' + url;
		c.setAttr("action", action);
	}

	/**
	 * 初始化CRUD
	 * @param c Controller
	 */
	private void init(Controller c) {
		// 获取Cmd参数
		this.url = c.getPara(0);

		// 获取菜单URLCMD
		this.cmd = new UrlCmd(url);
		// 对象Code
		String objectCode = cmd.getObjectCode();
		// 菜单Code
		String menuCode = cmd.getMenuCode();

		// 获取登录用户的角色
		Eova_User user = c.getSessionAttr("user");
		int rid = user.getInt("rid");
		
		this.obj = Eova_Object.dao.getByCode(objectCode);
		this.itemList = Eova_Item.dao.queryByObjectCode(objectCode);
		this.btnList = Eova_Button.dao.queryByMenuCode(menuCode, rid);
		this.table = obj.getStr("table");
		this.view = obj.getStr("view");
		if (xx.isEmpty(view)) {
			view = table;
		}

		boolean isQuery = false;
		// 判断是否存在快速查询面板
		for (Eova_Item ei : itemList) {
			if (ei.getBoolean("isQuery")) {
				isQuery = true;
				break;
			}
		}
		obj.put("isQuery", isQuery);

		// 保存对象和属性
		c.setAttr("obj", obj);
		c.setAttr("itemList", itemList);
		c.setAttr("cmd", cmd);

		// 保存控制器和处理方法名称
		c.setAttr("contro", CrudConfig.contro);
		c.setAttr("urlCmd", url);
	}

	public Eova_Object getObj() {
		return obj;
	}
	public void setObj(Eova_Object obj) {
		this.obj = obj;
	}
	public List<Eova_Item> getItemList() {
		return itemList;
	}
	public void setItemList(List<Eova_Item> itemList) {
		this.itemList = itemList;
	}
	public UrlCmd getCmd() {
		return cmd;
	}
	public void setCmd(UrlCmd cmd) {
		this.cmd = cmd;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getTable() {
		return table;
	}
	public void setTable(String table) {
		this.table = table;
	}
	public String getView() {

		return view;
	}
	public void setView(String view) {
		this.view = view;
	}
	public List<Eova_Button> getBtnList() {
		return btnList;
	}
	public void setBtnList(List<Eova_Button> btnList) {
		this.btnList = btnList;
	}

}