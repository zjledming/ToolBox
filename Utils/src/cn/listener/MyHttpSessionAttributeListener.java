package cn.listener;

import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;

/** 
 * @ClassName: MyHttpSessionAttributeListener
 * @Description: ���ڼ���Session�������Եĸı��¼�
 * @author XiMing.Fu
 * @date 2014-3-24 ����05:27:59
 * 
 */
public class MyHttpSessionAttributeListener implements HttpSessionAttributeListener {

	public void attributeAdded(HttpSessionBindingEvent arg0) {
		System.out.println("attributeAdded~~~~~~~~~~~~~");
		
	}

	public void attributeRemoved(HttpSessionBindingEvent arg0) {
		System.out.println("attributeRemoved~~~~~~~~~~~~~");
		
	}

	public void attributeReplaced(HttpSessionBindingEvent arg0) {
		System.out.println("attributeReplaced~~~~~~~~~~~~~");
		
	}

}
