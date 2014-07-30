package mst;

/**
 * 
 * @author ������
 * @since 2013��3��8��
 */

public class Test07 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// System.out.println(getPeach());
		// getLiangWeiShu();
		System.out.println(testSuShuHe(40));
		// System.out.println(isSuShu(4));
	}

	// ������41��
	// ��Ŀ����̲����һ�����ӣ���ֻ�������֡�
	// ��һֻ���Ӱ��������ƾ�ݷ�Ϊ��ݣ�����һ����
	// ��ֻ���ӰѶ��һ�����뺣�У�������һ�ݡ�
	// �ڶ�ֻ���Ӱ�ʣ�µ�������ƽ���ֳ���ݣ��ֶ���һ����
	// ��ͬ���Ѷ��һ�����뺣�У�������һ�ݣ�
	// ���������ġ�����ֻ���Ӷ����������ģ��ʺ�̲��ԭ�������ж��ٸ����ӣ�
	/**
	 * @return int Ϊ�ʼ���ٵ�������Ŀ
	 */
	public static int getPeach() {
		// �����ٵ�������Ŀ������Ϊÿһ��ֻ����һ������
		int sumByGroup = 1;
		// �������Ƶķ�����֪ÿһ���������Ŀ=5*��һ���������Ŀ + 1
		for (int i = 5; i >= 1; i--) {
			sumByGroup = (sumByGroup * 5 + 1);// ���ʼ����0����ǵ����ѭ�����������Ŀ
		}
		return sumByGroup;
	}

	// ������42��
	// ��Ŀ��809*??=800*??+9*??+1 ����??�������λ��,8*??�Ľ��Ϊ��λ����
	// 9*??�Ľ��Ϊ3λ������??�������λ������809*??��Ľ����

	public static void getLiangWeiShu() {
		int liangweishu = 10;
		for (int i = 10; i <= 99 / 8; i++) {
			if (9 * i >= 100) {
				if (809 * i == 800 * i + 9 * i + 1) {
					liangweishu = i;
				}
				break;
			}
		}
		System.out.println("����λ��Ϊ:" + liangweishu + ",809*" + liangweishu + "="
				+ (800 * liangweishu + 9 * liangweishu + 1));
	}

	// ������43��
	// ��Ŀ����0��7������ɵ�����������
	public int sumOfJiShu() {
		int count = 0;
		int n = 8;
		// һλ��
		count = n / 2;
		// ��λ��
		count += (n - 1) * n / 2;
		// ��λ��
		count += (n - 1) * n * n / 2;
		// ��λ��
		count += (n - 1) * n * n * n / 2;
		// ��λ��
		count += (n - 1) * n * n * n * n / 2;
		// ��λ��
		count += (n - 1) * n * n * n * n * n / 2;
		// ��λ��
		count += (n - 1) * n * n * n * n * n * n / 2;
		return count;
	}

	//	

	// ������44��
	// ��Ŀ��һ��ż�����ܱ�ʾΪ��������֮�͡�
	/**
	 * @param oushuΪ�û��Լ����������һ��ż��
	 * @return ����һ��boolean���� �ж��ǲ��ǿ��Էֽ����������ֻ��
	 */
	public static boolean testSuShuHe(int oushu) {
		boolean flag = false;
		int number1 = 0;
		int number2 = 0;
		for (int i = 1; i < oushu; i++) {
			number1 = i;
			number2 = oushu - i;
			if (isSuShu(number1) && isSuShu(number2)) {// �ж��ǲ�����������Ϊ����
				flag = true;
				System.out.println(number1 + "+" + number2 + "="
						+ (number1 + number2));
				break;
			}
		}
		return flag;
	}

	// Ϊ�˷�����ã�˽�ж���һ���ж������ķ���
	private static boolean isSuShu(int number) {
		boolean flag = true;
		if (number == 2) {
			return flag;
		}
		for (int i = 2; i < number; i++) {
			if (number % i == 0) {
				flag = false;
				break;
			}
		}
		return flag;
	}

	// ������45��
	// ��Ŀ���ж�һ�������ܱ�����9����

	// �⣺֮�鲻�ܱ�9����
}
