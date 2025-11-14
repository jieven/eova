package cn.eova.sql.dql.dialect;

import java.util.List;

import cn.eova.tools.x;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import cn.eova.common.vo.KeyVal;
import cn.eova.model.MetaField;
import cn.eova.sql.dql.QueryParam;

/**
 * DQL(data query language)数据查询方言
 * 关键字:select, where等
 *
 * @author Jieven
 *
 */
public abstract class QueryDialect {

    /**
     * 多值条件
     * @param field
     * @param params
     * @return
     */
    protected abstract String multipleCondition(String field, String value, List<Object> params);

    /**
     * 时间条件
     * @param field
     * @return
     */
    protected abstract String timeCondition(String field);

    /**
     * 日期条件
     * @param field
     * @return
     */
    protected abstract String dateCondition(String field);

    /**
     * 多选值查询条件规则
     * @param field
     * @param value
     * @param params
     * @return
     */
    public String multiple(String field, String value, List<Object> params) {
        StringBuilder sb = new StringBuilder();
        sb.append(" and (");
        for (String val : value.split(",")) {
            if (!sb.toString().endsWith(" (")) {
                sb.append(" or ");
            }
            sb.append(multipleCondition(field, val, params));
        }
        sb.append(")");

        return sb.toString();
    }

    /**
     * 单选值查询条件规则
     * @param type 控件类型
     * @param dataType 数据类型
     * @param dataSize TODO
     * @param field 当前字段
     * @param pm 查询参数
     * @return
     */
    public KeyVal single(String type, String dataType, int dataSize, String field, QueryParam pm) {

        String value = pm.value.toString();
        String cond = pm.cond;

        // 数字框
        if (type.equals(MetaField.TYPE_NUM)) {
            return numType(field, value);
        }

        // 时间框
        if (type.equals(MetaField.TYPE_TIME)) {
            return new KeyVal(timeCondition(field), value);
        }

        // 日期框
        if (type.equals(MetaField.TYPE_DATE)) {
            return new KeyVal(dateCondition(field), value);
        }

        // 多值查询模式 field in (1,2)
        String val = pm.value.toString();
        if (val.contains(",")) {
            StringBuilder str = new StringBuilder();
            for (int i = 0; i < val.split(",").length; i++) {
                str.append("?,");
            }
            return new KeyVal(String.format(" and " + field + " in (%s)", x.str.delEnd(str.toString(), ",")), val);
        }

        // 精准框 和 精确类型 都是精准查询: mysql *int || oracle number || long
        if (type.equals(MetaField.TYPE_VALUE) || dataType.contains("int") || dataType.equals("number") || dataType.equals("long")) {
            return new KeyVal(" and " + field + " = ?", val);
        }

        // 搜索框
        if (type.equals(MetaField.TYPE_SEARCH)) {
            if (cond.equals("like")) {
                return new KeyVal(" and " + field + " like ?", "%" + val + "%");
            } else {
                return new KeyVal(" and " + field + " = ?", val);
            }
        }

        // 文本控件
        if (type.equals(MetaField.TYPE_TEXT) || type.equals(MetaField.TYPE_TEXTS) || type.equals(MetaField.TYPE_EDIT)) {
            return new KeyVal(" and " + field + " like ?", "%" + val + "%");
        }

        return null;
    }

    /**
     * 数字范围查询
     * V1.0 按条件符号来判定条件
     * V2.0 通过开始+结束是否有值来判定范围, 提升用户体验.
     * @param field 字段
     * @param value 精准框
     * @return
     */
    public KeyVal numType(String field, String value) {
        String start = "", end = "";
        if (value.startsWith("[") && value.endsWith("]")) {
            JSONArray o = JSON.parseArray(value);
            start = o.get(0).toString();
            end = o.get(1).toString();
        }

        // N = ? 关联查询或手工条件还需要精准条件
        if (!x.isEmpty(value)) {
            return new KeyVal(String.format(" and %s = ? ", field), value);
        }

        // ? < N < ?
        if (!x.isEmpty(start) && !x.isEmpty(end)) {
            return new KeyVal(" and ? <= " + field + " and " + field + " <= ?", start + "," + end);
        }

        // ? <= N
        if (!x.isEmpty(start)) {
            return new KeyVal(String.format(" and %s >= ? ", field), start);
        }

        // N <= ?
        if (!x.isEmpty(end)) {
            return new KeyVal(String.format(" and %s <= ? ", field), end);
        }


        return null;
    }

}
