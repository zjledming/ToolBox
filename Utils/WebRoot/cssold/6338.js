document.write("<script type='text/javascript' src='<%=request.getContextPath()%>/dwrxzsp/interface/InstanceManager.js'><\/script>");
document.write("<script type='text/javascript' src='<%=request.getContextPath()%>/dwrxzsp/interface/InitializationManager.js'><\/script>");
document.write("<script type='text/javascript' src='<%=request.getContextPath()%>/dwrxzsp/engine.js'><\/script>");
document.write("<script type='text/javascript' src='<%=request.getContextPath()%>/dwrxzsp/util.js'><\/script>");
document.write("<script type='text/javascript' src='<%=request.getContextPath()%>/dwrxzsp/interface/BaseInfoManager.js'><\/script>");
document.write("<script type='text/javascript' src='<%=request.getContextPath()%>/dwrxzsp/interface/NoSubmitAttachManager.js'><\/script>");


        
//==============================================ȫ�ֶ����������=========================================
var PageParms = {
    //������ʱΪclear
    ccFormId :IsSpace(creator_getQueryString("cc_form_instanceid"))? "":creator_getQueryString("cc_form_instanceid"),
    //ֻ�����������ʱ��Żᴫ����ecId�����ݴ桢������֪�����˵���������ģ����instace���в�ѯ���
    ecId :IsSpace(creator_getQueryString("ec_id"))? "":creator_getQueryString("ec_id"),//�õ���Ŀ������ϢID
    //ֻ����������ʱ�Żᴫ����itemCode 
    itemCode : IsSpace(creator_getQueryString("item_code"))? "":creator_getQueryString("item_code"),//����ID
    itemInsId : "",  //��Ŀʵ����
    yxtywlsh : "",  //ԭϵͳҵ����ˮ��
    //ֻ����������ʱ�Żᴫ����flowId 
    flowId : IsSpace(creator_getQueryString("flow_id"))? "":creator_getQueryString("flow_id"),//����ID
    flowret : "",     //������Ϣ
    baseinfoVO : null,
    wfActionVO : null,
    instanceVO : null,
    applyViewVO : null,
    xzspWorkflowType : "",   //�������1-��ͨ���� 2-���֧ѡ�� 3-���֧ѡ��
    isDynamicSelectAct : false,  //�Ƿ�̬ѡ����һ���
    isDynamicSelectAndAct : false, //�Ƿ�̬ѡ����һ����
    isDynamicSelectUser : false, //�Ƿ�̬ѡ���ִ����
    nextActList : new Array(),
    instanceCode :  "",
    isChargeShow : false,
    isFromParent : false,
    isFromChild : false,
    isFromPre : false, //�Ƿ�����Ԥ����
    isFromNew : false, //�����������־
    isFromTemp : false, //�����ݴ��־
    isPrepareFlow : false,//����Ԥ�����־ 
    isFromSupply : false, //���Բ�����֪��־
    isFromBack : false,  //�����˻ص�����Ļ
    loginName:getSysElement("userAccount"),
	initItemNameHtml:""
}

//==============================================��ʼ�������������=========================================

/**
 * ��ʼ��������
 */
function initItemNameDiv(){
	// ��ʼȫ������
	PageParms.initItemNameHtml = callItemNameData("");
	//ѡ���������ʼ����/�ݴ��б�/��������
	var listType=creator_getQueryString("list_type");
	if (PageParms.ecId != "" || listType=="tobe_work" || listType=="tobe_work2" || listType=="worklist") {
		initByInitManager();
	}
	initItemName("ITEM_NAME","showItemNameDiv");
	// �½���-��ʾdiv
	if (PageParms.ecId == "") {
		disPlayDiv(document.getElementById("showItemNameDiv"),PageParms.initItemNameHtml);
		// ȡdiv��li�ڵ㣬���ֻ��2���ڵ㣨һ�����ͣ�һ����Ŀ������ôֱ��ѡ����Ŀ
		var obj = getLiByDiv("showItemNameDiv");
		if(obj != false){
			// ѡ����Ŀ
			obj.click();
		}
	}
}

/**
 * ȡdiv��li�ڵ㣬���ֻ��2���ڵ㣬���ؽڵ㣬��֮������false
 */
function getLiByDiv(divId){
	var div=document.getElementById(divId);  
	// �ų���div �ڵ�Ϊ�յ����
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
 * ��ʼ���������˵�
 */
function initItemName(id,divId){
	var inputObj = document.getElementById(id);
	var divObj = document.getElementById(divId);
	
	//�����ʧȥ����
//	inputObj.onblur=function(event){
//		event=window.event||event;
//		divObj.style.visibility="hidden";
//	}
	//�����ɿ�
	inputObj.onkeyup=function(e){
		if(trim(inputObj.value) == ""){
			disPlayDiv(divObj,PageParms.initItemNameHtml);
		}else{
			disPlayDiv(divObj,callItemNameData(trim(inputObj.value)));
		}
	}
	
	// ����¼�
	inputObj.onclick=function(e){
		if(trim(inputObj.value) == ""){
			disPlayDiv(divObj,PageParms.initItemNameHtml);
		}else{
			disPlayDiv(divObj,callItemNameData(trim(inputObj.value)));
		}
	}
}

//��ʾdiv
function disPlayDiv(divObj,htmlStr){
	divObj.innerHTML = htmlStr;
	divObj.style.visibility="visible";
}

// ȥ��ǰ��ո�
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

//��database����-�����ܼ���
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
	// �������
	var sql_sxlb = " select itemtype_id,itemtype_name from app_5.Ta_Param_Itemtype";
	var list_sxlb = executeSelect(sql_sxlb,1,200);
	for ( var i = 0; i < list_sxlb.length; i++) {
		// ���li��¼ - <img style='top:5px;' src='../../../images/leftmenu_01.gif'/>
		// ���˵��������������������
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
	// ����div html
	if(liStr == ""){
		return "";
	}else{
		return "<ul>"+liStr+"</ul>";
	}
}


/**
 * �������
 */
function gotoItemUrl(ec_id, item_name, process_id, flow_version, package_id, flow_id,formDefId,item_code){
	dwr.engine.setAsync(false); //��DWR��������Ϊͬ��   
	NoSubmitAttachManager.hasNoSubmitAttach(ec_id,function(flag){
			if(flag&&window.confirm("��������������֮ǰδ������Ŀ�ϴ��Ĳ��ϸ������Ƿ�ɾ��")){
				NoSubmitAttachManager.deleteNoSubmitAttach(ec_id,function(){});
			}
	})
	var mgrName = package_id+"%23"+flow_version+"%23"+process_id;
	var url = "../../../common_workflow/start_form.jsp?mgrName="+mgrName+"&formDefId="+formDefId+"&ec_id="+ec_id+"&flow_id="+flow_id+"&item_code="+item_code+"&back_prevpage=true&fristPage=2"; 
	window.open(url,"perspective_content");
}


/**
 * ��ʼ��
 * ��������4����ڣ��ֱ��������������ݴ桢���Բ�����֪���˻ص�����
 * ϵͳĬ��ֻչʾ��Ŀ�������˵���Ҫ��д���򣬿ɵ����¼�ͷչ����д����
 * ϵͳĬ�ϲ���ʾ�շ���Ϣ������б�ֻ�������Ϊ�շѵĲ�Сʱ�շѣ�ֻ�л��˵�����Ĳ���ʾ����б�
 */
function initByInitManager(){
    dwr.engine.setAsync(false); //��DWR��������Ϊͬ��
    hideOrShowTabByName("�շ���Ϣ","hide"); //Ĭ���������շ���Ϣҳ��
    if(PageParms.ccFormId=='clear'){//20091207����ƽ
       hideOrShowTabByName("����б�","hide"); //Ĭ������������б�ҳ�� 
    }
    var paraMap = {
        "initType" : "1", //��ʼ���������̶���1
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
    initActiveReport(PageParms.flowId,creator_getQueryString("actDefId"),$('btnPrtAccept'));//20091015�Ż���
    initActionExtForStart(PageParms.flowId,creator_getQueryString("actDefId"));
    serarchField("CORP_NAME","showCorpNameDiv","ta_sp_applycorp","applycorp_id",
                 "corp_accounts,corp_name",findCorpInfo); //��ʼ����λ��ģ����ѯ
    serarchField("APPLICANT_NAME","showPersonNameDiv","ta_sp_applicant","applicant_id",
                 "accounts,applicant_name",findPersonInfo);//��ʼ���û�����ģ����ѯ
    initByListType();
    if(IsSpace($('CERT_TYPE').value)){
        $('CERT_TYPE').value = "���֤";
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



//����AJAX��������ݽ���������漰���ɽ���Ԫ��
function handleRtnData(rtnData){
    var errMsg = rtnData["error"];
    if(!IsSpace(errMsg)){
        throw new Error(errMsg);
    }
    PageParms.flowret = rtnData["flowret"];  //��ȡ���̶�����Ϣ
    PageParms.baseinfoVO = rtnData["baseinfoVO"]; //��ȡ�������Ϣ
    PageParms.wfActionVO = rtnData["wfActionVO"];
    initDynamicWorkflow(PageParms.flowret); //��ʼ����̬ѡ��ִ���˺Ͷ�̬ѡ��ִ�л����������ػ�չʾ
    initIsChargeShow(); //�����շѽ���չʾ 
    if(isNewItem()){ //������
        initshowItemName();//����ƽ����(2009-10-20)
        PageParms.isFromNew = true;  
        var defaultApplyType = xzsp.util.CookieManager.getDefaultApplyType(PageParms.baseinfoVO.ec_id); 
        if(IsSpace(defaultApplyType)){
            defaultApplyType = "1";  //Ĭ����ʾ���뵥λ
        }
        initSelectApplyer(defaultApplyType); //�������뵥λ�������˵�ѡ��ť
        showApplyCtrl(defaultApplyType); //��ʾ�����˻����뵥λ
    }else{  //�����ݴ桢������֪���˻ص������
        disableApplySelect(); //ʹѡ��ť����ѡ��ֻ����������Ŀ�ſ�ѡ��
        PageParms.instanceVO = rtnData["instanceVO"];  //��ȡ��Ŀʵ����Ϣ
        PageParms.applyViewVO = rtnData["applyViewVO"]; //��ȡ��������Ϣ
        initGlobalFields();
        setRadioChecked("sex",PageParms.applyViewVO.sex);  //�����������ѡ���л�Ů
        setRadioChecked("corpCertType",PageParms.applyViewVO.corp_certtype);  //�����������ѡ���л�Ů
        initSelectApplyer(PageParms.instanceVO.apply_type); //�������뵥λ�������˵�ѡ��ť
        showApplyCtrl(PageParms.instanceVO.apply_type); //��ʾ�����˻����뵥λ
        initRenderBusiData(); //չʾҵ������
    }
    initTop(); //��ʼ��ͷ��
}


/* ��ʼ����̬ѡ��ִ���˺Ͷ�̬ѡ��ִ�л����������ػ�չʾ */
function initDynamicWorkflow(flowret){
    var ret = initDynamicWorkflowSet(flowret,"start"); //��ʼ����̬ѡ��
    PageParms.xzspWorkflowType = ret["xzspWorkflowType"];
    PageParms.isDynamicSelectAct = ret["isDynamicSelectAct"];
    PageParms.isDynamicSelectUser = ret["isDynamicSelectUser"];
    PageParms.isDynamicSelectAndAct = ret["isDynamicSelectAndAct"];
    PageParms.nextActList = ret["nextActList"];    
}


/* ��ʼ��ͷ�������б����� */
function initTop(){
    $('divItemName').innerHTML = PageParms.baseinfoVO.item_name;
	// ���ֵ
	document.getElementById("ITEM_NAME").value = PageParms.baseinfoVO.item_name;
    var crntTache = ""; //��ǰ����չʾ�޸ģ�By�Ż���20091005��
    if(PageParms.isFromNew){  //��������Ŀ
        crntTache = xzsp.api.BaseService2InstanceAPI.getActionAlias(PageParms.flowId,creator_getQueryString("actDefId"));
        if(IsSpace(crntTache)){
            crntTache = "����";
        }
    }else{  //�ݴ桢�ڰ���Ѱ��
        crntTache  = getCurrentTache(PageParms.ccFormId);
        if(IsSpace(crntTache)){
            crntTache = "�Ѱ��";
        }
    }
    $('divCurrentTache').innerHTML = "��ǰ���ڣ�"+crntTache;
}


/* ��ʼ��һ����ȫ�ֱ����������Ƿ��շѡ�����ID����Ŀʵ��ID�� */
function initGlobalFields(){
    PageParms.ecId = PageParms.baseinfoVO.ec_id;
    PageParms.itemCode = PageParms.baseinfoVO.item_code;
    PageParms.itemInsId = PageParms.instanceVO.item_insid;
    PageParms.flowId = PageParms.instanceVO.flow_id; 
}

/* ��ʾѡ�������˻�ѡ�����뵥λ */
function disableApplySelect(){
    var oRadios = $('applyTypeSelect').getElementsByTagName("input");
    for(var i=0; i<oRadios.length; i++){
        oRadios[i].disabled = true;
    }
    $('imgApply').style.display = "none";
}


/* ������ƣ������Ƿ�չʾ�շ� */
function initIsChargeShow(){
    if(PageParms.baseinfoVO.ischarge == "Y"){
        hideOrShowTabByName("�շ���Ϣ","show");    
    }
}


/* ѡ�����뵥λ�������� */
function initSelectApplyer(applyType){
    var applyBoxes = document.getElementsByName("RGapplyTypeCtrl");
    for(var i=0; i<applyBoxes.length; i++){
        if(applyType == applyBoxes[i].value){
            applyBoxes[i].checked = true;
            break;
        }
    }
}

/* ��ʼ��ҵ�����ݣ���ҵ�����ݴ�JS������ȡ�������ŵ�ÿ���ֶ���ȥ */
function initRenderBusiData(){
    xzsp.util.DBUtil.renderPageByVO(PageParms.instanceVO); //render��Ŀʵ����Ϣ
    xzsp.util.DBUtil.renderPageByVO(PageParms.applyViewVO);//render��������Ϣ
}


/* ���ݲ�ͬ���б����������ؼ������ػ���ʾ */
function initByListType(){
   if(creator_getQueryString("operType") == "nosend"){ //�ݴ�
        var listType=creator_getQueryString("list_type");
        if(listType=="tobe_work"){
           PageParms.isFromTemp = true;
           $('btnNotAccept').style.display = "none";
        }else{
           PageParms.isFromSupply = true;
           initFromSupply();
        }
    }else if(creator_getQueryString("operType") == "daiban"){ //���˵�����
        PageParms.isFromBack = true;
        initFromBack();
    }else if(creator_getQueryString("operType") == "readonly" || creator_getQueryString("operType") == "onuse"){
        $('btnBack').style.display = "";
    }
}


/**
 * ��ʼ�������Բ�����֪
 * ���°�ť�����ɵ㣺�ݴ桢Ԥ�����ѯ����ӡ����֪ͨ�顢��������������
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
 * ��ʼ�����˻ص�����
 */
function initFromBack(){
    $('btnTemp').style.display = "none";
    $('btnPreQuery').style.display = "none";
    $('btnPrtAccept').style.display = "none";
    $('btnAccept').innerHTML = "��ɻ";
    $('btnAccept').style.display = "";
    $('btnAccept').disabled = false;
    $('btnNotAccept').style.display = "none";
    $('btnSupplyInform').style.display = "none";
    $('btnSupplyAccept').style.display = "none";
}


/* ѡ����ʾ�������������� */
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

//�ж��Ƿ����Ը�����
function isFromParent(){
    return true;
}

//�ж��Ƿ�������̷���
function isFromChild(){
    return true;
}


//==============================================���ܰ�ť������=========================================


/* �ݴ��߼� */
function doTemp(){
    if(!checkByDataset() || !checkByReg()){
        return;
    }
    if(IsSpace(creator_getSession("cc_form_instanceid")) || (creator_getSession("cc_form_instanceid")=="clear")){
        PageParms.ccFormId = getNewCc_form_instanceid("true"); //����һ���µ�ҵ��ID
        creator_setSession("cc_form_instanceid",PageParms.ccFormId);
    }
    var flowData = getFlowData();  //��ȡ�����������
    var inputDatas = getInputData(); //��ȡ�û�������������
    createOverlayDIV();
    InstanceManager.doTemp(flowData,inputDatas[0],inputDatas[1],inputDatas[2],inputDatas[3],defaultCallback);
}


/* ��Ԥ�����ѯ�� */
function openPreQueryView(){
    var args = "&cc_form_instanceid="+creator_getSession("cc_form_instanceid")+"&ec_id="+PageParms.ecId;
    var viewAddr = "20090513085936250445.jsp?cc_form_instanceid="+creator_getSession("cc_form_instanceid")+args;
    var featrues = "dialogWidth:820px;dialogHeight:450px;center:yes;help:no;resizable:no;status:no;scroll:no";//2009-12-31����ƽ�޸�
    var rtn = window.showModalDialog(viewAddr,window,featrues); 
    if(!IsSpace(rtn)){ //rtnΪԤ����ʵ������Ŀʵ��ID
        PageParms.isFromPre = true; //�����Ƿ���������Ԥ����
        InstanceManager.doSaveFromPre(rtn,doCallBackForInitFromPre);
    }
}


//Ԥ�����ѯ�ص�����
function doCallBackForInitFromPre(rtnData){
    var errorMsg = rtnData["error"];
    if(!IsSpace(errorMsg)){  //�Ż���20091012�޸�
        alert(errorMsg);
        return;
    }
    PageParms.instanceVO = rtnData["instanceVO"];
    PageParms.applyViewVO = rtnData["applyViewVO"];
    PageParms.itemInsId = PageParms.instanceVO.item_insid;  
    initRenderBusiData(); //չʾҵ������
    setRadioChecked("sex",PageParms.applyViewVO.sex);  //�����������ѡ���л�Ů
    initSelectApplyer(PageParms.instanceVO.apply_type); //�������뵥λ�������˵�ѡ��ť
    showApplyCtrl(PageParms.instanceVO.apply_type); //��ʾ�����˻����뵥λ
    initPreAttachList(PageParms.itemInsId); //����Ԥ��������б�
}


/* ��ӡ����֪ͨ�飬��ӡ�����ʾ��������ť */
function doPrintAccept(){
//    if(!checkByDataset() || !checkByReg()){
//        return;
//    }
    if(!validate()) return;
    
    if(isNewItem() || PageParms.isFromTemp){  //ֻ���������ݴ�ʱ����Ҫ������ˮ��
        genInstanceCode(); //����������ˮ��
    }else if(isFromPre()){
        PageParms.yxtywlsh = $('INSTANCE_CODE').value;
        //genInstanceCode(); //����������ˮ��
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


//����workflow.js�еĺ���
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
    
   //0816ly�޸�
    reportParam.push("&start_year=");
    reportParam.push(applyDate.substr(0,4));
    reportParam.push("&start_month=");
    reportParam.push(applyDate.substr(5,2));
    reportParam.push("&start_day=");
    reportParam.push(applyDate.substr(8,2));
    var job_time = SqlToField("select ITEM_LIMIT from app_5.ta_sp_baseinfo where ec_id='"+PageParms.ecId+"'");
    reportParam.push("&banjie_day=");
    reportParam.push(job_time);
   //�㹤����job_time�������
     var end_time = SqlToField("select (to_date('"+applyDate+"','yyyy-mm-dd')+"+job_time+") from dual"); //Ĭ��
     var day = 0; //ͳ�ƹ���������
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
     //�����ա�����end...
    reportParam.push("&end_year=");
    reportParam.push(end_time.substr(0,4));
    reportParam.push("&end_month=");
    reportParam.push(end_time.substr(5,2));
    reportParam.push("&end_day=");
    reportParam.push(end_time.substr(8,2));
    
   //...0816ly�޸�end...

    
    if(getApplyType()=="1")//���뵥λ
    {
        if(!IsSpace($("APPLYCORP_ID").value)) //������뵥λ��Ϊ�գ�������뵥λ��Ϣ������
        {
            reportParam.push("&applycorp_id="); //���뵥λID
            reportParam.push($("APPLYCORP_ID").value);
        }
        if(!IsSpace($("CORP_CERTTYPE").value)) 
        {
            reportParam.push("&corp_certtype="); //���뵥λ֤������        
            reportParam.push($("CORP_CERTTYPE").value);
        }
        if(!IsSpace($("CORP_ACCOUNTS").value)) 
        {
            reportParam.push("&corp_accounts="); //���뵥λ��ʾ
            reportParam.push($("CORP_ACCOUNTS").value);
        }
        if(!IsSpace($("CORP_NAME").value)) 
        {
            reportParam.push("&corp_name=");  //���뵥λ��
            reportParam.push($("CORP_NAME").value);
        }
        if(!IsSpace($("CORP_ADDR").value)) //��ӵ�λ��ַ
        {
            reportParam.push("&corp_addr=");
            reportParam.push($("CORP_ADDR").value);
        }
        if(!IsSpace($("CORP_TYPE").value)) //��ӵ�λ����
        {
            reportParam.push("&corp_type=");
            reportParam.push($("CORP_TYPE").value);
        }
        if(!IsSpace($("RELATOR").value)) //��ӵ�λ��ϵ��
        {
            reportParam.push("&relator=");
            reportParam.push($("RELATOR").value);
        }
        if(!IsSpace($("RELATOR_MOBILE").value)) //��ϵ�˵绰
        {
            reportParam.push("&relator_mobile=");
            reportParam.push($("RELATOR_MOBILE").value);
        }
        if(!IsSpace($("CORPORATOR").value))//���˴���
        {
            reportParam.push("&corporator=");
            reportParam.push($("CORPORATOR").value);
        }
        if(!IsSpace($("CORPORATOR_PHONE").value))//���˴���绰
        {
            reportParam.push("&corporator_phone=");
            reportParam.push($("CORPORATOR_PHONE").value);
        }
    }else if(getApplyType()=="2"){ //������
        if(!IsSpace($("APPLICANT_ID").value))
        {
            reportParam.push("&applicant_id="); //������ID
            reportParam.push($("APPLICANT_ID").value);
        }
        if(!IsSpace($("CERT_TYPE").value))
        {
            reportParam.push("&cert_type="); //����֤������
            reportParam.push($("CERT_TYPE").value);  
        }
        if(!IsSpace($("ACCOUNTS").value))
        {
            reportParam.push("&accounts=");  //�����˱�ʾ(֤������)
            reportParam.push($("ACCOUNTS").value); 
        }
        if(!IsSpace($("APPLICANT_NAME").value))
        {
            reportParam.push("&applicant_name=")//����������
            reportParam.push($("APPLICANT_NAME").value);
        }
    
        if(!IsSpace($("SEX").value))
        {
            reportParam.push("&sex="); //������ID
            reportParam.push($("SEX").value);
        }
        if(!IsSpace($("MOBILE").value))
        {
            reportParam.push("&mobile="); //�������ֻ�
            reportParam.push($("MOBILE").value);
        }
        if(!IsSpace($("PHONE").value))
        {
            reportParam.push("&phone="); //�����˵绰
            reportParam.push($("PHONE").value);
        }
        if(!IsSpace($("ADDRESS").value))
        {
            reportParam.push("&address="); //�����˵�ַ
            reportParam.push($("ADDRESS").value);
        }
        if(!IsSpace($("POST").value))
        {
            reportParam.push("&post="); //�������ʱ�
            reportParam.push($("POST").value);
        }
        if(!IsSpace($("EMAIL").value))
        {
            reportParam.push("&email="); //����������
            reportParam.push($("EMAIL").value);
        }
    }
   //alert(reportParam);
    return reportParam.join("");
}


/* �򿪶�̬ѡ��ִ���˽��� */
function openOrSelectUserView(){
    var oUserId = document.getElementById("DYNAMICPERFORMER_ID");
    var oUserName = document.getElementById("DYNAMICPERFORMER");
    var oUserRealName = document.getElementById("DYNAMICPERFORMER_REALNAME");
    var actDefId = "";
    if(PageParms.xzspWorkflowType == "1"){  //��ͨ����
        actDefId = $('btnAccept').getAttribute("actid");
    }else if(PageParms.xzspWorkflowType == "2"){  //��ѡ��
        actDefId = $('xzsp_workflowControl_selectAct').value;
    }
    openSelectUserTree(oUserId,oUserName,oUserRealName,false,true,PageParms.flowId,actDefId);
}



/* ����ť��Ӧ�¼� */
function doAccept(){
//    if(!checkByDataset() || !checkByReg()){
//        return;
//    }
    if(!validate()) return;
    if(!isDynamicActAndUserSelected()){
        return;
    }
    if(IsSpace(creator_getSession("cc_form_instanceid")) || (creator_getSession("cc_form_instanceid")=="clear")){
        PageParms.ccFormId = getNewCc_form_instanceid("true"); //����һ���µ�ҵ��ID
        creator_setSession("cc_form_instanceid",PageParms.ccFormId);
    }
    var flowData = getFlowData();  //��ȡ�����������
    var inputDatas = getInputData(); //��ȡ�û�������������
    var paraMap = new Array();
    paraMap["xzspWorkflowType"] = PageParms.xzspWorkflowType;
    paraMap["isDynamicSelectUser"] = PageParms.isDynamicSelectUser;
    paraMap["flowId"] = PageParms.flowId;
    paraMap["nextActList"] = PageParms.nextSelectedActList; //���֧�ѡ�����һ����б�
    paraMap["countryId"] = xzsp.api.BaseService2InstanceAPI.getUserCountryId(PageParms.flowId,creator_getQueryString("actDefId"));//quan.zhou 2010.2.23
    var flowCtrlInfo = buildFlowCtrlMap(paraMap, "start");//��ȡ���̿�����Ϣ
    xzsp.util.CookieManager.setDefaultApplyType(PageParms.ecId,getRadioValue("applyTypeCtrl"));
    
    /* ����ģ����� begin */
    //����Ϊȡ����һ���ڵ�ִ���ˣ���Ϊһ�����̺������̣����ڷ�����
	if("3" == PageParms.xzspWorkflowType){
		userlist=getAndMan(flowCtrlInfo.ANDSPLITUSERS);
	}else{
		userlist=flowCtrlInfo.DYNAMICPERFORMER; 
	}
    /* end */
    
    if(confirm("��ȷ��Ҫ������")){
      document.getElementById("btnAccept").disabled = true;
      createOverlayDIV();
      //InstanceManager.doAccept(flowData,flowCtrlInfo,inputDatas[0],inputDatas[1],inputDatas[2],inputDatas[3],defaultCallback);
      InstanceManager.doAccept(flowData,flowCtrlInfo,inputDatas[0],inputDatas[1],inputDatas[2],inputDatas[3],defaultCallbackMess);
    }
   //jumpActive(PageParms.ccFormId);
}



/* ����ģ����� modify by xianlu.lu 2010/12/07 begin */

var userlist;  //����һ��ȫ�ֱ�������ȡ��һ���ڵĴ����ˣ��������ǳ�ʼ��

/*
**���庯�������ǣ������ɹ������ĺ�����ʵ�ֶ��ŷ��͹���
*/
function defaultCallbackMess(rtnData) {
	var fId = PageParms.flowId; // ��ȡ����ID
	var defId = creator_getQueryString("actDefId");// ��ȡ�����ID
	var ins_code = $('INSTANCE_CODE').value; // ��ȡ��ˮ��
	var sql = "select t.model_id from app_5.ta_sp_wfaction t where t.flow_id='"
			+ fId + "' and t.action_defid='" + defId + "'";
	var modelId = SqlToField(sql);
	var errMsg = rtnData;
	
	if (IsSpace(errMsg)) {
		var action_name = getHJName(fId, defId);
		
		// ��������Ա������
		var  ec_id= PageParms.ecId;
	    var  ins_code= $('INSTANCE_CODE').value;
	    // ��һ��������
		var next_actionname = PageParms.nextActList[0][1];
	    // ��������ǰ�������ƣ�����id����ˮ�ţ���һ���ڵĴ�����
	    var url1="../../../ccapp/app_5/jsp/mseeage/sendMember.jsp?action_name="+action_name+"&"
	                 +"ec_id=" + ec_id + "&instance_code=" + ins_code +"&userlist="+userlist+"&next_actionname="+next_actionname;
	    window.open(url1,"frame1");  

		// ���͸������˻������뵥λ
		var url2 = "../../../ccapp/app_5/jsp/mseeage/sendNotice.jsp?action_name="
				+ action_name
				+ "&"
				+ "modelId="
				+ modelId
				+ "&instance_code="
				+ ins_code;
		window.open(url2, "frame2");
		// ִ��Ĭ�ϻص�����
		defaultCallback();
	}
}

/**
**��ȡ������һ���ڵ�ִ����
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
**��ȡ��ǰ��������
**/
function getHJName(fId,defId){
     var sql="select t.action_name from app_5.ta_sp_wfaction t where t.flow_id='" + fId +"' and t.action_defid='" + defId + "'";
    var action_name =SqlToField(sql);
    return action_name;
}

/* �򿪲����������� */
function openNotAcceptView(){
//    if(!checkByDataset() || !checkByReg()){
//        return;
//    }
    if(!validate()) return;
    
    if(IsSpace($("INSTANCE_CODE").value))
    {
        genInstanceCode(); //����������ˮ��
     }
    
    var args = getCommonParam();
    var viewAddr = "20090501135508984913.jsp?cc_form_instanceid="+creator_getSession("cc_form_instanceid")+args;
    var featrues = "dialogWidth:760px;dialogHeight:300px;center:yes;help:no;resizable:no;status:no;scroll:no";//20100115����ƽ�޸�
    var rtn = window.showModalDialog(viewAddr,window,featrues);   
    if(rtn == true){
        backToWorklist();
    } 
}



/**
 * ��ȡ��һ���ڴ�����
 */
function getNextUserList(){
	var result = "";
	
	var paraMap = new Array();
    paraMap["xzspWorkflowType"] = PageParms.xzspWorkflowType; // �������1-��ͨ���� 2-���֧ѡ�� 3-���֧ѡ��
    paraMap["isDynamicSelectUser"] = PageParms.isDynamicSelectUser; // �Ƿ�̬ѡ���ִ����
    paraMap["flowId"] = PageParms.flowId; // ����id
    paraMap["nextActList"] = PageParms.nextSelectedActList; // ���֧�ѡ�����һ����б�
    paraMap["countryId"] = xzsp.api.BaseService2InstanceAPI.getUserCountryId(PageParms.flowId,creator_getQueryString("actDefId"));
    var flowCtrlInfo = buildFlowCtrlMap(paraMap, "start"); // ��ȡ���̿�����Ϣ
    
    if("3" == PageParms.xzspWorkflowType){
    	result=getAndMan(flowCtrlInfo.ANDSPLITUSERS);
	}else{
		result=flowCtrlInfo.DYNAMICPERFORMER; 
	}
    return result;
}


/* �򿪲�����֪����� */
function openSupplyInformView(){
//    if(!checkByDataset() || !checkByReg()){
//        return;
//    }
	
	
    if(!validate()) return;
    
    if(IsSpace($("INSTANCE_CODE").value)){
        genInstanceCode(); //����������ˮ��
    }
    var args = getCommonParam();
    
    // ��һ��������
    //var next_actionname = PageParms.nextActList[0][1];
    var viewAddr = "20090501140813203389.jsp?cc_form_instanceid="+creator_getSession("cc_form_instanceid")+args;
    var featrues = "dialogWidth:774px;dialogHeight:500px;center:yes;help:no;resizable:no;status:no;scroll:no";//20100115����ƽ�޸�
    var rtn = window.showModalDialog(viewAddr,window,featrues); 
    if(rtn == true){
        backToWorklist();
    } 
}


/* �򿪲����������� */
function openSupplyAcceptView(){
//    if(!checkByDataset() || !checkByReg()){
//        return;
//    }
    if(!validate()) return;
    
    var args = getCommonParam();
    
    // ��ȡ���ŷ������
	var action_name = getHJName(PageParms.flowId, creator_getQueryString("actDefId"));
	// ��һ��������
	var next_actionname = PageParms.nextActList[0][1];
	
	var viewAddr = "20090501141817140170.jsp?cc_form_instanceid="+creator_getSession("cc_form_instanceid")+args+"&action_name="+action_name+"&next_actionname="+next_actionname;
    var featrues = "dialogWidth:724px;dialogHeight:500px;center:yes;help:no;resizable:no;status:no;scroll:no";//20100115����ƽ�޸�
    //alert(viewAddr);
    var rtn = window.showModalDialog(viewAddr,window,featrues); 
    if(rtn == true){
        backToWorklist();
    } 
}


//==============================================ȡ���������============================================


/* ��ȡ��ˮ�� */
function genInstanceCode(){
    if(IsSpace($('INSTANCE_CODE').value) || $('INSTANCE_CODE').value.charAt(0)=='W'){//�����������Ԥ����
        BaseInfoManager.getInstanceCode(PageParms.itemCode,function(rtnData){
            $('INSTANCE_CODE').value = rtnData;
            PageParms.instanceCode = rtnData;
        });
    }
}


/* ��ȡ������Ϣ */
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


/* ��ȡ���̿�����Ϣ���ṩ���ݴ桢������֪��ʹ�� */
function getFlowCtrlInfoForTemp(){
    var flowControlInfo = {
        "DYNAMICPERFORMER" : getSysElement("userName")
    };
    return flowControlInfo;
}

/* ��ȡ���̿�����Ϣ���ṩ����������������������Ԥ����ת�����ʹ�� */
function getFlowCtrlInfoForAccept(){
    var nextActUserNameList = getNextActUserNameList(PageParms.flowId,creator_getQueryString("actDefId"));
    var nextActUser = xzsp.util.getStrFromArray(nextActUserNameList);
    var flowControlInfo = {
        "DYNAMICPERFORMER" : nextActUser
    };
    return flowControlInfo;
}

/* ��ȡ�������ֵ��������Ŀ��Ϣ����������Ϣ��������Ϣ���շ���Ϣ�� */
function getInputData(){
    var inputDatas = new Array();
    var instanceVO = xzsp.util.DBUtil.getVoFromPage(new xzsp.vo.Instance());
    instanceVO.ec_id = PageParms.ecId; //����EC_ID��ֵ
    instanceVO.item_insid = PageParms.itemInsId; //����ITEM_INSID��ֵ
    instanceVO.flow_id = PageParms.flowId; //����FLOW_ID��ֵ
    if(instanceVO.status==""||PageParms.isFromPre){//20100127����ƽ�޸�
       instanceVO.datasource = '0';//20100104����ƽ�޸�

    }
    instanceVO.apply_type = getApplyType(); 
    if(PageParms.instanceVO){ //����ǻ��˻������ݴ����ݣ�������������ID
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


/* ��ó��ò��� */
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


//�ж��Ƿ�����������Ŀ��ҵ��IDΪ�ա���������Ԥ����
function isNewItem(){
    if((IsSpace(PageParms.ccFormId) || PageParms.ccFormId=="clear") && PageParms.isFromPre==false){ 
        return true;
    }
    return false;
}

//�ж��Ƿ�����������Ԥ�������Ŀ
function isFromPre(){
    return PageParms.isFromPre;
}

/* �ж������������Ƿ�Ϊ���뵥λ��������ǣ����������� */
function isApplyTypeCorp(){
    return (getRadioValue("applyTypeCtrl") == "1");
}


//����������
function getApplyType(){
    return isApplyTypeCorp()?"1":"2"; 
}


//�������������
function getApplyName(){
    return isApplyTypeCorp()?$('CORP_NAME').value:$('APPLICANT_NAME').value;
}


/* ��ȡ�����б� */
function getAttachList(){
    var attachSubFormObj = window.frames("attachSubForm");
    if(attachSubFormObj){
        return attachSubFormObj.getAttachList();
    }
}


/* Ԥ���������ز�����Ϣ */
function initPreAttachList(itemInsId){
    var attachSubFormObj = window.frames("attachSubForm");
    if(attachSubFormObj){
        return attachSubFormObj.initFromItemInsId(itemInsId);
    }
}


/* ���ݲ���״̬��ȡ�����嵥 */
function getAttachListByStatus(status){
    var attachSubFormObj = window.frames("attachSubForm");
    if(attachSubFormObj){
        return attachSubFormObj.getAttachListByStatus(status);
    }
}


/* ��ȡ�������ύ�Ĳ����嵥 */
function getNotSubmittedList(){
    var attachSubFormObj = window.frames("attachSubForm");
    if(attachSubFormObj){
        return attachSubFormObj.getNotSubmittedList();
    }
}


/* ��ȡ�շ��б� */
function getChargeList(){
    var chargeSubFormObj = window.frames("chargeSubForm");
    if(chargeSubFormObj){
        return chargeSubFormObj.getChargeList();
    }
}

/*����绰�ĺϷ��� type:0  ����ֻ��͵绰 1 ���绰 2����ֻ�*/
function checkmobile(mobile,type){
	var phomecheck = /^\d{3,4}-\d{7,8}$/;
	var mobilecheck = /^\d{11}$/;
	if(type==0){
	if(phomecheck.test(mobile)||mobilecheck.test(mobile)){
		return true;
	}else{
		alert("��ϵ�绰���Ϸ�,��ʽΪ��0731-87654321��13762318272 �����飡");
		return false;
	}
	}else if(type==1){
		if(phomecheck.test(mobile)){
			return true;
		}else{
			alert("�绰���벻�Ϸ�����ʽΪ��0731-87654321�����飡");
			return false;
		}
	}else{
		if(mobilecheck.test(mobile)){
			return true;
		}else{
			alert("�ֻ����벻�Ϸ�����ʽΪ��13762318272�����飡");
			return false;
		}
	}
}

/* У�麯�� */
function checkByDataset(){
    if(!app_5_checkIsEmpty($('ITEM_INSNAME').value,"��Ŀ���Ʋ���Ϊ�գ�����")){
        app_5_focus($('ITEM_INSNAME'));
        return false;
    }
    if(!checkLenth($('ITEM_INSNAME'),"��Ŀ����",200)){
        app_5_focus($('ITEM_INSNAME'));
        return false;
    }
    if(!checkLenth($('REMARK1'),"��ע",500)){
        app_5_focus($('REMARK1'));
        return false;
    }
    if(isApplyTypeCorp()){
        if(!app_5_checkIsEmpty($('CORP_NAME').value,"��λ���Ʋ���Ϊ�գ�����")){
            app_5_focus($('CORP_NAME'));
            return false;
        }
        if(!checkLenth($('CORP_NAME'),"��λ����",100)){
            app_5_focus($('CORP_NAME'));
            return false;
        }
        if(!app_5_checkIsEmpty($('CORP_ACCOUNTS').value,"֤���Ų���Ϊ�գ�����")){
            app_5_focus($('CORP_ACCOUNTS'));
            return false;
        }
        if(!checkLenth($('CORP_ACCOUNTS'),"֤����",50)){
            app_5_focus($('CORP_ACCOUNTS'));
            return false;
        }
		if(!app_5_checkIsEmpty($('RELATOR').value,"��ϵ�˲���Ϊ�գ�����")){
            app_5_focus($('RELATOR'));
            return false;
        }
        if(!checkLenth($('RELATOR'),"��ϵ��",20)){
            app_5_focus($('RELATOR'));
            return false;
        }
		if(!app_5_checkIsEmpty($('RELATOR_MOBILE').value,"��ϵ�˵绰����Ϊ�գ�����")){
            app_5_focus($('RELATOR_MOBILE'));
            return false;
        }
		if(!checkmobile($('RELATOR_MOBILE').value,0)){
			return false;
		}
        if(!checkLenth($('RELATOR_MOBILE'),"��ϵ�˵绰",50)){
            app_5_focus($('RELATOR_MOBILE'));
            return false;
        }
        if(!checkLenth($('CORP_ADDR'),"��λ��ַ",150)){
            app_5_focus($('CORP_ADDR'));
            return false;
        }
        if(!checkLenth($('CORPORATOR'),"���˴���",20)){
            app_5_focus($('CORPORATOR'));
            return false;
        }
        if(!checkLenth($('CORPORATOR_PHONE'),"���˴���绰",40)){
            app_5_focus($('CORPORATOR_PHONE'));
            return false;
        } 
    }else{
        if(!app_5_checkIsEmpty($('APPLICANT_NAME').value,"��������������Ϊ�գ�����")){
            app_5_focus($('APPLICANT_NAME'));
            return false;
        }
        if(!checkLenth($('APPLICANT_NAME'),"����������",50)){
            app_5_focus($('APPLICANT_NAME'));
            return false;
        }
        if(!app_5_checkIsEmpty($('ACCOUNTS').value,"֤���Ų���Ϊ�գ�����")){
            $('ACCOUNTS').value="";
            app_5_focus($('ACCOUNTS'));
            return false;
        }
        if(!checkLenth($('ACCOUNTS'),"֤����",100)){
            app_5_focus($('ACCOUNTS'));
            return false;
        }
		if(IsSpace($('MOBILE').value)&&IsSpace($('PHONE').value)){
			alert("�ֻ������绰�������һ�");
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
        if(!checkLenth($('ADDRESS'),"��ַ",100)){
            app_5_focus($('ADDRESS'));
            return false;
        }
        if(!checkLenth($('POST'),"�ʱ�",20)){
            app_5_focus($('POST'));
            return false;
        }
        if(!checkLenth($('EMAIL'),"����",100)){
            app_5_focus($('EMAIL'));
            return false;
        }
    }
    return true;
}


/* У���ַ����� */
function checkLenth(fldObj,fldName, fldLen){
    if(app_5_GetLength(fldObj.value) > fldLen){
        alert(fldName+"�ĳ��Ȳ��ܳ���"+fldLen+"���ַ�����ʾ��1������=2���ַ���");
        app_5_focus(fldObj);
        return false;
    }
    return true;
}

/* �������Ƶ�ָ�������ϣ�������󲻿ɼ��������û�����ԭ�򲻿��ã���ת�� */
function app_5_focus(obj){
    try{
        obj.focus(); 
    }catch(e){}
}


/* �����Զ������У�� */
function checkByReg_back(){
    if(isApplyTypeCorp()){
//        if(!app_5_checkPhone($('RELATOR_MOBILE').value,'��ϵ�绰�����������')){
//            return false;
//        }
//        if(!app_5_checkPhone($('CORPORATOR_PHONE').value,'��ϵ�绰�����������')){
//            return false;
//        }
    }else{
        
        if(!app_5_checkPost($('POST').value,'�ʱ������������')){
            return false;
        }
        if(!app_5_checkEmail($('EMAIL').value,'���������������')){
            return false;
        }
    }
    return true;
}


/* �����Զ������У�� */
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
        
        if(!app_5_checkPost($('POST').value,'�ʱ������������')){
            return false;
        }
        if(!app_5_checkEmail($('EMAIL').value,'���������������')){
            return false;
        }
    }
    return true;
}

/*
 *��������ͬ������Ŀ������(����ƽ2009-10-20)
 */
function initshowItemName(){
   var IS_ITEMNAME = xzsp.api.BaseService2InstanceAPI.getIsItemName(PageParms.ecId);
   if(IS_ITEMNAME=="Y"){
    $('ITEM_INSNAME').value = PageParms.baseinfoVO.item_name;
   }
}

/**
 * ��֤
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


//��ò���tab�еĸ��ύ�Ĳ���ID
function getSubmittedStatusAttachIds(){
   var submittedStatusList = attachSubForm.PageParms.subAttIdList; 
   var attachIds = new Array();
   var attachInstance = null;
   for(var i=0; i<submittedStatusList.length; i++){
       attachInstanceId = submittedStatusList[i];
       attachIds.push("'"+attachInstanceId+"'"); //����ӵ�����
       if(i != submittedStatusList.length-1){
           attachIds.push(",");
       }
   }
   return attachIds.join("");
}

function importExcel() {
	 //������
	 var ecId = PageParms.ecId;
	 //��������
	 var itemName = document.getElementById("ITEM_NAME").value;
	//������id
	var accepterId = getSysElement("userId");
	//����������
	var accepterName = getSysElement("userName");
	 var url = "/creatorepp/ccapp/app_5/jsp/instance/importExcel.jsp?ecId="+ecId+"&itemName="+itemName+"&accepterId="+accepterId+"&accepterName="+accepterName;
	 openWin(url,600,200);     
}

function openWin(url,swidth,sheight){   
    var w = showModalDialog(url,window,"dialogWidth:"+swidth+"px;dialogHeight:"+sheight+"px;help:no;scroll:auto;status:no");
	 return w;
}