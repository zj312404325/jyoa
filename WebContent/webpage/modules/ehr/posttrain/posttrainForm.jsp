<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>信息管理</title>
	<meta name="decorator" content="default"/>
	<!-- SUMMERNOTE -->
	 <link href="${ctxStatic}/summernote/summernote.css" rel="stylesheet">
	 <link href="${ctxStatic}/summernote/summernote-bs3.css" rel="stylesheet">
	 <script src="${ctxStatic}/summernote/summernote.min.js"></script>
	 <script src="${ctxStatic}/summernote/summernote-zh-CN.js"></script>
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
			
			$('#content,#summary').summernote({
                lang: 'zh-CN',
                toolbar: [["style", ["style"]], ["font", ["bold", "italic", "underline", "clear"]], ["color", ["color"]], ["para", ["ul", "ol", "paragraph"]], ["height", ["height"]], ["table", ["table"]]]
            });
			
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
			laydate({
	            elem: '#traintime', //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
	            event: 'focus' //响应事件。如果没有传入event，则按照默认的click
	        });
		});
	</script>
</head>
<body class="hideScroll">
		<form:form id="inputForm" modelAttribute="posttrain" action="${ctx}/ehr/posttrain/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>	
		<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
		   <tbody>
				<tr>
					<td class="width-15 active"><label class="pull-right">培训标题：</label></td>
					<td class="width-35">
						<form:input path="title" htmlEscape="false"  maxlength="20"  class="form-control required"/>
					</td>
					<td class="width-15 active"><label class="pull-right">培训地点：</label></td>
					<td class="width-35">
						<form:input path="address" htmlEscape="false"  maxlength="20"  class="form-control required"/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">主办机构：</label></td>
					<td class="width-35">
						<form:input path="organizer" htmlEscape="false"  maxlength="20"  class="form-control required"/>
					</td>
					<td class="width-15 active"><label class="pull-right">培训时间：</label></td>
					<td class="width-35">
						<%-- <form:input path="traintime" htmlEscape="false"  readonly="true"  class="form-control required"/> --%>
						<input id="traintime" name="traintime" class="form-control required valid" type="text" readonly="true" value="<fmt:formatDate pattern="yyyy-MM-dd" value="${posttrain.traintime}" />" aria-required="true" aria-invalid="false">
					</td>
				</tr>
				<tr>
			         <td  class="width-15 active">	<label class="pull-right"><!-- <font color="red">*</font> -->培训内容：</label></td>
			         <td colspan="3" ><form:textarea path="content" htmlEscape="false" rows="6" maxlength="2000" class="form-control required"/></td>
			         
			    </tr>
				<tr>
			         <td  class="width-15 active">	<label class="pull-right"><!-- <font color="red">*</font> -->培训总结：</label></td>
			         <td colspan="3" ><form:textarea path="summary" htmlEscape="false" rows="6" maxlength="2000" class="form-control required"/></td>
			         
			    </tr>
		 	</tbody>
		</table>
	</form:form>
</body>
</html>