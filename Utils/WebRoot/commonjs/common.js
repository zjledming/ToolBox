/*
 * ȡ1-6���������1��6����������
 */
var ch = 1+Math.floor(Math.random()*6);

/**
 * ��ʽ��������ʾ��ʽ��Ҳ�������ڶ������ض����ȡ� �÷��� formatNumber(0,'') formatNumber(12432.21,'#,###')
 * formatNumber(12432.21,'#,###.000#') formatNumber('12432.415','####.0#')
 * formatNumber(12432,'#,###.00')
 */
function formatNumber(number,pattern){
    var str = number.toString();
    var strInt;
    var strFloat;
    var formatInt;
    var formatFloat;
    if(/\./g.test(pattern)){
        formatInt = pattern.split('.')[0];
        formatFloat = pattern.split('.')[1];
    }else{
        formatInt = pattern;
        formatFloat = null;
    }

    if(/\./g.test(str)){
        if(formatFloat!=null){
            var tempFloat = Math.round(parseFloat('0.'+str.split('.')[1])*Math.pow(10,formatFloat.length))/Math.pow(10,formatFloat.length);
            strInt = (Math.floor(number)+Math.floor(tempFloat)).toString();                
            strFloat = /\./g.test(tempFloat.toString())?tempFloat.toString().split('.')[1]:'0';            
        }else{
            strInt = Math.round(number).toString();
            strFloat = '0';
        }
    }else{
        strInt = str;
        strFloat = '0';
    }
    if(formatInt!=null){
        var outputInt = '';
        var zero = formatInt.match(/0*$/)[0].length;
        var comma = null;
        if(/,/g.test(formatInt)){
            comma = formatInt.match(/,[^,]*/)[0].length-1;
        }
        var newReg = new RegExp('(\\d{'+comma+'})','g');

        if(strInt.length<zero){
            outputInt = new Array(zero+1).join('0')+strInt;
            outputInt = outputInt.substr(outputInt.length-zero,zero)
        }else{
            outputInt = strInt;
        }

        var 
        outputInt = outputInt.substr(0,outputInt.length%comma)+outputInt.substring(outputInt.length%comma).replace(newReg,(comma!=null?',':'')+'$1')
        outputInt = outputInt.replace(/^,/,'');

        strInt = outputInt;
    }

    if(formatFloat!=null){
        var outputFloat = '';
        var zero = formatFloat.match(/^0*/)[0].length;

        if(strFloat.length<zero){
            outputFloat = strFloat+new Array(zero+1).join('0');
            // outputFloat = outputFloat.substring(0,formatFloat.length);
            var outputFloat1 = outputFloat.substring(0,zero);
            var outputFloat2 = outputFloat.substring(zero,formatFloat.length);
            outputFloat = outputFloat1+outputFloat2.replace(/0*$/,'');
        }else{
            outputFloat = strFloat.substring(0,formatFloat.length);
        }

        strFloat = outputFloat;
    }else{
        if(pattern!='' || (pattern=='' && strFloat=='0')){
            strFloat = '';
        }
    }
    return strInt+(strFloat==''?'':'.'+strFloat);
}

/*
 * ���ú��������text��textarea���Ƶ�ֵ������combobox������������ؼ���ֵ��Ϊ0
 * arguments_textΪ����text��textarea�Ŀؼ�id������ arguments_textΪ����������ȿؼ�id������
 * ע�⣺������ؼ��������ֵΪ��0����ѡ������ѡ�����ʾֵΪ��ȫ�����ȴ�
 */
function reset_common(arguments_text,arguments_combobox){
	for (var i=0; i<arguments_text.length; i++) {
		document.getElementById(arguments_text[i]).value = "";
	}
	for (var j=0; j<arguments_combobox.length; j++)
	{
		document.getElementById(arguments_combobox[j]).value = "0";
	}
}

/*
 * ���������ĳ���һ��ѡ�����������ѡ�� idΪҪ������������id
 */
function clearCombo(id){
	var object = document.getElementById(id);
	var optionIndex = object.options.length;
	for(;optionIndex>=1;optionIndex--){
		object.options.remove(optionIndex);
	}
}

/*
 * ��������������ѡ�� idΪҪ������������id
 */
function clearCombo_all(id){
	var object = document.getElementById(id);
	var optionIndex = object.options.length;
	for(;optionIndex>=0;optionIndex--){
		object.options.remove(optionIndex);
	}
}

/*
 * ��ѯ����������ָ����sql����ָ���ֶε����򷽷���ѯ���� dsΪ���ݼ� pgaeSizeΪ���ݼ�ÿҳ��ѯ������ sqlΪ��ѯ��sql���
 * FieldsΪҪ������ֶ����� OrdersΪҪ������ֶε�˳������ ע�⣺Fields��Orders�ĳ��ȱ���һ�£����򽫱���
 */
function findData(ds,pageSize,sql,Fields,Orders){
	for(var i=0;i<Fields.length;i++){
		if(Orders[i]=="����"){
			sql += " order by "+Fields[i]+" desc";
		}
	}
	ds.PageSize = pageSize;
	ds.Open(sql);
}

/*
 * ����һ��������������ģʽ�Ի��� urlΪ�Ի����·�� widthΪ�Ի���Ŀ�� heightΪ�Ի���ĸ߶�
 */
function openDlg(url,width,height){
	var returnVal = window.showModalDialog(url,window,"dialogWidth="+width+"px;dialogHeight="+height+"px;scroll=no;resizable=no;status=no");
	return returnVal;
}

/*
 * ����һ������������ģʽ�Ի��� urlΪ�Ի����·�� widthΪ�Ի���Ŀ�� heightΪ�Ի���ĸ߶�
 */
function openDlg_scroll(url,width,height){
	var returnVal = window.showModalDialog(url,window,"dialogWidth="+width+"px;dialogHeight="+height+"px;scroll=yes;resizable=no;status=no");
	return returnVal;
}

// ֻ������
function app_1_elementReadOnly(element,style,oldValue) { // element ������ֻ����Ԫ��
															// style
															// ���ڶ�̬�������������Ӧ�õ���ʽ
	var id = element.id;
	var value = "";
	if(isSpace(oldValue)) {
		value = element.value;
		var tmp = element.outerHTML.substring(0,7).toUpperCase();
		if (tmp == "<SELECT") // ѡ����ȡֵ��Ҫ���⴦��
		{
			value = element.options[element.selectedIndex].text;
		}
	} else {
		value = oldValue;
	}
	// ���������
	var myElement = document.createElement("input");
	myElement.setAttribute("id","my_" + id);
	myElement.setAttribute("value",value); // ��ֵ
// myElement.style.width = element.style.width; //Эͬ�칫���ĵ���Ҫ���� ����ͨ��ԭԪ�ص����Դ���
	myElement.style.width = "100%";
	myElement.readOnly = "true"; // ֻ��
	myElement.className = style; // Ӧ����ʽ
	element.style.display = "none"; // ����ԭԪ��
	element.parentNode.insertBefore(myElement,element); // ����̬�������������뵽ԭԪ��֮ǰ
}

// ֻ������
function elementReadOnly(element,style) { // element ������ֻ����Ԫ�� style
											// ���ڶ�̬�������������Ӧ�õ���ʽ
	var id = element.id;
	var value = element.value;
	var tmp = element.outerHTML.substring(0,7).toUpperCase();
	if (tmp == "<SELECT") // ѡ����ȡֵ��Ҫ���⴦��
	{
		value = element.options[element.selectedIndex].text;
	}
	// ���������
	var myElement = document.createElement("input");
	myElement.setAttribute("id","my_" + id);
	myElement.setAttribute("value",value); // ��ֵ
// myElement.style.width = element.style.width; //Эͬ�칫���ĵ���Ҫ���� ����ͨ��ԭԪ�ص����Դ���
	myElement.style.width = "100%";
	myElement.readOnly = "true"; // ֻ��
	myElement.className = style; // Ӧ����ʽ
	element.style.display = "none"; // ����ԭԪ��
	element.parentNode.insertBefore(myElement,element); // ����̬�������������뵽ԭԪ��֮ǰ
}

/**
 * ִ�в�ѯ���
 * 
 * @param:sql-��ѯsql��index-��ʼҳ�ţ�offset-ҳ��С return:result-��ά��������
 */
function executeSelect(sql,index,offset){
	var result = new Array();
	var s = SelectSql(sql,index,offset);
	if (s == "<root></root>" || s == "<root>" ) {
		return result;
    	}
	else {
		var x = SetDom(s);
		var rowCount = x.childNodes[0].childNodes.length - 1;	
		var colCount = x.childNodes[0].childNodes[0].childNodes.length;
		for (var i = 0; i < rowCount; i++) {			
			var col = new Array();
			for (var j = 0; j < colCount; j++) {
				col[j] = x.childNodes[0].childNodes[i].childNodes[j].text;
			}
			result[i] = col;
		}
		return result;
	}
}

/**
 * ��ȡ��������������
 */
function getAreaCode(){
    var userorgid = getSysElement('userorgid');
    var area_CodeV = SqlToField("select org_xzqm from td_sm_organization where org_id='"+userorgid+"'");

    return area_CodeV;
}

/**
 * ��ȡ��ϵͳ������������
 */
function getSysAreaCode(){

	var area_code = SqlToField("select baseinfo_value from ta_dic_baseinfo where baseinfo_key ='CURRENT_SYS_AREACODE'");
	return area_code;
}



/**
 * ��ȡ��������������
 */
function getParentAreaCode(area_code){
	var parent_areacode = SqlToField("select t.parent_areacode from ta_area t where area_code = '"+area_code+"'");
	return parent_areacode;
}

/**
 * ��ȡ��������������
 */
function getAreaType(area_code){
	var areaType = SqlToField("select t.area_type from ta_area t where area_code = '"+area_code+"'");
	return areaType;
}

/**
 * ��ȡ��������������
 */
function getAreaName(area_code){
	var areaName = SqlToField("select t.area_name from ta_dic_area t where area_code = '"+area_code+"'");
	return areaName;
}


/**
 * ��ʽ��ϵͳʱ�䣬��ʽΪYYYY-MM-DD hh24:mi:ss return��String
 */
function app_1_formatTime() {
	var sysDate = new Date();
	var year = sysDate.getYear();
	var month = sysDate.getMonth() + 1;
	var date = sysDate.getDate();
	var hour = sysDate.getHours();
	var minute = sysDate.getMinutes();
	var second = sysDate.getSeconds();
	var formatTime = year + "-" + month + "-" + date + " " + hour + ":" + minute + ":" + second;
	return 	formatTime;
}

/**
 * ֻ��ʾһ���������� param: areacode ��������ֶ���; param: areaname ���������ֶ����� return��String
 */
function chooseTestArea(areaValue,areacode,areaname) {
   var area_code=  areacode;
   var area_name = areaname;
   var url = "../../../ccapp/app_1/dzjctree/tree/areaTestRadioTree.jsp?areaValue="+areaValue+"&area_code="+area_code+"&area_name="+area_name+"&t="+new Date().getTime();
   var rtn = window.showModalDialog(url,window,"center:yes");
}

/**
 * ������ param: areacode ��������ֶ���; param: areaname ���������ֶ����� return��String
 */
function chooseArea(areacode,areaname) {
   var area_code=  areacode;
   var area_name = areaname;
   var url = "../../../ccapp/app_1/dzjctree/tree/areaRadioTree.jsp?area_code="+area_code+"&area_name="+area_name+"&t="+new Date().getTime();
   var rtn = window.showModalDialog(url,window,"center:yes");
}

/**
 * Ȩ�������� param: areacode ��������ֶ���; param: areaname ���������ֶ����� return��String
 */
function chooseQxArea(areacode,areaname) {
   var area_code=  areacode;
   var area_name = areaname;
   var url = "../../../ccapp/app_1/dzjctree/tree/QxareaRadioTree.jsp?area_code="+area_code+"&area_name="+area_name+"&t="+new Date().getTime();
   var rtn = window.showModalDialog(url,window,"center:yes");
}

/**
 * �ϼ������� param: areacode ��������ֶ���; param: areaname ���������ֶ����� return��String
 */
function chooseParentArea(areacode,areaname) {
   var area_code=  areacode;
   var area_name = areaname;
   var url = "../../../ccapp/app_1/dzjctree/tree/parentAreaRadioTree.jsp?area_code="+area_code+"&area_name="+area_name+"&t="+new Date().getTime();
   var rtn = window.showModalDialog(url,window,"center:yes");
}

/**
 * ������ param: orgCodeValue ������������ֵ; param: orgCode ���������ֶ����� param: orgName
 * ���������ֶ����� return��String
 */
function chooseOrg(orgCodeValue,orgCode,orgName,orgState){
	if(orgCodeValue==null){
	    orgCodeValue="";
	}
	
	  if(typeof(orgState)=="undefined"){
		orgState="";
	}
    var org_codevalue = orgCodeValue;
    // var parentOrgCodeValue = getParentAreaCode(org_codevalue);
	// var rootName=getAreaName(orgCodeValue);
	// var parentOrgCodeValue = orgCodeValue;
    var org_code= orgCode;
    var org_name = orgName;
    
    // var url =
	// "../../../ccapp/app_1/dzjctree/tree/orgRadioTree.jsp?orgCodeValue="+org_codevalue+"&parentOrgCodeValue="+parentOrgCodeValue+"&org_code="+org_code+"&org_name="+org_name+"&t="+new
	// Date().getTime()+"&orgState="+orgState;
	var url = "../../../ccapp/app_1/dzjctree/tree/dzjcOrgAreaTree.jsp?orgCodeValue="+org_codevalue+"&org_code="+org_code+"&org_name="+org_name+"&t="+new Date().getTime()+"&orgState="+orgState;    
    // var url = "../../../ccapp/app_1/dzjctree/tree/dzjcOrgAreaTree.jsp";
	 // alert(url);
	 var rtn = window.showModalDialog(url,window,"center:yes");

}


/**
 * ������ param: orgCodeValue ������������ֵ; param: orgCode ���������ֶ����� param: orgName
 * ���������ֶ����� return��String
 */
function chooseTestOrg(orgCodeValue,orgCode,orgName,orgState){
	  if(typeof(orgState)=="undefined"){
		orgState="";
	}
    var org_codevalue = orgCodeValue;
    // var parentOrgCodeValue = getParentAreaCode(org_codevalue);
	// var rootName=getAreaName(orgCodeValue);
	// var parentOrgCodeValue = orgCodeValue;
    var org_code= orgCode;
    var org_name = orgName;
    // var url =
	// "../../../ccapp/app_1/dzjctree/tree/orgRadioTree.jsp?orgCodeValue="+org_codevalue+"&parentOrgCodeValue="+parentOrgCodeValue+"&org_code="+org_code+"&org_name="+org_name+"&t="+new
	// Date().getTime()+"&orgState="+orgState;
	var url = "../../../ccapp/app_1/dzjctree/tree/dzjcTestOrgAreaTree.jsp?orgCodeValue="+org_codevalue+"&org_code="+org_code+"&org_name="+org_name+"&t="+new Date().getTime()+"&orgState="+orgState;    
   
	 var rtn = window.showModalDialog(url,window,"center:yes");

}


/**
 * Ȩ�޻����� param: orgCodeValue ������������ֵ; param: orgCode ���������ֶ����� param: orgName
 * ���������ֶ����� return��String
 */
function chooseQxOrg(orgCodeValue,orgCode,orgName,orgState){
	  if(typeof(orgState)=="undefined"){
		orgState="";
	}
    var org_codevalue = orgCodeValue;
    // var parentOrgCodeValue = getParentAreaCode(org_codevalue);
	var rootName=getAreaName(orgCodeValue);
	var parentOrgCodeValue = orgCodeValue;
    var org_code= orgCode;
    var org_name = orgName;
    // var url =
	// "../../../ccapp/app_1/dzjctree/tree/orgRadioTree.jsp?orgCodeValue="+org_codevalue+"&parentOrgCodeValue="+parentOrgCodeValue+"&org_code="+org_code+"&org_name="+org_name+"&t="+new
	// Date().getTime()+"&orgState="+orgState;
	var url = "../../../ccapp/app_1/dzjctree/tree/QxdzjcOrgAreaTree.jsp?orgCodeValue="+org_codevalue+"&parentOrgCodeValue="+parentOrgCodeValue+"&org_code="+org_code+"&org_name="+org_name+"&t="+new Date().getTime()+"&orgState="+orgState+"&rootName="+rootName;    
    // var url = "../../../ccapp/app_1/dzjctree/tree/dzjcOrgAreaTree.jsp";
	 // alert(url);
	 var rtn = window.showModalDialog(url,window,"center:yes");

}

/**
 * ƽ̨������ param: orgId ���������ֶ����� param: orgName ���������ֶ����� return��String
 */
function choosePTOrg(orgCode,orgName){
    var org_code= orgCode;
    var org_name = orgName;
    var url = "../../../ccapp/app_1/dzjctree/tree/orgPTRadioTree.jsp?&org_code="+org_code+"&org_name="+org_name+"&t="+new Date().getTime();   
    var rtn = window.showModalDialog(url,window,"center:yes");
	
}

/**
 * ƽ̨��Ա�� param: userId ���������ֶ����� return��String
 */
function choosePTUser(userId,userName,areaOrgId){
    var user_id = userId;
    var user_name = userName;
    var area_orgId = areaOrgId;
    var url = "../../../ccapp/app_1/dzjctree/tree/userPTRadioTree.jsp?user_id="+user_id+"&user_name="+user_name+"&area_orgId="+area_orgId+"&t="+new Date().getTime(); 

    var rtn = window.showModalDialog(url,window,"center:yes");
	
}

/**
 * �������� param: superviseTypeCode ����������ֶ���; param: superviseTypeName �����������ֶ�����
 * return��String
 */
function chooseSuperviseType(superviseTypeCode,superviseTypeName){
   var supervise_type_code= superviseTypeCode;
   var supervise_type_name = superviseTypeName;
   var url = "../../../ccapp/app_1/dzjctree/tree/superviseTypeRadioTree.jsp?supervise_type_code="+supervise_type_code+"&supervise_type_name="+supervise_type_name+"&t="+new Date().getTime();   
   var rtn = window.showModalDialog(url,window,"center:yes");
}

/**
 * ָ������� param: area_code ������������ֵ; param: pointTypeId ָ�����Id��id�� param:
 * pointTypeName ָ��������Ƶ�id�� param: parentPointTypeId �ϼ�ָ�����Id��id return��String
 */
function choosePointType(area_code,pointTypeId,pointTypeName,parentPointTypeId){
    var parentPointTypeIdValue = "0";
    if(!IsSpace(parentPointTypeId)) {
	parentPointTypeIdValue = document.getElementById(parentPointTypeId).value;
	if(IsSpace(parentPointTypeIdValue)) {
	    parentPointTypeIdValue = "0";
	}
    }
    var url = "../../../ccapp/app_1/dzjctree/tree/pointTypeTree.jsp?areaCodeValue="+area_code+"&parentPointTypeIdValue="+parentPointTypeIdValue+"&pointType_id="+pointTypeId+"&pointType_name="+pointTypeName+"&t="+new Date().getTime(); 
    var rtn = window.showModalDialog(url,window,"center:yes");
}

/**
 * ��Ч������ param: radixId ��Ч����Id��id�� param: radixName ��Ч����name��id; return��String
 */
function chooseRadix(radixId,radixName){
    var radix_id=radixId;
    var radix_name=radixName;
    var url = "../../../ccapp/app_1/dzjctree/tree/evalRadixTree.jsp?radix_id="+radixId+"&radix_name="+radix_name+"&t="+new Date().getTime();   
    var rtn = window.showModalDialog(url,window,"center:yes");
}


/**
 * ����ָ���� param: pointId ָ��Id��id�� param: pointName ָ��name��id; return��String
 */
function choosePoint(pointId,pointName,isRadio,area_code){
    var point_id=pointId;
    var point_name=pointName;
   // alert(isRadio);
    // alert(area_code);
    var url = "../../../ccapp/app_1/dzjctree/tree/evalPointTree.jsp?point_id="+pointId+"&point_name="+pointName+"&area_code="+area_code+"&t="+new Date().getTime(); 
    if("true" == isRadio) {
        url = "../../../ccapp/app_1/dzjctree/tree/evalPointRadioTree.jsp?point_id="+pointId+"&point_name="+pointName+"&area_code="+area_code+"&t="+new Date().getTime();
    }
    // alert(url);
    var rtn = window.showModalDialog(url,window,"center:yes");
}

/**
 * �������� param: itemId ������Id��id�� param: item_name ������name��id; return��String
 */
function chooseItem(itemId,itemNmae){
    var item_id=itemId;
    var item_name=itemNmae;
    var url = "../../../ccapp/app_1/dzjctree/tree/evalItemTree.jsp?item_id="+item_id+"&item_name="+item_name+"&t="+new Date().getTime();   
    var rtn = window.showModalDialog(url,window,"center:yes");
}

/**
 * �������ָ���� param��id ����idֵ�Ŀؼ�id param��name ����nameֵ�Ŀؼ�id return String
 */
function chooseFormulaItem(id,name) {
	var url = "../../../ccapp/app_1/dzjctree/tree/evalItemPointTree.jsp?id="+id+"&name="+name+"&t="+new Date().getTime();
	var rtn = window.showModalDialog(url,window,"center:yes");
}

/**
 * �û��� param: userName �û�������id�� param: userName �û�id�� param: mobile �ֻ������id;
 * return��String
 */
function chooseUser(userName,userId,mobile){
    var url = "../../../ccapp/app_1/dzjctree/tree/userRadixTree.jsp?user_name="+userName+"&user_id="+userId+"&mobile="+mobile+"&t="+new Date().getTime();   
    var rtn = window.showModalDialog(url,window,"center:yes");
}

/**
 * �������µ��û��� param: orgCode �����룻 return��String
 */
function chooseOrgUser(orgCode,userName,userId,mobile){
    var url = "../../../ccapp/app_1/dzjctree/tree/userRadixTree.jsp?orgCode="+orgCode+"&user_name="+userName+"&user_id="+userId+"&mobile="+mobile+"&t="+new Date().getTime();   
    var rtn = window.showModalDialog(url,window,"center:yes");
}

/**
 * ��ȡ��ϵͳ������������
 */
function getSysAreaCode(){
	var area_code = SqlToField("select baseinfo_value from ta_dic_baseinfo where baseinfo_key ='CURRENT_SYS_AREACODE'");
	return area_code;
}

/**
 * ��ȡ��������������
 */
function getAreaType(area_code){

	var areaType = SqlToField("select t.area_type from ta_area t where area_code = '"+area_code+"'");
	return areaType;
}


/**
 * ��ȡͼ�α���Ĭ����ʾ����
 */
function getDefaultChartPageCount(){

	var chartPageCount = SqlToField("select baseinfo_value from ta_dic_baseinfo where baseinfo_key ='DEFAULT_CHART_PAGE_COUNT'");
	return chartPageCount;
}

/**
 * ��ȡͼ�α��������ʾ����
 */
function getMaxChartPageCount(){

	var chartPageCount = SqlToField("select baseinfo_value from ta_dic_baseinfo where baseinfo_key ='MAX_CHART_PAGE_COUNT'");
	return chartPageCount;
}



/**
 * �ж������Ƿ���ʡ���� add by yin.liu 20110415
 */
function isProvinceCurrentLevel(area_org_code){
		area_org_code=area_org_code.substr(0,12);
	var c = SqlToField("select count(*) n from v_td_sm_organization where org_this_level='1' and substr(org_code,1,12)='"+area_org_code+"'");
	if(c>0){
	    return "true";
	}else{
	    return "false";
	}
}


/**
 * �ж������Ƿ����б��� add by yin.liu 20110414
 */
function isCityCurrentLevel(area_code){
	area_code=area_code.substr(0,12);

	var c = SqlToField("select count(*) n from v_td_sm_organization where org_this_level='2' and substr(org_code,1,12) = '"+area_code+"'");
	if(c>0){
	    return "true";
	}else{
	    return "false";
	}
}

/**
 * ��ȡ(12λ)�������� add by yin.liu 20110415
 */
function getOrgAreaName(area_code){
      return getOrgName(area_code);
	// var area_name = SqlToField("select org_name from v_td_sm_organization
	// where org_level in(1,2,3) and substr(org_code,1,12)= '"+area_code+"'");

	// return area_name;
}

/**
 * ��ȡ(24λ)�������� add by yin.liu 20110418
 */
function getOrgName(area_code){
   if(IsSpace(area_code)){
      return;
   }
    if(area_code.length==12){
       // return getOrgAreaName(area_code);
       area_code += "000000000000";
    }
	var area_name = SqlToField("select org_name from v_td_sm_organization where org_code= '"+area_code+"'");

	return area_name;
}


 
 /**
	 * ��ȡ���ݿ����ʱ�� add by yin.liu 20110415
	 */
function getDateOfData(){

	var sql_date="select to_char(sysdate,'yyyy-MM-dd') from dual ";
    var date = SqlToField(sql_date);   
	return date;
}


 /**
	 * ���ݻ����������伶�� add by yin.liu 20110415
	 */
function getOrgLevelOfOrgCode(orgCode){

    if(orgCode.length==12){
       orgCode=orgCode+"000000000000";
    }
	var sql="select org_level from v_td_sm_organization where org_code='"+orgCode+"' ";
    var level = SqlToField(sql);   
	return level;
}

 /**
	 * ���ݻ�����������ʡ������(24λ) add by yin.liu 20110415
	 */
function getProvinceCodeOfOrgCode(orgCode){

    if(orgCode.length==12){
       orgCode=orgCode+"000000000000";
    }
	var sql="select province_code from v_td_sm_organization where org_code='"+orgCode+"' ";

    var level = SqlToField(sql);   
    if(level=="000000000000"){return "";}   
	return level+"000000000000";
}

 /**
	 * ���ݻ������������м�����(24λ) add by yin.liu 20110415
	 */
function getCityCodeOfOrgCode(orgCode){
    if(orgCode.length==12){
       orgCode=orgCode+"000000000000";
    }
	var sql="select city_code from v_td_sm_organization where org_code='"+orgCode+"' ";
    var level = SqlToField(sql);   
    if(level=="000000000000"){return "";}   
	return level+"000000000000";
}


/**
 * ���ݻ��������������ֵ�������(24λ) add by yin.liu 20110415
 */
function getCountyCodeOfOrgCode(orgCode){
    if(orgCode.length==12){
       orgCode=orgCode+"000000000000";
    }
	var sql="select county_code from v_td_sm_organization where org_code='"+orgCode+"' ";
    var level = SqlToField(sql);   
    if(level=="000000000000"){return "";}   
	return level+"000000000000";
}

/**
 * ���ݻ��������������ֵ�������(24λ) add by yin.liu 20110415
 */
function getStreetCodeOfOrgCode(orgCode){
    if(orgCode.length==12){
       orgCode=orgCode+"000000000000";
    }
	var sql="select street_code from v_td_sm_organization where org_code='"+orgCode+"' ";
    var level = SqlToField(sql);
    if(level=="000000000000"){return "";}   
	return level+"000000000000";
}

/**
 * ��ʽ��������ʾ��ʽ��Ҳ�������ڶ������ض����ȡ� �÷��� formatNumber(0,'') formatNumber(12432.21,'#,###')
 * formatNumber(12432.21,'#,###.000#') formatNumber('12432.415','####.0#')
 * formatNumber(12432,'#,###.00')
 */
function formatNumber(number,pattern){
   var str = number.toString();
   var strInt;
   var strFloat;
   var formatInt;
   var formatFloat;
   if(/\./g.test(pattern)){
       formatInt = pattern.split('.')[0];
       formatFloat = pattern.split('.')[1];
   }else{
       formatInt = pattern;
       formatFloat = null;
   }

   if(/\./g.test(str)){
       if(formatFloat!=null){
           var tempFloat = Math.round(parseFloat('0.'+str.split('.')[1])*Math.pow(10,formatFloat.length))/Math.pow(10,formatFloat.length);
           strInt = (Math.floor(number)+Math.floor(tempFloat)).toString();                
           strFloat = /\./g.test(tempFloat.toString())?tempFloat.toString().split('.')[1]:'0';            
       }else{
           strInt = Math.round(number).toString();
           strFloat = '0';
       }
   }else{
       strInt = str;
       strFloat = '0';
   }
   if(formatInt!=null){
       var outputInt = '';
       var zero = formatInt.match(/0*$/)[0].length;
       var comma = null;
       if(/,/g.test(formatInt)){
           comma = formatInt.match(/,[^,]*/)[0].length-1;
       }
       var newReg = new RegExp('(\\d{'+comma+'})','g');

       if(strInt.length<zero){
           outputInt = new Array(zero+1).join('0')+strInt;
           outputInt = outputInt.substr(outputInt.length-zero,zero)
       }else{
           outputInt = strInt;
       }

       var 
       outputInt = outputInt.substr(0,outputInt.length%comma)+outputInt.substring(outputInt.length%comma).replace(newReg,(comma!=null?',':'')+'$1')
       outputInt = outputInt.replace(/^,/,'');

       strInt = outputInt;
   }

   if(formatFloat!=null){
       var outputFloat = '';
       var zero = formatFloat.match(/^0*/)[0].length;

       if(strFloat.length<zero){
           outputFloat = strFloat+new Array(zero+1).join('0');
           // outputFloat = outputFloat.substring(0,formatFloat.length);
           var outputFloat1 = outputFloat.substring(0,zero);
           var outputFloat2 = outputFloat.substring(zero,formatFloat.length);
           outputFloat = outputFloat1+outputFloat2.replace(/0*$/,'');
       }else{
           outputFloat = strFloat.substring(0,formatFloat.length);
       }

       strFloat = outputFloat;
   }else{
       if(pattern!='' || (pattern=='' && strFloat=='0')){
           strFloat = '';
       }
   }
   return strInt+(strFloat==''?'':'.'+strFloat);
}

/*
 * ���ú��������text��textarea���Ƶ�ֵ������combobox������������ؼ���ֵ��Ϊ0
 * arguments_textΪ����text��textarea�Ŀؼ�id������ arguments_textΪ����������ȿؼ�id������
 * ע�⣺������ؼ��������ֵΪ��0����ѡ������ѡ�����ʾֵΪ��ȫ�����ȴ�
 */
function reset_common(arguments_text,arguments_combobox){
	for (var i=0; i<arguments_text.length; i++) {
		document.getElementById(arguments_text[i]).value = "";
	}
	for (var j=0; j<arguments_combobox.length; j++)
	{
		document.getElementById(arguments_combobox[j]).value = "0";
	}
}

/*
 * ���������ĳ���һ��ѡ�����������ѡ�� idΪҪ������������id
 */
function clearCombo(id){
	var object = document.getElementById(id);
	var optionIndex = object.options.length;
	for(;optionIndex>=1;optionIndex--){
		object.options.remove(optionIndex);
	}
}

/*
 * ��������������ѡ�� idΪҪ������������id
 */
function clearCombo_all(id){
	var object = document.getElementById(id);
	var optionIndex = object.options.length;
	for(;optionIndex>=0;optionIndex--){
		object.options.remove(optionIndex);
	}
}

/*
 * ��ѯ����������ָ����sql����ָ���ֶε����򷽷���ѯ���� dsΪ���ݼ� pgaeSizeΪ���ݼ�ÿҳ��ѯ������ sqlΪ��ѯ��sql���
 * FieldsΪҪ������ֶ����� OrdersΪҪ������ֶε�˳������ ע�⣺Fields��Orders�ĳ��ȱ���һ�£����򽫱���
 */
function findData(ds,pageSize,sql,Fields,Orders){
	for(var i=0;i<Fields.length;i++){
		if(Orders[i]=="����"){
			sql += " order by "+Fields[i]+" desc";
		}
	}
	ds.PageSize = pageSize;
	ds.Open(sql);
}

/*
 * ����һ��������������ģʽ�Ի��� urlΪ�Ի����·�� widthΪ�Ի���Ŀ�� heightΪ�Ի���ĸ߶�
 */
function openDlg(url,width,height){
	var returnVal = window.showModalDialog(url,window,"dialogWidth="+width+"px;dialogHeight="+height+"px;scroll=no;resizable=no;status=no");
	return returnVal;
}

/*
 * ����һ������������ģʽ�Ի��� urlΪ�Ի����·�� widthΪ�Ի���Ŀ�� heightΪ�Ի���ĸ߶�
 */
function openDlg_scroll(url,width,height){
	var returnVal = window.showModalDialog(url,window,"dialogWidth="+width+"px;dialogHeight="+height+"px;scroll=yes;resizable=no;status=no");
	return returnVal;
}

// ֻ������
function elementReadOnly(element,style) { // element ������ֻ����Ԫ�� style
											// ���ڶ�̬�������������Ӧ�õ���ʽ
	var id = element.id;
	var value = element.value;
	var tmp = element.outerHTML.substring(0,7).toUpperCase();
	if (tmp == "<SELECT") // ѡ����ȡֵ��Ҫ���⴦��
	{
		value = element.options[element.selectedIndex].text;
	}
	// ���������
	var myElement = document.createElement("input");
	myElement.setAttribute("id","my_" + id);
	myElement.setAttribute("value",value); // ��ֵ
	myElement.style.width = element.style.width; // Эͬ�칫���ĵ���Ҫ���� ����ͨ��ԭԪ�ص����Դ���
	myElement.readOnly = "true"; // ֻ��
	myElement.className = style; // Ӧ����ʽ
	element.style.display = "none"; // ����ԭԪ��
	element.parentNode.insertBefore(myElement,element); // ����̬�������������뵽ԭԪ��֮ǰ
}

// js����js
document.write("<script language='javascript' src='../../../ccapp/app_5/js/prototype.js'></script>");

function trimString(str){
    str = this!=window?this:str;
    return str.replace(/^\s+/g,'').replace(/\s+$/g,'');
}
function trim(checkedObject) {
	return checkedObject.replace(/(^\s+)|\s+$/g, "");
}

String.prototype.trim = trimString;

function NumberToMoneyStr(aNumber,aFixLen,aDigital) // return 1000500 to
													// 1,000,500
{
	var lblankStr = "                                               ";
	var lstr = "" + aNumber;
	var lFixLen = aFixLen == null ? 1 : ( aFixLen > 20 ? 20 : aFixLen)
	var ldigital = aDigital == null ? 3 : aDigital	

	var lbase = lstr.length % ldigital;
	var ldstr = (lbase >0) ? lstr.substr( 0, lbase) : "";	
	var lneed = true;
	if(lbase == 0 || (lbase ==1 && ldstr == "-"))
	{
		lneed = false;
	}
	
	for(var i = lbase; i < lstr.length; i +=ldigital)
	{
		
		ldstr += (lneed ? "," : "") + lstr.substr(i,ldigital);
		lneed = true;
	}
	
	if (ldstr.length < lFixLen)
	{
		ldstr = lblankStr.substr(0,lFixLen - ldstr.length) + ldstr;
	}
	return ldstr;	 
}
function MoneyStrToNumber(aStr,aDigital) // 1,000,500 to 1000500
{
	if(aStr == null || aStr == "") return 0;
	var lstr = aStr.trim()	
	if(lstr == "" ) return 0;
	lstr = aStr.replace(/,/g,'')
	
	var lint = parseInt(lstr);
	if(isNaN(lint)) lint = -1;
	return lint;
}


function DateToString(aDate) // return aStr like 2001-03-05
{

	if(aDate == null || aDate.constructor != Date) return "";
// aDate instanceof Date
	try{
		var ys = aDate.getFullYear();
		var ms = aDate.getMonth() + 1;
		var ds = aDate.getDate();
		
		var s = "" + ys + "-";
		
		if(ms<10) s += "0";
		s += ms +"-"
		
		if(ds<10) s +="0";
		s +=ds
		
		return s
	
	}catch(e){
		return "";
	}

}


function StringToDate(aStr) // aStr like 2001-03-05, return a DateObj
{
	var y = aStr.indexOf("-")
	if( y <=0 ) return null;
	var ys = parseInt(aStr.substr(0,y), 10);
	
	var m =  aStr.indexOf("-", y + 1)
	if( m <= y + 1 ) return null;
	var ms = parseInt(aStr.substr(y+1, m - y -1), 10);
	
	var ds = parseInt(aStr.substr(m + 1) , 10);

	if( isNaN(ys) || isNaN(ms) || isNaN(ds) ) 	return null;

	if( ys < 1970 || ys > 9999 ||
	    ms < 1    || ms > 12   ||
	    ds < 1    || ds > 31      ) return null	
	    
	if( ds > 28 ){
		if(ds == 31 )
		{
			if( ms == 2 || ms == 4 || ms ==6 || ms == 9 || ms == 11 ) return null;
		}
		else if( ds == 30 ){
			if( ms == 2 ) return null;
		}else {
			if( ms == 2 ){
				if( ys % 4 == 0 ) {
					if( ys % 100 == 0 && ys % 400 != 0){
						return null;
					}
					// yes...
				}else{
					return null;
				}
				
			}
		}
		
	}
	
	
	return new Date(ys, ms - 1, ds);
	
}
function StrToDateStr(aStr) // return "" or like 2001-03-05
{
	if(aStr == null || aStr == "") return "";
	
	var y = aStr.indexOf("-")
	if( y <=0 ) return "";
	var ys = parseInt(aStr.substr(0,y), 10);
	
	var m =  aStr.indexOf("-", y + 1)
	if( m <= y + 1 ) return "";
	var ms = parseInt(aStr.substr( y + 1, m - y - 1), 10);
	
	var ds = parseInt(aStr.substr(m + 1), 10);

	if( isNaN(ys) || isNaN(ms) || isNaN(ds) ) 	return "";

	if( ys < 1970 || ys > 9999 ||
	    ms < 1    || ms > 12   ||
	    ds < 1    || ds > 31      ) return ""	
	    
	if( ds > 28 ){
		if(ds == 31 )
		{
			if( ms == 2 || ms == 4 || ms ==6 || ms == 9 || ms == 11 ) return "";
		}
		else if( ds == 30 ){
			if( ms == 2 ) return "";
		}else {
			if( ms == 2 ){
				if( ys % 4 == 0 ) {
					if( ys % 100 == 0 && ys % 400 != 0){
						return "";
					}
					// yes...
				}else{
					return "";
				}
				
			}
		}
		
	}
	
	var s = "" + ys + "-";
	if(ms<10) s += "0";
	s +=ms +"-"
	if(ds<10) s +="0";
	s +=ds
	// alert(s);
	return s
	 
}

function StringIsDate(aStr) // is aStr like 2001-03-05, return true of false;
{
	if(aStr == null || aStr == "") return false;
	
	var y = aStr.indexOf("-")
	if( y <=0 ) return false;
	var ys = parseInt(aStr.substr(0,y), 10);
	
	var m =  aStr.indexOf("-", y + 1)
	if( m <= y + 1 ) return false;
	var ms = parseInt(aStr.substr(y+1, m - y - 1), 10);
	
	var ds = parseInt(aStr.substr(m + 1), 10);

	if( isNaN(ys) || isNaN(ms) || isNaN(ds) ) 	return false;

	if( ys < 1970 || ys > 9999 ||
	    ms < 1    || ms > 12   ||
	    ds < 1    || ds > 31   ) 
	    return false	
	    
	if( ds > 28 ){
		if(ds == 31 )
		{
			if( ms == 2 || ms == 4 || ms ==6 || ms == 9 || ms == 11 ) return false;
		}
		else if( ds == 30 ){
			if( ms == 2 ) return false;
		}else {
			if( ms == 2 ){
				if( ys % 4 == 0 ) {
					if( ys % 100 == 0 && ys % 400 != 0){
						return false;
					}
					// yes...
				}else{
					return false;
				}
				
			}
		}
		
	}
	
	return true;
	 
}
function link3(url,w,h)
{
	window.open(url, '', 'toolbar=0, scrollbars=0, resizable=1, width='+w+', height='+h+', top='+(screen.availHeight/2-h/2)+', left='+(screen.availWidth/2-w/2)+' ');
}

function link2(url,w,h)
{
	window.open(url, '', 'toolbar=0, scrollbars=1, resizable=1, width='+w+', height='+h+', top='+(screen.availHeight/2-h/2)+', left='+(screen.availWidth/2-w/2)+' ');
}

function link(url,w,h)
{
	window.open(url, '', 'toolbar=0, scrollbars=0, resizable=0, width='+w+', height='+h+', top='+(screen.availHeight/2-h/2)+', left='+(screen.availWidth/2-w/2)+' ');
}


/* ����TABҳ����ʾ�����أ���TABҳ����Ϊ��׼ */
function hideOrShowTabByName(name, type) {
	  var realStyle = "";
	  if(IsSpace(type) || (type.toUpperCase()=="hide".toUpperCase())) {
		    realStyle = "none";
	  }
	  var objs = document.getElementsByTagName("H2");
	  for(var i=0; i<objs.length; i++) {
		    if(trim(objs[i].innerText) == trim(name)) {
			      objs[i].style.display = realStyle;
		    }
	  }
}

/*ֻ��ʾ�������ֵ�TABҳ*/
function onlyShowTabByName(name) {
	  var objs=document.getElementsByTagName("H2");
	  for(i=0;i<objs.length;i++) {
		    if(trim(objs[i].innerText)!=trim(name)) {
			      objs[i].style.display="none";
		    }else {
			      objs[i].style.display="";
		    }
	  }
}

/*��ʾ����TABҳ ��������ָ��������*/
function showAllTabExcept(name) {
	var objs=document.getElementsByTagName("H2");
	for(i=0;i<objs.length;i++) {

                  if(trim(objs[i].innerText)==trim(name)) {
			      objs[i].style.display="none";
		    }else {
			      objs[i].style.display="";
		    }
	}
}

/*��ʾ����TABҳ*/
function showAllTab() {
	var objs=document.getElementsByTagName("H2");
	for(i=0;i<objs.length;i++) {
		objs[i].style.display="";
	}
}

/*��������TABҳ*/
function hideAllTab() {
	var objs=document.getElementsByTagName("H2");
	for(i=0;i<objs.length;i++) {
		objs[i].style.display="none";
	}
}




/*����ҳ��Ԫ��ID������ҳ��Ԫ��,����Ԫ��ID������*/
function hideElementsById(elements){
   var element;
   for(var i=0;i<elements.length;i++){
   	  try{
         element=document.getElementById(elements[i]);
         element.style.display="none";
      }catch(e){}
  }
}

/*����ҳ��Ԫ��ID����ʾҳ��Ԫ��,����Ԫ��ID������*/
function showElementsById(elements){
    var element;
    for(var i=0;i<elements.length;i++){
    	 try{
          element = document.getElementById(elements[i]);
          element.style.display="";
       }catch(e){}
    }
}


/*�õ�ѡ�еĵ�ѡ��ֵ*/
function getRadioValue(RadioName) {
	RadioName="RG"+RadioName;
	var radioItem=document.getElementsByName(RadioName);
	for(i=0;i<radioItem.length;i++) {
		if(radioItem[i].checked) {
			return radioItem[i].value;
		}
	}
}


/*�õ���Ŀ������� EC_ID*/
function getEcId(){
    var url=parent.parent.document.URL;
    var purl=url.split("?");
    var vurl= purl[1].split("&");
    var vec_id=vurl[vurl.length-1]
    return vec_id.split("=")[1];
}


/* ͨ��TABLEIDȥ��������HTMLԪ�صķ��ʷ�Χ����ֻ�����ɱ༭���������� */
function setTableVisitStatus(tableId,visitType){
    var tableInfo = document.getElementById(tableId);
    var arrInput = tableInfo.getElementsByTagName("input");
    for(var i=0; i<arrInput.length; i++){
        var type = arrInput[i].getAttribute("controltype");
        if((type!=null) && (type!="undefind")){
            if(type.toLowerCase()=="text"){
                if(visitType.toLowerCase() == "view"){
                    arrInput[i].readOnly = true;
                }else if(visitType.toLowerCase() == "edit"){
                    arrInput[i].readOnly = false;
                }else{
                	  arrInput[i].style.display = "none";
                }
            }
        }
    }
    var arrComb = tableInfo.getElementsByTagName("select");
    for(var i=0; i<arrComb.length; i++){
        if(visitType.toLowerCase() == "view"){
            arrComb[i].disabled = true;
        }else if(visitType.toLowerCase() == "edit"){
            arrComb[i].disabled = false;
        }else{
            arrComb[i].style.display = "none";
        }
    }
}


/* ���ָ��TABLE�����������ֶ� */
function setTableElementClear(tableId){
	  var tableInfo = document.getElementById(tableId);
    var arrInput = tableInfo.getElementsByTagName("input");
    for(var i=0; i<arrInput.length; i++){
        var type = arrInput[i].getAttribute("controltype");
        if((type!=null) && (type!="undefind")){
            if(type.toLowerCase()=="text"){
                arrInput[i].value = "";
                arrInput[i].readOnly = false;
            }
        }
    }
    var arrComb = tableInfo.getElementsByTagName("select");
    for(var i=0; i<arrComb.length; i++){
    	  arrComb[i].value = "";
        arrComb[i].disabled = false;
    }
}

/* �����������ϸ��ҳ�� */
function toShowTaskList(taskflow_id) {
	  var para = null;
         //para = new Array(taskflow_id,"0");		
	 //  DjOpen(1472,para,'չ��',"��ģʽ����","ֱ��","���������ϸ");  
       var url="1472.jsp?cc_form_instanceid="+parent.cc_form_instanceid+
               "&taskflow_id="+taskflow_id;   
       
       window.showModalDialog(url,para,"dialogWidth:630px;");
	 
}

/* ������ѡ������һ��ֵ�ǿյ�ѡ�� */
function buildEmptyOption(comboxId){
    var first = document.createElement("option");
    var text = document.createTextNode("-��ѡ��-");
    first.appendChild(text);
    document.getElementById(comboxId).appendChild(first);
}


/*�õ����ݿ�ʱ��*/
function getSysDate(){
	var sql="select to_char(sysdate,'yyyy-mm-dd') from dual";
	return SqlToField(sql);
}

/*�õ����ݿ�ʱ��*/
function getSysDateTime(){
	var sql="select to_char(sysdate,'yyyy-mm-dd hh24:mi:ss') from dual";
	return SqlToField(sql);
}
   

/* ��ӡ���� */
function printReport(raqId){
	  var rptArgs = getRptData();
	  var rptUrl = creator_getContextPath() + "/report/showreport.jsp?raq=" + raqId + rptArgs+"&needDecode=true";
    window.open(rptUrl);
}

/* ���ر�������ַ��� */
function getRptData(){
	  var args = "";
	  return args;
}

/* ���������� word �ؼ�ʹ��
 * word ��ʼ������
 * status ��word ״̬��  
 * tempDoc��word ģ��ID��
 */
function loadWordByStatusAndTemplete(status,tempDoc){

   showModalDialog(getOpenUrlByDjid("20081028092118156028")+ "&status="+status+"&tempDoc="+tempDoc+"&cc_form_instanceid="+creator_getSession('cc_form_instanceid'),window,"status:no;resizable:yes;;unadorne:yes;scroll:no");  
}

/* �ڻָ�ҳ��������"�˴�"��"��һ��"��ť
* author ������
*/
function hiddenButtonFromResume(shenpi_insid)
{
    var sql="select status from app_5.ta_sp_instance where shenpi_insid='"+shenpi_insid+"'";
   var status=SqlToField(sql);
   
   if(status.trim()=="ҵ�����")
   {
       document.getElementById("btnNextStep").style.display="none";
       document.getElementById("btnReject").style.display="none";
   }
}

//�����շ�
function setChargetype(txt_type_id){
	try{ 
	  var id = (txt_type_id)?txt_type_id:"textxmlx"
	  var types = '���շ�,��������,ͳ�����,�������շ�,��ҵ���շ�,˰��,�������շ�,������ĿԤ�շ�,������Ŀ���շ�';
	  $(id).value = types.split(",")[$(id).value];
	}catch(e){
	}
}


/***************************����ϵͳ����js**********************************/

/**
*** add by ����
***���õ�js �����ҵ���޹ز���
***���ڴ����ַ����� ת���Ȳ���
***
***2008 - 12 - 10
***
***/

//���ַ���ת�������ݿ���ʶ����ַ���
// add by zhangxian 2008 -12-10
function escape2Sql( text ){
   return text.replace(/'/g,"''");
}

//�����ݵ�url��ַ�����ĺ��ַ���ʽ�� add by zhangxian 2008 -12-10
function getEscapeStr4Url(test){
    return base64encode(test);
}


/**************************************loading************************************/
/*************************** BEGIN ***************************/
/**
 *
 * ���ȣ����� createGcjDhccFullScreen() ���� fullScreen(ȫ�����ǲ�)
 * Ȼ��
 * ���� showGcjDhccFullScreen() ��ʾ fullScreen
 * ���� hideGcjDhccFullScreen() ���� fullScreen
 * 
 * ********** ����˵�� **********
 * path : ����·�����ñ�������ҳ�涨���ֵ�� var path = "<%=path%>";
 * gcjDhccFullScreenLoadingText : ���� fullScreen ��ļ���ʱ��ʾ�����֣�������HTML��
 * gcjDhccFullScreenLoadingTextAlign : ���� fullScreen ��ļ���ʱ��ʾ������λ�ڼ���ͼƬ��λ�ã�ͼƬ�Ϸ� up���� down���� left���� right
 * gcjDhccFullScreenLoadingImgHAlign : ���� fullScreen ��ļ���ͼƬ��ˮƽλ�ã��� left���� center���� right
 * gcjDhccFullScreenLoadingImgVAlign : ���� fullScreen ��ļ���ͼƬ����ֱλ�ã��� top���� middle���� bottom
 * gcjDhccFullScreenBKImgPath : ���� fullScreen ��ı���ͼƬ·��
 * gcjDhccFullScreenLoadingImgPath : ���� fullScreen ��ļ���ͼƬ·��
 * gcjDhccFullScreenDocumentObj : ���� fullScreen �����Ǹ�ҳ����ʾ
 *     ��ҳ�棺var gcjDhccFullScreenDocumentObj = window.document;
 *     ��ҳ�棺var gcjDhccFullScreenDocumentObj = window.parent.document;
 *     ��ҳ��ĸ�ҳ�棺var gcjDhccFullScreenDocumentObj = window.parent.parent.document;
 *     �������ơ�
 * 
 * ����ȱ�ݣ�gcjDhccFullScreenDocumentObj ����ֻ�������ڱ�ҳ�棬�����ڸ�ҳ��ʱ�޷����� fullScreen�� hideGcjDhccFullScreen() ����ʱ�ᱨ�� ��
 * 
 */

var gcjDhccFullScreenLoadingText = '<font color="#ff0000">������ ������</font>';
var gcjDhccFullScreenLoadingTextAlign = "up";
var gcjDhccFullScreenLoadingImgPath = "../../../ccapp/app_5/images/ajax-loader.gif";
var gcjDhccFullScreenLoadingImgHAlign = "center";
var gcjDhccFullScreenLoadingImgVAlign = "middle";
var gcjDhccFullScreenBKImgPath = "";
var gcjDhccFullScreenDocumentObj = window.document;

/**
 * ���� fullScreen(ȫ�����ǲ�)
 * 
 * loadingText = gcjDhccFullScreenLoadingText
 * loadingTextAlign = gcjDhccFullScreenLoadingTextAlign
 * loadingImgPath = gcjDhccFullScreenLoadingImgPath
 * imgHAlign = gcjDhccFullScreenLoadingImgHAlign
 * imgVAlign = gcjDhccFullScreenLoadingImgVAlign
 * bKImgPath = gcjDhccFullScreenBKImgPath
 * documentObj = gcjDhccFullScreenDocumentObj
 * 
 * ����Ҫ�ı�Ĳ����ÿգ�ֵΪ null �������ַ��� "null" ��
 * 
 */
function createGcjDhccFullScreen(loadingText, loadingTextAlign, loadingImgPath, imgHAlign, imgVAlign, bKImgPath, documentObj)
{
	//��ʼ������
	if(loadingText == null){ loadingText = gcjDhccFullScreenLoadingText; }
	if(loadingTextAlign == null){ loadingTextAlign = gcjDhccFullScreenLoadingTextAlign; }
	if(loadingImgPath == null){ loadingImgPath = gcjDhccFullScreenLoadingImgPath; }
	if(imgHAlign == null){ imgHAlign = gcjDhccFullScreenLoadingImgHAlign; }
	if(imgVAlign == null){ imgVAlign = gcjDhccFullScreenLoadingImgVAlign; }
	//if(bKImgPath == null){ bKImgPath = gcjDhccFullScreenBKImgPath; }
	if(documentObj == null){ documentObj = gcjDhccFullScreenDocumentObj; }
	else{gcjDhccFullScreenDocumentObj = documentObj;}
	
	//�ж� fullScreen �Ƿ����
	try{
		if(documentObj.getElementById("gcjDhccFullScreen")){ return null; }
	}
	catch(e){ }
	//��ʼ���� fullScreen
	var divObj = documentObj.createElement("div");
	divObj.setAttribute("id", "gcjDhccFullScreen");
	divObj.style.display = "none";  //���ɼ�
	divObj.style.position = "absolute";  //����
	divObj.style.top = 0;
	divObj.style.left = 0;
	divObj.style.width = documentObj.body.offsetWidth;
	divObj.style.height = documentObj.body.offsetHeight;
	divObj.style.background="#FFF";
	if(bKImgPath != null)
	{
		divObj.style.backgroundImage = "url("+ bKImgPath +")";
	}
	//divObj.style.backgroundImage = "url("+ bKImgPath +")";
	divObj.style.zIndex = 999;
	
	var loadingHtml = '<table width="100%" height="100%" align="'+ imgHAlign +'" valign="'+ imgVAlign +'" border="0" cellpadding="0" cellspacing="0">';
	loadingHtml += '<tr><td ondblclick="hideGcjDhccFullScreen();" align="'+ imgHAlign +'" valign="'+ imgVAlign +'">';
	//ͼƬ�Ϸ� up���� down���� left���� right
	if(loadingTextAlign == "up"){
		loadingHtml += '<div align="'+ imgHAlign +'">'+ loadingText +'<div>';
		loadingHtml += '<img src="'+ loadingImgPath +'" />';
	}
	else if(loadingTextAlign == "down"){
		loadingHtml += '<img src="'+ loadingImgPath +'" />';
		loadingHtml += '<div align="'+ imgHAlign +'">'+ loadingText +'<div>';
	}
	else if(loadingTextAlign == "left"){
		loadingHtml += loadingText +'&nbsp;&nbsp;';
		loadingHtml += '<img src="'+ loadingImgPath +'" />';
	}
	else if(loadingTextAlign == "right"){
		loadingHtml += '<img src="'+ loadingImgPath +'" />';
		loadingHtml += '&nbsp;&nbsp;'+ loadingText;
	}
	loadingHtml += '</td></tr>';
	loadingHtml += '</table>';
	
	divObj.innerHTML = loadingHtml;
	
	//��ʼ���� iframe
	var iframeObj = documentObj.createElement("iframe");
	iframeObj.setAttribute("id", "gcjDhccFullScreenIframe");
	iframeObj.style.filter = "progid:DXImageTransform.Microsoft.Alpha(style=0,opacity=0)";
	
	iframeObj.style.display = "none";  //���ɼ�
	iframeObj.style.position = "absolute";  //����
	iframeObj.style.top = 0;
	iframeObj.style.left = 0;
	iframeObj.style.width = documentObj.body.offsetWidth;
	iframeObj.style.height = documentObj.body.offsetHeight;
	iframeObj.style.zIndex = 998;
	
	// body ����� iframe
	documentObj.body.appendChild(iframeObj);
	
	// body ����� div
	documentObj.body.appendChild(divObj);
}
//��ʾ fullScreen
function showGcjDhccFullScreen()
{
	try{
		gcjDhccFullScreenDocumentObj.getElementById("gcjDhccFullScreen").style.display = "block";
		gcjDhccFullScreenDocumentObj.getElementById("gcjDhccFullScreenIframe").style.display = "block";
	}
	catch(e){ window.alert('showGcjDhccFullScreen'); }
}
//���� fullScreen
function hideGcjDhccFullScreen()
{
	try{
		gcjDhccFullScreenDocumentObj.getElementById("gcjDhccFullScreen").style.display = "none";
		gcjDhccFullScreenDocumentObj.getElementById("gcjDhccFullScreenIframe").style.display = "none";
	}
	catch(e){ window.alert('hideGcjDhccFullScreen'); }
}
/**************************** END ****************************/


function showLoading()
{
 	createGcjDhccFullScreen('<font style="font-size:15px; color:#ff0000;">���������� ������</font>', 'right', null, null,null, null, null);
	showGcjDhccFullScreen();
}

function hideLoading()
{
	hideGcjDhccFullScreen();
}

/*ȡ��ǰ����*/
function getCrntDateFmt1() {
	var date = new Date();
	var year = date.getFullYear();
	var month = date.getMonth()+1;
	var day = date.getDate();
	month = month  + "";
	day = day + "";

	if(month.length<2) {
		month = "0"+month;
	}

	if(day.length<2) {
		day = "0" + day;
	}

	return (year+month+day);
}

/*��ʽ���ַ���*/
function fmtAddZero(orgStr,desLen) {
	while(orgStr.length < desLen) {
		orgStr = "0" + orgStr;
	}

	return orgStr;
}

/*ȥ��ǰ�����õ�'0'������'008'Ҫ�޸ĳ�'8'*/
function fmtRemoveZero(orgStr) {
	var desStr = orgStr;

	while(desStr.length > 0) {
		var c = desStr.charAt(0);

		if(c == '0') {
			desStr = desStr.substr(1);
		}
		else {
			break;
		}
	}

	return desStr;
}

/*���̰��,���������0��֤�������1��֤�������2��֤�������3��֤�����*/
function finishMyWorkFlow(type){
    switch(type){
	  case 1: finishVersionOne();
			  break;
	  case 2: finishVersionTwo();
			  break;
	  
	  case 3: finishVersionThree();
	          break;
	  
	  case 4: finishVersionFore();
	          break;
	
	}
}

/**
*�ַ��п�
*/
function isSpace(strMain) {
	var strComp = strMain;
	try {
		if (strComp == "��" || strComp == "" || strComp == " "
				|| strComp == null || strComp == "null" || strComp.length == 0
				|| typeof strMain == "undefined" || strMain == "undefined") {
			return true;
		} else {
			return false;
		}
	} catch (e) {
		return false;
	}
}

//ȡ�õ�ѡ��ť���ʵ��ֵ
function getRadioGroupValue(objName){
  var radioGroupValue = "";
  var ridList = document.getElementsByName(objName);
  for(var i=0; i<ridList.length; i++){
      if(ridList[i].checked){
          radioGroupValue = ridList[i].value;
          break;
      }
  }
  return radioGroupValue; 
}


//���õ�ѡ��ť���ʵ��ֵ
function setRadioGroupValue(objName, objVal){
  var ridList = document.getElementsByName(objName);
  for(var i=0; i<ridList.length; i++){
      if(ridList[i].value == objVal){
          ridList[i].checked = true;
          break;
      }
  }
}

/*
* �����жϱ������ÿؼ��е�iframe�Ƿ������ɡ�
* ������iframeid �����˸ò�������ֻ�жϸ�iframe�Ƿ���ɼ��ء�
* ����ֵ��"true"��������ɣ�'false':����δ��ɡ�
*/
function creator_iframeIsCompleted(iframeid){
  var oIframes = document.all.tags('iframe');

  for(var i = 0;i < oIframes.length;i++){
      if(!IsSpace(oIframes[i].controltype) && oIframes[i].controltype=="creatorSubForm"){
      	if(!IsSpace(iframeid)){
      		if(oIframes[i].id != iframeid){
      			continue;
      		}
      	}
      	if(oIframes[i].autoload=='yes'){
          	//alert(oIframes[i].id+":"+oIframes[i].readyState);
          	if(oIframes[i].src =="" || oIframes[i].readyState!='complete'){
              	return 'false';
          	}
      	}
      }
  }
  return 'true';  
}

//ɾ���ܰ�ť��Ϊ
function checkAll(totalCheck,checkName){
   var selectAll = document.getElementsByName(totalCheck);
   var o = document.getElementsByName(checkName);
   if(selectAll[0].checked==true){
	   for (var i=0; i<o.length; i++){
      	  if(!o[i].disabled){
      	  	o[i].checked=true;
      	  }
	   }
   }else{
	   for (var i=0; i<o.length; i++){
   	  	  o[i].checked=false;
   	   }
   }
}

//����ɾ����ť��Ϊ
function checkOne(totalCheck,checkName){
   var selectAll = document.getElementsByName(totalCheck);
   var o = document.getElementsByName(checkName);
	var cbs = true;
	for (var i=0;i<o.length;i++){
		if(!o[i].disabled){
			if (o[i].checked==false){
				cbs=false;
			}
		}
	}
	if(cbs){
		selectAll[0].checked=true;
	}else{
		selectAll[0].checked=false;
	}
}


//ȫѡ��ȡ��
function checkAll(checkName,thisobj){
	 var checkObj = document.getElementsByName(checkName);
	 if( checkObj.length > 0){
	 	if(thisobj.innerText == "ȫѡ"){//��ʾȫѡ
	 		for( var i = 0 ; i < checkObj.length ; i ++){
	 			checkObj[i].checked = true;
	 		}
	 		thisobj.innerText = "ȡ��";
	 	}else{//��ʾ��ѡ
	 		for( var i = 0 ; i < checkObj.length ; i ++){
	 			checkObj[i].checked = false;
	 		}
	 		thisobj.innerText = "ȫѡ";
	 	}
	 }
}
function getCheckBoxsValue(boxname){
	var checkboxs=document.getElementsByTagName("input"); 
	var i,rv=""; 
	for(i=0;i<checkboxs.length; i++) { 
		if(checkboxs[i].type=='checkbox'&&boxname.indexOf(boxname)!=-1) {
			if(checkboxs[i].checked){
				if(rv!=""){
					rv+=",";
				}
				rv += checkboxs[i].value;
			}
		} 
	}
	return rv;
}


/*
	* ���������ĳ���һ��ѡ�����������ѡ��	
	* idΪҪ������������id
*/
function clearCombo(id){
	var object = document.getElementById(id);
	var optionIndex = object.options.length;
	for(;optionIndex>=1;optionIndex--){
		object.options.remove(optionIndex);
	}
}

/*
	* ��������������ѡ��	
	* idΪҪ������������id
*/
function clearCombo_all(id){
	var object = document.getElementById(id);
	var optionIndex = object.options.length;
	for(;optionIndex>=0;optionIndex--){
		object.options.remove(optionIndex);
	}
}

/*
	* ��ѯ����������ָ����sql����ָ���ֶε����򷽷���ѯ����
	* dsΪ���ݼ�
	* pgaeSizeΪ���ݼ�ÿҳ��ѯ������
	* sqlΪ��ѯ��sql���
	* FieldsΪҪ������ֶ�����
	* OrdersΪҪ������ֶε�˳������
	* ע�⣺Fields��Orders�ĳ��ȱ���һ�£����򽫱���
*/
function findData(ds,pageSize,sql,Fields,Orders){
	for(var i=0;i<Fields.length;i++){
		if(Orders[i]=="����"){
			sql += " order by "+Fields[i]+" desc";
		}
	}
	ds.PageSize = pageSize;
	ds.Open(sql);
}

/*
	* ����һ��������������ģʽ�Ի���
	* urlΪ�Ի����·��
	* widthΪ�Ի���Ŀ��
	* heightΪ�Ի���ĸ߶�
*/
function openDlg(url,width,height){
	var returnVal = window.showModalDialog(url,window,"dialogWidth="+width+"px;dialogHeight="+height+"px;scroll=no;resizable=no;status=no");
	return returnVal;
}

/*
	* ����һ������������ģʽ�Ի���
	* urlΪ�Ի����·��
	* widthΪ�Ի���Ŀ��
	* heightΪ�Ի���ĸ߶�
*/
function openDlg_scroll(url,width,height){
	var returnVal = window.showModalDialog(url,window,"dialogWidth="+width+"px;dialogHeight="+height+"px;scroll=yes;resizable=no;status=no");
	return returnVal;
}

//ֻ������
function elementReadOnly(element,style) { //element ������ֻ����Ԫ�� style ���ڶ�̬�������������Ӧ�õ���ʽ
	var id = element.id;
	var value = element.value;
	var tmp = element.outerHTML.substring(0,7).toUpperCase();
	if (tmp == "<SELECT") //ѡ����ȡֵ��Ҫ���⴦��
	{
		value = element.options[element.selectedIndex].text;
	}
	//���������
	var myElement = document.createElement("input");
	myElement.setAttribute("id","my_" + id);
	myElement.setAttribute("value",value); //��ֵ
	myElement.style.width = element.style.width; //Эͬ�칫���ĵ���Ҫ���� ����ͨ��ԭԪ�ص����Դ���
	myElement.readOnly = "true"; //ֻ��
	myElement.className = style; //Ӧ����ʽ
	element.style.display = "none"; //����ԭԪ��
	element.parentNode.insertBefore(myElement,element); //����̬�������������뵽ԭԪ��֮ǰ
}

ParameterContant = function(){
	this.GENERALSEARCHLIST = 'generalsearchlist';
	this.GENERALSEARCHANNUALLIST = 'generalsearchannuallist';
	this.ENTERPRISEANNUALLISTV2 = 'enterpriseAnnualListV2';
	this.ENTERPRISEANNUALLIST = 'enterpriseAnnualList';
	this.PRODUCTREGISTRLIST = "productregistrlist";
	this.PRODUCTSCONTINUEDLIST = "productscontinuedlist";
	this.PRODUCTCHANGELIST = "productchangelist";
	this.ENTERPRISEIDENTIFYLIST = "enterpriseIdentifyList";
	this.ENTERPRISEIDENTIFYLISTV2 = "enterpriseIdentifyListV2";
	this.ENTERPRISEAPPLICATIONLIST = "enterpriseApplicationList";
	this.PRODUCTREPLACELIST = "productreplacelist";
	this.ENTERANNUALAPPROVEWORKLIST = "enterAnnualApproveWorkList";
	this.ENTERANUALDONEWORKLIST = "enterAnnualDoneWorkList";
	this.SPPRODUCTREGISTRAPPROVEWORKLIST = "spproductRegistrApproveWorkList";
	this.SPPRODUCTREGISTRDONEWORKLIST = "spproductRegistrDoneWorkList";
	this.SPPRODUCTCONTINUEAPPROVEWORKLIST = "spproductContinueApproveWorkList";
	this.SPPRODUCTCONTINUEDONEWORKLIST = "spproductContinueDoneWorkList";
	   
	   /**
	    *��������-��Ʒ���-����  
	    */
	   this.SPPRODUCTCHCHANGEWORKLIST = "spproductchchangeworklist";
	   
	   /**
	    *��������-��Ʒ���-�Ѱ�  
	    */
	   this.SPPRODUCTCHCHANGEWORKYIBAN = "spproductchchangeworkyiban";
	   
	   /**
	    *��������-��ҵ�϶�-����  
	    */
	   this.SPENTERVERIFYAPPROVEWORKLIST = "spenterVerifyApproveWorkList";
	   
	   /**
	    *��������-��ҵ�϶�-�Ѱ�  
	    */
	   this.SPENTERVERIFYDONEWORKLIST = "spenterVerifyDoneWorkList";
	   /**
	    *��������-��ҵ���-����  
	    */
	   this.SPENTERCHANGEWORKLIST = "spenterChangeWorkList";
	   /**
	    *��������-��ҵ���-�Ѱ�  
	    */
	   this.SPENTERCHANGEAFTERWORKLIST = "spenterChangeAfterWorkList";
	   /**
	    *Ԥ����-��Ʒ�Ǽ�-����  
	    */
	   this.PREPRODUCTREGISTRAPPROVEWORKLIST = "preproductRegistrApproveWorkList";
	   /**
	    *Ԥ����-��Ʒ�Ǽ�-�Ѱ�  
	    */
	   this.PREPRODUCTREGISTRDONEWORKLIST = "preproductRegistrDoneWorkList";
	   
	   /**
	    *Ԥ����-��Ʒ����-����  
	    */
	   this.PREPRODUCTCONTINUEAPPROVEWORKLIST = "preproductContinueApproveWorkList";
	   
	   /**
	    *Ԥ����-��Ʒ����-�Ѱ�  
	    */
	   this.PREPRODUCTCONTINUEDONEWORKLIST = "preproductContinueDoneWorkList";
	   
	   /**
	    *Ԥ����-��Ʒ���-����  
	    */
	   this.PREPRODUCTCHCHANGEWORKLIST = "preproductchchangeworklist";
	   
	   /**
	    *Ԥ����-��Ʒ���-�Ѱ�  
	    */
	   this.PREPRODUCTCHCHANGEWORKYIBAN = "preproductchchangeworkyiban";
	   
	   /**
	    *Ԥ����-��ҵ�϶�-����  
	    */
	   this.PREENTERVERIFYAPPROVEWORKLIST = "preenterVerifyApproveWorkList";
	   
	   /**
	    *Ԥ����-��ҵ�϶�-�Ѱ�  
	    */
	   this.PREENTERVERIFYDONEWORKLIST = "preenterVerifyDoneWorkList";
	   
	   /**
	    *Ԥ����-��ҵ���-����  
	    */
	   this.PREENTERCHANGEWORKLIST = "preenterChangeWorkList";
	   
	   /**
	    *Ԥ����-��ҵ���-�Ѱ�  
	    */
	   this.PREENTERCHANGEAFTERWORKLIST = "preenterChangeAfterWorkList";
	   
	   /**
	    *������-��Ʒ�Ǽ�-����  
	    */
	   this.ACCEPTPRODUCTREGISTRAPPROVEWORKLIST = "acceptproductRegistrApproveWorkList";
	   /**
	    *������-��Ʒ�Ǽ�-�Ѱ�  
	    */
	   this.ACCEPTPRODUCTREGISTRDONEWORKLIST = "acceptproductRegistrDoneWorkList";
	   /**
	    *������-��Ʒ����-����  
	    */
	   this.ACCEPTPRODUCTCONTINUEAPPROVEWORKLIST = "acceptproductContinueApproveWorkList";
	   /**
	    *������-��Ʒ����-�Ѱ�  
	    */
	   this.ACCEPTPRODUCTCONTINUEDONEWORKLIST = "acceptproductContinueDoneWorkList";
	   /**
	    *������-��Ʒ���-����  
	    */
	   this.ACCEPTPRODUCTCHCHANGEWORKLIST = "acceptproductchchangeworklist";
	   /**
	    *������-��Ʒ���-�Ѱ�  
	    */
	   this.ACCEPTPRODUCTCHCHANGEWORKYIBAN = "acceptproductchchangeworkyiban";
	   /**
	    *������-��ҵ�϶�-����  
	    */
	   this.ACCEPTENTERVERIFYAPPROVEWORKLIST = "acceptenterVerifyApproveWorkList";
	   /**
	    *������-��ҵ�϶�-�Ѱ�  
	    */
	   this.ACCEPTENTERVERIFYDONEWORKLIST = "acceptenterVerifyDoneWorkList";
	   /**
	    *������-��ҵ���-����  
	    */
	   this.ACCEPTENTERCHANGEWORKLIST = "acceptenterChangeWorkList";
	   /**
	    *������-��ҵ���-�Ѱ�  
	    */
	   this.ACCEPTENTERCHANGEAFTERWORKLIST = "acceptenterChangeAfterWorkList";
	   /**
	    *��ᴦ��-��Ʒ�Ǽ�-����  
	    */
	   this.FINISHPRODUCTREGISTRAPROVEWORKLIST = "finishproductRegistrApproveWorkList";
	   /**
	    *��ᴦ��-��Ʒ�Ǽ�-�Ѱ�  
	    */
	   this.FINISHPRODUCTREGISTRDONEWORKLIST = "finishproductRegistrDoneWorkList";
	   /**
	    *��ᴦ��-��Ʒ����-����  
	    */
	   this.FINISHPRODUCTCONTINUEAPPROVEWORKLIST = "finishproductContinueApproveWorkList";
	   /**
	    *��ᴦ��-��Ʒ����-�Ѱ�  
	    */
	   this.FINISHPRODUCTCONTINUEDONEWORKLIST = "finishproductContinueDoneWorkList";
	   /**
	    *��ᴦ��-��Ʒ���-����  
	    */
	   this.FINISHPRODUCTCHCHANGEWORKLIST = "finishproductchchangeworklist";
	   /**
	    *��ᴦ��-��Ʒ���-�Ѱ�  
	    */
	   this.FINISHPRODUCTCHCHANGEWORKYIBAN = "finishproductchchangeworkyiban";
	   /**
	    *��ᴦ��-��ҵ�϶�-����  
	    */
	   this.FINISHENTERVERIFYAPPROVEWORKLIST = "finishenterVerifyApproveWorkList";
	   /**
	    *��ᴦ��-��ҵ�϶�-�Ѱ�  
	    */
	   this.FINISHENTERVERIFYDONEWORKLIST = "finishenterVerifyDoneWorkList";
	   /**
	    *��ᴦ��-��ҵ���-����  
	    */
	   this.FINISHENTERCHANGEWORKLIST = "finishenterChangeWorkList";
	   /**
	    *��ᴦ��-��ҵ���-�Ѱ�  
	    */
	   this.FINISHENTERCHANGEAFTERWORKLIST = "finishenterChangeAfterWorkList";
	   /**
	    *��ᴦ��-��֤����-����  
	    */
	   this.FINISHPRODUCTCHREPLACEWORKLIST = "finishproductchreplaceworklist";
	   /**
	    *��ᴦ��-������-�Ѱ�  
	    */
	   this.FINISHPRODUCTCHREPLACEWORKYIBAN = "finishproductchreplaceworkyiban";
	   /**
	    *��ҵ�϶��ʸ����  
	    */
	   this.ENTERPRISEEXAMINELIST = "enterpriseExamineList";
	   /**
	    *�ۺϲ鿴
	    */
	   this.GENERALSEARCHLISTSHOW = "generalsearchlistshow";
	   /**
	    * �ۺϲ鿴������
	    */
	   this.GENERALSEARCHANNUALLISTSHOW = "generalsearchannuallistshow";
	
	
	
	
}
var ParameterContants = new ParameterContant();
//org�ݹ�
//��ϵͳ�������������� type:'org','user','role','group','job'
//id:����id��text�ؼ���id
//name:����name��text�ؼ���id
//isRadio:true or false true ��ʾǰ���ǵ�ѡ��false��ʾ�Ǹ�ѡ�� �������ò�����Ĭ���Ǹ�ѡ��
//showmode:static-dynamic,static
//rootId-���ڵ�id, rootName-���ڵ�����, expandLevel-չ������
var openTree_orgids = "";
var openTree_userids = "";
function openTree(type, id, name, isRadio, treeshowmode, rootId, rootName, expandLevel) {
openTree_userids = id.value;
openTree_names = name.value;
var orgids = "";
try {
	eval("orgids=creator_tree_" + id.id);
} catch (e) {
};

var rootId = rootId || "0";
var rootName = rootName || "��֯������";
var expandLevel = expandLevel || "1";

var url = new StringBuffer().append(location.protocol).append("//")
		.append(location.host).append(getContextPath())
		.append("/eformsys/systemManager/");
if (!IsSpace(type)) {
	if (type == "org") { // ������ ��ѡ���ѡ
		if (!IsSpace(isRadio) && isRadio) {
			url.append("org_tree_radio.jsp?rootId="+rootId + "&rootName="+rootName + "&expandLevel="+expandLevel);
		} else {
			url.append("org_tree.jsp?rootId="+rootId + "&rootName="+rootName + "&expandLevel="+expandLevel);
		}
	} else if (type == "user") { // �����û��� ��ѡ���ѡ
		if (!IsSpace(isRadio) && isRadio) {
			url.append("orgUserTreeRadio.jsp?userid=").append(id.value)
					.append("&userName=").append(name.value+"&rootId="+rootId + "&rootName="+rootName + "&expandLevel="+expandLevel);
		} else {
			url.append("orgUserTree.jsp?orgid=").append(orgids+"&rootId="+rootId + "&rootName="+rootName + "&expandLevel="+expandLevel);
		}

	} else if (type == "role") { // ��ɫ�� ��ѡ���ѡ
		if (!IsSpace(isRadio) && isRadio) {
			url.append("role_tree_radio.jsp");
		} else {
			url.append("role_tree.jsp");
		}

	} else if (type == "group") { // �� ��ѡ���ѡ
		if (!IsSpace(isRadio) && isRadio) {
			url.append("groupTreeRadio.jsp");
		} else {
			url.append("groupTree.jsp");
		}

	} else if (type == "job") { // ������λ�� ��ѡ���ѡ
		if (!IsSpace(isRadio) && isRadio) {
			url.append("jobTreeRadio.jsp");
		} else {
			url.append("jobTree.jsp");
		}

	} else if (type == "business") { // ҵ������� ֻ�е�ѡ
		url.append("business_type_tree.jsp?busids=").append(id.value);

	} else if (type == "report") { // ������ ֻ�е�ѡ
		url.append("reportmain.jsp");
	} else {
		url.append("org_tree_radio.jsp");
	}

}

var myReturn = "";
if (type == "report") {
	myReturn = window.showModalDialog(url.toString(), window,
			"dialogWidth:600px");
} else {
	myReturn = window.showModalDialog(url.toString(), window);
}
if (myReturn != "undefined" && myReturn != null) {
	var arr = myReturn.split(";");
	id.value = arr[0];
	name.value = arr[1];
	if (arr.length > 2) {
		eval("creator_tree_" + id.id + "=arr[2]");
	}

}

}

//�Զ�����iframe�ߴ����tab�ߴ�
function autoSetTabSize(isSet,framename,scrollHeight){
if(isSet){
	if(scrollHeight !=null && scrollHeight != "" && scrollHeight > 0){
		document.getElementById(framename).style.height = scrollHeight + 10;
	}else{
		document.getElementById(framename).style.height = document.getElementById(framename).document.body.scrollHeight-10;
	}
}

}

//�Զ�����iframe�ߴ����tab�ߴ�
function parentAutoSetTabSize(isSet,framename){
if(isSet){
	parent.document.getElementById(framename).style.height = parent.document.getElementById(framename).document.body.scrollHeight-10;
}

}
//����isHidden�ж��Ƿ�����tab
function setTabHidden(isHidden,Tabname){
if(isHidden){
	document.getElementById(Tabname).style.display="none";	
}
}

//�ж��ַ����Ƿ�Ϊ��
function IsSpace(strMain) {
var strComp = strMain;
try {
	if (strComp == "  " || strComp == "" || strComp == " "
			|| strComp == null || strComp == "null" || strComp.length == 0
			|| typeof strMain == "undefined" || strMain == "undefined") {
		return true;
	} else {
		return false;
	}
} catch (e) {
	return false;
}
}

function isSpace(strMain){
return IsSpace(strMain);
}

function isOutStrLenth(str,lenth){
var length = str.replace(/[^\x00-\xff]/g,"**").length;
if(length>lenth){
	return true;
}
return false;
}

function clearhuanghangchar(value){

    var newValue = value.replaceAll("\r\n", "\\r\\n");  
    return newValue;   
}  


//����ַ������ߵĿհ�
function trim(strMain) {
if (strMain == null) {
	return "";
}
strMain = strMain + "";
return strMain.replace(/(^\s*)|(\s*$)/g, "");
}

//���combox
function clearCombo(object){
var optionIndex = object.options.length;
for(;optionIndex>=1;optionIndex--){
    object.options.remove(optionIndex);
}
}

/**
* ��ȡ����ΪobjName��checkbox�б�ѡ�е�ֵ,�á�,"��ƴ��
* @param objName
* @return
*/
function getCheckBoxValue(objName){
var checkboxarry = document.getElementsByName(objName);
var checkboxvalue = "";
for(var i=0;i<checkboxarry.length;i++){
	if(checkboxarry[i].checked){
		checkboxvalue = checkboxvalue + checkboxarry[i].value+",";
	}
}
if(!IsSpace(checkboxvalue)){
	checkboxvalue = checkboxvalue.substr(0,checkboxvalue.length-1);
}
return checkboxvalue;
}

/**
* ��������ΪobjName��checkbox
* @param objName
* @param checkboxvalue �ö���ƴ�ӵ�checkboxֵ���ַ���
* @return
*/
function setCheckBoxValue(objName,checkboxvalue){

var checkboxarry = document.getElementsByName(objName);
if(!IsSpace(checkboxvalue)){
	var checkboxvaluearry = checkboxvalue.split(",");
	for(var i=0;i<checkboxvaluearry.length;i++){
		alert(checkboxvaluearry[i]-1);
		checkboxarry[checkboxvaluearry[i]-1].checked = true;
	}
}

}

/**
* ��ȡ����ΪobjName��radio�б�ѡ�е���һ���ֵ
* @param objName
* @return
*/
function getRadioGroupValue(objName){
var radioGroupValue = "";
var ridList = document.getElementsByName(objName);
for(var i=0; i<ridList.length; i++){
    if(ridList[i].checked){
        radioGroupValue = ridList[i].value;
        break;
    }
}
return radioGroupValue; 
}

/**
* ���õ�ѡ��ť���ʵ��ֵ
* @param objName
* @return
*/
function setRadioGroupValue(objName, objVal){
var ridList = document.getElementsByName(objName);
for(var i=0; i<ridList.length; i++){
  if(ridList[i].value == objVal){
      ridList[i].checked = true;
      break;
  }
}
}
/**
* ���ַ����е�ĳЩ�ض��ַ�����ת��
* @param str
* @return
*/
function descape(str){
  if(/{\'/.test(str)){
	  str = str.replace("{'","chr(41)");
  }
  if(/\'}/.test(str)){
	  str = str.substring(0, str.length - 2) + str.substring(str.length -2, str.length).replace("'}","chr(42)");
  }
  if(/\}/.test(str)){
	  str = str.replaceAll("}","chr(46)");
  }
  if(/\{/.test(str)){
	  str = str.replaceAll("{","chr(47)");
  }
  if(/\'\,\'/.test(str)){
	  str = str.replaceAll("','","chr(43)");
  }
  if(/\':\'/.test(str)){
	  str = str.replaceAll("':'","chr(44)");
  }
  if(/\#/.test(str)){
	 str = str.replaceAll("#","chr(50)");
  }
  if(/\'/.test(str)){
	str = str.replaceAll("'","chr(39)")
  }
  if(/\"/.test(str)){
		str = str.replaceAll('"',"chr(45)")
	  }
  if(/\&/.test(str)){
	str = str.replaceAll("&","chr(38)")
  }
  if(/\</.test(str)){
	str = str.replaceAll("<","chr(60)")
  }
  if(/\>/.test(str)){
	str = str.replaceAll(">","chr(62)")
  }
  if(/\%/.test(str)){
	 str = str.replaceAll("%","chr(37)")
  }

  if(/\r\n/.test(str)){
	  str = str.replaceAll("\r\n","chr(64)")
  } 
  while(/\\/.test(str)){
	  str = str.replace("\\","chr(40)");
  }
  return str;
}

String.prototype.replaceAll = function (AFindText,ARepText){
raRegExp = new RegExp(AFindText,"g");
return this.replace(raRegExp,ARepText);
}

/**
* ��ȡ��ǰӦ��������·��
* @param str
* @return
*/
function getContextPath() { 
var contextPath = document.location.pathname; 
if(contextPath.substr(0,1) != "/"){
	contextPath="/"+contextPath;
}
var index =contextPath.substr(1).indexOf("/"); contextPath = contextPath.substr(0,index+1); 
delete index;
return contextPath; 
} 


/*չʾ�����ʷ�Ĺ�������*/

showFlowHistoryByInsId = function(item_insid){
var url = getContextPath()+"/kcapp/xzsp/jsp/instance/showflowhistorytiaozhuan.jsp?iteminsid="+item_insid;
var winwidth=(screen.width - 920)/2;
var winheight=(screen.height - 700)/2-30;
var freatrues = "height=700,width=920,top="+winheight+"px,left="+winwidth+"px,status=yes,toolbar=no,menubar=no,location=no";

window.open(url,"",freatrues ); 
}



/* ����TABҳ����ʾ�����أ���TABҳidΪ��׼ */
function hideOrShowTabById(id, type) {
  var realStyle = "";
  if(IsSpace(type) || (type.toUpperCase()=="hide".toUpperCase())) {
	    realStyle = "none";
  }
  var objs = document.getElementsByTagName("div");
  for(var i=0; i<objs.length; i++) {
	    if(trim(objs[i].id) == trim(id+"-tab")) {
		      objs[i].style.display = realStyle;
	    }
  }
}

//���url�����ĳ��������ֵ��
function creator_getQueryString(item) {
var svalue = location.search.match(new RegExp(new StringBuffer()
		.append("[\?\&]").append(item).append("=([^\&]*)(\&?)").toString(),
		"i"));
return svalue ? svalue[1] : svalue;
}
/**
* ҳ���ϵ��ֶ���װ��VO����
* @param vo����
* @return
*/

function getVoFromPage(vo){ 

for(var name in vo ){
  var obj = document.getElementById(name.toUpperCase());
  if(obj){
	  var vo_node= eval("vo."+name);
	  var vo_value = eval(vo.name);
	  if(vo_node!=null ){
    	 eval("vo."+name+".value = trim(obj.value)") ;
	  }else{
		   eval("vo."+name+" = trim(obj.value)") ; 
	  }
  }else{
	   var vo_node= eval("vo."+name);
	   if(vo_node!=null){
	       if(eval("vo."+name+".value")){
		   }else{
			   //eval("vo."+name+" = undefined") ;//modifyby yi.yang vo��Ĭ��ֵ���
		   }
	  }else{
		   eval("vo."+name+" = undefined");
	  }
   }
}
return vo;
}


//=====20100223���,js�ַ��������Ż���װ��
function StringBuffer() {
this._strs = new Array;
}
StringBuffer.prototype.append = function(str) {
this._strs.push(str);
return this;
}
StringBuffer.prototype.toString = function() {
return this._strs.join("");
}


//===============================20080424��ӣ�ͨ��djid��ȡjsp�ļ���·��
function getFormUrlByDjid(myDjid) {
var formUrl = new StringBuffer().append(location.protocol).append("//")
		.append(location.host).append(getContextPath()).append("/eformsys").append("/")
		.append("jxc/dj/").append(myDjid).append(".")
		.append("jsp");
return formUrl;
}


//20080612���������ݱ�id��ȡ�򿪱���·����
function getOpenUrlByDjid(myDjid) {
var openDjUrl = new StringBuffer();
openDjUrl.append(location.protocol).append("//")
		.append(location.host).append(getContextPath()).append("/eformsys")
		.append("/fceform/common/djframe.htm?djtype=").append("ZW")
		.append("&djsn=");
var formUrl = openDjUrl + myDjid;
return formUrl;
}


//������ʾ����������
function showApplyCtrl(applyType){
if(applyType == "1"){
    $('applyCorpArea').style.display = "";
    $('applicantArea').style.display = "none";   
}else{
	  $('applyCorpArea').style.display = "none";
      $('applicantArea').style.display = "";        
}
}

/* ����Ŀ������Ƿ���ʾ���������ʾ�������أ�������ʾ20095-5huajun.zhang */
function showAreaCtrl(destObjId){
var destObj = document.getElementById(destObjId);
var srcImg = event.srcElement;
if(destObj){
    var style = destObj.style;
    if(destObj.style.display == "none"){
        destObj.style.display = "";
        srcImg.src = "../../resources/images/menu_off.gif"
    }else{
        destObj.style.display = "none";
        srcImg.src = "../../resources/images/menu_off.gif"
    }
}
}
//��̬����INPUTԪ�أ�����IE��FF�����
function createInputElement(type, name){
  var oInput = null;
if(document.all){ //IE
	  var oStr = "<input type='"+type+"' name='"+name+"'>";
	  oInput = document.createElement(oStr);
	  
}else{
	  oInput = document.createElement("input");
	  oInput.type = type;
	  oInput.name = name;
}
return oInput;
}

/* ������תΪ������ */
function getLongFormat(datStr){
 if(IsSpace(datStr)){
 	   return null;
 }
 datStr = datStr.replace("-","/");
datStr = datStr.replace("-","/");
return Date.parse(datStr);
}
/* ȡָ�����ȵ��ַ��� */
function getContentByLimit(str, iLimit){
 if(IsSpace(str)){
 	   return null;
 }    
 str = trim(str);
 if(str.length <= iLimit){
 	  return str;
 }else{
 	  return str.substr(0,iLimit);
 }
}
/* �����ڵĺ�ܸ�Ϊ������ */
function getChnFormat(datStr){
 if(IsSpace(datStr)){
 	   return null;
 }
 return datStr.substr(0,4) + "��" + datStr.substr(5,2) + "��" + datStr.substr(8,2) + "��";
}

//ȫѡ��ȡ������ע��÷���֮��������ΪcheckAll����Ϊpg��ǩ������һ��checkAll����
function checkAllItem(checkName,thisobj){
 var checkObj = document.getElementsByName(checkName);
 if( checkObj.length > 0){
 	if(thisobj.innerText.trim() == "ȫѡ"){//��ʾȫѡ
 		for( var i = 0 ; i < checkObj.length ; i++){
 			checkObj[i].checked = true;
 		}
 		thisobj.innerText = "ȡ��";
 	}else{//��ʾ��ѡ
 		for( var i = 0 ; i < checkObj.length ; i++){
 			checkObj[i].checked = false;
 		}
 		thisobj.innerText = "ȫѡ";
 	}
 }
}


//��֤���֤�Ƿ�Ϸ�
function checkIdcard(idcard){ 
var Errors=new Array( 
"��֤ͨ��!", 
"�����֤����λ������!", 
"�����֤����������ڳ�����Χ���зǷ��ַ�!", 
"�����֤����У�����!", 
"�����֤�����Ƿ�!" 
); 
var area={11:"����",12:"���",13:"�ӱ�",14:"ɽ��",15:"���ɹ�",21:"����",22:"����",23:"������",31:"�Ϻ�",32:"����",33:"�㽭",34:"����",35:"����",36:"����",37:"ɽ��",41:"����",42:"����",43:"����",44:"�㶫",45:"����",46:"����",50:"����",51:"�Ĵ�",52:"����",53:"����",54:"����",61:"����",62:"����",63:"�ຣ",64:"����",65:"�½�",71:"̨��",81:"���",82:"����",91:"����"} 
var idcard,Y,JYM; 
var S,M; 
var idcard_array = new Array(); 
idcard_array = idcard.split(""); 
if(area[parseInt(idcard.substr(0,2))]==null) return Errors[4]; 
switch(idcard.length){ 
case 15: 
if ( (parseInt(idcard.substr(6,2))+1900) % 4 == 0 || ((parseInt(idcard.substr(6,2))+1900) % 100 == 0 && (parseInt(idcard.substr(6,2))+1900) % 4 == 0 )){ 
ereg=/^[1-9][0-9]{5}[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|[1-2][0-9]))[0-9]{3}$/; 
} else { 
ereg=/^[1-9][0-9]{5}[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|1[0-9]|2[0-8]))[0-9]{3}$/; 
} 
if(ereg.test(idcard)) return Errors[0]; 
else return Errors[2]; 
break; 
case 18: 
if ( parseInt(idcard.substr(6,4)) % 4 == 0 || (parseInt(idcard.substr(6,4)) % 100 == 0 && parseInt(idcard.substr(6,4))%4 == 0 )){ 
ereg=/^[1-9][0-9]{5}19[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|[1-2][0-9]))[0-9]{3}[0-9Xx]$/;//����������ڵĺϷ���������ʽ 
} else { 
ereg=/^[1-9][0-9]{5}19[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|1[0-9]|2[0-8]))[0-9]{3}[0-9Xx]$/;//ƽ��������ڵĺϷ���������ʽ 
} 
if(ereg.test(idcard)){ 
S = (parseInt(idcard_array[0]) + parseInt(idcard_array[10])) * 7 
+ (parseInt(idcard_array[1]) + parseInt(idcard_array[11])) * 9 
+ (parseInt(idcard_array[2]) + parseInt(idcard_array[12])) * 10 
+ (parseInt(idcard_array[3]) + parseInt(idcard_array[13])) * 5 
+ (parseInt(idcard_array[4]) + parseInt(idcard_array[14])) * 8 
+ (parseInt(idcard_array[5]) + parseInt(idcard_array[15])) * 4 
+ (parseInt(idcard_array[6]) + parseInt(idcard_array[16])) * 2 
+ parseInt(idcard_array[7]) * 1 
+ parseInt(idcard_array[8]) * 6 
+ parseInt(idcard_array[9]) * 3 ; 
Y = S % 11; 
M = "F"; 
JYM = "10X98765432"; 
M = JYM.substr(Y,1); 
if(M == idcard_array[17]) return Errors[0]; 
else return Errors[3]; 
} 
else return Errors[2]; 
break; 
default: 
return Errors[1]; 
break; 
} 
} 
//url�����ַ�
function encodeURLKcapp(str){
if(!IsSpace(str)){
	return encodeURIComponent(encodeURIComponent(str));
}else{
	return str;
}
}
//����
function decodeURLKcapp(str){
if(!IsSpace(str)){
	return decodeURIComponent(decodeURIComponent(str));
}else{
	return str;
}
}

//�����ַ�ת�루����չ��
function Escape2Sql(str){
str = str.replaceAll("'","'||chr(39)||'")
str = str.replaceAll("&","'||chr(38)||'")
str = str.replaceAll("<","'||chr(60)||'")
str = str.replaceAll(">","'||chr(62)||'")
str = str.replaceAll("%","'||chr(37)||'")
//str = str.replaceAll("%","\\%")
return str;
}

function iframeFitHeight(oIframe)
{//Iframe��������Ӧ�߶� ����IE6.0,google, FF2.0����
try
{
var oWin = oIframe.name ? window.frames[oIframe.name] : oIframe.contentWindow; 
oIframe.style.height = oWin.document.body.scrollHeight + "px";
}
catch(e){}
}

/*
* ���ݲ�������ҳ��������
* */
function createHiddenHtml(reportParam){
var str = reportParam.split("&");
for(var i = 0 ; i < str.length ; i++){	
	var name = str[i].split("=")[0];
	//var value = decodeURLKcapp(str[i].split("=")[1]);
	var value = str[i].split("=")[1];
	if(!IsSpace(name)){
		value = value.replaceAll("%25","%");
		if (IsSpace(document.getElementsByName(name))) {
			value = value.replaceAll("'","&#x27;");
			var hiddenObj = document.createElement("<input type = 'hidden' name = '"+name+"' value = '" + value + "'>");
			var tdObj = document.getElementById("hiddenTd1");
			tdObj.appendChild(hiddenObj);
		} else {
			document.getElementsByName(name)[0].value = value;				
		}
	}
}
}

//��ʼ�������е���js���Ѷ��巽��
function getInvoked(func) {   
return new Function(func);   
}

/**
* 
* @param from_page
* @param iscache  �Ƿ���ػ���
*/
function returnFun(from_page,iscache){
var cachestr = "?searchCache=true";
 if(iscache == "false"){
	 cachestr = "";
 }
 var url = "";
 if(from_page == ParameterContants.GENERALSEARCHLIST){
	 url = getContextPath()+"/srrz/jsp/generalsearch/generalsearchlist.jsp"+cachestr;
 }else if(from_page == ParameterContants.GENERALSEARCHANNUALLIST){
	 url = getContextPath()+"/srrz/jsp/generalsearch/generalsearchannuallist.jsp"+cachestr;
 }else if(from_page == ParameterContants.ENTERPRISEANNUALLISTV2){
	 url = getContextPath()+"/srrz/jsp/annualV2/enterpriseAnnualList.jsp"+cachestr;
 }else if(from_page == ParameterContants.ENTERPRISEANNUALLIST){
	 url = getContextPath()+"/srrz/jsp/annual/enterpriseAnnualList.jsp"+cachestr;
 }else if(from_page == ParameterContants.PRODUCTREGISTRLIST){
	 url = getContextPath()+"/srrz/jsp/productregistr/productregistrlist.jsp"+cachestr;
 }else if(from_page == ParameterContants.PRODUCTSCONTINUEDLIST){
	 url = getContextPath()+"/srrz/jsp/productscontinued/productscontinuedlist.jsp"+cachestr;
 }else if(from_page == ParameterContants.PRODUCTCHANGELIST){
	 url = getContextPath()+"/srrz/jsp/productchange/productchangelist.jsp"+cachestr;
 }else if(from_page == ParameterContants.ENTERPRISEIDENTIFYLIST){
	 url = getContextPath()+"/srrz/jsp/enterprise/enterpriseIdentifyList.jsp"+cachestr;
 }else if(from_page == ParameterContants.ENTERPRISEIDENTIFYLISTV2){
	 url = getContextPath()+"/srrz/jsp/enterpriseV2/enterpriseIdentifyList.jsp"+cachestr;
 }else if(from_page == ParameterContants.ENTERPRISEAPPLICATIONLIST){
	 url = getContextPath()+"/srrz/jsp/softenterprise/enterprisechangeeapplication/enterpriseApplicationList.jsp"+cachestr;
 }else if(from_page == ParameterContants.PRODUCTREPLACELIST){
	 url = getContextPath()+"/srrz/jsp/productchange/productreplacelist.jsp"+cachestr;
 }else if(from_page == ParameterContants.ENTERANNUALAPPROVEWORKLIST){
	 if(iscache != "false" ){
		 cachestr = "&iscache=true";
	 }
	 url = getContextPath()+"/srrz/jsp/softenterprise/enterAnnualAuditTab.jsp?selectedTabPaneId=worklist"+cachestr;
 }else if(from_page == ParameterContants.ENTERANUALDONEWORKLIST){
	 if(iscache != "false" ){
		 cachestr = "&iscache=true";
	 }
	 url = getContextPath()+"/srrz/jsp/softenterprise/enterAnnualAuditTab.jsp?selectedTabPaneId=done_work"+cachestr;
 }else if(from_page == ParameterContants.SPPRODUCTREGISTRAPPROVEWORKLIST){
	 if(iscache != "false" ){
		 cachestr = "&iscache=true";
	 }
	 url = getContextPath()+"/srrz/jsp/productregistr/productRegistrAuditTab.jsp?currentstatus=S5&selectedTabPaneId=worklist"+cachestr;
 }else if(from_page == ParameterContants.PREPRODUCTREGISTRAPPROVEWORKLIST){
	 if(iscache != "false" ){
		 cachestr = "&iscache=true";
	 }
	 url = getContextPath()+"/srrz/jsp/productregistr/productRegistrAuditTab.jsp?currentstatus=S1&selectedTabPaneId=worklist"+cachestr;
 }else if(from_page == ParameterContants.ACCEPTPRODUCTREGISTRAPPROVEWORKLIST){
	 if(iscache != "false" ){
		 cachestr = "&iscache=true";
	 }
	 url = getContextPath()+"/srrz/jsp/productregistr/productRegistrAuditTab.jsp?currentstatus=S6&selectedTabPaneId=worklist"+cachestr;
 }else if(from_page == ParameterContants.FINISHPRODUCTREGISTRAPROVEWORKLIST){
	 if(iscache != "false" ){
		 cachestr = "&iscache=true";
	 }
	 url = getContextPath()+"/srrz/jsp/productregistr/productRegistrAuditTab.jsp?currentstatus=S8&selectedTabPaneId=worklist"+cachestr;
 }else if(from_page == ParameterContants.SPPRODUCTREGISTRDONEWORKLIST){
	 if(iscache != "false" ){
		 cachestr = "&iscache=true";
	 }
	 url = getContextPath()+"/srrz/jsp/productregistr/productRegistrAuditTab.jsp?currentstatus=S5&selectedTabPaneId=done_work"+cachestr;
 }else if(from_page == ParameterContants.PREPRODUCTREGISTRDONEWORKLIST){
	 if(iscache != "false" ){
		 cachestr = "&iscache=true";
	 }
	 url = getContextPath()+"/srrz/jsp/productregistr/productRegistrAuditTab.jsp?currentstatus=S1&selectedTabPaneId=done_work"+cachestr;
 }else if(from_page == ParameterContants.ACCEPTPRODUCTREGISTRDONEWORKLIST){
	 if(iscache != "false" ){
		 cachestr = "&iscache=true";
	 }
	 url = getContextPath()+"/srrz/jsp/productregistr/productRegistrAuditTab.jsp?currentstatus=S6&selectedTabPaneId=done_work"+cachestr;
 }else if(from_page == ParameterContants.FINISHPRODUCTREGISTRDONEWORKLIST){
	 if(iscache != "false" ){
		 cachestr = "&iscache=true";
	 }
	 url = getContextPath()+"/srrz/jsp/productregistr/productRegistrAuditTab.jsp?currentstatus=S8&selectedTabPaneId=done_work"+cachestr;
 }else if(from_page == ParameterContants.SPPRODUCTCONTINUEAPPROVEWORKLIST){
	 if(iscache != "false" ){
		 cachestr = "&iscache=true";
	 }
	 url = getContextPath()+"/srrz/jsp/softenterprise/productContinueAuditTab.jsp?currentstatus=S5&selectedTabPaneId=worklist"+cachestr;
 }else if(from_page == ParameterContants.PREPRODUCTCONTINUEAPPROVEWORKLIST){
	 if(iscache != "false" ){
		 cachestr = "&iscache=true";
	 }
	 url = getContextPath()+"/srrz/jsp/softenterprise/productContinueAuditTab.jsp?currentstatus=S1&selectedTabPaneId=worklist"+cachestr;
 }else if(from_page == ParameterContants.ACCEPTPRODUCTCONTINUEAPPROVEWORKLIST){
	 if(iscache != "false" ){
		 cachestr = "&iscache=true";
	 }
	 url = getContextPath()+"/srrz/jsp/softenterprise/productContinueAuditTab.jsp?currentstatus=S6&selectedTabPaneId=worklist"+cachestr;
 }else if(from_page == ParameterContants.FINISHPRODUCTCONTINUEAPPROVEWORKLIST){
	 if(iscache != "false" ){
		 cachestr = "&iscache=true";
	 }
	 url = getContextPath()+"/srrz/jsp/softenterprise/productContinueAuditTab.jsp?currentstatus=S8&selectedTabPaneId=worklist"+cachestr;
 }else if(from_page == ParameterContants.SPPRODUCTCONTINUEDONEWORKLIST){
	 if(iscache != "false" ){
		 cachestr = "&iscache=true";
	 }
	 url = getContextPath()+"/srrz/jsp/softenterprise/productContinueAuditTab.jsp?currentstatus=S5&selectedTabPaneId=done_work"+cachestr;
 }else if(from_page == ParameterContants.PREPRODUCTCONTINUEDONEWORKLIST){
	 if(iscache != "false" ){
		 cachestr = "&iscache=true";
	 }
	 url = getContextPath()+"/srrz/jsp/softenterprise/productContinueAuditTab.jsp?currentstatus=S1&selectedTabPaneId=done_work"+cachestr;
 }else if(from_page == ParameterContants.ACCEPTPRODUCTCONTINUEDONEWORKLIST){
	 if(iscache != "false" ){
		 cachestr = "&iscache=true";
	 }
	 url = getContextPath()+"/srrz/jsp/softenterprise/productContinueAuditTab.jsp?currentstatus=S6&selectedTabPaneId=done_work"+cachestr;
 }else if(from_page == ParameterContants.FINISHPRODUCTCONTINUEDONEWORKLIST){
	 if(iscache != "false" ){
		 cachestr = "&iscache=true";
	 }
	 url = getContextPath()+"/srrz/jsp/softenterprise/productContinueAuditTab.jsp?currentstatus=S8&selectedTabPaneId=done_work"+cachestr;
 }else if(from_page == ParameterContants.SPPRODUCTCHCHANGEWORKLIST){
	 if(iscache != "false" ){
		 cachestr = "&iscache=true";
	 }
	 url = getContextPath()+"/srrz/jsp/productchange/productchangework/productchchangework.jsp?currentstatus=S5&selectedTabPaneId=worklist"+cachestr;
 }else if(from_page == ParameterContants.PREPRODUCTCHCHANGEWORKLIST){
	 if(iscache != "false" ){
		 cachestr = "&iscache=true";
	 }
	 url = getContextPath()+"/srrz/jsp/productchange/productchangework/productchchangework.jsp?currentstatus=S1&selectedTabPaneId=worklist"+cachestr;
 }else if(from_page == ParameterContants.ACCEPTPRODUCTCHCHANGEWORKLIST){
	 if(iscache != "false" ){
		 cachestr = "&iscache=true";
	 }
	 url = getContextPath()+"/srrz/jsp/productchange/productchangework/productchchangework.jsp?currentstatus=S6&selectedTabPaneId=worklist"+cachestr;
 }else if(from_page == ParameterContants.FINISHPRODUCTCHCHANGEWORKLIST){
	 if(iscache != "false" ){
		 cachestr = "&iscache=true";
	 }
	 url = getContextPath()+"/srrz/jsp/productchange/productchangework/productchchangework.jsp?currentstatus=S8&selectedTabPaneId=worklist"+cachestr;
 }else if(from_page == ParameterContants.SPPRODUCTCHCHANGEWORKYIBAN){
	 if(iscache != "false" ){
		 cachestr = "&iscache=true";
	 }
	 url = getContextPath()+"/srrz/jsp/productchange/productchangework/productchchangework.jsp?currentstatus=S5&selectedTabPaneId=done_work"+cachestr;
 }else if(from_page == ParameterContants.PREPRODUCTCHCHANGEWORKYIBAN){
	 if(iscache != "false" ){
		 cachestr = "&iscache=true";
	 }
	 url = getContextPath()+"/srrz/jsp/productchange/productchangework/productchchangework.jsp?currentstatus=S1&selectedTabPaneId=done_work"+cachestr;
 }else if(from_page == ParameterContants.ACCEPTPRODUCTCHCHANGEWORKYIBAN){
	 if(iscache != "false" ){
		 cachestr = "&iscache=true";
	 }
	 url = getContextPath()+"/srrz/jsp/productchange/productchangework/productchchangework.jsp?currentstatus=S6&selectedTabPaneId=done_work"+cachestr;
 }else if(from_page == ParameterContants.FINISHPRODUCTCHCHANGEWORKYIBAN){
	 if(iscache != "false" ){
		 cachestr = "&iscache=true";
	 }
	 url = getContextPath()+"/srrz/jsp/productchange/productchangework/productchchangework.jsp?currentstatus=S8&selectedTabPaneId=done_work"+cachestr;
 }else if(from_page == ParameterContants.SPENTERVERIFYAPPROVEWORKLIST){
	 if(iscache != "false" ){
		 cachestr = "&iscache=true";
	 }
	 url = getContextPath()+"/srrz/jsp/softenterprise/enterVerifyAuditTab.jsp?currentstatus=S5&selectedTabPaneId=worklist"+cachestr;
 }else if(from_page == ParameterContants.PREENTERVERIFYAPPROVEWORKLIST){
	 if(iscache != "false" ){
		 cachestr = "&iscache=true";
	 }
	 url = getContextPath()+"/srrz/jsp/softenterprise/enterVerifyAuditTab.jsp?currentstatus=S1&selectedTabPaneId=worklist"+cachestr;
 }else if(from_page == ParameterContants.ACCEPTENTERVERIFYAPPROVEWORKLIST){
	 if(iscache != "false" ){
		 cachestr = "&iscache=true";
	 }
	 url = getContextPath()+"/srrz/jsp/softenterprise/enterVerifyAuditTab.jsp?currentstatus=S6&selectedTabPaneId=worklist"+cachestr;
 }else if(from_page == ParameterContants.FINISHENTERVERIFYAPPROVEWORKLIST){
	 if(iscache != "false" ){
		 cachestr = "&iscache=true";
	 }
	 url = getContextPath()+"/srrz/jsp/softenterprise/enterVerifyAuditTab.jsp?currentstatus=S8&selectedTabPaneId=worklist"+cachestr;
 }else if(from_page == ParameterContants.SPENTERVERIFYDONEWORKLIST){
	 if(iscache != "false" ){
		 cachestr = "&iscache=true";
	 }
	 url = getContextPath()+"/srrz/jsp/softenterprise/enterVerifyAuditTab.jsp?currentstatus=S5&selectedTabPaneId=done_work"+cachestr;
 }else if(from_page == ParameterContants.PREENTERVERIFYDONEWORKLIST){
	 if(iscache != "false" ){
		 cachestr = "&iscache=true";
	 }
	 url = getContextPath()+"/srrz/jsp/softenterprise/enterVerifyAuditTab.jsp?currentstatus=S1&selectedTabPaneId=done_work"+cachestr;
 }else if(from_page == ParameterContants.ACCEPTENTERVERIFYDONEWORKLIST){
	 if(iscache != "false" ){
		 cachestr = "&iscache=true";
	 }
	 url = getContextPath()+"/srrz/jsp/softenterprise/enterVerifyAuditTab.jsp?currentstatus=S6&selectedTabPaneId=done_work"+cachestr;
 }else if(from_page == ParameterContants.FINISHENTERVERIFYDONEWORKLIST){
	 if(iscache != "false" ){
		 cachestr = "&iscache=true";
	 }
	 url = getContextPath()+"/srrz/jsp/softenterprise/enterVerifyAuditTab.jsp?currentstatus=S8&selectedTabPaneId=done_work"+cachestr;
 }else if(from_page == ParameterContants.SPENTERCHANGEWORKLIST){
	 if(iscache != "false" ){
		 cachestr = "&iscache=true";
	 }
	 url = getContextPath()+"/srrz/jsp/softenterprise/enterChangeAuditTab.jsp?currentstatus=S5&selectedTabPaneId=worklist"+cachestr;
 }else if(from_page == ParameterContants.PREENTERCHANGEWORKLIST){
	 if(iscache != "false" ){
		 cachestr = "&iscache=true";
	 }
	 url = getContextPath()+"/srrz/jsp/softenterprise/enterChangeAuditTab.jsp?currentstatus=S1&selectedTabPaneId=worklist"+cachestr;
 }else if(from_page == ParameterContants.ACCEPTENTERCHANGEWORKLIST){
	 if(iscache != "false" ){
		 cachestr = "&iscache=true";
	 }
	 url = getContextPath()+"/srrz/jsp/softenterprise/enterChangeAuditTab.jsp?currentstatus=S6&selectedTabPaneId=worklist"+cachestr;
 }else if(from_page == ParameterContants.FINISHENTERCHANGEWORKLIST){
	 if(iscache != "false" ){
		 cachestr = "&iscache=true";
	 }
	 url = getContextPath()+"/srrz/jsp/softenterprise/enterChangeAuditTab.jsp?currentstatus=S8&selectedTabPaneId=worklist"+cachestr;
 }else if(from_page == ParameterContants.SPENTERCHANGEAFTERWORKLIST){
	 if(iscache != "false" ){
		 cachestr = "&iscache=true";
	 }
	 url = getContextPath()+"/srrz/jsp/softenterprise/enterChangeAuditTab.jsp?currentstatus=S5&selectedTabPaneId=done_work"+cachestr;
 }else if(from_page == ParameterContants.PREENTERCHANGEAFTERWORKLIST){
	 if(iscache != "false" ){
		 cachestr = "&iscache=true";
	 }
	 url = getContextPath()+"/srrz/jsp/softenterprise/enterChangeAuditTab.jsp?currentstatus=S1&selectedTabPaneId=done_work"+cachestr;
 }else if(from_page == ParameterContants.ACCEPTENTERCHANGEAFTERWORKLIST){
	 if(iscache != "false" ){
		 cachestr = "&iscache=true";
	 }
	 url = getContextPath()+"/srrz/jsp/softenterprise/enterChangeAuditTab.jsp?currentstatus=S6&selectedTabPaneId=done_work"+cachestr;
 }else if(from_page == ParameterContants.FINISHENTERCHANGEAFTERWORKLIST){
	 if(iscache != "false" ){
		 cachestr = "&iscache=true";
	 }
	 url = getContextPath()+"/srrz/jsp/softenterprise/enterChangeAuditTab.jsp?currentstatus=S8&selectedTabPaneId=done_work"+cachestr;
 }else if(from_page == ParameterContants.FINISHPRODUCTCHREPLACEWORKLIST){
	 if(iscache != "false" ){
		 cachestr = "&iscache=true";
	 }
	 url = getContextPath()+"/srrz/jsp/productchange/productchangework/productchreplacework.jsp?selectedTabPaneId=worklist"+cachestr;
 }else if(from_page == ParameterContants.FINISHPRODUCTCHREPLACEWORKLIST){
	 if(iscache != "false" ){
		 cachestr = "&iscache=true";
	 }
	 url = getContextPath()+"/srrz/jsp/productchange/productchangework/productchreplacework.jsp?selectedTabPaneId=done_work"+cachestr;
 }else if(from_page == ParameterContants.ENTERPRISEEXAMINELIST){
	 url = getContextPath()+"/srrz/jsp/annual/enterpriseExamineList.jsp"+cachestr;
 }else if(from_page == ParameterContants.GENERALSEARCHLISTSHOW){
	 url = getContextPath()+"/srrz/jsp/generalsearch/generalsearchlistshow.jsp"+cachestr;
 }else if(from_page == ParameterContants.GENERALSEARCHANNUALLISTSHOW){
	 url = getContextPath()+"/srrz/jsp/generalsearch/generalsearchannuallistshow.jsp"+cachestr;
 }else{
	 url = getContextPath()+"/srrz/jsp/main.jsp?menu_path=module";
 }
 

 window.parent.location = url;
 
}


function openNewDiv_new(iframeid) {
var m = "mask_new_div";
var _id = "mesWindow_div";
if (docEle(_id)) document.body.removeChild(docEle(_id));
if (docEle(m)) document.body.removeChild(docEle(m));
//mask���ֲ�
var newMask = document.createElement("div");
newMask.id = m;
newMask.style.position = "absolute";
newMask.style.zIndex = "9999";
_scrollWidth = Math.max(document.body.scrollWidth,document.documentElement.scrollWidth);
_scrollHeight = Math.max(document.body.scrollHeight,document.documentElement.scrollHeight);
newMask.style.width = _scrollWidth + "px";
newMask.style.height = _scrollHeight + "px";
newMask.style.top = "0px";
newMask.style.left = "0px";
newMask.style.background = "#33393C";
newMask.style.filter = "alpha(opacity=20)";
newMask.style.opacity = "0.80";
document.body.appendChild(newMask);
//�µ�����
var newDiv = document.createElement("div");
newDiv.id = _id;
newDiv.style.position = "absolute";
newDiv.style.zIndex = "9999";
newDivWidth = 200;
newDivHeight = 40;
newDiv.style.width = newDivWidth + "px";
newDiv.style.height = newDivHeight + "px";
newDiv.style.top = (document.body.scrollTop + document.body.clientHeight/2 - newDivHeight/2) + "px";
newDiv.style.left = (document.body.scrollLeft + document.body.clientWidth/2 - newDivWidth/2) + "px";
newDiv.style.background = "#EFEFEF";
newDiv.style.border = "1px solid #860001";
newDiv.style.padding = "15px";
newDiv.id="mesWindow_div";
newDiv.className="mesWindow";
newDiv.innerHTML=document.frames[iframeid].document.getElementById("exportreport").innerHTML;
var v_top=(document.body.clientHeight-newDiv.clientHeight)/2;
v_top+=document.documentElement.scrollTop;
styleStr="top:"+(v_top-230)+"px;left:"+(document.body.clientWidth/2-newDiv.clientWidth/2)+"px;position:absolute;width:600px;height:300px;margin-left:-300px;left:50%;z-index:9999;";
newDiv.style.cssText=styleStr;
document.body.appendChild(newDiv);
}
//�رղ�
function closeDiv_new(){
if(document.getElementById("mesWindow_div")!=null){
     document.body.removeChild(docEle('mesWindow_div'));
}
if(document.getElementById('mask_new_div')!=null){
     document.body.removeChild(docEle('mask_new_div'));
}
  return false;
}
var docEle = function() {
return document.getElementById(arguments[0]) || false;
}

