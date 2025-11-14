package cn.eova.meta;

import cn.eova.meta.api.UserApi;
import cn.eova.core.api.ApiRoutes;

/**
 * 应用接口
 *
 * @author Jieven
 */
public class AppApiRoutes extends ApiRoutes {

    public void config() {
        super.config();

        // 用户接口
        add("/demo/user", UserApi.class);
    }

}
