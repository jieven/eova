/**
 * Copyright (c) 2015-2026 EOVA.CN. All rights reserved.
 * Licensed under the LGPL-3.0 license
 * For authorization, please contact: admin@eova.cn
 */
package cn.eova.model;

import java.util.List;

import cn.eova.common.Ds;
import cn.eova.common.base.BaseModel;
import cn.eova.core.menu.config.MenuConfig;
import cn.eova.template.common.config.TemplateConfig;
import cn.eova.tools.x;
import com.alibaba.fastjson.JSON;
import com.jfinal.json.Json;
import com.jfinal.kit.Kv;
import com.jfinal.plugin.activerecord.Db;

public class Menu extends BaseModel<Menu> {

    private static final long serialVersionUID = 7072369370299999169L;

    /** 菜单类型-应用 **/
    public static final String TYPE_APP = "app";
    /** 菜单类型-目录 **/
    public static final String TYPE_DIR = "dir";
    /** 菜单类型-URL **/
    public static final String TYPE_DIY = "diy";
    /** 菜单类型-Open **/
    public static final String TYPE_OPEN = "open";

    public static final Menu dao = new Menu();

    private List<Menu> childList;

    public List<Menu> getChildList() {
        return childList;
    }

    public void setChildList(List<Menu> childList) {
        this.childList = childList;
    }

    public String getBizIntercept() {
        return this.getStr("biz_intercept");
    }

    public String getTemplate() {
        return this.getStr("template");
    }

    /**
     * 旧版配置, 即将废弃
     * @return
     */
    @Deprecated
    public MenuConfig getConfig() {
        String json = this.getStr("config");
        if (x.isEmpty(json)) {
            return null;
        }
        return new MenuConfig(json);
    }

    /**
     * 菜单配置 JSON => Map
     * @return
     */
    public Kv getMenuConfig() {
        String json = this.getStr("config");
        if (x.isEmpty(json)) {
            return null;
        }

        return Json.getJson().parse(json, Kv.class);
    }

    public String getConf() {
        return this.getStr("config");
    }

    @Deprecated
    public void setConfig(MenuConfig config) {
        this.set("config", JSON.toJSONString(config));
    }

    /**
     * 获取访问URL
     */
    public String getUrl() {

        String type = this.getStr("type");
        if (type.equals(Menu.TYPE_DIR))
            return "";

        if (type.equals(TYPE_DIY) || type.equals(TYPE_OPEN))
            return this.getStr("url");

        // EovaMeta 新模版规则
        String template = this.getStr("template");
        if (!x.isEmpty(template)) {
            return String.format("/app/%s", this.getStr("code"));
        }

        return '/' + type + "/list/" + this.getStr("code");
    }

    public Menu findByCode(String code) {
        if (code == null) {
            return null;
        }
        String sql = "select * from eova_menu where code = ?";
        return Menu.dao.queryFisrtByCache(sql, code);
    }

    /**
     * 获取根节点
     *
     * @return
     */
    public List<Menu> queryRoot() {
        return super.queryByCache("select * from eova_menu where parent_id = 0 order by num");
    }

    /**
     * 获取所有可见菜单
     *
     * @return
     */
    public List<Menu> queryMenu() {
        // TODO MSSQL open为关键字需要替换成,比如 is_open,然后在外面启用兼容转换代码
        String sql = "select * from eova_menu where is_hide = 0 order by parent_id,num";
        List<Menu> list = super.queryByCache(sql);
        for (Menu m : list) {
            // 为JSON构造 展开参数 方便页面判断
            m.put("is_expand", buildExpand(m));
            // 去除前端无用字段
            m.remove("config", "diy_js", "biz_intercept", "filter");
            // 构建默认短名称
            if (x.isEmpty(m.getStr("short_name"))) {
                m.put("short_name", m.getStr("name").substring(0, 2));
            }
        }
        return list;
    }

    /**
     * 是否展开目录
     * @return
     */
    private boolean buildExpand(Menu m) {
        if (m.getStr("type").equals(TemplateConfig.DIR)) {
            // TODO open 为MSSQL敏感词, 后期替换
            String s = m.get("open").toString();
            if (s.equals("1") || s.equalsIgnoreCase("true")) {
                // 兼容不同DB值
                return true;
            }
        }
        return false;
    }

    /**
     * 是否父节点
     * @param id
     * @return
     */
    public boolean isParent(int id) {
        String sql = "select count(*) from eova_menu where parent_id = ?";
        return Menu.dao.isExist(sql, id);
    }

    /**
     * 删除菜单 且级联删除 按钮和 按钮权限
     * @param code
     */
    public void deleteByCode(String code) {
        // 删除菜单
        Db.use(Ds.EOVA).delete("delete from eova_menu where code = ?", code);

        // 删除菜单按钮关联权限
        RoleBtn.dao.deleteByMenuCode(code);

        // 删除菜单关联按钮
        Button.dao.deleteByMenuCode(code);
    }
}