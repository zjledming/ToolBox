<%@ page language="java" import="java.util.*" pageEncoding="gbk"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<%=basePath%>">

		<title>My JSP 'index.jsp' starting page</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	</head>

	<body>
		This is my JSP page.
		<br>
		<a href="jxl.jsp">jxl����excel</a>
		<a href="importcacode.jsp">jxl�ϴ�excel</a>
		<a href="tabs/demo.jsp">tabdemo</a>
		<a href="window/window.jsp">������</a>
		<INPUT type=button value="�鿴Դ�ļ�"
			onclick="location.replace('view-source:'+location.href)">
		<a href="prototypejs/demo.jsp">prototypejs demo</a>
		<a href="serverconfig.jsp">���������йز���</a>

	</body>
</html>
