/**
 * 初始化方法
 */

var instance_code = creator_getQueryString("INSTANCE_CODE");
var userAccount = getSysElement("userAccount");
var userId = getSysElement("userId");
var userRealName = SqlToField(" select u.user_realname from app_5.td_sm_user u where u.user_id="+userId+"");
var ecid = SqlToField(" select ec_id from app_5.ta_sp_instance where instance_code = '"+instance_code+"'");

var ec_id = creator_getQueryString("ec_id");
var actDefId = creator_getQueryString("actDefId");
var sisType =  creator_getQueryString("sisType");
var common = "";
if(IsSpace(ec_id)){
	ec_id = ecid;
}
//var orgId = SqlToField(" select b.orgid from app_5.ta_sp_baseinfo  b where b.ec_id =  '"+ec_id+"'");
//var orgName = SqlToField(" select org_name from app_5.td_sm_organization o where o.org_id = '"+orgId+"'");
//var itemLimit = SqlToField("select b.item_limit from app_5.ta_sp_baseinfo b where b.ec_id= '"+ec_id+"'");
var sqlStr = "select o.org_id||','||o.org_name||','||b.item_limit||','||b.law_limit|| ',' || b.item_type from app_5.ta_sp_baseinfo b inner join app_5.td_sm_organization o "+
"on b.orgid = o.org_id where b.ec_id= '"+ec_id+"'";
var  argStr  =   SqlToField(sqlStr);
var argArray = argStr.split(",");
var orgId = argArray[0];
var orgName = argArray[1];
var itemLimit = argArray[2]; 
var lawLimit = argArray[3];
var item_type = argArray[4];
var SUBREPORT_DIR = "<%=request.getRequestURL().replace(request.getRequestURL().indexOf(request.getRequestURI()),request.getRequestURL().length(),"")%>"+"/creatorepp/report/reportFiles/";
//alert(argStr);
//alert(sisType );
var report_idold = "";
function init()
{
    var amanuensisId=creator_getQueryString("amanuensis_id");
    /*if(isNaN(amanuensisId)){
         amanuensisId ="";
    }*/
    $("INSTANCE_CODE").value=creator_getQueryString("INSTANCE_CODE");
    if(!IsSpace(amanuensisId))
    {
        if(existSameReportInstance(amanuensisId,$("INSTANCE_CODE").value))
        {
            findReortInstance(amanuensisId,$("INSTANCE_CODE").value);
        }else{
            findAmanuensisReport(amanuensisId);
        }
        buildExtTableWithSQL(amanuensisId);
    }
	
	if(IsSpace(sisType)){
		$("btnReportSpecial").style.display = "none";
		$("btnReportSpecial2").style.display = "none";
		$("btnReport1").style.display = "none";
		$("btnReport2").style.display = "none";
	}else{
		var arr = findSpecialData();
		//alert(orgId);
		if(0==arr){
			$("btnReportSpecial").style.display = "none";
			$("btnReportSpecial2").style.display = "none";
			$("btnReport").style.display = "none";
		}else{
			$("btnReport1").style.display = "none";
			$("btnReport2").style.display = "none";
			//alert(orgId);
			if("249"==orgId||"67"==orgId||"41"==orgId||"52"==orgId||"42"==orgId||"263"==orgId||"72"==orgId){
				if("67"==orgId&&"0105"==sisType){
					$("btnReportSpecial2").style.display = "none";
					$("btnReport").style.display = "none";
				}else{
					$("btnReport").style.display = "none";
					$("btnReportSpecial").className = "btn_110";
					$("btnReportSpecial2").className = "btn_110";
					$("btnReportSpecial").innerText = "个性化(一份)";
					$("btnReportSpecial2").innerText = "个性化(二份)";
				}
			}else{
				$("btnReportSpecial2").style.display = "none";
				$("btnReport").style.display = "none";
			}
		}
	}

	report_idold = $("REPORT_ID").value;

}

function findSpecialData(){

	if(IsSpace(ec_id)){
		ec_id = ecid;
	}
	var sql = "select a.resource_id,a.is_onece from app_5.ta_sp_amanuensis a left join app_5.ta_sp_amanuensis_action c on a.amanuensis_id = c.amanuensis_id"+
		" where a.ec_id= '"+ec_id+"' and c.is_valid = 'Y' and a.is_valid = 'Y' and (c.action_defid = '"+actDefId+"' or a.amanuensis_type = '"+sisType+"')";
	var array = new Array();	
	dataset1.Open(sql);
	var count=dataset1.RecordCountAll;
	dataset1.MoveFirst();
	if(count<=0){
		return 0;
	}else{		
		for(var i=0;i<count;i++){
		   array[i] =dataset1.Fields.Field["resource_id"].Value;
		   dataset1.MoveNext();
		}
		
		return array;
	}
	return 0;
}

/*
 * 判断是否存在相同的报表实例
 */
function existSameReportInstance(baseReportId,instanceCode)
{
    var sql="select count(i.reportinstance_id)" +
            "  from app_5.ta_sp_reportinstance i" +
            "  where i.report_id =(select report_id from " +
            "   app_5.ta_sp_basereport b where b.basereport_id = '"+baseReportId+"')" +
            "    and i.instance_code = '"+instanceCode+"'";
    //CopyToPub(sql);
    if(parseInt(SqlToField(sql))>0){
        return true;
    }
    return false;
}

/**
 * 查询报表实例信息
 */
function findReortInstance(baseReportId,instanceCode){
    var sql="select i.*" +
            "  from app_5.ta_sp_reportinstance i" +
            "  where i.report_id =(select report_id from " +
            "   app_5.ta_sp_basereport b where b.basereport_id = '"+baseReportId+"')" +
            "    and i.instance_code = '"+instanceCode+"'";
   reportInstanceDataset.Open(sql);
}

/**
 * 查询文书报表配置信息
 */
function findAmanuensisReport(amanuesisId)
{  //alert(amanuesisId);
  // alert(isNaN(amanuesisId));
    if(isNaN(amanuesisId)){//ly新增0727
       var sql = "select report_name as AMANUENSIS_NAME,report_id as RESOURCE_ID,report_type as AMANUENSIS_TYPE from app_5.ta_sp_common_basereport "
                 +"  where amanuensis_id='"+amanuesisId+"'";
    }else{
       var sql = "select * from app_5.ta_sp_amanuensis where amanuensis_id="+amanuesisId+"";
   }
    // alert(sql);
    amanuensisDataset.Open(sql);
    $("REPORT_ID").value = amanuensisDataset.Fields.Field["RESOURCE_ID"].Value;
  // alert($("REPORT_ID").value);
    $("REPORT_NAME").value = amanuensisDataset.Fields.Field["AMANUENSIS_NAME"].Value;
    $("REPORT_TYPE").value = amanuensisDataset.Fields.Field["AMANUENSIS_TYPE"].Value;
//    $("PRINT_TYPE").value = amanuensisDataset.Fields.Field["OPERATION_TYPE"].Value;
    $("PRINT_TYPE").value = "1" //填报打印
}


/**
 * 构建参数table
 */
function buildExtTable(parameters)
{
    var oTR3=document.createElement("tr");
    for(var i=0;i<parameters.length;i++)
    {
        var oTR=document.createElement("tr");
        var oTD1 = document.createElement("td");

        
        oTD1.innerText=parameters[i].description;
        oTD1.className = "color";


        //生成参数描述隐藏域
        var oFieldDescription=document.createElement("<input name='fieldDescription' type=hidden value='"+parameters[i].description+"'>");
        oTD1.appendChild(oFieldDescription);
        
        //生成参数名隐藏域
        var oFieldName=document.createElement("<input name='fieldName' type=hidden value='"+parameters[i].fieldName+"'>");
        oTD1.appendChild(oFieldName);
        
        //生成字段输入类型隐藏域
        var oFieldAllowInput=document.createElement("<input name='fieldAllowInput' type=hidden value='"+parameters[i].allowInput+"'>");
        oTD1.appendChild(oFieldAllowInput);  
        
        oTR.appendChild(oTD1);

        
         //生成参数数据输入框

         var oTD2 =  document.createElement("td");
         oTD2.setAttribute("colSpan","6");
        //oTD2.className = "color";
         var oTD2_1 =  document.createElement("td");
          oTD2_1.style.width="15%";
        var oFieldInput=document.createElement("INPUT type='text' maxLength='1000'");
        oFieldInput.name="fieldValue";
        oFieldInput.style.width = "100%";
        //如果不可以填写，设置输入框为只读
        if(parameters[i].allowInput!="Y")
        oFieldInput.disabled=true;
        
        if(!IsSpace(creator_getQueryString(oFieldName.value))){
            if(ecid=="1000"){
               if(parameters[i].description=="至哪年办结"||parameters[i].description=="至哪月办结"||parameters[i].description=="至哪日办结"){
                   oFieldInput.value="";
               }else{
                    oFieldInput.value=creator_getQueryString(oFieldName.value);
               }
            }else{
               oFieldInput.value=creator_getQueryString(oFieldName.value);
            }
        }

        
        var defaultValue=parameters[i].defaultValue;
        if(!IsSpace(defaultValue)){
            oFieldInput.value=defaultValue;
        }
        
        oTD2.innerHTML=oFieldInput.outerHTML;
        oTR.appendChild(oTD2);

        oTD2_1.innerHTML=oFieldInput.outerHTML;

        var FN = parameters[i].fieldName;
       /* if(FN=='start_year'||FN=='start_month'||FN=='start_day'||FN=='end_year'||FN=='end_month'||FN=='end_day'){
           oTR3.appendChild(oTD1);
           oTR3.appendChild(oTD2_1);
            if(FN=='start_day'||FN=='end_day'){
                inputArea.insertBefore(oTR3,inputTR);
                oTR3=document.createElement("tr");
             }
        }else{
        inputArea.insertBefore(oTR,inputTR);
        }  */  
inputArea.insertBefore(oTR,inputTR);
       // document.getElementById("inputArea").appendChild(oTR);
    }
}



/*
 * 通过sql生成表格
 */
function buildExtTableWithSQL(amanuensisId)
{
     if(isNaN(amanuensisId)){//ly新增0727

           if(amanuensisId=="common01"){ //受理通知书
                   var sql = "select amanuensis_id from app_5.ta_sp_amanuensis where ec_id='tongyong' and amanuensis_name='受理通知书' ";
                   amanuensisId=SqlToField(sql);
           }else if(amanuensisId=="common02"){//不予受理通知书
                   amanuensisId=SqlToField("select amanuensis_id from app_5.ta_sp_amanuensis where ec_id='tongyong' and amanuensis_name='不予受理通知书' ");
           }else if(amanuensisId=="common03"){//补正告知通知书
                   amanuensisId=SqlToField("select amanuensis_id from app_5.ta_sp_amanuensis where ec_id='tongyong' and amanuensis_name='补正告知通知书' ");
           }else if(amanuensisId=="common04"){//补交受理通知书
                   amanuensisId=SqlToField("select amanuensis_id from app_5.ta_sp_amanuensis where ec_id='tongyong' and amanuensis_name='补交受理通知书' ");
           }else if(amanuensisId=="common05"){//许可决定通知书
                   amanuensisId=SqlToField("select amanuensis_id from app_5.ta_sp_amanuensis where ec_id='tongyong' and amanuensis_name='许可决定通知书' ");
           }else if(amanuensisId=="common06"){//不予许可决定通知书
                   amanuensisId=SqlToField("select amanuensis_id from app_5.ta_sp_amanuensis where ec_id='tongyong' and amanuensis_name='不予许可决定通知书' ");
           }                    
     }
       
    var sql="select ext_field_name,ext_field_description,ext_allow_input,ext_allow_display,ext_field_defaultvalue " +
            " from app_5.ta_sp_amanuensis_ext where amanuensis_id='"+amanuensisId+"'";
        extDataset.Open(sql);
    //alert(sql);
    //CopyToPub(sql);
    var count=extDataset.RecordCountAll;
    var parameters=new Array();
    extDataset.MoveFirst();
    for(var i=0;i<count;i++)
    {
        var allowDisplay=extDataset.Fields.Field["EXT_ALLOW_DISPLAY"].Value;
        if(allowDisplay=="Y")
        {
           var parameter=new Array();
           parameter.fieldName=extDataset.Fields.Field["EXT_FIELD_NAME"].Value;
           parameter.description=extDataset.Fields.Field["EXT_FIELD_DESCRIPTION"].Value;
           parameter.allowInput=extDataset.Fields.Field["EXT_ALLOW_INPUT"].Value;   
           parameter.defaultValue=extDataset.Fields.Field["EXT_FIELD_DEFAULTVALUE"].Value;
           parameters.push(parameter);
        }
        extDataset.MoveNext();
    }
  
    buildExtTable(parameters);
}

/**
 * 保存
 */
function save(report_id,report_name)
{
    $("REPORT_ID").value  = report_id;
    //保存报表主信息
    //if(IsSpace($("REPORTINSTANCE_ID").value))
    //{
       pubdjbh="";
       $("REPORTINSTANCE_ID").value=SqlToField("select app_5.seq_reportinstance_id.nextval from dual");
    //}else{
       //pubdjbh=$("REPORTINSTANCE_ID").value;
   // }
    $("PRINTER_ID").value=getSysElement("userid");
    $("PRINT_DATE").value="";
    fcpubvar.DsMain = "reportInstanceDataset"; //
    SKbillsheet.keyfield = "REPORTINSTANCE_ID";
    var msg = DjSave();
    if(!IsSpace(msg)){
        alert("保存报表实例信息发送错误:"+msg);
        return;
    }else{	
		var insertsql = "update app_5.ta_sp_reportinstance set print_date=sysdate,report_id="+report_id;
		var reports_id = $("REPORTINSTANCE_ID").value;
		if(!IsSpace(report_name)){
			insertsql += ",report_name='"+report_name+"'";
		}
		insertsql +=" where reportinstance_id='"+$("REPORTINSTANCE_ID").value+"'";
		InsertSql(insertsql);
       // InsertSql("update app_5.ta_sp_reportinstance set print_date=sysdate,report_id="+report_id+" where reportinstance_id='"+$("REPORTINSTANCE_ID").value+"'");
    }
   // alert($("REPORTINSTANCE_ID").value);
    var fieldNames=document.getElementsByName("fieldName");
    var fieldValues=document.getElementsByName("fieldValue");
    var fieldDescriptions=document.getElementsByName("fieldDescription");
    var fieldAllowInput=document.getElementsByName("fieldAllowInput");
    var sql="<no>delete from app_5.ta_sp_reportinstance_ext"+
            " where reportinstance_id='"+$("REPORTINSTANCE_ID").value+"'</no>";
    var reportInstanceId=$("REPORTINSTANCE_ID").value;
    for(var i=0;i<fieldNames.length;i++)
    {
        sql+="<no>insert into app_5.ta_sp_reportinstance_ext(EXT_ID,REPORTINSTANCE_ID,EXT_FIELD_NAME,EXT_FIELD_DESCRIPTION," +
            "EXT_FIELD_VALUE,EXT_ALLOW_INPUT) values(app_5.seq_reportinstance_ext_id.nextval,'"+reportInstanceId+"'," +
            "'"+fieldNames[i].value+"','"+fieldDescriptions[i].value+"','"+fieldValues[i].value+"','"+fieldAllowInput[i].value+"')</no>"
    }
//alert(sql);
    var msg=InsertSqls(sql);
    if(!IsSpace(msg))
    {
        alert("保存报表实例扩展信息发送错误:"+msg);   
    }
}

function getFieldsInfo()
{
    var fieldNames=document.getElementsByName("fieldName");
    var fieldValues=document.getElementsByName("fieldValue");
    var fieldDescriptions=document.getElementsByName("fieldDescription");
    var fieldTypes=document.getElementsByName("fieldType");
    fieldsInfo={names:fieldNames,values:fieldValues,descriptions:fieldDescriptions,types:fieldTypes}
    return fieldsInfo;
}


function getArgs()
{
    var sourceUrl="&"+document.URL.split("?")[1];
    var fieldsInfo=getFieldsInfo();
    var count=fieldsInfo.names.length;
    for(var i=0;i<count;i++)
    {
        if(!IsSpace(fieldsInfo.values[i].value))
        {
            sourceUrl=setUrlParam(sourceUrl,fieldsInfo.names[i].value,fieldsInfo.values[i].value);
        }
    }
    return sourceUrl+"&acceptor="+userRealName+"&itemLimit="+itemLimit;
}

/*
 * 替换url参数
 */
function setUrlParam(oldurl,paramname,pvalue){     
      var   reg   =   new   RegExp("(\\?|&)"+   paramname   +"=([^&]*)(&|$)","gi");     
      var   pst=oldurl.match(reg);     
      if((pst==undefined)   ||   (pst==null)){    
        return   oldurl+("&")+paramname+"="+pvalue;     
      }   
      var   t=pst[0];     
      var   retxt=t.substring(0,t.indexOf("=")+1)+pvalue;     
      if(t.charAt(t.length-1)=='&')   retxt+="&";     
      return   oldurl.replace(reg,retxt);     
} 

function validate()
{
    var checkList = new SqlInjCheckList();
    var inputs=document.getElementsByName("fieldValue");
    for(var i=0;i<inputs.length;i++)
    {
        checkList.addItem(inputs[i].value);
        if(!app_5_checkSpecial(inputs[i].value)){
            inputs[i].focus();
            return false;
        }
    }
    if(!checkList.check()){
       return false;
    }
    return true;
}



/*
 * 打印
 */
function printReport()
{
    if(!validate()) return;
    var reportId=report_idold;

    if("1"==item_type&&"0105"==sisType){
		reportId ="20140828153230265666";
	}
    var fieldsInfo=getFieldsInfo();
    var args=getArgs();
    var args1 = "&orgName="+orgName+"&lawLimit="+lawLimit;
    var dateV = SqlToField("select to_char(sysdate,'yyyy') ||'年'|| to_char(sysdate,'MM')||'月'||to_char(sysdate,'dd')||'日' from dual");
	//var reportUrl="../../../report/showJasperReport.jsp?raq="+reportId+args;
      var reportUrl="../../../report/showJasperReport.jsp?raq="+reportId+"&SUBREPORT_DIR="+SUBREPORT_DIR+"&date="+dateV+args1+args;
	//若reportId对应的为润乾报表(jinbo.tan 20101222新增)
	var publishState = SqlToField("select publishstate from dzzwpt.tb_report r where r.rep_id='" + reportId + "'");
	var reportType  = SqlToField("select rep_stype from dzzwpt.tb_report r where r.rep_id='" + reportId + "'");
	if("1"==publishState){
		if("0"==reportType||"1"==reportType){
			//sendAjax(reportId,args);
                                var reportUrl = "../../../report/showreport.jsp?raq="+reportId+args;
          var featrues = "dialogWidth:720px;dialogHeight:800px;center:yes;help:no;resizable:no;status:no;scroll:yes";
          window.showModalDialog(reportUrl,window,featrues);
                                save(reportId);
			return;
		}
	}
	//新增结束
    var featrues = "dialogWidth:720px;dialogHeight:800px;center:yes;help:no;resizable:no;status:no;scroll:yes";
    var printFlag=window.showModalDialog(reportUrl,window,featrues);
   //var printFlag=window.open(reportUrl); 
    //if(printFlag)
    save(reportId);
	if("1"==item_type&&"0105"==sisType){
		 if(confirm("您是否需要打印办结通知书？")){ 
	    	 if(confirm("确定打印一页一份，取消打印一页二份！")){ 
				printReportNew3();
			 } else {
			   printReport4();
			 }
		 } 
		
	}
}
/*
 * 行政许可事项打印一页二份的办结通知书
 */
function printReport4()
{
    if(!validate()) return;
    var reportId="20140828153018031166";

    var fieldsInfo=getFieldsInfo();
    var args=getArgs();
    var args1 = "&orgName="+orgName+"&lawLimit="+lawLimit;
    var dateV = SqlToField("select to_char(sysdate,'yyyy') ||'年'|| to_char(sysdate,'MM')||'月'||to_char(sysdate,'dd')||'日' from dual");
	//var reportUrl="../../../report/showJasperReport.jsp?raq="+reportId+args;
      var reportUrl="../../../report/showJasperReport.jsp?raq="+reportId+"&SUBREPORT_DIR="+SUBREPORT_DIR+"&date="+dateV+args1+args;
	//若reportId对应的为润乾报表(jinbo.tan 20101222新增)
	var publishState = SqlToField("select publishstate from dzzwpt.tb_report r where r.rep_id='" + reportId + "'");
	var reportType  = SqlToField("select rep_stype from dzzwpt.tb_report r where r.rep_id='" + reportId + "'");
	if("1"==publishState){
		if("0"==reportType||"1"==reportType){
			//sendAjax(reportId,args);
                                var reportUrl = "../../../report/showreport.jsp?raq="+reportId+args;
          var featrues = "dialogWidth:720px;dialogHeight:800px;center:yes;help:no;resizable:no;status:no;scroll:yes";
          window.showModalDialog(reportUrl,window,featrues);
                                save(reportId,"办结通知书");
			return;
		}
	}
	//新增结束
    var featrues = "dialogWidth:720px;dialogHeight:800px;center:yes;help:no;resizable:no;status:no;scroll:yes";
    var printFlag=window.showModalDialog(reportUrl,window,featrues);
   //var printFlag=window.open(reportUrl); 
    //if(printFlag)
    save(reportId,"办结通知书");
}

/*
 * 个性化打印一页一份
 */
function printReportSpecial()
{
    if(!validate()) return;
    var reportId="";
	var arr = findSpecialData();
	if(!IsSpace(arr)){
		reportId = arr[0];
	}
	//新增环保局个性化打印一份
		if("72"==orgId){
			reportId = "20141119152259546467";
		}
    var fieldsInfo=getFieldsInfo();
    var args=getArgs();
   var args1 = "&orgName="+orgName+"&lawLimit="+lawLimit;
    var dateV = SqlToField("select to_char(sysdate,'yyyy') ||'年'|| to_char(sysdate,'MM')||'月'||to_char(sysdate,'dd')||'日' from dual");
	//var reportUrl="../../../report/showJasperReport.jsp?raq="+reportId+args;
      var reportUrl="../../../report/showJasperReport.jsp?raq="+reportId+"&SUBREPORT_DIR="+SUBREPORT_DIR+"&date="+dateV+args1+args;
	//若reportId对应的为润乾报表(jinbo.tan 20101222新增)
	var publishState = SqlToField("select publishstate from dzzwpt.tb_report r where r.rep_id='" + reportId + "'");
	var reportType  = SqlToField("select rep_stype from dzzwpt.tb_report r where r.rep_id='" + reportId + "'");
	if("1"==publishState){
		if("0"==reportType||"1"==reportType){
			//sendAjax(reportId,args);
                                var reportUrl = "../../../report/showreport.jsp?raq="+reportId+args;
          var featrues = "dialogWidth:720px;dialogHeight:800px;center:yes;help:no;resizable:no;status:no;scroll:yes";
          window.showModalDialog(reportUrl,window,featrues);
                                save(reportId);
			return;
		}
	}
	//新增结束
    var featrues = "dialogWidth:720px;dialogHeight:800px;center:yes;help:no;resizable:no;status:no;scroll:yes";
    var printFlag=window.showModalDialog(reportUrl,window,featrues);
   //var printFlag=window.open(reportUrl); 
    //if(printFlag)
    save(reportId);

    if("1"==item_type&&"0105"==sisType){
		 if(confirm("您是否需要打印办结通知书？")){ 
			 if(confirm("确定打印一页一份，取消打印一页二份！")){ 
				printReportNew3();
			 } else {
			   printReport4();
			 }
		 }
	}
}


/*
 * 个性化打印一页两份
 */
function printReportSpecialNew()
{
    if(!validate()) return;
    var reportId="";
	 if("0101"==sisType){
	    if("249"==orgId){
			reportId = "20130905142511125820";
		}else if("67"==orgId){
			reportId = "20130912102310375544";
		}else if("41"==orgId){
			reportId = "20130925160421421298";
                     }else if("42" ==orgId){
			reportId = "20140815164726734771";

		}
	}else if("0105"==sisType){
		if("249"==orgId){
			reportId = "20130905142646031189";
		}else if("41"==orgId){
			reportId = "20130925160605406003";
		}else if("52" ==orgId){
			reportId = "20140630095934218957";
		}else if("263" ==orgId){
			reportId = "20140630095934218957";
		}else if("72" ==orgId){
			reportId = "20141119093535062455";
		}

	}
    var fieldsInfo=getFieldsInfo();
    var args=getArgs();
      var args1 = "&orgName="+orgName+"&lawLimit="+lawLimit;
    var dateV = SqlToField("select to_char(sysdate,'yyyy') ||'年'|| to_char(sysdate,'MM')||'月'||to_char(sysdate,'dd')||'日' from dual");
	//var reportUrl="../../../report/showJasperReport.jsp?raq="+reportId+args;
      var reportUrl="../../../report/showJasperReport.jsp?raq="+reportId+"&SUBREPORT_DIR="+SUBREPORT_DIR+"&date="+dateV+args1+args;
	//若reportId对应的为润乾报表(jinbo.tan 20101222新增)
	var publishState = SqlToField("select publishstate from dzzwpt.tb_report r where r.rep_id='" + reportId + "'");
	var reportType  = SqlToField("select rep_stype from dzzwpt.tb_report r where r.rep_id='" + reportId + "'");
	if("1"==publishState){
		if("0"==reportType||"1"==reportType){
			//sendAjax(reportId,args);
                                var reportUrl = "../../../report/showreport.jsp?raq="+reportId+args;
          var featrues = "dialogWidth:720px;dialogHeight:800px;center:yes;help:no;resizable:no;status:no;scroll:yes";
          window.showModalDialog(reportUrl,window,featrues);
                                save(reportId);
			return;
		}
	}
	//新增结束
    var featrues = "dialogWidth:720px;dialogHeight:800px;center:yes;help:no;resizable:no;status:no;scroll:yes";
    var printFlag=window.showModalDialog(reportUrl,window,featrues);
   //var printFlag=window.open(reportUrl); 
    //if(printFlag)
    save(reportId);

	if("1"==item_type&&"0105"==sisType){
		 if(confirm("您是否需要打印办结通知书？")){ 
			 if(confirm("确定打印一页一份，取消打印一页二份！")){ 
				printReportNew3();
			 } else {
			   printReport4();
			 }
		 }
	}

}

/*
 * 打印一页一份的报表
 */
function printReportNew()
{
    if(!validate()) return;
	var arr = findSpecialData();
	var reportId="";
    if("0101"==sisType){
		common = "common11";
	}else if("0105"==sisType){
		common = "common15";
	}
	reportId = SqlToField("select t.report_id from app_5.ta_sp_common_basereport t where t.report_type='"+sisType+"' and t.amanuensis_id='"+common+"'");
    if("1"==item_type&&"0105"==sisType){
		reportId ="20140828153151984953";
	}
	var fieldsInfo=getFieldsInfo();
    var args=getArgs();
    var args1 = "&orgName="+orgName+"&lawLimit="+lawLimit;
    var dateV = SqlToField("select to_char(sysdate,'yyyy') ||'年'|| to_char(sysdate,'MM')||'月'||to_char(sysdate,'dd')||'日' from dual");
	//var reportUrl="../../../report/showJasperReport.jsp?raq="+reportId+args;
      var reportUrl="../../../report/showJasperReport.jsp?raq="+reportId+"&SUBREPORT_DIR="+SUBREPORT_DIR+"&date="+dateV+args1+args;
	//若reportId对应的为润乾报表(jinbo.tan 20101222新增)
	var publishState = SqlToField("select publishstate from dzzwpt.tb_report r where r.rep_id='" + reportId + "'");
	var reportType  = SqlToField("select rep_stype from dzzwpt.tb_report r where r.rep_id='" + reportId + "'");
	if("1"==publishState){
		if("0"==reportType||"1"==reportType){
			//sendAjax(reportId,args);
                                var reportUrl = "../../../report/showreport.jsp?raq="+reportId+args;
          var featrues = "dialogWidth:720px;dialogHeight:400px;center:yes;help:no;resizable:no;status:no;scroll:yes";
          window.showModalDialog(reportUrl,window,featrues);
                                save(reportId);
			return;
		}
	}
	//新增结束
    var featrues = "dialogWidth:720px;dialogHeight:800px;center:yes;help:no;resizable:no;status:no;scroll:yes";
    var printFlag=window.showModalDialog(reportUrl,window,featrues);
	
   //var printFlag=window.open(reportUrl); 
    //if(printFlag)
    save(reportId);
	if("1"==item_type&&"0105"==sisType){
		 if(confirm("您是否需要打印办结通知书？")){ 
			 if(confirm("确定打印一页一份，取消打印一页二份！")){ 
				printReportNew3();
			 } else {
			   printReport4();
			 }
		 }
		 
		
	}
}

/*
 * 行政许可事项打印一页一份的办结通知书
 */
function printReportNew3()
{
    if(!validate()) return;
	var arr = findSpecialData();
	var reportId="";
    if("0101"==sisType){
		common = "common11";
	}else if("0105"==sisType){
		common = "common15";
	}
	var reportId="20140828152842296940";
	
	var fieldsInfo=getFieldsInfo();
    var args=getArgs();
    var args1 = "&orgName="+orgName+"&lawLimit="+lawLimit;
    var dateV = SqlToField("select to_char(sysdate,'yyyy') ||'年'|| to_char(sysdate,'MM')||'月'||to_char(sysdate,'dd')||'日' from dual");
	//var reportUrl="../../../report/showJasperReport.jsp?raq="+reportId+args;
      var reportUrl="../../../report/showJasperReport.jsp?raq="+reportId+"&SUBREPORT_DIR="+SUBREPORT_DIR+"&date="+dateV+args1+args;
	//若reportId对应的为润乾报表(jinbo.tan 20101222新增)
	var publishState = SqlToField("select publishstate from dzzwpt.tb_report r where r.rep_id='" + reportId + "'");
	var reportType  = SqlToField("select rep_stype from dzzwpt.tb_report r where r.rep_id='" + reportId + "'");
	if("1"==publishState){
		if("0"==reportType||"1"==reportType){
			//sendAjax(reportId,args);
                                var reportUrl = "../../../report/showreport.jsp?raq="+reportId+args;
          var featrues = "dialogWidth:720px;dialogHeight:400px;center:yes;help:no;resizable:no;status:no;scroll:yes";
          window.showModalDialog(reportUrl,window,featrues);
                                save(reportId,'办结通知书');
			return;
		}
	}
	//新增结束
    var featrues = "dialogWidth:720px;dialogHeight:800px;center:yes;help:no;resizable:no;status:no;scroll:yes";
    var printFlag=window.showModalDialog(reportUrl,window,featrues);
	
   //var printFlag=window.open(reportUrl); 
    //if(printFlag)
    save(reportId,"办结通知书");
}


/*
 * 打印一页两份的报表
 */
function printReportNew2()
{
    if(!validate()) return;
    var reportId="";
	if("0101"==sisType){
		common = "common01";
	}else if("0105"==sisType){
		common = "common05";
	}
	reportId = SqlToField("select t.report_id from app_5.ta_sp_common_basereport t where t.report_type='"+sisType+"' and t.amanuensis_id='"+common+"'");
    var fieldsInfo=getFieldsInfo();
    var args=getArgs();
    var args1 = "&orgName="+orgName+"&lawLimit="+lawLimit;
    var dateV = SqlToField("select to_char(sysdate,'yyyy') ||'年'|| to_char(sysdate,'MM')||'月'||to_char(sysdate,'dd')||'日' from dual");
	//var reportUrl="../../../report/showJasperReport.jsp?raq="+reportId+args;
      var reportUrl="../../../report/showJasperReport.jsp?raq="+reportId+"&SUBREPORT_DIR="+SUBREPORT_DIR+"&date="+dateV+args1+args;
	//若reportId对应的为润乾报表(jinbo.tan 20101222新增)
	var publishState = SqlToField("select publishstate from dzzwpt.tb_report r where r.rep_id='" + reportId + "'");
	var reportType  = SqlToField("select rep_stype from dzzwpt.tb_report r where r.rep_id='" + reportId + "'");
	if("1"==publishState){
		if("0"==reportType||"1"==reportType){
			//sendAjax(reportId,args);
                                var reportUrl = "../../../report/showreport.jsp?raq="+reportId+args;
          var featrues = "dialogWidth:720px;dialogHeight:400px;center:yes;help:no;resizable:no;status:no;scroll:yes";
          window.showModalDialog(reportUrl,window,featrues);
                                save(reportId);
			return;
		}
	}
	//新增结束
    var featrues = "dialogWidth:720px;dialogHeight:800px;center:yes;help:no;resizable:no;status:no;scroll:yes";
    var printFlag=window.showModalDialog(reportUrl,window,featrues);
   //var printFlag=window.open(reportUrl); 
    //if(printFlag)
    save(reportId);
}

//若reportId对应的为润乾报表，则用showGXHReport.jsp来显示(jinbo.tan 20101222新增)
function sendAjax(reportId,args){
    new Ajax.Request(
		"../../../report/yuqianReport.jsp?",//请求ｕｒｌ
		{
					method:"post",
					asynchronous:false,
					parameters:{prt:args},
					onSuccess:function(req){
          var reportUrl = "../../../report/showGXHReport.jsp?raq="+reportId;
          var featrues = "dialogWidth:720px;dialogHeight:800px;center:yes;help:no;resizable:no;status:no;scroll:yes";
          window.showModalDialog(reportUrl,window,featrues); 
					}
				}
		);
}