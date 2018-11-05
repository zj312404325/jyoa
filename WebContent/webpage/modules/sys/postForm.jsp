<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>信息管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		var validateForm;
		function doSubmit(){//回调函数，在编辑和保存动作时，供openDialog调用提交表单。
		  if(validateForm.form()){
			  $("#inputForm").submit();
			  return true;
		  }
	
		  return false;
		}
		$(document).ready(function() {
			validateForm = $("#inputForm").validate({
				submitHandler: function(form){
					loading('正在提交，请稍等...');
					form.submit();
				},
				errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					$("#messageBox").text("输入有误，请先更正。");
					if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
						error.appendTo(element.parent().parent());
					} else {
						error.insertAfter(element);
					}
				}
			});

            $(document).keypress(function(e) {
				if (e.keyCode == 13) {
					e.preventDefault();
				}
			});
			
		});

	</script>
</head>
<body class="hideScroll">
		<form:form id="inputForm" modelAttribute="post" action="${ctx}/sys/post/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
			<input type="hidden" name="officeid" value="${officeid}" />
		<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
		   <tbody>
				<tr>
					<td class="width-15 active"><label class="pull-right">岗位名称：</label></td>
					<td class="width-35" colspan="3">
						<form:input path="postname" htmlEscape="false"    class="form-control required"/>
					</td>
					<%--<td class="width-15 active"></td>
		   			<td class="width-35" ></td>--%>
		  		</tr>
		 	</tbody>
		</table>
	</form:form>
</body>
</html>