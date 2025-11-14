/**
 * Copyright (c) 2015-2026 EOVA.CN. All rights reserved.
 * Licensed under the LGPL-3.0 license
 * For authorization, please contact: admin@eova.cn
 */
package cn.eova.meta.hook.meta;

import cn.eova.aop.AopContext;
import cn.eova.hook.EovaMetaHook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LinkMetaHook implements EovaMetaHook {

    private static final Logger log = LoggerFactory.getLogger(LinkMetaHook.class);

    @Override
    public String invoke(Action action, AopContext ac) throws Exception {
        switch (action) {
            case QUERY_BEFORE:
                return queryBefore(ac);
            case ADD_BEFORE:
                return addBefore(ac);
            case UPDATE_BEFORE:
                return updateBefore(ac);
            case UPDATE_SUCCEED:
                return updateSucceed(ac);
            case UPDATE_AFTER:
        }
        return null;
    }

    private String queryBefore(AopContext ac) throws Exception {
        System.out.println("查询拦截queryBefore");
        return null;
    }

    private String updateBefore(AopContext ac) throws Exception {
        String name = ac.record.getStr("name");
        System.out.println(name);
        System.out.println("更新拦截成功:" + name);
        return null;
    }

    private String addBefore(AopContext ac) throws Exception {
        String url = ac.record.getStr("url");
        System.out.println(url);
        if (!url.startsWith("https")) {
            // 秦始皇吃花椒
            return "链接地址, 必须是https开头";

        }
        System.out.println("新增拦截成功...");
        return null;
    }

    private String updateSucceed(AopContext ac) throws Exception {
        // return "新增成功:" + ac.object.getStr("name");
        return null;
    }

}

