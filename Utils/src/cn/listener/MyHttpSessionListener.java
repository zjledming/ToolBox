package cn.listener;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * @ClassName: MyHttpSessionListener
 * @Description: ���ڼ���Session����Ĵ���������
 * @author XiMing.Fu
 * @date 2014-3-24 ����05:27:37
 * 
 */
public class MyHttpSessionListener implements HttpSessionListener {

	public void sessionCreated(HttpSessionEvent arg0) {
		System.out.println("sessionCreated~~~~~~~~~~~~");

	}

	public void sessionDestroyed(HttpSessionEvent arg0) {
		System.out.println("sessionDestroyed~~~~~~~~~~~~");

	}

}
