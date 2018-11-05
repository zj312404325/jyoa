<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>绩效考核面谈表管理</title>
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
        function setReadOnly() {
            $(".tbdisable input").attr("disabled","true");
            $(".tbdisable textarea").attr("disabled","true");
            $(".tbdisable button").attr("disabled","true");
            $(".tbdisable select").attr("disabled","true");
            $(".tbdisable a[name=removeFile]").remove();
        }
		$(document).ready(function() {
            if('${bhv}'=='1'){
                setReadOnly();
            }

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
			            elem: '#writedate', //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
			            event: 'focus' //响应事件。如果没有传入event，则按照默认的click
			        });
		});
	</script>
</head>
<body class="hideScroll">
		<form:form id="inputForm" modelAttribute="performanceInterview" action="${ctx}/checkmodel/performanceInterview/save" method="post" class="form-horizontal">
		<input type="hidden" name="type" value="${type }" />
		<form:hidden path="id"/>
		<sys:message content="${message}"/>	
		<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer tbdisable">
		   <tbody>
				<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>季度：</label></td>
					<td class="width-35">
						<form:select path="checkquarter" class="form-control required">
							<form:option value="" label=""/>
							<form:options items="${fns:getDictList('checkQuarter')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
						</form:select>
					</td>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>年份：</label></td>
					<td class="width-35">
						<input id="checkyear" name="checkyear" type="text" maxlength="20" onclick="WdatePicker({skin:'default',dateFmt:'yyyy'})" class="laydate-icon form-control layer-date required"
							value="${performanceInterview.checkyear}"/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>填表日期：</label></td>
					<td class="width-35">
						<input id="writedate" name="writedate" type="text" maxlength="20" class="laydate-icon form-control layer-date required"
							value="<fmt:formatDate value="${performanceInterview.writedate}" pattern="yyyy-MM-dd"/>"/>
					</td>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>面谈地点：</label></td>
					<td class="width-35">
						<form:input path="place" htmlEscape="false"    class="form-control required"/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">本季度你的主要工作任务：</label></td>
					<td class="width-35">
						<form:textarea path="work" htmlEscape="false" rows="4" maxlength="500"    class="form-control "/>
					</td>
					<td class="width-15 active"><label class="pull-right">本季度你的主要工作业绩（含改进计划/绩效标准）：</label></td>
					<td class="width-35">
						<form:textarea path="achievement" htmlEscape="false" rows="4" maxlength="500"    class="form-control "/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">本季度未能达成任务或未能发挥个人才能的主要原因：</label></td>
					<td class="width-35">
						<form:textarea path="uncompletereason" htmlEscape="false" rows="4" maxlength="500"    class="form-control "/>
					</td>
					<td class="width-15 active"><label class="pull-right">请列出本季度有哪些事实有利于工作任务的完成和个人才能的发挥：</label></td>
					<td class="width-35">
						<form:textarea path="favorreason" htmlEscape="false" rows="4" maxlength="500"    class="form-control "/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">下月工作计划（含改进计划/绩效标准）：</label></td>
					<td class="width-35">
						<form:textarea path="plan" htmlEscape="false" rows="4" maxlength="500"    class="form-control "/>
					</td>
					<td class="width-15 active"></td>
		   			<td class="width-35" ></td>
		  		</tr>
				<c:if test="${(performanceInterview.overachievement!=null&&performanceInterview.overachievement!='')||(performanceInterview.interviewcomment!=null&&performanceInterview.interviewcomment!='')||(performanceInterview.development!=null&&performanceInterview.development!='')}">
					<tr>
						<td class="width-15 active" style="border: none;"><label class="pull-right">考核人评价：</label></td>
						<td class="active" colspan="3" style="border: none;"></td>
					</tr>
					<tr>
						<td class="width-15 active"><label class="pull-right">本月突出业绩：</label></td>
						<td class="active" colspan="3">
							<form:textarea path="overachievement" htmlEscape="false" rows="4" maxlength="500"    class="form-control " readonly="true"/>
						</td>
					</tr>
					<tr>
						<td class="width-15 active"><label class="pull-right">面谈改进建议：</label></td>
						<td class="active" colspan="3">
							<form:textarea path="interviewcomment" htmlEscape="false" rows="4" maxlength="500"    class="form-control " readonly="true"/>
						</td>
					</tr>
					<tr>
						<td class="width-15 active"><label class="pull-right">职业发展建议：</label></td>
						<td class="active" colspan="3">
							<form:textarea path="development" htmlEscape="false" rows="4" maxlength="500"    class="form-control " readonly="true"/>
						</td>
					</tr>
				</c:if>
		 	</tbody>
		</table>
	</form:form>
</body>
</html>