package cn.jvm;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

import org.junit.Test;

public class JvmLearn {
	
	public static void main(String[] args) throws MalformedURLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
		mainPerson();
	}

	/**
	 * bootstrap classloader��������Щ�������
	 * 
	 */
	@Test
	public void main() {
		URL[] urls = sun.misc.Launcher.getBootstrapClassPath().getURLs();
		for (int i = 0; i < urls.length; i++) {
			System.out.println(urls[i].toExternalForm());
		}
	}

	@Test
	public void main1() {
		System.out.println(System.getProperty("java.ext.dirs"));
		ClassLoader extensionClassloader = ClassLoader.getSystemClassLoader()
				.getParent();
		System.out.println("the parent of extension classloader : "
				+ extensionClassloader.getParent());

		ClassLoader systemClassLoader = ClassLoader.getSystemClassLoader();
		System.out.println("ϵͳ��װ����:" + systemClassLoader);
		ClassLoader extClassLoader = systemClassLoader.getParent();
		System.out.println("ϵͳ��װ�����ĸ��������������չ�������:" + extClassLoader);
		ClassLoader bootClassLoader = extClassLoader.getParent();
		System.out.println("��չ��������ĸ�����������������������:" + bootClassLoader);
	}

	public static void mainPerson() throws MalformedURLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
		String url = "file://"
				+ System.getProperty("user.dir").replaceAll("\\\\", "/")
				+ "/WebRoot/WEB-INF/classes/";
		System.out.println(url);
		ClassLoader c1 = new URLClassLoader(new URL[] { new URL(url) }, null);
		System.out.println("c1�ĸ��������: " + c1.getParent());
		System.out.println("SystemClassLoader: "
				+ ClassLoader.getSystemClassLoader());
		Class<?> class1 = c1.loadClass("cn.jvm.Person");
		Object o = class1.newInstance();
		System.out.println("Person:" + o);
		System.out.println("Test�Ķ�����װ����: "
				+ Person.class.getClassLoader());
		System.out.println("Test��ֱ��ʹ��Personʹ�õ�ClassLoader: "
				+ Person.class.getClassLoader());
		System.out.println("�Զ���װ����װ��Person�Ķ����������: "
				+ o.getClass().getClassLoader());

		Person p = (Person) o;
	}

}
