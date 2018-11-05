<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>离职审计管理</title>
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
		<h5>离职审计列表 </h5>
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
	<form:form id="searchForm" modelAttribute="leaveaudit" action="${ctx}/ehr/leaveaudit/list" method="post" class="form-inline">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<form:hidden path="isadmin"/>
		<table:sortColumn id="orderBy" name="orderBy" value="${page.orderBy}" callback="sortOrRefresh();"/><!-- 支持排序 -->
		<div class="form-group">
            <span>离职人：</span>
            <form:input path="leaver" class="form-control"/>
            <span>开始时间：</span>
            <input id="startdate" name="startdate" type="text" maxlength="20" class="laydate-icon form-control layer-date"
                   value="<fmt:formatDate value="${leaveaudit.startdate}" pattern="yyyy-MM-dd"/>"/>
            <span>结束时间：</span>
            <input id="enddate" name="enddate" type="text" maxlength="20" class="laydate-icon form-control layer-date"
                   value="<fmt:formatDate value="${leaveaudit.enddate}" pattern="yyyy-MM-dd"/>"/>
		 </div>	
	</form:form>
	<br/>
	</div>
	</div>
	
	<!-- 工具栏 -->
	<div class="row">
	<div class="col-sm-12">
		<div class="pull-left">
			<c:if test="${leaveaudit.isadmin==null||leaveaudit.isadmin==''}">
				<shiro:hasPermission name="ehr:leaveaudit:add">
					<table:addRow url="${ctx}/ehr/leaveaudit/form?isadmin=${leaveaudit.isadmin}" title="离职审计"></table:addRow><!-- 增加按钮 -->
				</shiro:hasPermission>
				<shiro:hasPermission name="ehr:leaveaudit:edit">
					<table:editRow url="${ctx}/ehr/leaveaudit/form" title="离职审计" id="contentTable"></table:editRow><!-- 编辑按钮 -->
				</shiro:hasPermission>
				<shiro:hasPermission name="ehr:leaveaudit:del">
					<table:delRow url="${ctx}/ehr/leaveaudit/deleteAll" id="contentTable"></table:delRow><!-- 删除按钮 -->
				</shiro:hasPermission>
			</c:if>
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
                <c:if test="${leaveaudit.isadmin=='1'}">
                    <c:set var="cadmin" value="1"/>
                </c:if>
				<c:if test="${leaveaudit.isadmin==null||leaveaudit.isadmin==''}">
					<th> <input type="checkbox" class="i-checks"></th>
				</c:if>
				<th  class="sort-column leaver">离职人姓名</th>
				<th  class="sort-column officename">离职人部门</th>
				<th  class="sort-column stationname">离职人岗位</th>
				<th  class="sort-column entrydate">入职日期</th>
				<th  class="sort-column leavedate">离职日期</th>
				<c:if test="${leaveaudit.isadmin=='1'}">
					<th  class="sort-column createusername">审计提交人</th>
				</c:if>
				<th  class="sort-column create_date">创建日期</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="leaveaudit">
			<tr>
                <c:if test="${cadmin==null||cadmin==''}">
                    <td><input type="checkbox" id="${leaveaudit.id}" class="i-checks"></td>
                </c:if>
				<td><a  href="#" onclick="openDialogView('查看离职审计', '${ctx}/ehr/leaveaudit/form?id=${leaveaudit.id}&isadmin=${leaveaudit.isadmin}&bhv=1','800px', '500px')">
					${leaveaudit.leaver}
				</a></td>
				<td>
					${leaveaudit.officename}
				</td>
				<td>
					${leaveaudit.stationname}
				</td>
				<td>
					<fmt:formatDate value="${leaveaudit.entrydate}" pattern="yyyy-MM-dd"/>
				</td>
				<td>
					<fmt:formatDate value="${leaveaudit.leavedate}" pattern="yyyy-MM-dd"/>
				</td>
				<c:if test="${cadmin=='1'}">
					<td>
							${leaveaudit.createusername}
					</td>
				</c:if>
				<td>
					<fmt:formatDate value="${leaveaudit.createDate}" pattern="yyyy-MM-dd"/>
				</td>
				<td>
					<shiro:hasPermission name="ehr:leaveaudit:view">
						<a href="#" onclick="openDialogView('查看离职审计', '${ctx}/ehr/leaveaudit/form?id=${leaveaudit.id}&isadmin=${leaveaudit.isadmin}&bhv=1','800px', '500px')" class="btn btn-info btn-xs" ><i class="fa fa-search-plus"></i> 查看</a>
					</shiro:hasPermission>
					<c:if test="${cadmin==null||cadmin==''}">
						<shiro:hasPermission name="ehr:leaveaudit:edit">
							<a href="#" onclick="openDialog('修改离职审计', '${ctx}/ehr/leaveaudit/form?id=${leaveaudit.id}&isadmin=${leaveaudit.isadmin}','800px', '500px')" class="btn btn-success btn-xs" ><i class="fa fa-edit"></i> 修改</a>
						</shiro:hasPermission>
						<shiro:hasPermission name="ehr:leaveaudit:del">
							<a href="${ctx}/ehr/leaveaudit/delete?id=${leaveaudit.id}&isadmin=${leaveaudit.isadmin}" onclick="return confirmx('确认要删除该离职审计吗？', this.href)"   class="btn btn-danger btn-xs"><i class="fa fa-trash"></i> 删除</a>
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
	</div>
</div>
</body>
</html>