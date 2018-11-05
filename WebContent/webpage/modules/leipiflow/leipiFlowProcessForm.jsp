<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>流程步骤管理</title>
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
		<form:form id="inputForm" modelAttribute="leipiFlowProcess" action="${ctx}/leipiflow/leipiFlowProcess/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>	
		<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
		   <tbody>
				<tr>
					<td class="width-15 active"><label class="pull-right">is_child 子流程id有return_step_to结束后继续父流程下一步：</label></td>
					<td class="width-35">
						<form:textarea path="child_id" htmlEscape="false" rows="4"    class="form-control "/>
					</td>
					<td class="width-15 active"><label class="pull-right">子流程 结束后动作 0结束并更新父流程节点为结束  1结束并返回父流程步骤：</label></td>
					<td class="width-35">
						<form:input path="child_after" htmlEscape="false"    class="form-control "/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">子流程结束返回的步骤id：</label></td>
					<td class="width-35">
						<form:input path="child_back_process" htmlEscape="false"    class="form-control "/>
					</td>
					<td class="width-15 active"><label class="pull-right">[保留功能]主办人 子流程结束后下一步的主办人：</label></td>
					<td class="width-35">
						<form:input path="return_sponsor_ids" htmlEscape="false"    class="form-control "/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">[保留功能]经办人 子流程结束后下一步的经办人：</label></td>
					<td class="width-35">
						<form:input path="return_respon_ids" htmlEscape="false"    class="form-control "/>
					</td>
					<td class="width-15 active"><label class="pull-right">这个步骤可写的字段：</label></td>
					<td class="width-35">
						<form:input path="write_fields" htmlEscape="false"    class="form-control "/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">这个步骤隐藏的字段：</label></td>
					<td class="width-35">
						<form:input path="secret_fields" htmlEscape="false"    class="form-control "/>
					</td>
					<td class="width-15 active"><label class="pull-right">锁定不能更改宏控件的值：</label></td>
					<td class="width-35">
						<form:input path="lock_fields" htmlEscape="false"    class="form-control "/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">字段验证规则：</label></td>
					<td class="width-35">
						<form:input path="check_fields" htmlEscape="false"    class="form-control "/>
					</td>
					<td class="width-15 active"><label class="pull-right">本步骤的自动选主办人规则0:为不自动选择1：流程发起人2：本部门主管3指定默认人4上级主管领导5. 一级部门主管6. 指定步骤主办人：</label></td>
					<td class="width-35">
						<form:input path="auto_person" htmlEscape="false"    class="form-control "/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">是否允许修改主办人auto_type>0 0不允许 1允许（默认）：</label></td>
					<td class="width-35">
						<form:input path="auto_unlock" htmlEscape="false"    class="form-control "/>
					</td>
					<td class="width-15 active"><label class="pull-right">3指定步骤主办人ids：</label></td>
					<td class="width-35">
						<form:input path="auto_sponsor_ids" htmlEscape="false"    class="form-control "/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">3指定步骤主办人text：</label></td>
					<td class="width-35">
						<form:input path="auto_sponsor_text" htmlEscape="false"    class="form-control "/>
					</td>
					<td class="width-15 active"><label class="pull-right">3指定步骤主办人ids：</label></td>
					<td class="width-35">
						<form:input path="auto_respon_ids" htmlEscape="false"    class="form-control "/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">3指定步骤主办人text：</label></td>
					<td class="width-35">
						<form:input path="auto_respon_text" htmlEscape="false"    class="form-control "/>
					</td>
					<td class="width-15 active"><label class="pull-right">制定默认角色ids：</label></td>
					<td class="width-35">
						<form:input path="auto_role_ids" htmlEscape="false"    class="form-control "/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">制定默认角色text：</label></td>
					<td class="width-35">
						<form:input path="auto_role_text" htmlEscape="false"    class="form-control "/>
					</td>
					<td class="width-15 active"><label class="pull-right">[保留功能]指定其中一个步骤的主办人处理：</label></td>
					<td class="width-35">
						<form:input path="auto_process_sponsor" htmlEscape="false"    class="form-control "/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">本步骤的经办人授权范围ids：</label></td>
					<td class="width-35">
						<form:input path="range_user_ids" htmlEscape="false"    class="form-control "/>
					</td>
					<td class="width-15 active"><label class="pull-right">本步骤的经办人授权范围text：</label></td>
					<td class="width-35">
						<form:input path="range_user_text" htmlEscape="false"    class="form-control "/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">本步骤的经办部门授权范围：</label></td>
					<td class="width-35">
						<form:input path="range_dept_ids" htmlEscape="false"    class="form-control "/>
					</td>
					<td class="width-15 active"><label class="pull-right">本步骤的经办部门授权范围text：</label></td>
					<td class="width-35">
						<form:input path="range_dept_text" htmlEscape="false"    class="form-control "/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">本步骤的经办角色授权范围ids：</label></td>
					<td class="width-35">
						<form:input path="range_role_ids" htmlEscape="false"    class="form-control "/>
					</td>
					<td class="width-15 active"><label class="pull-right">本步骤的经办角色授权范围text：</label></td>
					<td class="width-35">
						<form:input path="range_role_text" htmlEscape="false"    class="form-control "/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">0明确指定主办人1先接收者为主办人：</label></td>
					<td class="width-35">
						<form:input path="receive_type" htmlEscape="false"    class="form-control "/>
					</td>
					<td class="width-15 active"><label class="pull-right">允许主办人在非最后步骤也可以办结流程：</label></td>
					<td class="width-35">
						<form:input path="is_user_end" htmlEscape="false"    class="form-control "/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">经办人可以转交下一步：</label></td>
					<td class="width-35">
						<form:input path="is_userop_pass" htmlEscape="false"    class="form-control "/>
					</td>
					<td class="width-15 active"><label class="pull-right">会签选项0禁止会签1允许会签（默认） 2强制会签：</label></td>
					<td class="width-35">
						<form:input path="is_sing" htmlEscape="false"    class="form-control "/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">会签可见性0总是可见（默认）,1本步骤经办人之间不可见2针对其他步骤不可见：</label></td>
					<td class="width-35">
						<form:input path="sign_look" htmlEscape="false"    class="form-control "/>
					</td>
					<td class="width-15 active"><label class="pull-right">转出条件：</label></td>
					<td class="width-35">
						<form:input path="out_condition" htmlEscape="false"    class="form-control "/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">左 坐标：</label></td>
					<td class="width-35">
						<form:input path="setleft" htmlEscape="false"    class="form-control "/>
					</td>
					<td class="width-15 active"><label class="pull-right">上 坐标：</label></td>
					<td class="width-35">
						<form:input path="settop" htmlEscape="false"    class="form-control "/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">样式 序列化：</label></td>
					<td class="width-35">
						<form:input path="style" htmlEscape="false"    class="form-control "/>
					</td>
					<td class="width-15 active"><label class="pull-right">是否删除：</label></td>
					<td class="width-35">
						<form:input path="isdel" htmlEscape="false"    class="form-control "/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">更新时间：</label></td>
					<td class="width-35">
						<form:input path="updatetime" htmlEscape="false"    class="form-control "/>
					</td>
					<td class="width-15 active"><label class="pull-right">结束时间：</label></td>
					<td class="width-35">
						<form:input path="dateline" htmlEscape="false"    class="form-control "/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">是否允许回退0不允许（默认） 1允许退回上一步2允许退回之前步骤：</label></td>
					<td class="width-35">
						<form:input path="is_back" htmlEscape="false"    class="form-control "/>
					</td>
					<td class="width-15 active"></td>
		   			<td class="width-35" ></td>
		  		</tr>
		 	</tbody>
		</table>
	</form:form>
</body>
</html>