package mst;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;


public class Test08 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		getGrade();
	}

	// ������46��
	// ��Ŀ�������ַ������ӳ���

	public static String getString(String str1, String str2) {
		return str1 + str2;
	}

	// ������47��
	// ��Ŀ����ȡ7������1��50��������ֵ��ÿ��ȡһ��ֵ�������ӡ����ֵ�����ģ���
	
	public static void printxing(){
		Scanner sca = new Scanner(System.in);
		int time = 1;
		int number = 0;
		while(time <= 7){
			System.out.println("������һ��1-50������");
			number = sca.nextInt();
			if(number>50||number<1){
				System.out.println("���벻�Ϸ�������������");
				continue;
			}
			for(int i=1;i<=number;i++){
				System.out.print("*");
			}
			time++;
			System.out.println();
		}
	}

	// ������48��
//	 ��Ŀ��ĳ����˾���ù��õ绰�������ݣ���������λ��������
//	 �ڴ��ݹ������Ǽ��ܵģ����ܹ������£�ÿλ���ֶ�����5,
//	 Ȼ���úͳ���10��������������֣��ٽ���һλ�͵���λ�������ڶ�λ�͵���λ������

	public static int getMiMa(int number){
		int num = 0;
		int qianwei = number/1000;
		int baiwei = (number-qianwei*1000)/100;
		int shiwei = (number-qianwei*1000-baiwei*100)/10;
		int gewei = number%10;
		int [] weishu = {gewei,shiwei,baiwei,qianwei};
		for (int i = 0; i < weishu.length; i++) {
			weishu[i] += 5;
			weishu[i] %= 10;
		}
		int temp = weishu[0];
		weishu[0] = weishu[3];
		weishu[3] = temp;
		
		temp = weishu[1];
		weishu[1] = weishu[2];
		weishu[2] = temp;
		
		String numValue = String.valueOf(weishu);
		num = Integer.parseInt(numValue);
		return num;
	}
	// ������49��
	// ��Ŀ�������ַ������Ӵ����ֵĴ���
	/**
	 * @param str �ϳ����ַ��� zichuan �Ӵ�
	 */
	public static void getZiChuang(String str, String zichuan) {
		int count = 0;
		int startIndex = 0;
		int index = -1;
		while ((str.indexOf(zichuan) >= 0)) {
			count++;
			startIndex += zichuan.length();
		}
		System.out.println(count);
	}

	// ������50��
	// ��Ŀ�������ѧ����ÿ��ѧ����3�ſεĳɼ���
	// �Ӽ��������������ݣ�����ѧ���ţ����������ſγɼ�����
	// �����ƽ���ɼ�����ԭ�е����ݺͼ������ƽ����������ڴ����ļ�"stud"�С�
	public static void getGrade() {
		File flle = new File("D:stud.txt");
		if (!flle.exists()) {
			try {
				File.createTempFile("stud", "txt");
			} catch (IOException e) {
				e.printStackTrace();
			} finally {

			}
		}
	}

}
