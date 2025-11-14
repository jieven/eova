/**
 * Copyright (c) 2015-2026 EOVA.CN. All rights reserved.
 * Licensed under the LGPL-3.0 license
 * For authorization, please contact: admin@eova.cn
 */
package cn.eova.core.admin;

import java.util.Map;

import cn.eova.tools.x;
import com.alibaba.fastjson.JSONObject;
import cn.eova.common.base.BaseController;
import cn.eova.common.utils.web.WebUtil;
import cn.eova.config.EovaConfig;
import cn.eova.model.Role;
import cn.eova.model.User;
import cn.eova.service.sm;
import com.jfinal.aop.Before;
import com.jfinal.aop.Clear;
import com.jfinal.kit.Ret;

/**
 * EOVA超级管理功能
 * @author Jieven
 *
 */
@Before(AdminInterceptor.class)
public class AdminController extends BaseController {

    /**
     * 升级控制台
     */
    public void upgrade() {
        render("/eova/admin/upgrade.html");
    }

    // 超级切换
    public void su() {

//        setAttr("ID", x.conf.get("login.user.id", "id"));
//        setAttr("RID", x.conf.get("login.user.rid", "rid"));
//        setAttr("NAME", x.conf.get("login.user.name", "name"));
//        setAttr("INFO", x.conf.get("su.user.info", "${user.rid}:${user.name}[${user.id}]"));

        setAttr("objectCode", x.conf.get("su.object.code", "eova_user_code"));

        setAttr("USERINFO", String.format("%s", getUser().getStr("company_id")));

        // 自动重登
        reLogin();

        render("/eova/user/su/app.html");
    }

    private static String LOGIN_ID = "login_id";

    // 切换用户
    public void doSu() {

        String UID = x.conf.get("login.user.id", "id");// 用户
        String RID = x.conf.get("login.user.rid", "rid");// 角色
        String OID = x.conf.get("login.user.oid", "org_id");// 部门
        String NAME = x.conf.get("login.user.name", "name");

        Integer uid = getInt(UID);
        Integer rid = getInt(RID + "_val");
        Integer oid = getInt(OID + "_val");
        String name = get(NAME);

        JSONObject o = (JSONObject) getJson();
        x.log.info(String.format("SU:%s", name));

        User user = getUser();
        // 备份当前登录ID
        user.put(LOGIN_ID, user.getId());
        user.set("id", uid);
        // 临时用户角色
//        Integer rid = o.getInteger(RID + "_val");
        user.put("su_rid", rid);
        user.setRole(Role.dao.findById(rid));
        user.set("name", name);
        // 默认切部门, 其它的数据和参数通过拦截器干预
        if (oid != null) {
            user.put("org_id", oid);
        }

        // 用户切换AOP
        if (EovaConfig.getUserSessionIntercept() != null) {
            EovaConfig.getUserSessionIntercept().su(user, o);
        }

        updateUser(user);

//        renderJson(Ret.ok().set("info", String.format("1111")));
        OK();
    }

    // 重新登录
    public void reLogin() {
        String ip = WebUtil.getRealIp(getRequest());
        User user = sm.login.loginBySid(SID(), ip);
        // 登录初始化
        if (EovaConfig.getUserSessionIntercept() != null) {
            EovaConfig.getUserSessionIntercept().login(user);
        }
        // 更新用户缓存
        updateUser(user);

        String NAME = x.conf.get("login.user.name", "name");

        renderJson(Ret.ok().set("name", user.getStr(NAME)));
    }

    // 查看当前运行时配置
    public void showRuntimeConfig() {
        Map<String, String> map = x.conf.getProps();
        StringBuilder sb = new StringBuilder("<h4>配置项总数:" + map.size() + "</h4>");
        map.forEach((k, v) -> {
            sb.append(String.format("<b>%s</b> = %s<hr>", k, v));
        });

        renderHtml(sb.toString());
    }


    // 查看当前用户数据 仅开发环境可查看
    @Clear
    public void showUserData() {
        if (!x.conf.get("env").equalsIgnoreCase("DEV")) {
            renderText("仅开发环境可查看用户数据");
            return;
        }
        StringBuilder sb = new StringBuilder();

        renderJson(getUser());
    }
}