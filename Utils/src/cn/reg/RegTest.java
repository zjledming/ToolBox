package cn.reg;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import org.junit.Test;

public class RegTest {

	public static void main(String[] args) {
		// [\\s\\S]*?£ºÆ¥ÅäÈÎÒâ×Ö·ûÈÎÒâ´ÎÊı
		// [\\s\\S]:Æ¥ÅäÈÎÒâ×Ö·û \s£º¿Õ°××Ö·û \S£º·Ç¿Õ°××Ö·û
		// *?£º³öÏÖÈÎÒâ´Î *£º0´Î»òÕß¶à´Î ?:0»ò1´Î
		Pattern pattern = Pattern.compile("(<MSG>[\\s\\S]*?</MSG>)");
		Matcher matcher = pattern.matcher("ç³ÎÅç³ÎÅ<MSG>ÈÃç³ÎÅç³ÎÅ	s</MSG>ç³ÎÅç³ÎÅ");
		while (matcher.find()) {
			String textarea = matcher.group(1);
			System.out.println(textarea);
		}
		
		phhanzi("fewf2313()&*·É·ÇÎÒ·½2132¶îµçfewfÍâ");
	}
	
	
	public static void  phhanzi(String value){
		
		String regExp = "[\u4E00-\u9FA5]";  
		Pattern p = Pattern.compile(regExp); 
		Matcher m = p.matcher(value); 
		while (m.find()) {
			System.out.println(m.group());
			
		}
		System.out.println("fewf2313()&*·É·ÇÎÒ·½2132¶îµçfewfÍâ".replaceAll("[\u4E00-\u9FA5]", ""));
	}

	public static String StringFilter(String str) throws PatternSyntaxException {
		// Ö»ÔÊĞí×ÖÄ¸ºÍÊı×Ö
		// String regEx = "[^a-zA-Z0-9]";
		// Çå³ıµôËùÓĞÌØÊâ×Ö·û
		String regEx = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~£¡@#£¤%¡­¡­&*£¨£©¡ª¡ª+|{}¡¾¡¿¡®£»£º¡±¡°¡¯¡££¬¡¢£¿]";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(str);
		return m.replaceAll("").trim();
	}

	/**
	 * ¹¦ÄÜÃèÊö£ºÅĞ¶ÏÊÇ·ñÎªÕûÊı
	 * 
	 * @param str
	 *            ´«ÈëµÄ×Ö·û´®
	 * @return ÊÇÕûÊı·µ»Øtrue,·ñÔò·µ»Øfalse
	 */
	public static boolean isInteger(String str) {
		Pattern pattern = Pattern.compile("^[-\\+]?[\\d]+$");
		return pattern.matcher(str).matches();
	}

	/**
	 * ÅĞ¶ÏÊÇ·ñÎª¸¡µãÊı£¬°üÀ¨doubleºÍfloat
	 * 
	 * @param str
	 *            ´«ÈëµÄ×Ö·û´®
	 * @return ÊÇ¸¡µãÊı·µ»Øtrue,·ñÔò·µ»Øfalse
	 */
	public static boolean isDouble(String str) {
		Pattern pattern = Pattern.compile("^[-\\+]?\\d+\\.\\d+$");
		return pattern.matcher(str).matches();
	}

	/**
	 * ÅĞ¶ÏÊÇ²»ÊÇºÏ·¨×Ö·û c ÒªÅĞ¶ÏµÄ×Ö·û
	 */
	public static boolean isLetter(String str) {
		if (str == null || str.length() < 0) {
			return false;
		}
		Pattern pattern = Pattern.compile("[\\w\\.-_]*");
		return pattern.matcher(str).matches();
	}

	/**
	 * ¹¦ÄÜÃèÊö£ºÅĞ¶ÏÊäÈëµÄ×Ö·û´®ÊÇ·ñ·ûºÏEmailÑùÊ½.
	 * 
	 * @param str
	 *            ´«ÈëµÄ×Ö·û´®
	 * @return ÊÇEmailÑùÊ½·µ»Øtrue,·ñÔò·µ»Øfalse
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
	 * ¹¦ÄÜÃèÊö£ºÅĞ¶ÏÊäÈëµÄ×Ö·û´®ÊÇ·ñÎª´¿ºº×Ö
	 * 
	 * @param str
	 *            ´«ÈëµÄ×Ö·û´Ü
	 * @return Èç¹ûÊÇ´¿ºº×Ö·µ»Øtrue,·ñÔò·µ»Øfalse
	 */
	public static boolean isChinese(String str) {
		Pattern pattern = Pattern.compile("[\u0391-\uFFE5]+$");
		return pattern.matcher(str).matches();
	}

	/**
	 * ¹¦ÄÜÃèÊö£ºÅĞ¶ÏÊÇ²»ÊÇºÏ·¨µÄÊÖ»úºÅÂë
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
	 * Number×ª»»×Ö·û
	 * 
	 * @param1: ×ª»»Êı¾İ
	 * @param2: ¸ñÊ½£º##.##
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

	String sqlTemplate;

	/**
	 * ´ÓÄ£°åÖĞÌáÈ¡±äÁ¿
	 * 
	 * @param template
	 * @return
	 */
	public List<String> getVariables() {
		List<String> resultList = new ArrayList<String>();
		Pattern pattern = Pattern.compile("\\$\\{([^\\}]*)");//
		Matcher matcher = pattern.matcher(this.sqlTemplate);
		while (matcher.find()) {
			resultList.add(matcher.group(1));
		}
		return resultList;
	}

	/**
	 * ÓÃ¡±?¡±Ìæ»»Ä£°åÖĞµÄ±äÁ¿
	 * 
	 * @param template
	 * @return
	 */
	public String toPreparedSQL() {
		Pattern pattern = Pattern.compile("\\$\\{([^\\}]*)\\}");//
		Matcher matcher = pattern.matcher(this.sqlTemplate);
		return matcher.replaceAll("?");
	}

	public static void main1(String[] args) {
		String resutl = StringFilter("'fewfew/''fewfewfewf'");
		System.out.println(resutl);

		// ²âÊÔ2:Æ¥Åä³öËùÓĞµÄ<IMG...>
		String temp = "<P><IMG border=0 src='http://172.16.17.232:8080/creatorepp/spmanager/eWebEditor/uploadfile/20140121143133876.jpg'>fwefewwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwewfewfwe<IMG border=0 src='http://172.16.17.232:8080/creatorepp/spmanager/eWebEditor/uploadfile/20140121152222534.jpg'>fwfewfew<IMG border=0 src='http://172.16.17.232:8080/creatorepp/spmanager/eWebEditor/uploadfile/20140121152232577.jpg'></P>";
		String regEx = ".*http://.*.jpg.*";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(temp);
		// System.out.println(m.group());

		// ²éÕÒÒÔJava¿ªÍ·,ÈÎÒâ½áÎ²µÄ×Ö·û´®
		Pattern pattern = Pattern.compile("^Java.*");
		Matcher matcher = pattern.matcher("Java²»ÊÇÈË");
		boolean b = matcher.matches();
		// µ±Ìõ¼şÂú×ãÊ±£¬½«·µ»Øtr£¬·ñÔò·µ»Øfalse
		System.out.println("=="+b);

		String[] arrString = temp.split("IMG");
		System.out.println(arrString.length);
		if (arrString.length > 1) {
			for (int i = 0; i < arrString.length; i++) {
				System.out.println(arrString[i]);

			}
		}

		// ½ØÈ¡ http»òÕßhttps ... .jpg
		String s = "°¡°¡°¡°¡°¡°¡°¡  https://192.168.0.102/relativeEventDetail.do?mode=view&id=895\nhttps://192.168.0.102/relativeEventDetail.do?mode=view&id=844";
		// p = Pattern.compile("((https|http)://\\S{1,})\\s*'\\>");
		p = Pattern.compile("((https|http)://\\S{1,})\\s*'\\>");
		m = p.matcher(temp);
		String path;
		while (m.find()) {
			System.out.println("--" + m.group(1));
			path = m.group(1) + ".jpg";
			System.out.println("--" + path);

		}

	}

	/**
	 * @Title: StringFilter
	 * @Description: ¹ıÂËµ¥Ë«ÒıºÅ
	 * @param str
	 * @return
	 * @throws PatternSyntaxException
	 * @return String
	 * @throws
	 * @author XiMing.Fu
	 */
	public static String StringFilter_(String str)
			throws PatternSyntaxException {
		String regEx = "[\'\"]";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(str);
		return m.replaceAll("").trim();
	}

	/**
	 * @Title: String
	 * @Description: ÑéÖ¤ÈıÎ»Ò»¶ººÅ
	 * @return void
	 * @throws
	 * @author XiMing.Fu
	 */
	@Test
	public void vailidation() {

		/*
		 * [0-9]{1,3}\\£º1-3Î»Êı×Ö,¿ªÍ·£» ([0-9]{3}\\,)*£º3Î»Êı×Ö,ÖĞ¼ä
		 * ³öÏÖ0´Î»ò¶à´Î£»[0-9]{3}£º3Î»ÓĞĞ§Êı×Ö£»((\\.)[0-9]+)?£º.+1Î»Êı×Ö ³öÏÖ1´Î»òÕß¶à´Î
		 */
		Pattern pattern = Pattern
				.compile("[0-9]{1,3}\\,([0-9]{3}\\,)*[0-9]{3}((\\.)[0-9]+)?");
		String content = "10,014";
		Matcher matcher = pattern.matcher(content);
		while (matcher.find()) {
			System.out.println(matcher.group());
		}

	}

	/**
	 * @Title: vailidation1
	 * @Description: ÅĞ¶ÏÊÇ·ñÊÇ 1µ½4 ÓÉ¶ººÅ·Ö¸ôµÄ×Ö·û´®
	 * @return void
	 * @throws
	 * @author XiMing.Fu
	 */
	@Test
	public void vailidation1() {

		/*
		 * ^([1-4]+[,]))*£º1-4Ö®¼äµÄÊı×Ö+, ³öÏÖ0´Î»ò¶à´Î £¬¿ªÍ·£» ([1-4]+)$£º1-4Ö®¼äµÄÊı×Ö ³öÏÖ1´Î»òÕß¶à´Î£¬½áÎ²
		 */
		String reg = "^([1-4]+[,])*([1-4]+)$";
		Pattern pattern = Pattern.compile(reg);
		String str = "1,2,3,3";
		Matcher matcher = pattern.matcher(str);
		System.out.println(matcher.matches());
		while (matcher.find()) {
			System.out.println(matcher.group());
		}

	}
	
	/**
	 * ´Ó
	 * <?xml version="1.0" encoding="gbk"?>
<Context crossContext="true" path="/creatorepp" docBase="E:\workspace\hyxfs\creatorepp" reloadable="false">
	<ResourceLink name="reportsource" global="reportsource" type="javax.sql.DataSource" />
</Context>
	 * ÖĞÈ¡"E:\workspace\hyxfs\creatorepp"
	 * @param content
	 * @return
	 * @throws Exception
	 */
	public String getByReg(String content) throws Exception{
		StringBuffer sb = new StringBuffer();
		String reg = "docBase=\".*creatorepp\"";
		Pattern pattern = Pattern.compile(reg);
		Matcher matcher = pattern.matcher(content);
		while (matcher.find()) {
			sb.append(matcher.group(0));
		}
		return sb.toString();
	}
	
}
