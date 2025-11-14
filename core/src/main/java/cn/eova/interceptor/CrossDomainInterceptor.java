package cn.eova.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;

public class CrossDomainInterceptor implements Interceptor {

    @Override
    public void intercept(Invocation inv) {
        HttpServletRequest request = inv.getController().getRequest();
        HttpServletResponse response = inv.getController().getResponse();
 
        // 允许跨域的域名，*代表允许任何域名
        response.setHeader("Access-Control-Allow-Origin", "*");
        // 允许的方法
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        // 允许的头信息字段
        response.setHeader("Access-Control-Allow-Headers", "x-requested-with, content-type");
        // 允许
        response.setHeader("Access-Control-Allow-Credentials", "true");

        inv.invoke();
    }
}