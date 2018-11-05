<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>信息管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
		});
	</script>
</head>
<body class="gray-bg">
	<div class="wrapper wrapper-content">
	<div class="ibox">
	<div class="ibox-title">
		<h5>信息列表 </h5>
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
	<form:form id="searchForm" modelAttribute="userInfo" action="${ctx}/ehr/entry/peopleStructList" method="post" class="form-inline">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<table:sortColumn id="orderBy" name="orderBy" value="${page.orderBy}" callback="sortOrRefresh();"/><!-- 支持排序 -->
		 
		 <div class="form-group">
				<span>类型：</span>
				<form:select path="peopleStructType"  class="form-control m-b">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('peopleStructType')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
		<div class="pull-right" style="padding-left: 20px;">
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
		<div class="pull-left">
			</div>
	</div>
	</div>
	
	<!-- 表格 -->
	<table id="contentTable" class="table table-striped table-bordered table-hover table-condensed dataTables-example dataTable">
		<thead>
			<tr>
				<c:if test="${userInfo.peopleStructType=='0'}">
					<th>研究生以上</th>
					<th>研究生</th>
					<th>本科</th>
					<th>大专</th>
					<th>中专</th>
					<th>高中</th>
					<th>初中</th>
					<th>初中以下</th>
				</c:if>
				<c:if test="${userInfo.peopleStructType=='1'}">
					<th>男</th>
					<th>女</th>
				</c:if>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${list}" var="s">
			<tr>
				<c:if test="${userInfo.peopleStructType=='0'}">
					<td>
							${s.VAR1}
					</td>
					<td>
							${s.VAR2}
					</td>
					<td>
							${s.VAR3}
					</td>
					<td>
							${s.VAR4}
					</td>
					<td>
							${s.VAR5}
					</td>
					<td>
							${s.VAR6}
					</td>
					<td>
							${s.VAR7}
					</td>
					<td>
							${s.VAR8}
					</td>
				</c:if>
				<c:if test="${userInfo.peopleStructType=='1'}">
					<td>
							${s.MALE}
					</td>
					<td>
							${s.FEMALE}
					</td>
				</c:if>
			</tr>
		</c:forEach>
		</tbody>
	</table>

		<!-- 分页代码 -->
	<%--<table:page page="${page}"></table:page>--%>
	<br/>
	<br/>
	</div>
	</div>
</div>
</body>
</html>