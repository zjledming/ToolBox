<%@ page contentType="text/html; charset=gbk" language="java" import="java.sql.*" errorPage="" %>
<%@ page import="java.io.OutputStream"%>
<%@ page import="com.frameworkset.common.poolman.DBUtil"%>
<%@ page import="com.chinacreator.xzsp.web.wsbs.Constant"%>
<%@ page import="com.frameworkset.common.poolman.PreparedDBUtil" %>
<%
    String business_id=request.getParameter("djbh");
    String name = request.getParameter("name");//�������� 
    String attachid=request.getParameter("attachid");
    //DBUtil db = new DBUtil();
    PreparedDBUtil db=new PreparedDBUtil();
    String exname="";
    
    System.out.println("[Ԥ���봦��ʼ]����:"+business_id);
    //String sql=" select * from fc_attach where  djbh='" + business_id 
             //  + "' and djsn='"+Constant.gonggaoId+"' and attachid='"+attachid+"'";
    String sql=" select * from fc_attach where  djbh=? and djsn=? and attachid=?";
    //db.executeSelect("spweb",sql);
    try {
	    db.preparedSelect("spweb",sql);
	    db.setString(1, business_id);
	    db.setString(2, Constant.gonggaoId);
	    db.setString(3, attachid);
	    db.executePrepared();
    } catch (Exception e) {
		System.out.println("��ע�⣬�����쳣��"+e.getMessage());
	}
	System.out.println("[Ԥ���봦�����]"); 
    
    if(db.size() > 0 )
    {
        if( "".equals(db.getString(0,"ATTACHCONTENT")))
		{
			   out.print("<script type='text/javascript'>alert('�޲��ϸ������ػ򸽼�����Ϊ�գ�');</script>");
			   out.println("<script type='text/javascript'>window.history.back(1);</script>");
		}else
		{
	   //exname = db.getString(0,"EXTEND");
       response.setContentType("application/octet-stream;charset=gb2312");
       response.setHeader("Content-Disposition", "attachment;filename="
					+ new String(name.getBytes("GB2312"), "ISO8859-1"));
		OutputStream os = response.getOutputStream();
			try {
				os.write(db.getByteArray(0,"ATTACHCONTENT"));
				os.flush();
			} catch (Exception e) {
				//e.printStackTrace();
			} finally {
				try {
					if (os != null)
						os.close();
				} catch (Exception eeee) {
					//eeee.printStackTrace();
				}
			}
			}
    }else
    {
       out.print("<script type='text/javascript'>alert('�޲��ϸ������ػ򸽼�����Ϊ�գ�');</script>");
       out.println("<script type='text/javascript'>history.go(-1);</script>");
    }
%>

