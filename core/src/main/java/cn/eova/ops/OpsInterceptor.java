/**
 * Copyright (c) 2015-2026 EOVA.CN. All rights reserved.
 * Licensed under the LGPL-3.0 license
 * For authorization, please contact: admin@eova.cn
 */
package cn.eova.ops;

import cn.eova.common.base.BaseController;
import cn.eova.tools.x;
import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;

/**
 * 运维服务通用逻辑
 *
 * @author Jieven
 */
public class OpsInterceptor implements Interceptor {

    @Override
    public void intercept(Invocation inv) {
        BaseController ctrl = (BaseController) inv.getController();

        String token = ctrl.get("token");
        if (x.isEmpty(token)) {
            ctrl.NO("运维令牌不存在");
            return;
        }
        String opsToken = x.conf.get("eova.ops.token");
        if (x.isEmpty(opsToken)) {
            ctrl.NO("运维令牌未配置, 无法使用运维服务");
            return;
        }
        if (!token.equals(opsToken)) {
            ctrl.NO("运维令牌错误");
            return;
        }

        inv.invoke();
    }
}