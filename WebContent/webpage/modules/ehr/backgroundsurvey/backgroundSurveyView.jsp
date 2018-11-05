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
			
		});
	</script>
</head>
<body class="hideScroll">
		<form:form id="inputForm" modelAttribute="backgroundSurvey" action="${ctx}/ehr/backgroundSurvey/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>	
		<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
		   <tbody>
				<tr>
					<td class="width-10 active"><label class="pull-right">被调查人：</label></td>
					<td class="width-30">
						${backgroundSurvey.surveyname}
					</td>
					<td class="width-10 active"><label class="pull-right">年龄：</label></td>
					<td class="width-30">
						${backgroundSurvey.age}
					</td>
					<td class="width-10 active"><label class="pull-right">性别：</label></td>
					<td class="width-30">
						${fns:getDictLabel(backgroundSurvey.sex, 'sex', '')}
					</td>
				</tr>
				<tr>
					<td class="width-10 active"><label class="pull-right">部门：</label></td>
					<td class="width-30">
						${backgroundSurvey.depart}
					</td>
					<td class="width-10 active"><label class="pull-right">岗位：</label></td>
					<td class="width-30">
						${backgroundSurvey.position}
					</td>
					<td class="width-10 active"><label class="pull-right">入职时间：</label></td>
					<td class="width-30">
						${backgroundSurvey.entrydate}
					</td>
				</tr>
				<tr>
					<td class="width-10 active"><label class="pull-right">调查时间：</label></td>
					<td class="width-70" colspan="3">
						${backgroundSurvey.surveydate}
					</td>
					<td class="width-10 active"><label class="pull-right">调查人：</label></td>
					<td class="width-30">
						${backgroundSurvey.operater}
					</td>
				</tr>
				<tr>
		         <td  class="width-10 active">	<label class="pull-right"><!-- <font color="red">*</font> -->调查结果：</label></td>
		         <td colspan="5" >${fns:unescapeHtml(backgroundSurvey.surveyresult)}</td>
		         
		      </tr>
		      <tr>
		         <td  class="width-10 active">	<label class="pull-right"><!-- <font color="red">*</font> -->hr建议：</label></td>
		         <td colspan="5" >${fns:unescapeHtml(backgroundSurvey.hradvice)}</td>
		         
		      </tr>
		      <tr>
		      	<td  class="width-10 active">	<label class="pull-right">附件：</label></td>
		         <td colspan="5">
					<form:hidden id="surveyfiles" path="surveyfiles" htmlEscape="false" maxlength="255" class="form-control"/>
					<sys:ckfinder input="surveyfiles" type="files" uploadPath="/ehr/surveyfiles" selectMultiple="true" viewonly="true"/>
					<%-- <form:hidden id="files" path="files" htmlEscape="false" maxlength="255" class="form-control"/>
					<sys:ckfinder input="files" type="files" uploadPath="/oa/notify" selectMultiple="true" readonly="true" /> --%>
		         
		         
		         </td>
		      </tr>
		 	</tbody>
		</table>
	</form:form>
</body>
</html>