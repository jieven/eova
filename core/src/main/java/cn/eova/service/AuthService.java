/**
 * Copyright (c) 2015-2026 EOVA.CN. All rights reserved.
 * Licensed under the LGPL-3.0 license
 * For authorization, please contact: admin@eova.cn
 */
package cn.eova.service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import cn.eova.tools.x;
import cn.eova.common.Ds;
import cn.eova.common.base.BaseCache;
import cn.eova.common.base.BaseService;
import cn.eova.common.utils.data.CloneUtil;
import cn.eova.config.EovaConst;
import cn.eova.model.Button;
import cn.eova.model.Menu;
import cn.eova.model.RoleBtn;
import cn.eova.widget.WidgetUtil;
import com.jfinal.plugin.activerecord.Db;

/**
 * 权限数据访问
 *
 * @author Jieven
 *
 */
public class AuthService extends BaseService {

    /**
     * 根据角色ID获取已授权查询的菜单Code
     *
     * @param rid 角色ID
     * @return
     */
    public List<String> queryMenuCodeByRid(int rid) {
        String sql = "select DISTINCT(b.menu_code) from eova_role_btn rf LEFT JOIN eova_button b on rf.bid = b.id where b.ui = 'query' and rf.rid = ?";
        return Db.use(Ds.EOVA).query(sql, rid);
    }

    /**
     * 查询某棵树下是否存在已授权的功能
     *
     * @param parentId 父节点ID
     * @param rid　角色ID
     * @return
     */
    public boolean isExistsAuthByPidRid(int parentId, int rid) {
        // 根据角色ID获取已授权查询的菜单Code
        List<String> menuCodes = queryMenuCodeByRid(rid);
        LinkedHashMap<Integer, Menu> result = (LinkedHashMap<Integer, Menu>) getByParentId(parentId);
        for (String menuCode : menuCodes) {
            for (Map.Entry<Integer, Menu> map : result.entrySet()) {
                if (map.getValue().getStr("code").equals(menuCode)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 递归获取所有父子数据
     *
     * @param parentId 父节点ID
     * @return List
     */
    public List<Menu> queryMenuByParentId(int parentId) {
        LinkedHashMap<Integer, Menu> result = (LinkedHashMap<Integer, Menu>) getByParentId(parentId);
        List<Menu> menus = new ArrayList<Menu>();
        for (Map.Entry<Integer, Menu> map : result.entrySet()) {
            menus.add(map.getValue());
        }
        return menus;
    }

    /**
     * 递归获取所有父子数据
     * @param parentId 父节点
     * @return Map
     */
    public Map<Integer, Menu> getByParentId(int parentId) {
        LinkedHashMap<Integer, Menu> temp = null;

        // 取Cache，没有再查库
        Object obj = BaseCache.getSer(EovaConst.ALL_MENU);
        if (x.isEmpty(obj)) {
            // 获取所有菜单信息 TODO where is_hide = 0 隐藏的菜单也可以授权:需求背景,某菜单作为级联功能,不在Left Tree上显示,但是需要授权
            String sql = "select * from eova_menu order by parent_id,num";
            List<Menu> records = Menu.dao.find(sql);

            temp = WidgetUtil.menusToLinkedMap(records);

            // Cache 转换后的菜单信息
            BaseCache.putSer(EovaConst.ALL_MENU, temp);
        } else {
            temp = new LinkedHashMap<Integer, Menu>();
            temp.putAll((LinkedHashMap<Integer, Menu>) obj);
        }

        // 获取某节点下所有数据
        LinkedHashMap<Integer, Menu> result = new LinkedHashMap<Integer, Menu>();
        // 递归获取子节点
        getChildren(temp, parentId, result);

        return CloneUtil.clone(result);
    }

    /**
     * 递归查找子节点
     *
     * @param all 所有菜单
     * @param parentId 父节点ID
     * @param result 节点下所有数据(包括父)
     */
    private void getChildren(Map<Integer, Menu> all, int parentId, Map<Integer, Menu> result) {
        for (Map.Entry<Integer, Menu> map : all.entrySet()) {
            // 获取父节点
            if (map.getKey() == parentId) {
                result.put(map.getKey(), map.getValue());
            }
            // 获取子节点
            if (map.getValue().getInt("parent_id") == parentId) {
                result.put(map.getKey(), map.getValue());
                // 子ID递归找孙节点
                getChildren(all, map.getKey(), result);
            }
        }
    }

    /**
     * 授权菜单功能给超管
     * @param menuCode
     */
    public void authMenuToAdmin(String menuCode) {
        // 获取菜单按钮
        List<Button> btns = Button.dao.find("select id from eova_button where menu_code = ?", menuCode);
        btns.forEach(x -> {
            RoleBtn rb = new RoleBtn();
            rb.set("rid", EovaConst.ADMIN_RID);
            rb.set("bid", x.get("id"));
            rb.save();
        });

    }
}