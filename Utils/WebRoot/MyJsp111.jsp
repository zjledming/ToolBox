<%@ page contentType="text/html; charset=gb2312" language="java" import="java.sql.*" errorPage="" %>
<%@ taglib   uri="/WEB-INF/pager-taglib.tld" prefix="pg"%>
<%@ page import="com.frameworkset.common.poolman.DBUtil" %>
<%@ page import="com.frameworkset.common.poolman.PreparedDBUtil" %>
<%@ page import="java.util.*"%>
<% 

//String parentid=request.getParameter("parentid");
String windowid=request.getParameter("windowid");
//String sql = "select org_name from td_sm_organization where org_id = '" + windowid + "'";
String sql = "select org_name from td_sm_organization where org_id = ?";
//String catalogname=request.getParameter("catalogname");
//DBUtil dbutil=new DBUtil();
PreparedDBUtil pdb=new PreparedDBUtil();
String windowname = "";
//dbutil.executeSelect("spweb",sql);
System.out.println("[Ԥ���봦��ʼ]����:"+windowid);
try {
pdb.preparedSelect("spweb", sql);
pdb.setString(1, windowid);
pdb.executePrepared();
 //if(dbutil!=null && dbutil.size()>0){
	 if(pdb!=null && pdb.size()>0){
              	    //windowname=dbutil.getString(0,"org_name");
		 windowname=pdb.getString(0,"org_name");
              	 }   
 } catch (Exception e) {
		System.out.println("��ע�⣬�����쳣��"+e.getMessage());
	}
System.out.println("[Ԥ���봦��ʼ]���:"+windowname); 
String pre2_windowname=windowname;
String pre2_windowid=windowid;

//background:url(images/back_007.gif)backall.jpg
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>[�й���ɳ]-���ϰ���</title>
<link href="css/css.css" rel="stylesheet" type="text/css" />
<style type="text/css">
<!--
.style3 {font-size: 18px}
.STYLE5 {color: #000000}
-->
</style>
</head>
<body style="margin:0px; font-size:12px; color:414141; line-height:21px; background:url(images/back_007.gif)">
<table width="780" height="520"border="0" align="center" cellpadding="0" cellspacing="0" bgcolor="#ffffff">
  <tr>
  	<td height="520" align="center">
  <table width="780" border="0" align="center" cellpadding="0" cellspacing="0" bgcolor="#FFFFFF">
  <tr>
    <td><iframe  align="middle" src='index_top.htm' width=780 height=158 scrolling=no marginwidth=0 marginheight=0 frameborder=0 vspace=0 hspace=0 ></iframe></td>
  </tr>
  <tr>
    <td><table width="780" border="0" align="center" cellpadding="0" cellspacing="0" background="images/Current_Position_bg1.gif" class="Current_Position_box">
      <tr>
        <td width="30" align="center"><img src="images/arrow.gif" width="10" height="10" vspace="0"></td>
        <td width="820" style="color:#000000">��ǰλ�ã�<a href="http://www.changsha.gov.cn/wsbs/" style="color:#000000">��ҳ</a>  > <%=windowname%></td>
      </tr>
    </table></td>
  </tr>
</table><table width="780" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td><table  width="100%" height="400" border="0" cellpadding="0" cellspacing="0" >
    <tr>
      
      <td valign="top"><table  width="780" border="0" align="right" cellpadding="0" cellspacing="0" >
        <tr>
          <td valign="top"><table width="100%" align="center" style=" background:url(images/bs3_05.gif);border-collapse:collapse; border-style:solid;border:1px #d5d5d5 solid;"   border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td width="42" align="center"><img src="images/bs3_03.gif" width="19" height="25" /></td>
                      <td style="font-size:14px; font-weight:bold; color:#12437B"> �������ϰ��������б�</td>
                    </tr>
                </table></td>
              </tr>
              <tr>
                <td height="3" background="images/bs3_07.gif"></td>
              </tr>
            </table>
              <table width="98%"  align="center" border="0" cellspacing="0" cellpadding="0">
                <tr>
                  <td height="5"  bgcolor=""></td>
                </tr>
              </table>
            <table style="border-collapse:collapse; padding-top:5px; border-style:solid;border:1px #d5d5d5 solid;"   width="100%" border="0" cellspacing="0" cellpadding="0" align="center">
                <pg:listdata dataInfo="com.chinacreator.xzsp.web.wsbs.BaseInfoList" keyName="BaseListInfo" />
                <pg:pager  maxPageItems="10" scope="request" data="BaseListInfo" isList="false">
                  <pg:param name="windowid" />
                  <pg:param name="windowname" />
                  <pg:notify>
                    <tr>
                      <td>��ʱû�������Ϣ</td>
                    </tr>
                  </pg:notify>
                  <pg:list>
                    <%
						String name=dataSet.getString("name");
						String id=dataSet.getString("id");
						String sp_out_flag = dataSet.getString("sp_out_flag");
				%>                   					 
					  <tr>
                      <td height="30" bgcolor="#E6EBFE" align="left">&nbsp;<img src="images/lamp.gif" />
					  <a href='wsbs_content.jsp?pre2_windowid=<%=pre2_windowid%>&amp;pre2_windowname=<%=pre2_windowname%>&amp;id=<%=id%>&amp;name=<%=name%>'></a>
                      <b><pg:cell colName="name" defaultValue=""/></b>                    					  
					  </td></tr>
					  <tr><td height="30">
					   &nbsp;&nbsp;&nbsp;&nbsp;<a href='wsbs_content.jsp?bz=0&pre2_windowid=<%=pre2_windowid%>&amp;pre2_windowname=<%=pre2_windowname%>&amp;id=<%=id%>&amp;name=<%=name%>&spout=<%=sp_out_flag %>'>����ָ��</a>&nbsp;<img src="images/flaw.gif"> 
					   
					     <a href='wsbs_content.jsp?bz=1&pre2_windowid=<%=pre2_windowid%>&amp;pre2_windowname=<%=pre2_windowname%>&amp;id=<%=id%>&amp;name=<%=name%>&spout=<%=sp_out_flag %>'>��ز���</a>&nbsp;<img src="images/flaw.gif"> 
					     <a href='wsbs_content.jsp?bz=2&pre2_windowid=<%=pre2_windowid%>&amp;pre2_windowname=<%=pre2_windowname%>&amp;id=<%=id%>&amp;name=<%=name%>&spout=<%=sp_out_flag %>'>������ѯ</a> 
					      <%if("N".equals(sp_out_flag)){%>
					      <%--&nbsp;<img src="images/flaw.gif"><a href="javascript:void(null)" onclick='alert("����Ŀ��ʱ���ṩ����Ԥ����,��ֱ�ӵ����´�������");'><font color="red">���߰���</font></a>--%>
					      <%}else{%>
					      &nbsp;<img src="images/flaw.gif"><a href='wsbs_content.jsp?bz=3&pre2_windowid=<%=pre2_windowid%>&amp;pre2_windowname=<%=pre2_windowname%>&amp;id=<%=id%>&amp;name=<%=name%>'>���߰���</a>
					      <%}%>
					  </td>
                    </tr>                   
                  </pg:list>
                  <tr>
                    <td align="center"> ��
                      <pg:rowcount />
                      ����¼
                      <pg:index />
                    </td>
                  </tr>
                </pg:pager>
              </table>
</td>
        </tr>
      </table></td>
    </tr>
  </table></td>
  </tr>
  <tr>
    <td><iframe  align="middle" src='index_bottom.htm' width=100% height=80 scrolling=no marginwidth=0 marginheight=0 frameborder=0 vspace=0 hspace=0 ></iframe></td>
  </tr>
</table>
  </td>
  </tr>
</table>
</body>
</html>