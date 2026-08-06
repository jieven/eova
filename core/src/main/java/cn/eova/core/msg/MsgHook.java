package cn.eova.core.msg;

import cn.eova.aop.AopContext;
import cn.eova.engine.SqlCondition;
import cn.eova.hook.EovaMetaHook;

public class MsgHook implements EovaMetaHook {

    @Override
    public String invoke(Action action, AopContext ac) throws Exception {
        switch (action) {

            case QUERY_BEFORE:
                queryBefore(ac);
                break;

        }
        return null;
    }

    public String queryBefore(AopContext ac) throws Exception {
        // 2=查全部
        int status = ac.ctrl.getInt("status", 2);
        if (status == 2) {
            ac.setCondition("status", new SqlCondition("and status >= 0"));
        }

        return null;
    }

}

