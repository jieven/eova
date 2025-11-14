/**
 * Copyright (c) 2015-2026 EOVA.CN. All rights reserved.
 * Licensed under the LGPL-3.0 license
 * For authorization, please contact: admin@eova.cn
 */
package cn.eova.handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.regex.Pattern;

import com.jfinal.handler.Handler;
import com.jfinal.kit.HandlerKit;
import com.jfinal.kit.StrKit;

/**
 * Ban掉禁止访问的资源
 *
 *
 * @创建者：Jieven
 * @创建时间：2017-2-20 下午3:14:06
 */
public class UrlBanHandler extends Handler {

    private Pattern skipedUrlPattern;

    public UrlBanHandler(String skipedUrlRegx, boolean isCaseSensitive) {
        if (StrKit.isBlank(skipedUrlRegx))
            throw new IllegalArgumentException("The para excludedUrlRegx can not be blank.");
        skipedUrlPattern = isCaseSensitive ? Pattern.compile(skipedUrlRegx) : Pattern.compile(skipedUrlRegx, Pattern.CASE_INSENSITIVE);
    }

    public void handle(String target, HttpServletRequest request, HttpServletResponse response, boolean[] isHandled) {
        if (skipedUrlPattern.matcher(target).matches()) {
            System.err.println(skipedUrlPattern + " 文件禁止直接访问, 如果想直接访问静态网页,请改成 .htm 格式!");
            HandlerKit.renderError404(request, response, isHandled);
            return;
        } else {
            next.handle(target, request, response, isHandled);
        }
    }
}