/**
 * Copyright (c) 2015-2026 EOVA.CN. All rights reserved.
 * Licensed under the LGPL-3.0 license
 * For authorization, please contact: admin@eova.cn
 */
package cn.eova.widget.tree;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import cn.eova.tools.x;
import cn.eova.core.menu.config.TreeConfig;
import cn.eova.widget.WidgetUtil;
import com.jfinal.kit.JsonKit;
import com.jfinal.plugin.activerecord.Record;


/**
 * Tree 工具类
 *
 * @author Jieven
 *
 */
public class TreeUtil {

    /**
     * 准备废弃, 推荐 TreeUtil.buildTree()
     * 转化为Tree所需JSON结构
     *
     * @param reList
     * @return
     */
    public static String toTreeJson(List<Record> list, TreeConfig treeConfig) {
        Map<String, Record> map = WidgetUtil.listToLinkedMap(list, treeConfig);
        return map2TreeJson(map, treeConfig);
    }

    public static String map2TreeJson(Map<String, Record> temp, TreeConfig treeConfig) {

        String rootPid = treeConfig.getRootPid();
        //String iconField = treeConfig.getIconField();
        String parentField = treeConfig.getParentField();

        // EasyUI 公共数据格式处理
        //		for (Map.Entry<String, Record> map : temp.entrySet()) {
        //			Record x = map.getValue();
        //			// 是否默认折叠
        //			String state = "open";
        //			Boolean isOpen = x.getBoolean("open");
        //			if (!x.isEmpty(isOpen) && !isOpen) {
        //				state = "closed";
        //			}
        //			x.set("state", state);
        //			x.set("iconCls", x.getStr(iconField));
        //			// 移除冗余属性
        //			// x.remove("open"); 如果要展示呢?
        //			x.remove(iconField);
        //		}

        String childrenField = "nodes";
        Record tree = TreeUtil.buildTree(rootPid, parentField, childrenField, temp);

        // 组装Tree Json
        StringBuilder sb = new StringBuilder("[");
        // 获取根节点
        List<Record> childList = tree.get(childrenField);
        for (Record r : childList) {
            if (!x.isEmpty(r.get(childrenField))) {
                // 父节点默认折叠
                //x.set("state", "closed");
            }
            sb.append(JsonKit.toJson(r));
            sb.append(",");
        }
        sb.deleteCharAt(sb.length() - 1);
        sb.append("]");
        //System.out.println(sb.toString());

        // 大小写与EasyUI兼容问题：
        String json = sb.toString();
        // json = json.replaceAll("iconcls", "iconCls");

        return json;
    }

    /**
     * 将有序Map数据处理为Tree型数据
     * @param rootValue 根节点的值
     * @param parentField 父节点字段
     * @param childrenField 子节点字段
     * @param temp 有序Map
     * @return
     */
    public static Record buildTree(String rootValue, String parentField, String childrenField, Map<String, Record> temp) {
        // 创建根节点，不断往上挂子节点
        temp.put(rootValue, new Record());

        // 将temp整理成Tree结构
        for (Map.Entry<String, Record> map : temp.entrySet()) {

            // 跳过新增的树根
            if (map.getKey().equals(rootValue)) {
                continue;
            }

            Record e = map.getValue();
            String pid = e.get(parentField).toString();
            Record pe = temp.get(pid);
            if (pe == null) {
                // 父节点为空说明已经遍历到最外层父节点，直接跳过
                continue;
            }
            // 获取父的子集
            List<Record> childList = pe.get(childrenField);
            if (childList == null) {
                childList = new ArrayList<Record>();
                temp.get(pid).set(childrenField, childList);
            }
            // 将当前节点放入父的子集
            childList.add(e);
        }

        return temp.get(rootValue);
    }


    /**
     * 数据集合转TreeMap结构
     * @param list
     * @param rootValue
     * @param idField
     * @param parentField
     * @param childrenField
     * @return
     */
    public static Record listToTree(List<Record> list, String rootValue, String idField, String parentField, String childrenField) {
        LinkedHashMap<String, Record> temp = new LinkedHashMap<>();

        // 变换数据结构，方便遍历 list to map
        for (Record r : list) {
            temp.put(r.get(idField).toString(), r);
        }

        return buildTree(rootValue, parentField, childrenField, temp);
    }

}