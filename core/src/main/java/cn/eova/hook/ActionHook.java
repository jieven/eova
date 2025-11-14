/**
 * Copyright (c) 2015-2026 EOVA.CN. All rights reserved.
 * Licensed under the LGPL-3.0 license
 * For authorization, please contact: admin@eova.cn
 */
package cn.eova.hook;

import com.jfinal.core.Controller;
import com.jfinal.kit.Kv;

/**
 * 控制器业务钩子
 *
 * @author Jieven
 */
public interface ActionHook extends Hook {

    void invoke(Controller ctrl, Kv kv) throws Exception;

}
