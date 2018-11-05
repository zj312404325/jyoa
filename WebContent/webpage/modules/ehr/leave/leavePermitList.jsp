<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>合同管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			
			if(navigator.userAgent.match(/(iPhone|iPod|Android|ios)/i)){//如果是移动端，就使用自适应大小弹窗
				width='auto';
				height='auto';
			}else{//如果是PC端，根据用户设置的width和height显示。
				width='700px';
				height='200px';
			}
			
		});
	</script>
</head>
<body class="gray-bg">
	<div class="wrapper wrapper-content">
	<div class="ibox">
	<div class="ibox-title">
		<h5>离职申请列表 </h5>
		<div class="ibox-tools">
			<a class="collapse-link">
				<i class="fa fa-chevron-up"></i>
			</a>
			<!-- <a class="dropdown-toggle" data-toggle="dropdown" href="#">
				<i class="fa fa-wrench"></i>
			</a> -->
			<!-- <ul class="dropdown-menu dropdown-user">
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
	<%-- <div class="row">
	<div class="col-sm-12"> --%>
	<form:form id="searchForm" modelAttribute="userInfo" action="${ctx}/ehr/entry/leavePermitlist/" method="post" class="form-inline">
		<input id="isadmin" name="isadmin" type="hidden" value="${isadmin}"/>
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<table:sortColumn id="orderBy" name="orderBy" value="${page.orderBy}" callback="sortOrRefresh();"/><!-- 支持排序 -->
		<div class="form-group">
		 </div>	
	</form:form>
	<%-- <br/>
	</div>
	</div> --%>
	
	<!-- 工具栏 -->
	<div class="row">
	<div class="col-sm-12">
		<div class="pull-left">
			<c:if test="${isadmin==null||isadmin==''}">
				<table:addRow url="${ctx}/ehr/leaveSheet/form?isadmin=1" title="离职申请"></table:addRow><!-- 增加按钮 -->
			</c:if>
			<%-- <shiro:hasPermission name="ehr:entryContract:add">
				<table:addRow url="${ctx}/ehr/entryContract/form" title="合同"></table:addRow><!-- 增加按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="ehr:entryContract:edit">
			    <table:editRow url="${ctx}/ehr/entryContract/form" title="合同" id="contentTable"></table:editRow><!-- 编辑按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="ehr:entryContract:del">
				<table:delRow url="${ctx}/ehr/entryContract/deleteAll" id="contentTable"></table:delRow><!-- 删除按钮 -->
			</shiro:hasPermission> --%>
	       <!-- <button class="btn btn-white btn-sm " data-toggle="tooltip" data-placement="left" onclick="sortOrRefresh()" title="刷新"><i class="glyphicon glyphicon-repeat"></i> 刷新</button> -->
		
			</div>
		<div class="pull-right">
			<!-- <button  class="btn btn-primary btn-rounded btn-outline btn-sm " onclick="search()" ><i class="fa fa-search"></i> 查询</button>
			<button  class="btn btn-primary btn-rounded btn-outline btn-sm " onclick="reset()" ><i class="fa fa-refresh"></i> 重置</button> -->
		</div>
	</div>
	</div>
	
	<!-- 表格 -->
	<table id="contentTable" class="table table-striped table-bordered table-hover table-condensed dataTables-example dataTable">
		<thead>
			<tr>
				<!-- <th> <input type="checkbox" class="i-checks"></th> -->
				<th  class="">序号</th>
				<th  class="">审核状态</th>
				<th  class=" ">姓名</th>
				<th  class=" ">公司</th>
				<th  class=" ">部门</th>
				<th  class=" ">提交日期</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="userInfo" varStatus="s">
			<tr>
				<%-- <td> <input type="checkbox" id="${userInfo.id}" class="i-checks"></td> --%>
				<td>
					${s.count}
				</td>
				<td>
				   <%-- <a  href="#" onclick="openDialogView('查看合同', '${ctx}/ehr/entryContract/form?id=${entryContract.id}','800px', '500px')">
					${entryContract.remarks}
				</a> --%>
					${fns:getDictLabel(userInfo.leavestatus, 'common_permit', '')}
				</td>
				<td>
				    ${userInfo.fullname}
				</td>
				<td>
				    ${fns:getByOfficeId(userInfo.createBy.company.id).name}
				</td>
				<td>
				    ${fns:getByOfficeId(userInfo.createBy.office.id).name}
				</td>
				<td>
				    <fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${userInfo.leavedate}" />
				</td>
				<td>
						<a href="javascript:" vl="${userInfo.leaveurl}" onclick="commonFileDownLoad(this)" class="btn btn-info btn-xs" ><i class="glyphicon glyphicon-save"></i>下载离职申请</a>
					<c:if test="${userInfo.resignation!=null&&userInfo.resignation!=''}">
						<a href="javascript:" vl="${userInfo.resignation}" onclick="commonFileDownLoad(this)" class="btn btn-warning btn-xs" ><i class="glyphicon glyphicon-save"></i>下载辞职报告</a>
					</c:if>

    					<shiro:hasPermission name="ehr:leaveSheet:permit">
	    					<a href="${ctx}/ehr/entry/permitLeave?id=${userInfo.id}&pt=yes" onclick="return confirmx('确认审核通过？', this.href)"   class="btn btn-success btn-xs" ><i class="glyphicon glyphicon-ok"></i>审核通过</a>
							<a href="${ctx}/ehr/entry/permitLeave?id=${userInfo.id}&pt=no" onclick="return confirmx('确认审核不通过？', this.href)"   class="btn btn-danger btn-xs"><i class="glyphicon glyphicon-remove"></i>审核不通过</a>
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