package fxm.patch.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

public class Util {
	/**
	 * д���ļ�
	 * 
	 * @param file
	 * @param content
	 */
	public static void writeStr2File(String file, String content)
			throws Exception {
		try {
			File f = new File(file);
			if (!f.exists()) {
				f.createNewFile();
			}
			FileWriter fileWriter = new FileWriter(file, false); // true ĩβ����д
			fileWriter.write(content + "\r\n");
			fileWriter.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw e;
		}

	}

	/**
	 * ���ֽڶ�ȡ�ļ�������©�κ��ַ��� �������У�����֮ǰ�Ŀո�
	 * 
	 * @param file
	 * @return
	 */
	public static String read(String file) throws Exception {
		BufferedReader br = null;
		InputStreamReader isr = null;
		StringBuffer buffer = new StringBuffer();
		try {
			if (!new File(file).exists()) {
				return "";
			}
			isr = new InputStreamReader(new FileInputStream(file), "gbk");
			br = new BufferedReader(isr);
			int s;
			while ((s = br.read()) != -1) {
				buffer.append((char) s);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
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
}
