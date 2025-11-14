/**
 * Copyright (c) 2015-2026 EOVA.CN. All rights reserved.
 * Licensed under the LGPL-3.0 license
 * For authorization, please contact: admin@eova.cn
 */
package cn.eova.core.api;

import com.jfinal.config.Routes;
import com.jfinal.core.Controller;

public class ApiRoutes extends Routes {

    public void config() {
        addInterceptor(new ApiInterceptor());
    }

    @Override
    public Routes add(String controllerPath, Class<? extends Controller> controllerClass) {
        return super.add(ApiRouterHandler.ROUTER + controllerPath, controllerClass);
    }


}