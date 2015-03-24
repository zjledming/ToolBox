package cn.performance;

import java.util.concurrent.CountDownLatch;

/**
 * 
 * ͨ�����ϴ����µ��ػ��̣߳�awaitʹ��һֱ�ȴ������ͷţ��Ӷ����ԣ�jvm�ܴ���������߳���
 * @author ximing.fu
 *
 */
public class TestNativeOutOfMemoryError {

	public static void main(String[] args) {

		for (int i = 0;; i++) {
			System.out.println("i = " + i);
			new Thread(new HoldThread()).start();
		}
	}

}

class HoldThread extends Thread {
	CountDownLatch cdl = new CountDownLatch(1);

	public HoldThread() {
		// �����̱߳��Ϊ�ػ��̻߳��û��߳�
		this.setDaemon(true);
	}

	public void run() {
		try {
			// ʹ��ǰ�߳�������������������֮ǰһֱ�ȴ��������̱߳��ж�
			cdl.await();
		} catch (InterruptedException e) {
		}
	}
}