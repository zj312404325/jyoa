<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>考核时间管理</title>
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
		<h5>考核时间列表 </h5>
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
	<form:form id="searchForm" modelAttribute="checkTime" action="${ctx}/checkmodel/checkTime/" method="post" class="form-inline">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<table:sortColumn id="orderBy" name="orderBy" value="${page.orderBy}" callback="sortOrRefresh();"/><!-- 支持排序 -->
		<div class="form-group">
		 </div>	
	</form:form>
	<br/>
	</div>
	</div>
	
	<!-- 工具栏 -->
	<div class="row">
	<div class="col-sm-12">
		<div class="pull-left">
			<shiro:hasPermission name="checkmodel:checkTime:add">
				<table:addRow url="${ctx}/checkmodel/checkTime/form" title="考核时间"></table:addRow><!-- 增加按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="checkmodel:checkTime:edit">
			    <table:editRow url="${ctx}/checkmodel/checkTime/form" title="考核时间" id="contentTable"></table:editRow><!-- 编辑按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="checkmodel:checkTime:del">
				<table:delRow url="${ctx}/checkmodel/checkTime/deleteAll" id="contentTable"></table:delRow><!-- 删除按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="checkmodel:checkTime:import">
				<table:importExcel url="${ctx}/checkmodel/checkTime/import"></table:importExcel><!-- 导入按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="checkmodel:checkTime:export">
	       		<table:exportExcel url="${ctx}/checkmodel/checkTime/export"></table:exportExcel><!-- 导出按钮 -->
	       	</shiro:hasPermission>
	       <button class="btn btn-white btn-sm " data-toggle="tooltip" data-placement="left" onclick="sortOrRefresh()" title="刷新"><i class="glyphicon glyphicon-repeat"></i> 刷新</button>
		
			</div>
	</div>
	</div>
	
	<!-- 表格 -->
	<table id="contentTable" class="table table-striped table-bordered table-hover table-condensed dataTables-example dataTable">
		<thead>
			<tr>
				<th> <input type="checkbox" class="i-checks"></th>
				<th  class="sort-column checkYear">年份</th>
				<th  class="sort-column checkQuarter">季度</th>
				<th  class="sort-column startDate">考核开始时间</th>
				<th  class="sort-column enddate">考核结束时间</th>
				<th  class="">创建者</th>
				<th  class="sort-column create_date">创建时间</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="checkTime">
			<tr>
				<td> <input type="checkbox" id="${checkTime.id}" class="i-checks"></td>
				
				<td><a  href="#" onclick="openDialogView('查看考核时间', '${ctx}/checkmodel/checkTime/form?id=${checkTime.id}','800px', '500px')">
					${checkTime.checkYear}
				</a>
				</td>
				<td>
					${checkTime.checkQuarter}
				</td>
				<td>
					<fmt:formatDate value="${checkTime.startDate}" pattern="yyyy-MM-dd"/>
				</td>
				<td>
					<fmt:formatDate value="${checkTime.enddate}" pattern="yyyy-MM-dd"/>
				</td>
				<td>${checkTime.createusername}</td>
				<td>
					<fmt:formatDate value="${checkTime.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					<shiro:hasPermission name="checkmodel:checkTime:view">
						<a href="#" onclick="openDialogView('查看考核时间', '${ctx}/checkmodel/checkTime/form?id=${checkTime.id}','800px', '500px')" class="btn btn-info btn-xs" ><i class="fa fa-search-plus"></i> 查看</a>
					</shiro:hasPermission>
					<shiro:hasPermission name="checkmodel:checkTime:edit">
    					<a href="#" onclick="openDialog('修改考核时间', '${ctx}/checkmodel/checkTime/form?id=${checkTime.id}','800px', '500px')" class="btn btn-success btn-xs" ><i class="fa fa-edit"></i> 修改</a>
    				</shiro:hasPermission>
    				<shiro:hasPermission name="checkmodel:checkTime:del">
						<a href="${ctx}/checkmodel/checkTime/delete?id=${checkTime.id}" onclick="return confirmx('确认要删除该考核时间吗？', this.href)"   class="btn btn-danger btn-xs"><i class="fa fa-trash"></i> 删除</a>
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