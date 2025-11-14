/**
 * Copyright (c) 2015-2026 EOVA.CN. All rights reserved.
 * Licensed under the LGPL-3.0 license
 * For authorization, please contact: admin@eova.cn
 */
package cn.eova.ext.jfinal;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.jfinal.plugin.activerecord.CPI;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.ModelBuilder;

/**
 * Oracle专用Model构建器
 * @author Jieven
 *
 */
public class OracleModelBuilder extends ModelBuilder {

    public static final OracleModelBuilder me = new OracleModelBuilder();

    public <T> List<T> build(ResultSet rs, Class<? extends Model> modelClass) throws SQLException, InstantiationException, IllegalAccessException {
        List<T> result = new ArrayList<T>();
        ResultSetMetaData rsmd = rs.getMetaData();
        int columnCount = rsmd.getColumnCount();
        String[] labelNames = new String[columnCount + 1];
        int[] types = new int[columnCount + 1];
        buildLabelNamesAndTypes(rsmd, labelNames, types);
        while (rs.next()) {
            Model<?> model = modelClass.newInstance();
            Map<String, Object> columns = CPI.getAttrs(model);

            String ds = CPI.getConfig(model).getName();

            // 重新构建获得精准类型值
            OracleRecordBuilder.buildValue(ds, rs, rsmd, columnCount, labelNames, types, columns);

            result.add((T) model);
        }
        return result;
    }
}