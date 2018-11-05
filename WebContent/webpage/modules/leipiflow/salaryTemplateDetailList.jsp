<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>信息管理</title>
	<meta name="decorator" content="default"/>
	<script src="${ctxStatic}/common/contabs.js"></script>
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

        function add(flowid){
            //openDialog("新增传阅","${ctx}/oa/oaNotify/form","60%", "80%","");
            window.location.href="${ctx}/leipiflow/leipiFlowApply/form?flowid="+flowid;
        }
	</script>
</head>
<body class="gray-bg">
	<div class="wrapper wrapper-content">
	<div class="ibox">

    
    <div class="ibox-content">
	<sys:message content="${message}"/>
	
	<!--查询条件-->
	<div class="row">
	<div class="col-sm-12">
		<c:set var="mtd" value="${templateDetail.mtd}" />
	<form:form id="searchForm" modelAttribute="templateDetail" action="${ctx}/leipiflow/templateDetail/salarydetaillist" method="post" class="form-inline">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<table:sortColumn id="orderBy" name="orderBy" value="${page.orderBy}" callback="sortOrRefresh();"/><!-- 支持排序 -->

			<div class="form-group">

				<span>申请时间：</span>
				<input id="startdate" name="startdate" type="text" maxlength="20" class="laydate-icon form-control layer-date required"
					   value="<fmt:formatDate value="${templateDetail.startdate}" pattern="yyyy-MM-dd"/>"/>
				<span>至</span>
				<input id="enddate" name="enddate" type="text" maxlength="20" class="laydate-icon form-control layer-date required"
					   value="<fmt:formatDate value="${templateDetail.enddate}" pattern="yyyy-MM-dd"/>"/>


				<div class="pull-right" style="padding-left: 20px">
					<button  class="btn btn-primary btn-rounded btn-outline btn-sm " onclick="search()" ><i class="fa fa-search"></i> 查询</button>
					<button  class="btn btn-primary btn-rounded btn-outline btn-sm " onclick="resetform()" ><i class="fa fa-refresh"></i> 重置</button>
				</div>
			</div>

	</form:form>
	<br/>
	</div>
	</div>
	
	<!-- 工具栏 -->
	<div class="row">
	<div class="col-sm-12">
		<ul class="list-group">
			<li class="list-group-item list-group-item-success">
				薪资调整人数：${temptotal}人&nbsp;&nbsp;&nbsp; 占比${temptotalPercentage}
			</li>
			<li class="list-group-item list-group-item-success">
				大于1次薪资调整人数：${temptwice}人&nbsp;&nbsp;&nbsp;占比${temptwicePercentage}
			</li>
			<li class="list-group-item">
				<ul class="list-group" style="width: 50%; min-width: 200px; max-height: 200px; overflow-y: auto;">
				<c:forEach items="${salarylist}" var="s" varStatus="st">
					<li class="list-group-item">${st.count}：${s.key}<span class="badge">${s.value}次</span></li>
				</c:forEach>
				</ul>
			</li>

	       <%--<button class="btn btn-white btn-sm " data-toggle="tooltip" data-placement="left" onclick="sortOrRefresh()" title="刷新"><i class="glyphicon glyphicon-repeat"></i> 刷新</button>--%>
		</ul>

	</div>
	</div>

	<table id="contentTable" class="table table-bordered table-condensed dataTables-example dataTable">
		<thead>
			<tr>
				<th  class="">姓名</th>
				<th  class="">现部门</th>
				<th  class="">原薪资</th>
				<th  class="">调整后薪资</th>
				<th  class="">申请人</th>
				<th  class="">申请时间</th>
				<th  class="">执行时间</th>
				<th  class="">调整说明</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${detailList.list}" var="templateDetail" varStatus="st">
			<tr>
				<td>
						${templateDetail.var1}
				</td>
				<td>
						${templateDetail.var3}
				</td>
				<td>
						${templateDetail.var6}
				</td>
				<td>
						${templateDetail.var7}
				</td>

				<td>
						${fns:getUserById(templateDetail.createBy.id).name}
				</td>

				<td >
					<fmt:formatDate value="${templateDetail.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
						${templateDetail.var8}
				</td>
				<td>
						${templateDetail.var9}
				</td>

			</tr>
		</c:forEach>

		</tbody>
	</table>

	
		<!-- 分页代码 -->
	<table:page page="${detailList}"></table:page>
	<br/>
	<br/>
	</div>
	</div>
</div>
</body>
</html>