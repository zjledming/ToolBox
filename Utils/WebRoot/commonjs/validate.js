/*
	* ��֤�ʼ���ʽ�Ƿ���ȷ�������ȷ�򷵻�true�����򾯸沢����false
	* valueΪҪ��֤��ֵ
	* alertValueΪ����ֵ
	* regExpΪ������ʽ
	* ����ֵ����Ϊboolean
*/
function checkEmail(value,alertValue){
	var regExp = /^\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/;
	value = value.trim();
	if(!IsSpace(value)){
		if(!regExp.test(value)){
			alert(alertValue);
			return false;
		}
	}
	return true;
}

/*
	* ��֤�绰�����ʽ�Ƿ���ȷ�������ȷ�򷵻�true�����򾯸沢����false
	* valueΪҪ��֤��ֵ
	* alertValueΪ����ֵ
	* regExpΪ������ʽ
	* ����ֵ����Ϊboolean
*/
function checkPhone(value,alertValue){
	var regExp = /(^\d{7}$)|(^0\d{3}-?\d{7}$)|(^0?1[35]\d{9}$)/;
	value = value.trim();
	if(!IsSpace(value)){
		if(!regExp.test(value)){
			alert(alertValue);
			return false;
		}
	}
	return true;
}

/*
	* ��֤�ֻ������ʽ�Ƿ���ȷ�������ȷ�򷵻�true,���򾯸沢����false
	* valueΪҪ��֤��ֵ
	* alertValueΪ����ֵ
	* regExpΪ������ʽ
	* ����ֵ����Ϊboolean
*/
function checkMobile(value,alertValue){
	value = value.trim();
	var regExp = /^0?\d{11}$/;
	if(!IsSpace(value)){
		if(!regExp.test(value)){
			alert(alertValue);
			return false;
		}
	}
	return true;
}

/*
	* �ж�ֵ�Ƿ�Ϊ�գ������Ϊ���򷵻�true�����򾯸沢����false
	* valueΪҪ��֤��ֵ
	* alertValueΪ����ֵ
	* ����ֵ����Ϊboolean
*/
function checkIsEmpty(value,alertValue){
	value = value.trim();
	if(IsSpace(value)){
		alert(alertValue);
		return false;
	}
	return true;
}

/*
	* �ж�ֵ�ó����Ƿ���ڹ涨���ȣ�����������򷵻�true�����򾯸沢����false
	* valueΪҪ��֤��ֵ
	* lengthΪ�涨����
	* alertValueΪ����ֵ
	* ����ֵ����Ϊboolean
*/
function checkLength(value,length,alertValue){
	value = value.trim();
	if(value.length>length){
		alert(alertValue);
		return false;
	}
	return true;
}

/*
	* ��֤�Ƿ�Ϊ�涨�ĸ�ʽ�����֣�����ǵ��򷵻�true�����򾯸沢����false
	* valueΪҪ��֤��ֵ
	* intLengthΪ����λ��
	* floatLengthΪС��λ��
	* alertValueΪ����ֵ
	* idΪ��ȡֵ���ı����ID
	* ����ֵ����Ϊboolean
*/
function checkNum(value,intLength,floatLength,alertValue,id){
	value = value.trim();
	if(!IsSpace(value)){
		value = Math.round(value*Math.pow(10,floatLength));
		value = value.toString();
		value = value.substring(0,value.length-floatLength)+"."+value.substring(value.length-floatLength,value.length);
		if(isNaN(value)){
			alert(alertValue);
			return false;
		}
		document.getElementById(id).value = value;
	}
	return true;
}

/*
	* ��֤�Ƿ�Ϊ�涨�ĸ�ʽ�����֣�����ǵ��򷵻�true�����򾯸沢����false
	* valueΪҪ��֤��ֵ
	* intLengthΪ����λ����Ϊ����0������
	* floatLengthΪС��λ����Ϊ���ڵ���0�������������ֵ֤Ϊ���������ֵΪ0
	* alertValueΪ����ֵ
	* regExpΪ������ʽ
	* ����ֵ����Ϊboolean
*/
function checkNum(value,intLength,floatLength,alertValue){
	value = value.trim();
	var regExp = /^(-|\+)?\d+(\.)?(\d+)*$/;
	var index = value.indexOf(".");
	if(!IsSpace(value)){
		var values = new Array();
		if(index != "-1"){
			values[0] = value.substring(0,index);
			values[1] = value.substring(index+1,value.length);
		}
		else{
			values[0] = value;
			values[1] = "0";
		}
		if(floatLength==0&&index!="-1"){
			alert(alertValue);
			return false;
		}
		if(values[0].length>intLength){
			alert(alertValue);
			return false;
		}
		else if(floatLength!=0){
			if(values[1].length>floatLength){
				alert(alertValue);
				return false;
			}
		}
		if(!regExp.test(value)){
			alert(alertValue);
			return false;
		}
	}
	return true;
}

/*
	* ��֤�����Ƿ���ȷ������ǵ��򷵻�true�����򾯸沢����false
	* valueΪҪ��֤��ֵ
	* alertValueΪ����ֵ
	* ����ֵ����Ϊboolean
*/
function checkDate(value,alertValue){
	value = value.trim();
	var regExp = /^((([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})-(((0?[13578]|1[02])-(0?[1-9]|[12][0-9]|3[01]))|((0?[469]|11)-(0?[1-9]|[12][0-9]|30))|(0?2-(0?[1-9]|[1][0-9]|2[0-8]))))|((([0-9]{2})(0[48]|[2468][048]|[13579][26])|((0[48]|[2468][048]|[3579][26])00))-0?2-29))$/;
	if(!IsSpace(regExp)){
		if(!regExp.test(value)){
			alert(alertValue);
			return false;
		}
	}
	return true;
}

/*
	* ��֤�����ַ������ţ�'��,������е������򾯸沢����false,���򷵻�true
	* valueΪҪ��֤��ֵ
	* ��������Ϊboolean
*/
function checkSpecial(value){
	value = value.trim();
	var str = value;
	var SPECIAL_STR = "'";
	for(i=0;i<str.length;i++){
		if (SPECIAL_STR.indexOf(str.charAt(i))!= -1){
			alert("���ܺ����ַ�������("+str.charAt(i)+")�����������룡");
			return false;
		}
	}
	return true;
}