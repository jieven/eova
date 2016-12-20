package com.oss.hotel;

import com.eova.aop.AopContext;
import com.eova.aop.MetaObjectIntercept;
import com.eova.common.utils.xx;

public class HotelIntercept extends MetaObjectIntercept {

	@Override
	public void queryBefore(AopContext ac) throws Exception {
		String name = ac.ctrl.getPara("query_name");
		if (!xx.isEmpty(name)) {
			ac.condition = " state = " + name;
		}
	}
	
}
