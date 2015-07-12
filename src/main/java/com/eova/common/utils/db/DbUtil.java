package com.eova.common.utils.db;

import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.eova.common.utils.xx;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

public class DbUtil {

	/**
	 * 转换Oracle数据类型
	 * 
	 * @param typeName DB数据类型
	 * @return
	 */
	private static String convertDataType(String typeName) {
		if (typeName.contains("INT")) {
			return "NUMBER";
		} else if (typeName.contains("BIT")) {
			return "CHAR";
		} else if (typeName.indexOf("TIME") != -1) {
			return "DATE";
		} else {
			return "VARCHAR2";
		}
	}

	public static void createOracleSql(String ds, String tableNamePattern) {
		
		StringBuilder sbs = new StringBuilder();
		StringBuilder sbDrop = new StringBuilder();
		StringBuilder sbDropSeq = new StringBuilder();
		StringBuilder sbCreateSeq = new StringBuilder();
		
		List<String> tables = DsUtil.getTableNamesByConfigName(ds, DsUtil.TABLE, null, tableNamePattern);

		for (String table : tables) {

			String pk = DsUtil.getPkName(ds, table);
			
			String drop = "drop table " + table + ";\n";
			sbDrop.append(drop);
			
			String dropSeq = "drop sequence seq_" + table + ";\n";
			sbDropSeq.append(dropSeq);
			
			// 获取表中最大值
			String sql = "select max("+ pk +") from " + table;
			Object max = Db.use(ds).queryColumn(sql);

			String createSeq = "create sequence seq_" + table + " increment by 1 start with "+ max + 1 +" maxvalue 9999999999;\n";
			sbCreateSeq.append(createSeq);

			JSONArray list = DsUtil.getColumnInfoByConfigName(ds, table);

			StringBuilder sb = new StringBuilder();
			StringBuilder sb2 = new StringBuilder();
			StringBuilder sb3 = new StringBuilder();

			 System.out.println(list);

			sb.append("create table " + table);
			sb.append("(\n");

			for (int i = 0; i < list.size(); i++) {
	            JSONObject o = list.getJSONObject(i);
	            
	            Record re = new Record();
				re.set("en", o.getString("COLUMN_NAME"));
				re.set("cn", o.getString("REMARKS"));
	            re.set("order_num", o.getIntValue("ORDINAL_POSITION"));
				re.set("is_required", "YES".equalsIgnoreCase(o.getString("IS_NULLABLE")) ? false : true);

				// 是否自增
				boolean isAuto = "YES".equalsIgnoreCase(o.getString("IS_AUTOINCREMENT")) ? true : false;
				re.set("is_auto", isAuto);
				// 字段类型
				String typeName = o.getString("TYPE_NAME");
				re.set("data_type", convertDataType(typeName));
				// 字段长度
				int size = o.getIntValue("COLUMN_SIZE");
				if(size == 0){
					size = 1;
				}
				// 默认值
				String def = o.getString("COLUMN_DEF");
				re.set("defaulter", def);

				// create table
				sb.append("    " + re.getStr("en") + " " + re.getStr("data_type") + "(" + size + ")");
				if (re.getBoolean("is_required")) {
					sb.append(" NOT NULL");
				}
				sb.append(",\n");

				// create remarks
				String remarks = o.getString("REMARKS");
				if (!xx.isEmpty(remarks)) {
					String str = "comment on column %s.%s is '%s';\n";
					sb2.append(String.format(str, table, re.getStr("en"), remarks));
				}

				// add default
				{
					if (!xx.isEmpty(def)) {
						String str = "alter table %s modify %s default %s;\n";
						sb3.append(String.format(str, table, re.getStr("en"), xx.format(def)));
					}

				}

	        }
			sb.delete(sb.length() - 2, sb.length() - 1);
			sb.append(");\n");
			
			// 导入元字段
			// importMetaField(code, list);

			// 导入视图默认第一列为主键
			String pkName = DsUtil.getPkName(ds, table);
			if (!xx.isEmpty(pkName)) {
				String str = "\nalter table %s add constraint pk_%s primary key(%s);\n";
				sb2.insert(0, String.format(str, table, table, pkName));
			}

			// 导入元对象
			// importMetaObject(ds, type, table, name, code, pkName);

			sbs.append(sb);
			sbs.append(sb2);
			sbs.append(sb3);
			sbs.append("\n");
		}

		System.out.println(sbDrop.toString());
		System.out.println(sbDropSeq.toString());
		System.out.println(sbCreateSeq.toString());
		System.out.println(sbs.toString());
	}
}
