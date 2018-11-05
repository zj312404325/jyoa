<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>项目管理管理</title>
	<meta name="decorator" content="default"/>
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
						${projectManage.projectname}
					</td>
					
				</tr>
				<tr>
					<td class="width-15 active" colspan="4"><label class="pull-right"><font color="red">*</font>项目编号：</label></td>
					<td class="width-35" colspan="6">
						${projectManage.projectno}
					</td>
					<td class="width-15 active" colspan="4"><label class="pull-right"><font color="red">*</font>项目类别：</label></td>
					<td class="width-35" colspan="6">
						${fns:getDictLabel(projectManage.projectcategory, 'projectcategory', '')}
					</td>
					
				</tr>
				<tr>
					<td class="width-15 active" colspan="4" ><label class="pull-right">项目预算收款：</label></td>
					<td colspan="3">
						<fmt:formatNumber type="number" value="${projectManage.projectgather}" pattern="0.00" maxFractionDigits="2"/>
					</td>
					
					<td class="active" colspan="2" style="width:8%"><label class="pull-right"><font color="red">*</font>币种：</label></td>
					<td class="width-15" colspan="3">
						${fns:getDictLabel(projectManage.gathercurrencytype, 'currency', '')}
					</td>
					<td class="width-15 active" colspan="3"><label class="pull-right"><font color="red">*</font>计划开始时间：</label></td>
					<td colspan="5">
						<fmt:formatDate value="${projectManage.planstartdate}" pattern="yyyy-MM-dd"/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active" colspan="4"><label class="pull-right">项目预算付款：</label></td>
					<td colspan="3">
						<fmt:formatNumber type="number" value="${projectManage.projectpayment}" pattern="0.00" maxFractionDigits="2"/>
					</td>
					<td class="active" colspan="2" style="width:8%"><label class="pull-right"><font color="red">*</font>币种：</label></td>
					<td class="width-15" colspan="3">
						${fns:getDictLabel(projectManage.paymentcurrencytype, 'currency', '')}
					</td>
					<td class="width-15 active" colspan="3"><label class="pull-right"><font color="red">*</font>计划结束时间：</label></td>
					<td colspan="5">
						<fmt:formatDate value="${projectManage.planenddate}" pattern="yyyy-MM-dd"/>
					</td>
				</tr>
				<tr>
				
					
					<td class="width-15 active" colspan="4"><label class="pull-right">备用1：</label></td>
					<td class="width-35" colspan="6">
						${projectManage.backupone}
					</td>
					<td class="width-15 active" colspan="4"><label class="pull-right">备用2：</label></td>
					<td class="width-35" colspan="6">
						${projectManage.backuptwo}
					</td>
				</tr>
				<tr>
					
					<td class="width-15 active" colspan="4"><label class="pull-right"><font color="red">*</font>负责人姓名：</label></td>
					<td colspan="16">
						${projectManage.officalname}
					</td>
				</tr>
				<tr>
					<td class="width-15 active" colspan="4"><label class="pull-right">项目状态：</label></td>
					<td class="width-35" colspan="16">
							${fns:getDictLabel(projectManage.state, 'projectstate', '')}
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
                <li class=""><a data-toggle="tab" href="#tab-4" aria-expanded="false">相关合同</a>
                </li>
            </ul>
            <div class="tab-content">
				<div id="tab-1" class="tab-pane active">
			<table id="contentTable" class="table table-striped table-bordered table-condensed">
				<thead>
					<tr>
						<th>附件名称</th>
						<th>附件类型</th>
						<th>上传日期</th>
						<th>附件说明</th>
						<th>上传人</th>
						<th>下载</th>
					</tr>
				</thead>
				<tbody id="projectAttachmentList">
					<c:forEach items="${projectManage.projectAttachmentList}" var="item" >
					<tr> 
				        <td>${item.attachmentname}</td>
				        <td>${fns:getDictLabel(item.attachmentcategory, 'projectattachment', '')}</td>
				        <td><fmt:formatDate value="${item.createDate}" pattern="yyyy-MM-dd"/></td>
				        <td>${item.attachmentmemo}</td>
				        <td>${fns:getUserById(item.createBy.id).name}</td>
				        <td><a href="${ctx}/sys/user/fileDownload?fileUrl=${item.fileurl}" >下载</a></td>
				        </tr>
				    </c:forEach>
				</tbody>
			</table>
			</div>
				<div id="tab-2" class="tab-pane">
			<table id="contentTable" class="table table-striped table-bordered table-condensed">
				<thead>
					<tr>
						<th>说明事项</th>
						<th>内容</th>
						<th>提交人</th>
						<th>提交时间</th>
					</tr>
				</thead>
				<tbody id="projectMemoList">
					<c:forEach items="${projectManage.projectMemoList}" var="item" >
					<tr> 
				        <td>${item.matter}</td>
				        <td>${item.content}</td>
				        <td>${fns:getUserById(item.createBy.id).name}</td>
				        <td><fmt:formatDate value="${item.createDate}" pattern="yyyy-MM-dd"/></td>
				        </tr>
				    </c:forEach>
				</tbody>
			</table>
			</div>
				<div id="tab-3" class="tab-pane">
			<table id="contentTable" class="table table-striped table-bordered table-condensed">
				<thead>
					<tr>
						<th>事项</th>
						<th>计划开始日期</th>
						<th>计划结束时间</th>
						<th>备注</th>
					</tr>
				</thead>
				<tbody id="projectTimeList">
					<c:forEach items="${projectManage.projectTimeList}" var="item" >
					  <tr> 
				        <td>${item.matter}</td>
				        <td><fmt:formatDate value="${item.planstarttime}" pattern="yyyy-MM-dd"/></td>
				        <td><fmt:formatDate value="${item.planendtime}" pattern="yyyy-MM-dd"/></td>
				        <td>${item.memo}</td>
				      </tr>
				    </c:forEach>
				</tbody>
			</table>
			</div>
			
			<div id="tab-4" class="tab-pane">
			<table id="" class="table table-striped table-bordered table-condensed">
				<thead>
					<tr>
						<th>合同名称</th>
						<th>合同编号</th>
						<th>合同类别</th>
						<th>合同金额</th>
						<th>币种</th>
						<th>合同对方</th>
						<th>合同状态</th>
						<th>资金性质</th>
					</tr>
				</thead>
				<tbody id="">
					<c:forEach items="${contractManagerList}" var="item">
					       <tr> 
					          <td>${item.contractname}</td>
					          <td>${item.contractno}</td>
					          <td>${item.contractclass}</td>
					          <td>${item.contractamount}</td>
					          <td>${item.currency}</td>
					          <td>${item.contractparty}</td>
					          <td>${fns:getDictLabel(item.status, 'contractmanager_contractstatus', '')}</td>
					          <td>${item.fundnature}</td>
					       </tr>
					   </c:forEach>
				</tbody>
			</table>
			</div>
			
		</div>
		</div>
	</form:form>
</body>
</html>