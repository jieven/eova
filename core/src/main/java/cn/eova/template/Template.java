/**
 * Copyright (c) 2015-2026 EOVA.CN. All rights reserved.
 * Licensed under the LGPL-3.0 license
 * For authorization, please contact: admin@eova.cn
 */
package cn.eova.template;

import java.util.List;
import java.util.Map;

import cn.eova.model.Button;

/**
 * Eova 业务模版接口
 *
 * @author Jieven
 *
 */
public interface Template {
    /**
     * 模版名称
     *
     * @return
     */
    String name();

    /**
     * 模版编码
     *
     * @return
     */
    String code();

    /**
     * 模版按钮组
     * @return
     */
    Map<Integer, List<Button>> getBtnMap();

}