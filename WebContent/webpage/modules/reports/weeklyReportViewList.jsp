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
	
	<!-- 表格 -->
	<table id="contentTable" class="table table-striped table-bordered table-hover table-condensed dataTables-example dataTable">
		<thead>
			<tr>
				<th>周报标题</th>
				<th>姓名</th>
				<th>用户编号</th>
				<th>部门名称</th>
				<th>岗位名称</th>
				<th>提交时间</th>
				<th>项目</th>
				<th>任务类型</th>
				<th>任务描述</th>
				<th>内容</th>
				<th>任务状态</th>
				<th>花费时间(天)</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${weeklyReportDetail}" var="weeklyReportDetail">
			<tr>
				<td>
					${weeklyReportDetail.exceltitle}
				</td>
				<td>
					${weeklyReportDetail.excelusername}
				</td>
				<td>
					${weeklyReportDetail.exceluserno}
				</td>
				<td>
					${weeklyReportDetail.excelofficename}
				</td>
				<td>
					${weeklyReportDetail.excelstationname}
				</td>
				<td>
					<fmt:formatDate value="${weeklyReportDetail.exceldate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${weeklyReportDetail.project}
				</td>
				<td>
					${weeklyReportDetail.tasktype}
				</td>
				<td>
					${weeklyReportDetail.taskdesc}
				</td>
				<td>
					${weeklyReportDetail.content}
				</td>
				<td>
					${weeklyReportDetail.taskstatus}
				</td>
				<td>
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