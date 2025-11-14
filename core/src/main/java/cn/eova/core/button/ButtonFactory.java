/**
 * Copyright (c) 2015-2026 EOVA.CN. All rights reserved.
 * Licensed under the LGPL-3.0 license
 * For authorization, please contact: admin@eova.cn
 */
package cn.eova.core.button;

import java.util.List;

import cn.eova.model.Button;

/**
 * 按钮构建工厂
 *
 * @author Jieven
 * @date 2016-11-19
 */
public class ButtonFactory {

    /**
     * 创建模版应用
     */
    public static void create(String menuCode, String template) {
        String code = String.format("eova_template_%s", template);

        // 获取应用功能
        List<Button> list = Button.dao.findByMenuCode(code);
        for (Button btn : list) {
            btn.remove("id");
            btn.set("menu_code", menuCode);
            String bs = btn.getStr("bs");
            // btn.set("bs", formatTemplate(bs, Kv.of("object", config.getObjectCode())));
            // buildAuth(btn); TODO 使用时实时构建, 无需提前配置, 方便动态修改, 例如 动态修改菜单编码, 元对象编码.
            btn.save();
        }
    }

}