package cn.eova.interceptor;

import java.sql.Timestamp;

import cn.eova.common.base.BaseController;
import cn.eova.common.utils.util.ExceptionUtil;
import cn.eova.common.utils.web.WebUtil;
import cn.eova.model.User;
import cn.eova.tools.x;
import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.core.ActionException;
import com.jfinal.core.Controller;
import com.jfinal.kit.LogKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

public class ExceptionInterceptor implements Interceptor {

    @Override
    public void intercept(Invocation inv) {
        Controller ctrl = inv.getController();

        try {
            inv.invoke();
        } catch (Exception e) {
            // 仅处理 HTTP 500 异常
            if (e instanceof ActionException) {
                ActionException ae = (ActionException) e;
                if (ae.getErrorCode() != 500) {
                    ctrl.renderError(ae.getErrorCode());
                    return;
                }
            }

            LogKit.error(e.getMessage(), e);

            String uri = ctrl.getRequest().getRequestURI();
            String paras = ctrl.getRequest().getQueryString();

            String url = uri;
            if (!x.isEmpty(paras)) {
                url += "?" + paras;
            }
            if (url.length() > 250) {
                url = url.substring(0, 250);
            }

            String info = ExceptionUtil.getStackTrace(e);
            String type = e.getClass().getName();
            String msg = e.getMessage();

            // 截短消息
            if (!x.isEmpty(msg) && msg.length() > 500) {
                msg = msg.substring(0, 500);
            }

            String ip = WebUtil.getRealIp(ctrl.getRequest());
            String uid = null;

            if (ctrl instanceof BaseController) {
                BaseController base = (BaseController) ctrl;
                User user = base.getUser();
                if (user != null) {
                    uid = base.UID() + "";
                }
            }

            // 判断N分钟内是否已存在相同报错日志
            int min = x.conf.getInt("eova.exception.min", 60);
            Timestamp beforeTime = x.time.calcMin(-min);
            Long id = Db.use(x.DS_EOVA).queryLong("select max(id) from eova_exception where create_time > ? and url = ? and info = ?", beforeTime, url, info);
            if (id != null && id > 0) {
                // 叠加计数, 不重复保存
                Db.use(x.DS_EOVA).update("update eova_exception set num = num+1 where id = ?", id);
            } else {
                // 保存异常
                Record o = new Record();
                o.set("ip", ip);
                o.set("uid", uid);
                o.set("url", url);
                o.set("type", type);
                o.set("msg", msg);
                o.set("info", info);
                Db.use(x.DS_EOVA).save("eova_exception", o);
            }

            ctrl.renderError(500);
        }
    }

}
