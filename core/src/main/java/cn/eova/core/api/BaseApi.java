package cn.eova.core.api;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import cn.eova.common.base.BaseController;
import com.jfinal.core.NotAction;
import com.jfinal.json.Json;
import com.jfinal.kit.Kv;

/**
 * @author Jieven
 */
public class BaseApi extends BaseController {

    @NotAction
    public void OK(Object data) {
        renderJson(ApiResponse.OK(data));
    }

    @NotAction
    public void OK() {
        renderJson(ApiResponse.OK(null));
    }

    @NotAction
    public void NO(String msg) {
        renderJson(ApiResponse.NO(msg));
    }

    @NotAction
    public void NO(String msg, int code) {
        renderJson(ApiResponse.NO(code, msg));
    }

    @NotAction
    public JSONObject getJson() {
        return JSONObject.parseObject(getAttr("_data_str"));
    }

    @NotAction
    public Kv getKv() {
        return Json.getJson().parse(getJson().toJSONString(), Kv.class);
    }

    @NotAction
    public JSONArray getJsonArray() {
        return JSONArray.parseArray(getAttr("_data_str"));
    }

    @NotAction
    public List<Kv> getKvs() {
        List<Kv> kvs = new ArrayList<>();

        JSONArray arrs = getJsonArray();
        for (int i = 0; i < arrs.size(); i++) {
            kvs.add(Json.getJson().parse(arrs.getJSONObject(i).toJSONString(), Kv.class));
        }
        return kvs;
    }

}
