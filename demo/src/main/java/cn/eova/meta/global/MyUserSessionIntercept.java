/**
 * Copyright (c) 2021 Wuhan JianMa Technology Co. LTD. All rights reserved.
 *
 * Licensed under the EPPL license: http://eova.cn/eppl.txt
 * For authorization, please contact: admin@eova.cn
 */
package cn.eova.meta.global;

import cn.eova.aop.UserSessionIntercept;
import cn.eova.model.User;
import com.alibaba.fastjson.JSONObject;
import com.jfinal.plugin.activerecord.Record;

/**
 * 用户登录拦截器
 * @author Jieven
 *
 */
public class MyUserSessionIntercept implements UserSessionIntercept {

    @Override
    public String loginBefore(User user) {
        Record data = user.getData();

        if (data.getInt("status") == 0) {
            return "帐号不存在";
        }
        if (data.getInt("status") == 2) {
            return "帐号已禁用";
        }

        return null;
    }

    @Override
    public void login(User user) {
        // user数据结构: id, rid, 帐号, 登录源Record 如果登录源存在会自动续传的字段org_id, name

        // 从登录源表获取信息()
        Record data = user.getData();
//		data.getStr("phone");

//        user.put("company_id",29373);
//		Users users = Users.dao.findById(user.getId());
//		Company company = Company.dao.findById(users.getInt("company_id"));
//
//		//查询该企业是否有代发订单
//		boolean hasDf = biz.order.hasDfOrder(company.getId());
//		company.put("hasdf", hasDf);
//
//		user.put("company", company);
//		user.put("name", user.getData().getStr("name"));
    }

    @Override
    public void logout(User user) {
        // TODO Auto-generated method stub
    }

    @Override
    public void su(User user, JSONObject switchUser) {
//        Users users = Users.dao.findById(switchUser.getInteger("id"));
//        Company company = Company.dao.findById(users.getInt("company_id"));
//        //查询该企业是否有代发订单
//        boolean hasDf = biz.order.hasDfOrder(company.getId());
//        company.put("hasdf", hasDf);
//        user.put("company", company);
//        user.setData(users.toRecord());
    }

}