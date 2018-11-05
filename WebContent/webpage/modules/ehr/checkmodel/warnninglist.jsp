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
	<form:form id="searchForm" modelAttribute="contractExpirate" action="${ctx}/checkmodel/contractExpirate/contractWarnninglist" method="post" class="form-inline">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<table:sortColumn id="orderBy" name="orderBy" value="${page.orderBy}" callback="sortOrRefresh();"/><!-- 支持排序 -->
		<div class="form-group">
		 </div>
	</form:form>
	</div>
	</div>
	
	<!-- 表格 -->
	<table id="contentTable" class="table table-striped table-bordered table-hover table-condensed dataTables-example dataTable">
		<thead>
			<tr>
				<!-- <th> <input type="checkbox" class="i-checks"></th> -->
				<th  class="sort-column username">员工姓名</th>
				<th  class="sort-column officename">部门</th>
				<%--<th  class="sort-column stationname">岗位</th>--%>
				<th  class="sort-column">合同日期</th>
				<th  class="sort-column">合同</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="contractExpirate">
			<tr>
				<td>
					${contractExpirate.username}
				</td>
				<td>${contractExpirate.officename}</td>
				<%--<td>
					${contractExpirate.stationname}
				</td>--%>
				<td>
					<fmt:formatDate value="${contractExpirate.startdate}" pattern="yyyy-MM-dd"/>至<fmt:formatDate value="${contractExpirate.enddate}" pattern="yyyy-MM-dd"/>
				</td>
				<td>
					<a href="javascript:" vl="${contractExpirate.contract}" onclick="commonFileDownLoad(this)" ><i class="glyphicon glyphicon-save"></i>合同下载</a>
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