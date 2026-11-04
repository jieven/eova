package cn.eova.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.eova.tools.x;
import cn.hutool.core.util.URLUtil;
import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;

/**
 * 跨域常见问题：
 * 1.307 Temporary Redirect （Redirect is not allowed for a preflight request）
 * /print/commit/ 必须要以 / 结尾， 307 跳转的本质是 Nginx 的路径匹配规则太死板
 *
 * 2.CORS 规范里对简单请求 / 复杂请求的划分
 * HTML <form> 本来就能跨域提交，因为Form 是显示提交的，不会发送 OPTIONS 请求。
 * 浏览器认可的简单 Content-Type 只有三种[application/x-www-form-urlencoded, multipart/form-data, text/plain]
 * 先发 OPTIONS 问服务器：“这个跨域 JSON 请求你允许吗？”
 * 
 * @author Jieven
 */
public class CrossDomainInterceptor implements Interceptor {

    @Override
    public void intercept(Invocation inv) {
        HttpServletRequest request = inv.getController().getRequest();
        HttpServletResponse response = inv.getController().getResponse();

        // 获取当前请求域名
        String origin = request.getHeader("Origin");
        if (x.isEmpty(origin)) {
            inv.invoke();
            return;
        }

        // 是否白名单允许跨域
        if (isAllowedOrigin(origin)) {
            // 白名单命中时回写具体 Origin（Credentials 模式下不能用 *）
            response.setHeader("Access-Control-Allow-Origin", origin);
            response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
            response.setHeader("Access-Control-Allow-Headers", "x-requested-with, content-type");
            response.setHeader("Access-Control-Allow-Credentials", "true");
            response.setHeader("Vary", "Origin");

            // 预检请求直接结束，避免被登录拦截器 302（浏览器禁止 preflight 跟随重定向）
            if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
                response.setStatus(HttpServletResponse.SC_OK);
                return;
            }
        }

        inv.invoke();
    }

    /**
     * Origin 是否命中 access.domain 配置的主域名白名单（含子域名）
     */
    private boolean isAllowedOrigin(String origin) {
        String domains = x.conf.get("cross.domain");
        if (x.isEmpty(domains)) {
            return false;
        }

        String host = hostOf(origin);
        if (x.isEmpty(host)) {
            return false;
        }
        host = host.toLowerCase();

        for (String domain : domains.split(",")) {
            if (x.isEmpty(domain)) {
                continue;
            }
            domain = domain.trim().toLowerCase();
            if (domain.isEmpty()) {
                continue;
            }
            // 精确匹配主域名，或匹配其子域名
            if (host.equals(domain) || host.endsWith("." + domain)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 从 Origin 取主机名（Hutool → java.net.URL，支持 test_dev 这类带下划线域名）。
     */
    public static String hostOf(String origin) {
        if (x.isEmpty(origin)) {
            return null;
        }
        try {
            String host = URLUtil.url(origin.trim()).getHost();
            return x.isEmpty(host) ? null : host.toLowerCase();
        } catch (Exception e) {
            return null;
        }
    }
}
