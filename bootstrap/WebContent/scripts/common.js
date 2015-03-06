/**
 * 
 */
if(!window.console){
        window.console={};
        window.console.log=function(){return;};
  	}
  	
function alert(msg, title, type,callback){ //success  error  warn   question info  none
	if(!type) type = 'info';
	if(!title) title = '提示';
	//$.ligerDialog.alert(msg, title, type, callback);
	var html ='<div id="dialog-message" title="'+title+'" style="display:none">';
	html += '<p>'+msg+'</p>';
	html += '</div>';
	$('body').append(html);
	$("#dialog-message").dialog({
      modal: true,
      buttons: {
        "确定": function() {
          if(callback)callback();
          $( this ).dialog( "close" );          
          $(this).remove();
          
        }
      }
    });
	$(".ui-dialog .ui-widget .ui-draggable .ui-resizable").css("width",230);
}