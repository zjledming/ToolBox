<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<%@include file="/jsp/common/base.jsp"%>
<script type="text/javascript">
function init(){
	
	alert(1);
}

$(function () 
	      { $("[data-toggle='popover']").popover();
	      });

</script>
</head>



<body onload="init()">


	<h1>Hello, world!</h1>



	<!-- 标准的按钮 -->
	<button type="button" class="btn btn-default" disabled="disabled">默认按钮</button>

	<!-- 提供额外的视觉效果，标识一组按钮中的原始动作 -->
	<button type="button" class="btn btn-primary" disabled="disabled">原始按钮</button>

	<!-- 表示一个成功的或积极的动作 -->
	<button type="button" class="btn btn-success">成功按钮</button>

	<!-- 信息警告消息的上下文按钮 -->
	<button type="button" class="btn btn-info">信息按钮</button>

	<!-- 表示应谨慎采取的动作 -->
	<button type="button" class="btn btn-warning">警告按钮</button>

	<!-- 表示一个危险的或潜在的负面动作 -->
	<button type="button" class="btn btn-danger">危险按钮</button>

	<!-- 并不强调是一个按钮，看起来像一个链接，但同时保持按钮的行为 -->
	<button type="button" class="btn btn-link">链接按钮</button>
	
	
	
<div class="container" style="padding: 100px 50px 10px;" >
   <button type="button" class="btn btn-default" title="Popover title"  
      data-container="body" data-toggle="popover" data-placement="left" 
      data-content="左侧的 Popover 中的一些内容">
      左侧的 Popover
   </button>
   <button type="button" class="btn btn-primary" title="Popover title"  
      data-container="body" data-toggle="popover" data-placement="top" 
      data-content="顶部的 Popover 中的一些内容">
      顶部的 Popover
   </button>
   <button type="button" class="btn btn-success" title="Popover title"  
      data-container="body" data-toggle="popover" data-placement="bottom" 
      data-content="底部的 Popover 中的一些内容">
      底部的 Popover
   </button>
   <button type="button" class="btn btn-warning" title="Popover title"  
      data-container="body" data-toggle="popover" data-placement="right" 
      data-content="右侧的 Popover 中的一些内容">
      右侧的 Popover
   </button>
   </div>

</div>

</body>
</html>