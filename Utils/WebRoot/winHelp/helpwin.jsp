<%@page import="com.jsp.HelpWin"%>
<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<title>��ҵ�������ҳ��</title>
<LINK href="helpwin.css" type=text/css rel=stylesheet></link>  
<script type="text/javascript" src="helpwin.js"></script>
</head>
<body>
<TABLE width="100%">
	<tr>
		<td width="25%" align="right" >
		<button class="cButton" id="btn_plsh" onclick="agentInstance();">�������
		</button>
		</td>
	</tr>
	<tr>
		<td width="35%" align="right" style="right: 15px;">
			<div onmouseout="openHelpWin('openHelpWinId')" onclick=""
				style="width: 88px; text-align: center; position: relative; color: red;"
				onmouseover="openHelpWin('openHelpWinId')">
				����˵��
				<div id="openHelpWinId"
					style="display: none; position: absolute; top: 30px; left: -<%=HelpWin.win_l%>px; width: <%=HelpWin.win_w%>px; height: <%=HelpWin.win_h%>px;">
					<table border="0" cellpadding="0" cellspacing="0" height="<%=HelpWin.win_h%>">
						<tr>
							<td class="box">
								<div class="nr">
									<font color="red"> ����˵��: 1�������<IMG id="img1"
											src="../../resources/images/gif_47_029.gif" width="24"
											height="24">ͼ�����������ˡ� 2�������<IMG id="img1"
											src="../../resources/images/gif_47_088.gif" width="24"
											height="24">ͼ���ӡ������Ϣ�� </font>
								</div>
							</td>

						</tr>
					</table>
					<s style="top: -19px; left: <%=HelpWin.win_sj_l%>px;"><i></i> </s>
				</div>

			</div>
		</td>
	</tr>
</TABLE>
</body>
</html>