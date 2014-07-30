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

public class Tomcat {

	public static String tomcatPath = "E:\\tomcat-6.0";
	public static String projectPath = "E:\\workspace";
	private static String f = File.separator;

	/**
	 * ����tomcat·���Լ���Ŀ·������tomcat
	 * 
	 * @param tomcatPath
	 * @param projectPath
	 * @return
	 */
	public boolean config(String tomcatPath, String projectPath) {
		try {
			System.out.println(tomcatPath + projectPath);// E:\tomcat-6.0  E:\workspace\hyxfs-core\creatorepp
			
			
			/*
			 * ����jar֮��·����ȡ���󣬷����쳣��
			 * java.io.FileNotFoundException: file:\C:\Users\ android\Desktop\TomcatTool.jar!\ fxm\tom\ util\creatorepp.xml (�ļ�����Ŀ¼�������﷨����ȷ��)
			 */
			// ��ȡ�����ļ����ݣ�����Ŀ·����ֵ�����磺E:\workspace\hyxfs-core\creatorepp
//			String localPath = getCurrentClassPath();
//			System.out.println(localPath);
//			String content = read(new File(localPath.substring(0,
//					localPath.lastIndexOf("\\")), "creatorepp.xml"));
			
			// �ĳ�string�ַ���
			String content = "<?xml version=\"1.0\" encoding=\"gbk\"?><Context crossContext=\"true\"    path=\"/creatorepp\"    docBase=\"PROPATH\"    reloadable=\"false\">    <ResourceLink name=\"reportsource\" global=\"reportsource\" type=\"javax.sql.DataSource\"/></Context>";
			content = content.replace("PROPATH", projectPath);
			// �����µ������ļ� conf\Catalina\localhost
			String confPath = tomcatPath + f + "conf" + f + "Catalina" + f
					+ "localhost" + f + "creatorepp.xml";
			File configFile = new File(confPath);
			if (!configFile.exists()) {
				configFile.mkdirs();
			}
			// �滻�ļ�����
			writeStr2File(configFile, content);
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
		return true;
	}
	
	/**
	 * ���tomcat����
	 * @param tomcatPath
	 * @return
	 */
	public boolean clear(String tomcatPath) {
		try {
			System.out.println(tomcatPath);// E:\tomcat-6.0
			tomcatPath = tomcatPath.replace("\\", "\\\\");
			System.out.println(tomcatPath);// E:\tomcat-6.0
			// "cmd /c rd/s/q e:\\tomcat-6.0\\work"
			String cmdStr = "cmd /c rd/s/q "+tomcatPath+"\\work" ;
			Runtime.getRuntime().exec(cmdStr);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	/**
	 * ���ļ�f��д������content
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
	 * ȡ��ǰclass�ļ����ڵ������ַ
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
	 * ���ֽڶ�ȡ�ļ�������©�κ��ַ��� �������У�����֮ǰ�Ŀո�
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
	 * ���ж�ȡ�ļ�������bug��Ϊ��ȡ���з���
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


	public static void main(String[] args) {
		Tomcat t = new Tomcat();
		try {
			System.out.println(t.getCurrentClassPath());
			String re = t.getCurrentClassPath().substring(0,
					t.getCurrentClassPath().lastIndexOf("\\"));
			System.out.println(re);
			File file = new File("/creatorepp.xml");
			System.out.println(file.getAbsolutePath());
			// t.delFile(new File(tomcatPath + File.separator + "work"));
			// t.delFolder(new File(tomcatPath + File.separator + "work"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void start(String tomcatPath) throws Exception {
		File startUpFile = new File(tomcatPath, "bin\\startup.bat");
		String path = startUpFile.getPath().replace("\\", "\\\\");
		Runtime.getRuntime().exec("cmd.exe /k start " + path);;
	}
}
