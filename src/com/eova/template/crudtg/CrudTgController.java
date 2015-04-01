/**
 * Copyright (c) 2013-2015, Jieven. All rights reserved.
 *
 * Licensed under the GPL license: http://www.gnu.org/licenses/gpl.txt
 * To use it on other terms please contact us at 1623736450@qq.com
 */
package com.eova.template.crudtg;

import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.eova.common.Easy;
import com.eova.model.Eova_Item;
import com.eova.model.Eova_Log;
import com.eova.model.Eova_Object;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.IAtom;
import com.jfinal.plugin.activerecord.Record;
import com.rzyunyou.common.xx;
import com.rzyunyou.common.util.ExceptionUtil;

/**
 * CRUD模板 功能:通用单表增删改查
 * 
 * @author Jieven
 * 
 */
public class CrudTgController extends Controller {

	/**自定义拦截器**/
	protected CrudTgIntercept intercept = null;

	/**异常信息**/
	private String error = "";
	private String info = "";

	/**当前操作的主对象**/
	private Record record = new Record();

	/**
	 * 初始化拦截器
	 * @param eo
	 */
	private void initIntercept(Eova_Object eo) {
		String diyIntercept = eo.getStr("diyIntercept");
		if (!xx.isEmpty(diyIntercept)) {
			try {

				// 实例化自定义拦截器
				intercept = (CrudTgIntercept) Class.forName(diyIntercept).newInstance();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 构建异常信息
	 * @param e 异常
	 */
	private void buildException(Exception e) {
		String type = e.getClass().getName();
		type = type.replace("java.lang.", "");
		info = ExceptionUtil.getStackTrace(e);
		error = type + ":" + e.getMessage();
	}

	/**
	 * 新增
	 */
	public void toAdd() {
		// Get Eova_Object Code
		String objectCode = getPara(0);

		// Get Eova_Object and Eova_Item List
		Eova_Object eo = Eova_Object.dao.getByCode(objectCode);
		List<Eova_Item> eis = Eova_Item.dao.queryByObjectCode(objectCode);
//		for (Eova_Item e : eis) {
//			System.out.println(e.getStr("en") + "  " + e.getStr("type"));
//		}
		setAttr("obj", eo);
		setAttr("itemList", eis);

		render("/eova/template/crud/add.html");
	}

	/**
	 * 修改
	 */
	public void toUpdate() {

		// Get Eova_Object Code
		String objectCode = getPara(0);

		// Get Eova_Object and Eova_Item List
		Eova_Object eo = Eova_Object.dao.getByCode(objectCode);
		List<Eova_Item> eis = Eova_Item.dao.queryByObjectCode(objectCode);

		// 获取主键的值
		int pkValue = getParaToInt(1);
		// 根据主键获取对象
		Record record = Db.use(eo.getStr("dataSource")).findById(eo.getView(), eo.getStr("pkName"), pkValue);

		// 分别根据字段获取值
		for (Eova_Item ei : eis) {
			String key = ei.getStr("en");
			Object value = record.get(key);
			if (value == null) {
				value = "";
			}
			// ei.setValue(value.toString());
			ei.put("value", value);
		}
		setAttr("obj", eo);
		setAttr("itemList", eis);

		render("/eova/template/crud/update.html");
	}

	/**
	 * 列表
	 */
	public void toList() {

		Crud crud = new Crud(this, CrudConfig.LIST);

		List<Eova_Item> itemList = crud.getItemList();
		boolean isQuery = false;
		for(Eova_Item item : itemList){
			if (item.getBoolean("isQuery")) {
				isQuery = true;
				break;
			}
		}
		// 当前Object字段
		setAttr("itemList", itemList);
		// 当前功能按钮
		setAttr("btnList", crud.getBtnList());
		// 是否需要显示快速查询
		setAttr("isQuery", isQuery);
		// 显示默认排序
		// if (crud.getObj().getBoolean("isDefaultPkDesc")) {
		// setAttr(PageConst.SORT, crud.getPkName());
		// setAttr(PageConst.ORDER, "desc");
		// }

		render("/eova/template/crudtg/list.html");
	}

	/**
	 * 新增
	 */
	@SuppressWarnings("rawtypes")
	public void add() {

		// Get Eova_Object Code
		String objectCode = getPara(0);

		// Get Eova_Object and Eova_Item List
		final Eova_Object eo = Eova_Object.dao.getByCode(objectCode);
		List<Eova_Item> eis = Eova_Item.dao.queryByObjectCode(objectCode);

		// 构建对象数据
		final Map<String, Record> reMap = CrudManager.buildData(this, eis, record);

		// 初始化拦截器
		initIntercept(eo);

		// 事务(默认为TRANSACTION_READ_COMMITTED)
		boolean flag = Db.tx(new IAtom() {
			public boolean run() throws SQLException {
				try {
					// 删除前置任务
					if (intercept != null) {
						intercept.addBefore(eo, record);
					}

					// 新增主对象数据
					Db.use(eo.getStr("dataSource")).save(eo.getStr("table"), eo.getStr("pkName"), record);
					// 新增其它对象的数据
					Iterator iter = reMap.entrySet().iterator();
					while (iter.hasNext()) {
						Map.Entry entry = (Map.Entry) iter.next();
						String objectCode = entry.getKey().toString();
						// 分别持久化非主对象
						CrudManager.updateRecordByCode(objectCode, reMap, record.get(eo.getStr("pkName")), false);
					}

					// 删除后置任务
					if (intercept != null) {
						intercept.addAfter(eo, record);
					}
				} catch (Exception e) {
					buildException(e);
					return false;
				}
				return true;
			}
		});

		if (!flag) {
			renderJson(new Easy("新增失败<p title=\"" + info + "\">" + error + "</p>"));
			return;
		}

		// 记录新增日志
		Eova_Log.dao.info(this, Eova_Log.ADD, objectCode);
		
		// 新增成功之后
		if (intercept != null) {
			try {
				intercept.addSucceed(eo, record);
			} catch (Exception e) {
				buildException(e);
				renderJson(new Easy("新增成功,后置任务执行异常!<p title=\"" + info + "\">" + error + "</p>"));
				return;
			}
		}

		renderJson(new Easy());
	}

	/**
	 * 删除
	 */
	public void delete() {

		// Get Eova_Object Code
		String objectCode = getPara(0);

		// Get Eova_Object and Eova_Item List
		final Eova_Object eo = Eova_Object.dao.getByCode(objectCode);

		// 获取主键的值 格式:pkvala,pkvalb,pkvalc
		String str = getPara(1);
		final String pkValues = str.substring(0, str.length() - 1);

		// 初始化拦截器
		initIntercept(eo);

		// 事务(默认为TRANSACTION_READ_COMMITTED)
		boolean flag = Db.tx(new IAtom() {
			public boolean run() throws SQLException {
				try {
					// 删除前置任务
					if (intercept != null) {
						intercept.deleteBefore(pkValues);
					}

					// 删除动作
					if (!xx.isEmpty(pkValues)) {
						String[] pks = pkValues.split(",");
						for (int i = 0; i < pks.length; i++) {
							// 根据主键删除对象
							Db.use(eo.getStr("dataSource")).deleteById(eo.getStr("table"), eo.getStr("pkName"), pks[i]);
						}
					}

					// 删除后置任务
					if (intercept != null) {
						intercept.deleteAfter(pkValues);
					}
				} catch (Exception e) {
					buildException(e);
					return false;
				}
				return true;
			}
		});

		if (!flag) {
			renderJson(new Easy("删除失败<p title=\"" + info + "\">" + error + "</p>"));
			return;
		}

		// 记录新增日志
		Eova_Log.dao.info(this, Eova_Log.DELETE, objectCode + "[" + pkValues + "]");
		
		// 删除成功之后
		if (intercept != null) {
			try {
				intercept.deleteSucceed(pkValues);
			} catch (Exception e) {
				buildException(e);
				renderJson(new Easy("删除成功,后置任务执行异常!<p title=\"" + info + "\">" + error + "</p>"));
				return;
			}
		}

		renderJson(new Easy());
	}

	/**
	 * 修改
	 */
	@SuppressWarnings("rawtypes")
	public void update() {

		// Get Eova_Object Code
		String objectCode = getPara(0);

		// Get Eova_Object and Eova_Item List
		final Eova_Object eo = Eova_Object.dao.getByCode(objectCode);
		List<Eova_Item> eis = Eova_Item.dao.queryByObjectCode(objectCode);

		// 构建对象数据
		final Map<String, Record> reMap = CrudManager.buildData(this, eis, record);
		// 获取主对象主键值
		final Object pkValue = record.get(eo.getStr("pkName"));
		// record.put("pkValue", record.get(eo.getStr("pkName")));

		// 初始化拦截器
		initIntercept(eo);

		// 事务(默认为TRANSACTION_READ_COMMITTED)
		boolean flag = Db.tx(new IAtom() {
			public boolean run() throws SQLException {
				try {
					// 修改前置任务
					if (intercept != null) {
						intercept.updateBefore(eo, record);
					}

					// 更新主对象数据
					Db.use(eo.getStr("dataSource")).update(eo.getStr("table"), eo.getStr("pkName"), record);
					// 更新其它对象数据
					Iterator iter = reMap.entrySet().iterator();
					while (iter.hasNext()) {
						Map.Entry entry = (Map.Entry) iter.next();
						String objectCode = entry.getKey().toString();
						// 分别持久化非主对象
						CrudManager.updateRecordByCode(objectCode, reMap, pkValue, true);
					}

					// 修改后置任务
					if (intercept != null) {
						intercept.updateAfter(eo, record);
					}
				} catch (Exception e) {
					buildException(e);
					return false;
				}
				return true;
			}
		});

		if (!flag) {
			renderJson(new Easy("修改失败<p title=\"" + info + "\">" + error + "</p>"));
			return;
		}
		
		// 记录新增日志
		Eova_Log.dao.info(this, Eova_Log.UPDATE, objectCode + "[" + pkValue + "]");

		// 修改成功之后
		if (intercept != null) {
			try {
				intercept.updateSucceed(eo, record);
			} catch (Exception e) {
				buildException(e);
				renderJson(new Easy("修改成功,后置任务执行异常!<p title=\"" + info + "\">" + error + "</p>"));
				return;
			}
		}

		renderJson(new Easy());
	}

}