package cn.eova.model;

import java.util.stream.Stream;

/**
 * 通知类型
 *
 * @author Jieven
 */
public enum MsgType {

    OK(1, "成功"),
    NO(2, "失败"),
    WA(3, "警告"),
    INFO(4, "普通");

    int val;
    String txt;

    MsgType(int val, String txt) {
        this.val = val;
        this.txt = txt;
    }

    public int getVal() {
        return val;
    }

    public void setVal(int val) {
        this.val = val;
    }

    public String getTxt() {
        return txt;
    }

    public void setTxt(String txt) {
        this.txt = txt;
    }

    public static MsgType valueOf(int val) {
        return Stream.of(MsgType.values()).filter(r -> r.getVal() == val).findAny().orElse(null);
    }

}