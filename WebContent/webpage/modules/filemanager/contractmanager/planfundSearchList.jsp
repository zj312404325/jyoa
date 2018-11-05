<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>计划资金查询管理</title>
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
		<h5>计划资金查询列表 </h5>
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
	<form:form id="searchForm" modelAttribute="planfundSearch" action="${ctx}/contractmanager/planfundSearch/list?fundnature=1" method="post" class="form-inline">
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
			<span>客户名称：</span>
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
			<shiro:hasPermission name="contractmanager:planfundSearch:add">
				<table:addRow url="${ctx}/contractmanager/planfundSearch/form" title="计划资金查询"></table:addRow><!-- 增加按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="contractmanager:planfundSearch:edit">
			    <table:editRow url="${ctx}/contractmanager/planfundSearch/form" title="计划资金查询" id="contentTable"></table:editRow><!-- 编辑按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="contractmanager:planfundSearch:del">
				<table:delRow url="${ctx}/contractmanager/planfundSearch/deleteAll" id="contentTable"></table:delRow><!-- 删除按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="contractmanager:planfundSearch:import">
				<table:importExcel url="${ctx}/contractmanager/planfundSearch/import"></table:importExcel><!-- 导入按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="contractmanager:planfundSearch:export">
	       		<table:exportExcel url="${ctx}/contractmanager/planfundSearch/export"></table:exportExcel><!-- 导出按钮 -->
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
				<th  class="sort-column planfundname">计划收款名称</th>
				<th  class="sort-column contractname">合同名称</th>
				<th  class="sort-column contractno">合同编号</th>
				<th  class="sort-column contractclass">合同类别</th>
				<th  class="sort-column customername">客户名称</th>
				<th  class="sort-column money">计划收款金额</th>
				<th  class="">余额</th>
				<th  class="">完成比例</th>
				<th  class="">逾期（天）</th>
				<th  class="sort-column customerclass">客户类别</th>
				<th  class="sort-column currency">币种</th>
				<th  class="">已完成金额</th>
				<th  class="sort-column settlement">结算方式</th>
				<th  class="sort-column remark">备注</th>
				<!-- <th>操作</th> -->
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="planfundSearch">
			<tr>
				<td> <input type="checkbox" id="${planfundSearch.id}" class="i-checks"></td>
				<td>${planfundSearch.planfundname}</td>
				<td>
					<a href="#" onclick="openDialogView('查看信息', '${ctx}/contractmanager/contractManager/view?id=${planfundSearch.contractid}','950px', '720px')" class="" >${planfundSearch.contractname}</a>
				</td>
				<td>
					${planfundSearch.contractno}
				</td>
				<td>
					${planfundSearch.contractclass}
				</td>
				<td>
					${planfundSearch.customername}
				</td>
				<td>
					${planfundSearch.money}
				</td>
				<td>
					${planfundSearch.balance}
				</td>
				<td>
					${planfundSearch.completerate}
				</td>
				<td>
					${planfundSearch.overdue}
				</td>
				<td>
					${planfundSearch.customerclass}
				</td>
				<td>
					${planfundSearch.currency}
				</td>
				<td>
					${planfundSearch.completemoney}
				</td>
				<td>
					${fns:getDictLabel(planfundSearch.settlementid, 'contractmanager_settlement', '')}
				</td>
				<td>
					${planfundSearch.remark}
				</td>
				<%-- <td>
					<shiro:hasPermission name="contractmanager:planfundSearch:view">
						<a href="#" onclick="openDialogView('查看计划资金查询', '${ctx}/contractmanager/planfundSearch/form?id=${planfundSearch.id}','800px', '500px')" class="btn btn-info btn-xs" ><i class="fa fa-search-plus"></i> 查看</a>
					</shiro:hasPermission>
					<shiro:hasPermission name="contractmanager:planfundSearch:edit">
    					<a href="#" onclick="openDialog('修改计划资金查询', '${ctx}/contractmanager/planfundSearch/form?id=${planfundSearch.id}','800px', '500px')" class="btn btn-success btn-xs" ><i class="fa fa-edit"></i> 修改</a>
    				</shiro:hasPermission>
    				<shiro:hasPermission name="contractmanager:planfundSearch:del">
						<a href="${ctx}/contractmanager/planfundSearch/delete?id=${planfundSearch.id}" onclick="return confirmx('确认要删除该计划资金查询吗？', this.href)"   class="btn btn-danger btn-xs"><i class="fa fa-trash"></i> 删除</a>
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