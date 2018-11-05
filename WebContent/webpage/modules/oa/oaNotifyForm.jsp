<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>通知管理</title>
	<meta name="decorator" content="default"/>
	<script src="${ctxStatic}/jquery/jQSelect.js" type="text/javascript"></script>
	<!-- SUMMERNOTE -->
	 <link href="${ctxStatic}/summernote/summernote.css" rel="stylesheet">
	 <link href="${ctxStatic}/summernote/summernote-bs3.css" rel="stylesheet">
	 <script src="${ctxStatic}/summernote/summernote.min.js"></script>
	 <script src="${ctxStatic}/summernote/summernote-zh-CN.js"></script>
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
			
			$('#content').summernote({
                lang: 'zh-CN',
                toolbar: [["style", ["style"]], ["font", ["bold", "italic", "underline", "clear"]], ["color", ["color"]], ["para", ["ul", "ol", "paragraph"]], ["height", ["height"]], ["table", ["table"]]]
            });
			
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
			
			
			$("#savefiles").click(function(){
				$.post("${ctx}/oa/oaNotify/upOaNotifyfiles",
						{
					     "oanotifyid":"${oaNotify.id}",
					     "oafiles":$("#oafiles").val()
					    },function(data){
					var jsonData = jQuery.parseJSON(data);
					if(jsonData.status == 'y'){
						layer.msg(jsonData.info, {icon: 1});
						window.location.href="${ctx}/oa/oaNotify/view?id=${oaNotify.id}";
					}else{
						layer.msg(jsonData.info, {icon: 2});
					}
				});
			});
			$("#addRecord").click(function(){
				///jy_oa/a/tag/treeselect?url=%2Fsys%2Foffice%2FtreeData%3Ftype%3D3&module=&checked=true&extId=&isAll=&selectIds=
					var ids = [], names = [], nodes = [];
					layer.open({
					    type: 2, 
					    area: ['300px', '420px'],
					    title:"新增人员",
					    ajaxData:{selectIds: $("#addoaNotifyRecordId").val()},
					    content: "${ctx}/tag/treeselect?url="+encodeURIComponent("/sys/office/treeData?type=3")+"&module=&checked=true&extId=&isAll=&selectIds="+$("#addoaNotifyRecordId").val() ,
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
									$.post("${ctx}/oa/oaNotify/addOaNotifyRecord",
											{
										     "oanotifyid":"${oaNotify.id}",
										     "ids":idsStr
										    },function(data){
										var jsonData = jQuery.parseJSON(data);
										if(jsonData.status == 'y'){
											layer.close(index);
											window.location.href="${ctx}/oa/oaNotify/view?id=${oaNotify.id}";
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
			
			$("a[name=delrecord]").live("click",function(){
				$(this).parent().parent().remove();
				resetRecord();
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
					    content: "${ctx}/oa/oaNotify/oaGroupform?ids="+$("#oaNotifyRecordId").val()+"&names="+$("#oaNotifyRecordName").val() ,
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
						var idsarr = ids.split(',');
						var namesarr = names.split(',');
						$.each(idsarr, function(i,val){      
						      //alert(i+"   "+val+"   "+namesarr[i]);
						      if($("#oaNotifyRecordId").val() == ''){
						    	  $("#oaNotifyRecordId").val(val);
						    	  $("#oaNotifyRecordName").val(namesarr[i]);
						      }else{
						    	  if($("#oaNotifyRecordId").val().indexOf(val) !=-1 )
							      {
							          ;
							      }else{
							    	  $("#oaNotifyRecordId").val($("#oaNotifyRecordId").val()+","+val);
							    	  $("#oaNotifyRecordName").val($("#oaNotifyRecordName").val()+","+namesarr[i]);
							      }
						      }
						      
						});
						callback();
						layer.close(index);
					});
				}
				$("#oagroupsel").val("");
			});

			$("a[name=furl]").live("click",function(){
			    $(this).parent().remove();
                totalUrl();
            });
			
		});
		//新添人员后回调
		function callback(){

			if($("#oaNotifyRecordId").val() != ''){
				var idsArr = $("#oaNotifyRecordId").val().split(",");
				var namesArr = $("#oaNotifyRecordName").val().split(",");
				var htmlStr = '';
				
				$.each(idsArr, function(i,val){
				    htmlStr+='<tr name="recordtr" vl1="'+idsArr[i]+'" vl2="'+namesArr[i]+'"><td>'+namesArr[i]+'</td><td></td><td></td><td>未开封</td><td><a class="remove" name="delrecord" href="javascript:void(0)" title="删除"><i class="glyphicon glyphicon-remove"></i></a></td></tr>';
				});
				$("#recordArea").html(htmlStr);
			}
			
		}
		//点击叉叉后重置人员
		function resetRecord(){
			var ids = [], names = [];
			var trs = $("#recordArea").find("tr[name=recordtr]");
			$.each(trs, function(i,val){
			    ids.push($(this).attr("vl1"));
				names.push($(this).attr("vl2"));
			});
			$("#oaNotifyRecordId").val(ids.join(",").replace(/u_/ig,""));
			$("#oaNotifyRecordName").val(names.join(","));
		}
		
		function JQSelectcallback(){
			if($("#typeIDVal").val() != ''){
				var ids = $("#typeIDVal").val();
				var names = $("#typeIDVl").val();
				layer.confirm('确定要将分组人员添加到【传阅对象】？', {
				    btn: ['提交','取消'], //按钮
				    shade: false //不显示遮罩
				}, function(index){
					var idsarr = ids.split(',');
					var namesarr = names.split(',');
					$.each(idsarr, function(i,val){      
					      //alert(i+"   "+val+"   "+namesarr[i]);
					      if($("#oaNotifyRecordId").val() == ''){
					    	  $("#oaNotifyRecordId").val(val);
					    	  $("#oaNotifyRecordName").val(namesarr[i]);
					      }else{
					    	  if($("#oaNotifyRecordId").val().indexOf(val) !=-1 )
						      {
						          ;
						      }else{
						    	  $("#oaNotifyRecordId").val($("#oaNotifyRecordId").val()+","+val);
						    	  $("#oaNotifyRecordName").val($("#oaNotifyRecordName").val()+","+namesarr[i]);
						      }
					      }
					      
					});
					callback();
					layer.close(index);
				});
				$("#typeIDTxt").val("选取自定人员分组");
				$("#typeIDVal").val("");
				$("#typeIDVl").val("");
			}
		}

        function commonFileUploadCallBack(id,url,fname){
            if(url!=null){
                $.each(url,function (i,n) {
                    $("#filearea").append('<li class="inline_box"><a class="inline_two" href="javascript:" vl="'+n.fileUrl+'" onclick="commonFileDownLoad(this)">'+n.fileName+'</a> &nbsp; <a href="javascript:" name="furl" vl="'+n.fileUrl+'"><i class="glyphicon glyphicon-remove text-danger"></i></a></li>');
                })
			}
            totalUrl();
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
	<style>
		table tr td input[type=checkbox] { margin-top:0;}
	</style>
</head>
<body class="hideScroll">
	<form:form id="inputForm" modelAttribute="oaNotify" action="${ctx}/oa/oaNotify/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>	
		<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
		   <tbody>
		      <tr>
				  <td  class="width-15 active">	<label class="pull-right"><font color="red">*</font>标题：</label></td>
				  <td style="width: 55%"><form:input path="title" htmlEscape="false" maxlength="100" class="form-control required"/></td>
		         <td class="active" style="width: 10%">	<label class="pull-right"><font color="red">*</font>类型：</label></td>
		         <td><form:select path="type" class="form-control required">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('oa_notify_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
					</form:select></td>

		      </tr>
		       <tr>
		         <td  class="width-15 active">	<label class="pull-right"><!-- <font color="red">*</font> -->内容：</label></td>
		         <td colspan="3" ><form:textarea path="content" htmlEscape="false" rows="8" style="height:250px" maxlength="2000" class="form-control required"/></td>
		         
		      </tr>
		      <tr>
		      	<td  class="width-15 active">	<label class="pull-right">附件：</label></td>
		         <td colspan="3">

		         <%--<c:if test="${oaNotify.status ne '1'}">
					<form:hidden id="oafiles" path="oafiles" htmlEscape="false" maxlength="255" class="form-control"/>
					<sys:ckfinder input="oafiles" type="files" uploadPath="/oa/notify" selectMultiple="true"/>
				</c:if>
		         <c:if test="${oaNotify.status eq '1'}">
					<form:hidden id="oafiles" path="oafiles" htmlEscape="false" maxlength="255" class="form-control"/>
					<sys:ckfinder input="oafiles" type="files" uploadPath="/oa/notify" selectMultiple="true" readonly="true" />
		         </c:if>--%>
					 <button type="button" class="btn btn-primary" onclick="commonFileUpload('oaNotifyFileUrl','muti')"><i class="glyphicon glyphicon-plus"></i>&nbsp;添加附件</button>
		         	<ol style="line-height: 20px; padding: 10px; padding-left:40px;" id="filearea">
						<c:forEach items="${oaNotify.oaNotifyFileList}" var="oaNotifyFile" varStatus="s">
							    <li class="inline_box"><a class="inline_two" href="javascript:" vl="${oaNotifyFile.fileurl}" onclick="commonFileDownLoad(this)">${oaNotifyFile.filename}</a> &nbsp; <a href="javascript:" name="furl" vl="${oaNotifyFile.fileurl}"><i class="glyphicon glyphicon-remove text-danger"></i></a></li>
						</c:forEach>
					</ol>
					 <input id="oaNotifyFileUrl" name="oaNotifyFileUrls"  type="hidden" value="${oaNotify.oaNotifyFileUrls}" >
		         
		         </td>
		      </tr>
		      <tr>
		      	<td  class="width-15 active">	<label class="pull-right">传阅操作：</label></td>
		         <td colspan="3">
		             <c:if test="${oaNotify.status == null}">
		             <%--<form:checkbox path="mobileremind" value="1"  />短信提醒--%>
		             <form:checkbox path="recordremind" checked="checked" value="1"/>确认时提醒
		             <form:checkbox path="secretsend" value="1"/>密送
		             <form:checkbox path="isallow" checked="checked" value="1"/>允许另外新增人员和转发此传阅
		             </c:if>
		             <c:if test="${oaNotify.status eq '0'}">
		               <%--<c:if test="${oaNotify.mobileremind eq '1'}"><form:checkbox path="mobileremind" value="1" checked="checked" />短信提醒</c:if>
		               <c:if test="${oaNotify.mobileremind ne '1'}"><form:checkbox path="mobileremind" value="1"  />短信提醒</c:if>--%>
		               <c:if test="${oaNotify.recordremind eq '1'}"><form:checkbox path="recordremind" checked="checked" value="1"/>确认时提醒</c:if>
		               <c:if test="${oaNotify.recordremind ne '1'}"><form:checkbox path="recordremind" value="1"/>确认时提醒</c:if>
		               <c:if test="${oaNotify.secretsend eq '1'}"><form:checkbox path="secretsend" checked="checked" value="1"/>密送</c:if>
		               <c:if test="${oaNotify.secretsend ne '1'}"><form:checkbox path="secretsend" value="1"/>密送</c:if>
		               <c:if test="${oaNotify.isallow eq '1'}"><form:checkbox path="isallow" checked="checked" value="1"/>允许另外新增人员和转发此传阅</c:if>
		               <c:if test="${oaNotify.isallow ne '1'}"><form:checkbox path="isallow" value="1"/>允许另外新增人员和转发此传阅</c:if>
		             </c:if>
		             
		         </td>
		      </tr>
		      <c:if test="${oaNotify.status ne '1'}">
		      	 <tr>
			         <td  class="width-15 active">	<label class="pull-right"><font color="red">*</font>状态：</label></td>
			         <td class="width-35" colspan="3"><form:radiobuttons path="status" items="${fns:getDictList('oa_notify_status')}" itemLabel="label" itemValue="value" htmlEscape="false" class="i-checks required"/></td>
			         <%-- <td  class="width-15 active">	<label class="pull-right"><font color="red">*</font>接受人：</label></td>
			         <td class="width-35" ><sys:treeselect id="oaNotifyRecord" name="oaNotifyRecordIds" value="${oaNotify.oaNotifyRecordIds}" labelName="oaNotifyRecordNames" labelValue="${oaNotify.oaNotifyRecordNames}"
							title="用户" url="/sys/office/treeData?type=3" cssClass="form-control required" notAllowSelectParent="true" checked="true"/></td> --%>
		      	</tr>
		      	<tr>
				         <td  class="width-15 active">	<label class="pull-right">接受人：</label></td>
				         <td class="width-35" colspan="3">
				            <table id="contentTable" class="table table-striped table-bordered table-hover table-condensed dataTables-example dataTable">
								<%-- <span style="line-height: 30px;">&nbsp;已查阅：${oaNotify.readNum}/${oaNotify.readNum + oaNotify.unReadNum} &nbsp; 已确认：${oaNotify.commentNum}/${oaNotify.readNum + oaNotify.unReadNum} </span> --%>
								<%-- <span class="pull-left" style="line-height: 30px; width:300px;">
									<label class="col-md-4">人员分组：</label>
									<div class="col-md-8">
									  <select id="oagroupsel" name="oagroupsel" class="form-control" aria-required="true">
										<option value="" selected="selected">选取自定人员分组</option>
										<c:forEach items="${groups}" var="gp">
										   <option value="${gp.ids}" vl="${gp.names}" >${gp.groupname}</option>
										</c:forEach>
									  </select>
								  </div>
								</span> --%>
								<span class="pull-left" style="line-height: 30px; width:300px;">
								<label class="pull-left">人员分组：</label>
								<div class="pull-left">
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
								<span class="pull-right" style="padding-bottom: 5px;"><sys:treeselectcallbackHold id="oaNotifyRecord" name="oaNotifyRecordIds" value="${oaNotify.oaNotifyRecordIds}" labelName="oaNotifyRecordNames" labelValue="${oaNotify.oaNotifyRecordNames}"
							title="用户" url="/sys/office/treeData?type=3" cssClass="form-control required" isAll="true" notAllowSelectParent="true" checked="true"/></span>
								<thead>
									<tr>
										<th>接受人</th>
										<!-- <th>接受部门</th> -->
										<th>确认/标志</th>
										<th>确认时间</th>
										<th>状态</th>
										<th>&nbsp;</th>
									</tr>
								</thead>
								<tbody id="recordArea">
								<c:if test="${oaNotify.id!=null&&oaNotify.id!=''}">
									<c:forEach items="${oaNotify.oaNotifyRecordList}" var="oaNotifyRecord">
										<tr name="recordtr" vl1="${oaNotifyRecord.user.id}" vl2="${oaNotifyRecord.user.name}">
											<td>
												${oaNotifyRecord.userName}
											</td>
											<td>
												
											</td>
											<td>
												<fmt:formatDate value="${oaNotifyRecord.commentDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
											</td>
											<td>
												未开封
											</td>
											<td>
												<a class="remove" name="delrecord" href="javascript:void(0)" title="删除"><i class="glyphicon glyphicon-remove"></i></a>
											</td>
										</tr>
									</c:forEach>
								</c:if>
								</tbody>
							</table>
							</td>
				      </tr>
		      	
			</c:if>
			
					<c:if test="${oaNotify.status eq '1'}">
					  <tr>
				         <td  class="width-15 active">	<label class="pull-right">接受人：</label></td>
				         <td class="width-35" colspan="3">
				            <table id="contentTable" class="table table-striped table-bordered table-hover table-condensed dataTables-example dataTable">
								<span style="line-height: 30px;">&nbsp;已查阅：${oaNotify.readNum}/${oaNotify.readNum + oaNotify.unReadNum} &nbsp; 已确认：${oaNotify.commentNum}/${oaNotify.readNum + oaNotify.unReadNum} </span>
								<a href="javascript:" id="addRecord" class="btn btn-success pull-right" style="margin-bottom:10px;"><i class="fa fa-plus"></i> 新添人员</a>
								<thead>
									<tr>
										<th>接收人</th>
										<th>接收人部门</th>
										<th>确认/标志</th>
										<th>确认时间</th>
										<th>状态</th>
									</tr>
								</thead>
								<tbody>
								<c:forEach items="${page.list}" var="oaNotifyRecord">
									<tr>
										<td>
											${oaNotifyRecord.userName}
										</td>
										<td>
											${oaNotifyRecord.officeName}
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
									</tr>
								</c:forEach>
								</tbody>
							</table>
							<!-- 分页代码 -->
						<table:page page="${page}"></table:page>
							</td>
				      </tr>
		</c:if>
		</tbody>
		</table>

	</form:form>
	
	<form:form id="searchForm" action="${ctx}/oa/oaNotify/view?id=${oaNotify.id}" method="post" class="form-inline">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
    </form:form>
</body>
</html>