package mst;

/**
 * 
 * @author ������
 * @since 20113��3��7��
 * 
 */
public class Test04 {


	/**
	 * @author ������ ��Ŀ����ӡ������ͼ�������Σ���һ�����η�Ϊ������������ Ϊ��������ѭ�����ƿո��* ������һ������������ * ***
	 *         ***** ******* ***** *** *
	 */
	public static void lingxing(int hangshu) {
		for (int i = 1; i <= hangshu; i++) {// ��������
			if (i <= (hangshu / 2) + 1) {
				for (int j = 1; j <= (hangshu / 2 + 1) - i; j++) {
					System.out.print(" ");
				}
				for (int k = 1; k <= 2 * i - 1; k++) {

					System.out.print("*");
				}
			} else {
				for (int m = 1; m <= i - hangshu / 2 - 1; m++) {
					System.out.print(" ");
				}
				for (int n = 1; n <= 2 * (hangshu - i) + 1; n++) {
					System.out.print("*");
				}
			}

			System.out.println();
		}
	}
	/**
	 * @author ������
	 * @since 20113��3��7��
	 * ������20�� 
		��Ŀ����һ�������У�2/1��3/2��5/3��8/5��13/8��21/13...���������е�ǰ20��֮�͡�
		1.�����������ץס�������ĸ�ı仯���ɡ� 
	 */
	 
	public static float getSum(){
		float sum = 0;//��ʼ����Ϊ0
		int fenzi = 2;//��ʼ������Ϊ2
		int fenmu = 1;//��ʼ����ĸΪ1
		int temp = 0;
		for(int i=1;i<=20;i++){
			sum += fenzi/fenmu;
			temp = fenzi;
			fenzi += fenmu;//�ڶ������ķ���Ϊǰһ�����ķ��Ӻͷ�ĸ֮��
			fenmu = temp;//�ڶ������ķ�ĸΪǰ��һ�����ֵķ���
		}
		return sum;
	}
	
	
	/**
	 * ������21�� 
		��Ŀ����1+2!+3!+...+20!�ĺ�
		1.����������˳���ֻ�ǰ��ۼӱ�����۳ˡ� 
	 */
	
	public static int getSumBy20(){
		int sum = 0;//��ʼ��sumΪ0
		for(int i=1;i<=20;i++){//���ѭ��������Ҫ��ӵ����ĸ���
			int number = 1;
			for(int j = 1;j<=i;j++){//�ڲ�ѭ��������ÿһ�����Ľ׳�
				number *= j;
			}
			sum += number;
		}
		return sum;
	}
	
	/**
	 * @param number ��ʾ��Ҫ���n��
	 * ������22�� 
		��Ŀ�����õݹ鷽����5!��
		1.����������ݹ鹫ʽ��fn=fn_1*4!
	 */
	
	public static int digui(int number){
		int result = 0;
		if(number==1){
			result =  1;
		}else if(number==2){
			result =  2;
		}else{
			result =  digui(number-1)*number;//�ݹ��������
		}
		return result;
	}
	
	/**
	 * @param perpleNumber ��ʾ��N���� 
	 * ������23�� 
		��Ŀ����5��������һ���ʵ�����˶����ꣿ��˵�ȵ�4���˴�2�ꡣ
		�ʵ�4������������˵�ȵ�3���˴�2�ꡣ�ʵ������ˣ���˵�ȵ�2�˴����ꡣ
		�ʵ�2���ˣ�˵�ȵ�һ���˴����ꡣ����ʵ�һ���ˣ���˵��10�ꡣ���ʵ�����˶��
		1.������������õݹ�ķ������ݹ��Ϊ���ƺ͵��������׶Ρ�Ҫ��֪���������������
		��֪�������˵��������������ƣ��Ƶ���һ�ˣ�10�꣩���������ơ�
	 */
	public static int getYear(int perpleNumber){
		int year = 10;//��ʼ����һ���˵�����Ϊ10��
		year = 10+2*(perpleNumber-1);
		return year;
	}
	/**
 	 *  ������24�� 
		��Ŀ����һ��������5λ����������Ҫ��һ�������Ǽ�λ�������������ӡ����λ���֡�
	 */
	public static int getWeishu(int number){
		String strNum = String.valueOf(number);//����������ֻ��Ҫ��numberת��ΪString������������ͺܼ���
		int length = strNum.length();
		StringBuffer strNumValue = new StringBuffer(strNum);
		strNumValue = strNumValue.reverse();//��Ϊ��StringBuffer����һ�����õ�reverse���������Ƚ�StringתΪStringBuffer
		System.out.println(strNumValue);
		return length;
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		lingxing(5);
//		System.out.println(getSum());
//		System.out.println(getSumBy20());
		System.out.println(getWeishu(12345));
	}

}
