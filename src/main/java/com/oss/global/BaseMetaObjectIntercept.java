package com.oss.global;

import java.util.Date;

import com.eova.aop.AopContext;
import com.eova.aop.MetaObjectIntercept;
import com.eova.model.MetaField;

/**
 * 公共元对象业务拦截器
 * <pre>
 * 使用场景:
 * 全局业务 例:增删改查日志记录
 * 高频字段统一处理 例:create_time update_time ...
 * 其它更多高端玩法,请尽情的发挥想象吧!
 * </pre>
 * 
 * @author Jieven
 *
 */
public class BaseMetaObjectIntercept extends MetaObjectIntercept {

	@Override
	public String updateBefore(AopContext ac) throws Exception {
		System.out.println("公共元对象业务拦截器");
		for (MetaField f : ac.object.getFields()) {
			if (f.getEn().equals("update_time")) {
				ac.record.set("update_time", new Date());
			}
			if (f.getEn().equals("user_id")) {
				ac.record.set("user_id", ac.user.getInt("id"));
			}
		}
		return null;
	}

}
