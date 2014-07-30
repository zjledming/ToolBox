package cn.file;

import java.io.*;

/**
 * 
 * �������������� TXT �ļ������ж���д���޸Ĳ���
 * 
 */
public class TXTFileUtil {
	public static BufferedReader bufread = null;
	// ָ���ļ�·��������
	private static String path = "D:/suncity.txt";
	private static File filename = new File(path);
	private static String readStr = "";

	/** */
	/**
	 * �����ı��ļ� .
	 * 
	 * @throws IOException
	 * 
	 */
	public static void creatTxtFile() throws IOException {
		if (!filename.exists()) {
			filename.createNewFile();
			System.err.println(filename + "�Ѵ����� ");
		}
	}

	/**
	 * δ��ȡ����
	 * ��ȡ�ı��ļ� .
	 * 
	 */
	public static String readTxtFile() {
		String read;
		FileReader fileread = null;
		try {
			fileread = new FileReader(filename);
			bufread = new BufferedReader(fileread);
			try {
				while ((read = bufread.readLine()) != null) {
					readStr = readStr + read + "/r/n";
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				if (bufread != null) {
					bufread.close();
				}
				if (fileread != null) {
					fileread.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		System.out.println("�ļ������� :" + "/r/n" + readStr);
		return readStr;
	}

	/** */
	/**
	 * д�ļ� .
	 * 
	 */
	public static void writeTxtFile(String newStr) throws IOException {
		// �ȶ�ȡԭ���ļ����ݣ�Ȼ�����д�����
		String filein = newStr + "/r/n" + readStr + "/r/n";
		RandomAccessFile mm = null;
		try {
			mm = new RandomAccessFile(filename, "rw");
			mm.writeBytes(filein);
		} catch (IOException e1) {
			// TODO �Զ����� catch ��
			e1.printStackTrace();
		} finally {
			if (mm != null) {
				try {
					mm.close();
				} catch (IOException e2) {
					// TODO �Զ����� catch ��
					e2.printStackTrace();
				}
			}
		}
	}

	/**
	 * ���ļ���ָ�����ݵĵ�һ���滻Ϊ�������� .
	 * 
	 * @param oldStr
	 *            ��������
	 * @param replaceStr
	 *            �滻����
	 */
	public static void replaceTxtByStr(String oldStr, String replaceStr) {
		String temp = "";
		try {
			File file = new File(path);
			FileInputStream fis = new FileInputStream(file);
			InputStreamReader isr = new InputStreamReader(fis);
			BufferedReader br = new BufferedReader(isr);
			StringBuffer buf = new StringBuffer();

			// �������ǰ�������
			for (int j = 1; (temp = br.readLine()) != null
					&& !temp.equals(oldStr); j++) {
				buf = buf.append(temp);
				buf = buf.append(System.getProperty("line.separator"));
			}

			// �����ݲ���
			buf = buf.append(replaceStr);

			// ������к��������
			while ((temp = br.readLine()) != null) {
				buf = buf.append(System.getProperty("line.separator"));
				buf = buf.append(temp);
			}

			br.close();
			FileOutputStream fos = new FileOutputStream(file);
			PrintWriter pw = new PrintWriter(fos);
			pw.write(buf.toString().toCharArray());
			pw.flush();
			pw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * д���ļ�
	 * @param file
	 * @param content
	 */
	public static void writeStr2File(String file, String content) {
		try {
			File f = new File(file);
			if (!f.exists()) {
				f.createNewFile();
			}
			FileWriter fileWriter = new FileWriter(file, false); //true ĩβ����д
			fileWriter.write(content + "\r\n");
			fileWriter.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

	}
	
	/**
	 * ���ֽڶ�ȡ�ļ�������©�κ��ַ��� �������У�����֮ǰ�Ŀո�
	 * @param file
	 * @return
	 */
	public static String read(File file) {
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
	 * ���ж�ȡ�ļ�������bug��δ��ȡ���з���
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
				sb.append(read);//.append("/r/n")
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
	
	/** */
	/**
	 * main��������
	 * 
	 * @param s
	 * @throws IOException
	 */
	public static void main(String[] s) throws IOException {
//		TXTFileUtil.creatTxtFile();
//		TXTFileUtil.readTxtFile();
		System.out.println(new TXTFileUtil().readFile(filename));
//		TXTFileUtil.writeTxtFile("20080808:12:13");
//		TXTFileUtil.replaceTxtByStr("ken", "zhang");
//		TXTFileUtil.writeStr2File(filename.getPath(),"���ư�");
//		System.out.println(TXTFileUtil.read(filename));
		
	}
}