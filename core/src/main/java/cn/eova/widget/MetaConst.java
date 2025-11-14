package cn.eova.widget;

import java.util.HashMap;

import cn.eova.engine.EovaExp;
import cn.eova.engine.EovaExpConfig;
import cn.eova.model.User;

/**
 *
 *
 * @author Jieven
 */
public class MetaConst {

    private static final String META_FIND = "eova_find@";

    public static HashMap<String, EovaExpConfig> exps = new HashMap<>();

    static {
        {
            EovaExpConfig meta = new EovaExpConfig("users");
            meta.setSql("select id, nickname 名称, status 状态, info 备注, login_id 密码 from users");
            meta.setDs("main");
            meta.setValField("id");
            meta.setTxtField("nickname");

            exps.put("users", meta);
        }
        {
            EovaExpConfig meta = new EovaExpConfig("eova_user");
            meta.setSql("select id ID,login_id 帐号 from eova_user");
            meta.setDs("eova");
            meta.setValField("id");
            meta.setTxtField("login_id");

            exps.put("eova_user", meta);
        }
    }

    public static String getCode(String objectCode) {
        String code = objectCode.replaceAll(META_FIND, "");
        return code;
    }


    public static EovaExp getExp(User user, String objectCode) {
        String expKey = objectCode.replaceAll(META_FIND, "");

        // 构建表达式
        EovaExpConfig me = MetaConst.exps.get(expKey);
//        EovaExpBuilder eeb = new EovaExpBuilder(user, me.getSql());
        //EovaExp se = eeb.build();

        //return new EovaExp(me);
        return null;
    }
}
