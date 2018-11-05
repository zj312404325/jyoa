<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>流程操作管理</title>
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
			gosel();
			$("#flowarea").change(function(){
				gosel();
			});
			
		});
		
		function gosel(){
			if($("#flowarea").find("option:selected").val()==0){
				$("#deptarea").show();
				$("#userarea").hide();
			}else{
				$("#deptarea").hide();
				$("#userarea").show();
			}
		}
	</script>
</head>
<body class="hideScroll">
		<form:form id="inputForm" modelAttribute="leipiFlow" action="${ctx}/leipiflow/leipiFlow/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
			<input id="leipiflowtype" name="leipiflowtype" type="hidden" value="${leipiFlow.leipiflowtype}"/>
		<sys:message content="${message}"/>	
		<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
		   <tbody>
				<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>流程名称：</label></td>
					<td class="width-35" colspan="3">
						<form:input path="flowname" htmlEscape="false"    class="form-control required" maxlength="20"/>
					</td>
				</tr>
				<tr>
					
					<td class="width-15 active"><label class="pull-right">描述：</label></td>
					<td class="width-35" colspan="3">
						<form:textarea path="flowdesc" htmlEscape="false" rows="4"    class="form-control " maxlength="100"/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>表单：</label></td>
					<td class="width-35" colspan="3">
						<select id="type" name="formid" class="form-control m-b required">
							<option value="" selected="selected"></option>
							<c:forEach items="${formList }" var="s">
								<option value="${s.id }" <c:if test="${leipiFlow.formid == s.id}">selected="selected"</c:if>>${s.templatename}</option>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>权限：</label></td>
					<td class="width-35" colspan="1">
						<select id="flowarea" name="flowarea" class="form-control m-b required">
							<option value="0" <c:if test="${leipiFlow.receivedeptids != null}">selected="selected"</c:if>>选取部门</option>
							<option value="1" <c:if test="${leipiFlow.receiveuserids != null}">selected="selected"</c:if>>选取人员</option>
						</select>
					</td>
					<td class="width-35" colspan="2">
							<span id="deptarea"><sys:treeselect id="receivedept" name="receivedeptids" value="${leipiFlow.receivedeptids}" labelName="receivedeptnames" labelValue="${leipiFlow.receivedeptnames}"
								title="部门" url="/sys/office/treeData?type=2" isAll="true" cssClass="form-control required" notAllowSelectParent="false" checked="true"/></span>
							<span id="userarea" style="display: none;"><sys:treeselect id="receiveruser" name="receiveuserids" value="${leipiFlow.receiveuserids}" labelName="receiveusernames" labelValue="${leipiFlow.receiveusernames}"
								title="用户" url="/sys/office/treeData?type=3" isAll="true" cssClass="form-control required" notAllowSelectParent="true" checked="true"/></span>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">是否可用：</label></td>
					<td class="width-35" colspan="3">
						<form:radiobuttons path="status" items="${fns:getDictList('status')}" itemLabel="label" itemValue="value" htmlEscape="false" class="i-checks required"/>
					</td>
				</tr>
		 	</tbody>
		</table>
	</form:form>
</body>
</html>