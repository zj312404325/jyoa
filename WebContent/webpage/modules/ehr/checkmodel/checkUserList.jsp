<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<c:if test="${category==null||category==''||category=='0'}"><c:set var="titlestr" value="绩效" /></c:if>
	<c:if test="${category=='1'}"><c:set var="titlestr" value="转正" /></c:if>
	<title>${titlestr}考核考核人管理</title>
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
		<h5>${titlestr}考核考核人列表 </h5>
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
	<form:form id="searchForm" modelAttribute="checkUser" action="${ctx}/checkmodel/checkUser/list?category=${category}" method="post" class="form-inline">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input id="checkofficeid" name="checkofficeid" type="hidden" value="${checkofficeid}"/>
		<form:hidden path="checkofficename" htmlEscape="false" value="${checkUser.checkofficename}"/>
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
			<shiro:hasPermission name="checkmodel:checkUser:add">
				<table:addRow url="${ctx}/checkmodel/checkUser/form?checkofficeid=${checkUser.checkofficeid }&category=${category}&checkofficename=${checkUser.checkofficename }" title="${titlestr}考核考核人"></table:addRow><!-- 增加按钮 -->
			</shiro:hasPermission>
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
				<%--<th> <input type="checkbox" class="i-checks"></th>--%>
				<th  class="sort-column userNo">员工编号</th>
				<th  class="sort-column userName">员工姓名</th>
				<th  class="sort-column officeName">部门名称</th>
				<th  class="">考核部门</th>
				<th  class="sort-column stationName">考核岗位</th>
				<th  class="sort-column memo">备注</th>
				<th  class="sort-column weight">权重</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="checkUser">
			<tr>
				<%--<td> <input type="checkbox" id="${checkUser.id}" class="i-checks"></td>--%>
				<td>
				<%-- <a  href="#" onclick="openDialogView('查看绩效考核考核人', '${ctx}/checkmodel/checkUser/form?id=${checkUser.id}','800px', '500px')">
					${checkUser.userNo}
				</a> --%>
				${checkUser.userNo}
				</td>
				<td>
					${checkUser.userName}
				</td>
				<td>
					${checkUser.officeName}
				</td>
					<td>
							${checkUser.checkofficename}
					</td>
				<td>
					${checkUser.stationName}
				</td>
				<td>
					${checkUser.memo}
				</td>
				<td>
					${checkUser.weight}
				</td>
				<td>
					<shiro:hasPermission name="checkmodel:checkUser:view">
						<a href="#" onclick="openDialogView('查看${titlestr}考核考核人', '${ctx}/checkmodel/checkUser/form?id=${checkUser.id}&checkofficeid=${checkUser.checkofficeid }&category=${category}&checkofficename=${checkUser.checkofficename }&method=1','800px', '500px')" class="btn btn-info btn-xs" ><i class="fa fa-search-plus"></i> 查看</a>
					</shiro:hasPermission>
					<shiro:hasPermission name="checkmodel:checkUser:edit">
						<a href="#" onclick="openDialog('修改${titlestr}考核考核人', '${ctx}/checkmodel/checkUser/form?id=${checkUser.id}&checkofficeid=${checkUser.checkofficeid }&category=${category}&checkofficename=${checkUser.checkofficename }','800px', '500px')" class="btn btn-success btn-xs" ><i class="fa fa-edit"></i> 修改</a>
    				</shiro:hasPermission>
    				<shiro:hasPermission name="checkmodel:checkUser:del">
						<a href="${ctx}/checkmodel/checkUser/delete?id=${checkUser.id}" onclick="return confirmxParent('确认要删除该${titlestr}考核考核人吗？', this.href)"   class="btn btn-danger btn-xs"><i class="fa fa-trash"></i> 删除</a>
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