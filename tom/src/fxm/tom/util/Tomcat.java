package fxm.tom.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;

public class Tomcat {

	public static String tomcatPath = "E:\\server\\apache-tomcat-6.0.41";
	public static String projectPath = "F:\\workspace";
	private static String f = File.separator;

	/**
	 * 根据tomcat路径以及项目路径配置tomcat
	 * 
	 * @param tomcatPath
	 * @param projectPath
	 * @return
	 */
	public boolean config(String tomcatPath, String projectPath)
			throws Exception {
		// E:\tomcat-6.0
														// E:\workspace\hyxfs-core\creatorepp

		/*
		 * 导出jar之后路径获取错误，发生异常： java.io.FileNotFoundException: file:\C:\Users\
		 * android\Desktop\TomcatTool.jar!\ fxm\tom\ util\creatorepp.xml
		 * (文件名、目录名或卷标语法不正确。)
		 */
		// 获取配置文件内容，给项目路径赋值，例如：E:\workspace\hyxfs-core\creatorepp
		// String localPath = getCurrentClassPath();
		// System.out.println(localPath);
		// String content = read(new File(localPath.substring(0,
		// localPath.lastIndexOf("\\")), "creatorepp.xml"));

		// 改成string字符串
		// String content =
		// "<?xml version=\"1.0\" encoding=\"gbk\"?><Context crossContext=\"true\"    path=\"/creatorepp\"    docBase=\"PROPATH\"    reloadable=\"false\">    <ResourceLink name=\"reportsource\" global=\"reportsource\" type=\"javax.sql.DataSource\"/></Context>";
		// content = content.replace("PROPATH", projectPath);
		// // 生成新的配置文件 conf\Catalina\localhost
		// String confPath = tomcatPath + f + "conf" + f + "Catalina" + f
		// + "localhost" + f + "creatorepp.xml";
		// File configFile = new File(confPath);
		// if (!configFile.exists()) {
		// configFile.mkdirs();
		// }
		// 替换文件内容
		// writeStr2File(configFile, content);

		/***************** 重写该功能 *****************/
		// 1.定位配置文件creatorepp.xml，如果不存在，创建
		String configFolder = tomcatPath + f + "conf" + f + "Catalina" + f
				+ "localhost";
		File folder = new File(configFolder);
		if (!folder.exists()) {
			folder.mkdirs();
		}
		File file = new File(folder, "creatorepp.xml");
		if (!file.exists()) {
			file.createNewFile();
		}
		// 2.清空配置
		FileUtils.writeStringToFile(file, "");
		// 3.重写配置
		write2log("<?xml version=\"1.0\" encoding=\"gbk\"?>", file);
		write2log(
				"<Context crossContext=\"true\" path=\"/creatorepp\" docBase=\""
						+ projectPath + "\" reloadable=\"false\">", file);
		write2log(
				"\t<ResourceLink name=\"reportsource\" global=\"reportsource\" type=\"javax.sql.DataSource\" />",
				file);
		write2log("</Context>", file);
		return true;
	}

	public static void write2log(String data, File file) throws IOException {
		FileWriter fileWriter = new FileWriter(file, true);
		fileWriter.write(data + "\r\n");
		fileWriter.close();
	}

	/**
	 * 清楚tomcat缓存
	 * 
	 * @param tomcatPath
	 * @return
	 */
	public boolean clear(String tomcatPath) throws Exception {
		//System.out.println(tomcatPath);// E:\tomcat-6.0
		File file = new File(tomcatPath,"work");
		if (file.exists()) {
			tomcatPath = tomcatPath.replace("\\", "\\\\");
			System.out.println(tomcatPath);// E:\tomcat-6.0
			// "cmd /c rd/s/q e:\\tomcat-6.0\\work"
			String cmdStr = "cmd /c rd/s/q " + tomcatPath + "\\work";
			Runtime.getRuntime().exec(cmdStr);
		}else {
//			throw new Exception("缓存文件【"+file.getAbsolutePath()+"】不存在，请检查tomcat路径是否正确！");
		}
		return true;
	}

	/**
	 * 往文件f中写入内容content
	 * 
	 * @param f
	 * @param content
	 */
	public void writeStr2File(File f, String content) throws Exception {
		if (!f.exists()) {
			f.createNewFile();
		}
		FileWriter fileWriter = new FileWriter(f);
		fileWriter.write(content);
		fileWriter.close();
	}

	/**
	 * 取当前class文件所在的物理地址
	 * 
	 * @return
	 */
	public String getCurrentClassPath() {
		String classPath = "";
		try {
			String classname = this.getClass().getName();
			String classname_resource = "/" + classname.replace('.', '/')
					+ ".class";
			URL url = this.getClass().getResource(classname_resource);
			String urlPath = url.getPath();
			File classFile = new File(urlPath);
			classPath = classFile.getPath();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return classPath;
	}

	/**
	 * 按字节读取文件：不遗漏任何字符。 包括换行，换行之前的空格
	 * 
	 * @param file
	 * @return
	 */
	public String read(File file) {
		BufferedReader br = null;
		InputStreamReader isr = null;
		StringBuffer buffer = new StringBuffer();
		try {
			isr = new InputStreamReader(new FileInputStream(file), "gbk");
			br = new BufferedReader(isr);
			int s;
			while ((s = br.read()) != -1) {
				buffer.append((char) s);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null) {
					br.close();
				}
				if (isr != null) {
					isr.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return buffer.toString();
	}

	/**
	 * 按行读取文件（发现bug：为读取换行符）
	 * 
	 * @param file
	 * @return
	 */
	public String readFile(File file) {
		StringBuffer sb = new StringBuffer();
		FileReader fileread = null;
		BufferedReader bufread = null;
		try {
			fileread = new FileReader(file);
			bufread = new BufferedReader(fileread);
			String read;
			while ((read = bufread.readLine()) != null) {
				sb.append(read).append("/r/n");
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (bufread != null) {
					bufread.close();
				}
				if (fileread != null) {
					fileread.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	}


	public void start(String tomcatPath) throws Exception {
		File startUpFile = new File(tomcatPath, "bin\\startup.bat");
		String path = startUpFile.getPath().replace("\\", "\\\\");
		Runtime.getRuntime().exec("cmd.exe /k start " + path);
	}
	
	
	public String getLocalCofingProject(String tomcatPath) throws Exception{
		String re = "";
		File configXML = new File(tomcatPath, "conf\\Catalina\\localhost\\creatorepp.xml");
		if (configXML.exists()) {
			String content = FileUtils.readFileToString(configXML);
			//System.out.println(content);
			re = getByReg(content);
			re = re.replace("docBase=", "").replace("\"", "");
		}else{
			re = "配置文件不存在："+configXML.getAbsolutePath();
		}
		//System.out.println(re);
		return re;
	}
	
	public String getByReg(String content) throws Exception{
		StringBuffer sb = new StringBuffer();
		String reg = "docBase=\".*creatorepp\"";
		Pattern pattern = Pattern.compile(reg);
		Matcher matcher = pattern.matcher(content);
		while (matcher.find()) {
			sb.append(matcher.group(0));
		}
		return sb.toString();
	}
	
	
	public static void main(String[] args) {
		Tomcat t = new Tomcat();
		try {
//			System.out.println(t.getCurrentClassPath());
//			String re = t.getCurrentClassPath().substring(0,
//					t.getCurrentClassPath().lastIndexOf("\\"));
//			System.out.println(re);
//			File file = new File("/creatorepp.xml");
//			System.out.println(file.getAbsolutePath());
			// t.delFile(new File(tomcatPath + File.separator + "work"));
			// t.delFolder(new File(tomcatPath + File.separator + "work"));
			
			t.getLocalCofingProject(tomcatPath);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	
}
