/**
 * Copyright (c) 2015-2026 EOVA.CN. All rights reserved.
 * Licensed under the LGPL-3.0 license
 * For authorization, please contact: admin@eova.cn
 */
package cn.eova.mod;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.jfinal.aop.Interceptor;
import com.jfinal.core.Controller;

public class EovaModRoute {

    private HashMap<String, Class<? extends Controller>> routeMap = new HashMap<>();
    private List<Interceptor> interceptors = new ArrayList<>();

    /**
     * 添加路由
     * @param controllerKey
     * @param controllerClass
     */
    public void add(String controllerKey, Class<? extends Controller> controllerClass) {
        routeMap.put(controllerKey, controllerClass);
    }

    /**
     * 添加拦截器
     * @param interceptor
     */
    public void addInterceptor(Interceptor interceptor) {
        interceptors.add(interceptor);
    }

    public HashMap<String, Class<? extends Controller>> getRouteMap() {
        return routeMap;
    }

    public List<Interceptor> getInterceptors() {
        return interceptors;
    }


}
