<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>数据选择</title>
	<meta name="decorator" content="blank"/>
	<script type="text/javascript">
		$(document).ready(function(){
			$("#crtGroup").click(function(){
                var ids = [], names = [], nodes = [];
                parent.layer.open({
                    type: 2,
                    //area: ['300px', '420px'],
                    area: ['550px', '60%'],
                    title:"添加组",
                    ajaxData:{selectIds: ''},
                    //content: "${ctx}/tag/treeselect?url="+encodeURIComponent("/sys/office/treeData?type=3")+"&module=&checked=true&extId=&isAll=" ,
                    content: "${ctx}/leipiflow/leipiFlow/groupform",
                    btn: ['确定', '关闭']
                    ,yes: function(index, layero){ //或者使用btn1
                        var groupname = layero.find("iframe")[0].contentWindow.$("#groupname").val();
                        var groupuIds = layero.find("iframe")[0].contentWindow.$("#oagroupId").val();
                        var groupuNames = layero.find("iframe")[0].contentWindow.$("#oagroupName").val();
                        // alert(groupname+"    "+groupuIds+"   "+groupuNames);

                        $.post("${ctx}/leipiflow/leipiFlow/saveGroup",
                            {
                                "groupname":groupname,
                                "groupuIds":groupuIds,
                                "groupuNames":groupuNames
                            },function(data){
                                var jsonData = jQuery.parseJSON(data);
                                if(jsonData.status == 'y'){
                                    parent.layer.confirm(jsonData.info, {
                                        btn: ['确认'] //按钮
                                    }, function(){
                                        parent.layer.closeAll();
                                    });
                                }else{
                                    layer.msg(jsonData.info, {icon: 2});
                                }
                            });


                    },
                    cancel: function(index){ //或者使用btn2
                        //按钮【按钮二】的回调
                    }
                });
			});
		});
		function choose(obj) {
			if($(obj).attr("vl") != ""){
			    var ids = $(obj).attr("vl");
			    var idsArr = ids.split(",");
                var names = $(obj).attr("vl2");
                var namesArr = names.split(",");
			    var contain = $("#selectArea").find("ol");
                var html = "";
			    for(var i=0; i<idsArr.length; i++){
                    html += '<li vl="'+ idsArr[i] +'"><a href="javascript:">'+ namesArr[i] +'</a></li>';

				}
                contain.html(html);
			    $("#ids").val(ids);
                $("#names").val(names);
			}
        }
        function delGroup(obj) {
		    var groupid = $(obj).attr("vl");
            var ii = layer.confirm('确定删除此分组？', {
                btn: ['提交','取消'], //按钮
                shade: false //不显示遮罩
            }, function(index){
                $.post("${ctx}/oa/oaNotify/delOaGroup",
                    {
                        "groupid":groupid
                    },function(data){
                        var jsonData = jQuery.parseJSON(data);
                        if(jsonData.status == 'y'){
                            layer.close(ii);
                            $(obj).closest("li").remove();
                            $("#selectArea").find("ol").empty();
                            $("#ids").val("");
                            $("#names").val("");
                        }else{
                            layer.msg(jsonData.info, {icon: 2});
                        }
                    });
            });

        }
	</script>
</head>
<body>
	<div class="clearfix">
		<div style="padding: 10px; float:left; width: 270px;" id="searchBox"><small><a href="javascript:" id="crtGroup" ><i class="glyphicon glyphicon-plus"></i>创建分组</a></small></div>
		<div style="padding: 10px; float:left;"><small>已选人员：</small></div>
	</div>
	<div id="tree" class="ztree pull-left" style="padding: 0px 30px 15px 20px; border-right:1px dashed grey; height: 85%; overflow-y: auto; width: 260px;">
		<c:if test="${fn:length(groups) == 0}">
		暂无分组
		</c:if>
		<c:if test="${fn:length(groups) > 0}">
			<ol id="ol_group">
			<c:forEach items="${groups}" var="group" >
				<li class="rel"><button onclick="delGroup(this)" vl="${group.id}" type="button" class="close" aria-label="Close"><span aria-hidden="true">&times;</span></button><a href="javascript:" onclick="choose(this)" vl="${group.ids}" vl2="${group.names}">${group.groupname}</a></li>
			</c:forEach>
			</ol>
		</c:if>
	</div>
	<div id="selectArea" class="pull-left"><ol></ol></div>
<input type="hidden" id="ids" name="ids" value="" />
	<input type="hidden" id="names" name="names" value="" />
</body>