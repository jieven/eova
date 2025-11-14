/**
 * Copyright (c) 2015-2026 EOVA.CN. All rights reserved.
 * Licensed under the LGPL-3.0 license
 * For authorization, please contact: admin@eova.cn
 */
package cn.eova.ops;

import cn.eova.auth.AuthInterceptor;
import cn.eova.common.base.BaseController;
import cn.eova.interceptor.LoginInterceptor;
import cn.eova.tools.x;
import com.jfinal.aop.Before;
import com.jfinal.aop.Clear;
import com.jfinal.kit.Ret;

/**
 * 运维服务
 *
 * @author Jieven
 */
@Clear({LoginInterceptor.class, AuthInterceptor.class})
@Before(OpsInterceptor.class)
public class OpsController extends BaseController {

    // 运维信息查看
    public void info() {
        Ret ret = Ret.ok();
        ret.set("ver", OpsConst.DEPLOY_VER);
        ret.set("time", OpsConst.DEPLOY_TIME);

        renderJson(ret);
    }

    // 服务暂停
    public void pause() {

        int flag = getInt("flag", 0);
        OpsConst.WAITING = flag == 1;

        // 自定义维护文案
        String info = get("info");
        if (!x.isEmpty(info)) {
            OpsConst.WAIT_INFO = info;
        }

        OK(OpsConst.WAITING ? "服务已暂停" : "服务已恢复");
    }

    // 运维信息更新
    public void update() {

        OpsConst.DEPLOY_VER = get("ver", "-.-.-");
        OpsConst.DEPLOY_GIT = get("git", "");
        OpsConst.DEPLOY_TIME = x.time.formatNowTime();

        OK();
    }

}