package cn.performance;

import java.util.concurrent.CountDownLatch;

/**
 * 
 * 通过不断创建新的守护线程，await使其一直等待而不释放，从而测试，jvm能创建的最大线程数
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
		// 将该线程标记为守护线程或用户线程
		this.setDaemon(true);
	}

	public void run() {
		try {
			// 使当前线程在锁存器倒计数至零之前一直等待，除非线程被中断
			cdl.await();
		} catch (InterruptedException e) {
		}
	}
}