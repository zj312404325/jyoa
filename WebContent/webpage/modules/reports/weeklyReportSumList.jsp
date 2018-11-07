<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>周报导出预览</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
        function reset1(){//重置，页码清零
			$("#pageNo").val(0);
            $("#searchForm div.form-group input").val("");
            $("#searchForm div.form-group select").val("");
            $("#searchForm div input.form-control").val("");
            $("#searchForm").submit();
            return false;
        }
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
<body class="hideScroll">
	<div class="panel-body">
	<sys:message content="${message}"/>

	<!--查询条件-->
	<form:form class="form-inline">
		<table:sortColumn id="orderBy" name="orderBy" value="${page.orderBy}" callback="sortOrRefresh();"/><!-- 支持排序 -->
		<div class="form-group">
			<span>开始时间：</span>
			<input id="startdate" name="startdate" disabled="disabled" type="text" maxlength="20" class="laydate-icon form-control layer-date required"
				   value="<fmt:formatDate value="${weeklyReport.startdate}" pattern="yyyy-MM-dd"/>"/>
			<span>结束时间：</span>
			<input id="enddate" name="enddate" disabled="disabled" type="text" maxlength="20" class="laydate-icon form-control layer-date required"
				   value="<fmt:formatDate value="${weeklyReport.enddate}" pattern="yyyy-MM-dd"/>"/>
		</div>
	</form:form>
	
	<!-- 表格 -->
	<table id="contentTable" class="table table-striped table-bordered table-hover table-condensed dataTables-example dataTable">
		<thead>
			<tr>
				<th width="50">姓名</th>
				<th width="50">项目</th>
				<th width="50">合计花费时间(天)</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${weeklyReportDetail}" var="weeklyReportDetail">
			<tr>
				<td width="50">
					${weeklyReportDetail.excelusername}
				</td>
				<td width="50">
					${weeklyReportDetail.project}
				</td>
				<td width="50">
					${weeklyReportDetail.days}
				</td>

			</tr>
		</c:forEach>
		</tbody>
	</table>
	
		<!-- 分页代码 -->
	<%--<table:page page="${page}"></table:page>--%>
	<br/>
	<br/>

</div>
</body>
</html>