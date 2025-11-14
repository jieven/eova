/**
 * Copyright (c) 2015-2026 EOVA.CN. All rights reserved.
 * Licensed under the LGPL-3.0 license
 * For authorization, please contact: admin@eova.cn
 */
package cn.eova.mod.emi;

import com.jfinal.kit.Kv;

/**
 * Eova Mod Invoke
 * 通过反射Mod类执行方法
 * @author Jieven
 */
public interface EMI {

    Kv invoke(Kv kv);

}