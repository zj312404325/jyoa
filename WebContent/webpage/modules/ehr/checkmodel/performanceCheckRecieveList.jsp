<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
    <c:if test="${performanceCheck.category==null||performanceCheck.category==''||performanceCheck.category=='0'}"><c:set var="titlestr" value="绩效考核明细" /></c:if>
    <c:if test="${performanceCheck.category=='1'}"><c:set var="titlestr" value="转正考核" /></c:if>
	<title>${titlestr}管理</title>
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
	<!-- <div class="ibox"> -->
	<!-- <div class="ibox-title">
		<h5>${titlestr}列表111111111 </h5>
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
	</div> -->
    
    <!-- <div class="ibox-content"> -->
	<sys:message content="${message}"/>
	
	<!--查询条件-->
	<form:form id="searchForm" modelAttribute="performanceCheck" action="${ctx}/checkmodel/performanceCheck/list?cat=recieve" method="post" class="form-inline">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
        <form:hidden path="category"/>
		<table:sortColumn id="orderBy" name="orderBy" value="${page.orderBy}" callback="sortOrRefresh();"/><!-- 支持排序 -->
		<div class="form-group">
			<%-- <span>员工编号：</span>
			<form:input path="userno" htmlEscape="false" maxlength="10"  class=" form-control input-sm"/>
			<span>员工姓名：</span>
			<form:input path="username" htmlEscape="false" maxlength="10"  class=" form-control input-sm"/> --%>
			<span>开始时间：</span>
			<input id="startdate" name="startdate" type="text" maxlength="20" class="laydate-icon form-control layer-date required"
							value="<fmt:formatDate value="${performanceCheck.startdate}" pattern="yyyy-MM-dd"/>"/>
			<span>结束时间：</span>
			<input id="enddate" name="enddate" type="text" maxlength="20" class="laydate-icon form-control layer-date required"
							value="<fmt:formatDate value="${performanceCheck.enddate}" pattern="yyyy-MM-dd"/>"/>

				<div class="pull-right" style="padding-left: 20px">
					<button  class="btn btn-primary btn-rounded btn-outline btn-sm " onclick="search()" ><i class="fa fa-search"></i> 查询</button>
					<button  class="btn btn-primary btn-rounded btn-outline btn-sm " onclick="reset1()" ><i class="fa fa-refresh"></i> 重置</button>
				</div>
		 </div>	
	</form:form>
	
	<!-- 工具栏 -->
	<div class="row">
	<div class="col-sm-12">
		<div class="pull-left">
		
			</div>

	</div>
	</div>
	
	<!-- 表格 -->
	<table id="contentTable" class="table table-striped table-bordered table-hover table-condensed dataTables-example dataTable">
		<thead>
			<tr>
				<th> <input type="checkbox" class="i-checks"></th>
				<th>员工编号</th>
				<th>员工姓名</th>
				<th  class="sort-column officename">部门</th>
				<th  class="sort-column stationname">岗位</th>
				<th  class="sort-column create_date">填表日期</th>
				<th  class="sort-column selfscore">自评得分</th>
				<th  class="sort-column score">考核总分</th>
                <c:if test="${performanceCheck.category==null||performanceCheck.category==''||performanceCheck.category=='0'}">
                    <th  class="sort-column checkyear">年份</th>
                    <th  class="sort-column checkquarter">季度</th>
                </c:if>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="performanceCheck">
			<tr>
				<td> <input type="checkbox" id="${performanceCheck.id}" class="i-checks"></td>
				<td>
						${performanceCheck.userno}
				</td>
				<td>
						${performanceCheck.username}
				</td>
				<td><a  href="#" onclick="openDialogView('查看${titlestr}', '${ctx}/checkmodel/performanceCheck/form?id=${performanceCheck.id}&type=1&bhv=1','800px', '500px')">
					${performanceCheck.officename}
				</a></td>
				<td>
					${performanceCheck.stationname}
				</td>
				<td>
					<fmt:formatDate value="${performanceCheck.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${performanceCheck.selfscore}
				</td>
				<td>
					${performanceCheck.score}
				</td>
                <c:if test="${performanceCheck.category==null||performanceCheck.category==''||performanceCheck.category=='0'}">
                    <td>
                            ${performanceCheck.checkyear}
                    </td>
                    <td>
                            ${fns:getDictLabel(performanceCheck.checkquarter, 'checkQuarter', '')}
                    </td>
                </c:if>
				<td>
					<shiro:hasPermission name="checkmodel:performanceCheck:view">
						<a href="#" onclick="openDialog('查看${titlestr}', '${ctx}/checkmodel/performanceCheck/form?id=${performanceCheck.id}&type=1&bhv=1','800px', '500px')" class="btn btn-info btn-xs" ><i class="fa fa-search-plus"></i> 查看</a>
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
	<!-- </div>
	</div> -->
</div>
</body>
</html>