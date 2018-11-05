<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>档案管理管理</title>
	<meta name="decorator" content="default"/>
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
			$("a[name=showfile]").click(function(){
				$('#myTabs a:first').tab('show')
				$("#iframepage").attr("src","/webpage/modules/ehr/viewPDF/web/viewer.html?file="+$(this).attr("vl"));
			});
			
			$("#destory").on("ifChecked",function(){
				$(this).val("1");
			});
			$("#destory").on("ifUnchecked",function(){
				$(this).val("0");
			});
			
			$("#fileVersion").on("click",function(){
				if($(this).attr("data-value") == "1"){
					$(this).find("i").removeClass("glyphicon-triangle-bottom").addClass("glyphicon-triangle-right");
					$(this).attr("data-value","0");
					$("#fileList").hide();
				}else{
					$(this).find("i").removeClass("glyphicon-triangle-right").addClass("glyphicon-triangle-bottom");
					$(this).attr("data-value","1");
					$("#fileList").show();
				}
			});
			
			
			
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
			
			laydate({
	            elem: '#effectivedate', //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
	            event: 'focus' //响应事件。如果没有传入event，则按照默认的click
	        });
			laydate({
	            elem: '#expirydate', //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
	            event: 'focus' //响应事件。如果没有传入event，则按照默认的click
	        });
			
			//档案保存
			$("#subFileManagement").click(function(){
				//表单验证
				if(validateForm.form()){
					//提交数据
					$.post("${ctx}/filemanagement/fileManagement/saveAjax",$("#inputForm").serialize(),function(data){
						var jsonData = jQuery.parseJSON(data);
						if(jsonData.status == 'y'){
							var linfo=layer.alert(jsonData.info,{icon: 1}, function(){
							    //location.reload();
								layer.close(linfo);
							});
						}else{
							layer.alert(jsonData.info,{icon: 2});
						}
					});
				}
			});
			
			//评注保存
			$("#subComment").click(function(){
				var annotateContent=$("#annotateContent").val();
				if(annotateContent==null||annotateContent==''){
					layer.alert("请填写评注内容！",{icon: 2});
					return;
				}
				
				//提交数据
				$.post("${ctx}/filemanagement/fileManagement/saveAnnotate",{"id":"${fileManagement.id}","content":annotateContent},function(data){
					var jsonData = jQuery.parseJSON(data);
					if(jsonData.status == 'y'){
						var linfo=layer.alert(jsonData.info,{icon: 1}, function(){
							layer.close(linfo);
						    var htmlstr='<div class="social-comment"><a href="javascript:;" class="pull-left"><img alt="image" src="/images/nohead.jpg" width="25" height="25">';
						    htmlstr+='</a><div class="media-body"><a href="javascript:;">${fns:getUser().name}</a> '+annotateContent;
						    htmlstr+='<br/><small class="text-muted">'+jsonData.dt+'</small></div></div>';
						    $("#divAnnotate").before(htmlstr);
						    $("#annotateContent").val("");
						});
					}else{
						layer.alert(jsonData.info,{icon: 2});
					}
				});
			});
		});
		function addRow(list, idx, tpl, row){
			$(list).append(Mustache.render(tpl, {
				idx: idx, delBtn: true, row: row
			}));
			$(list+idx).find("select").each(function(){
				$(this).val($(this).attr("data-value"));
			});
			$(list+idx).find("input[type='checkbox'], input[type='radio']").each(function(){
				var ss = $(this).attr("data-value").split(',');
				for (var i=0; i<ss.length; i++){
					if($(this).val() == ss[i]){
						$(this).attr("checked","checked");
					}
				}
			});
		}
		function delRow(obj, prefix){
			var id = $(prefix+"_id");
			var delFlag = $(prefix+"_delFlag");
			if (id.val() == ""){
				$(obj).parent().parent().remove();
			}else if(delFlag.val() == "0"){
				delFlag.val("1");
				$(obj).html("&divide;").attr("title", "撤销删除");
				$(obj).parent().parent().addClass("error");
			}else if(delFlag.val() == "1"){
				delFlag.val("0");
				$(obj).html("&times;").attr("title", "删除");
				$(obj).parent().parent().removeClass("error");
			}
		}
	</script>
</head>
<body class="hideScroll">
	<form:form id="inputForm" modelAttribute="fileManagement" action="${ctx}/filemanagement/fileManagement/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>	
		<div class="wrapper wrapper-content container-fluid">
			<div class="row">
				<div class="col-xs-9">
					<div class="tabs-container">
			        	<ul class="nav nav-tabs" id="myTabs">
			                <li class="active"><a data-toggle="tab" href="#tab-1" aria-expanded="true">查看</a>
			                </li>
			                <li class=""><a data-toggle="tab" href="#tab-2" aria-expanded="false">档案信息</a>
			                </li>
			                <li class=""><a data-toggle="tab" href="#tab-3" aria-expanded="false">评注</a>
			                </li>
			            </ul>
			        	<div class="tab-content">
			            	<div id="tab-1" class="tab-pane active">
			                    <div class="panel-body">
			                    	<h3>${fileManagement.title }</h3>
			                    	<small><a href="javascript:;">${fileManagement.createusername }</a>上传</small>
			                    	<div>
			                    	    <c:if test="${!filesuccess }">预览文件转换失败！</c:if>
			                    		<c:if test="${!fileexist&&filesuccess }">预览文件生成中，请稍后再试...</c:if>
			                    		<c:if test="${fileexist&&filesuccess }"><iframe class="J_iframe" id="iframepage" width="100%" height="850" name="iframe0"  src="${fileurl}" frameborder="0" data-id="${ctx}/home" seamless></iframe></c:if>
			                    	</div>
			                    </div>
			                </div>
			                <div id="tab-2" class="tab-pane">
			                    <div class="panel-body">
			                    	<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
									   <tbody>
											<tr>
												<td class="width-15 active"><label class="pull-right">档号:</label></td>
											    <td class="width-35"><form:input path="fileno" htmlEscape="false" maxlength="30"    class="form-control "/></td>
											</tr>
											<tr>
												<td class="width-15 active"><label class="pull-right">分类号:</label></td>
											    <td class="width-35"><form:input path="categoryno" htmlEscape="false" maxlength="30"    class="form-control "/></td>
											</tr>
											<tr>
												<td class="width-15 active"><label class="pull-right">案卷号:</label></td>
											    <td class="width-35"><form:input path="rollno" htmlEscape="false" maxlength="30"    class="form-control "/></td>
											</tr>
											<tr>
												<td class="width-15 active"><label class="pull-right">全宗号:</label></td>
											    <td class="width-35"><form:input path="fondno" htmlEscape="false" maxlength="30"    class="form-control "/></td>
											</tr>
											<tr>
												<td class="width-15 active"><label class="pull-right">件号:</label></td>
											    <td class="width-35"><form:input path="pieceno" htmlEscape="false" maxlength="30"    class="form-control "/></td>
											</tr>
											<tr>
												<td class="width-15 active"><label class="pull-right">文号:</label></td>
											    <td class="width-35"><form:input path="wirteno" htmlEscape="false" maxlength="30"    class="form-control "/></td>
											</tr>
											<tr>
												<td class="width-15 active"><label class="pull-right">编制机构:</label></td>
											    <td class="width-35">
											    	<div class="radio">
														<label>
													    	<input type="radio" class="i-checks" name="organization" <c:if test="${fileManagement.organization=='0'||fileManagement.organization=='' }" >checked</c:if>  value="0">
													    	开发部
													  	</label>
													</div>
													<div class="radio">
													  	<label>
													    	<input type="radio" class="i-checks" name="organization" <c:if test="${fileManagement.organization=='1' }" >checked</c:if> value="1">
													   		市场部
													  	</label>
													</div>
											    </td>
											</tr>
											<tr>
												<td class="width-15 active"><label class="pull-right">档案时间:</label></td>
											    <td class="width-35">年：<input id="fileyear" name="fileyear" type="text" maxlength="20" onclick="WdatePicker({skin:'whyGreen',dateFmt:'yyyy'})" class="laydate-icon form-control layer-date required" value="${fileManagement.fileyear}" style="width: 120px;"/>
													月：<input id="filemonth" name="filemonth" type="text" maxlength="20" onclick="WdatePicker({skin:'whyGreen',dateFmt:'MM'})" class="laydate-icon form-control layer-date required" value="${fileManagement.filemonth}" style="width: 120px;"/>

												</td>
											</tr>
											<tr>
												<td class="width-15 active"><label class="pull-right">密级:</label></td>
											    <td class="width-35">
											    	<div class="radio">
														<label>
													    	<input type="radio" class="i-checks" name="secret" <c:if test="${fileManagement.secret=='0'||fileManagement.secret=='' }" >checked</c:if> value="0">
													    	无密级
													  	</label>
													</div>
													<div class="radio">
													  	<label>
													    	<input type="radio" class="i-checks" name="secret" <c:if test="${fileManagement.secret=='1' }" >checked</c:if> value="1">
													   		秘密
													  	</label>
													</div>
													<div class="radio">
													  	<label>
													    	<input type="radio" class="i-checks" name="secret" <c:if test="${fileManagement.secret=='2' }" >checked</c:if> value="2">
													   		内部
													  	</label>
													</div>
													<div class="radio">
													  	<label>
													    	<input type="radio" class="i-checks" name="secret" <c:if test="${fileManagement.secret=='3' }" >checked</c:if> value="3">
													   		公开
													  	</label>
													</div>
											    </td>
											</tr>
											<tr>
												<td class="width-15 active"><label class="pull-right">存放位置:</label></td>
											    <td class="width-35"><form:input path="position" htmlEscape="false" maxlength="500"    class="form-control "/></td>
											</tr>
											<tr>
												<td class="width-15 active"><label class="pull-right">份数:</label></td>
											    <td class="width-35"><form:input path="num" htmlEscape="false"    class="form-control  digits required"/></td>
											</tr>
											<tr>
												<td class="width-15 active"><label class="pull-right">页数:</label></td>
											    <td class="width-35"><form:input path="pages" htmlEscape="false"    class="form-control  digits required"/></td>
											</tr>
											<tr>
												<td class="width-15 active"><label class="pull-right">保存期限:</label></td>
											    <td class="width-35">
											    	<div class="radio">
														<label>
													    	<input type="radio" class="i-checks" name="savetime" <c:if test="${fileManagement.savetime=='0'||fileManagement.savetime=='' }" >checked</c:if> value="0">
													    	永久
													  	</label>
													</div>
													<div class="radio">
													  	<label>
													    	<input type="radio" class="i-checks" name="savetime" <c:if test="${fileManagement.savetime=='1' }" >checked</c:if> value="1">
													   		长期
													  	</label>
													</div>
													<div class="radio">
													  	<label>
													    	<input type="radio" class="i-checks" name="savetime" <c:if test="${fileManagement.savetime=='2' }" >checked</c:if> value="2">
													   		短期
													  	</label>
													</div>
											    </td>
											</tr>
											<tr>
												<td class="width-15 active"><label class="pull-right">备注:</label></td>
											    <td class="width-35"><form:textarea path="memo" htmlEscape="false" maxlength="500" class="form-control " rows="3"/></td>
											</tr>
											<tr>
												<td class="width-15 active"><label class="pull-right">是否已销毁:</label></td>
											    <td class="width-35">
											    	<div class="checkbox">
													  	<label>
													    	<input type="checkbox" id="destory" name="destory" class="i-checks" value="0" <c:if test="${fileManagement.destory=='1' }" >checked</c:if> >
													    	是
													  	</label>
													</div>
											    </td>
											</tr>
											<tr>
												<td class="width-15 active"><label class="pull-right">生效时间:</label></td>
											    <td class="width-35"><input id="effectivedate" name="effectivedate" type="text" maxlength="20" class="laydate-icon form-control layer-date required"
							value="<fmt:formatDate value="${fileManagement.effectivedate}" pattern="yyyy-MM-dd"/>"/></td>
											</tr>
											<tr>
												<td class="width-15 active"><label class="pull-right">失效时间:</label></td>
											    <td class="width-35"><input id="expirydate" name="expirydate" type="text" maxlength="20" class="laydate-icon form-control layer-date required"
							value="<fmt:formatDate value="${fileManagement.expirydate}" pattern="yyyy-MM-dd"/>"/></td>
											</tr>
											<tr>
												<td class="width-15 active"><label class="pull-right">&nbsp;</label></td>
											    <td class="width-35"><input type="button" class="btn btn-primary" id="subFileManagement" value="保存" /> </td>
											</tr>
									 	</tbody>
									</table>
									
			                    </div>
			                </div>
			                <div id="tab-3" class="tab-pane">
			                    <div class="panel-body">
			                    	<div class="social-footer">
			                    		<c:forEach items="${fileManagement.annotateList }" var="zb">
						            		<div class="social-comment">
					                            <a href="javascript:;" class="pull-left">
					                                <img alt="image" src="/images/nohead.jpg" width="25" height="25">
					                            </a>
					                            <div class="media-body">
					                                <a href="javascript:;">${zb.createusername }</a> ${zb.content }
					                                <br/>
					                                <small class="text-muted"><fmt:formatDate value="${zb.createDate}" pattern="yyyy年MM月dd日"/></small>
					                            </div>
					                        </div>
						            	</c:forEach>
			                    	
				                        <!-- <div class="social-comment">
				                            <a href="" class="pull-left">
				                                <img alt="image" src="img/a1.jpg">
				                            </a>
				                            <div class="media-body">
				                                <a href="#">郭建</a> jQuery的作者在Twitter上发了一条这样的推，也是有点平衡了
				                                <br/>
				                                <small class="text-muted">2017年8月18日</small>
				                            </div>
				                        </div>
				
				                        <div class="social-comment">
				                            <a href="" class="pull-left">
				                                <img alt="image" src="img/a2.jpg">
				                            </a>
				                            <div class="media-body">
				                                <a href="#">郭建</a> John大概是在学习React和Flux。。。
				                                <br/>
				                                <small class="text-muted">2017年8月18日</small>
				                            </div>
				                        </div> -->
				
				                        <div class="social-comment" id="divAnnotate">
				                            <a href="" class="pull-left">
				                                <img alt="image" src="/images/nohead.jpg">
				                            </a>
				                            <div class="media-body">
				                                <textarea id="annotateContent" class="form-control" placeholder="填写评注..." maxlength="100"></textarea>
				                            </div>
				                        </div>
				                    </div>
				                    <input type="button" class="btn btn-info" id="subComment" value="发表评注" />
			                    </div>
			                </div>
			           	</div>
			            	
			    	</div>
				</div>
				<div class="col-xs-3">
					<p><a href="javascript:" id="fileVersion" data-value="1"><i class="glyphicon glyphicon-triangle-bottom"></i> 文件版本</a></p>
					<ol id="fileList" style="padding-left:30px;">
						<c:forEach items="${fileManagement.editRecordList }" var="zb">
		            		<li><a href="javascript:;" vl="${zb.filepdf }" name="showfile"><fmt:formatDate value="${zb.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>  ${zb.createusername }</a></li>
		            	</c:forEach>
						<!-- <li><a href="#">2017-11-23 15:23  郭建</a></li> -->
					</ol>
				</div>
			</div>
			
		</div>
		<%-- <table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
		   <tbody>
				<tr>
					<td class="width-15 active"><label class="pull-right">所属档案库:</label></td>
				         <td class="width-35"><sys:treeselect id="fileManagement" name="categoryid" value="${fileManagement.categoryid}" labelName="categoryname" labelValue="${fileManagement.categoryname}"
							title="目录" url="/ehr/archiveManager/treeData" extId="${fileManagement.id}"  cssClass="form-control" />
				    </td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">标题：</label></td>
					<td class="width-35">
						<form:input path="title" htmlEscape="false" maxlength="50"    class="form-control required"/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">文件路径：</label></td>
					<td class="width-35">
						<form:hidden id="fileurl" path="fileurl" htmlEscape="false" maxlength="2000" class="form-control"/>
						<sys:ckfinderhead input="fileurl" type="files" uploadPath="/filemanagement/fileManagement" selectMultiple="false"/>
					</td>
		  		</tr>
		 	</tbody>
		</table> --%>
		
		
	</form:form>
</body>
</html>