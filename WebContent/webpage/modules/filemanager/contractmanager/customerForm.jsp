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
			
			laydate({
	            elem: '#businesssdeadline', //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
	            event: 'focus' //响应事件。如果没有传入event，则按照默认的click
	        });
	        laydate({
	            elem: '#setuptime', //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
	            event: 'focus' //响应事件。如果没有传入event，则按照默认的click
	        });


	        //new PCAS("P3","C3","江苏省","苏州市");
	        if("${customer.country}".localeCompare("中国")==0){
	        	new PCAS("province","city","${customer.province}","${customer.city}");
	        }else if("${customer.country}".localeCompare("海外")==0){
	        	$("#province").html('<option value="">--请选择省份--</option>');
        		$("#city").html('<option value="">--请选择城市--</option>');
	        }else{
                new PCAS("province","city","江苏省","苏州市");
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
	        });
			
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
					<i class="glyphicon glyphicon-chevron-up"></i> 客户基本信息
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
					<td class="width-15 active" colspan="4"><label class="pull-right">客户名称：</label></td>
					<td colspan="16">
						<form:input path="customername" htmlEscape="false"    class="form-control required"  maxlength="20"/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active" colspan="4"><label class="pull-right">客户编号：</label></td>
					<td colspan="6" class="width-35">
						<form:input path="customerno" htmlEscape="false"    class="form-control required" maxlength="20"/>
					</td>
					<td class="width-15 active" colspan="4"><label class="pull-right">行业：</label></td>
					<td colspan="6" class="width-35">
						<form:input path="industry" htmlEscape="false"    class="form-control " maxlength="30"/>
					</td>
				</tr>
				<tr>
					<%-- <td class="width-15 active"><label class="pull-right">客户类别id：</label></td>
					<td class="width-35">
						<form:input path="customerclassid" htmlEscape="false"    class="form-control "/>
					</td> --%>
					<td class="active" colspan="4"><label class="pull-right">客户类别：</label></td>
					<td colspan="6">
						<form:select path="customerclassid"  class="form-control m-b required">
							<form:option value="" label=""/>
							<form:options items="${fns:getDictList('customerclass')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
						</form:select>
					</td>
					<td colspan="10">&nbsp;</td>
				</tr>
				<tr>
				    <td class="active" colspan="4"><label class="pull-right">国家：</label></td>
					<td colspan="6">
						<%-- <form:input path="country" htmlEscape="false"    class="form-control "/> --%>
						<select id="country" name="country" class="form-control m-b ">
						  <option value="中国" <c:if test="${customer.country == '中国'}">selected="selected"</c:if>>中国</option>
						  <option value="海外" <c:if test="${customer.country == '海外'}">selected="selected"</c:if>>海外</option>
						</select>
					</td>
					<td class="active" colspan="2"><label class="pull-right">省：</label></td>
					<td colspan="3">
						<%-- <form:input path="province" htmlEscape="false"    class="form-control "/> --%>
						<select id="province" name="province" class="form-control m-b ">
						</select>
					</td>
					<td class="active" colspan="2"><label class="pull-right">市：</label></td>
					<td colspan="3">
						<%-- <form:input path="city" htmlEscape="false"    class="form-control "/> --%>
						<select id="city" name="city" class="form-control m-b ">
						</select>
					</td>
				</tr>
				<%-- <tr>
					
					<td class="width-15 active"><label class="pull-right">客户级别id：</label></td>
					<td class="width-35">
						<form:input path="customerlevelid" htmlEscape="false"    class="form-control "/>
					</td>
				</tr> --%>
				<tr>
					<td class="active" colspan="4"><label class="pull-right">客户级别：</label></td>
					<td colspan="6">
						<form:select path="customerlevelid"  class="form-control m-b required">
							<form:option value="" label=""/>
							<form:options items="${fns:getDictList('customerlevel')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
						</form:select>
					</td>
					<%-- <td class="width-15 active"><label class="pull-right">信用级别id：</label></td>
					<td class="width-35">
						<form:input path="creditlevelid" htmlEscape="false"    class="form-control "/>
					</td> --%>
					<td class="active" colspan="4"><label class="pull-right">信用级别：</label></td>
					<td colspan="6">
						<form:select path="creditlevelid"  class="form-control m-b required">
							<form:option value="" label=""/>
							<form:options items="${fns:getDictList('creditlevel')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
						</form:select>
					</td>
				</tr>
				<tr>
					<td class="active" colspan="4"><label class="pull-right">联系地址：</label></td>
					<td colspan="6">
						<form:input path="address" htmlEscape="false"    class="form-control " maxlength="200"/>
					</td>
					<td class="active" colspan="4"><label class="pull-right">邮编：</label></td>
					<td colspan="6">
						<form:input path="zipcode" htmlEscape="false"    class="form-control " maxlength="20"/>
					</td>
				</tr>
				<tr>
					<td class="active" colspan="4"><label class="pull-right">公司网址：</label></td>
					<td colspan="6">
						<form:input path="companyurl" htmlEscape="false"    class="form-control " maxlength="50"/>
					</td>
					<td class="active" colspan="4"><label class="pull-right">首要联系人：</label></td>
					<td colspan="6">
						<form:input path="contact" htmlEscape="false"    class="form-control " maxlength="20"/>
					</td>
				</tr>
				<tr>
					<td class="active" colspan="4"><label class="pull-right">办公电话：</label></td>
					<td colspan="6">
						<form:input path="phone" htmlEscape="false"    class="form-control " maxlength="20"/>
					</td>
					<td class="active" colspan="4"><label class="pull-right">职位：</label></td>
					<td colspan="6">
						<form:input path="position" htmlEscape="false"    class="form-control " maxlength="20"/>
					</td> 
				</tr>
				<tr>
					<td class="active" colspan="4"><label class="pull-right">移动电话：</label></td>
					<td colspan="6">
						<form:input path="mobile" htmlEscape="false"    class="form-control " maxlength="20"/>
					</td>
					<td class="active" colspan="4"><label class="pull-right">传真：</label></td>
					<td colspan="6">
						<form:input path="fax" htmlEscape="false"    class="form-control " maxlength="20"/>
					</td>
				</tr>
				<tr>
					<td class="active" colspan="4"><label class="pull-right">E-Mail：</label></td>
					<td colspan="6">
						<form:input path="email" htmlEscape="false"    class="form-control " maxlength="20"/>
					</td>
					<td class="active" colspan="4"><label class="pull-right">(QQ/MSN)：</label></td>
					<td colspan="6">
						<form:input path="qqmsn" htmlEscape="false"    class="form-control " maxlength="20"/>
					</td>
				</tr>
				<tr>
					<td class="active" colspan="4"><label class="pull-right">法定代表人：</label></td>
					<td colspan="6">
						<form:input path="legal" htmlEscape="false"    class="form-control " maxlength="20"/>
					</td>
					<td class="active" colspan="2"><label class="pull-right">注册资本：</label></td>
					<td colspan="3">
						<form:input path="registeredcapital" htmlEscape="false" onkeyup='clearNoNum(this)' style="width:85%"  class="form-control number" maxlength="20"/>万
					</td>
					<%-- <td class="width-15 active"><label class="pull-right">币种id：</label></td>
					<td class="width-35">
						<form:input path="currencyid" htmlEscape="false"    class="form-control "/>
					</td> --%>
					<td class="active" colspan="2"><label class="pull-right">币种：</label></td>
					<td colspan="3">
						<form:select path="currencyid"  class="form-control m-b">
							<form:option value="" label=""/>
							<form:options items="${fns:getDictList('currency')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
						</form:select>
					</td>
				</tr>
				<tr>
					
					<td class="active" colspan="4"><label class="pull-right">实收资本：</label></td>
					<td colspan="6">
						<form:input path="paidincapital" htmlEscape="false"  onkeyup='clearNoNum(this)'  class="form-control number" maxlength="20"/>
					</td>
					<%-- <td class="width-15 active"><label class="pull-right">公司类型id：</label></td>
					<td class="width-35">
						<form:input path="companyclassid" htmlEscape="false"    class="form-control "/>
					</td> --%>
					<td class="active" colspan="4"><label class="pull-right">公司类型：</label></td>
					<td colspan="6">
						<form:select path="companyclassid"  class="form-control m-b">
							<form:option value="" label=""/>
							<form:options items="${fns:getDictList('companyclass')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
						</form:select>
					</td>
				</tr>
				<tr>
					<td class="active" colspan="4"><label class="pull-right">住所：</label></td>
					<td colspan="6">
						<form:input path="residence" htmlEscape="false"    class="form-control " maxlength="20"/>
					</td>
					<td class="active" colspan="4"><label class="pull-right">成立时间：</label></td>
					<td colspan="6">
						<input id="setuptime" name="setuptime" type="text" maxlength="20" class="laydate-icon form-control layer-date "
							value="<fmt:formatDate value="${customer.setuptime}" pattern="yyyy-MM-dd"/>" readonly="readonly" />
					</td>
				</tr>
				<tr>
					<td class="active" colspan="4"><label class="pull-right">经营范围：</label></td>
					<td colspan="6">
						<form:input path="businessscope" htmlEscape="false"    class="form-control " maxlength="30"/>
					</td>
					<td class="active" colspan="4"><label class="pull-right">经营截止日期：</label></td>
					<td colspan="6">
						<input id="businesssdeadline" name="businesssdeadline" type="text" maxlength="20" class="laydate-icon form-control layer-date "
							value="<fmt:formatDate value="${customer.businesssdeadline}" pattern="yyyy-MM-dd"/>" readonly="readonly" />
					</td>
				</tr>
				<tr>
					<td class="active" colspan="4"><label class="pull-right">备用1：</label></td>
					<td colspan="6">
						<form:input path="remark1" htmlEscape="false"    class="form-control " maxlength="500"/>
					</td>
					<td class="active" colspan="4"><label class="pull-right">备用2：</label></td>
					<td colspan="6">
						<form:input path="remark2" htmlEscape="false"    class="form-control " maxlength="500"/>
					</td>
				</tr>
				<tr>
					<td class="active" colspan="4"><label class="pull-right">负责人：</label></td>
					<td colspan="6">
						<%-- <form:input path="responsibleperson" htmlEscape="false"    class="form-control "/> --%>
						<sys:treeselect id="responsibleperson" name="responsiblepersonid" value="${customer.responsiblepersonid}" labelName="responsibleperson" labelValue="${customer.responsibleperson}"
								title="用户" url="/sys/office/treeData?type=3" isAll="true" cssClass="form-control" notAllowSelectParent="true" />
					</td>
					<c:if test="${customer.id == null}">
					<td colspan="10">&nbsp;</td>
					</c:if>
					<c:if test="${customer.id != null}">
					<td class="active" colspan="4"><label class="pull-right">审核状态：</label></td>
					<td colspan="6">
						<form:select path="status"  class="form-control m-b">
							<form:options items="${fns:getDictList('customer_status')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
						</form:select>
					</td>
					</c:if>
					
				</tr>
			</tbody>
		</table>	
			</div>
		</div>	
		
		
		<div class="panel-heading">
			<h4 class="panel-title">
				<a data-toggle="collapse" data-parent="#accordion" 
				   href="#collapseTwo">
					<i class="glyphicon glyphicon-chevron-up"></i> 客户开票信息
				</a>
			</h4>
		</div>	
		<div id="collapseTwo" class="panel-collapse collapse in">
			<div class="panel-body">
		<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
		   	<tbody>
				
				<tr>
				    <td class="active" colspan="4"><label class="pull-right">名称：</label></td>
					<td colspan="6">
						<form:input path="invoicename" htmlEscape="false"    class="form-control " maxlength="20"/>
					</td>
					<td class="active" colspan="4"><label class="pull-right">纳税人识别号：</label></td>
					<td colspan="6">
						<form:input path="invoicetaxno" htmlEscape="false"    class="form-control " maxlength="50"/>
					</td>
				</tr>
				<tr>
				    <td class="active" colspan="4"><label class="pull-right">地址：</label></td>
					<td colspan="6">
						<form:input path="invoiceaddress" htmlEscape="false"    class="form-control " maxlength="200"/>
					</td>
					<td class="active" colspan="4"><label class="pull-right">电话：</label></td>
					<td colspan="6">
						<form:input path="invoicemobile" htmlEscape="false"    class="form-control " maxlength="20"/>
					</td>
				</tr>
				<tr>
				    <td class="active" colspan="4"><label class="pull-right">开户银行：</label></td>
					<td colspan="6">
						<form:input path="bank" htmlEscape="false"    class="form-control " maxlength="20"/>
					</td>
					<td class="active" colspan="4"><label class="pull-right">银行账号：</label></td>
					<td colspan="6">
						<form:input path="bankno" htmlEscape="false"    class="form-control " maxlength="50"/>
					</td>
				</tr>
				<%-- <tr>
				    <td class="width-15 active"><label class="pull-right">用户类型：</label></td>
					<td class="width-35">
						<form:input path="usertype" htmlEscape="false"    class="form-control "/>
					</td>
					<td class="width-15 active"><label class="pull-right">审核状态：</label></td>
					<td class="width-35">
						<form:input path="status" htmlEscape="false"    class="form-control "/>
					</td>
					<td class="width-15 active"></td>
		   			<td class="width-35" ></td>
		  		</tr> --%>
		 	</tbody>
		</table>
			</div>
		</div>
		
		</div>
		<div class="tabs-container">
            <ul class="nav nav-tabs">
				<li class="active"><a data-toggle="tab" href="#tab-1" aria-expanded="true">其他联系人</a>
                </li>
				<li class=""><a data-toggle="tab" href="#tab-2" aria-expanded="false">客户附件</a>
                </li>
				<li class=""><a data-toggle="tab" href="#tab-3" aria-expanded="false">客户备忘</a>
                </li>
            </ul>
            <div class="tab-content">
				<div id="tab-1" class="tab-pane active">
			<a class="btn btn-white btn-sm" onclick="addRow('#customerContactList', customerContactRowIdx, customerContactTpl);customerContactRowIdx = customerContactRowIdx + 1;" title="新增"><i class="fa fa-plus"></i> 新增</a>
			<table id="contentTable" class="table table-striped table-bordered table-condensed">
				<thead>
					<tr>
						<th class="hide"></th>
						<!-- <th>备注信息</th> -->
						<th>姓名</th>
						<th>职位</th>
						<th>办公电话</th>
						<th>移动电话</th>
						<th>传真</th>
						<th>E-Mail</th>
						<th>备注</th>
						<!-- <th>序号</th> -->
						<th width="10">&nbsp;</th>
					</tr>
				</thead>
				<tbody id="customerContactList">
				</tbody>
			</table>
			<script type="text/template" id="customerContactTpl">//<!--
				<tr id="customerContactList{{idx}}">
					<td class="hide">
						<input id="customerContactList{{idx}}_id" name="customerContactList[{{idx}}].id" type="hidden" value="{{row.id}}"/>
						<input id="customerContactList{{idx}}_delFlag" name="customerContactList[{{idx}}].delFlag" type="hidden" value="0"/>
					</td>
					
					<!-- <td>
						<textarea id="customerContactList{{idx}}_remarks" name="customerContactList[{{idx}}].remarks" rows="4"    class="form-control ">{{row.remarks}}</textarea>
					</td>
					 -->
					
					<td>
						<input id="customerContactList{{idx}}_realname" name="customerContactList[{{idx}}].realname" type="text" value="{{row.realname}}"    class="form-control required" maxlength="20"/>
					</td>
					
					
					<td>
						<input id="customerContactList{{idx}}_dept" name="customerContactList[{{idx}}].dept" type="text" value="{{row.dept}}"    class="form-control required" maxlength="20"/>
					</td>
					

					<td>
						<input id="customerContactList{{idx}}_phone" name="customerContactList[{{idx}}].phone" type="text" value="{{row.phone}}"    class="form-control required" maxlength="20"/>
					</td>
					
					<td>
						<input id="customerContactList{{idx}}_mobile" name="customerContactList[{{idx}}].mobile" type="text" value="{{row.mobile}}"    class="form-control  " maxlength="20"/>
					</td>
					
					
					<td>
						<input id="customerContactList{{idx}}_fax" name="customerContactList[{{idx}}].fax" type="text" value="{{row.fax}}"    class="form-control " maxlength="20"/>
					</td>
					
					
					<td>
						<input id="customerContactList{{idx}}_email" name="customerContactList[{{idx}}].email" type="text" value="{{row.email}}"    class="form-control " maxlength="20"/>
					</td>
					
					
					<td>
						<input id="customerContactList{{idx}}_remark" name="customerContactList[{{idx}}].remark" type="text" value="{{row.remark}}"    class="form-control " maxlength="200"/>
					</td>
					
					
					<!-- <td>
						<input id="customerContactList{{idx}}_sortno" name="customerContactList[{{idx}}].sortno" type="text" value="{{row.sortno}}"    class="form-control "/>
					</td> -->
					
					<td class="text-center" width="10">
						{{#delBtn}}<span class="close" onclick="delRow(this, '#customerContactList{{idx}}')" title="删除">&times;</span>{{/delBtn}}
					</td>
				</tr>//-->
			</script>
			<script type="text/javascript">
				var customerContactRowIdx = 0, customerContactTpl = $("#customerContactTpl").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g,"");
				$(document).ready(function() {
					var data = ${fns:toJson(customer.customerContactList)};
					for (var i=0; i<data.length; i++){
						addRow('#customerContactList', customerContactRowIdx, customerContactTpl, data[i]);
						customerContactRowIdx = customerContactRowIdx + 1;
					}
				});
			</script>
			</div>
				<div id="tab-2" class="tab-pane">
			<a class="btn btn-white btn-sm" onclick="addRow('#customerFileList', customerFileRowIdx, customerFileTpl);customerFileRowIdx = customerFileRowIdx + 1;" title="新增"><i class="fa fa-plus"></i> 新增</a>
			<table id="contentTable" class="table table-striped table-bordered table-condensed">
				<thead>
					<tr>
						<th class="hide"></th>
						<!-- <th>备注信息</th> -->
						<th>附件名称</th>
						<!-- <th>附件类别id</th> -->
						<th>附件类别</th>
						<th>附件</th>
						<th>附件说明</th>
						<!-- <th>序号</th> -->
						<th width="10">&nbsp;</th>
					</tr>
				</thead>
				<tbody id="customerFileList">
				</tbody>
			</table>
			<script type="text/template" id="customerFileTpl">//<!--
				<tr id="customerFileList{{idx}}">
					<td class="hide">
						<input id="customerFileList{{idx}}_id" name="customerFileList[{{idx}}].id" type="hidden" value="{{row.id}}"/>
						<input id="customerFileList{{idx}}_delFlag" name="customerFileList[{{idx}}].delFlag" type="hidden" value="0"/>
					</td>
					
					<!-- <td>
						<textarea id="customerFileList{{idx}}_remarks" name="customerFileList[{{idx}}].remarks" rows="4"    class="form-control ">{{row.remarks}}</textarea>
					</td> -->
					
					<td>
						<input id="customerFileList{{idx}}_filename" name="customerFileList[{{idx}}].filename" type="text" value="{{row.filename}}"    class="form-control required" maxlength="500"/>
					</td>
					
					
					<!-- <td>
						<input id="customerFileList{{idx}}_fileclassid" name="customerFileList[{{idx}}].fileclassid" type="text" value="{{row.fileclassid}}"    class="form-control required"/>
					</td> -->
					
					
					<td>
						<!-- <input id="customerFileList{{idx}}_fileclassid" name="customerFileList[{{idx}}].fileclassid" type="hidden" value="{{row.fileclassid}}" class="form-control required"/>
						<input id="customerFileList{{idx}}_fileclass" name="customerFileList[{{idx}}].fileclass" type="text" value="{{row.fileclass}}" onclick="goSelect('customerFileList{{idx}}_fileclassid','customerFileList{{idx}}_fileclass','customerfileclass');" readonly="true"  class="form-control " placeholder="点击选择类别" /> -->
						<select id="customerFileList{{idx}}_fileclassid" name="customerFileList[{{idx}}].fileclassid" data-value="{{row.fileclassid}}" class="form-control m-b required ">
							<option value=""></option>
							<c:forEach items="${fns:getDictList('customerfileclass')}" var="dict">
								<option value="${dict.value}">${dict.label}</option>
							</c:forEach>
						</select>

					</td>
					
					
					<td>
						<input id="customerFileList{{idx}}_fileurl" name="customerFileList[{{idx}}].fileurl" type="text" value="{{row.fileurl}}"    readonly="true"  class="form-control required" placeholder="点击上传附件" onclick="commonFileUpload('customerFileList{{idx}}_fileurl');" />
					</td>
					
					
					<td>
						<input id="customerFileList{{idx}}_remark" name="customerFileList[{{idx}}].remark" type="text" value="{{row.remark}}"    class="form-control " maxlength="200"/>
					</td>
					
					
					<!-- <td>
						<input id="customerFileList{{idx}}_sortno" name="customerFileList[{{idx}}].sortno" type="text" value="{{row.sortno}}"    class="form-control "/>
					</td> -->
					
					<td class="text-center" width="10">
						{{#delBtn}}<span class="close" onclick="delRow(this, '#customerFileList{{idx}}')" title="删除">&times;</span>{{/delBtn}}
					</td>
				</tr>//-->
			</script>
			<script type="text/javascript">
				var customerFileRowIdx = 0, customerFileTpl = $("#customerFileTpl").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g,"");
				$(document).ready(function() {
					var data = ${fns:toJson(customer.customerFileList)};
					for (var i=0; i<data.length; i++){
						addRow('#customerFileList', customerFileRowIdx, customerFileTpl, data[i]);
						customerFileRowIdx = customerFileRowIdx + 1;
					}
				});
			</script>
			</div>
				<div id="tab-3" class="tab-pane">
			<a class="btn btn-white btn-sm" onclick="addRow('#customerRemarkList', customerRemarkRowIdx, customerRemarkTpl);customerRemarkRowIdx = customerRemarkRowIdx + 1;" title="新增"><i class="fa fa-plus"></i> 新增</a>
			<table id="contentTable" class="table table-striped table-bordered table-condensed">
				<thead>
					<tr>
						<th class="hide"></th>
						<!-- <th>备注信息</th> -->
						<th>说明事项</th>
						<th>内容</th>
						<!-- <th>序号</th> -->
						<th width="10">&nbsp;</th>
					</tr>
				</thead>
				<tbody id="customerRemarkList">
				</tbody>
			</table>
			<script type="text/template" id="customerRemarkTpl">//<!--
				<tr id="customerRemarkList{{idx}}">
					<td class="hide">
						<input id="customerRemarkList{{idx}}_id" name="customerRemarkList[{{idx}}].id" type="hidden" value="{{row.id}}"/>
						<input id="customerRemarkList{{idx}}_delFlag" name="customerRemarkList[{{idx}}].delFlag" type="hidden" value="0"/>
					</td>
					
					<!-- <td>
						<textarea id="customerRemarkList{{idx}}_remarks" name="customerRemarkList[{{idx}}].remarks" rows="4"    class="form-control ">{{row.remarks}}</textarea>
					</td> -->
					
					
					<td>
						<input id="customerRemarkList{{idx}}_remarktitle" name="customerRemarkList[{{idx}}].remarktitle" type="text" value="{{row.remarktitle}}"    class="form-control required" maxlength="20"/>
					</td>
					
					
					<td>
						<input id="customerRemarkList{{idx}}_remark" name="customerRemarkList[{{idx}}].remark" type="text" value="{{row.remark}}"    class="form-control " maxlength="200"/>
					</td>
					
					
					<!-- <td>
						<input id="customerRemarkList{{idx}}_sortno" name="customerRemarkList[{{idx}}].sortno" type="text" value="{{row.sortno}}"    class="form-control "/>
					</td> -->
					
					<td class="text-center" width="10">
						{{#delBtn}}<span class="close" onclick="delRow(this, '#customerRemarkList{{idx}}')" title="删除">&times;</span>{{/delBtn}}
					</td>
				</tr>//-->
			</script>
			<script type="text/javascript">
				var customerRemarkRowIdx = 0, customerRemarkTpl = $("#customerRemarkTpl").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g,"");
				$(document).ready(function() {
					var data = ${fns:toJson(customer.customerRemarkList)};
					for (var i=0; i<data.length; i++){
						addRow('#customerRemarkList', customerRemarkRowIdx, customerRemarkTpl, data[i]);
						customerRemarkRowIdx = customerRemarkRowIdx + 1;
					}
				});
			</script>
			</div>
		</div>
		</div>
	</form:form>
</body>
</html>