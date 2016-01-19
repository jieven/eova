/**
 * Copyright (c) 2013-2016, Jieven. All rights reserved.
 *
 * Licensed under the GPL license: http://www.gnu.org/licenses/gpl.txt
 * To use it on other terms please contact us at 1623736450@qq.com
 */
package com.oss.model;

import com.eova.common.base.BaseModel;

/**
 * 酒店
 * @author Jieven
 *
 */
public class Hotel extends BaseModel<Hotel> {

	private static final long serialVersionUID = 1064291771401662738L;

	public static final Hotel dao = new Hotel();

	/**
	 * 根据UID获取所属酒店信息
	 * @param uid
	 * @return
	 */
	public Hotel findByUid(int uid){
		String sql = "select h.* from hotel h,eova_user u where u.hotel_id = h.id and u.id = ?";
		return this.findFirst(sql, uid);
	}
}