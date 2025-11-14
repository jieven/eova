/**
 * Copyright (c) 2015-2026 EOVA.CN. All rights reserved.
 * Licensed under the LGPL-3.0 license
 * For authorization, please contact: admin@eova.cn
 */
package cn.eova.auth;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import cn.eova.tools.x;
import cn.eova.common.Ds;
import cn.eova.config.EovaConfig;
import cn.eova.config.EovaConst;
import cn.eova.model.Menu;
import cn.eova.model.User;
import com.jfinal.kit.Kv;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.template.Engine;

/**
 * <pre>
 * 权限URI配置管理中心
 * 每个功能绑定动态权限集(减少冗余, 降低维护成本, 可以很方便的进行URI的替换和调整.)
 * 除内置规则外, 可自定义规则 AuthUri.add()
 * </pre>
 */
public class AuthUri {

    // 功能权限配置<功能编码, 权限URI集>
    private static HashMap<String, String[]> URIS = new HashMap<>();

    // 登录后免鉴权URI
    public static HashSet<String> loginAuth = new HashSet<String>();

    static {
        // 系统通用功能登录免鉴权
        loginAuth.add("/sse/**");// 消息推送
        loginAuth.add("/user/**");// 用户相关:修改密码,用户登录,退出登录...

        loginAuth.add(EovaConfig.EOVA_INDEX);               // 入口
        loginAuth.add("/api/home/*");                       // 首页
        loginAuth.add("/api/widget/data");                  // 通用数据
        loginAuth.add("/api/widget/text");                  // 通用翻译
        loginAuth.add("/api/widget/query/eova_find@*");     // 查找数据
        loginAuth.add("/api/meta/table/eova_find@*");       // 查找表头


        // TODO 可添加开关, 进入宽松模式.
        loginAuth.add("/api/meta/form/*");                  // 表单元数据
        loginAuth.add("/api/meta/table/*");                 // 表格元数据

        // TODO　Admin免鉴权
//        loginAuth.add("/api/meta/reorder*");                // 重新排序
//        loginAuth.add("/api/table/width");                  // 更新表列宽
    }

    /**
     * Eova内置权限
     * URI动态解析规则: 支持自动解析的对象 菜单:#(menu.xxx) 菜单配置:#(conf.xxxx)
     */
    static {
        // 单表
        URIS.put("ev_table", new String[]{
                        "/app/#(menu.code)",                        // 页面
                        "/api/meta/form/#(conf.object_code)",       // 查询表单
                        "/api/meta/table/#(conf.object_code)",      // 表
                        "/api/table/query/#(conf.object_code)",     // 表数据
                }
        );
        // 单树
        URIS.put("ev_tree", new String[]{
                        "/app/#(menu.code)",                        // 页面
                        "/api/tree/query/#(conf.object_code)",      // 树
                        "/api/meta/form/#(conf.object_code)",       // 编辑表单
                        "/api/form/data/#(conf.object_code)",       // 表单数据
                }
        );
        // 树表
        URIS.put("ev_tree_table", new String[]{
                        "/app/#(menu.code)",                        // 页面
                        "/api/tree/query/#(conf.tree_object_code)", // 树
                        "/api/meta/form/#(conf.object_code)",       // 查询表单
                        "/api/meta/table/#(conf.object_code)",      // 表
                        "/api/table/query/#(conf.object_code)",     // 表数据
                }
        );
        URIS.put("ev_add", new String[]{
                        "/app/add/#(conf.object_code)",             // 新增页
                        "/api/meta/form/#(conf.object_code)",       // 表单元数据
                        "/api/form/data/#(conf.object_code)",       // 表单数据
                        "/api/form/add/#(conf.object_code)",        // 表单新增处理
                }
        );
        URIS.put("ev_update", new String[]{
                        "/app/update/#(conf.object_code)",
                        "/api/meta/form/#(conf.object_code)",
                        "/api/form/data/#(conf.object_code)",
                        "/api/form/update/#(conf.object_code)",
                }
        );
        URIS.put("ev_detail", new String[]{
                        "/app/detail/#(conf.object_code)",
                        "/api/meta/form/#(conf.object_code)",
                        "/api/form/data/#(conf.object_code)",
                }
        );
        URIS.put("ev_hide", new String[]{
                        "/api/form/hide/#(conf.object_code)",
                }
        );
        URIS.put("ev_delete", new String[]{
                        "/api/form/delete/#(conf.object_code)"
                }
        );
        URIS.put("ev_import", new String[]{
                        "/app/import/#(conf.object_code)"
                }
        );
    }

    /**
     * 获取权限
     * @param code
     * @return
     */
    public static String[] get(String code) {
        return URIS.get(code);
    }

    /**
     * 添加新权限
     * @param code 权限集编码
     * @param uris 权限URI集
     */
    public static void add(String code, String[] uris) {
        if (URIS.get(code) != null) {
            throw new RuntimeException("权限编码已存在, 请更换其他编码:" + code);
        }
        URIS.put(code, uris);
    }

    /**
     * 构建用户URI权限集
     * @param user 当前登录用户数据
     * @throws Exception
     */
    public static void build(User user) {
        int rid = user.getRid();

        // 初始化获取授权信息
        Set<String> auths = new HashSet<>();

        // 获取角色已授权功能
        String sql = "SELECT b.menu_code, b.auth FROM eova_role_btn rf LEFT JOIN eova_button b ON rf.bid = b.id WHERE rf.rid = ?";
        // 多角色支持
        List<Object> paras = new ArrayList<>();
        paras.add(rid);
        // 用户分多业务端
        if (EovaConst.SYS_BIZ != 0) {
            sql += String.format(" and rf.%s = ?", EovaConst.USER_SYS_FIELD);
            paras.add(EovaConst.SYS_BIZ);
        }
        List<Record> btns = Db.use(Ds.EOVA).find(sql, paras.toArray());

        if (!x.isEmpty(btns)) {
            Set<String> menuCodes = btns.stream()
                    .map(m -> m.getStr("menu_code"))
                    .filter(Objects::nonNull)
                    .collect(Collectors.toSet());

            // 获取已授权功能菜单
            String menuCodes_ = x.str.join(menuCodes, "'", ",");
            List<Menu> menus_ = Menu.dao.find(String.format("select code, config from eova_menu where code in (%s)", menuCodes_));
            Map<String, Menu> menuMap = menus_.stream().collect(Collectors.toMap(m -> m.getStr("code"), m -> m));

            // 根据菜单和功能动态构建URI
            for (Record btn : btns) {
                String auth = btn.getStr("auth");
                String menuCode = btn.getStr("menu_code");
                if (!x.isEmpty(auth)) {
                    Menu menu = menuMap.get(menuCode);
                    if (menu == null) {
                        x.log.error("授权功能对应的菜单不存在:" + menuCode);
                        continue;
                    }
                    buildUriByAuthCode(auths, auth, menu);
                }
            }
        }

        user.put("auths", auths);
    }

    /**
     /**
     * 根据权限编码动态构建功能URI
     * @param auths
     * @param auth
     * @param menu
     */
    public static void buildUriByAuthCode(Set<String> auths, String auth, Menu menu) {
        Kv kv = Kv.of("menu", menu).set("conf", menu.getMenuConfig());
        // 运行时动态调试菜单权限生成结果
        String mcode = x.conf.get("dev.auth.menucode");
        if (!x.isEmpty(mcode) && menu.getStr("code").equals(mcode)) {
            x.log.info("[debug]menu:{}", menu);
        }

        // 支持权限编码和URI配置 ev_table;/xxx/xxx;/xxx/xxx/*
        String[] ss = auth.split(";");
        for (String s : ss) {
            // 单个静态URI
            if (s.startsWith("/")) {
                auths.add(s);
            }
            // 多个动态URI
            else {
                String[] uris = AuthUri.get(auth);
                if (x.isEmpty(uris)) {
                    x.log.error("未知的权限组编码:{}, 请检查权限配置", auth);
                    continue;
                }

                for (String uri : uris) {
                    uri = parseAuthUri(uri, kv);

                    if (!x.isEmpty(mcode) && menu.getStr("code").equals(mcode)) {
                        x.log.info("[debug]{}", uri);
                    }

                    auths.add(uri);
                }
            }
        }

    }

    /**
     * 动态解析URI参数
     * @param s
     * @param pms
     * @return
     */
    public static String parseAuthUri(String s, Kv pms) {
        if (x.isEmpty(s)) {
            return s;
        }
        return Engine.use().getTemplateByString(s).renderToString(pms);
    }


}


// 通用业务免鉴权
//        whiteList.add("/meta/object/**");
//        whiteList.add("/meta/fields/**");
//        whiteList.add("/meta/dict/**");
// 用户个性化设置
//        whiteList.add("/meta/diy/*");
//        whiteList.add("/meta/swapdiy");
//        whiteList.add("/grid/query/eova_diy*");
// 权限相关免鉴权
//        whiteList.add("/auth/button/*");
//        whiteList.add("/widget/**");
//        whiteList.add("/upload/**");
//        whiteList.add("/grid/updateCell");
//        whiteList.add("/user/password");
//        whiteList.add("/user/doPassword");

//        getAuthUris().get(0).add(EOVA_INDEX);
//        getAuthUris().get(0).add(EOVA_INDEX + '/');
//        getAuthUris().get(0).add(EOVA_INDEX_H5);
//        getAuthUris().get(0).add(EOVA_INDEX_H5 + '/');

//        getAuthUris().get(0).add("/menu1/**/**");
//        getAuthUris().get(0).add("/app/**/**");
//        getAuthUris().get(0).add("/api/meta/**/**");