<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>运行流程sign管理</title>
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
		<h5>运行流程sign列表 </h5>
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
	<form:form id="searchForm" modelAttribute="leipiRunSign" action="${ctx}/leipiflow/leipiRunSign/" method="post" class="form-inline">
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
			<shiro:hasPermission name="leipiflow:leipiRunSign:add">
				<table:addRow url="${ctx}/leipiflow/leipiRunSign/form" title="运行流程sign"></table:addRow><!-- 增加按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="leipiflow:leipiRunSign:edit">
			    <table:editRow url="${ctx}/leipiflow/leipiRunSign/form" title="运行流程sign" id="contentTable"></table:editRow><!-- 编辑按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="leipiflow:leipiRunSign:del">
				<table:delRow url="${ctx}/leipiflow/leipiRunSign/deleteAll" id="contentTable"></table:delRow><!-- 删除按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="leipiflow:leipiRunSign:import">
				<table:importExcel url="${ctx}/leipiflow/leipiRunSign/import"></table:importExcel><!-- 导入按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="leipiflow:leipiRunSign:export">
	       		<table:exportExcel url="${ctx}/leipiflow/leipiRunSign/export"></table:exportExcel><!-- 导出按钮 -->
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
				<th  class="sort-column content">会签内容</th>
				<th  class="sort-column signAttId">DEFAULT 0</th>
				<th  class="sort-column signLook">步骤设置的会签可见性,0总是可见（默认）,1本步骤经办人之间不可见2针对其他步骤不可见</th>
				<th  class="sort-column dateline">添加时间</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="leipiRunSign">
			<tr>
				<td> <input type="checkbox" id="${leipiRunSign.id}" class="i-checks"></td>
				<td><a  href="#" onclick="openDialogView('查看运行流程sign', '${ctx}/leipiflow/leipiRunSign/form?id=${leipiRunSign.id}','800px', '500px')">
					${leipiRunSign.content}
				</a></td>
				<td>
					${leipiRunSign.signAttId}
				</td>
				<td>
					${leipiRunSign.signLook}
				</td>
				<td>
					${leipiRunSign.dateline}
				</td>
				<td>
					<shiro:hasPermission name="leipiflow:leipiRunSign:view">
						<a href="#" onclick="openDialogView('查看运行流程sign', '${ctx}/leipiflow/leipiRunSign/form?id=${leipiRunSign.id}','800px', '500px')" class="btn btn-info btn-xs" ><i class="fa fa-search-plus"></i> 查看</a>
					</shiro:hasPermission>
					<shiro:hasPermission name="leipiflow:leipiRunSign:edit">
    					<a href="#" onclick="openDialog('修改运行流程sign', '${ctx}/leipiflow/leipiRunSign/form?id=${leipiRunSign.id}','800px', '500px')" class="btn btn-success btn-xs" ><i class="fa fa-edit"></i> 修改</a>
    				</shiro:hasPermission>
    				<shiro:hasPermission name="leipiflow:leipiRunSign:del">
						<a href="${ctx}/leipiflow/leipiRunSign/delete?id=${leipiRunSign.id}" onclick="return confirmx('确认要删除该运行流程sign吗？', this.href)"   class="btn btn-danger btn-xs"><i class="fa fa-trash"></i> 删除</a>
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