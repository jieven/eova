/**
 * Copyright (c) 2015-2026 EOVA.CN. All rights reserved.
 * Licensed under the LGPL-3.0 license
 * For authorization, please contact: admin@eova.cn
 */
package cn.eova.widget.tree;

import java.util.List;

import com.jfinal.plugin.activerecord.Record;

/**
 * Tree Node VO
 *
 * @author Jieven
 * @date 2014-9-8
 */
public class TreeNode extends Record {

    private static final long serialVersionUID = -5190761342805087001L;

    // 子节点
    private List<TreeNode> childs;

    public List<TreeNode> getChildList() {
        return childs;
    }

    public void setChildList(List<TreeNode> childList) {
        this.childs = childList;
    }

}