/**
 * Copyright (c) 2015-2026 EOVA.CN. All rights reserved.
 * Licensed under the LGPL-3.0 license
 * For authorization, please contact: admin@eova.cn
 */
package cn.eova.core.dict;


import cn.eova.tools.x;
import cn.eova.common.Ds;
import cn.eova.common.Easy;
import cn.eova.common.base.BaseController;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;


/**
 * 字典管理
 * @author Jieven
 *
 */
public class DictController extends BaseController {

    // 复制字典
    public void copy() {
        String id = getSelectValue("id");

        String dictTable = x.conf.get("main_dict_table");

        Record r = Db.use(Ds.MAIN).findById(dictTable, id);
        r.remove("id");

        Db.use(Ds.MAIN).save(dictTable, r);
        renderJson(new Easy());
    }
}