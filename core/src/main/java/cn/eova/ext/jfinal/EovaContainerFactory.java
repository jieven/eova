/**
 * Copyright (c) 2015-2026 EOVA.CN. All rights reserved.
 * Licensed under the LGPL-3.0 license
 * For authorization, please contact: admin@eova.cn
 */
package cn.eova.ext.jfinal;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import com.jfinal.plugin.activerecord.IContainerFactory;

/**
 * JFinal Reocrd 规则配置 
 * 默认全小写且保持DB字段顺序
 * @author Jieven
 *
 */
public class EovaContainerFactory implements IContainerFactory {

    private static Boolean toLowerCase = null;

    public EovaContainerFactory() {
    }

    public EovaContainerFactory(boolean toLowerCase) {
        EovaContainerFactory.toLowerCase = toLowerCase;
    }

    public Map<String, Object> getAttrsMap() {
        return new CaseInsensitiveMap<Object>();
    }

    public Map<String, Object> getColumnsMap() {
        return new CaseInsensitiveMap<Object>();
    }

    public Set<String> getModifyFlagSet() {
        return new CaseInsensitiveSet();
    }

    private static String convertCase(String key) {
        if (toLowerCase != null) {
            return toLowerCase ? key.toLowerCase() : key.toUpperCase();
        } else {
            return key;
        }
    }

    /*
     * 1：非静态内部类拥有对外部类的所有成员的完全访问权限，包括实例字段和方法，
     *    为实现这一行为，非静态内部类存储着对外部类的实例的一个隐式引用
     * 2：序列化时要求所有的成员变量是Serializable 包括上面谈到的引式引用
     * 3：外部类CaseInsensitiveContainerFactory 需要 implements Serializable 才能被序列化
     * 4：可以使用静态内部类来实现内部类的序列化，而非让外部类实现 implements Serializable
     */
    public static class CaseInsensitiveSet extends LinkedHashSet<String> {

        private static final long serialVersionUID = 6236541338642353211L;

        //		public CaseInsensitiveSet() {
        //			super(String.CASE_INSENSITIVE_ORDER);
        //		}

        public boolean add(String e) {
            return super.add(convertCase(e));
        }

        public boolean addAll(Collection<? extends String> c) {
            boolean modified = false;
            for (String o : c) {
                if (super.add(convertCase(o))) {
                    modified = true;
                }
            }
            return modified;
        }
    }

    public static class CaseInsensitiveMap<V> extends LinkedHashMap<String, V> {

        private static final long serialVersionUID = 7482853823611007217L;

        //		public CaseInsensitiveMap() {
        //			super(String.CASE_INSENSITIVE_ORDER);
        //		}

        public V put(String key, V value) {
            return super.put(convertCase(key), value);
        }

        public void putAll(Map<? extends String, ? extends V> map) {
            for (Map.Entry<? extends String, ? extends V> e : map.entrySet()) {
                super.put(convertCase(e.getKey()), e.getValue());
            }
        }
    }
}

