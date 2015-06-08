/**
 * Copyright (c) 2013-2015, Jieven. All rights reserved.
 *
 * Licensed under the GPL license: http://www.gnu.org/licenses/gpl.txt
 * To use it on other terms please contact us at 1623736450@qq.com
 */
package com.eova.model;

import java.util.List;

import com.eova.common.base.BaseModel;
import com.eova.common.utils.xx;
import com.jfinal.plugin.activerecord.Db;

/**
 * 字段属性
 * 
 * @author Jieven
 * @date 2014-9-10
 */
public class MetaItem extends BaseModel<MetaItem> {

	private static final long serialVersionUID = -7381270435240459528L;

	public static final MetaItem dao = new MetaItem();
	
	public static final String TYPE_TEXT = "文本框";
	public static final String TYPE_COMBO = "下拉框";
	public static final String TYPE_FIND = "查找框";
	public static final String TYPE_TIME = "时间框";
	public static final String TYPE_TEXTS = "文本域";
	public static final String TYPE_EDIT = "编辑框";
	public static final String TYPE_CHECK = "复选框";
	public static final String TYPE_AUTO = "自增框";
	public static final String TYPE_IMG = "图片框";

	/**
	 * 获取对象详情
	 * 
	 * @param objectCode 对象Code
	 * @return 对象详情集合
	 */
	public List<MetaItem> queryByObjectCode(String objectCode) {
		return MetaItem.dao.find("select * from eova_item where objectCode = ? order by indexNum", objectCode);
	}
	
	/**
	 * 获取字段
	 * 
	 * @param objectCode 对象Code
	 * @param en 字段Key
	 * @return
	 */
	public MetaItem getByObjectCodeAndEn(String objectCode, String en) {
		MetaItem ei = MetaItem.dao.findFirst("select * from eova_item where objectCode = ? and en = ? order by indexNum", objectCode, en);
		return ei;
	}

	/**
	 * 删除对象关联属性
	 * 
	 * @param objectId
	 */
	public void deleteByObjectCode(String objectId) {
		String sql = "delete from eova_item where objectCode = (select code from eova_object where id in(?))";
		Db.use(xx.DS_EOVA).update(sql, objectId);
	}

	/**
	 * 查询视图包含的持久化对象Code
	 * 
	 * @param objectCode 视图对象编码
	 * @return
	 */
	public List<MetaItem> queryPoCodeByObjectCode(String objectCode) {
		return MetaItem.dao.find("SELECT DISTINCT(poCode) from eova_item where objectCode = ?", objectCode);
	}
}