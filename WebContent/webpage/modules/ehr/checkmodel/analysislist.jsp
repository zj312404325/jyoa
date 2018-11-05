<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>绩效分析表</title>
	<meta name="decorator" content="default"/>
	<%@include file="/webpage/include/treetable.jsp" %>
	<script type="text/javascript">
        $(document).ready(function() {
            var tpl = $("#treeTableTpl").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g,"");
            var data = ${fns:toJson(list)}, rootId = "${not empty office.id ? office.id : '0'}";
            addRow("#treeTableList", tpl, data, rootId, true);
            $("#treeTable").treeTable({expandLevel : 5});

            $("#treeTableList").find("a[name=godown]").each(function(){
                var obj = $(this);
                if(obj.attr("vl")=='3'){
                    obj.hide();
                }
            });
        });
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
        function searchPerformCheck() {
            var searchType=$("#searchType").val();
            if(searchType!=null&&searchType!=''){
				var checkyear=$("#checkyear").val();
                if(checkyear==null||checkyear==''){
                    layer.alert("请选择年份！");
                    return false;
				}
                if(searchType=='1'){//季度
                    var checkquarter=$("#checkquarter").val();
                    if(checkquarter==null||checkquarter==''){
                        layer.alert("请选择季度！");
                        return false;
                    }
				}
			}
			$("#searchForm").submit();
        }
        $(function () {
            if('${office.checkquarter}'!=''){
                $("#checkquarter").show();
			}

            $("#searchType").change(function () {
                var searchType=$("#searchType").val();
                if(searchType=='1'){
                    $("#checkquarter").val("");
                    $("#checkyear").val("");
                    $("#checkquarterspan").show();
                    $("#checkquarter").show();
                }else {
                    $("#checkquarterspan").hide();
                    $("#checkquarter").hide();
                    $("#checkquarter").val("");
                    $("#checkyear").val("");
				}
            });
        });
	</script>
</head>
<body>
	<div class="wrapper wrapper-content">
	<sys:message content="${message}"/>
		<!-- 工具栏 -->
	<div class="row">
	<div class="col-sm-12">
		<form:form id="searchForm" modelAttribute="office" action="${ctx}/checkmodel/performanceCheck/analysislist" method="post" class="form-inline pull-left">
			<table:sortColumn id="orderBy" name="orderBy" value="${orderBy}" callback="sortOrRefresh();"/><!-- 支持排序 -->
			<div class="form-group">
				<span>统计类型：</span>
				<form:select path="searchType"  class="form-control m-b required">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('statisticType')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>&nbsp;<span>年份：</span>
				<input id="checkyear" name="checkyear" type="text" maxlength="20" onclick="WdatePicker({skin:'default',dateFmt:'yyyy'})" class="laydate-icon form-control layer-date required"
					   value="${office.checkyear}"/>&nbsp;
				<span id="checkquarterspan" style="display:none;">季度：</span>
				<form:select path="checkquarter" class="form-control required" style="display:none;">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('checkQuarter')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>

			</div>
		</form:form>
		<div class="pull-left" style="padding-left: 20px;">
			<button  class="btn btn-primary btn-rounded btn-outline btn-sm " onclick="searchPerformCheck()"><i class="fa fa-search"></i> 查询</button>
			<button  class="btn btn-primary btn-rounded btn-outline btn-sm " onclick="resetform()" ><i class="fa fa-refresh"></i> 重置</button>
		</div>
	</div>
	</div>
	<table id="treeTable" class="table table-striped table-bordered table-hover table-condensed dataTables-example dataTable">
		<thead><tr><th>部门</th><th>员工数</th><%--<th class="sort-column selfscore">自评平均分</th><th class="sort-column score">考核平均分</th>--%><th>自评平均分</th><th>考核平均分</th><th>操作</th></tr></thead>
		<tbody id="treeTableList"></tbody>
	</table>
	</div>
	<script type="text/template" id="treeTableTpl">
		<tr id="{{row.id}}" pId="{{pid}}">
			<td><a  href="#" onclick="openDialogView('查看', '${ctx}/checkmodel/performanceCheck/managerPerformChecklist?officeid={{row.id}}&checkyear=${office.checkyear}&checkquarter=${office.checkquarter}','800px', '620px')">{{row.name}}</a></td>
			<td>{{row.peoplecount}}</td>
			<td>{{row.selfscore}}</td>
			<td>{{row.score}}</td>
			<td>
				<a href="#" onclick="openDialogView('查看', '${ctx}/checkmodel/performanceCheck/managerPerformChecklist?officeid={{row.id}}&checkyear=${office.checkyear}&checkquarter=${office.checkquarter}','800px', '620px')" class="btn btn-info btn-xs" ><i class="fa fa-search-plus"></i> 查看</a>
			</td>
		</tr>
	</script>
</body>
</html>