package cn.reflect;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.Iterator;

import org.apache.log4j.Logger;
import org.dom4j.Element;
import org.junit.Test;

import cn.com.service.Helloworld;

/**
 * @ClassName: ReflectUtil
 * @Description: ���乤����
 * @author XiMing.Fu
 * @date 2014-3-10 ����02:19:48
 * 
 */
public class ReflectUtil {
	private static final Logger LOG = Logger.getLogger(ReflectUtil.class);

	/**
	 * @Title: executeMethod
	 * @Description:ִ����className�еķ���method������Ϊparam
	 * @param className
	 * @param method
	 * @param param
	 * @return
	 * @return Object
	 * @throws
	 * @author XiMing.Fu
	 */
	public static Object executeMethod(String className, String method,
			String param) {
		if (className == null || "".equals(className) || method == null
				|| "".equals(className)) {
			return null;
		}
		try {
			Class<?> obj = Class.forName(className);
			Method mt = obj.getMethod(method, String.class);
			return mt.invoke(obj.newInstance(), param);
		} catch (Exception e) {
			LOG.error("socket������Ϣ�ص���������" + e.getMessage());
			return null;
		}
	}

	/**
	 * ��ȡʵ��
	 * 
	 * @param className
	 * @return
	 * @throws Exception
	 */
	public Helloworld getEngineStartupListener(String className)
			throws Exception {
		Class clazz = Class.forName(className);
		Object obj = clazz.newInstance();
		if (!(obj instanceof Helloworld)) {
			throw new Exception("���ʹ���");
		}
		return (Helloworld) obj;
	}

	/**
	 * @Title: getClassFile
	 * @Description: ��ȡclass�ļ�
	 * @param clazz
	 * @return
	 * @return File
	 * @throws
	 * @author XiMing.Fu
	 */
	public static File getClassFile(Class<?> clazz) {
		URL path = clazz.getResource(clazz.getName().substring(
				clazz.getName().lastIndexOf(".") + 1)
				+ ".class");
		if (path == null) {
			String name = clazz.getName().replaceAll("[.]", "/");
			path = clazz.getResource("/" + name + ".class");
		}
		return new File(path.getFile());
	}

	public static File getClassPathFile(Class<?> clazz) {
		File file = getClassFile(clazz);
		for (int i = 0, count = clazz.getName().split("[.]").length; i < count; i++)
			file = file.getParentFile();
		if (file.getName().toUpperCase().endsWith(".JAR!")) {
			file = file.getParentFile();
		}
		return file;
	}

	public static String getClassPath(Class<?> clazz)
			throws UnsupportedEncodingException {
		return java.net.URLDecoder.decode(getClassPathFile(clazz)
				.getAbsolutePath(), "UTF-8");

	}

	public static Object getClassInstance(String classPath)
			throws ClassNotFoundException, InstantiationException,
			IllegalAccessException {
		Class<?> t = Class.forName(classPath); // �������Class����
		Object res = t.newInstance(); // ���ɷ�����
		return res;
	}

	/**
	 * <b>Summary: </b> getBeanDButil(ͨ��������ƽ�Element�е�ֵ��ֵ��object��)
	 * 
	 * @param e
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public static Object getBeanDButil(Element e, Object vo) throws Exception {
		for (Iterator iterInner = e.elementIterator(); iterInner.hasNext();) {
			Element elementInner = (Element) iterInner.next();
			String rootName = elementInner.getName();// �ڵ�����ƣ���title
			String rootValue = elementInner.getText();// �ڵ�����ݣ���title��ǩ�������
			Method method = vo.getClass().getMethod(
					"set" + getSetterName(rootName),
					new Class[] { Class.forName("java.lang.String") });
			method.invoke(vo, new Object[] { rootValue });
		}
		return vo;
	}

	/**
	 * ת��������getter��ʽ �磺COLUMN_NAME ת����Column_name
	 * 
	 * @param columnName
	 * @return
	 */
	public static String getSetterName(String columnName) {
		String head = "";
		String tail = "";
		int len = columnName.length();
		head = columnName.substring(0, 1).toUpperCase();
		if (len > 1) {
			tail = columnName.substring(1).toLowerCase();
		}
		return head + tail;
	}

	/**
	 * @Title: method
	 * @Description: ִ��cls������ֶ�fieldName��get����
	 * @param cls
	 * @param fieldName
	 * @return
	 * @throws Exception
	 * @return String
	 * @throws
	 * @author XiMing.Fu
	 */
	public String method(Class cls, String fieldName) throws Exception {
		fieldName = fieldName.substring(0, 1).toUpperCase()
				+ fieldName.substring(1);
		Method meth = cls.getDeclaredMethod("get" + fieldName, new Class[0]);
		return (String) meth.invoke(cls, new Object[] {});
	}
	
	@Test
	public void getPath(){
		
		System.out.println(ReflectUtil.class.getClassLoader());
	}

	public static void main(String[] args) {
		executeMethod(
				"com.chinacreator.v2.xzsp.charge.service.impl.SocketMsgServiceImpl",
				"responseMessage",
				"<MSG><MsgID>4</MsgID><MsgClass>1</MsgClass><CmdType>1001</CmdType></MSG>");
	}
}
