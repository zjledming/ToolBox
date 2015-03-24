package cn.httpclient.cn.client;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
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

import cn.httpclient.cn.util.ThreadPoolUtils;

public class HttpClient {
	// ����Ϣд�뵽����Ŀ¼
	private static String localFilepath = "f:/cookie.txt";
	private static FileWriter fw;

	/*
	 * http/https�����ʽ ������(http://|https://){1}����ʾ����1��http����https��
	 * [\\w\\.\\-/:]+��\wƥ����ĸ�����ֻ��»��߻���\\w�Ķ������ת��
	 */
	private static Pattern patternurl = Pattern
			.compile("(http://|https://){1}[\\w\\.\\-/:]+");
	// ͼƬ��ʽ
	private static Pattern patternimg = Pattern
			.compile("(http://|https://){1}[\\w\\.\\-/:]+(\\.png|\\.jpg|\\.jpeg|\\.gif){1}");
	// ��������
	private static Pattern patternurl_ = Pattern
			.compile("(http://|https://){1}[\\w\\.\\-/:]+(com|xhtml|php|shtml|html|jsp){1}");

	// ͼƬ���·��
	private static String filepath_ = "C://img";

	private DefaultHttpClient client;
	private String hostname = "172.16.17.1";
	private int port = 808;

	static {
		try {
			fw = new FileWriter(localFilepath, true);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public HttpClient() {
		// TODO Auto-generated constructor stub
	}

	public HttpClient(String hostname, int port, DefaultHttpClient client) {
		this.hostname = hostname;
		this.port = port;
		this.client = client;
	}

	/**
	 * @Title: begin
	 * @Description: ���߳����
	 * @param url
	 * @return void
	 * @throws
	 * @author XiMing.Fu
	 */
	public void begin(String url) {
		try {
			final String resp = get(url);
			// �����߳�1�����ص�ǰ�����е�ͼƬ
			ThreadPoolUtils.getInstance().execute(new Runnable() {
				public void run() {
					searchImg(resp);
				}
			});
			// �����߳�2������������url
			ThreadPoolUtils.getInstance().execute(new Runnable() {
				public void run() {
					Set<String> result = searchurl(resp);
					for (String tempurl : result) {
						begin(tempurl);
					}
				}
			});
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			if (fw != null) {
				// try {
				// fw.close();
				// } catch (IOException e) {
				// // TODO Auto-generated catch block
				// e.printStackTrace();
				// }
			}
		}
	}

	/**
	 * @Title: begin_
	 * @Description: ���߳����
	 * @param url
	 * @return void
	 * @throws
	 * @author XiMing.Fu
	 */
	public void begin_(String url) {
		try {
			// 1.ִ������
			String resp = get(url);
			// 2.��ȡ��Ӧ�е�ͼƬ
			searchImg(resp);
			// 3.����������url
			Set<String> result = searchurl(resp);
			for (String tempurl : result) {
				// �ݹ�
				begin_(tempurl);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			// try {
			// fw.close();
			// } catch (IOException e) {
			// // TODO Auto-generated catch block
			// e.printStackTrace();
			// }
		}
	}

	/**
	 * @Title: get
	 * @Description: get����
	 * @return
	 * @throws Exception
	 * @return String ��Ӧ��Ϣ
	 * @throws
	 * @author XiMing.Fu
	 */
	public String get(String url) {

		System.out.println("get from url:" + url);
		HttpGet get = new HttpGet(url);
		HttpEntity entity = null;
		HttpResponse response = null;
		StringWriter sw = new StringWriter();
		try {
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
					fw.write(cookies.get(i).toString() + "\r\n");
					System.out.println("Thread"
							+ Thread.currentThread().getId() + ",url��" + url
							+ "  /cookies:" + cookies.get(i).toString());
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
			fw.write("Thread" + Thread.currentThread().getId() + ",url��" + url
					+ "  /response:" + sw.toString());
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("��ҳ�򿪳���:" + url);
		} finally {
			get.abort();
			try {
				EntityUtils.consume(entity);
				if (response != null) {
					EntityUtils.consumeQuietly(response.getEntity());
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			if (fw != null) {
				// try {
				// fw.close();
				// } catch (IOException e) {
				// // TODO Auto-generated catch block
				// e.printStackTrace();
				// }
			}
		}
		return sw.toString();
	}

	/**
	 * @Title: downImg
	 * @Description: ����ͼƬ
	 * @param url
	 *            ͼƬ��ַ
	 * @param filepath
	 *            ͼƬ���·��
	 * @return void
	 * @throws
	 * @author XiMing.Fu
	 */
	public String downImg(String url, String filepath) {
		if (filepath == null || filepath.equals("")) {
			filepath = filepath_;
		}
		FileOutputStream out = null;
		try {
			HttpGet get = new HttpGet(url);
			HttpResponse response = client.execute(get);
			String filename = url.substring(url.lastIndexOf("/") + 1);
			File file = new File(filepath);
			if (!file.exists()) {
				file.mkdirs();
			}
			System.out.println("Thread" + Thread.currentThread().getId()
					+ "��������ͼƬ,url��" + url + "  /name:" + filename);
			file = new File(file, filename);
			if (file.exists()) {
				return null;
			}
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
		return null;
	}

	/**
	 * @Title: searchImg
	 * @Description: ����������resp�е�ͼƬ
	 * @param resp
	 *            �����ַ�
	 * @throws Exception
	 * @return void
	 * @throws
	 * @author XiMing.Fu
	 */
	public void searchImg(String resp) {
		Matcher matcher = patternimg.matcher(resp);
		while (matcher.find()) {
			downImg(matcher.group(), null);
		}
	}

	/**
	 * @Title: searchurl
	 * @Description: ����������url
	 * @param resp
	 * @return
	 * @return Set<String>
	 * @throws
	 * @author XiMing.Fu
	 */
	public Set<String> searchurl(String resp) {
		Set<String> set = new HashSet<String>();
		Matcher matcher = patternurl_.matcher(resp);
		while (matcher.find()) {
			set.add(matcher.group());
		}
		return set;
	}

	public static String getLocalFilepath() {
		return localFilepath;
	}

	public static void setLocalFilepath(String localFilepath) {
		HttpClient.localFilepath = localFilepath;
	}

	public static FileWriter getFw() {
		return fw;
	}

	public static void setFw(FileWriter fw) {
		HttpClient.fw = fw;
	}

	public static Pattern getPatternurl() {
		return patternurl;
	}

	public static void setPatternurl(Pattern patternurl) {
		HttpClient.patternurl = patternurl;
	}

	public static Pattern getPatternimg() {
		return patternimg;
	}

	public static void setPatternimg(Pattern patternimg) {
		HttpClient.patternimg = patternimg;
	}

	public static Pattern getPatternurl_() {
		return patternurl_;
	}

	public static void setPatternurl_(Pattern patternurl_) {
		HttpClient.patternurl_ = patternurl_;
	}

	public static String getFilepath_() {
		return filepath_;
	}

	public static void setFilepath_(String filepath_) {
		HttpClient.filepath_ = filepath_;
	}

	public DefaultHttpClient getClient() {
		return client;
	}

	public void setClient(DefaultHttpClient client) {
		this.client = client;
	}

	public String getHostname() {
		return hostname;
	}

	public void setHostname(String hostname) {
		this.hostname = hostname;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public static void main(String[] args) {
		HttpClient client = new HttpClient("172.16.17.1", 808,
				ConnectionManager.getHttpClient());
		 client.begin("http://image.baidu.com//channel/wallpaper?fm=index&amp;image_id=6840566822#%E9%A3%8E%E6%99%AF&amp;%E5%85%A8%E9%83%A8&amp;8&amp;0");
		// client.begin_("http://auto.qq.com/autophoto/index.htm?pgv_ref=aio2012&ptlang=2052");


	}

}
