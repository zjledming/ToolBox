<%@page import="java.net.URLDecoder"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>

<html>
	<head>
		<link type='text/css' rel='stylesheet'
			href='../../../resources/css/creatorBlue/style_right.css' />
		<link type='text/css' rel='stylesheet'
			href='../../../resources/css/creatorBlue/allStyle.css' />
		<script language="JavaScript" type="text/JavaScript">
	function check() {
		var fileurl = document.Form1.File1.value;
		var extname = fileurl.substring(fileurl.indexOf("."), fileurl.length);
		if (!fileurl) {
			alert('��ѡ��CA֤�鵼���ļ�');
			return false;
		}
		if (".xls" != extname) {
			alert('��ѡ��excel�ļ�');
			return false;
		} else {
			document.Form1.submit();
		}
	}
	
	function importCA() {
		var filename = document.getElementById("affix").value;
		if (!filename.endWith(".xls")) {
			alert("��ѡ����ȷ��excel���!");
			return;
		}
		document.all.form1.submit();
	}

	String.prototype.endWith = function(str) {
		if (str == null || str == "" || this.length == 0
				|| str.length > this.length)
			return false;
		if (this.substring(this.length - str.length) == str)
			return true;
		else
			return false;
	}
</script>
		<title>����ca֤����</title>
	</head>
	<body>
		<form name="Form1" enctype="multipart/form-data" method="post"
			action="importcacode_do.jsp">
			<table width='100%' border='1' class=table_function>
				<tr>
					<td height='30' colspan='2' class=all_title align='center'>
						CA֤�鵼��
					</td>
				</tr>
				<tr>
					<td height='30' width='30%' class=color>
						ѡ��Excel�ļ� ��
					</td>
					<td align='left'>
						<input type="file" name="File1" size="25" maxlength="20">
					</td>
				</tr>
				<tr>
					<td height='30' colspan='2'>
						ע�⣺1.��ȷ��ѡ�е�ΪExcel��ʽ�ļ���Excel�ļ������ܰ����ո�2.��ȷ����¼��������ȷ��
					</td>
				</tr>
				<tr>
					<td height='30' colspan='2' align='center'>
						<input type="button" class=sub_btn Onclick="check()" value="�ύ">
						<input type="reset" class=sub_btn value="����">
						<input type="button" class=sub_btn value="����"
							onclick="window.close()">
					</td>
				</tr>
			</table>
		</form>
	</body>