package cn.eova.api.sys;

import cn.eova.tools.x;
import cn.eova.core.api.BaseApi;
import cn.eova.hook.EovaHookType;
import cn.eova.hook.HookRegistry;
import cn.eova.service.sm;
import com.jfinal.aop.Before;
import com.jfinal.kit.Kv;
import com.jfinal.plugin.activerecord.tx.Tx;

/**
 * 企业用户接口
 *
 * @author Jieven
 */
public class UserApi extends BaseApi {

    // 用户同步(创建or更新)
    public void sync() {

        Kv kv = getKv();
        try {
            // 执行用户授权钩子, 此处不方便处理用户业务, 由用户自定义实现钩子
            HookRegistry.getAction(EovaHookType.USER, "").invoke(this, kv.set("_biz", "sync"));
        } catch (Exception e) {
            NO("用户同步异常:" + e.getMessage());
            return;
        }

        OK();
    }

    // 更新用户角色
    @Before(Tx.class)
    public void updateRole() {

        Kv kv = getKv();
        try {
            // 执行用户授权钩子, 此处不方便处理用户业务, 由用户自定义实现钩子
            HookRegistry.getAction(EovaHookType.USER, "").invoke(this, kv.set("_biz", "updateRole"));
        } catch (Exception e) {
            x.log.error("用户角色更新异常", e);
            NO("用户角色更新异常:" + e.getMessage());
            return;
        }

        OK();
    }

    // 初始化当前系统的业务用户(对用户进行产品授权时使用)
    public void init() {
        Kv kv = getKv();
        try {
            // 执行用户授权钩子, 此处不方便处理用户业务, 由用户自定义实现钩子
            HookRegistry.getAction(EovaHookType.USER, "").invoke(this, kv.set("_biz", "init"));
        } catch (Exception e) {
            x.log.error("用户初始化异常", e);
            NO("用户初始化异常:" + e.getMessage());
            return;
        }

        OK();
    }

    // 用户强制退出
    public void logout() {
        Kv kv = getKv();

        String loginId = kv.getStr("login_id");
        try {
            sm.login.forceLogoutByLoginId(loginId);
        } catch (Exception e) {
            x.log.error("用户强制退出异常", e);
            NO("用户退出异常:" + e.getMessage());
            return;
        }

        OK();
    }

}