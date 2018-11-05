<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>考核人设定</title>
	<meta name="decorator" content="default"/>
	<%@include file="/webpage/include/treeview.jsp" %>
	<style type="text/css">
		.ztree {overflow:auto;margin:0;_margin-top:10px;padding:10px 0 0 10px;}
	</style>
	<script type="text/javascript">
		function refresh(){//刷新
			
			window.location="${ctx}/checkmodel/checkUser/checkUserIndex?category=${category}";
		}
	</script>
</head>
<body class="gray-bg">
	
	<div class="wrapper wrapper-content">
	<div class="ibox">
	<div class="ibox-content">
	<sys:message content="${message}"/>
	<div id="content" class="row">
		<div id="left"  style="background-color:#e7eaec" class="leftBox col-sm-1">
			<a onclick="refresh()" class="pull-right">
				<i class="fa fa-refresh"></i>
			</a>
			<div id="ztree" class="ztree leftBox-content"></div>
		</div>
		<div id="right"  class="col-sm-11  animated fadeInRight">
			<c:if test="${checkUser.checkofficeid!=null&&checkUser.checkofficeid!='' }"><iframe id="userContent" name="userContent" src="${ctx}/checkmodel/checkUser/list?checkofficeid=${checkUser.checkofficeid}&checkofficename=${checkUser.checkofficename}&category=${category}" width="100%" height="91%" frameborder="0"></iframe></c:if>
			<c:if test="${checkUser.checkofficeid==null||checkUser.checkofficeid=='' }"><iframe id="userContent" name="userContent" src="" width="100%" height="91%" frameborder="0"></iframe></c:if>
		</div>
	</div>
	</div>
	</div>
	<script type="text/javascript">
		var setting = {data:{simpleData:{enable:true,idKey:"id",pIdKey:"pId",rootPId:'0'}},
			callback:{onClick:function(event, treeId, treeNode){
					var id = treeNode.id == '0' ? '' :treeNode.id;
					var officename = window.encodeURI(window.encodeURI(treeNode.name));
					$('#userContent').attr("src","${ctx}/checkmodel/checkUser/list?checkofficeid="+id+"&checkofficename="+officename+"&category=${category}");
				}
			}
		};
		
		function refreshTree(){
			$.getJSON("${ctx}/sys/office/treeData",function(data){
				$.fn.zTree.init($("#ztree"), setting, data).expandAll(true);
				if('${checkUser.checkofficeid}'!=''){
					var treeObj = $.fn.zTree.getZTreeObj("ztree");
					var node = treeObj.getNodeByParam("id", "${checkUser.checkofficeid}");
					treeObj.selectNode(node);
				}
			});
		}
		refreshTree();
		 
		var leftWidth = 180; // 左侧窗口大小
		var htmlObj = $("html"), mainObj = $("#main");
		var frameObj = $("#left, #openClose, #right, #right iframe");
		function wSize(){
			var strs = getWindowSize().toString().split(",");
			htmlObj.css({"overflow-x":"hidden", "overflow-y":"hidden"});
			mainObj.css("width","auto");
			frameObj.height(strs[0] - 120);
			var leftWidth = ($("#left").width() < 0 ? 0 : $("#left").width());
			$("#right").width($("#content").width()- leftWidth - $("#openClose").width() -61);
			$(".ztree").width(leftWidth - 10).height(frameObj.height() - 46);
		}
	</script>
	<script src="${ctxStatic}/common/wsize.min.js" type="text/javascript"></script>
</body>
</html>