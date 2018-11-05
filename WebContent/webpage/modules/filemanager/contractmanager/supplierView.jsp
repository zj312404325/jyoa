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
			
			/* laydate({
	            elem: '#businesssdeadline', //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
	            event: 'focus' //响应事件。如果没有传入event，则按照默认的click
	        });
	        laydate({
	            elem: '#setuptime', //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
	            event: 'focus' //响应事件。如果没有传入event，则按照默认的click
	        }); */
	        
	        //new PCAS("P3","C3","江苏省","苏州市");
	        /* if("${customer.country}" == '中国'){
	        	new PCAS("province","city","${customer.province}","${customer.city}");
	        }else{
	        	$("#province").html('<option value="">--请选择省份--</option>');
        		$("#city").html('<option value="">--请选择城市--</option>');
	        }

	        $("#country").change(function(){
	        	if($("#country").val() == '海外'){
	        		$("#province").html('<option value="">--请选择省份--</option>');
	        		$("#city").html('<option value="">--请选择城市--</option>');
	        	}else{
	        		$("#province").html('');
	        		$("#city").html('');
	        		new PCAS("province","city");
	        	}
	        }); */
			
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
			$('#collapseTwo').on('show.bs.collapse', function () {
				if($(this).prev(".panel-heading").find("i").hasClass("glyphicon-chevron-down")){
					$(this).prev(".panel-heading").find("i").removeClass("glyphicon-chevron-down").addClass("glyphicon-chevron-up");
				}
				
			});
			$('#collapseTwo').on('hide.bs.collapse', function () {
				if($(this).prev(".panel-heading").find("i").hasClass("glyphicon-chevron-up")){
					$(this).prev(".panel-heading").find("i").removeClass("glyphicon-chevron-up").addClass("glyphicon-chevron-down");
				}
				
			});
			
		});
		function goSelect(pid,pvalue,type){
			layer.open({
    		    type: 2,
    		    title : '选择',
    		    shadeClose: true,
    		    shade: 0.8,
    		    area : ['250px' , '200px'],
    		    offset : ['150px',''],
    		    content: '${ctx}/filemanagement/customer/goselect?pid='+pid+'&pvalue='+pvalue+'&type='+type //iframe的url
    		});
			return false;
		}
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
	<style type="text/css">
	.form-control[disabled], .form-control[readonly], fieldset[disabled] .form-control {
	    background-color: #fbfbfb;
	    opacity: 1;
	}
	</style>
</head>
<body class="hideScroll">
	<form:form id="inputForm" modelAttribute="customer" action="${ctx}/filemanagement/customer/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<form:hidden path="usertype"/>
		<sys:message content="${message}"/>	
		<div class="panel-group" id="accordion">
		<div class="panel-heading">
			<h4 class="panel-title">
				<a data-toggle="collapse" data-parent="#accordion" 
				   href="#collapseOne">
					<i class="glyphicon glyphicon-chevron-up"></i> 供应商基本信息
				</a>
			</h4>
		</div>
		
		<div id="collapseOne" class="panel-collapse collapse in">
			<div class="panel-body">
		<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
		   <tbody>
				<tr>
					<%-- <td class="width-15 active"><label class="pull-right">备注信息：</label></td>
					<td class="width-35">
						<form:textarea path="remarks" htmlEscape="false" rows="4"    class="form-control "/>
					</td> --%>
					<td class="width-15 active" colspan="4"><label class="pull-right">供应商名称：</label></td>
					<td colspan="16">
						${customer.customername}
					</td>
				</tr>
				<tr>
					<td class="width-15 active" colspan="4"><label class="pull-right">供应商编号：</label></td>
					<td colspan="6" class="width-35">
						${customer.customerno}
					</td>
					<td class="width-15 active" colspan="4"><label class="pull-right">行业：</label></td>
					<td colspan="6" class="width-35">
						${customer.industry}
					</td>
				</tr>
				<tr>
					<td class="active" colspan="4"><label class="pull-right">供应商类别：</label></td>
					<td colspan="6">
						<%-- ${fns:getDictLabel(customer.customerclassid, 'customerclass', '')} --%>
						${customer.customerclass}
					</td>
					<td colspan="10">&nbsp;</td>
				</tr>
				<tr>
				    <td class="active" colspan="4"><label class="pull-right">国家：</label></td>
					<td colspan="6">
						${customer.country}
					</td>
					<td class="active" colspan="4"><label class="pull-right">省市：</label></td>
					<td colspan="6">
						${customer.province}${customer.city}
					</td>
				</tr>
				<tr>
					<td class="active" colspan="4"><label class="pull-right">供应商级别：</label></td>
					<td colspan="6">
						<%-- ${fns:getDictLabel(customer.customerlevelid, 'customerlevel', '')} --%>
						${customer.customerlevel}
					</td>
					<td class="active" colspan="4"><label class="pull-right">信用级别：</label></td>
					<td colspan="6">
						<%-- ${fns:getDictLabel(customer.creditlevelid, 'creditlevel', '')} --%>
						${customer.creditlevel}
					</td>
				</tr>
				<tr>
					<td class="active" colspan="4"><label class="pull-right">联系地址：</label></td>
					<td colspan="6">
						${customer.address}
					</td>
					<td class="active" colspan="4"><label class="pull-right">邮编：</label></td>
					<td colspan="6">
						${customer.zipcode}
					</td>
				</tr>
				<tr>
					<td class="active" colspan="4"><label class="pull-right">公司网址：</label></td>
					<td colspan="6">
						${customer.companyurl}
					</td>
					<td class="active" colspan="4"><label class="pull-right">首要联系人：</label></td>
					<td colspan="6">
						${customer.contact}
					</td>
				</tr>
				<tr>
					<td class="active" colspan="4"><label class="pull-right">办公电话：</label></td>
					<td colspan="6">
						${customer.phone}
					</td>
					<td class="active" colspan="4"><label class="pull-right">职位：</label></td>
					<td colspan="6">
						${customer.position}
					</td> 
				</tr>
				<tr>
					<td class="active" colspan="4"><label class="pull-right">移动电话：</label></td>
					<td colspan="6">
						${customer.mobile}
					</td>
					<td class="active" colspan="4"><label class="pull-right">传真：</label></td>
					<td colspan="6">
						${customer.fax}
					</td>
				</tr>
				<tr>
					<td class="active" colspan="4"><label class="pull-right">E-Mail：</label></td>
					<td colspan="6">
						${customer.email}
					</td>
					<td class="active" colspan="4"><label class="pull-right">(QQ/MSN)：</label></td>
					<td colspan="6">
						${customer.qqmsn}
					</td>
				</tr>
				<tr>
					<td class="active" colspan="4"><label class="pull-right">法定代表人：</label></td>
					<td colspan="6">
					    ${customer.legal}
					</td>
					<td class="active" colspan="2"><label class="pull-right">注册资本：</label></td>
					<td colspan="3">
						${customer.registeredcapital}万
					</td>
					<td class="active" colspan="2"><label class="pull-right">币种：</label></td>
					<td colspan="3">
						<%-- ${fns:getDictLabel(customer.currencyid, 'currency', '')} --%>
						${customer.currency}
					</td>
				</tr>
				<tr>
					
					<td class="active" colspan="4"><label class="pull-right">实收资本：</label></td>
					<td colspan="6">
						${customer.paidincapital}
					</td>
					<td class="active" colspan="4"><label class="pull-right">公司类型：</label></td>
					<td colspan="6">
						<%-- ${fns:getDictLabel(customer.companyclassid, 'companyclass', '')} --%>
						${customer.companyclass}
					</td>
				</tr>
				<tr>
					<td class="active" colspan="4"><label class="pull-right">住所：</label></td>
					<td colspan="6">
						${customer.residence}
					</td>
					<td class="active" colspan="4"><label class="pull-right">成立时间：</label></td>
					<td colspan="6">
						<fmt:formatDate value="${customer.setuptime}" pattern="yyyy-MM-dd"/>
					</td>
				</tr>
				<tr>
					<td class="active" colspan="4"><label class="pull-right">经营范围：</label></td>
					<td colspan="6">
						${customer.businessscope}
					</td>
					<td class="active" colspan="4"><label class="pull-right">经营截止日期：</label></td>
					<td colspan="6">
						<fmt:formatDate value="${customer.businesssdeadline}" pattern="yyyy-MM-dd"/>
					</td>
				</tr>
				<tr>
				    <td class="active" colspan="4"><label class="pull-right">开户银行：</label></td>
					<td colspan="6">
						${customer.bank}
					</td>
					<td class="active" colspan="4"><label class="pull-right">账号：</label></td>
					<td colspan="6">
						${customer.bankno}
					</td>
				</tr>
				<tr>
					<td class="active" colspan="4"><label class="pull-right">备用1：</label></td>
					<td colspan="6">
						${customer.remark1}
					</td>
					<td class="active" colspan="4"><label class="pull-right">备用2：</label></td>
					<td colspan="6">
						${customer.remark2}
					</td>
				</tr>
				<tr>
					<td class="active" colspan="4"><label class="pull-right">负责人：</label></td>
					<td colspan="6">
						${customer.responsibleperson}
					</td>
					<td class="active" colspan="4"><label class="pull-right">审核状态：</label></td>
					<td colspan="6">
						${fns:getDictLabel(customer.status, 'customer_status', '')}
					</td>
				</tr>
			</tbody>
		</table>	
			</div>
		</div>	
		
		
		<!-- <div class="panel-heading">
			<h4 class="panel-title">
				<a data-toggle="collapse" data-parent="#accordion" 
				   href="#collapseTwo">
					<i class="glyphicon glyphicon-chevron-up"></i> 供应商开票信息
				</a>
			</h4>
		</div>	 -->
		<%-- <div id="collapseTwo" class="panel-collapse collapse in">
			<div class="panel-body">
		<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
		   	<tbody>
				
				<tr>
				    <td class="active" colspan="4"><label class="pull-right">名称：</label></td>
					<td colspan="6">
						<form:input path="invoicename" htmlEscape="false"    class="form-control "/>
					</td>
					<td class="active" colspan="4"><label class="pull-right">纳税人识别号：</label></td>
					<td colspan="6">
						<form:input path="invoicetaxno" htmlEscape="false"    class="form-control "/>
					</td>
				</tr>
				<tr>
				    <td class="active" colspan="4"><label class="pull-right">地址：</label></td>
					<td colspan="6">
						<form:input path="invoiceaddress" htmlEscape="false"    class="form-control "/>
					</td>
					<td class="active" colspan="4"><label class="pull-right">电话：</label></td>
					<td colspan="6">
						<form:input path="invoicemobile" htmlEscape="false"    class="form-control "/>
					</td>
				</tr>
				<tr>
				    <td class="active" colspan="4"><label class="pull-right">开户银行：</label></td>
					<td colspan="6">
						<form:input path="bank" htmlEscape="false"    class="form-control "/>
					</td>
					<td class="active" colspan="4"><label class="pull-right">银行账号：</label></td>
					<td colspan="6">
						<form:input path="bankno" htmlEscape="false"    class="form-control "/>
					</td>
				</tr>
		 	</tbody>
		</table>
			</div>
		</div> --%>
		
		</div>
		<div class="tabs-container">
            <ul class="nav nav-tabs">
				<li class="active"><a data-toggle="tab" href="#tab-1" aria-expanded="true">其他联系人</a>
                </li>
				<li class=""><a data-toggle="tab" href="#tab-2" aria-expanded="false">供应商附件</a>
                </li>
				<li class=""><a data-toggle="tab" href="#tab-3" aria-expanded="false">供应商备忘</a>
                </li>
                <li class=""><a data-toggle="tab" href="#tab-4" aria-expanded="false">相关合同</a>
                </li>
                <li class=""><a data-toggle="tab" href="#tab-5" aria-expanded="false">合同标的</a>
                </li>
                <li class=""><a data-toggle="tab" href="#tab-6" aria-expanded="false">相关项目</a>
                </li>
            </ul>
            <div class="tab-content">
				<div id="tab-1" class="tab-pane active">
			<table id="contentTable" class="table table-striped table-bordered table-condensed">
				<thead>
					<tr>
						<th>姓名</th>
						<th>部门及职位</th>
						<th>办公电话</th>
						<th>移动电话</th>
						<th>传真</th>
						<th>E-Mail</th>
						<th>备注</th>
					</tr>
				</thead>
				<tbody id="customerContactList">
					<c:forEach items="${customer.customerContactList}" var="item" >
					<tr> 
				        <td>${item.realname}</td>
				        <td>${item.dept}</td>
				        <td>${item.phone}</td>
				        <td>${item.mobile}</td>
				        <td>${item.fax}</td>
				        <td>${item.email}</td>
				        <td>${item.remark}</td>
				        </tr>
				    </c:forEach>
				</tbody>
			</table>
			</div>
				<div id="tab-2" class="tab-pane">
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
				<tbody id="customerFileList">
					<c:forEach items="${customer.customerFileList}" var="item" >
					<tr> 
				        <td>${item.filename}</td>
				        <td>${fns:getDictLabel(item.fileclassid, 'contractmanager_fileclass', '')}</td>
				        <td><fmt:formatDate value="${item.createDate}" pattern="yyyy-MM-dd"/></td>
				        <td>${item.remark}</td>
				        <td>${fns:getUserById(item.createBy.id).name}</td>
				        <td><a href="${ctx}/sys/user/fileDownload?fileUrl=${item.fileurl}" >下载</a></td>
				        </tr>
				    </c:forEach>
				</tbody>
			</table>
			</div>
				<div id="tab-3" class="tab-pane">
			<table id="contentTable" class="table table-striped table-bordered table-condensed">
				<thead>
					<tr>
						<th>说明事项</th>
						<th>内容</th>
						<th>提交人</th>
						<th>提交时间</th>
					</tr>
				</thead>
				<tbody id="customerRemarkList">
					<c:forEach items="${customer.customerRemarkList}" var="item" >
					<tr> 
				        <td>${item.remarktitle}</td>
				        <td>${item.remark}</td>
				        <td>${fns:getUserById(item.createBy.id).name}</td>
				        <td><fmt:formatDate value="${item.createDate}" pattern="yyyy-MM-dd"/></td>
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
					</tr>
				</thead>
				<tbody id="">
				   <c:forEach items="${customer.contractManagerList}" var="item">
				       <tr> 
				          <td>${item.contractname}</td>
				          <td>${item.contractno}</td>
				          <td>${item.contractclass}</td>
				          <td>${item.contractamount}</td>
				          <td>${item.currency}</td>
				          <td>${item.contractparty}</td>
				          <td>${fns:getDictLabel(item.status, 'contractmanager_contractstatus', '')}</td>
				       </tr>
				   </c:forEach>
				</tbody>
			</table>
			
			</div>
			
			<div id="tab-5" class="tab-pane">
			<table id="" class="table table-striped table-bordered table-condensed">
				<thead>
					<tr>
						<th>所属合同</th>
						<th>标的名称</th>
						<th>规格</th>
						<th>型号</th>
						<th>数量</th>
					</tr>
				</thead>
				<tbody id="">
				   <c:forEach items="${customer.contractManagerList}" var="item">
				       <c:forEach items="${item.contractSubjectList}" var="item2">
				       <tr> 
				          <td>${item.contractname}</td>
				          <td>${item2.subjectname}</td>
				          <td>${item2.specification}</td>
				          <td>${item2.model}</td>
				          <td>${item2.quantity}</td>
				       </tr>
				       </c:forEach>
				   </c:forEach>
				</tbody>
			</table>
			
			</div>
			
			<div id="tab-6" class="tab-pane">
			<table id="" class="table table-striped table-bordered table-condensed">
				<thead>
					<tr>
						<th>项目名称</th>
						<th>项目编号</th>
						<th>项目类别</th>
						<th>计划预算收款</th>
						<th>计划预算付款</th>
						<th>项目负责人</th>
						<th>项目状态</th>
					</tr>
				</thead>
				<tbody id="">
				   <c:forEach items="${customer.projectManageList}" var="item">
				       <tr>                                                                                                                                                                                          
				          <td>${item.projectname}</td>
				          <td>${item.projectno}</td>
				          <td>${fns:getDictLabel(item.projectcategory, 'projectcategory', '')}</td>
				          <td>${item.projectgather}</td>
				          <td>${item.projectpayment}</td>
				          <td>${item.officalname}</td>
				          <td>${fns:getDictLabel(item.state, 'projectstate', '')}</td>
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