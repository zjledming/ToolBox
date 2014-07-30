package cn.cglib;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import org.junit.Test;

public class CglibUtil {

	/**
	 * @Title: testProxy1
	 * @Description: ���Դ���һ��û��ʵ���κνӿڵ�Person��
	 * @throws Exception
	 * @return void
	 * @throws
	 * @author XiMing.Fu
	 */
	@Test
	public void testProxy1() throws Exception {

		final Person p1 = new Person(); // Person��û��ʵ���κνӿ�

		Enhancer en = new Enhancer(); // ����������ʵ��

		en.setSuperclass(Person.class); // ���ñ��������ֽ��룬CGLIB�����ֽ������ɱ������������

		en.setCallback(new MethodInterceptor() {// ���ûص���������һ����������

			public Object intercept(Object target, Method method,

			Object[] args, MethodProxy proxy) throws Throwable {

				Object o = method.invoke(p1, args); // ע�����p1,��ȻΪ�ⲿ������Դ������MethodΪJDK��Method����

				System.err.println("After...");

				return o;

			}

		});

		Person p = (Person) en.create(); // ͨ��create��������Person��Ĵ���

		System.err.println(p.getClass());// ������Ķ���

		p.sayHi("Hello");

	}

	@Test
	public void testProxy2() throws Exception {

		final Dog dog = new Dog(); // �������������

		Enhancer en = new Enhancer(); // ����CGLIB��ǿ��

		en.setSuperclass(IAnimal.class); // ���ýӿ��࣬Ҳ�������ó�dogʵ���࣬��Ӱ��create���صĶ���

		en.setCallback(new MethodInterceptor() {

			public Object intercept(Object target, Method method,

			Object[] args, MethodProxy proxy) throws Throwable {

				System.err.println("Before...");

				Object o = method.invoke(dog, args);

				System.err.println("After...");

				return o;

			}

		});

		// Dog dog2 = (Dog) en.create();//����ת��Ϊ�ӿ�,�����׳�ClassCastException

		IAnimal dog2 = (IAnimal) en.create();

		dog2.eat();

	}

	/**
	 * @Title: main
	 * @Description: ʹ�þ�̬��������һ��û�нӿڵĶ���
	 * @param args
	 * @return void
	 * @throws
	 * @author XiMing.Fu
	 */
	public static void main(String[] args) {
		final Person src = new Person();

		// ֱ��ʹ�þ�̬��������һ������

		Person p = (Person) Enhancer.create(Person.class,
				new MethodInterceptor() {

					public Object intercept(Object proxyedObj, Method method,
							Object[] args,

							MethodProxy proxy) throws Throwable {

						System.err.println("Hello");

						// ʹ��ԭ���ķ������ã�ע�������src

						// Object oo = method.invoke(src, args);

						// ʹ��MethodProxy���ø���Ĵ��룬ͬ����Ч

						Object oo = proxy.invokeSuper(proxyedObj, args);

						return oo;

					}

				});

		System.err.println(p.getClass());

		p.abc();
	}
}
