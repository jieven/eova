package cn.eova.meta.api;

import java.util.Date;
import java.util.List;

import cn.eova.core.api.BaseApi;
import com.jfinal.kit.Kv;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

/**
 * 用户接口
 *
 * @author Jieven
 */
public class UserApi extends BaseApi {

    // 查询新用户
    public void query() {
        System.out.println("用户查询");
        List<Record> list = Db.find("select id, login_id, nickname from users order by id desc limit 1");
        OK(list);
    }

    // 新增用户
    public void add() {
        Kv kv = getKv();

        String loginId = kv.getStr("login_id");
        String nickname = kv.getStr("nickname");

        Record user = new Record();
        user.set("nickname", nickname);
        user.set("login_id", loginId);
        user.set("login_pwd", "000000");
        user.set("reg_time", new Date());
        Db.save("users", user);

        OK();
    }

}
