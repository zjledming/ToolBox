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

		<title>My JSP 'demo.jsp' starting page</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
		<script type="text/javascript" src="prototypejs/prototype-1.4.0.js"></script>
		<script type="text/javascript">
	function ajaxrequest() {
		var myAjax = new Ajax.Request("prototypejs/ajaxresp.jsp", {
			method : "post", //���ύ��ʽ 
			parameters : "flag=acai&id=26", //�ύ�ı�����
			setRequestHeader : {
				"If-Modified-Since" : "0"
			}, //��ֹ��ȡ��������
			onComplete : function(x) { //�ύ�ɹ��ص� 
				alert(x.responseText);
			},
			//onComplete:showResponse
			onError : function(x) { //�ύʧ�ܻص�
				alert(x.statusText);
			}
		});
	}

	function showResponse(originalRequest) {
		//put returned XML in the textarea
		var restr = originalRequest.responseText;
		if (restr.replace(/\s+/g, "") == "success") {
			alert("�����ɹ���");
			window.location.href = 'showdelegate.jsp';
		} else if (restr.replace(/\s+/g, "") == "errmsg") {
			alert('ȡ�������ɹ�������֪ͨ��Ϣ����ʧ�ܣ�');
		} else {
			alert('����ʧ�ܣ���' + restr);
		}
	}
</script>
	</head>

	<body>
		prototype.js ajax demo:
		<br>
		<a href="javascript:ajaxrequest();">ajax request</a>
	</body>
</html>
