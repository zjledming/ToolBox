package cn.sax;


/**
 *<p>Title:LinkMendProofDataConfigBean.java</p>
 *<p>Description:��֤�м����ݿ���������ʵ��bean</p>
 *<p>Copyright:Copyright (c) 2013</p>
 *<p>Company:���Ͽƴ�</p>
 *@author dongxu.jiang
 *@version 1.0
 *2013-05-16
 */
public class LinkMendProofDataConfigBean {

	 private String url;//��ַ
	 private String username;// �û��� 
	 private String password;// ���� 
	 private String port;// �˿�
	 private String driverClass;// ���ݿ����� 
	 private String updateChargeSql;//�޸���˰֤��״̬sql
	 private String chargeSql;// �жϵ�˰��˰֤��sql 
	 private String  linkmendproofSql;// ��ȡ ���ܾ�ҵ��ϵͳ��״̬
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPort() {
		return port;
	}
	public void setPort(String port) {
		this.port = port;
	}
	public String getDriverClass() {
		return driverClass;
	}
	public void setDriverClass(String driverClass) {
		this.driverClass = driverClass;
	}
	public String getUpdateChargeSql() {
		return updateChargeSql;
	}
	public void setUpdateChargeSql(String updateChargeSql) {
		this.updateChargeSql = updateChargeSql;
	}
	public String getChargeSql() {
		return chargeSql;
	}
	public void setChargeSql(String chargeSql) {
		this.chargeSql = chargeSql;
	}
	public String getLinkmendproofSql() {
		return linkmendproofSql;
	}
	public void setLinkmendproofSql(String linkmendproofSql) {
		this.linkmendproofSql = linkmendproofSql;
	}
	
	@Override
	public String toString() {
		return "LinkMendProofDataConfigBean [chargeSql=" + chargeSql
				+ ", driverClass=" + driverClass + ", linkmendproofSql="
				+ linkmendproofSql + ", password=" + password + ", port="
				+ port + ", updateChargeSql=" + updateChargeSql + ", url="
				+ url + ", username=" + username + "]";
	}
}
