
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
		
		String temp = "���л�����ʩ���׷ѣ��س��̳���";
		String re = temp.split("��")[0];
		System.out.println(re);
		 re = "���л�����ʩ���׷�(�س��̳���".split("\\(")[0];
		System.out.println(re);
		
		int i=  "���л�����ʩ���׷�(�س��̳���".indexOf("(");
		System.out.println(i);
		
		
	}

}
