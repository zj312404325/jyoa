<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>考核时间管理</title>
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
			
					laydate({
			            elem: '#startDate', //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
			            event: 'focus' //响应事件。如果没有传入event，则按照默认的click
			        });
					laydate({
			            elem: '#enddate', //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
			            event: 'focus' //响应事件。如果没有传入event，则按照默认的click
			        });
					/* laydate.render({
			            elem: '#checkYear', //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
			            event: 'focus', //响应事件。如果没有传入event，则按照默认的click
			            type: 'year'
			        }); */
			        
			        
		});
	</script>
</head>
<body class="hideScroll">
		<form:form id="inputForm" modelAttribute="checkTime" action="${ctx}/checkmodel/checkTime/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>	
		<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
		   <tbody>
				<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>年份：</label></td>
					<td class="width-35">
						<input id="checkYear" name="checkYear" type="text" maxlength="20" onclick="WdatePicker({skin:'whyGreen',dateFmt:'yyyy'})" class="laydate-icon form-control layer-date Wdate required "
							value="${checkTime.checkYear}"/>
					</td>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>季度：</label></td>
					<td class="width-35">
						<form:select path="checkQuarter" class="form-control required">
							<form:option value="" label=""/>
							<form:options items="${fns:getDictList('checkQuarter')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
						</form:select>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>考核开始时间：</label></td>
					<td class="width-35">
						<input id="startDate" name="startDate" type="text" maxlength="20" class="laydate-icon form-control layer-date required"
							value="<fmt:formatDate value="${checkTime.startDate}" pattern="yyyy-MM-dd"/>"/>
					</td>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>考核结束时间：</label></td>
					<td class="width-35">
						<input id="enddate" name="enddate" type="text" maxlength="20" class="laydate-icon form-control layer-date required"
							value="<fmt:formatDate value="${checkTime.enddate}" pattern="yyyy-MM-dd"/>"/>
					</td>
				</tr>
		 	</tbody>
		</table>
	</form:form>
</body>
</html>