package cn.file;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @ClassName: FileUtil
 * @Description: �ļ�����������
 * @author XiMing.Fu
 * @date 2013-5-24 ����03:18:29
 * 
 */
public class FileUtil {
	private static final Log LOG = LogFactory.getLog(FileUtil.class.getName());

	private static int totle = 0;

	public static File bytesToFile(String filePath, byte[] bytes,
			ByteCharSet charset) {
		File file = new File(filePath);
		byte[] b = (byte[]) null;
		try {
			System.out.println("CHARTSET::::" + charset.toString());
			switch (charset) {
			case DEFAULT:
				b = new String(bytes, "utf-8").getBytes();
				break;
			case GBK$UTF8:
				b = new String(bytes, "gbk").getBytes("gbk");
				break;
			case UTF8:
				b = new String(bytes, "utf-8").getBytes("gbk");
				break;
			case GBK:
				b = new String(bytes, "utf-8").getBytes("utf-8");
				break;
			case GBK$GBK:
				b = new String(bytes, "gbk").getBytes();
				break;
			case UTF8$GBK:
				b = new String(bytes, "gbk").getBytes("utf-8");
				break;
			default:
				FileOutputStream f = new FileOutputStream(file);
				f.write(bytes);
				f.close();
				return file;
			}

			BufferedWriter fw = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(file), "utf-8"));
			fw.write(new String(b));
			fw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return file;
	}

	public static File bytesToFile(String filePath, byte[] bytes, int flag) {
		File file = new File(filePath);
		try {
			FileOutputStream fos = new FileOutputStream(filePath);
			fos.write(bytes);
			fos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return file;
	}

	public static byte[] byteToByte(byte[] content, ByteCharSet encode) {
		byte[] b = content;
		try {
			switch (encode) {
			case DEFAULT:
				b = new String(content, "utf-8").getBytes();
				break;
			case GBK$UTF8:
				b = new String(content, "gbk").getBytes("gbk");
				break;
			case UTF8:
				b = new String(content, "utf-8").getBytes("gbk");
				break;
			case GBK:
				b = new String(content, "utf-8").getBytes("utf-8");
				break;
			case GBK$GBK:
				b = new String(content, "gbk").getBytes();
				break;
			case UTF8$GBK:
				b = new String(content, "gbk").getBytes("utf-8");
				break;
			default:
				b = content;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return b;
	}

	public static void copy(byte[] in, File out) throws IOException {
		ByteArrayInputStream inStream = new ByteArrayInputStream(in);
		OutputStream outStream = new BufferedOutputStream(new FileOutputStream(
				out));
		copy(inStream, outStream);
	}

	/**
	 * �ļ����ٸ���
	 * 
	 * @param infile
	 * @param outfile
	 * @throws IOException
	 */
	public static void copy(File infile, File outfile) throws IOException {
		FileChannel in = null;
		FileChannel out = null;
		FileInputStream inStream = null;
		FileOutputStream outStream = null;
		try {
			inStream = new FileInputStream(infile);
			outStream = new FileOutputStream(outfile);
			in = inStream.getChannel();
			out = outStream.getChannel();
			ByteBuffer buffer = ByteBuffer.allocate(4096);
			while (in.read(buffer) != -1) {
				buffer.flip();
				out.write(buffer);
				buffer.clear();
			}
		} catch (IOException e) {
			throw new IOException(e);
		} finally {
			if (inStream != null) {
				inStream.close();
				inStream = null;
			}
			if (outStream != null) {
				outStream.close();
				outStream = null;
			}
		}

	}

	public static int copy(InputStream in, OutputStream out) throws IOException {
		try {
			int byteCount = 0;
			byte[] buffer = new byte[4096];
			int bytesRead = -1;
			while ((bytesRead = in.read(buffer)) != -1) {
				out.write(buffer, 0, bytesRead);
				byteCount += bytesRead;
			}
			out.flush();
			int i = byteCount;
			return i;
		} finally {
			try {
				in.close();
			} catch (IOException ex) {
			}
			try {
				out.close();
			} catch (IOException ex) {
			}
		}
	}

	/**
	 * @throws IOException
	 * @throws FileNotFoundException
	 * @Title: copy
	 * @Description: �����ļ�
	 * @param srcFile
	 * @param descFile
	 * @return void
	 * @throws
	 */
	public static void copy2(File srcFile, File destFile) throws IOException {
		// �ֽ�����������⣺���ļ��е���Ϣ����ĳ����У�
		FileInputStream fis = new FileInputStream(srcFile);
		if (!destFile.exists()) {
			try {
				destFile.createNewFile();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		FileOutputStream fos = null;
		try {
			// �ֽ����������⣺�������е���Ϣ�����Ŀ���ļ���
			fos = new FileOutputStream(destFile);

			byte[] buffer = new byte[1024];
			int len;
			// ��srcFile�е���Ϣȡ����������
			while ((len = fis.read(buffer)) != -1) {
				System.out.println(len);
				// �������ƣ�ÿ�δ�fis�ж�ȡ1024���ֽڴ洢��buffer�����У�Ȼ��buffer����д�뵽fos��
				fos.write(buffer, 0, len);
			}
			// ˢ�������
			fos.flush();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			fos.close();
			fis.close();
		}
	}

	/**
	 * �ļ����ٸ���
	 * 
	 * @param infile
	 * @param outfile
	 * @throws IOException
	 */
	public static void copy3(File infile, File outfile) throws IOException {
		int length = 2097152;
		FileInputStream in = new FileInputStream(infile);
		FileOutputStream out = new FileOutputStream(outfile);
		FileChannel inC = in.getChannel();
		FileChannel outC = out.getChannel();
		ByteBuffer b = null;
		while (true) {
			if (inC.position() == inC.size()) {
				inC.close();
				outC.close();
			}
			if ((inC.size() - inC.position()) < length) {
				length = (int) (inC.size() - inC.position());
			} else {
				length = 2097152;
			}
			b = ByteBuffer.allocateDirect(length);
			inC.read(b);
			b.flip();
			outC.write(b);
			outC.force(false);
		}

	}

	public static int countFiles(String path) {
		// ��0
		File temp = null;
		File file = new File(path);
		String[] tempList = file.list();
		for (int i = 0; i < tempList.length; i++) {
			// ȡ�ļ�
			if (path.endsWith(File.separator)) {
				temp = new File(path + tempList[i]);
			} else {
				temp = new File(path + File.separator + tempList[i]);
			}
			// �ļ���ֱ��ɾ��
			if (temp.isFile()) {
				totle++;
			}
			// �ļ��У��ݹ�
			if (temp.isDirectory()) {
				countFiles(path + "/" + tempList[i]);
			}
		}
		return totle;
	}

	/**
	 * @Title: delAllFile
	 * @Description: ɾ��ָ���ļ����������ļ�
	 * @param path
	 *            �ļ�����������·��
	 * @return
	 * @return boolean
	 * @throws
	 */
	public static boolean delAllFile(String path) {
		boolean flag = false;
		File file = new File(path);
		// �ļ�������
		if (!file.exists()) {
			return flag;
		}
		// �ļ���ΪĿ¼
		if (!file.isDirectory()) {
			return flag;
		}
		String[] tempList = file.list();
		File temp = null;
		for (int i = 0; i < tempList.length; i++) {
			// ȡ�ļ�
			if (path.endsWith(File.separator)) {
				temp = new File(path + tempList[i]);
			} else {
				temp = new File(path + File.separator + tempList[i]);
			}
			// �ļ���ֱ��ɾ��
			if (temp.isFile()) {
				temp.delete();
			}
			// �ļ��У��ݹ�
			if (temp.isDirectory()) {
				delAllFile(path + "/" + tempList[i]);// ��ɾ���ļ���������ļ�
				delFolder(path + "/" + tempList[i]);// ��ɾ�����ļ���
				flag = true;
			}
		}
		return flag;
	}

	/**
	 * @Title: delFolder
	 * @Description: ɾ���ļ���
	 * @param folderPath
	 *            �ļ�����������·��
	 * @return void
	 * @throws
	 */
	public static void delFolder(String folderPath) {
		try {
			delAllFile(folderPath); // ɾ����������������
			String filePath = folderPath;
			filePath = filePath.toString();
			java.io.File myFilePath = new java.io.File(filePath);
			myFilePath.delete(); // ɾ�����ļ���
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * ��file����charsetת��
	 * 
	 * @param file
	 * @param charset
	 * @return
	 */
	public static byte[] fileToBytes(File file, String charset) {
		byte[] content = new byte[(int) file.length()];
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(file);
			fis.read(content);
			System.out.println(new String(content, charset));
			content = new String(content, charset).getBytes();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return content;
	}

	public static void main(String[] args) {
		String filepath = "C:\\Users\\android\\git\\CodeCreator\\src\\code\\dao\\activities\\ActivitiesDao.java";
		filepath = "C:\\Users\\android\\git\\Utils\\src\\code\\dao\\activities\\impl\\ActivitiesDaoImpl.java";
		filepath = "C:\\Users\\android\\git\\Utils\\src\\code\\dao\\activitystates\\impl\\ActivitystatesDaoImpl.java";
		fileToBytes(new File(filepath), "utf-8");
	}

	/**
	 * ���ļ���ȡΪbyte[]
	 * 
	 * @param FilePath
	 * @return
	 * @throws FileNotFoundException
	 * @throws Exception
	 */
	public synchronized static byte[] getFileBytes(String filePath)
			throws FileNotFoundException, IOException {
		byte[] buffer = null;
		FileInputStream fin = null;
		try {
			File file = new File(filePath);
			fin = new FileInputStream(file);
			buffer = new byte[fin.available()];
			fin.read(buffer);
		} finally {
			try {
				if (fin != null) {
					fin.close();
					fin = null;
				}
			} catch (IOException e) {

			}
		}
		return buffer;
	}

	/**
	 * ���� �ļ������ļ���׺
	 * 
	 * @param fileName
	 *            return string
	 */
	public static String getFiletype(String fileName) {

		String type = "";
		if (fileName == null || fileName.equals(""))
			return type;
		int position = fileName.lastIndexOf(".");
		if (position != -1) {
			type = fileName.substring(position, fileName.length());
		}
		return type;
	}

	/**
	 * 
	 * �õ�ϵͳʱ����ϳ�ʱ�ַ��� �����ǲ����ã���ô����ݿ�õ�ʱ�� return string
	 */
	public static String getSysTime() {
		String str = "";
		try {

			Date d = new Date();
			SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
			df.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai")); // ����ʱ��

			str = df.format(d);
			return str;
		} catch (Exception e) {
			LOG.info(e.getMessage());
			return null;
		}

	}

	public static void main1(String[] args) {
		String path1 = "C:/Users/android/Desktop/01_������/02_������/01_Դ����/hnszwfw-core/src/xzsp/main/java/com/chinacreator/v2/xzsp";
		String path2 = "C:/Users/android/Desktop/����ʡ���������������Ӽ��ϵͳ/����/hnszwfw10203_v2.0.0_XXX_131025/creatorepp/WEB-INF/classes/com/chinacreator/v2/xzsp";
		// String path3 =
		// "C:/Users/android/Desktop/dabao/hnszwfw10203_v2.0.0_149_130531/creatorepp";

		System.out.println("Ӧ�����ļ���������svn����" + countFiles(path2));
		// ����
		totle = 0;
		System.out.println("ʵ���滻�ļ����������滻�ļ�����" + countFiles(path1));
		totle = 0;
		// System.out.println("�����ļ�����"+countFiles(path3));
		// writeStr2File("c:\\Result.txt","1");
		// writeStr2File("c:\\Result.txt","1");
		// writeStr2File("c:\\Result.txt","1");
		// writeStr2File("c:\\Result.txt","1");
	}

	/**
	 * ����·������һϵ�е�Ŀ¼
	 * 
	 * @param path
	 */
	public static void mkDirectory(String path) {
		File file;
		try {
			file = new File(path);
			if (!file.exists()) {
				boolean returnVal = file.mkdirs();
				if (returnVal) {
					LOG.info("�ļ������ɹ�");
				}
			}
		} catch (RuntimeException e) {
			e.printStackTrace();
		} finally {
			file = null;
		}
	}

	/**
	 * uuid ���ļ�����Ψһ return string
	 */
	public static String randomUUID() {
		return UUID.randomUUID().toString();
	}

	/**
	 * 
	 * �ַ������� return date
	 */
	public static Date stringToStime(String str) {
		Date d;
		try {
			SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
			d = df.parse(str);
			return d;
		} catch (Exception e) {
			LOG.info(e.getMessage());
			return null;
		}
	}

	public static void writeStr2File(String file, String content) {
		try {
			File f = new File(file);
			if (!f.exists()) {
				f.createNewFile();
			}
			FileWriter fileWriter = new FileWriter(file, true);
			fileWriter.write(content + "\r\n");
			fileWriter.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

	}

	/**
	 * @Title: writeStr2File
	 * @Description: ���ļ���д���ַ�
	 * @param file
	 * @param content
	 * @return void
	 * @throws
	 * @author XiMing.Fu
	 */
	public static void writeStr2File2(String file, String content) {
		try {
			String path = FileUtil.class.getResource("/").getPath();
			String[] patharr = path.split("WEB-INF/classes/");
			// creatorepp�´���Ŀ¼
			path = patharr[0] + "nontaxlog";
			File f0 = new File(path);
			f0.mkdir();
			// д���ļ�
			File f = new File(path, file);
			if (!f.exists()) {
				f.createNewFile();
			}
			FileWriter fileWriter = new FileWriter(f, true);
			fileWriter.write(new Date() + "\r\n" + content + "\r\n");
			fileWriter.close();
			System.out.println("д��" + path + "/" + file + "�ɹ�");
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("д��" + file + "ʧ��");
		}

	}

	/**
	 * @Title: stream2Str
	 * @Description: streamתstring
	 * @param stream
	 * @param encode
	 * @return
	 * @return String
	 * @throws
	 * @author XiMing.Fu
	 */
	public String stream2Str(InputStream stream, String encode) {
		StringWriter buffer = new StringWriter();
		try {
			IOUtils.copy(stream, buffer, encode);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			try {
				buffer.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return buffer.toString();
	}

	/**
	 * ��Ҫ���ص��� commons-io-1.3.jar
	 * 
	 * @param resFilePath
	 * @param distFolder
	 * @return
	 */
	public static boolean copyFile(String resFilePath, String distFolder) {
		File resFile = new File(resFilePath);
		File distFile = new File(distFolder);
		try {
			if (resFile.isDirectory()) {
				FileUtils.copyDirectoryToDirectory(resFile, distFile);
			} else if (resFile.isFile()) {
				FileUtils.copyFileToDirectory(resFile, distFile, true);
			}
			return true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.err.println("file Backup error!");
			return false;
		}
	}

	/**
	 * ��utf-8�ļ�ת��Ϊgbk<br>
	 * ˼·��<br>
	 * 1.��srcFileName�ļ����䱾��ı����ȡ��StringBuffer��<br>
	 * 2.�ٽ�StringBuffer�Զ�Ӧ�ı���д�뵽Ŀ���ļ���ȥ��ʵ��ת���Ŀ�ġ�
	 * 
	 * @param srcFileName
	 * @param destFileName
	 * @throws IOException
	 */
	public static void transfer(String srcFileName, String destFileName)
			throws IOException {

		// ���з�
		String line_separator = System.getProperty("line.separator");

		FileInputStream fileInputStream = new FileInputStream(srcFileName);
		StringBuffer stringBuffer = new StringBuffer();
		DataInputStream dataInputStream = new DataInputStream(fileInputStream);

		// ��װdataInputStream�������ַ������read()Ч��
		BufferedReader bufferedReader = new BufferedReader(
				new InputStreamReader(dataInputStream, "UTF-8"));
		String line = null;
		while ((line = bufferedReader.readLine()) != null) {
			stringBuffer.append(line).append(line_separator);
		}
		bufferedReader.close();
		dataInputStream.close();
		fileInputStream.close();

		Writer writer = new OutputStreamWriter(new FileOutputStream(
				destFileName), "GBK");
		writer.write(stringBuffer.toString());
		writer.close();

	}

}
