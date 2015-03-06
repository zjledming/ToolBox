document.write("<script type='text/javascript' src='<%=request.getContextPath()%>/dwrxzsp/interface/InstanceManager.js'><\/script>");
document.write("<script type='text/javascript' src='<%=request.getContextPath()%>/dwrxzsp/interface/InitializationManager.js'><\/script>");
document.write("<script type='text/javascript' src='<%=request.getContextPath()%>/dwrxzsp/engine.js'><\/script>");
document.write("<script type='text/javascript' src='<%=request.getContextPath()%>/dwrxzsp/util.js'><\/script>");
document.write("<script type='text/javascript' src='<%=request.getContextPath()%>/dwrxzsp/interface/BaseInfoManager.js'><\/script>");
document.write("<script type='text/javascript' src='<%=request.getContextPath()%>/dwrxzsp/interface/NoSubmitAttachManager.js'><\/script>");


        
//==============================================全局对象变量定义=========================================
var PageParms = {
    //新受理时为clear
    ccFormId :IsSpace(creator_getQueryString("cc_form_instanceid"))? "":creator_getQueryString("cc_form_instanceid"),
    //只有在新受理的时候才会传过来ecId，从暂存、补交告知、回退到受理过来的，须从instace表中查询获得
    ecId :IsSpace(creator_getQueryString("ec_id"))? "":creator_getQueryString("ec_id"),//得到项目基本信息ID
    //只有在新受理时才会传过来itemCode 
    itemCode : IsSpace(creator_getQueryString("item_code"))? "":creator_getQueryString("item_code"),//流程ID
    itemInsId : "",  //项目实例号
    yxtywlsh : "",  //原系统业务流水号
    //只有在新受理时才会传过来flowId 
    flowId : IsSpace(creator_getQueryString("flow_id"))? "":creator_getQueryString("flow_id"),//流程ID
    flowret : "",     //流程信息
    baseinfoVO : null,
    wfActionVO : null,
    instanceVO : null,
    applyViewVO : null,
    xzspWorkflowType : "",   //流程类别：1-普通流程 2-或分支选择 3-与分支选择
    isDynamicSelectAct : false,  //是否动态选择下一步活动
    isDynamicSelectAndAct : false, //是否动态选择下一步与活动
    isDynamicSelectUser : false, //是否动态选择或活动执行人
    nextActList : new Array(),
    instanceCode :  "",
    isChargeShow : false,
    isFromParent : false,
    isFromChild : false,
    isFromPre : false, //是否来自预受理
    isFromNew : false, //来自新受理标志
    isFromTemp : false, //来自暂存标志
    isPrepareFlow : false,//来自预受理标志 
    isFromSupply : false, //来自补交告知标志
    isFromBack : false,  //来自退回到受理的活动
    loginName:getSysElement("userAccount"),
	initItemNameHtml:""
}

//==============================================初始化及界面控制区=========================================

/**
 * 初始事项下拉
 */
function initItemNameDiv(){
	// 初始全部事项
	PageParms.initItemNameHtml = callItemNameData("");
	//选择事项，做初始操作/暂存列表/补正补齐
	var listType=creator_getQueryString("list_type");
	if (PageParms.ecId != "" || listType=="tobe_work" || listType=="tobe_work2" || listType=="worklist") {
		initByInitManager();
	}
	initItemName("ITEM_NAME","showItemNameDiv");
	// 新进入-显示div
	if (PageParms.ecId == "") {
		disPlayDiv(document.getElementById("showItemNameDiv"),PageParms.initItemNameHtml);
		// 取div下li节点，如果只有2个节点（一个类型，一个项目），那么直接选中项目
		var obj = getLiByDiv("showItemNameDiv");
		if(obj != false){
			// 选中项目
			obj.click();
		}
	}
}

/**
 * 取div下li节点，如果只有2个节点，返回节点，反之，返回false
 */
function getLiByDiv(divId){
	var div=document.getElementById(divId);  
	// 排除掉div 节点为空的情况
	if(div.childNodes.length > 0){
		var ul=div.childNodes.item(0);  
		var lis=ul.childNodes;  
		if(lis.length == 2){
			return lis.item(1);
		}
	}
	return false;
}

/**
 * 初始事项下拉菜单
 */
function initItemName(id,divId){
	var inputObj = document.getElementById(id);
	var divObj = document.getElementById(divId);
	
	//输入框失去焦点
//	inputObj.onblur=function(event){
//		event=window.event||event;
//		divObj.style.visibility="hidden";
//	}
	//按键松开
	inputObj.onkeyup=function(e){
		if(trim(inputObj.value) == ""){
			disPlayDiv(divObj,PageParms.initItemNameHtml);
		}else{
			disPlayDiv(divObj,callItemNameData(trim(inputObj.value)));
		}
	}
	
	// 点击事件
	inputObj.onclick=function(e){
		if(trim(inputObj.value) == ""){
			disPlayDiv(divObj,PageParms.initItemNameHtml);
		}else{
			disPlayDiv(divObj,callItemNameData(trim(inputObj.value)));
		}
	}
}

//显示div
function disPlayDiv(divObj,htmlStr){
	divObj.innerHTML = htmlStr;
	divObj.style.visibility="visible";
}

// 去掉前后空格
function trim(str){
	if(str != undefined){
		var reg1 = /^(\s|\u00A0)+/;
		var reg2 =/\S/;
		str = str.replace(reg1,"");
	    for(var i=str.length-1; i>=0; i--){
	    	if(reg2.test(str.charAt(i))){
	            str = str.substring(0, i+1);
	            break;
	        }
	    }
	    return str;
	}
	return "";
}

function setColor(obj){
	obj.style.backgroundColor = "#ACDFF2";
}

function resetColor(obj){
	obj.style.backgroundColor = "#FFFFFF";
}

//和database交互-尽可能减少
function callItemNameData(itemname){
	var liStr = "";
	//itemname = trim(itemname);
	var sql="select distinct v1.ec_id,v1.item_name,v1.item_code,v1.item_order,v1.flow_id,v1.package_id,v1.flow_version,v1.process_id,v2.formid,v1.Item_Type,v1.itemtype_name from"+
    " (select b.ec_id,b.item_name,b.item_code,b.item_order,f.flow_id,f.package_id,f.flow_version,f.process_id,b.ITEM_TYPE,c.itemtype_name from"+
    " app_5.ta_sp_baseinfo b left join app_5.Ta_Param_Itemtype c on c.itemtype_id = b.item_type inner join app_5.ta_sp_flowinfo f on b.ec_id=f.ec_id and b.is_valid='Y' and f.is_valid='Y'";
    if(itemname != ""){
    	sql += " where b.item_name like '%"+itemname+"%'";
    }		
    sql += ") v1"+
    " inner join"+
    " (select w.flow_id,e.formid from app_5.ta_sp_wfaction w left join app_5.ta_sp_actformconf e on w.action_id=e.action_id"+
    " where exists (select 1 from app_5.v_wfaction_user u where u.action_id=w.action_id and u.username='"+PageParms.loginName+"')"+
	" and exists (select 1 from app_5.ta_param_actdef a where a.actdef_id=w.action_codeid and a.actdef_value like '02%')"+
    " ) v2 on v1.flow_id=v2.flow_id"+
    " order by v1.item_order asc,v1.item_name asc";

	var list = executeSelect(sql,1,200);
	// 事项类别
	var sql_sxlb = " select itemtype_id,itemtype_name from app_5.Ta_Param_Itemtype";
	var list_sxlb = executeSelect(sql_sxlb,1,200);
	for ( var i = 0; i < list_sxlb.length; i++) {
		// 添加li记录 - <img style='top:5px;' src='../../../images/leftmenu_01.gif'/>
		// 过滤掉事项类别下无事项的情况
		var tempStr = "";
		for ( var j = 0; j < list.length; j++) {
			if (list[j][9] == list_sxlb[i][0]) {
				tempStr += "<li style='cursor:pointer;position: relative;' onmouseover='setColor(this)' onmouseout='resetColor(this)'" +
						" onclick=gotoItemUrl('"+list[j][0]+"','"+list[j][1]+"','"+list[j][7]+"','"+list[j][6]+"','"+list[j][5]+"','"+list[j][4]+"','"+list[j][8]+"','"+list[j][2]+"')>"+list[j][1]+"</li>";
			}
		}
		if(tempStr != ""){
			liStr += "<li style='background-color:#e2ecf6;color:#234773;font-weight:bold;'>"+list_sxlb[i][1]+"</li>"+tempStr;
		}
	}
	// 返回div html
	if(liStr == ""){
		return "";
	}else{
		return "<ul>"+liStr+"</ul>";
	}
}


/**
 * 点击事项
 */
function gotoItemUrl(ec_id, item_name, process_id, flow_version, package_id, flow_id,formDefId,item_code){
	dwr.engine.setAsync(false); //将DWR请求设置为同步   
	NoSubmitAttachManager.hasNoSubmitAttach(ec_id,function(flag){
			if(flag&&window.confirm("该审批事项下有之前未受理项目上传的材料附件，是否删除")){
				NoSubmitAttachManager.deleteNoSubmitAttach(ec_id,function(){});
			}
	})
	var mgrName = package_id+"%23"+flow_version+"%23"+process_id;
	var url = "../../../common_workflow/start_form.jsp?mgrName="+mgrName+"&formDefId="+formDefId+"&ec_id="+ec_id+"&flow_id="+flow_id+"&item_code="+item_code+"&back_prevpage=true&fristPage=2"; 
	window.open(url,"perspective_content");
}


/**
 * 初始化
 * 本界面有4种入口，分别是新受理、来自暂存、来自补交告知、退回到受理
 * 系统默认只展示项目、申请人的主要填写区域，可点向下箭头展开填写更多
 * 系统默认不显示收费信息和意见列表，只有事项定义为收费的才小时收费，只有回退到受理的才显示意见列表
 */
function initByInitManager(){
    dwr.engine.setAsync(false); //将DWR请求设置为同步
    hideOrShowTabByName("收费信息","hide"); //默认先隐藏收费信息页面
    if(PageParms.ccFormId=='clear'){//20091207黄艺平
       hideOrShowTabByName("意见列表","hide"); //默认先隐藏意见列表页面 
    }
    var paraMap = {
        "initType" : "1", //初始化类别：受理固定是1
        "cc_form_instanceid" : PageParms.ccFormId,
        "userName" : getSysElement("userAccount"),
        "opType" : getFlowOpType(),
        "mgrName" : creator_getQueryString("mgrName"),
        "actDefId" : creator_getQueryString("actDefId"),
        "operType" : creator_getQueryString("operType"),
        "ecId" : PageParms.ecId,
         flowId : PageParms.flowId
    };
   
    try{
        InitializationManager.init(paraMap, function(rtnData){
            handleRtnData(rtnData);
        });
    }catch(e){
        alert(e);
    }
    initActComponent(); 
    initActiveReport(PageParms.flowId,creator_getQueryString("actDefId"),$('btnPrtAccept'));//20091015张华俊
    initActionExtForStart(PageParms.flowId,creator_getQueryString("actDefId"));
    serarchField("CORP_NAME","showCorpNameDiv","ta_sp_applycorp","applycorp_id",
                 "corp_accounts,corp_name",findCorpInfo); //初始化单位名模糊查询
    serarchField("APPLICANT_NAME","showPersonNameDiv","ta_sp_applicant","applicant_id",
                 "accounts,applicant_name",findPersonInfo);//初始化用户名名模糊查询
    initByListType();
    if(IsSpace($('CERT_TYPE').value)){
        $('CERT_TYPE').value = "身份证";
    }

  if(!IsSpace(PageParms.ccFormId)){
    var relator_sql = "select RELATOR, RELATOR_MOBILE from app_5.ta_sp_corprelator where item_insid = (select item_insid from app_5.ta_sp_instance i where i.cc_form_instanceid = '"+PageParms.ccFormId +"')  " ;
    relator_dataset.Open(relator_sql);
    if(relator_dataset.RecordCount == 1){
      $('RELATOR').value = relator_dataset.Fields.Field['RELATOR'].Value;
      $('RELATOR_MOBILE').value = relator_dataset.Fields.Field['RELATOR_MOBILE'].Value;
    }
  }

}



//根据AJAX请求的数据结果，填充界面及生成界面元素
function handleRtnData(rtnData){
    var errMsg = rtnData["error"];
    if(!IsSpace(errMsg)){
        throw new Error(errMsg);
    }
    PageParms.flowret = rtnData["flowret"];  //获取流程定义信息
    PageParms.baseinfoVO = rtnData["baseinfoVO"]; //获取事项定义信息
    PageParms.wfActionVO = rtnData["wfActionVO"];
    initDynamicWorkflow(PageParms.flowret); //初始化动态选择执行人和动态选择执行活动，视情况隐藏或展示
    initIsChargeShow(); //控制收费界面展示 
    if(isNewItem()){ //新受理
        initshowItemName();//黄艺平增加(2009-10-20)
        PageParms.isFromNew = true;  
        var defaultApplyType = xzsp.util.CookieManager.getDefaultApplyType(PageParms.baseinfoVO.ec_id); 
        if(IsSpace(defaultApplyType)){
            defaultApplyType = "1";  //默认显示申请单位
        }
        initSelectApplyer(defaultApplyType); //申请申请单位或申请人单选按钮
        showApplyCtrl(defaultApplyType); //显示申请人或申请单位
    }else{  //来自暂存、补交告知、退回到受理等
        disableApplySelect(); //使选择按钮不可选（只有新受理项目才可选）
        PageParms.instanceVO = rtnData["instanceVO"];  //获取项目实例信息
        PageParms.applyViewVO = rtnData["applyViewVO"]; //获取申请者信息
        initGlobalFields();
        setRadioChecked("sex",PageParms.applyViewVO.sex);  //根据数据情况选择男或女
        setRadioChecked("corpCertType",PageParms.applyViewVO.corp_certtype);  //根据数据情况选择男或女
        initSelectApplyer(PageParms.instanceVO.apply_type); //申请申请单位或申请人单选按钮
        showApplyCtrl(PageParms.instanceVO.apply_type); //显示申请人或申请单位
        initRenderBusiData(); //展示业务数据
    }
    initTop(); //初始化头部
}


/* 初始化动态选择执行人和动态选择执行活动，视情况隐藏或展示 */
function initDynamicWorkflow(flowret){
    var ret = initDynamicWorkflowSet(flowret,"start"); //初始化动态选择
    PageParms.xzspWorkflowType = ret["xzspWorkflowType"];
    PageParms.isDynamicSelectAct = ret["isDynamicSelectAct"];
    PageParms.isDynamicSelectUser = ret["isDynamicSelectUser"];
    PageParms.isDynamicSelectAndAct = ret["isDynamicSelectAndAct"];
    PageParms.nextActList = ret["nextActList"];    
}


/* 初始化头部：从列表传过来 */
function initTop(){
    $('divItemName').innerHTML = PageParms.baseinfoVO.item_name;
	// 事项赋值
	document.getElementById("ITEM_NAME").value = PageParms.baseinfoVO.item_name;
    var crntTache = ""; //当前环节展示修改（By张华俊20091005）
    if(PageParms.isFromNew){  //新受理项目
        crntTache = xzsp.api.BaseService2InstanceAPI.getActionAlias(PageParms.flowId,creator_getQueryString("actDefId"));
        if(IsSpace(crntTache)){
            crntTache = "受理";
        }
    }else{  //暂存、在办或已办结
        crntTache  = getCurrentTache(PageParms.ccFormId);
        if(IsSpace(crntTache)){
            crntTache = "已办结";
        }
    }
    $('divCurrentTache').innerHTML = "当前环节："+crntTache;
}


/* 初始化一部分全局变量，包括是否收费、事项ID、项目实例ID等 */
function initGlobalFields(){
    PageParms.ecId = PageParms.baseinfoVO.ec_id;
    PageParms.itemCode = PageParms.baseinfoVO.item_code;
    PageParms.itemInsId = PageParms.instanceVO.item_insid;
    PageParms.flowId = PageParms.instanceVO.flow_id; 
}

/* 显示选择申请人或选择申请单位 */
function disableApplySelect(){
    var oRadios = $('applyTypeSelect').getElementsByTagName("input");
    for(var i=0; i<oRadios.length; i++){
        oRadios[i].disabled = true;
    }
    $('imgApply').style.display = "none";
}


/* 界面控制：决定是否展示收费 */
function initIsChargeShow(){
    if(PageParms.baseinfoVO.ischarge == "Y"){
        hideOrShowTabByName("收费信息","show");    
    }
}


/* 选中申请单位或申请人 */
function initSelectApplyer(applyType){
    var applyBoxes = document.getElementsByName("RGapplyTypeCtrl");
    for(var i=0; i<applyBoxes.length; i++){
        if(applyType == applyBoxes[i].value){
            applyBoxes[i].checked = true;
            break;
        }
    }
}

/* 初始化业务数据，把业务数据从JS对象中取出来，放到每个字段中去 */
function initRenderBusiData(){
    xzsp.util.DBUtil.renderPageByVO(PageParms.instanceVO); //render项目实例信息
    xzsp.util.DBUtil.renderPageByVO(PageParms.applyViewVO);//render申请者信息
}


/* 根据不同的列表类别做界面控件的隐藏或显示 */
function initByListType(){
   if(creator_getQueryString("operType") == "nosend"){ //暂存
        var listType=creator_getQueryString("list_type");
        if(listType=="tobe_work"){
           PageParms.isFromTemp = true;
           $('btnNotAccept').style.display = "none";
        }else{
           PageParms.isFromSupply = true;
           initFromSupply();
        }
    }else if(creator_getQueryString("operType") == "daiban"){ //回退到受理
        PageParms.isFromBack = true;
        initFromBack();
    }else if(creator_getQueryString("operType") == "readonly" || creator_getQueryString("operType") == "onuse"){
        $('btnBack').style.display = "";
    }
}


/**
 * 初始化：来自补交告知
 * 以下按钮将不可点：暂存、预受理查询、打印受理通知书、受理、不予受理办件
 */
function initFromSupply(){
    initHideDynamic();
    $('btnTemp').style.display = "none";
    $('btnPreQuery').style.display = "none";
    $('btnPrtAccept').style.display = "none";
    $('btnAccept').style.display = "none";
    $('btnNotAccept').style.display = "none";
    $('btnSupplyInform').disabled = true;
    $('btnSupplyAccept').style.display = "";
}


/**
 * 初始化：退回到受理
 */
function initFromBack(){
    $('btnTemp').style.display = "none";
    $('btnPreQuery').style.display = "none";
    $('btnPrtAccept').style.display = "none";
    $('btnAccept').innerHTML = "完成活动";
    $('btnAccept').style.display = "";
    $('btnAccept').disabled = false;
    $('btnNotAccept').style.display = "none";
    $('btnSupplyInform').style.display = "none";
    $('btnSupplyAccept').style.display = "none";
}


/* 选择显示哪种申请者类型 */
function chooseApply(robj){
    if(robj.value == "1"){
        $("applyCorpArea").style.display="";
        $("applicantArea").style.display="none";
        $('applicantDefaultHideArea').style.display="none";
    }else{
        $("applyCorpArea").style.display="none";
        $("applicantArea").style.display="";
        $('applyCorpDefaultHideArea').style.display="none";
    }
}

//判断是否来自父流程
function isFromParent(){
    return true;
}

//判断是否从子流程返回
function isFromChild(){
    return true;
}


//==============================================功能按钮控制区=========================================


/* 暂存逻辑 */
function doTemp(){
    if(!checkByDataset() || !checkByReg()){
        return;
    }
    if(IsSpace(creator_getSession("cc_form_instanceid")) || (creator_getSession("cc_form_instanceid")=="clear")){
        PageParms.ccFormId = getNewCc_form_instanceid("true"); //生成一个新的业务ID
        creator_setSession("cc_form_instanceid",PageParms.ccFormId);
    }
    var flowData = getFlowData();  //获取流程相关数据
    var inputDatas = getInputData(); //获取用户输入的相关数据
    createOverlayDIV();
    InstanceManager.doTemp(flowData,inputDatas[0],inputDatas[1],inputDatas[2],inputDatas[3],defaultCallback);
}


/* 打开预受理查询表单 */
function openPreQueryView(){
    var args = "&cc_form_instanceid="+creator_getSession("cc_form_instanceid")+"&ec_id="+PageParms.ecId;
    var viewAddr = "20090513085936250445.jsp?cc_form_instanceid="+creator_getSession("cc_form_instanceid")+args;
    var featrues = "dialogWidth:820px;dialogHeight:450px;center:yes;help:no;resizable:no;status:no;scroll:no";//2009-12-31黄艺平修改
    var rtn = window.showModalDialog(viewAddr,window,featrues); 
    if(!IsSpace(rtn)){ //rtn为预受理实例的项目实例ID
        PageParms.isFromPre = true; //设置是否来自外网预受理
        InstanceManager.doSaveFromPre(rtn,doCallBackForInitFromPre);
    }
}


//预受理查询回调函数
function doCallBackForInitFromPre(rtnData){
    var errorMsg = rtnData["error"];
    if(!IsSpace(errorMsg)){  //张华俊20091012修改
        alert(errorMsg);
        return;
    }
    PageParms.instanceVO = rtnData["instanceVO"];
    PageParms.applyViewVO = rtnData["applyViewVO"];
    PageParms.itemInsId = PageParms.instanceVO.item_insid;  
    initRenderBusiData(); //展示业务数据
    setRadioChecked("sex",PageParms.applyViewVO.sex);  //根据数据情况选择男或女
    initSelectApplyer(PageParms.instanceVO.apply_type); //申请申请单位或申请人单选按钮
    showApplyCtrl(PageParms.instanceVO.apply_type); //显示申请人或申请单位
    initPreAttachList(PageParms.itemInsId); //加载预受理材料列表
}


/* 打印受理通知书，打印完后显示“受理”按钮 */
function doPrintAccept(){
//    if(!checkByDataset() || !checkByReg()){
//        return;
//    }
    if(!validate()) return;
    
    if(isNewItem() || PageParms.isFromTemp){  //只有新受理、暂存时才需要产生流水号
        genInstanceCode(); //生成受理流水号
    }else if(isFromPre()){
        PageParms.yxtywlsh = $('INSTANCE_CODE').value;
        //genInstanceCode(); //生成受理流水号
    }
    var baseReportId = getBaseReportByType(PageParms.ecId, 1);
    if(IsSpace(baseReportId)){
        var actDefId = creator_getQueryString("actDefId");
        var reportId = xzsp.api.BaseService2InstanceAPI.getReportId(PageParms.flowId,actDefId,"01");
        if(IsSpace(reportId)){
            reportId = getDefaultReportId("01");
        }
        printReportByRaqId(reportId);
        
    }else{
        printBaseReport(baseReportId);
        
    }
    $('btnTemp').disabled = true;
    $('btnAccept').disabled = false;
}


//覆盖workflow.js中的函数
function getReportParam(){
    var reportParam = new Array();
    reportParam.push("&instance_code=");
    if(PageParms.isFromPre){
        reportParam.push(PageParms.yxtywlsh);
    }else{
        reportParam.push($('INSTANCE_CODE').value);
    }
    var applyDate = "";
    if(PageParms.isFromPre){
        applyDate = xzsp.api.WebServ2InstanceAPI.getApplyDate(PageParms.itemInsId);
    }else{
        applyDate = getCrntDateFmt1FromDB();
    }
    reportParam.push("&apply_year=");
    reportParam.push(applyDate.substr(0,4));
    reportParam.push("&apply_month=");
    reportParam.push(applyDate.substr(5,2));
    reportParam.push("&apply_day=");
    reportParam.push(applyDate.substr(8,2));
    reportParam.push("&item_name=");
    reportParam.push(PageParms.baseinfoVO.item_name);
    reportParam.push("&item_insname=");
    reportParam.push(getContentByLimit($("ITEM_INSNAME").value,200));
    reportParam.push("&remark1=");
    reportParam.push(getContentByLimit($("REMARK1").value,500));
    reportParam.push("&apply_name=");
    reportParam.push(getApplyName());
	reportParam.push("&ec_id=");
    reportParam.push(PageParms.ecId);
    
   //0816ly修改
    reportParam.push("&start_year=");
    reportParam.push(applyDate.substr(0,4));
    reportParam.push("&start_month=");
    reportParam.push(applyDate.substr(5,2));
    reportParam.push("&start_day=");
    reportParam.push(applyDate.substr(8,2));
    var job_time = SqlToField("select ITEM_LIMIT from app_5.ta_sp_baseinfo where ec_id='"+PageParms.ecId+"'");
    reportParam.push("&banjie_day=");
    reportParam.push(job_time);
   //算工作日job_time后的日期
     var end_time = SqlToField("select (to_date('"+applyDate+"','yyyy-mm-dd')+"+job_time+") from dual"); //默认
     var day = 0; //统计工作日天数
     var addDay = 1;
      var sql_day="select count(*) from dzzwpt.td_sp_holiday t where to_date(t.holiday,'yyyy-mm-dd') "+
            " between to_date('"+applyDate.substr(0,10)+"','yyyy-mm-dd') and to_date('"+end_time.substr(0,10)+"','yyyy-mm-dd')";
      var sql_dayV = SqlToField(sql_day);      
     while(day<sql_dayV){
         // alert(day);
        temp= SqlToField("select (to_date('"+end_time.substr(0,10)+"','yyyy-mm-dd')+"+addDay+") from dual");
          // alert(temp);
         if(IsSpace(temp)){
              return;
         }
         end_time=temp.substr(0,10);
         var sql = "select * from dzzwpt.td_sp_holiday t where to_date(t.holiday,'yyyy-mm-dd')=to_date('"+end_time+"','yyyy-mm-dd')";
         //alert(sql);
         var dayFlag = SqlToField(sql);
         //alert(dayFlag);
         if(IsSpace(dayFlag)){
              day++;
         }
        //addDay++;
     }
     //工作日。。。end...
    reportParam.push("&end_year=");
    reportParam.push(end_time.substr(0,4));
    reportParam.push("&end_month=");
    reportParam.push(end_time.substr(5,2));
    reportParam.push("&end_day=");
    reportParam.push(end_time.substr(8,2));
    
   //...0816ly修改end...

    
    if(getApplyType()=="1")//申请单位
    {
        if(!IsSpace($("APPLYCORP_ID").value)) //如果申请单位不为空，添加申请单位信息到参数
        {
            reportParam.push("&applycorp_id="); //申请单位ID
            reportParam.push($("APPLYCORP_ID").value);
        }
        if(!IsSpace($("CORP_CERTTYPE").value)) 
        {
            reportParam.push("&corp_certtype="); //申请单位证件类型        
            reportParam.push($("CORP_CERTTYPE").value);
        }
        if(!IsSpace($("CORP_ACCOUNTS").value)) 
        {
            reportParam.push("&corp_accounts="); //申请单位标示
            reportParam.push($("CORP_ACCOUNTS").value);
        }
        if(!IsSpace($("CORP_NAME").value)) 
        {
            reportParam.push("&corp_name=");  //申请单位名
            reportParam.push($("CORP_NAME").value);
        }
        if(!IsSpace($("CORP_ADDR").value)) //添加单位地址
        {
            reportParam.push("&corp_addr=");
            reportParam.push($("CORP_ADDR").value);
        }
        if(!IsSpace($("CORP_TYPE").value)) //添加单位性质
        {
            reportParam.push("&corp_type=");
            reportParam.push($("CORP_TYPE").value);
        }
        if(!IsSpace($("RELATOR").value)) //添加单位联系人
        {
            reportParam.push("&relator=");
            reportParam.push($("RELATOR").value);
        }
        if(!IsSpace($("RELATOR_MOBILE").value)) //联系人电话
        {
            reportParam.push("&relator_mobile=");
            reportParam.push($("RELATOR_MOBILE").value);
        }
        if(!IsSpace($("CORPORATOR").value))//法人代表
        {
            reportParam.push("&corporator=");
            reportParam.push($("CORPORATOR").value);
        }
        if(!IsSpace($("CORPORATOR_PHONE").value))//法人代表电话
        {
            reportParam.push("&corporator_phone=");
            reportParam.push($("CORPORATOR_PHONE").value);
        }
    }else if(getApplyType()=="2"){ //申请人
        if(!IsSpace($("APPLICANT_ID").value))
        {
            reportParam.push("&applicant_id="); //申请人ID
            reportParam.push($("APPLICANT_ID").value);
        }
        if(!IsSpace($("CERT_TYPE").value))
        {
            reportParam.push("&cert_type="); //申请证件类型
            reportParam.push($("CERT_TYPE").value);  
        }
        if(!IsSpace($("ACCOUNTS").value))
        {
            reportParam.push("&accounts=");  //申请人标示(证件号码)
            reportParam.push($("ACCOUNTS").value); 
        }
        if(!IsSpace($("APPLICANT_NAME").value))
        {
            reportParam.push("&applicant_name=")//申请人姓名
            reportParam.push($("APPLICANT_NAME").value);
        }
    
        if(!IsSpace($("SEX").value))
        {
            reportParam.push("&sex="); //申请人ID
            reportParam.push($("SEX").value);
        }
        if(!IsSpace($("MOBILE").value))
        {
            reportParam.push("&mobile="); //申请人手机
            reportParam.push($("MOBILE").value);
        }
        if(!IsSpace($("PHONE").value))
        {
            reportParam.push("&phone="); //申请人电话
            reportParam.push($("PHONE").value);
        }
        if(!IsSpace($("ADDRESS").value))
        {
            reportParam.push("&address="); //申请人地址
            reportParam.push($("ADDRESS").value);
        }
        if(!IsSpace($("POST").value))
        {
            reportParam.push("&post="); //申请人邮编
            reportParam.push($("POST").value);
        }
        if(!IsSpace($("EMAIL").value))
        {
            reportParam.push("&email="); //申请人邮箱
            reportParam.push($("EMAIL").value);
        }
    }
   //alert(reportParam);
    return reportParam.join("");
}


/* 打开动态选择执行人界面 */
function openOrSelectUserView(){
    var oUserId = document.getElementById("DYNAMICPERFORMER_ID");
    var oUserName = document.getElementById("DYNAMICPERFORMER");
    var oUserRealName = document.getElementById("DYNAMICPERFORMER_REALNAME");
    var actDefId = "";
    if(PageParms.xzspWorkflowType == "1"){  //普通流程
        actDefId = $('btnAccept').getAttribute("actid");
    }else if(PageParms.xzspWorkflowType == "2"){  //或选择
        actDefId = $('xzsp_workflowControl_selectAct').value;
    }
    openSelectUserTree(oUserId,oUserName,oUserRealName,false,true,PageParms.flowId,actDefId);
}



/* 受理按钮响应事件 */
function doAccept(){
//    if(!checkByDataset() || !checkByReg()){
//        return;
//    }
    if(!validate()) return;
    if(!isDynamicActAndUserSelected()){
        return;
    }
    if(IsSpace(creator_getSession("cc_form_instanceid")) || (creator_getSession("cc_form_instanceid")=="clear")){
        PageParms.ccFormId = getNewCc_form_instanceid("true"); //生成一个新的业务ID
        creator_setSession("cc_form_instanceid",PageParms.ccFormId);
    }
    var flowData = getFlowData();  //获取流程相关数据
    var inputDatas = getInputData(); //获取用户输入的相关数据
    var paraMap = new Array();
    paraMap["xzspWorkflowType"] = PageParms.xzspWorkflowType;
    paraMap["isDynamicSelectUser"] = PageParms.isDynamicSelectUser;
    paraMap["flowId"] = PageParms.flowId;
    paraMap["nextActList"] = PageParms.nextSelectedActList; //与分支活动选择的下一步活动列表
    paraMap["countryId"] = xzsp.api.BaseService2InstanceAPI.getUserCountryId(PageParms.flowId,creator_getQueryString("actDefId"));//quan.zhou 2010.2.23
    var flowCtrlInfo = buildFlowCtrlMap(paraMap, "start");//获取流程控制信息
    xzsp.util.CookieManager.setDefaultApplyType(PageParms.ecId,getRadioValue("applyTypeCtrl"));
    
    /* 短信模块代码 begin */
    //以下为取得下一环节的执行人，分为一般流程和与流程，用于发短信
	if("3" == PageParms.xzspWorkflowType){
		userlist=getAndMan(flowCtrlInfo.ANDSPLITUSERS);
	}else{
		userlist=flowCtrlInfo.DYNAMICPERFORMER; 
	}
    /* end */
    
    if(confirm("您确定要受理吗？")){
      document.getElementById("btnAccept").disabled = true;
      createOverlayDIV();
      //InstanceManager.doAccept(flowData,flowCtrlInfo,inputDatas[0],inputDatas[1],inputDatas[2],inputDatas[3],defaultCallback);
      InstanceManager.doAccept(flowData,flowCtrlInfo,inputDatas[0],inputDatas[1],inputDatas[2],inputDatas[3],defaultCallbackMess);
    }
   //jumpActive(PageParms.ccFormId);
}



/* 短信模块代码 modify by xianlu.lu 2010/12/07 begin */

var userlist;  //定义一个全局变量，获取下一环节的处理人，在受理是初始化

/*
**定义函数，覆盖，操作成功的与否的函数，实现短信发送功能
*/
function defaultCallbackMess(rtnData) {
	var fId = PageParms.flowId; // 获取流程ID
	var defId = creator_getQueryString("actDefId");// 获取活动定义ID
	var ins_code = $('INSTANCE_CODE').value; // 获取流水号
	var sql = "select t.model_id from app_5.ta_sp_wfaction t where t.flow_id='"
			+ fId + "' and t.action_defid='" + defId + "'";
	var modelId = SqlToField(sql);
	var errMsg = rtnData;
	
	if (IsSpace(errMsg)) {
		var action_name = getHJName(fId, defId);
		
		// 给工作人员发短信
		var  ec_id= PageParms.ecId;
	    var  ins_code= $('INSTANCE_CODE').value;
	    // 下一环节名称
		var next_actionname = PageParms.nextActList[0][1];
	    // 参数：当前环节名称，事项id，流水号，下一环节的处理人
	    var url1="../../../ccapp/app_5/jsp/mseeage/sendMember.jsp?action_name="+action_name+"&"
	                 +"ec_id=" + ec_id + "&instance_code=" + ins_code +"&userlist="+userlist+"&next_actionname="+next_actionname;
	    window.open(url1,"frame1");  

		// 发送给申请人或者申请单位
		var url2 = "../../../ccapp/app_5/jsp/mseeage/sendNotice.jsp?action_name="
				+ action_name
				+ "&"
				+ "modelId="
				+ modelId
				+ "&instance_code="
				+ ins_code;
		window.open(url2, "frame2");
		// 执行默认回调函数
		defaultCallback();
	}
}

/**
**获取与活动的下一环节的执行人
**/
function getAndMan(AndMan){
 var tem="";
 var arr=AndMan.split(";");
 for(var i=0;i<arr.length;i++){ 
    if(i==arr.length-1){
       tem += arr[i].split(":")[1];
     }else{
       tem += arr[i].split(":")[1]+",";
     }
 }
 return tem;
}

/**
**获取当前环节名称
**/
function getHJName(fId,defId){
     var sql="select t.action_name from app_5.ta_sp_wfaction t where t.flow_id='" + fId +"' and t.action_defid='" + defId + "'";
    var action_name =SqlToField(sql);
    return action_name;
}

/* 打开不予受理办件表单 */
function openNotAcceptView(){
//    if(!checkByDataset() || !checkByReg()){
//        return;
//    }
    if(!validate()) return;
    
    if(IsSpace($("INSTANCE_CODE").value))
    {
        genInstanceCode(); //生成受理流水号
     }
    
    var args = getCommonParam();
    var viewAddr = "20090501135508984913.jsp?cc_form_instanceid="+creator_getSession("cc_form_instanceid")+args;
    var featrues = "dialogWidth:760px;dialogHeight:300px;center:yes;help:no;resizable:no;status:no;scroll:no";//20100115黄艺平修改
    var rtn = window.showModalDialog(viewAddr,window,featrues);   
    if(rtn == true){
        backToWorklist();
    } 
}



/**
 * 获取下一环节处理人
 */
function getNextUserList(){
	var result = "";
	
	var paraMap = new Array();
    paraMap["xzspWorkflowType"] = PageParms.xzspWorkflowType; // 流程类别：1-普通流程 2-或分支选择 3-与分支选择
    paraMap["isDynamicSelectUser"] = PageParms.isDynamicSelectUser; // 是否动态选择或活动执行人
    paraMap["flowId"] = PageParms.flowId; // 流程id
    paraMap["nextActList"] = PageParms.nextSelectedActList; // 与分支活动选择的下一步活动列表
    paraMap["countryId"] = xzsp.api.BaseService2InstanceAPI.getUserCountryId(PageParms.flowId,creator_getQueryString("actDefId"));
    var flowCtrlInfo = buildFlowCtrlMap(paraMap, "start"); // 获取流程控制信息
    
    if("3" == PageParms.xzspWorkflowType){
    	result=getAndMan(flowCtrlInfo.ANDSPLITUSERS);
	}else{
		result=flowCtrlInfo.DYNAMICPERFORMER; 
	}
    return result;
}


/* 打开补交告知办件表单 */
function openSupplyInformView(){
//    if(!checkByDataset() || !checkByReg()){
//        return;
//    }
	
	
    if(!validate()) return;
    
    if(IsSpace($("INSTANCE_CODE").value)){
        genInstanceCode(); //生成受理流水号
    }
    var args = getCommonParam();
    
    // 下一环节名称
    //var next_actionname = PageParms.nextActList[0][1];
    var viewAddr = "20090501140813203389.jsp?cc_form_instanceid="+creator_getSession("cc_form_instanceid")+args;
    var featrues = "dialogWidth:774px;dialogHeight:500px;center:yes;help:no;resizable:no;status:no;scroll:no";//20100115黄艺平修改
    var rtn = window.showModalDialog(viewAddr,window,featrues); 
    if(rtn == true){
        backToWorklist();
    } 
}


/* 打开补交受理办件表单 */
function openSupplyAcceptView(){
//    if(!checkByDataset() || !checkByReg()){
//        return;
//    }
    if(!validate()) return;
    
    var args = getCommonParam();
    
    // 获取短信服务参数
	var action_name = getHJName(PageParms.flowId, creator_getQueryString("actDefId"));
	// 下一环节名称
	var next_actionname = PageParms.nextActList[0][1];
	
	var viewAddr = "20090501141817140170.jsp?cc_form_instanceid="+creator_getSession("cc_form_instanceid")+args+"&action_name="+action_name+"&next_actionname="+next_actionname;
    var featrues = "dialogWidth:724px;dialogHeight:500px;center:yes;help:no;resizable:no;status:no;scroll:no";//20100115黄艺平修改
    //alert(viewAddr);
    var rtn = window.showModalDialog(viewAddr,window,featrues); 
    if(rtn == true){
        backToWorklist();
    } 
}


//==============================================取界面参数区============================================


/* 获取流水号 */
function genInstanceCode(){
    if(IsSpace($('INSTANCE_CODE').value) || $('INSTANCE_CODE').value.charAt(0)=='W'){//新受理或外网预受理
        BaseInfoManager.getInstanceCode(PageParms.itemCode,function(rtnData){
            $('INSTANCE_CODE').value = rtnData;
            PageParms.instanceCode = rtnData;
        });
    }
}


/* 获取流程信息 */
function getFlowData(){
    var remoteAddr = location.host;
    if(remoteAddr.indexOf("localhost") != -1){
        remoteAddr = "127.0.0.1";
    }else{
        remoteAddr = remoteAddr.substr(0,remoteAddr.indexOf(":"));
    }
    var mgrName = creator_getQueryString("mgrName");
    var flowData = {
        "mgrName" : creator_getQueryString("mgrName"),
        "formDefId" : creator_getQueryString("djid"),
        "actDefId" : creator_getQueryString("actDefId"),
        "actInsId" : creator_getQueryString("actInsId"),
        "remoteAddr" : remoteAddr,
        "busiId" : creator_getSession("cc_form_instanceid")
    }
    return flowData;
}


/* 获取流程控制信息：提供给暂存、补交告知等使用 */
function getFlowCtrlInfoForTemp(){
    var flowControlInfo = {
        "DYNAMICPERFORMER" : getSysElement("userName")
    };
    return flowControlInfo;
}

/* 获取流程控制信息：提供给受理、不予受理、补交受理、预受理转受理等使用 */
function getFlowCtrlInfoForAccept(){
    var nextActUserNameList = getNextActUserNameList(PageParms.flowId,creator_getQueryString("actDefId"));
    var nextActUser = xzsp.util.getStrFromArray(nextActUserNameList);
    var flowControlInfo = {
        "DYNAMICPERFORMER" : nextActUser
    };
    return flowControlInfo;
}

/* 获取界面参数值，包括项目信息、申请者信息、材料信息、收费信息等 */
function getInputData(){
    var inputDatas = new Array();
    var instanceVO = xzsp.util.DBUtil.getVoFromPage(new xzsp.vo.Instance());
    instanceVO.ec_id = PageParms.ecId; //设置EC_ID的值
    instanceVO.item_insid = PageParms.itemInsId; //设置ITEM_INSID的值
    instanceVO.flow_id = PageParms.flowId; //设置FLOW_ID的值
    if(instanceVO.status==""||PageParms.isFromPre){//20100127黄艺平修改
       instanceVO.datasource = '0';//20100104黄艺平修改

    }
    instanceVO.apply_type = getApplyType(); 
    if(PageParms.instanceVO){ //如果是回退回来或暂存数据，则设置申请者ID
        instanceVO.apply_id = PageParms.instanceVO.apply_id;
    }
    instanceVO.accept_time = null;
    instanceVO.country_id= xzsp.api.BaseService2InstanceAPI.getUserCountryId(PageParms.flowId,creator_getQueryString("actDefId"));//quan.zhou 2009.11.04
    instanceVO.cc_form_instanceid = creator_getSession("cc_form_instanceid");
    inputDatas[0] = instanceVO;
    var applyViewVO = xzsp.util.DBUtil.getVoFromPage(new xzsp.vo.ApplyView());//alert(applyViewVO.corp_certtype);
    applyViewVO.cc_form_instanceid = creator_getSession("cc_form_instanceid");
    inputDatas[1] = applyViewVO;
    var attachList = getAttachList();
    inputDatas[2] = attachList;
    if(PageParms.baseinfoVO.ischarge == "Y"){
        var chargeList = getChargeList(); 
        inputDatas[3] = chargeList;
    }
    return inputDatas;
}


/* 获得常用参数 */
function getCommonParam(){

    var subStatusIds = getSubmittedStatusAttachIds();

    var args = new Array();
    args.push("&flow_id=");args.push(PageParms.flowId);
    args.push("&actdef_id=");args.push(creator_getQueryString("actDefId"));
    args.push("&ec_id="); args.push(PageParms.ecId);
    args.push("&item_insid=");args.push(PageParms.itemInsId);
    args.push("&item_insname=");args.push($('ITEM_INSNAME').value);
    args.push("&item_name=");args.push(PageParms.baseinfoVO.item_name);
    args.push("&apply_type=");args.push(getApplyType());
    args.push("&apply_name=");args.push(getApplyName());
    args.push("&instance_code=");args.push($('INSTANCE_CODE').value);
    args.push("&attach_id=");args.push(subStatusIds);
   
    var applyDate = "";
    if(PageParms.isFromPre){
        applyDate = xzsp.api.WebServ2InstanceAPI.getApplyDate(PageParms.itemInsId);
    }else{
        applyDate = getCrntDateFmt1FromDB();
    }
    args.push("&apply_year=");args.push(applyDate.substr(0,4));
    args.push("&apply_month=");args.push(applyDate.substr(5,2));
    args.push("&apply_day=");args.push(applyDate.substr(8,2));
    return args.join("");
}


//判断是否是新受理项目：业务ID为空、不是来自预受理
function isNewItem(){
    if((IsSpace(PageParms.ccFormId) || PageParms.ccFormId=="clear") && PageParms.isFromPre==false){ 
        return true;
    }
    return false;
}

//判断是否是来自外网预受理的项目
function isFromPre(){
    return PageParms.isFromPre;
}

/* 判断申请者类型是否为申请单位，如果不是，则是申请人 */
function isApplyTypeCorp(){
    return (getRadioValue("applyTypeCtrl") == "1");
}


//活动申请者类别
function getApplyType(){
    return isApplyTypeCorp()?"1":"2"; 
}


//获得申请者姓名
function getApplyName(){
    return isApplyTypeCorp()?$('CORP_NAME').value:$('APPLICANT_NAME').value;
}


/* 获取材料列表 */
function getAttachList(){
    var attachSubFormObj = window.frames("attachSubForm");
    if(attachSubFormObj){
        return attachSubFormObj.getAttachList();
    }
}


/* 预受理办件加载材料信息 */
function initPreAttachList(itemInsId){
    var attachSubFormObj = window.frames("attachSubForm");
    if(attachSubFormObj){
        return attachSubFormObj.initFromItemInsId(itemInsId);
    }
}


/* 根据材料状态获取材料清单 */
function getAttachListByStatus(status){
    var attachSubFormObj = window.frames("attachSubForm");
    if(attachSubFormObj){
        return attachSubFormObj.getAttachListByStatus(status);
    }
}


/* 获取不是已提交的材料清单 */
function getNotSubmittedList(){
    var attachSubFormObj = window.frames("attachSubForm");
    if(attachSubFormObj){
        return attachSubFormObj.getNotSubmittedList();
    }
}


/* 获取收费列表 */
function getChargeList(){
    var chargeSubFormObj = window.frames("chargeSubForm");
    if(chargeSubFormObj){
        return chargeSubFormObj.getChargeList();
    }
}

/*检验电话的合法性 type:0  检测手机和电话 1 检测电话 2检测手机*/
function checkmobile(mobile,type){
	var phomecheck = /^\d{3,4}-\d{7,8}$/;
	var mobilecheck = /^\d{11}$/;
	if(type==0){
	if(phomecheck.test(mobile)||mobilecheck.test(mobile)){
		return true;
	}else{
		alert("联系电话不合法,格式为：0731-87654321或13762318272 ，请检查！");
		return false;
	}
	}else if(type==1){
		if(phomecheck.test(mobile)){
			return true;
		}else{
			alert("电话号码不合法，格式为：0731-87654321，请检查！");
			return false;
		}
	}else{
		if(mobilecheck.test(mobile)){
			return true;
		}else{
			alert("手机号码不合法，格式为：13762318272，请检查！");
			return false;
		}
	}
}

/* 校验函数 */
function checkByDataset(){
    if(!app_5_checkIsEmpty($('ITEM_INSNAME').value,"项目名称不能为空，请检查")){
        app_5_focus($('ITEM_INSNAME'));
        return false;
    }
    if(!checkLenth($('ITEM_INSNAME'),"项目名称",200)){
        app_5_focus($('ITEM_INSNAME'));
        return false;
    }
    if(!checkLenth($('REMARK1'),"备注",500)){
        app_5_focus($('REMARK1'));
        return false;
    }
    if(isApplyTypeCorp()){
        if(!app_5_checkIsEmpty($('CORP_NAME').value,"单位名称不能为空，请检查")){
            app_5_focus($('CORP_NAME'));
            return false;
        }
        if(!checkLenth($('CORP_NAME'),"单位名称",100)){
            app_5_focus($('CORP_NAME'));
            return false;
        }
        if(!app_5_checkIsEmpty($('CORP_ACCOUNTS').value,"证件号不能为空，请检查")){
            app_5_focus($('CORP_ACCOUNTS'));
            return false;
        }
        if(!checkLenth($('CORP_ACCOUNTS'),"证件号",50)){
            app_5_focus($('CORP_ACCOUNTS'));
            return false;
        }
		if(!app_5_checkIsEmpty($('RELATOR').value,"联系人不能为空，请检查")){
            app_5_focus($('RELATOR'));
            return false;
        }
        if(!checkLenth($('RELATOR'),"联系人",20)){
            app_5_focus($('RELATOR'));
            return false;
        }
		if(!app_5_checkIsEmpty($('RELATOR_MOBILE').value,"联系人电话不能为空，请检查")){
            app_5_focus($('RELATOR_MOBILE'));
            return false;
        }
		if(!checkmobile($('RELATOR_MOBILE').value,0)){
			return false;
		}
        if(!checkLenth($('RELATOR_MOBILE'),"联系人电话",50)){
            app_5_focus($('RELATOR_MOBILE'));
            return false;
        }
        if(!checkLenth($('CORP_ADDR'),"单位地址",150)){
            app_5_focus($('CORP_ADDR'));
            return false;
        }
        if(!checkLenth($('CORPORATOR'),"法人代表",20)){
            app_5_focus($('CORPORATOR'));
            return false;
        }
        if(!checkLenth($('CORPORATOR_PHONE'),"法人代表电话",40)){
            app_5_focus($('CORPORATOR_PHONE'));
            return false;
        } 
    }else{
        if(!app_5_checkIsEmpty($('APPLICANT_NAME').value,"申请人姓名不能为空，请检查")){
            app_5_focus($('APPLICANT_NAME'));
            return false;
        }
        if(!checkLenth($('APPLICANT_NAME'),"申请人姓名",50)){
            app_5_focus($('APPLICANT_NAME'));
            return false;
        }
        if(!app_5_checkIsEmpty($('ACCOUNTS').value,"证件号不能为空，请检查")){
            $('ACCOUNTS').value="";
            app_5_focus($('ACCOUNTS'));
            return false;
        }
        if(!checkLenth($('ACCOUNTS'),"证件号",100)){
            app_5_focus($('ACCOUNTS'));
            return false;
        }
		if(IsSpace($('MOBILE').value)&&IsSpace($('PHONE').value)){
			alert("手机号码或电话号码必填一项！");
			return false;
		}
        if(!IsSpace($('MOBILE').value)&&!checkmobile($('MOBILE').value,2)){
            app_5_focus($('MOBILE'));
            return false;
        }
        if(!IsSpace($('PHONE').value)&&!checkmobile($('PHONE').value,1)){
            app_5_focus($('PHONE'));
            return false;
        }
        if(!checkLenth($('ADDRESS'),"地址",100)){
            app_5_focus($('ADDRESS'));
            return false;
        }
        if(!checkLenth($('POST'),"邮编",20)){
            app_5_focus($('POST'));
            return false;
        }
        if(!checkLenth($('EMAIL'),"邮箱",100)){
            app_5_focus($('EMAIL'));
            return false;
        }
    }
    return true;
}


/* 校验字符长度 */
function checkLenth(fldObj,fldName, fldLen){
    if(app_5_GetLength(fldObj.value) > fldLen){
        alert(fldName+"的长度不能超过"+fldLen+"个字符（提示：1个汉字=2个字符）");
        app_5_focus(fldObj);
        return false;
    }
    return true;
}

/* 将焦点移到指定对象上，如果对象不可见、被禁用或其他原因不可用，则不转移 */
function app_5_focus(obj){
    try{
        obj.focus(); 
    }catch(e){}
}


/* 根据自定义规则校验 */
function checkByReg_back(){
    if(isApplyTypeCorp()){
//        if(!app_5_checkPhone($('RELATOR_MOBILE').value,'联系电话输入错误，请检查')){
//            return false;
//        }
//        if(!app_5_checkPhone($('CORPORATOR_PHONE').value,'联系电话输入错误，请检查')){
//            return false;
//        }
    }else{
        
        if(!app_5_checkPost($('POST').value,'邮编输入错误，请检查')){
            return false;
        }
        if(!app_5_checkEmail($('EMAIL').value,'邮箱输入错误，请检查')){
            return false;
        }
    }
    return true;
}


/* 根据自定义规则校验 */
function checkByReg(){
    if(isApplyTypeCorp()){
        var checkItems=new checkList();
        checkItems.addRegularExpreesion("CORP_NAME");
        checkItems.addRegularExpreesion("ITEM_INSNAME");
        if(!checkItems.check()) return false;
    }else{
        var checkItems=new checkList();
        checkItems.addRegularExpreesion("APPLICANT_NAME");
        checkItems.addRegularExpreesion("ITEM_INSNAME");
        if(!checkItems.check()) return false;
        
        if(!app_5_checkPost($('POST').value,'邮编输入错误，请检查')){
            return false;
        }
        if(!app_5_checkEmail($('EMAIL').value,'邮箱输入错误，请检查')){
            return false;
        }
    }
    return true;
}

/*
 *事项名称同步到项目名称上(黄艺平2009-10-20)
 */
function initshowItemName(){
   var IS_ITEMNAME = xzsp.api.BaseService2InstanceAPI.getIsItemName(PageParms.ecId);
   if(IS_ITEMNAME=="Y"){
    $('ITEM_INSNAME').value = PageParms.baseinfoVO.item_name;
   }
}

/**
 * 验证
 */
function validate()
{
    var checkList = new SqlInjCheckList();
    checkList.addItem($("ITEM_INSNAME").value);
    checkList.addItem($("REMARK1").value);
    checkList.addItem($("CORP_NAME").value);
    checkList.addItem($("CORP_ACCOUNTS").value);
    checkList.addItem($("RELATOR").value);
    checkList.addItem($("RELATOR_MOBILE").value);
    checkList.addItem($("CORP_ADDR").value);
    checkList.addItem($("CORPORATOR").value);
    checkList.addItem($("CORPORATOR_PHONE").value);
    checkList.addItem($("APPLICANT_NAME").value);
    checkList.addItem($("ACCOUNTS").value);
    checkList.addItem($("MOBILE").value);
    checkList.addItem($("PHONE").value);
    checkList.addItem($("ADDRESS").value);
    checkList.addItem($("POST").value);
    checkList.addItem($("EMAIL").value);
    if(!checkList.check()){
        return false;
    }
    if(!checkByDataset() || !checkByReg()){
        return false;
    }
    return true;
}


//获得材料tab中的刚提交的材料ID
function getSubmittedStatusAttachIds(){
   var submittedStatusList = attachSubForm.PageParms.subAttIdList; 
   var attachIds = new Array();
   var attachInstance = null;
   for(var i=0; i<submittedStatusList.length; i++){
       attachInstanceId = submittedStatusList[i];
       attachIds.push("'"+attachInstanceId+"'"); //必须加单引号
       if(i != submittedStatusList.length-1){
           attachIds.push(",");
       }
   }
   return attachIds.join("");
}

function importExcel() {
	 //事项编号
	 var ecId = PageParms.ecId;
	 //事项名称
	 var itemName = document.getElementById("ITEM_NAME").value;
	//受理人id
	var accepterId = getSysElement("userId");
	//受理人名字
	var accepterName = getSysElement("userName");
	 var url = "/creatorepp/ccapp/app_5/jsp/instance/importExcel.jsp?ecId="+ecId+"&itemName="+itemName+"&accepterId="+accepterId+"&accepterName="+accepterName;
	 openWin(url,600,200);     
}

function openWin(url,swidth,sheight){   
    var w = showModalDialog(url,window,"dialogWidth:"+swidth+"px;dialogHeight:"+sheight+"px;help:no;scroll:auto;status:no");
	 return w;
}