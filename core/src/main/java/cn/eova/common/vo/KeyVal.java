/**
 * Copyright (c) 2015-2026 EOVA.CN. All rights reserved.
 * Licensed under the LGPL-3.0 license
 * For authorization, please contact: admin@eova.cn
 */
package cn.eova.common.vo;

import java.io.Serializable;

/**
 * 键值对
 * @author Jieven
 *
 */
public class KeyVal implements Serializable {

    private static final long serialVersionUID = -9095802999489821104L;

    private Object key;

    private Object val;

    private Object txt;

    public KeyVal() {
    }

    public KeyVal(Object key, Object val) {
        super();
        this.key = key;
        this.val = val;
    }

    public KeyVal(Object key, Object val, Object txt) {
        super();
        this.key = key;
        this.val = val;
        this.txt = txt;
    }

    public Object getKey() {
        return key;
    }

    public void setKey(Object key) {
        this.key = key;
    }

    public Object getVal() {
        return val;
    }

    public void setVal(Object val) {
        this.val = val;
    }

    public Object getTxt() {
        return txt;
    }

    public void setTxt(Object txt) {
        this.txt = txt;
    }

    @Override
    public String toString() {
        return "[key=" + key + ", value=" + val + "]";
    }

}