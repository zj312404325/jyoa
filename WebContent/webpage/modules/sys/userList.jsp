<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>用户管理</title>
	<meta name="decorator" content="default"/>
</head>
<body>
	<div class="wrapper wrapper-content">
    <sys:message content="${message}"/>
		<!-- 查询条件 -->
	<div class="form-group">
	<div class="row">
	<form:form id="searchForm" modelAttribute="user" action="${ctx}/sys/user/list" method="post" class="form-horizontal">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input id="isAdmin" name="isAdmin" type="hidden" value="${isAdmin}"/>
		<table:sortColumn id="orderBy" name="orderBy" value="${page.orderBy}" callback="sortOrRefresh();"/><!-- 支持排序 -->
		
			<%--<label class="col-sm-1 control-label">归属公司：</label>
			<div class="col-sm-2">
				<sys:treeselect id="company" name="company.id" value="${user.company.id}" labelName="company.name" labelValue="${user.company.name}" 
				title="公司" url="/sys/office/treeData?type=1" cssClass=" form-control input-sm" allowClear="true"/>
			</div>--%>
			<label class="col-sm-1 control-label">归属部门：</label>
			<div class="col-sm-2">
				<sys:treeselect id="office" name="office.id" value="${user.office.id}" labelName="office.name" labelValue="${user.office.name}"
								title="部门" url="/sys/office/treeData?type=2" cssClass=" form-control input-sm" allowClear="true" notAllowSelectParent="false"/>
			</div>
			<label class="col-sm-1 control-label">工号：</label>
			<div class="col-sm-2">
				<form:input path="no" htmlEscape="false" maxlength="50" class=" form-control input-sm"/>
			</div>
			<label class="col-sm-1 control-label">登录名：</label>
			<div class="col-sm-2">
				<form:input path="loginName" htmlEscape="false" maxlength="50" class=" form-control input-sm"/>
			</div>

			<label class="col-sm-1 control-label">姓&nbsp;&nbsp;&nbsp;名：</label>
			<div class="col-sm-2">
				<form:input path="name" htmlEscape="false" maxlength="50" class=" form-control input-sm"/>
			</div>
		
		 	
	</form:form>
	<br/>
	</div>
	</div>
	
	<!-- 工具栏 -->
	<div class="row">
	<div class="col-sm-12">
		<div class="pull-left">
			<shiro:hasPermission name="sys:user:add">
				<table:addRow url="${ctx}/sys/user/form?office.id=${user.office.id}&company.id=${user.company.id}" title="用户" width="800px" height="625px" target="userContent"></table:addRow><!-- 增加按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="sys:user:edit">
			    <table:editRow url="${ctx}/sys/user/form" id="contentTable"  title="用户" width="800px" height="680px" target="userContent"></table:editRow><!-- 编辑按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="sys:user:del">
				<table:delRow url="${ctx}/sys/user/deleteAll" id="contentTable"></table:delRow><!-- 删除按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="sys:user:import">
				<table:importExcel url="${ctx}/sys/user/import"></table:importExcel><!-- 导入按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="sys:user:export">
	       		<table:exportExcel url="${ctx}/sys/user/export"></table:exportExcel><!-- 导出按钮 -->
	       </shiro:hasPermission>
	       <button class="btn btn-white btn-sm " data-toggle="tooltip" data-placement="left" onclick="sortOrRefresh()" title="刷新"><i class="glyphicon glyphicon-repeat"></i> 刷新</button>
		
			</div>
		<div class="pull-right">
			<button  class="btn btn-primary btn-rounded btn-outline btn-sm " onclick="search()" ><i class="fa fa-search"></i> 查询</button>
			<button  class="btn btn-primary btn-rounded btn-outline btn-sm " onclick="reset()" ><i class="fa fa-refresh"></i> 重置</button>
		</div>
	</div>
	</div>
	
	<table id="contentTable" class="table table-striped table-bordered table-hover table-condensed dataTables-example dataTable">
		<thead>
			<tr>
				<th><input type="checkbox" class="i-checks"></th>
				<th class="sort-column login_name">登录名</th>
				<th class="sort-column name">姓名</th>
				<th class="sort-column phone">电话</th>
				<th class="sort-column mobile">手机</th>
				<th class="sort-column c.name">归属公司</th>
				<th class="sort-column officeTrueName">归属部门</th>
				<th class="sort-column o.name">归属岗位</th>
				<%--<th>用户组</th>--%>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="user">
			<tr>
				<td> <input type="checkbox" id="${user.id}" class="i-checks"></td>
				<td><a  href="#" onclick="openDialogView('查看用户', '${ctx}/sys/user/form?id=${user.id}','800px', '680px')">${user.loginName}</a></td>
				<td>${user.name}</td>
				<td>${user.phone}</td>
				<td>${user.mobile}</td>
				<td>${user.company.name}</td>
				<td>${user.officeTrueName}</td>
				<td><%--${fns:getDictLabel(user.stationType, 'sys_station_type', '')}--%>${user.stationName}</td>
				<%--<td>${fns:getDictLabel(user.userType, 'sys_user_type', '')}</td>--%>
				<td>
					<shiro:hasPermission name="sys:user:view">
						<a href="#" onclick="openDialogView('查看用户', '${ctx}/sys/user/form?id=${user.id}','800px', '680px')" class="btn btn-info btn-xs" ><i class="fa fa-search-plus"></i> 查看</a>
					</shiro:hasPermission>
					<shiro:hasPermission name="sys:user:edit">
						<a href="#" onclick="openDialog('修改用户', '${ctx}/sys/user/form?id=${user.id}','800px', '700px', 'userContent')" class="btn btn-success btn-xs" ><i class="fa fa-edit"></i> 修改</a>
					</shiro:hasPermission>
					<shiro:hasPermission name="sys:user:del">
						<a href="${ctx}/sys/user/delete?id=${user.id}" onclick="return confirmx('确认要删除该用户吗？', this.href)" class="btn btn-danger btn-xs"><i class="fa fa-trash"></i> 删除</a>
					</shiro:hasPermission>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<table:page page="${page}"></table:page>
	</div>
</body>
</html>