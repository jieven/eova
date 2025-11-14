/**
 * Copyright (c) 2015-2026 EOVA.CN. All rights reserved.
 * Licensed under the LGPL-3.0 license
 * For authorization, please contact: admin@eova.cn
 */
package cn.eova.service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import cn.eova.tools.x;
import cn.eova.common.Ds;
import cn.eova.common.base.BaseService;
import cn.eova.engine.ExpUtil;
import cn.eova.model.MetaField;
import cn.eova.model.MetaFieldDiy;
import cn.eova.model.MetaObject;
import cn.eova.model.User;
import com.jfinal.kit.Kv;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

/**
 * 元数据服务
 *
 * @author Jieven
 * @date 2013-1-3
 */
public class MetaService extends BaseService {

    /**
     * 获取元数据(对象和字段)
     * @param objectCode
     * @return
     */
    public MetaObject getMeta(String objectCode) {
        MetaObject object = MetaObject.dao.getByCode(objectCode);
        object.setFields(getMetaField(objectCode));
        return object;
    }

    /**
     * 获取元字段
     * @param objectCode
     * @return
     */
    public List<MetaField> getMetaField(String objectCode) {
        return MetaField.dao.queryByObjectCode(objectCode);
    }

    /**
     * 获取元数据个性化
     * @param objectCode 对象
     * @param mode 用途
     * @return
     */
    public List<MetaFieldDiy> getMetaFieldDiy(String objectCode, String mode) {
        return MetaFieldDiy.dao.queryByCache("select * from eova_field_diy where object_code = ? and mode = ? order by num", objectCode, mode);
    }


    /**
     * 根据原对象获取数据
     * @param objectCode
     * @param pk
     * @return
     */
    public Record getDataByMetaObject(String objectCode, String pk) {

        MetaObject object = sm.meta.getMeta(objectCode);

        // 根据主键获取对象
        Record record = null;
        // EOVA系统内部特殊处理 如果是元对象,自动识别根据编码查询
        if (objectCode.equals("eova_object_code") && !x.isNum(pk)) {
            MetaObject mo = MetaObject.dao.getByCode(pk.toString());
            record = new Record().setColumns(mo);
        } else {
            record = Db.use(object.getDs()).findById(object.getView(), object.getPk(), object.buildPkValue(pk));
        }

        return record;
    }

    /**
     * 创建元字段个性化数据
     * @param object
     * @param f
     */
    public void initMetaFieldDiy(String object, MetaField f) {
        String[] modes = {"create", "read", "update", "query"};
        for (String mode : modes) {
            MetaFieldDiy diy = new MetaFieldDiy();
            // 是否自增
            if (f.getBoolean("is_auto") && mode.equals("create")) {
                diy.set("status", 4);
                //	1	正常	eova_field_diy
                //	2	只读	eova_field_diy
                //	3	隐藏	eova_field_diy
                //	4	禁用	eova_field_diy
            } else {
                // TODO EovaMeta 还需重构
                diy.set("status", formStatus(mode, f));
            }
            diy.set("object_code", object);
            diy.set("mode", mode);
            diy.set("en", f.getEn());
            diy.set("cn", f.getCn());
            diy.set("num", f.getInt("num"));
            diy.set("defaulter", f.getStr("defaulter"));
            diy.set("height", f.getStr("height"));
            diy.save();
        }
    }

    /**
     * 新增元字段个性化数据
     * @param object 对象编码
     * @param fieldName 字段名
     */
    public void addMetaFieldDiy(String object, String fieldName) {
        String[] modes = {"create", "read", "update", "query"};
        for (String mode : modes) {
            MetaFieldDiy diy = new MetaFieldDiy();
            diy.set("mode", mode);
            diy.set("object_code", object);
            diy.set("en", fieldName);
            //diy.set("status", 1);
            //diy.set("num", 0);
            //diy.set("defaulter", f.getStr("defaulter"));
            //diy.set("height", f.getStr("height"));
            diy.save();
        }
    }

    /**
     * 删除所有元字段
     * @param object 对象编码
     */
    public void deleteMetaField(String object) {
        // 删除元字段
        Db.use(Ds.EOVA).delete("delete from eova_field where object_code = ?", object);
        // 删除个性化
        Db.use(Ds.EOVA).delete("delete from eova_field_diy where object_code = ?", object);
        // 删除字典表达式
        Db.use(Ds.EOVA).delete("delete from eova_option where code like ?", String.format("dict_%s_%%", object));
    }

    /**
     * 删除单个元字段
     * @param object 对象编码
     */
    public void deleteMetaField(String object, String fieldName) {
        // 删除元字段
        Db.use(Ds.EOVA).delete("delete from eova_field where object_code = ? and en = ?", object, fieldName);
        // 删除个性化
        deleteMetaFieldDiy(object, fieldName);
        // 删除字段对应的字典表达式(自动)
        Db.use(Ds.EOVA).delete("delete from eova_option where code like ?", String.format("dict_%s_%s%%", object, fieldName));
    }

    /**
     * 删除元字段个性化数据
     * @param object 对象编码
     * @param fieldName 字段名
     */
    public void deleteMetaFieldDiy(String object, String fieldName) {
        Db.use(Ds.EOVA).delete("delete from eova_field_diy where object_code = ? and en = ?", object, fieldName);
    }

    private int formStatus(String mode, MetaField f) {
        int status = 0;
        if (mode.equals("create")) {
            status = f.get("add_status", 0);
        } else {
            // 其他都按修改状态来更新.
            status = f.get("update_status", 0);
        }

        //	0	正常	eova_field	status
        //	10	只读	eova_field	statusa
        //	20	隐藏	eova_field	statusa
        //	50	禁用	eova_field	statusa

        //	1	正常	eova_field_diy
        //	2	只读	eova_field_diy
        //	3	隐藏	eova_field_diy
        //	4	禁用	eova_field_diy

        switch (status) {
            case 0:
                return 1;
            case 10:
                return 2;
            case 20:
                return 3;
            case 50:
                return 4;
            default:
                return 1;
        }
    }

    /**
     * 更新字段宽度
     * @param object
     * @param fieldName
     * @param width
     */
    public void upodateFieldWdith(String object, String fieldName, int width) {
        Db.use(Ds.EOVA).delete("update eova_field set width = ? where object_code = ? and en = ? ", width, object, fieldName);
    }


    /**
     * 构建前端Form所需数据
     * @param user
     */
    public void buildMetaFieldDiy(MetaFieldDiy f, User user) {
        // 元字段默认值的处理
        String def = f.getStr("defaulter");
        if (!x.isEmpty(def)) {
            if (def.equalsIgnoreCase("now()") || def.equalsIgnoreCase("NOW") || def.equalsIgnoreCase("CURRENT_TIMESTAMP") || def.equalsIgnoreCase("SYSDATE") || def.startsWith("0000-")) {
                f.set("defaulter", new Date(x.time.now()));
            } else if (def.equalsIgnoreCase("UUID")) {
                f.set("defaulter", UUID.randomUUID().toString().replaceAll("-", "").toUpperCase());
            } else {
                f.set("defaulter", ExpUtil.parse(f.getStr("defaulter"), Kv.of("user", user)));
            }
        }
    }

    /**
     * 构建前端Form所需数据
     * @param user
     * @param mfs
     */
//    public void buildMetaField(List<MetaField> mfs, User user) {
//        for (MetaField f : mfs) {
//            String en = f.getStr("en");
//
//            // 元字段默认值的处理
//            String def = f.getStr("defaulter");
//            if (!x.isEmpty(def)) {
//                if (def.equalsIgnoreCase("now()") || def.equalsIgnoreCase("NOW") || def.equalsIgnoreCase("CURRENT_TIMESTAMP") || def.equalsIgnoreCase("SYSDATE") || def.startsWith("0000-")) {
//                    f.set("defaulter", TimestampUtil.getNow());
//                } else if (def.equalsIgnoreCase("UUID")) {
//                    f.set("defaulter", UUID.randomUUID().toString().replaceAll("-", "").toUpperCase());
//                } else {
//                    f.set("defaulter", ExpUtil.parse(f.getStr("defaulter"), Kv.of("user", user)));
//                }
//            }
////            buildMetaField(objectCode, user, f);
//        }
//    }
}