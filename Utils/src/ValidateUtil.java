

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidateUtil {
	/**
	 * �ж��Ƿ�Ϊ����
	 * @param str
	 * @return
	 * @throws Exception
	 */
	public static boolean is_China(String str)throws Exception{
		int n=0;
		for(int i=0; i<str.length(); i++) {
			n = (int)str.charAt(i);
			if(!(19968 <= n && n <40623)) {
				return false;
			}
		}
		return true;
	}
	/**
	 * �ж��Ƿ�Ϊ��Ч���Ա�
	 * @param str
	 * @return
	 * @throws Exception
	 */
	public static boolean is_Sex(String str) throws Exception{
		if ("��".equals(str) || "Ů".equals(str)) {
			return true;
		}
		return false;
	}
	/**
	 * �ж��Ƿ�Ϊ��Ч���ֻ�����
	 * @param str
	 * @return
	 * @throws Exception
	 */
	public static boolean is_Mobile(String str) throws Exception{ 
		Pattern pattern = Pattern.compile("^(13[0-9]|15[0-9]|18[0-9])\\d{8}$");
		
		if(str.length() > 11){
			int index = str.indexOf(".");
			if(11==index){
				str = str.substring(0, index);
			}
		}
		Matcher matcher = pattern.matcher(str);
		if (matcher.matches()) {
			return true;
		}
		return false;
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
	 * �ж��Ƿ�Ϊ��Ч������
	 * @param str
	 * @return
	 * @throws Exception
	 */
	public static boolean is_Email(String str) throws Exception{
		Pattern pattern = Pattern.compile("^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\\.[a-zA-Z0-9_-]{2,3}){1,2})$");
		Pattern pattern_ = Pattern.compile("^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$");
		Matcher matcher = pattern_.matcher(str);
		if (matcher.matches()){
			return true;
		}
		return false;
    }
	/**
	 * �ж��Ƿ�Ϊ��Ч�ĵ绰����
	 * @param str
	 * @return
	 * @throws Exception
	 */
	public static boolean is_Phone(String str) throws Exception{ 
		Pattern pattern = Pattern.compile("(^0\\d{2,3}\\-\\d{7,8}$)|(^\\d{7,8}$)");
		
		Matcher matcher = pattern.matcher(str);
		if (matcher.matches()) {
			return true;
		}
		return false;
    }
	/**
	 * ������
	 * @param str
	 * @return
	 * @throws Exception
	 */
	public static boolean is_Required(String str) throws Exception{
		str = StringUtil.deNull(str);
		if (str.length() > 0) {
			return true;
		}
		return false;
	}
	/**
	 * ֤������У��
	 * @param string
	 * @return
	 * @throws Exception
	 */
	public static boolean is_CertType(String str) throws Exception{
		str = StringUtil.deNull(str);
		if(str.length() > 1){
			int index = str.indexOf(".");
			if(1==index){
				str = str.substring(0, index);
			}
		}
		if("0".equals(str) || "1".equals(str) || "2".equals(str)){
			return true;
		}
		return false;
	}
	/**
	 * �жϵ�ַ�����������»�����ĸ��ͷ
	 * @param str
	 * @return
	 * @throws Exception
	 */
	public static boolean is_Address(String str) throws Exception{
//		Pattern pattern = Pattern.compile("\\w+((-\\w+)|(\\.\\w+))*\\@[A-Za-z0-9]+((\\.|-)[A-Za-z0-9]+)*\\.[A-Za-z0-9]+$");
		Pattern pattern = Pattern.compile("^(\\w+)|(_)(.*)");
		Matcher matcher = pattern.matcher(str);
		if (!matcher.matches()) {
			return true;
		}
		return false;
	}
	
	/**
	 * �ж��Ƿ�Ϊ�����ַ���
	 * @param str
	 * @return
	 */
	public static boolean isChinese(String str) {
	   char[] nameChar = str.toCharArray();
	   for(int i=0; i<nameChar.length; i++){
		   Character.UnicodeBlock ub = Character.UnicodeBlock.of(nameChar[i]); 
		   if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
			   continue; 
		   }else{
			   return false;
		   }
	   }
	   return true; 
    } 
	
	
	public static void main(String[] args) {
		try {
			System.out.println(is_Email("shuanglong.gong@126.com"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	
}
