package com.jfinal.plugin.activerecord;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * ModelBuilder.
 */
public class ModelBuilder {

	@SuppressWarnings({"rawtypes", "unchecked"})
	public static final <T> List<T> build(ResultSet rs, Class<? extends Model> modelClass) throws SQLException, InstantiationException, IllegalAccessException {
		List<T> result = new ArrayList<T>();
		ResultSetMetaData rsmd = rs.getMetaData();
		int columnCount = rsmd.getColumnCount();
		String[] labelNames = new String[columnCount + 1];
		int[] types = new int[columnCount + 1];
		buildLabelNamesAndTypes(rsmd, labelNames, types);
		while (rs.next()) {
			Model<?> ar = modelClass.newInstance();
			Map<String, Object> attrs = ar.getAttrs();
			for (int i=1; i<=columnCount; i++) {
				Object value;
				if (types[i] == Types.INTEGER)
					value = rs.getInt(i);
				else if (types[i] == Types.BIGINT)
					value = rs.getLong(i);
				else if (types[i] == Types.FLOAT)
					value = rs.getFloat(i);
				else if (types[i] == Types.DOUBLE)
					value = rs.getDouble(i);
				else if (types[i] == Types.CLOB)
					value = handleClob(rs.getClob(i));
				else if (types[i] == Types.NCLOB)
					value = handleClob(rs.getNClob(i));
				else if (types[i] == Types.BLOB)
					value = handleBlob(rs.getBlob(i));
				else
					value = rs.getObject(i);

				// 自动类型转换
				if (value != null) {
					String str = value.toString();
					if (types[i] == Types.NUMERIC) {
						if (str.length() < 11) {
							value = Integer.parseInt(str);
						} else if (str.contains(".")) {
							value = Double.parseDouble(str);
						} else {
							value = Long.valueOf(str);
						}
					} else if (types[i] == Types.CHAR) {
						if (str.equals("1")) {
							value = new Boolean(true);
						} else {
							value = new Boolean(false);
						}
					}
				}

				attrs.put(labelNames[i], value);
			}
			result.add((T)ar);
		}
		return result;
	}
	
	private static final void buildLabelNamesAndTypes(ResultSetMetaData rsmd, String[] labelNames, int[] types) throws SQLException {
		for (int i=1; i<labelNames.length; i++) {
			labelNames[i] = rsmd.getColumnLabel(i);
			types[i] = rsmd.getColumnType(i);
		}
	}
	
	public static byte[] handleBlob(Blob blob) throws SQLException {
		if (blob == null)
			return null;
		
		InputStream is = null;
		try {
			is = blob.getBinaryStream();
			byte[] data = new byte[(int)blob.length()];		// byte[] data = new byte[is.available()];
			is.read(data);
			is.close();
			return data;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		finally {
			try {is.close();} catch (IOException e) {throw new RuntimeException(e);}
		}
	}
	
	public static String handleClob(Clob clob) throws SQLException {
		if (clob == null)
			return null;
		
		Reader reader = null;
		try {
			reader = clob.getCharacterStream();
			char[] buffer = new char[(int)clob.length()];
			reader.read(buffer);
			return new String(buffer);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		finally {
			try {reader.close();} catch (IOException e) {throw new RuntimeException(e);}
		}
	}
	
	/* backup before use columnType
	@SuppressWarnings({"rawtypes", "unchecked"})
	static final <T> List<T> build(ResultSet rs, Class<? extends Model> modelClass) throws SQLException, InstantiationException, IllegalAccessException {
		List<T> result = new ArrayList<T>();
		ResultSetMetaData rsmd = rs.getMetaData();
		int columnCount = rsmd.getColumnCount();
		String[] labelNames = getLabelNames(rsmd, columnCount);
		while (rs.next()) {
			Model<?> ar = modelClass.newInstance();
			Map<String, Object> attrs = ar.getAttrs();
			for (int i=1; i<=columnCount; i++) {
				Object attrValue = rs.getObject(i);
				attrs.put(labelNames[i], attrValue);
			}
			result.add((T)ar);
		}
		return result;
	}
	
	private static final String[] getLabelNames(ResultSetMetaData rsmd, int columnCount) throws SQLException {
		String[] result = new String[columnCount + 1];
		for (int i=1; i<=columnCount; i++)
			result[i] = rsmd.getColumnLabel(i);
		return result;
	}
	*/
	
	/* backup
	@SuppressWarnings({"rawtypes", "unchecked"})
	static final <T> List<T> build(ResultSet rs, Class<? extends Model> modelClass) throws SQLException, InstantiationException, IllegalAccessException {
		List<T> result = new ArrayList<T>();
		ResultSetMetaData rsmd = rs.getMetaData();
		List<String> labelNames = getLabelNames(rsmd);
		while (rs.next()) {
			Model<?> ar = modelClass.newInstance();
			Map<String, Object> attrs = ar.getAttrs();
			for (String lableName : labelNames) {
				Object attrValue = rs.getObject(lableName);
				attrs.put(lableName, attrValue);
			}
			result.add((T)ar);
		}
		return result;
	}
	
	private static final List<String> getLabelNames(ResultSetMetaData rsmd) throws SQLException {
		int columCount = rsmd.getColumnCount();
		List<String> result = new ArrayList<String>();
		for (int i=1; i<=columCount; i++) {
			result.add(rsmd.getColumnLabel(i));
		}
		return result;
	}
	*/
}

