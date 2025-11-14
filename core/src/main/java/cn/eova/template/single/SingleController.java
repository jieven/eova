/**
 * Copyright (c) 2015-2026 EOVA.CN. All rights reserved.
 * Licensed under the LGPL-3.0 license
 * For authorization, please contact: admin@eova.cn
 */
package cn.eova.template.single;

import java.util.List;

import cn.eova.tools.x;
import cn.eova.aop.AopContext;
import cn.eova.common.base.BaseController;
import cn.eova.common.utils.io.ClassUtil;
import cn.eova.common.utils.io.FileUtil;
import cn.eova.core.menu.config.MenuConfig;
import cn.eova.i18n.I18NBuilder;
import cn.eova.model.Button;
import cn.eova.model.Menu;
import cn.eova.model.MetaObject;
import cn.eova.model.User;
import cn.eova.service.sm;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.upload.UploadFile;

/**
 * 业务模版：单表(DataGrid)
 *
 * @author Jieven
 *
 */
public class SingleController extends BaseController {

    final Controller ctrl = this;

    /** 自定义拦截器 **/
    protected SingleIntercept intercept = null;

    public void list() {

        String menuCode = this.get(0);

        // 获取元数据
        Menu menu = Menu.dao.findByCode(menuCode);
        MenuConfig config = menu.getConfig();
        String objectCode = config.getObjectCode();
        MetaObject object = MetaObject.dao.getByCode(objectCode);
        if (object == null) {
            throw new RuntimeException("元对象不存在,请检查是否存在?元对象编码=" + objectCode);
        }

        // 根据权限获取功能按钮
        User user = this.getUser();
        List<Button> btnList = Button.dao.queryByMenuCode(menuCode, user.getRid());

        // 是否需要显示快速查询
        setAttr("isQuery", MetaObject.dao.isExistQuery(objectCode));

        setAttr("menu", menu);
        setAttr("btnList", btnList);
        setAttr("object", object);

        // TODO  test
        //		List<Record> list = Db.find("select * from users limit 0,30");
        //		setAttr("list", JsonKit.toJson(list));

        String template = x.conf.get("ui.template.single", "/eova/template/single/list.html");
        String menuTemplate = config.get("template");
        if (menuTemplate != null) {
            template = menuTemplate;
        }
        render(template);
    }

    public void importXls() {
        String menuCode = this.get(0);

        Menu menu = Menu.dao.findByCode(menuCode);
        MetaObject object = sm.meta.getMeta(menu.getConfig().getObjectCode());

        boolean info = true;

        // 默认构造导出当前空数据[主键=0]模版
        String url = String.format("/grid/export/%s?type=xls&query_%s=0", object.getCode(), object.getPk());
        try {
            String importXls = menu.getConfig().get("import_xls");
            if (!x.isEmpty(importXls)) {
                url = importXls;
                info = false;
            }
        } catch (Exception e) {
        }

        // 显示导入提示(增量和覆盖)
        setAttr("info", info);
        // 导入处理URI
        setAttr("action", "/single_grid/doImportXls/" + menuCode);
        // 下载模版URL
        setAttr("template", url);

        render("/eova/template/common/import.html");
    }

    // 方便自定义
    public void diyImportXls() {

    }

    public void doImportXls() throws Exception {

        String menuCode = this.get(0);

        // 获取元数据
        Menu menu = Menu.dao.findByCode(menuCode);
        MenuConfig config = menu.getConfig();
        String objectCode = config.getObjectCode();
        final MetaObject object = sm.meta.getMeta(objectCode);

        intercept = ClassUtil.newClass(menu.getBizIntercept());

        // 默认上传到/temp 临时目录
        final UploadFile file = getFile("upfile", "/temp");
        if (file == null) {
            uploadCallback(false, I18NBuilder.get("上传失败，文件不存在"));
            return;
        }

        // 获取文件后缀
        String suffix = FileUtil.getFileType(file.getFileName());
        if (!suffix.equals(".xls")) {
            uploadCallback(false, I18NBuilder.get("请导入.xls格式的Excel文件"));
            return;
        }

        // 事务(默认为TRANSACTION_READ_COMMITTED)
        SingleAtom atom = new SingleAtom(file.getFile(), object, intercept, ctrl);
        boolean flag = Db.use(object.getDs()).tx(atom);

        if (!flag) {
            atom.getRunExp().printStackTrace();
            uploadCallback(false, atom.getRunExp().getMessage());
            return;
        }

        // 导入成功之后
        if (intercept != null) {
            try {
                AopContext ac = new AopContext(ctrl, atom.getRecords());
                intercept.importSucceed(ac);
            } catch (Exception e) {
                e.printStackTrace();
                uploadCallback(false, e.getMessage());
                return;
            }
        }

        uploadCallback(true, I18NBuilder.get("导入成功"));
    }

}