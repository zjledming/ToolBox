<%@ page language="java" import="java.util.*" pageEncoding="GB18030"%>
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

		<title>My JSP 'w1.jsp' starting page</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
		<script type="text/javascript">
	function closedialog() {
		// ������ҳ�����
		if (window.dialogArguments) {

		}
		// �����ڵĸ��ڵ�
		window.dialogArguments.parentWindow;
		var name1 = window.dialogArguments.form1.name1.value;
		alert(name1);
		var name2 = window.dialogArguments.document.getElementById("name2").value;
		alert(name2);
		window.dialogArguments.location.reload();

		window.returnValue = "�ز�";
		window.close();
	}

	// ������ҳ�����:ע��,Ҫ��ҳ�����,�ڸ�ҳ���д򿪵�������б��봫�ݵڶ�������
	function dialogArgumentsFun() {

		// ˢ��
		window.dialogArguments.parent.document.location.href = window.dialogArguments.parent.document.location.href;
		window.dialogArguments.parent.location.href = window.dialogArguments.parent.location.href;
		window.dialogArguments.location.href = window.dialogArguments.location.href;
		window.dialogArguments.location.href = strArray + "?"
				+ window.dialogArguments.document.all.queryString.value;
		var str = window.dialogArguments.location.href;
		window.dialogArguments.location.reload();
		window.dialogArguments.location = "roletype_list.jsp";

		// ȡֵ���߸�ֵ
		var ustate = window.dialogArguments.createTypeForm.dicttypeUseTabelState.value;
		window.dialogArguments.createTypeForm.field_order.value = "";
		webOffice = window.dialogArguments.parent.frames["mainFrame"].document
				.getElementsByName("WebOffice")[0];
		var win = window.dialogArguments || window.opener;
		window.dialogArguments.document.getElementById("issubmit").value = "false";
		parent.window.dialogArguments.document.getElementById("divProcessing").style.display = "block";
		parent.window.dialogArguments.document.getElementById("myFrame").src = _url;
		retObj = winObj.dialogArguments.parentWindow;

		// ���ø�ҳ��ĺ���
		var flowData = window.dialogArguments.getFlowData(); //��ȡ�����������  
		parent.window.dialogArguments.document.forms[0].submit();
		window.dialogArguments.open("navigator_content.jsp",
				"perspective_toolbar");
		window.dialogArguments.parent.toolFrm.reloadView();

	}
</script>

	</head>

	<body>
		This is my JSP page.
		<br>
		<a href="javascript:closedialog();">�ر�</a>
	</body>
</html>
