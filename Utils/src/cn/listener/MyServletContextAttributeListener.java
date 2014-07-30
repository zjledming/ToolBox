package cn.listener;

import javax.servlet.ServletContextAttributeEvent;
import javax.servlet.ServletContextAttributeListener;

/**
 * @ClassName: MyServletContextAttributeListener
 * @Description: ���ڼ���WEBӦ�����Ըı���¼����������������ԡ�ɾ�����ԡ��޸�����
 * @author XiMing.Fu
 * @date 2014-3-24 ����05:26:28
 * 
 */
public class MyServletContextAttributeListener implements
		ServletContextAttributeListener {

	public void attributeAdded(ServletContextAttributeEvent arg0) {
		System.out.println("attributeAdded~~~~~~~~~~~~~~~~~~~~~~~~~");

	}

	public void attributeRemoved(ServletContextAttributeEvent arg0) {
		System.out.println("attributeRemoved~~~~~~~~~~~~~~~~~~~~~~~~~");

	}

	public void attributeReplaced(ServletContextAttributeEvent arg0) {
		System.out.println("attributeReplaced~~~~~~~~~~~~~~~~~~~~~~~~~");

	}

}
