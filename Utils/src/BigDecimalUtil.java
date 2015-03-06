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

		// 四舍五入，保留2位小数
		System.out.println("--"+shouldMoney.setScale(2, BigDecimal.ROUND_HALF_UP));//140.68
		
		// 加
		System.out.println(new BigDecimal(140.6756).setScale(4, BigDecimal.ROUND_HALF_UP));//141.79
		// 减
		System.out.println(shouldMoney.subtract(preferentiaMoney).setScale(2, BigDecimal.ROUND_HALF_UP));//139.56
		System.out.println(shouldMoney.subtract(preferentiaMoney).setScale(0, BigDecimal.ROUND_HALF_UP));//140
		// 剩
		System.out.println(shouldMoney.multiply(preferentiaMoney).setScale(2, BigDecimal.ROUND_HALF_UP));//157.40
		// 除
		System.out.println(shouldMoney.divide(preferentiaMoney,2,BigDecimal.ROUND_HALF_UP));//125.73
		
		
		System.out.println(new BigDecimal(140).setScale(4, BigDecimal.ROUND_HALF_UP));//141.79
		
	}
	
	/**
	 * 对Double数据类型数据四舍五入并保留一位小数
	 * @param data Double的数据
	 * @return String
	 */
	public static String formatNum(Double data){	
	
		//BigDecimal b = new 	BigDecimal(data*10);
		//double f = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();//对Double数据类型数据四舍五入并保留两位小数
		//return f/10 + "";
		String f = Math.round(data*10)/10.0+"";
		return f.replace(".0", "");
	}
	
	/**
	 * 科学计数法 装正常显示
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
		System.out.println(subZeroAndDot("1"));;  // 转换后为1
		System.out.println(subZeroAndDot("10"));;  // 转换后为10
		System.out.println(subZeroAndDot("1.0"));;  // 转换后为1
		System.out.println(subZeroAndDot("1.010"));;  // 转换后为1.01 
		System.out.println(subZeroAndDot("1.01"));;  // 转换后为1.01
	}
	
	/**
	 * 使用java正则表达式去掉多余的.与0
	 * @param s
	 * @return 
	 */
	public static String subZeroAndDot(String s){
		if(s.indexOf(".") > 0){
			s = s.replaceAll("0+?$", "");//去掉多余的0
			s = s.replaceAll("[.]$", "");//如最后一位是.则去掉
		}
		return s;
	}

}
