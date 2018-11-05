<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>档案管理</title>
	<meta name="decorator" content="default"/>
	<%@include file="/webpage/include/treetable.jsp" %>
	<script type="text/javascript">
		$(document).ready(function() {window.parent.refreshTree();//刷新左边列表
			var tpl = $("#treeTableTpl").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g,"");
			var data = ${fns:toJson(list)}, rootId = "${not empty archiveManager.id ? archiveManager.id : '0'}";
			addRow("#treeTableList", tpl, data, rootId, true);
			$("#treeTable").treeTable({expandLevel : 5});
		});
		function addRow(list, tpl, data, pid, root){
			for (var i=0; i<data.length; i++){
				var row = data[i];
				if ((${fns:jsGetVal('row.parentId')}) == pid){
					$(list).append(Mustache.render(tpl, {
						dict: {
							//type: getDictLabel(${fns:toJson(fns:getDictList('sys_office_type'))}, row.type)
						}, pid: (root?0:pid), row: row
					}));
					addRow(list, tpl, data, row.id);
				}
			}
		}
		function refresh(){//刷新或者排序，页码不清零
			window.parent.location.reload();
			//window.location="${ctx}/sys/office/list";
    	}
	</script>
</head>
<body>
	<div class="wrapper wrapper-content">
	<sys:message content="${message}"/>
		<!-- 工具栏 -->
	<div class="row">
	<div class="col-sm-12">
		<div class="pull-left">
			<shiro:hasPermission name="ehr:archiveManager:add">
				<table:addRow url="${ctx}/ehr/archiveManager/form" title="信息"></table:addRow><!-- 增加按钮 -->
			</shiro:hasPermission>
	        <button class="btn btn-white btn-sm " data-toggle="tooltip" data-placement="left" onclick="refresh()" title="刷新"><i class="glyphicon glyphicon-repeat"></i> 刷新</button>
			</div>
	</div>
	</div>
	<table id="treeTable" class="table table-striped table-bordered table-hover table-condensed dataTables-example dataTable">
		<thead><tr><th>名称</th><th>操作</th></tr></thead>
		<tbody id="treeTableList"></tbody>
	</table>
	</div>
	<script type="text/template" id="treeTableTpl">
		<tr id="{{row.id}}" pId="{{pid}}">
			<td><a  href="#" onclick="openDialogView('查看', '${ctx}/ehr/archiveManager/form?id={{row.id}}','800px', '620px')">{{row.name}}</a></td>
			<td>
				<shiro:hasPermission name="ehr:archiveManager:view">
					<a href="#" onclick="openDialogView('查看', '${ctx}/ehr/archiveManager/form?id={{row.id}}','800px', '620px')" class="btn btn-info btn-xs" ><i class="fa fa-search-plus"></i> 查看</a>
				</shiro:hasPermission>
				<shiro:hasPermission name="ehr:archiveManager:edit">
					<a href="#" onclick="openDialog('修改', '${ctx}/ehr/archiveManager/form?id={{row.id}}','800px', '620px', '')" class="btn btn-success btn-xs" ><i class="fa fa-edit"></i> 修改</a>
				</shiro:hasPermission>
				<shiro:hasPermission name="ehr:archiveManager:del">
					<a href="${ctx}/ehr/archiveManager/delete?id={{row.id}}" onclick="return confirmx('要删除该目录及所有子目录项吗？', this.href)" class="btn btn-danger btn-xs"><i class="fa fa-trash"></i> 删除</a>
				</shiro:hasPermission>
				<shiro:hasPermission name="ehr:archiveManager:add">
					<a href="#" onclick="openDialog('添加下级', '${ctx}/ehr/archiveManager/form?parent.id={{row.id}}','800px', '620px', '')" class="btn  btn-primary btn-xs"><i class="fa fa-plus"></i> 添加下级</a>
				</shiro:hasPermission>
			</td>
		</tr>
	</script>
</body>
</html>