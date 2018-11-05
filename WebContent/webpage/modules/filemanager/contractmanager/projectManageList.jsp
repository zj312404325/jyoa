<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>项目管理管理</title>
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
	</script>
</head>
<body class="gray-bg">
	<div class="wrapper wrapper-content">
	<div class="ibox">
	<div class="ibox-title">
		<h5>项目管理列表 </h5>
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
	<form:form id="searchForm" modelAttribute="projectManage" action="${ctx}/filemanagement/projectManage/" method="post" class="form-inline">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<table:sortColumn id="orderBy" name="orderBy" value="${page.orderBy}" callback="sortOrRefresh();"/><!-- 支持排序 -->
		<div class="form-group">
			<span>项目名称：</span>
			<form:input path="projectname" htmlEscape="false" maxlength="30"    class="form-control "/>
			<span>项目编号：</span>
			<form:input path="projectno" htmlEscape="false" maxlength="30"    class="form-control "/>
			<span>项目类别：</span>
			<form:select path="projectcategory" class="form-control required">
				<form:option value="" label=""/>
				<form:options items="${fns:getDictList('projectcategory')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
			</form:select>
			<span>建立时间：</span>
			<input id="startdate" name="startdate" type="text" maxlength="20" class="laydate-icon form-control layer-date required"
							value="<fmt:formatDate value="${projectManage.startdate}" pattern="yyyy-MM-dd"/>"/>
			<span>至</span>
			<input id="enddate" name="enddate" type="text" maxlength="20" class="laydate-icon form-control layer-date required"
							value="<fmt:formatDate value="${projectManage.enddate}" pattern="yyyy-MM-dd"/>"/>
		 </div>	
	</form:form>
	<br/>
	</div>
	</div>
	
	<!-- 工具栏 -->
	<div class="row">
	<div class="col-sm-12">
		<div class="pull-left">
			<shiro:hasPermission name="filemanagement:projectManage:add">
				<table:addRow url="${ctx}/filemanagement/projectManage/form" title="项目管理"></table:addRow><!-- 增加按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="filemanagement:projectManage:edit">
			    <table:editRow url="${ctx}/filemanagement/projectManage/form" title="项目管理" id="contentTable"></table:editRow><!-- 编辑按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="filemanagement:projectManage:del">
				<table:delRow url="${ctx}/filemanagement/projectManage/deleteAll" id="contentTable"></table:delRow><!-- 删除按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="filemanagement:projectManage:import">
				<table:importExcel url="${ctx}/filemanagement/projectManage/import"></table:importExcel><!-- 导入按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="filemanagement:projectManage:export">
	       		<table:exportExcel url="${ctx}/filemanagement/projectManage/export"></table:exportExcel><!-- 导出按钮 -->
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
				<th  class="sort-column projectname">项目名称</th>
				<th  class="sort-column projectno">项目编号</th>
				<th  class="sort-column projectcategory">项目类别</th>
				<th  class="sort-column projectgather">项目预算收款</th>
				<th  class="sort-column projectpayment">项目预算付款</th>
				<th  class="sort-column backupone">备用1</th>
				<th  class="sort-column backuptwo">备用2</th>
				<th  class="sort-column officalname">负责人姓名</th>
				<th  class="sort-column state">项目状态</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="projectManage">
			<tr>
				<td> <input type="checkbox" id="${projectManage.id}" class="i-checks"></td>
				<td><a  href="#" onclick="openDialogView('查看项目管理', '${ctx}/filemanagement/projectManage/view?id=${projectManage.id}','950px', '80%')">
					${projectManage.projectname}
				</a></td>
				<td>
					${projectManage.projectno}
				</td>
				<td>
					${fns:getDictLabel(projectManage.projectcategory, 'projectcategory', '')}
				</td>
				<td>
					${projectManage.projectgather}
				</td>
				<td>
					${projectManage.projectpayment}
				</td>
				<td>
					${projectManage.backupone}
				</td>
				<td>
					${projectManage.backuptwo}
				</td>
				<td>
					${projectManage.officalname}
				</td>
				<td>
					${fns:getDictLabel(projectManage.state, 'projectstate', '')}
				</td>
				<td>
					<shiro:hasPermission name="filemanagement:projectManage:view">
						<a href="#" onclick="openDialogView('查看项目管理', '${ctx}/filemanagement/projectManage/view?id=${projectManage.id}','950px', '80%')" class="btn btn-info btn-xs" ><i class="fa fa-search-plus"></i> 查看</a>
					</shiro:hasPermission>
					<shiro:hasPermission name="filemanagement:projectManage:edit">
    					<a href="#" onclick="openDialog('修改项目管理', '${ctx}/filemanagement/projectManage/form?id=${projectManage.id}','950px', '80%')" class="btn btn-success btn-xs" ><i class="fa fa-edit"></i> 修改</a>
    				</shiro:hasPermission>
    				<shiro:hasPermission name="filemanagement:projectManage:del">
						<a href="${ctx}/filemanagement/projectManage/delete?id=${projectManage.id}" onclick="return confirmx('确认要删除该项目管理吗？', this.href)"   class="btn btn-danger btn-xs"><i class="fa fa-trash"></i> 删除</a>
					</shiro:hasPermission>
					<shiro:hasPermission name="filemanagement:projectManage:edit">
						<a href="#" onclick="openDialog('授权信息', '${ctx}/filemanagement/projectManage/authorform?id=${projectManage.id}','950px', '50%')" class="btn btn-success btn-xs" ><i class="fa fa-edit"></i> 授权</a>
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