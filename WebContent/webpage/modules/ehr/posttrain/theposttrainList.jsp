<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>岗位培训</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
		});
	</script>
</head>
<body class="hideScroll">
	<div class="wrapper wrapper-content">
	<sys:message content="${message}"/>

					<form:form id="searchForm" modelAttribute="posttrain" action="${ctx}/ehr/posttrain/thelist" method="post" class="form-inline">
						<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
						<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
						<table:sortColumn id="orderBy" name="orderBy" value="${page.orderBy}" callback="sortOrRefresh();"/><!-- 支持排序 -->
					</form:form>
						
						<!-- 工具栏 -->
						<div class="row">
						<div class="col-sm-12">
							<div class="pull-left">
								<%-- <shiro:hasPermission name="ehr:posttrain:add">
									<table:addRow url="${ctx}/ehr/posttrain/form" title="信息"></table:addRow><!-- 增加按钮 -->
								</shiro:hasPermission>
								<shiro:hasPermission name="ehr:posttrain:edit">
								    <table:editRow url="${ctx}/ehr/posttrain/form" title="信息" id="contentTable"></table:editRow><!-- 编辑按钮 -->
								</shiro:hasPermission> --%>
								<%-- <shiro:hasPermission name="ehr:posttrain:del">
									<table:delRow url="${ctx}/ehr/posttrain/deleteAll" id="contentTable"></table:delRow><!-- 删除按钮 -->
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
									<th  class="sort-column trainer">姓名</th>
									<th  class="sort-column title">培训标题</th>
									<th  class="sort-column address">培训地点</th>
									<th  class="sort-column organizer">主办机构</th>
									<th  class="sort-column traintime">培训时间</th>
									<th  class="sort-column create_Date">创建时间</th>
									<th>操作</th>
								</tr>
							</thead>
							<tbody>
							<c:forEach items="${page.list}" var="posttrain">
								<tr>
									<td> <input type="checkbox" id="${posttrain.id}" class="i-checks"></td>
									<td>
										${posttrain.trainer}
									</td>
									<td>
										${posttrain.title}
									</td>
									<td>
										${posttrain.address}
									</td>
									<td>
										${posttrain.organizer}
									</td>
									<td>
										<fmt:formatDate pattern="yyyy年MM月dd日" value="${posttrain.traintime}" />
									</td>
									<td>
										<fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${posttrain.createDate}" />
									</td>
									<td>
										<%-- <shiro:hasPermission name="ehr:posttrain:view"> --%>
											<a href="#" onclick="openDialogView('查看信息', '${ctx}/ehr/posttrain/view?id=${posttrain.id}','800px', '500px')" class="btn btn-info btn-xs" ><i class="fa fa-search-plus"></i> 查看</a>
										<%-- </shiro:hasPermission> --%>
					    				<%-- <shiro:hasPermission name="ehr:posttrain:del">
											<a href="${ctx}/ehr/posttrain/delete?id=${posttrain.id}" onclick="return confirmx('确认要删除该信息吗？', this.href)"   class="btn btn-danger btn-xs"><i class="fa fa-trash"></i> 删除</a>
										</shiro:hasPermission> --%>
									</td>
								</tr>
							</c:forEach>
							</tbody>
						</table>
						
							<!-- 分页代码 -->
						<table:page page="${page}"></table:page>
					
	<!--查询条件-->
</div>
</body>
</html>