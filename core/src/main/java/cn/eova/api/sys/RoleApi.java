package cn.eova.api.sys;

import java.util.List;

import cn.eova.tools.x;
import cn.eova.common.Ds;
import cn.eova.core.api.BaseApi;
import cn.eova.model.Button;
import cn.eova.model.Role;
import com.jfinal.kit.Kv;
import com.jfinal.plugin.activerecord.Db;

/**
 * 企业用户角色同步
 *
 * @author Jieven
 */
public class RoleApi extends BaseApi {

    // 新增角色
    public void add() {
        Kv kv = getKv();

        // 默认权限级别(同步范围限定, 防止和本系统角色冲突)
        int lv = x.conf.getInt("eova.role.lv.default", 900);

        Integer companyId = kv.getInt("company_id");
        Integer roleId = kv.getInt("role_id");
        String name = kv.getStr("name");

        boolean isExist = Role.dao.isExist("select count(*) from eova_role where id = ?", roleId);
        if (isExist) {
            NO("已存在该角色");
            return;
        }

        addRole(companyId, roleId, name, lv);

        OK();
    }

    // 删除角色
    public void delete() {
        Kv kv = getKv();

        // 默认权限级别(同步范围限定, 防止和本系统角色冲突)
        int lv = x.conf.getInt("eova.role.lv.default", 900);

//        Integer companyId = kv.getInt("company_id");
//        String name = kv.getStr("name");

        Integer roleId = kv.getInt("role_id");

        // 删除角色
        Db.use(Ds.EOVA).delete("delete from eova_role where id = ?", roleId);

        OK();
    }

    // 更新角色
    public void update() {
        Kv kv = getKv();

        // 默认权限级别(同步范围限定, 防止和本系统角色冲突)
        int lv = x.conf.getInt("eova.role.lv.default", 900);

        Integer companyId = kv.getInt("company_id");
        Integer roleId = kv.getInt("role_id");
//        String newname = kv.getStr("newname");
        String name = kv.getStr("name");

        // 更新角色
        Db.use(Ds.EOVA).delete("update eova_role set name = ? where id = ?", name, roleId);

        OK();
    }

    // 批量同步
    public void sync() {
        List<Kv> kvs = getKvs();// base 批量传入角色

        int lv = x.conf.getInt("eova.role.lv.default", 900);

        for (Kv kv : kvs) {
            Integer roleId = kv.getInt("role_id");
            Integer companyId = kv.getInt("company_id");
            String name = kv.getStr("name");

            Role role = Role.dao.findById(roleId);
            // 存在就更新名称
            if (role == null) {
                addRole(companyId, roleId, name, lv);
            }
            // 不存在新增角色
            else {
                role.set("name", name);
                role.update();
            }
        }

        OK();
    }

    /**
     * 1.对比base.role 和 erp.role 新增/更新/删除
     * 2.特殊企业不更新
     */
    public void sync1() {
        // base
    }

    /**
     * 添加角色
     * @param companyId
     * @param roleId
     * @param name
     * @param lv
     */
    private void addRole(Integer companyId, Integer roleId, String name, int lv) {
        Role role = new Role();
        role.set("id", roleId);
        role.set("company_id", companyId);
        role.set("name", name);
        role.set("lv", lv);
        role.save();
    }

    // 获取角色已授权菜单
    public void menus() {
        Kv kv = getKv();

        Integer roleId = kv.getInt("role_id");

        // 获取已授权菜单ID
        List<String> menus = Button.dao.queryMenuCodeByRid(roleId);

        OK(menus);
    }

}
