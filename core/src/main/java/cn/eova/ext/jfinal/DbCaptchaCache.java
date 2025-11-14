/**
 * Copyright (c) 2015-2026 EOVA.CN. All rights reserved.
 * Licensed under the LGPL-3.0 license
 * For authorization, please contact: admin@eova.cn
 */
package cn.eova.ext.jfinal;

import cn.eova.common.Ds;
import cn.eova.config.EovaConst;
import com.jfinal.captcha.Captcha;
import com.jfinal.captcha.ICaptchaCache;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

/**
 * <pre>
 * 分布式验证码(eova.eova_cache)
 * 支持集群部署多实例
 * 性能优越:基于DB内存表
 * 无第三方依赖
 * 自动回收,无内存泄漏
 * </pre>
 */
public class DbCaptchaCache implements ICaptchaCache {

    private static int BIZ = 1;

    @Override
    public void put(Captcha captcha) {
        // 验证码Key有可能重复
        String key = captcha.getKey();

        // 保证每次都生成新鲜的验证码
        remove(captcha.getKey());

        Record e = new Record();
        e.set("biz", BIZ);
        e.set("id", key);
        e.set("val", captcha.getValue());
        e.set("expire", captcha.getExpireAt());
        Db.use(Ds.EOVA).save(EovaConst.EOVA_CACHE, e);
    }

    @Override
    public Captcha get(String key) {

        // 取最新的有效验证码
        Record e = Db.use(Ds.EOVA).findFirst(String.format("select * from %s where biz = ? and id = ? order by expire desc", EovaConst.EOVA_CACHE), BIZ, key);
        if (e == null) {
            return null;
        }

        // 获取成功后 回收过期验证码
        long t = System.currentTimeMillis();
        Db.use(Ds.EOVA).delete(String.format("delete from %s where biz = ? and expire < ?", EovaConst.EOVA_CACHE), BIZ, t);

        return new Captcha(e.getStr("id"), e.getStr("val"));
    }

    @Override
    public void remove(String key) {
        Db.use(Ds.EOVA).delete(String.format("delete from %s where id = ?", EovaConst.EOVA_CACHE), key);
    }

    @Override
    public void removeAll() {
        Db.use(Ds.EOVA).delete(String.format("delete from %s where biz = ?", EovaConst.EOVA_CACHE), BIZ);
    }

}