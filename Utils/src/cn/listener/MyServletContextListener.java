package cn.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * @ClassName: MyServletContextListener
 * @Description: 
 *               ���ڼ������������Ĵ��������ٵ��¼�������:��WEB��������ʱ����contextInitialed,ֹͣWEB����ʱ����contextDestroyed
 *               ��
 * @author XiMing.Fu
 * @date 2014-3-24 ����04:49:35
 * 
 */
public class MyServletContextListener implements ServletContextListener {

	public void contextDestroyed(ServletContextEvent arg0) {
		System.out.println("contextDestroyed~~~~~~~~~~~");

	}

	public void contextInitialized(ServletContextEvent arg0) {
		System.out.println("contextInitialized~~~~~~~~~~~~~~");

	}

}
