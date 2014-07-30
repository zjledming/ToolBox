/**
 * �����Сдת��д
 * 
 * @author ximing.fu
 * 
 */
public class XhgUtil {

	private static final String UNIT = "��ǧ��ʰ��ǧ��ʰ��ǧ��ʰԪ�Ƿ�";

	private static final String DIGIT = "��Ҽ��������½��ƾ�";

	private static final double MAX_VALUE = 9999999999999.99D;

	public static String change(double v) {
		if (v < 0 || v > MAX_VALUE)
			return "�����Ƿ�!";
		long l = Math.round(v * 100);
		if (l == 0)
			return "��Ԫ��";
		String strValue = l + "";
		// i����������
		int i = 0;
		// j�������Ƶ�λ
		int j = UNIT.length() - strValue.length();
		String rs = "";
		boolean isZero = false;
		for (; i < strValue.length(); i++, j++) {
			char ch = strValue.charAt(i);

			if (ch == '0') {
				isZero = true;
				if (UNIT.charAt(j) == '��' || UNIT.charAt(j) == '��'
						|| UNIT.charAt(j) == 'Ԫ') {
					rs = rs + UNIT.charAt(j);
					isZero = false;
				}
			} else {
				if (isZero) {
					rs = rs + "��";
					isZero = false;
				}
				rs = rs + DIGIT.charAt(ch - '0') + UNIT.charAt(j);
			}
		}

		if (!rs.endsWith("��")) {
			rs = rs + "��";
		}
		rs = rs.replaceAll("����", "��");
		return rs;
	}

	public static void main(String[] args) {
		System.out.println(change(Double.parseDouble("901010.20")));

	}
}