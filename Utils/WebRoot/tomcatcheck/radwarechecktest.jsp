<%@page import="java.util.Date"%>
<%@page import="com.chinacreator.v2.xzsp.util.monitoring.Constant"%>
<%@page contentType="text/html;charset=GBK"%>

<%
	//String radchecktest = "checksussess";
	/*
	* �������ʶ��success������    | error���쳣
	*/
	String success = "checksussess";
	String error = "checkerror";

	// ���ҵ��
	try {
		// ��������ʶΪfalse����Զ���ʧ�ܽ�� 
		if(!Constant.MONITORING_RESULT){
			out.println(error);
		}else{
			if (Constant.MONITORING_TIME_LIST.size()>0) {
				// ���˼·������ֱ��remove i ����Ϊ�������ִ�еĹ����У��������µļ�ص㱻��ӽ�����Constant.MONITORING_TIME_LIST.size()�ڲ��ϱ�����remove i�Ļ���...
				int j = 0;
				for (int i = 0; i < Constant.MONITORING_TIME_LIST.size(); i++) {
					long nowTime = new Date().getTime();
					long temp = Constant.MONITORING_TIME_LIST.get(j);
					/*
					 * ���������е������쳣�����㣺
					 * 	1����������趨ʱ����֤�����쳣������ʱ��֮�⣬�Ƴ�����
					 * 	2�����С�ڵ����趨ʱ����ͳ���쳣����
					 */
					if (Math.abs(nowTime - temp) > Constant.MONITORING_TIME_LONG) {
						Constant.MONITORING_TIME_LIST.remove(temp);
						j--;
					}
					j++;
				}
				// ����쳣�����ڵ��ڼ������쳣������ֵ��ؽ��Ϊfalse
				if (Constant.MONITORING_TIME_LIST.size() >= Constant.MONITORING_MAX_EXCEPTION) {
					Constant.MONITORING_RESULT = false;
					out.println(error);
				}else{
					out.println(success);
				}
			}else{
				out.println(success);
			}
		}
	} catch (Exception e) {
		// TODO: handle exception
		e.printStackTrace();
	}
%>