/**
 * Copyright (c) 2015-2026 EOVA.CN. All rights reserved.
 * Licensed under the LGPL-3.0 license
 * For authorization, please contact: admin@eova.cn
 */
package cn.eova.core;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import cn.eova.common.base.BaseController;
import cn.eova.core.menu.MenuUtil;
import cn.eova.model.Menu;
import cn.eova.tools.x;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jfinal.aop.Before;
import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.activerecord.tx.Tx;

/**
 * 首页接口
 *
 * @author Jieven
 */
public class HomeController extends BaseController {

    public void menu() {

        int rid = getUser().getRid();

        // 获取菜单和分类
        List<Record> cats = Db.use(x.DS_EOVA).find("SELECT * FROM eova_menu where is_hide = 0 and type = 'dir' ORDER BY parent_id, num");
        // 所有菜单
        //List<Record> menus = Db.use(x.DS_EOVA).find("SELECT * FROM eova_menu where is_hide = 0 and type <> 'dir' ORDER BY id");
        List<Menu> menus = MenuUtil.buildMenu(getUser());
        if (menus == null) {
            renderText("当前角色无权限, 请联系管理员分配权限!");
            return;
        }
        // 非管理员禁用平台维护
        if (!getUser().isAdmin()) {
            for (Record r : cats) {
                if (r.getStr("name").equals("平台维护")) {
                    r.set("is_hide", true);
                }
            }
        }

        renderJson(Ret.ok().set("menus", menus).set("cats", cats));
    }

    @Before(Tx.class)
    public void star() {
        JSONObject pms = getJsonObj();
        System.out.println(pms.toJSONString());

        int menuId = pms.getIntValue("menu_id");
        int num = pms.getIntValue("num");

        Record r = Db.findFirst("select * from menu_user where menu_id = ?", menuId);
        if (r == null) {
            r = new Record();
            r.set("uid", UID());
            r.set("menu_id", menuId);
            r.set("num", num);
            Db.save("menu_user", r);
            x.log.info("{}-{}收藏菜单{}", getUser().getCompanyId(), getUser().getName(), menuId);
        } else {
            Db.delete("delete from menu_user where menu_id = ?", menuId);
            x.log.info("{}-{}取消收藏{}", getUser().getCompanyId(), getUser().getName(), menuId);
        }

        renderJson(Ret.ok());
    }

    // 重新排序
    @Before(Tx.class)
    public void resort() {
        String s = getRawData();
        JSONArray list = JSON.parseArray(s);

        List<String> sqls = new ArrayList<>();

        for (Iterator i = list.iterator(); i.hasNext(); ) {
            String sql = "";

            JSONObject o = (JSONObject) i.next();
            int id = o.getIntValue("id");
            int userNum = o.getIntValue("user_num");

            sqls.add(String.format("update menu_user set num = %s where uid = %s and menu_id = %s", userNum, UID(), id));
        }

        // sqls.forEach(sa -> System.out.println(sa));

        Db.batch(sqls, sqls.size());

        renderJson(Ret.ok());
    }


//    public void meta() {
//        renderEnjoy("/home/meta.html");
//    }
//
//    public void page(){
//        renderJson(Db.findById("menu", get("id")));
//    }
//
//    public void getMyMenu() {
//        List<Record> menus = Db.find("SELECT * FROM menu where is_hide = 0 and id in (select menu_id from menu_user where uid = ?)", UID());
//        renderJson(Ret.ok().set("menus", menus));
//    }


}

//int oid = om.getIntValue("id");
//    int onum = om.getIntValue("user_num");
//    int nnum = nm.getIntValue("user_num");
//
//    int UID = 100000;
//
//// 向下移动
//        if (onum < nnum) {
//        // 向下移动算法 小于O, 大于等于N的--
//        // 1   2(O)  3   4  5(N)    6
//        // 1         2   3  4    O  6 ; O=5
//        Db.update("UPDATE menu_user SET num = num-1 WHERE uid = ? and ? < num and num <= ?", UID, onum, nnum);
//        }
//        // 向上移动
//        else {
//        // 向上移动算法 N~O ++
//        //      1(N) 2   3   4(O)  5
//        // O=1  2    3   4         5
//        Db.update("UPDATE menu_user SET num = num+1 WHERE uid = ? and ? <= num and num < ?", UID, nnum, onum);
//        }
//
//        // N->O 交换
//        Db.update("UPDATE menu_user SET num = ? WHERE uid = ? and num = ?", UID, nnum, onum);