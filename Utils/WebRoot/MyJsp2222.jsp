<%@ page contentType="text/html; charset=gb2312" language="java"%>
<%@ taglib uri="/WEB-INF/pager-taglib.tld" prefix="pg"%>
<%@ page import="java.util.*,com.frameworkset.common.poolman.DBUtil"%>
<%@ page import="com.frameworkset.common.poolman.PreparedDBUtil" %>

<%
   String userType=(String)session.getAttribute("userType");
   if("0".equals(userType)){//当工作人员登陆此页面时，就跳转到主页
     response.sendRedirect("index.jsp");
    }
    String flag = null==request.getParameter("spout")? "Y":request.getParameter("spout");
	String baseinfoname = request.getParameter("name");
	String id = request.getParameter("id");
	String user_realname = (String)session.getAttribute("user_realname");
	String pre_parentid = request.getParameter("pre_parentid");
	String pre_catalogname = request.getParameter("pre_catalogname");
	String pre_parentid_index = request
			.getParameter("pre_parentid_index");
	String pre2_windowname = request.getParameter("pre2_windowname");
	String pre2_windowid = request.getParameter("pre2_windowid");
	String searchtype = request.getParameter("searchtype");
    String bz=request.getParameter("bz")==null ? "3" : request.getParameter("bz");
	if(!"2".equals(bz)){
		bz = "3";
	}
	//此处type是指从不同页面进入此页面
	String type = request.getParameter("type");
	String shenpi_insid = request.getParameter("shenpi_insid");
	String url = "bjsl/public_bjsl.jsp";
	//String sql1 = "select item_name,IS_OUTERAPPLY from ta_sp_baseinfo t where t.ec_id=" + id;
	String sql1 = "select item_name,IS_OUTERAPPLY from ta_sp_baseinfo t where t.ec_id=?";
	//DBUtil db = new DBUtil();
	PreparedDBUtil db=new PreparedDBUtil();
	System.out.println("[预编译处理开始]参数:"+id);
      //db.executeSelect("spweb", sql1);
    try {
      db.preparedSelect("spweb", sql1);
      db.setString(1, id);
	  db.executePrepared();
	  if (db!=null && db.size() > 0) {
         baseinfoname = db.getString(0, "item_name");
		 flag = db.getString(0, "IS_OUTERAPPLY");
	  }
    } catch (Exception e) {
		System.out.println("请注意，发生异常："+e.getMessage());
	}
	System.out.println("[预编译处理开始]结果:"+baseinfoname+"  |  "+flag); 
%>
<html>
	<head>
		<script type="text/javascript" language="JavaScript1.2"
			src="tab/tab.js"></script>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
		<title>[中国长沙]-网上办事</title>
		<link href="css/css.css" rel="stylesheet" type="text/css" />
		<link href="css/tab_css.css" rel="stylesheet" type="text/css" />
	</head>
<script type="text/JavaScript">
 <!--
   function secBoard_2(n){
			 //-- 根据点击的不同，来控制显示风格
			 for (i=0;i<secTable_2.cells.length;i++)
			 secTable_2.cells[i].className="FZmenu02";
			 secTable_2.cells[n].className="FZmenu01";
			 document.getElementById("secTable_"+n+"_a").click();
			 }
 /* function checkLogin(){    
    if("N"=="<%=flag%>"){
      alert("该项目暂时不提供网上预受理,请直接到办事大厅办理。");          
      document.all.secTable_3_a.href = "message.jsp?flag=spout";
      return false;
    } 
  if( "<%=user_realname%>" == "" || "<%=user_realname%>" == "null" )
  {
    alert("您还没有登陆！请您先登陆！");
     var isLogin = document.all("isLogin");
	 
     url = "zonghe.jsp";
     window.showModalDialog(url,isLogin,"dialogWidth=300px;dialogHeight=180px;left=0;resize=0;help=no;status=no");
     if(isLogin.value !="yes")
     {  
      document.all.secTable_3_a.href = "message.jsp";
     }else 
	     { 	     
	       document.location.href = document.location.reload() ;
	      // secBoard_2("3");     
	     }
    }
  }*/

function outapply(){
if("N"=="<%=flag%>"){
      alert("该项目暂时不提供网上预受理,请直接到办事大厅办理。");          
      document.all.secTable_3_a.href = "message.jsp?flag=spout";
      return false;
    } 
	return true;
}


function checkLogin(){   
	    
  if( "<%=user_realname%>" == "" || "<%=user_realname%>" == "null" )
  {
    alert("您还没有登陆！请您先登陆！");
     var isLogin = document.all("isLogin");
	 
     url = "zonghe.jsp";
     window.showModalDialog(url,isLogin,"dialogWidth=300px;dialogHeight=180px;left=0;resize=0;help=no;status=no");
	 
     if(isLogin.value =="yes")
     {  
      document.location.href = document.location.reload() ;
     }else 
	     { 	     
	       window.close();
	      // secBoard_2("3");     
	     }
		  return false;
    }
	return true;
  }
 
 function init()
 {
	 if(!checkLogin()){
	return false;	
	};
    if("<%=bz%>" == "0" || "<%=bz%>" == "" ){  
     document.all.secTable_0_a.click();
     }else{
   secBoard_2('<%=bz%>');
   }   
 }
//-->
</script>	
<BODY onload="init()" style="margin:0px; font-size:12px; color:414141; line-height:21px; background:url(images/back_007.gif)">
<table width="794" background="images/comm_table_bg.gif" cellpadding="0" align="center" cellspacing="0"border="0">
<input type="hidden" name="isLogin" value=""/>
  <tr><td align="center">
<table width="780" border="0" align="center" cellpadding="0" cellspacing="0" bgcolor="#FFFFFF">
						<tr>
							<td>
								<iframe align="middle" src='index_top.htm' width=780 height=148 scrolling=no marginwidth=0 marginheight=0 frameborder=0
									vspace=0 hspace=0></iframe>
							</td>
						</tr>
						<tr style="display:none">
							<td>
								<table width="780" border="0" align="center" cellpadding="0"
									cellspacing="0" background="images/nav_bar.gif"
									class="Current_Position_box">
									<tr>
										<td width="30" align="center">
											<img src="images/arrow.gif" width="10" height="10"
												vspace="0">
										</td>
										<td width="543" style="color: #000000">
										<%if("newapply".equals(type))
										{
										 %>
										 <div>
												&nbsp;&nbsp; 当前位置：<a href="http://www.changsha.gov.cn/wsbs/" target="_top"
													style="color: #000000">首页</a> &gt;
												<a href="http://www.changsha.gov.cn/wsbs/" target="_top"
													style="color: #000000">办事大厅首页</a>&gt;
													<a href="banjian_list.jsp" target="_top"
													style="color: #000000">我的办件</a>&gt;
													预受理重新申请
											    </div>
										 <%
										}else if("zancun".equals(type))
                                               {
                                                 %>
                                                  <div>
												&nbsp;&nbsp; 当前位置：<a href="http://www.changsha.gov.cn/wsbs/" target="_top"
													style="color: #000000">首页</a> &gt;
												<a href="http://www.changsha.gov.cn/wsbs/" target="_top"
													style="color: #000000">办事大厅首页</a>&gt;
													<a href="zancun_list.jsp" target="_top"
													style="color: #000000">暂存列表</a>&gt;
													事件办理
											    </div>
                                                   <%                                            
                                               } 
                                               else
                                               {
                                               %>
                                               <%
												if (pre2_windowid == null && searchtype == null) {
											%>
											<div>
												&nbsp;&nbsp; 当前位置：<a href="http://www.changsha.gov.cn/wsbs/" target="_top"
													style="color: #000000">首页</a> &gt;
												<a href="http://www.changsha.gov.cn/wsbs/" target="_top"
													style="color: #000000">办事大厅首页</a>&gt;
												<a
													href="baseInfoList.jsp?parentid=<%=pre_parentid%>&catalogname=<%=pre_catalogname%>&parentid_index=<%=pre_parentid_index%>"
													style="color: #000000"><%=pre_catalogname%></a>
											</div>
											<%
												}
											%>

											<%
												if (pre2_windowid != null && searchtype == null) {
											%>
											<div>
												&nbsp;&nbsp; 当前位置：<a href="http://www.changsha.gov.cn/wsbs/" target="_top"
													style="color: #000000">首页</a> &gt;
												<a href="http://www.changsha.gov.cn/wsbs/" target="_top"
													style="color: #000000">办事大厅首页</a>&gt;
												<a
													href="bm_baseinfolist.jsp?windowid=<%=pre2_windowid%>&windowname=<%=pre2_windowname%>"
													style="color: #000000"><%=pre2_windowname%></a>
											</div>

											<%
												}
											%>
											<%
												if (searchtype != null && pre2_windowid == null) {
											%>
											<div>
												&nbsp;&nbsp; 当前位置：<a href="index.jsp" target="_top"
													style="color: #000000">首页</a> &gt;
												<a href="http://www.changsha.gov.cn/wsbs/" target="_top"
													style="color: #000000">网上办事</a>&gt;服务搜索
											</div>
											<%
												}
											  }
                                             %>                                               
										</td>
									</tr>
								</table>
							</td>
						</tr>
					</table>
					<!--内容块2-->
					<center>						
						<table
							style="border-collapse: collapse; padding-top: 5px; border-style: solid; border: 1px #d5d5d5 solid;"
							width="780" align="center" border="0" cellspacing="0"
							cellpadding="0">
							<tr style="display: none">
								<td valign="top">
									<img src="../images/wsbs_bg.jpg">
								</td>
							</tr>
							<tr>
								<td>
									<table width="780" border="0" cellpadding="0" cellspacing="0"
										class="index_box_second">
										<tr>
											<td width="700" nowrap="nowrap"
												style="padding-left: 20px; font-weight: bold; color: #FF0000; font-size: 14px;">
												<img src="images/point_banshi.gif">
												<%=baseinfoname%></td>
											<td>
											</td>
										</tr>
									</table>
								</td>
							</tr>
							<tr>
								<td>
									<TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
										<TBODY>
											<TR>
											<TD width="72%" align="left" background=images/Table01Bg.gif>
													<%
													 String sql = "";
													    if( "zancun".equals(type))
													    {
													    sql =" select * from ta_view_instance_web where item_insid = '" + shenpi_insid+"'";													   
													    }else
													    {
													     sql = "select * from ta_sp_baseinfo t where t.ec_id='" + id
																+ "'";
													    }	
													%>
														<pg:beaninfo statement="<%=sql%>" dbname="spweb">
														<TABLE id=secTable_2 height=37 cellSpacing=0 cellPadding=0
															border=0>
															<TBODY>
																<TR>																	
																	<TD class=FZmenu01 id="secTable_2_td" width=80 onclick=secBoard_2(0) style="display:none">
																		<a id="secTable_0_a"
																			href="public/bszn_detail.jsp?id=<%=id%>"
																			target="tab_frame">办事指南</a>
																	</TD>																
																	<TD class=FZmenu02 width=80 onclick=secBoard_2(1) style="display:none">
																		<a id="secTable_1_a"
																			href="public/bgxz_list.jsp?id=<%=id%>&orgid=<pg:cell colName="orgid" defaultValue=""/>"
																			target="tab_frame">相关材料</a>
																	</TD>																
																	<TD class=FZmenu02 width=80 onclick=secBoard_2(2)>
																		<a id="secTable_2_a"
																		 href="zxzx/zx_zx_content.jsp?id=<%=id%>&orgid=<pg:cell colName="orgid" defaultValue=""/>&orgname=<pg:cell colName="orgname" defaultValue=""/>&shenpi_name=<pg:cell colName="item_name" defaultValue=""/>" 
																		 target="tab_frame" onClick="checkLogin();">在线咨询</a>
																	</TD>														
																	<TD class=FZmenu02 width=80 onclick=secBoard_2(3)>
																		<%if("zancun".equals(type) || "newapply".equals(type) )
																		{
																		%>
																		<a id="secTable_3_a"
																		href="<%=url %>?ec_id=<%=id %>&shenpi_insid=<%=shenpi_insid %>&orgId=<pg:cell colName="orgid" defaultValue=""/>&orgName=<pg:cell colName="orgname" defaultValue=""/>&type=type&bz=3"																			
																			target="tab_frame" onClick="outapply();">在线办理</a>
																		<%
																		}else{
																		%> 
																		<a id="secTable_3_a"
																		href="<%=url %>?ec_id=<%=id %>&orgId=<pg:cell colName="orgid" defaultValue=""/>&orgName=<pg:cell colName="orgname" defaultValue=""/>"																			
																			target="tab_frame"
																			onClick="outapply();">在线办理</a>
																		<%
																		}
																		%>																		
																	</TD>
																</TR>
															</TBODY>
														</TABLE>						
													</pg:beaninfo>
												</TD>
											</TR>
										</TBODY>
									</TABLE>
									<TABLE cellSpacing=0 cellPadding=0 width="90%" bgColor=#ffffff border=0 align="center">
										<TR>
										<TD id='td_height' width="90%" height="230" align="center" vAlign="top"
												bgcolor="#FFFFFF">
												<div width="100%" height="100%" vAlign="top">
												<iframe style="width:100%;" name="tab_frame" 
													frameborder="0" scrolling="no" onload="this.height = document.frames['tab_frame'].document.body.scrollHeight"></iframe>
												</div>
											</TD>
										</TR>
									</TABLE>
								</td>
							</tr>
							<tr>
								<td>
									<iframe align="middle" src='index_bottom.htm' width=100%
										height=80 scrolling=no marginwidth=0 marginheight=0
										frameborder=0 vspace=0 hspace=0></iframe>
								</td>
							</tr>
						</table>
					</center>
				</td>
			</tr>
		</table>
	
	</body>
</html>