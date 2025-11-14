/**
 * Copyright (c) 2015-2026 EOVA.CN. All rights reserved.
 * Licensed under the LGPL-3.0 license
 * For authorization, please contact: admin@eova.cn
 */
package cn.eova.common.utils.jfinal;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.eova.tools.x;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Record;

public class RecordUtil {

    /**
     * 从指定Record中剥离出指定字段
     *
     * @param data 原数据集
     * @param columnNames 要剥离的字段名组
     * @return 剥离出的数据集
     */
    public static Record peel(Record data, String... columnNames) {
        Record o = new Record();
        for (String name : columnNames) {
            if (name.contains("->")) {
                String[] ss = name.split("->");
                String source = ss[0].trim();
                String target = ss[1].trim();
                o.set(target, data.get(source));
                // 虚拟字段: Table模式不参与持久化无需移除, View模式也不应使用虚拟字段
                // 存在关系的字段不移除，后续其它表可能还需使用
            } else {
                o.set(name, data.get(name));
                data.remove(name);
            }
        }
        return o;
    }

    /**
     * 从指定Record中剥离出指定字段
     * @param modelClass 类型
     * @param data 原数据集
     * @param columnNames 要剥离的字段名组(默认使用全部数据)
     * @return
     */
    public static <T extends Model> T peelModel(Class<? extends Model> modelClass, Record data, String... columnNames) {
        Model<?> m = null;
        try {
            m = modelClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (x.isEmpty(columnNames)) {
            m._setAttrs(data.getColumns());
        } else {
            Record r = peel(data, columnNames);
            m._setAttrs(r.getColumns());
        }
        return (T) m;
    }

    /**
     * JSON String -> List&lt;Record>
     * @param json
     * @return
     */
    public static List<Record> parseArray(String json) {
        List<Record> records = new ArrayList<Record>();

        List<JSONObject> list = JSON.parseArray(json, JSONObject.class);
        for (JSONObject o : list) {
            Map<String, Object> map = JSON.parseObject(o + "", new TypeReference<Map<String, Object>>() {
            });
            Record e = new Record();
            e.setColumns(map);
            records.add(e);
        }

        return records;
    }

    /**
     * JSON String -> Record
     * @param json
     * @return
     */
    public static Record parseObject(String json) {
        Map<String, Object> map = JSON.parseObject(json, new TypeReference<Map<String, Object>>() {
        });
        Record e = new Record();
        e.setColumns(map);
        return e;
    }
}