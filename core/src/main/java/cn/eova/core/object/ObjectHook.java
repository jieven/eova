package cn.eova.core.object;

import cn.eova.aop.AopContext;
import cn.eova.hook.EovaMetaHook;
import cn.eova.service.sm;

public class ObjectHook implements EovaMetaHook {

    @Override
    public String invoke(Action action, AopContext ac) throws Exception {
        switch (action) {
            case DELETE_BEFORE:
                deleteBefore(ac);
                break;
        }
        return null;
    }

    public String deleteBefore(AopContext ac) throws Exception {
        Integer id = ac.record.getInt("id");
        String code = ac.record.getStr("code");
        // 删除对象关联元字段属性
//        MetaField.dao.deleteByObjectId(id);
//        MetaFieldDiy.dao.deleteByObjectCode(code);
        sm.meta.deleteMetaField(code);

        // 删除对象关联的所有字典 慎重，会导致误删同表字段
        // String ds = ac.record.getStr("data_source");
        // String table = ac.record.getStr("table_name");
        // String dict = EovaConfig.getProps().get("main_dict_table");
        // Db.use(ds).update("delete from " + dict + " where object = ?", table);

        return null;
    }

}

