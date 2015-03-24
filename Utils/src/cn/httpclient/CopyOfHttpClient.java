package cn.httpclient;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.net.MalformedURLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.params.ConnRouteParams;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.AbstractHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

public class CopyOfHttpClient {
	private static DefaultHttpClient client = ConnectionManager.getHttpClient();
	private String url = "http://journal.biomart.cn/";
	private String hostname = "";  //172.16.17.1
	private int port = 808;

	public CopyOfHttpClient() {
		// TODO Auto-generated constructor stub
	}

	public CopyOfHttpClient(String url, String hostname, int port) {
		this.url = url;
		this.hostname = hostname;
		this.port = port;
	}

	public String get() {

		System.out.println("get from url:" + url);
		HttpGet get = new HttpGet(url);
		HttpEntity entity = null;
		HttpResponse response = null;
		// ��Ϣд�뵽�����ļ�
		FileWriter fw = null;
		// ��Ӧ����
		StringWriter sw = new StringWriter();
		try {
			fw = new FileWriter("f:/cookie.txt");

			// ���ô���
			if (!"".equals(hostname)) {
				HttpHost proxy = new HttpHost(hostname, port);
				client.getParams().setParameter(ConnRouteParams.DEFAULT_PROXY,
						proxy);
			}
			// ��������
			response = client.execute(get);
			// cookie��Ϣ
			List<Cookie> cookies = ((AbstractHttpClient) client)
					.getCookieStore().getCookies();

			if (cookies.isEmpty()) {
				System.out.println("cookies is None");
			} else {
				System.out.println("cookies:");
				for (int i = 0; i < cookies.size(); i++) {
					System.out.println("- " + cookies.get(i).toString());
					fw.write(cookies.get(i).toString() + "\r\n");
				}
			}
			// ��Ӧ����
			entity = response.getEntity();
			if (entity != null) {
				InputStream is = entity.getContent();
				IOUtils.copy(is, sw, "utf-8");
				is.close();
				sw.close();
			}
			fw.write("response��\r\n" + sw.toString());
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("��ҳ�򿪳���:" + url);
		} finally {
			get.abort();
			try {
				EntityUtils.consume(entity);
			} catch (IOException e) {
				e.printStackTrace();
			}
			if (fw != null) {
				try {
					fw.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return sw.toString();
	}

	public static void downImg(String url, String filepath) {
		FileOutputStream out = null;
		try {
			HttpGet get = new HttpGet(url);
			HttpResponse response = client.execute(get);
			String filename = url.substring(url.lastIndexOf("/") + 1);
			File file = new File(filepath);
			if (!file.exists()) {
				file.mkdirs();
			}
			file = new File(file, filename);
			out = new FileOutputStream(file);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				// byte[] inbyte = new byte[entity.getContent().available()];
				// out.write(inbyte);
				InputStream ins = entity.getContent();
				byte[] buffer = new byte[1024];
				int len;
				// ��srcFile�е���Ϣȡ����������
				while ((len = ins.read(buffer)) != -1) {
					// System.out.println(len);
					// �������ƣ�ÿ�δ�ins�ж�ȡ1024���ֽڴ洢��buffer�����У�Ȼ��buffer����д�뵽out��
					out.write(buffer, 0, len);
				}
			}
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	public static void main(String[] args) throws MalformedURLException {
		String result = new CopyOfHttpClient().get();
		// System.out.println("get result:" + result);

		/*
		 * ������(http://|https://){1}����ʾ����1��http����https��
		 * [\\w\\.\\-/:]+��\wƥ����ĸ�����ֻ��»��߻���\\w�Ķ������ת��
		 */
		Pattern pattern = Pattern
				.compile("(http://|https://){1}[\\w\\.\\-/:]+(\\.png|\\.jpg|\\.jpeg|\\.jif){1}");
		Pattern pattern1 = Pattern
				.compile("(http://|https://){1}[\\w\\.\\-/:]+(\'|\"){1}");
		pattern1 = Pattern.compile("(\"url\"){1}\\s*:\\s*(\'|\"){1}[\\w//]+");
		pattern1 = Pattern.compile("(\"url\")([/s/S]*)+[,]$");
		pattern1 = Pattern.compile("[a-zA-z]+://[^s]* ");

		Matcher matcher = pattern1.matcher(result);
		String filepath = "f://img1";
		// ����http��׺
		Set<String> set = new HashSet<String>();
		while (matcher.find()) {
			String imgurl = matcher.group();
			System.out.println(imgurl);
			// String filename = imgurl.substring(imgurl.lastIndexOf("/") + 1);
			// System.out.println(imgurl + "|" + filename);
			// ��׺
			// String houzhui = filename.substring(filename.lastIndexOf(".") +
			// 1);
			// set.add(houzhui);
			// GetImages.getImage(filepath, filename, imgurl);
			// downImg(imgurl, filepath);

		}
		//
		// for (String item:set) {
		// System.out.println(item);
		// }

	}

}
