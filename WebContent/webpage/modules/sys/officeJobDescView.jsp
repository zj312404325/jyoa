<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>职务说明书</title>
	<meta name="decorator" content="default"/>
	<script src="${ctxStatic}/common/contabs.js"></script>
	<%@include file="/webpage/include/treetable.jsp" %>
	<script type="text/javascript">
		$(document).ready(function() {window.parent.refreshTree();//刷新左边列表
			/*var tpl = $("#treeTableTpl").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g,"");
			var data = ${fns:toJson(list)}, rootId = "${not empty office.id ? office.id : '0'}";
			addRow("#treeTableList", tpl, data, rootId, true);
			$("#treeTable").treeTable({expandLevel : 5});

			$("#treeTableList").find("a[name=godown]").each(function(){
			    var obj = $(this);
			    if(obj.attr("vl")=='3'){
			        obj.hide();
				}
			});*/
            $('.frameItem').on('click', frameItem);
		});
        //iframe内元素链接
        function frameItem() {
            // 获取标识数据
            var dataUrl = $(this).attr('vl'),
                dataIndex = $(this).data('index'),
                menuName = $(this).attr('vl2'),
                flag = true;


            if (dataUrl == undefined || $.trim(dataUrl).length == 0)return false;

            // 选项卡菜单已存在
            $('.J_menuTab',parent.parent.document).each(function () {
                if ($(this).data('id') == dataUrl) {
                    if (!$(this).hasClass('active')) {
                        $(this).addClass('active').siblings('.J_menuTab').removeClass('active');
                        scrollToTab(this);
                        // 显示tab对应的内容区
                        $('.J_mainContent .J_iframe',parent.parent.document).each(function () {

                            if ($(this).data('id') == dataUrl) {
                                $(this).show().siblings('.J_iframe').hide();
                                $(this).attr("src",dataUrl);
                                return false;
                            }
                        });
                    }
                    flag = false;
                    return false;
                }
            });

            // 选项卡菜单不存在
            if (flag) {
                var str = '<a href="javascript:;" class="active J_menuTab" data-id="' + dataUrl + '">' + menuName + ' <i class="fa fa-times-circle"></i></a>';
                $('.J_menuTab',parent.parent.document).removeClass('active');

                // 添加选项卡对应的iframe
                var str1 = '<iframe class="J_iframe" name="iframe' + dataIndex + '" width="100%" height="100%" src="' + dataUrl + '" frameborder="0" data-id="' + dataUrl + '" seamless></iframe>';
                $('.J_mainContent',parent.parent.document).find('iframe.J_iframe').hide().parents('.J_mainContent').append(str1);

                //显示loading提示
                // var loading = layer.load();

                $('.J_mainContent iframe:visible').load(function () {
                    //iframe加载完成后隐藏loading提示
                    layer.close(loading);
                });
                // 添加选项卡
                $('.J_menuTabs .page-tabs-content',parent.parent.document).append(str);
                scrollToTab($('.J_menuTab.active'));
            }
            return false;
        }
		function addRow(list, tpl, data, pid, root){
			for (var i=0; i<data.length; i++){
				var row = data[i];
				if ((${fns:jsGetVal('row.parentId')}) == pid){
					$(list).append(Mustache.render(tpl, {
						dict: {
							type: getDictLabel(${fns:toJson(fns:getDictList('sys_office_type'))}, row.type)
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
			<%--<shiro:hasPermission name="sys:office:add">
				<table:addRow url="${ctx}/sys/office/form?parent.id=${office.id}" title="机构" width="800px" height="620px" target="officeContent"></table:addRow><!-- 增加按钮 -->
			</shiro:hasPermission>
            <c:if test="${office.name != null}">
                <button class="btn btn-white btn-sm" data-toggle="tooltip" data-placement="left" onclick="openDialog('上传职务说明书', '/a/sys/office/jobDescUpload?id=${office.id}','500px', '420px')" title="档案管理添加"><i class="fa fa-plus"></i> 上传职务说明书</button>
            </c:if>
	        <button class="btn btn-white btn-sm " data-toggle="tooltip" data-placement="left" onclick="refresh()" title="刷新"><i class="glyphicon glyphicon-repeat"></i> 刷新</button>--%>
			</div>
	</div>
	</div>
	<table id="treeTable" class="table table-striped table-bordered table-hover table-condensed dataTables-example dataTable">
		<thead><tr><th>文档名称</th><th>操作</th></tr></thead>
		<tbody id="treeTableList">

        <c:if test="${officePostDesc.file1 != null}">
		<tr>
			<td>职务说明书</td>
			<td>
				<c:if test="${officePostDesc.file1 != null}">
                    <a class="frameItem btn btn-info btn-xs" href="javascript:" vl="${ctx}/sys/office/viewPdf?fileurl=${officePostDesc.file1}" vl2="查看-职务说明书" data-index="${officePostDesc.file1}1" >
                        <i class="fa fa-search-plus"></i> 查看
                    </a>
				</c:if>
			</td>
		</tr>
		</c:if>
		<c:if test="${officePostDesc.file2 != null}">
		<tr>
			<td>绩效考核指标</td>
			<td>
				<c:if test="${officePostDesc.file2 != null}">
					<a class="frameItem btn btn-info btn-xs" href="javascript:" vl="${ctx}/sys/office/viewPdf?fileurl=${officePostDesc.file2}" vl2="查看-绩效考核指标" data-index="${officePostDesc.file2}2" >
						<i class="fa fa-search-plus"></i> 查看
					</a>
				</c:if>
			</td>
		</tr>
        </c:if>

		</tbody>
	</table>
	</div>
	<%--<script type="text/template" id="treeTableTpl">
		<tr id="{{row.id}}" pId="{{pid}}">
			<td><a  href="#" onclick="openDialogView('查看机构', '${ctx}/sys/office/form?id={{row.id}}','800px', '620px')">{{row.name}}</a></td>
			<td>{{row.area.name}}</td>
			<td>{{dict.type}}</td>
			<td>{{row.grade}}</td>
			<td>{{row.remarks}}</td>
			<td>
				<shiro:hasPermission name="sys:office:view">
					<a href="#" onclick="openDialogView('查看机构', '${ctx}/sys/office/form?id={{row.id}}','800px', '620px')" class="btn btn-info btn-xs" ><i class="fa fa-search-plus"></i> 查看</a>
				</shiro:hasPermission>
				<shiro:hasPermission name="sys:office:edit">
					<a href="#" onclick="openDialog('修改机构', '${ctx}/sys/office/form?id={{row.id}}','800px', '620px', 'officeContent')" class="btn btn-success btn-xs" ><i class="fa fa-edit"></i> 修改</a>
				</shiro:hasPermission>
				<shiro:hasPermission name="sys:office:del">
					<a href="${ctx}/sys/office/delete?id={{row.id}}" onclick="return confirmx('要删除该机构及所有子机构项吗？', this.href)" class="btn btn-danger btn-xs"><i class="fa fa-trash"></i> 删除</a>
				</shiro:hasPermission>
				<shiro:hasPermission name="sys:office:auth"> 
					<a href="#" onclick="openDialog('权限设置', '${ctx}/sys/office/auth?id={{row.id}}','350px', '700px')" class="btn  btn-warning btn-xs" ><i class="fa fa-edit"></i> 权限设置</a> 
				</shiro:hasPermission>
				<shiro:hasPermission name="sys:office:add">
					<a href="#" name="godown" vl="{{row.type}}" onclick="openDialog('添加下级机构', '${ctx}/sys/office/form?parent.id={{row.id}}','800px', '620px', 'officeContent')" class="btn  btn-primary btn-xs"><i class="fa fa-plus"></i> 添加下级机构</a>
				</shiro:hasPermission>
			</td>
		</tr>
	</script>--%>
</body>
</html>