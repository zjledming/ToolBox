package mst;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Test02 {

	/***
	 * ������7�� ��Ŀ������һ���ַ����ֱ�ͳ�Ƴ�����Ӣ����ĸ���ո����ֺ������ַ��ĸ�����
	 * 1.�������������while���,����Ϊ������ַ���Ϊ'\n'.
	 */
	public Map fenlei() {
		Map res = new HashMap();
		char charVar = 0;// ��������������ַ�
		int yingwen = 0;
		int kongge = 0;
		int shuzi = 0;
		int qita = 0;
		Scanner sca = new Scanner(System.in);
		System.out.println("�����������ַ�");
		charVar = sca.next().charAt(0);
		while (charVar != '\n') {
			if (charVar == ' ') {
				kongge++;
			} else if ((charVar >= 65 && charVar <= 90)
					|| (charVar >= 97 && charVar <= 122)) {
				yingwen++;
			} else if (charVar >= 48 && charVar <= 57) {
				shuzi++;
			} else {
				qita++;
			}
		}
		// ��map����� key��ʾΪ�������ƣ�value��ʾΪֵ
		res.put("kongge", kongge);
		res.put("yingwen", yingwen);
		res.put("shuzi", shuzi);
		res.put("qita", qita);
		return res;
	}

	/**
	 * @param number��ʾһ������
	 * @param weishu��ʾ���������
	 *            ������8�� ��Ŀ����s=a+aa+aaa+aaaa+aa...a��ֵ��
	 *            ����a��һ�����֡�����2+22+222+2222+22222(��ʱ����5�������)�� ����������м��̿��ơ�
	 *            1.����������ؼ��Ǽ����ÿһ���ֵ��
	 */
	public long getSum(int number, int weishu) {
		long sum = 0;
		for (int i = 1; i <= weishu; i++) {
			int temp = 0;// ��ʼ��
			for (int j = 1; j <= i; j++) {
				temp += Math.pow(10, j - 1) * number;// �ڴ�ѭ����������ÿһ���ֵ
			}
			sum += temp;

		}

		return sum;
	}

	/**
	 * @return wanshu��һ�����ϣ�����װ��һǧ���ڴ���������� ������9�� ��Ŀ��һ�������ǡ�õ�����������֮�ͣ�������ͳ�Ϊ"����"��
	 *         ����6=1��2��3.��� �ҳ�1000���ڵ�����������
	 */
	public List wanshu() {
		List wanshu = new ArrayList();
		int yushu = 0;
		for (int i = 1; i <= 1000; i++) {
			int sum = 1;
			for (int j = 2; j < i / 2 + 1; j++) {
				if (i % j == 0) {
					yushu = i / j;
					sum = sum + j;
				}
			}
			if (sum == i) {
				wanshu.add(i);
			}
		}
		return wanshu;
	}

	/**
	 * ������10�� ��Ŀ��һ���100�׸߶��������£�ÿ����غ�����ԭ�߶ȵ�һ�룻 �����£������� ��10�����ʱ�������������ף���10�η�����ߣ�
	 */
	public float[] testBall() {// ����Ҫ��������float���͵��������Է���һ��float���͵�����
		float[] balls = new float[2];
		float sumLenth = 0;
		float high = 100;
		for (int i = 1; i <= 10; i++) {
			sumLenth += high;
			high = high / 2;
		}
		balls[0] = sumLenth;
		balls[1] = high;
		return balls;
	}

	/**
	 * ������11�� ��Ŀ����1��2��3��4�����֣�����ɶ��ٸ�������ͬ�����ظ����ֵ���λ�������Ƕ��٣�
	 * 1.��������������ڰ�λ��ʮλ����λ�����ֶ���1��2��3��4��������е����к���ȥ �����������������С�
	 */
	public List testTN() {
		List nums = new ArrayList();
		int n = 0;
		for (int i = 1; i <= 4; i++) {
			for (int j = 1; j <= 4; j++) {
				for (int k = 1; k <= 4; k++) {
					if (i != j && j != k && i != k ) {
						n++;
						System.out.println("NO." + n + ":" + i + "" + j + ""
								+ k);
					}
				}
			}
		}
		System.out.println("����:" + n + "��");
		return nums;
	}
	
	/**
	 * ������12��������12��
��Ŀ����ҵ���ŵĽ������������ɡ�����(I)���ڻ����10��Ԫʱ���������10%��
�������10��Ԫ������20��Ԫʱ������10��Ԫ�Ĳ��ְ�10%��ɣ�����10��Ԫ�Ĳ��֣�
�ɿ����7.5%��20��40��֮��ʱ������20��Ԫ�Ĳ��֣������5%��40��60��֮��ʱ����40��Ԫ�Ĳ��֣�
�����1.5%������100��Ԫʱ������100��Ԫ�Ĳ��ְ�1%��ɣ��Ӽ������뵱������I����Ӧ���Ž���������
1.����������������������ֽ磬��λ��ע�ⶨ��ʱ��ѽ�����ɳ����͡�
	 */
	public static long moneyAward(long lirun){{
		long jiangjin = 0;
		if(lirun <= 100000){
			jiangjin = (long) (lirun*0.1);
		}else if(lirun <= 20){
			jiangjin = (long) (100000*0.1+(lirun-10)*0.075);
		}else if(lirun <= 40 ){
			jiangjin = (long) (100000*0.1+100000*0.075+(lirun-400000)*0.05);
		}else if(lirun <= 60){
			jiangjin = (long) (100000*0.1+100000*0.075+200000*0.05+(lirun-400000)*0.015);
		}else if(lirun > 1000000){
			jiangjin = (long) (100000*0.1+100000*0.075+200000*0.05+600000*0.015+(lirun-1000000)*0.01);
		}
		return jiangjin;
	}
		
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Test02 t = new Test02();
		float[] balls = t.testBall();
		System.out.println(balls[0] + " " + balls[1]);
	}
}
