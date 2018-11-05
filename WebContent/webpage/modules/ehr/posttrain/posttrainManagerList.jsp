<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>岗位培训</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			laydate({
	            elem: '#traintime', //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
	            event: 'focus' //响应事件。如果没有传入event，则按照默认的click
	        });
		});
		
	</script>
</head>
<body class="gray-bg">
	<div class="wrapper wrapper-content">
	<div class="ibox">
    
    <div class="ibox-content">
	<sys:message content="${message}"/>
	<div class="tabs-container">
    	<div class="tab-content">
	        <div id="tab-2" class="tab-pane active">
	            <div class="panel-body">
					<form:form id="searchForm" modelAttribute="posttrain" action="${ctx}/ehr/posttrain/managerlist" method="post" class="form-inline">
						<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
						<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
						<table:sortColumn id="orderBy" name="orderBy" value="${page.orderBy}" callback="sortOrRefresh();"/><!-- 支持排序 -->
						
						<div class="form-group">
							<span>姓名：</span>
								<form:input path="trainer" htmlEscape="false" maxlength="200"  class=" form-control input-sm"/>
							<span>培训地点：</span>
								<form:input path="address" htmlEscape="false" maxlength="200"  class=" form-control input-sm"/>
							<span>主办机构：</span>
								<form:input path="organizer" htmlEscape="false" maxlength="200"  class=" form-control input-sm"/>
							<span>培训时间：</span>
								<input id="traintime" name="traintime" class="form-control required valid" type="text" readonly="true" value="<fmt:formatDate pattern="yyyy-MM-dd" value="${posttrain.traintime}" />" aria-required="true" aria-invalid="false">
							
							<div class="pull-right" style="padding-left: 20px;">
								<button  class="btn btn-primary btn-rounded btn-outline btn-sm " onclick="search()" ><i class="fa fa-search"></i> 查询</button>
								<button  class="btn btn-primary btn-rounded btn-outline btn-sm " onclick="resetform()" ><i class="fa fa-refresh"></i> 重置</button>
							</div>
						 </div>	
					</form:form>
						
						<!-- 工具栏 -->
						<!-- <div class="row">
						<div class="col-sm-12">
							<div class="pull-left">
						       <button class="btn btn-white btn-sm " data-toggle="tooltip" data-placement="left" onclick="sortOrRefresh()" title="刷新"><i class="glyphicon glyphicon-repeat"></i> 刷新</button>
							
								</div>
						</div>
						</div> -->
						
						<!-- 表格 -->
						<table id="contentTable" class="table table-striped table-bordered table-hover table-condensed dataTables-example dataTable">
							<thead>
								<tr>
									<%--<th> <input type="checkbox" class="i-checks"></th>--%>
									<th  class="sort-column trainer">姓名</th>
									<th  class="sort-column title">培训标题</th>
									<th  class="sort-column address">培训地点</th>
									<th  class="sort-column organizer">主办机构</th>
									<th  class="sort-column traintime">培训时间</th>
									<th  class="sort-column create_Date">创建时间</th>
									<th>操作</th>
								</tr>
							</thead>
							<tbody>
							<c:forEach items="${page.list}" var="posttrain">
								<tr>
									<%--<td> <input type="checkbox" id="${posttrain.id}" class="i-checks"></td>--%>
									<td>
										${posttrain.trainer}
									</td>
									<td>
										${posttrain.title}
									</td>
									<td>
										${posttrain.address}
									</td>
									<td>
										${posttrain.organizer}
									</td>
									<td>
										<fmt:formatDate pattern="yyyy年MM月dd日" value="${posttrain.traintime}" />
									</td>
									<td>
										<fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${posttrain.createDate}" />
									</td>
									<td>
										<%-- <shiro:hasPermission name="ehr:posttrain:view"> --%>
											<a href="#" onclick="openDialogView('查看信息', '${ctx}/ehr/posttrain/view?id=${posttrain.id}','800px', '500px')" class="btn btn-info btn-xs" ><i class="fa fa-search-plus"></i> 查看</a>
										<%-- </shiro:hasPermission> --%>
					    				<%-- <shiro:hasPermission name="ehr:posttrain:del">
											<a href="${ctx}/ehr/posttrain/delete?id=${posttrain.id}" onclick="return confirmx('确认要删除该信息吗？', this.href)"   class="btn btn-danger btn-xs"><i class="fa fa-trash"></i> 删除</a>
										</shiro:hasPermission> --%>
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
	</div>
	<!--查询条件-->
	
	</div>
	</div>
</div>
</body>
</html>