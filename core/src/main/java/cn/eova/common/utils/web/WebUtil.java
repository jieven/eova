/**
 * Copyright (c) 2015-2026 EOVA.CN. All rights reserved.
 * Licensed under the LGPL-3.0 license
 * For authorization, please contact: admin@eova.cn
 */
package cn.eova.common.utils.web;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.eova.tools.x;
import com.jfinal.core.paragetter.JsonRequest;
import com.jfinal.kit.LogKit;

public class WebUtil {

    // \b 是单词边界(连着的两个(字母字符 与 非字母字符) 之间的逻辑上的间隔),
    // 字符串在编译时会被转码一次,所以是 "\\b"
    // \B 是单词内部逻辑间隔(连着的两个字母字符之间的逻辑上的间隔)
    static String phoneReg = "\\b(ip(hone|od)|android|opera m(ob|in)i" + "|windows (phone|ce)|blackberry" + "|s(ymbian|eries60|amsung)|p(laybook|alm|rofile/midp"
            + "|laystation portable)|nokia|fennec|htc[-_]" + "|mobile|up.browser|[1-4][0-9]{2}x[1-4][0-9]{2})\\b";
    static String tableReg = "\\b(ipad|tablet|(Nexus 7)|up.browser" + "|[1-4][0-9]{2}x[1-4][0-9]{2})\\b";

    // 移动设备正则匹配：手机端、平板
    static Pattern phonePat = Pattern.compile(phoneReg, Pattern.CASE_INSENSITIVE);
    static Pattern tablePat = Pattern.compile(tableReg, Pattern.CASE_INSENSITIVE);

    /**
     * 校验浏览器来源是否为手机端
     */
    public static Boolean isMobile(HttpServletRequest request) {
        String ua = getUserAgent(request);
        if (null == ua) {
            return false;
        }
        // 匹配
        Matcher matcherPhone = phonePat.matcher(ua);
        Matcher matcherTable = tablePat.matcher(ua);
        if (matcherPhone.find() || matcherTable.find()) {
            return true;
        } else {
            return false;
        }

    }

    /**
     * 判断一个HttpServletRequest是否为Ajax请求
     *
     * @param request HttpServletRequest对象
     * @return 如果是Ajax请求则返回true，否则返回false
     */
    public static boolean isAjax(HttpServletRequest request) {
        String requestedWithHeader = request.getHeader("X-Requested-With");
        // JSON请求
        if (request instanceof JsonRequest) {
            return true;
        }
        // Ajax请求
        return "XMLHttpRequest".equalsIgnoreCase(requestedWithHeader);
    }

    /**
     * 获取浏览器的用户代理信息
     *
     * @param request
     * @return
     */
    public static String getUserAgent(HttpServletRequest request) {
        String userAgent = request.getHeader("user-agent");
        if (x.isEmpty(userAgent)) {
            userAgent = request.getHeader("http_user_agent");
        }
        return userAgent;
    }

    /**
     * 从请求流中快速读取文件内容，例如JSON，TXT，XML
     *
     * @param charSet
     * @return
     */
    public static String readData(HttpServletRequest request, String charSet) {
        InputStream in = null;
        StringBuilder sb = new StringBuilder();
        try {
            in = request.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in, charSet));
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append("\n");
            }
            return sb.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    LogKit.error(e.getMessage(), e);
                }
            }
        }
    }

    public static String readData(HttpServletRequest request) {
        return readData(request, "UTF-8");
    }

    /**
     * 获取用户端请求的真实IP地址
     *
     * @param request
     * @return
     */
    public static String getRealIp(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        if (ip.indexOf(",") > -1) {
            ip = ip.split(",")[0];
        }
        return ip;
    }
}