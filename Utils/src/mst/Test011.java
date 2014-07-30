package mst;

import java.util.ArrayList;
import java.util.List;

public class Test011 {

	
	public static void main(String[] args) {
		Test011 test = new Test011();
//		int result = test.getRabitSum(5);
//		System.out.println(result);
//		List l = test.getSuShu();
//		System.out.println(l.size());
//		getAllFlowerNumber();
//		System.out.println(test.fenjieNumber(25));
		System.out.println(test.getGrade(25));
	}
	/**
	 * @������
	 * @return month���µ�����������
	 * @param  ��Ҫ�������
	 * 
	 * ��Ŀ1���ŵ����⣺��һ�����ӣ�
	 * �ӳ������3������ÿ���¶���һ�����ӣ�
	 * С���ӳ����������º�ÿ��������һ�����ӣ�
	 * �������Ӷ���������ÿ���µ���������Ϊ���٣�
	 * 1.��������� ���ӵĹ���Ϊ����1,1,2,3,5,8,13,21....
	 */
	public int getRabitSum(int month){
		int lastRabit = 1; //���ó�ʼ��ǰ�µ�������
		int currRabit = 1;//���ó�ʼ�ĵ�����������
		int sumRabits = 2;//���ó�ʼ����������
		int temp = 0;
		//���·�С��1ʱ�����Ϸ���
		if(month<=0){
			return 0;
		}
		//���·�Ϊ1ʱֻ��һ������
		else if(month<=1){
			return 1;
		}
		//���·�Ϊ2ʱ
		else if(month<=2){
			return sumRabits;
		}
		//���·ݴ��ڵ���3ʱ
		//1.��һ��ѭ�������õ��µ�������ĿΪǰ�������µ�������Ŀֻ��
		//2.����������ΪsumRabits���ϵ��µ�������Ŀ
		for(int i=3;i<=month;i++){
			temp = currRabit;
			currRabit += lastRabit;
			lastRabit = temp;
			sumRabits +=  currRabit;
		}
		return sumRabits;
	}

	/**
	 * @author ������
	 * @return װ��101��200���������ļ���
	 * @serialData 2012��03��06��
	 * ��Ŀ02���ж�101-200֮���ж��ٸ����������������������
	 * 1.����������ж������ķ�������һ�����ֱ�ȥ��2��sqrt(�����)������ܱ�������
	 * ���������������������֮�������� 
	 */
	public List getSuShu(){
		List sushu = new ArrayList();
		for(int i = 101;i<=200;i++){
			boolean flag = true;//����һ���ж��ǲ��������ı��
			for(int j = 2; j<=Math.sqrt(i);j++){
				if(i%j == 0){
					flag = false;
					break; //����ܹ�����������֤���������������������˴�ѭ��
				}
			}
			if(flag){
				sushu.add(i);//�������������ӵ�sushe������ȥ
			}
		}
		return sushu;
	}
	
	/**
	 * @author ������
	 * @return װ��100��999����ˮ�ɻ���-�ļ���
	 * @serialData 2012��03��06��
	 * ��Ŀ����ӡ�����е�"ˮ�ɻ���"��
	 * ��ν"ˮ�ɻ���"��ָһ����λ�������λ���������͵��ڸ�������
	 * ���磺153��һ��"ˮ�ɻ���"����Ϊ153=1�����η���5�����η���3�����η���
	 * 1.�������������forѭ������100-999������ÿ�����ֽ����λ��ʮλ����λ��
	 */
	public  List getAllFlowerNumber(){
		List flowerNumbers = new ArrayList();
		int gewei = 0;
		int shiwei = 0;
		int baiwei = 0;
		for(int i = 100;i<=999;i++){
			baiwei = i/100;
			shiwei = (i-100*baiwei)/10;
			gewei = i%10;
			if((i==baiwei*baiwei*baiwei+shiwei*shiwei*shiwei+gewei*gewei*gewei)){
				flowerNumbers.add(i);
			}
		}
		return flowerNumbers;
	}
	/**
	 * @author ������
	 * @return ����number��ʽ�ֽ��һ��String
	 * @serialData 2012��03��06��
	 * ������4��
		��Ŀ����һ���������ֽ������������磺����90,��ӡ��90=2*3*3*5��
		�����������n���зֽ���������Ӧ���ҵ�һ����С������k��Ȼ������������ɣ� 
		(1)����������ǡ����n����˵���ֽ��������Ĺ����Ѿ���������ӡ�����ɡ�
		(2)���n<>k����n�ܱ�k��������Ӧ��ӡ��k��ֵ������n����k����,��Ϊ�µ���������n,�ظ�ִ�е�һ����
		(3)���n���ܱ�k����������k+1��Ϊk��ֵ,�ظ�ִ�е�һ����
	 */
	public String fenjieNumber(int number){
		String result = String.valueOf(number) + "=";
		boolean flag = true;
		int chushu = 2;//����һ���Ӷ���ʼ�ĳ���
		int yushu = 0;//��ʼ������Ϊ0
		while(flag){
			yushu = number%chushu;
			if(yushu == 0){
				result += (chushu+"*");
				if(chushu == number){
					flag = false;
				}else{
					number = number/chushu;
				}
			}else{
				chushu += 1;
			}
		}
		result = result.substring(0, result.length()-1);//ȥ��ĩβ���Ǹ�*
		return result;
	}
	
	/**
	 * @author ������
	 * @return ����fenshu�ĳɼ��ȼ�
	 * @serialData 2012��03��06��
	 * ������5��
		��Ŀ�����������������Ƕ������ɴ��⣺ѧϰ�ɼ�>=90�ֵ�ͬѧ��A��ʾ��
		60-89��֮�����B��ʾ��60�����µ���C��ʾ��
		1.���������(a>b)?a:b��������������Ļ������ӡ�
	 */
	public String getGrade(int fenshu){
		String result = "";
		result = ((fenshu >= 90) ? "A" : ((fenshu>=60) ? "B" : "C"));
		return result;
	}
	/**
	 * ������6��
		��Ŀ����������������m��n���������Լ������С��������
		1.�������������շ������
		
	 */
	public int[] getGongyueshuAndGongyueshu(int number1,int number2){
		int [] res = new int [2];
		
		return res;
	}
}
