package bean;
/**
 *<p>Title:�շ���Ӧ��ϢBean</p>
 *<p>Description:</p>
 *<p>Copyright:Copyright (c) 2012</p>
 *<p>Company:���Ͽƴ�</p>
 *@author ����
 *@version 1.0
 *@date 2012-11-20
 */

public class SocketMsg {
private String msgID;//��ϢID (0-2147483648��2��31�η�)
private String msgClass;//��Ϣ����  ������Ӧ��֪ͨ��1/2/0��
private String cmdType;//��Ϣ���� ��������(1xxx)���������Ĳ���(2xxx)��ִ��ϵͳ����(3xxx)
private String bianMa;//��λ���������
private String tmType;//��������  1��ʵʱ��Ϣ;2-FTP�ļ�
private String wbxtlsh;//�ⲿϵͳ��ˮ��
private String xmbm;//��˰ϵͳ����Ŀ����
private String kpxx;//��Ʊ��Ϣ
private String errCode;//������
private String errString;//��������
private String jksbh;//�ɿ�����
private String dprq;//��˰��Ʊ����
private String dprxm;//��˰��Ʊ������
private String exp1;//��������
private String dwmc;//��λ����
private String applyname;//�ɿ���
private String trnType;
private String cltType;
private String areaCode;
public String getMsgID() {
	return msgID;
}
public void setMsgID(String msgID) {
	this.msgID = msgID;
}
public String getMsgClass() {
	return msgClass;
}
public void setMsgClass(String msgClass) {
	this.msgClass = msgClass;
}
public String getCmdType() {
	return cmdType;
}
public void setCmdType(String cmdType) {
	this.cmdType = cmdType;
}
public String getBianMa() {
	return bianMa;
}
public void setBianMa(String bianMa) {
	this.bianMa = bianMa;
}
public String getTmType() {
	return tmType;
}
public void setTmType(String tmType) {
	this.tmType = tmType;
}
public String getWbxtlsh() {
	return wbxtlsh;
}
public void setWbxtlsh(String wbxtlsh) {
	this.wbxtlsh = wbxtlsh;
}
public String getXmbm() {
	return xmbm;
}
public void setXmbm(String xmbm) {
	this.xmbm = xmbm;
}
public String getKpxx() {
	return kpxx;
}
public void setKpxx(String kpxx) {
	this.kpxx = kpxx;
}
public String getErrCode() {
	return errCode;
}
public void setErrCode(String errCode) {
	this.errCode = errCode;
}
public String getErrString() {
	return errString;
}
public void setErrString(String errString) {
	this.errString = errString;
}
public String getJksbh() {
	return jksbh;
}
public void setJksbh(String jksbh) {
	this.jksbh = jksbh;
}
public String getDprq() {
	return dprq;
}
public void setDprq(String dprq) {
	this.dprq = dprq;
}
public String getDprxm() {
	return dprxm;
}
public void setDprxm(String dprxm) {
	this.dprxm = dprxm;
}
public String getExp1() {
	return exp1;
}
public void setExp1(String exp1) {
	this.exp1 = exp1;
}
public String getDwmc() {
	return dwmc;
}
public void setDwmc(String dwmc) {
	this.dwmc = dwmc;
}
public String getApplyname() {
	return applyname;
}
public void setApplyname(String applyname) {
	this.applyname = applyname;
}
public String getTrnType() {
	return trnType;
}
public void setTrnType(String trnType) {
	this.trnType = trnType;
}
public String getCltType() {
	return cltType;
}
public void setCltType(String cltType) {
	this.cltType = cltType;
}
public String getAreaCode() {
	return areaCode;
}
public void setAreaCode(String areaCode) {
	this.areaCode = areaCode;
}

}
