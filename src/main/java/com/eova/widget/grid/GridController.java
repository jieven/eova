/**
 * Copyright (c) 2013-2015, Jieven. All rights reserved.
 *
 * Licensed under the GPL license: http://www.gnu.org/licenses/gpl.txt
 * To use it on other terms please contact us at 1623736450@qq.com
 */
package com.eova.widget.grid;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.eova.common.Easy;
import com.eova.common.render.XlsRender;
import com.eova.common.utils.xx;
import com.eova.config.PageConst;
import com.eova.core.meta.MetaObjectIntercept;
import com.eova.model.EovaLog;
import com.eova.model.MetaField;
import com.eova.model.MetaObject;
import com.eova.service.sm;
import com.eova.template.common.util.TemplateUtil;
import com.eova.widget.WidgetManager;
import com.eova.widget.WidgetUtil;
import com.jfinal.core.Controller;
import com.jfinal.kit.JsonKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.IAtom;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

/**
 * Grid组件
 * 
 * @author Jieven
 * 
 */
public class GridController extends Controller {

	final Controller ctrl = this;

	/** 元对象业务拦截器 **/
	protected MetaObjectIntercept intercept = null;
	
	/** 异常信息 **/
	private String errorInfo = "";

	/**
	 * 分页查询
	 */
	public void query() {

		String code = getPara(0);

		MetaObject object = MetaObject.dao.getByCode(code);
		List<MetaField> fields = MetaField.dao.queryByObjectCode(code);

		// 获取分页参数
		int pageNumber = getParaToInt(PageConst.PAGENUM, 1);
		int pageSize = getParaToInt(PageConst.PAGESIZE, 95);

		// 获取条件
		String where = "";
		List<String> parmList = new ArrayList<String>();
		where = WidgetManager.getWhere(this, fields, parmList, object.getStr("filter"));

		// 转换SQL参数为Obj[]
		Object[] parm = new Object[parmList.size()];
		parmList.toArray(parm);
		// 获取排序
		String sort = WidgetManager.getSort(this, object);

		// 分页查询Grid数据
		String view = object.getView();
		String sql = "from " + view + where + sort;
		// SQL优化
		if (sql.endsWith(" where 1=1 ")) {
			sql = sql.replace(" where 1=1 ", "");
		}
		Page<Record> page = Db.use(object.getDs()).paginate(pageNumber, pageSize, "select *", sql, parm);

		intercept = TemplateUtil.initIntercept(object.getBizIntercept());
		// 查询后置任务
		if (intercept != null) {
			intercept.queryAfter(ctrl, page);
		}

		// 备份Value列，然后将值列转换成Key列
		WidgetUtil.copyValueColumn(page.getList(), object.getPk(), fields);
		// 根据表达式将数据中的值翻译成汉字
		WidgetManager.convertValueByExp(fields, page.getList());

		// 将分页数据转换成JSON
		String json = JsonKit.toJson(page.getList());
		json = "{\"total\":" + page.getTotalRow() + ",\"rows\":" + json + "}";
		// System.out.println(json);

		renderJson(json);
	}

	/**
	 * 新增
	 */
	public void add() {
		String objectCode = getPara(0);
		final MetaObject object = sm.meta.getMeta(objectCode);
		
		String json = getPara("rows");
		final List<Record> records = getRecordsByJson(json, object.getFields(), object.getPk());
		
		intercept = TemplateUtil.initIntercept(object.getBizIntercept());
		// 事务(默认为TRANSACTION_READ_COMMITTED)
		boolean flag = Db.tx(new IAtom() {
			@Override
			public boolean run() throws SQLException {
				try {
					for (Record record : records) {
						// 新增前置任务
						if (intercept != null) {
							intercept.addBefore(ctrl, record);
						}
						Db.use(object.getDs()).save(object.getTable(), object.getPk(), record);
						EovaLog.dao.info(ctrl, EovaLog.ADD, object.getStr("code"));
						// 新增后置任务
						if (intercept != null) {
							intercept.addAfter(ctrl, record);
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
			renderJson(new Easy("新增失败" + errorInfo));
			return;
		}

		// 新增成功之后
		if (intercept != null) {
			try {
				intercept.addSucceed(this, records);
			} catch (Exception e) {
				errorInfo = TemplateUtil.buildException(e);
				renderJson(new Easy("新增成功,addSucceed拦截执行异常!" + errorInfo));
				return;
			}
		}

		renderJson(new Easy());
	}

	/**
	 * 删除
	 */
	public void delete() {
		String objectCode = getPara(0);
		final MetaObject object = sm.meta.getMeta(objectCode);

		String json = getPara("rows");

		final List<Record> records = getRecordsByJson(json, object.getFields(), object.getPk());
		
		intercept = TemplateUtil.initIntercept(object.getBizIntercept());
		// 事务(默认为TRANSACTION_READ_COMMITTED)
		boolean flag = Db.tx(new IAtom() {
			@Override
			public boolean run() throws SQLException {
				try {
					for (Record record : records) {
						// 删除前置任务
						if (intercept != null) {
							intercept.deleteBefore(ctrl, record);
						}
						Db.use(object.getDs()).delete(object.getTable(), object.getPk(), record);
						EovaLog.dao.info(ctrl, EovaLog.DELETE, object.getStr("code") + "[" + record.get(object.getPk()) + "]");
						// 删除后置任务
						if (intercept != null) {
							intercept.deleteAfter(ctrl, record);
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

		// 删除成功之后
		if (intercept != null) {
			try {
				intercept.deleteSucceed(this, records);
			} catch (Exception e) {
				errorInfo = TemplateUtil.buildException(e);
				renderJson(new Easy("删除成功,deleteSucceed执行异常!" + errorInfo));
				return;
			}
		}

		renderJson(new Easy());
	}

	/**
	 * 更新
	 * 
	 * @throws IOException
	 */
	public void update() throws IOException {

		String objectCode = getPara(0);
		final MetaObject object = sm.meta.getMeta(objectCode);

		String json = getPara("rows");

		final List<Record> records = getRecordsByJson(json, object.getFields(), object.getPk());
		
		intercept = TemplateUtil.initIntercept(object.getBizIntercept());
		// 事务(默认为TRANSACTION_READ_COMMITTED)
		boolean flag = Db.tx(new IAtom() {
			@Override
			public boolean run() throws SQLException {
				try {
					for (Record record : records) {
						// 修改前置任务
						if (intercept != null) {
							intercept.updateBefore(ctrl, record);
						}
						Db.use(object.getDs()).update(object.getTable(), object.getPk(), record);
						EovaLog.dao.info(ctrl, EovaLog.UPDATE, object.getStr("code") + "[" + record.get(object.getPk()) + "]");
						// 修改后置任务
						if (intercept != null) {
							intercept.updateAfter(ctrl, record);
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
			renderJson(new Easy("修改失败" + errorInfo));
			return;
		}

		// 修改成功之后
		if (intercept != null) {
			try {
				intercept.updateSucceed(this, records);
			} catch (Exception e) {
				errorInfo = TemplateUtil.buildException(e);
				renderJson(new Easy("修改成功,updateSucceed拦截执行异常!" + errorInfo));
				return;
			}
		}

		renderJson(new Easy());
	}

	/**
	 * 导出
	 */
	public void export() {
		String objectCode = getPara(0);
		MetaObject object = MetaObject.dao.getByCode(objectCode);
		List<MetaField> items = MetaField.dao.queryByObjectCode(objectCode);
		List<Record> data = Db.use(object.getDs()).find("select * from " + object.getTable());
		render(new XlsRender(data, items, object));
	}

	/**
	 * json转List
	 * 
	 * @param json
	 * @param pkName
	 * @return
	 */
	private static List<Record> getRecordsByJson(String json, List<MetaField> items, String pkName) {
		List<Record> records = new ArrayList<Record>();

		List<JSONObject> list = JSON.parseArray(json, JSONObject.class);
		for (JSONObject o : list) {
			Map<String, Object> map = JSON.parseObject(o + "", new TypeReference<Map<String, Object>>() {
			});
			Record re = new Record();
			re.setColumns(map);
			// 将Text翻译成Value,然后删除val字段
			for (MetaField x : items) {
				String en = x.getEn();// 字段名
				String exp = x.getStr("exp");// 表达式
				Object value = re.get(en);// 值

				if (!xx.isEmpty(exp)) {
					String valField = en + "_val";
					// 获取值列中的值
					value = re.get(valField).toString();
					// 获得值之后删除值列防止持久化报错
					re.remove(valField);
				}

				re.set(en, TemplateUtil.convertValue(x, value));
			}
			// 删除主键备份值列
			re.remove("pk_val");
			// 删除Orcle分页产生的rownum_
			if (xx.isOracle()) {
				re.remove("rownum_");
			}
			records.add(re);
		}

		return records;
	}
	

	public static void main(String[] args) {

		String sl = "[{'id':1,'loginId':'111'},{'id':2,'loginId':'222'}]";
		List<JSONObject> list = JSON.parseArray(sl, JSONObject.class);
		for (JSONObject o : list) {
			Map<String, Object> map = JSON.parseObject(o + "", new TypeReference<Map<String, Object>>() {
			});
			Record re = new Record();
			re.setColumns(map);
			System.out.println(re.toJson());
		}
	}

}