/**
 * Copyright (c) 2015-2026 EOVA.CN. All rights reserved.
 * Licensed under the LGPL-3.0 license
 * For authorization, please contact: admin@eova.cn
 */
package cn.eova.core.auth;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import cn.eova.tools.EovaTool;
import cn.eova.tools.x;
import cn.eova.common.Ds;
import cn.eova.common.Easy;
import cn.eova.common.base.BaseController;
import cn.eova.config.EovaConst;
import cn.eova.model.Button;
import cn.eova.model.Menu;
import cn.eova.model.Role;
import cn.eova.model.RoleBtn;
import cn.eova.model.User;
import cn.eova.service.sm;
import cn.eova.widget.WidgetUtil;
import com.jfinal.aop.Before;
import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.tx.Tx;
import com.jfinal.plugin.activerecord.tx.TxConfig;

/**
 * 权限管理
 *
 * @author Jieven
 * @date 2014-9-11
 */
public class AuthController extends BaseController {

    /**
     * 功能权限分配
     */
    public void index() {
        setAttr("rid", get(0));

        User user = getUser();
        // 当前用户的下级角色(权限继承)
        List<Role> roles = Role.dao.findSubRole(user);
        setAttr("roles", roles);

        // 获取可分配的菜单
        //getAuthMenu();

//        render("/eova/auth/roleChoose.html");
        render("/eova/role/auth/app.html");
    }


    /**
     * 获取可分配菜单和已分配功能
     */
    public void data() {
        int rid = getInt("rid");
        if (x.isEmpty(rid)) {
            NO("参数缺失!");
            return;
        }

        Ret ret = Ret.ok();
        // 登录用户 已授权菜单
        List<Menu> menus = getAuthMenu();
        // 登录用户 已授权按钮
        List<Button> btns = RoleBtn.dao.authBtnByRid(RID());

        // 当前角色已授权按钮
        HashSet<Integer> authBtnIds = new HashSet<>(RoleBtn.dao.queryByRid(rid));
        // 更新已授权按钮状态
        for (Button btn : btns) {
            Integer id = btn.getInt("id");
            btn.put("checked", authBtnIds.contains(id));
        }

        ret.set("menus", menus);
        ret.set("btns", btns);
//        ret.set("auth_btn", authBtn);

//        String json = JsonKit.toJson(authed);

        renderJson(ret);
    }

    /**
     * 授权
     */
    @Before(Tx.class)
    @TxConfig(Ds.EOVA)
    public void doAuth() {
        int rid = getInt("rid");
        if (x.isEmpty(rid)) {
            NO("参数缺失!");
            return;
        }
        // 获取选中功能点
        String checks = get("auth_btns");
        if (x.isEmpty(checks)) {
            NO("请至少勾选一个功能点");
            return;
        }

        String[] ids = checks.split(",");
        User user = getUser();
        int userRid = user.getRid();
        // 如果不是超管角色进行授权操作,只允许分配已拥有的权限
        if (!user.isAdmin()) {
            List<Integer> list = RoleBtn.dao.queryByRid(userRid);
            for (String id : ids) {
                if (!x.isContains(list, id)) {
                    NO("禁止越权操作");
                    return;
                }
            }
        }

        // 删除历史授权
        RoleBtn.dao.deleteByRid(rid);

        if (x.isEmpty(checks)) {
            renderJson(new Easy());
            return;
        }

        HashMap<String, RoleBtn> map = new HashMap<>();
        // 当前勾选的授权
        for (String id : ids) {
            if (!map.containsKey(id)) {
                RoleBtn o = new RoleBtn();
                o.set("rid", EovaTool.toInt(rid));
                o.set("bid", EovaTool.toInt(id));
                map.put(id, o);
            }
        }

        List<RoleBtn> list = new ArrayList<>();
        // 批量更新
        for (String key : map.keySet()) {
            RoleBtn rf = map.get(key);
            rf.remove("id");
            rf.set("rid", rid);
            // 用户分多业务端
            if (EovaConst.SYS_BIZ != 0) {
                rf.set(EovaConst.USER_SYS_FIELD, EovaConst.SYS_BIZ);
            }
            list.add(rf);

        }
        // 批量保存
        Db.use(Ds.EOVA).batchSave(list, list.size());

        OK();
    }

    // 授权功能给某些角色
    public void update() {
        Boolean isCheck = getBoolean("is_check");
        Integer rid = getInt("rid");
        Integer bid = getInt("bid");

        if (isCheck) {
            RoleBtn x = new RoleBtn();
            x.set("rid", rid);
            x.set("bid", bid);
            x.save();
        } else {
            RoleBtn.dao.deleteByBidAndRid(bid, rid);
        }

        OK();
    }

    /**
     * 获取角色已分配功能JSON
     */
    public void button() {
        int rid = getInt(0);
        String menuCode = get("menu_code");
        if (x.isEmpty(rid)) {
            NO("参数缺失!");
            return;
        }
        List<String> list = RoleBtn.dao.findByRoleMenu(rid, menuCode);
        renderJson(list);
    }

    private List<Menu> getAuthMenu() {
        long t = x.time.now();

        // 查所有节点
        int rootId = 0;

        // 获取登录用户
        User user = getUser();
        int rid = user.getRid();

        // 获取所有菜单
        LinkedHashMap<Integer, Menu> allMenu = (LinkedHashMap<Integer, Menu>) sm.auth.getByParentId(rootId);
        // 获取登录用户已授权菜单(编码)
        List<String> authMenuCodeList = sm.auth.queryMenuCodeByRid(rid);

        // 获取已授权菜单
        // LinkedHashMap<Integer, Menu> authMenu = new LinkedHashMap<Integer, Menu>();
        List<Menu> authMenu = new ArrayList<>();
        for (Map.Entry<Integer, Menu> map : allMenu.entrySet()) {
            Menu menu = map.getValue();
            // 未授权 也不是超级管理员
            if (!authMenuCodeList.contains(menu.getStr("code")) && !user.isAdmin()) {
                continue;
            }
            // authMenu.put(map.getKey(), menu);
            authMenu.add(menu);
        }

        // 获取已授权子菜单的所有上级节点(若功能有授权，需要找到上级才能显示)
        LinkedHashMap<Integer, Menu> authParent = new LinkedHashMap<Integer, Menu>();
        for (Menu m : authMenu) {
            WidgetUtil.getParent(allMenu, authParent, m);
        }

        // 根节点不显示排除
        authParent.remove(rootId);

        // 帮孤儿找亲爹, 把爹放到大儿子前面带队(静态树需要)
        F1:
        for (Map.Entry<Integer, Menu> map : authParent.entrySet()) {
            for (int i = 0; i < authMenu.size(); i++) {
                Menu m = authMenu.get(i);
                Menu dir = map.getValue();
                // 已存在目录, 跳过
                if (m.getInt("id").equals(dir.getInt("id"))) {
                    continue F1;
                }
                if (m.getInt("parent_id").equals(map.getKey())) {
                    authMenu.add(i, dir);
                    continue F1;
                }
            }
        }

        // 将已授权的子菜单 放入 已授权 父菜单 Map
        // 顺序说明：父在前，子在后,子默认又是有序的
        //authParent.putAll(authMenu);

        // 获取所有按钮信息
//      List<Button> btns = Button.dao.find("select * from eova_button where is_hide = 0 order by menu_code,cat,num");
        // 获取已授权功能点
//        HashSet<Integer> authBtnIds = new HashSet<>(RoleBtn.dao.queryByRid(rid));

        //		for (int i = 0; i < 8000; i++) {
        //			authBtnIds.add(10000+i);
        //		}
        //		Button tt = btns.get(0);
        //		for (int i = 0; i < 1000; i++) {
        //			Menu tm = authMenu.get(12);
        //			String menuCode = "test" + i;
        //			tm.set("code", menuCode);
        //			authMenu.add(tm);
        //			for (int j = 0; j < 8; j++) {
        //				Button tb = new Button();
        //				tb.put(tt);
        //				tb.set("menu_code", menuCode);
        //				btns.add(tb);
        //			}
        //		}

        // 1000 个菜单作为测试数据
        // 优化前: Load Cost Time:769ms 正想三重重遍历
        // 优化后: Load Cost Time:48ms 逆向单次遍历
        // 优化效果: 性能提升16倍
        // 优化思路:

        // 构建菜单对应功能点
//        buildMenuBtn(btns, authMenu, authBtnIds, user.isAdmin());

        // Map 转 Tree Json
        // String json = WidgetUtil.menu2TreeJson(authParent, rootId);
        // 由前台手工构建静态树, 动态树对于授权并没有太大作用.

//        setAttr("menus", authMenu);
        return authMenu;
    }

    /**
     * 构建菜单按钮
     * eg. [玩家管理] 口查询 口新增 口修改 口删除
     * @param btns
     * @param authMenu
     * @param authBtnIds
     * @param isAdmin
     */
    private void buildMenuBtn(List<Button> btns, List<Menu> authMenu, HashSet<Integer> authBtnIds, boolean isAdmin) {

        HashMap<String, Menu> menuMap = new HashMap<>();
        authMenu.forEach(x -> menuMap.put(x.getStr("code"), x));

        // 按钮比菜单多, 优先级遍历 例如:200菜单 对应 1000个功能, 多重循环消耗巨变
        for (Button btn : btns) {
            int bid = btn.getInt("id");
            String name = btn.getStr("name");
            String menuCode = btn.getStr("menu_code");

            Menu menu = menuMap.get(menuCode);
            if (menu == null) {// 模版按钮无菜单编码
                continue;
            }

            // 非超管 && 未找到授权, 跳过当前按钮
            if (!isAdmin) {
                boolean flag = false;
                for (Number authBtnId : authBtnIds) {
                    if (authBtnId.intValue() == bid) {
                        flag = true;
                        break;
                    }
                }
                if (!flag) continue;
            }

            String btnId = menu.get("btnid", "");
            String btnName = menu.get("btnname", "");
            if (!x.isEmpty(btnId)) {
                btnId += ",";
                btnName += ",";
            }
            btnId += bid;
            btnName += name;

            menu.put("btnid", btnId);
            menu.put("btnname", btnName);
        }

    }

}