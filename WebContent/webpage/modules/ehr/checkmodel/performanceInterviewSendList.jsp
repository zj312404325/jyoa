<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>绩效考核面谈表管理</title>
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
<body  class="hideScroll">
	<div class="wrapper wrapper-content">

	<sys:message content="${message}"/>
	
	<!--查询条件-->
	<div class="row">
	<div class="col-sm-12">
	<form:form id="searchForm" modelAttribute="performanceInterview" action="${ctx}/checkmodel/performanceInterview/" method="post" class="form-inline">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<table:sortColumn id="orderBy" name="orderBy" value="${page.orderBy}" callback="sortOrRefresh();"/><!-- 支持排序 -->
		<div class="form-group">
			<%-- <span>员工编号：</span>
			<form:input path="userno" htmlEscape="false" maxlength="10"  class=" form-control input-sm"/>
			<span>员工姓名：</span>
			<form:input path="username" htmlEscape="false" maxlength="10"  class=" form-control input-sm"/> --%>
			<span>开始时间：</span>
			<input id="startdate" name="startdate" type="text" maxlength="20" class="laydate-icon form-control layer-date required"
							value="<fmt:formatDate value="${performanceInterview.startdate}" pattern="yyyy-MM-dd"/>"/>
			<span>结束时间：</span>
			<input id="enddate" name="enddate" type="text" maxlength="20" class="laydate-icon form-control layer-date required"
							value="<fmt:formatDate value="${performanceInterview.enddate}" pattern="yyyy-MM-dd"/>"/>

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
			<c:if test="${canCheck }">
			<shiro:hasPermission name="checkmodel:performanceInterview:add">
				<table:addRow url="${ctx}/checkmodel/performanceInterview/form?type=0" title="绩效考核面谈表"></table:addRow><!-- 增加按钮 -->
			</shiro:hasPermission>
			<%--<shiro:hasPermission name="checkmodel:performanceInterview:edit">
			    <table:editRow url="${ctx}/checkmodel/performanceInterview/form?type=0" title="绩效考核面谈表" id="contentTable"></table:editRow><!-- 编辑按钮 -->
			</shiro:hasPermission>--%></c:if>
			<%-- <shiro:hasPermission name="checkmodel:performanceInterview:del">
				<table:delRow url="${ctx}/checkmodel/performanceInterview/deleteAll" id="contentTable"></table:delRow><!-- 删除按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="checkmodel:performanceInterview:import">
				<table:importExcel url="${ctx}/checkmodel/performanceInterview/import"></table:importExcel><!-- 导入按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="checkmodel:performanceInterview:export">
	       		<table:exportExcel url="${ctx}/checkmodel/performanceInterview/export"></table:exportExcel><!-- 导出按钮 -->
	       	</shiro:hasPermission> --%>
	       <button class="btn btn-white btn-sm " data-toggle="tooltip" data-placement="left" onclick="sortOrRefresh()" title="刷新"><i class="glyphicon glyphicon-repeat"></i> 刷新</button>
		
			</div>

	</div>
	</div>
	
	<!-- 表格 -->
	<table id="contentTable" class="table table-striped table-bordered table-hover table-condensed dataTables-example dataTable">
		<thead>
			<tr>
				<!-- <th> <input type="checkbox" class="i-checks"></th> -->
				<th  class="sort-column userno">员工编号</th>
				<th  class="sort-column username">员工姓名</th>
				<th  class="sort-column officename">部门</th>
				<th  class="sort-column stationname">岗位</th>
				<th  class="sort-column writedate">填表日期</th>
				<th  class="sort-column checkyear">年份</th>
				<th  class="sort-column checkquarter">季度</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="performanceInterview">
			<tr>
				<%-- <td> <input type="checkbox" id="${performanceInterview.id}" class="i-checks"></td> --%>
				<td>
					${performanceInterview.userno}
				</td>
				<td>
					${performanceInterview.username}
				</td>
				<td>${performanceInterview.officename}</td>
				<td>
					${performanceInterview.stationname}
				</td>
				<td>
					<fmt:formatDate value="${performanceInterview.writedate}" pattern="yyyy-MM-dd"/>
				</td>
				<td>
					${performanceInterview.checkyear}
				</td>
				<td>
					${fns:getDictLabel(performanceInterview.checkquarter, 'checkQuarter', '')}
				</td>
				<td>
					<shiro:hasPermission name="checkmodel:performanceInterview:view">
						<a href="#" onclick="openDialogView('查看绩效考核面谈表', '${ctx}/checkmodel/performanceInterview/form?id=${performanceInterview.id}&bhv=1','800px', '500px')" class="btn btn-info btn-xs" ><i class="fa fa-search-plus"></i> 查看</a>
					</shiro:hasPermission>
					<c:if test="${canCheck }">
					<shiro:hasPermission name="checkmodel:performanceInterview:edit">
    					<a href="#" onclick="openDialog('修改绩效考核面谈表', '${ctx}/checkmodel/performanceInterview/form?id=${performanceInterview.id}&type=0','800px', '500px')" class="btn btn-success btn-xs" ><i class="fa fa-edit"></i> 修改</a>
    				</shiro:hasPermission></c:if>
    				<shiro:hasPermission name="checkmodel:performanceInterview:del">
						<a href="${ctx}/checkmodel/performanceInterview/delete?id=${performanceInterview.id}&type=0" onclick="return confirmxParent('确认要删除该绩效考核面谈表吗？', this.href)"   class="btn btn-danger btn-xs"><i class="fa fa-trash"></i> 删除</a>
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
</body>
</html>