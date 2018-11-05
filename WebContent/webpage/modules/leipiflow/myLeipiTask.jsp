<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>待办任务</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
	        laydate({
	            elem: '#beginDate', //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
	            event: 'focus', //响应事件。如果没有传入event，则按照默认的click
	            format: 'YYYY/MM/DD hh:mm:ss',
	            istime: true,
	        });
	        laydate({
	            elem: '#endDate', //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
	            event: 'focus', //响应事件。如果没有传入event，则按照默认的click
	            format: 'YYYY/MM/DD hh:mm:ss',
	            istime: true,
	        });
					
			//alert('${jumpUrl}');
			if('${jumpUrl}'!=''){
				alert(1);
				location.href='${jumpUrl}';
			}
		});
		/**
		 * 签收任务
		 */
		function claim(taskId,procInsId) {
			$.get('${ctx}/act/task/claim' ,{taskId: taskId,procInsId:procInsId}, function(data) {
				if (data == 'true'){
		        	top.$.jBox.tip('签收完成');
		            location = '${ctx}/act/task/todo/';
				}else{
		        	top.$.jBox.tip('签收失败');
				}
		    });
		}
	</script>
</head>
<body class="gray-bg">
	<div class="wrapper wrapper-content">
	<div class="ibox">
	<div class="ibox-title">
	  <c:if test="${taskStatus == '0'}"><h5>待办任务 </h5></c:if>
	  <c:if test="${taskStatus == '1'}"><h5>在办任务 </h5></c:if>
	  <c:if test="${taskStatus == '2'}"><h5>已办任务 </h5></c:if>
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
	<form:form id="searchForm" modelAttribute="flowapply" action="${ctx}/leipiflow/leipiFlowApply/myLeipiTask/?type=${taskStatus}" method="get" class="form-inline">
		<input id="type" name="type" type="hidden" value="${taskStatus}"/>
		<input id="flowtype" name="flowtype" type="hidden" value="${flowtype}"/>
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<table:sortColumn id="orderBy" name="orderBy" value="${page.orderBy}" callback="sortOrRefresh();"/><!-- 支持排序 -->
		<div class="form-group">
			<label>标题：</label>
			<form:input path="var2" htmlEscape="false"  class="form-control" maxlength="20"/>
			<label>编号：</label>
			<form:input path="keyword" htmlEscape="false"  class="form-control" maxlength="20"/>
			<label>发起人：</label>
			<form:input path="keyword1" htmlEscape="false"  class="form-control" maxlength="20"/>
			<label>发起人部门：</label>
			<%--<form:input path="keyword1" htmlEscape="false"  class="form-control" maxlength="20"/>--%>
			<sys:treeselect id="office" name="office.id" value="${flowapply.office.id}" labelName="office.name" labelValue="${flowapply.office.name}"
							title="部门" url="/sys/office/treeData?type=2" cssClass="form-control required" notAllowSelectParent="false"/>
			<div class="pull-right" style="padding-left: 20px;">
				<button  class="btn btn-primary btn-rounded btn-outline btn-sm " onclick="search()" ><i class="fa fa-search"></i> 查询</button>
				<button  class="btn btn-primary btn-rounded btn-outline btn-sm " onclick="resetform()" ><i class="fa fa-refresh"></i> 重置</button>
			</div>
		 </div>
	</form:form>
	</div>
	</div>
	
	<!-- 工具栏 -->
	<div class="row">
	<div class="col-sm-12">
		<div class="pull-left">
	       <button class="btn btn-white btn-sm " data-toggle="tooltip" data-placement="left" onclick="sortOrRefresh()" title="刷新"><i class="glyphicon glyphicon-repeat"></i> 刷新</button>
		</div>
	</div>
	</div>
	

	<!-- 表格 -->
	<table id="contentTable" class="table table-striped table-bordered table-hover table-condensed dataTables-example dataTable">
		<thead>
			<tr>
				<th>编号</th>
				<th>名称</th>
				<th>标题</th>
				<c:if test="${taskStatus=='0'}"><th>当前环节</th></c:if>
				<c:if test="${taskStatus=='1'||taskStatus=='2'}"><th>处理环节</th></c:if>
				<th>发起人</th>
				<th>发起人部门</th>
				<th>申请时间</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.list}" var="map">
				<tr>
					<td>${map.pid}</td>
					<td>${map.leipiFlow.flowname}</td>
					<td>${map.var2}</td>
					<td>
						${map.leipiFlowProcess.processName}
					</td>
					<td>${map.createBy.name}</td>
					<td>${map.office.name}</td>
					<td><fmt:formatDate value="${map.leipiRunProcess.jsTime}" type="both"/></td>
					<td>
						<c:if test="${taskStatus=='0'}">
							<a href="${ctx}/leipiflow/leipiFlowApply/myLeipiEdit?id=${map.id}">任务办理</a>
							<%--<a href="${ctx}/leipiflow/leipiFlowApply/myLeipiView?id=${map.id}&flag=1">查看</a>--%>
						</c:if>
						<c:if test="${taskStatus=='1'||taskStatus=='2'}">
							<a href="${ctx}/leipiflow/leipiFlowApply/myLeipiView?id=${map.id}&flag=1">查看</a>
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