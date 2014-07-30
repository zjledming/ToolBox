package cn.sax;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class Constant {

	private static final Log LOG = LogFactory.getLog(Constant.class);

	public static List<ConfigBean> configList = new ArrayList<ConfigBean>(); // ������Ϣ

	// public static Map<String, String> configMap = new HashMap<String,
	// String>(); // ������Ϣ

	/**
	 * @Title: parseXml
	 * @Description: �������ã���������ֵ
	 * @return void
	 * @throws
	 * @author XiMing.Fu
	 */
	public static void parseXml() {
		File f = new File(RelatedPath.getWebAppPath(Constant.class)
				+ "WEB-INF\\classes\\nontax-config.xml");
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc;
			try {
				doc = builder.parse(f);
				// String switch_ = doc
				// .getElementsByTagName("switch").item(0)
				// .getFirstChild().getNodeValue();
				// Constant.NONTAX_SWITCH =
				// StringUtil.isBlank(switch_)?"0":StringUtil.deNull(switch_);

				// configMap.clear();
				// NodeList notes = doc.getElementsByTagName("note");
				// for (int i = 0; i < notes.getLength(); i++) {
				// Element element = (Element)notes.item(i);
				// if (!StringUtil.isBlank(getNodeVal(element,"areacode"))) {
				// configMap.put(getNodeVal(element,"areacode"),
				// getNodeVal(element,"webservice"));
				// }
				// }

				// ��������֮ǰ,���������Ϣ
				configList.clear();
				NodeList notes = doc.getElementsByTagName("note");
				for (int i = 0; i < notes.getLength(); i++) {
					ConfigBean bean = new ConfigBean();
					Element element = (Element) notes.item(i);
					bean.setAreacode(getNodeVal(element, "areacode"));
					bean.setSwitchFlag(getNodeVal(element, "switch"));
					bean.setWebservice(getNodeVal(element, "webservice"));
					bean.setPjrk(getNodeVal(element, "pjrk"));
					bean.setJksxjdy(getNodeVal(element, "jksxjdy"));
					bean.setJksskdy(getNodeVal(element, "jksskdy"));
					bean.setPjsy(getNodeVal(element, "pjsy"));
					bean.setSrrb(getNodeVal(element, "srrb"));
					bean.setDddl(getNodeVal(element, "dddl"));
					bean.setWebnamespace(getNodeVal(element, "webnamespace"));
					bean.setBaseurl(getNodeVal(element, "baseurl"));
					bean.setPosurl(getNodeVal(element, "posurl"));
					bean.setJzjf(getNodeVal(element, "jzjf"));
					bean.setDztcx(getNodeVal(element, "dztcx"));
					configList.add(bean);
				}
			} catch (SAXException e) {
				LOG.error(e.getMessage());
				e.printStackTrace();
			} catch (IOException e) {
				LOG.error(e.getMessage());
				e.printStackTrace();
			}
		} catch (ParserConfigurationException e) {
			LOG.error(e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * @Title: getNodeVal
	 * @Description: ȡelement�ڵ���tagname��ֵ
	 * @param element
	 * @param tagname
	 * @return
	 * @return String
	 * @throws
	 * @author XiMing.Fu
	 */
	private static String getNodeVal(Element element, String tagname) {
		try {
			NodeList list = element.getElementsByTagName(tagname);
			if (list.getLength() > 0
					&& list.item(0).getChildNodes().getLength() > 0) {
				return StringUtil.deNull(list.item(0).getFirstChild()
						.getNodeValue());
			}
		} catch (Exception e) {
			// TODO: handle exception
			LOG.error(e.getMessage());
			e.printStackTrace();
			return "";
		}
		return "";
	}

	/**
	 * @Title: check
	 * @Description: �����Ƿ����㹦��������ͬʱ���㣺 1.���ش� 2.��������
	 * @param areaCode
	 * @return
	 * @return boolean
	 * @throws
	 * @author XiMing.Fu
	 */
	public static boolean check(String areaCode) {
		try {
			areaCode = StringUtil.deNull(areaCode);
			for (int i = 0; i < configList.size(); i++) {
				// 1.���ش� && 2.��������
				if (configList.get(i).getSwitchFlag().equals("1")
						&& configList.get(i).getAreacode().equals(areaCode)) {
					return true;
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * @Title: getConfigByAreacode
	 * @Description: ����areacodeȡ��˰������Ϣ
	 * @param areaCode
	 * @return
	 * @return ConfigBean
	 * @throws
	 * @author XiMing.Fu
	 */
	public static ConfigBean getConfigByAreacode(String areaCode) {
		ConfigBean bean = new ConfigBean();
		try {
			for (int i = 0; i < configList.size(); i++) {
				if (configList.get(i).getAreacode().equals(areaCode)) {
					bean = configList.get(i);
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return bean;
	}

}
