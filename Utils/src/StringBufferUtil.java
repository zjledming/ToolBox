import org.apache.commons.lang.StringUtils;


public class StringBufferUtil {
	
/**
 * ɾ����ʱ��ע��ո�
 * @param args
 */
public static void main(String[] args) {
	StringBuffer getSQL = new StringBuffer();
	getSQL.append("12345");
	getSQL.deleteCharAt(getSQL.length()-1);
	System.out.println(getSQL.toString());
	getSQL = new StringBuffer(getSQL.substring(0, getSQL.length()-1));
	System.out.println(getSQL.toString());
}

}
