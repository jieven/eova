/**
 * Copyright (c) 2015-2026 EOVA.CN. All rights reserved.
 * Licensed under the LGPL-3.0 license
 * For authorization, please contact: admin@eova.cn
 */
package cn.eova.template.single;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.eova.model.Button;
import cn.eova.template.Template;
import cn.eova.template.common.config.TemplateConfig;
import cn.eova.template.common.util.TemplateUtil;

public class SingleTemplate implements Template {

    @Override
    public String name() {
        return "单表";
    }

    @Override
    public String code() {
        return TemplateConfig.SINGLE_GRID;
    }

    @Override
    public Map<Integer, List<Button>> getBtnMap() {
        Map<Integer, List<Button>> btnMap = new HashMap<>();

        {
            List<Button> btns = new ArrayList<>();

            btns.add(TemplateUtil.getQueryButton());
            btns.add(new Button("新增", "/eova/template/single/btn/add.html", false));
            btns.add(new Button("修改", "/eova/template/single/btn/update.html", false));
            btns.add(new Button("彻底删除", "/eova/template/single/btn/delete.html", false));
            btns.add(new Button("查看", "/eova/template/single/btn/detail.html", false));
            btns.add(new Button("导入", "/eova/template/single/btn/import.html", false));
            btns.add(new Button("删除", "/eova/template/single/btn/hide.html", true));

            btnMap.put(0, btns);
        }

        return btnMap;
    }

}