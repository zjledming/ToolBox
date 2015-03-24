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
		// [\\s\\S]*?��ƥ�������ַ��������
		// [\\s\\S]:ƥ�������ַ� \s���հ��ַ� \S���ǿհ��ַ�
		// *?����������� *��0�λ��߶�� ?:0��1��
		Pattern pattern = Pattern.compile("(<MSG>[\\s\\S]*?</MSG>)");
		Matcher matcher = pattern.matcher("������<MSG>��������	s</MSG>������");
		while (matcher.find()) {
			String textarea = matcher.group(1);
			System.out.println(textarea);
		}
		
		phhanzi("fewf2313()&*�ɷ��ҷ�2132���fewf��");
	}
	
	
	public static void  phhanzi(String value){
		
		String regExp = "[\u4E00-\u9FA5]";  
		Pattern p = Pattern.compile(regExp); 
		Matcher m = p.matcher(value); 
		while (m.find()) {
			System.out.println(m.group());
			
		}
		System.out.println("fewf2313()&*�ɷ��ҷ�2132���fewf��".replaceAll("[\u4E00-\u9FA5]", ""));
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

	String sqlTemplate;

	/**
	 * ��ģ������ȡ����
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
	 * �á�?���滻ģ���еı���
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

		// ����2:ƥ������е�<IMG...>
		String temp = "<P><IMG border=0 src='http://172.16.17.232:8080/creatorepp/spmanager/eWebEditor/uploadfile/20140121143133876.jpg'>fwefewwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwewfewfwe<IMG border=0 src='http://172.16.17.232:8080/creatorepp/spmanager/eWebEditor/uploadfile/20140121152222534.jpg'>fwfewfew<IMG border=0 src='http://172.16.17.232:8080/creatorepp/spmanager/eWebEditor/uploadfile/20140121152232577.jpg'></P>";
		String regEx = ".*http://.*.jpg.*";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(temp);
		// System.out.println(m.group());

		// ������Java��ͷ,�����β���ַ���
		Pattern pattern = Pattern.compile("^Java.*");
		Matcher matcher = pattern.matcher("Java������");
		boolean b = matcher.matches();
		// ����������ʱ��������tr�����򷵻�false
		System.out.println("=="+b);

		String[] arrString = temp.split("IMG");
		System.out.println(arrString.length);
		if (arrString.length > 1) {
			for (int i = 0; i < arrString.length; i++) {
				System.out.println(arrString[i]);

			}
		}

		// ��ȡ http����https ... .jpg
		String s = "��������������  https://192.168.0.102/relativeEventDetail.do?mode=view&id=895\nhttps://192.168.0.102/relativeEventDetail.do?mode=view&id=844";
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
	 * @Description: ���˵�˫����
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
	 * @Description: ��֤��λһ����
	 * @return void
	 * @throws
	 * @author XiMing.Fu
	 */
	@Test
	public void vailidation() {

		/*
		 * [0-9]{1,3}\\��1-3λ����,��ͷ�� ([0-9]{3}\\,)*��3λ����,�м�
		 * ����0�λ��Σ�[0-9]{3}��3λ��Ч���֣�((\\.)[0-9]+)?��.+1λ���� ����1�λ��߶��
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
	 * @Description: �ж��Ƿ��� 1��4 �ɶ��ŷָ����ַ���
	 * @return void
	 * @throws
	 * @author XiMing.Fu
	 */
	@Test
	public void vailidation1() {

		/*
		 * ^([1-4]+[,]))*��1-4֮�������+, ����0�λ��� ����ͷ�� ([1-4]+)$��1-4֮������� ����1�λ��߶�Σ���β
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
	 * ��
	 * <?xml version="1.0" encoding="gbk"?>
<Context crossContext="true" path="/creatorepp" docBase="E:\workspace\hyxfs\creatorepp" reloadable="false">
	<ResourceLink name="reportsource" global="reportsource" type="javax.sql.DataSource" />
</Context>
	 * ��ȡ"E:\workspace\hyxfs\creatorepp"
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
