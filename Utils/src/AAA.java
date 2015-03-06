
public class AAA {
 public static void main(String[] args) {
	System.out.println("SSXTGXPT_20140816001_2a9f57d8-e9f1-4c59-9ae5-9484dad8fac3".length());
	
	String orgId = "";
	String sql = "select * from TA_SP_INSTANCE_TEMP where 1=1 ";	
	if(!"".equals(orgId.trim())){
		sql += " orgId = '"+orgId.trim()+"'";
	}
	sql += " order by ACCEPT_TIME desc";
}
}

