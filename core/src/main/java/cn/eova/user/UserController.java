/**
 * Copyright (c) 2015-2026 EOVA.CN. All rights reserved.
 *
 * Licensed under the LGPL-3.0 license
 * For authorization, please contact: admin@eova.cn
 */
package cn.eova.user;

import java.time.LocalDate;
import java.util.List;

import cn.eova.tools.EovaTool;
import cn.eova.tools.x;
import com.alibaba.fastjson.JSONObject;
import cn.eova.common.Ds;
import cn.eova.common.Easy;
import cn.eova.common.base.BaseCache;
import cn.eova.common.base.BaseController;
import cn.eova.common.utils.EncryptUtil;
import cn.eova.common.utils.string.Base64;
import cn.eova.common.utils.string.RSAEncrypt;
import cn.eova.common.utils.web.HtmlUtil;
import cn.eova.common.utils.web.WebUtil;
import cn.eova.common.utils.xx;
import cn.eova.config.EovaConfig;
import cn.eova.config.EovaConst;
import cn.eova.i18n.I18NBuilder;
import cn.eova.model.User;
import cn.eova.service.LoginService;
import cn.eova.service.sm;
import com.jfinal.captcha.CaptchaRender;
import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.ehcache.CacheKit;

/**
 * 用户相关业务
 *
 * @author Jieven
 */
public class UserController extends BaseController {

    public void captcha() {
        render(new CaptchaRender());
    }

    // 退出登录
    public void logout() {
        // 登出拦截器 >> 统计, 日志 等
        if (EovaConfig.getUserSessionIntercept() != null) {
            EovaConfig.getUserSessionIntercept().logout(getUser());
        }
        // 清除登录状态
        sm.login.logout(getCookie(LoginService.CKSID));
        removeCookie(LoginService.CKSID);
        redirect(EovaConfig.EOVA_INDEX);
    }

    public void login() {

        boolean isCaptcha = x.conf.getBool("isCaptcha", true);
        boolean isI18N = x.conf.getBool("isI18N", false);

        set("isCaptcha", isCaptcha);
        set("isI18N", isI18N);

        // 应用版权配置
        String cp = x.conf.get("app.copyright");
        if (x.isEmpty(cp)) {
            // 十年仰望星空，指尖磨出银河的纹路；所谓情怀，不过是把一句承诺，熬成永恒的星光。
            cp = String.format("© 2015-%s EOVA.CN", LocalDate.now().getYear());
        }
        set("copyright", cp);
        set("app_name", x.conf.get("app.name", null));
        set("login_id", x.conf.get("dev.login_id"));
        set("login_pwd", x.conf.get("dev.login_pwd"));

        set("source", get("back"));

        render(x.conf.get("app.login.page", "/eova/index/login.html"));
    }

    public void doLogin() {
        String loginId = get("login_id");
        String loginPwd = get("login_pwd");

        // XSS过滤
        loginId = HtmlUtil.XSSEncode(loginId);
        loginPwd = HtmlUtil.XSSEncode(loginPwd);

        // 默认保持登录1年（不需要保持登录可调整此参数）
        boolean keepLogin = getParaToBoolean("keepLogin", true);

//		keepPara();

        boolean isCaptcha = x.conf.getBool("isCaptcha", true);
        if (isCaptcha && !super.validateCaptcha("captcha")) {
            NO("验证码错误，请重新输入！");
            return;
        }

        String ip = WebUtil.getRealIp(getRequest());
        Ret ret = sm.login.login(loginId, loginPwd, keepLogin, ip);
        if (ret.isFail()) {
            // 登录错误次数限制
            String key = "login_error_num";
            int max = x.conf.getInt("login_error_max", 10);
            Integer num = CacheKit.get(BaseCache.SYS, key);
            if (num == null) {
                num = 0;
            }
            if (num >= max) {
                x.log.error(String.format("用户登录错误超过限制次数, 用户:%s IP:%s", loginId, ip));
                NO("登录错误次数过多, 请1小时后再试");
                return;
            }

            CacheKit.put(BaseCache.SYS, key, num + 1);

            NO(ret.getStr("msg"));
            return;
        }

        User user = (User) ret.get(LoginService.USER); // 用户:id, rid, 帐号
        // 用户角色一个权限都没有(角色异常或角色无授权)
        if (x.isEmpty(user.get("auths"))) {
            NO("您暂未获得任何功能授权, 请联系管理员!");
            return;
        }

        // 登录前置处理
        if (EovaConfig.getUserSessionIntercept() != null) {
            String msg = EovaConfig.getUserSessionIntercept().loginBefore(user);
            if (msg != null) {
                NO(msg);
                return;
            }
        }

        String sid = user.getStr(LoginService.SID);
        Integer maxAge = ret.getInt("maxAgeInSeconds");
        setCookie(LoginService.CKSID, sid, maxAge, true);
        // 登录拦截器 >> 登录初始化等
        if (EovaConfig.getUserSessionIntercept() != null) {
            EovaConfig.getUserSessionIntercept().login(user);
            updateUser(user);// 更新用户缓存
        }

        // initI18N();

        // setAttr(LoginService.SID, ret.get(BaseCache.LOGIN));

        // 重定向到首页
        boolean isMobile = WebUtil.isMobile(getRequest());
        String source = EovaConfig.EOVA_INDEX;
        if (isMobile) {
            source = EovaConfig.EOVA_INDEX_H5;
        }
        String back = get("source");
        if (!x.isEmpty(back)) {
            source = back;
        }
        // redirect(source);

        OK();
    }

    // 授权登录
    public void authLogin() {

        String publicKey = "公钥";
        String token = "JSON登录密文";
//		{
//			rid : 1,
//			role_name : "角色名称",
//			company_id : 1,
//			login_id : "账号名",
//			nickname : "昵称",
//		}

        try {
            // 获取第三方返回的授权内容
            byte[] res = RSAEncrypt.decrypt(RSAEncrypt.loadPublicKeyByStr(publicKey), Base64.decode(token));
            String s = new String(res, "UTF-8");

            JSONObject o = JSONObject.parseObject(s);


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    // 用户管理>修改密码
    public void pwd() {
        Integer id = EovaTool.toInt(getSelectValue("id"));
        String val = getInputValue();

        if (x.isEmpty(val)) {
            renderJson(new Easy("密码不能为空！"));
            return;
        }
        if (val.length() < 6) {
            renderJson(new Easy("密码不能少于6位！"));
            return;
        }

        User user = new User();
        user.set("id", id);
        user.set("login_pwd", EncryptUtil.getSM32(val));
        user.update();

        renderJson(new Easy());
    }

    // 首页>修改密码
    public void password() {
//        renderBeetl("/eova/updatePwd.html");
        render("/eova/user/password/app.html");
    }

    // 首页>修改密码
    public void doPassword() {
        String oldPwd = get("oldPwd");
        String newPwd = get("newPwd");
        String confirm = get("confirm");

        if (x.isOneEmpty(oldPwd, newPwd, confirm)) {
            NO("三个密码都不能为空");
            return;
        }

        if (!newPwd.equals(confirm)) {
            NO("新密码两次输入不一致");
            return;
        }

        String userDs = x.conf.get("login.user.ds", Ds.EOVA);
        String userTable = x.conf.get("login.user.table", "eova_user");
        String userId = x.conf.get("login.user.id", "id");
        String userPassword = x.conf.get("login.user.password", "login_pwd");
        String uid = getUser().getStr("id");

        Record r = Db.use(userDs).findFirst(String.format("select %s,%s from %s where %s = ?", userId, userPassword, userTable, userId), uid);
        String pwd = r.getStr(userPassword);

        if (!pwd.equals(EncryptUtil.getSM32(oldPwd))) {
            NO("旧密码错误");
            return;
        }

        // 修改密码
        // 加密方式可配置
        String encrypt = x.conf.get("eova.pwd.encrypt", "SM32");
        if (encrypt.equals("SM32")) {
            newPwd = EncryptUtil.getSM32(newPwd);
        } else {
            newPwd = EncryptUtil.getMd5(newPwd);
        }
        r.set(userPassword, newPwd);
        Db.use(userDs).update(userTable, r);

        OK();
    }

    private void initI18N() {
        boolean isI18N = x.conf.getBool("isI18N", false);
        if (isI18N) {
            String local = get("local");
            if (!x.isEmpty(local)) {
                // 记录当前语种(默认记录1年)
                setCookie(EovaConst.LOCAL, local, 60 * 60 * 24 * 365, true);
                // 加载国际化文案
                List<Record> list = Db.use(Ds.EOVA).find("select * from eova_i18n where val is not null or val <> ''");
                I18NBuilder.init(list);
                xx.info("%s语言,加载文案总数:%s", local, list.size());
            }
        }
    }

}