<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>培训计划管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
            laydate({
                elem: '#traindate', //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
                event: 'focus' //响应事件。如果没有传入event，则按照默认的click
            });
		});
	</script>
</head>
<body class="gray-bg">
	<div class="wrapper wrapper-content">
	<div class="ibox">
	<div class="ibox-title">
		<h5>培训列表 </h5>
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
	<form:form id="searchForm" modelAttribute="trainplan" action="${ctx}/ehr/trainplan/?isadmin=${trainplan.isadmin}" method="post" class="form-inline pull-left">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<table:sortColumn id="orderBy" name="orderBy" value="${page.orderBy}" callback="sortOrRefresh();"/><!-- 支持排序 -->
		<div class="form-group">
			<c:if test="${trainplan.isadmin=='1'}">
				<span>计划部门：</span>
				<sys:treeselect id="officeid" name="officeid" value="${trainplan.officeid}" labelName="officename" labelValue="${trainplan.officename}"
								title="部门" url="/sys/office/treeData?type=2" cssClass=" form-control input-sm" allowClear="true" notAllowSelectParent="false"/>
				<span>培训人：</span>
				<sys:treeselect id="userid" name="userid" value="${trainplan.userid}" labelName="username" labelValue="${trainplan.username}"
								title="用户" url="/sys/office/treeData?type=3" cssClass=" form-control input-sm" allowClear="true" notAllowSelectParent="false"/>
				<span>是否完成：</span>
				<form:select path="status" class="form-control ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</c:if>
			<c:if test="${trainplan.isadmin==null||trainplan.isadmin==''}">
				<span>培训主题：</span>
				<form:input path="title" htmlEscape="false" maxlength="200"    class="form-control" />
				<span>培训时间：</span>
				<input id="traindate" name="traindate" type="text" maxlength="20" class="laydate-icon form-control layer-date"
					   value="<fmt:formatDate value="${trainplan.traindate}" pattern="yyyy-MM-dd"/>" />
				<span>是否完成：</span>
				<form:select path="status" class="form-control ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</c:if>
		 </div>
	</form:form>
		<div class="pull-left" style="margin-left: 20px;">
			<button  class="btn btn-primary btn-rounded btn-outline btn-sm " onclick="search()" ><i class="fa fa-search"></i> 查询</button>
			<button  class="btn btn-primary btn-rounded btn-outline btn-sm " onclick="reset()" ><i class="fa fa-refresh"></i> 重置</button>
		</div>
	<br/>
	</div>
	</div>
	
	<!-- 工具栏 -->
	<div class="row">
	<div class="col-sm-12">
		<div class="pull-left">
			<shiro:hasPermission name="ehr:trainplan:add">
				<table:addRow url="${ctx}/ehr/trainplan/form?isadmin=${trainplan.isadmin}" title="培训计划"></table:addRow><!-- 增加按钮 -->
			</shiro:hasPermission>
			<%--<shiro:hasPermission name="ehr:trainplan:edit">
			    <table:editRow url="${ctx}/ehr/trainplan/form?isadmin=${trainplan.isadmin}" title="培训计划" id="contentTable"></table:editRow><!-- 编辑按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="ehr:trainplan:del">
				<table:delRow url="${ctx}/ehr/trainplan/deleteAll?isadmin=${trainplan.isadmin}" id="contentTable"></table:delRow><!-- 删除按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="ehr:trainplan:import">
				<table:importExcel url="${ctx}/ehr/trainplan/import"></table:importExcel><!-- 导入按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="ehr:trainplan:export">
	       		<table:exportExcel url="${ctx}/ehr/trainplan/export"></table:exportExcel><!-- 导出按钮 -->
	       	</shiro:hasPermission>--%>
	       <button class="btn btn-white btn-sm " data-toggle="tooltip" data-placement="left" onclick="sortOrRefresh()" title="刷新"><i class="glyphicon glyphicon-repeat"></i> 刷新</button>
		
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
				<%--<th> <input type="checkbox" class="i-checks"></th>--%>
				<c:set var="tplan" value="${trainplan}" />
				<c:if test="${trainplan.isadmin=='1'}">
					<th  class="sort-column username">培训人</th>
					<th  class="sort-column a.officename">部门</th>
				</c:if>
				<th  class="sort-column traindate">培训日期</th>
				<th  class="sort-column title">培训标题</th>
				<th  class="sort-column status">是否完成</th>
					<th  class="">培训对象</th>
					<th  class="">完成情况</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="trainplan">
			<tr>
				<%--<td> <input type="checkbox" id="${trainplan.id}" class="i-checks"></td>--%>
				<c:if test="${tplan.isadmin=='1'}">
					<td>
						${trainplan.username}
					</td>
					<td>
						${trainplan.officename}
					</td>
				</c:if>
				<td>
					<fmt:formatDate value="${trainplan.traindate}" pattern="yyyy-MM-dd"/>
				</td>
				<td>
					${trainplan.title}
				</td>
				<td>
					${fns:getDictLabel(trainplan.status, 'yes_no', '')}
				</td>
					<td>
							${trainplan.trainpeople}
					</td>
					<td>
							${trainplan.completesituation}
					</td>
				<td>
					<%--<shiro:hasPermission name="ehr:trainplan:view">

					</shiro:hasPermission>--%>
						<a href="#" onclick="openDialogView('查看', '${ctx}/ehr/trainplan/form?id=${trainplan.id}&mview=1','800px', '500px')" class="btn btn-info btn-xs" ><i class="fa fa-search-plus"></i> 查看</a>
					<shiro:hasPermission name="ehr:trainplan:edit">
    					<a href="#" onclick="openDialog('修改', '${ctx}/ehr/trainplan/form?id=${trainplan.id}&isadmin=${tplan.isadmin}','800px', '500px')" class="btn btn-success btn-xs" ><i class="fa fa-edit"></i> 修改</a>
    				</shiro:hasPermission>
    				<shiro:hasPermission name="ehr:trainplan:del">
						<a href="${ctx}/ehr/trainplan/delete?id=${trainplan.id}&isadmin=${tplan.isadmin}" onclick="return confirmx('确认要删除该记录吗？', this.href)"   class="btn btn-danger btn-xs"><i class="fa fa-trash"></i> 删除</a>
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