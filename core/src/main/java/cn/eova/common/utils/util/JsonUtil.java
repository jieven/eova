/**
 * Copyright (c) 2015-2026 EOVA.CN. All rights reserved.
 * Licensed under the LGPL-3.0 license
 * For authorization, please contact: admin@eova.cn
 */
package cn.eova.common.utils.util;

import cn.eova.common.utils.jfinal.RecordUtil;
import cn.eova.tools.x;
import com.jfinal.json.Json;
import com.jfinal.kit.Kv;

/**
 * 原JsonUtil 相关方法移动到RecordUtil parseArray parseObject
 * @see RecordUtil
 * @author Jieven
 *
 */
public class JsonUtil {
    /**
     * JSON转KV
     * @param json
     * @return
     */
    public static Kv toKv(String json) {
        // 空KV减少异常率
        if (x.isEmpty(json)) {
            return Kv.create();
        }
        return Json.getJson().parse(json, Kv.class);
    }
}