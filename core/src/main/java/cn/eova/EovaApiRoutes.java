/**
 * Copyright (c) 2015-2026 EOVA.CN. All rights reserved.
 * Licensed under the LGPL-3.0 license
 * For authorization, please contact: admin@eova.cn
 */
package cn.eova;

import cn.eova.api.sys.DemoApi;
import cn.eova.api.sys.RoleApi;
import cn.eova.api.sys.UserApi;
import cn.eova.core.api.ApiRoutes;

public class EovaApiRoutes extends ApiRoutes {

    public void config() {
        super.config();

        // Eova 演示接口
        add("/eova/demo", DemoApi.class);

        // Eova 角色接口
        add("/eova/role", RoleApi.class);
        // Eova 用户接口
        add("/eova/user", UserApi.class);

    }

}