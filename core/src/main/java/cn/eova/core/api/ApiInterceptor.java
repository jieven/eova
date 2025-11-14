/**
 * Copyright (c) 2015-2026 EOVA.CN. All rights reserved.
 * Licensed under the LGPL-3.0 license
 * For authorization, please contact: admin@eova.cn
 */
package cn.eova.core.api;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;
import com.jfinal.kit.LogKit;

/**
 * API 统一处理
 * 1.异常处理
 *
 * @author Jieven
 */
public class ApiInterceptor implements Interceptor {

    @Override
    public void intercept(Invocation inv) {
        Controller ctrl = inv.getController();

        try {
            inv.invoke();
        } catch (Exception e) {
            LogKit.error(e.getMessage(), e);
            // 统一返回格式
            ctrl.renderJson(ApiResponse.NO("服务内部错误"));
        }
    }

}