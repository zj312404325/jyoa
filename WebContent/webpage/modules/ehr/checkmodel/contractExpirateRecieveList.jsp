<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>合同到期人员考核申请管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
		});
	</script>
</head>
<body class="hideScroll">
	<div class="wrapper wrapper-content">

	<sys:message content="${message}"/>
	
	<!--查询条件-->
	<div class="row">
	<div class="col-sm-12">
	<form:form id="searchForm" modelAttribute="contractExpirate" action="${ctx}/checkmodel/contractExpirate/list?category=recieve" method="post" class="form-inline">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<table:sortColumn id="orderBy" name="orderBy" value="${page.orderBy}" callback="sortOrRefresh();"/><!-- 支持排序 -->
		<div class="form-group">
			<span>员工编号：</span>
			<form:input path="userno" htmlEscape="false" maxlength="10"  class=" form-control input-sm"/>
			<span>员工姓名：</span>
			<form:input path="username" htmlEscape="false" maxlength="10"  class=" form-control input-sm"/>

			<div class="pull-right" style="padding-left: 20px">
				<button  class="btn btn-primary btn-rounded btn-outline btn-sm " onclick="search()" ><i class="fa fa-search"></i> 查询</button>
				<button  class="btn btn-primary btn-rounded btn-outline btn-sm " onclick="resetform()" ><i class="fa fa-refresh"></i> 重置</button>
			</div>

		 </div>
	</form:form>
	<br/>
	</div>
	</div>
	
	<!-- 工具栏 -->
	<div class="row">
	<div class="col-sm-12">
		<div class="pull-left">
			<%-- <shiro:hasPermission name="checkmodel:contractExpirate:add">
				<table:addRow url="${ctx}/checkmodel/contractExpirate/form" title="合同到期人员考核申请"></table:addRow><!-- 增加按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="checkmodel:contractExpirate:del">
				<table:delRow url="${ctx}/checkmodel/contractExpirate/deleteAll" id="contentTable"></table:delRow><!-- 删除按钮 -->
			</shiro:hasPermission> --%>
	       <%--<button class="btn btn-white btn-sm " data-toggle="tooltip" data-placement="left" onclick="sortOrRefresh()" title="刷新"><i class="glyphicon glyphicon-repeat"></i> 刷新</button>--%>
		
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
				<th  class="sort-column username">被考核人名字</th>
				<th  class="sort-column officename">被考核人部门</th>
				<th  class="sort-column create_date">创建时间</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="contractExpirate">
			<tr>
				<td><a  href="#" onclick="openDialogView('查看合同到期人员考核申请', '${ctx}/checkmodel/contractExpirate/form?id=${contractExpirate.id}&type=1','900px', '80%')">
					${contractExpirate.username}</a>
				</td>
				<td>
					${contractExpirate.officename}
				</td>
				<td>
					<fmt:formatDate value="${contractExpirate.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					<shiro:hasPermission name="checkmodel:contractExpirate:view">
						<a href="#" onclick="openDialogView('查看合同到期人员考核申请', '${ctx}/checkmodel/contractExpirate/form?id=${contractExpirate.id}&type=1','900px', '80%')" class="btn btn-info btn-xs" ><i class="fa fa-search-plus"></i> 查看</a>
					</shiro:hasPermission>
					<c:if test="${commonuser==null }">
						<shiro:hasPermission name="checkmodel:contractExpirate:edit">
	    					<a href="#" onclick="openDialog('修改合同到期人员考核申请', '${ctx}/checkmodel/contractExpirate/form?id=${contractExpirate.id}&type=1','900px', '80%')" class="btn btn-success btn-xs" ><i class="fa fa-edit"></i> 修改</a>
	    				</shiro:hasPermission>
					</c:if>
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
</body>
</html>