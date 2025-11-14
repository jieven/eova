/**
 * Copyright (c) 2015-2026 EOVA.CN. All rights reserved.
 * Licensed under the LGPL-3.0 license
 * For authorization, please contact: admin@eova.cn
 */
package cn.eova.core;

import cn.eova.tools.x;
import cn.eova.common.base.BaseController;
import cn.eova.model.User;
import com.jfinal.aop.Clear;
import com.jfinal.core.Controller;

/**
 * 系统入口
 *
 * @author Jieven
 */
public class IndexController extends BaseController {

    public void index() {

        User user = getUser();
        // 已经登录
        if (user != null) {

            // 首页初始化
            indexInit(this, user);

            set("app_logo", x.conf.get("app.logo", null));

            render("/eova/index/index.html");
            return;
        }

        // 未登录
        // login();

        redirect("/user/login");
    }


    public void code() {
        setAttr("exp1", "select id UID,login_id CN from users where <%if(user.id != 0){%>  id > ${user.id}<%}%> order by id desc");
        render("/eova/code.html");
    }

    /**
     * 首页初始化
     * @param ctrl
     * @param user 当前用户
     * @throws Exception
     */
    protected void indexInit(Controller ctrl, User user) {
//        set("LOGIN_INFO", String.format("%s %s", "超级管理员", "林羽"));
        setAttr("LOGIN_INFO", String.format("%s[%s]", user.role.getStr("name"), user.get("name")));
    }

    @Clear
    public void diy() {
        // 特别注意， 此处仅限于与业务无关，且无动态业务参数，防止注入。

        String cmd = get(0);

        // 动态切换迷你皮肤 服务端是全局的, 不能切换....
        if (cmd.equals("mini")) {
            x.conf.addConfig("SKIN", "mini");
        }

        OK();
    }
}