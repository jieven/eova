/**
 * Copyright (c) 2015-2026 EOVA.CN. All rights reserved.
 * Licensed under the LGPL-3.0 license
 * For authorization, please contact: admin@eova.cn
 */
package cn.eova.hook;

import com.jfinal.kit.Kv;

/**
 * 通用钩子
 *
 * @author Jieven
 */
public interface BizHook extends Hook {

    void invoke(Kv kv) throws Exception;

}
