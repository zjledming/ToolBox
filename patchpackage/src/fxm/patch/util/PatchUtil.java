package fxm.patch.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;

import com.alibaba.fastjson.JSON;

public class PatchUtil {

	private String patchName;
	private String patchPath;
	private String svnFilePath;
	private String myeclipseFilePath;
	private static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	private int count = 1;

	private String result = "";
	// ��������������־�ļ�
	private static File logFile;
	public static String cacheFilePath = "C:\\patchpackage.log";

	public PatchUtil() {
	}

	public PatchUtil(String patchName, String patchPath, String svnFilePath,
			String myeclipseFilePath) {
		this.patchName = patchName;
		this.patchPath = patchPath;
		this.svnFilePath = svnFilePath;
		this.myeclipseFilePath = myeclipseFilePath;
	}

	/**
	 * д����־
	 * 
	 * @param data
	 * @throws IOException
	 */
	public static void write2log(String data) throws IOException {
		// FileUtils.writeStringToFile(logFile, data);
		FileWriter fileWriter = new FileWriter(logFile, true);
		fileWriter.write(data + "\r\n");
		fileWriter.close();
	}

	/**
	 * ���ɲ�����:<br>
	 * 1.���ɡ�creatorepp���ļ��У�����svn�еġ�creatorepp��������copy�����ļ����У�<br>
	 * 2.���ɡ�����˵��.txt�� ��<br>
	 * 3.��src�е�javaԴ�ļ�copy����������class��
	 */
	public boolean creatPatch() {
		try {
			// ������file
			if (!"".equals(patchName.trim())) {
				patchPath = patchPath + File.separator + patchName;
			}
			File patchFile = new File(patchPath);
			// �������ɲ����� - ��tm��ɾ���ˣ���С��ѡ���ļ��У����̫����
			// FileUtils.deleteDirectory(patchFile);
			// String temp = patchPath.replace("\\", "\\\\");
			// String cmdStr = "cmd /c rd/s/q "+temp;
			// Runtime.getRuntime().exec(cmdStr);
			if (!patchFile.exists()) {
				patchFile.mkdirs();
			}

			// ��������������־�ļ�
			logFile = new File(patchFile, "patchpackage.log");
			if (!logFile.exists()) {
				logFile.createNewFile();
			} else {
				// �����־
				FileUtils.writeStringToFile(logFile, "");
			}

			write2log("==========================\r\n�������ſ�\r\n==========================");
			write2log("��������" + patchFile.getPath());

			// ��������creatorepp file
			File eppFile = new File(patchFile, "creatorepp");
			if (!eppFile.exists()) {
				eppFile.mkdirs();
			}
			write2log("��������creatorepp��" + eppFile.getPath());
			// ������������˵��.txt
			File sjFile = new File(patchFile, "����˵��.txt");
			if (!sjFile.exists()) {
				sjFile.createNewFile();
			}
			write2log("������������˵����" + sjFile.getPath());
			write2log("svn��" + svnFilePath);
			write2log("myeclipse��" + myeclipseFilePath);

			write2log("==========================\r\n����1��copy ��svn��creatorepp�� to ����������creatorepp��\r\n==========================");
			// ��svn�����ļ����¡�creatorepp���µ����ݸ��Ƶ���eppFile����
			String srcFilePath = this.svnFilePath + File.separator
					+ "creatorepp";
			File srcFile = new File(srcFilePath);
			/*
			 * ���ﻹ��һ�ֿ��ܣ��������ֻ�޸���javaԴ���룬ͬ�����ᵼ��creatorepp�ļ���
			 */
			if (!srcFile.exists()) {
				write2log("��ע�⣺svn�����ļ��в�����creatoreppĿ¼�������������ֿ��ܣ�");
				write2log("1.svn�����ļ�·��ѡ�������ѡ����ĿĿ¼�����磺hnszwfw-core��");
				write2log("2.�˴β�����δ�޸��κ�creatorepp���ļ���ֵ�޸���javaԴ�ļ���");
				write2log("��ԡ�2������������лᴴ��һ���յġ�creatorepp��Ŀ¼���Բ�������Ӱ�졣");
				// this.result += "svn�����ļ�·��ѡ�������ѡ����ĿĿ¼�����磺hnszwfw-core";
				// return "";
				// ������;�ڵ���svn�ļ������洴��һ����creatorepp����Ӱ��
				srcFile.mkdirs();
			}
			// �¶������Ǻ�ѽ���Ǻ�...
			FileUtils.copyDirectoryToDirectory(srcFile, patchFile);

			write2log("==========================\r\n����2��������������������˵����������ʼ��Ϣ\r\n==========================");
			// ��ʼ������˵��.txt��������
			write2sjsm(sjFile);
			write2log("==========================\r\n����3������javaԴ�ļ�\r\n==========================");
			write2log("a��copy�����С�svn��src��...��java���µ���Ŀ¼to����������WEB-INF��classes��");
			// ��src�е�javaԴ�ļ�copy����������class��
			// SVN FILE
			File svnFile = new File(svnFilePath);
			write2log("svn:" + svnFile.getPath());
			// SVN ��src file
			File javaFile = new File(svnFile, "src");
			write2log("svn��src:" + javaFile.getPath());
			if (javaFile.exists()) {
				/*
				 * ��java�µ�����Դ����copy����������\creatorepp\WEB-INF\classes��
				 */
				// ��������creatorepp file
				File classFile = new File(eppFile, "WEB-INF\\classes");
				// ������һ��bug������ڲ�ͬsource·���£�������javaԴ�����ֱ��ڲ�ͬ���ļ����£�����������Ҫһһ����
				for (File javaFileTemp : javaFile.listFiles()) {
					// ���˵�source���ƣ�ֱ���ҵ�java�µ�Դ����
					File tempFile = javaFileTemp;
					write2log("svn��src��...:" + tempFile.getPath());
					// �������javaĿ¼����������Ŀ¼
					while (!tempFile.getPath().endsWith("java")) {
						tempFile = tempFile.listFiles()[0];
					}
					write2log("svn��src��...��java:" + tempFile.getPath());
					// ÿ�����ɲ����������²���class�ļ�
					// ע�⣺��ֻ�Ǹ����������ߣ�����delete����ֹ��������
					// classFile.deleteOnExit();
					if (!classFile.exists()) {
						classFile.mkdirs();
						write2log("����  ��������WEB-INF��classes:"
								+ classFile.getPath());
					}
					File[] fileList = tempFile.listFiles();
					// ��java�µ������ļ���copy����������WEB-INF\\classes��
					for (int i = 0; i < fileList.length; i++) {
						FileUtils.copyDirectoryToDirectory(fileList[i],
								classFile);
						write2log("copy ��svn��src��...��java���µ���Ŀ¼��"
								+ fileList[i].getPath()
								+ "�� to ����������WEB-INF��classes��"
								+ classFile.getPath());
					}
				}
				write2log("b���滻���á�myeclipse��creatorepp��WEB-INF��classes���µ�class�ļ��滻����������WEB-INF��classes���µ�java�ļ�");
				// ����Ŀ�е�class�ļ��滻����������\creatorepp\WEB-INF\classes�µ�java�ļ�
				// ��ĿFILE
				File proFile = new File(myeclipseFilePath);
				write2log("myeclipse:" + proFile.getPath());
				// ��Ŀ�µ�class·��
				File proClassFile = new File(proFile,
						"creatorepp\\WEB-INF\\classes");
				write2log("myeclipse��creatorepp��WEB-INF��classes:"
						+ proClassFile.getPath());

				// �滻�ļ�
				// write2log("��ĿԴ�����ַ��" + proClassFile.getPath());
				// write2log("������class��ַ��" + classFile.getPath());
				// C:\Users\android\Desktop\����\bd1\creatorepp\WEB-INF\classes
				// E:\workspace\jszwfw-core\creatorepp\WEB-INF\classes
				copyFile(classFile, proClassFile);
				// write2log("================ class�滻Java�ļ����� ================");

				// System.out.println(proFile);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * ����ĿproClassFile��copy class�ļ���������file����
	 * 
	 * @param file
	 * @param proClassFile
	 * @throws Exception
	 */
	public void copyFile(File file, File proClassFile) throws Exception {
		// �����������е�java�ļ�
		File[] listFiles = file.listFiles();
		for (int i = 0; i < listFiles.length; i++) {
			// ��������java
			File javaFile = listFiles[i];
			if (javaFile.isFile()) {
				// java path
				String filePath = javaFile.getPath();
				// ����Ŀclass�µĶ�Ӧ�����ļ�·��
				String path = proClassFile.getPath()
						+ filePath.split("classes")[1];
				// �ҵ���.class�ļ�����.java�ļ�
				System.out.println("====" + path);
				/*
				 * �����쳣�� ԭ���ڲ������д�����.class�ļ� �Ʋ⣺Ӧ�����ڲ�����ɾ����ʱ��û��ɾ����ȫ
				 * �����������ÿ�����ɲ�������ʱ����ɾ���ڴ��
				 */
				if (path.indexOf(".java") != -1) {
					String claFilePath = path.substring(0,
							path.indexOf(".java"))
							+ ".class";
					File claFile = new File(claFilePath);
					write2log("-----------" + count++ + "-----------");
					write2log(claFile.getPath() + "   copy��");
					write2log(javaFile.getPath());
					FileUtils.copyFileToDirectory(claFile,
							javaFile.getParentFile());
					// copy���֮��ɾ�������������java�ļ�
					javaFile.delete();
					write2log(filePath + "  ��ɾ��");
					write2log("----------------------");
				} else {
					// ��¼�쳣
					write2log("�������쳣����ǰ�ļ���java�ļ���" + path
							+ "������ϸ����ļ��Ƿ�Ϊclass�ļ���" + filePath);
				}
			} else {
				copyFile(javaFile, proClassFile);
			}
		}
		System.out.println(proClassFile);
	}

	private void write2sjsm(File f) throws Exception {
		if (!f.exists()) {
			f.createNewFile();
		}
		FileWriter fileWriter = new FileWriter(f);
		fileWriter
				.write("������ϵ�ˣ���ϸ��\r\n�����ύ���ڣ�"
						+ format.format(new Date())
						+ "\r\n*****************************************\r\nע�����\r\n1������ǰ�뱸��\r\n*****************************************\r\n�������裺\r\n1������creatorepp\r\n2��\r\n3����������\r\n*****************************************\r\n����˵����\r\n");
		fileWriter.close();
	}
	
	/**
	 * ����������Ϣ
	 * 
	 * @param patch
	 */
	public static void cacheInfo(PatchUtil patch) {
		try {
			Util.writeStr2File(cacheFilePath, JSON.toJSONString(patch));
		} catch (Exception e) {
			try {
				write2log("����������Ϣʧ�ܣ�" + e.getMessage());
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}

	public String getPatchName() {
		return patchName;
	}

	public void setPatchName(String patchName) {
		this.patchName = patchName;
	}

	public String getPatchPath() {
		return patchPath;
	}

	public void setPatchPath(String patchPath) {
		this.patchPath = patchPath;
	}

	public String getSvnFilePath() {
		return svnFilePath;
	}

	public void setSvnFilePath(String svnFilePath) {
		this.svnFilePath = svnFilePath;
	}

	public String getMyeclipseFilePath() {
		return myeclipseFilePath;
	}

	public void setMyeclipseFilePath(String myeclipseFilePath) {
		this.myeclipseFilePath = myeclipseFilePath;
	}


}
