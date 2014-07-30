import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @ClassName: PinyinUtil
 * @Description: ƴ������������
 * @author XiMing.Fu
 * @date 2014-3-11 ����04:42:19
 * 
 */
public class PinyinUtil {

	private static final Log logger = LogFactory.getLog(PinyinUtil.class
			.getName());

	/**
	 * ����תƴ���ķ���
	 * 
	 * @param name
	 *            ����
	 * @return ƴ��
	 */
	public static String HanyuToPinyin(String name) {
		String pinyinName = "";
		char[] nameChar = name.toCharArray();
		HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
		defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
		defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
		for (int i = 0; i < nameChar.length; i++) {
			if (nameChar[i] > 128) {
				try {
					pinyinName += PinyinHelper.toHanyuPinyinStringArray(
							nameChar[i], defaultFormat)[0];
				} catch (Exception e) {
					logger.error(e);
				}
			} else {
				pinyinName += nameChar[i];
			}
		}
		return pinyinName;
	}

	public static void main(String[] args) {
		System.out.println(HanyuToPinyin("��ϸ��"));
	}

}
