package cn.cmd;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

import org.junit.Test;

import com.sun.xml.internal.ws.api.streaming.XMLStreamReaderFactory.Woodstox;

/**
 * Java ����cmd.exe����
 * 
 * @author ximing.fu
 * 
 */
public class CmdUtil {
	public static void main(String[] args) throws IOException,
			InterruptedException {
		CmdUtil util = new CmdUtil();
		// test11();
		// test44();
		// test6();
		// util.opennote();
		//util.setFoulder();
		util.doEXE();
	}

	/**
	 * ��D��copyһ���ļ���E��-����ͨ��
	 */
	@Test
	public void test1() {
		try {
			String cmdStr = "cmd /c copy d:\\test.txt e:\\";
			Runtime.getRuntime().exec(cmdStr);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * �����ļ���
	 */
	public static void test11() {
		try {
			// xcopy c:\img d:\file /d/e
			String cmdStr = "cmd /c xcopy c:\\img d:\\file /d/e";
			Runtime.getRuntime().exec(cmdStr);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void test12() {
		try {

			/* ��dos����:xcopy C:\Դ�ļ� D:\Դ�ļ�\/S/E */
			/* �ַ�����ĸ�ʽ:xcopy C:\\Դ�ļ� D:\\Դ�ļ�\\/S/E */
			System.out.println("������Դ�ļ��ĵ�ַ��·��:");
			BufferedReader br = new BufferedReader(new InputStreamReader(
					System.in));
			String input = br.readLine();
			/* ����ַ�еĵ�б�ܱ�Ϊ˫б�� */
			String from = input.replaceAll("\\\\", "\\\\\\\\");
			/* ��ȡ�ļ��� */
			String name_file = from.substring(from.lastIndexOf("\\") + 1,
					from.length());
			/* ��Ŀ�꿽����D�̸�Ŀ¼ */
			String to = "D:\\" + name_file + "\\/S/E";
			/* ƴװ���� */
			String cmd = "cmd.exe /C xcopy " + from + " " + to;
			System.out.println(cmd);
			/* ִ������ */
			java.lang.Runtime.getRuntime().exec(cmd);
			System.out.println("�ļ�������ϡ�");
			System.out.println("�ļ��Ѿ�������:D:\\" + name_file);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	/**
	 * ɾ��һ���ļ�(ע���ǵ����ļ������ļ���)-����ͨ��
	 */
	@Test
	public void test2() {
		try {
			String cmdStr = "cmd /c del e:\\test.txt";
			Runtime.getRuntime().exec(cmdStr);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * ǿ��ɾ��e:/test�ļ����µ������ļ��м��ļ�-����ͨ��
	 */
	@Test
	public void test3() {
		try {
			String cmdStr = "cmd /c rd/s/q e:\\tomcat-6.0\\work";
			Runtime.getRuntime().exec(cmdStr);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * ִ��bat�ļ�-����ͨ�������Ǵ���δ�Զ��ر�
	 */
	@Test
	public static void test4() {
		String path = "F:\\tempServer\\tomcat-6.0\\bin\\startup.bat";
		Runtime run = Runtime.getRuntime();
		try {
			Process process = run.exec("cmd.exe /k start " + path);

			// �����ý����ӡ������̨��
			InputStream in = process.getInputStream();
			while (in.read() != -1) {
				System.out.println(in.read());
			}
			in.close();
			process.waitFor();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void test44() throws IOException {
		String path = "F:\\tempServer\\tomcat-6.0\\bin\\startup.bat";
		// Runtime.getRuntime().exec(path);

		try {
			Process p = Runtime.getRuntime().exec(path);
			DataInputStream ls_in = new DataInputStream(p.getInputStream());
			String ls_str;

			try {
				while ((ls_str = ls_in.readLine()) != null) {
					System.out.println(ls_str);
				}
			} catch (IOException e) {
				System.exit(0);
			}

		} catch (Exception ex) {
			System.out.println("startup Exception: " + ex);
		}
	}

	/**
	 * ProcessDemo���򿪼��±� from url: http://www.tutorialspoint.com/java/index.htm;
	 * ��Ҫ�رռ��±�������ֹ����
	 */
	@Test
	public void opennote() {
		try {
			// create a new process
			System.out.println("Creating Process...");
			Process p = Runtime.getRuntime().exec("notepad.exe");

			// get the output stream
			OutputStream out = p.getOutputStream();

			// close the output stream
			System.out.println("Closing the output stream...");
			out.close();
			System.out.println(p.toString());

			// ��txt�ļ�
			String[] cmd = { "notepad.exe", "c:\\Result.txt" };
			Runtime.getRuntime().exec(cmd);

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		System.out.println("done");
	}

	/**
	 * ���ļ��������������������ر��ļ� ����Ҳ��������ִ�С� /E:ON ����cmd��չ /c ָ֮��Ķ����ַ���
	 * ����ʱ��E�ĳ�F��ִ�н����һ���ġ�
	 * 
	 * @throws IOException
	 */
	public void opennotedo() throws IOException {
		Process process = Runtime.getRuntime().exec(
				"cmd /E:ON /c start c:\\Result.txt");
		// Process process =
		// Runtime.getRuntime().exec("cmd.exe /c start F:\\long_cennect.txt");
		// // ����仰���Խ�����Ͼ仰��һ���ġ�
		InputStreamReader inputStr = new InputStreamReader(
				process.getInputStream());
		BufferedReader br = new BufferedReader(inputStr);
		String temp = "";
		while ((temp = br.readLine()) != null) {
			System.out.println(temp);
		}

		process.destroy();
		br.close();
		inputStr.close();
	}

	/**
	 * ���ù���Ŀ¼����ִ���빤��Ŀ¼��ص��������ļ������õ�
	 * 
	 * @throws IOException
	 */
	public void setFoulder() throws IOException {
		File dir = new File("d:\\");
		Process process = Runtime.getRuntime().exec("d:\\delWork.bat",
				null, dir);
	}

	/**
	 * ��ʾ��Ϣ
	 * 
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public static void show() throws IOException, InterruptedException {
		String str;
		Process process = Runtime.getRuntime().exec("cmd /c dir windows");
		BufferedReader bufferedReader = new BufferedReader(
				new InputStreamReader(process.getInputStream()));
		while ((str = bufferedReader.readLine()) != null)
			System.out.println(str);
		process.waitFor();
	}
	
	/**
	 * ִ�б���ext�ļ�������չִ���κ��ļ�
	 */
	public void doEXE(){
		 // TODO Auto-generated method stub
        Runtime r = Runtime.getRuntime();
        //Ӧ�ó������ڵ�·��
        String str_path = "C:\\Users\\android\\Desktop\\AdbeRdr11000_zh_CN.exe";
        
        try {
            //�÷�������һ���µĽ���
            Process pro = r.exec(str_path);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        //���ø÷������������Ľ���
        //pro.destroy������
	}

}
