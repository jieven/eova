package com.eova.common.base;

import java.util.List;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;
import com.eova.common.utils.xx;

@SuppressWarnings("rawtypes")
public class BaseModel<M extends Model> extends Model<M> {

	private static final long serialVersionUID = 1702469565872353932L;

	/**
	 * 重写父类查询缓存方法(基础数据缓存30分钟)
	 */
	@Override
	public List<M> findByCache(String cacheName, Object key, String sql) {
		// Base数据缓存30Min
		if (xx.contains(sql, "from base_")) {
			cacheName = BaseCache.BASE;
		}
		return super.findByCache(cacheName, key, sql);
	}

	/**
	 * 查询当前Model所有数据
	 * @return
	 */
	public List<M> queryAll() {
		return super.find("select * from " + this.getClass().getSimpleName().toLowerCase());
	}

	/**
	 * 查询当前Model所有数据并Cache
	 * @return
	 */
	public List<M> queryAllByCache() {
		String sql = "select * from " + this.getClass().getSimpleName().toLowerCase();
		return super.findByCache(BaseCache.SER, sql, sql);
	}

	/**
	 * 根据主键获取对象(cache 3 min)
	 * @param id 主键
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public M getByCache(Object id) {
		String key = this.getClass().getSimpleName() + "_" + id;
		// get by cache
		M me = (M) BaseCache.getSer(key);
		if (me == null) {
			// get by db
			me = super.findById(id);
			if (me != null) {
				// add to service cache
				BaseCache.putSer(key, me);
			}
		}
		return me;
	}

	/**
	 * 查询自动缓存
	 * @param sql
	 * @return
	 */
	public List<M> queryByCache(String sql) {
		// 查询SQL作为Key值
		return super.findByCache(BaseCache.SER, sql, sql);
	}

	/**
	 * 查询自动缓存
	 * @param sql
	 * @param paras
	 * @return
	 */
	public List<M> queryByCache(String sql, Object... paras) {
		// sql_xx_xx_xx
		String key = sql;
		for (Object obj : paras) {
			key += "_" + obj.toString();
		}
		return super.findByCache(BaseCache.SER, key, sql, paras);
	}

	/**
	 * 缓存分页查询
	 * @param pageNumber 页码
	 * @param pageSize 数量
	 * @param select 查询前缀
	 * @param sqlExceptSelect 查询条件
	 * @return
	 */
	public Page<M> pagerByCache(int pageNumber, int pageSize, String select, String sqlExceptSelect) {
		String key = select + sqlExceptSelect + "_" + pageNumber + "_" + pageSize;
		return super.paginateByCache(BaseCache.SER, key, pageNumber, pageSize, select, sqlExceptSelect);
	}

	/**
	 * 缓存分页查询
	 * @param pageNumber 页码
	 * @param pageSize 数量
	 * @param select 查询前缀
	 * @param sqlExceptSelect 查询条件
	 * @param paras SQL参数
	 * @return 
	 */
	public Page<M> pagerByCache(int pageNumber, int pageSize, String select, String sqlExceptSelect, Object... paras) {
		String key = select + sqlExceptSelect + "_" + pageNumber + "_" + pageSize;
		for (Object obj : paras) {
			key += "_" + obj.toString();
		}
		return super.paginateByCache(BaseCache.SER, key, pageNumber, pageSize, select, sqlExceptSelect, paras);
	}

	/**
	 * 是否存在
	 * @param sql
	 * @param paras
	 * @return
	 */
	public boolean isExist(String sql, Object... paras){
		long count = Db.queryLong(sql, paras);
		if (count != 0) {
			return true;
		}
		return false;
	}
}
