<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>信息管理</title>
	<meta name="decorator" content="default"/>
	<%@ include file="/webpage/include/common.jsp"%>
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
			            elem: '#signdate', //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
			            event: 'focus' //响应事件。如果没有传入event，则按照默认的click
			        });
					laydate({
			            elem: '#effectivedate', //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
			            event: 'focus' //响应事件。如果没有传入event，则按照默认的click
			        });
					laydate({
			            elem: '#plancompletiondate', //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
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
					
					
				$("#fundnatureid").change(function(){
					$("#contractpartyid").val("");
					$("#contractparty").val("");
				});
				$("#selectcontractparty").click(function(){
					if($('#fundnatureid option:selected') .val() == ''){
						layer.alert('请先选择资金性质', {icon: 3});
						return false;
					}else if($('#fundnatureid option:selected') .val() == '1'){
						var url = "${ctx}/filemanagement/customer/selectlist?usertype=0";
						layer.open({
						    type: 2,  
						    area: ['800px', '450px'],
						    title: '选择合同对方',
					        maxmin: true, //开启最大化最小化按钮
						    content: url 
						}); 
						return false;
					}else if($('#fundnatureid option:selected') .val() == '2'){
						var url = "${ctx}/filemanagement/customer/selectlist?usertype=1";
						layer.open({
						    type: 2,  
						    area: ['800px', '450px'],
						    title: '选择合同对方',
					        maxmin: true, //开启最大化最小化按钮
						    content: url 
						}); 
						return false;
					}
					
				});
				$("#selectproject").click(function(){
					var url = "${ctx}/filemanagement/projectManage/selectlist";
					layer.open({
					    type: 2,  
					    area: ['800px', '450px'],
					    title: '选择所属项目',
				        maxmin: true, //开启最大化最小化按钮
					    content: url 
					}); 
					return false;
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
		function addRowOne(list, idx, tpl, row){
			if($(list).find("tr").length == 1){
				layer.alert('无法添加多条计划资金！', {icon: 0});
				return;
			}else{
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
	<style type="text/css">
	.form-control[disabled], .form-control[readonly], fieldset[disabled] .form-control {
	    background-color: #fbfbfb;
	    opacity: 1;
	}
	</style>
</head>
<body class="hideScroll">
	<form:form id="inputForm" modelAttribute="contractManager" action="${ctx}/contractmanager/contractManager/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
		
		<div class="panel-group" id="accordion">
		<div class="panel-heading">
			<h4 class="panel-title">
				<a data-toggle="collapse" data-parent="#accordion" 
				   href="#collapseOne">
					<i class="glyphicon glyphicon-chevron-up"></i> 合同基本信息
				</a>
			</h4>
		</div>
		<div id="collapseOne" class="panel-collapse collapse in">
			<div class="panel-body">
		<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
		   <tbody>
				<tr>
					<td class="active" colspan="4"><label class="pull-right">合同名称：</label></td>
					<td colspan="16">
						<form:input path="contractname" htmlEscape="false"    class="form-control required" maxlength="20"/>
					</td>
					
				</tr>
				<tr>
					<td class="width-15 active" colspan="4"><label class="pull-right">合同编号：</label></td>
					<td class="width-35" colspan="6">
						<form:input path="contractno" htmlEscape="false"    class="form-control required" maxlength="20"/>
					</td>
					<td class="width-15 active" colspan="4"><label class="pull-right">签订日期：</label></td>
					<td class="width-35" colspan="6">
						<input id="signdate" name="signdate" type="text" maxlength="20" class="laydate-icon form-control layer-date required"
							value="<fmt:formatDate value="${contractManager.signdate}" pattern="yyyy-MM-dd"/>"/>
					</td>
				</tr>
				<tr>
					<td class="width-10 active" colspan="2"><label class="pull-right">合同类别：</label></td>
					<td colspan="3">
						<form:select path="contractclassid" class="form-control required">
							<form:option value="" label=""/>
							<form:options items="${fns:getDictList('contractmanager_contractclass')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
						</form:select>
					</td>
					<td class="active" colspan="2"><label class="pull-right">资金性质：</label></td>
					<td class="width-15" colspan="3">
						<form:select path="fundnatureid" class="form-control required">
							<form:option value="" label=""/>
							<form:options items="${fns:getDictList('fundnature')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
						</form:select>
					</td>
					<td class="active" colspan="4"><label class="pull-right">生效日期：</label></td>
					<td colspan="6">
						<input id="effectivedate" name="effectivedate" type="text" maxlength="20" class="laydate-icon form-control layer-date required"
							value="<fmt:formatDate value="${contractManager.effectivedate}" pattern="yyyy-MM-dd"/>"/>
					</td>
				</tr>
				<tr>
					<td class="active" colspan="2"><label class="pull-right">合同金额：</label></td>
					<td colspan="3">
						<form:input path="contractamount" htmlEscape="false"  onkeyup='clearNoNum(this)'  class="form-control required number" maxlength="20"/>
					</td>
					<td class="active" colspan="2"><label class="pull-right">币种：</label></td>
					<td colspan="3">
						<form:select path="currencyid" class="form-control required">
							<form:option value="" label=""/>
							<form:options items="${fns:getDictList('currency')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
						</form:select>
					</td>
					<td class="width-15 active" colspan="4"><label class="pull-right">计划完成日期：</label></td>
					<td class="width-35" colspan="6">
						<input id="plancompletiondate" name="plancompletiondate" type="text" maxlength="20" class="laydate-icon form-control layer-date "
							value="<fmt:formatDate value="${contractManager.plancompletiondate}" pattern="yyyy-MM-dd"/>"/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active" colspan="4"><label class="pull-right">印花税额：</label></td>
					<td class="width-35" colspan="6">
						<form:input path="stamptax" htmlEscape="false"  style="width:70%;" onkeyup='clearNoNum(this)' class="form-control required number" maxlength="20"/>元（人民币）
					</td>
					<td class="width-15 active" colspan="4"><label class="pull-right">所属机构：</label></td>
					<td class="width-35" colspan="6">
						<form:select path="affiliationid" class="form-control required">
							<form:option value="" label=""/>
							<form:options items="${fns:getDictList('contractmanager_affiliation')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
						</form:select>
					</td>
				</tr>
				<tr>
					<td class="width-15 active" colspan="4"><label class="pull-right">备注1：</label></td>
					<td class="width-35" colspan="6">
						<form:input path="remark1" htmlEscape="false"    class="form-control " maxlength="200"/>
					</td>
					<td class="width-15 active" colspan="4"><label class="pull-right">备注2：</label></td>
					<td class="width-35" colspan="6">
						<form:input path="remark2" htmlEscape="false"    class="form-control " maxlength="200"/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active" colspan="4"><label class="pull-right">合同对方：</label></td>
					<td colspan="16">
					    <form:hidden path="contractpartyid"/>
						<form:input path="contractparty" htmlEscape="false"  style="width:90%;" readonly="true"  class="form-control "/>
						<button id="selectcontractparty" class="btn btn-info" >选择</button>
					</td>
				</tr>
				<tr>
					<td class="width-15 active" colspan="4"><label class="pull-right">所属项目：</label></td>
					<td colspan="16">
					    <form:hidden path="projectid"/>
						<form:input path="project" htmlEscape="false"  style="width:90%;" readonly="true"  class="form-control "/>
						<button id="selectproject" class="btn btn-info" >选择</button>
					</td>
				</tr>
				<tr>
					<td class="width-15 active" colspan="4"><label class="pull-right">负责人：</label></td>
					<td colspan="16">
						<sys:treeselect id="responsibleperson" name="responsiblepersonid" value="${contractManager.responsiblepersonid}" labelName="responsibleperson" labelValue="${contractManager.responsibleperson}"
								title="用户" url="/sys/office/treeData?type=3" isAll="true" cssClass="form-control" notAllowSelectParent="true" />
					</td>
				</tr>
				
				<tr>
					<td class="width-15 active" colspan="4"><label class="pull-right">合同状态：</label></td>
					<td class="width-35" colspan="6">
						<form:select path="status" class="form-control required">
							<form:options items="${fns:getDictList('contractmanager_contractstatus')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
						</form:select>
					</td>
					<td class="width-15" colspan="10"></td>
				</tr>

		 	</tbody>
		</table>
		</div>
		</div>
		</div>
		<div class="tabs-container">
            <ul class="nav nav-tabs">
				<li class="active"><a data-toggle="tab" href="#tab-5" aria-expanded="true">合同文本</a>
                </li>
                <li class=""><a data-toggle="tab" href="#tab-4" aria-expanded="false">合同标的</a>
                </li>
                <li class=""><a data-toggle="tab" href="#tab-3" aria-expanded="false">计划资金</a>
                </li>
                <li class=""><a data-toggle="tab" href="#tab-7" aria-expanded="false">实际资金</a>
                </li>
                <li class=""><a data-toggle="tab" href="#tab-6" aria-expanded="false">资金条款</a>
                </li>
                <li class=""><a data-toggle="tab" href="#tab-8" aria-expanded="false">发票</a>
                </li>
                <li class=""><a data-toggle="tab" href="#tab-2" aria-expanded="false">合同备忘</a>
                </li>
                <li class=""><a data-toggle="tab" href="#tab-1" aria-expanded="false">合同附件</a>
                </li>
                
                
                
            </ul>
            <div class="tab-content">
				<div id="tab-1" class="tab-pane ">
			<a class="btn btn-white btn-sm" onclick="addRow('#contractFileList', contractFileRowIdx, contractFileTpl);contractFileRowIdx = contractFileRowIdx + 1;" title="新增"><i class="fa fa-plus"></i> 新增</a>
			<table id="contentTable" class="table table-striped table-bordered table-condensed">
				<thead>
					<tr>
						<th class="hide"></th>
						<th>附件名称</th>
						<th>附件类型id</th>
						<th>附件文件</th>
						<th>附件说明</th>
						<th width="10">&nbsp;</th>
					</tr>
				</thead>
				<tbody id="contractFileList">
				</tbody>
			</table>
			<script type="text/template" id="contractFileTpl">//<!--
				<tr id="contractFileList{{idx}}">
					<td class="hide">
						<input id="contractFileList{{idx}}_id" name="contractFileList[{{idx}}].id" type="hidden" value="{{row.id}}"/>
						<input id="contractFileList{{idx}}_delFlag" name="contractFileList[{{idx}}].delFlag" type="hidden" value="0"/>
					</td>
					
					
					<td>
						<input id="contractFileList{{idx}}_filename" name="contractFileList[{{idx}}].filename" type="text" value="{{row.filename}}"    class="form-control required" maxlength="50"/>
					</td>
					
					
					<td>
						<select id="contractFileList{{idx}}_fileclassid" name="contractFileList[{{idx}}].fileclassid" data-value="{{row.fileclassid}}" class="form-control m-b  required">
							<option value=""></option>
							<c:forEach items="${fns:getDictList('contractmanager_fileclass')}" var="dict">
								<option value="${dict.value}">${dict.label}</option>
							</c:forEach>
						</select>
					</td>
					
					
					<td>
						<input id="contractFileList{{idx}}_fileurl" name="contractFileList[{{idx}}].fileurl" type="text" value="{{row.fileurl}}"    class="form-control required" readonly="true" placeholder="点击上传附件" onclick="commonFileUpload('contractFileList{{idx}}_fileurl');"  maxlength="500"/>
					</td>
					
					
					<td>
						<input id="contractFileList{{idx}}_fileremark" name="contractFileList[{{idx}}].fileremark" type="text" value="{{row.fileremark}}"    class="form-control " maxlength="200" />
					</td>
					
					<td class="text-center" width="10">
						{{#delBtn}}<span class="close" onclick="delRow(this, '#contractFileList{{idx}}')" title="删除">&times;</span>{{/delBtn}}
					</td>
				</tr>//-->
			</script>
			<script type="text/javascript">
				var contractFileRowIdx = 0, contractFileTpl = $("#contractFileTpl").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g,"");
				$(document).ready(function() {
					var data = ${fns:toJson(contractManager.contractFileList)};
					for (var i=0; i<data.length; i++){
						addRow('#contractFileList', contractFileRowIdx, contractFileTpl, data[i]);
						contractFileRowIdx = contractFileRowIdx + 1;
					}
				});
			</script>
			</div>
				<div id="tab-2" class="tab-pane">
			<a class="btn btn-white btn-sm" onclick="addRow('#contractNoteList', contractNoteRowIdx, contractNoteTpl);contractNoteRowIdx = contractNoteRowIdx + 1;" title="新增"><i class="fa fa-plus"></i> 新增</a>
			<table id="contentTable" class="table table-striped table-bordered table-condensed">
				<thead>
					<tr>
						<th class="hide"></th>
						<th>说明事项</th>
						<th>内容</th>
						<th width="10">&nbsp;</th>
					</tr>
				</thead>
				<tbody id="contractNoteList">
				</tbody>
			</table>
			<script type="text/template" id="contractNoteTpl">//<!--
				<tr id="contractNoteList{{idx}}">
					<td class="hide">
						<input id="contractNoteList{{idx}}_id" name="contractNoteList[{{idx}}].id" type="hidden" value="{{row.id}}"/>
						<input id="contractNoteList{{idx}}_delFlag" name="contractNoteList[{{idx}}].delFlag" type="hidden" value="0"/>
					</td>
					
					
					<td>
						<input id="contractNoteList{{idx}}_note" name="contractNoteList[{{idx}}].note" type="text" value="{{row.note}}"    class="form-control required" maxlength="20"/>
					</td>
					
					
					<td>
						<input id="contractNoteList{{idx}}_content" name="contractNoteList[{{idx}}].content" type="text" value="{{row.content}}"    class="form-control " maxlength="200"/>
					</td>
					
					<td class="text-center" width="10">
						{{#delBtn}}<span class="close" onclick="delRow(this, '#contractNoteList{{idx}}')" title="删除">&times;</span>{{/delBtn}}
					</td>
				</tr>//-->
			</script>
			<script type="text/javascript">
				var contractNoteRowIdx = 0, contractNoteTpl = $("#contractNoteTpl").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g,"");
				$(document).ready(function() {
					var data = ${fns:toJson(contractManager.contractNoteList)};
					for (var i=0; i<data.length; i++){
						addRow('#contractNoteList', contractNoteRowIdx, contractNoteTpl, data[i]);
						contractNoteRowIdx = contractNoteRowIdx + 1;
					}
				});
			</script>
			</div>
				<div id="tab-3" class="tab-pane">
			<a class="btn btn-white btn-sm" onclick="addRowOne('#contractPlanfundList', contractPlanfundRowIdx, contractPlanfundTpl);contractPlanfundRowIdx = contractPlanfundRowIdx + 1;" title="新增"><i class="fa fa-plus"></i> 新增</a>
			<table id="contentTable" class="table table-striped table-bordered table-condensed">
				<thead>
					<tr>
						<th class="hide"></th>
						<th>名称</th>
						<th>计划完成日期</th>
						<th>金额</th>
						<th>结算方式</th>
						<th>备注</th>
						<th width="10">&nbsp;</th>
					</tr>
				</thead>
				<tbody id="contractPlanfundList">
				</tbody>
			</table>
			<script type="text/template" id="contractPlanfundTpl">//<!--
				<tr id="contractPlanfundList{{idx}}">
					<td class="hide">
						<input id="contractPlanfundList{{idx}}_id" name="contractPlanfundList[{{idx}}].id" type="hidden" value="{{row.id}}"/>
						<input id="contractPlanfundList{{idx}}_delFlag" name="contractPlanfundList[{{idx}}].delFlag" type="hidden" value="0"/>
					</td>

					
					<td>
						<input id="contractPlanfundList{{idx}}_planfundname" name="contractPlanfundList[{{idx}}].planfundname" type="text" value="{{row.planfundname}}"    class="form-control required" maxlength="20"/>
					</td>
					
					
					<td>
						<input id="contractPlanfundList{{idx}}_plancompletiondate" name="contractPlanfundList[{{idx}}].plancompletiondate" type="text" readonly="readonly" maxlength="20" class="laydate-icon form-control layer-date  "
							value="{{row.plancompletiondate}}" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
					</td>
					
					
					<td>
						<input id="contractPlanfundList{{idx}}_money" name="contractPlanfundList[{{idx}}].money" type="text" value="{{row.money}}"  onkeyup='clearNoNum(this)'  class="form-control required number" maxlength="20"/>
					</td>
					
					
					<td>
						<select id="contractPlanfundList{{idx}}_settlementid" name="contractPlanfundList[{{idx}}].settlementid" data-value="{{row.settlementid}}" class="form-control m-b  ">
							<option value=""></option>
							<c:forEach items="${fns:getDictList('contractmanager_settlement')}" var="dict">
								<option value="${dict.value}">${dict.label}</option>
							</c:forEach>
						</select>
					</td>
					
					
					<td>
						<input id="contractPlanfundList{{idx}}_remark" name="contractPlanfundList[{{idx}}].remark" type="text" value="{{row.remark}}"    class="form-control " maxlength="200"/>
					</td>
					
					<td class="text-center" width="10">
						{{#delBtn}}<span class="close" onclick="delRow(this, '#contractPlanfundList{{idx}}')" title="删除">&times;</span>{{/delBtn}}
					</td>
				</tr>//-->
			</script>
			<script type="text/javascript">
				var contractPlanfundRowIdx = 0, contractPlanfundTpl = $("#contractPlanfundTpl").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g,"");
				$(document).ready(function() {
					var data = ${fns:toJson(contractManager.contractPlanfundList)};
					for (var i=0; i<data.length; i++){
						addRow('#contractPlanfundList', contractPlanfundRowIdx, contractPlanfundTpl, data[i]);
						contractPlanfundRowIdx = contractPlanfundRowIdx + 1;
					}
				});
			</script>
			</div>
			<div id="tab-6" class="tab-pane">
			<form class="form-horizontal">
				<p>&nbsp;</p>
			 	<label class="col-sm-2 control-label">资金条款：</label>
			 	<div class="col-sm-9"><textarea  rows="6" id="fundclause"  name="fundclause" class="form-control " maxlength="200">${contractManager.fundclause}</textarea></div>
			</form>
			</div>
				<div id="tab-4" class="tab-pane">
			<a class="btn btn-white btn-sm" onclick="addRow('#contractSubjectList', contractSubjectRowIdx, contractSubjectTpl);contractSubjectRowIdx = contractSubjectRowIdx + 1;" title="新增"><i class="fa fa-plus"></i> 新增</a>
			<table id="contentTable" class="table table-striped table-bordered table-condensed">
				<thead>
					<tr>
						<th class="hide"></th>
						<th>名称</th>
						<th>型号</th>
						<th>规格</th>
						<th>数量</th>
						<th>单位</th>
						<th>单价</th>
						<th>小计</th>
						<th>备注</th>
						<th width="10">&nbsp;</th>
					</tr>
				</thead>
				<tbody id="contractSubjectList">
				</tbody>
			</table>
			<script type="text/template" id="contractSubjectTpl">//<!--
				<tr id="contractSubjectList{{idx}}">
					<td class="hide">
						<input id="contractSubjectList{{idx}}_id" name="contractSubjectList[{{idx}}].id" type="hidden" value="{{row.id}}"/>
						<input id="contractSubjectList{{idx}}_delFlag" name="contractSubjectList[{{idx}}].delFlag" type="hidden" value="0"/>
					</td>
					
					
					<td>
						<input id="contractSubjectList{{idx}}_subjectname" name="contractSubjectList[{{idx}}].subjectname" type="text" value="{{row.subjectname}}"    class="form-control required" maxlength="20"/>
					</td>
					
					
					<td>
						<input id="contractSubjectList{{idx}}_model" name="contractSubjectList[{{idx}}].model" type="text" value="{{row.model}}"    class="form-control " maxlength="20"/>
					</td>
					
					
					<td>
						<input id="contractSubjectList{{idx}}_specification" name="contractSubjectList[{{idx}}].specification" type="text" value="{{row.specification}}"    class="form-control " maxlength="20"/>
					</td>
					
					
					<td>
						<input id="contractSubjectList{{idx}}_quantity" name="contractSubjectList[{{idx}}].quantity" type="text" value="{{row.quantity}}"    class="form-control " maxlength="20"/>
					</td>
					
					
					<td>
						<input id="contractSubjectList{{idx}}_unit" name="contractSubjectList[{{idx}}].unit" type="text" value="{{row.unit}}"    class="form-control " maxlength="20"/>
					</td>
					
					
					<td>
						<input id="contractSubjectList{{idx}}_unitprice" name="contractSubjectList[{{idx}}].unitprice" type="text" value="{{row.unitprice}}"  onkeyup='clearNoNum(this)'  class="form-control number " maxlength="20"/>
					</td>
					
					
					<td>
						<input id="contractSubjectList{{idx}}_subtotal" name="contractSubjectList[{{idx}}].subtotal" type="text" value="{{row.subtotal}}"    class="form-control " maxlength="20"/>
					</td>
					
					
					<td>
						<input id="contractSubjectList{{idx}}_remark" name="contractSubjectList[{{idx}}].remark" type="text" value="{{row.remark}}"    class="form-control " maxlength="200"/>
					</td>

					<td class="text-center" width="10">
						{{#delBtn}}<span class="close" onclick="delRow(this, '#contractSubjectList{{idx}}')" title="删除">&times;</span>{{/delBtn}}
					</td>
				</tr>//-->
			</script>
			<script type="text/javascript">
				var contractSubjectRowIdx = 0, contractSubjectTpl = $("#contractSubjectTpl").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g,"");
				$(document).ready(function() {
					var data = ${fns:toJson(contractManager.contractSubjectList)};
					for (var i=0; i<data.length; i++){
						addRow('#contractSubjectList', contractSubjectRowIdx, contractSubjectTpl, data[i]);
						contractSubjectRowIdx = contractSubjectRowIdx + 1;
					}
				});
			</script>
			</div>
				<div id="tab-5" class="tab-pane active">
			<a class="btn btn-white btn-sm" onclick="addRow('#contractTextList', contractTextRowIdx, contractTextTpl);contractTextRowIdx = contractTextRowIdx + 1;" title="新增"><i class="fa fa-plus"></i> 新增</a>
			<table id="contentTable" class="table table-striped table-bordered table-condensed">
				<thead>
					<tr>
						<th class="hide"></th>
						<!-- <th>备注信息</th> -->
						<th>文本名称</th>
						<th>文本类别</th>
						<th>文本文件</th>
						<th>文本说明</th>
						<th width="10">&nbsp;</th>
					</tr>
				</thead>
				<tbody id="contractTextList">
				</tbody>
			</table>
			<script type="text/template" id="contractTextTpl">//<!--
				<tr id="contractTextList{{idx}}">
					<td class="hide">
						<input id="contractTextList{{idx}}_id" name="contractTextList[{{idx}}].id" type="hidden" value="{{row.id}}"/>
						<input id="contractTextList{{idx}}_delFlag" name="contractTextList[{{idx}}].delFlag" type="hidden" value="0"/>
					</td>
					
<!-- 
					<td>
						<textarea id="contractTextList{{idx}}_remarks" name="contractTextList[{{idx}}].remarks" rows="4"    class="form-control ">{{row.remarks}}</textarea>
					</td>
 -->					
					
					<td>
						<input id="contractTextList{{idx}}_textname" name="contractTextList[{{idx}}].textname" type="text" value="{{row.textname}}"    class="form-control required" maxlength="20"/>
					</td>
					
					
					<td>
						<select id="contractTextList{{idx}}_textclassid" name="contractTextList[{{idx}}].textclassid" data-value="{{row.textclassid}}" class="form-control m-b  required">
							<option value=""></option>
							<c:forEach items="${fns:getDictList('contractmanager_textclass')}" var="dict">
								<option value="${dict.value}">${dict.label}</option>
							</c:forEach>
						</select>
					</td>
					
					
					<td>
						<input id="contractTextList{{idx}}_texturl" name="contractTextList[{{idx}}].texturl" type="text" value="{{row.texturl}}"    class="form-control required" readonly="true" placeholder="点击上传附件" onclick="commonFileUpload('contractTextList{{idx}}_texturl');" />
					</td>
					
					
					<td>
						<input id="contractTextList{{idx}}_textremark" name="contractTextList[{{idx}}].textremark" type="text" value="{{row.textremark}}"    class="form-control " maxlength="200"/>
					</td>
					
					<td class="text-center" width="10">
						{{#delBtn}}<span class="close" onclick="delRow(this, '#contractTextList{{idx}}')" title="删除">&times;</span>{{/delBtn}}
					</td>
				</tr>//-->
			</script>
			<script type="text/javascript">
				var contractTextRowIdx = 0, contractTextTpl = $("#contractTextTpl").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g,"");
				$(document).ready(function() {
					var data = ${fns:toJson(contractManager.contractTextList)};
					for (var i=0; i<data.length; i++){
						addRow('#contractTextList', contractTextRowIdx, contractTextTpl, data[i]);
						contractTextRowIdx = contractTextRowIdx + 1;
					}
				});
			</script>
			</div>
			
			<div id="tab-7" class="tab-pane">
			<a class="btn btn-white btn-sm" onclick="addRow('#contractActualFundsList', contractActualFundsRowIdx, contractActualFundsTpl);contractActualFundsRowIdx = contractActualFundsRowIdx + 1;" title="新增"><i class="fa fa-plus"></i> 新增</a>
			<table id="contentTable" class="table table-striped table-bordered table-condensed">
				<thead>
					<tr>
						<th class="hide"></th>
						<th>资金金额</th>
						<th>票据号码</th>
						<th>结算方式</th>
						<th>结算日期</th>
						<th>备注1</th>
						<th>备注2</th>
						<th width="10">&nbsp;</th>
					</tr>
				</thead>
				<tbody id="contractActualFundsList">
				</tbody>
			</table>
			<script type="text/template" id="contractActualFundsTpl">//<!--
				<tr id="contractActualFundsList{{idx}}">
					<td class="hide">
						<input id="contractActualFundsList{{idx}}_id" name="contractActualFundsList[{{idx}}].id" type="hidden" value="{{row.id}}"/>
						<input id="contractActualFundsList{{idx}}_delFlag" name="contractActualFundsList[{{idx}}].delFlag" type="hidden" value="0"/>
					</td>
					
					<td>
						<input id="contractActualFundsList{{idx}}_money" name="contractActualFundsList[{{idx}}].money" type="text" value="{{row.money}}"  onkeyup='clearNoNum(this)'  class="form-control required number" maxlength="20"/>
					</td>
					
					
					<td>
						<input id="contractActualFundsList{{idx}}_billno" name="contractActualFundsList[{{idx}}].billno" type="text" value="{{row.billno}}"    class="form-control required" maxlength="20"/>
					</td>
					
					
					<td>
						<select id="contractActualFundsList{{idx}}_settlementid" name="contractActualFundsList[{{idx}}].settlementid" data-value="{{row.settlementid}}" class="form-control m-b required ">
							<option value=""></option>
							<c:forEach items="${fns:getDictList('contractmanager_settlement')}" var="dict">
								<option value="${dict.value}">${dict.label}</option>
							</c:forEach>
						</select>
					</td>
					
					
					<td>
						<input id="contractActualFundsList{{idx}}_settlementdate" name="contractActualFundsList[{{idx}}].settlementdate" type="text" readonly="readonly" maxlength="20" class="laydate-icon form-control layer-date  "
							value="{{row.settlementdate}}" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
					</td>
					
					
					<td>
						<input id="contractActualFundsList{{idx}}_remark1" name="contractActualFundsList[{{idx}}].remark1" type="text" value="{{row.remark1}}"    class="form-control " maxlength="200"/>
					</td>
					
					
					<td>
						<input id="contractActualFundsList{{idx}}_remark2" name="contractActualFundsList[{{idx}}].remark2" type="text" value="{{row.remark2}}"    class="form-control " maxlength="20"/>
					</td>
					
					<td class="text-center" width="10">
						{{#delBtn}}<span class="close" onclick="delRow(this, '#contractActualFundsList{{idx}}')" title="删除">&times;</span>{{/delBtn}}
					</td>
				</tr>//-->
			</script>
			<script type="text/javascript">
				var contractActualFundsRowIdx = 0, contractActualFundsTpl = $("#contractActualFundsTpl").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g,"");
				$(document).ready(function() {
					var data = ${fns:toJson(contractManager.contractActualFundsList)};
					for (var i=0; i<data.length; i++){
						addRow('#contractActualFundsList', contractActualFundsRowIdx, contractActualFundsTpl, data[i]);
						contractActualFundsRowIdx = contractActualFundsRowIdx + 1;
					}
				});
			</script>
			</div>
			
			<div id="tab-8" class="tab-pane">
			<a class="btn btn-white btn-sm" onclick="addRow('#contractInvoiceList', contractInvoiceRowIdx, contractInvoiceTpl);contractInvoiceRowIdx = contractInvoiceRowIdx + 1;" title="新增"><i class="fa fa-plus"></i> 新增</a>
			<table id="contentTable" class="table table-striped table-bordered table-condensed">
				<thead>
					<tr>
						<th class="hide"></th>
						<th>发票类型</th>
						<th>金额</th>
						<th>开票日期</th>
						<th>开票号</th>
						<th>备注1</th>
						<th>备注2</th>
						<th width="10">&nbsp;</th>
					</tr>
				</thead>
				<tbody id="contractInvoiceList">
				</tbody>
			</table>
			<script type="text/template" id="contractInvoiceTpl">//<!--
				<tr id="contractInvoiceList{{idx}}">
					<td class="hide">
						<input id="contractInvoiceList{{idx}}_id" name="contractInvoiceList[{{idx}}].id" type="hidden" value="{{row.id}}"/>
						<input id="contractInvoiceList{{idx}}_delFlag" name="contractInvoiceList[{{idx}}].delFlag" type="hidden" value="0"/>
					</td>
					
					<td>
						<select id="contractInvoiceList{{idx}}_invoicetypeid" name="contractInvoiceList[{{idx}}].invoicetypeid" data-value="{{row.invoicetypeid}}" class="form-control m-b required ">
							<option value=""></option>
							<c:forEach items="${fns:getDictList('contractmanager_invoicetype')}" var="dict">
								<option value="${dict.value}">${dict.label}</option>
							</c:forEach>
						</select>
					</td>
					
					
					<td>
						<input id="contractInvoiceList{{idx}}_money" name="contractInvoiceList[{{idx}}].money" type="text" value="{{row.money}}"  onkeyup='clearNoNum(this)'  class="form-control number required" maxlength="20"/>
					</td>
					
					
					<td>
						<input id="contractInvoiceList{{idx}}_invoicedate" name="contractInvoiceList[{{idx}}].invoicedate" type="text" readonly="readonly" maxlength="20" class="laydate-icon form-control layer-date  required"
							value="{{row.invoicedate}}" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
					</td>
					
					
					<td>
						<input id="contractInvoiceList{{idx}}_invoiceno" name="contractInvoiceList[{{idx}}].invoiceno" type="text" value="{{row.invoiceno}}"    class="form-control required" maxlength="20"/>
					</td>
					
					
					<td>
						<input id="contractInvoiceList{{idx}}_remark1" name="contractInvoiceList[{{idx}}].remark1" type="text" value="{{row.remark1}}"    class="form-control " maxlength="200"/>
					</td>
					
					
					<td>
						<input id="contractInvoiceList{{idx}}_remark2" name="contractInvoiceList[{{idx}}].remark2" type="text" value="{{row.remark2}}"    class="form-control " maxlength="200"/>
					</td>
					
					<td class="text-center" width="10">
						{{#delBtn}}<span class="close" onclick="delRow(this, '#contractInvoiceList{{idx}}')" title="删除">&times;</span>{{/delBtn}}
					</td>
				</tr>//-->
			</script>
			<script type="text/javascript">
				var contractInvoiceRowIdx = 0, contractInvoiceTpl = $("#contractInvoiceTpl").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g,"");
				$(document).ready(function() {
					var data = ${fns:toJson(contractManager.contractInvoiceList)};
					for (var i=0; i<data.length; i++){
						addRow('#contractInvoiceList', contractInvoiceRowIdx, contractInvoiceTpl, data[i]);
						contractInvoiceRowIdx = contractInvoiceRowIdx + 1;
					}
				});
			</script>
			</div>
			
		</div>
		</div>
	</form:form>
</body>
</html>