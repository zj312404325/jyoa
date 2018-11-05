<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>流程申请</title>
	<style>
		.form-horizontal .controls { padding-top:7px;}
		.clr{ margin:0;line-height:20px}
		.title1 th,.bottomTr td{ background:#293846 !important; color:#fff}
		
	</style>
	<meta name="decorator" content="default"/>
	<script src="${ctxStatic}/common/contabs.js" type="text/javascript"></script>
	<script type="text/javascript">
		var validateForm;
		function doSubmit(){//回调函数，在编辑和保存动作时，供openDialog调用提交表单。
		  if(validateForm.form()){
			  $("#inputForm").submit();
			  return true;
		  }
	
		  return false;
		}

        function commonFileUploadCallBack(id,url,fname){
            //$("#"+id).val(url);
            /*if(url!=""&&fname!=""){
                $("#filearea").append('<li>'+fname+' &nbsp; <a href="javascript:" name="furl" vl="'+url+'"><i class="glyphicon glyphicon-remove text-danger"></i></a></li>');
            }
            totalUrl();*/
            console.log("url:",url);
            console.log("urlstr:",JSON.stringify(url));
            $.post("${ctx}/oa/oaNotify/addOaNotifyFile",{'id':"${flowapply.id}","url":JSON.stringify(url)},function(data){
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

		$(document).ready(function() {
			$("#name").focus();
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

            $("#addRecord").click(function(){
                var processId=$(this).attr("vl");
                var ids = [], names = [], nodes = [];
                layer.open({
                    type: 2,
                    area: ['300px', '420px'],
                    title:"添加人员",
                    ajaxData:{selectIds: ''},
                    content: "${ctx}/tag/treeselect?url="+encodeURIComponent("/sys/office/treeData?type=3")+"&module=&checked=true&extId=&isAll=" ,
                    btn: ['确定', '关闭']
                    ,yes: function(index, layero){ //或者使用btn1
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
                        var idsStr = ids.join(",").replace(/u_/ig,"");
                        //$("#addoaNotifyRecordId").val(ids.join(",").replace(/u_/ig,""));
                        //$("#addoaNotifyRecordName").val(names.join(","));
                        //$("#addoaNotifyRecordName").focus();
                        $.post("${ctx}/leipiflow/leipiFlowApply/leipiApplyAddProcessNoLast",
                            {
                                "processInstanceId":$("#processInstanceId").val(),
                                "processId":processId,
                                "ids":idsStr
                            },function(data){
                                var jsonData = jQuery.parseJSON(data);
                                if(jsonData.status == 'y'){
                                    layer.close(index);
                                    window.location.href="${ctx}/leipiflow/leipiFlowApply/myLeipiView?id=${flowapply.id}";
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
			
			$("#oanotifyresend").click(function(){
				//top.layer.closeAll();
				//openDialog("转发"+'传阅',"/jy_oa/a/oa/oaNotify/reform?id=${oaNotify.id}","1000px", "600px","");
				
				if(navigator.userAgent.match(/(iPhone|iPod|Android|ios)/i)){//如果是移动端，就使用自适应大小弹窗
					width='auto';
					height='auto';
				}else{//如果是PC端，根据用户设置的width和height显示。
				
				}
				
				var index1 = layer.load(0, {shade: false});
				$.post("${ctx}/flow/flowapply/updateFlowViewHtml",
						{
					     "flowapplyid":"${flowapply.id}",
					     "htmlstr":$("#detailarea").html()
					    },function(data){
					var jsonData = jQuery.parseJSON(data);
					if(jsonData.status == 'y'){
						layer.close(index1);
						top.layer.open({
						    type: 2,  
						    area: ["1000px", "600px"],
						    title: "转发传阅",
					        maxmin: true, //开启最大化最小化按钮
						    content: "${ctx}/oa/oaNotify/flowreform?id=${flowapply.id}",
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
						
					}else{
						layer.msg(jsonData.info, {icon: 2});
					}
				});	
			});
			
			var index1;
			$("#flowhandle").click(function(){
				index1 = layer.open({
					  type: 2,
					  title: '流程督办',
					  shadeClose: true,
					  shade: 0.8,
					  area: ['800px', '390px'],
					  content: '${ctx}/flow/flowapply/flowHandle?procInsId=${flowapply.act.procInsId}&flowapplyid=${flowapply.id}' //iframe的url
					});
			});

            $("#processView").click(function(){
                layer.open({
                    type: 1,
                    title: '流程预览',
                    shadeClose: true,
                    shade: 0.8,
                    area: ['300px', 'auto'],
                    content: $("#processList").html()
                });
            });

			laydate({
	            elem: '#startTime', //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
	            event: 'focus' //响应事件。如果没有传入event，则按照默认的click
	        });
			laydate({
	            elem: '#endTime', //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
	            event: 'focus' //响应事件。如果没有传入event，则按照默认的click
	        });


		});
        function delFile(obj) {
            var id = $(obj).attr("vl2");
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
        }
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
	             //var loading = layer.load();

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
	</script>
</head>
<body class="gray-bg">
	<div class="wrapper wrapper-content">
	<div class="ibox">
	<div class="ibox-title">
		<h5>[${flowapply.leipiFlow.flowname}] 
		  <c:choose>
			<c:when test="${flowapply.leipiRun.status == 0}">--[运行中]</c:when>
			<c:when test="${flowapply.leipiRun.status == 1}">--[已完成]</c:when>
			<c:when test="${flowapply.leipiRun.status == 2}">--[已撤回]</c:when>
			<%-- <c:otherwise>
				已完成
			</c:otherwise> --%>
	      </c:choose>
			<span class="text-danger">【流程ID：${flowapply.pid}】</span>
		</h5>
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
	<form:form id="inputForm" modelAttribute="flowapply" action="${ctx}/flow/flowapply/saveAudit" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
		
		<div id="detailarea">
		<div class="row" >
<c:if test="${flowapply.flowid == SALARY_FLOW_ID}">
	<div class="col-md-12">
		<table class="table table-bordered table-hover dataTable create_table" id="salaryTbl">
			<thead>
			<tr>
                <th>序号</th>
				<th>姓名</th>
				<th>原部门</th>
				<th>现部门</th>
				<th>原岗位</th>
				<th>现岗位</th>
				<th>原薪资</th>
				<th>现薪资</th>
				<th>执行时间</th>
				<th>调整说明</th>
				<%--<th>附件</th>--%>
			</tr>
			</thead>
			<tbody id="createTbody">
               <c:forEach items="${flowapply.templatedetailList}" var="detail" varStatus="n">
				   <tr name="createTr">
                       <td>${n.count}</td>
					   <td>${detail.var1}</td>
					   <td>${detail.var2}</td>
					   <td>${detail.var3}</td>
					   <td>${detail.var4}</td>
					   <td>${detail.var5}</td>
					   <td>${detail.var6}</td>
					   <td>${detail.var7}</td>
					   <td>${detail.var8}</td>
					   <td>${detail.var9}</td>
					   <%--<td><c:forEach items="${detail.var10 }" var="tmpfile" varStatus="st">
						   <a vl="${tmpfile}" target="_blank" onclick="fileDownLoads(this)">附件${st.index+1 }</a><br/>
					   </c:forEach></td>--%>
				   </tr>
			   </c:forEach>
			</tbody>
		</table>
	</div>
</c:if>
<c:if test="${flowapply.flowid == REWARD_FLOW_ID}">
    <div class="col-md-12">
        <table class="table table-bordered table-hover dataTable create_table" id="salaryTbl">
            <thead>
            <tr>
                <th>序号</th>
                <th>姓名</th>
                <th>奖惩</th>
                <th>部门</th>
                <th>岗位</th>
                <th>奖惩金额</th>
                <th>奖惩分数</th>
                <th>奖惩说明</th>
                <%--<th>附件</th>--%>
            </tr>
            </thead>
            <tbody id="createTbody">
            <c:forEach items="${flowapply.templatedetailList}" var="detail" varStatus="n" >
                <tr name="createTr">
                    <td>${n.count}</td>
                    <td>${detail.var1}</td>
                    <td>${detail.var2}</td>
                    <td>${detail.var3}</td>
                    <td>${detail.var4}</td>
                    <td>${detail.var5}</td>
                    <td>${detail.var6}</td>
                    <td>${detail.var7}</td>
                   <%-- <td><c:forEach items="${detail.var10 }" var="tmpfile" varStatus="st">
                        <a vl="${tmpfile}" target="_blank" onclick="fileDownLoads(this)">附件${st.index+1 }</a><br/>
                    </c:forEach></td>--%>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</c:if>
<c:if test="${flowapply.flowid != SALARY_FLOW_ID && flowapply.flowid != REWARD_FLOW_ID}">
		<c:if test="${flowapply.showcolumn==1 }">
		
			<div class="col-sm-12">
		<c:forEach items="${flowapply.templatecontentList }" var="zb">
          		<div class="control-group">
			<c:if test="${zb.columntype!=10}">
				<label class="control-label">${zb.columnname }：</label>
				<div class="controls">
					<c:choose>
					  <c:when test="${zb.columntype==4}">
						<c:forEach items="${zb.tmplatefile }" var="tmpfile" varStatus="st">
							${st.index+1 }.<a vl="${tmpfile.url}" target="_blank" onclick="fileDownLoads(this)">${tmpfile.filename}</a><br/>
						</c:forEach>
					  </c:when>
						<c:when test="${zb.columntype==10}">
							${zb.columnname }
						</c:when>
						<c:otherwise>
						${zb.columnvalue }
					  </c:otherwise>
					</c:choose>
				</div>
			</c:if>
			<c:if test="${zb.columntype==10}">
				<label class="control-label">${zb.columnname }</label>
			</c:if>
		</div>
          	</c:forEach>
          	</div>
          
          </c:if>
          
          
          
          <c:if test="${flowapply.showcolumn==2 }">
			<div class="col-sm-6">
		<c:forEach items="${flowapply.templatecontentList }" var="zb" varStatus="status">
          		<c:if test="${zb.columnlocate==1||zb.columnlocate==0 }">
			      <c:if test="${zb.columntype!=10}">
							<div class="control-group">
						<label class="control-label">${zb.columnname }：</label>
						<div class="controls">
							<c:choose>
							  <c:when test="${zb.columntype==4}">
								<c:forEach items="${zb.tmplatefile }" var="tmpfile" varStatus="st">
									${st.index+1 }.<a vl="${tmpfile.url}" target="_blank" onclick="fileDownLoads(this)">${tmpfile.filename}</a><br/>
								</c:forEach>
							  </c:when>
							  <c:otherwise>
								${zb.columnvalue }
							  </c:otherwise>
							</c:choose>
						</div>
					</div>
				  </c:if>
					<c:if test="${zb.columntype==10}">
						<div class="control-group"><label class="control-label">${zb.columnname }</label></div>
					</c:if>
				</c:if>
          	</c:forEach>
          	</div>
          	
          	<div class="col-sm-6">
		<c:forEach items="${flowapply.templatecontentList }" var="zb" varStatus="status">
			<c:if test="${zb.columnlocate==2 }">
			<c:if test="${zb.columntype!=10}">
          		<div class="control-group">
			<label class="control-label">${zb.columnname }：</label>
			<div class="controls">
				<c:choose> 
				  <c:when test="${zb.columntype==4}">   
				    <c:forEach items="${zb.tmplatefile }" var="tmpfile" varStatus="st">
				    	${st.index+1 }.<a vl="${tmpfile.url}" target="_blank" onclick="fileDownLoads(this)">${tmpfile.filename}</a><br/>
				    </c:forEach> 
				  </c:when> 
				  <c:otherwise>   
				    ${zb.columnvalue } 
				  </c:otherwise> 
				</c:choose>
			</div>
		</div>
			</c:if>
				<c:if test="${zb.columntype==10}">
					<div class="control-group"><label class="control-label">${zb.columnname }</label></div>
				</c:if>
			</c:if>
          	</c:forEach>
          </div>
          </c:if>
          
          <c:if test="${flowapply.showcolumn==3 }">
			<div class="col-sm-4">
		<c:forEach items="${flowapply.templatecontentList }" var="zb" varStatus="status">
          		<c:if test="${zb.columnlocate==1||zb.columnlocate==0 }">
			<c:if test="${zb.columntype!=10}">
          		<div class="control-group">
				<label class="control-label">${zb.columnname }：</label>
				<div class="controls">
					<c:choose> 
					  <c:when test="${zb.columntype==4}">   
					    <c:forEach items="${zb.tmplatefile }" var="tmpfile" varStatus="st">
					    	${st.index+1 }.<a vl="${tmpfile.url}" target="_blank" onclick="fileDownLoads(this)">${tmpfile.filename}</a><br/>
					    </c:forEach> 
					  </c:when> 
					  <c:otherwise>   
					    ${zb.columnvalue } 
					  </c:otherwise> 
					</c:choose>
			</div></div>
				</c:if>
				<c:if test="${zb.columntype==10}">
					<div class="control-group"><label class="control-label">${zb.columnname }</label></div>
				</c:if>
			</c:if>
          	</c:forEach>
          	</div>
          	
          	<div class="col-sm-4">
		<c:forEach items="${flowapply.templatecontentList }" var="zb" varStatus="status">
			<c:if test="${zb.columnlocate==2 }">
			<c:if test="${zb.columntype!=10}">
          		<div class="control-group">
			<label class="control-label">${zb.columnname }：</label>
			<div class="controls">
				<c:choose> 
				  <c:when test="${zb.columntype==4}">   
				    <c:forEach items="${zb.tmplatefile }" var="tmpfile" varStatus="st">
				    	${st.index+1 }.<a vl="${tmpfile.url}" target="_blank" onclick="fileDownLoads(this)">${tmpfile.filename}</a><br/>
				    </c:forEach> 
				  </c:when> 
				  <c:otherwise>   
				    ${zb.columnvalue } 
				  </c:otherwise> 
				</c:choose>
		</div></div>
			</c:if>
				<c:if test="${zb.columntype==10}">
					<div class="control-group"><label class="control-label">${zb.columnname }</label></div>
				</c:if>
			</c:if>
          	</c:forEach>
          	</div>
          	
          	<div class="col-sm-4">
		<c:forEach items="${flowapply.templatecontentList }" var="zb" varStatus="status">
			<c:if test="${zb.columnlocate==3 }">
			<c:if test="${zb.columntype!=10}">
          		<div class="control-group">
			<label class="control-label">${zb.columnname }：</label>
			<div class="controls">
				<c:choose> 
				  <c:when test="${zb.columntype==4}">
				    <c:forEach items="${zb.tmplatefile }" var="tmpfile" varStatus="st">
				    	${st.index+1 }.<a vl="${tmpfile.url}" target="_blank" onclick="fileDownLoads(this)">${tmpfile.filename}</a><br/>
				    </c:forEach> 
				  </c:when> 
				  <c:otherwise>   
				    ${zb.columnvalue } 
				  </c:otherwise> 
				</c:choose>
		</div></div>
			</c:if>
				<c:if test="${zb.columntype==10}">
					<div class="control-group"><label class="control-label">${zb.columnname }</label></div>
				</c:if>
			</c:if>
          	</c:forEach>
          	</div>
          </c:if>


</c:if>
          </div>

		</div>
		<%-- <div class="control-group">
			<label class="control-label">审批意见：</label>
			<div class="controls">
				<form:textarea path="act.comment" class="form-control required" rows="5" maxlength="20"/>
			</div>
		</div>
	
		<div class="form-actions">
				<c:if test="${flowapply.act.taskDefKey ne 'apply_end'}">
					<input id="btnSubmit" class="btn btn-primary" type="submit" value="同 意" onclick="$('#flag').val('yes')"/>&nbsp;
					<input id="btnSubmit" class="btn btn-inverse" type="submit" value="驳 回" onclick="$('#flag').val('no')"/>&nbsp;
				</c:if>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div> --%>
		
		<%-- <div class="form-actions clearfix">
			<div class="pull-right">
			   <a href="javascript:" id="oanotifyresend"  ><i class="glyphicon glyphicon-share-alt"></i>转发传阅</a>
			   <a href="javascript:" id="flowhandle"  ><i class="glyphicon glyphicon-eye-open"></i>流程督办</a>
			   <c:if test="${canUndo=='1'}"><a href="${ctx}/act/process/deleteProcInsForApply?procInsId=${flowapply.act.procInsId}" ><i class="glyphicon glyphicon-trash"></i>撤销流程</a></c:if>
			</div>
		</div> --%>
		<%-- <act:flowChart procInsId="${flowapply.processInstanceId}"/> --%>
		<%-- <act:histoicFlow procInsId="${flowapply.act.procInsId}"/> --%>
        <div class="clearfix">
		<c:if test="${flowapply.leipiRun.status == 0 && flowapply.createBy.id==fns:getUser().id}">
			<a class="btn btn-info" href="${ctx}/leipiflow/leipiFlowApply/unDoFlowApply?id=${flowapply.id}" onclick="return confirmx('确认要撤销吗？', this.href)"><i class="glyphicon glyphicon-arrow-left"></i>&nbsp;撤销</a>
			<%--<a class="btn btn-info" href="${ctx}/leipiflow/leipiFlowApply/delete?id=${flowapply.id}" onclick="return confirmx('确认要删除吗？', this.href)"><i class="glyphicon glyphicon-trash"></i>&nbsp;删除</a>--%>
		</c:if>
		<%--<input id="btnCancel" class="btn btn-success pull-right" type="button" value="返 回" onclick="history.go(-1)"/>--%>
        <c:if test="${flowapply.leipiRun.status == 0}">
		<a id="processView" class="btn btn-info pull-right" style="margin-bottom:10px; margin-right:10px;" href="javascript:"><i class="fa fa-eye"></i> 流程预览</a>
        </c:if>

		<input type="hidden" id="processInstanceId" name="processInstanceId" value="${flowapply.processInstanceId }" />
        </div>



		<div class="row" style="margin-top:12px;">
			<ul class="list-group">
				<li class="list-group-item clearfix">
					<b>附件：</b>
		<c:if test="${flowapply.leipiRun.status == 0}"><button type="button" class="btn btn-primary" onclick="commonFileUpload('oaNotifyFileUrl','muti')"><i class="glyphicon glyphicon-plus"></i>&nbsp;添加附件</button></c:if>
					<ol style="line-height: 20px; padding:10px 0; padding-left: 40px; width:98%;" id="filearea">
						<c:forEach items="${flowapply.oaNotifyFileList}" var="oaNotifyFile" varStatus="s">
							<li class="inline_box">
								<a class="inline_five" href="javascript:" vl="${oaNotifyFile.fileurl}" onclick="commonFileDownLoad(this)">${oaNotifyFile.filename}</a>
								<span class="inline_five text-center"><c:if test="${fns:isImg(oaNotifyFile.filename)}"><a href="javascript:" onclick="javascript:window.parent.showimage('${oaNotifyFile.fileurl}')" >预览</a></c:if></span>
								<span class="inline_five">上传人：${(fns:getUserById(oaNotifyFile.user.id)).name}</span><span class="inline_five">上传时间：<fmt:formatDate value="${oaNotifyFile.uploadDate}" pattern="yyyy-MM-dd HH:mm:ss"/></span>
								<c:if test="${oaNotifyFile.canEdit=='1'}">
									<a class="inline_five" href="javascript:" name="furl" onclick="delFile(this)" vl="${oaNotifyFile.fileurl}" vl2="${oaNotifyFile.id}" style="<c:if test="${fns:getUser().id != oaNotifyFile.user.id}">display: none;</c:if>"><i class="glyphicon glyphicon-remove text-danger"></i>&nbsp;</a>
								</c:if>
							</li>
						</c:forEach>
					</ol>
					<input id="oaNotifyFileUrl" name="oaNotifyFileUrls"  type="hidden" value="" >
				</li>
			</ul>
		</div>


		<table id="contentTable" class="table table-bordered table-condensed dataTables-example dataTable">
		<thead>
			<tr class="title1">
				<!-- <th>标题</th> -->
				<th>步骤名称</th>
				<th>处理人</th>
				<%--<th>审阅</th>--%>
				<th width="38%">备注</th>
				<th>提交时间</th>
				<th>完毕时间</th>
				<%--<th width="30">操作</th>--%>
			</tr>
		</thead>
		<tbody>
		        <tr>
					<td>开始</td>
					<td><p class="clr">${flowapply.createBy.name}</p></td>
					<%--<td></td>--%>
					<td></td>
					<td><fmt:formatDate value="${flowapply.createDate }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
					<td>
						
					</td>
					<%--<td>

					</td>--%>
				</tr>
				<%--<c:forEach items="${leipiRunProcessList}" var="p" >
					<tr>
						<td>${p.leipiFlowProcess.processName}</td>
						<td><p class="clr">${p.runUser.name}<c:if test="${p.agentUser!=null }">（由${p.agentUser.name}指定代理）</c:if>
							<c:if test="${p.status==0 && p.isOpen==0}">（未开封）</c:if><c:if test="${p.status==0 && p.isOpen==1}">（已开封）</c:if>
						    </p></td>
						<td>
							  <c:if test="${p.status==1 }">同意</c:if><c:if test="${p.status==2 }">不同意</c:if>
						</td>
						<td>${p.remark }</td>
						<td><fmt:formatDate value="${p.jsTime }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
						<td>
							<fmt:formatDate value="${p.blTime }" pattern="yyyy-MM-dd HH:mm:ss"/>
						</td>
					</tr>
				</c:forEach>--%>
				<c:forEach items="${leipiRunProcessMap}" var="pp" varStatus="ss" >

					<c:forEach items="${pp.processList}" var="p" varStatus="s" >
						<c:if test="${fn:length(pp.processList)>1}">
						<tr>
							<c:if test="${s.count == 1}">
								<td rowspan="${fn:length(pp.processList)}">${pp.processName}</td>
							</c:if>
							<td><p class="clr">${p.runUser.name}<c:if test="${p.agentUser!=null }">（由${p.agentUser.name}指定代理）</c:if>
								<c:if test="${p.addprocessid!=null }">（由${fns:getUserById(p.addprocessid).name}[${fns:getUserById(p.addprocessid).officeName}]指定添加）</c:if>
								<c:if test="${p.status==0 && p.isOpen==0}">（未开封）</c:if><c:if test="${p.status==0 && p.isOpen==1}">（已开封）</c:if>
							</p></td>
							<%--<td>
								<c:if test="${p.status==1 }">同意</c:if><c:if test="${p.status==2 }">不同意</c:if>
							</td>--%>
							<td>${p.remark }</td>
							<td><fmt:formatDate value="${p.jsTime }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
							<td>
								<fmt:formatDate value="${p.blTime }" pattern="yyyy-MM-dd HH:mm:ss"/>
							</td>
							<%--<c:if test="${s.count == 1}">
								<c:set var="exitId1" value="0"></c:set>
								<c:set var="processIdValue" value=""></c:set>
								<c:forEach items="${pp.processList}" var="sp" varStatus="vs">
									<c:if test="${fns:getUser().id==sp.upid}">
										<c:set var="exitId1" value="1"></c:set>
									</c:if>
									<c:if test="${vs.index+1==fn:length(pp.processList)}">
										<c:set var="processIdValue" value="${sp.id}"></c:set>
									</c:if>
								</c:forEach>
								<td rowspan="${fn:length(pp.processList)}" align="center">
									<!--此步骤是否已完成-->
									<c:if test="${fn:length(leipiRunProcessMap)!=ss.count}">
										<c:if test="${exitId1=='1'}">
											<a href="javascript:" id="addRecord" vl="${processIdValue}"><i class="fa fa-plus"></i></a>
										</c:if>
									</c:if>
								</td>
							</c:if>--%>
						</tr>
						</c:if>
					<c:if test="${fn:length(pp.processList)==1}">
						<tr>
							<td>${pp.processName}</td>
							<td><p class="clr">${p.runUser.name}<c:if test="${p.agentUser!=null }">（由${p.agentUser.name}指定代理）</c:if>
								<c:if test="${p.addprocessid!=null }">（由${fns:getUserById(p.addprocessid).name}[${fns:getUserById(p.addprocessid).officeName}]指定添加）</c:if>
								<c:if test="${p.status==0 && p.isOpen==0}">（未开封）</c:if><c:if test="${p.status==0 && p.isOpen==1}">（已开封）</c:if>
							</p></td>
							<%--<td>
								<c:if test="${p.status==1 }">同意</c:if><c:if test="${p.status==2 }">不同意</c:if>
							</td>--%>
							<td>${p.remark }</td>
							<td><fmt:formatDate value="${p.jsTime }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
							<td>
								<fmt:formatDate value="${p.blTime }" pattern="yyyy-MM-dd HH:mm:ss"/>
							</td>
							<%--<td align="center">
								<c:if test="${fn:length(leipiRunProcessMap)!=ss.count}">
									<c:if test="${fns:getUser().id==p.upid}">
										<a href="javascript:" id="addRecord" vl="${p.id}"><i class="fa fa-plus"></i></a>
									</c:if>
								</c:if>
							</td>--%>
						</tr>
					</c:if>

					</c:forEach>


				</c:forEach>
									<!-- <tr>
						<td>请假流程（IT技术服务部）</td>
						<td><p class="clr">2313</p><p class="clr">2313</p><p class="clr">2313</p><p class="clr">2313</p><p class="clr">2313</p><p class="clr">2313</p></td>
						<td>陈钱江</td>
						<td>2017-02-16 17:02:03</td>
						<td>运行中</td>
						<td>
							
						</td>
					</tr> -->
				
				<!-- <tr>
					<td>请假流程（IT技术服务部）</td>
					<td><p class="clr">2313</p></td>
					<td>陈钱江</td>
					<td>2017-02-16 17:02:03</td>
					<td>运行中</td>
					<td>
						
					</td>
				</tr> -->
				<c:if test="${flowapply.leipiRun.status != 0}">
				<tr>
					<td>结束</td>
					<td><p class="clr"></p></td>
					<%--<td></td>--%>
					<td></td>
					<td></td>
					<td>
						
					</td>
					<%--<td>

					</td>--%>
				</tr>
				</c:if>
		</tbody>
		<tr class="bottomTr">
			<td>创建人：${flowapply.createBy.name}</td>
			<td colspan="4">创建时间：<fmt:formatDate value="${flowapply.createDate }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
		</tr>
		
	</table>
		<%--<div class="form-actions clearfix">
			<input id="btnCancel" class="btn btn-success pull-right" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>--%>
	</form:form>

</div>
	</div>

	</div>

	<div class="row" style="display: none" id="processList">
		<p>&nbsp;</p>
		<p class="text-center text-success"><strong>开始</strong></p>
		<c:forEach items="${leipiFlowProcessNameList}" var="pn" >
			<p class="text-center"><i class="glyphicon glyphicon-arrow-down" style="font-size: 20px"></i></p>
			<p class="text-center text-success"><strong>${pn.processName}</strong></p>
		</c:forEach>
		<p class="text-center"><i class="glyphicon glyphicon-arrow-down" style="font-size: 20px"></i></p>
		<p class="text-center text-success"><strong>结束</strong></p>
		<p>&nbsp;</p>
	</div>
	
</body>
</html>

