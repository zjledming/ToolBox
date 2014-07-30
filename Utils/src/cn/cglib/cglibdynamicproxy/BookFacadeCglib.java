package cn.cglib.cglibdynamicproxy;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

/**
 * ʹ��cglib��̬����
 * 
 * @author student
 * 
 */
public class BookFacadeCglib implements MethodInterceptor {
	private Object target;

	/**
	 * �����������
	 * 
	 * @param target
	 * @return
	 */
	public Object getInstance(Object target) {
		this.target = target;
		
		Enhancer enhancer = new Enhancer();
		enhancer.setSuperclass(this.target.getClass());//class cn.cglib.cglibdynamicproxy.BookFacadeImpl1
		// �ص�����
		enhancer.setCallback(this);// cn.cglib.cglibdynamicproxy.BookFacadeCglib@13a328f
		// �����������
		return enhancer.create();
	}

	@Override
	// �ص�����
	public Object intercept(Object obj, Method method, Object[] args,
			MethodProxy proxy) throws Throwable {
		System.out.println("���￪ʼ");
		proxy.invokeSuper(obj, args);
		System.out.println("�������");
		return null;

	}

}