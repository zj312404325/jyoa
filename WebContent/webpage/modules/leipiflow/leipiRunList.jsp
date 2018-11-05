<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>流程运行表管理</title>
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
		<h5>流程运行表列表 </h5>
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
	<form:form id="searchForm" modelAttribute="leipiRun" action="${ctx}/leipiflow/leipiRun/" method="post" class="form-inline">
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
			<shiro:hasPermission name="leipiflow:leipiRun:add">
				<table:addRow url="${ctx}/leipiflow/leipiRun/form" title="流程运行表"></table:addRow><!-- 增加按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="leipiflow:leipiRun:edit">
			    <table:editRow url="${ctx}/leipiflow/leipiRun/form" title="流程运行表" id="contentTable"></table:editRow><!-- 编辑按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="leipiflow:leipiRun:del">
				<table:delRow url="${ctx}/leipiflow/leipiRun/deleteAll" id="contentTable"></table:delRow><!-- 删除按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="leipiflow:leipiRun:import">
				<table:importExcel url="${ctx}/leipiflow/leipiRun/import"></table:importExcel><!-- 导入按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="leipiflow:leipiRun:export">
	       		<table:exportExcel url="${ctx}/leipiflow/leipiRun/export"></table:exportExcel><!-- 导出按钮 -->
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
				<th  class="sort-column flowId">流程id 正常流程</th>
				<th  class="sort-column runName">公文名称</th>
				<th  class="sort-column runFlowId">流转到什么流程 最新流程，查询优化，进入子流程时将简化查询，子流程与父流程同步</th>
				<th  class="sort-column runFlowProcess">流转到第几步</th>
				<th  class="sort-column attIds">公文附件ids</th>
				<th  class="sort-column endtime">结束时间</th>
				<th  class="sort-column status">公文导入的状态，0未审核，1通过,2不通过</th>
				<th  class="sort-column isdel">是否删除</th>
				<th  class="sort-column updatetime">更新时间</th>
				<th  class="sort-column dateline">结束时间</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="leipiRun">
			<tr>
				<td> <input type="checkbox" id="${leipiRun.id}" class="i-checks"></td>
				<td><a  href="#" onclick="openDialogView('查看流程运行表', '${ctx}/leipiflow/leipiRun/form?id=${leipiRun.id}','800px', '500px')">
					${leipiRun.flowId}
				</a></td>
				<td>
					${leipiRun.runName}
				</td>
				<td>
					${leipiRun.runFlowId}
				</td>
				<td>
					${leipiRun.runFlowProcess}
				</td>
				<td>
					${leipiRun.attIds}
				</td>
				<td>
					<fmt:formatDate value="${leipiRun.endtime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${leipiRun.status}
				</td>
				<td>
					${leipiRun.isdel}
				</td>
				<td>
					<fmt:formatDate value="${leipiRun.updatetime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					<fmt:formatDate value="${leipiRun.dateline}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					<shiro:hasPermission name="leipiflow:leipiRun:view">
						<a href="#" onclick="openDialogView('查看流程运行表', '${ctx}/leipiflow/leipiRun/form?id=${leipiRun.id}','800px', '500px')" class="btn btn-info btn-xs" ><i class="fa fa-search-plus"></i> 查看</a>
					</shiro:hasPermission>
					<shiro:hasPermission name="leipiflow:leipiRun:edit">
    					<a href="#" onclick="openDialog('修改流程运行表', '${ctx}/leipiflow/leipiRun/form?id=${leipiRun.id}','800px', '500px')" class="btn btn-success btn-xs" ><i class="fa fa-edit"></i> 修改</a>
    				</shiro:hasPermission>
    				<shiro:hasPermission name="leipiflow:leipiRun:del">
						<a href="${ctx}/leipiflow/leipiRun/delete?id=${leipiRun.id}" onclick="return confirmx('确认要删除该流程运行表吗？', this.href)"   class="btn btn-danger btn-xs"><i class="fa fa-trash"></i> 删除</a>
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