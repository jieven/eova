/**
 * Copyright (c) 2015-2026 EOVA.CN. All rights reserved.
 * Licensed under the LGPL-3.0 license
 * For authorization, please contact: admin@eova.cn
 */
package cn.eova.handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.eova.common.base.BaseCache;
import cn.eova.common.utils.web.RequestUtil;
import cn.eova.tools.x;
import com.jfinal.handler.Handler;
import com.jfinal.kit.HandlerKit;
import com.jfinal.kit.LogKit;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.ehcache.CacheKit;

/**
 * WAF 安全防护
 * 1.按IP统计404，超阈值后临时封禁10分钟。
 * 2.软封后若持续刷，每 LOG_EVERY 次输出一次日志，便于决定是否网关封 IP。
 */
public class WAFHandler extends Handler {

    /** 404 次数达到该值即软封 */
    public static final int LIMIT = x.conf.getInt("waf.404.limit", 10);
    /** 软封后持续拦截，每隔多少次打一条日志 */
    public static final int LOG_EVERY = x.conf.getInt("waf.404.log", 1000);

    @Override
    public void handle(String target, HttpServletRequest request, HttpServletResponse response, boolean[] isHandled) {
        String ip = RequestUtil.getIp(request);
        if (StrKit.notBlank(ip) && CacheKit.get(BaseCache.WAF_BAN, ip) != null) {
            onBlocked(ip, target);
            // 与正常 404 一致，不暴露限流/封禁信号
            HandlerKit.renderError404(request, response, isHandled);
            return;
        }

        next.handle(target, request, response, isHandled);

        if (response.getStatus() == HttpServletResponse.SC_NOT_FOUND && StrKit.notBlank(ip)) {
            onNotFound(ip, target);
        }
    }

    private void onNotFound(String ip, String target) {
        Integer count = CacheKit.get(BaseCache.WAF_404, ip);
        int next = (count == null ? 0 : count) + 1;
        CacheKit.put(BaseCache.WAF_404, ip, next);

        if (next >= LIMIT) {
            CacheKit.put(BaseCache.WAF_BAN, ip, Boolean.TRUE);
            CacheKit.remove(BaseCache.WAF_404, ip);
            LogKit.warn("WAF softban ip=" + ip);
        }
    }

    /** 已软封：累计拦截次数，每 LOG_EVERY 次提醒一次 */
    private void onBlocked(String ip, String target) {
        Integer count = CacheKit.get(BaseCache.WAF_404, ip);
        int next = (count == null ? 0 : count) + 1;
        CacheKit.put(BaseCache.WAF_404, ip, next);

        if (next % LOG_EVERY == 0) {
            LogKit.warn("WAF 404 scan ip=" + ip + ", scan num=" + next);
        }
    }
}
