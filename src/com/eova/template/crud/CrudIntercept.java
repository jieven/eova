/**
 * Copyright (c) 2013-2015, Jieven. All rights reserved.
 *
 * Licensed under the GPL license: http://www.gnu.org/licenses/gpl.txt
 * To use it on other terms please contact us at 1623736450@qq.com
 */
package com.eova.template.crud;

import com.jfinal.plugin.activerecord.Record;

/**
 * CRUD业务拦截器<br>
 * 前置任务和后置任务(事务未提交)<br>
 * 成功之后(事务已提交)<br>
 * 
 * @author Jieven
 * @date 2014-8-29
 */
public interface CrudIntercept {

	/**
	 * 新增前置任务
	 * 
	 * @param record 当前操作数据
	 * @throws Exception
	 */
	public void addBefore(Record record) throws Exception;

	/**
	 * 新增后置任务
	 * 
	 * @param record 当前操作数据
	 * @throws Exception
	 */
	public void addAfter(Record record) throws Exception;

	/**
	 * 新增成功之后
	 * 
	 * @param record 当前操作数据
	 * @throws Exception
	 */
	public void addSucceed(Record record) throws Exception;

	/**
	 * 删除前置任务
	 * 
	 * @param pkValues 选中行主键值
	 * @throws Exception
	 */
	public void deleteBefore(String pkValues) throws Exception;

	/**
	 * 删除后置任务
	 * 
	 * @param pkValues 选中行主键值
	 * @throws Exception
	 */
	public void deleteAfter(String pkValues) throws Exception;

	/**
	 * 删除成功之后
	 * 
	 * @param pkValues 选中行主键值
	 * @throws Exception
	 */
	public void deleteSucceed(String pkValues) throws Exception;

	/**
	 * 更新前置任务
	 * 
	 * @param record 当前操作数据
	 * @throws Exception
	 */
	public void updateBefore(Record record) throws Exception;

	/**
	 * 更新后置任务
	 * 
	 * @param record 当前操作数据
	 * @throws Exception
	 */
	public void updateAfter(Record record) throws Exception;

	/**
	 * 更新成功之后
	 * 
	 * @param record 当前操作数据
	 * @throws Exception
	 */
	public void updateSucceed(Record record) throws Exception;
}