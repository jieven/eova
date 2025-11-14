/**
 * Copyright (c) 2015-2026 EOVA.CN. All rights reserved.
 * Licensed under the LGPL-3.0 license
 * For authorization, please contact: admin@eova.cn
 */
package cn.eova.aop;

import cn.eova.common.base.BaseController;
import cn.eova.model.User;
import com.jfinal.core.Controller;


/**
 * EOVA AOP上下文
 *
 * @author Jieven
 */
public class EovaAopContext {

    /**
     * 当前控制器
     */
    public Controller ctrl;

    /**
     * 当前用户对象
     */
    public User user;

    public EovaAopContext(Controller ctrl) {
        this.ctrl = ctrl;
        this.user = ((BaseController) ctrl).getUser();
    }

    public int UID() {
        return this.user.get("id");
    }

}