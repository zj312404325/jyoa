<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>8s检查管理</title>
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
		<h5>8s检查列表 </h5>
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
	<form:form id="searchForm" modelAttribute="hygiene" action="${ctx}/oa/hygiene/" method="post" class="form-inline">
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
			<shiro:hasPermission name="oa:hygiene:add">
				<table:addRow url="${ctx}/oa/hygiene/form" title="8s检查"></table:addRow><!-- 增加按钮 -->
			</shiro:hasPermission>
			<%--<shiro:hasPermission name="oa:hygiene:edit">
			    <table:editRow url="${ctx}/oa/hygiene/form" title="8s检查" id="contentTable"></table:editRow><!-- 编辑按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="oa:hygiene:del">
				<table:delRow url="${ctx}/oa/hygiene/deleteAll" id="contentTable"></table:delRow><!-- 删除按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="oa:hygiene:import">
				<table:importExcel url="${ctx}/oa/hygiene/import"></table:importExcel><!-- 导入按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="oa:hygiene:export">
	       		<table:exportExcel url="${ctx}/oa/hygiene/export"></table:exportExcel><!-- 导出按钮 -->
	       	</shiro:hasPermission>
	       <button class="btn btn-white btn-sm " data-toggle="tooltip" data-placement="left" onclick="sortOrRefresh()" title="刷新"><i class="glyphicon glyphicon-repeat"></i> 刷新</button>--%>
		
			</div>
		<%--<div class="pull-right">
			<button  class="btn btn-primary btn-rounded btn-outline btn-sm " onclick="search()" ><i class="fa fa-search"></i> 查询</button>
			<button  class="btn btn-primary btn-rounded btn-outline btn-sm " onclick="reset()" ><i class="fa fa-refresh"></i> 重置</button>
		</div>--%>
	</div>
	</div>
	
	<!-- 表格 -->
	<table id="contentTable" class="table table-striped table-bordered table-hover table-condensed dataTables-example dataTable">
		<thead>
		<tr>
			<th  class="">标题</th>
			<th  class="">检查组长</th>
			<th  class="sort-column checkdate">检查日期</th>
			<th>操作</th>
		</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="hygiene">
			<tr>
				<td><a  href="#" onclick="openDialogView('查看8s检查', '${ctx}/oa/hygiene/form?id=${hygiene.id}&tp=0','90%', '90%')">
					<fmt:formatDate value="${hygiene.checkdate}" pattern="yyyy-MM-dd"/>_<c:if test="${hygiene.type==0}">CS</c:if><c:if test="${hygiene.type==1}">NB</c:if>工厂8s检查表
				</a></td>
				<td>
						${hygiene.leader}
				</td>
				<td>
						<fmt:formatDate value="${hygiene.checkdate}" pattern="yyyy-MM-dd"/>
				</td>
				<td>
					<shiro:hasPermission name="oa:hygiene:view">
						<a href="#" onclick="openDialogView('查看8s检查', '${ctx}/oa/hygiene/form?id=${hygiene.id}&tp=0','90%', '90%')" class="btn btn-info btn-xs" ><i class="fa fa-search-plus"></i> 查看</a>
					</shiro:hasPermission>
					<c:if test="${fns:getUser().id==hygiene.createBy.id}">
						<shiro:hasPermission name="oa:hygiene:edit">
							<a href="#" onclick="openDialog('修改8s检查', '${ctx}/oa/hygiene/form?id=${hygiene.id}','90%', '90%')" class="btn btn-success btn-xs" ><i class="fa fa-edit"></i> 修改</a>
						</shiro:hasPermission>
					</c:if>
    				<%--<shiro:hasPermission name="oa:hygiene:del">
						<a href="${ctx}/oa/hygiene/delete?id=${hygiene.id}" onclick="return confirmx('确认要删除该8s检查吗？', this.href)"   class="btn btn-danger btn-xs"><i class="fa fa-trash"></i> 删除</a>
					</shiro:hasPermission>--%>
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