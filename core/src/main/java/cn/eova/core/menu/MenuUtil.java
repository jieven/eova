/**
 * Copyright (c) 2015-2026 EOVA.CN. All rights reserved.
 * <p>
 *
 * Licensed under the LGPL-3.0 license
 * Software copyright registration number:2018SR1012969
 * For authorization, please contact: admin@eova.cn
 */
package cn.eova.core.menu;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import cn.eova.tools.x;
import cn.eova.i18n.I18NBuilder;
import cn.eova.model.Button;
import cn.eova.model.Menu;
import cn.eova.model.User;

public class MenuUtil {

    /**
     * 构建当前用户可用菜单数据集
     * @return
     */
    public static List<Menu> buildMenu(User user) {

        // 获取所有菜单
        List<Menu> menus = Menu.dao.queryMenu();

        // 获取已授权菜单ID
        List<Integer> ids = Button.dao.queryMenuIdByRid(user.getRid());
        if (x.isEmpty(ids)) {
            return null;
        }

        // 递归查找已授权功能的上级节点
        HashSet<Integer> authPid = new HashSet<Integer>();
        for (Integer id : ids) {
            Menu m = getParent(menus, id);
            findParent(authPid, menus, m);
        }

        Iterator<Menu> it = menus.iterator();
        while (it.hasNext()) {
            Menu m = it.next();
            m.put("link", m.getUrl());
            m.remove("url");

            Integer mid = m.getInt("id");

            // 已授权目录
            if (x.isContains(authPid, mid))
                continue;

            if (!x.isContains(ids, mid)) {
                it.remove();
            }
        }

        I18NBuilder.models(menus, "name");
        return menus;
    }

    /**
     * 获取父菜单
     *
     * @param menus
     * @param id
     * @return
     */
    private static Menu getParent(List<Menu> menus, int id) {
        for (Menu m : menus) {
            if (m.getInt("id") == id) {
                return m;
            }
        }
        return null;
    }

    /**
     *
     * 递归向上查找父节点
     *
     * @param authPid 找到的父节点
     * @param menus 所有菜单
     * @param m
     */
    private static void findParent(HashSet<Integer> authPid, List<Menu> menus, Menu m) {
        if (m == null) {
            return;
        }
        Integer pid = m.getInt("parent_id");
        if (pid == 0) {
            return;
        }
        authPid.add(pid);

        Menu p = getParent(menus, pid);
        findParent(authPid, menus, p);
    }

}