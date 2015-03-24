import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Formatter;
import java.util.UUID;

import org.junit.Test;


public class BigDecimalUtil {
	
	public static void main1(String[] args) {
		DecimalFormat format = new DecimalFormat("#######,###.##");
		DecimalFormat format1 = new DecimalFormat("00000,000.000");
		String result = format.format(new BigDecimal("1234568.1"));
		System.out.println(result); //1,234,568.1
		result = format1.format(new BigDecimal("1234568.1"));
		System.out.println(result);//01,234,568.100
		
		BigDecimal shouldMoney = new BigDecimal(140.6756);
		BigDecimal preferentiaMoney = new BigDecimal(1.1189);

		// �������룬����2λС��
		System.out.println("--"+shouldMoney.setScale(2, BigDecimal.ROUND_HALF_UP));//140.68
		
		// ��
		System.out.println(new BigDecimal(140.6756).setScale(4, BigDecimal.ROUND_HALF_UP));//141.79
		// ��
		System.out.println(shouldMoney.subtract(preferentiaMoney).setScale(2, BigDecimal.ROUND_HALF_UP));//139.56
		System.out.println(shouldMoney.subtract(preferentiaMoney).setScale(0, BigDecimal.ROUND_HALF_UP));//140
		// ʣ
		System.out.println(shouldMoney.multiply(preferentiaMoney).setScale(2, BigDecimal.ROUND_HALF_UP));//157.40
		// ��
		System.out.println(shouldMoney.divide(preferentiaMoney,2,BigDecimal.ROUND_HALF_UP));//125.73
		
		
		System.out.println(new BigDecimal(140).setScale(4, BigDecimal.ROUND_HALF_UP));//141.79
		
	}
	
	/**
	 * ��Double�������������������벢����һλС��
	 * @param data Double������
	 * @return String
	 */
	public static String formatNum(Double data){	
	
		//BigDecimal b = new 	BigDecimal(data*10);
		//double f = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();//��Double�������������������벢������λС��
		//return f/10 + "";
		String f = Math.round(data*10)/10.0+"";
		return f.replace(".0", "");
	}
	
	/**
	 * ��ѧ������ װ������ʾ
	 */
	@Test
	public void kxjsf(){
		String sjiachun = "12345E-10";
		BigDecimal db = new BigDecimal(sjiachun);
		String ii = db.toPlainString();
		System.out.println(ii);
	}
	
	
	public static void main(String[] args) {
		Float f = 1f;
		System.out.println(f.toString());//1.0
		System.out.println(subZeroAndDot("1"));;  // ת����Ϊ1
		System.out.println(subZeroAndDot("10"));;  // ת����Ϊ10
		System.out.println(subZeroAndDot("1.0"));;  // ת����Ϊ1
		System.out.println(subZeroAndDot("1.010"));;  // ת����Ϊ1.01 
		System.out.println(subZeroAndDot("1.01"));;  // ת����Ϊ1.01
	}
	
	/**
	 * ʹ��java������ʽȥ�������.��0
	 * @param s
	 * @return 
	 */
	public static String subZeroAndDot(String s){
		if(s.indexOf(".") > 0){
			s = s.replaceAll("0+?$", "");//ȥ�������0
			s = s.replaceAll("[.]$", "");//�����һλ��.��ȥ��
		}
		return s;
	}

}
