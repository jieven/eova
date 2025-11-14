package cn.eova.config;

import cn.eova.tools.x;
import cn.eova.aop.AopContext;
import cn.eova.common.Ds;
import cn.eova.hook.EovaMetaHook;
import com.jfinal.kit.LogKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

public class MetaConfigHook implements EovaMetaHook {

    @Override
    public String invoke(Action action, AopContext ac) throws Exception {
        switch (action) {
            case ADD_SUCCEED:
                return addSucceed(ac);
            case UPDATE_SUCCEED:
                return updateSucceed(ac);
            case EDIT_BEFORE:
                return editBefore(ac);
        }
        return null;
    }

    public String addSucceed(AopContext ac) throws Exception {
        reload(ac.record);
        return null;
    }

    public String updateSucceed(AopContext ac) throws Exception {
        reload(ac.record);
        return null;
    }

    // 修改后更新配置缓存
    public String editBefore(AopContext ac) throws Exception {
        String pk = ac.record.getStr("pk");
        Record e = Db.use(Ds.EOVA).findById("eova_config", pk);
        reload(e);
        return null;
    }

    public void reload(Record e) {
        String key = e.getStr("code");
        // 测试值 || 默认值
        String val = e.get("test", e.getStr("value"));

        LogKit.info("更新配置文件[%s=%s]", key, val);
        x.conf.addConfig(key, val);

//        // 页面模版常量 动态更新配置
//        EovaConst.getPageConst().forEach((k, v) -> {
//            if (v.equalsIgnoreCase(key)) {
//                EovaConfig.putSharedVar(k, val);
//                LogKit.info("页面模版常量[%s=%s]更新", k, key);
//            }
//        });

    }

}

