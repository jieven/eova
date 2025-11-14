/**
 * Copyright (c) 2015-2026 EOVA.CN. All rights reserved.
 * Licensed under the LGPL-3.0 license
 * For authorization, please contact: admin@eova.cn
 */
package cn.eova.config;

import cn.eova.tools.x;
import cn.eova.aop.AopContext;
import cn.eova.aop.MetaObjectIntercept;
import cn.eova.common.Ds;
import com.jfinal.kit.LogKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

public class EovaConfigIntercept extends MetaObjectIntercept {

    @Override
    public String addSucceed(AopContext ac) throws Exception {
        reload(ac.record);
        return super.addSucceed(ac);
    }

    @Override
    public String updateSucceed(AopContext ac) throws Exception {
        reload(ac.record);
        return super.addSucceed(ac);
    }

    // 修改后更新配置缓存
    @Override
    public String updateCellAfter(AopContext ac) throws Exception {
        String pk = ac.record.getStr("pk");
        Record e = Db.use(Ds.EOVA).findById("eova_config", pk);
        reload(e);
        return super.updateCellAfter(ac);
    }

    public void reload(Record e) {
        String key = e.getStr("code");
        // 测试值 || 默认值
        String val = e.get("test", e.get("value"));

        LogKit.info("更新配置文件[%s=%s]", key, val);
        x.conf.addConfig(key, val);
//
//        // 页面模版常量 动态更新配置
//        EovaConst.getPageConst().forEach((k, v) -> {
//            if (v.equalsIgnoreCase(key)) {
//                EovaConfig.putSharedVar(k, val);
//                LogKit.info("页面模版常量[%s=%s]更新", k, key);
//            }
//        });

    }

}