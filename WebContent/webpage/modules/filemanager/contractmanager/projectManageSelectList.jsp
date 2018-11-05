<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>项目管理管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
		});
		function selectone(id,name){
			 window.parent.$("#projectid").val(id);
			 window.parent.$("#project").val(name);
			 window.parent.layer.closeAll();
			 return false;
		}
	</script>
</head>
<body class="gray-bg">
	<div class="wrapper wrapper-content">
	<div class="ibox">
	<!-- <div class="ibox-title">
		<h5>项目管理列表 </h5>
		<div class="ibox-tools">
			<a class="collapse-link">
				<i class="fa fa-chevron-up"></i>
			</a>
		</div>
	</div> -->
    
    <div class="ibox-content">
	
	<!--查询条件-->
	<div class="row">
	<div class="col-sm-12">
	<form:form id="searchForm" modelAttribute="projectManage" action="${ctx}/filemanagement/projectManage/selectlist" method="post" class="form-inline">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<table:sortColumn id="orderBy" name="orderBy" value="${page.orderBy}" callback="sortOrRefresh();"/><!-- 支持排序 -->
		<div class="form-group">
			<span>项目名称：</span>
			<form:input path="projectname" htmlEscape="false" maxlength="30"    class="form-control "/>
			<div class="pull-right" style="padding-left: 20px;">
					<button  class="btn btn-primary btn-rounded btn-outline btn-sm " onclick="search()" ><i class="fa fa-search"></i> 查询</button>
					<button  class="btn btn-primary btn-rounded btn-outline btn-sm " onclick="resetform()" ><i class="fa fa-refresh"></i> 重置</button>
				</div>
		 </div>	
	</form:form>
	<br/>
	</div>
	</div>
	
	<!-- 表格 -->
	<table id="contentTable" class="table table-striped table-bordered table-hover table-condensed dataTables-example dataTable">
		<thead>
			<tr>
				<th  class="sort-column projectname">项目名称</th>
				<th  class="sort-column projectno">项目编号</th>
				<th  class="sort-column projectcategory">项目类别</th>
				<th  class="sort-column officalname">负责人姓名</th>
				<th  class="sort-column state">项目状态</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="projectManage">
			<tr>
				<td>${projectManage.projectname}</td>
				<td>
					${projectManage.projectno}
				</td>
				<td>
					${fns:getDictLabel(projectManage.projectcategory, 'projectcategory', '')}
				</td>
				<td>
					${projectManage.officalname}
				</td>
				<td>
					${fns:getDictLabel(projectManage.state, 'projectstate', '')}
				</td>
				<td>
						<a href="javascript:" onclick="javascript:selectone('${projectManage.id}','${projectManage.projectname}')" class="btn btn-info btn-xs" ><i class="fa fa-check"></i>选择</a>
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