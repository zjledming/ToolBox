
public class SpiltUtil {
	public static void main(String[] args) {
		String tem = "1014,1013";
		String[] arr = tem.split(",");
		System.out.println(arr.length);
		tem = "1014, ";
		arr = tem.split(",");
		System.out.println(arr.length);
		tem = "1014";
		arr = tem.split(",");
		System.out.println(arr.length);
		
		
		tem ="E:\\workspace\\CodeCreator\\src\\cn\\cc\\service\\user\\impl";
		tem ="E:\\workspace\\CodeCreator\\src";
		arr = tem.split("src\\\\");
		System.out.println(arr.length);
		
		String temp = "城市基础设施配套费（县城商场）";
		String re = temp.split("（")[0];
		System.out.println(re);
		 re = "城市基础设施配套费(县城商场）".split("\\(")[0];
		System.out.println(re);
		
		int i=  "城市基础设施配套费(县城商场）".indexOf("(");
		System.out.println(i);
		
		
	}

}
