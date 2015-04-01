/**
 * Copyright (c) 2013-2015, Jieven. All rights reserved.
 *
 * Licensed under the GPL license: http://www.gnu.org/licenses/gpl.txt
 * To use it on other terms please contact us at 1623736450@qq.com
 */
package com.eova.template.crud;

import java.util.List;

import com.eova.model.Eova_Button;
import com.eova.model.Eova_Item;
import com.eova.model.Eova_Menu;
import com.eova.model.Eova_Menu_Object;
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

	private Eova_Object object; // 对象模型
	private List<Eova_Item> itemList; // 字段属性
	private List<Eova_Button> btnList; // 功能按钮
	private UrlCmd cmd; // 参数对象
	private String url; // 参数URL
	private String menuCode; // 菜单编码
	private String view; // 数据源表/视图
	private String bizIntercept; // 业务拦截器

	/**
	 * 获取对象数据源
	 * 
	 * @return
	 */
	public String getDs() {
		return object.getStr("dataSource");
	}

	/***
	 * 获取主键Key
	 * 
	 * @return
	 */
	public String getPkName() {
		return object.getStr("pkName");
	}

	/**
	 * 构造CRUD
	 * 
	 * @param c Controller
	 */
	public Crud(Controller c) {
		init(c);
	}

	/**
	 * 构造CRUD
	 * 
	 * @param c Controller
	 * @param actionMethod CRUD方法
	 */
	public Crud(Controller c, String actionMethod) {
		init(c, actionMethod);
	}

	/**
	 * 初始化CRUD(生成ActionURL)
	 * 
	 * @param c
	 * @param actionMethod
	 */
	private void init(Controller c, String actionMethod) {
		init(c);
		// 获取ActionURL=/控制器名/Action方法名/默认参数
		String action = '/' + CrudConfig.contro + '/' + actionMethod + '/' + menuCode;
		c.setAttr("action", action);
	}

	/**
	 * 初始化CRUD
	 * 
	 * @param c Controller
	 */
	private void init(Controller c) {

		this.menuCode = c.getPara(0);

		// 获取菜单
		Eova_Menu menu = Eova_Menu.dao.findByCode(menuCode);
		this.bizIntercept = menu.getStr("bizIntercept");
		
		// 获取当前菜单的数据对象
		List<Eova_Menu_Object> objectCodes = Eova_Menu_Object.dao.queryByMenuCode(menuCode);
		String objectCode = objectCodes.get(0).getStr("objectCode");

		this.object = Eova_Object.dao.getByCode(objectCode);
		this.itemList = Eova_Item.dao.queryByObjectCode(objectCode);
		// 根据权限获取功能按钮
		Eova_User user = c.getSessionAttr("user");
		this.btnList = Eova_Button.dao.queryByMenuCode(menuCode, user.getInt("rid"));

		this.view = object.getStr("view");
		if (xx.isEmpty(view)) {
			view = object.getStr("table");
		}

		// 保存对象和属性
		c.setAttr("obj", object);
		c.setAttr("itemList", itemList);
		c.setAttr("cmd", cmd);
		c.setAttr("menu", menu);

		// 保存控制器和处理方法名称
		c.setAttr("contro", CrudConfig.contro);

		// c.setAttr("urlCmd", url);
		// 获取Cmd参数
		// this.url = c.getPara(0);
		// 获取菜单URLCMD
		// this.cmd = new UrlCmd(url);
	}

	public Eova_Object getObject() {
		return object;
	}

	public void setObject(Eova_Object obj) {
		this.object = obj;
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

	public String getMenuCode() {
		return menuCode;
	}

	public void setMenuCode(String menuCode) {
		this.menuCode = menuCode;
	}

	public String getBizIntercept() {
		return bizIntercept;
	}

	public void setBizIntercept(String bizIntercept) {
		this.bizIntercept = bizIntercept;
	}

}