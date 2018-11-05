<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>流程申请</title>
	<style>
		.form-horizontal .controls { padding-top:7px;}
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
			
			laydate({
	            elem: '#startTime', //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
	            event: 'focus' //响应事件。如果没有传入event，则按照默认的click
	        });
			laydate({
	            elem: '#endTime', //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
	            event: 'focus' //响应事件。如果没有传入event，则按照默认的click
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
		<%-- <h5>当前步骤--[${flowapply.act.taskName}] </h5> --%>
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
		<form:hidden path="act.taskId"/>
		<form:hidden path="act.taskName"/>
		<form:hidden path="act.taskDefKey"/>
		<form:hidden path="act.procInsId"/>
		<form:hidden path="act.procDefId"/>
		<form:hidden id="flag" path="act.flag"/>
		<sys:message content="${message}"/>
		
		<div id="detailarea">
		<div class="row" >
		<c:if test="${flowapply.showcolumn==1 }">
		
			<div class="col-sm-12">
		<c:forEach items="${flowapply.templatecontentList }" var="zb">
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
          	</c:forEach>
          	</div>
          
          </c:if>
          
          
          
          <c:if test="${flowapply.showcolumn==2 }">
			<div class="col-sm-6">
		<c:forEach items="${flowapply.templatecontentList }" var="zb" varStatus="status">
          		<c:if test="${zb.columnlocate==1||zb.columnlocate==0 }">
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
		</div></c:if>
          	</c:forEach>
          	</div>
          	
          	<div class="col-sm-6">
		<c:forEach items="${flowapply.templatecontentList }" var="zb" varStatus="status">
			<c:if test="${zb.columnlocate==2 }">
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
		</div></c:if>
          	</c:forEach>
          </div>
          </c:if>
          
          <c:if test="${flowapply.showcolumn==3 }">
			<div class="col-sm-4">
		<c:forEach items="${flowapply.templatecontentList }" var="zb" varStatus="status">
          		<c:if test="${zb.columnlocate==1||zb.columnlocate==0 }">
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
			</div></div></c:if>
          	</c:forEach>
          	</div>
          	
          	<div class="col-sm-4">
		<c:forEach items="${flowapply.templatecontentList }" var="zb" varStatus="status">
			<c:if test="${zb.columnlocate==2 }">
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
		</div></div></c:if>
          	</c:forEach>
          	</div>
          	
          	<div class="col-sm-4">
		<c:forEach items="${flowapply.templatecontentList }" var="zb" varStatus="status">
			<c:if test="${zb.columnlocate==3 }">
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
		</div></div></c:if>
          	</c:forEach>
          	</div>
          </c:if>
          </div></div>
		<div class="control-group">
			<label class="control-label">审批意见：</label>
			<div class="controls">
				<form:textarea path="act.comment" class="form-control required" rows="5" maxlength="100"/>
			</div>
		</div>
	
		<div class="form-actions">
				<c:if test="${flowapply.act.taskDefKey ne 'apply_end'}">
					<input id="btnSubmit" class="btn btn-primary" type="submit" value="同 意" onclick="$('#flag').val('yes')"/>&nbsp;
					<input id="btnSubmit" class="btn btn-inverse" type="submit" value="驳 回" onclick="$('#flag').val('no')"/>&nbsp;
				</c:if>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
		<div class="form-actions clearfix">
			<div class="pull-right">
			   <a href="javascript:" id="oanotifyresend"  ><i class="glyphicon glyphicon-share-alt"></i>转发传阅</a>
			   <a href="javascript:" id="flowhandle"  ><i class="glyphicon glyphicon-eye-open"></i>流程督办</a>
			</div>
		</div>
		
		<act:flowChart procInsId="${flowapply.act.procInsId}"/>
		<act:histoicFlow procInsId="${flowapply.act.procInsId}"/>
	</form:form>
</div>
	</div>
	</div>
</body>
</html>

