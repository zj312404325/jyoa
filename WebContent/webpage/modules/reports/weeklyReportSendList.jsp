<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>周报管理</title>
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
	<div class="">
	<sys:message content="${message}"/>
	<div class="row">
		<div class="col-sm-12">
	<!--查询条件-->
	<form:form id="searchForm" modelAttribute="weeklyReport" action="${ctx}/checkmodel/reports/?" method="post" class="form-inline">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<table:sortColumn id="orderBy" name="orderBy" value="${page.orderBy}" callback="sortOrRefresh();"/><!-- 支持排序 -->
		<div class="form-group">
			<span>开始时间：</span>
			<input id="startdate" name="startdate" type="text" maxlength="20" class="laydate-icon form-control layer-date required"
							value="<fmt:formatDate value="${weeklyReport.startdate}" pattern="yyyy-MM-dd"/>"/>
			<span>结束时间：</span>
			<input id="enddate" name="enddate" type="text" maxlength="20" class="laydate-icon form-control layer-date required"
							value="<fmt:formatDate value="${weeklyReport.enddate}" pattern="yyyy-MM-dd"/>"/>

				<div class="pull-right" style="padding-left: 20px">
					<button  class="btn btn-primary btn-rounded btn-outline btn-sm " onclick="search()" ><i class="fa fa-search"></i> 查询</button>
					<button  class="btn btn-primary btn-rounded btn-outline btn-sm " onclick="reset1()" ><i class="fa fa-refresh"></i> 重置</button>
				</div>
		 </div>	
	</form:form>
		</div>
		
	</div>
	<!-- 工具栏 -->
	<br/>
	<div class="row">
	<div class="col-sm-12">
		<div class="pull-left">
			<table:addRowJump url="${ctx}/checkmodel/reports/form?type=0&opt=add" width="80%" title="周报"></table:addRowJump><!-- 增加按钮 -->
	        <button class="btn btn-white btn-sm " data-toggle="tooltip" data-placement="left" onclick="sortOrRefresh()" title="刷新"><i class="glyphicon glyphicon-repeat"></i> 刷新</button>
		</div>

	</div>
	</div>
	
	<!-- 表格 -->
	<table id="contentTable" class="table table-striped table-bordered table-hover table-condensed dataTables-example dataTable">
		<thead>
			<tr>
				<th  class="sort-column officename">部门</th>
				<th>岗位</th>
				<th  class="sort-column create_date">填写日期</th>
				<th  class="sort-column selfscore">周报标题</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="weeklyReport">
			<tr>
				<td><a  href="#" onclick="openDialogView('查看周报', '${ctx}/checkmodel/reports/form?id=${weeklyReport.id}&bhv=1','1200px', '500px')">
					${weeklyReport.officename}
				</a></td>
				<td>
					${weeklyReport.stationname}
				</td>
				<td>
					<fmt:formatDate value="${weeklyReport.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${weeklyReport.title}
				</td>
				<td>
					<shiro:hasPermission name="checkmodel:weeklyReport:view">
						<a href="#" onclick="openDialogView('查看周报', '${ctx}/checkmodel/reports/form?id=${weeklyReport.id}&bhv=1','1200px', '500px')" class="btn btn-info btn-xs" ><i class="fa fa-search-plus"></i> 查看</a>
					</shiro:hasPermission>
					<shiro:hasPermission name="checkmodel:weeklyReport:edit">
						<a href="#" onclick="openDialog('修改周报', '${ctx}/checkmodel/reports/form?id=${weeklyReport.id}&type=0','1200px', '500px')" class="btn btn-success btn-xs" ><i class="fa fa-edit"></i> 修改</a>
					</shiro:hasPermission>
    				<shiro:hasPermission name="checkmodel:weeklyReport:del">
						<a href="${ctx}/checkmodel/reports/delete?id=${weeklyReport.id}&type=0" onclick="return confirmxParent('确认要删除该周报吗？', this.href)"   class="btn btn-danger btn-xs"><i class="fa fa-trash"></i> 删除</a>
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