import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * MyEclipse9 插件配置代码生成器: 1.这里需要注意的是修改成为刚才svn所在路径，建议改为绝对路径。
 * 比如我的svn在“/opt/soft/Genuitec/myplugins/svn/”，那么就这样：String plugin =
 * "/opt/soft/Genuitec/myplugins/svn/";
 * 用windows的朋友只需要设置你们的绝对路径就可以了，比如d:/myplugins/svn/。。。
 * 2.找到“$myeclipse_home/configuration
 * /org.eclipse.equinox.simpleconfigurator/”，打开其中的
 * “bundles.inf”文件，为了防止分不清是不是我们自己后加的东西，在最后面多回几次车，然后粘贴第4步运行后的代码，保存 3.重启myeclipse
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
				int last = fileName.lastIndexOf("_");// 最后一个下划线的位置
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
		/* 你的插件的安装目录 */
//		String plugin = "D:/Program Files/myplugins/svn/site-1.6.5";
		String plugin = "D:/Program Files/myeclipse9.1/myplugins/jadclipse_3.3.0";
		new PluginConfigCreator().print(plugin);
	}
}