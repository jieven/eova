package cn.eova.engine;

import com.jfinal.kit.Kv;

/**
 * 表达式配置
 *
 * @author Jieven
 */
@Deprecated
public class EovaExpConfig {

    private String code;// 编码
    private String sql;// SQL
    private String ds;// 数据源
    private String cache;// 缓存
    private String valField;// 值字段名
    private String txtField;// 文本字段名
    private Kv kv;

    public EovaExpConfig(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public String getDs() {
        return ds;
    }

    public void setDs(String ds) {
        this.ds = ds;
    }

    public String getCache() {
        return cache;
    }

    public void setCache(String cache) {
        this.cache = cache;
    }

    public String getValField() {
        return valField;
    }

    public void setValField(String valField) {
        this.valField = valField;
    }

    public String getTxtField() {
        return txtField;
    }

    public void setTxtField(String txtField) {
        this.txtField = txtField;
    }

    public Kv getKv() {
        return kv;
    }

    public void setKv(Kv kv) {
        this.kv = kv;
    }
}
