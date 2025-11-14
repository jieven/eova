/**
 * Copyright (c) 2015-2026 EOVA.CN. All rights reserved.
 * Licensed under the LGPL-3.0 license
 * For authorization, please contact: admin@eova.cn
 */
package cn.eova.meta;

import cn.eova.common.utils.web.WebUtil;
import cn.eova.core.IndexController;
import com.jfinal.aop.Clear;

public class AppController extends IndexController {

    // EovaUI主题风格演示
    public void theme() {
        render("/_view/theme/index.html");
    }

    // EovaUI 组件演示
    public void widget() {
        render("/_view/widget/index.html");
    }

    public void ip() {
        String ip = WebUtil.getRealIp(this.getRequest());
        System.out.println(ip);

        renderText(ip);
    }

    // 临时查看登录页效果
    @Clear
    public void sso() {
        render("/_view/login/login.html");
    }


}