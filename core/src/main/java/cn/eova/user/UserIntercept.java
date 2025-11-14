/**
 * Copyright (c) 2015-2026 EOVA.CN. All rights reserved.
 *
 * Licensed under the LGPL-3.0 license
 * For authorization, please contact: admin@eova.cn
 */
package cn.eova.user;

import cn.eova.tools.x;
import cn.eova.aop.AopContext;
import cn.eova.aop.MetaObjectIntercept;
import cn.eova.common.Ds;
import cn.eova.common.utils.EncryptUtil;
import com.jfinal.plugin.activerecord.Db;

/**
 * 自定义用户管理拦截器
 * @author Jieven
 *
 */
public class UserIntercept extends MetaObjectIntercept {

    @Override
    public String addBefore(AopContext ac) throws Exception {
        // 数据服务端校验
        String loginId = ac.record.getStr("login_id");
        Long num = Db.use(Ds.EOVA).queryLong("select count(*) from eova_user where login_id = ?", loginId);
        if (num > 0) {
            return warn("帐号重复,请重新填写!");
        }

        // 新增时密码加密储存
        String str = ac.record.getStr("login_pwd");
        // 加密方式可配置
        String encrypt = x.conf.get("eova.pwd.encrypt", "SM32");
        if (encrypt.equals("SM32")) {
            str = EncryptUtil.getSM32(str);
        } else {
            str = EncryptUtil.getMd5(str);
        }
        ac.record.set("login_pwd", str);

        return null;
    }

}