/**
 * Copyright (c) 2015-2026 EOVA.CN. All rights reserved.
 * Licensed under the LGPL-3.0 license
 * For authorization, please contact: admin@eova.cn
 */
package cn.eova.core.type;

import java.text.ParseException;
import java.util.Map;

import cn.eova.model.MetaField;
import com.jfinal.core.converter.TypeConverter;

/**
 * Eova 数据类型转换器(简单背后的代价)
 * <pre>
 * 需求背景:
 * JFinal为了做到极速和简化,没有像传统思路一样采用强类型
 * 由此带来了2个比较讨厌的问题:(3.2已经添加了com.jfinal.core.converter.Converters)来解决类型丢失的问题
 * 1.ORM时因为没有强类型映射,所以只能依赖JDBC类型,如果JDBC类型不准确就会出现类型混乱的问题,而传统ORM都是有映射配置的(鱼与熊掌不可兼得)
 * 2.获取客户端数据时因为没有强类型映射,所以也只能依赖JDBC类型,无法做到精准的Java类型的转换,比如Oracle所有数值类型最后都是BigDecimal
 * 3.Eova处于应用层就更尴尬了,CRUD为了更简单,直接连Model都没有用,所以直接根据元数据来撸值(所以后来加入了同步字段类型)
 *
 * 解决方案:
 * 在不牺牲使用方便,不增加繁重的配置情况下通过部分的约定(按照常规开发习惯),且可自主定制规则的原则下,继续众享丝滑
 * 相当于使用了默认映射配置+部分习惯约定,来实现既不用写映射配置类型又不会大乱
 *
 * 特别注意:
 * 1.如果JDBC类型不稳定,比如Oracle Number,会导致使用时类型出现波动,所以需要开发者主动使用大类型来容纳!
 * 2.精度丢失,因为没有强类型,可能存在精度丢失问题,如果是金融类的可以主动改变规则,一切均是BigDecimal
 * </pre>
 * @author Jieven
 *
 */

public abstract class Convertor {

    /**
     * 数据库类型 <> Java类型
     * @return
     */
    public abstract Map<String, Class> mapping();

    /**
     * 根据元字段转换值类型
     * @param field 元字段
     * @param o 数据
     * @return
     */
    public abstract Object convert(MetaField field, Object o);

    /**
     * 根据Java类型转换值类型
     * @param o 值对象
     * @param type JDBC类型
     * @return
     */
    public abstract Object convertValue(Object o, int type);

    /**
     *
     * 根据DB类型获取Java类型
     * DB类型可能对应多种Java类型, 比如Oracle Number, Date
     * @param field 元字段
     * @return
     */
    public Class getJavaType(MetaField field) {
        String dataType = field.getDataTypeName();
        Class javaType = mapping().get(dataType);
        if (javaType == null)
            throw new RuntimeException(String.format("当前数据类型无法匹配数据库字段类型:%s,请更换其它常用类型或自定义类型转换器", dataType));
        return javaType;
    }

    /**
     * 数据转换规则
     * @param o
     * @param c
     * @return
     * @throws ParseException
     */
    protected Object rule(Object o, Class c) {
        if (o == null) {
            return null;
        }

        try {
            return TypeConverter.me().convert(c, o.toString());
        } catch (Exception e) {
            throw new RuntimeException(String.format("无法将值[%s]转换为[%s]", o, c), e);
        }
    }

}