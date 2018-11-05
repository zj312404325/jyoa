<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>平台管理</title>
	<meta name="decorator" content="default"/>
	<%@include file="/webpage/include/treetable.jsp" %>
	<script type="text/javascript">
		$(document).ready(function() {
			
			var tpl = $("#treeTableTpl").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g,"");
			var data = ${fns:toJson(list)}, rootId = "${not empty institution.id ? institution.id : '0'}";
			addRow("#treeTableList", tpl, data, rootId, true);
			$("#treeTable").treeTable({expandLevel : 9});
			hidBtn();
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
    		
			window.location="${ctx}/sys/institution/";
    	}
		function hidBtn(){
			$("a[name=act1]").each(function(){
				var obj = $(this);
				var v = $(obj).attr("vl");
				if(v == '1'){
					$(obj).hide();
				}
		    });
			$("a[name=act2]").each(function(){
				var obj = $(this);
				var v = $(obj).attr("vl");
				if(v == '0'){
					$(obj).hide();
				}
		    });
            $("a[name=dl]").each(function(){
                var obj = $(this);
                var v = $(obj).attr("vl");
                if(v == ''){
                    $(obj).hide();
                }
            });
		}
		
		function updateSort() {
			loading('正在提交，请稍等...');
	    	$("#listForm").attr("action", "${ctx}/sys/institution/updateSort");
	    	$("#listForm").submit();
    	}
	</script>
</head>
<body class="gray-bg">
	<div class="wrapper wrapper-content">
	<div class="ibox">
	<div class="ibox-title">
		<h5>平台制度 </h5>
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
	
	<!-- 工具栏 -->
	<div class="row">
	<div class="col-sm-12">
		<div class="pull-left">
			<shiro:hasPermission name="sys:institution:add">
				<table:addRow url="${ctx}/sys/institution/formMuti" title="制度"></table:addRow><!-- 增加按钮 -->
			</shiro:hasPermission>
			<%-- <shiro:hasPermission name="sys:institution:edit">
			    <table:editRow url="${ctx}/sys/institution/form" title="oa制度" id="contentTable"></table:editRow><!-- 编辑按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="sys:institution:del">
				<table:delRow url="${ctx}/sys/institution/deleteAll" id="contentTable"></table:delRow><!-- 删除按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="sys:institution:import">
				<table:importExcel url="${ctx}/sys/institution/import"></table:importExcel><!-- 导入按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="sys:institution:export">
	       		<table:exportExcel url="${ctx}/sys/institution/export"></table:exportExcel><!-- 导出按钮 -->
	       	</shiro:hasPermission> --%>
	       	<shiro:hasPermission name="sys:institution:edit">
				<button id="btnSubmit" class="btn btn-white btn-sm " data-toggle="tooltip" data-placement="left" onclick="updateSort()" title="保存排序"><i class="fa fa-save"></i> 保存排序</button>
			</shiro:hasPermission>
	       <button class="btn btn-white btn-sm " data-toggle="tooltip" data-placement="left" onclick="refresh()" title="刷新"><i class="glyphicon glyphicon-repeat"></i> 刷新</button>
		
			</div>
		<!-- <div class="pull-right">
			<button  class="btn btn-primary btn-rounded btn-outline btn-sm " onclick="search()" ><i class="fa fa-search"></i> 查询</button>
			<button  class="btn btn-primary btn-rounded btn-outline btn-sm " onclick="reset()" ><i class="fa fa-refresh"></i> 重置</button>
		</div> -->
	</div>
	</div>

<form id="listForm" method="post">	
	<!-- 表格 -->
	<table id="treeTable" class="table table-striped table-bordered table-hover table-condensed dataTables-example dataTable">
		<thead>
			<tr>
				<!-- <th> <input type="checkbox" class="i-checks"></th> -->
				<th  class="sort-column title">标题</th>
				<th>排序</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody id="treeTableList"></tbody>
	</table>
	
	<script type="text/template" id="treeTableTpl">
		<tr id="{{row.id}}" pId="{{pid}}">
			<td>{{row.title}}</td>
			<td>	
				<input type="hidden" name="ids" value="{{row.id}}"/>
				<input name="sorts" type="text" value="{{row.sort}}" class="form-control" style="width:100px;margin:0;padding:0;text-align:center;">
			</td>
			<td>
				<shiro:hasPermission name="sys:institution:view">
					<a href="#" onclick="openDialogView('查看', '${ctx}/sys/institution/form?id={{row.id}}','800px', '60%')" class="btn btn-info btn-xs" ><i class="fa fa-search-plus"></i> 查看</a>
				</shiro:hasPermission>
				<shiro:hasPermission name="sys:institution:edit">
					<a href="#" onclick="openDialog('修改', '${ctx}/sys/institution/form?id={{row.id}}','800px', '60%')" class="btn btn-success btn-xs" ><i class="fa fa-edit"></i> 修改</a>
				</shiro:hasPermission>
				<shiro:hasPermission name="sys:institution:del">
					<a href="${ctx}/sys/institution/delete?id={{row.id}}" onclick="return confirmx('要删除该及所有子项吗？', this.href)" class="btn btn-danger btn-xs"><i class="fa fa-trash"></i> 删除</a>
				</shiro:hasPermission>
				<shiro:hasPermission name="sys:institution:add">
					<a href="#" name="act1" vl="{{row.itype}}" onclick="openDialog('添加下级', '${ctx}/sys/institution/formMuti?parent.id={{row.id}}','800px', '60%')" class="btn  btn-primary btn-xs"><i class="fa fa-plus"></i> 添加下级</a>
				</shiro:hasPermission>
				<a href="#" name="dl" vl="{{row.sourcefileurl}}" onclick="commonFileDownLoad(this)"   class="btn btn-primary btn-xs"><i class="fa fa-file-text-o"></i> 源文件下载</a>
				<%--<shiro:hasPermission name="sys:institution:edit">
					<a href="#" name="act2" vl="{{row.itype}}" onclick="openDialog('制度详情', '${ctx}/sys/institution/contentForm?id={{row.id}}','500px', '460px')" class="btn btn-warning btn-xs" ><i class="fa fa-edit"></i> 制度详情</a>
				</shiro:hasPermission>--%>
			</td>
		</tr>
	</script>
</form>
	
	<br/>
	<br/>
	</div>
	</div>
</div>
</body>
</html>