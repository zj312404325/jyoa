<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>流程操作管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
		});
		function addFlow(flowid,formid){
			window.location.href="${ctx}/leipiflow/leipiFlowApply/form?formid="+formid+"&flowid="+flowid;
			//openDialog("发起流程","${ctx}/oa/oaNotify/form","60%", "80%","");
		}
		
	</script>
</head>
<body class="gray-bg">
	<div class="wrapper wrapper-content">
	<div class="ibox">
	<div class="ibox-title">
		<c:if test="${leipiflowtype==0}"><h5>流程操作列表 </h5></c:if>
		<c:if test="${leipiflowtype==1}"><h5>申请操作列表 </h5></c:if>
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
	<c:if test="${isadmin == '1'}">
	    <form:form id="searchForm" modelAttribute="leipiFlow" action="${ctx}/leipiflow/leipiFlow" method="post" class="form-inline">
			<input id="leipiflowtype" name="leipiflowtype" type="hidden" value="${leipiflowtype}"/>
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<table:sortColumn id="orderBy" name="orderBy" value="${page.orderBy}" callback="sortOrRefresh();"/><!-- 支持排序 -->
		<div class="form-group">
			<%-- <span style="display: none;">流程类型：</span>
				<form:radiobuttons  class="i-checks" path="flowtype" items="${fns:getDictList('flowtype')}" itemLabel="label" itemValue="value" htmlEscape="false"/> --%>
			<span>名称：</span>
				<form:input path="flowname" htmlEscape="false" maxlength="255"  class=" form-control input-sm"/>
			<div class="pull-right" style="padding-left: 20px;" >
				<button  class="btn btn-primary btn-rounded btn-outline btn-sm " onclick="search()" ><i class="fa fa-search"></i> 查询</button>
				<button  class="btn btn-primary btn-rounded btn-outline btn-sm " onclick="resetform()" ><i class="fa fa-refresh"></i> 重置</button>
			</div>
		 </div>	
	    </form:form>
	</c:if>
	<c:if test="${isadmin != '1'}">
	    <form:form id="searchForm" modelAttribute="leipiFlow" action="${ctx}/leipiflow/leipiFlow/goLeipiFlow" method="post" class="form-inline">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<table:sortColumn id="orderBy" name="orderBy" value="${page.orderBy}" callback="sortOrRefresh();"/><!-- 支持排序 -->
		<div class="form-group">
			<%-- <span style="display: none;">流程类型：</span>
				<form:radiobuttons  class="i-checks" path="flowtype" items="${fns:getDictList('flowtype')}" itemLabel="label" itemValue="value" htmlEscape="false"/> --%>
			<span>名称：</span>
				<form:input path="flowname" htmlEscape="false" maxlength="255"  class=" form-control input-sm"/>
			<div class="pull-right" style="padding-left: 20px;" >
				<button  class="btn btn-primary btn-rounded btn-outline btn-sm " onclick="search()" ><i class="fa fa-search"></i> 查询</button>
				<button  class="btn btn-primary btn-rounded btn-outline btn-sm " onclick="resetform()" ><i class="fa fa-refresh"></i> 重置</button>
			</div>
		 </div>	
	    </form:form>
	</c:if>
	
	<br/>
	</div>
	</div>
	
	<!-- 工具栏 -->
	<div class="row">
	<div class="col-sm-12">
		<div class="pull-left">
			<shiro:hasPermission name="leipiflow:leipiFlow:add">
				<table:addRow url="${ctx}/leipiflow/leipiFlow/form?leipiflowtype=${leipiFlow.leipiflowtype}" title="流程操作"></table:addRow><!-- 增加按钮 -->
			</shiro:hasPermission>
			<%-- <shiro:hasPermission name="leipiflow:leipiFlow:edit">
			    <table:editRow url="${ctx}/leipiflow/leipiFlow/form" title="流程操作" id="contentTable"></table:editRow><!-- 编辑按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="leipiflow:leipiFlow:del">
				<table:delRow url="${ctx}/leipiflow/leipiFlow/deleteAll" id="contentTable"></table:delRow><!-- 删除按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="leipiflow:leipiFlow:import">
				<table:importExcel url="${ctx}/leipiflow/leipiFlow/import"></table:importExcel><!-- 导入按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="leipiflow:leipiFlow:export">
	       		<table:exportExcel url="${ctx}/leipiflow/leipiFlow/export"></table:exportExcel><!-- 导出按钮 -->
	       	</shiro:hasPermission> --%>
	       <button class="btn btn-white btn-sm " data-toggle="tooltip" data-placement="left" onclick="sortOrRefresh()" title="刷新"><i class="glyphicon glyphicon-repeat"></i> 刷新</button>
		
			</div>
		
	</div>
	</div>
	
	<!-- 表格 -->
	<table id="contentTable" class="table table-bordered table-condensed dataTables-example dataTable">
		<thead>
			<tr>
				<!-- <th> <input type="checkbox" class="i-checks"></th> -->
				<!-- <th  class="sort-column formid">表单id</th> -->
				<!-- <th  class="sort-column flowtype">流程类型</th> -->
				<th  class="">名称</th>
				<th  class="" width="50%">描述</th>
				<shiro:hasPermission name="leipiflow:leipiFlow:add">
				<th  class="sort-column status">状态</th>
				</shiro:hasPermission>
				<th  class="sort-column dateline">创建时间</th>
				<th width="185px">操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="leipiFlow">

			<tr class="<c:if test="${leipiFlow.status == 0}">bg-danger</c:if>">
				<%-- <td> <input type="checkbox" id="${leipiFlow.id}" class="i-checks"></td> --%>
				<%-- <td><a  href="#" onclick="openDialogView('查看流程操作', '${ctx}/leipiflow/leipiFlow/form?id=${leipiFlow.id}','800px', '500px')">
					${leipiFlow.formid}
				</a></td> --%>
				<%-- <td>
					${fns:getDictLabel(leipiFlow.flowtype, 'flowtype', '')}
				</td> --%>
				<td>
					${leipiFlow.flowname}
				</td>
				<td>
					${leipiFlow.flowdesc}
				</td>
				<shiro:hasPermission name="leipiflow:leipiFlow:add">
				<td>
					${fns:getDictLabel(leipiFlow.status, 'status', '')}
				</td>
				</shiro:hasPermission>
				<td>
					<fmt:formatDate value="${leipiFlow.dateline}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					<shiro:hasPermission name="leipiflow:leipiFlow:view">
						<a href="#" onclick="openDialogView('查看流程操作', '${ctx}/leipiflow/leipiFlow/leipiflowInit?flowid=${leipiFlow.id}','950px', '600px')" class="btn btn-info btn-xs" ><i class="fa fa-search-plus"></i> 设计流程</a>
					</shiro:hasPermission>
					<shiro:hasPermission name="leipiflow:leipiFlow:edit">
    					<a href="#" onclick="openDialog('修改流程操作', '${ctx}/leipiflow/leipiFlow/form?id=${leipiFlow.id}&leipiflowtype=${leipiFlow.leipiflowtype}','800px', '500px')" class="btn btn-success btn-xs" ><i class="fa fa-edit"></i> 修改</a>
    				</shiro:hasPermission>
    				<shiro:hasPermission name="leipiflow:leipiFlow:del">
						<a href="${ctx}/leipiflow/leipiFlow/delete?id=${leipiFlow.id}" onclick="return confirmx('确认要删除该流程操作吗？', this.href)"   class="btn btn-danger btn-xs"><i class="fa fa-trash"></i> 删除</a>
					</shiro:hasPermission>
					
					<c:if test="${isadmin==null }">
						<shiro:hasPermission name="leipiflow:leipiFlow:sub">
	    					<button class="btn btn-success btn-sm" data-toggle="tooltip" data-placement="left" onclick="addFlow('${leipiFlow.id }','${leipiFlow.formid }')" title="发起流程" vl="${leipiFlow.id }" vl1="${leipiFlow.formid }"><i class="fa fa-plus"></i> 发起流程</button>
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