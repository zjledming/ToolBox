import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;

import org.apache.commons.io.IOUtils;

public class IOUtil {

	/**
	 * InputStream�ж�ȡstring
	 * 
	 * @param stream
	 *            ������
	 * @param encode
	 *            ����
	 * @return
	 */
	public static String inputStream2Str(InputStream stream, String encode) {
		StringWriter buffer = new StringWriter();
		try {
			IOUtils.copy(stream, buffer, encode);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				buffer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return buffer.toString();
	}

	/**
	 * InputStream�ж�ȡstring��Ĭ�ϲ���UTF-8����
	 * 
	 * @param stream
	 *            ������
	 * @return
	 */
	public static String inputStream2Str(InputStream stream) {
		return inputStream2Str(stream, "UTF-8");
	}

}
