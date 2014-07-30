import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * MyEclipse9 ������ô���������: 1.������Ҫע������޸ĳ�Ϊ�ղ�svn����·���������Ϊ����·����
 * �����ҵ�svn�ڡ�/opt/soft/Genuitec/myplugins/svn/������ô��������String plugin =
 * "/opt/soft/Genuitec/myplugins/svn/";
 * ��windows������ֻ��Ҫ�������ǵľ���·���Ϳ����ˣ�����d:/myplugins/svn/������
 * 2.�ҵ���$myeclipse_home/configuration
 * /org.eclipse.equinox.simpleconfigurator/���������е�
 * ��bundles.inf���ļ���Ϊ�˷�ֹ�ֲ����ǲ��������Լ���ӵĶ�������������ؼ��γ���Ȼ��ճ����4�����к�Ĵ��룬���� 3.����myeclipse
 */
public class PluginConfigCreator {
	public PluginConfigCreator() {
	}

	public void print(String path) {
		List<String> list = getFileList(path);
		if (list == null) {
			return;
		}
		int length = list.size();
		for (int i = 0; i < length; i++) {
			String result = "";
			String thePath = getFormatPath(getString(list.get(i)));
			File file = new File(thePath);
			if (file.isDirectory()) {
				String fileName = file.getName();
				if (fileName.indexOf("_") < 0) {
					print(thePath);
					continue;
				}
				String[] filenames = fileName.split("_");
				String filename1 = filenames[0];
				String filename2 = filenames[1];
				result = filename1 + "," + filename2 + ",file:/" + path + "/"
						+ fileName + "//,4,false";
				System.out.println(result);
			} else if (file.isFile()) {
				String fileName = file.getName();
				if (fileName.indexOf("_") < 0) {
					continue;
				}
				int last = fileName.lastIndexOf("_");// ���һ���»��ߵ�λ��
				String filename1 = fileName.substring(0, last);
				String filename2 = fileName.substring(last + 1,
						fileName.length() - 4);
				result = filename1 + "," + filename2 + ",file:/" + path + "/"
						+ fileName + ",4,false";
				System.out.println(result);
			}
		}
	}

	public List<String> getFileList(String path) {
		path = getFormatPath(path);
		path = path + "/";
		File filePath = new File(path);
		if (!filePath.isDirectory()) {
			return null;
		}
		String[] filelist = filePath.list();
		List<String> filelistFilter = new ArrayList<String>();
		for (int i = 0; i < filelist.length; i++) {
			String tempfilename = getFormatPath(path + filelist[i]);
			filelistFilter.add(tempfilename);
		}
		return filelistFilter;
	}

	public String getString(Object object) {
		if (object == null) {
			return "";
		}
		return String.valueOf(object);
	}

	public String getFormatPath(String path) {
		path = path.replaceAll("////", "/");
		path = path.replaceAll("//", "/");
		return path;
	}

	public static void main(String[] args) {
		/* ��Ĳ���İ�װĿ¼ */
//		String plugin = "D:/Program Files/myplugins/svn/site-1.6.5";
		String plugin = "D:/Program Files/myeclipse9.1/myplugins/helloworld";
		new PluginConfigCreator().print(plugin);
	}
}