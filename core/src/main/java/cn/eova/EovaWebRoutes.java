/**
 * Copyright (c) 2015-2026 EOVA.CN. All rights reserved.
 * Licensed under the LGPL-3.0 license
 * For authorization, please contact: admin@eova.cn
 */
package cn.eova;

import cn.eova.config.WebRoutes;
import cn.eova.core.HomeController;
import cn.eova.core.admin.AdminController;
import cn.eova.core.auth.AuthController;
import cn.eova.core.button.ButtonController;
import cn.eova.core.dict.DictController;
import cn.eova.core.menu.MenuController;
import cn.eova.core.meta.MetaController;
import cn.eova.core.sse.SSEController;
import cn.eova.core.task.TaskController;
import cn.eova.meta.api.FormControler;
import cn.eova.meta.api.MetaControler;
import cn.eova.meta.api.TableController;
import cn.eova.meta.api.WidgetController;
import cn.eova.ops.OpsController;
import cn.eova.user.UserController;
import cn.eova.widget.tree.TreeController;
import cn.eova.widget.upload.UploadController;

public class EovaWebRoutes extends WebRoutes {

    public void config() {
        super.config();

        // 需要注册父类Action 暂不需要
        //setMappingSuperClass(true);

        // EovaMeta
        add("/api/home", HomeController.class);
        add("/api/meta", MetaControler.class);
        add("/api/widget", WidgetController.class);
        add("/api/form", FormControler.class);
        add("/api/table", TableController.class);
        add("/api/tree", TreeController.class);

        // 通用业务
        add("/upload", UploadController.class);

        // Eova业务
        add("/sse", SSEController.class);
        add("/eova/admin", AdminController.class);
        add("/eova/ops", OpsController.class);
        add("/user", UserController.class);


        add("/meta", MetaController.class);
        add("/menu", MenuController.class);
        add("/button", ButtonController.class);
        add("/auth", AuthController.class);
        add("/task", TaskController.class);
        add("/dict", DictController.class);

        // LoginInterceptor.excludes.add(String.format("%s/**/**", ApiRouterHandler.ROUTER));

    }

}