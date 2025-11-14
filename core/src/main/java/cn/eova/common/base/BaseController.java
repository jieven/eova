/**
 * Copyright (c) 2015-2026 EOVA.CN. All rights reserved.
 * Licensed under the LGPL-3.0 license
 * For authorization, please contact: admin@eova.cn
 */
package cn.eova.common.base;

import java.util.HashSet;
import java.util.List;

import cn.eova.tools.x;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import cn.eova.common.utils.jfinal.RecordUtil;
import cn.eova.common.utils.xx;
import cn.eova.model.User;
import cn.eova.service.LoginService;
import cn.eova.service.sm;
import com.jfinal.core.Controller;
import com.jfinal.core.NotAction;
import com.jfinal.json.Json;
import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.render.TemplateRender;

public class BaseController extends Controller {

    protected String http(String domain) {
        return "http://" + x.conf.get(domain);
    }

    @NotAction
    public int UID() {
        // 字符串ID自行获取
        return (Integer) getUser().getId();
    }

    @NotAction
    public int RID() {
        return getUser().getRid();
    }

    @NotAction
    public int CID() {
        return getUser().getCompanyId();
    }

    @NotAction
    public String SID() {
        // Request域优先于 Cookie域
        String sid = getAttr(LoginService.SID);
        if (sid == null) {
            sid = getCookie(LoginService.CKSID);
        }
        if (sid == null) {
            sid = get(LoginService.SID);
        }
        // 开发环境配置固定SID(方便测试)
        if (sid == null) {
            StringBuffer url = getRequest().getRequestURL();
            if (url.toString().startsWith(x.conf.get("dev.domain", ""))) {
                sid = x.conf.get("dev.sid");
            }
//            x.conf.getBool("devMode", false)
        }


        return sid;
    }

    @NotAction
    public User getUser() {
        return sm.login.getLoginUser(SID());
    }

    @NotAction
    public void updateUser(User user) {
        sm.login.update(user);
    }

    /**
     * <pre>
     * 获取选中行某列的值
     * 多选且为整型:1,2,3
     * 多选且为字符:'a','b','c'
     * PS:多选的处理方便取值后直接in
     * </pre>
     * @param field 字段名
     */
    protected String getSelectValue(String field) {
        // 单选
        List<JSONObject> rows = getSelectRows();
        if (rows.isEmpty()) {
            return null;
        }
        if (rows.size() == 1) {
            return rows.get(0).getString(field);
        }

        boolean isNum = true;
        // 多选
        HashSet<String> set = new HashSet<>();
        for (int i = 0; i < rows.size(); i++) {
            Object val = rows.get(i).get(field);
            set.add(val.toString());
            if (!x.isNum(val))
                isNum = false;
        }
        if (isNum) {
            return xx.join(set);
        } else {
            return xx.join(set, "'", ",");
        }
    }

    protected String getSelectValue(String field, String def) {
        String val = getSelectValue(field);
        return x.isEmpty(val) ? def : val;
    }

    protected Integer getSelectValueToInt(String field) {
        return x.toInt(getSelectValue(field));
    }

    /**
     * 获取选中行
     *
     * @return
     */
    protected JSONObject getSelectRow() {
        String json = get("row");
        if (json == null) {
            return getSelectRows().get(0);
        }
        return JSONObject.parseObject(json);
    }

    /**
     * 获取自定义按钮提交的所有选中行
     *
     * @return
     */
    //	protected JSONArray getSelectRows() {
    //		String json = get("rows");
    //		JSONArray o = JSONObject.parseArray(json);
    //		return o;
    //	}
    protected List<JSONObject> getSelectRows() {
        String json = get("rows");
        return JSONObject.parseArray(json, JSONObject.class);
    }

    /**
     * 获取提交表单时的自定义数据
     *
     * @return
     */
    protected String getEovaData() {
        return get("eova_data");
    }

    protected JSONObject getEovaDataToJSONObject() {
        return JSON.parseObject(getEovaData());
    }

    protected JSONArray getEovaDataToJSONArray() {
        return JSON.parseArray(getEovaData());
    }


    /**
     * 获取按钮输入框的值
     * <pre>
     * Integer id = getSelectValue("id");
     *
     * if (x.isEmpty(val)) {
     *     renderJson(new Easy("参数不能为空！"));
     *     return;
     * }
     *
     * Db.update("update xxx set xx = ? where id = ?", val, id);
     * renderJson(Easy.sucess());
     *</pre>
     */
    protected String getInputValue() {
        String input = get("input");
        if (x.isEmpty(input)) {
            Record r = getJsonToRecord();
            input = r.getStr("input");
        }
        return input.trim();
    }

    protected JSONObject getJsonObj() {
        String s = getRawData();
        if (x.isEmpty(s)) {
            return null;
        }
        return JSON.parseObject(s);
    }

    @NotAction
    public JSON getJson() {
        String s = getRawData();
        if (x.isEmpty(s)) {
            return null;
        }
        return (JSON) JSON.parse(s);
    }

    protected List<JSONObject> getJsons(String name) {
        JSONObject o = getJsonObj();
        JSONArray rows = o.getJSONArray(name);
        return rows.toJavaList(JSONObject.class);
    }

    protected List<Record> getJsonToRecords(String name) {
        JSONObject jo = getJsonObj();
        return RecordUtil.parseArray(jo.getJSONArray(name).toJSONString());
    }

    /**
     * 获取JSON集合转Record集合
     * @return
     */
    protected List<Record> getRecords() {
        JSONArray list = (JSONArray) getJson();
        return RecordUtil.parseArray(list.toJSONString());
    }

    /**
     * 获取提交的JSON 转 Ret
     * @return
     */
    protected Ret getJsonToRet() {
        String s = getRawData();
        if (x.isEmpty(s)) {
            return null;
        }

        return Json.getJson().parse(s, Ret.class);
    }

    /**
     * 获取提交的JSON 转 Record
     * @return
     */
    @NotAction
    public Record getJsonToRecord() {

        JSONObject json = (JSONObject) getJson();
        if (json == null) {
            return null;
        }
        Record r = new Record();
        // 将 JSONObject 的所有键值对添加到 Record 中
        json.keySet().forEach(key -> {
            Object value = json.get(key);
            r.set(key, value);
        });
        return r;
        // return JSONObject.parseObject(getRawData());
    }

    /**
     * 快速GET Request请求参数
     * @param modelClass
     * @param fields
     * @return
     */
    protected <T extends Model> T getModel(Class<? extends Model> modelClass, String... fields) {
        Model<?> m = null;
        try {
            m = modelClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        for (String field : fields) {
            if (!field.contains(" ")) {
                m.set(field, get(field));
                continue;
            }
            String[] ss = field.split("\\s+");
            String type = ss[0];
            String col = ss[1];
            Object val = "";
            if (type.equals("int")) {
                val = getInt(col);
            } else if (type.equals("long")) {
                val = getLong(col);
            } else if (type.equals("double")) {
                val = x.toDouble(get(col));
            } else if (type.equals("boolean")) {
                val = getBoolean(col);
            } else if (type.equals("date")) {
                val = getDate(col);
            } else {
                val = get(col);
            }
            m.set(col, val);
        }

        return (T) m;
    }

    @NotAction
    public void renderMsg(String msg) {
        String style = "<link rel=\"stylesheet\" href=\"/eova/plugins/eova/css/eova.render.css\">";
        renderHtml(style + "<div class='eova-msg-error'>" + msg + "</div>");
    }

    @NotAction
    public void OK() {
        renderJson(Ret.ok());
    }

    @NotAction
    public void OK(String msg) {
        renderJson(Ret.ok(msg));
    }

    @NotAction
    public void NO(String msg) {
        // renderJson(Ret.fail("msg", msg));
        renderJson(Ret.fail(msg));
    }

    @NotAction
    public void renderEnjoy(String view) {
        render(new TemplateRender(view));
    }

//    @NotAction
//    public void renderBeetl(String view) {
//        render(EovaConfig.rf.getRender(view));
//    }

    @NotAction
    public void render(String view) {
        // 如果是Enjoy模式, 自动格式化Path, 自动命中指定目录
        // /eova/menu/add.html -> /eova/_view/menu/add/index.html
        // /eova/menu/update.html -> /eova/_view/menu/update/index.html
        if (view.startsWith("/eova/")) {
            view = view.replaceFirst("/eova/", "/eova/_view/");
            // view = view.replaceFirst(".html", "/app.html");
        }
        renderTemplate(view);
    }

    /**
     * IFrame伪异步上传JSONP回调
     * @param succeed
     * @param msg
     */
    @NotAction
    public void uploadCallback(boolean succeed, String msg) {
        renderHtml("<script>parent.callback(\"" + msg + "\", " + succeed + ");</script>");
    }

}