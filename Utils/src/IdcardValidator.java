import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

@SuppressWarnings({ "unchecked", "unused", "all" })
public class IdcardValidator {

	protected String codeAndCity[][] = { { "11", "����" }, { "12", "���" },
			{ "13", "�ӱ�" }, { "14", "ɽ��" }, { "15", "���ɹ�" }, { "21", "����" },
			{ "22", "����" }, { "23", "������" }, { "31", "�Ϻ�" }, { "32", "����" },
			{ "33", "�㽭" }, { "34", "����" }, { "35", "����" }, { "36", "����" },
			{ "37", "ɽ��" }, { "41", "����" }, { "42", "����" }, { "43", "����" },
			{ "44", "�㶫" }, { "45", "����" }, { "46", "����" }, { "50", "����" },
			{ "51", "�Ĵ�" }, { "52", "����" }, { "53", "����" }, { "54", "����" },
			{ "61", "����" }, { "62", "����" }, { "63", "�ຣ" }, { "64", "����" },
			{ "65", "�½�" }, { "71", "̨��" }, { "81", "���" }, { "82", "����" },
			{ "91", "����" } };

	private String cityCode[] = { "11", "12", "13", "14", "15", "21", "22",
			"23", "31", "32", "33", "34", "35", "36", "37", "41", "42", "43",
			"44", "45", "46", "50", "51", "52", "53", "54", "61", "62", "63",
			"64", "65", "71", "81", "82", "91" };

	// ÿλ��Ȩ����
	private int power[] = { 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2 };

	// ��18λУ����
	private String verifyCode[] = { "1", "0", "X", "9", "8", "7", "6", "5",
			"4", "3", "2" };

	public boolean isValidatedAllIdcard(String idcard) {
		if (idcard.length() == 15) {
			idcard = this.convertIdcarBy15bit(idcard);
		}
		return this.isValidate18Idcard(idcard);
	}

	public boolean isValidate18Idcard(String idcard) {
		if (idcard == null) {
			return false;
		}
		// ��18λΪ��
		if (idcard.length() != 18) {
			return false;
		}
		// ��ȡǰ17λ
		String idcard17 = idcard.substring(0, 17);
		// ��ȡ��18λ
		String idcard18Code = idcard.substring(17, 18);
		char c[] = null;
		String checkCode = "";
		// �Ƿ�Ϊ����
		if (isDigital(idcard17)) {
			c = idcard17.toCharArray();
		} else {
			return false;
		}

		if (null != c) {
			int bit[] = new int[idcard17.length()];

			bit = converCharToInt(c);

			int sum17 = 0;

			sum17 = getPowerSum(bit);

			// ����ֵ��11ȡģ�õ���������У�����ж�
			checkCode = getCheckCodeBySum(sum17);
			if (null == checkCode) {
				return false;
			}
			// �����֤�ĵ�18λ���������У�����ƥ�䣬����Ⱦ�Ϊ��
			if (!idcard18Code.equalsIgnoreCase(checkCode)) {
				return false;
			}
		}
		return true;
	}

	public boolean isValidate15Idcard(String idcard) {
		// ��15λΪ��
		if (idcard.length() != 15) {
			return false;
		}

		// �Ƿ�ȫ��Ϊ����
		if (isDigital(idcard)) {
			String provinceid = idcard.substring(0, 2);
			String birthday = idcard.substring(6, 12);
			int year = Integer.parseInt(idcard.substring(6, 8));
			int month = Integer.parseInt(idcard.substring(8, 10));
			int day = Integer.parseInt(idcard.substring(10, 12));

			// �ж��Ƿ�Ϊ�Ϸ���ʡ��
			boolean flag = false;
			for (String id : cityCode) {
				if (id.equals(provinceid)) {
					flag = true;
					break;
				}
			}
			if (!flag) {
				return false;
			}
			// �����֤���������ڵ�ǰ����֮��ʱΪ��
			Date birthdate = null;
			try {
				birthdate = new SimpleDateFormat("yyMMdd").parse(birthday);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			if (birthdate == null || new Date().before(birthdate)) {
				return false;
			}

			// �ж��Ƿ�Ϊ�Ϸ������
			GregorianCalendar curDay = new GregorianCalendar();
			int curYear = curDay.get(Calendar.YEAR);
			int year2bit = Integer.parseInt(String.valueOf(curYear)
					.substring(2));

			// �жϸ���ݵ���λ��ʾ����С��50�ĺʹ��ڵ�ǰ��ݵģ�Ϊ��
			if ((year < 50 && year > year2bit)) {
				return false;
			}

			// �ж��Ƿ�Ϊ�Ϸ����·�
			if (month < 1 || month > 12) {
				return false;
			}

			// �ж��Ƿ�Ϊ�Ϸ�������
			boolean mflag = false;
			curDay.setTime(birthdate); // �������֤�ĳ������ڸ��ڶ���curDay
			switch (month) {
			case 1:
			case 3:
			case 5:
			case 7:
			case 8:
			case 10:
			case 12:
				mflag = (day >= 1 && day <= 31);
				break;
			case 2: // ������2�·�������28��,�����2����29�졣
				if (curDay.isLeapYear(curDay.get(Calendar.YEAR))) {
					mflag = (day >= 1 && day <= 29);
				} else {
					mflag = (day >= 1 && day <= 28);
				}
				break;
			case 4:
			case 6:
			case 9:
			case 11:
				mflag = (day >= 1 && day <= 30);
				break;
			}
			if (!mflag) {
				return false;
			}
		} else {
			return false;
		}
		return true;
	}

	public String convertIdcarBy15bit(String idcard) {
		String idcard17 = null;
		// ��15λ���֤
		if (idcard.length() != 15) {
			return null;
		}

		if (isDigital(idcard)) {
			// ��ȡ����������
			String birthday = idcard.substring(6, 12);
			Date birthdate = null;
			try {
				birthdate = new SimpleDateFormat("yyMMdd").parse(birthday);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			Calendar cday = Calendar.getInstance();
			cday.setTime(birthdate);
			String year = String.valueOf(cday.get(Calendar.YEAR));

			idcard17 = idcard.substring(0, 6) + year + idcard.substring(8);

			char c[] = idcard17.toCharArray();
			String checkCode = "";

			if (null != c) {
				int bit[] = new int[idcard17.length()];

				// ���ַ�����תΪ��������
				bit = converCharToInt(c);
				int sum17 = 0;
				sum17 = getPowerSum(bit);

				// ��ȡ��ֵ��11ȡģ�õ���������У����
				checkCode = getCheckCodeBySum(sum17);
				// ��ȡ����У��λ
				if (null == checkCode) {
					return null;
				}

				// ��ǰ17λ���18λУ����ƴ��
				idcard17 += checkCode;
			}
		} else { // ���֤��������
			return null;
		}
		return idcard17;
	}

	public boolean isIdcard(String idcard) {
		return idcard == null || "".equals(idcard) ? false : Pattern.matches(
				"(^\\d{15}$)|(\\d{17}(?:\\d|x|X)$)", idcard);
	}

	public boolean is15Idcard(String idcard) {
		return idcard == null || "".equals(idcard) ? false : Pattern.matches(
				"^[1-9]\\d{7}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}$",
				idcard);
	}

	public boolean is18Idcard(String idcard) {
		return Pattern
				.matches(
						"^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}([\\d|x|X]{1})$",
						idcard);
	}

	public boolean isDigital(String str) {
		return str == null || "".equals(str) ? false : str.matches("^[0-9]*$");
	}

	public int getPowerSum(int[] bit) {

		int sum = 0;

		if (power.length != bit.length) {
			return sum;
		}

		for (int i = 0; i < bit.length; i++) {
			for (int j = 0; j < power.length; j++) {
				if (i == j) {
					sum = sum + bit[i] * power[j];
				}
			}
		}
		return sum;
	}

	public String getCheckCodeBySum(int sum17) {
		String checkCode = null;
		switch (sum17 % 11) {
		case 10:
			checkCode = "2";
			break;
		case 9:
			checkCode = "3";
			break;
		case 8:
			checkCode = "4";
			break;
		case 7:
			checkCode = "5";
			break;
		case 6:
			checkCode = "6";
			break;
		case 5:
			checkCode = "7";
			break;
		case 4:
			checkCode = "8";
			break;
		case 3:
			checkCode = "9";
			break;
		case 2:
			checkCode = "x";
			break;
		case 1:
			checkCode = "0";
			break;
		case 0:
			checkCode = "1";
			break;
		}
		return checkCode;
	}

	public int[] converCharToInt(char[] c) throws NumberFormatException {
		int[] a = new int[c.length];
		int k = 0;
		for (char temp : c) {
			a[k++] = Integer.parseInt(String.valueOf(temp));
		}
		return a;
	}

	public static void main(String[] args) throws Exception {
		String idcard15 = "43110319860910243X";
		String idcard18 = "43110319860910243X";
		IdcardValidator iv = new IdcardValidator();
		boolean flag = false;
		flag = iv.isValidate18Idcard(idcard18);
		System.out.println(flag);

		flag = iv.isValidate15Idcard(idcard15);
		System.out.println(flag);

		System.out.println(iv.convertIdcarBy15bit(idcard15));
		flag = iv.isValidate18Idcard(iv.convertIdcarBy15bit(idcard15));
		System.out.println(flag);

		System.out.println(iv.isValidatedAllIdcard(idcard18));

	}
}