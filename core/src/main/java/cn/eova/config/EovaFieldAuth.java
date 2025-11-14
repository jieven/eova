/**
 * Copyright (c) 2015-2026 EOVA.CN. All rights reserved.
 * <p>
 *
 * Licensed under the LGPL-3.0 license
 * Software copyright registration number:2018SR1012969
 * For authorization, please contact: admin@eova.cn
 */
package cn.eova.config;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import cn.eova.tools.x;
import cn.eova.common.Ds;
import cn.eova.model.User;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

/**
 * 字段授权
 *
 * @author Jieven
 * @date 2014-5-15
 */
public class EovaFieldAuth {

    /**
     * 租户管理员角色
     */
    public static Set<String> adminRole = new HashSet<>();

    /**
     * 租户(企业)字段授权
     */
    public static Map<Integer, List<Record>> fieldAuths = new HashMap<>();

    /**
     * 平台字段权限-白名单<对象.字段, 角色集>
     **/
    private static final HashMap<String, Set<String>> roleFields = new HashMap<String, Set<String>>();


    /**
     * 重载字段授权
     * @param companyId 为Null 刷新所有企业
     */
    public static void authReload(Integer companyId) {

        boolean isFieldAuth = x.conf.getBool("eova.field.auth", false);
        if (!isFieldAuth) {
            return;
        }

        String sql = "select * from eova_field_auth";

        // 加载全部
        if (companyId == 0) {
            List<Record> fieldAuth = Db.use(Ds.EOVA).find(sql);
            if (!x.isEmpty(fieldAuth)) {
                EovaFieldAuth.fieldAuths = fieldAuth.stream().collect(Collectors.groupingBy(m -> m.getInt("company_id")));
                x.log.info("加载字段授权策略:{}", fieldAuth.size());
            }
            return;
        }

        // 重载指定企业
        sql += " where company_id = " + companyId;
        List<Record> fieldAuth = Db.use(Ds.EOVA).find(sql);
        EovaFieldAuth.fieldAuths.put(companyId, fieldAuth);

        x.log.info("更新企业[{}]字段授权策略:{}", companyId, fieldAuth.size());

    }

    /**
     * 字段角色授权
     *
     * @param objectCode 元对象
     * @param field      字段
     * @param rids       角色集
     */
    public static void authRole(String objectCode, String field, String rids) {
        String key = String.format("%s.%s", objectCode, field);

        Set<String> set = roleFields.get(key);
        if (set == null) {
            set = new HashSet<>();
        }
        set.addAll(Arrays.asList(rids.split(",")));
        roleFields.put(key, set);
    }

    /**
     * 获取当前用户需禁用的字段
     * TODO 应该将权限优化到用户域
     *
     * @param user 登录用户
     * @return 禁用字段列表
     */
    public static Set<String> getDisableFields(User user) {

        // 字段禁用-黑名单<对象.字段>
        Set<String> disableFields = new HashSet<>();

        String uid = user.getId() + "";
        String rid = user.getRid() + "";

        // 租户字段授权策略
        if (!fieldAuths.isEmpty()) {
            List<Record> companyAuths = fieldAuths.get(user.getCompanyId());
            if (!x.isEmpty(companyAuths)) {
                for (Record ca : companyAuths) {
                    Integer type = ca.getInt("type");

                    // 支持多字段
                    String[] fss = ca.getStr("field").split(",");
                    Set<String> fields = new HashSet<>();
                    for (String s : fss) {
                        fields.add(String.format("%s.%s", ca.getStr("object"), s));
                    }

                    // 策略配置值 1,2,5
                    List<String> auths = Arrays.asList(ca.getStr("auth").split(","));

                    // 管理员跳过白名单策略(租户管理员角色不受限制)
                    if (!adminRole.contains(rid)) {
                        // 角色白名单:未授权角色屏蔽当前字段
                        if (type == 1 && !auths.contains(rid)) {
                            disableFields.addAll(fields);
                        }
                        // 用户白名单:未授权用户 屏蔽当前字段
                        else if (type == 3 && !auths.contains(uid)) {
                            disableFields.addAll(fields);
                        }
                    }

                    // 角色黑名单:仅指定角色不可见
                    if (type == 2 && auths.contains(rid)) {
                        disableFields.addAll(fields);
                    }
                }
            }
        }

        // 平台角色白名单:平台字段授权(兜底优先)
        for (String field : roleFields.keySet()) {
            Set<String> rids = roleFields.get(field);
            if (!rids.contains(rid)) {
                disableFields.add(field);
            }
        }

        return disableFields;
    }

}