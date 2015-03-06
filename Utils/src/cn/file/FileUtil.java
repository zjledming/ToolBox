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
import org.junit.Test;

/**
 * @ClassName: FileUtil
 * @Description: 文件操作工具类
 * @author XiMing.Fu
 * @date 2013-5-24 下午03:18:29
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
	 * 文件快速复制
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
	 * @Description: 复制文件
	 * @param srcFile
	 * @param descFile
	 * @return void
	 * @throws
	 */
	public static void copy2(File srcFile, File destFile) throws IOException {
		// 字节输入流（理解：将文件中的信息输入的程序当中）
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
			// 字节输出流（理解：将程序当中的信息输出到目标文件）
			fos = new FileOutputStream(destFile);

			byte[] buffer = new byte[1024];
			int len;
			// 将srcFile中的信息取出放入数组
			while ((len = fis.read(buffer)) != -1) {
				System.out.println(len);
				// 工作机制：每次从fis中读取1024个字节存储在buffer数组中，然后将buffer数组写入到fos中
				fos.write(buffer, 0, len);
			}
			// 刷新输出流
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
	 * 文件快速复制
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
		// 清0
		File temp = null;
		File file = new File(path);
		String[] tempList = file.list();
		for (int i = 0; i < tempList.length; i++) {
			// 取文件
			if (path.endsWith(File.separator)) {
				temp = new File(path + tempList[i]);
			} else {
				temp = new File(path + File.separator + tempList[i]);
			}
			// 文件，直接删除
			if (temp.isFile()) {
				totle++;
			}
			// 文件夹，递归
			if (temp.isDirectory()) {
				countFiles(path + "/" + tempList[i]);
			}
		}
		return totle;
	}

	/**
	 * @Title: delAllFile
	 * @Description: 删除指定文件夹下所有文件
	 * @param path
	 *            文件夹完整绝对路径
	 * @return
	 * @return boolean
	 * @throws
	 */
	public static boolean delAllFile(String path) {
		boolean flag = false;
		File file = new File(path);
		// 文件不存在
		if (!file.exists()) {
			return flag;
		}
		// 文件不为目录
		if (!file.isDirectory()) {
			return flag;
		}
		String[] tempList = file.list();
		File temp = null;
		for (int i = 0; i < tempList.length; i++) {
			// 取文件
			if (path.endsWith(File.separator)) {
				temp = new File(path + tempList[i]);
			} else {
				temp = new File(path + File.separator + tempList[i]);
			}
			// 文件，直接删除
			if (temp.isFile()) {
				temp.delete();
			}
			// 文件夹，递归
			if (temp.isDirectory()) {
				delAllFile(path + "/" + tempList[i]);// 先删除文件夹里面的文件
				delFolder(path + "/" + tempList[i]);// 再删除空文件夹
				flag = true;
			}
		}
		return flag;
	}

	/**
	 * @Title: delFolder
	 * @Description: 删除文件夹
	 * @param folderPath
	 *            文件夹完整绝对路径
	 * @return void
	 * @throws
	 */
	public static void delFolder(String folderPath) {
		try {
			delAllFile(folderPath); // 删除完里面所有内容
			String filePath = folderPath;
			filePath = filePath.toString();
			java.io.File myFilePath = new java.io.File(filePath);
			myFilePath.delete(); // 删除空文件夹
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 对file进行charset转码
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
	 * 把文件读取为byte[]
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
	 * 根据 文件名到文件后缀
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
	 * 得到系统时间组合成时字符串 这里是测试用，最好从数据库得到时间 return string
	 */
	public static String getSysTime() {
		String str = "";
		try {

			Date d = new Date();
			SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
			df.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai")); // 设置时区

			str = df.format(d);
			return str;
		} catch (Exception e) {
			LOG.info(e.getMessage());
			return null;
		}

	}

	public static void main1(String[] args) {
		String path1 = "C:/Users/android/Desktop/01_开发库/02_集成区/01_源代码/hnszwfw-core/src/xzsp/main/java/com/chinacreator/v2/xzsp";
		String path2 = "C:/Users/android/Desktop/湖南省网上政务服务与电子监察系统/二期/hnszwfw10203_v2.0.0_XXX_131025/creatorepp/WEB-INF/classes/com/chinacreator/v2/xzsp";
		// String path3 =
		// "C:/Users/android/Desktop/dabao/hnszwfw10203_v2.0.0_149_130531/creatorepp";

		System.out.println("应该有文件数（参照svn）：" + countFiles(path2));
		// 清零
		totle = 0;
		System.out.println("实际替换文件数（参照替换文件）：" + countFiles(path1));
		totle = 0;
		// System.out.println("最终文件数："+countFiles(path3));
		// writeStr2File("c:\\Result.txt","1");
		// writeStr2File("c:\\Result.txt","1");
		// writeStr2File("c:\\Result.txt","1");
		// writeStr2File("c:\\Result.txt","1");
	}

	/**
	 * 根据路径创建一系列的目录
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
					LOG.info("文件创建成功");
				}
			}
		} catch (RuntimeException e) {
			e.printStackTrace();
		} finally {
			file = null;
		}
	}

	/**
	 * uuid 把文件名成唯一 return string
	 */
	public static String randomUUID() {
		return UUID.randomUUID().toString();
	}

	/**
	 * 
	 * 字符到日期 return date
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
	 * @Description: 向文件中写入字符
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
			// creatorepp下创建目录
			path = patharr[0] + "nontaxlog";
			File f0 = new File(path);
			f0.mkdir();
			// 写入文件
			File f = new File(path, file);
			if (!f.exists()) {
				f.createNewFile();
			}
			FileWriter fileWriter = new FileWriter(f, true);
			fileWriter.write(new Date() + "\r\n" + content + "\r\n");
			fileWriter.close();
			System.out.println("写入" + path + "/" + file + "成功");
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("写入" + file + "失败");
		}

	}

	/**
	 * @Title: stream2Str
	 * @Description: stream转string
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
	 * 需要下载导入 commons-io-1.3.jar
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
	
	public static void deleteFolder(String filePath){
		try {
			File file = new File(filePath);
			if (file.exists() && file.isDirectory()) {
				FileUtils.deleteDirectory(file);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	
	@Test
	public void copyTest(){
//		String resFilePath = "E:\\workspace\\ROOT";
		String resFilePath = "E:\\workspace\\ROOT\\WEB-INF\\jsp";
		
		String distFolder = "F:\\webapp\\Tomcat6-USER\\webapps";
		String distFolder2 = "F:\\webapp\\Tomcat6-USER\\webapps\\ROOT";
		String distFolder3 = "F:\\webapp\\Tomcat6-USER\\work";
		String distFolder4 = "F:\\webapp\\Tomcat6-USER\\webapps\\ROOT\\WEB-INF\\jsp";
		
		//deleteFolder(distFolder2);
		//deleteFolder(distFolder3);
		deleteFolder(distFolder4);
		copyFile(resFilePath, distFolder4);
	}

	/**
	 * 将utf-8文件转码为gbk<br>
	 * 思路：<br>
	 * 1.将srcFileName文件以其本身的编码读取到StringBuffer中<br>
	 * 2.再将StringBuffer以对应的编码写入到目标文件中去；实现转码的目的。
	 * 
	 * @param srcFileName
	 * @param destFileName
	 * @throws IOException
	 */
	public static void transfer(String srcFileName, String destFileName)
			throws IOException {

		// 换行符
		String line_separator = System.getProperty("line.separator");

		FileInputStream fileInputStream = new FileInputStream(srcFileName);
		StringBuffer stringBuffer = new StringBuffer();
		DataInputStream dataInputStream = new DataInputStream(fileInputStream);

		// 包装dataInputStream，缓存字符，提高read()效率
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
