/**
 * Copyright (c) 2015-2026 EOVA.CN. All rights reserved.
 * <p>
 *
 * Licensed under the LGPL-3.0 license
 * Software copyright registration number:2018SR1012969
 * For authorization, please contact: admin@eova.cn
 */
package cn.eova.model;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import cn.eova.tools.x;
import com.alibaba.fastjson.JSON;
import cn.eova.common.Ds;
import cn.eova.common.base.BaseModel;
import cn.eova.common.utils.jfinal.RecordUtil;
import cn.eova.common.utils.time.TimestampUtil;
import cn.eova.common.utils.xx;
import cn.eova.config.EovaConst;
import cn.eova.core.meta.ColumnMeta;
import cn.eova.engine.EovaExpBuilder;
import cn.eova.engine.ExpUtil;
import cn.eova.i18n.I18NBuilder;
import com.jfinal.kit.Kv;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

/**
 * 字段属性
 *
 * @author Jieven
 * @date 2014-9-10
 */
public class MetaField extends BaseModel<MetaField> {

    private static final long serialVersionUID = -7381270435240459528L;

    public static final MetaField dao = new MetaField();

    public static final String TYPE_VALUE = "精准框";
    public static final String TYPE_TEXT = "文本框";
    public static final String TYPE_NUM = "数字框";
    public static final String TYPE_COMBO = "下拉框";
    public static final String TYPE_COMBO_TREE = "下拉树";
    public static final String TYPE_FIND = "查找框";
    public static final String TYPE_TIME = "时间框";
    public static final String TYPE_DATE = "日期框";
    public static final String TYPE_TEXTS = "文本域";
    public static final String TYPE_EDIT = "编辑框";
    public static final String TYPE_BOOL = "布尔框";
    public static final String TYPE_RADIO = "单选框";
    public static final String TYPE_IMG = "图片";
    public static final String TYPE_FILE = "文件";
    public static final String TYPE_JSON = "JSON";
    public static final String TYPE_SEARCH = "搜索";


    /** 字段数据类型-字符 **/
    public static final String DATATYPE_STRING = "string";
    /** 字段数据类型-数值 **/
    public static final String DATATYPE_NUMBER = "number";
    /** 字段数据类型-时间 **/
    public static final String DATATYPE_TIME = "time";

    /**个性化表格**/
    private static final int DIY_TABLE = 1;
    /**个性化查询**/
    private static final int DIY_QUERY = 2;

    public MetaField() {
    }

    public MetaField(String en, String cn, String type) {
        this.set("en", en);
        this.set("cn", cn);
        this.set("type", type == null);
    }

    public MetaField(String en, String cn) {
        this(en, cn, TYPE_TEXT);
    }

    public MetaField(String objectCode, ColumnMeta col) {

        // TODO 目前统一全部小写,有待观察全小写会导致什么问题
        //		if (EovaConfig.isLowerCase) {
        //			col.name = col.name.toLowerCase();
        //		}

        this.set("object_code", objectCode);
        this.set("en", col.name);
        this.set("cn", col.remarks);
        this.set("num", col.position * 10);
        this.set("is_required", !col.isNull);
        this.set("data_type", col.dataType);
        this.set("data_type_name", col.dataTypeName);
        this.set("data_size", col.dataSize);
        this.set("data_decimal", col.dataDecimal);
        this.set("defaulter", col.defaultValue);
        this.set("is_auto", col.isAuto);

        this.set("type", getFormType(col));

        // 是否自增
//        if (col.isAuto) {
//            // 自增框新增禁用
//            this.set("add_status", 50);
//            // 自增框编辑隐藏
//            this.set("update_status", 20);
//        }
        // 将注释作为CN,若为空使用EN,比如某些专有名词缩写,QQ,SKU 等
        if (x.isEmpty(this.getCn())) {
            this.set("cn", this.getEn().toUpperCase());
        }
        // 默认值
        String defaulter = this.getStr("defaulter");
        if (x.isEmpty(defaulter)) {
            this.set("defaulter", "");
        } else {
            // 清除Mysql函数,不能作为字符串长传入.如果缺省值应在DB中自动自动执行.
            if (defaulter.indexOf("(") != -1 && defaulter.indexOf(")") != -1) {
                this.set("defaulter", "");
            }
        }
    }

    /**
     * 获取表单类型
     *
     * @return
     */
    public static String getFormType(ColumnMeta col) {
        String t = col.dataTypeName.toLowerCase();
        int s = col.dataSize;
        if (t.contains("time") || t.contains("date")) {
            return TYPE_TIME;
        } else if (s > 100) {
            return TYPE_TEXTS;
        } else if (t.equals("bit")) {
            return TYPE_BOOL;
        } else if (s == 1 && (t.equals("tinyint") || t.equals("char"))) {
            return TYPE_BOOL;
        } else {
            // 默认都是文本框
            return TYPE_TEXT;
        }
    }

    /**
     * Record 封装参数
     * @return
     */
    public Record getConf() {
        String json = this.getStr("config");
        if (!x.isEmpty(json)) {

            // 元字段JSON配置中可支持表达式
            Map<String, String> props = x.conf.getProps();
            json = ExpUtil.parse(json, props);

            return RecordUtil.parseObject(json);
        }
        // 防止取配置Null异常
        return new Record();
    }

    /**
     * Java Bean 封装参数
     * @return
     */
    public MetaFieldConfig getConfig() {
        String json = this.getStr("config");
        return parseConfig(json);
    }

    public static MetaFieldConfig parseConfig(String json) {
        if (x.isEmpty(json)) {
            return null;
        }
        return JSON.parseObject(json, MetaFieldConfig.class);
    }

    public void setConfig(MetaFieldConfig config) {
        this.set("config", JSON.toJSONString(config));
    }

    /**
     * 获取字段中文名
     *
     * @return
     */
    public int getId() {
        return this.getInt("id");
    }

    /**
     * 获取字段英文名
     *
     * @return
     */
    public String getEn() {
        return this.getStr("en");
    }

    /**
     * 获取字段中文名
     *
     * @return
     */
    public String getCn() {
        return this.getStr("cn");
    }

    /**
     * 获取字段中文名
     *
     * @return
     */
    public int getNum() {
        return this.getInt("num");
    }

    /**
     * 是否存在Eova表达式(sql和固定)
     * @return
     */
//	public boolean isEovaExp() {
//		return getExp() != null || getItems() != null;
//	}

    /**
     * 获取Eova表达式
     * @return
     */
    public String getExp() {
        return this.getStr("exp");
    }

    /**
     * 获取固定值域
     * @return
     */
    public List<Record> getItems() {
        return new EovaExpBuilder(getExp()).buildFixedItem();
    }


    /**
     * 获取控件类型
     *
     * @return
     */
    public String getType() {
        return this.getStr("type");
    }

    /**
     * 获取数据类型
     *
     * @return
     */
    public int getDataType() {
        return this.getInt("data_type");
    }

    /**
     * 是否是虚拟字段
     * @return
     */
    public boolean isVirtual() {
        if (x.isEmpty(getStr("table_name"))) {
            return false;
        }
        return getStr("table_name").equals(EovaConst.VIRTUAL);
    }

    public String getDataTypeName() {
        return this.getStr("data_type_name").toUpperCase();
    }

    public int getDataSize() {
        return this.getInt("data_size");
    }

    public int getDataDecimal() {
        return this.getInt("data_decimal");
    }

    // TODO PGSQL 为强类型, 为了兼容性使用INT2, 需要自动识别为Bool后使用

    /**
     * 是否显示字段
     * @return
     */
    public boolean isShow() {
        return xx.isTrue(this.get("is_show"));
    }

    /**
     * 是否多选
     * @return
     */
    public boolean isMultiple() {
        return xx.isTrue(this.get("is_multiple"));
    }

    /**
     * 获取元字段数据模版(用于模拟元数据)
     *
     * @return
     */
    public MetaField getTemplate() {
        MetaField mf = this.queryFisrtByCache("select * from eova_field where id = 1");
        mf.remove("id");
        return mf;
    }

    /**
     * 获取元字段信息
     *
     * @param objectCode 对象Code
     * @return
     */
    public List<MetaField> queryByObjectCode(String objectCode) {
        List<MetaField> list = MetaField.dao.queryByCache("select * from eova_field where object_code = ? order by num", objectCode);
        I18NBuilder.models(list, "cn", "fieldset", "placeholder");
        return list;
    }

    /**
     * 获取字段
     *
     * @param objectCode 对象Code
     * @param en 字段Key
     * @return
     */
    public MetaField getByObjectCodeAndEn(String objectCode, String en) {
        MetaField ei = MetaField.dao.queryFisrtByCache("select * from eova_field where object_code = ? and en = ? order by num", objectCode, en);
        return ei;
    }

    public void deleteByObjectId(int objectId) {
        String sql = "delete from eova_field where object_code = (select code from eova_object where id = ?)";
        Db.use(Ds.EOVA).update(sql, objectId);
    }

    public void deleteByObjectCode(String objectCode) {
        String sql = "delete from eova_field where object_code = ?";
        Db.use(Ds.EOVA).update(sql, objectCode);
    }

    public List<MetaField> queryShowFieldByObjectCode(String objectCode) {
        return MetaField.dao.queryByCache("select * from eova_field where object_code = ? and is_show = 1 order by fieldnum,num", objectCode);
    }

    /**
     * 查询视图包含的持久化对象Code
     *
     * @param objectCode 视图对象编码
     * @return
     */
    public List<MetaField> queryPoCodeByObjectCode(String objectCode) {
        return MetaField.dao.find("SELECT DISTINCT(po_code) from eova_field where object_code = ?", objectCode);
    }

    /**
     * 查询元字段(Eova内部使用)
     *
     * @param objectCode 元对象编码
     * @return
     */
    public List<MetaField> queryFields(String objectCode) {
        return MetaField.dao.queryByObjectCode(objectCode);
    }

    /**
     *
     * 查询元字段(前端直接获取 主要用户表格展示)
     * 敏感信息屏蔽
     * 权限处理
     *
     * @param objectCode
     * @param user
     * @return
     */
    public List<MetaField> queryFields(String objectCode, User user) {
        List<MetaField> mfs = MetaField.dao.queryByObjectCode(objectCode);
        buildTableMetaField(objectCode, user, mfs);
        return mfs;
    }

    /**
     * 分组查询元字段(用于表单分组)
     *
     * @param objectCode
     * @param user
     * @return
     */
    public List<MetaField> queryFieldsGroup(String objectCode, User user) {
        List<MetaField> mfs = MetaField.dao.queryByObjectCode(objectCode);
        buildFormMetaField(objectCode, user, mfs);
        return mfs;
    }

    /**
     * 获取查询元字段(用于查询组件搜索)
     * 查询条件无需进行用权限控制
     *
     * @param objectCode
     * @return
     */
    public List<MetaField> querySearchFields(String objectCode, User user) {
        List<MetaField> mfs = MetaField.dao.queryByObjectCode(objectCode);
        buildQueryMetaField(objectCode, user, mfs);
        return mfs;
    }

    /**
     * 构建前端Table所需数据
     * @param objectCode
     * @param user
     * @param mfs
     */
    private void buildTableMetaField(String objectCode, User user, List<MetaField> mfs) {

        // 用户个性化设置
        List<Record> diyFields = Db.use(Ds.EOVA).find("select * from eova_diy where type = ? and uid = ? and object_code = ?", DIY_TABLE, user.getId(), objectCode);

        for (MetaField f : mfs) {

            buildMetaField(objectCode, user, f);

            // 排除自身
            if (objectCode.startsWith("eova_diy")) {
                continue;
            }
            // 个性化覆盖默认
            for (Record diy : diyFields) {
                if (f.getEn().equals(diy.getStr("en"))) {
                    f.set("num", diy.getInt("num"));
                    f.set("is_show", diy.get("is_open"));
                    f.set("width", diy.getInt("width"));
                }
            }

        }

        fieldSort(mfs);
    }


    /**
     * 构建前端Form所需数据
     * @param objectCode
     * @param mfs
     */
    private void buildQueryMetaField(String objectCode, User user, List<MetaField> mfs) {

        // 用户个性化设置
        List<Record> diyFields = Db.use(Ds.EOVA).find("select * from eova_diy where type = ? and uid = ? and object_code = ?", DIY_QUERY, user.getId(), objectCode);
        for (MetaField f : mfs) {

            // buildMetaField(objectCode, user, f); 不考虑默认值和角色权限

            // 排除自身
            if (objectCode.startsWith("eova_diy")) {
                continue;
            }
            // 个性化覆盖默认
            for (Record diy : diyFields) {
                if (f.getEn().equals(diy.getStr("en"))) {
                    f.set("num", diy.getInt("num"));
                    f.set("is_query", diy.get("is_open"));
                }
            }
        }

        fieldSort(mfs);

    }

    /**
     * 字段重新排序
     * @param mfs
     */
    private void fieldSort(List<MetaField> mfs) {
        Collections.sort(mfs, new Comparator<MetaField>() {
            @Override
            public int compare(MetaField f1, MetaField f2) {
                int n1 = f1.getInt("num");
                int n2 = f2.getInt("num");
                if (n1 > n2) {
                    return 1;
                }
                if (n1 == n2) {
                    return 0;
                }
                return -1;
            }
        });
    }

    /**
     * 构建前端Form所需数据
     * @param objectCode
     * @param user
     * @param mfs
     */
    private void buildFormMetaField(String objectCode, User user, List<MetaField> mfs) {
        for (MetaField f : mfs) {
            String en = f.getStr("en");

            // 元字段默认值的处理
            String def = f.getStr("defaulter");
            if (!x.isEmpty(def)) {
                if (def.equalsIgnoreCase("now()") || def.equalsIgnoreCase("NOW") || def.equalsIgnoreCase("CURRENT_TIMESTAMP") || def.equalsIgnoreCase("SYSDATE") || def.startsWith("0000-")) {
                    f.set("defaulter", TimestampUtil.getNow());
                } else if (def.equalsIgnoreCase("UUID")) {
                    f.set("defaulter", UUID.randomUUID().toString().replaceAll("-", "").toUpperCase());
                } else {
                    f.set("defaulter", ExpUtil.parse(f.getStr("defaulter"), Kv.of("user", user)));
                }
            }

            buildMetaField(objectCode, user, f);

        }
    }

    /**
     * 元数据使用前公共处理
     * @param objectCode
     * @param user
     * @param f
     */
    private void buildMetaField(String objectCode, User user, MetaField f) {
        String en = f.getStr("en");
        String exp = f.getStr("exp");

        if (x.isEmpty(exp)) {
            if (f.getType().equals(MetaField.TYPE_FIND)) {
                throw new RuntimeException(String.format("元对象[%s]中的元字段[%s]是查找框，该字段必须填写Eova表达式！", objectCode, f.getEn()));
            } else if (f.getType().equals(MetaField.TYPE_COMBO)) {
                throw new RuntimeException(String.format("元对象[%s]中的元字段[%s]是下拉框，该字段必须填写Eova表达式！", objectCode, f.getEn()));
            }
        }

        if (user != null) {
            // 未授权字段
            String field = String.format("%s.%s", objectCode, en);
            if (user.getDisableFields().contains(field)) {
                f.set("is_query", false);
                f.set("is_show", false);
                f.set("add_status", 20);
                f.set("update_status", 20);
                // 没权限的按钮,不能禁用,只能隐藏,比如走默认值的字段,禁用会出现其它角色编辑持久化缺字段异常
            }
        }
    }


    /**
     * 更新原字段归属表
     *
     * @param objectCode 对象编码
     * @param en 字段名
     * @param tableName 字段归属表
     * @return
     */
    public int updateTableNameByCode(String objectCode, String en, String tableName) {
        return Db.use(Ds.EOVA).update("update eova_field set table_name = ? where object_code = ? and en = ?", tableName, objectCode, en);
    }

    /**
     * 更改排序
     */
    public void updateOrderNum(String objectCode, String sen, String ten) {
        MetaField t = MetaField.dao.getByObjectCodeAndEn(objectCode, ten);
        Db.use(Ds.EOVA).update("update eova_field set num = ? where object_code = ? and en = ?", t.getInt("num") + 1, objectCode, sen);
    }


}