package cn.listener;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;

/** 
 * @ClassName: MyServletRequestListener
 * @Description: ���ڼ����󶨵�HttpSession���е�ĳ�������״̬���¼�������
 * @author XiMing.Fu
 * @date 2014-3-24 ����05:28:39
 * 
 */
public class MyServletRequestListener implements ServletRequestListener {

	public void requestDestroyed(ServletRequestEvent arg0) {
		System.out.println("requestDestroyed~~~~~~~~~");
		
	}

	public void requestInitialized(ServletRequestEvent arg0) {
		System.out.println("requestInitialized~~~~~~~~~");
		
	}

}
