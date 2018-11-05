<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>流程督办</title>
	<meta name="decorator" content="default"/>
	
	<script type="text/javascript">
		$(document).ready(function() {
			
			$("#savefiles").click(function(){
				$.post("${ctx}/oa/oaNotify/upOaNotifyfiles",
						{
					     "oanotifyid":"${oaNotify.id}",
					     "oafiles":$("#oafiles").val()
					    },function(data){
					var jsonData = jQuery.parseJSON(data);
					if(jsonData.status == 'y'){
						layer.msg(jsonData.info, {icon: 1});
						window.location.href="${ctx}/oa/oaNotify/view?id=${oaNotify.id}";
					}else{
						layer.msg(jsonData.info, {icon: 2});
					}
				});
			});
			
			$("#sub").click(function(){
				//alert($("#isWinRemind").is(":checked"));
				//alert($("#isMobileRemind").is(":checked"));
				//alert($("#content").val());
				if(!$("#isWinRemind").is(":checked") && !$("#isMobileRemind").is(":checked") && !$("#isOanotifyRemind").is(":checked")){
					layer.alert('请选择督办方式', {
						  icon: 2,
					});
					return false;
				}
				var ids =  [];
				var mobiles =  [];
				$('input[name="reminduser"]:checked').each(function(){    
					ids.push($(this).val());
					if($(this).attr("vl") != ''){
						mobiles.push($(this).attr("vl")); 
					}
					
				 });
				if(ids.length == 0){
					layer.alert('请选择督办人员', {
						  icon: 2,
					});
					return false;
				}
				$.post("${ctx}/flow/flowapply/goFlowHandle",
						{
					     "ids":ids.join(","),
					     "mobiles":mobiles.join(","),
					     "isWinRemind":$("#isWinRemind").is(":checked"),
					     "isMobileRemind":$("#isMobileRemind").is(":checked"),
					     "isOanotifyRemind":$("#isOanotifyRemind").is(":checked"),
					     "content":$("#content").val()
					    },function(data){
					var jsonData = jQuery.parseJSON(data);
					if(jsonData.status == 'y'){
						window.parent.layer.closeAll();
						window.parent.layer.msg(jsonData.info, {icon: 1});
					}else{
						layer.msg(jsonData.info, {icon: 2});
					}
				});
			});
		});
	</script>
	<style>
		table tr td input[type=checkbox] { margin-top:0;}
	</style>
</head>
<body class="hideScroll">
	<form id="inputForm" action="${ctx}/oa/oaNotify/save" method="post" class="form-horizontal">
		<sys:message content="${message}"/>	
		<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
		   <tbody>
		       
		       <tr>
		      	<td  class="width-15 active">	<label class="pull-right">流程标题：</label></td>
		         <td colspan="3">
		             ${flowtemplate.templatename}
		         </td>
		      </tr>
		      <tr>
		      	<td  class="width-15 active">	<label class="pull-right">督办方式：</label></td>
		         <td colspan="3">
		             <input type="checkbox" id="isWinRemind" checked="checked" value="1"/>窗口提醒
		             <input type="checkbox" id="isMobileRemind"" checked="checked" value="1"/>手机短信<!-- (仅一次) -->
		             <input type="checkbox" id="isOanotifyRemind"" checked="checked" value="1"/>传阅提醒
		         </td>
		      </tr>
		      <tr>
		         <td  class="width-15 active">	<label class="pull-right"><font color="red">*</font>督办信息：</label></td>
		         <td colspan="3" ><textarea id="content" htmlEscape="false" rows="6" maxlength="2000" class="form-control required">请及时处理待办流程[${flowtemplate.templatename}]！</textarea></td>
		         
		      </tr>
		      <tr>
		      	<td  class="width-15 active">	<label class="pull-right">接收督办信息的在办人员：</label></td>
		         <td colspan="3">
		             <c:forEach items="${nextUserList}" var="user">
		               <input type="checkbox" name="reminduser" value="${user.id}" vl="${user.mobile}" checked="checked" />${user.name}【${user.office.name}】&nbsp;&nbsp;
		             </c:forEach>
		         </td>
		      </tr>
			
		</tbody>
		</table>
      <div class="layui-layer-btn"><a id="sub" class="layui-layer-btn0">确定</a><a class="layui-layer-btn1" onclick="javascript:parent.window.layer.closeAll();">关闭</a></div>
	</form>
	
</body>
</html>