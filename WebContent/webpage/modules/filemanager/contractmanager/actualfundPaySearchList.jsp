<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>实际资金管理</title>
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
		<h5>实际资金列表 </h5>
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
	<form:form id="searchForm" modelAttribute="actualfundSearch" action="${ctx}/contractmanager/actualfundSearch/list?fundnature=2" method="post" class="form-inline">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<table:sortColumn id="orderBy" name="orderBy" value="${page.orderBy}" callback="sortOrRefresh();"/><!-- 支持排序 -->
		<div class="form-group">
			<span>合同名称：</span>
			<form:input path="contractname" htmlEscape="false" maxlength="20"  class=" form-control input-sm"/>
			<span>合同编号：</span>
			<form:input path="contractno" htmlEscape="false" maxlength="20"  class=" form-control input-sm"/>
			<span>合同类别：</span>
			<form:select path="contractclassid" class="form-control required">
				<form:option value="" label=""/>
				<form:options items="${fns:getDictList('contractmanager_contractclass')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
			</form:select>
			<span>供应商名称：</span>
			<form:input path="customername" htmlEscape="false" maxlength="20"  class=" form-control input-sm"/>
		 </div>	
	</form:form>
	<br/>
	</div>
	</div>
	
	<!-- 工具栏 -->
	<div class="row">
	<div class="col-sm-12">
		<div class="pull-left">
			<shiro:hasPermission name="contractmanager:actualfundSearch:add">
				<table:addRow url="${ctx}/contractmanager/actualfundSearch/form" title="实际资金"></table:addRow><!-- 增加按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="contractmanager:actualfundSearch:edit">
			    <table:editRow url="${ctx}/contractmanager/actualfundSearch/form" title="实际资金" id="contentTable"></table:editRow><!-- 编辑按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="contractmanager:actualfundSearch:del">
				<table:delRow url="${ctx}/contractmanager/actualfundSearch/deleteAll" id="contentTable"></table:delRow><!-- 删除按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="contractmanager:actualfundSearch:import">
				<table:importExcel url="${ctx}/contractmanager/actualfundSearch/import"></table:importExcel><!-- 导入按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="contractmanager:actualfundSearch:export">
	       		<table:exportExcel url="${ctx}/contractmanager/actualfundSearch/export"></table:exportExcel><!-- 导出按钮 -->
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
				<th  class="sort-column money">实际金额</th>
				<th  class="sort-column contractname">合同名称</th>
				<th  class="sort-column contractno">合同编号</th>
				<th  class="sort-column contractclass">合同类别</th>
				<th  class="sort-column settlementdate">结算日期</th>
				<th  class="sort-column customername">供应商名称</th>
				<th  class="">备用1</th>
				<th  class="">备用2</th>
				<!-- <th>操作</th> -->
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="actualfundSearch">
			<tr>
				<td> <input type="checkbox" id="${actualfundSearch.id}" class="i-checks"></td>
				<td>${actualfundSearch.money}</td>
				<td>
					<a href="#" onclick="openDialogView('查看信息', '${ctx}/contractmanager/contractManager/view?id=${actualfundSearch.contractid}','950px', '720px')" class="" >${actualfundSearch.contractname}</a>
				</td>
				<td>
					${actualfundSearch.contractno}
				</td>
				<td>
					${actualfundSearch.contractclass}
				</td>
				<td>
					<fmt:formatDate value="${actualfundSearch.settlementdate}" type="both" pattern="yyyy-MM-dd"/>
				</td>
				<td>
					${actualfundSearch.customername}
				</td>
				<td>
					${actualfundSearch.remark1}
				</td>
				<td>
					${actualfundSearch.remark2}
				</td>
				<%-- <td>
					<shiro:hasPermission name="contractmanager:actualfundSearch:view">
						<a href="#" onclick="openDialogView('查看实际资金', '${ctx}/contractmanager/actualfundSearch/form?id=${actualfundSearch.id}','800px', '500px')" class="btn btn-info btn-xs" ><i class="fa fa-search-plus"></i> 查看</a>
					</shiro:hasPermission>
					<shiro:hasPermission name="contractmanager:actualfundSearch:edit">
    					<a href="#" onclick="openDialog('修改实际资金', '${ctx}/contractmanager/actualfundSearch/form?id=${actualfundSearch.id}','800px', '500px')" class="btn btn-success btn-xs" ><i class="fa fa-edit"></i> 修改</a>
    				</shiro:hasPermission>
    				<shiro:hasPermission name="contractmanager:actualfundSearch:del">
						<a href="${ctx}/contractmanager/actualfundSearch/delete?id=${actualfundSearch.id}" onclick="return confirmx('确认要删除该实际资金吗？', this.href)"   class="btn btn-danger btn-xs"><i class="fa fa-trash"></i> 删除</a>
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