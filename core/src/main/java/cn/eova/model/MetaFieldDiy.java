/**
 * Copyright (c) 2015-2026 EOVA.CN. All rights reserved.
 * Licensed under the LGPL-3.0 license
 * For authorization, please contact: admin@eova.cn
 */
package cn.eova.model;

import cn.eova.common.Ds;
import cn.eova.common.base.BaseModel;
import com.jfinal.plugin.activerecord.Db;

/**
 * 元字段个性化
 *
 * @author Jieven
 *
 */
public class MetaFieldDiy extends BaseModel<MetaFieldDiy> {

    private static final long serialVersionUID = -7381270435240459528L;

    public static final MetaFieldDiy dao = new MetaFieldDiy();

    public void deleteByObjectCode(String code) {
        String sql = "delete from eova_field_diy where object_code = ?";
        Db.use(Ds.EOVA).update(sql, code);
    }
}