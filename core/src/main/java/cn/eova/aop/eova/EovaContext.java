/**
 * Copyright (c) 2015-2026 EOVA.CN. All rights reserved.
 * Licensed under the LGPL-3.0 license
 * For authorization, please contact: admin@eova.cn
 */
package cn.eova.aop.eova;

import cn.eova.aop.EovaAopContext;
import cn.eova.engine.EovaExp;
import cn.eova.model.Menu;
import cn.eova.model.MetaObject;
import com.jfinal.core.Controller;

/**
 * Eova全局业务拦截器上下文
 *
 * @author Jieven
 * @date 2014-8-29
 */
public class EovaContext extends EovaAopContext {

    /**
     * 当前菜单
     */
    public Menu menu;

    /**
     * 当前元对象
     * 元字段=object.fields
     *
     */
    public MetaObject object;

    /**
     * 当前操作表达式
     */
    public EovaExp exp;

    public EovaContext(Controller ctrl) {
        super(ctrl);
    }

}