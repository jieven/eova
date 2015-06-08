/**
 * Copyright (c) 2013-2015, Jieven. All rights reserved.
 *
 * Licensed under the GPL license: http://www.gnu.org/licenses/gpl.txt
 * To use it on other terms please contact us at 1623736450@qq.com
 */
package com.eova.template.crud;

import java.util.List;

import com.eova.model.Button;
import com.eova.model.Menu;
import com.eova.model.MenuObject;
import com.eova.model.MetaItem;
import com.eova.model.MetaObject;
import com.eova.model.User;
import com.eova.template.common.vo.UrlCmd;
import com.jfinal.core.Controller;

/**
 * CRUD模板 VO
 * 
 * @author Jieven
 * 
 */
public class Crud {

	private MetaObject object; // 对象模型
	private List<MetaItem> itemList; // 字段属性
	private List<Button> btnList; // 功能按钮
	private UrlCmd cmd; // 参数对象
	private String url; // 参数URL
	private String menuCode; // 菜单编码
	private String view; // 查询视图
	private String table; // 操作源表
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
		Menu menu = Menu.dao.findByCode(menuCode);
		this.bizIntercept = menu.getStr("bizIntercept");
		
		// 获取当前菜单的数据对象
		List<MenuObject> objectCodes = MenuObject.dao.queryByMenuCode(menuCode);
		String objectCode = objectCodes.get(0).getStr("objectCode");

		this.object = MetaObject.dao.getByCode(objectCode);
		this.itemList = MetaItem.dao.queryByObjectCode(objectCode);
		// 根据权限获取功能按钮
		User user = c.getSessionAttr("user");
		this.btnList = Button.dao.queryByMenuCode(menuCode, user.getInt("rid"));

		this.table = object.getTable();
		this.view = object.getView();
		
		// 获取Edit 控件类型
		for(MetaItem item : itemList){
			String type = item.getStr("type");
			if (type.equals("复选框")) {
				item.put("editor", "eovacheck");
			} else if (type.equals("下拉框")) {
				item.put("editor", "eovacombo");
			} else if (type.equals("时间框")) {
				item.put("editor", "eovatime");
			} else {
				item.put("editor", "eovatext");
			}
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

	public MetaObject getObject() {
		return object;
	}

	public void setObject(MetaObject obj) {
		this.object = obj;
	}

	public List<MetaItem> getItemList() {
		return itemList;
	}

	public void setItemList(List<MetaItem> itemList) {
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

	public String getView() {
		return view;
	}

	public List<Button> getBtnList() {
		return btnList;
	}

	public void setBtnList(List<Button> btnList) {
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