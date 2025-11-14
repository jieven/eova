/**
 * Copyright (c) 2015-2026 EOVA.CN. All rights reserved.
 * Licensed under the LGPL-3.0 license
 * For authorization, please contact: admin@eova.cn
 */
package cn.eova.meta;

import cn.eova.auth.AuthUri;
import cn.eova.config.WebRoutes;
import cn.eova.meta.ctrl.ImportController;
import cn.eova.meta.ctrl.TestController;

public class AppRoutes extends WebRoutes {

    public void config() {
        super.config();

        add("/test", TestController.class);
        add("/excel", ImportController.class);

        // 登录后免鉴权
        AuthUri.loginAuth.add("/theme");
        AuthUri.loginAuth.add("/test/**");
        AuthUri.loginAuth.add("/api/table/query/*");

        // 登录后免鉴权 @see com.eova.common.utils.util.AntPathMatcher
        // 如果是内网可信人员使用的系统,可以采用这种配置方式, 一劳永逸.
        // AuthUri.loginAuth.add("/**/**"); // 所有URI 登录后免检(存在安全风险, 请自行负责)
        // AuthUri.loginAuth.add("/xxx/**");// 指定URI 登录后免检(存在安全风险, 请自行负责)

        // 登录免鉴权（优先使用@Clear）
        // LoginInterceptor.excludes.add("xxxxx");

    }

}