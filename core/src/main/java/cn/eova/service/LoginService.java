package cn.eova.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import cn.eova.tools.x;
import cn.eova.auth.AuthUri;
import cn.eova.common.Ds;
import cn.eova.common.base.BaseCache;
import cn.eova.common.utils.EncryptUtil;
import cn.eova.common.utils.HttpUtils;
import cn.eova.common.utils.xx;
import cn.eova.config.EovaConst;
import cn.eova.model.Session;
import cn.eova.model.User;
import com.jfinal.json.Json;
import com.jfinal.kit.LogKit;
import com.jfinal.kit.Ret;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.ehcache.CacheKit;

/**
 * 登录服务
 */
public class LoginService {

    /**SID Cookie Key**/
    public static final String CKSID = "eovasid";
    /**SID Field Key**/
    public static final String SID = "sid";
    /**Login User Key**/
    public static final String USER = "loginUser";

    // 登录配置信息
    private String userDs = x.conf.get("login.user.ds", Ds.EOVA);
    private String userTable = x.conf.get("login.user.table", "eova_user");
    private String userId = x.conf.get("login.user.id", "id");
    private String userAccount = x.conf.get("login.user.account", "login_id");
    private String userPassword = x.conf.get("login.user.password", "login_pwd");
    private String userRid = x.conf.get("login.user.rid", "rid");

    /**
     * 根据UID查找用户
     * @param uid 类型不确定 int long String
     * @return
     */
    private User findUserByUid(Object uid) {
        Record r = Db.use(userDs).findFirst(String.format("select * from %s where %s = ?", userTable, userId), xx.ET(uid));
        return initUser(r);
    }

    /**
     * 根据用户唯一标识查找用户
     * @param loginId 用户唯一标识
     * @return
     */
    private User findUserByLoginId(String loginId) {
        return initUser(findByLoginId(loginId));
    }

    /**
     * 根据用户唯一标识查询用户(角色可能为空)
     * @param loginId
     * @return
     */
    private Record findByLoginId(String loginId) {
        return Db.use(userDs).findFirst(String.format("select * from %s where %s = ?", userTable, userAccount), loginId);
    }

    /**
     * 根据帐号获取用户
     * @param account
     * @return
     */
    private Record findUserByAccount(String account) throws Exception {

        String loginServer = x.conf.get("service.user.login");
        // 登录用户服务(外部用户获取)
        if (!x.isEmpty(loginServer)) {

            HashMap<String, String> params = new HashMap<>();
            params.put("account", account);

            // 从第三方服务获取用户数据
            Ret ret = null;
            try {
                String str = HttpUtils.cs().post(loginServer, params);
                ret = Json.getJson().parse(str, Ret.class);
            } catch (Exception e) {
                LogKit.error("服务请求异常:" + loginServer, e);
                throw new Exception("服务请求异常，请联系客服解决！");
            }

            if (ret.isFail()) {
                throw new Exception("登录失败，" + ret.getStr("msg"));
            }

            // TODO 伪Token校验, 后期进行白名单验证
            String token = EncryptUtil.getMd5(account + x.time.formatNowDate());
            if (!ret.getStr("token").equals(token)) {
                throw new Exception("登录失败，用户服务鉴权失败，请联系客服！");
            }

            // 根据UID查询用户业务数据
            // 登录服务字段配置
            String serverUid = x.conf.get("service.user.uid", "id");
            String serverPassword = x.conf.get("service.user.password", "login_pwd");
            String serverName = x.conf.get("service.user.name", "name");
            String serverCompanyId = x.conf.get("service.user.cid", "company_id");

            String val = ret.get(serverUid).toString();
            if (!x.isEmpty(val)) {
                String userDs = x.conf.get("login.user.ds", Ds.EOVA);
                String userTable = x.conf.get("login.user.table", "eova_user");

                Record user = null;
                try {
                    String sql = String.format("select * from %s where %s = ?", userTable, serverUid);
                    user = Db.use(userDs).findFirst(sql, val);
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new Exception("登录失败，查询本地用户异常!");
                }

                if (user == null) {
                    LogKit.error("未找到本地关联用户:%s=%s", serverUid, val);
                    throw new Exception("登录成功，您暂未获得本系统授权，请联系您企业的管理员为您分配角色！");
                }

                // Eova内字段配置
                String password = x.conf.get("login.user.password", "login_pwd");
                String name = x.conf.get("login.user.name", "name");
                String companyId = x.conf.get("login.user.cid", "company_id");

                // 续传登录信息(密文, 昵称, 企业ID)
                user.set(password, ret.get(serverPassword));
                user.set(name, ret.get(serverName));
                user.set(companyId, ret.get(serverCompanyId));

                return user;
            }


        }

        String sql = "select * from " + userTable + " where ";

        // 支持多字段登录
        ArrayList<Object> paras = new ArrayList<>();
        String collect = Arrays.stream(userAccount.split(",")).map(s -> {
            paras.add(account);
            return s + " = ? ";
        }).collect(Collectors.joining(" or "));

        return Db.use(userDs).findFirst(sql + collect, paras.toArray());
    }

    /**
     * 登录校验
     * @param loginId 帐号
     * @param loginPwd 密码
     * @return
     */
    private Ret loginValidate(String loginId, String loginPwd) {
        Record r = null;
        try {
            r = findUserByAccount(loginId);
        } catch (Exception e) {
            return Ret.fail(e.getMessage());
        }
        if (r == null) {
            return Ret.fail("用户名不存在");
        }
        // 加密方式可配置
        String encrypt = x.conf.get("eova.pwd.encrypt", "SM32");
        if (encrypt.equals("SM32")) {
            loginPwd = EncryptUtil.getSM32(loginPwd);
        } else {
            loginPwd = EncryptUtil.getMd5(loginPwd);
        }
        String userPassword = r.getStr(this.userPassword);
        if (x.isEmpty(userPassword) || !userPassword.equals(loginPwd)) {
            return Ret.fail("密码错误");
        }
        // 初始化用户, 并回登录源数据
        return Ret.ok().set(USER, initUser(r));

    }

    /**
     * 初始化用户
     * @param r
     * @return
     */
    public User initUser(Record r) {
        User user = new User();
        user.setData(r);// 登录源数据备用
        Object uid = r.get(userId);
        Object rid = r.get(userRid);
        if (uid == null || rid == null) {
            return null;
        }

        user.set("id", uid);
        user.set("rid", rid);
        user.put(userAccount, r.get(userAccount));// 帐号备用
        Integer orgId = r.getInt("org_id");
        Integer companyId = r.getInt("company_id");
        // 尝试自动续传企业ID
        if (companyId != null) {
            user.setOrPut("company_id", companyId);
        }
        // 尝试自动续传部门ID
        if (orgId != null) {
            user.setOrPut("org_id", orgId);
        }
        // 尝试自动续传姓名
        String name = r.getStr("name");
        if (name != null) {
            user.setOrPut("name", name);
        }

        // 初始化角色
        user.initRole();

        // 初始化权限
        AuthUri.build(user);

        return user;
    }

    /**
     * 强制用户下线(单系统)
     * @param uid
     */
    public void forceLogout(String uid) {
        xx.info("强制用户下线:" + uid);
        Session session = getLastSession(uid);
        if (session != null) {
            String sid = session.getSid();
            xx.info("销毁会话成功:" + sid);
            CacheKit.remove(BaseCache.LOGIN, sid);
            deleteSession(uid);
        }
    }

    /**
     * 强制用户下线(根据用户唯一标识)
     * @param loginId 用户唯一标识
     */
    public void forceLogoutByLoginId(String loginId) {
        Record user = findByLoginId(loginId);
        forceLogout(user.getStr(userId));
    }

    /**
     * 主动退出当前登录
     */
    public void logout(String sid) {
        if (sid != null) {
            CacheKit.remove(BaseCache.LOGIN, sid);
            Session.dao.deleteById(sid);
        }
    }

    /**
     * 第三方一键登录
     * @param keepLogin
     * @return
     */
    public Ret login(String loginId, boolean keepLogin) {
//		String userDs = x.conf.get("login.user.ds", Ds.EOVA);
//		String userTable = x.conf.get("login.user.table", "eova_user");
//		String userId = x.conf.get("login.user.id", "id");

        User user = findUserByLoginId(loginId);

        int sessionMin = x.conf.getInt("login.user.session", 120);// 单位分钟， 默认2小时
        // 如果用户勾选保持登录，过期时间为 1 年，否则为默认值
        long liveSeconds = keepLogin ? 1 * 365 * 24 * 60 * 60 : sessionMin * 60;
        // 传递给控制层的 cookie
        int maxAgeInSeconds = (int) (keepLogin ? liveSeconds : -1);
        // expireAt 用于设置 session 的过期时间点，需要转换成毫秒
        long expire = System.currentTimeMillis() + (liveSeconds * 1000);
        // 保存登录 session 到数据库
        String sid = StrKit.getRandomUUID();
        Session session = new Session(sid, user.getId(), expire);
        if (!session.save()) {
            return Ret.fail("会话保存异常，请联系管理员");
        }

        user.put(SID, sid); // SID备用

        CacheKit.put(BaseCache.LOGIN, sid, user);

        return Ret.ok().set(USER, user).set("maxAgeInSeconds", maxAgeInSeconds);
    }

    /**
     * 登录
     * @param loginId
     * @param loginPwd
     * @param keepLogin
     * @param ip
     * @return
     */
    public Ret login(String loginId, String loginPwd, boolean keepLogin, String ip) {
        loginId = loginId != null ? loginId.trim() : null;
        loginPwd = loginId != null ? loginPwd.trim() : null;

        Ret ret = loginValidate(loginId, loginPwd);
        if (ret.isFail()) {
            return ret;
        }

        User user = (User) ret.get(USER);

        int sessionMin = x.conf.getInt("login.user.session", 120);// 单位分钟， 默认2小时
        // 如果用户勾选保持登录，过期时间为 1 年，否则为默认值
        long liveSeconds = keepLogin ? 1 * 365 * 24 * 60 * 60 : sessionMin * 60;
        // 传递给控制层的 cookie
        int maxAgeInSeconds = (int) (keepLogin ? liveSeconds : -1);
        // expireAt 用于设置 session 的过期时间点，需要转换成毫秒
        long expire = System.currentTimeMillis() + (liveSeconds * 1000);
        // 保存登录 session 到数据库
        String sid = StrKit.getRandomUUID();
        Session session = new Session(sid, user.getId(), expire);
        if (!session.save()) {
            return Ret.fail("会话保存异常，请联系管理员");
        }

        user.put(SID, sid); // SID备用

        CacheKit.put(BaseCache.LOGIN, sid, user);

        loginLog(user.getId(), ip);

        return Ret.ok().set(USER, user).set("maxAgeInSeconds", maxAgeInSeconds);
    }


    /**
     * 通过 sessionId 获取登录用户信息
     * sessoin表结构：session(id, uid, expire)
     *
     * 1：先从缓存里面取，如果取到则返回该值，如果没取到则从数据库里面取
     * 2：在数据库里面取，如果取到了，则检测是否已过期，如果过期则清除记录，
     *     如果没过期则先放缓存一份，然后再返回
     */
    public User loginBySid(String sid, String ip) {
        Session session = Session.dao.findById(sid);
        if (session == null) {      // session 不存在
            return null;
        }
        if (session.isExpired()) {  // session 已过期
            session.delete();        // 被动式删除过期数据，此外还需要定时线程来主动清除过期数据
            return null;
        }

        User user = findUserByUid(session.getUid());
        if (user != null) {//  && user.getStatus()
            user.put(SID, sid); // 备份SID
            CacheKit.put(BaseCache.LOGIN, sid, user);

            loginLog(user.getId(), ip);
            return user;
        }
        return null;
    }

    /**
     * 获取已登录用户
     * @param sid
     * @return
     */
    public User getLoginUser(String sid) {
        return CacheKit.get(BaseCache.LOGIN, sid);
    }

    private void loginLog(Object uid, String ip) {
        Record loginLog = new Record().set("user_id", uid).set("ip", ip);
        Db.use(Ds.EOVA).save("eova_login_log", loginLog);
    }

    /**
     * 更新登录用户
     * @param user
     */
    public void update(User user) {
        String sid = user.get(SID);
        CacheKit.put(BaseCache.LOGIN, sid, user);
    }

    //	/**
    //	 * 重载用户
    //	 * @param user
    //	 */
    //	public void reload(User user) {
    //		String sid = user.get(SID);
    //		User dbUser = userDao.findById(user.getId());
    //		dbUser.put(SID, sid); // 备份SID
    //
    //		// 集群方式下，要做一通知其它节点的机制，让其它节点使用缓存更新后的数据，
    //		// 将来可能把 user 用 id : obj 的形式放缓存，更新缓存只需要 CacheKit.remove("account", id) 就可以了，
    //		// 其它节点发现数据不存在会自动去数据库读取，所以未来可能就是在 AccountService.getById(int id)的方法引入缓存就好
    //		// 所有用到 user 对象的地方都从这里去取
    //		CacheKit.put(BaseCache.LOGIN, sid, user);
    //	}

    /**
     * 已废弃
     * @param user 当前登录用户数据
     * @throws Exception
     */
    @Deprecated
    public void initAuth(User user) {
        // 初始化获取授权信息
        Set<String> auths = new HashSet<String>();
        String sql = "SELECT b.id,b.bs FROM eova_role_btn rf LEFT JOIN eova_button b ON rf.bid = b.id WHERE rf.rid = ?";
        List<Object> paras = new ArrayList<Object>();
        paras.add(user.getRid());
        // 用户分多业务端
        if (EovaConst.SYS_BIZ != 0) {
            sql += String.format(" and rf.%s = ?", EovaConst.USER_SYS_FIELD);
            paras.add(EovaConst.SYS_BIZ);
        }
        List<Record> bss = Db.use(Ds.EOVA).find(sql, paras.toArray());
        for (Record r : bss) {
            String bs = r.getStr("bs");
            if (x.isEmpty(bs)) {
                continue;
            }
            if (!bs.contains(";")) {
                auths.add(bs);
                continue;
            }
            String[] strs = bs.split(";");
            for (String str : strs) {
                auths.add(str);
            }
        }
        if (x.isEmpty(auths)) {
            LogKit.error("用户角色没有任何授权,请联系管理员授权");
        }
        user.put("auths", auths);
    }


    /**
     * 获取最近一次会话
     * @param uid
     * @return
     */
    private Session getLastSession(String uid) {
        return Session.dao.findFirst("SELECT * FROM eova_session where user_id = ? order by expire desc", uid);
    }

    /**
     * 清理用户所有会话
     * @param uid
     */
    private void deleteSession(String uid) {
        Db.use(Ds.EOVA).delete("DELETE FROM eova_session where user_id = ?", uid);
    }
}
