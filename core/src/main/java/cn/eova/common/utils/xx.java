/**
 * Copyright (c) 2015-2026 EOVA.CN. All rights reserved.
 * Licensed under the LGPL-3.0 license
 * For authorization, please contact: admin@eova.cn
 */
package cn.eova.common.utils;

import java.io.File;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.eova.tools.x;
import com.alibaba.druid.util.JdbcUtils;
import cn.eova.config.EovaConfig;
import com.jfinal.kit.LogKit;

/**
 * 请 import cn.eova.tools.x;
 * <br>
 * 使用 x.xxx 代替 xx.xxx
 *
 * @author Jieven
 *
 */
@Deprecated
public class xx {

    /**默认数据源名称**/
    public static final String DS_MAIN = "main";
    /**EOVA数据源名称**/
    public static final String DS_EOVA = "eova";

    public static void debug(String s, Object... args) {
        LogKit.debug(String.format(s, args));
    }

    /**
     * 万能判空
     * @param o
     * @return
     */
    public static boolean isEmpty(Object o) {
//		if (o == null) {
//			return true;
//		}
//		if (o instanceof Collection) {
//			return ((Collection<?>) o).isEmpty();
//		}
//		if (o instanceof Map) {
//			return ((Map<?, ?>) o).isEmpty();
//		}
//		if (o.getClass().isArray()) {
//			return Array.getLength(o) == 0;
//		}
//		if (o instanceof Iterator) {
//			return !((Iterator<?>) o).hasNext();
//		}
//		if (o instanceof Iterable) {
//			return !((Iterable<?>) o).iterator().hasNext();
//		}
//		if (o instanceof String) {
//			if (o.toString().equals("")) {
//				return true;
//			}
//		}
//		return false;
        return x.isEmpty(o);
    }

    public static void info(String s, Object... args) {
        LogKit.info(s, args);
    }

    /**
     * 目标集合中是否包含指定对象(按对象字符串值判定)
     * @param arrs 目标集合
     * @param s 指定对象
     * @return
     */
    public static boolean isContains(Collection<?> arrs, Object s) {
        for (Object o : arrs) {
            if (o.toString().equals(s.toString())) {
                return true;
            }
        }
        return false;
    }

    /**
     * 对象组中是否存在 Empty Object
     * @param os 对象组
     * @return
     */
    public static boolean isEmptyOne(Object... os) {
        for (Object o : os) {
            if (isEmpty(o)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 对象组中是否全是 Empty Object
     * @param os
     * @return
     */
    public static boolean isEmptyAll(Object... os) {
        for (Object o : os) {
            if (!isEmpty(o)) {
                return false;
            }
        }
        return true;
    }

    /**
     * EOVA魔法类型:优先转数字
     * 主要用于未知类型到严格类型
     * @param o
     * @return
     */
    public static Object ET(Object o) {
        try {
            return Integer.parseInt(o.toString());
        } catch (Exception e) {
            return o;
        }
    }

    /**
     * 是否为数字
     * @param o
     * @return
     */
    public static boolean isNum(Object o) {
        try {
            Integer.parseInt(o.toString());
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     *  字符串是否为 true
     * @param str
     * @return
     */
    public static boolean isTrue(Object str) {
        if (isEmpty(str)) {
            return false;
        }
        str = str.toString().trim().toLowerCase();
        if (str.equals("true") || str.equals("on") || str.equals("1")) {
            return true;
        }
        return false;
    }

    /**
     * 格式化字符串->'str'
     * @param str
     * @return
     */
    public static String format(Object str) {
        return "'" + str.toString() + "'";
    }

    /**
     * 格式化文件路径中的目录分隔符
     * @param path 路径
     * @param args
     * @return
     */
    public static String formatPath(String path, Object... args) {
        return String.format(path.replace("/", File.separator), args);
    }

    /**
     * 强转->Integer
     * @param obj
     * @return
     */
    public static Integer toInt(Object obj) {
        return Integer.parseInt(obj.toString());
    }

    /**
     * 强转->Integer
     * @param obj
     * @param defaultValue
     * @return
     */
    public static Integer toInt(Object obj, int defaultValue) {
        if (isEmpty(obj)) {
            return defaultValue;
        }
        return toInt(obj);
    }

    /**
     * 强转->Long
     * @param obj
     * @return
     */
    public static long toLong(Object obj) {
        return Long.parseLong(obj.toString());
    }

    /**
     * 强转->Long
     * @param obj
     * @param defaultValue
     * @return
     */
    public static long toLong(Object obj, long defaultValue) {
        if (isEmpty(obj)) {
            return defaultValue;
        }
        return toLong(obj);
    }

    /**
     * 强转->Double
     * @param obj
     * @return
     */
    public static double toDouble(Object obj) {
        return Double.parseDouble(obj.toString());
    }

    public static double toDouble(Object obj, double defaultValue) {
        if (isEmpty(obj)) {
            return defaultValue;
        }
        return toDouble(obj);
    }

    /**
     * 强转->Boolean
     * @param obj
     * @return
     */
    public static Boolean toBoolean(Object obj) {
        return Boolean.parseBoolean(obj.toString());
    }

    /**
     * 强转->Boolean
     * @param obj
     * @param defaultValue
     * @return
     */
    public static Boolean toBoolean(Object obj, Boolean defaultValue) {
        if (isEmpty(obj)) {
            return defaultValue;
        }
        return toBoolean(obj);
    }

    /**
     * 强转->java.util.Date
     * @param str 日期字符串
     * @return
     */
    public static Date toDate(String str) {
        try {
            if (str == null || "".equals(str.trim()))
                return null;
            return new java.text.SimpleDateFormat("yyyy-MM-dd").parse(str.trim());
        } catch (Exception e) {
            throw new RuntimeException("Can not parse the parameter \"" + str + "\" to Date value.");
        }
    }

    /**
     * main数据源 是否PostgreSql数据源
     *
     * case postgresql:
     * case edb:
     * case polardb:
     *
     * @return
     */
    public static boolean isPgsql() {
        return JdbcUtils.isPgsqlDbType(EovaConfig.EOVA_DBTYPE);
    }

    /**
     * main数据源 是否SqlServer数据源
     *
     * case sqlserver:
     * case jtds:
     *
     * @return
     */
    public static boolean isSqlServer() {
        return JdbcUtils.isSqlserverDbType(EovaConfig.EOVA_DBTYPE);
    }

    /**
     * main数据源 是否Oracle数据源
     *
     * case oracle:
     * case ali_oracle:
     * case oceanbase:
     *
     * @return
     */
    public static boolean isOracle() {
        return JdbcUtils.isOracleDbType(EovaConfig.EOVA_DBTYPE);
    }

    /**
     * main数据源 是否Mysql数据源
     *
     * case mysql:
     * case oceanbase:
     * case drds:
     * case mariadb:
     * case h2:
     *
     * @return
     */
    public static boolean isMysql() {
        return JdbcUtils.isMysqlDbType(EovaConfig.EOVA_DBTYPE);
    }

    /**
     * 按空格分割字符串
     * @param s
     * @return
     */
    public static String[] splitBlank(String s) {
        return s.trim().split("\\s|\t|\r|\n");
    }

    public static String join(Collection<?> s) {
        return join(s, "", ",");
    }

    public static String join(Integer[] s) {
        return join(Arrays.asList(s), "", ",");
    }

    public static String join(String[] s) {
        return join(Arrays.asList(s), "", ",");
    }

    /**
     * 集合转字符串
     * @param s 集合
     * @param parcel 包裹符 e.g 'aa','bb'
     * @param sign 分割符 e.g 1,2
     * @return
     */
    public static String join(Collection<?> s, String parcel, String sign) {
        if (s.isEmpty())
            return "";
        Iterator<?> iter = s.iterator();
        StringBuilder sb = new StringBuilder(parcel + iter.next().toString() + parcel);
        while (iter.hasNext())
            sb.append(sign).append(parcel + iter.next() + parcel);
        return sb.toString();
    }

    /**
     * 删除开头字符串
     *
     * @param s 待处理字符串
     * @param sign 需要删除的符号
     * @return
     */
    public static String delStart(String s, String sign) {
        if (s.startsWith(sign)) {
            return s.substring(s.indexOf(sign) + sign.length(), s.length());
        }
        return s;
    }

    /**
     * 删除末尾字符串
     *
     * @param s 待处理字符串
     * @param sign 需要删除的符号
     * @return
     */
    public static String delEnd(String s, String sign) {
        if (x.isEmpty(s)) {
            return s;
        }
        if (s.endsWith(sign)) {
            return s.substring(0, s.lastIndexOf(sign));
        }
        return s;
    }

    /**
     * 删除首位字符串
     * @param s
     * @param sign
     * @return
     */
    public static String delStartEnd(String s, String sign) {
        return delEnd(delStart(s, sign), sign);
    }

    /**
     * 获取配置项 使用 x.conf.get()
     * @param key
     * @return
     */
    public static String getConfig(String key) {
        return x.conf.get(key);
//		String value = EovaConfig.getProps().get(key);
//		if (value == null) {
//			return "";
//		}
//		return value.trim();
    }

    /**
     * 获取配置项  x.conf.get()
     * @param key
     * @param defaultValue
     * @return
     */
    public static String getConfig(String key, String defaultValue) {
        return x.conf.get(key, defaultValue);
//        String value = EovaConfig.getProps().get(key);
//        if (value == null) {
//            return defaultValue;
//        }
//        return value;
    }

    /**
     * 获取Bool配置
     * @param key
     * @param defaultValue
     * @return
     */
    public static boolean getConfigBool(String key, boolean defaultValue) {
        return x.conf.getBool(key, defaultValue);
//		String val = getConfig(key);
//        if (x.isEmpty(val))
//            return defaultValue;
//        if (val.equals("1") || val.equals("true")) {
//            return true;
//        }
//        return false;
    }

    /**
     * 获取Int配置
     * @param key
     * @param defaultValue
     * @return
     */
    public static int getConfigInt(String key, int defaultValue) {
        return x.conf.getInt(key, defaultValue);
        // return toInt(getConfig(key), defaultValue);
    }

    /**
     * 消耗毫秒数
     * @param time
     */
    public static void costTime(long time) {
        System.err.println("Load Cost Time:" + (System.currentTimeMillis() - time) + "ms\n");
    }

    /**
     * 格式化输出JSON
     * @param json
     * @return
     */
    public static String formatJson(String json) {
        int level = 0;
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < json.length(); i++) {
            char c = json.charAt(i);
            if (level > 0 && '\n' == sb.charAt(sb.length() - 1)) {
                sb.append(getLevelStr(level));
            }
            switch (c) {
                case '{':
                case '[':
                    sb.append(c + "\n");
                    level++;
                    break;
                case ',':
                    sb.append(c + "\n");
                    break;
                case '}':
                case ']':
                    sb.append("\n");
                    level--;
                    sb.append(getLevelStr(level));
                    sb.append(c);
                    break;
                default:
                    sb.append(c);
                    break;
            }
        }
        return sb.toString();
    }

    private static String getLevelStr(int level) {
        StringBuffer levelStr = new StringBuffer();
        for (int levelI = 0; levelI < level; levelI++) {
            levelStr.append("  ");
        }
        return levelStr.toString();
    }

    /**
     * 替换空格,换行符等
     *
     * @param s
     * @return
     */
    public static String replaceBlank(String s) {
        if (x.isEmpty(s)) {
            return s;
        }
        Pattern p = Pattern.compile("\\s*|\t|\r|\n");
        Matcher m = p.matcher(s);
        return m.replaceAll("");
    }

    /**
     * 是否超过了xx秒
     * @param oldTime 过去的时间点
     * @param timeoutSec 已过期时长
     * @return
     */
    public static boolean isTimeout(Long oldTime, int timeoutSec) {
        if (oldTime == null) {
            return true;
        }
        if ((System.currentTimeMillis() - oldTime) / 1000 > timeoutSec) {
            return true;
        }
        return false;
    }

    /**
     * 集合转数组 直接使用 list.toArray();
     * @param list
     * @return
     */
    @Deprecated
    public static Object[] toArray(List<Object> list) {
        Object[] os = new Object[list.size()];
        list.toArray(os);
        return os;
    }

    /**
     * 自动关闭资源
     * @param autoCloseables
     */
    public static void close(AutoCloseable... autoCloseables) {
        for (AutoCloseable closeable : autoCloseables) {
            if (closeable != null) {
                try {
                    closeable.close();
                } catch (Exception e) {
                    // do nothing
                }
            }
        }
    }

}