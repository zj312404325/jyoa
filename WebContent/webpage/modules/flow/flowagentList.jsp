<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>流程代理管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			
		});
	</script>
</head>
<body class="gray-bg">
	<div class="wrapper wrapper-content">
	<div class="ibox">
	<div class="ibox-title">
		<h5>流程代理 <c:if test="${flowagent.agentuserid != null}"><font style="color: red">(代理中)</font></c:if></h5>
		<div class="ibox-tools">
			<!-- <a class="collapse-link">
				<i class="fa fa-chevron-up"></i>
			</a> -->
			<!-- <a class="dropdown-toggle" data-toggle="dropdown" href="#">
				<i class="fa fa-wrench"></i>
			</a> -->
			<!-- <ul class="dropdown-menu dropdown-user">
				<li><a href="#">选项1</a>
				</li>
				<li><a href="#">选项2</a>
				</li>
			</ul> -->
			<!-- <a class="close-link">
				<i class="fa fa-times"></i>
			</a> -->
		</div>
	</div>
		<div class="ibox-content">
    <sys:message content="${message}"/>	
		<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
		   <tbody>
				<tr>
					<td class="width-15 active"><label class="pull-right">代理人：</label></td>
					<td class="width-35">
					   <form:form id="inputForm" modelAttribute="flowagent" action="${ctx}/flow/flowagent/save" method="post" class="form-horizontal">
							<form:hidden path="id"/>
							
						    <sys:treeselectone id="agentuser" name="agentuserid" value="${flowagent.agentuserid}" labelName="agentname" labelValue="${flowagent.agentname}"
							title="用户" url="/sys/office/treeData?type=3" cssClass="form-control required"  />
							</form:form>
					</td>
					<td class="width-15">
					  <button class="btn btn-white btn-sm" data-toggle="tooltip" data-placement="left" id="saveagent" title="确认代理"><i class="fa fa-file-text-o"></i> 确认代理</button>
					  <c:if test="${flowagent.agentuserid != null}"><button class="btn btn-white btn-sm" id="cancleagent" data-toggle="tooltip" data-placement="top"><i class="fa fa-trash-o"> 取消代理</i></button></c:if>
					</td>
				</tr>
		 	</tbody>
		</table>
		</div>
	</div>
</div>

<script>
		$(function(){
			$("#saveagent").click(function(){
				var index = layer.load(0, {shade: false});
				$("#inputForm").submit();
			});
			$("#cancleagent").click(function(){
				var index = layer.load(0, {shade: false});
				location.href="${ctx}/flow/flowagent/delete?id=${flowagent.id}";
			});
		});
</script>
</body>
</html>