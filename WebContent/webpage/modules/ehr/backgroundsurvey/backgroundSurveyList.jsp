<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>信息管理</title>
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
		<h5>背景调查报告列表 </h5>
		<div class="ibox-tools">
			<a class="collapse-link">
				<i class="fa fa-chevron-up"></i>
			</a>
			<!-- <a class="dropdown-toggle" data-toggle="dropdown" href="#">
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
			</a> -->
		</div>
	</div>
    
    <div class="ibox-content">
	<sys:message content="${message}"/>
	
	<!--查询条件-->
	<div class="row">
	<div class="col-sm-12">
	<form:form id="searchForm" modelAttribute="backgroundSurvey" action="${ctx}/ehr/backgroundSurvey/" method="post" class="form-inline">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<table:sortColumn id="orderBy" name="orderBy" value="${page.orderBy}" callback="sortOrRefresh();"/><!-- 支持排序 -->
		<div class="form-group">
		    <span>被调查人：</span>
				<form:input path="surveyname" htmlEscape="false" maxlength="200"  class=" form-control input-sm"/>
			<span>调查人：</span>
				<form:input path="operater" htmlEscape="false" maxlength="200"  class=" form-control input-sm"/>
		    <div class="pull-right" style="padding-left: 10px;">
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
				<table:addRow url="${ctx}/ehr/backgroundSurvey/form" title="信息"></table:addRow><!-- 增加按钮 -->
			    <table:editRow url="${ctx}/ehr/backgroundSurvey/form" title="信息" id="contentTable"></table:editRow><!-- 编辑按钮 -->
				<table:delRow url="${ctx}/ehr/backgroundSurvey/deleteAll" id="contentTable"></table:delRow><!-- 删除按钮 -->
	       <button class="btn btn-white btn-sm " data-toggle="tooltip" data-placement="left" onclick="sortOrRefresh()" title="刷新"><i class="glyphicon glyphicon-repeat"></i> 刷新</button>
		
			</div>
		
	</div>
	</div>
	
	<!-- 表格 -->
	<table id="contentTable" class="table table-striped table-bordered table-hover table-condensed dataTables-example dataTable">
		<thead>
			<tr>
				<th> <input type="checkbox" class="i-checks"></th>
				<th  class="sort-column surveyname">被调查人</th>
				<th  class="sort-column depart">部门</th>
				<th  class="sort-column position">岗位</th>
				<th  class="sort-column entrydate">入职时间</th>
				<th  class="sort-column surveydate">调查时间</th>
				<th  class="sort-column operater">调查人</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="backgroundSurvey">
			<tr>
				<td> <input type="checkbox" id="${backgroundSurvey.id}" class="i-checks"></td>
				<td>
					${backgroundSurvey.surveyname}
				</td>
				<td>
					${backgroundSurvey.depart}
				</td>
				<td>
					${backgroundSurvey.position}
				</td>
				<td>
					${backgroundSurvey.entrydate}
				</td>
				<td>
					${backgroundSurvey.surveydate}
				</td>
				<td>
					${backgroundSurvey.operater}
				</td>
				<td>
						<a href="#" onclick="openDialogView('查看信息', '${ctx}/ehr/backgroundSurvey/view?id=${backgroundSurvey.id}','800px', '80%')" class="btn btn-info btn-xs" ><i class="fa fa-search-plus"></i> 查看</a>
    					<a href="#" onclick="openDialog('修改信息', '${ctx}/ehr/backgroundSurvey/form?id=${backgroundSurvey.id}','800px', '80%')" class="btn btn-success btn-xs" ><i class="fa fa-edit"></i> 修改</a>
						<a href="${ctx}/ehr/backgroundSurvey/delete?id=${backgroundSurvey.id}" onclick="return confirmx('确认要删除该信息吗？', this.href)"   class="btn btn-danger btn-xs"><i class="fa fa-trash"></i> 删除</a>
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