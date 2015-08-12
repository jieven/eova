/**
 * Copyright (c) 2013-2015, Jieven. All rights reserved.
 *
 * Licensed under the GPL license: http://www.gnu.org/licenses/gpl.txt
 * To use it on other terms please contact us at 1623736450@qq.com
 */
package com.eova.widget.form;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.eova.common.Easy;
import com.eova.common.utils.xx;
import com.eova.core.meta.MetaObjectIntercept;
import com.eova.model.EovaLog;
import com.eova.model.MetaField;
import com.eova.model.MetaObject;
import com.eova.service.sm;
import com.eova.template.common.config.TemplateConfig;
import com.eova.template.common.util.TemplateUtil;
import com.eova.widget.WidgetManager;
import com.jfinal.core.Controller;
import com.jfinal.kit.JsonKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.IAtom;
import com.jfinal.plugin.activerecord.Record;

/**
 * Form组件
 * 
 * @author Jieven
 * 
 */
public class FormController extends Controller {

	final Controller ctrl = this;

	/** 自定义拦截器 **/
	protected MetaObjectIntercept intercept = null;

	/** 异常信息 **/
	private String errorInfo = "";

	/** 当前操作的主对象 **/
	private final Record record = new Record();

	public void add() {
		String objectCode = this.getPara(0);
		MetaObject object = MetaObject.dao.getByCode(objectCode);
		List<MetaField> fields = MetaField.dao.queryByObjectCode(objectCode);
		setAttr("object", object);
		setAttr("fields", fields);
		render("/eova/widget/form/add.html");
	}

	public void doAdd() {

		String objectCode = this.getPara(0);

		final MetaObject object = sm.meta.getMeta(objectCode);

		// 构建对象数据
		final Map<String, Record> reMap = WidgetManager.buildData(this, object.getFields(), record, object.getPk(), true);
		
		intercept = TemplateUtil.initIntercept(object.getBizIntercept());
		// 事务(默认为TRANSACTION_READ_COMMITTED)
		boolean flag = Db.tx(new IAtom() {
			@Override
			public boolean run() throws SQLException {
				try {
					// 新增前置任务
					if (intercept != null) {
						intercept.addBefore(ctrl, record);
					}

					if (!xx.isEmpty(object.getTable())) {
						// update table
						Db.use(object.getDs()).save(object.getTable(), object.getPk(), record);
					} else {
						// update view
						WidgetManager.operateView(object.getPk(), reMap, TemplateConfig.ADD);
					}

					// 新增后置任务
					if (intercept != null) {
						intercept.addAfter(ctrl, record);
					}
				} catch (Exception e) {
					errorInfo = TemplateUtil.buildException(e);
					return false;
				}
				return true;
			}
		});

		if (!flag) {
			renderJson(new Easy("新增失败" + errorInfo));
			return;
		}

		// 记录新增日志
		EovaLog.dao.info(this, EovaLog.ADD, object.getStr("code"));

		// 新增成功之后
		if (intercept != null) {
			try {
				ArrayList<Record> records = new ArrayList<Record>();
				records.add(record);
				intercept.addSucceed(this, records);
			} catch (Exception e) {
				errorInfo = TemplateUtil.buildException(e);
				renderJson(new Easy("新增成功,addSucceed拦截执行异常!" + errorInfo));
				return;
			}
		}

		renderJson(new Easy());
	}

	public void update() {

		String objectCode = this.getPara(0);
		// 获取主键的值
		Object pkValue = getPara(1);

		MetaObject object = sm.meta.getMeta(objectCode);

		// 根据主键获取对象
		Record record = Db.use(object.getDs()).findById(object.getView(), object.getPk(), pkValue);

		// 遍历给字段赋值
		for (MetaField ei : object.getFields()) {
			String key = ei.getEn();
			Object value = record.get(key);
			if (value == null) {
				value = "";
			}
			ei.put("value", value);
		}

		setAttr("object", object);
		setAttr("fields", object.getFields());

		render("/eova/widget/form/update.html");
	}

	public void doUpdate() {

		String objectCode = this.getPara(0);

		final MetaObject object = sm.meta.getMeta(objectCode);

		// 获取基础数据
		final Map<String, Record> reMap = WidgetManager.buildData(this, object.getFields(), record, object.getPk(), false);
		final Object pkValue = record.get(object.getPk());

		intercept = TemplateUtil.initIntercept(object.getBizIntercept());
		// 事务(默认为TRANSACTION_READ_COMMITTED)
		boolean flag = Db.tx(new IAtom() {
			@Override
			public boolean run() throws SQLException {
				try {
					// 修改前置任务
					if (intercept != null) {
						intercept.updateBefore(ctrl, record);
					}

					if (!xx.isEmpty(object.getTable())) {
						// update table
						System.out.println(JsonKit.toJson(record));
						Db.use(object.getDs()).update(object.getTable(), object.getPk(), record);
					} else {
						// update view
						WidgetManager.operateView(object.getPk(), reMap, TemplateConfig.UPDATE);
					}

					// 修改后置任务
					if (intercept != null) {
						intercept.updateAfter(ctrl, record);
					}
				} catch (Exception e) {
					errorInfo = TemplateUtil.buildException(e);
					return false;
				}
				return true;
			}
		});

		if (!flag) {
			renderJson(new Easy("修改失败" + errorInfo));
			return;
		}

		// 记录新增日志
		EovaLog.dao.info(this, EovaLog.UPDATE, object.getStr("code") + "[" + pkValue + "]");

		// 修改成功之后
		if (intercept != null) {
			try {
				ArrayList<Record> records = new ArrayList<Record>();
				records.add(record);
				intercept.updateSucceed(this, records);
			} catch (Exception e) {
				errorInfo = TemplateUtil.buildException(e);
				renderJson(new Easy("修改成功,updateSucceed拦截执行异常!" + errorInfo));
				return;
			}
		}

		renderJson(new Easy());
	}

	public void delete() {

		String objectCode = this.getPara(0);
		// 获取主键的值 格式:pkval1,pkval2,pkval3
		String str = getPara(1);

		final MetaObject object = MetaObject.dao.getByCode(objectCode);

		final String pkValues = str.substring(0, str.length() - 1);

		intercept = TemplateUtil.initIntercept(object.getBizIntercept());
		// 事务(默认为TRANSACTION_READ_COMMITTED)
		boolean flag = Db.tx(new IAtom() {
			@Override
			public boolean run() throws SQLException {
				try {
					// 删除动作
					if (!xx.isEmpty(pkValues)) {
						String[] pks = pkValues.split(",");
						for (String pk : pks) {
							Record record = new Record();
							record.set("id", pk);
							// 删除前置任务
							if (intercept != null) {
								intercept.deleteBefore(ctrl, record);
							}
							// 根据主键删除对象
							if (!xx.isEmpty(object.getTable())) {
								Db.use(object.getDs()).deleteById(object.getTable(), object.getPk(), pk);
							} else {
								// update view
								WidgetManager.deleteView(object.getStr("code"), pk);
							}
							// 删除后置任务
							if (intercept != null) {
								intercept.deleteAfter(ctrl, record);
							}
						}
					}
				} catch (Exception e) {
					errorInfo = TemplateUtil.buildException(e);
					return false;
				}
				return true;
			}
		});

		if (!flag) {
			renderJson(new Easy("删除失败" + errorInfo));
			return;
		}

		// 记录新增日志
		EovaLog.dao.info(this, EovaLog.DELETE, object.getStr("code") + "[" + pkValues + "]");

		// 删除成功之后
		if (intercept != null) {
			try {
				List<Record> records = new ArrayList<Record>();
				for(String pk : pkValues.split(",")){
					Record r = new Record();
					r.set("id", pk);
					records.add(r);
				}
				intercept.deleteSucceed(this, records);
			} catch (Exception e) {
				errorInfo = TemplateUtil.buildException(e);
				renderJson(new Easy("删除成功,deleteSucceed执行异常!" + errorInfo));
				return;
			}
		}

		renderJson(new Easy());
	}

}