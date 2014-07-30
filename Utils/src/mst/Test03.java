package mst;

import java.util.ArrayList;
import java.util.List;

public class Test03 {

	
	/**
	 * ������13��
		��Ŀ��һ��������������100����һ����ȫƽ�������ټ���168����һ����ȫƽ���������ʸ����Ƕ��٣�
		1.�����������10�������жϣ��Ƚ���������100���ٿ������ٽ���������268���ٿ��������������Ľ�������������������ǽ����
	 */
	public List findNumber(){
		List number = new ArrayList();
		for(int i = 1;i<=10000;i++){
			if(Math.sqrt(i+100)%1==0){
				if((Math.sqrt(i+268))%1==0){
					number.add(i);
				}
			}
		}
		return number;
	}
	/**
	 * ������14��
		��Ŀ������ĳ��ĳ��ĳ�գ��ж���һ������һ��ĵڼ��죿
		1.�����������3��5��Ϊ����Ӧ���Ȱ�ǰ�����µļ�������Ȼ���ټ���5�켴����ĵڼ��죬
		��������������������·ݴ���3ʱ�迼�Ƕ��һ�졣
	 */
	public int getDays(int year,int month,int day){
		int days = 0;
		switch (month) {
		case 1:
			days = day;
			break;
		case 2:
			days = 31+day;
			break;
		case 3:
			 if((year%4==0&&year%100!=0)||year%400==0){
				 days = day + 29 + 31;
			 }
			 days = day + 28 + 31;
			break;
		case 4:
			 if((year%4==0&&year%100!=0)||year%400==0){
				 days = day + 29 + 31 + 31;
			 }
			 days = day + 28 + 31*2;
			break;
		case 5:
			 if((year%4==0&&year%100!=0)||year%400==0){
				 days = day + 29 + 31*2 + 30;
			 }
			 days = day + 28 + 31*2 +30;
			break;
		case 6:
			 if((year%4==0&&year%100!=0)||year%400==0){
				 days = day + 29 + 31*3 +30;
			 }
			 days = day + 28 + 31*3 + 30;
			break;
		case 7:
			 if((year%4==0&&year%100!=0)||year%400==0){
				 days = day + 29 + 31*3 + 30*2;
			 }
			 days = day + 28 + 31*3 + 30*2;
			break;
		case 8:
			 if((year%4==0&&year%100!=0)||year%400==0){
				 days = day + 31*4 + 30*2;
			 }
			 days = day + 28 + 31*4 + 30*2;
			break;
		case 9:
			 if((year%4==0&&year%100!=0)||year%400==0){
				 days = day + 29 + 31*5 + 30*2;
			 }
			 days = day + 28 + 31*5 + 30*2;
			break;
		case 10:
			 if((year%4==0&&year%100!=0)||year%400==0){
				 days = day + 29 + 31*5 + 30*3;
			 }
			 days = day + 28 + 31*5 + 30*3;
			break;
		case 11:
			 if((year%4==0&&year%100!=0)||year%400==0){
				 days = day + 29 + 31*6 + 30*2;
			 }
			 days = day + 28 + 31*6 + 30*2;
			break;
		case 12:
			 if((year%4==0&&year%100!=0)||year%400==0){
				 days = day + 29 + 31*6 + 30*3;
			 }
			 days = day + 28 + 31*6 + 30*3;
			break;

		default:
			break;
		}
		return days;
	}
	
	/**
	 * ������15��
	��Ŀ��������������x,y,z���������������С���������
	1.���������������취����С�����ŵ�x�ϣ��Ƚ�x��y���бȽϣ�
	���x>y��x��y��ֵ���н�����Ȼ������x��z���бȽϣ����x>z��x��z��ֵ���н�����������ʹx��С��
	 */
	public static void paixu(int number1,int number2,int number3){
		int min = number1;
		int mid = 0;
		int max = 1;
		if(number2<min){
			
			if(number3<min){
				min = number3;
				max = number2;
				mid = number1;
				if(max < number1){
					max = number1;
					mid = number2;
				}
			}else{
				min = number2;
				max = number1;
				mid = number3;
				if(max < number3){
					max = number3;
					mid = number1;
				}
			}
		}else if(number3<min){
			min = number3;
			max = number2;
			mid = number1;
			if(max < number1){
				max = number1;
				mid = number2;
			}
		}
		
	}
	/**
	 * ������16��
		��Ŀ�����9*9�ھ���
		1.����������������п��ǣ���9��9�У�i�����У�j�����С�
	 */
	public static void nine(){
		for(int i=1;i<=9;i++){
			for(int j=1;j<=i;j++){
				System.out.print(i+"*"+j+"="+i*j+"\t\t");
			}
			System.out.println();
		}
	}
	
	/**
	 * ������17��
		��Ŀ�����ӳ������⣺���ӵ�һ��ժ�����ɸ����ӣ�
		��������һ�룬����񫣬�ֶ����һ�� �ڶ��������ֽ�ʣ�µ����ӳԵ�һ�룬�ֶ����һ����
		�Ժ�ÿ�����϶�����ǰһ��ʣ�� ��һ����һ��������10���������ٳ�ʱ����ֻʣ��һ�������ˡ����һ�칲ժ�˶��١�
		1.�����������ȡ����˼ά�ķ������Ӻ���ǰ�ƶϡ�
	 */
	public int monkeyEatPeach(){
		int sum = 1;
		for(int i=1;i<=10;i++){
			sum = (sum+1)*2;
		}
		return sum;
	}
	/**
	 * ������18��
		��Ŀ������ƹ����ӽ��б������������ˡ��׶�Ϊa,b,c���ˣ��Ҷ�Ϊx,y,z���ˡ�
		�ѳ�ǩ���������������������Ա����������������a˵������x�ȣ�c˵������x,z�ȣ�
		�������ҳ��������ֵ������� 
		1.����������ж������ķ�������һ�����ֱ�ȥ��2��sqrt(�����)������ܱ������� ���������������������֮��������
	 */
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Test03 t3 = new Test03();
		System.out.println(t3.monkeyEatPeach());
//		nine();
	}

}
