
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.xml.XMLSerializer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import bean.JKSbean;
import bean.SocketMsg;

public class XmlUtil {

	private static final Log LOG = LogFactory.getLog(XmlUtil.class);

	/**
	 * @Title: xml2List
	 * @Description: xmlתlist
	 * @param xml
	 * @param bean
	 * @return
	 * @return List<Object>
	 * @throws
	 * @author XiMing.Fu
	 */
	public static List xml2List(String xml, Class cla) throws Exception {
		try {
			// ����xml
			xml = xml.replaceAll("></", "> </");
			XMLSerializer xmlSerializer = new XMLSerializer();
			JSON json = xmlSerializer.read(xml);
			return JSONArray.toList(JSONArray.fromObject(json.toString()), cla);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			LOG.error(e.getMessage());
		}
		return null;
	}

	/**
	 * @Title: readFileByLines
	 * @Description: �ļ��ж�ȡ�ı�
	 * @param fileName
	 * @return
	 * @return String
	 * @throws
	 * @author XiMing.Fu
	 */
	public static String readFileByLines(String fileName) throws Exception {
		BufferedReader reader = null;
		StringBuffer sb = new StringBuffer();
		try {
			reader = new BufferedReader(new InputStreamReader(
					new FileInputStream(fileName), "UTF-8"));
			String tempString = null;
			while ((tempString = reader.readLine()) != null) {
				sb.append(StringUtil.deNull(tempString));
			}
			System.out.println(sb);
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
		return sb.toString();
	}

	/**
	 * @Title: list2xml
	 * @Description: listװxml
	 * @param list
	 * @return void
	 * @throws
	 * @author XiMing.Fu
	 */
	public static String list2xml(List<JKSbean> list) throws Exception {
		try {
			// ��ʼ
			StringBuffer buffer = new StringBuffer(
					"<?xml version='1.0' encoding='UTF-8'?><jkss>");
			for (int i = 0; i < list.size(); i++) {
				// �ڵ�
				buffer.append("<jks>");
				JKSbean bean = list.get(i);
				Field[] fields = bean.getClass().getDeclaredFields();
				for (int j = 0; j < fields.length; j++) {
					String fieldName = StringUtil.deNull(fields[j].getName());
					String fieldValue = StringUtil.deNull((String) fields[j]
							.get(bean));
					buffer.append("<" + fieldName + ">");
					buffer.append(fieldValue);
					buffer.append("</" + fieldName + ">");
				}
				buffer.append("</jks>");
			}
			// ����
			buffer.append("</jkss>");
			System.out.println(buffer.toString());
			return buffer.toString();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			System.out.println("listתxml�쳣��" + e.getMessage());
			return "";
		}
	}

	public static void main(String[] args) throws Exception {
		List list = xml2List(
				readFileByLines("C://Users//android//Desktop//����ʡ���������������Ӽ��ϵͳ//����//1203//��˰�Խ��ĵ�//jks.xml"),
				JKSbean.class);
		// list = (List<NontaxOrganization>)list;
		//
		// List list = XmlUtil.xml2List(bilXml,JKSbean.class);
		list = (List<JKSbean>) list;

		System.out.println(list.size());
	}
	
	/**
	 * ͨ��������ƽ�XML��ʽ���ַ�����ֵ������
	 * 
	 * @param xml
	 * @param bean
	 * @return
	 */
	public static void fromXmlToBean(String xml, Object bean) {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder;
		Document doc;
		try {
			builder = factory.newDocumentBuilder();
			try {
				doc = builder.parse(new InputSource(new StringReader(xml)));
				Element root = doc.getDocumentElement();
				String fieldname;
				// �ӽڵ�
				NodeList childList = root.getChildNodes();
				for (int i = 0; i < childList.getLength(); i++) {
					Node childNode = childList.item(i);
					// �ж�ȡ����ֵ�Ƿ�����ElementԪ��,Ŀ���ǹ��˵�
					if (childNode instanceof Element) {
						// �õ��ӽڵ������
						String nodename = childNode.getNodeName();
						String value = childNode.getTextContent();
						fieldname = nodename.substring(0, 1).toLowerCase()
								+ nodename.substring(1);
						XmlUtil.setField(bean, fieldname, value);
					}
				}
			} catch (SAXException e) {
				LOG.error(e);
			} catch (IOException e) {
				LOG.error(e);
			}
		} catch (ParserConfigurationException e) {
			LOG.error(e);
		}
	}

	public static void setField(Object bean, String fieldname, String value) {
		Class c = bean.getClass();
		Field field;
		try {
			field = c.getDeclaredField(fieldname);
			field.setAccessible(true);
			try {
				field.set(bean, value);
			} catch (IllegalArgumentException e) {
				LOG.error(e);
			} catch (IllegalAccessException e) {
				LOG.error(e);
			}
		} catch (SecurityException e) {
			LOG.error(e);
		} catch (NoSuchFieldException e) {
			LOG.error(e);
		}

	}

	public static void main1(String[] args) {
		String xml = "<MSG><MsgID>4</MsgID><MsgClass>1</MsgClass><CmdType>1001</CmdType></MSG>   ";
		SocketMsg bean = new SocketMsg();
		XmlUtil.fromXmlToBean(xml, bean);
		System.out.println("MsgID:" + bean.getMsgID());
		System.out.println("MsgClass:" + bean.getMsgClass());
		System.out.println("CmdType:" + bean.getCmdType());
		System.out.println("BianMa:" + bean.getBianMa());
		System.out.println("Wbxtlsh:" + bean.getWbxtlsh());
		System.out.println("Xmbm:" + bean.getXmbm());
		System.out.println("Jksbh:" + bean.getJksbh());
		System.out.println("Dprq:" + bean.getDprq());
		System.out.println("Dprxm:" + bean.getDprxm());
		SimpleDateFormat sf = new SimpleDateFormat("YYYYMMDDHHmmss");
		System.out.println(sf.format(new Date()));
	}
	
	
	
	
}
