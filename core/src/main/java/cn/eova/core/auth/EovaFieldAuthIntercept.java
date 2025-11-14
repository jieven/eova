/**
 * Copyright (c) 2015-2026 EOVA.CN. All rights reserved.
 * Licensed under the LGPL-3.0 license
 * For authorization, please contact: admin@eova.cn
 */

package cn.eova.core.auth;

import java.util.List;

import cn.eova.aop.AopContext;
import cn.eova.aop.MetaObjectIntercept;
import cn.eova.common.Ds;
import cn.eova.common.utils.xx;
import cn.eova.config.EovaFieldAuth;
import cn.eova.core.meta.MetaUtil;
import cn.eova.tools.x;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

/**
 * 字段权限拦截
 *
 * @author Jieven
 */
public class EovaFieldAuthIntercept extends MetaObjectIntercept {

    @Override
    public void queryAfter(AopContext ac) throws Exception {
        for (Record r : ac.records) {
            String object = r.getStr("object");
            String fields = r.getStr("field");

            // 翻译字段中文名显示
            String sql = "select cn from eova_field where object_code = ? and en in (";
            for (String s : fields.split(",")) {
                sql += x.str.format(s) + ",";
            }
            sql = x.str.delEnd(sql, ",");
            sql += ")";

            List<String> cns = Db.use(Ds.EOVA).query(sql, object);

            r.set("field", xx.join(cns));
        }
    }

    @Override
    public String addAfter(AopContext ac) throws Exception {
        cleanUserDiy(ac);

        return null;
    }

    @Override
    public String updateAfter(AopContext ac) throws Exception {
        cleanUserDiy(ac);

        return null;
    }

    /**
     * 新增修改规则时, 自动清理用户个性化缓存
     * @param ac
     */
    private void cleanUserDiy(AopContext ac) {
        Integer type = ac.record.getInt("type");
        // 角色黑名单
        String object = ac.record.getStr("object");
        String fields = ac.record.getStr("field");
        String roles = ac.record.getStr("auth");

        MetaUtil.removeRoleDiy(object, fields, roles, type);
    }

    @Override
    public String updateSucceed(AopContext ac) throws Exception {
        EovaFieldAuth.authReload(ac.user.getCompanyId());
        return null;
    }

    @Override
    public String addSucceed(AopContext ac) throws Exception {
        EovaFieldAuth.authReload(ac.user.getCompanyId());
        return null;
    }

    @Override
    public String deleteSucceed(AopContext ac) throws Exception {
        EovaFieldAuth.authReload(ac.user.getCompanyId());
        return null;
    }
}
