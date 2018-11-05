<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>信息管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			laydate({
	            elem: '#startdate', //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
	            event: 'focus' //响应事件。如果没有传入event，则按照默认的click
	        });
			laydate({
	            elem: '#enddate', //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
	            event: 'focus' //响应事件。如果没有传入event，则按照默认的click
	        });
		});
		function add(){
			openDialog("新增供应商信息","${ctx}/filemanagement/customer/form?usertype=${usertype}","950px", "600px","");
		}
	</script>
</head>
<body class="gray-bg">
	<div class="wrapper wrapper-content">
	<div class="ibox">
	<div class="ibox-title">
		<h5>供应商信息列表 </h5> 
		<div class="ibox-tools">
			<a class="collapse-link">
				<i class="fa fa-chevron-up"></i>
			</a>
			<!-- <a class="dropdown-toggle" data-toggle="dropdown" href="#">
				<i class="fa fa-wrench"></i>
			</a>
			<ul class="dropdown-menu dropdown-user">
				<li><a href="#">选项1</a>
				</li>
				<li><a href="#">选项2</a>
				</li>
			</ul>
			<a class="close-link">
				<i class="fa fa-times"></i>
			</a> -->
		</div>
	</div>
    
    <div class="ibox-content">
	<sys:message content="${message}"/>
	
	<!--查询条件-->
	<div class="row">
	<div class="col-sm-12">
	<form:form id="searchForm" modelAttribute="customer" action="${ctx}/filemanagement/customer/" method="post" class="form-inline">
		<input id="usertype" name="usertype" type="hidden" value="${usertype}"/>
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<table:sortColumn id="orderBy" name="orderBy" value="${page.orderBy}" callback="sortOrRefresh();"/><!-- 支持排序 -->
		<div class="form-group">
		   <span>供应商名称：</span>
				<form:input path="customername" htmlEscape="false" maxlength="200"  class=" form-control input-sm"/>
		   &nbsp;<span>供应商编号：</span>
				<form:input path="customerno" htmlEscape="false" maxlength="200"  class=" form-control input-sm"/>
		   &nbsp;<span>供应商类别：</span>		
				<form:select path="customerclassid"  class="form-control m-b required">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('customerclass')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
		   &nbsp;<span>创建日期：</span>
				<input id="startdate" name="startdate" type="text" maxlength="20" class="laydate-icon form-control layer-date "
							value="<fmt:formatDate value="${customer.startdate}" pattern="yyyy-MM-dd"/>"  />
				至
				<input id="enddate" name="enddate" type="text" maxlength="20" class="laydate-icon form-control layer-date "
							value="<fmt:formatDate value="${customer.enddate}" pattern="yyyy-MM-dd"/>"  />
				
			  <div class="pull-right" style="padding-left: 20px;">
				<button  class="btn btn-primary btn-rounded btn-outline btn-sm " onclick="search()" ><i class="fa fa-search"></i> 查询</button>
				<button  class="btn btn-primary btn-rounded btn-outline btn-sm " onclick="resetform()" ><i class="fa fa-refresh"></i> 重置</button>
			</div>
		 </div>	
	</form:form>
	<br/>
	</div>
	</div>
	
	<!-- 工具栏 -->
	<div class="row">
	<div class="col-sm-12">
		<div class="pull-left">
			<shiro:hasPermission name="filemanagement:customer:add">
				<%-- <table:addRow url="${ctx}/filemanagement/customer/form" title="信息"></table:addRow> --%><!-- 增加按钮 -->
				<button class="btn btn-white btn-sm" data-toggle="tooltip" data-placement="left" onclick="add()" title="添加"><i class="fa fa-plus"></i> 添加</button>
			</shiro:hasPermission>
			<%-- <shiro:hasPermission name="filemanagement:customer:edit">
			    <table:editRow url="${ctx}/filemanagement/customer/form" title="信息" id="contentTable"></table:editRow><!-- 编辑按钮 -->
			</shiro:hasPermission> --%>
			<shiro:hasPermission name="filemanagement:customer:del">
				<table:delRow url="${ctx}/filemanagement/customer/deleteAll" id="contentTable"></table:delRow><!-- 删除按钮 -->
			</shiro:hasPermission>
			<%-- <shiro:hasPermission name="filemanagement:customer:import">
				<table:importExcel url="${ctx}/filemanagement/customer/import"></table:importExcel><!-- 导入按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="filemanagement:customer:export">
	       		<table:exportExcel url="${ctx}/filemanagement/customer/export"></table:exportExcel><!-- 导出按钮 -->
	       	</shiro:hasPermission> --%>
	       <button class="btn btn-white btn-sm " data-toggle="tooltip" data-placement="left" onclick="sortOrRefresh()" title="刷新"><i class="glyphicon glyphicon-repeat"></i> 刷新</button>
		
			</div>
		<!-- <div class="pull-right">
			<button  class="btn btn-primary btn-rounded btn-outline btn-sm " onclick="search()" ><i class="fa fa-search"></i> 查询</button>
			<button  class="btn btn-primary btn-rounded btn-outline btn-sm " onclick="reset()" ><i class="fa fa-refresh"></i> 重置</button>
		</div> -->
	</div>
	</div>
	
	<!-- 表格 -->
	<table id="contentTable" class="table table-striped table-bordered table-hover table-condensed dataTables-example dataTable">
		<thead>
			<tr>
				<th> <input type="checkbox" class="i-checks"></th>
				<!-- <th  class="sort-column remarks">备注信息</th> -->
				<th  class="sort-column customername">供应商名称</th>
				<th  class="sort-column customerno">供应商编号</th>
				<th  class="sort-column industry">行业</th>
				<!-- <th  class="sort-column customerclassid">供应商类别id</th> -->
				<th  class="sort-column customerclass">供应商类别</th>
				<!-- <th  class="sort-column country">国家</th>
				<th  class="sort-column province">省</th>
				<th  class="sort-column city">市</th> -->
				<!-- <th  class="sort-column customerlevelid">供应商级别id</th> -->
				<th  class="sort-column customerlevel">供应商级别</th>
				<!-- <th  class="sort-column creditlevelid">信用级别id</th> -->
				<th  class="sort-column creditlevel">信用级别</th>
				<th  class="sort-column contact">首要联系人</th>
				<!-- <th  class="sort-column address">联系地址</th>
				<th  class="sort-column zipcode">邮编</th>
				<th  class="sort-column companyurl">公司网址</th>
				<th  class="sort-column phone">办公电话</th>
				<th  class="sort-column position">职位</th>
				<th  class="sort-column mobile">移动电话</th>
				<th  class="sort-column fax">传真</th>
				<th  class="sort-column email">E-Mail</th>
				<th  class="sort-column qqmsn">(QQ/MSN)</th>
				<th  class="sort-column legal">法定代表人</th>
				<th  class="sort-column registeredcapital">注册资本</th>
				<th  class="sort-column currencyid">币种id</th>
				<th  class="sort-column currency">币种</th>
				<th  class="sort-column paidincapital">实收资本</th>
				<th  class="sort-column companyclassid">公司类型id</th>
				<th  class="sort-column companyclass">公司类型</th>
				<th  class="sort-column residence">住所</th>
				<th  class="sort-column setuptime">成立时间</th>
				<th  class="sort-column businessscope">经营范围</th>
				<th  class="sort-column businesssdeadline">经营截止日期</th> -->
				<th  class="sort-column remark1">备用1</th>
				<th  class="sort-column remark2">备用2</th>
				<!-- <th  class="sort-column responsibleperson">负责人</th>
				<th  class="sort-column invoicename">名称</th>
				<th  class="sort-column invoicetaxno">纳税人识别号</th>
				<th  class="sort-column invoiceaddress">地址</th>
				<th  class="sort-column invoicemobile">电话</th>
				<th  class="sort-column bank">开户银行</th>
				<th  class="sort-column bankno">银行账号</th>
				<th  class="sort-column usertype">用户类型</th> -->
				<th  class="sort-column status">审核状态</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="customer">
			<tr>
				<td> <input type="checkbox" id="${customer.id}" class="i-checks"></td>
				<%-- <td><a  href="#" onclick="openDialogView('查看信息', '${ctx}/filemanagement/customer/form?id=${customer.id}','800px', '500px')">
					${customer.remarks}
				</a></td> --%>
				<td>
					<a href="#" onclick="openDialogView('查看信息', '${ctx}/filemanagement/customer/view?id=${customer.id}','950px', '600px')" >${customer.customername}</a>
				</td>
				<td>
					${customer.customerno}
				</td>
				<td>
					${customer.industry}
				</td>
				<%-- <td>
					${customer.customerclassid}
				</td> --%>
				<td>
					${customer.customerclass}
				</td>
				<%-- <td>
					${customer.country}
				</td>
				<td>
					${customer.province}
				</td>
				<td>
					${customer.city}
				</td> --%>
				<%-- <td>
					${customer.customerlevelid}
				</td> --%>
				<td>
					${customer.customerlevel}
				</td>
				<%-- <td>
					${customer.creditlevelid}
				</td> --%>
				<td>
					${customer.creditlevel}
				</td>
				<td>
					${customer.contact}
				</td>
				<%-- <td>
					${customer.address}
				</td>
				<td>
					${customer.zipcode}
				</td>
				<td>
					${customer.companyurl}
				</td>
				
				<td>
					${customer.phone}
				</td>
				<td>
					${customer.position}
				</td>
				<td>
					${customer.mobile}
				</td>
				<td>
					${customer.fax}
				</td>
				<td>
					${customer.email}
				</td>
				<td>
					${customer.qqmsn}
				</td>
				<td>
					${customer.legal}
				</td>
				<td>
					${customer.registeredcapital}
				</td>
				<td>
					${customer.currencyid}
				</td>
				<td>
					${customer.currency}
				</td>
				<td>
					${customer.paidincapital}
				</td>
				<td>
					${customer.companyclassid}
				</td>
				<td>
					${customer.companyclass}
				</td>
				<td>
					${customer.residence}
				</td>
				<td>
					${customer.setuptime}
				</td>
				<td>
					${customer.businessscope}
				</td>
				<td>
					${customer.businesssdeadline}
				</td> --%>
				<td>
					${customer.remark1}
				</td>
				<td>
					${customer.remark2}
				</td>
				<%-- <td>
					${customer.responsibleperson}
				</td>
				<td>
					${customer.invoicename}
				</td>
				<td>
					${customer.invoicetaxno}
				</td>
				<td>
					${customer.invoiceaddress}
				</td>
				<td>
					${customer.invoicemobile}
				</td>
				<td>
					${customer.bank}
				</td>
				<td>
					${customer.bankno}
				</td>
				<td>
					${customer.usertype}
				</td> --%>
				<td>
					${fns:getDictLabel(customer.status, 'customer_status', '')}
				</td>
				<td>
					<shiro:hasPermission name="filemanagement:customer:view">
						<a href="#" onclick="openDialogView('查看信息', '${ctx}/filemanagement/customer/view?id=${customer.id}&usertype=${customer.usertype}','950px', '600px')" class="btn btn-info btn-xs" ><i class="fa fa-search-plus"></i> 查看</a>
					</shiro:hasPermission>
					<shiro:hasPermission name="filemanagement:customer:edit">
    					<a href="#" onclick="openDialog('修改信息', '${ctx}/filemanagement/customer/form?id=${customer.id}&usertype=${customer.usertype}','950px', '600px')" class="btn btn-success btn-xs" ><i class="fa fa-edit"></i> 修改</a>
    				</shiro:hasPermission>
    				<shiro:hasPermission name="filemanagement:customer:del">
						<a href="${ctx}/filemanagement/customer/delete?id=${customer.id}" onclick="return confirmx('确认要删除该信息吗？', this.href)"   class="btn btn-danger btn-xs"><i class="fa fa-trash"></i> 删除</a>
					</shiro:hasPermission>
					<shiro:hasPermission name="filemanagement:customer:edit">
						<a href="#" onclick="openDialog('授权信息', '${ctx}/filemanagement/customer/authorform?id=${customer.id}','950px', '600px')" class="btn btn-success btn-xs" ><i class="fa fa-edit"></i> 授权</a>
					</shiro:hasPermission>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	
		<!-- 分页代码 -->
	<table:page page="${page}"></table:page>
	<br/>
	<br/>
	</div>
	</div>
</div>
</body>
</html>