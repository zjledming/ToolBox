package cn.producerandconsumer;

/**
 * ������
 * 
 * @version 1.0 2013-7-24 ����02:14:14
 */
public class Producer implements Runnable {
	private SynchronizedStack stack;

	public Producer(SynchronizedStack s) {
		stack = s;
	}

	public void run() {
		char ch;
		for (int i = 0; i < 100; i++) {
			// �������100���ַ�
			ch = (char) (Math.random() * 26 + 'A');
			stack.push(ch);
			System.out.println("Produced:" + ch);
			try {
				// ÿ����һ���ַ��߳̾�˯��һ��
				Thread.sleep((int) (Math.random() * 1000));
			} catch (InterruptedException e) {

			}
		}
	}

}