package mst;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ������
 * @since 2013��3��8��
 */
public class Test06 {

	// ������34��
	// ��Ŀ������3����a,b,c������С˳�������
	// 1.�������������ָ�뷽����
	public String paixu(int num1, int num2, int num3) {
		int temp = 0;
		int[] shu = new int[] { num1, num2, num3 };
		for (int i = 0; i < 2; i++)
			for (int j = i + 1; j < 3; j++) {
				if (shu[i] < shu[j]) {
					temp = shu[i];
					shu[i] = shu[j];
					shu[j] = shu[i];
				}
			}
		return shu[0] + ">" + shu[1] + ">" + shu[2] ;
	}

	// ������35��
	// ��Ŀ���������飬�������һ��Ԫ�ؽ�������С�������һ��Ԫ�ؽ�����������顣
	/**
	 * @param arrays
	 *            һ�����ε�Ŀ������
	 * @return int[] ����һ����һλΪ��������һλλ��С��������
	 */
	public static int[] shuchuArray(int[] arrays) {
		int max = 0;
		int min = 1;
		int[] newArray = new int[arrays.length];
		for (int i = 0; i < arrays.length; i++) {
			newArray[i] = arrays[i];// ����һ����Ŀ��������ȫ��ͬ������
		}
		Arrays.sort(newArray);// ���������������
		System.out.println(newArray[0] + " " + newArray[5]);
		for (int i = 0; i < arrays.length; i++) {// ����Ŀ������
			if (arrays[i] == 7) {// newArray[arrays.length-1]����±�Ϊi���ݵ������������һ�����±�Ϊi���������±�Ϊ0�����ݽ���
				max = i;
			} else if (arrays[i] == 1) {// newArray[0]����±�Ϊi���ݵ������������һ�����±�Ϊi���������±�Ϊ0�����ݽ���
				min = i;
			}
		}
		int temp = arrays[max];
		arrays[max] = arrays[0];
		arrays[0] = temp;

		temp = arrays[min];
		arrays[min] = arrays[arrays.length - 1];
		arrays[arrays.length - 1] = temp;
		System.out.println();
		for (int i = 0; i < newArray.length; i++) {
			System.out.println(arrays[i] + "  " + newArray[i]);
		}
		return arrays;
	}

	// ������36��
	// ��Ŀ����n��������ʹ��ǰ�����˳�������m��λ�ã����m���������ǰ���m����
	/**
	 * @param array
	 *            һ��Ŀ��������������lengthΪn mΪ��Ҫ����ƶ���λ��
	 * @return int[] ���ص���������������飬��������������ݷ���Ҫ��
	 */
	public static int[] huanwei(int[] array, int m) {// ������ĿҪ����������1�����ݽṹ
		int len = array.length;
		int newArray[] = new int[len];
		for (int i = 0; i < newArray.length; i++) {
			if (i < m) {// ��ǰ���m������
				newArray[i] = array[len - m + i];// newArray��ǰm����λarray�ĺ�mΪ����
			} else {
				newArray[i] = array[i - m];// newArray�ĺ���len-m������λarray��Ǯlen-mΪ����
			}
		}
		return newArray;
	}

	// ������37��
	// ��Ŀ����n����Χ��һȦ��˳���źš��ӵ�һ���˿�ʼ��������1��3��������
	// ������3�����˳�Ȧ�ӣ���������µ���ԭ���ڼ��ŵ���λ��
	/**
	 * @param peopleNum
	 *            ��ʾ��Ҫ������������
	 * @return int ����������µ��Ǹ��˵ı��
	 */
	public static int zhaoren(int peopleNum) {
		int res = 0;
		int flag = 1;
		List<Integer> lists = new ArrayList<Integer>();// ��Ϊ�����漰��ɾ�����������ԲŲ���List���ݽṹ
		for (int j = 1; j <= peopleNum; j++) {
			lists.add(j);// �o���ϵ�ÿһ��Ԫ�ظ�ֵ
		}
		int temp = 1;// ÿһȦ��һ���˱������֣�Ĭ��Ϊ1

		while (lists.size() >= 3) {// ��ʣ�µ���������������������ѭ��
			System.out.println(lists.size());
			for (int i = 0; i < lists.size(); i++) {
				if (temp % 3 == 0) {
					System.out.println(temp + "temp");
					lists.remove(temp - 1);
					i--;// ***��Ϊ��ɾ��һ��Ԫ���Ժ�lists��size��Ӧ�ļ���1��������������i�Լ�һ��
				}
				temp++;// temp��������ÿһ�������������
				if (temp > 3) {
					temp = temp % 3;
				}
			}
		}
		if (temp == 1) {
			lists.remove(0);
		} else {
			lists.remove(1);
		}
		int are = lists.get(0);
		return are;
	}

	// ������38��
	// ��Ŀ��дһ����������һ���ַ����ĳ��ȣ���main�����������ַ�����������䳤�ȡ�
	/**
	 * @param strValue
	 *            ��ʾһ��String���͵�����
	 * @return int ����һ��int���͵����ݱ�ʾ���String�������ݵĳ���
	 */
	public int getLengthByString(String strValue) {
		int length = 0;
		char[] strToChar = strValue.toCharArray();
		length = strToChar.length;
		return length;
	}

	// ������39��
	// ��Ŀ����дһ������������nΪż��ʱ��
	// ���ú�����1/2+1/4+...+1/n,������nΪ����ʱ��
	// ���ú���1/1+1/3+...+1/n(����ָ�뺯��)
	/**
	 *@param number�û������һ������
	 *@return intΪ�����
	 */
	public int getSumByNumber(int number) {
		int sum = 0;
		boolean flag = (number % 2 == 0);// �ж����������ǲ���ż��
		int fenmu = 1; // ����Ĭ�ϵĳ�ʼ��ĸΪ1
		if (flag) { // ��numbnerΪż��ʱĬ�ϵĳ�ʼ��ĸΪ2
			fenmu = 2;
		}
		for (int i = fenmu; i <= number; i += 2) {
			sum += 1 / i;
		}
		return sum;
	}

	// ������40��
	// ��Ŀ���ַ�������
	/**
	 * @param strValueһ����Ҫ������ַ���
	 * @return ����һ������õ��ַ���
	 */
	public static String paixun(String strValue) {
		char[] stringArray = strValue.toCharArray();
		Arrays.sort(stringArray);
		strValue = String.copyValueOf(stringArray);
		return strValue;
	}

	public static void main(String[] args) {
		int[] a = { 3, 2, 4, 1, 5, 7 };
		// shuchuArray(a);
		// huanwei(a, 3);
		// System.out.println(zhaoren(4));
		System.out.println(paixun("124a2Q1."));
	}

}
