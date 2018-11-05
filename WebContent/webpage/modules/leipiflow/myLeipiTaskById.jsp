<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>待办任务</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
            laydate({
                elem: '#startdate', //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
                event: 'focus' , //响应事件。如果没有传入event，则按照默认的click
                format: 'YYYY/MM/DD',
                istime: false,
            });
            laydate({
                elem: '#enddate', //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
                event: 'focus' , //响应事件。如果没有传入event，则按照默认的click
                format: 'YYYY/MM/DD',
                istime: false,
            });
					
			if('${jumpUrl}'!=''){
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
<body>
	<div class="ibox">
	<%--<div class="ibox-title">
		<h5>待办任务 </h5>
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
	</div>--%>
    
    <div class="ibox-content">
	<sys:message content="${message}"/>
	
	<!--查询条件-->
	<form:form id="searchForm" modelAttribute="flowapply" action="${ctx }/leipiflow/leipiFlowApply/myLeipiTaskById?isadmin=${isadmin}" method="get" class="form-inline">
		<input id="isadmin" name="isadmin" type="hidden" value="${isadmin}"/>
		<input id="flowid" name="flowid" type="hidden" value="${flowid}"/>
		<input id="pageNo" name="pageNo" type="hidden" value="${page1.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page1.pageSize}"/>
		<table:sortColumn id="orderBy" name="orderBy" value="${page1.orderBy}" callback="sortOrRefresh();"/><!-- 支持排序 -->
		<div class="form-group">
			<span>发起人：</span>
			<form:input path="keyword1" htmlEscape="false" maxlength="10"  class=" form-control input-sm"/>
			<span>被申请人：</span>
			<form:input path="var1" htmlEscape="false" maxlength="10"  class=" form-control input-sm"/>
			<span>创建日期：</span>
			<input id="startdate" name="startdate" type="text" maxlength="20" class="laydate-icon form-control layer-date "
				   value="<fmt:formatDate value="${flowapply.startdate}" pattern="yyyy-MM-dd"/>"  />
			至
			<input id="enddate" name="enddate" type="text" maxlength="20" class="laydate-icon form-control layer-date "
				   value="<fmt:formatDate value="${flowapply.enddate}" pattern="yyyy-MM-dd"/>"  />
			<div class="pull-right" style="padding-left: 20px;">
				<button  class="btn btn-primary btn-rounded btn-outline btn-sm " onclick="search()" ><i class="fa fa-search"></i> 查询</button>
				<button  class="btn btn-primary btn-rounded btn-outline btn-sm " onclick="resetform()" ><i class="fa fa-refresh"></i> 重置</button>
			</div>
		 </div>	
	</form:form>

	
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
				<th>名称</th>
				<th>标题</th>
				<c:if test="${taskStatus=='0'}"><th>当前环节</th></c:if>
				<c:if test="${taskStatus=='1'}"><th>处理环节</th></c:if>
				<th>发起人</th>
				<c:if test="${isVar1Show}"><th>被申请人</th></c:if>
				<th>申请时间</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.list}" var="map">
				<tr>
					<td>${map.leipiFlow.flowname}</td>
					<td>${map.var2}</td>
					<td>
						${map.leipiFlowProcess.processName}
					</td>
					<td>${map.createBy.name}</td>
					<c:if test="${isVar1Show}"><td>${map.var1}</td></c:if>
					<td><fmt:formatDate value="${map.leipiRunProcess.jsTime}" type="both"/></td>
					<td>
						<c:if test="${taskStatus=='0'}">
							<a href="${ctx}/leipiflow/leipiFlowApply/myLeipiEdit?id=${map.id}&mtd=ehr">任务办理</a>
							<%-- <a href="#" onclick="openDialogNoBtn('任务办理', '${ctx}/leipiflow/leipiFlowApply/myLeipiEdit?id=${map.id}','800px', '500px')" class="btn btn-success btn-xs" ><i class="fa fa-edit"></i> 任务办理</a> --%>
							<%--<a href="#" onclick="openDialogView('查看我的申请-${fns:abbr(map.leipiFlow.flowname,16)}', '${ctx}/leipiflow/leipiFlowApply/myLeipiView?id=${map.id}&flag=1','1000px', '80%')" > 详情</a>--%>
						</c:if>
						<c:if test="${taskStatus=='1'}">
							<a href="${ctx}/leipiflow/leipiFlowApply/myLeipiView?id=${map.id}&flag=1">查看</a>
						</c:if>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>

		<!-- 分页代码 -->
	<table:page page="${page1}"></table:page>
	<br/>
	<br/>
	</div>
	</div>
</body>
</html>