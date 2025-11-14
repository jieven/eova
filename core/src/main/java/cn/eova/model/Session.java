/**
 * 请勿将俱乐部专享资源复制给其他人，保护知识产权即是保护我们所在的行业，进而保护我们自己的利益
 * 即便是公司的同事，也请尊重 JFinal 作者的努力与付出，不要复制给同事
 * 
 * 如果你尚未加入俱乐部，请立即删除该项目，或者现在加入俱乐部：http://jfinal.com/club
 * 
 * 俱乐部将提供 jfinal-club 项目文档与设计资源、专用 QQ 群，以及作者在俱乐部定期的分享与答疑，
 * 价值远比仅仅拥有 jfinal club 项目源代码要大得多
 * 
 * JFinal 俱乐部是五年以来首次寻求外部资源的尝试，以便于有资源创建更加
 * 高品质的产品与服务，为大家带来更大的价值，所以请大家多多支持，不要将
 * 首次的尝试扼杀在了摇篮之中
 */

package cn.eova.model;

import cn.eova.common.base.BaseModel;

/**
 * session 存放在数据库中，并引入 cache 中间层，优点如下：
 * 1：简单且高性能
 * 2：支持分布式与集群
 * 3：支持服务器断电和重启
 * 4：支持 tomcat、jetty 等运行容器重启
 */
public class Session extends BaseModel<Session> {

	private static final long serialVersionUID = 1L;
	public static final Session dao = new Session().dao();

	public Session() {
	}

	public Session(String sid, Object uid, long expire) {
		this.set("id", sid);
		this.set("user_id", uid);
		this.set("expire", expire);
	}

	/**
	 * 会话是否已过期
	 */
	public boolean isExpired() {
		return getLong("expire") < System.currentTimeMillis();
	}

	public Object getUid() {
		return get("user_id");
	}

	public String getSid() {
		return getStr("id");
	}

}
