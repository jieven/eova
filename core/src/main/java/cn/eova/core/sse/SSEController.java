package cn.eova.core.sse;

import cn.eova.common.base.BaseController;
import cn.eova.common.utils.web.SseKit;
import com.jfinal.kit.Ret;

public class SSEController extends BaseController {
    // 开启sse连接
    public void index() {
        // System.out.println("start sse:UID=" + UID());
        SseKit.startAsync(UID() + "", this);
    }

    // 关闭sse连接
    public void close() {
        // 主动关闭连接
        // System.out.println("close sse:UID=" + UID());
        SseKit.remove(UID() + "");
        renderJson(Ret.ok("OK"));
    }

}