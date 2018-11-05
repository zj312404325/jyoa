<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>数据选择</title>
	<meta name="decorator" content="blank"/>
	<%@include file="/webpage/include/treeview.jsp" %>
	<script type="text/javascript">
		var key, lastValue = "", nodeList = [], type = getQueryString("type", "${url}");
		var tree, setting = {view:{selectedMulti:false,dblClickExpand:false},check:{enable:"${checked}",nocheckInherit:true},
				async:{enable:(type==3),url:"${ctx}/sys/user/treeData",autoParam:["id=officeId"]},
				data:{simpleData:{enable:true}},callback:{<%--
					beforeClick: function(treeId, treeNode){
						if("${checked}" == "true"){
							//tree.checkNode(treeNode, !node.checked, true, true);
							tree.expandNode(treeNode, true, false, false);
						}
					}, --%>
					onClick:function(event, treeId, treeNode){
						tree.expandNode(treeNode);
						if(!treeNode.isParent){
						    var flag=true;
                            $("#selectArea ol").find("li").each(function(){
                                if($(this).attr("vl") == treeNode.id){
                                    flag = false;
								}
							});
                            if(flag) {
                                tree.checkNode(treeNode, true, true);
                                $("#selectArea ol").append('<li name="selectName" vl="' + treeNode.id + '"><a href="javascript:" onclick="del(this)"><i class="glyphicon glyphicon-user"></i>&nbsp;' + treeNode.name + '</a></li>');
                            }
                            total();
						};
					},onCheck: function(e, treeId, treeNode){
                    	tree.setting.check.chkboxType = { "Y" : "ps", "N" : "ps" };//
						var nodes = tree.getCheckedNodes(true);
						//$("#selectArea ol").empty();
						$("#selectArea ol").find("li").each(function() {
							if ($(this).attr("vl") == treeNode.id) {
								$(this).remove();
							}
						});
						for (var i = 0, l = nodes.length; i < l; i++) {
							tree.expandNode(nodes[i], true, false, false);
							if (!nodes[i].isParent) {
								var flag=true;
								$("#selectArea ol").find("li").each(function(){
                                    if($(this).attr("vl") == nodes[i].id){
                                        flag = false;
                                    }
								});
								if(flag) {
									$("#selectArea ol").append('<li name="selectName" vl="' + nodes[i].id + '"><a href="javascript:" onclick="del(this)"><i class="glyphicon glyphicon-user"></i>&nbsp;' + nodes[i].name + '</a></li>');
								}
							}
						}

                    	total();
						return false;
					},onAsyncSuccess: function(event, treeId, treeNode, msg){
						/* var nodes = tree.getNodesByParam("pId", treeNode.id, null);
						for (var i=0, l=nodes.length; i<l; i++) {
							try{tree.checkNode(nodes[i], treeNode.checked, true);}catch(e){}
							//tree.selectNode(nodes[i], false);
						} */
						selectCheckNode();
					},onDblClick: function(){//<c:if test="${!checked}">
						top.$.jBox.getBox().find("button[value='ok']").trigger("click");
						//$("input[type='text']", top.mainFrame.document).focus();//</c:if>
					}
				}
			};
		function expandNodes(nodes) {
			if (!nodes) return;
			for (var i=0, l=nodes.length; i<l; i++) {
				tree.expandNode(nodes[i], true, false, false);
				if (nodes[i].isParent && nodes[i].zAsync) {
					expandNodes(nodes[i].children);
				}
			}
		}
		$(document).ready(function(){


			$.get("${ctx}${url}${fn:indexOf(url,'?')==-1?'?':'&'}&extId=${extId}&isAll=${isAll}&module=${module}&t="
					+ new Date().getTime(), function(zNodes){
				// 初始化树结构
				tree = $.fn.zTree.init($("#tree"), setting, zNodes);
				
				//if("${isAll}" != 'true'){
					// 默认展开一级节点
					var nodes = tree.getNodesByParam("level", 0);
					for(var i=0; i<nodes.length; i++) {
						tree.expandNode(nodes[i], true, false, false);
					}
				//}
				
				//异步加载子节点（加载用户）
				var nodesOne = tree.getNodesByParam("isParent", true);
				for(var j=0; j<nodesOne.length; j++) {
					tree.reAsyncChildNodes(nodesOne[j],"!refresh",true);
				}
				selectCheckNode();

			});
			key = $("#key");
			key.bind("focus", focusKey).bind("blur", blurKey).bind("change cut input propertychange", searchNode);
			key.bind('keydown', function (e){if(e.which == 13){searchNode();}});
			setTimeout("search();$(\"#searchBox\").width($(\"#tree\").width() + 30);", "300");

		});
		
		// 默认选择节点
		function selectCheckNode(){
            /*if("${checked}" == "true" && "${notAllowSelectParent}" == "false"){
                tree.setting.check.chkboxType = { "Y" : "p", "N" : "s" };//默认勾选父类不勾选子类
			}*/
            tree.setting.check.chkboxType = { "Y" : "p", "N" : "s" };//默认勾选父类不勾选子类
			var ids = "${selectIds}".split(",");
			for(var i=0; i<ids.length; i++) {
				var node = tree.getNodeByParam("id", (type==3?"u_":"")+ids[i]);
				if("${checked}" == "true"){
					try{tree.checkNode(node, true, true);}catch(e){}
					tree.selectNode(node, false);
				}else{
					tree.selectNode(node, true);
				}
			}
            tree.setting.check.chkboxType = { "Y" : "ps", "N" : "ps" };//
		}
	  	function focusKey(e) {
			if (key.hasClass("empty")) {
				key.removeClass("empty");
			}
		}
		function blurKey(e) {
			if (key.get(0).value === "") {
				key.addClass("empty");
			}
			searchNode(e);
		}
		
		//搜索节点
		function searchNode() {
			// 取得输入的关键字的值
			var value = $.trim(key.get(0).value);
			
			// 按名字查询
			var keyType = "name";<%--
			if (key.hasClass("empty")) {
				value = "";
			}--%>
			
			// 如果和上次一次，就退出不查了。
			if (lastValue === value) {
				return;
			}
			
			// 保存最后一次
			lastValue = value;
			
			var nodes = tree.getNodes();
			// 如果要查空字串，就退出不查了。
			if (value == "") {
				showAllNode(nodes);
				return;
			}
			hideAllNode(nodes);
			nodeList = tree.getNodesByParamFuzzy(keyType, value);
			updateNodes(nodeList);
		}
		
		//隐藏所有节点
		function hideAllNode(nodes){			
			nodes = tree.transformToArray(nodes);
			for(var i=nodes.length-1; i>=0; i--) {
				tree.hideNode(nodes[i]);
			}
		}
		
		//显示所有节点
		function showAllNode(nodes){			
			nodes = tree.transformToArray(nodes);
			for(var i=nodes.length-1; i>=0; i--) {
				/* if(!nodes[i].isParent){
					tree.showNode(nodes[i]);
				}else{ */
					if(nodes[i].getParentNode()!=null){
						tree.expandNode(nodes[i],false,false,false,false);
					}else{
						tree.expandNode(nodes[i],true,true,false,false);
					}
					tree.showNode(nodes[i]);
					showAllNode(nodes[i].children);
				/* } */
			}
		}
		
		//更新节点状态
		function updateNodes(nodeList) {
			tree.showNodes(nodeList);
			var str = "";
            $("#selectArea ol").find("li").each(function() {
                /* if ($(this).attr("vl") == nodeList[i].id) {
                     tree.checkNode(nodeList[i], true, true);
                 }else{
                     tree.checkNode(nodeList[i], false, false);
                 }*/
                str += $(this).attr("vl")+",";
            });
			for(var i=0, l=nodeList.length; i<l; i++) {
				if(str.indexOf(nodeList[i].id) > -1){
                    tree.checkNode(nodeList[i], true, true);
				}else{
                    tree.checkNode(nodeList[i], false, false);
				}

				//展开当前节点的父节点
				tree.showNode(nodeList[i].getParentNode()); 
				//tree.expandNode(nodeList[i].getParentNode(), true, false, false);
				//显示展开符合条件节点的父节点
				while(nodeList[i].getParentNode()!=null){
					tree.expandNode(nodeList[i].getParentNode(), true, false, false);
					nodeList[i] = nodeList[i].getParentNode();
					tree.showNode(nodeList[i].getParentNode());
				}
				//显示根节点
				tree.showNode(nodeList[i].getParentNode());
				//展开根节点
				tree.expandNode(nodeList[i].getParentNode(), true, false, false);
			}
		}
		
		function del(obj){

            var nodes = tree.getCheckedNodes();

            for (var i=0, n=nodes.length; i<n; i++) {
				if(nodes[i].id == $(obj).parent("li").attr("vl")){
                    tree.cancelSelectedNode(nodes[i]);
                    tree.checkNode(nodes[i], false, false);
				}

            }
            $(obj).closest("li").remove();
            total();
		}
		function total(){
            var ids = [];
            var names = [];
            $("#selectArea ol").find("li").each(function(){
                ids.push($(this).attr("vl"));
                names.push($.trim($(this).find("a").text()));
            });
            var idsStr = ids.join(",").replace(/u_/ig,"");
            var namesStr = names.join(",").replace(/u_/ig,"");
            $("#ids").val(idsStr);
            $("#names").val(namesStr);
		}
	</script>
</head>
<body>
	<div class="clearfix">
		<div style="padding: 10px; float:left;" id="searchBox"><small>关键字：</small><input type="text" id="key" style="height: 18px;font-size: 85%;" /></div>
		<div style="padding: 10px; float:left;"><small>已选人员：</small></div>
	</div>
	<div id="tree" class="ztree pull-left" style="padding: 0px 30px 15px 20px; border-right:1px dashed grey; height: 85%; overflow-y: auto;"></div>
	<div id="selectArea" class="pull-left"><ol></ol></div>
<input type="hidden" id="ids" name="ids" value="" />
	<input type="hidden" id="names" name="names" value="" />
</body>