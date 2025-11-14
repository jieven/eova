/**
 * Copyright (c) 2015-2026 EOVA.CN. All rights reserved.
 * Licensed under the LGPL-3.0 license
 * For authorization, please contact: admin@eova.cn
 */
package cn.eova.mod;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.eova.tools.x;
import com.alibaba.druid.DbType;
import com.alibaba.druid.util.JdbcUtils;
import cn.eova.common.utils.db.DbUtil;
import cn.eova.common.utils.db.DsUtil;
import cn.eova.common.utils.db.SqlUtil;
import cn.eova.common.utils.io.FileUtil;
import cn.eova.common.utils.xx;
import cn.eova.config.EovaDataSource;
import cn.eova.model.Mod;
import cn.eova.sql.DbDialect;
import com.jfinal.plugin.activerecord.Db;

public class EovaModUtil {

    /**
     * 加载/WEB-INF/mod 下所有的jar
     * @return
     */
    public static URLClassLoader initLoader() {
        long t = System.currentTimeMillis();

        xx.info("Scan eova mod:" + EovaModConst.DIR_MOD);
        File[] jars = FileUtil.getFiles(EovaModConst.DIR_MOD);
        ArrayList<URL> list = new ArrayList<>();

        if (!x.isEmpty(jars)) {
            for (File jar : jars) {
                // 排除非jar文件
                if (!jar.getPath().endsWith(".jar")) {
                    continue;
                }
                try {
                    list.add(jar.toURI().toURL());
                    if (x.conf.get("env").equals("DEV")) {
                        System.out.println("find:" + jar.getName());
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            }
            System.out.println(String.format("Init ModLoader in %s ms\n", System.currentTimeMillis() - t));
        }

        URL[] urls = new URL[list.size()];
        list.toArray(urls);


        ClassLoader parent = Thread.currentThread().getContextClassLoader();
        URLClassLoader loader = new URLClassLoader(urls, parent);

        return loader;
    }

    /**
     * 单独加载指定的Mod
     * @param mods
     * @return
     */
    public static URLClassLoader createLoader(List<Mod> mods) {

        URL[] urls = new URL[mods.size()];
        for (int i = 0; i < urls.length; i++) {
            Mod mod = mods.get(i);
            String path = String.format("%s%s-%s-%s.jar", EovaModConst.DIR_MOD, mod.getGroup(), mod.getCode(), mod.getVersion());
            // System.err.println("Load eova mod jar:" + path);
            try {
                urls[i] = new File(path).toURI().toURL();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
        ClassLoader parent = Thread.currentThread().getContextClassLoader();
        URLClassLoader loader = new URLClassLoader(urls, parent);

        return loader;
    }

    /**
     * 加载指定Mod
     * @param group
     * @param code
     * @param version
     * @return
     */
    public static URLClassLoader createLoader(String group, String code, String version) {
        String path = String.format("%s%s-%s-%s.jar", EovaModConst.DIR_MOD, group, code, version);
        // System.err.println("Load eova mod jar:" + path);
        return loaderJar(path);
    }

    /**
     * 加载指定Jar
     * @param path
     * @return
     */
    public static URLClassLoader loaderJar(String path) {
        URL[] urls = new URL[1];
        try {
            urls[0] = new File(path).toURI().toURL();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        ClassLoader parent = Thread.currentThread().getContextClassLoader();
        URLClassLoader loader = new URLClassLoader(urls, parent);

        return loader;
    }

    public static EovaModConfig getEovaModConfig(URLClassLoader loader, String group, String code) throws Exception {
        String cs = String.format("com.eova.mod.%s.%s.ModConfig", group, code);
        Class clazz = loader.loadClass(cs);
        EovaModConfig mc = (EovaModConfig) clazz.newInstance();
        return mc;
    }
    // 无关关闭loader 会导致jar无法卸载
    //	public static <T> T loadModConfig(String group, String code) throws Exception {
    //		URLClassLoader loader = EovaModUtil.createLoader(group, code);
    //		String cs = String.format("com.eova.mod.%s.%s.ModConfig", group, code);
    //		Class clazz = loader.loadClass(cs);
    //		// xx.close(loader); 不能关闭, 还需要加载其它class
    //		return (T) clazz.newInstance();
    //	}

    /**
     * 执行标准通用SQL
     * @param ds
     * @param sqlFile
     */
    public static void executeSql(String ds, String sqlFile) {
        x.log.info("执行SQL脚本:{}", sqlFile);
        String[] sqls = DbUtil.loadSqls(sqlFile);
        for (String sql : sqls) {
            if (x.isEmpty(sql)) {
                continue;
            }
            //System.out.println(sql);
            Db.use(ds).update(sql.trim());
        }
    }

    /**
     * 执行标准通用SQL
     * @param ds
     * @param group
     * @param code
     * @param fileName
     */
    public static void executeSql(String ds, String group, String code, String fileName) {
        String sqlPath = String.format("%s%s#%s#%s", EovaModConst.DIR_MOD_VIEW, group, code, fileName).replace("#", File.separator);
        x.log.info("加载SQL脚本:{}", sqlPath);
        String[] sqls = DbUtil.loadSqls(sqlPath);
        for (String sql : sqls) {
            System.out.println(sql);
            Db.use(ds).update(sql);
        }
    }

    /**
     * <pre>
     * 按数据源标准执行兼容(INSERT, UPDATE, DELETE)
     * 1.自动处理转义符号
     * 2.在Oracle执行自动补充自增
     * </pre>
     * @param ds
     * @param group
     * @param code
     * @param fileName
     */
    public static void executeDml(String ds, String group, String code, String fileName) {
        String sqlPath = String.format("%s%s#%s#%s", EovaModConst.DIR_MOD_VIEW, group, code, fileName).replace("#", File.separator);

        executeEsql(ds, sqlPath);
    }

    /**
     * 执行兼容SQL(按数据源规范处理)
     * @param ds 数据源
     * @param sqlPath sql文件
     */
    public static List<String> executeEsql(String ds, String sqlPath) {
        List<String> sqls = generateEsql(ds, sqlPath);

        System.out.println("执行兼容后的SQL:");
        sqls.forEach(sql -> System.out.println(sql + ";"));
        Db.use(ds).batch(sqls, sqls.size());

        return sqls;
    }

    /**
     * 生成兼容SQL(按数据源规范处理)
     * @param ds
     * @param sqlPath
     * @return
     */
    public static List<String> generateEsql(String ds, String sqlPath) {
        DbType dbType = EovaDataSource.getDbType(ds);
        // dbType = JdbcUtils.ORACLE;

        HashMap<String, String> autoMap = new HashMap<>();

        System.out.println(String.format("加载SQL脚本:%s, 转为%s规范", sqlPath, dbType));
        List<String> tps = DbUtil.loadSql(sqlPath);
        List<String> sqls = new ArrayList<>();
        for (String sql : tps) {
            if (x.isEmpty(sql)) {
                continue;
            }

            // 跳过旧的不兼容语法
            if (sql.startsWith("SET @pid")) {
                continue;
            }

            // 维稳模式, 主动跳过风险语法
            if (x.conf.getBool("mysql.stable", false)) {
                String TP = sql.toUpperCase();
                if (TP.startsWith("UPDATE") && TP.contains("= (SELECT")) {
                    System.err.println("禁止执行高风险语法，请手工执行！ UPDATE ... = (SELECT...");
                    continue;
                }
            }

            // 去掉结束符
            sql = x.str.delEnd(sql, ";");

            // 转义符号
            sql = DbDialect.escape(dbType, sql);

            // Oracle 自增的特别处理
            // else
            if (JdbcUtils.isOracleDbType(dbType)) {
                if (sql.trim().toUpperCase().startsWith("INSERT") && sql.toUpperCase().indexOf(".NEXTVAL") == -1) {
                    String tableName = SqlUtil.getInsertTableName(DbType.oracle, sql);
                    String autoColumn = autoMap.get(tableName);
                    if (autoColumn == null) {
                        // 该表是否存在自增
                        autoColumn = DsUtil.getAutoColumn(ds, tableName);
                        autoMap.put(tableName, autoColumn);
                    }
                    // 按Eova约定添加自增列
                    sql = SqlUtil.addInsertSequenceColumn(DbType.oracle, sql, "SEQ_" + tableName.toUpperCase(), "id");
                }
            }


            // 保存兼容后的SQL
            sqls.add(sql);
        }

        return sqls;
    }

    /**
     * 执行SQL集合
     * @param ds 数据源
     * @param sqls SQL集合
     */
    public static void executeSql(String ds, List<String> sqls) {
        // 生成SQL日志
        StringBuffer sb = new StringBuffer();
        sqls.forEach(x -> sb.append(x).append("\n"));// 空行格式化

        System.out.println(sb.toString());

        // 执行SQL
        Db.use(ds).batch(sqls, sqls.size());
    }
}
