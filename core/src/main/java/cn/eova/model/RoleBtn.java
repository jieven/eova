/**
 * Copyright (c) 2015-2026 EOVA.CN. All rights reserved.
 * Licensed under the LGPL-3.0 license
 * For authorization, please contact: admin@eova.cn
 */
package cn.eova.model;

import java.util.ArrayList;
import java.util.List;

import cn.eova.common.Ds;
import cn.eova.common.base.BaseModel;
import cn.eova.config.EovaConst;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

/**
 * 角色已授权功能点
 *
 * @author Jieven
 * @date 2014-9-10
 */
public class RoleBtn extends BaseModel<RoleBtn> {

    private static final long serialVersionUID = -1794335434198017392L;

    public static final RoleBtn dao = new RoleBtn();

    /**
     * 获取角色已授权功能(按钮)ID
     *
     * @param rid 角色ID
     * @return
     */
    public List<Integer> queryByRid(int rid) {
        List<Object> paras = new ArrayList<Object>();
        String sql = "select bid from eova_role_btn where rid = ?";
        paras.add(rid);
        // 用户分多业务端
        if (EovaConst.SYS_BIZ != 0) {
            sql += String.format(" and %s = ?", EovaConst.USER_SYS_FIELD);
            paras.add(EovaConst.SYS_BIZ);
        }
        return Db.use(Ds.EOVA).query(sql, paras.toArray());
    }

    /**
     * 获取角色已授权按钮
     *
     * @param rid 角色ID
     * @return
     */
    public List<Button> authBtnByRid(int rid) {
        List<Object> paras = new ArrayList<Object>();
        String sql = "select id,menu_code,name from eova_button where id in (select bid from eova_role_btn where rid = ?) order by menu_code,cat,num";
        paras.add(rid);
        // 用户分多业务端
        if (EovaConst.SYS_BIZ != 0) {
            sql += String.format(" and %s = ?", EovaConst.USER_SYS_FIELD);
            paras.add(EovaConst.SYS_BIZ);
        }
        return Button.dao.find(sql, paras.toArray());
    }

    /**
     * 删除角色已授权
     * @param rid
     *
     */
    public void deleteByRid(int rid) {
        List<Object> paras = new ArrayList<Object>();
        String sql = "delete from eova_role_btn where rid = ?";
        paras.add(rid);
        // 用户分多业务端
        if (EovaConst.SYS_BIZ != 0) {
            sql += String.format(" and %s = ?", EovaConst.USER_SYS_FIELD);
            paras.add(EovaConst.SYS_BIZ);
        }
        Db.use(Ds.EOVA).delete(sql, paras.toArray());
    }

    /**
     * 获取菜单已分配权限
     * @param menuId
     * @return
     */
    public List<Record> findByMenuId(int menuId) {
        return Db.use(Ds.EOVA).find("select bid, rid from eova_role_btn where bid in (select id from eova_button where menu_code = (select code from eova_menu where id = ?))", menuId);
    }

    /**
     * 获取指定菜单角色已分配权限
     * @param rid
     * @param menuCode
     * @return
     */
    public List<String> findByRoleMenu(int rid, String menuCode) {
        List<Object> paras = new ArrayList<Object>();
        String sql = "select name from eova_button where id in (select bid from eova_role_btn where rid = ?";
        paras.add(rid);
        // 用户分多业务端
        if (EovaConst.SYS_BIZ != 0) {
            sql += String.format(" and %s = ?", EovaConst.USER_SYS_FIELD);
            paras.add(EovaConst.SYS_BIZ);
        }
        sql += " and bid in (select id from eova_button where menu_code = ?))";
        paras.add(menuCode);
        return Db.use(Ds.EOVA).query(sql, paras.toArray());
    }

    /**
     * 获取角色已授权功能
     *
     * @param rids 角色ID
     * @return
     */
//	public List<RoleBtn> findByRid(String rids) {
//		return this.find("select * from eova_role_btn where rid in (?)", rids);
//	}

    /**
     * 删除菜单功能关联的权限
     * @param menuCode
     */
    public void deleteByMenuCode(String menuCode) {
        String sql = "delete from eova_role_btn where bid in (select id from eova_button where menu_code = ?)";
        Db.use(Ds.EOVA).update(sql, menuCode);
    }

    /**
     * 删除按钮相关的权限
     * @param bid 按钮ID
     */
    public void deleteByBid(int bid) {
        String sql = "delete from eova_role_btn where bid = ?";
        Db.use(Ds.EOVA).update(sql, bid);
    }

    /**
     * 删除授权
     * @param bid
     * @param rid
     */
    public void deleteByBidAndRid(int bid, int rid) {
        String sql = "delete from eova_role_btn where bid = ? and rid = ?";
        Db.use(Ds.EOVA).update(sql, bid, rid);
    }

}