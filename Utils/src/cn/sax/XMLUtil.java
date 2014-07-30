package cn.sax;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class XMLUtil {
	private static final Log LOG = LogFactory.getLog(XMLUtil.class);
	public static LinkMendProofDataConfigBean bean = null;
	public static List<ItemIndividualPageCfgBean> list = null;

	/**
	 * @Title: initObj
	 * @Description: file ������> ����Document����
	 * @return
	 * @throws Exception
	 * @return Document
	 * @throws
	 * @author XiMing.Fu
	 */
	private static Document initObj() throws Exception {
		File f = new File(RelatedPath.getWebAppPath(XMLUtil.class)
				+ "\\linkmendproof-config.xml");
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		Document doc = null;
		try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			doc = builder.parse(f);
		} catch (Exception e) {
			LOG.error("��ʼ������ʧ��" + e);
			throw e;
		}
		return doc;
	}

	/**
	 * @Description ��ʼ�����ܾ�ϵͳ�ṩ���м�����
	 * @return void
	 */
	public static void initLinkmendproofServer() {
		bean = new LinkMendProofDataConfigBean();
		Document doc;
		try {
			doc = initObj();
			setField(
					bean,
					"url",
					StringUtil.deNull(doc.getElementsByTagName("url").item(0)
							.getFirstChild().getNodeValue()));
			setField(
					bean,
					"username",
					StringUtil.deNull(doc.getElementsByTagName("username")
							.item(0).getFirstChild().getNodeValue()));
			setField(
					bean,
					"password",
					StringUtil.deNull(doc.getElementsByTagName("password")
							.item(0).getFirstChild().getNodeValue()));
			setField(
					bean,
					"port",
					StringUtil.deNull(doc.getElementsByTagName("port").item(0)
							.getFirstChild().getNodeValue()));
			setField(
					bean,
					"driverClass",
					StringUtil.deNull(doc.getElementsByTagName("driverClass")
							.item(0).getFirstChild().getNodeValue()));
			NamedNodeMap chargeSqlMap = doc.getElementsByTagName("charge-sql")
					.item(0).getAttributes();
			setField(bean, "chargeSql", StringUtil.deNull(chargeSqlMap
					.getNamedItem("value").getNodeValue()));
			NamedNodeMap linkmendproofSqlMap = doc
					.getElementsByTagName("linkmendproof-sql").item(0)
					.getAttributes();
			setField(bean, "linkmendproofSql",
					StringUtil.deNull(linkmendproofSqlMap.getNamedItem("value")
							.getNodeValue()));
			NamedNodeMap updatechargeSqlMap = doc
					.getElementsByTagName("updatecharge-sql").item(0)
					.getAttributes();
			setField(bean, "updateChargeSql",
					StringUtil.deNull(updatechargeSqlMap.getNamedItem("value")
							.getNodeValue()));
		} catch (Exception e) {
			LOG.error(e.getMessage());
		}
	}

	/**
	 * @Description ��ʼ����˰����͸���ҳ������
	 * @return void
	 */
	public static void initLinkmendproofConfig() {
		list = new ArrayList<ItemIndividualPageCfgBean>();
		Document doc;
		try {
			doc = initObj();
			NodeList members = doc.getElementsByTagName("page-config");
			NodeList childNodes = members.item(0).getChildNodes();
			for (int i = 0; i < childNodes.getLength(); i++) {
				ItemIndividualPageCfgBean cfgBean = new ItemIndividualPageCfgBean();
				if (childNodes.item(i).getNodeType() == Node.ELEMENT_NODE) {
					if ("ecid".equals(childNodes.item(i).getNodeName())) {
						NamedNodeMap map = childNodes.item(i).getAttributes();
						cfgBean.setPage_path(StringUtil.deNull(map
								.getNamedItem("page").getNodeValue()));
						cfgBean.setEc_id(StringUtil.deNull(childNodes.item(i)
								.getFirstChild().getNodeValue()));
						list.add(cfgBean);
					}
				}
			}
		} catch (Exception e) {
			LOG.error(e.getMessage());
		}
	}

	/**
	 * @Title: setField
	 * @Description: ��bean������ֶ�fieldname����ֵvalue
	 * @param bean
	 * @param fieldname
	 * @param value
	 * @return void
	 * @throws
	 * @author XiMing.Fu
	 */
	public static void setField(Object bean, String fieldname, String value) {
		Class c = bean.getClass();
		Field field;
		try {
			// ȡ�ֶΣ�����һ�� Field ���󣬸ö���ӳ�� Class ��������ʾ�����ӿڵ�ָ���������ֶΡ�
			field = c.getDeclaredField(fieldname);
			// ���÷���Ȩ�ޣ����˶���� accessible ��־����Ϊָʾ�Ĳ���ֵ��ֵΪ true ��ָʾ����Ķ�����ʹ��ʱӦ��ȡ�� Java
			// ���Է��ʼ�顣ֵΪ false ��ָʾ����Ķ���Ӧ��ʵʩ Java ���Է��ʼ�顣
			field.setAccessible(true);
			try {
				// ����ǰ������ֶ�field������ֵ����ָ����������ϴ� Field �����ʾ���ֶ�����Ϊָ������ֵ��
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

	/**
	 * ��̬��set����
	 * 
	 * @param paramMap
	 * @param preparedStatement
	 * @param xmlTags
	 */
	public static void setPrepareValue(Map<String, Object> paramMap,
			PreparedStatement preparedStatement, String xmlTags) {
		try {
			List<Map<String, String>> strList = getValueStr(xmlTags);
			String filedname = "";
			for (int i = 0; i < strList.size(); i++) {
				filedname = strList.get(i).get("filedname");
				preparedStatement.setString(i + 1,
						(String) paramMap.get(filedname));
			}
		} catch (Exception e) {
			LOG.error(e);
		}
	}

	/**
	 * ��������
	 * 
	 * @param xmlTags
	 * @return
	 */
	private static List<Map<String, String>> getValueStr(String xmlTags) {
		List<Map<String, String>> strList = new ArrayList<Map<String, String>>();
		Document doc;
		try {
			doc = initObj();
			NodeList members = doc.getElementsByTagName(xmlTags);
			NodeList childNodes = members.item(0).getChildNodes();
			for (int i = 0; i < childNodes.getLength(); i++) {
				if (childNodes.item(i).getNodeType() == Node.ELEMENT_NODE) {
					Map<String, String> strMap = new HashMap<String, String>();
					NamedNodeMap map = childNodes.item(i).getAttributes();
					strMap.put("type", map.getNamedItem("type").getNodeValue());
					strMap.put("method", map.getNamedItem("method")
							.getNodeValue());
					strMap.put("filedname", map.getNamedItem("filedname")
							.getNodeValue());
					strList.add(strMap);
				}
			}
		} catch (Exception e) {
			LOG.error(e);
		}
		return strList;
	}

	public static Map<String, String> getZWZXAccessControl() {
		Map<String, String> map = new HashMap<String, String>();
		String path = RelatedPath.getWebAppPath(XMLUtil.class);
		File f = new File(path + "WEB-INF/zwzxaccesscontrol.xml");
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder;
		Document doc;
		try {
			builder = factory.newDocumentBuilder();
			try {
				doc = builder.parse(f);
				NodeList members = doc.getElementsByTagName("url");
				for (int i = 0; i < members.getLength(); i++) {
					NamedNodeMap text = members.item(i).getAttributes();
					map.put(text.item(0).getNodeValue(), text.item(1)
							.getNodeValue());
					System.out.println(text.item(0).getNodeValue() + ":"
							+ text.item(1).getNodeValue());
				}
				return map;
			} catch (SAXException e) {
				LOG.error(e);
				return map;
			} catch (IOException e) {
				LOG.error(e);
				return map;
			}
		} catch (ParserConfigurationException e) {
			LOG.error(e);
			return map;
		}

	}
}