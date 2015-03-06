public class StrUtil {
	/**
	 * 过滤掉为null的字符串
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
	 * 判断字符是否为空
	 * 
	 * @param: String param
	 * @return: boolean
	 */
	public static boolean nullOrBlank(String param) {
		return (param == null || param.trim().equals("") || param
				.equals("null")) ? true : false;
	}

	/**
	 * 功能描述：是否为空白,包括null和""
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isBlank(String str) {
		return str == null || str.trim().length() == 0 || "null".equals(str);
	}
}
