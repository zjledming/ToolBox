import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.CodeSource;
import java.security.ProtectionDomain;

import org.junit.Test;

/**
 * @ClassName: RelatedPathUtil
 * @Description: ������ṩ��һЩ�������class�ļ�λ������λ�ķ�����
 * @author XiMing.Fu
 * @date 2014-3-10 ����05:31:30
 * 
 */
public class RelatedPathUtil {
	/**
	 * ��ȡһ�����class�ļ����ڵľ���·���� ����������JDK������࣬Ҳ�������û��Զ�����࣬�����ǵ���������������ࡣ
	 * ֻҪ���ڱ������п��Ա����ص��࣬�����Զ�λ������class�ļ��ľ���·����
	 * 
	 * @param cls
	 *            һ�������Class����
	 * @return ������class�ļ�λ�õľ���·���� ���û�������Ķ��壬�򷵻�null��
	 */
	public static String getPathFromClass(Class cls) throws IOException {
		String path = null;
		if (cls == null) {
			throw new NullPointerException();
		}
		URL url = getClassLocationURL(cls);
		if (url != null) {
			path = url.getPath();
			if ("jar".equalsIgnoreCase(url.getProtocol())) {
				try {
					path = new URL(path).getPath();
				} catch (MalformedURLException e) {
				}
				int location = path.indexOf("!/");
				if (location != -1) {
					path = path.substring(0, location);
				}
			}
			File file = new File(path);
			path = file.getCanonicalPath();
		}
		return path;
	}

	/**
	 * �����������ͨ����ĳ�����class�ļ������·������ȡ�ļ���Ŀ¼�ľ���·���� ͨ���ڳ����к��Ѷ�λĳ�����·�����ر�����B/SӦ���С�
	 * ͨ��������������ǿ��Ը������ǳ�����������ļ���λ������λĳ�����·����
	 * ���磺ĳ��txt�ļ�����ڳ����Test���ļ���·����../../resource/test.txt��
	 * ��ôʹ�ñ�����Path.getFullPathRelateClass("../../resource/test.txt",Test.class)
	 * �õ��Ľ����txt�ļ�����ϵͳ�еľ���·����
	 * 
	 * @param relatedPath
	 *            ���·��
	 * @param cls
	 *            ������λ����
	 * @return ���·������Ӧ�ľ���·��
	 * @throws IOException
	 *             ��Ϊ����������ѯ�ļ�ϵͳ�����Կ����׳�IO�쳣
	 */
	public static String getFullPathRelateClass(String relatedPath, Class cls)
			throws IOException {
		String path = null;
		if (relatedPath == null) {
			throw new NullPointerException();
		}
		String clsPath = getPathFromClass(cls);
		File clsFile = new File(clsPath);
		String tempPath = clsFile.getParent() + File.separator + relatedPath;
		File file = new File(tempPath);
		path = file.getCanonicalPath();
		return path;
	}

	/**
	 * ��ȡ���class�ļ�λ�õ�URL����������Ǳ���������ķ������������������á�
	 */
	private static URL getClassLocationURL(final Class cls) {
		if (cls == null)
			throw new IllegalArgumentException("null input: cls");
		URL result = null;
		final String clsAsResource = cls.getName().replace('.', '/')
				.concat(".class");
		final ProtectionDomain pd = cls.getProtectionDomain();
		// java.lang.Class contract does not specify
		// if 'pd' can ever be null;
		// it is not the case for Sun's implementations,
		// but guard against null
		// just in case:
		if (pd != null) {
			final CodeSource cs = pd.getCodeSource();
			// 'cs' can be null depending on
			// the classloader behavior:
			if (cs != null)
				result = cs.getLocation();

			if (result != null) {
				// Convert a code source location into
				// a full class file location
				// for some common cases:
				if ("file".equals(result.getProtocol())) {
					try {
						if (result.toExternalForm().endsWith(".jar")
								|| result.toExternalForm().endsWith(".zip"))
							result = new URL("jar:"
									.concat(result.toExternalForm())
									.concat("!/").concat(clsAsResource));
						else if (new File(result.getFile()).isDirectory())
							result = new URL(result, clsAsResource);
					} catch (MalformedURLException ignore) {
					}
				}
			}
		}

		if (result == null) {
			// Try to find 'cls' definition as a resource;
			// this is not
			// document��d to be legal, but Sun's
			// implementations seem to //allow this:
			final ClassLoader clsLoader = cls.getClassLoader();
			result = clsLoader != null ? clsLoader.getResource(clsAsResource)
					: ClassLoader.getSystemResource(clsAsResource);
		}
		return result;
	}

	/**
	 * ����classes��λ��ȡ���õ�WEBINF��·��
	 * */
	public static String getProjectPath(Class c) {
		String pp = null;
		try {
			pp = getPathFromClass(c);
			pp = pp.substring(0, pp.indexOf("classes"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return pp;
	}

	/**
	 * ����classes��λ��ȡ���õ�WEBӦ�ø�·��
	 * */
	public static String getWebAppPath(Class c) {
		String pp = null;
		try {
			pp = getPathFromClass(c);
			pp = pp.substring(0, pp.indexOf("WEB-INF"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return pp;
	}

	public static void main(String[] args) {
		try {
			String fullPath = getPathFromClass(RelatedPathUtil.class);
			System.out.println("fullPath:" + fullPath);
			String classessPath = fullPath.substring(0,
					fullPath.indexOf("WEB-INF"));
			System.out.println(getFullPathRelateClass("../test/abc/..",
					RelatedPathUtil.class));
			System.out.println("classessPath:" + classessPath);
			getProjectPath(RelatedPathUtil.class);
			String ss = "a:/dfd/soaconfig/helo.xml";
			System.out.println(ss = ss.substring(ss.indexOf("soaconfig")));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Java Resource·��С��
	 * �ɲο��ʼǣ�Java Resource·��С��
	 */
	@Test
	public void testResource1(){
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		loader = RelatedPathUtil.class.getClassLoader();
		loader = ClassLoader.getSystemClassLoader();
	}

}
