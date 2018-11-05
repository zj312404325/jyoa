<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>传阅管理</title>
	<meta name="decorator" content="default"/>
	<script src="${ctxStatic}/common/contabs.js" type="text/javascript"></script>
	<script src="${ctxStatic}/jquery/jQSelect.js" type="text/javascript"></script>
	<style type="text/css">
		/*select css*/
		.selectbox{width:100%; position:relative; padding:4px 12px;}
		.selectbox i { position:absolute; right:6px; top:12px; width:0;height:0;border-style:solid dashed dashed dashed;border-width:5px;border-color:#999 #fff #fff #fff;overflow:hidden;zoom:1;font-size:0;}
		.listTxt{background:none;border:none;cursor:pointer;width:80%;border:none; font-size:14px; line-height:20px;}
		.cartes{width:100%;}
		.v3s{cursor:pointer;font-size:14px;height:24px;line-height:24px;color:#686868; text-align:left;text-overflow:ellipsis;overflow:hidden;white-space:nowrap;}
		.lists{background:#fff; border:1px solid #cfdadd; margin-left:-12px;position:absolute;top:29px; border-top:0;display:none;width:100%;z-index:20;}
		.lists .list{margin:1px 0px 1px 0;padding:0 0 0 0; overflow:hidden;max-height:200px; _height:expression(this.scrollHeight > 200 ? "200px":""); overflow-y:auto;}
		.lists li{padding:0px 0px 0px 8px;cursor:pointer;font-size:12px;height:24px;line-height:24px;color:#5e5e5e; margin-bottom:1px; position:relative;}
		.lists li.cgray{background:#f0f0f0;color:#5e5e5e; -webkit-transition: all .2s ease-in-out;-moz-transition: all .2s ease-in-out;-o-transition: all .2s ease-in-out;-ms-transition: all .2s ease-in-out;transition: all .2s ease-in-out;}
		.lists li.cwhite{background:#FFF;}
		.hover .lists{display:block;}
		.lists li b {position:absolute; right:6px; top:6px;}
        .clearfix {
            *zoom: 1;
        }

        .clearfix:before,
        .clearfix:after {
            display: table;

        }

        .clearfix:after {
            clear: both;
        }
        ol#filearea li{
            list-style-position: outside;
            padding-left: 10px;
        }


    </style>
	<script type="text/javascript">
		var validateForm;
		function doSubmit(){//回调函数，在编辑和保存动作时，供openDialog调用提交表单。
		  if(validateForm.form()){
			  $("#inputForm").submit();
			  return true;
		  }
	
		  return false;
		}
		$(document).ready(function() {
			//$("#name").focus();
			validateForm = $("#inputForm").validate({
				submitHandler: function(form){
					loading('正在提交，请稍等...');
					form.submit();
				},
				errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					$("#messageBox").text("输入有误，请先更正。");
					if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
						error.appendTo(element.parent().parent());
					} else {
						error.insertAfter(element);
					}
				}
			});

			$("#reSure").click(function(){
				$("#reSureArea").show();
                $("#message").focus();
			});

			$("#closeComment").click(function(){
                $("#reSureArea").hide();
			});

			$("#select01").jQSelect({ id: "typeIDVal",vl: "typeIDVl" });
			$("#select01").find("b").on("click",function(event){
				var isIE = !!window.ActiveXObject || "ActiveXObject" in window;
				if(!!window.ActiveXObject || "ActiveXObject" in window) {//识别ie浏览器（包括ie11）
					window.event.cancelBubble = true;
				}else{
					event.stopPropagation();
				}
				var obj = $(this);
				var groupid = $(obj).attr("vl");
				/* if($(this).parent().hasClass("cgray")){
					if($(this).parent().hasClass("false")){
						return;
					}else{
						$("#typeIDTxt").val("");
						$("#typeIDVal").val("");
						$("#typeIDVl").val("");
					}
				} */
				layer.confirm('确定删除此分组？', {
				    btn: ['提交','取消'], //按钮
				    shade: false //不显示遮罩
				}, function(index){
					$.post("${ctx}/oa/oaNotify/delOaGroup",
							{
						     "groupid":groupid
						    },function(data){
						var jsonData = jQuery.parseJSON(data);
						if(jsonData.status == 'y'){
							layer.closeAll();
							$(obj).parent().remove();
						}else{
							layer.msg(jsonData.info, {icon: 2});
						}
					});
				});
				
			});
			
			$("#subComment").click(function(){
                var index = layer.load(0);
				$.post("${ctx}/oa/oaNotify/upOaNotifyRecordComment",
						{
					     "recordid":$("#rid").val(),
					     "message":$("#message").val()
					    },function(data){
                        layer.close(index);
					var jsonData = jQuery.parseJSON(data);
					if(jsonData.status == 'y'){
						layer.msg(jsonData.info, {icon: 1});
                        window.location.reload();
					}else{
						layer.msg(jsonData.info, {icon: 2});
					}
				});
			});
			
			$("#savefiles").click(function(){
				$.post("${ctx}/oa/oaNotify/upOaNotifyfiles",
						{
					     "oanotifyid":"${oaNotify.id}",
					     "oafiles":$("#oafiles").val()
					    },function(data){
					var jsonData = jQuery.parseJSON(data);
					if(jsonData.status == 'y'){
						layer.msg(jsonData.info, {icon: 1});
						window.location.reload();;
					}else{
						layer.msg(jsonData.info, {icon: 2});
					}
				});
			});
			
			$("#oanotifyresend").click(function(){
				//top.layer.closeAll();
				//openDialog("转发"+'传阅',"/jy_oa/a/oa/oaNotify/reform?id=${oaNotify.id}","1000px", "600px","");
				
				if(navigator.userAgent.match(/(iPhone|iPod|Android|ios)/i)){//如果是移动端，就使用自适应大小弹窗
					width='auto';
					height='auto';
				}else{//如果是PC端，根据用户设置的width和height显示。
				
				}
				
				top.layer.open({
				    type: 2,  
				    area: ["1000px", "600px"],
				    title: "转发传阅",
			        maxmin: true, //开启最大化最小化按钮
				    content: "${ctx}/oa/oaNotify/reform?id=${oaNotify.id}" ,
				    btn: ['确定', '关闭'],
				    yes: function(index, layero){
				    	 var body = top.layer.getChildFrame('body', index);
				         var iframeWin = layero.find('iframe')[0]; //得到iframe页的窗口对象，执行iframe页的方法：iframeWin.method();
				         var inputForm = body.find('#inputForm');
				         var top_iframe;
				   
				         
				         if(iframeWin.contentWindow.doSubmit() ){
				        	// top.layer.close(index);//关闭对话框。
				        	  setTimeout(function(){showoanotifytab();top.layer.closeAll();}, 1000);//延时0.1秒，对应360 7.1版本bug
				         }
				       
					  },
					  cancel: function(index){ 
				       }
				}); 	
			});
			
			$("#addRecord").click(function(){
				///jy_oa/a/tag/treeselect?url=%2Fsys%2Foffice%2FtreeData%3Ftype%3D3&module=&checked=true&extId=&isAll=&selectIds=
					var ids = [], names = [], nodes = [];
					layer.open({
					    type: 2, 
					    //area: ['300px', '420px'],
                        area: ['550px', '60%'],
					    title:"新增人员",
					    ajaxData:{selectIds: $("#addoaNotifyRecordId").val()},
					    //content: "${ctx}/tag/treeselect?url="+encodeURIComponent("/sys/office/treeData?type=3")+"&module=&checked=true&extId=&isAll=&selectIds="+$("#addoaNotifyRecordId").val() ,
                        content: "${ctx}/tag/treeselectHold?url="+encodeURIComponent("/sys/office/treeData?type=3")+"&checked=true",
                        btn: ['确定', '关闭']
			    	       ,yes: function(index, layero){ //或者使用btn1
                            		/*ids = [];
									var tree = layero.find("iframe")[0].contentWindow.tree;//h.find("iframe").contents();
									nodes = tree.getCheckedNodes(true);
									for(var i=0; i<nodes.length; i++) {
										if (nodes[i].isParent){
											continue; // 如果为复选框选择，则过滤掉父节点
										}
										if (nodes[i].level == 0){
											//top.$.jBox.tip("不能选择根节点（"+nodes[i].name+"）请重新选择。");
											top.layer.msg("不能选择根节点（"+nodes[i].name+"）请重新选择。", {icon: 0});
											return false;
										}
										if (nodes[i].isParent){
											//top.$.jBox.tip("不能选择父节点（"+nodes[i].name+"）请重新选择。");
											//layer.msg('有表情地提示');
											top.layer.msg("不能选择父节点（"+nodes[i].name+"）请重新选择。", {icon: 0});
											return false;
										}
										ids.push(nodes[i].id);
										names.push(nodes[i].name);
										//break;
									}
									var idsStr = ids.join(",").replace(/u_/ig,"");*/
                                    var idsStr = layero.find("iframe")[0].contentWindow.$("#ids").val();
									//$("#addoaNotifyRecordId").val(ids.join(",").replace(/u_/ig,""));
									//$("#addoaNotifyRecordName").val(names.join(","));
									//$("#addoaNotifyRecordName").focus();
									
									$.post("${ctx}/oa/oaNotify/addOaNotifyRecord",
											{
										     "oanotifyid":"${oaNotify.id}",
										     "ids":idsStr
										    },function(data){
										var jsonData = jQuery.parseJSON(data);
										if(jsonData.status == 'y'){
											layer.close(index);
											window.location.reload();
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
			
			
			$("#savegroup").click(function(){
				if($("#oaNotifyRecordId").val() == ''){
			    	layer.msg("人员组不能为空", {icon: 2});
			    	layer.close(index);
			    }else{
			    	layer.open({
					    type: 2, 
					    area: ['300px', '400px'],
					    title:"人员分组",
					    //ajaxData:{selectIds: $("#addoaNotifyRecordId").val()},
					    content: "${ctx}/oa/oaNotify/oaGroupform?id=${oaNotify.id}" ,
					    btn: ['确定', '关闭']
			    	       ,yes: function(index,layero){ //或者使用btn1
			    	    	   var iframeWin = window[layero.find('iframe')[0]['name']];
			    	    	   iframeWin.sub(index);
			    	    	   /* console.log(data);
			    	    	   var jsonData = jQuery.parseJSON(data);
								if(jsonData.status == 'y'){
									layer.closeAll();
								}else{
									layer.msg(jsonData.info, {icon: 2});
								} */
							},
			    	cancel: function(index){ //或者使用btn2
			    	           //按钮【按钮二】的回调
			    	       }
					}); 
			    }
			});
			
			$("#oagroupsel").live("change",function(){
				if($(this).find("option:selected").val() != ''){
					var ids = $(this).find("option:selected").val();
					var names = $(this).find("option:selected").attr("vl");
					layer.confirm('确定要将分组人员添加到【传阅对象】？', {
					    btn: ['提交','取消'], //按钮
					    shade: false //不显示遮罩
					}, function(index){
						$.post("${ctx}/oa/oaNotify/addOaGroupToOanotify",
								{
							     "oanotifyid":"${oaNotify.id}",
							     "ids":ids,
							     "names":names
							    },function(data){
							var jsonData = jQuery.parseJSON(data);
							if(jsonData.status == 'y'){
								 location.reload();
							}else{
								layer.msg(jsonData.info, {icon: 2});
							}
						});
					});
					$("#oagroupsel").val("");
				}
				
			});

            $("a[name=furl]").live("click",function(){
                //$(this).parent().remove();
                //totalUrl();
				var id = $(this).attr("vl2");
                layer.confirm('您确认要删除？', {
                    btn: ['确认','关闭'] //按钮
                }, function(){
                    $.post("${ctx}/oa/oaNotify/delOaNotifyFile",{'fileid':id},function(data){
                        var jsonData = jQuery.parseJSON(data);
                        if(jsonData.status == 'y'){
                            //layer.alert(jsonData.info,{icon: 1}, function(){
                            location.reload();
                            //});
                        }else{
                            layer.alert(jsonData.info,{icon: 2});
                        }
                    });
                });

            });


            $("a[name=delOaNotifyRecord]").live("click",function(){
   				var oaNotifyRecordId = $(this).attr("vl");
                layer.confirm('您确认要删除？', {
                    btn: ['确认','关闭'] //按钮
                }, function(){
                    $.post("${ctx}/oa/oaNotify/delOaNotifyRecord",{'oaNotifyRecordId':oaNotifyRecordId},function(data){
                        var jsonData = jQuery.parseJSON(data);
                        if(jsonData.status == 'y'){
                            //layer.alert(jsonData.info,{icon: 1}, function(){
                            location.reload();
                            //});
                        }else{
                            layer.alert(jsonData.info,{icon: 2});
                        }
                    });
                });
            });

		});
		
		function showoanotifytab(){
			 var dataUrl = "${ctx}/oa/oaNotify";
	         dataIndex = "27",
	         menuName = "发送的传阅",
	         flag = true;
	         
	      // 选项卡菜单已存在
	         top.$('.J_menuTab').each(function () {
	             if ($(this).data('id') == dataUrl) {
	                 if (!$(this).hasClass('active')) {
	                     $(this).addClass('active').siblings('.J_menuTab').removeClass('active');
	                     scrollToTab(this);
	                     // 显示tab对应的内容区 
	                     top.$('.J_mainContent .J_iframe').each(function () {
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
	             top.$('.J_menuTab').removeClass('active');

	             // 添加选项卡对应的iframe
	             var str1 = '<iframe class="J_iframe" name="iframe' + dataIndex + '" width="100%" height="100%" src="' + dataUrl + '" frameborder="0" data-id="' + dataUrl + '" seamless></iframe>';
	             top.$('.J_mainContent').find('iframe.J_iframe').hide().parents('.J_mainContent').append(str1);

	             //显示loading提示
	             var loading = layer.load();

	             $('.J_mainContent iframe:visible').load(function () {
	                 //iframe加载完成后隐藏loading提示
	                 top.layer.close(loading);
	             });
	             // 添加选项卡
	             top.$('.J_menuTabs .page-tabs-content').append(str);
	             scrollToTab($('.J_menuTab.active'));
	         }
	         //top_iframe = top.getActiveTab().attr("name");//获取当前active的tab的iframe 
	         
	         //inputForm.attr("target",top_iframe);//表单提交成功后，从服务器返回的url在当前tab中展示
		}
		
		function JQSelectcallback(){
			if($("#typeIDVal").val() != ''){
				var ids = $("#typeIDVal").val();
				var names = $("#typeIDVl").val();
				layer.confirm('确定要将分组人员添加到【传阅对象】？', {
				    btn: ['提交','取消'], //按钮
				    shade: false //不显示遮罩
				}, function(index){
					$.post("${ctx}/oa/oaNotify/addOaGroupToOanotify",
							{
						     "oanotifyid":"${oaNotify.id}",
						     "ids":ids,
						     "names":names
						    },function(data){
						var jsonData = jQuery.parseJSON(data);
						if(jsonData.status == 'y'){
							 location.reload();
						}else{
							layer.msg(jsonData.info, {icon: 2});
						}
					});
				},function(){
					$("#typeIDTxt").val("选取自定人员分组");
					$("#typeIDVal").val("");
					$("#typeIDVl").val("");
				});
				
			}
		}

        function commonFileUploadCallBack(id,url,fname){
            //$("#"+id).val(url);
            /*if(url!=""&&fname!=""){
                $("#filearea").append('<li>'+fname+' &nbsp; <a href="javascript:" name="furl" vl="'+url+'"><i class="glyphicon glyphicon-remove text-danger"></i></a></li>');
            }
            totalUrl();*/
            console.log("url:",url);
            console.log("urlstr:",JSON.stringify(url));
            $.post("${ctx}/oa/oaNotify/addOaNotifyFile",{'id':"${oaNotify.id}","url":JSON.stringify(url)},function(data){
                var jsonData = jQuery.parseJSON(data);
                if(jsonData.status == 'y'){
                    //layer.alert(jsonData.info,{icon: 1}, function(){
                        location.reload();
                    //});
                }else{
                    layer.alert(jsonData.info,{icon: 2});
                }
            });
        }
        function totalUrl() {
            var urls = "";
            $("#filearea").find("a[name=furl]").each(function(i,n){
                var obj = $(this);
                if(i==0){
                    urls+=obj.attr("vl");
                }else{
                    urls+=","+obj.attr("vl");
                }
            });
            $("#oaNotifyFileUrl").val(urls);
        }

	</script>
</head>
<body class="hideScroll">
	<form:form id="inputForm" modelAttribute="oaNotify" action="${ctx}/oa/oaNotify/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>	
		<%--<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
		   <tbody>
		      <tr>
		         <td  class="width-15 active">	<label class="pull-right"><font color="red">*</font>类型：</label></td>
		         <td class="width-35" >
		           &lt;%&ndash;${fns:getDictList('oa_notify_type')[oaNotify.type-1]}&ndash;%&gt;
						   ${fns:getDictLabel(oaNotify.type, 'oa_notify_type', '')}
		            </td>
		         <td  class="width-15 active">	<label class="pull-right"><font color="red">*</font>标题：</label></td>
		         <td class="width-35" >${oaNotify.title}</td>
		      </tr>
		       <tr>
		         <td  class="width-15 active">	<label class="pull-right"><font color="red">*</font>内容：</label></td>
		         <td colspan="3" >${fns:unescapeHtml(oaNotify.content)}</td>
		         
		      </tr>
		      <tr>
		      	<td  class="width-15 active">	<label class="pull-right">附件：</label></td>
		         <td colspan="3" >
		         &lt;%&ndash;<c:if test="${oaNotify.status ne '1'}">
					<form:hidden id="oafiles" path="oafiles" htmlEscape="false" maxlength="255" class="form-control"/>
					<sys:ckfinder input="oafiles" type="files" uploadPath="/oa/notify" selectMultiple="true"/>
				</c:if>
		         <c:if test="${oaNotify.status eq '1'}">
					<form:hidden id="oafiles" path="oafiles" htmlEscape="false" maxlength="255" class="form-control"/>

					<sys:ckfinder input="oafiles" type="files" uploadPath="/oa/notify" selectMultiple="true" readonly="true" />
		         </c:if>&ndash;%&gt;
					 <button type="button" class="btn btn-primary" onclick="commonFileUpload('oaNotifyFileUrl')"><i class="glyphicon glyphicon-plus"></i>&nbsp;添加附件</button>
					 <ol style="line-height: 20px; padding:10px 0; padding-left: 40px; width:70%;" id="filearea">
					 <c:forEach items="${oaNotify.oaNotifyFileList}" var="oaNotifyFile" varStatus="s">
						 <li><a class="col-sm-6" href="javascript:" vl="${oaNotifyFile.fileurl}" onclick="commonFileDownLoad(this)">${oaNotifyFile.filename}</a>
							 <span class="col-sm-2">上传人：${(fns:getUserById(oaNotifyFile.user.id)).name}</span><span class="col-sm-3">上传时间：<fmt:formatDate value="${oaNotifyFile.uploadDate}" pattern="yyyy-MM-dd HH:mm:ss"/></span>
							 <a class="col-sm-1" href="javascript:" name="furl" vl="${oaNotifyFile.fileurl}" vl2="${oaNotifyFile.id}" style="<c:if test="${fns:getUser().id != oaNotifyFile.user.id}">display: none;</c:if>"><i class="glyphicon glyphicon-remove text-danger"></i>&nbsp;</a>
						 </li>
					 </c:forEach>
					 </ol>
					 <input id="oaNotifyFileUrl" name="oaNotifyFileUrls"  type="hidden" value="" >
		         
		         </td>
		      </tr>
		      &lt;%&ndash; <tr>
		      	<td  class="width-15 active">	<label class="pull-right">传阅操作：</label></td>
		         <td colspan="3">
		             <form:checkbox path="mobileremind" value="1" />短信提醒
		             <form:checkbox path="recordremind" checked="checked" value="1"/>确认时提醒
		             <form:checkbox path="secretsend" value="1"/>密送
		             <form:checkbox path="isallow" checked="checked" value="1"/>允许另外新增人员和转发、另存此传阅
		         </td>
		      </tr> &ndash;%&gt;
		      <c:if test="${oaNotify.status ne '1'}">
		      	 <tr>
			         <td  class="width-15 active">	<label class="pull-right"><font color="red">*</font>状态：</label></td>
			         <td class="width-35" ><form:radiobuttons path="status" items="${fns:getDictList('oa_notify_status')}" itemLabel="label" itemValue="value" htmlEscape="false" class="i-checks required"/></td>
			         <td  class="width-15 active">	<label class="pull-right"><font color="red">*</font>接受人：</label></td>
			         <td class="width-35" ><sys:treeselect id="oaNotifyRecord" name="oaNotifyRecordIds" value="${oaNotify.oaNotifyRecordIds}" labelName="oaNotifyRecordNames" labelValue="${oaNotify.oaNotifyRecordNames}"
							title="用户" url="/sys/office/treeData?type=3" cssClass="form-control required" notAllowSelectParent="true" checked="true"/></td>
		      	</tr>
			</c:if>
			
					<c:if test="${oaNotify.status eq '1'}">
					  <tr>
				         <td  class="width-15 active">	<label class="pull-right">接收人：</label></td>
				         <td class="width-35" colspan="3">
				         <span style="line-height: 30px;">&nbsp;已查阅：${oaNotify.readNum}/${oaNotify.readNum + oaNotify.unReadNum} &nbsp; 已确认：${oaNotify.commentNum}/${oaNotify.readNum + oaNotify.unReadNum} </span>
				         
				         <c:if test="${oaNotify.secretsend != '1' && oaNotify.isallow == '1'}">
				                &lt;%&ndash; <span class="pull-left" style="line-height: 30px; width:300px;">
									<label class="col-md-4">人员分组：</label>
									<div class="col-md-8">
									  <select id="oagroupsel" name="oagroupsel" class="form-control" aria-required="true">
										<option value="" selected="selected">选取自定人员分组</option>
										<c:forEach items="${groups}" var="gp">
										   <option value="${gp.ids}" vl="${gp.names}" >${gp.groupname}</option>
										</c:forEach>
									  </select>
								  </div>
								</span> &ndash;%&gt;
								<span class="pull-left" style="line-height: 30px; width:300px;">
								<label class="col-md-4">人员分组：</label>
								<div class="col-md-8">
								<div id="select01" class="form-control selectbox">
								    <i></i>
								    <div class="cartes">
								        <input type="text" value="选取自定人员分组" class="listTxt" id="typeIDTxt" onfocus="this.blur()" />
								        <input type="hidden" value="" class="listVal" id="typeIDVal" />
								        <input type="hidden" value="" class="listVl" id="typeIDVl" />
								    </div>
								    <div class="lists">
								        <ul class="list" id="typeID">
								            <li class="v3s" id="" vl="">选取自定人员分组 </li>
								            <c:forEach items="${groups}" var="gp">
								                <li class="v3s" id="${gp.ids}" vl="${gp.names}">${gp.groupname} <b class="glyphicon glyphicon-remove" vl="${gp.id}"></b></li>
								            </c:forEach>
								        </ul>
								    </div>
								</div>
								</div>
								</span>
								<span class="pull-left" style="line-height: 30px;">
									<a href="javascript:" id="savegroup" ><i class="glyphicon glyphicon-floppy-disk"></i>将传阅对象保存到人员分组中</a>
								</span>
						 </c:if>		
				         <c:if test="${oaNotify.secretsend != '1' && oaNotify.isallow == '1'}">
				         <a href="javascript:" id="oanotifyresend" class=" pull-right" style="margin-bottom:10px;"><i class="fa fa-share-square-o"></i>传阅转发</a>
				         <a href="javascript:" id="addRecord" class=" pull-right" style="margin-bottom:10px;padding-right: 10px"><i class="fa fa-plus"></i> 新添人员</a>
				         </c:if>
				         
				         <table id="contentTable" class="table table-striped table-bordered table-hover table-condensed dataTables-example dataTable" style="table-layout:fixed">
								<thead>
									<tr>
										<th width="70">接收人</th>
										<th>接收人公司</th>
										<th>接收人部门</th>
										<th>确认/标志</th>
										<th>确认时间</th>
										<th>状态</th>
										<th width="12px">操作</th>
									</tr>
								</thead>
								<tbody>
								<c:forEach items="${page.list}" var="oaNotifyRecord">
									<tr>
										<td>
											${oaNotifyRecord.userName}
										</td>
										<td>
												${oaNotifyRecord.companyName}
										</td>
										<td>
												${oaNotifyRecord.officeName}&lt;%&ndash;<c:if test="${oaNotifyRecord.stationName!=null}">-${oaNotifyRecord.stationName}</c:if>&ndash;%&gt;
										</td>
										<td>
											${oaNotifyRecord.oacomment}
										</td>
										<td>
											<fmt:formatDate value="${oaNotifyRecord.commentDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
										</td>
										<td>
											${fns:getDictLabel(oaNotifyRecord.readFlag, 'oa_notify_read', '')}
											<c:if test="${oaNotifyRecord.addname != null}">
											  {由[${oaNotifyRecord.addname}]新添加}
											</c:if>
										</td>
										<td>
											<c:if test="${oaNotifyRecord.readFlag != '2'}">
												<a href="javascript:" name="delOaNotifyRecord" vl="${oaNotifyRecord.id}"><i class="glyphicon glyphicon-remove"></i></a>
										    </c:if>
										</td>
									</tr>
								</c:forEach>
								</tbody>
							</table>
							
							<!-- 分页代码 -->
						<table:page page="${page}"></table:page>
						</td>
				      </tr>
				       
		</c:if>

		<tr>
		         <td  class="width-15 active">	<label class="pull-right">作者：</label></td>
		         <td class="width-35" >
		            ${oaNotify.createBy.name}
		            </td>
		         <td  class="width-15 active">	<label class="pull-right">创建日期：</label></td>
		         <td class="width-35" ><fmt:formatDate value="${oaNotify.createDate}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/></td>
		      </tr>
			  <c:if test="${myoaNotifyRecord != null && myoaNotifyRecord.readFlag == 2}">
				  <tr>
					  <td class="width-15 active">
						  <h4 class="pull-right"><i class="glyphicon glyphicon-hand-right"></i></h4>
					  </td>
					  <td class="width-35" colspan="3">
						  <h4><a href="javascript:" id="reSure">&nbsp;重新【确认/标志】该传阅</a></h4>
					  </td>
				  </tr>
			  </c:if>
		</tbody>
		</table>
<c:if test="${myoaNotifyRecord != null && myoaNotifyRecord.readFlag != 2}">
			<div class="col-sm-12">
				
					<div class="form-group">
						<label for="message">确认/标志（可在此输入意见、评论等相关内容）</label>
						<div class="row">
							<div class="col-sm-10">
								<textarea class="form-control" id="message" rows="5" >已读</textarea>
								<input type="hidden" id="rid" value="${myoaNotifyRecord.id}" />
							</div>
							<div class="col-sm-2">
								<button class="btn btn-lg btn-info" id="subComment" type="button">确认</button>
							</div>
						</div>
					</div>
				
				
			</div>
</c:if>
<c:if test="${myoaNotifyRecord != null && myoaNotifyRecord.readFlag == 2}">

	<div class="col-sm-12" style="display: none;" id="reSureArea">

		<div class="form-group">
			<label for="message">确认/标志（可在此输入意见、评论等相关内容）</label>
			<div class="row">
				<div class="col-sm-10">
					<textarea class="form-control" id="message" rows="5" >已读</textarea>
					<input type="hidden" id="rid" value="${myoaNotifyRecord.id}" />
				</div>
				<div class="col-sm-2">
					<div class="form-group">
						<button class="btn btn-info" id="subComment" type="button">确认</button>
					</div>
					<div class="form-group">
						<button class="btn btn-default" id="closeComment" type="button">关闭</button>
					</div>
				</div>
			</div>
		</div>


	</div>
</c:if>--%>
        <div class="col-sm-12" style="margin-top: 12px;">
            <div class="panel panel-primary">
                <!-- Default panel contents -->
                <div class="panel-heading">
                    <h3>标题：${oaNotify.title} <small class="pull-right" style="color:#fff">类型：${fns:getDictLabel(oaNotify.type, 'oa_notify_type', '')}</small></h3>
                </div>
                <div class="panel-body">
                    <div class="well" style="min-height: 200px">${fns:unescapeHtml(oaNotify.content)}</div>
                    <ul class="list-group">
                        <li class="list-group-item clearfix">
                            附件：
                            <button type="button" class="btn btn-primary" onclick="commonFileUpload('oaNotifyFileUrl','muti')"><i class="glyphicon glyphicon-plus"></i>&nbsp;添加附件</button>
                            <ol style="line-height: 20px; padding:10px 0; padding-left: 40px; width:98%;" id="filearea">
                                <c:forEach items="${oaNotify.oaNotifyFileList}" var="oaNotifyFile" varStatus="s">
                                    <li class="inline_box">
                                        <a class="inline_five" href="javascript:" vl="${oaNotifyFile.fileurl}" onclick="commonFileDownLoad(this)">${oaNotifyFile.filename}</a>
                                        <span class="inline_five text-center"><c:if test="${fns:isImg(oaNotifyFile.filename)}"><a href="javascript:" onclick="javascript:window.parent.showimage('${oaNotifyFile.fileurl}')" >预览</a></c:if></span>
                                        <span class="inline_five">上传人：${(fns:getUserById(oaNotifyFile.user.id)).name}</span><span class="inline_five">上传时间：<fmt:formatDate value="${oaNotifyFile.uploadDate}" pattern="yyyy-MM-dd HH:mm:ss"/></span>
                                        <a class="inline_five" href="javascript:" name="furl" vl="${oaNotifyFile.fileurl}" vl2="${oaNotifyFile.id}" style="<c:if test="${fns:getUser().id != oaNotifyFile.user.id}">display: none;</c:if>"><i class="glyphicon glyphicon-remove text-danger"></i>&nbsp;</a>


                                    </li>
                                </c:forEach>
                            </ol>
                            <input id="oaNotifyFileUrl" name="oaNotifyFileUrls"  type="hidden" value="" >
                        </li>
                        <li class="list-group-item clearfix">
                            <span style="line-height: 30px;">&nbsp;已查阅：${oaNotify.readNum}/${oaNotify.readNum + oaNotify.unReadNum} &nbsp; 已确认：${oaNotify.commentNum}/${oaNotify.readNum + oaNotify.unReadNum} </span>

                            <c:if test="${oaNotify.secretsend != '1' && oaNotify.isallow == '1'}">
                                <span class="pull-left" style="line-height: 30px; width:300px;">
								<label class="col-md-5">人员分组：</label>
								<div class="col-md-7">
								<div id="select01" class="form-control selectbox">
								    <i></i>
								    <div class="cartes">
								        <input type="text" value="选取自定人员分组" class="listTxt" id="typeIDTxt" onfocus="this.blur()" />
								        <input type="hidden" value="" class="listVal" id="typeIDVal" />
								        <input type="hidden" value="" class="listVl" id="typeIDVl" />
								    </div>
								    <div class="lists">
								        <ul class="list" id="typeID">
								            <li class="v3s" id="" vl="">选取自定人员分组 </li>
								            <c:forEach items="${groups}" var="gp">
                                                <li class="v3s" id="${gp.ids}" vl="${gp.names}">${gp.groupname} <b class="glyphicon glyphicon-remove" vl="${gp.id}"></b></li>
                                            </c:forEach>
								        </ul>
								    </div>
								</div>
								</div>
								</span>
                                <span class="pull-left" style="line-height: 30px;">
									<a href="javascript:" id="savegroup" ><i class="glyphicon glyphicon-floppy-disk"></i>将传阅对象保存到人员分组中</a>
								</span>
                            </c:if>
                            <c:if test="${oaNotify.secretsend != '1' && oaNotify.isallow == '1'}">
                                <a href="javascript:" id="oanotifyresend" class=" pull-right" style="margin-bottom:10px;"><i class="fa fa-share-square-o"></i>传阅转发</a>
                                <a href="javascript:" id="addRecord" class=" pull-right" style="margin-bottom:10px;padding-right: 10px"><i class="fa fa-plus"></i> 新添人员</a>
                            </c:if>
                        </li>
                        <li class="list-group-item clearfix">
                            <table id="contentTable" class="table table-bordered table-condensed dataTables-example dataTable" style="table-layout:fixed">
                                <thead>
                                <tr>
                                    <th width="20%">接收人</th>
                                    <%--<th>接收人公司</th>--%>
                                    <th>接收人部门</th>
                                    <th>确认/标志</th>
                                    <th>确认时间</th>
                                    <th>状态</th>
                                    <th width="30px">操作</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach items="${page.list}" var="oaNotifyRecord">
                                    <tr class="<c:if test="${oaNotifyRecord.readFlag=='1'}">bg-warning</c:if><c:if test="${oaNotifyRecord.readFlag=='2'}">bg-success</c:if>">
                                        <td>
                                                ${oaNotifyRecord.userName}
                                        </td>
                                        <%--<td>
                                                ${oaNotifyRecord.companyName}
                                        </td>--%>
                                        <td>
                                                ${oaNotifyRecord.officeName}<%--<c:if test="${oaNotifyRecord.stationName!=null}">-${oaNotifyRecord.stationName}</c:if>--%>
                                        </td>
                                        <td>
                                                ${oaNotifyRecord.oacomment}
                                        </td>
                                        <td>
                                            <fmt:formatDate value="${oaNotifyRecord.commentDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
                                        </td>
                                        <td>
                                                ${fns:getDictLabel(oaNotifyRecord.readFlag, 'oa_notify_read', '')}
                                            <c:if test="${oaNotifyRecord.addname != null}">
                                                {由[${oaNotifyRecord.addname}<c:if test="${oaNotifyRecord.addoffice != null}">(${oaNotifyRecord.addoffice})</c:if>]新添加}
                                            </c:if>
                                        </td>
                                        <td>
                                            <c:if test="${oaNotifyRecord.readFlag != '2'}">
                                                <c:if test="${oaNotify.createBy.id == fns:getUser().id}">
                                                <a href="javascript:" name="delOaNotifyRecord" vl="${oaNotifyRecord.id}" style="margin-left: 6px;" ><i class="glyphicon glyphicon-remove"></i></a>
                                                </c:if>
                                            </c:if>
                                        </td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>
                            <table:page page="${page}"></table:page>
                        </li>
                        <li class="list-group-item clearfix"><p class="pull-left">作者：${oaNotify.createBy.name}【${oaNotify.createBy.office.name}】</p><p class="pull-right">创建时间：<fmt:formatDate value="${oaNotify.createDate}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/></p></li>
                        <li class="list-group-item clearfix">
                            <c:if test="${myoaNotifyRecord != null && myoaNotifyRecord.readFlag == 2}">
                                <h4><a href="javascript:" id="reSure"><i class="glyphicon glyphicon-hand-right"></i>&nbsp;重新【确认/标志】该传阅</a></h4>
                            </c:if>
                        </li>
                        <li class="list-group-item clearfix">
                            <c:if test="${myoaNotifyRecord != null && myoaNotifyRecord.readFlag != 2}">
                                <div class="col-sm-12">

                                    <div class="form-group">
                                        <label for="message">确认/标志（可在此输入意见、评论等相关内容）</label>
                                        <div class="row">
                                            <div class="col-sm-10">
                                                <textarea class="form-control" id="message" rows="5" >已读</textarea>
                                                <input type="hidden" id="rid" value="${myoaNotifyRecord.id}" />
                                            </div>
                                            <div class="col-sm-2">
                                                <button class="btn btn-lg btn-info" id="subComment" type="button">确认</button>
                                            </div>
                                        </div>
                                    </div>


                                </div>
                            </c:if>
                            <c:if test="${myoaNotifyRecord != null && myoaNotifyRecord.readFlag == 2}">

                                <div class="col-sm-12" style="display: none;" id="reSureArea">

                                    <div class="form-group">
                                        <label for="message">确认/标志（可在此输入意见、评论等相关内容）</label>
                                        <div class="row">
                                            <div class="col-sm-10">
                                                <textarea class="form-control" id="message" rows="5" >已读</textarea>
                                                <input type="hidden" id="rid" value="${myoaNotifyRecord.id}" />
                                            </div>
                                            <div class="col-sm-2">
                                                <div class="form-group">
                                                    <button class="btn btn-info" id="subComment" type="button">确认</button>
                                                </div>
                                                <div class="form-group">
                                                    <button class="btn btn-default" id="closeComment" type="button">关闭</button>
                                                </div>
                                            </div>
                                        </div>
                                    </div>


                                </div>
                            </c:if>
                        </li>
                    </ul>

                </div>

                <!-- Table -->

            </div>
        </div>
	</form:form>
	
	<form:form id="searchForm" action="${ctx}/oa/oaNotify/view?id=${oaNotify.id}" method="post" class="form-inline">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
    </form:form>
    
</body>
</html>