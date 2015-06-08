/**
 * Copyright (c) 2013-2015, Jieven. All rights reserved.
 *
 * Licensed under the GPL license: http://www.gnu.org/licenses/gpl.txt
 * To use it on other terms please contact us at 1623736450@qq.com
 */
package com.eova.template.crud;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.eova.common.Easy;
import com.eova.common.utils.xx;
import com.eova.common.utils.file.FileUtil;
import com.eova.common.utils.file.ImageUtil;
import com.eova.common.utils.util.ExceptionUtil;
import com.eova.config.EovaConst;
import com.eova.model.EovaLog;
import com.eova.model.MetaItem;
import com.eova.model.MetaObject;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.IAtom;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.upload.UploadFile;

/**
 * CRUD模板 功能:通用单表增删改查
 * 
 * @author Jieven
 * 
 */
public class CrudController extends Controller {

	final Controller ctrl = this;

	/** 自定义拦截器 **/
	protected CrudIntercept intercept = null;

	/** 异常信息 **/
	private String error = "";
	private String info = "";

	/** 当前操作的主对象 **/
	private final Record record = new Record();

	/**
	 * 初始化拦截器
	 * 
	 * @param eo
	 */
	private void initIntercept(String bizIntercept) {
		if (!xx.isEmpty(bizIntercept)) {
			try {
				// 实例化自定义拦截器
				intercept = (CrudIntercept) Class.forName(bizIntercept).newInstance();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 构建异常信息
	 * 
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
		Crud crud = new Crud(this, CrudConfig.LIST);
		setAttr("itemList", crud.getItemList());
		render("/eova/template/crud/add.html");
	}

	/**
	 * 修改
	 */
	public void toUpdate() {

		Crud crud = new Crud(this, CrudConfig.LIST);

		// 初始化业务拦截器
		initIntercept(crud.getBizIntercept());

		// 获取基础数据
		List<MetaItem> eis = crud.getItemList();

		// 获取主键的值
		Object pkValue = getPara(1);
		// 根据主键获取对象
		Record record = Db.use(crud.getDs()).findById(crud.getView(), crud.getPkName(), pkValue, "*");

		// 分别根据字段获取值
		for (MetaItem ei : eis) {
			String key = ei.getStr("en");
			Object value = record.get(key);
			if (value == null) {
				value = "";
			}
			ei.put("value", value);
		}
		setAttr("itemList", eis);

		render("/eova/template/crud/update.html");
	}

	/**
	 * 导入
	 */
	public void toImportXls() {
		new Crud(this, CrudConfig.IMPORTXLS);
		render("/eova/template/crud/dialog/import.html");
	}

	/**
	 * 列表
	 */
	public void list() {

		Crud crud = new Crud(this, CrudConfig.LIST);

		List<MetaItem> eis = crud.getItemList();
		boolean isQuery = false;
		for (MetaItem item : eis) {
			if (item.getBoolean("isQuery")) {
				isQuery = true;
				break;
			}
		}

		// 是否需要显示快速查询
		setAttr("isQuery", isQuery);
		// 当前Object字段
		setAttr("itemList", eis);
		// 当前功能按钮
		setAttr("btnList", crud.getBtnList());

		render("/eova/template/crud/list.html");
	}

	/**
	 * 新增
	 */
	public void add() {

		final Crud crud = new Crud(this, CrudConfig.ADD);

		// 初始化业务拦截器
		initIntercept(crud.getBizIntercept());

		// 获取基础数据
		final MetaObject eo = crud.getObject();
		List<MetaItem> eis = crud.getItemList();

		// 构建对象数据
		final Map<String, Record> reMap = CrudManager.buildData(this, eis, record, crud.getPkName(), true);

		// 事务(默认为TRANSACTION_READ_COMMITTED)
		boolean flag = Db.tx(new IAtom() {
			@Override
			public boolean run() throws SQLException {
				try {
					// 新增前置任务
					if (intercept != null) {
						intercept.addBefore(ctrl, record);
					}

					if (!xx.isEmpty(crud.getTable())) {
						// update table
						Db.use(crud.getDs()).save(crud.getTable(), crud.getPkName(), record);
					} else {
						// update view
						CrudManager.operateView(crud.getPkName(), reMap, CrudConfig.ADD);
					}

					// 新增后置任务
					if (intercept != null) {
						intercept.addAfter(ctrl, record);
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
		EovaLog.dao.info(this, EovaLog.ADD, eo.getStr("code"));

		// 新增成功之后
		if (intercept != null) {
			try {
				intercept.addSucceed(this, record);
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

		final Crud crud = new Crud(this, CrudConfig.DELETE);

		// 初始化业务拦截器
		initIntercept(crud.getBizIntercept());

		// 获取基础数据
		final MetaObject eo = crud.getObject();
		// 获取主键的值 格式:pkval1,pkval2,pkval3
		String str = getPara(1);
		final String pkValues = str.substring(0, str.length() - 1);
		
		// 事务(默认为TRANSACTION_READ_COMMITTED)
		boolean flag = Db.tx(new IAtom() {
			@Override
			public boolean run() throws SQLException {
				try {
					// 删除前置任务
					if (intercept != null) {
						intercept.deleteBefore(ctrl, pkValues);
					}

					// 删除动作
					if (!xx.isEmpty(pkValues)) {
						String[] pks = pkValues.split(",");
						for (String pk : pks) {
							// 根据主键删除对象
							if (!xx.isEmpty(crud.getTable())) {
								Db.use(crud.getDs()).deleteById(crud.getTable(), crud.getPkName(), pk);
							} else {
								// update view
								CrudManager.deleteView(eo.getStr("code"), pk);
							}
						}
					}

					// 删除后置任务
					if (intercept != null) {
						intercept.deleteAfter(ctrl, pkValues);
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
		EovaLog.dao.info(this, EovaLog.DELETE, eo.getStr("code") + "[" + pkValues + "]");

		// 删除成功之后
		if (intercept != null) {
			try {
				intercept.deleteSucceed(this, pkValues);
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
	public void update() {

		final Crud crud = new Crud(this, CrudConfig.UPDATE);

		// 初始化业务拦截器
		initIntercept(crud.getBizIntercept());

		// 获取基础数据
		final MetaObject eo = crud.getObject();
		final Map<String, Record> reMap = CrudManager.buildData(this, crud.getItemList(), record, crud.getPkName(), false);
		final Object pkValue = record.get(crud.getPkName());

		// 事务(默认为TRANSACTION_READ_COMMITTED)
		boolean flag = Db.tx(new IAtom() {
			@Override
			public boolean run() throws SQLException {
				try {
					// 修改前置任务
					if (intercept != null) {
						intercept.updateBefore(ctrl, record);
					}

					if (!xx.isEmpty(crud.getTable())) {
						// update table
						Db.use(crud.getDs()).update(crud.getTable(), crud.getPkName(), record);
					} else {
						// update view
						CrudManager.operateView(crud.getPkName(), reMap, CrudConfig.UPDATE);
					}

					// 修改后置任务
					if (intercept != null) {
						intercept.updateAfter(ctrl, record);
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
		EovaLog.dao.info(this, EovaLog.UPDATE, eo.getStr("code") + "[" + pkValue + "]");

		// 修改成功之后
		if (intercept != null) {
			try {
				intercept.updateSucceed(this, record);
			} catch (Exception e) {
				buildException(e);
				renderJson(new Easy("修改成功,后置任务执行异常!<p title=\"" + info + "\">" + error + "</p>"));
				return;
			}
		}

		renderJson(new Easy());
	}

	/**
	 * 导入
	 */
	public void importXls() {
		final Crud crud = new Crud(this, CrudConfig.IMPORTXLS);

		// 初始化业务拦截器
		initIntercept(crud.getBizIntercept());

		// 获取基础数据
		final MetaObject eo = crud.getObject();

		// 默认上传到/upload 临时目录
		final UploadFile file = getFile("upfile");
		if (file == null) {
			uploadCallback(false, "上传失败，文件不存在！");
			return;
		}

		// 获取文件后缀
		String suffix = FileUtil.getFileType(file.getFileName());
		if (!suffix.equals(".xls")) {
			uploadCallback(false, "请导入.xls格式的Excel文件！");
			return;
		}

		// 事务(默认为TRANSACTION_READ_COMMITTED)
		CrudAtom atom = new CrudAtom(file.getFile(), crud, intercept, ctrl);
		boolean flag = Db.tx(atom);

		if (!flag) {
			atom.getRunExp().printStackTrace();
			uploadCallback(false, "导入失败,服务异常!<br>" + atom.getRunExp().getMessage());
			return;
		}

		// 记录导入日志
		EovaLog.dao.info(this, EovaLog.IMPORT, eo.getStr("code"));

		// 导入成功之后
		if (intercept != null) {
			try {
				intercept.importSucceed(this, atom.getRecords());
			} catch (Exception e) {
				e.printStackTrace();
				uploadCallback(false, "导入成功,后置任务执行异常!<br>" + e.getMessage());
				return;
			}
		}

		uploadCallback(true, "导入成功！");
	}

	// ajax 上传回调
	private void uploadCallback(boolean succeed, String msg) {
		renderHtml("<script>parent.callback(\"" + msg + "\", " + succeed + ");</script>");
	}
	
	// 异步传图(返回必须是html,否则无法解析)
	public void uploadImg() {

		String name = getPara("name");
		if (xx.isEmpty(name)) {
			renderHtml("{\"success\":false, \"msg\": '找不到上传控件，一开始是拒绝的！'}");
			return;
		}

		String fileName = "";

		UploadFile file = null;
		try {
			// 上传到/upimg 目录
			file = getFile(name, EovaConst.DIR_IMG);
			if (xx.isEmpty(file)) {
				renderHtml("{\"success\":false, \"msg\": '请选择一个图片'}");
				return;
			}
			// 大小限制
			if (FileUtil.checkFileSize(file.getFile(), 2048)) {
				String msg = "图片大小不能超过2M";
				renderHtml("{\"success\":false, \"msg\": '" + msg + "'}");
				return;
			}
			// 图片合法性严格检查(图片后缀+图片头)
			if (!ImageUtil.isImage(file.getFile().getPath())) {
				renderHtml("{\"success\":false, \"msg\": '该文件不是标准的图片文件，请勿手工对图片文件进行修改'}");
				return;
			}

			// 获取文件名
			fileName = file.getFileName();
			// 获取文件后缀
			String suffix = FileUtil.getFileType(fileName);
			// 创建新的随机文件名
			fileName = System.currentTimeMillis() + suffix;

		} catch (Exception e) {
			e.printStackTrace();
			renderHtml("{\"success\":false, \"msg\": '系统异常：图片上传失败,请稍后再试'}");
			return;
		}
		// 返回必须是html,否则无法解析
		renderHtml("{\"success\":true, \"msg\": \"上传成功\", \"fileName\": \"" + fileName + "\"}");
	}
}