package cn.jdbc;
import java.util.HashMap;

/**
 * @ClassName: DBColumnMapper
 * @Description: ���ݿ��ֶβ�����
 * @author XiMing.Fu
 * @date 2014-3-10 ����01:54:29
 * 
 */
public class DBColumnMapper {
	private static HashMap<String, String> dataTypes = new HashMap<String, String>();

	static {
		dataTypes.put("VARCHAR2", "java.lang.String");
		dataTypes.put("DATE", "java.sql.Timestamp");
		dataTypes.put("TIMESTAMP", "java.sql.Timestamp");
		dataTypes.put("CLOB", byte[].class.getName());
	}

	public static Object getDBUtilColumValue(Object obj, String columType) {

		// initMapper();
		String javaType = getJavaType(columType);
		if (javaType.equals("java.lang.String")) {
			return obj == null ? null : obj.toString();
		} else {
			return obj;
		}

	}

	public static String getJavaType(String type) {
		// initMapper();
		String javaType = dataTypes.get(type);
		if (javaType == null) {
			javaType = "java.lang.String";
		}
		return javaType;
	}

	/**
	 * ת��������getter��ʽ �磺COLUMN_NAME ת����Column_name
	 * 
	 * @param columnName
	 * @return
	 */
	public static String getSetterName(String columnName) {
		String head = "";
		String tail = "";
		int len = columnName.length();

		head = columnName.substring(0, 1).toUpperCase();
		if (len > 1) {
			tail = columnName.substring(1).toLowerCase();
		}
		return head + tail;
	}

	/**
	 * ��getSetterName(String columnName)������ͬ
	 * 
	 * @param columnName
	 * @return
	 */
	public static String getGetterName(String columnName) {
		return getSetterName(columnName);
	}
}
