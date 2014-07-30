package cn.sax;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class StringUtil {
	/**
	 * ���ַ���ת��Ϊ int.
	 * 
	 * @param input
	 *            ������ִ�
	 * @date 2005-07-29
	 * @return �������
	 */
	public static int parseInt(String input) {
		try {
			return Integer.parseInt(input);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return 0;
	}

	public static final String EMPTY_STRING = "";

	private StringUtil() {
	}

	public static boolean getBoolean(String property) {
		return Boolean.valueOf(property).booleanValue();
	}

	public static boolean getBoolean(String property, boolean defaultValue) {
		return (property == null) ? defaultValue : Boolean.valueOf(property)
				.booleanValue();
	}

	public static int getInt(String property) {
		return Integer.parseInt(property);
	}

	public static int getInt(String property, int defaultValue) {
		return (property == null) ? defaultValue : Integer.parseInt(property);
	}

	public static String getString(String property, String defaultValue) {
		return (property == null) ? defaultValue : property;
	}

	public static Integer getInteger(String property) {
		return (property == null) ? null : Integer.valueOf(property);
	}

	public static Integer getInteger(String property, Integer defaultValue) {
		return (property == null || property.equals("")) ? defaultValue
				: getInteger(property);
	}

	public static long getLong(String property) {
		return Long.parseLong(property);
	}

	public static long getLong(String property, long defaultValue) {
		return (property == null || property.equals("")) ? defaultValue
				: getLong(property);
	}

	public static double getDouble(String property) {
		return Double.parseDouble(property);
	}

	public static double getDouble(String property, double defaultValue) {
		return (property == null || property.equals("")) ? defaultValue
				: getDouble(property);
	}

	public static float getFloat(String property) {
		return Float.parseFloat(property);
	}

	public static float getFloat(String property, float defaultValue) {
		return (property == null || property.equals("")) ? defaultValue
				: getFloat(property);
	}

	public static java.sql.Date getDate(String str) {
		return str == null ? null : java.sql.Date.valueOf(str);
	}

	public static java.sql.Time getTime(String str) {
		return str == null ? null : java.sql.Time.valueOf(str);
	}

	public static java.sql.Timestamp getTimeStamp(String str) {
		return str == null ? null : java.sql.Timestamp.valueOf(str);
	}

	/**
	 * ������������
	 * 
	 * @param className
	 *            String
	 * @return String
	 */
	public static String getObjectName(String className) {
		StringBuffer result = new StringBuffer();
		if (className.indexOf(" ") != -1) {
			result.append(className.substring(className.lastIndexOf(" ") + 1));
		}
		if (className.indexOf(".") != -1) {
			result.append(className.substring(className.lastIndexOf(".") + 1));
		}
		return result.toString().toUpperCase();
	}

	/**
	 * 
	 * @param property
	 *            String
	 * @param delim
	 *            String
	 * @return Map
	 */
	public static Map toMap(String property, String delim) {
		Map map = new HashMap();
		if (property != null) {
			StringTokenizer tokens = new StringTokenizer(property, delim);
			while (tokens.hasMoreTokens()) {
				map.put(tokens.nextToken(),
						tokens.hasMoreElements() ? tokens.nextToken()
								: EMPTY_STRING);
			}
		}
		return map;
	}

	public static String[] toStringArray(String propValue, String delim) {
		if (propValue != null) {
			return propValue.split(delim);
		} else {
			return null;
		}
	}

	public static String fieldValue2String(Object object) {
		if (object != null) {
			return object.toString();
		} else {
			return null;
		}
	}

	/**
	 * ����ָ��������ַ���ֵ
	 * 
	 * @param object
	 * @return
	 */
	public static String getValue(Object object) {
		String result = "";
		if (object != null) {
			result = String.valueOf(object);
			result = result.equals("null") ? "" : result;
		}
		return result;
	}

	/**
	 * ���ַ���������ʽ��sql��ʽ
	 * 
	 * @param paramStr
	 *            String Դ����
	 * @param splitStr
	 *            String �ָ��ַ�����","
	 * @return String
	 */
	public static String returnParam(String paramStr, String splitStr) {
		StringBuffer result = new StringBuffer();
		String[] params = paramStr.split(splitStr);
		for (int i = 0; i < params.length; i++) {
			if (i == 0) {
				result.append("'" + params[i] + "'");
			} else {
				result.append("," + "'" + params[i] + "'");
			}
		}
		return result.toString();
	}

	/**
	 * �ж��Ƿ�������
	 * 
	 * @param: String param
	 * @return: boolean
	 */
	public static boolean isValidNumber(String param) {
		try {
			Integer.parseInt(param);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	/**
	 * ��ȡ����Ϊָ�����ȵı���...
	 * 
	 * @param str
	 *            String
	 * @param len
	 *            int
	 * @return String
	 */
	public static String replaceTitle(String str, int len, String taget) {
		StringBuffer buf2 = new StringBuffer();
		if (str.length() > len) {
			buf2.append(str.substring(0, len));
			buf2.append(taget);
		} else {
			buf2.append(str);
		}
		return buf2.toString();
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
	 * �ṩ��ȷ��С��λ�������봦��
	 * 
	 * @param v
	 *            ��Ҫ�������������
	 * @param scale
	 *            С���������λ
	 * @return ���������Ľ��
	 */
	public static float round(float v, int scale) {
		if (scale < 0) {
			throw new IllegalArgumentException(
					"The scale must be a positive integer or zero");
		}
		BigDecimal b = new BigDecimal(Double.toString(v));
		BigDecimal one = new BigDecimal("1");
		return b.divide(one, scale, BigDecimal.ROUND_HALF_UP).floatValue();
	}

	/**
	 * ��ʾ����������
	 * 
	 * @param str
	 *            String
	 * @param length
	 *            int
	 * @return String
	 */
	public static String subString(String str, int length) {
		int len = str.length();
		String strnew = "";
		if (len >= length) {
			strnew = str.substring(0, length - 2) + "....";
		} else {
			strnew = str;
		}
		return strnew;
	}

	/**
	 * �����ṩ�������������ɸ�����
	 * 
	 * @param str
	 *            String
	 * @param length
	 *            int
	 * @return String
	 */
	public static String makeString(String str, int length) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length; i++) {
			sb.append(str);
		}
		return sb.toString();
	}

	/**
	 * 
	 * @param str
	 *            String
	 * @param num
	 *            int
	 * @param tag
	 *            String
	 * @return String
	 */
	public static String newString(String str, int num, String tag) {
		StringBuffer sb = new StringBuffer();

		int len = str.length();
		int t = len / num;
		if (t > 0) {
			for (int i = 1; i <= t; i++) {
				if (i == 1) {
					sb.append(str.substring(0, num));
				} else {
					sb.append(tag + str.substring(num * (i - 1), num * (i)));
				}
			}
			sb.append(tag + str.substring(num * t));
		} else {
			sb.append(str);
		}
		return sb.toString();
	}

	/**
	 * html����
	 * 
	 * @param str
	 * @return
	 */
	public static final String htmlFilter(String str) {
		if (str == null)
			return "&nbsp;";
		char toCompare;
		StringBuffer replaceChar = new StringBuffer(str.length() + 256);
		int maxLength = str.length();
		try {
			for (int i = 0; i < maxLength; i++) {
				toCompare = str.charAt(i);
				// ���е� " �滻�� : &quot;
				if (toCompare == '"')
					replaceChar.append("&quot;");
				// ���е� < �滻�ɣ� &lt;
				else if (toCompare == '<')
					replaceChar.append("&lt;");
				// ���е� > �滻�ɣ� &gt;
				else if (toCompare == '>')
					replaceChar.append("&gt;");
				// ���е� & �滻��: &amp;
				else if (toCompare == '&') {
					if (i < maxLength - 1)
						if (str.charAt(i + 1) == '#') {
							replaceChar.append("&#");
							i++;
						} else
							replaceChar.append("&amp;");
				} else if (toCompare == ' ')
					replaceChar.append("&nbsp;");
				// ���е� \r\n ��using System.getProperty("line.separator") to get
				// it �� �滻�ɡ�<br>lihjk
				else if (toCompare == '\r')
					;// replaceChar.append("<br>");
				else if (toCompare == '\n')
					replaceChar.append("<br>");
				else
					replaceChar.append(toCompare);
			}// end for
		} catch (Exception e) {
			// System.out.println(e.getMessage());
		} finally {
			return replaceChar.toString();
		}
	}

	/**
	 * ���ַ��� t_String �е� value1 �滻Ϊ value2
	 * 
	 * @param t_String
	 * @param value1
	 *            ԭ�ַ�
	 * @param value2
	 *            ���ַ�
	 * @return
	 */
	public static String replaceString(String t_String, String value1,
			String value2) {
		while (t_String.indexOf(value1) != -1) {
			t_String = t_String.substring(0, t_String.indexOf(value1))
					+ value2
					+ t_String.substring(
							t_String.indexOf(value1) + value1.length(),
							t_String.length());
		}
		return t_String;
	}

	/**
	 * �ַ������ָ���ĳ���,���Ȳ������ұ߿ո�
	 * 
	 * @param t_String
	 * @param len
	 * @return
	 */
	public static String formatStrWithBlank(String t_String, int len) {
		return formatStrWithBlank(t_String, len, " ", true);
	}

	/**
	 * �ַ������ָ���ĳ���,���Ȳ������ұ߿ո�
	 * 
	 * @param t_String
	 * @param len
	 * @return
	 */
	public static String formatStrWithBlank(String t_String, int len,
			String defaultStr) {
		return formatStrWithBlank(t_String, len, defaultStr, true);
	}

	/**
	 * �ַ������ָ���ĳ���,���Ȳ�����ո�
	 * 
	 * @param t_String
	 *            Դ�ַ���
	 * @param len
	 *            ����
	 * @param bl
	 *            Ĭ��Ϊ�ҿո���
	 * @return
	 */
	public static String formatStrWithBlank(String t_String, int len,
			String defaultStr, boolean bl) {
		StringBuffer sbf = new StringBuffer();
		if (t_String.length() >= len) {
			return t_String.substring(0, len);
		} else {
			if (bl) {// �Ҳ���ո�
				sbf.append(t_String);
				for (int i = 0; i < len - t_String.length(); i++) {
					sbf.append(defaultStr);
				}
			} else {
				sbf.append("");
				for (int i = 0; i < len - t_String.length(); i++) {
					sbf.append(defaultStr);
				}
				sbf.append(t_String);
			}
			return sbf.toString();
		}
	}

	/**
	 * Numberת���ַ�
	 * 
	 * @param1: ת������
	 * @param2: ��ʽ��##.##
	 */
	public static String convertNumberToString(double number, String pattern) {
		String returnValue = "";
		try {
			DecimalFormat df = new DecimalFormat(pattern);
			returnValue = df.format(number);
		} catch (Exception e) {
		}
		return returnValue;
	}

	/**
	 * ��ʽ�����ַ���Ϊȱʡ�ַ���
	 * 
	 * @param fmtStr
	 * @param defaultStr
	 *            ����fmtStrΪ�ջ�""ʱ��ȱʡ�ַ���
	 * @return
	 */
	public static String formatNull(String fmtStr, String defaultStr) {
		String tmp = defaultStr;
		if (fmtStr == null || fmtStr.trim().length() == 0)
			return defaultStr;
		else
			return fmtStr;

	}

	/**
	 * �ַ�������
	 * 
	 * @param str
	 * @param enc
	 *            ����,��"GBK"
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String formatStrDecoder(String str, String enc) {
		String formatStr = null;
		if (str != null)
			try {
				formatStr = URLDecoder.decode(str, enc);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		return formatStr;
	}

	/**
	 * �ַ�������
	 * 
	 * @param str
	 * @param enc
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String formatStrEncoder(String str, String enc) {
		String formatStr = null;
		if (str != null)
			try {
				formatStr = URLEncoder.encode(str, enc);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		return formatStr;
	}

	public static String convertTimeToString(Timestamp time, String style) {
		String timeStr = "";
		if (style == null || style.length() == 0)
			style = "yyyy-MM-dd HH:mm:ss";
		if (time == null)
			return "";
		try {
			SimpleDateFormat df = new SimpleDateFormat(style);
			timeStr = df.format(time);
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}

		return timeStr;
	}

	public static String convertTimeToString(Timestamp time, String style,
			String defaultStr) {
		String timeStr = "";
		if (style == null || style.length() == 0)
			style = "yyyy-MM-dd HH:mm:ss";
		if (time == null)
			return defaultStr;
		try {
			SimpleDateFormat df = new SimpleDateFormat(style);
			timeStr = df.format(time);
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}

		return timeStr;
	}

	public static Date convertStringToDate(String srcStr, String style) {

		Date dateTmp = null;
		if (srcStr == null || srcStr.length() == 0)
			return null;
		if (style == null || style.length() == 0)
			style = "yyyy-MM-dd HH:mm:ss";

		try {
			SimpleDateFormat df = new SimpleDateFormat(style);
			dateTmp = df.parse(srcStr);
		} catch (Exception e) {
			e.printStackTrace();
			return new Date();
		}

		return dateTmp;
	}

	public static Timestamp convertStringToTimestamp(String timeStr) {
		if (timeStr == null || timeStr.length() == 0)
			return null;
		return Timestamp.valueOf(timeStr);
	}

	public static String getLastStrWithSplit(String srcStr, String split) {
		if (srcStr.lastIndexOf(split) != -1) {
			return srcStr.substring(srcStr.lastIndexOf(split), srcStr.length());
		}
		return "";
	}

	/**
	 * Դ�ַ���Ϊ777,888��Ϊ'777','888'
	 * 
	 * @param strStr
	 * @param split
	 *            Դ�ַ����ķָ���
	 * @param addSplit
	 *            ��Ҫ׷�ӵ�����
	 * @return
	 */
	public static String splitString(String strStr, String split,
			String addSplit) {
		if (strStr == null || strStr.length() == 0) {
			return "' '";
		}
		String id[] = strStr.split(split);
		StringBuffer tmp = new StringBuffer();
		for (int i = 0; i < id.length; i++) {
			tmp.append(addSplit + id[i] + addSplit + split);
		}
		strStr = tmp.toString().substring(0, tmp.toString().length() - 1);

		if (strStr == null || strStr.length() == 0) {
			strStr = "' '";
		}
		return strStr;
	}

	/**
	 * srcStr�Ƿ����destStr
	 * 
	 * @param srcStr
	 * @param destStr
	 * @return
	 */
	public static boolean isContain(String srcStr, String destStr, boolean split) {
		boolean is = false;
		if (srcStr == null || srcStr.length() < 1) {
			return is;
		}
		if (split) {
			srcStr = StringUtil.splitString(srcStr, ",", "'");
			destStr = StringUtil.splitString(destStr, ",", "'");
		}
		int post = srcStr.indexOf(destStr);
		if (post >= 0) {
			is = true;
		}
		return is;
	}

	public static boolean isContain(String srcStr, String destStr) {
		return StringUtil.isContain(srcStr, destStr, false);
	}

	/**
	 * ȥ�����пո�
	 * 
	 * @param value
	 * @return
	 */
	public static String trimAll(String value) {
		String result = value;
		if (result == null)
			return result;
		char ch[] = result.toCharArray();
		StringBuffer strBuf = new StringBuffer();
		strBuf.append("");
		int endIndex = -1;
		for (int i = 0; i < ch.length; i++) {
			if (Character.isWhitespace(ch[i])) {
				endIndex = i;
			} else {
				strBuf.append(ch[i]);
			}
		}

		return strBuf.toString();
	}

	/**
	 * ���ַ���ǰ��ĩβ���ӷָ
	 * 
	 * @param srcStr
	 * @param split
	 * @return
	 */
	public static String addSplitAndFB(String srcStr, String split) {
		// �����split��β����ĩβ������,�����split��ʼ����ͷ������
		if (srcStr == null || srcStr.length() == 0)
			return "";
		if (!split.equalsIgnoreCase(srcStr.substring(0, 1))) {
			srcStr = split + srcStr;
		}
		if (!split.equalsIgnoreCase(srcStr.substring(srcStr.length() - 1))) {
			srcStr = srcStr + split;
		}
		return srcStr;
	}

	/**
	 * ȥ��ǰ��ķָ��
	 * 
	 * @param srcStr
	 * @param split
	 * @return
	 */
	public static String offSplitAndFB(String srcStr, String split) {
		if (srcStr == null || srcStr.length() == 0)
			return "";
		if (split.equalsIgnoreCase(srcStr.substring(0, 1))) {
			if (srcStr.length() > 1) {
				srcStr = srcStr.substring(1, srcStr.length());
			} else {
				return "";
			}
		}
		if (split.equalsIgnoreCase(srcStr.substring(srcStr.length() - 1))) {
			if (srcStr.length() > 1) {
				srcStr = srcStr.substring(0, srcStr.length() - 1);
			} else {
				return "";
			}
		}

		return srcStr;
	}

	/**
	 * ����ظ����ֵĵ���
	 * 
	 * @param srcStr
	 * @return
	 */
	public static String offReStr(String srcStr, String split) {
		StringBuffer tmp = new StringBuffer();
		srcStr = StringUtil.offSplitAndFB(srcStr, split);
		String[] newIDs = srcStr.split(split);
		for (int count = 0; count < newIDs.length; count++) {
			if (StringUtil.isContain(tmp.toString(), newIDs[count], true)) {

			} else {
				tmp.append(newIDs[count] + split);
			}
		}
		return tmp.toString();
	}

	/**
	 * 
	 * @param o
	 *            String
	 * @return String
	 */

	public static void main(String[] args) {
		String b = "JScript С�������λ������������ĺ�����"
				+ "ʲô�� ...... ������, ����: JScript С����"
				+ "����λ������������ĺ�����ʲô�� ... JScri" + "pt ��round()�ҿ���˵��ֻ��һ������ȡ����"
				+ "vbs ��round() ���ƵĿ���ȡ�ø���λ������"
				+ "�����뺯����ʲô�� ��1.237802456 ---> 1" + "���ڶ����JavaScript����... subs"
				+ "tring(start,end) ��start��ʼ��end����"
				+ "��ȫ�����ء� ��)������������math ... abs()��������"
				+ "ֵ��sin(),cos() �����ҷ�����:asin(), acos() "
				+ "���з����У�tan(),atan()�������룺round() ƽ��"
				+ "�� ... 2��javascript�е�ϵͳ����javascript�е�"
				+ "ϵͳ�����ֳ��ڲ� ... www.dapt.org/Article_Pri"
				+ "nt.asp?ArticleID=34 - 17k - ������� - ��ҳ��" + "�� - ������ҳ ";
		// byte[] b2 = b.getBytes();

		// ArrayList arylist = StringUtil.cutString(b);
		// String a = StringUtil.GetsplitString(b,1,15,"UTF8");

	}

	/**
	 * ���ַ���strText ��strInFormat ��ʽ ת����strOutFormat��ʽ
	 * 
	 * @param strText
	 *            String �����ַ���
	 * @param strInFormat
	 *            String �����ʽ
	 * @param strOutFormat
	 *            �����ʽ
	 * @throws Exception
	 * @return String
	 */
	public static String switch1(String strText, String strInFormat,
			String strOutFormat) {
		String strResults = "";
		try {
			strResults = new String(strText.getBytes(strInFormat), strOutFormat);
		} catch (Exception ex) {

		}
		return strResults;
	}

	/**
	 * ���ַ���strText ��strInFormat ��ʽ ת����strOutFormat��ʽ
	 * 
	 * @param strText
	 *            String �����ַ���
	 * @param strOutFormat
	 *            �����ʽ
	 * @throws Exception
	 * @return String
	 * 
	 *         FIXME �������淶 ��������
	 */
	public static String switch1(String strText, String strOutFormat) {
		String strResults = "";
		try {
			strResults = new String(strText.getBytes(), strOutFormat);
		} catch (Exception ex) {

		}
		return strResults;
	}

	/**
	 * ��ȡ��Ӣ�Ļ�ϳ����ַ�������
	 * 
	 * @param res
	 *            ����ȡ�ַ���
	 * @param start
	 *            ��ʼλ��
	 * @param length
	 *            ��ȡ����
	 * @param CharSet
	 *            ��ȡ���ַ��������ʽ���������ĸ�ʽΪ��UTF8��UTF8�����ַ������㣬����ʹ��GBK��ĳЩ�ַ��������ݣ����磺���ġ�������
	 *            ��
	 * @return
	 * 
	 *         FIXME �������淶
	 */
	public static String getsplitString(String res, int start, int length,
			String CharSet) {
		int istart, ilen, i, j, ilenByte, is, il;
		istart = 0;
		is = 0;
		ilen = 0;
		il = 0;

		try {
			byte[] resBytes = res.getBytes(CharSet);
			ilenByte = resBytes.length;

			for (i = 0; i < ilenByte; i++) {
				istart = istart + 1;
				if (istart <= start) {
					is = is + 1;
				}
				if (istart >= start) {
					ilen = ilen + 1;
					il = il + 1;
				}
				if (resBytes[i] < 0) {
					i = i + 2;
					istart = istart + 1;
					if (istart < start) {
						is = is + 2;
					}
					if (istart >= start) {
						ilen = ilen + 1;
						il = il + 2;
					}
				}
				if (ilen >= length)
					i = ilenByte;
			}

			byte[] dest = new byte[il];

			for (j = 0; j < il; j++) {
				dest[j] = resBytes[is + j - 1];
			}

			return new String(dest, CharSet);

		} catch (Exception e) {
			return null;
		}
	}

	public static ArrayList cutString(String str) {
		ArrayList arylist = new ArrayList();

		// �������ݳ���
		int allLength = str.length();
		// ��ȡ��ʣ�೤��
		int lastLength = allLength;

		String lastStr = "";
		String strCut = "";

		int intCut = 80;

		if (allLength > intCut) {
			while (lastLength >= 0) {

				if (str.length() < intCut) {
					strCut = str;
				} else {
					strCut = StringUtil.getsplitString(str, 1, intCut, "UTF8");
					lastStr = StringUtil.getsplitString(str, intCut,
							str.length() * 2, "UTF8");
				}

				str = lastStr + " ";

				arylist.add(strCut);

				lastLength = lastLength - strCut.length();

			}
		}
		return arylist;
	}

	public static String escape2Sql(String str) {
		if (str == null) {
			return null;
		}
		str = str.replaceAll("'", "'||chr(39)||'");
		str = str.replaceAll("&", "'||chr(38)||'");
		str = str.replaceAll("<", "'||chr(60)||'");
		str = str.replaceAll(">", "'||chr(62)||'");
		str = str.replaceAll("%", "'||chr(37)||'");
		return str;

	}

	public static String StringFilter(String str) throws PatternSyntaxException {
		// ֻ������ĸ������
		// String regEx = "[^a-zA-Z0-9]";
		// ��������������ַ�
		String regEx = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~��@#��%����&*��������+|{}������������������������]";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(str);
		return m.replaceAll("").trim();
	}

	/**
	 * �����������ָ��ַ���
	 * 
	 * @param str
	 *            String ԭʼ�ַ���
	 * @param splitsign
	 *            String �ָ���
	 * @return String[] �ָ����ַ�������
	 */
	public static String[] split(String str, String splitsign) {
		int index;
		if (str == null || splitsign == null) {
			return null;
		}
		ArrayList al = new ArrayList();
		while ((index = str.indexOf(splitsign)) != -1) {
			al.add(str.substring(0, index));
			str = str.substring(index + splitsign.length());
		}
		al.add(str);
		return (String[]) al.toArray(new String[0]);
	}

	/**
	 * �����������滻�ַ���
	 * 
	 * @param from
	 *            String ԭʼ�ַ���
	 * @param to
	 *            String Ŀ���ַ���
	 * @param source
	 *            String ĸ�ַ���
	 * @return String �滻����ַ���
	 */
	public static String replace(String from, String to, String source) {
		if (source == null || from == null || to == null)
			return null;
		StringBuffer str = new StringBuffer("");
		int index = -1;
		while ((index = source.indexOf(from)) != -1) {
			str.append(source.substring(0, index) + to);
			source = source.substring(index + from.length());
			index = source.indexOf(from);
		}
		str.append(source);
		return str.toString();
	}

	/**
	 * �滻�ַ��������ܹ���HTMLҳ����ֱ����ʾ(�滻˫���ź�С�ں�)
	 * 
	 * @param str
	 *            String ԭʼ�ַ���
	 * @return String �滻����ַ���
	 */
	public static String htmlencode(String str) {
		if (str == null) {
			return null;
		}
		return replace("\"", "&quot;", replace("<", "&lt;", str));
	}

	/**
	 * �滻�ַ��������������ת����ԭʼ�루�滻��˫���ź�С�ںţ�
	 * 
	 * @param str
	 *            String
	 * @return String
	 */
	public static String htmldecode(String str) {
		if (str == null) {
			return null;
		}

		return replace("&quot;", "\"", replace("&lt;", "<", str));
	}

	private static final String _BR = "<br/>";

	/**
	 * ������������ҳ����ֱ����ʾ�ı����ݣ��滻С�ںţ��ո񣬻س���TAB
	 * 
	 * @param str
	 *            String ԭʼ�ַ���
	 * @return String �滻����ַ���
	 */
	public static String htmlshow(String str) {
		if (str == null) {
			return null;
		}

		str = replace("<", "&lt;", str);
		str = replace(" ", "&nbsp;", str);
		str = replace("\r\n", _BR, str);
		str = replace("\n", _BR, str);
		str = replace("\t", "&nbsp;&nbsp;&nbsp;&nbsp;", str);
		return str;
	}

	/**
	 * ��������������ָ���ֽڳ��ȵ��ַ���
	 * 
	 * @param str
	 *            String �ַ���
	 * @param length
	 *            int ָ������
	 * @return String ���ص��ַ���
	 */
	public static String toLength(String str, int length) {
		if (str == null) {
			return null;
		}
		if (length <= 0) {
			return "";
		}
		try {
			if (str.getBytes("GBK").length <= length) {
				return str;
			}
		} catch (Exception e) {
		}
		StringBuffer buff = new StringBuffer();

		int index = 0;
		char c;
		length -= 3;
		while (length > 0) {
			c = str.charAt(index);
			if (c < 128) {
				length--;
			} else {
				length--;
				length--;
			}
			buff.append(c);
			index++;
		}
		buff.append("...");
		return buff.toString();
	}

	/**
	 * �����������ж��Ƿ�Ϊ����
	 * 
	 * @param str
	 *            ������ַ���
	 * @return ����������true,���򷵻�false
	 */
	public static boolean isInteger(String str) {
		Pattern pattern = Pattern.compile("^[-\\+]?[\\d]+$");
		return pattern.matcher(str).matches();
	}

	/**
	 * �ж��Ƿ�Ϊ������������double��float
	 * 
	 * @param str
	 *            ������ַ���
	 * @return �Ǹ���������true,���򷵻�false
	 */
	public static boolean isDouble(String str) {
		Pattern pattern = Pattern.compile("^[-\\+]?\\d+\\.\\d+$");
		return pattern.matcher(str).matches();
	}

	/**
	 * �ж��ǲ��ǺϷ��ַ� c Ҫ�жϵ��ַ�
	 */
	public static boolean isLetter(String str) {
		if (str == null || str.length() < 0) {
			return false;
		}
		Pattern pattern = Pattern.compile("[\\w\\.-_]*");
		return pattern.matcher(str).matches();
	}

	/**
	 * ��ָ�����ַ�������ȡEmail content ָ�����ַ���
	 * 
	 * @param content
	 * @return
	 */
	public static String parse(String content) {
		String email = null;
		if (content == null || content.length() < 1) {
			return email;
		}
		// �ҳ�����@
		int beginPos;
		int i;
		String token = "@";
		String preHalf = "";
		StringBuffer sufHalf = new StringBuffer();

		beginPos = content.indexOf(token);
		if (beginPos > -1) {
			// ǰ��ɨ��
			String s = null;
			i = beginPos;
			while (i > 0) {
				s = content.substring(i - 1, i);
				if (isLetter(s))
					preHalf = s + preHalf;
				else
					break;
				i--;
			}
			// ����ɨ��
			i = beginPos + 1;
			while (i < content.length()) {
				s = content.substring(i, i + 1);
				if (isLetter(s))
					sufHalf.append(s);
				else
					break;
				i++;
			}
			// �жϺϷ���
			email = preHalf + "@" + sufHalf.toString();
			if (isEmail(email)) {
				return email;
			}
		}
		return null;
	}

	/**
	 * �����������ж�������ַ����Ƿ����Email��ʽ.
	 * 
	 * @param str
	 *            ������ַ���
	 * @return ��Email��ʽ����true,���򷵻�false
	 */
	public static boolean isEmail(String email) {
		if (email == null || email.length() < 1 || email.length() > 256) {
			return false;
		}
		Pattern pattern = Pattern
				.compile("^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$");
		return pattern.matcher(email).matches();
	}

	/**
	 * �����������ж�������ַ����Ƿ�Ϊ������
	 * 
	 * @param str
	 *            ������ַ���
	 * @return ����Ǵ����ַ���true,���򷵻�false
	 */
	public static boolean isChinese(String str) {
		Pattern pattern = Pattern.compile("[\u0391-\uFFE5]+$");
		return pattern.matcher(str).matches();
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

	/**
	 * �����������ж��Ƿ�Ϊ����
	 * 
	 * @param x
	 * @return
	 */
	public static boolean isPrime(int x) {
		if (x <= 7) {
			if (x == 2 || x == 3 || x == 5 || x == 7)
				return true;
		}
		int c = 7;
		if (x % 2 == 0)
			return false;
		if (x % 3 == 0)
			return false;
		if (x % 5 == 0)
			return false;
		int end = (int) Math.sqrt(x);
		while (c <= end) {
			if (x % c == 0) {
				return false;
			}
			c += 4;
			if (x % c == 0) {
				return false;
			}
			c += 2;
			if (x % c == 0) {
				return false;
			}
			c += 4;
			if (x % c == 0) {
				return false;
			}
			c += 2;
			if (x % c == 0) {
				return false;
			}
			c += 4;
			if (x % c == 0) {
				return false;
			}
			c += 6;
			if (x % c == 0) {
				return false;
			}
			c += 2;
			if (x % c == 0) {
				return false;
			}
			c += 6;
		}
		return true;
	}

	/**
	 * ���������������ת�ɴ�д
	 * 
	 * @param str
	 *            �����ַ���
	 * @return String �����ת���ɴ�д����ַ���
	 */
	public static String hangeToBig(String str) {
		double value;
		try {
			value = Double.parseDouble(str.trim());
		} catch (Exception e) {
			return null;
		}
		char[] hunit = { 'ʰ', '��', 'Ǫ' }; // ����λ�ñ�ʾ
		char[] vunit = { '��', '��' }; // ������ʾ
		char[] digit = { '��', 'Ҽ', '��', '��', '��', '��', '½', '��', '��', '��' }; // ���ֱ�ʾ
		long midVal = (long) (value * 100); // ת��������
		String valStr = String.valueOf(midVal); // ת�����ַ���

		String head = valStr.substring(0, valStr.length() - 2); // ȡ��������
		String rail = valStr.substring(valStr.length() - 2); // ȡС������

		StringBuffer prefix = new StringBuffer(); // ��������ת���Ľ��
		StringBuffer suffix = new StringBuffer(); // С������ת���Ľ��
		// ����С����������
		if (rail.equals("00")) { // ���С������Ϊ0
			suffix.append("��");
		} else {
			suffix.append(digit[rail.charAt(0) - '0'] + "��"
					+ digit[rail.charAt(1) - '0'] + "��"); // ����ѽǷ�ת������
		}
		// ����С����ǰ�����
		char[] chDig = head.toCharArray(); // ����������ת�����ַ�����
		char zero = '0'; // ��־'0'��ʾ���ֹ�0
		byte zeroSerNum = 0; // ��������0�Ĵ���
		for (int i = 0; i < chDig.length; i++) { // ѭ������ÿ������
			int idx = (chDig.length - i - 1) % 4; // ȡ����λ��
			int vidx = (chDig.length - i - 1) / 4; // ȡ��λ��
			if (chDig[i] == '0') { // �����ǰ�ַ���0
				zeroSerNum++; // ����0��������
				if (zero == '0') { // ��־
					zero = digit[0];
				} else if (idx == 0 && vidx > 0 && zeroSerNum < 4) {
					prefix.append(vunit[vidx - 1]);
					zero = '0';
				}
				continue;
			}
			zeroSerNum = 0; // ����0��������
			if (zero != '0') { // �����־��Ϊ0,�����,������,��ʲô��
				prefix.append(zero);
				zero = '0';
			}
			prefix.append(digit[chDig[i] - '0']); // ת�������ֱ�ʾ
			if (idx > 0)
				prefix.append(hunit[idx - 1]);
			if (idx == 0 && vidx > 0) {
				prefix.append(vunit[vidx - 1]); // �ν���λ��Ӧ�ü��϶�������,��
			}
		}

		if (prefix.length() > 0)
			prefix.append('Բ'); // ����������ִ���,����Բ������
		return prefix.append(suffix).toString(); // ������ȷ��ʾ
	}

	/**
	 * ����������ȥ���ַ������ظ������ַ���
	 * 
	 * @param str
	 *            ԭ�ַ�������������ַ������ÿո�����Ա�ʾ���ַ���
	 * @return String ����ȥ���ظ����ַ�������ַ���
	 */
	private static String removeSameString(String str) {
		Set mLinkedSet = new LinkedHashSet();// set���ϵ����������Ӽ��������ظ�
		String[] strArray = str.split(" ");// ���ݿո�(������ʽ)�ָ��ַ���
		StringBuffer sb = new StringBuffer();

		for (int i = 0; i < strArray.length; i++) {
			if (!mLinkedSet.contains(strArray[i])) {
				mLinkedSet.add(strArray[i]);
				sb.append(strArray[i] + " ");
			}
		}
		return sb.toString();
	}

	/**
	 * �������������������ַ�
	 * 
	 * @param src
	 * @return
	 */
	public static String encoding(String src) {
		if (src == null)
			return "";
		StringBuilder result = new StringBuilder();
		if (src != null) {
			src = src.trim();
			for (int pos = 0; pos < src.length(); pos++) {
				switch (src.charAt(pos)) {
				case '\"':
					result.append("&quot;");
					break;
				case '<':
					result.append("&lt;");
					break;
				case '>':
					result.append("&gt;");
					break;
				case '\'':
					result.append("&apos;");
					break;
				case '&':
					result.append("&amp;");
					break;
				case '%':
					result.append("&pc;");
					break;
				case '_':
					result.append("&ul;");
					break;
				case '#':
					result.append("&shap;");
					break;
				case '?':
					result.append("&ques;");
					break;
				default:
					result.append(src.charAt(pos));
					break;
				}
			}
		}
		return result.toString();
	}

	/**
	 * �����������ж��ǲ��ǺϷ����ֻ�����
	 * 
	 * @param handset
	 * @return boolean
	 */
	public static boolean isHandset(String handset) {
		try {
			String regex = "^1[\\d]{10}$";
			Pattern pattern = Pattern.compile(regex);
			Matcher matcher = pattern.matcher(handset);
			return matcher.matches();

		} catch (RuntimeException e) {
			return false;
		}
	}

	/**
	 * ���������������������ַ�
	 * 
	 * @param src
	 * @return
	 */
	public static String decoding(String src) {
		if (src == null)
			return "";
		String result = src;
		result = result.replace("&quot;", "\"").replace("&apos;", "\'");
		result = result.replace("&lt;", "<").replace("&gt;", ">");
		result = result.replace("&amp;", "&");
		result = result.replace("&pc;", "%").replace("&ul", "_");
		result = result.replace("&shap;", "#").replace("&ques", "?");
		return result;
	}

	public static String replaceAll(String str) {
		if (str == null) {
			return null;
		}
		str = str.replace("chr(41)", "{'");
		str = str.replace("chr(42)", "'}");
		str = str.replace("chr(43)", "','");
		str = str.replace("chr(44)", "':'");
		str = str.replace("chr(39)", "\\'");
		str = str.replace("chr(38)", "&");
		str = str.replace("chr(60)", "<");
		str = str.replace("chr(62)", ">");
		str = str.replace("chr(37)", "%");
		str = str.replace("chr(50)", "#");
		str = str.replace("chr(40)", "\\\\");
		str = str.replace("chr(64)", "\\\r\\\n");
		str = str.replaceAll("chr()", "\"");
		return str;
	}

	public static String replaceAll2(String str) {
		if (str == null) {
			return null;
		}
		str = str.replace("chr(41)", "{'");
		str = str.replace("chr(42)", "'}");
		str = str.replace("chr(43)", "','");
		str = str.replace("chr(44)", "':'");
		str = str.replace("chr(39)", "\\'");
		str = str.replace("chr(45)", "\"");
		str = str.replace("chr(38)", "&");
		str = str.replace("chr(60)", "<");
		str = str.replace("chr(62)", ">");
		str = str.replace("chr(37)", "%");
		str = str.replace("chr(50)", "#");
		str = str.replace("chr(40)", "\\\\");
		str = str.replace("chr(64)", "\\\r\\\n");
		str = str.replaceAll("chr()", "\"");
		return str;
	}

	public static String descape(String str) {
		if (str == null) {
			return null;
		}
		str = str.replace("{'", "chr(41)");
		str = str.replace("'}", "chr(42)");
		str = str.replace("','", "chr(43)");
		str = str.replace("':'", "chr(44)");
		str = str.replace("#", "chr(50)");
		str = str.replace("\"", "chr(45)");
		str = str.replace("'", "chr(39)");
		str = str.replace("&", "chr(38)");
		str = str.replace("<", "chr(60)");
		str = str.replace(">", "chr(62)");
		str = str.replace("%", "chr(37)");
		str = str.replace("\\", "chr(40)");
		str = str.replace("\r\n", "chr(64)");
		return str;
	}

	/**
	 * �������ݿ�ʱ ",<>ת����:&quot,&lt;&gt; ����json��ѯʱ��������ת����Ӧ�ַ�(���޸Ĳ��룬�Ķ���Χ��)
	 * 
	 * @param str
	 * @return
	 */
	public static String undecodingStr(String str) {
		str = str.replaceAll("\"", "&quot");
		str = str.replaceAll("<", "&lt");
		str = str.replaceAll(">", "&gt;");
		return str;
	}

	/**
	 * @Title: StringFilter
	 * @Description: ���˵�˫����
	 * @param str
	 * @return
	 * @throws PatternSyntaxException
	 * @return String
	 * @throws
	 * @author XiMing.Fu
	 */
	public static String StringFilter1(String str)
			throws PatternSyntaxException {
		String regEx = "[\'\"]";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(str);
		return m.replaceAll("").trim();
	}

	public static String getUUID() {
		String uuid = UUID.randomUUID().toString();
		return uuid;
	}

	// ����97��122�������(a-z�ļ�λֵ)
	public static String getRandomString() {
		StringBuffer generateRandStr = new StringBuffer();
		Random rand = new Random();
		int length = 6;
		char ch;
		for (int i = 0; i < length; i++) {
			int randNum = Math.abs(rand.nextInt()) % 26 + 97; // ����97��122�������(a-z�ļ�λֵ)
			ch = (char) randNum;
			generateRandStr.append(ch);
		}
		return generateRandStr.toString();
	}

	public static String getSavePath(String IMGPATH, String fileName) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String date = sdf.format(new Date()).toString();
		if (!(fileName.endsWith(".jpg"))) {
			fileName = fileName + ".jpg";
		}
		String randStr = StringUtil.getRandomString();
		return IMGPATH + File.separator + date + File.separator + randStr
				+ fileName;
	}

	public static String getDate() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		return sdf.format(new Date()).toString();
	}

}