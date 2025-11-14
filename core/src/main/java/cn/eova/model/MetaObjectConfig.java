/**
 * Copyright (c) 2015-2026 EOVA.CN. All rights reserved.
 * Licensed under the LGPL-3.0 license
 * For authorization, please contact: admin@eova.cn
 */
package cn.eova.model;

/**
 * 元字段配置
 *
 * @author Jieven
 *
 */
public class MetaObjectConfig {

    // 是否进行合计
    private boolean totalRow = false;

    public boolean isTotalRow() {
        return totalRow;
    }

    public void setTotalRow(boolean totalRow) {
        this.totalRow = totalRow;
    }

}