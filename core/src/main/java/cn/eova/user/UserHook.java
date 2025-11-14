package cn.eova.user;

import cn.eova.tools.x;
import cn.eova.aop.AopContext;
import cn.eova.common.Ds;
import cn.eova.common.utils.EncryptUtil;
import cn.eova.engine.SqlCondition;
import cn.eova.hook.EovaMetaHook;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

public class UserHook implements EovaMetaHook {

    @Override
    public String invoke(Action action, AopContext ac) throws Exception {
        switch (action) {
            case ADD_BEFORE:
                return addBefore(ac);
            case QUERY_BEFORE:
                return queryBefore(ac);
        }
        return null;
    }

    public String queryBefore(AopContext ac) throws Exception {
        // 重写部门查询, 自动当前部门和下级部门
        Integer orgId = ac.ctrl.getInt("org_id");
        if (!x.isEmpty(orgId)) {
            Record org = Db.use(Ds.EOVA).findById("eova_org", orgId);
            String code = org.getStr("code");
            ac.setCondition("org_id", new SqlCondition("and org_id = ? or org_id in (select id from eova_org where code like '" + code + "%')", orgId));
        }
        return null;
    }

    public String addBefore(AopContext ac) throws Exception {
        // 数据服务端校验
        String loginId = ac.record.getStr("login_id");
        Long num = Db.use(Ds.EOVA).queryLong("select count(*) from eova_user where login_id = ?", loginId);
        if (num > 0) {
            return "帐号重复,请重新填写!";
        }

        // 新增时密码加密储存
        String str = ac.record.getStr("login_pwd");
        // 加密方式可配置
        String encrypt = x.conf.get("eova.pwd.encrypt", "SM32");
        if (encrypt.equals("SM32")) {
            str = EncryptUtil.getSM32(str);
        } else {
            str = EncryptUtil.getMd5(str);
        }
        ac.record.set("login_pwd", str);

        return null;
    }


}

