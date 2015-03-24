public class StrUtil {
	/**
	 * ���˵�Ϊnull���ַ���
	 * 
	 * @param: String param
	 * @return: String
	 */
	public static String deNull(String param) {
		if (nullOrBlank(param))
			return "";
		return param.trim();
	}

	/**
	 * �ж��ַ��Ƿ�Ϊ��
	 * 
	 * @param: String param
	 * @return: boolean
	 */
	public static boolean nullOrBlank(String param) {
		return (param == null || param.trim().equals("") || param
				.equals("null")) ? true : false;
	}

	/**
	 * �����������Ƿ�Ϊ�հ�,����null��""
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isBlank(String str) {
		return str == null || str.trim().length() == 0 || "null".equals(str);
	}
}
