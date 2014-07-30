package cn.producerandconsumer;

/**
 * ͬ����ջ��
 * 
 * @version 1.0 2013-7-24 ����02:03:21
 */
public class SynchronizedStack {
	private int index = 0;
	private int size = 100;
	// �ڴ湲����
	private char[] data;

	public SynchronizedStack(int size) {
		System.out.println("ջ������");
		this.size = size;
		data = new char[size];
	}

	/**
	 * ��������
	 * 
	 * @param c
	 */
	public synchronized void push(char c) {
		while (index == size) {
			try {
				System.err.println("ջ����");
				this.wait();// �ȴ���ֱ�������ݳ�ջ
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
				e.printStackTrace();
			}
		}
		data[index] = c;
		index++;
		this.notify();// ֪ͨ�����̰߳����ݳ�ջ
	}

	/**
	 * ��������
	 * 
	 * @return
	 */
	public synchronized char pop() {
		while (index == 0) {
			try {
				System.err.println("ջ����");
				this.wait();// �ȴ���ֱ�������ݳ�ջ
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
				e.printStackTrace();
			}
		}
		index--; // ָ�������ƶ�
		char ch = data[index];
		this.notify(); // ֪ͨ�����̰߳�������ջ
		return ch;
	}

	// ��ʾ��ջ����
	public synchronized void print() {
		for (int i = 0; i < data.length; i++) {
			System.out.print(data[i]);
		}
		System.out.println();
		this.notify(); // ֪ͨ�����߳���ʾ��ջ����
	}
}