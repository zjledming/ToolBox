package cn.httpclient.cn.util;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @ClassName: ThreadPoolUtils
 * @Description: 线程池辅助类，整个应用程序就只有一个线程池去管理线程。
 *               可以设置核心线程数、最大线程数、额外线程空状态生存时间，阻塞队列长度来优化线程池。
 * @author XiMing.Fu
 * @date 2014-3-7 下午02:45:21
 * 
 */
public class ThreadPoolUtils {
	private static final Log LOG = LogFactory.getLog(ThreadPoolUtils.class);

	private ThreadPoolUtils() {

	}

	private final static Object lock = new Object();
	// 池中所保存的线程数，包括空闲线程。
	private static int CORE_POOL_SIZE = 5;

	// 池中允许的最大线程数。
	private static int MAX_POOL_SIZE = 10;

	// 当线程数大于核心时，此为终止前多余的空闲线程等待新任务的最长时间。
	private static int KEEP_ALIVE_TIME = 1;

	// KEEP_ALIVE_TIME 参数的时间单位 秒
	private static TimeUnit UNIT = TimeUnit.SECONDS;

	// 阻塞队列中最大等待任务数
	private static int MAX_WAIT_TASK = 10000;

	// 任务等待队列
	private static BlockingQueue<Runnable> workQueue = new ArrayBlockingQueue<Runnable>(
			MAX_WAIT_TASK);

	// 线程池
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

	// 测试
	public static void main(String[] args) {
		// 启动线
		threadPool = getInstance();
		// threadPool.execute(new Threadone("1"));
		// threadPool.execute(new Threadtwo());
	}
}
