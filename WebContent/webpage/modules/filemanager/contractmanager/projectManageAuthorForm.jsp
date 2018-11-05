<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>项目管理管理</title>
	<meta name="decorator" content="default"/>
	<%@ include file="/webpage/include/common.jsp"%>
	<style type="text/css">
	.form-control[disabled], .form-control[readonly], fieldset[disabled] .form-control {
	    background-color: #fbfbfb;
	    opacity: 1;
	}
	</style>
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
	<form:form id="inputForm" modelAttribute="projectManage" action="${ctx}/filemanagement/projectManage/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>	
		<div class="panel-group" id="accordion">

		
		<div id="collapseOne" class="panel-collapse collapse in">
			<div class="panel-body">
		<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
		   <tbody>
		   <tr>
			   <td class="width-15 active"><label class="pull-right">授权：</label></td>
			   <td class="width-35">
							<span id="userarea" ><sys:treeselect id="receiveuser" name="receiveuserids" value="${projectManage.receiveuserids}" labelName="receiveusernames" labelValue="${projectManage.receiveusernames}"
																 title="用户" url="/sys/office/treeData?type=3" isAll="true" cssClass="form-control" notAllowSelectParent="true" checked="true"/></span>
			   </td>
		   </tr>
		 	</tbody>
		</table>
		</div>
		</div>
		</div>

	</form:form>
</body>
</html>