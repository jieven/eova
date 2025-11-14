/**
 * Copyright (c) 2015-2026 EOVA.CN. All rights reserved.
 * Licensed under the LGPL-3.0 license
 * For authorization, please contact: admin@eova.cn
 */
package cn.eova.common.base;

import cn.eova.tools.kit.HtmlKit;
import cn.eova.tools.x;
import com.jfinal.kit.Kv;

import static cn.eova.plugin.config.EovaConfigPlugin.UI_CONF_KEYS;

/**
 * 基础模版共享方法
 * #(conf('ui.include'))
 *
 * @author Jieven
 */
public class BaseSharedMethod {

    public String getUIConf() {
        Kv kv = Kv.create();
        UI_CONF_KEYS.forEach(k -> {
            kv.set(k, x.conf.get(k));
        });
        return kv.toJson();
    }

    public String conf(String key) {
        return x.conf.get(key);
    }

    public String conf(String key, String defaultValue) {
        return x.conf.get(key, defaultValue);
    }

    public String ws(String domain) {
        return "ws://" + x.conf.get(domain);
    }

    public String wss(String domain) {
        return "wss://" + x.conf.get(domain);
    }

    public String htt(String domain) {
        return "//" + x.conf.get(domain);
    }

    public String http(String domain) {
        return "http://" + x.conf.get(domain);
    }

    public String https(String domain) {
        return "https://" + x.conf.get(domain);
    }

    public String dir(String dirName) {
        return x.conf.get("dir.static") + x.conf.get("dir." + dirName);
    }

    //	 非法内容过滤,适用于纯文本输出
    public String xss(String s) {
        return HtmlKit.XSSEncode(s);
    }

    // html内容转码
    public String html(String s) {
        return HtmlKit.HTMLEncode(s);
    }
}