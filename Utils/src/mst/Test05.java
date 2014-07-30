package mst;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


/**
 * @author ������
 * @since 2013��3��7��
 * @param args
 */
public class Test05 {
	/**
	 * ������25�� 
		��Ŀ��һ��5λ�����ж����ǲ��ǻ���������12321�ǻ���������λ����λ��ͬ��ʮλ��ǧλ��ͬ�� 
	 */
	public static boolean huiwenshu(int number){
		boolean result = false;
		String strNumber = String.valueOf(number);
		StringBuffer numberStr = new StringBuffer(strNumber);
		numberStr.reverse();//����reserse������ת���� �����תǰ�ͺ���ȣ����ǻ���������֮����
		if(strNumber.equals(numberStr.toString())){
			result = true;
		}
		return result;
	}
	
	/**
	 * Monday Tuesday Wednesday  Friday Saturday 
	 * 		  Thursday					Sunday
	 * ������26��
		��Ŀ�����������ڼ��ĵ�һ����ĸ���ж�һ�������ڼ��������һ����ĸһ��������� �жϵڶ�����ĸ��
		1.�����������������ȽϺã������һ����ĸһ�������ж����������if����жϵڶ�����ĸ��
	 * @param args
	 */
	public static void testWeek(){
		Scanner sca = new Scanner(System.in);
		System.out.println("����������һ���ַ�");
		char week = sca.next().toLowerCase().charAt(0);
		switch (week) {
		case 'm':
			System.out.println("����һ");
			break;
		case 'w':
			System.out.println("������");
			break;
		case 'f':
			System.out.println("������");
			break;
		case 't':
			System.out.println("������ڶ����ַ�");
			week = sca.next().toLowerCase().charAt(0);
			if(week == 'u'){
				System.out.println("���ڶ�");
			}else if(week == 'h'){
				System.out.println("������");
			}else{
				System.out.println("���벻��ȷ");
			}
			break;
		case 's':
			System.out.println("������ڶ����ַ�");
			week = sca.next().toLowerCase().charAt(0);
			if(week == 'u'){
				System.out.println("������");
			}else if(week == 'a'){
				System.out.println("������");
			}else{
				System.out.println("���벻��ȷ");
			}
			break;
		default:
			System.out.println("���벻��ȷ");
			break;
		}
	}
	
	/**
	 * @param number��ʾnumber���ڵ�����
	 * ������27��
		��Ŀ����100֮�ڵ����� 
	 */
	public List zhisu(int number){
		List zhisuList =  new ArrayList();
		for(int i=2;i<=number;i++){
			boolean flag = true;//�ȶ���i������
			for(int j=2;j<=number/2+1;j++){
				if(i%j==0){
					//���������������������ѭ��
					flag = false;
					break;
				}
			}
			if(flag == true){
				zhisuList.add(i);
			}
		}
		return zhisuList;
	}
	
	/**
	 * ������28��
		��Ŀ����10������������
		1.�����������������ѡ�񷨣����Ӻ�9���ȽϹ����У�ѡ��һ����С�����һ��Ԫ�ؽ�����
	 	�´����ƣ����õڶ���Ԫ�����8�����бȽϣ������н�����
	 * @param args
	 */
	public int[] sortNumber(int [] numbers){
		int temp = 0;
		for(int i=0;i<numbers.length;i++){
			for(int j=i+1;j<numbers.length;j++){
				if(numbers[i] > numbers[j]){
					temp = numbers[i];
					numbers[i] = numbers[j];
					numbers[j] = temp;
				}
			}
		}
		return numbers;
	}
	/**
	 * ������29��
	 *	��Ŀ����һ��3*3����Խ���Ԫ��֮�� 
	 *	1.�������������˫��forѭ�����������ά���飬�ٽ�a[i][i]�ۼӺ������
	 *  1	2	3
	 *  4	5	6
	 *  7	8	9
	 * @return �Խ��ߵ�Ԫ��֮��
	 */
	public static int getSumByArray(int length){
		int sum =  0;
		int number = 0;
		int [][] numbers = new int [length][length];
		for(int i=0;i<numbers.length;i++){
			for(int j=0;j<numbers[0].length;j++){
				number++;
				numbers[i][j] = number;
				if(i==j||i+j==numbers.length-1){
					System.out.println(i+" "+j);
					sum += numbers[i][j];
				}
			}
		}
		sum += numbers[numbers.length/2][numbers.length/2];//�м��Ǹ�Ԫ��Ҫ������
		return sum;
	}
//	������30��
//	��Ŀ����һ���Ѿ��ź�������顣������һ������Ҫ��ԭ���Ĺ��ɽ������������С�
//	1. ��������������жϴ����Ƿ�������һ������Ȼ���ٿ��ǲ����м�����������������Ԫ��֮����������κ���һ��λ�á�
	/***
	 *@return int[]����һ���Ѿ����������
	 *@param number����Ҫ��������� firsrArray[]��ʾһ���Ѿ�����õ����� 
	 */
	public static int[] chashuju(int number,int firstArray[]){
		int flag = 0;
		for (int i = firstArray.length-1; i >= 0; i--) {
			if(number>=firstArray[i]){
				flag = i;
				break;
			}
		}
		int [] newArray = new int [firstArray.length+1];
		System.out.println(flag+" 11111d");
		for (int i = 0; i < newArray.length; i++) {
			if(i <= flag){
				newArray[i] = firstArray[i];
			}else if(i == flag+1){
				newArray[i] = number;
			}else{
				newArray[i] = firstArray[i-1];
			}
		}
		
		return newArray;
	}
	//	������31��
	//	��Ŀ����һ���������������
	//	1.����������õ�һ�������һ��������
	/**
	 * @param obj[] һ���������͵�����
	 * @return Object[] ����һ�����������
	 */
	public static Object [] nixu(Object []obj){
		Object temp = null;
		for (int i = 0; i < obj.length/2; i++) {
			temp = obj[i];
			obj[i] = obj[obj.length-i-1];
			obj[obj.length-i-1] = temp;
		}
		return obj;
	}
	
//	������32��
//	��Ŀ��ȡһ������a���Ҷ˿�ʼ��4��7λ��
//	��������������������ǣ� 
//	(1)��ʹa����4λ��
//	(2)����һ����4λȫΪ1,����ȫΪ0����������~(~0<<4)
//	(3)��������߽���&���㡣
	
	
//	������33��
//	��Ŀ����ӡ����������Σ�Ҫ���ӡ��10������ͼ�� 
//	1.���������
//	1
//	1 1
//	1 2 1
//	1 3 3 1
//	1 4 6 4 1
//	1 5 10 10 5 1 
	/**
	 * @param hangshu��ʾ��Ҫ��ӡ������
	 * ���ǵ�������ǵĽṹ�����Բ��ö�ά��������ݽṹ��
	 */
	public static void yanghui(int hangshu){
		int number[][] = new int [hangshu][hangshu];
		for(int i = 0;i < number.length; i++){//���ѭ��������������
			for(int j = 0; j <= i;j++){//�ڲ�ѭ����������һ���еĸ���
				if(j==0||i==j){
					number[i][j] = 1;
				}else{
					number[i][j] = number[i-1][j-1]+number[i-1][j];//����ÿһ�еĵ�һ�������һ������1���⣬
																  //������ÿһ�����ݵ�����һ��ǰ����������֮��
				}
				System.out.print(number[i][j]+" ");
			}
			System.out.println();
		}
	}
	

	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		System.out.println(huiwenshu(21323));
//		testWeek();
//		int [] a = {0,1,2,4};
//		a = chashuju(3,a);
//		for (int i = 0; i < a.length; i++) {
//			System.out.println(a[i]);
//		}
		yanghui(10);
	}

}
