/**
 * Copyright (c) 2015-2026 EOVA.CN. All rights reserved.
 * Licensed under the LGPL-3.0 license
 * For authorization, please contact: admin@eova.cn
 */
package cn.eova.common.base;

import java.util.List;

import cn.eova.tools.x;
import com.alibaba.druid.DbType;
import cn.eova.common.utils.db.SqlUtil;
import cn.eova.config.EovaDataSource;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.DbKit;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Table;
import com.jfinal.plugin.activerecord.TableMapping;

public class BaseModel<M extends Model> extends Model<M> {

    private static final long serialVersionUID = 1702469565872353932L;

    public void execute(String sql, Object... paras) {
        String ds = this._getConfig().getName();
        Db.use(ds).update(sql, paras);
    }

    public void execute(String sql) {
        execute(sql, new Object[0]);
    }

    @Override
    public List<M> findByCache(String cacheName, Object key, String sql) {
        // Base数据缓存30Min
        // if (sql.contains("from base_")) {
        // cacheName = BaseCache.BASE;
        // }
        return super.findByCache(cacheName, key, sql);
    }

    @Override
    public List<M> findByCache(String cacheName, Object key, String sql, Object... paras) {
        return super.findByCache(cacheName, key, sql, paras);
    }

    /**
     * 根据主键获取对象
     *
     * @param id 主键
     * @return
     */
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
     *
     * @param sql
     * @return
     */
    public List<M> queryByCache(String sql) {
        // 查询SQL作为Key值
        return findByCache(BaseCache.SER, sql, sql);
    }

    /**
     * 查询自动缓存
     *
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
        return findByCache(BaseCache.SER, key, sql, paras);
    }

    /**
     * 缓存查询第一项
     *
     * @param sql
     * @return
     */
    public M queryFisrtByCache(String sql) {
        List<M> list = queryByCache(sql);
        if (x.isEmpty(list)) {
            return null;
        }
        return list.get(0);
    }

    /**
     * 缓存查询第一项
     *
     * @param sql
     * @param paras
     * @return
     */
    public M queryFisrtByCache(String sql, Object... paras) {
        List<M> list = queryByCache(sql, paras);
        if (x.isEmpty(list)) {
            return null;
        }
        return list.get(0);
    }

    /**
     * 缓存分页查询
     *
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
     *
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
     *
     * @param sql
     * @param paras
     * @return
     */
    public boolean isExist(String sql, Object... paras) {
        String configName = DbKit.getConfig(this.getClass()).getName();
        Long count = Db.use(configName).queryNumber(sql, paras).longValue();
        if (count != null && count != 0) {
            return true;
        }
        return false;
    }

    /**
     * 保存
     */
    @Override
    public boolean save() {
        Table table = TableMapping.me().getTable(getClass());
        String pk = table.getPrimaryKey()[0];
        // Class<?> pkType = table.getColumnType(pk);

        String ds = this._getConfig().getName();

        if (EovaDataSource.getDbType(ds) == DbType.oracle) {
            // 序列默认值
            if (this.get(pk) == null) {
                this.set(pk, SqlUtil.getSequence(ds, table.getName()));
            }
        }
        boolean isSave = super.save();
        if (EovaDataSource.getDbType(ds) == DbType.oracle) {
            this.set(pk, this.get(pk));
        }
        return isSave;
    }
}