<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>流程步骤管理</title>
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
		<h5>流程步骤列表 </h5>
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
	<form:form id="searchForm" modelAttribute="leipiFlowProcess" action="${ctx}/leipiflow/leipiFlowProcess/" method="post" class="form-inline">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<table:sortColumn id="orderBy" name="orderBy" value="${page.orderBy}" callback="sortOrRefresh();"/><!-- 支持排序 -->
		<div class="form-group">
		 </div>	
	</form:form>
	<br/>
	</div>
	</div>
	
	<!-- 工具栏 -->
	<div class="row">
	<div class="col-sm-12">
		<div class="pull-left">
			<shiro:hasPermission name="leipiflow:leipiFlowProcess:add">
				<table:addRow url="${ctx}/leipiflow/leipiFlowProcess/form" title="流程步骤"></table:addRow><!-- 增加按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="leipiflow:leipiFlowProcess:edit">
			    <table:editRow url="${ctx}/leipiflow/leipiFlowProcess/form" title="流程步骤" id="contentTable"></table:editRow><!-- 编辑按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="leipiflow:leipiFlowProcess:del">
				<table:delRow url="${ctx}/leipiflow/leipiFlowProcess/deleteAll" id="contentTable"></table:delRow><!-- 删除按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="leipiflow:leipiFlowProcess:import">
				<table:importExcel url="${ctx}/leipiflow/leipiFlowProcess/import"></table:importExcel><!-- 导入按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="leipiflow:leipiFlowProcess:export">
	       		<table:exportExcel url="${ctx}/leipiflow/leipiFlowProcess/export"></table:exportExcel><!-- 导出按钮 -->
	       	</shiro:hasPermission>
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
				<th> <input type="checkbox" class="i-checks"></th>
				<th  class="sort-column child_id">is_child 子流程id有return_step_to结束后继续父流程下一步</th>
				<th  class="sort-column child_after">子流程 结束后动作 0结束并更新父流程节点为结束  1结束并返回父流程步骤</th>
				<th  class="sort-column child_back_process">子流程结束返回的步骤id</th>
				<th  class="sort-column return_sponsor_ids">[保留功能]主办人 子流程结束后下一步的主办人</th>
				<th  class="sort-column return_respon_ids">[保留功能]经办人 子流程结束后下一步的经办人</th>
				<th  class="sort-column write_fields">这个步骤可写的字段</th>
				<th  class="sort-column secret_fields">这个步骤隐藏的字段</th>
				<th  class="sort-column lock_fields">锁定不能更改宏控件的值</th>
				<th  class="sort-column check_fields">字段验证规则</th>
				<th  class="sort-column auto_person">本步骤的自动选主办人规则0:为不自动选择1：流程发起人2：本部门主管3指定默认人4上级主管领导5. 一级部门主管6. 指定步骤主办人</th>
				<th  class="sort-column auto_unlock">是否允许修改主办人auto_type>0 0不允许 1允许（默认）</th>
				<th  class="sort-column auto_sponsor_ids">3指定步骤主办人ids</th>
				<th  class="sort-column auto_sponsor_text">3指定步骤主办人text</th>
				<th  class="sort-column auto_respon_ids">3指定步骤主办人ids</th>
				<th  class="sort-column auto_respon_text">3指定步骤主办人text</th>
				<th  class="sort-column auto_role_ids">制定默认角色ids</th>
				<th  class="sort-column auto_role_text">制定默认角色text</th>
				<th  class="sort-column auto_process_sponsor">[保留功能]指定其中一个步骤的主办人处理</th>
				<th  class="sort-column range_user_ids">本步骤的经办人授权范围ids</th>
				<th  class="sort-column range_user_text">本步骤的经办人授权范围text</th>
				<th  class="sort-column range_dept_ids">本步骤的经办部门授权范围</th>
				<th  class="sort-column range_dept_text">本步骤的经办部门授权范围text</th>
				<th  class="sort-column range_role_ids">本步骤的经办角色授权范围ids</th>
				<th  class="sort-column range_role_text">本步骤的经办角色授权范围text</th>
				<th  class="sort-column receive_type">0明确指定主办人1先接收者为主办人</th>
				<th  class="sort-column is_user_end">允许主办人在非最后步骤也可以办结流程</th>
				<th  class="sort-column is_userop_pass">经办人可以转交下一步</th>
				<th  class="sort-column is_sing">会签选项0禁止会签1允许会签（默认） 2强制会签</th>
				<th  class="sort-column sign_look">会签可见性0总是可见（默认）,1本步骤经办人之间不可见2针对其他步骤不可见</th>
				<th  class="sort-column out_condition">转出条件</th>
				<th  class="sort-column setleft">左 坐标</th>
				<th  class="sort-column settop">上 坐标</th>
				<th  class="sort-column style">样式 序列化</th>
				<th  class="sort-column isdel">是否删除</th>
				<th  class="sort-column updatetime">更新时间</th>
				<th  class="sort-column dateline">结束时间</th>
				<th  class="sort-column is_back">是否允许回退0不允许（默认） 1允许退回上一步2允许退回之前步骤</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="leipiFlowProcess">
			<tr>
				<td> <input type="checkbox" id="${leipiFlowProcess.id}" class="i-checks"></td>
				<td><a  href="#" onclick="openDialogView('查看流程步骤', '${ctx}/leipiflow/leipiFlowProcess/form?id=${leipiFlowProcess.id}','800px', '500px')">
					${leipiFlowProcess.child_id}
				</a></td>
				<td>
					${leipiFlowProcess.child_after}
				</td>
				<td>
					${leipiFlowProcess.child_back_process}
				</td>
				<td>
					${leipiFlowProcess.return_sponsor_ids}
				</td>
				<td>
					${leipiFlowProcess.return_respon_ids}
				</td>
				<td>
					${leipiFlowProcess.write_fields}
				</td>
				<td>
					${leipiFlowProcess.secret_fields}
				</td>
				<td>
					${leipiFlowProcess.lock_fields}
				</td>
				<td>
					${leipiFlowProcess.check_fields}
				</td>
				<td>
					${leipiFlowProcess.auto_person}
				</td>
				<td>
					${leipiFlowProcess.auto_unlock}
				</td>
				<td>
					${leipiFlowProcess.auto_sponsor_ids}
				</td>
				<td>
					${leipiFlowProcess.auto_sponsor_text}
				</td>
				<td>
					${leipiFlowProcess.auto_respon_ids}
				</td>
				<td>
					${leipiFlowProcess.auto_respon_text}
				</td>
				<td>
					${leipiFlowProcess.auto_role_ids}
				</td>
				<td>
					${leipiFlowProcess.auto_role_text}
				</td>
				<td>
					${leipiFlowProcess.auto_process_sponsor}
				</td>
				<td>
					${leipiFlowProcess.range_user_ids}
				</td>
				<td>
					${leipiFlowProcess.range_user_text}
				</td>
				<td>
					${leipiFlowProcess.range_dept_ids}
				</td>
				<td>
					${leipiFlowProcess.range_dept_text}
				</td>
				<td>
					${leipiFlowProcess.range_role_ids}
				</td>
				<td>
					${leipiFlowProcess.range_role_text}
				</td>
				<td>
					${leipiFlowProcess.receive_type}
				</td>
				<td>
					${leipiFlowProcess.is_user_end}
				</td>
				<td>
					${leipiFlowProcess.is_userop_pass}
				</td>
				<td>
					${leipiFlowProcess.is_sing}
				</td>
				<td>
					${leipiFlowProcess.sign_look}
				</td>
				<td>
					${leipiFlowProcess.out_condition}
				</td>
				<td>
					${leipiFlowProcess.setleft}
				</td>
				<td>
					${leipiFlowProcess.settop}
				</td>
				<td>
					${leipiFlowProcess.style}
				</td>
				<td>
					${leipiFlowProcess.isdel}
				</td>
				<td>
					<fmt:formatDate value="${leipiFlowProcess.updatetime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					<fmt:formatDate value="${leipiFlowProcess.dateline}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${leipiFlowProcess.is_back}
				</td>
				<td>
					<shiro:hasPermission name="leipiflow:leipiFlowProcess:view">
						<a href="#" onclick="openDialogView('查看流程步骤', '${ctx}/leipiflow/leipiFlowProcess/form?id=${leipiFlowProcess.id}','800px', '500px')" class="btn btn-info btn-xs" ><i class="fa fa-search-plus"></i> 查看</a>
					</shiro:hasPermission>
					<shiro:hasPermission name="leipiflow:leipiFlowProcess:edit">
    					<a href="#" onclick="openDialog('修改流程步骤', '${ctx}/leipiflow/leipiFlowProcess/form?id=${leipiFlowProcess.id}','800px', '500px')" class="btn btn-success btn-xs" ><i class="fa fa-edit"></i> 修改</a>
    				</shiro:hasPermission>
    				<shiro:hasPermission name="leipiflow:leipiFlowProcess:del">
						<a href="${ctx}/leipiflow/leipiFlowProcess/delete?id=${leipiFlowProcess.id}" onclick="return confirmx('确认要删除该流程步骤吗？', this.href)"   class="btn btn-danger btn-xs"><i class="fa fa-trash"></i> 删除</a>
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