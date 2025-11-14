/**
 * Copyright (c) 2015-2026 EOVA.CN. All rights reserved.
 * Licensed under the LGPL-3.0 license
 * For authorization, please contact: admin@eova.cn
 */
package cn.eova.config;

import cn.eova.tools.x;
import cn.eova.core.api.ApiRouterHandler;
import com.jfinal.kit.LogKit;

public class EovaInit {
    /**
     * 初始化Eova Api 应用配置
     */
    static void initEovaApiAppCofing() {
        if (!ApiRouterHandler.getAppConfig().isEmpty()) {
            return;
        }

        String appsConfig = x.conf.get("eova.api.apps");
        if (x.isEmpty(appsConfig)) {
            LogKit.debug("eova.api.apps 为空, 可能无法使用API");
            return;
        }
        String[] apps = appsConfig.split(";");
        for (String app : apps) {
            String[] ss = app.split(":");
            ApiRouterHandler.addAppConfig(ss[0], ss[1]);
        }

    }

}