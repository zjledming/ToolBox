package cn.thread;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @ClassName: ThreadPoolUtils
 * @Description: �̳߳ظ����࣬����Ӧ�ó����ֻ��һ���̳߳�ȥ�����̡߳�
 *               �������ú����߳���������߳����������߳̿�״̬����ʱ�䣬�������г������Ż��̳߳ء�
 * @author XiMing.Fu
 * @date 2014-3-7 ����02:45:21
 * 
 */
public class ThreadPoolUtils {
	private static final Log LOG = LogFactory.getLog(ThreadPoolUtils.class);

	private ThreadPoolUtils() {

	}

	private final static Object lock = new Object();
	// ������������߳��������������̡߳�
	private static int CORE_POOL_SIZE = 5;

	// �������������߳�����
	private static int MAX_POOL_SIZE = 10;

	// ���߳������ں���ʱ����Ϊ��ֹǰ����Ŀ����̵߳ȴ���������ʱ�䡣
	private static int KEEP_ALIVE_TIME = 1;

	// KEEP_ALIVE_TIME ������ʱ�䵥λ ��
	private static TimeUnit UNIT = TimeUnit.SECONDS;

	// �������������ȴ�������
	private static int MAX_WAIT_TASK = 10000;

	// ����ȴ�����
	private static BlockingQueue<Runnable> workQueue = new ArrayBlockingQueue<Runnable>(
			MAX_WAIT_TASK);

	// �̳߳�
	private static ThreadPoolExecutor threadPool;

	public static ThreadPoolExecutor getInstance() {
		synchronized (lock) {
			if (threadPool == null) {
				threadPool = new ThreadPoolExecutor(CORE_POOL_SIZE,
						MAX_POOL_SIZE, KEEP_ALIVE_TIME, UNIT, workQueue,
						new RejectedExecutionHandler() {

							public void rejectedExecution(Runnable r,
									ThreadPoolExecutor executor) {
								try {
									if (!executor.isShutdown()) {
										executor.getQueue().poll();
										executor.execute(r);
									}
								} catch (Throwable e) {
									LOG.error(e);
								}
							}
						});
			}
			return threadPool;
		}
	}

	// ����
	public static void main(String[] args) {
		// ������
		threadPool = getInstance();
		threadPool.execute(new Threadone("1"));
		threadPool.execute(new Threadtwo());
	}
}
