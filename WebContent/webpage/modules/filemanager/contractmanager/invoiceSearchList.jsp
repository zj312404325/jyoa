<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>发票查询管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
		});
	</script>
</head>
<body class="gray-bg">
	<div class="wrapper wrapper-content">
	<div class="ibox">
	<div class="ibox-title">
		<h5>发票查询列表 </h5>
		<div class="ibox-tools">
			<a class="collapse-link">
				<i class="fa fa-chevron-up"></i>
			</a>
			<a class="dropdown-toggle" data-toggle="dropdown" href="#">
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
			</a>
		</div>
	</div>
    
    <div class="ibox-content">
	<sys:message content="${message}"/>
	
	<!--查询条件-->
	<div class="row">
	<div class="col-sm-12">
	<form:form id="searchForm" modelAttribute="invoiceSearch" action="${ctx}/contractmanager/invoiceSearch/" method="post" class="form-inline">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<table:sortColumn id="orderBy" name="orderBy" value="${page.orderBy}" callback="sortOrRefresh();"/><!-- 支持排序 -->
		<div class="form-group">
			<span>合同名称：</span>
			<form:input path="contractname" htmlEscape="false" maxlength="20"  class=" form-control input-sm"/>
			<span>合同编号：</span>
			<form:input path="contractno" htmlEscape="false" maxlength="20"  class=" form-control input-sm"/>
			<span>合同对方：</span>
			<form:input path="customername" htmlEscape="false" maxlength="20"  class=" form-control input-sm"/>
			<span>发票状态：</span>
			<form:select path="fundnatureid" class="form-control required">
				<form:option value="" label=""/>
				<form:option value="1" label="已开出"/>
				<form:option value="2" label="已收到"/>
			</form:select>
		 </div>	
	</form:form>
	<br/>
	</div>
	</div>
	
	<!-- 工具栏 -->
	<div class="row">
	<div class="col-sm-12">
		<div class="pull-left">
			<shiro:hasPermission name="contractmanager:invoiceSearch:add">
				<table:addRow url="${ctx}/contractmanager/invoiceSearch/form" title="发票查询"></table:addRow><!-- 增加按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="contractmanager:invoiceSearch:edit">
			    <table:editRow url="${ctx}/contractmanager/invoiceSearch/form" title="发票查询" id="contentTable"></table:editRow><!-- 编辑按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="contractmanager:invoiceSearch:del">
				<table:delRow url="${ctx}/contractmanager/invoiceSearch/deleteAll" id="contentTable"></table:delRow><!-- 删除按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="contractmanager:invoiceSearch:import">
				<table:importExcel url="${ctx}/contractmanager/invoiceSearch/import"></table:importExcel><!-- 导入按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="contractmanager:invoiceSearch:export">
	       		<table:exportExcel url="${ctx}/contractmanager/invoiceSearch/export"></table:exportExcel><!-- 导出按钮 -->
	       	</shiro:hasPermission>
	       <button class="btn btn-white btn-sm " data-toggle="tooltip" data-placement="left" onclick="sortOrRefresh()" title="刷新"><i class="glyphicon glyphicon-repeat"></i> 刷新</button>
		
			</div>
		<div class="pull-right">
			<button  class="btn btn-primary btn-rounded btn-outline btn-sm " onclick="search()" ><i class="fa fa-search"></i> 查询</button>
			<button  class="btn btn-primary btn-rounded btn-outline btn-sm " onclick="reset()" ><i class="fa fa-refresh"></i> 重置</button>
		</div>
	</div>
	</div>
	
	<!-- 表格 -->
	<table id="contentTable" class="table table-striped table-bordered table-hover table-condensed dataTables-example dataTable">
		<thead>
			<tr>
				<th> <input type="checkbox" class="i-checks"></th>
				<th  class="sort-column invoicetype">类型</th>
				<th  class="sort-column contractname">合同名称</th>
				<th  class="sort-column contractno">合同编号</th>
				<th  class="sort-column invoiceno">发票号</th>
				<th  class="sort-column fundnatureid">资金性质</th>
				<th  class="sort-column customername">合同对方</th>
				<th  class="sort-column money">发票金额</th>
				<th  class="sort-column status">发票状态</th>
				<th  class="sort-column remark1">备用1</th>
				<th  class="sort-column remark2">备用2</th>
				<!-- <th>操作</th> -->
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="invoiceSearch">
			<tr>
				<td> <input type="checkbox" id="${invoiceSearch.id}" class="i-checks"></td>
				<td>${fns:getDictLabel(invoiceSearch.invoicetypeid, 'contractmanager_invoicetype', '')}</td>
				<td>
					<a href="#" onclick="openDialogView('查看信息', '${ctx}/contractmanager/contractManager/view?id=${invoiceSearch.contractid}','950px', '720px')" class="" >${invoiceSearch.contractname}</a>
				</td>
				<td>
					${invoiceSearch.contractno}
				</td>
				<td>
					${invoiceSearch.invoiceno}
				</td>
				<td>
					${fns:getDictLabel(invoiceSearch.fundnatureid, 'fundnature', '')}
				</td>
				<td>
					${invoiceSearch.customername}
				</td>
				<td>
					${invoiceSearch.money}
				</td>
				<td>
					${invoiceSearch.status}
				</td>
				<td>
					${invoiceSearch.remark1}
				</td>
				<td>
					${invoiceSearch.remark2}
				</td>
				<%-- <td>
					<shiro:hasPermission name="contractmanager:invoiceSearch:view">
						<a href="#" onclick="openDialogView('查看发票查询', '${ctx}/contractmanager/invoiceSearch/form?id=${invoiceSearch.id}','800px', '500px')" class="btn btn-info btn-xs" ><i class="fa fa-search-plus"></i> 查看</a>
					</shiro:hasPermission>
					<shiro:hasPermission name="contractmanager:invoiceSearch:edit">
    					<a href="#" onclick="openDialog('修改发票查询', '${ctx}/contractmanager/invoiceSearch/form?id=${invoiceSearch.id}','800px', '500px')" class="btn btn-success btn-xs" ><i class="fa fa-edit"></i> 修改</a>
    				</shiro:hasPermission>
    				<shiro:hasPermission name="contractmanager:invoiceSearch:del">
						<a href="${ctx}/contractmanager/invoiceSearch/delete?id=${invoiceSearch.id}" onclick="return confirmx('确认要删除该发票查询吗？', this.href)"   class="btn btn-danger btn-xs"><i class="fa fa-trash"></i> 删除</a>
					</shiro:hasPermission>
				</td> --%>
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