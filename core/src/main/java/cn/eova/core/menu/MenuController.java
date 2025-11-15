/**
 * Copyright (c) 2015-2026 EOVA.CN. All rights reserved.
 * Licensed under the LGPL-3.0 license
 * For authorization, please contact: admin@eova.cn
 */
package cn.eova.core.menu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.eova.common.Ds;
import cn.eova.common.Easy;
import cn.eova.common.base.BaseCache;
import cn.eova.common.base.BaseController;
import cn.eova.common.utils.db.DbUtil;
import cn.eova.common.utils.xx;
import cn.eova.config.EovaConst;
import cn.eova.core.button.ButtonFactory;
import cn.eova.core.menu.config.ChartConfig;
import cn.eova.core.menu.config.MenuConfig;
import cn.eova.core.menu.config.TreeConfig;
import cn.eova.core.meta.MetaUtil;
import cn.eova.model.Button;
import cn.eova.model.EovaProps;
import cn.eova.model.EovaTemplate;
import cn.eova.model.Menu;
import cn.eova.model.MetaField;
import cn.eova.model.MetaObject;
import cn.eova.model.Role;
import cn.eova.model.RoleBtn;
import cn.eova.service.sm;
import cn.eova.template.common.config.TemplateConfig;
import cn.eova.tools.x;
import com.jfinal.aop.Before;
import com.jfinal.kit.Kv;
import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.NestedTransactionHelpException;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.activerecord.tx.Tx;
import com.jfinal.plugin.activerecord.tx.TxConfig;

/**
 * 菜单管理
 *
 * @author Jieven
 * @date 2014-9-11
 */
public class MenuController extends BaseController {

    public void icon() {
        render("/eova/icon.html");
    }

    public void toAdd() {
        Integer pid = getParaToInt("parent_id");
        // pid == 0 是根节点
        if (pid != 0) {
            // 判断父节点是否为目录
            Menu menu = Menu.dao.findById(pid);
            if (!menu.getStr("type").equals(Menu.TYPE_DIR)) {
                renderHtml("您选择的节点类型不是目录,无法添加子菜单,请选择其他目录节点进行添加");
                return;
            }
        }
        keepPara("parent_id");

        // 获取模版列表
        List<EovaTemplate> tps = EovaTemplate.dao.findAll();
        set("templates", tps);

        render("/eova/menu/add/app.html");
    }

    public void props() {
        String code = get(0);
        // 模版配置
        List<EovaProps> props = EovaProps.dao.find("select * from eova_props where biz = ? and code = ? order by num", "app", code);
        props.forEach(o -> {
            o.remove("id").remove("biz").remove("app").remove("code");
        });
        renderJson(Ret.data(props).setOk());
    }

    // 更新窗口大小
    public void updateLayerSize() {
        String menuCode = get(0);
        Integer width = getInt("width");
        Integer height = getInt("height");
        Menu menu = Menu.dao.findByCode(menuCode);
        Kv kv = menu.getMenuConfig();
        kv.set("layer_width", width);
        kv.set("layer_height", height);
        menu.set("config", kv.toJson());
        menu.update();

        OK();
    }

    public void toUpdate() {
        int pkValue = getParaToInt(1);
        Menu menu = Menu.dao.findById(pkValue);

        setAttr("menu", menu);

        render("/eova/menu/add.html");
    }

    /**
     * 菜单基本功能管理
     */
    public void toMenuFun() {
        int id = getParaToInt(0);
        Menu menu = Menu.dao.findById(id);

        setAttr("menu", menu);

        HashMap<Integer, List<Button>> btnMap = new HashMap<Integer, List<Button>>();

        List<Button> btns = Button.dao.findNoQueryByMenuCode(menu.getStr("code"));
        for (Button b : btns) {
            int group = b.getInt("cat");
            List<Button> list = btnMap.get(group);
            if (list == null) {
                list = new ArrayList<Button>();
                btnMap.put(group, list);
            }
            list.add(b);
        }

        setAttr("btnMap", btnMap);

        render("/eova/menu/menuFun.html");
    }

    // 一键导入
    @Before(Tx.class)
    @TxConfig(Ds.EOVA)
    public void addAll() {
        if (!get(0, "").equals("eova")) {
            renderJson(new Easy("请输入校验码，防止误操作！！！！！"));
            return;
        }

        List<MetaObject> objects = MetaObject.dao.find("select * from eova_object where id >= 1100");
        for (MetaObject o : objects) {

            String menuCode = o.getStr("code");
            System.out.println("create " + menuCode);
            Menu menu = new Menu();
            menu.set("parent_id", 3);
            menu.set("name", o.getStr("name"));
            menu.set("code", menuCode);
            menu.set("type", TemplateConfig.SINGLE_GRID);

            // 菜单配置
            MenuConfig config = new MenuConfig();
            config.setObjectCode(o.getStr("code"));
            menu.setConfig(config);
            menu.save();

//			createMenuButton(menuCode, TemplateConfig.SINGLE_GRID, config);
            // 创建菜单按钮 TODO 临时注释
//            new ButtonFactory(TemplateConfig.SINGLE_GRID, menuCode, config).build();

            // 还原成默认状态
            o.set("diy_card", null);
            o.update();
        }
        // 新增菜单使缓存失效
        BaseCache.delSer(EovaConst.ALL_MENU);

        renderJson(new Easy("Auto Create Menu:" + objects.size(), true));
    }

    // 导出选中菜单数据
    public void doExport() {
        String ids = get(0);

        StringBuilder sb = new StringBuilder();

        String sql = "select * from eova_menu where id in (" + ids + ")";
        List<Record> list = Db.use(Ds.EOVA).find(sql);
        // 删除语句
        list.forEach(x -> {
            String code = x.getStr("code");
            sb.append(String.format("delete from eova_menu where code = '%s';\n", code));
            sb.append(String.format("delete from eova_button where menu_code = '%s';\n", code));
            sb.append("\n");
        });
        sb.append("\n");
        DbUtil.generateSql(Ds.EOVA, "eova_menu", list, "id", sb);
        sb.append("\n");
        for (Record r : list) {
            List<Record> btns = Db.use(Ds.EOVA).find("select * from eova_button where menu_code = ?", r.getStr("code"));
            DbUtil.generateSql(Ds.EOVA, "eova_button", btns, "id", sb);
            sb.append("\n");
        }

        renderText(sb.toString());
    }

    /**
     * 新增菜单
     */
    @Before(Tx.class)
    @TxConfig(Ds.EOVA)
    public void add() {
        Record data = getJsonToRecord();

        String menuCode = get("code");
        String type = get("type");
        String icon = get("icon");
        String template = get("template");

        Menu temp = Menu.dao.findFirst("select * from eova_menu where code = ?", menuCode);
        if (temp != null) {
            renderJson(new Easy("菜单编码不能重复"));
            return;
        }
//		String icon = get("iconField");
//		if (!x.isEmpty(icon) && icon.equalsIgnoreCase("icon")) {
//			renderJson(new Easy("Tree图标字段:字段名不能为icon(系统关键字，你可以改为：icon)"));
//			return;
//		}
//		icon = get("treeGridIconField");
//		if (!x.isEmpty(icon) && icon.equalsIgnoreCase("icon")) {
//			renderJson(new Easy("Tree图标字段:字段名不能为icon(系统关键字，你可以改为：icon)"));
//			return;
//		}

        try {
            String name = get("name");
            Menu menu = new Menu();

            Map<String, String[]> paraMap = getParaMap();
            paraMap.forEach((k, v) -> {
                menu.set(k, v[0]);
            });

//			menu._setAttrs();


//			menu.set("parent_id", getParaToInt("parent_id"));
//			menu.set("icon", get("icon", ""));
//			menu.set("name", name);
//			menu.set("code", menuCode);
//			menu.set("num", getParaToInt("num"));
            if (type.equals(TemplateConfig.QUERY)) {
                // 自定义查询复用单表模版(除了自定义SQL, 其它都一样)
                menu.set("type", TemplateConfig.SINGLE_GRID);
                String sql = get("sql");
                String ds = get("ds");
                // 通过SQL生成虚拟元对象和元字段 入库
                MetaUtil.addVirtualObject(sql, menuCode, name, ds);
                String objectCode = "v_" + menuCode;
                // 默认指定通用SQL查询
                Db.use(Ds.EOVA).update("update eova_object set biz_intercept = 'com.eova.aop.impl.SqlQueryIntercept' where code = ?", objectCode);
            } else {
                menu.set("type", type);
            }
            // menu.set("biz_intercept", get("bizIntercept", ""));

            String url = get("url", "");// 自定义业务URL

            // 自定义office模版
//			String path = get("path", "");
//			menu.set("url", type.equals(TemplateConfig.DIY) ? url : path);

            // 菜单配置
//			MenuConfig config = new MenuConfig();
            // 构建菜单配置项
//			buildConfig(type, config);
//			menu.setConfig(config);
            Kv menuConfig = menu.getMenuConfig();
            menu.save();

            // 目录没有默认按钮
            if (type.equals(Menu.TYPE_DIR)) {
                OK();
                return;
            }

            ButtonFactory.create(menuCode, template);
            // 创建模版默认按钮

            // 自动授权给超管
            sm.auth.authMenuToAdmin(menuCode);

            // 新增菜单使缓存失效
            BaseCache.delSer(EovaConst.ALL_MENU);

            OK();
        } catch (Exception e) {
            e.printStackTrace();
//			renderJson(new Easy());
            NO("新增菜单失败,请仔细查看控制台日志！");
            throw new NestedTransactionHelpException("新增菜单异常");
        }

    }

    /**
     * 配置菜单
     *
     * @param type 模版类型
     * @param config
     */
    private void buildConfig(String type, MenuConfig config) {
        if (type.equals(TemplateConfig.SINGLE_GRID)) {
            // 单表
            config.setObjectCode(get("objectCode"));
        } else if (type.equals(TemplateConfig.QUERY)) {
            // 自定义查询 虚拟元对象编码
            config.setObjectCode("v_" + get("code"));
        } else if (type.equals(TemplateConfig.SINGLE_TREE)) {
            // 单表树
            config.setObjectCode(get("singleTreeObjectCode"));// TODO 此参数后续应去掉,tree配置应在一起.

            TreeConfig tc = new TreeConfig();
            tc.setObjectCode(config.getObjectCode());
            tc.setRootPid(get("rootPid"));
            tc.setIdField(get("idField", "id"));
            tc.setParentField(get("parentField"));
            tc.setTreeField(get("treeField"));
            tc.setIconField(get("iconField"));
            tc.setOrderField(get("orderField"));
            config.setTree(tc);
        } else if (type.equals(TemplateConfig.SINGLE_CHART)) {
            // 单表图
            config.setObjectCode(get("singleChartObjectCode"));

            String ys = get("singleChartY");
            List<String> ens = Arrays.asList(ys.split(","));

            // 根据字段英文名，获取字段中文名
            List<String> ycn = new ArrayList<>();
            List<MetaField> fields = MetaField.dao.queryFields(config.getObjectCode());
            for (String en : ens) {
                for (MetaField f : fields) {
                    if (f.getEn().equals(en)) {
                        ycn.add(f.getCn());
                        break;
                    }
                }
            }

            ChartConfig cc = new ChartConfig();
            cc.setType(getParaToInt("singleChartType"));
            cc.setX(get("singleChartX"));
            cc.setYunit(get("singleChartYunit"));
            cc.setY(ens);
            cc.setYcn(ycn);
            config.setChart(cc);

        } else if (type.equals(TemplateConfig.MASTER_SLAVE_GRID)) {
            // 主
            String masterObjectCode = get("masterObjectCode");
            String masterFieldCode = get("masterFieldCode");
            config.setObjectCode(masterObjectCode);
            config.setObjectField(masterFieldCode);

            // 子
            ArrayList<String> objects = new ArrayList<String>();
            ArrayList<String> fields = new ArrayList<String>();
            for (int i = 1; i <= 5; i++) {
                String slaveObjectCode = get("slaveObjectCode" + i);
                String slaveFieldCode = get("slaveFieldCode" + i);
                if (x.isOneEmpty(slaveObjectCode, slaveFieldCode)) {
                    break;
                }
                objects.add(slaveObjectCode);
                fields.add(slaveFieldCode);
            }
            config.setObjects(objects);
            config.setFields(fields);
        } else if (type.equals(TemplateConfig.TREE_GRID)) {
            // 树&表
            TreeConfig tc = new TreeConfig();
            tc.setObjectCode(get("treeGridTreeObjectCode"));
            tc.setObjectField(get("treeGridTreeFieldCode"));

            tc.setIconField(get("treeGridIconField"));
            tc.setTreeField(get("treeGridTreeField"));
            tc.setParentField(get("treeGridParentField"));
            tc.setIdField(get("treeGridIdField", "id"));
            tc.setRootPid(get("treeGridRootPid"));
            config.setTree(tc);

            config.setObjectCode(get("treeGridObjectCode"));
            config.setObjectField(get("treeGridFieldCode"));
        } else if (type.equals(TemplateConfig.OFFICE)) {
            config.getParams().put("office_type", get("office_type"));
        }

    }

    /**
     * 菜单功能管理
     */
    @Before(Tx.class)
    @TxConfig(Ds.EOVA)
    public void menuFun() {
        String menuCode = get(0);

        List<Button> btns = Button.dao.findNoQueryByMenuCode(menuCode);
        // 动态获取按钮是否禁用
        for (Button btn : btns) {
            String ck = get("btn" + btn.getInt("id"));
            // 选中表示可被分配
            if (!x.isEmpty(ck) && ck.equals("on")) {
                btn.set("is_hide", false);
            } else {
                // 未选中表示不可分配
                btn.set("is_hide", true);
            }
            btn.update();
        }

        renderJson(new Easy());
    }

    /**
     * 是否能配置基础功能
     */
    public void isFun() {
        int id = getParaToInt(0);
        Menu m = Menu.dao.findById(id);
        String type = m.getStr("type");
        // 目录和自定义功能,不能配置基本功能
        if (type.equals(Menu.TYPE_DIR) || type.equals(Menu.TYPE_DIY)) {
            renderJson(Ret.fail());
            return;
        }
        renderJson(Ret.ok());
    }

    // 菜单功能授权给角色
    public void auth() {
        int id = getInt(0);

//        HashMap<Integer, List<Button>> btnMap = new HashMap<Integer, List<Button>>();
//
//        List<Button> btns = Button.dao.findByMenuId(id);
//        for (Button b : btns) {
//            int group = b.getInt("cat");
//            List<Button> list = btnMap.get(group);
//            if (list == null) {
//                list = new ArrayList<Button>();
//                btnMap.put(group, list);
//            }
//            list.add(b);
//        }
//
//        if (btns.isEmpty()) {
//            renderMsg("当前菜单无功能可分配");
//            return;
//        }
//
//        List<Role> roles = Role.dao.findAll();
//
//        List<Record> auths = RoleBtn.dao.findByMenuId(id);
//        Menu menu = Menu.dao.findById(id);
//
//        setAttr("menu", menu);
//        setAttr("btnMap", btnMap);
//        setAttr("roles", roles);
//        setAttr("auths", auths);
        set("id", id);

        render("/eova/menu/auth/app.html");
    }

    public void authData() {
        int id = getInt("id");

        List<Button> btns = Button.dao.findByMenuId(id);

//        HashMap<Integer, List<Button>> btnMap = new HashMap<Integer, List<Button>>();
//        for (Button b : btns) {
//            int group = b.getInt("cat");
//            List<Button> list = btnMap.get(group);
//            if (list == null) {
//                list = new ArrayList<Button>();
//                btnMap.put(group, list);
//            }
//            list.add(b);
//        }

        if (btns.isEmpty()) {
            renderMsg("当前菜单无功能可分配");
            return;
        }

        List<Role> roles = Role.dao.findAll();

        List<Record> auths = RoleBtn.dao.findByMenuId(id);
        Menu menu = Menu.dao.findById(id);

//        setAttr("menu", menu);
//        setAttr("btnMap", btnMap);
//        setAttr("roles", roles);
//        setAttr("auths", auths);

        Ret ret = Ret.ok().set("menu", menu).set("btns", btns).set("auths", auths).set("roles", roles);
        renderJson(ret);
    }

    /**
     * 初始化EOVA按钮信息
     */
    @Before(Tx.class)
    @TxConfig(Ds.EOVA)
    public void initEovaButton() {

        String isUpgrade = x.conf.get("isUpgrade");
        if (x.isEmpty(isUpgrade) || !isUpgrade.equals("true")) {
            renderText("未开启升级模式，请启动配置 eova.config isUpgrade = true");
            return;
        }

        {
            String sql = "UPDATE eova_button SET is_hide = 1 WHERE menu_code = ? and name = ?";
            Db.use(Ds.EOVA).update(sql, "eova_menu", "新增");
        }
        {
            String sql = "UPDATE eova_button SET is_hide = 1 WHERE menu_code = ? and name = ?";
            Db.use(Ds.EOVA).update(sql, "eova_menu", "查看");
        }
        {
            String sql = "UPDATE eova_button SET is_hide = 1 WHERE menu_code = ? and name = ?";
            Db.use(Ds.EOVA).update(sql, "eova_task", "导入");
        }
        {
            String sql = "UPDATE eova_button SET is_hide = 1 WHERE menu_code = ? and name = ?";
            Db.use(Ds.EOVA).update(sql, "eova_object", "新增");
        }
        {
            String sql = "UPDATE eova_button SET is_hide = 1 WHERE menu_code = ? and name = ?";
            Db.use(Ds.EOVA).update(sql, "eova_object", "查看");
        }
        {
            String sql = "UPDATE eova_button SET is_hide = 1 WHERE menu_code = ? and name = ?";
            Db.use(Ds.EOVA).update(sql, "eova_object", "新增");
        }
        {
            String sql = "UPDATE eova_button SET is_hide = 1 WHERE menu_code = ? and name = ?";
            Db.use(Ds.EOVA).update(sql, "sys_auth_users", "查看");
        }
        {
            String sql = "UPDATE eova_button SET is_hide = 1 WHERE menu_code = ? and name = ?";
            Db.use(Ds.EOVA).update(sql, "sys_auth_users", "用户详细信息新增");
        }
        {
            String sql = "UPDATE eova_button SET is_hide = 1 WHERE menu_code = ? and name = ?";
            Db.use(Ds.EOVA).update(sql, "sys_auth_users", "用户详细信息删除");
        }
        {
            String sql = "UPDATE eova_button SET is_hide = 1 WHERE menu_code = ? and name = ?";
            Db.use(Ds.EOVA).update(sql, "sys_auth_role", "查看");
        }
        {
            String sql = "UPDATE eova_button SET is_hide = 1 WHERE menu_code = ? and name = ?";
            Db.use(Ds.EOVA).update(sql, "sys_auth_role", "导入");
        }
        {
            String sql = "UPDATE eova_button SET is_hide = 1 WHERE menu_code = ? and ui <> 'query'";
            Db.use(Ds.EOVA).update(sql, "sys_log");
        }

        System.err.println("初始化EOVA按钮信息成功！");

        renderText("初始化EOVA按钮信息成功！");
    }

    /**
     * 初始化Oracle类型
     * 场景:Mysql迁移到Oracle
     */
    @Before(Tx.class)
    @TxConfig(Ds.EOVA)
    public void initOracleType() {

        String isUpgrade = x.conf.get("isUpgrade");
        if (x.isEmpty(isUpgrade) || !isUpgrade.equals("true")) {
            renderText("未开启升级模式，请启动配置 eova.config isUpgrade = true");
            return;
        }

        if (!xx.isOracle()) {
            renderText("当前Eova数据源不是Oracle禁止执行");
            return;
        }

        {
            // VARCHAR2
            String sql = "UPDATE EOVA_FIELD SET DATA_TYPE_NAME = 'VARCHAR2' WHERE DATA_TYPE_NAME = 'VARCHAR' OR  DATA_TYPE_NAME = 'TEXT'";
            Db.use(Ds.EOVA).update(sql);
        }
        {
            // NUMBER
            String sql = "UPDATE EOVA_FIELD SET DATA_TYPE_NAME = 'NUMBER' WHERE DATA_TYPE_NAME LIKE '%INT%'";
            Db.use(Ds.EOVA).update(sql);
        }
        {
            // FLOAT
            String sql = "UPDATE EOVA_FIELD SET DATA_TYPE_NAME = 'NUMBER', DATA_DECIMAL = 1 WHERE DATA_TYPE_NAME LIKE '%FLOAT%'";
            Db.use(Ds.EOVA).update(sql);
        }
        {
            // DOUBLE
            String sql = "UPDATE EOVA_FIELD SET DATA_TYPE_NAME = 'NUMBER', DATA_DECIMAL = 8 WHERE DATA_TYPE_NAME LIKE '%DOUBLE%'";
            Db.use(Ds.EOVA).update(sql);
        }
        {
            // DECIMAL
            String sql = "UPDATE EOVA_FIELD SET DATA_TYPE_NAME = 'NUMBER', DATA_DECIMAL = 20 WHERE DATA_TYPE_NAME = 'DECIMAL'";
            Db.use(Ds.EOVA).update(sql);
        }
        {
            // CHAR
            String sql = "UPDATE EOVA_FIELD SET DATA_TYPE_NAME = 'CHAR', DATA_SIZE = 1 WHERE DATA_TYPE_NAME = 'BIT'";
            Db.use(Ds.EOVA).update(sql);
        }
        {
            // DATE
            String sql = "UPDATE EOVA_FIELD SET DATA_TYPE_NAME = 'DATE' WHERE DATA_TYPE_NAME LIKE '%DATE%' OR DATA_TYPE_NAME LIKE '%TIME%'";
            Db.use(Ds.EOVA).update(sql);
        }


        System.err.println("初始化Oracle类型成功！");

        renderText("初始化Oracle类型成功！");
    }

    /**
     * 初始化PostgreSql类型
     * 场景:Mysql迁移到PostgreSql
     */
    @Before(Tx.class)
    @TxConfig(Ds.EOVA)
    public void initPostgreSqlType() {

        String isUpgrade = x.conf.get("isUpgrade");
        if (x.isEmpty(isUpgrade) || !isUpgrade.equals("true")) {
            renderText("未开启升级模式，请启动配置 eova.config isUpgrade = true");
            return;
        }

        if (!xx.isPgsql()) {
            renderText("当前Eova数据源不是PostgreSql禁止执行");
            return;
        }

        {
            // INT
            String sql = "UPDATE EOVA_FIELD SET DATA_TYPE_NAME = 'INT4' WHERE DATA_TYPE_NAME LIKE '%INT%'";
            Db.use(Ds.EOVA).update(sql);
        }
        {
            // float
            String sql = "UPDATE EOVA_FIELD SET DATA_TYPE_NAME = 'FLOAT4', DATA_DECIMAL = 4 WHERE DATA_TYPE_NAME LIKE '%FLOAT%'";
            Db.use(Ds.EOVA).update(sql);
        }
        {
            // double
            String sql = "UPDATE EOVA_FIELD SET DATA_TYPE_NAME = 'FLOAT8', DATA_DECIMAL = 8 WHERE DATA_TYPE_NAME LIKE '%DOUBLE%'";
            Db.use(Ds.EOVA).update(sql);
        }
        {
            // BIT
            String sql = "UPDATE EOVA_FIELD SET DATA_TYPE_NAME = 'INT2', DATA_SIZE = 1 WHERE DATA_TYPE_NAME = 'BIT'";
            Db.use(Ds.EOVA).update(sql);
        }
        {
            // DATETIME
            String sql = "UPDATE EOVA_FIELD SET DATA_TYPE_NAME = 'TIMESTAMP', DATA_SIZE = 1 WHERE DATA_TYPE_NAME = 'DATETIME'";
            Db.use(Ds.EOVA).update(sql);
        }

        System.err.println("初始化PostgreSql类型成功！");

        renderText("初始化PostgreSql类型成功！");
    }

    public void flow() {
        render("/eova/menu/flow.html");
    }

    // 创建流程
    @Before(Tx.class)
    @TxConfig(Ds.EOVA)
    public void doFlow() {
        Integer dir = getParaToInt("dir", 0);// 默认为根目录
        String name = get("name");
        String code = get("code");
        String object_code = get("object_code");
        String object_field = get("object_field");
        String details = xx.replaceBlank(get("bs"));

        try {
            // 先生成菜单目录
            Menu menu = new Menu();
            menu.set("parent_id", dir);
            menu.set("name", name);
            menu.set("code", code);
            menu.set("type", TemplateConfig.DIR);
            menu.save();

            Integer mid = menu.getInt("id");

            // 根据命令生成菜单 待支付流程|my_orders10|10|1;
            String[] menus = details.split(";");
            for (String m : menus) {
                System.out.println(m);
                String[] ps = m.split("\\|");
                System.out.println(ps);
                String filter = String.format("%s = %s", object_field, ps[2]);
                createFlowMenu(mid, object_code, ps[0], ps[1], filter, ps[3]);
            }
        } catch (Exception e) {
            e.printStackTrace();
            renderJson(Easy.fail("创建流程失败:" + e.getMessage()));
            throw new NestedTransactionHelpException("创建流程失败");
        }

        renderJson(Easy.sucess());
    }

    private void createFlowMenu(Integer mid, String object, String name, String menuCode, String filter, String roles) {
        System.out.println("create " + menuCode);
        Menu menu = new Menu();
        menu.set("parent_id", mid);
        menu.set("name", name);
        menu.set("code", menuCode);
        menu.set("type", TemplateConfig.SINGLE_GRID);
        // 菜单状态过滤
        menu.set("filter", filter);

        // 菜单配置
        MenuConfig config = new MenuConfig();
        config.setObjectCode(object);
        menu.setConfig(config);
        menu.save();

        // 创建菜单按钮
//        new ButtonFactory(TemplateConfig.SINGLE_GRID, menuCode, config).build();

        // 默认干掉除查询和查看的按钮
        Db.use(Ds.EOVA).update("update eova_button set is_hide = 1 where menu_code = ? and name not in ('查询', '查看')", menuCode);

        // 获取查询和查看的ID, 用于自动分配
        List<Integer> ids = Db.use(Ds.EOVA).query("select id from eova_button where menu_code = ? and name in ('查询', '查看')", menuCode);

        // 分配权限
        if (x.isEmpty(roles)) {
            roles = EovaConst.ADMIN_RID + "";
        }
        for (String role : roles.split(",")) {
            ids.forEach(id -> {
                RoleBtn rb = new RoleBtn();
                rb.set("rid", role);
                rb.set("bid", id);
                rb.save();
            });
        }

        // 清菜单缓存
        BaseCache.delSer(EovaConst.ALL_MENU);
    }
}