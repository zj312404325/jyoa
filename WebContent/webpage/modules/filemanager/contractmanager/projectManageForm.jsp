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
			
					laydate({
			            elem: '#planstartdate', //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
			            event: 'focus' //响应事件。如果没有传入event，则按照默认的click
			        });
					laydate({
			            elem: '#planenddate', //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
			            event: 'focus' //响应事件。如果没有传入event，则按照默认的click
			        });
					
					
					$('#collapseOne').on('show.bs.collapse', function () {
						if($(this).prev(".panel-heading").find("i").hasClass("glyphicon-chevron-down")){
							$(this).prev(".panel-heading").find("i").removeClass("glyphicon-chevron-down").addClass("glyphicon-chevron-up");
						}
						
					});
					$('#collapseOne').on('hide.bs.collapse', function () {
						if($(this).prev(".panel-heading").find("i").hasClass("glyphicon-chevron-up")){
							$(this).prev(".panel-heading").find("i").removeClass("glyphicon-chevron-up").addClass("glyphicon-chevron-down");
						}
						
					});
		});
		function addRow(list, idx, tpl, row){
			$(list).append(Mustache.render(tpl, {
				idx: idx, delBtn: true, row: row
			}));
			$(list+idx).find("select").each(function(){
				$(this).val($(this).attr("data-value"));
			});
			$(list+idx).find("input[type='checkbox'], input[type='radio']").each(function(){
				var ss = $(this).attr("data-value").split(',');
				for (var i=0; i<ss.length; i++){
					if($(this).val() == ss[i]){
						$(this).attr("checked","checked");
					}
				}
			});
		}
		function delRow(obj, prefix){
			var id = $(prefix+"_id");
			var delFlag = $(prefix+"_delFlag");
			if (id.val() == ""){
				$(obj).parent().parent().remove();
			}else if(delFlag.val() == "0"){
				delFlag.val("1");
				$(obj).html("&divide;").attr("title", "撤销删除");
				$(obj).parent().parent().addClass("error");
			}else if(delFlag.val() == "1"){
				delFlag.val("0");
				$(obj).html("&times;").attr("title", "删除");
				$(obj).parent().parent().removeClass("error");
			}
		}
	</script>
</head>
<body class="hideScroll">
	<form:form id="inputForm" modelAttribute="projectManage" action="${ctx}/filemanagement/projectManage/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>	
		<div class="panel-group" id="accordion">
		<div class="panel-heading">
			<h4 class="panel-title">
				<a data-toggle="collapse" data-parent="#accordion" 
				   href="#collapseOne">
					<i class="glyphicon glyphicon-chevron-up"></i> 项目基本信息
				</a>
			</h4>
		</div>
		
		<div id="collapseOne" class="panel-collapse collapse in">
			<div class="panel-body">
		<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
			<form:hidden path="createusername" value="${fns:getUser().name }"/>
		   <tbody>
				<tr>
					<td class="width-15 active" colspan="4"><label class="pull-right"><font color="red">*</font>项目名称：</label></td>
					<td colspan="16">
						<form:input path="projectname" htmlEscape="false" maxlength="100"    class="form-control required"/>
					</td>
					
				</tr>
				<tr>
					<td class="width-15 active" colspan="4"><label class="pull-right"><font color="red">*</font>项目编号：</label></td>
					<td class="width-35" colspan="6">
						<form:input path="projectno" htmlEscape="false" maxlength="100"    class="form-control required"/>
					</td>
					<td class="width-15 active" colspan="4"><label class="pull-right"><font color="red">*</font>项目类别：</label></td>
					<td class="width-35" colspan="6">
						<form:select path="projectcategory" class="form-control required">
							<form:option value="" label=""/>
							<form:options items="${fns:getDictList('projectcategory')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
						</form:select>
					</td>
				</tr>
				<tr>
					<td class="width-15 active" colspan="4" ><label class="pull-right">项目预算收款：</label></td>
					<td colspan="3">
						<form:input path="projectgather" htmlEscape="false" onkeyup='clearNoNum(this)'   class="form-control  number"/>
					</td>
					
					<td class="active" colspan="2" style="width:8%"><label class="pull-right"><font color="red">*</font>币种：</label></td>
					<td class="width-15" colspan="3">
						<form:select path="gathercurrencytype"  class="form-control required">
							<form:option value="" label=""/>
							<form:options items="${fns:getDictList('currency')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
						</form:select>
					</td>
					<td class="width-15 active" colspan="3"><label class="pull-right"><font color="red">*</font>计划开始时间：</label></td>
					<td colspan="5">
						<input id="planstartdate" name="planstartdate" type="text" maxlength="20" class="laydate-icon form-control layer-date required"
							value="<fmt:formatDate value="${projectManage.planstartdate}" pattern="yyyy-MM-dd"/>"/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active" colspan="4"><label class="pull-right">项目预算付款：</label></td>
					<td colspan="3">
						<form:input path="projectpayment" htmlEscape="false"  onkeyup='clearNoNum(this)'  class="form-control  number"/>
					</td>
					<td class="active" colspan="2" style="width:8%"><label class="pull-right"><font color="red">*</font>币种：</label></td>
					<td class="width-15" colspan="3">
						<form:select path="paymentcurrencytype"  class="form-control required">
							<form:option value="" label=""/>
							<form:options items="${fns:getDictList('currency')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
						</form:select>
					</td>
					<td class="width-15 active" colspan="3"><label class="pull-right"><font color="red">*</font>计划结束时间：</label></td>
					<td colspan="5">
						<input id="planenddate" name="planenddate" type="text" maxlength="20" class="laydate-icon form-control layer-date required"
							value="<fmt:formatDate value="${projectManage.planenddate}" pattern="yyyy-MM-dd"/>"/>
					</td>
				</tr>
				<tr>
					
					<td class="width-15 active" colspan="4"><label class="pull-right">备用1：</label></td>
					<td class="width-35" colspan="6">
						<form:input path="backupone" htmlEscape="false" maxlength="200"    class="form-control "/>
					</td>
					<td class="width-15 active" colspan="4"><label class="pull-right">备用2：</label></td>
					<td class="width-35" colspan="6">
						<form:input path="backuptwo" htmlEscape="false" maxlength="200"    class="form-control "/>
					</td>
				</tr>
				<tr>
					
					<td class="width-15 active" colspan="4"><label class="pull-right"><font color="red">*</font>负责人姓名：</label></td>
					<td class="width-35" colspan="16">
						<%-- <form:input path="officalname" readonly="true" htmlEscape="false" maxlength="30"    class="form-control required"/> --%>
						<sys:treeselect id="officalname" name="officalid" value="${projectManage.officalid}" labelName="officalname" labelValue="${projectManage.officalname}"
								title="用户" url="/sys/office/treeData?type=3" isAll="true" cssClass="form-control required" notAllowSelectParent="true"/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active" colspan="4"><label class="pull-right">项目状态：</label></td>
					<td class="width-35" colspan="16">
						<form:select path="state" class="form-control required" style="width:235px">
							<form:options items="${fns:getDictList('projectstate')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
						</form:select>
					</td>
				</tr>
		 	</tbody>
		</table>
		</div>
		</div>
		</div>
		<div class="tabs-container">
            <ul class="nav nav-tabs">
				<li class="active"><a data-toggle="tab" href="#tab-1" aria-expanded="true">项目附件表</a>
                </li>
				<li class=""><a data-toggle="tab" href="#tab-2" aria-expanded="false">项目备忘</a>
                </li>
				<li class=""><a data-toggle="tab" href="#tab-3" aria-expanded="false">项目时间表</a>
                </li>
            </ul>
            <div class="tab-content">
				<div id="tab-1" class="tab-pane active">
			<a class="btn btn-white btn-sm" onclick="addRow('#projectAttachmentList', projectAttachmentRowIdx, projectAttachmentTpl);projectAttachmentRowIdx = projectAttachmentRowIdx + 1;" title="新增"><i class="fa fa-plus"></i> 新增</a>
			<table id="contentTable" class="table table-striped table-bordered table-condensed">
				<thead>
					<tr>
						<th class="hide"></th>
						<th>附件名称</th>
						<th>附件类别</th>
						<th>文件路径</th>
						<th>附件说明</th>
						<th width="10">&nbsp;</th>
					</tr>
				</thead>
				<tbody id="projectAttachmentList">
				</tbody>
			</table>
			<script type="text/template" id="projectAttachmentTpl">//<!--
				<tr id="projectAttachmentList{{idx}}">
					<td class="hide">
						<input id="projectAttachmentList{{idx}}_id" name="projectAttachmentList[{{idx}}].id" type="hidden" value="{{row.id}}"/>
						<input id="projectAttachmentList{{idx}}_delFlag" name="projectAttachmentList[{{idx}}].delFlag" type="hidden" value="0"/>
					</td>
					
					<td>
						<input id="projectAttachmentList{{idx}}_attachmentname" name="projectAttachmentList[{{idx}}].attachmentname" type="text" value="{{row.attachmentname}}" maxlength="50"    class="form-control required"/>
					</td>
					
					
					<td>
						<select id="projectAttachmentList{{idx}}_attachmentcategory" name="projectAttachmentList[{{idx}}].attachmentcategory" data-value="{{row.attachmentcategory}}" class="form-control m-b  required">
							<option value=""></option>
							<c:forEach items="${fns:getDictList('projectattachment')}" var="dict">
								<option value="${dict.value}">${dict.label}</option>
							</c:forEach>
						</select>
					</td>
					
					
					<td>
						<input id="projectAttachmentList{{idx}}_fileurl" name="projectAttachmentList[{{idx}}].fileurl" type="text" value="{{row.fileurl}}"    readonly="true"  class="form-control required" placeholder="点击上传附件" onclick="commonFileUpload('projectAttachmentList{{idx}}_fileurl');" />
					</td>
					
					
					<td>
						<input id="projectAttachmentList{{idx}}_attachmentmemo" name="projectAttachmentList[{{idx}}].attachmentmemo" type="text" value="{{row.attachmentmemo}}" maxlength="500"    class="form-control "/>
					</td>
					
					<td class="text-center" width="10">
						{{#delBtn}}<span class="close" onclick="delRow(this, '#projectAttachmentList{{idx}}')" title="删除">&times;</span>{{/delBtn}}
					</td>
				</tr>//-->
			</script>
			<script type="text/javascript">
				var projectAttachmentRowIdx = 0, projectAttachmentTpl = $("#projectAttachmentTpl").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g,"");
				$(document).ready(function() {
					var data = ${fns:toJson(projectManage.projectAttachmentList)};
					for (var i=0; i<data.length; i++){
						addRow('#projectAttachmentList', projectAttachmentRowIdx, projectAttachmentTpl, data[i]);
						projectAttachmentRowIdx = projectAttachmentRowIdx + 1;
					}
				});
			</script>
			</div>
				<div id="tab-2" class="tab-pane">
			<a class="btn btn-white btn-sm" onclick="addRow('#projectMemoList', projectMemoRowIdx, projectMemoTpl);projectMemoRowIdx = projectMemoRowIdx + 1;" title="新增"><i class="fa fa-plus"></i> 新增</a>
			<table id="contentTable" class="table table-striped table-bordered table-condensed">
				<thead>
					<tr>
						<th class="hide"></th>
						<th>说明事项</th>
						<th>内容</th>
						<th width="10">&nbsp;</th>
					</tr>
				</thead>
				<tbody id="projectMemoList">
				</tbody>
			</table>
			<script type="text/template" id="projectMemoTpl">//<!--
				<tr id="projectMemoList{{idx}}">
					<td class="hide">
						<input id="projectMemoList{{idx}}_id" name="projectMemoList[{{idx}}].id" type="hidden" value="{{row.id}}"/>
						<input id="projectMemoList{{idx}}_delFlag" name="projectMemoList[{{idx}}].delFlag" type="hidden" value="0"/>
					</td>
					
					<td>
						<input id="projectMemoList{{idx}}_matter" name="projectMemoList[{{idx}}].matter" type="text" value="{{row.matter}}" maxlength="200"    class="form-control "/>
					</td>
					
					
					<td>
						<input id="projectMemoList{{idx}}_content" name="projectMemoList[{{idx}}].content" type="text" value="{{row.content}}" maxlength="500"    class="form-control "/>
					</td>
					
					<td class="text-center" width="10">
						{{#delBtn}}<span class="close" onclick="delRow(this, '#projectMemoList{{idx}}')" title="删除">&times;</span>{{/delBtn}}
					</td>
				</tr>//-->
			</script>
			<script type="text/javascript">
				var projectMemoRowIdx = 0, projectMemoTpl = $("#projectMemoTpl").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g,"");
				$(document).ready(function() {
					var data = ${fns:toJson(projectManage.projectMemoList)};
					for (var i=0; i<data.length; i++){
						addRow('#projectMemoList', projectMemoRowIdx, projectMemoTpl, data[i]);
						projectMemoRowIdx = projectMemoRowIdx + 1;
					}
				});
			</script>
			</div>
				<div id="tab-3" class="tab-pane">
			<a class="btn btn-white btn-sm" onclick="addRow('#projectTimeList', projectTimeRowIdx, projectTimeTpl);projectTimeRowIdx = projectTimeRowIdx + 1;" title="新增"><i class="fa fa-plus"></i> 新增</a>
			<table id="contentTable" class="table table-striped table-bordered table-condensed">
				<thead>
					<tr>
						<th class="hide"></th>
						<th>事项</th>
						<th>计划开始日期</th>
						<th>计划结束时间</th>
						<th>备注</th>
						<th width="10">&nbsp;</th>
					</tr>
				</thead>
				<tbody id="projectTimeList">
				</tbody>
			</table>
			<script type="text/template" id="projectTimeTpl">//<!--
				<tr id="projectTimeList{{idx}}">
					<td class="hide">
						<input id="projectTimeList{{idx}}_id" name="projectTimeList[{{idx}}].id" type="hidden" value="{{row.id}}"/>
						<input id="projectTimeList{{idx}}_delFlag" name="projectTimeList[{{idx}}].delFlag" type="hidden" value="0"/>
					</td>
					
					<td>
						<input id="projectTimeList{{idx}}_matter" name="projectTimeList[{{idx}}].matter" type="text" value="{{row.matter}}" maxlength="200"    class="form-control required"/>
					</td>
					
					
					<td>
						<input id="projectTimeList{{idx}}_planstarttime" name="projectTimeList[{{idx}}].planstarttime" type="text" readonly="readonly" maxlength="20" class="laydate-icon form-control layer-date  required"
							value="{{row.planstarttime}}" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
					</td>
					
					
					<td>
						<input id="projectTimeList{{idx}}_planendtime" name="projectTimeList[{{idx}}].planendtime" type="text" readonly="readonly" maxlength="20" class="laydate-icon form-control layer-date  required"
							value="{{row.planendtime}}" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
					</td>
					
					
					<td>
						<input id="projectTimeList{{idx}}_memo" name="projectTimeList[{{idx}}].memo" type="text" value="{{row.memo}}" maxlength="500"    class="form-control "/>
					</td>
					
					<td class="text-center" width="10">
						{{#delBtn}}<span class="close" onclick="delRow(this, '#projectTimeList{{idx}}')" title="删除">&times;</span>{{/delBtn}}
					</td>
				</tr>//-->
			</script>
			<script type="text/javascript">
				var projectTimeRowIdx = 0, projectTimeTpl = $("#projectTimeTpl").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g,"");
				$(document).ready(function() {
					var data = ${fns:toJson(projectManage.projectTimeList)};
					for (var i=0; i<data.length; i++){
						addRow('#projectTimeList', projectTimeRowIdx, projectTimeTpl, data[i]);
						projectTimeRowIdx = projectTimeRowIdx + 1;
					}
				});
			</script>
			</div>
		</div>
		</div>
	</form:form>
</body>
</html>