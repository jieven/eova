/**
 * Copyright (c) 2015-2026 EOVA.CN. All rights reserved.
 * <p>
 *
 * Licensed under the LGPL-3.0 license
 * Software copyright registration number:2018SR1012969
 * For authorization, please contact: admin@eova.cn
 */
package cn.eova.model;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

import cn.eova.tools.x;
import cn.eova.common.Ds;
import cn.eova.common.base.BaseModel;
import cn.eova.common.utils.data.ListUtil;
import cn.eova.config.EovaConst;
import cn.eova.i18n.I18NBuilder;
import com.jfinal.plugin.activerecord.Db;

/**
 * 功能按钮
 *
 * @author Jieven
 * @date 2014-9-10
 */
public class Button extends BaseModel<Button> {

    private static final long serialVersionUID = 3481288366186459644L;

    /** 基本通用功能-查询 **/
    public static final int FUN_QUERY = 1;
    public static final int FUN_QUERY_INDEX = 1;
    public static final String FUN_QUERY_NAME = "查询";
    public static final String FUN_QUERY_UI = "query";
    public static final String FUN_QUERY_BS = "/grid/query";
    /** 基本通用功能-新增 **/
    public static final int FUN_ADD = 2;
    public static final int FUN_ADD_INDEX = 2;
    public static final String FUN_ADD_NAME = "新增";
    public static final String FUN_ADD_UI = "/eova/widget/form/btn/add.html";
    public static final String FUN_ADD_BS = "/form/add";
    /** 基本通用功能-修改 **/
    public static final int FUN_UPDATE = 3;
    public static final int FUN_UPDATE_INDEX = 3;
    public static final String FUN_UPDATE_NAME = "修改";
    public static final String FUN_UPDATE_UI = "/eova/widget/form/btn/update.html";
    public static final String FUN_UPDATE_BS = "/form/update";
    /** 基本通用功能-删除 **/
    public static final int FUN_DELETE = 4;
    public static final int FUN_DELETE_INDEX = 4;
    public static final String FUN_DELETE_NAME = "删除";
    public static final String FUN_DELETE_UI = "/eova/widget/form/btn/delete.html";
    public static final String FUN_DELETE_BS = "/grid/delete";
    /** 基本通用功能-查看 **/
    public static final int FUN_DETAIL = 5;
    public static final int FUN_DETAIL_INDEX = 5;
    public static final String FUN_DETAIL_NAME = "查看";
    public static final String FUN_DETAIL_UI = "/eova/widget/form/btn/detail.html";
    public static final String FUN_DETAIL_BS = "/form/detail";
    /** 单表模版功能-导入 **/
    public static final int FUN_IMPORT = 6;
    public static final int FUN_IMPORT_INDEX = 6;
    public static final String FUN_IMPORT_NAME = "导入";
    public static final String FUN_IMPORT_UI = "/eova/template/single/btn/import.html";
    public static final String FUN_IMPORT_BS = "/single_grid/import";

    /** 单表树模版功能-新增 **/
    public static final int SINGLETREE_ADD = 7;
    public static final int SINGLETREE_ADD_INDEX = 2;
    public static final String SINGLETREE_ADD_NAME = "新增";
    public static final String SINGLETREE_ADD_UI = "/eova/template/singletree/btn/add.html";
    public static final String SINGLETREE_ADD_BS = "/form/import";
    /** 单表树模版功能-新增 **/
    public static final int SINGLETREE_UPDATE = 8;
    public static final int SINGLETREE_UPDATE_INDEX = 3;
    public static final String SINGLETREE_UPDATE_NAME = "修改";
    public static final String SINGLETREE_UPDATE_UI = "/eova/template/singletree/btn/update.html";
    public static final String SINGLETREE_UPDATE_BS = "/form/import";

    public static final Button dao = new Button();

    public Button() {
    }

    public Button(String name, String ui, boolean isHide) {
        this.set("name", name);
        this.set("ui", ui);
        this.set("num", 0);
        this.set("cat", 0);
        this.set("is_base", true);
        this.set("is_hide", isHide);
    }

    public String toString() {
        return this.getStr("name");
    }

    public Button(String menuCode, int type) {
        String ui = null;
        String bs = null;
        String name = null;
        int index = 0;

        switch (type) {
            case FUN_QUERY:
                ui = FUN_QUERY_UI;
                bs = FUN_QUERY_BS;
                name = FUN_QUERY_NAME;
                index = FUN_QUERY_INDEX;
                break;
            case FUN_ADD:
                ui = FUN_ADD_UI;
                bs = FUN_ADD_BS;
                name = FUN_ADD_NAME;
                index = FUN_ADD_INDEX;
                break;
            case FUN_UPDATE:
                ui = FUN_UPDATE_UI;
                bs = FUN_UPDATE_BS;
                name = FUN_UPDATE_NAME;
                index = FUN_UPDATE_INDEX;
                break;
            case FUN_DELETE:
                ui = FUN_DELETE_UI;
                bs = FUN_DELETE_BS;
                name = FUN_DELETE_NAME;
                index = FUN_DELETE_INDEX;
                break;
            case FUN_DETAIL:
                ui = FUN_DETAIL_UI;
                bs = FUN_DETAIL_BS;
                name = FUN_DETAIL_NAME;
                index = FUN_DETAIL_INDEX;
                break;
            case FUN_IMPORT:
                ui = FUN_IMPORT_UI;
                bs = FUN_IMPORT_BS;
                name = FUN_IMPORT_NAME;
                index = FUN_IMPORT_INDEX;
                break;
            case SINGLETREE_ADD:
                ui = SINGLETREE_ADD_UI;
                bs = SINGLETREE_ADD_BS;
                name = SINGLETREE_ADD_NAME;
                index = SINGLETREE_ADD_INDEX;
                break;
            case SINGLETREE_UPDATE:
                ui = SINGLETREE_UPDATE_UI;
                bs = SINGLETREE_UPDATE_BS;
                name = SINGLETREE_UPDATE_NAME;
                index = SINGLETREE_UPDATE_INDEX;
                break;
        }
        this.set("menu_code", menuCode);
        this.set("name", name);
        this.set("ui", ui);
        this.set("bs", bs);
        this.set("num", index);
        this.set("is_base", true);
        this.set("cat", 0);
    }

    /**
     * 根据权限获取非查询功能按钮
     *
     * @param menuCode
     * @param rid
     * @return
     */
    public List<Button> queryByMenuCode(String menuCode, int rid) {
        // 为了同时兼容Mysql和Oracle的写法
        String findRoleBtn = "select bid from eova_role_btn where rid = ?";
        // 用户分多业务端
        if (EovaConst.SYS_BIZ != 0) {
            findRoleBtn += String.format(" and %s = %s", EovaConst.USER_SYS_FIELD, EovaConst.SYS_BIZ);
        }

        List<Button> list = dao.queryByCache("select * from eova_button where is_hide <> 1 and menu_code = ? and id in (" + findRoleBtn + ") order by num",
                menuCode, rid);
        I18NBuilder.models(list, "name");

        String btnset = "btnset";
        // 兼容模式 没按钮 || 没分组字段 || 只有1个按钮
        if (x.isEmpty(list) || list.size() < 2 || !list.get(0)._getAttrs().containsKey(btnset)) {
            return list;
        }

        ArrayList<Button> tps = new ArrayList<>();

        try {
            // 按钮分组
            LinkedHashMap<String, List<Button>> map = list.stream().collect(Collectors.groupingBy(m -> m.getStr(btnset), LinkedHashMap::new, Collectors.toList()));

            for (String key : map.keySet()) {
                List<Button> btns = map.get(key);

                // 无分组按钮
                if (key.equals("")) {
                    tps.addAll(btns);
                }
                // 有分组, 推举第一项为组长(携带组员)
                else {
                    Button button = btns.get(0);
                    btns.remove(0);
                    button.put("items", btns);
                    tps.add(button);
                }
            }
        } catch (Exception e) {
            x.log.error("按钮分组异常, 请检查btnset的默认值是否为空字符串", e);
        }

        return tps;
    }

    /**
     * 是否存在功能按钮
     *
     * @param menuCode 菜单编码
     * @param bs 服务端
     * @return 是否存在该按钮
     */
    public boolean isExistButton(String menuCode, String bs, int groupNum) {
        String sql = "select count(*) from eova_button where menu_code = ? and bs = ? and cat = ?";
        long count = Db.use(Ds.EOVA).queryLong(sql, menuCode, bs, groupNum);
        if (count != 0) {
            return true;
        }
        return false;
    }

    /**
     * 删除基础功能按钮(删除不用查询)
     *
     * @param menuCode
     */
    public void deleteFunByMenuCode(String menuCode) {
        String sql = "delete from eova_button where is_base = 1 and ui <> 'query' and menu_code = ?";
        Db.use(Ds.EOVA).update(sql, menuCode);
    }

    /**
     * 删除菜单下所有按钮
     *
     * @param menuCode
     */
    public void deleteByMenuCode(String menuCode) {
        String sql = "delete from eova_button where menu_code = ?";
        Db.use(Ds.EOVA).update(sql, menuCode);
    }

    /**
     * 获取当前菜单的所有功能
     * @param menuId
     * @return
     */
    public List<Button> findByMenuId(int menuId) {
        String sql = "select id, name, num, cat from eova_button where is_hide = 0 and menu_code = (select code from eova_menu where id = ?)  order by cat, num";
        return this.find(sql, menuId);
    }

    /**
     * 有序按组获取非查询功能按钮
     * @param menuCode
     * @return
     */
    public List<Button> findNoQueryByMenuCode(String menuCode) {
        String sql = "select * from eova_button where ui <> 'query' and menu_code = ? and is_base = 1 order by cat, num";
        return this.find(sql, menuCode);
    }

    /**
     * 有序按组获取非查询功能按钮
     * @param menuCode
     * @return
     */
    public List<Button> findByMenuCode(String menuCode) {
        String sql = "select * from eova_button where menu_code = ? order by cat, num";
        return this.find(sql, menuCode);
    }

    /**
     * 查询按钮当前最大排序值
     * @param menuCode 菜单编码
     * @param groupNum 按钮分组号
     * @return
     */
    public int getMaxOrderNum(String menuCode, int groupNum) {
        String sql = "select max(num) from eova_button where menu_code = ? and cat = ?";
        Number num = Db.use(Ds.EOVA).queryNumber(sql, menuCode, groupNum);
        if (num == null) {
            return 0;
        }
        return num.intValue();
    }

    /**
     * 获取已授权按钮的菜单ID
     * @param rid
     * @return
     */
    public List<Integer> queryMenuIdByRid(int rid) {
        // 已授权菜单
        List<Object> paras = new ArrayList<Object>();
        String sql = "select id from eova_menu where is_hide = 0 and code in ( select b.menu_code from eova_role_btn rf left join eova_button b on rf.bid = b.id where b.ui = 'query' and rf.rid = ?";
        paras.add(rid);
        // 用户分多业务端
        if (EovaConst.SYS_BIZ != 0) {
            sql += String.format(" and rf.%s = ?", EovaConst.USER_SYS_FIELD);
            paras.add(EovaConst.SYS_BIZ);
        }
        sql += " )";

        // 为了兼容Oracle 返回的List<BigDecimal>
        return ListUtil.toNumber(Db.use(Ds.EOVA).query(sql, paras.toArray()), Integer.class);
    }

    /**
     * 获取已授权按钮的菜单ID
     * @param rid 角色ID
     * @return 菜单编码集合
     */
    public List<String> queryMenuCodeByRid(int rid) {
        String sql = "select code from eova_menu where is_hide = 0 and code in ( select b.menu_code from eova_role_btn rf left join eova_button b on rf.bid = b.id where b.ui = 'query' and rf.rid = ?)";
        return Db.use(Ds.EOVA).query(sql, rid);
    }

}