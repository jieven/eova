/**
 * Copyright (c) 2013-2015, Jieven. All rights reserved.
 *
 * Licensed under the GPL license: http://www.gnu.org/licenses/gpl.txt
 * To use it on other terms please contact us at 1623736450@qq.com
 */
package com.eova.template.crud;

import java.util.List;

import com.jfinal.core.Controller;
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
	 * 新增前置任务(事务内)
	 * 
	 * @param ctrl 当前会话控制器
	 * @param record 当前操作数据
	 * 
	 * @throws Exception
	 */
	public void addBefore(Controller ctrl, Record record) throws Exception;

	/**
	 * 新增后置任务(事务内)
	 * 
	 * @param ctrl 当前会话控制器
	 * @param record 当前操作数据
	 * @throws Exception
	 */
	public void addAfter(Controller ctrl, Record record) throws Exception;

	/**
	 * 新增成功之后(事务外)
	 * 
	 * @param ctrl 当前会话控制器
	 * @param record 当前操作数据
	 * @throws Exception
	 */
	public void addSucceed(Controller ctrl, Record record) throws Exception;

	/**
	 * 删除前置任务(事务内)
	 * 
	 * @param ctrl 当前会话控制器
	 * @param pkValues 选中行主键值
	 * @throws Exception
	 */
	public void deleteBefore(Controller ctrl, String pkValues) throws Exception;

	/**
	 * 删除后置任务(事务内)
	 * 
	 * @param ctrl 当前会话控制器
	 * @param pkValues 选中行主键值
	 * @throws Exception
	 */
	public void deleteAfter(Controller ctrl, String pkValues) throws Exception;

	/**
	 * 删除成功之后(事务外)
	 * 
	 * @param ctrl 当前会话控制器
	 * @param pkValues 选中行主键值
	 * @throws Exception
	 */
	public void deleteSucceed(Controller ctrl, String pkValues) throws Exception;

	/**
	 * 更新前置任务(事务内)
	 * 
	 * @param ctrl 当前会话控制器
	 * @param record 当前操作数据
	 * @throws Exception
	 */
	public void updateBefore(Controller ctrl, Record record) throws Exception;

	/**
	 * 更新后置任务(事务内)
	 * 
	 * @param ctrl 当前会话控制器
	 * @param record 当前操作数据
	 * @throws Exception
	 */
	public void updateAfter(Controller ctrl, Record record) throws Exception;

	/**
	 * 更新成功之后(事务外)
	 * 
	 * @param ctrl 当前会话控制器
	 * @param record 当前操作数据
	 * @throws Exception
	 */
	public void updateSucceed(Controller ctrl, Record record) throws Exception;

	/**
	 * 导入前置任务(事务内)
	 * 
	 * @param ctrl 当前会话控制器
	 * @param records 当前操作数据
	 * @throws Exception
	 */
	public void importBefore(Controller ctrl, List<Record> records) throws Exception;

	/**
	 * 导入后置任务(事务内)
	 * 
	 * @param ctrl 当前会话控制器
	 * @param records 当前操作数据
	 * @throws Exception
	 */
	public void importAfter(Controller ctrl, List<Record> records) throws Exception;

	/**
	 * 导入成功之后(事务外)
	 * 
	 * @param ctrl 当前会话控制器
	 * @param records 当前操作数据
	 * @throws Exception
	 */
	public void importSucceed(Controller ctrl, List<Record> records) throws Exception;
}