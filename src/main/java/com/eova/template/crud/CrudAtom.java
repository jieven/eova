package com.eova.template.crud;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.List;

import com.eova.common.utils.xx;
import com.eova.common.utils.excel.ExcelUtil;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.IAtom;
import com.jfinal.plugin.activerecord.Record;

public class CrudAtom implements IAtom {

	private final Controller ctrl;
	private File file;
	private Crud crud;
	private CrudIntercept intercept;
	private List<Record> records;
	private Exception runExp;

	public CrudAtom(File file, Crud crud, CrudIntercept intercept, Controller ctrl) {
		super();
		this.file = file;
		this.crud = crud;
		this.intercept = intercept;
		this.ctrl = ctrl;
	}

	@Override
	public boolean run() throws SQLException {
		try {

			// 导入Excel
			InputStream is = new FileInputStream(file);
			records = ExcelUtil.importExcel(is, crud.getItemList());

			// 导入前置任务
			if (intercept != null) {
				intercept.importBefore(ctrl, records);
			}

			// 保存导入数据
			for (Record re : records) {
				System.out.println(re.toJson());
				// 移除主键(新增数据主键自增长)
				re.remove(crud.getPkName());
				if (!xx.isEmpty(crud.getTable())) {
					Db.use(crud.getDs()).save(crud.getTable(), crud.getPkName(), re);
				}
			}

			// 新增后置任务
			if (intercept != null) {
				intercept.importAfter(ctrl, records);
			}
		} catch (Exception e) {
			runExp = e;
			return false;
		} finally {
			// 自动删除废弃临时文件
			if (file.exists()) {
				file.delete();
			}
		}
		return true;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public Crud getCrud() {
		return crud;
	}

	public void setCrud(Crud crud) {
		this.crud = crud;
	}

	public CrudIntercept getIntercept() {
		return intercept;
	}

	public void setIntercept(CrudIntercept intercept) {
		this.intercept = intercept;
	}

	public List<Record> getRecords() {
		return records;
	}

	public void setRecords(List<Record> records) {
		this.records = records;
	}

	public Exception getRunExp() {
		return runExp;
	}

	public void setRunExp(Exception runExp) {
		this.runExp = runExp;
	}

}
