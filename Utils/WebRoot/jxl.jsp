<%@page import="cn.jxl.Jxlutil"%>
<%@page import="java.io.OutputStream"%>
<%@ page language="java" import="java.util.*" pageEncoding="gbk"%>
<%@ page import="java.util.Vector"%>
<%
	/*
	 *����jsp container�ڴ���������������releasePageContet�����ͷ����õ�PageContext object,
	     ����ͬʱ����getWriter����,
	     ����getWriter��������jspҳ����ʹ������ص�getOutputStream������ͻ,���Ի���������쳣,
	     ����취��:
	    	 ֻ��Ҫ��jspҳ����������������:  
	    		 out.clear();
				 out=pageContext.pushBody();����(����out,pageContext��Ϊjsp���ö���!)
	 */
	out.clear();
	out = pageContext.pushBody();

	response.setContentType("application/ms-excel");
	response.setHeader("Content-Disposition", "attachment;Filename="
			+ new String("����excel.xls".getBytes("gb2312"), "iso8859-1"));
	OutputStream os = null;

	// ����
	Vector<Vector<String>> v = new Vector();
	Vector v01 = new Vector();
	v01.add("�Ї���");
	v01.add("������");
	v01.add("؈");
	v01.add("�t");
	v01.add("�G");
	v01.add("�G");
	v.add(v01);
	Vector v02 = new Vector();
	v02.add("aaaaa");
	v02.add("bbbbb");
	v02.add("cccc");
	v02.add("");
	v02.add("eeeee");
	v02.add("fffff");
	v.add(v02);

	Vector v03 = new Vector();
	v03.add("aaaaa01");
	v03.add("");
	v03.add("cccc");
	v03.add("dddd");
	v03.add("eeeee");
	v03.add("fffff");
	v.add(v03);
	Vector v04 = new Vector();
	v04.add("");
	v04.add("bbbbb");
	v04.add("cccc");
	v04.add("dddd");
	v04.add("eeeee");
	v04.add("fffff");
	v.add(v04);
	Vector v05 = new Vector();
	v05.add("aaaaa");
	v05.add("bbbbb01");
	v05.add("cccc");
	v05.add("dddd");
	v05.add("eeeee");
	v05.add("fffff");
	v.add(v05);
	Vector v06 = new Vector();
	v06.add("aaaaa");
	v06.add("bbbbb");
	v06.add("cccc");
	v06.add("dddd01");
	v06.add("eeeee");
	v06.add("fffff");
	v.add(v06);

	try {
		os = response.getOutputStream(); //���������  
		Jxlutil.export(os, v, null, null);
		os.flush();
		response.reset();
	} catch (Exception e) {
		if (os != null) {
			try {
				os.close();
			} catch (Exception ee) {

			}
		}
	} finally {
		if (os != null) {
			try {
				os.close();
			} catch (Exception ee) {

			}
		}
	}
%>