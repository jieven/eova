package cn.eova.api.sys;

import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import cn.eova.core.api.BaseApi;
import com.jfinal.kit.Kv;

/**
 * Demo API
 * 语法测试与验证
 *
 * @author Jieven
 */
public class DemoApi extends BaseApi {

    public void ok1() {
        System.out.println("DemoApi ok1 ...........");

        OK();
    }

    public void ok2() {
        System.out.println("DemoApi ok2 ...........");
        JSONObject tp = new JSONObject();
        tp.put("test1", 111);
        tp.put("test2", 222);
        tp.put("test3", 3333);
        OK(tp);
    }

    public void no1() {
        System.out.println("DemoApi no1 ...........");
        NO("业务异常,balabala");// try
    }

    public void no2() {
        System.out.println("DemoApi no2 ...........");
        NO("IP地址不在白名单之内", 10040);// try
    }

    public void err() {
        System.out.println("DemoApi error ...........");

        String a = null;
        System.out.println(a.length());

        OK();
    }

    public void post() {
        // 参数快速获取 JFinal 风格 推荐+1
        Kv kv = getKv();
        List<Kv> kvs = getKvs();
        // 参数快速获取 FastJson 风格
        JSONObject json = getJson();
        JSONArray array = getJsonArray();

        // 其他非json, 例如文件, 自行封装解析
        String str = get("_data_str");

        System.out.println(kv);
        System.out.println(kv.getStr("id") + " -> " + kv.getStr("name"));

        OK();
    }

}
