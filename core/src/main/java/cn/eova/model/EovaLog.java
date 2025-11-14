/**
 * Copyright (c) 2015-2026 EOVA.CN. All rights reserved.
 * Licensed under the LGPL-3.0 license
 * For authorization, please contact: admin@eova.cn
 */
package cn.eova.model;

import cn.eova.common.base.BaseController;
import cn.eova.common.base.BaseModel;
import cn.eova.common.utils.web.RequestUtil;
import com.jfinal.core.Controller;

/**
 * 系统操作日志
 *
 * @author Jieven
 * @date 2014-9-10
 */
public class EovaLog extends BaseModel<EovaLog> {

    private static final long serialVersionUID = -1592533967096109392L;

    public static final EovaLog dao = new EovaLog().dao();

    /** 新增 **/
    public static final int ADD = 1;
    /** 修改 **/
    public static final int UPDATE = 2;
    /** 删除 **/
    public static final int DELETE = 3;
    /** 导入 **/
    public static final int IMPORT = 4;
    /** 隐藏 **/
    public static final int HIDE = 5;

    /**
     * 操作日志
     * @param con
     * @param info 日志详情
     */
    public void info(Controller ctrl, int type, String info) {
        EovaLog el = new EovaLog();
        // TYPE
        el.set("type", type);
        // UID
        User user = ((BaseController) ctrl).getUser();
        el.set("user_id", user.get("id"));
        // IP
        String ip = RequestUtil.getIp(ctrl.getRequest());
        el.set("ip", ip);
        el.set("info", info);
        el.save();
    }
}