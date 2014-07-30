/**
 * <p> ���ڴ���base64����ͽ���
 * <p>Copyright: Copyright (C)  </p>
 * @creator zhangxian on 2006-11-20
 * @version	1.0
 */

var base64EncodeChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/";

var base64DecodeChars = new Array(

����-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,

����-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,

����-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 62, -1, -1, -1, 63,

����52, 53, 54, 55, 56, 57, 58, 59, 60, 61, -1, -1, -1, -1, -1, -1,

����-1,��0,��1,��2,��3,  4,��5,��6,��7,��8,��9, 10, 11, 12, 13, 14,

����15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, -1, -1, -1, -1, -1,

����-1, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40,

����41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, -1, -1, -1, -1, -1);

function base64encode(str) {

����var out, i, len;

����var c1, c2, c3;

����len = str.length;

����i = 0;

����out = "";

����while(i < len) {

 c1 = str.charCodeAt(i++) & 0xff;

 if(i == len)

 {

���� out += base64EncodeChars.charAt(c1 >> 2);

���� out += base64EncodeChars.charAt((c1 & 0x3) << 4);

���� out += "==";

���� break;

 }

 c2 = str.charCodeAt(i++);

 if(i == len)

 {

���� out += base64EncodeChars.charAt(c1 >> 2);

���� out += base64EncodeChars.charAt(((c1 & 0x3)<< 4) | ((c2 & 0xF0) >> 4));

���� out += base64EncodeChars.charAt((c2 & 0xF) << 2);

���� out += "=";

���� break;

 }

 c3 = str.charCodeAt(i++);

 out += base64EncodeChars.charAt(c1 >> 2);

 out += base64EncodeChars.charAt(((c1 & 0x3)<< 4) | ((c2 & 0xF0) >> 4));

 out += base64EncodeChars.charAt(((c2 & 0xF) << 2) | ((c3 & 0xC0) >>6));

 out += base64EncodeChars.charAt(c3 & 0x3F);

����}

����return out;

}

function base64decode(str) {

����var c1, c2, c3, c4;

����var i, len, out;

����len = str.length;

����i = 0;

����out = "";

����while(i < len) {

 /* c1 */

 do {

���� c1 = base64DecodeChars[str.charCodeAt(i++) & 0xff];

 } while(i < len && c1 == -1);

 if(c1 == -1)

���� break;

 /* c2 */

 do {

���� c2 = base64DecodeChars[str.charCodeAt(i++) & 0xff];

 } while(i < len && c2 == -1);

 if(c2 == -1)

���� break;

 out += String.fromCharCode((c1 << 2) | ((c2 & 0x30) >> 4));

 /* c3 */

 do {

���� c3 = str.charCodeAt(i++) & 0xff;

���� if(c3 == 61)

��return out;

���� c3 = base64DecodeChars[c3];

 } while(i < len && c3 == -1);

 if(c3 == -1)

���� break;

 out += String.fromCharCode(((c2 & 0XF) << 4) | ((c3 & 0x3C) >> 2));

 /* c4 */

 do {

���� c4 = str.charCodeAt(i++) & 0xff;

���� if(c4 == 61)

��return out;

���� c4 = base64DecodeChars[c4];

 } while(i < len && c4 == -1);

 if(c4 == -1)

���� break;

 out += String.fromCharCode(((c3 & 0x03) << 6) | c4);

����}

����return out;

}

function utf16to8(str) {

����var out, i, len, c;

����out = "";

����len = str.length;

����for(i = 0; i < len; i++) {

 c = str.charCodeAt(i);

 if ((c >= 0x0001) && (c <= 0x007F)) {

���� out += str.charAt(i);

 } else if (c > 0x07FF) {

���� out += String.fromCharCode(0xE0 | ((c >> 12) & 0x0F));

���� out += String.fromCharCode(0x80 | ((c >>��6) & 0x3F));

���� out += String.fromCharCode(0x80 | ((c >>��0) & 0x3F));

 } else {

���� out += String.fromCharCode(0xC0 | ((c >>��6) & 0x1F));

���� out += String.fromCharCode(0x80 | ((c >>��0) & 0x3F));

 }

����}

����return out;

}

function utf8to16(str) {

����var out, i, len, c;

����var char2, char3;

����out = "";

����len = str.length;

����i = 0;

����while(i < len) {

 c = str.charCodeAt(i++);

 switch(c >> 4)

 {

�� case 0: case 1: case 2: case 3: case 4: case 5: case 6: case 7:

���� // 0xxxxxxx

���� out += str.charAt(i-1);

���� break;

�� case 12: case 13:

���� // 110x xxxx�� 10xx xxxx

���� char2 = str.charCodeAt(i++);

���� out += String.fromCharCode(((c & 0x1F) << 6) | (char2 & 0x3F));

���� break;

�� case 14:

���� // 1110 xxxx��10xx xxxx��10xx xxxx

���� char2 = str.charCodeAt(i++);

���� char3 = str.charCodeAt(i++);

���� out += String.fromCharCode(((c & 0x0F) << 12) |

��������((char2 & 0x3F) << 6) |

��������((char3 & 0x3F) << 0));

���� break;

 }

����}

����return out;

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
function autoSetTabSize(isSet,framename){
	if(isSet){
		if(document.frames[framename].document.body.scrollHeight > 180){
			$(framename).style.height = document.frames[framename].document.body.scrollHeight-10;		
		}
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
/**
* ��ת�����ַ�ת��
* @param str
* @return
*/
function undescape(str){
	if(/&quot/.test(str)){
		str = str.replaceAll("&quot;","\"");
	}
	if(/&lt;/.test(str)){
		str = str.replaceAll("&lt;","<");
	}
	if(/&gt;/.test(str)){
		str = str.replaceAll("&gt;",">");
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


//��֤���֤�Ƿ�Ϸ�
function clsIDCard(CardNo) {
	  this.Valid=false;
	  this.ID15='';
	  this.ID18='';
	  this.Local='';
	  if(CardNo!=null)this.SetCardNo(CardNo);
	}

	// �������֤���룬15λ����18λ
	clsIDCard.prototype.SetCardNo = function(CardNo) {
	  this.ID15='';
	  this.ID18='';
	  this.Local='';
	  CardNo=CardNo.replace(" ","");
	  var strCardNo;
	  if(CardNo.length==18) {
	    pattern= /^\d{17}(\d|x|X)$/;
	    if (pattern.exec(CardNo)==null)return;
	    strCardNo=CardNo.toUpperCase();
	  } else {
	    pattern= /^\d{15}$/;
	    if (pattern.exec(CardNo)==null)return;
	    strCardNo=CardNo.substr(0,6)+'19'+CardNo.substr(6,9)
	    strCardNo+=this.GetVCode(strCardNo);
	  }
	  this.Valid=this.CheckValid(strCardNo);
	}

	// У�����֤��Ч��
	clsIDCard.prototype.IsValid = function() {
	  return this.Valid;
	}

	// ���������ַ�������ʽ���£�1981-10-10
	clsIDCard.prototype.GetBirthDate = function() {
	  var BirthDate='';
	  if(this.Valid)BirthDate=this.GetBirthYear()+'-'+this.GetBirthMonth()+'-'+this.GetBirthDay();
	  return BirthDate;
	}

	// ���������е��꣬��ʽ���£�1981
	clsIDCard.prototype.GetBirthYear = function() {
	  var BirthYear='';
	  if(this.Valid)BirthYear=this.ID18.substr(6,4);
	  return BirthYear;
	}

	// ���������е��£���ʽ���£�10
	clsIDCard.prototype.GetBirthMonth = function() {
	  var BirthMonth='';
	  if(this.Valid)BirthMonth=this.ID18.substr(10,2);
	  if(BirthMonth.charAt(0)=='0')BirthMonth=BirthMonth.charAt(1);
	  return BirthMonth;
	}

	// ���������е��գ���ʽ���£�10
	clsIDCard.prototype.GetBirthDay = function() {
	  var BirthDay='';
	  if(this.Valid)BirthDay=this.ID18.substr(12,2);
	  return BirthDay;
	}

	// �����Ա�1���У�0��Ů
	clsIDCard.prototype.GetSex = function() {
	  var Sex='';
	  if(this.Valid)Sex=this.ID18.charAt(16)%2;
	  return Sex;
	}

	// ����15λ���֤����
	clsIDCard.prototype.Get15 = function() {
	  var ID15='';
	  if(this.Valid)ID15=this.ID15;
	  return ID15;
	}

	// ����18λ���֤����
	clsIDCard.prototype.Get18 = function() {
	  var ID18='';
	  if(this.Valid)ID18=this.ID18;
	  return ID18;
	}

	// ��������ʡ�����磺�Ϻ��С��㽭ʡ
	clsIDCard.prototype.GetLocal = function() {
	  var Local='';
	  if(this.Valid)Local=this.Local;
	  return Local;
	}

	clsIDCard.prototype.GetVCode = function(CardNo17) {
	  var Wi = new Array(7,9,10,5,8,4,2,1,6,3,7,9,10,5,8,4,2,1);
	  var Ai = new Array('1','0','X','9','8','7','6','5','4','3','2');
	  var cardNoSum = 0;
	  for (var i=0; i<CardNo17.length; i++)cardNoSum+=CardNo17.charAt(i)*Wi[i];
	  var seq = cardNoSum%11;
	  return Ai[seq];
	}

	clsIDCard.prototype.CheckValid = function(CardNo18) {
	  if(this.GetVCode(CardNo18.substr(0,17))!=CardNo18.charAt(17))return false;
	  if(!this.IsDate(CardNo18.substr(6,8)))return false;
	  var aCity={11:"����",12:"���",13:"�ӱ�",14:"ɽ��",15:"���ɹ�",21:"����",22:"����",23:"������ ",31:"�Ϻ�",32:"����",33:"�㽭",34:"����",35:"����",36:"����",37:"ɽ��",41:"����",42:"���� ",43:"����",44:"�㶫",45:"����",46:"����",50:"����",51:"�Ĵ�",52:"����",53:"����",54:"���� ",61:"����",62:"����",63:"�ຣ",64:"����",65:"�½�",71:"̨��",81:"���",82:"����",91:"����"};
	  if(aCity[parseInt(CardNo18.substr(0,2))]==null)return false;
	  this.ID18=CardNo18;
	  this.ID15=CardNo18.substr(0,6)+CardNo18.substr(8,9);
	  this.Local=aCity[parseInt(CardNo18.substr(0,2))];
	  return true;
	}

	clsIDCard.prototype.IsDate = function(strDate) {
	  var r = strDate.match(/^(\d{1,4})(\d{1,2})(\d{1,2})$/);
	  if(r==null)return false;
	  var d= new Date(r[1], r[2]-1, r[3]);
	  return (d.getFullYear()==r[1]&&(d.getMonth()+1)==r[2]&&d.getDate()==r[3]);
	}



//��֤���֤�Ƿ�Ϸ�
function checkIdcard(idcard){ 	
	var checkFlag = new clsIDCard(idcard);
if (!checkFlag.IsValid()){
	  return "��֤��ͨ����";
}else{
	  return "��֤ͨ��!";
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

//
var docEle = function() {
 return document.getElementById(arguments[0]) || false;
}

function openNewDiv() {
 var m = "mask";
 var _id = "newdiv";
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
	var marquee = document.createElement("marquee");
	marquee.direction = "right";
	marquee.behavior = "alternate";
	marquee.style.lineHeight = "1.2"; 
	marquee.innerText = "���ڴ������Ժ�...";
	newDiv.appendChild(marquee);

 document.body.appendChild(newDiv);
}
//�رղ�
function closeDiv(){
	  document.body.removeChild(docEle('newdiv'));
   document.body.removeChild(docEle('mask'));
   return false;
}

function openNewDiv_new() {
 var m = "mask_new";
 var _id = "newdiv_new";
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
	var marquee = document.createElement("marquee");
	marquee.direction = "right";
	marquee.behavior = "alternate";
	marquee.style.lineHeight = "1.2"; 
	marquee.innerText = "���ڼ��أ����Ժ�...";
	newDiv.appendChild(marquee);

 document.body.appendChild(newDiv);
}
//�رղ�
function closeDiv_new(){
	if(document.getElementById("newdiv_new")!=null){
	     document.body.removeChild(docEle('newdiv_new'));
	}
	if(document.getElementById('mask_new')!=null){
      document.body.removeChild(docEle('mask_new'));
	}
   return false;
}
//У��
function valiBlank(id,text){
	var val = document.getElementById(id).value;
	if(IsSpace(val)){
		alert(text);
		return false;
	}else{
		return true;
	}
}

//ѡ������
function checkSelect(id,val){
	var sel = document.getElementById(id);
	for(var i=0;i<sel.options.length;i++){
		if(sel.options[i].value == val){
			sel.options[i].selected = true;
			break;
		}
	}
}

//�õ�����ֵ
function getSelectInnerHTML(id){
	var sel = document.getElementById(id);
	for(var i=0;i<sel.options.length;i++){
		if(sel.options[i].selected == true){
			return sel.options[i].innerHTML;
			break;
		}
	}
}
//����
function encodeURLKcapp(str){
	if(!IsSpace(str)){
		return encodeURIComponent(encodeURIComponent(str));
	}else{
		return str;
	}
}  

