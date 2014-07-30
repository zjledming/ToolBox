/**
 * ���ܣ����������ʾ��Ϣ��
 */

/*
 * ���ܣ���û�������ʾ��Ϣ���ID����ʾ�����������ʾ��Ϣ��
 * ������tipDiv��ʾ��Ϣ���ID
 * ������getTipDivId()�����ʾ��Ϣ���ID
 *       setTipDivId(tipDivId)������ʾ��Ϣ���ID
 *       show()��ʾ��ʾ��Ϣ��
 *       hide()������ʾ��Ϣ��
 * �÷���������ʾ��Ϣ���ID��tipDiv.getTipDivId()
 *       ������ʾ��Ϣ���ID��tipDiv.setTipDivId(tipDivId)
 *       ��ʾ��ʾ��Ϣ�㣺tipDiv.show()
 *       ������ʾ��Ϣ�㣺tipDiv.hide();      
 */
var tipDiv = {
	//��ʾ��Ϣ���ID
	tipDivId : "null",
	//�����ʾ��Ϣ���ID
	getTipDivId : function () {
		return this.tipDivId;
	},
	//������ʾ��Ϣ���ID
	setTipDivId : function (tipDivId) {
		this.tipDivId = tipDivId;
	},
	//��ʾ��ʾ��Ϣ��
	show : function () { 
		var tip = document.getElementById(this.getTipDivId());
		//if(event.srcElement.innerText!=null&&event.srcElement.innerText!="") {
		with(tip.style){ 
			//������ʾ���X����Ϊ���λ��ƫ��10���� 
			posLeft=document.body.scrollLeft+event.x+10; 
			//������ʾ���Y����Ϊ���λ��ƫ��10���� 
			posTop=document.body.scrollTop+event.y+10;
			if(event.srcElement.tagName=="INPUT") {
				oTip.innerText = document.getElementById(event.srcElement.id).value;
			}
			else {
				//������ʾ���ڵ�����Ϊ��������ֵ 
				oTip.innerText=event.srcElement.innerText; 
			}
			if(oTip.innerText!=null&&oTip.innerText!=""){
				//�Կ�Ԫ�ط�ʽ��ʾ��ʾ��
				display="block";
			}
		}
	},
	//������ʾ��Ϣ��
	hide : function () {
		var tip = document.getElementById(this.getTipDivId());
		//�����ʾ�����ı� 
		tip.innerText="";  
		//������ʾ�� 
		tip.style.display="none"; 
	}
}

/*
 * ���ܣ����������ʾ��Ϣ��
 * �÷������������ʾ��Ϣ�㣺windowInit()
 */
function windowInit(){
	//����һ��DIV���󣬲����������tip�������������ʾ��ʾ���ֵĲ� 
	var tip=document.createElement("DIV");  
	//����DIV�����idΪoTip�������ڽű��п���         
	tip.id="oTip";       
	with(tip.style){ 
		//���ö���Ϊ���Զ�λ����Ϊ��ʾ���λ���������λ�ñ仯�ģ����Ա���Ϊ���Զ�λ�Ķ��� 
		position="absolute";   
		//���ö���ı߿�Ϊ1���ؿ�����ɫΪ��ɫ(#363636)��ʵ�� 
		border="1px solid #363636";   
		//���ö���ı�����ɫΪ����ɫ(#F8F8E0) 
		backgroundColor="#F8F8E0";  
		//���ö����ڵ�����ΪTahoma����СΪ12���� 
		font="normal 12px Tahoma"; 
		//���ö������������ıߵ����ֵ����λ���� 
		padding="1 3 1 3"; 
		display="none"; 
	} 
	//���ĵ�����׷����Ԫ��tip
	document.body.appendChild(tip);
	//������ʾ��Ϣ��tip��ID  
	tipDiv.setTipDivId(tip.id);  
};

//��windowInit()�����󶨵�����window.onload�¼�
window.attachEvent("onload", windowInit);
