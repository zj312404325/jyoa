<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>模板创建管理</title>
	<meta name="decorator" content="default"/>
	<link rel="stylesheet" type="text/css" href="${ctxStatic}/bootstrapValidator/bootstrapValidator.min.css">
    <script type="text/javascript" src="${ctxStatic}/bootstrapValidator/bootstrapValidator.min.js"></script>
	<style>
        .droppable-active {
            background-color: #ffe !important;
        }

        .tools a {
            cursor: pointer;
            font-size: 80%;
        }

        .form-body .col-md-6,
        .form-body .col-md-12,
		.form-body .col-md-4 {
            min-height: 400px;
        }

        .draggable {
            cursor: move;
        }
        .radio-inline input[type=radio] {
    		margin-top: 0px;
		}
		.checkbox-inline input[type=checkbox] {
    		margin-top: 0px;
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
			
			
			//验证
			$('#commentForm').bootstrapValidator({
				feedbackIcons: {
					valid: 'glyphicon glyphicon-ok',
					invalid: 'glyphicon glyphicon-remove',
					validating: 'glyphicon glyphicon-refresh'
				},
				fields: {
					templatename: {
						validators: {
							notEmpty: {
								message: '请填写模板名称！'
							}
						}
					}
				}
			});
			$("#saveTemplete").click(function(){
				var bootstrapValidator = $("#commentForm").data('bootstrapValidator');
				bootstrapValidator.validate();
				if(bootstrapValidator.isValid()){
					$.post("${ctx}/flow/flowtemplate/save",
							{
						     "flowtemplateid":$("#flowtemplateid").val(),
						     "templatename":$("#templatename").val(),
						     "remarks":$("#remarks").val(),
                             "templatetype":$("#templatetype").val()
						    },function(data){
						var jsonData = jQuery.parseJSON(data);
						if(jsonData.status == 'y'){
							layer.confirm(jsonData.info, {
								  btn: ['去设置字段','关闭'] //按钮
								}, function(){
									$("#flowtemplateid").val(jsonData.flowtemplateid);
									location.href='${ctx}/flow/flowtemplate/form?id='+$("#flowtemplateid").val()+'&tab=1';
								}, function(){
									//layer.msg(jsonData.info, {icon: 1});
									$(".nav-tabs").find("li").removeClass("disabled");
									$("#flowtemplateid").val(jsonData.flowtemplateid);
									
									$('#processTable').attr("data-ajax","${ctx}/flow/flowtemplate/getTemplatecontrolList?id="+jsonData.flowtemplateid);
									$('#processTable').bootstrapTable();
							});
							
							
						}else{
							layer.msg(jsonData.info, {icon: 2});
							//layer.alert('内容', {icon: 0});		
						}
					});
				}

				//$('#commentForm').bootstrapValidator('validate');
				
				//alert($("#commentForm").serializeObject());
				//转成json对象
				//JSON.parse("#commentForm").serializeObject());
			});
			$("#resetsaveTemplete").click(function(){
				$('#commentForm').data('bootstrapValidator').resetForm(true);
			});
			
			//验证
			$('#commentForm2').bootstrapValidator({
				feedbackIcons: {
					valid: 'glyphicon glyphicon-ok',
					invalid: 'glyphicon glyphicon-remove',
					validating: 'glyphicon glyphicon-refresh'
				},
				fields: {
					columnname: {
						validators: {
							notEmpty: {
								message: '请填写字段名称！'
							}
						}
					}
				}
			});
			$("#saveTemplatecontrol").click(function(){
				var bootstrapValidator = $("#commentForm2").data('bootstrapValidator');
				bootstrapValidator.validate();
				if(bootstrapValidator.isValid()){
					$.post("${ctx}/flow/flowtemplate/saveTemplatecontrol",{
						"flowtemplateid":$("#flowtemplateid").val(),
						"controlid":$("#controlid").val(),
						"columnname":$("#columnname").val(),
						//"columnid":$("#columnid").val(),
						"columntype":$("#columntype").find("option:selected").val(),
						"columnvalue":$("#checkboxVal").val(),
                        "valuerequire":$("#valuerequire").val()
				    },function(data){
						var jsonData = jQuery.parseJSON(data);
						if(jsonData.status == 'y'){
							layer.confirm(jsonData.info, {
								  btn: ['去设置模板','关闭'] //按钮
								}, function(){
									location.href='${ctx}/flow/flowtemplate/form?id='+$("#flowtemplateid").val()+'&tab=2';
								}, function(){
									$('#addField').modal('hide');
									//layer.msg(jsonData.info, {icon: 1});
									
									$table.bootstrapTable('refresh', {url: "${ctx}/flow/flowtemplate/getTemplatecontrolList?id="+$("#flowtemplateid").val()});
							});
							
						}else{
							layer.msg(jsonData.info, {icon: 2});
							//layer.alert('内容', {icon: 0});		
						}
					});
				}

				//$('#commentForm').bootstrapValidator('validate');
				
				//alert($("#commentForm").serializeObject());
				//转成json对象
				//JSON.parse("#commentForm").serializeObject());
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


<body class="gray-bg">
    <div class="row wrapper border-bottom white-bg page-heading">
        <div class="col-sm-4">
            <h2>
                     <c:if test="${flowtemplate.id == null || flowtemplate.id ==''}">新增流程</c:if>
                     <c:if test="${flowtemplate.id != null && flowtemplate.id !=''}">修改流程</c:if>
            </h2>
            <ol class="breadcrumb">
                <li>
                    <a href="javascript:">OA办公</a>
                </li>
                <li>
                    <a href="${ctx}/flow/flowtemplate">流程模板管理</a>
                </li>
                <li>
                     <c:if test="${flowtemplate.id == null || flowtemplate.id ==''}"><strong>新增流程</strong></c:if>
                     <c:if test="${flowtemplate.id != null && flowtemplate.id !=''}"><strong>修改流程</strong></c:if>
                </li>
            </ol>
        </div>
    </div>

    <div class="wrapper wrapper-content animated fadeInRight">
    <input type="hidden" name="flowtemplateid" id="flowtemplateid" value="${flowtemplate.id}" />
		<input type="hidden" name="templatetype" id="templatetype" value="${flowtemplate.templatetype}" />
    <sys:message content="${message}"  />
    
<%-- <input id="${flowtemplate.id}" name="${flowtemplate.id}" maxlength="255" class="form-control" type="hidden" value=""><ol id="filesPreview"><li style="list-style:none;padding-top:5px;">无</li></ol><a href="javascript:" onclick="filesFinderOpen();" class="btn btn-primary">添加</a>&nbsp;<a href="javascript:" onclick="filesDelAll();" class="btn btn-default">清除</a>
<script type="text/javascript">
function filesFinderOpen(){var date = new Date(), year = date.getFullYear(), month = (date.getMonth()+1)>9?date.getMonth()+1:"0"+(date.getMonth()+1);var url = "/jy_oa/static/ckfinder/ckfinder.html?type=files&start=files:/common/upload/"+year+"/"+month+"/&action=js&func=filesSelectAction&thumbFunc=filesThumbSelectAction&cb=filesCallback&dts=0&sm=1";windowOpen(url,"文件管理",1000,700);}
function filesSelectAction(fileUrl, data, allFiles){var url="", files=ckfinderAPI.getSelectedFiles();for(var i=0; i<files.length; i++){url += files[i].getUrl();if (i<files.length-1) url+="|";}$("#${flowtemplate.id}").val($("#${flowtemplate.id}").val()+($("#${flowtemplate.id}").val(url)==""?url:"|"+url));filesPreview();}
function filesThumbSelectAction(fileUrl, data, allFiles){var url="", files=ckfinderAPI.getSelectedFiles();for(var i=0; i<files.length; i++){url += files[i].getThumbnailUrl();if (i<files.length-1) url+="|";}$("#${flowtemplate.id}").val($("#${flowtemplate.id}").val()+($("#${flowtemplate.id}").val(url)==""?url:"|"+url));filesPreview();}
function filesCallback(api){ckfinderAPI = api;}
function filesDel(obj){var url = $(obj).prev().attr("url");$("#${flowtemplate.id}").val($("#${flowtemplate.id}").val().replace("|"+url,"","").replace(url+"|","","").replace(url,"",""));filesPreview();}
function filesDelAll(){$("#${flowtemplate.id}").val("");filesPreview();}
function filesPreview(){var li, urls = $("#${flowtemplate.id}").val().split("|");$("#filesPreview").children().remove();for (var i=0; i<urls.length; i++){if (urls[i]!=""){li = "<li><a href=\""+urls[i]+"\" url=\""+urls[i]+"\" target=\"_blank\">"+decodeURIComponent(urls[i].substring(urls[i].lastIndexOf("/")+1))+"</a>";li += "&nbsp;&nbsp;<a href=\"javascript:\" onclick=\"filesDel(this);\">×</a></li>";$("#filesPreview").append(li);}}if ($("#filesPreview").text() == ""){$("#filesPreview").html("<li style=\'list-style:none;padding-top:5px;\'>无</li>");}}
filesPreview();
</script> --%>
    
    
    	<div class="tabs-container">
        	<ul class="nav nav-tabs">
                <li id="tabtitle1" class=""><a href="#tab-1">基本设置</a>
                </li>
                <c:if test="${flowtemplate.id == null || flowtemplate.id ==''}">
	                <li id="tabtitle2" class="disabled"><a href="#tab-2">字段设置</a>
	                </li>
	                <li id="tabtitle3" class="disabled"><a href="#tab-3">模板设置</a>
	                </li>
                </c:if>
                <c:if test="${flowtemplate.id != null && flowtemplate.id !=''}">
	                <li id="tabtitle2" class=""><a href="#tab-2">字段设置</a>
	                </li>
	                <li id="tabtitle3" class=""><a href="#tab-3">模板设置</a>
	                </li>
                </c:if>
            </ul>
        	<div class="tab-content">
            	<div id="tab-1" class="tab-pane active">
                    <div class="panel-body">
                        <div class="row">
                            <div class="col-sm-6">
                                <form class="form-horizontal m-t" id="commentForm">
                                    <div class="form-group">
                                        <label class="col-sm-3 control-label">模板名称：</label>
                                        <div class="col-sm-8">
                                            <input id="templatename" name="templatename" type="text" value="${flowtemplate.templatename}"  class="form-control" >
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-sm-3 control-label">备注：</label>
                                        <div class="col-sm-8">
                                            <textarea id="remarks" name="remarks" class="form-control" >${flowtemplate.flowremarks}</textarea>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <div class="col-sm-4 col-sm-offset-3">
                                            <button class="btn btn-primary" type="button" id="saveTemplete">保存</button>
                                            <button class="btn btn-white" id="resetsaveTemplete" type="reset">重置</button>
                                        </div>
                                    </div>
                                </form>
                            </div>
                        </div>
                	</div>
                </div>
                
                <div id="tab-2" class="tab-pane">
                	<div class="panel-body">
                        <div class="row">
                            <div class="col-sm-12">
                                <div class="example-wrap">
                                    <div class="example">
                                        <div id="toolbar">
                                            <button id="add" class="btn btn-info">
                                                <i class="glyphicon glyphicon-plus"></i> 新增字段
                                            </button>
                                        </div>
                                        <%-- <table id="processTable" data-toggle="table"  data-url="${ctxStatic}/bootstrap_table_test.json" data-query-params="queryParams" data-mobile-responsive="true" data-height="" data-pagination="true" data-icon-size="outline" data-search="true" data-striped="true" data-icons-prefix="glyphicon" data-show-refresh="true" data-toolbar="#toolbar" data-page-list="[10, 15, 25, 50]" data-maintain-selected="true" data-response-handler="responseHandler">
                                            <thead>
                                                <tr>
                                                    <th data-field="id">序号</th>
                                                    <th data-field="name">字段名称</th>
                                                    <th data-field="belong">所属字段</th>
                                                    <th data-field="type" data-formatter="typeFormatter">类型</th>
                                                    <th data-field="operation" data-formatter="operateFormatter" data-events="operateEvents" data-align="center">操作</th>
                                                </tr>
                                            </thead>
                                        </table> --%>
                                        <table id="processTable"></table>
                                    </div>
                                </div>
                            </div>
                        </div>
                	</div>          
                </div>
                
                
                
                <div id="tab-3" class="tab-pane">
                	<div class="panel-body">
                        <div class="row">
                            <!-- <div class="col-sm-4">
                                <div class="ibox float-e-margins">
                                    <div class="ibox-title">
                                        <h5>字段</h5>
                                    </div>
                                    <div class="ibox-content">
                                        <div class="alert alert-info">
                                            拖拽左侧的表单元素到右侧区域，即可生成相应的表单！
                                        </div>
                                        <form role="form" class="form-horizontal m-t">
                                            <div class="form-group draggable" vl="1">
                                                <label class="col-sm-3 control-label">文本框：</label>
                                                <div class="col-sm-9">
                                                    <input type="text" name="" class="form-control" placeholder="请输入文本">
                                                    <span class="help-block m-b-none">说明文字</span>
                                                </div>
                                            </div>
                                            <div class="form-group draggable" vl="2">
                                                <label class="col-sm-3 control-label">密码框：</label>
                                                <div class="col-sm-9">
                                                    <input type="password" class="form-control" name="password" placeholder="请输入密码">
                                                </div>
                                            </div>
                                            <div class="form-group draggable" vl="7">
                                                <label class="col-sm-3 control-label">文本域：</label>
                                                <div class="col-sm-9">
                                                    <textarea class="form-control" name="" placeholder="请输入内容"></textarea>
                                                </div>
                                            </div>
                                            <div class="form-group draggable" vl="3">
                                                <label class="col-sm-3 control-label">下拉列表：</label>
                                                <div class="col-sm-9">
                                                    <select class="form-control" name="">
                                                        <option>选项 1</option>
                                                        <option>选项 2</option>
                                                        <option>选项 3</option>
                                                        <option>选项 4</option>
                                                    </select>
                                                </div>
                                            </div>
                                            <div class="form-group draggable" vl="8">
                                                <label class="col-sm-3 control-label" >文件域：</label>
                                                <div class="col-sm-9">
                                                    <input type="file" name="" class="form-control">
                                                </div>
                                            </div>
                                            <div class="form-group draggable" vl="9">
                                                <label class="col-sm-3 control-label" >纯文本：</label>
                
                                                <div class="col-sm-9">
                                                    <p class="form-control-static">这里是纯文字信息</p>
                                                </div>
                                            </div>
                                            <div class="form-group draggable" vl="4">
                                                <label class="col-sm-3 control-label">单选框：
                                                </label>
                
                                                <div class="col-sm-9">
                                                    <label class="radio-inline">
                                                        <input type="radio" checked="" value="option1" id="optionsRadios1" name="optionsRadios">选项1</label>
                                                    <label class="radio-inline">
                                                        <input type="radio" value="option2" id="optionsRadios2" name="optionsRadios">选项2</label>
                
                                                </div>
                                            </div>
                                            <div class="form-group draggable" vl="5">
                                                <label class="col-sm-3 control-label">复选框：</label>
                
                                                <div class="col-sm-9">
                                                    <label class="checkbox-inline">
                                                        <input type="checkbox" value="option1" id="inlineCheckbox1">选项1</label>
                                                    <label class="checkbox-inline">
                                                        <input type="checkbox" value="option2" id="inlineCheckbox2">选项2</label>
                                                    <label class="checkbox-inline">
                                                        <input type="checkbox" value="option3" id="inlineCheckbox3">选项3</label>
                                                </div>
                                            </div>
                                            
                                            <div class="form-group draggable" vl="6">
                                                <label class="col-sm-3 control-label">时间控件：</label>
                                                <div class="col-sm-9">
                                                    <input class="laydate-icon form-control layer-date" placeholder='YYYY-MM-DD hh:mm:ss' onclick="laydate({istime: true, format: 'YYYY-MM-DD hh:mm:ss'})">
                                                </div>
                                            </div>	
                                        </form>
                                        <div class="clearfix"></div>
                                    </div>
                                </div>
                            </div> -->
                            <div class="col-sm-12">
                                <div class="ibox float-e-margins">
                                    <div class="ibox-title">
                                        <h5>拖拽表单元素排序</h5>
                                        <div class="ibox-tools">
                                            请选择显示的列数：
                                            <select id="n-columns">
                                                <option value="1">显示1列</option>
                                                <option value="2">显示2列</option>
                                                <option value="3">显示3列</option>
                                            </select>
                                        </div>
                                    </div>
                
                                    <div class="ibox-content">
                                        <div class="row form-body form-horizontal m-t" id="formBox">
                                        
                                        <c:if test="${flowtemplate.showcolumn == '' || flowtemplate.showcolumn == null || flowtemplate.showcolumn == 1}">
                                            <div class="col-md-12 droppable sortable" name="ar">

												<%--默认标题框--%>
												<div  vl2="${con.id}" class="form-group draggable dropped controlDiv" vl="1">
													<label class="col-sm-3 control-label">标题：</label><div class="col-sm-9">
													<input type="text" name="title" id="title" class="form-control required" placeholder="请输入标题" maxlength="100"></div></div>

                                            	<c:forEach items="${flowtemplate.templatecontrolList}" var="con">
                                        				<c:choose>
                                        				  <c:when test="${con.columntype == 0}">
                                        				     <div  vl2="${con.id}" class="form-group draggable dropped controlDiv" vl="1">
                                        				     <label class="col-sm-3 control-label">${con.columnname}：</label><div class="col-sm-9">
                                        				     <input type="text" name="${con.columnid}" id="${con.columnid}" class="form-control" placeholder="请输入${con.columnname}" maxlength="1000"></div></div>
                                        				  </c:when>
                                        				  <c:when test="${con.columntype == 1}">
                                        				      <div  vl2="${con.id}" class="form-group draggable dropped controlDiv" vl="2">
                                        				      <label class="col-sm-3 control-label">${con.columnname}：</label><div class="col-sm-9">
                                        				      <input type="password" class="form-control" name="${con.columnid}" id="${con.columnid}" placeholder="请输入${con.columnname}"></div></div>
                                        				  </c:when>
                                        				  <c:when test="${con.columntype == 2}">
                                        				      <div  vl2="${con.id}" class="form-group draggable dropped controlDiv" vl="6">
                                        				      <label class="col-sm-3 control-label">${con.columnname}：</label><div class="col-sm-9">
                                        				      <input class="laydate-icon form-control layer-date" name="${con.columnid}" id="${con.columnid}" placeholder="请选择${con.columnname}"  ></div></div>
                                        				  </c:when>
                                        				  <c:when test="${con.columntype == 8}">
                                        				      <div  vl2="${con.id}" class="form-group draggable dropped controlDiv" vl="9">
                                        				      <label class="col-sm-3 control-label">${con.columnname}：</label><div class="col-sm-9">
                                        				      <input class="laydate-icon form-control layer-date" name="${con.columnid}" id="${con.columnid}" placeholder="请选择${con.columnname}"  ></div></div>
                                        				  </c:when>
                                        				  <c:when test="${con.columntype == 3}">
                                        				       <div  vl2="${con.id}" class="form-group draggable dropped controlDiv" vl="7">
                                        				       <label class="col-sm-3 control-label">${con.columnname}：</label><div class="col-sm-9">
                                        				       <textarea class="form-control" name="${con.columnid}" id="${con.columnid}" placeholder="请输入${con.columnname}" maxlength="1000"></textarea></div></div>
                                        				  </c:when>
                                        				  <c:when test="${con.columntype == 4}">
															<div vl2="${con.id}" class="form-group draggable dropped controlDiv" vl="8">
																<label class="col-sm-3 control-label" >${con.columnname}：</label><div class="col-sm-9">
																<input id="${con.columnid}" name="${con.columnid}" maxlength="255" class="form-control" type="hidden" value="">
																<button type="button" class="btn btn-primary" onclick="commonFileUpload('${con.columnid}','muti')"><i class="glyphicon glyphicon-plus"></i>&nbsp;添加附件</button>
																<ol style="line-height: 20px; padding: 10px; padding-left:40px;" id="filearea${con.columnid}"></ol></div>
																<script type="text/javascript">
                                                                    $(function(){
                                                                        $("a[name=furl${con.columnid}]").live("click",function(){
                                                                            $(this).parent().remove();
                                                                            totalUrl("${con.columnid}");
                                                                        });
                                                                    });
																	function commonFileUploadCallBack(id,url,fname){
                                                                        if(url!=null){
                                                                            $.each(url,function (i,n) {
                                                                                $("#filearea"+id).append('<li class="inline_box"><a class="inline_two" href="javascript:" vl="'+n.fileUrl+'" onclick="commonFileDownLoad(this)">'+n.fileName+'</a> &nbsp; <a href="javascript:" name="furl'+id+'" vl="'+n.fileUrl+'"><i class="glyphicon glyphicon-remove text-danger"></i></a></li>');
                                                                            })
                                                                        }
                                                                        totalUrl(id);

																	}
                                                                    function totalUrl(id) {
                                                                        var urls = "";
                                                                        $("#filearea"+id).find("a[name=furl"+id+"]").each(function(i,n){
                                                                            var obj = $(this);
                                                                            if(i==0){
                                                                                urls+=obj.attr("vl");
                                                                            }else{
                                                                                urls+=","+obj.attr("vl");
                                                                            }
                                                                        });
                                                                        $("#"+id).val(urls);
                                                                    }

																</script></div>
                                        				  </c:when>
                                        				  <c:when test="${con.columntype == 5}">
                                        				      <div  vl2="${con.id}" class="form-group draggable dropped controlDiv" vl="3">
                                        				      <label class="col-sm-3 control-label">${con.columnname}：</label><div class="col-sm-9">
                                        				      <select class="form-control" name="${con.columnid}" id="${con.columnid}">
                                        				         <c:forEach items="${ fn:split(con.columnvalue, ',') }" var="va">
                                        				            <option value="${va}">${va}</option>
                                        				         </c:forEach>
                                        				      </select></div></div>
                                        				  </c:when>
                                        				  <c:when test="${con.columntype == 6}">
                                        				      <div  vl2="${con.id}" class="form-group draggable dropped controlDiv" vl="4">
                                        				      <label class="col-sm-3 control-label">${con.columnname}：</label><div class="col-sm-9">
                                        				          <c:forEach items="${ fn:split(con.columnvalue, ',') }" var="va" varStatus="n">
                                        				            <label class="radio-inline"><input type="radio" value="${va}" name="${con.columnid}" <c:if test="${n.count==1}">checked="checked"</c:if> >${va}</label>
                                        				          </c:forEach>
                                        				      </div></div>
                                        				  </c:when>
                                        				  <c:when test="${con.columntype == 7}">
                                        				      <div  vl2="${con.id}" class="form-group draggable dropped controlDiv" vl="5">
                                        				      <label class="col-sm-3 control-label">${con.columnname}：</label><div class="col-sm-9">
                                        				          <c:forEach items="${ fn:split(con.columnvalue, ',') }" var="va" varStatus="n">
                                        				              <label class="checkbox-inline"><input type="checkbox" value="${va}" name="${con.columnid}">${va}</label>
                                        				          </c:forEach>
                                        				      </div></div>
                                        				  </c:when>
															<c:when test="${con.columntype == 10}">
																<div  vl2="${con.id}" class="form-group draggable dropped controlDiv" vl="10">
																	<label class="col-sm-3 control-label"></label><div class="col-sm-9">${con.columnname}</div></div>
															</c:when>
                                        				</c:choose>
                                                </c:forEach>
                                            </div>
                                            <div class="col-md-6 droppable sortable" style="display: none;" name="ar"></div>
                                            <div class="col-md-6 droppable sortable" style="display: none;" name="ar"></div>
                                            <div class="col-md-4 droppable sortable" style="display: none;" name="ar"></div>
                                            <div class="col-md-4 droppable sortable" style="display: none;" name="ar"></div>
                                            <div class="col-md-4 droppable sortable" style="display: none;" name="ar"></div>
                                        </c:if>
                                        
                                        <c:if test="${flowtemplate.showcolumn == 2}">
                                        	<div class="col-md-12 droppable sortable" style="display: none;" name="ar"></div>
                                            <div class="col-md-6 droppable sortable" name="ar">

												<div  vl2="title" class="form-group draggable dropped controlDiv" vl="1">
													<label class="col-sm-3 control-label">标题：</label><div class="col-sm-9">
													<input type="text" name="title" id="title" class="form-control" placeholder="请输入标题" maxlength="1000"></div></div>
                                            
                                                <c:forEach items="${flowtemplate.templatecontrolList}" var="con" varStatus="n">
                                                  <c:if test="${con.columnlocate == 1 || con.columnlocate == null}">
                                        				<c:choose>
                                        				  <c:when test="${con.columntype == 0}">
                                        				     <div  vl2="${con.id}" class="form-group draggable dropped controlDiv" vl="1">
                                        				     <label class="col-sm-3 control-label">${con.columnname}：</label><div class="col-sm-9">
                                        				     <input type="text" name="${con.columnid}" id="${con.columnid}" class="form-control" placeholder="请输入${con.columnname}" maxlength="1000"></div></div>
                                        				  </c:when>
                                        				  <c:when test="${con.columntype == 1}">
                                        				      <div  vl2="${con.id}" class="form-group draggable dropped controlDiv" vl="2">
                                        				      <label class="col-sm-3 control-label">${con.columnname}：</label><div class="col-sm-9">
                                        				      <input type="password" class="form-control" name="${con.columnid}" id="${con.columnid}" placeholder="请输入${con.columnname}"></div></div>
                                        				  </c:when>
                                        				  <c:when test="${con.columntype == 2}">
                                        				      <div  vl2="${con.id}" class="form-group draggable dropped controlDiv" vl="6">
                                        				      <label class="col-sm-3 control-label">${con.columnname}：</label><div class="col-sm-9">
                                        				      <input class="laydate-icon form-control layer-date" name="${con.columnid}" id="${con.columnid}" placeholder="请选择${con.columnname}"  ></div></div>
                                        				  </c:when>
                                        				  <c:when test="${con.columntype == 8}">
                                        				      <div  vl2="${con.id}" class="form-group draggable dropped controlDiv" vl="9">
                                        				      <label class="col-sm-3 control-label">${con.columnname}：</label><div class="col-sm-9">
                                        				      <input class="laydate-icon form-control layer-date" name="${con.columnid}" id="${con.columnid}" placeholder="请选择${con.columnname}"  ></div></div>
                                        				  </c:when>
                                        				  <c:when test="${con.columntype == 3}">
                                        				       <div  vl2="${con.id}" class="form-group draggable dropped controlDiv" vl="7">
                                        				       <label class="col-sm-3 control-label">${con.columnname}：</label><div class="col-sm-9">
                                        				       <textarea class="form-control" name="${con.columnid}" id="${con.columnid}" placeholder="请输入${con.columnname}" maxlength="1000"></textarea></div></div>
                                        				  </c:when>
                                        				  <c:when test="${con.columntype == 4}">
                                        				        <%--<div  vl2="${con.id}" class="form-group draggable dropped controlDiv" vl="8">
                                        				        <label class="col-sm-3 control-label" >${con.columnname}：</label><div class="col-sm-9">
                                        				  	    <input id="${con.columnid}" name="${con.columnid}" maxlength="255" class="form-control" type="hidden" value=""><ol id="filesPreview${con.columnid}"><li style="list-style:none;padding-top:5px;">无</li></ol><a href="javascript:" onclick="filesFinderOpen();" class="btn btn-primary">添加</a>&nbsp;<a href="javascript:" onclick="filesDelAll();" class="btn btn-default">清除</a></div>
																<script type="text/javascript">
																function filesFinderOpen(){var date = new Date(), year = date.getFullYear(), month = (date.getMonth()+1)>9?date.getMonth()+1:"0"+(date.getMonth()+1);var url = "${basePath}/static/ckfinder/ckfinder.html?type=files&start=files:/common/upload/"+year+"/"+month+"/&action=js&func=filesSelectAction&thumbFunc=filesThumbSelectAction&cb=filesCallback&dts=0&sm=1";windowOpen(url,"文件管理",1000,700);}
																function filesSelectAction(fileUrl, data, allFiles){var url="", files=ckfinderAPI.getSelectedFiles();for(var i=0; i<files.length; i++){url += files[i].getUrl();if (i<files.length-1) url+="|";}$("#${con.columnid}").val($("#${con.columnid}").val()+($("#${con.columnid}").val(url)==""?url:"|"+url));filesPreview();}
																function filesThumbSelectAction(fileUrl, data, allFiles){var url="", files=ckfinderAPI.getSelectedFiles();for(var i=0; i<files.length; i++){url += files[i].getThumbnailUrl();if (i<files.length-1) url+="|";}$("#${con.columnid}").val($("#${con.columnid}").val()+($("#${con.columnid}").val(url)==""?url:"|"+url));filesPreview();}
																function filesCallback(api){ckfinderAPI = api;}
																function filesDel(obj){var url = $(obj).prev().attr("url");$("#${con.columnid}").val($("#${con.columnid}").val().replace("|"+url,"","").replace(url+"|","","").replace(url,"",""));filesPreview();}
																function filesDelAll(){$("#${con.columnid}").val("");filesPreview();}
																function filesPreview(){var li, urls = $("#${con.columnid}").val().split("|");$("#filesPreview${con.columnid}").children().remove();for (var i=0; i<urls.length; i++){if (urls[i]!=""){li = "<li><a vl=\""+urls[i]+"\" url=\""+urls[i]+"\" target=\"_blank\" onclick=\"fileDownLoads(this)\" >"+decodeURIComponent(urls[i].substring(urls[i].lastIndexOf("/")+1))+"</a>";li += "&nbsp;&nbsp;<a href=\"javascript:\" onclick=\"filesDel(this);\">×</a></li>";$("#filesPreview${con.columnid}").append(li);}}if ($("#filesPreview${con.columnid}").text() == ""){$("#filesPreview${con.columnid}").html("<li style=\'list-style:none;padding-top:5px;\'>无</li>");}}
																filesPreview();
																</script></div>--%>
															  <div vl2="${con.id}" class="form-group draggable dropped controlDiv" vl="8">
																  <label class="col-sm-3 control-label" >${con.columnname}：</label><div class="col-sm-9">
																  <input id="${con.columnid}" name="${con.columnid}" maxlength="255" class="form-control" type="hidden" value="">
																  <button type="button" class="btn btn-primary" onclick="commonFileUpload('${con.columnid}','muti')"><i class="glyphicon glyphicon-plus"></i>&nbsp;添加附件</button>
																  <ol style="line-height: 20px; padding: 10px; padding-left:40px;" id="filearea${con.columnid}"></ol></div>
																  <script type="text/javascript">
                                                                      $(function(){
                                                                          $("a[name=furl${con.columnid}]").live("click",function(){
                                                                              $(this).parent().remove();
                                                                              totalUrl("${con.columnid}");
                                                                          });
                                                                      });
                                                                      function commonFileUploadCallBack(id,url,fname){
                                                                          if(url!=null){
                                                                              $.each(url,function (i,n) {
                                                                                  $("#filearea"+id).append('<li class="inline_box"><a class="inline_two" href="javascript:" vl="'+n.fileUrl+'" onclick="commonFileDownLoad(this)">'+n.fileName+'</a> &nbsp; <a href="javascript:" name="furl'+id+'" vl="'+n.fileUrl+'"><i class="glyphicon glyphicon-remove text-danger"></i></a></li>');
                                                                              })
                                                                          }
                                                                          totalUrl(id);

                                                                      }
                                                                      function totalUrl(id) {
                                                                          var urls = "";
                                                                          $("#filearea"+id).find("a[name=furl"+id+"]").each(function(i,n){
                                                                              var obj = $(this);
                                                                              if(i==0){
                                                                                  urls+=obj.attr("vl");
                                                                              }else{
                                                                                  urls+=","+obj.attr("vl");
                                                                              }
                                                                          });
                                                                          $("#"+id).val(urls);
                                                                      }

																  </script></div>
                                        				  </c:when>
                                        				  <c:when test="${con.columntype == 5}">
                                        				      <div  vl2="${con.id}" class="form-group draggable dropped controlDiv" vl="3">
                                        				      <label class="col-sm-3 control-label">${con.columnname}：</label><div class="col-sm-9">
                                        				      <select class="form-control" name="${con.columnid}" id="${con.columnid}">
                                        				         <c:forEach items="${ fn:split(con.columnvalue, ',') }" var="va">
                                        				            <option value="${va}">${va}</option>
                                        				         </c:forEach>
                                        				      </select></div></div>
                                        				  </c:when>
                                        				  <c:when test="${con.columntype == 6}">
                                        				      <div  vl2="${con.id}" class="form-group draggable dropped controlDiv" vl="4">
                                        				      <label class="col-sm-3 control-label">${con.columnname}：</label><div class="col-sm-9">
                                        				          <c:forEach items="${ fn:split(con.columnvalue, ',') }" var="va" varStatus="n">
                                        				            <label class="radio-inline"><input type="radio" value="${va}" name="${con.columnid}" <c:if test="${n.count==1}">checked="checked"</c:if> >${va}</label>
                                        				          </c:forEach>
                                        				      </div></div>
                                        				  </c:when>
                                        				  <c:when test="${con.columntype == 7}">
                                        				      <div  vl2="${con.id}" class="form-group draggable dropped controlDiv" vl="5">
                                        				      <label class="col-sm-3 control-label">${con.columnname}：</label><div class="col-sm-9">
                                        				          <c:forEach items="${ fn:split(con.columnvalue, ',') }" var="va" varStatus="n">
                                        				              <label class="checkbox-inline"><input type="checkbox" value="${va}" name="${con.columnid}">${va}</label>
                                        				          </c:forEach>
                                        				      </div></div>
                                        				  </c:when>
															<c:when test="${con.columntype == 10}">
																<div  vl2="${con.id}" class="form-group draggable dropped controlDiv" vl="10">
																	<label class="col-sm-3 control-label"></label><div class="col-sm-9">${con.columnname}</div></div>
															</c:when>
                                        				</c:choose>
                                        		    </c:if>
                                                </c:forEach>
                                            
                                            </div>
                                            <div class="col-md-6 droppable sortable" name="ar">
                                            			
                                            			<c:forEach items="${flowtemplate.templatecontrolList}" var="con" varStatus="n">
                                                  <c:if test="${con.columnlocate == 2}">
                                        				<c:choose>
                                        				  <c:when test="${con.columntype == 0}">
                                        				     <div  vl2="${con.id}" class="form-group draggable dropped controlDiv" vl="1">
                                        				     <label class="col-sm-3 control-label">${con.columnname}：</label><div class="col-sm-9">
                                        				     <input type="text" name="${con.columnid}" id="${con.columnid}" class="form-control" placeholder="请输入${con.columnname}" maxlength="1000"></div></div>
                                        				  </c:when>
                                        				  <c:when test="${con.columntype == 1}">
                                        				      <div  vl2="${con.id}" class="form-group draggable dropped controlDiv" vl="2">
                                        				      <label class="col-sm-3 control-label">${con.columnname}：</label><div class="col-sm-9">
                                        				      <input type="password" class="form-control" name="${con.columnid}" id="${con.columnid}" placeholder="请输入${con.columnname}"></div></div>
                                        				  </c:when>
                                        				  <c:when test="${con.columntype == 2}">
                                        				      <div  vl2="${con.id}" class="form-group draggable dropped controlDiv" vl="6">
                                        				      <label class="col-sm-3 control-label">${con.columnname}：</label><div class="col-sm-9">
                                        				      <input class="laydate-icon form-control layer-date" name="${con.columnid}" id="${con.columnid}" placeholder="请选择${con.columnname}"  ></div></div>
                                        				  </c:when>
                                        				  <c:when test="${con.columntype == 8}">
                                        				      <div  vl2="${con.id}" class="form-group draggable dropped controlDiv" vl="9">
                                        				      <label class="col-sm-3 control-label">${con.columnname}：</label><div class="col-sm-9">
                                        				      <input class="laydate-icon form-control layer-date" name="${con.columnid}" id="${con.columnid}" placeholder="请选择${con.columnname}"  ></div></div>
                                        				  </c:when>
                                        				  <c:when test="${con.columntype == 3}">
                                        				       <div  vl2="${con.id}" class="form-group draggable dropped controlDiv" vl="7">
                                        				       <label class="col-sm-3 control-label">${con.columnname}：</label><div class="col-sm-9">
                                        				       <textarea class="form-control" name="${con.columnid}" id="${con.columnid}" placeholder="请输入${con.columnname}" maxlength="1000"></textarea></div></div>
                                        				  </c:when>
                                        				  <c:when test="${con.columntype == 4}">
                                        				        <%--<div  vl2="${con.id}" class="form-group draggable dropped controlDiv" vl="8">
                                        				        <label class="col-sm-3 control-label" >${con.columnname}：</label><div class="col-sm-9">
                                        				  	    <input id="${con.columnid}" name="${con.columnid}" maxlength="255" class="form-control" type="hidden" value=""><ol id="filesPreview${con.columnid}"><li style="list-style:none;padding-top:5px;">无</li></ol><a href="javascript:" onclick="filesFinderOpen();" class="btn btn-primary">添加</a>&nbsp;<a href="javascript:" onclick="filesDelAll();" class="btn btn-default">清除</a></div>
																<script type="text/javascript">
																function filesFinderOpen(){var date = new Date(), year = date.getFullYear(), month = (date.getMonth()+1)>9?date.getMonth()+1:"0"+(date.getMonth()+1);var url = "${basePath}/static/ckfinder/ckfinder.html?type=files&start=files:/common/upload/"+year+"/"+month+"/&action=js&func=filesSelectAction&thumbFunc=filesThumbSelectAction&cb=filesCallback&dts=0&sm=1";windowOpen(url,"文件管理",1000,700);}
																function filesSelectAction(fileUrl, data, allFiles){var url="", files=ckfinderAPI.getSelectedFiles();for(var i=0; i<files.length; i++){url += files[i].getUrl();if (i<files.length-1) url+="|";}$("#${con.columnid}").val($("#${con.columnid}").val()+($("#${con.columnid}").val(url)==""?url:"|"+url));filesPreview();}
																function filesThumbSelectAction(fileUrl, data, allFiles){var url="", files=ckfinderAPI.getSelectedFiles();for(var i=0; i<files.length; i++){url += files[i].getThumbnailUrl();if (i<files.length-1) url+="|";}$("#${con.columnid}").val($("#${con.columnid}").val()+($("#${con.columnid}").val(url)==""?url:"|"+url));filesPreview();}
																function filesCallback(api){ckfinderAPI = api;}
																function filesDel(obj){var url = $(obj).prev().attr("url");$("#${con.columnid}").val($("#${con.columnid}").val().replace("|"+url,"","").replace(url+"|","","").replace(url,"",""));filesPreview();}
																function filesDelAll(){$("#${con.columnid}").val("");filesPreview();}
																function filesPreview(){var li, urls = $("#${con.columnid}").val().split("|");$("#filesPreview${con.columnid}").children().remove();for (var i=0; i<urls.length; i++){if (urls[i]!=""){li = "<li><a vl=\""+urls[i]+"\" url=\""+urls[i]+"\" target=\"_blank\" onclick=\"fileDownLoads(this)\" >"+decodeURIComponent(urls[i].substring(urls[i].lastIndexOf("/")+1))+"</a>";li += "&nbsp;&nbsp;<a href=\"javascript:\" onclick=\"filesDel(this);\">×</a></li>";$("#filesPreview${con.columnid}").append(li);}}if ($("#filesPreview${con.columnid}").text() == ""){$("#filesPreview${con.columnid}").html("<li style=\'list-style:none;padding-top:5px;\'>无</li>");}}
																filesPreview();
																</script></div>--%>
															  <div vl2="${con.id}" class="form-group draggable dropped controlDiv" vl="8">
																  <label class="col-sm-3 control-label" >${con.columnname}：</label><div class="col-sm-9">
																  <input id="${con.columnid}" name="${con.columnid}" maxlength="255" class="form-control" type="hidden" value="">
																  <button type="button" class="btn btn-primary" onclick="commonFileUpload('${con.columnid}','muti')"><i class="glyphicon glyphicon-plus"></i>&nbsp;添加附件</button>
																  <ol style="line-height: 20px; padding: 10px; padding-left:40px;" id="filearea${con.columnid}"></ol></div>
																  <script type="text/javascript">
                                                                      $(function(){
                                                                          $("a[name=furl${con.columnid}]").live("click",function(){
                                                                              $(this).parent().remove();
                                                                              totalUrl("${con.columnid}");
                                                                          });
                                                                      });
                                                                      function commonFileUploadCallBack(id,url,fname){
                                                                          if(url!=null){
                                                                              $.each(url,function (i,n) {
                                                                                  $("#filearea"+id).append('<li class="inline_box"><a class="inline_two" href="javascript:" vl="'+n.fileUrl+'" onclick="commonFileDownLoad(this)">'+n.fileName+'</a> &nbsp; <a href="javascript:" name="furl'+id+'" vl="'+n.fileUrl+'"><i class="glyphicon glyphicon-remove text-danger"></i></a></li>');
                                                                              })
                                                                          }
                                                                          totalUrl(id);

                                                                      }
                                                                      function totalUrl(id) {
                                                                          var urls = "";
                                                                          $("#filearea"+id).find("a[name=furl"+id+"]").each(function(i,n){
                                                                              var obj = $(this);
                                                                              if(i==0){
                                                                                  urls+=obj.attr("vl");
                                                                              }else{
                                                                                  urls+=","+obj.attr("vl");
                                                                              }
                                                                          });
                                                                          $("#"+id).val(urls);
                                                                      }

																  </script></div>
                                        				  </c:when>
                                        				  <c:when test="${con.columntype == 5}">
                                        				      <div  vl2="${con.id}" class="form-group draggable dropped controlDiv" vl="3">
                                        				      <label class="col-sm-3 control-label">${con.columnname}：</label><div class="col-sm-9">
                                        				      <select class="form-control" name="${con.columnid}" id="${con.columnid}">
                                        				         <c:forEach items="${ fn:split(con.columnvalue, ',') }" var="va">
                                        				            <option value="${va}">${va}</option>
                                        				         </c:forEach>
                                        				      </select></div></div>
                                        				  </c:when>
                                        				  <c:when test="${con.columntype == 6}">
                                        				      <div  vl2="${con.id}" class="form-group draggable dropped controlDiv" vl="4">
                                        				      <label class="col-sm-3 control-label">${con.columnname}：</label><div class="col-sm-9">
                                        				          <c:forEach items="${ fn:split(con.columnvalue, ',') }" var="va" varStatus="n">
                                        				            <label class="radio-inline"><input type="radio" value="${va}" name="${con.columnid}" <c:if test="${n.count==1}">checked="checked"</c:if> >${va}</label>
                                        				          </c:forEach>
                                        				      </div></div>
                                        				  </c:when>
                                        				  <c:when test="${con.columntype == 7}">
                                        				      <div  vl2="${con.id}" class="form-group draggable dropped controlDiv" vl="5">
                                        				      <label class="col-sm-3 control-label">${con.columnname}：</label><div class="col-sm-9">
                                        				          <c:forEach items="${ fn:split(con.columnvalue, ',') }" var="va" varStatus="n">
                                        				              <label class="checkbox-inline"><input type="checkbox" value="${va}" name="${con.columnid}">${va}</label>
                                        				          </c:forEach>
                                        				      </div></div>
                                        				  </c:when>
															<c:when test="${con.columntype == 10}">
																<div  vl2="${con.id}" class="form-group draggable dropped controlDiv" vl="10">
																	<label class="col-sm-3 control-label"></label><div class="col-sm-9">${con.columnname}</div></div>
															</c:when>
                                        				</c:choose>
                                        		    </c:if>
                                                </c:forEach>
                                            			
                                            </div>
                                            <div class="col-md-4 droppable sortable" style="display: none;" name="ar"></div>
                                            <div class="col-md-4 droppable sortable" style="display: none;" name="ar"></div>
                                            <div class="col-md-4 droppable sortable" style="display: none;" name="ar"></div>
                                        </c:if>
                                        <c:if test="${flowtemplate.showcolumn == 3}">
                                        	<div class="col-md-12 droppable sortable" style="display: none;" name="ar"></div>
                                        	<div class="col-md-6 droppable sortable" style="display: none;" name="ar"></div>
                                        	<div class="col-md-6 droppable sortable" style="display: none;" name="ar"></div>
                                            <div class="col-md-4 droppable sortable" name="ar">

												<div  vl2="title" class="form-group draggable dropped controlDiv" vl="1">
													<label class="col-sm-3 control-label">标题：</label><div class="col-sm-9">
													<input type="text" name="title" id="title" class="form-control" placeholder="请输入标题" maxlength="1000"></div></div>
                                            
                                            		<c:forEach items="${flowtemplate.templatecontrolList}" var="con" varStatus="n">
                                                  <c:if test="${con.columnlocate == 1 || con.columnlocate == null}">
                                        				<c:choose>
                                        				  <c:when test="${con.columntype == 0}">
                                        				     <div  vl2="${con.id}" class="form-group draggable dropped controlDiv" vl="1">
                                        				     <label class="col-sm-3 control-label">${con.columnname}：</label><div class="col-sm-9">
                                        				     <input type="text" name="${con.columnid}" id="${con.columnid}" class="form-control" placeholder="请输入${con.columnname}" maxlength="1000"></div></div>
                                        				  </c:when>
                                        				  <c:when test="${con.columntype == 1}">
                                        				      <div  vl2="${con.id}" class="form-group draggable dropped controlDiv" vl="2">
                                        				      <label class="col-sm-3 control-label">${con.columnname}：</label><div class="col-sm-9">
                                        				      <input type="password" class="form-control" name="${con.columnid}" id="${con.columnid}" placeholder="请输入${con.columnname}"></div></div>
                                        				  </c:when>
                                        				  <c:when test="${con.columntype == 2}">
                                        				      <div  vl2="${con.id}" class="form-group draggable dropped controlDiv" vl="6">
                                        				      <label class="col-sm-3 control-label">${con.columnname}：</label><div class="col-sm-9">
                                        				      <input class="laydate-icon form-control layer-date" name="${con.columnid}" id="${con.columnid}" placeholder="请选择${con.columnname}"  ></div></div>
                                        				  </c:when>
                                        				  <c:when test="${con.columntype == 8}">
                                        				      <div  vl2="${con.id}" class="form-group draggable dropped controlDiv" vl="9">
                                        				      <label class="col-sm-3 control-label">${con.columnname}：</label><div class="col-sm-9">
                                        				      <input class="laydate-icon form-control layer-date" name="${con.columnid}" id="${con.columnid}" placeholder="请选择${con.columnname}"  ></div></div>
                                        				  </c:when>
                                        				  <c:when test="${con.columntype == 3}">
                                        				       <div  vl2="${con.id}" class="form-group draggable dropped controlDiv" vl="7">
                                        				       <label class="col-sm-3 control-label">${con.columnname}：</label><div class="col-sm-9">
                                        				       <textarea class="form-control" name="${con.columnid}" id="${con.columnid}" placeholder="请输入${con.columnname}" maxlength="1000"></textarea></div></div>
                                        				  </c:when>
                                        				  <c:when test="${con.columntype == 4}">
                                        				        <%--<div  vl2="${con.id}" class="form-group draggable dropped controlDiv" vl="8">
                                        				        <label class="col-sm-3 control-label" >${con.columnname}：</label><div class="col-sm-9">
                                        				  	    <input id="${con.columnid}" name="${con.columnid}" maxlength="255" class="form-control" type="hidden" value=""><ol id="filesPreview${con.columnid}"><li style="list-style:none;padding-top:5px;">无</li></ol><a href="javascript:" onclick="filesFinderOpen();" class="btn btn-primary">添加</a>&nbsp;<a href="javascript:" onclick="filesDelAll();" class="btn btn-default">清除</a></div>
																<script type="text/javascript">
																function filesFinderOpen(){var date = new Date(), year = date.getFullYear(), month = (date.getMonth()+1)>9?date.getMonth()+1:"0"+(date.getMonth()+1);var url = "${basePath}/static/ckfinder/ckfinder.html?type=files&start=files:/common/upload/"+year+"/"+month+"/&action=js&func=filesSelectAction&thumbFunc=filesThumbSelectAction&cb=filesCallback&dts=0&sm=1";windowOpen(url,"文件管理",1000,700);}
																function filesSelectAction(fileUrl, data, allFiles){var url="", files=ckfinderAPI.getSelectedFiles();for(var i=0; i<files.length; i++){url += files[i].getUrl();if (i<files.length-1) url+="|";}$("#${con.columnid}").val($("#${con.columnid}").val()+($("#${con.columnid}").val(url)==""?url:"|"+url));filesPreview();}
																function filesThumbSelectAction(fileUrl, data, allFiles){var url="", files=ckfinderAPI.getSelectedFiles();for(var i=0; i<files.length; i++){url += files[i].getThumbnailUrl();if (i<files.length-1) url+="|";}$("#${con.columnid}").val($("#${con.columnid}").val()+($("#${con.columnid}").val(url)==""?url:"|"+url));filesPreview();}
																function filesCallback(api){ckfinderAPI = api;}
																function filesDel(obj){var url = $(obj).prev().attr("url");$("#${con.columnid}").val($("#${con.columnid}").val().replace("|"+url,"","").replace(url+"|","","").replace(url,"",""));filesPreview();}
																function filesDelAll(){$("#${con.columnid}").val("");filesPreview();}
																function filesPreview(){var li, urls = $("#${con.columnid}").val().split("|");$("#filesPreview${con.columnid}").children().remove();for (var i=0; i<urls.length; i++){if (urls[i]!=""){li = "<li><a vl=\""+urls[i]+"\" url=\""+urls[i]+"\" target=\"_blank\" onclick=\"fileDownLoads(this)\" >"+decodeURIComponent(urls[i].substring(urls[i].lastIndexOf("/")+1))+"</a>";li += "&nbsp;&nbsp;<a href=\"javascript:\" onclick=\"filesDel(this);\">×</a></li>";$("#filesPreview${con.columnid}").append(li);}}if ($("#filesPreview${con.columnid}").text() == ""){$("#filesPreview${con.columnid}").html("<li style=\'list-style:none;padding-top:5px;\'>无</li>");}}
																filesPreview();
																</script></div>--%>
															  <div vl2="${con.id}" class="form-group draggable dropped controlDiv" vl="8">
																  <label class="col-sm-3 control-label" >${con.columnname}：</label><div class="col-sm-9">
																  <input id="${con.columnid}" name="${con.columnid}" maxlength="255" class="form-control" type="hidden" value="">
																  <button type="button" class="btn btn-primary" onclick="commonFileUpload('${con.columnid}','muti')"><i class="glyphicon glyphicon-plus"></i>&nbsp;添加附件</button>
																  <ol style="line-height: 20px; padding: 10px; padding-left:40px;" id="filearea${con.columnid}"></ol></div>
																  <script type="text/javascript">
                                                                      $(function(){
                                                                          $("a[name=furl${con.columnid}]").live("click",function(){
                                                                              $(this).parent().remove();
                                                                              totalUrl("${con.columnid}");
                                                                          });
                                                                      });
                                                                      function commonFileUploadCallBack(id,url,fname){
                                                                          if(url!=null){
                                                                              $.each(url,function (i,n) {
                                                                                  $("#filearea"+id).append('<li class="inline_box"><a class="inline_two" href="javascript:" vl="'+n.fileUrl+'" onclick="commonFileDownLoad(this)">'+n.fileName+'</a> &nbsp; <a href="javascript:" name="furl'+id+'" vl="'+n.fileUrl+'"><i class="glyphicon glyphicon-remove text-danger"></i></a></li>');
                                                                              })
                                                                          }
                                                                          totalUrl(id);

                                                                      }
                                                                      function totalUrl(id) {
                                                                          var urls = "";
                                                                          $("#filearea"+id).find("a[name=furl"+id+"]").each(function(i,n){
                                                                              var obj = $(this);
                                                                              if(i==0){
                                                                                  urls+=obj.attr("vl");
                                                                              }else{
                                                                                  urls+=","+obj.attr("vl");
                                                                              }
                                                                          });
                                                                          $("#"+id).val(urls);
                                                                      }

																  </script></div>
                                        				  </c:when>
                                        				  <c:when test="${con.columntype == 5}">
                                        				      <div  vl2="${con.id}" class="form-group draggable dropped controlDiv" vl="3">
                                        				      <label class="col-sm-3 control-label">${con.columnname}：</label><div class="col-sm-9">
                                        				      <select class="form-control" name="${con.columnid}" id="${con.columnid}">
                                        				         <c:forEach items="${ fn:split(con.columnvalue, ',') }" var="va">
                                        				            <option value="${va}">${va}</option>
                                        				         </c:forEach>
                                        				      </select></div></div>
                                        				  </c:when>
                                        				  <c:when test="${con.columntype == 6}">
                                        				      <div  vl2="${con.id}" class="form-group draggable dropped controlDiv" vl="4">
                                        				      <label class="col-sm-3 control-label">${con.columnname}：</label><div class="col-sm-9">
                                        				          <c:forEach items="${ fn:split(con.columnvalue, ',') }" var="va" varStatus="n">
                                        				            <label class="radio-inline"><input type="radio" value="${va}" name="${con.columnid}" <c:if test="${n.count==1}">checked="checked"</c:if> >${va}</label>
                                        				          </c:forEach>
                                        				      </div></div>
                                        				  </c:when>
                                        				  <c:when test="${con.columntype == 7}">
                                        				      <div  vl2="${con.id}" class="form-group draggable dropped controlDiv" vl="5">
                                        				      <label class="col-sm-3 control-label">${con.columnname}：</label><div class="col-sm-9">
                                        				          <c:forEach items="${ fn:split(con.columnvalue, ',') }" var="va" varStatus="n">
                                        				              <label class="checkbox-inline"><input type="checkbox" value="${va}" name="${con.columnid}">${va}</label>
                                        				          </c:forEach>
                                        				      </div></div>
                                        				  </c:when>
															<c:when test="${con.columntype == 10}">
																<div  vl2="${con.id}" class="form-group draggable dropped controlDiv" vl="10">
																	<label class="col-sm-3 control-label"></label><div class="col-sm-9">${con.columnname}</div></div>
															</c:when>
                                        				</c:choose>
                                        		    </c:if>
                                                </c:forEach>
                                            
                                            </div>
                                            <div class="col-md-4 droppable sortable" name="ar">
                                            		<c:forEach items="${flowtemplate.templatecontrolList}" var="con" varStatus="n">
                                                  <c:if test="${con.columnlocate == 2}">
                                        				<c:choose>
                                        				  <c:when test="${con.columntype == 0}">
                                        				     <div  vl2="${con.id}" class="form-group draggable dropped controlDiv" vl="1">
                                        				     <label class="col-sm-3 control-label">${con.columnname}：</label><div class="col-sm-9">
                                        				     <input type="text" name="${con.columnid}" id="${con.columnid}" class="form-control" placeholder="请输入${con.columnname}" maxlength="1000"></div></div>
                                        				  </c:when>
                                        				  <c:when test="${con.columntype == 1}">
                                        				      <div  vl2="${con.id}" class="form-group draggable dropped controlDiv" vl="2">
                                        				      <label class="col-sm-3 control-label">${con.columnname}：</label><div class="col-sm-9">
                                        				      <input type="password" class="form-control" name="${con.columnid}" id="${con.columnid}" placeholder="请输入${con.columnname}"></div></div>
                                        				  </c:when>
                                        				  <c:when test="${con.columntype == 2}">
                                        				      <div  vl2="${con.id}" class="form-group draggable dropped controlDiv" vl="6">
                                        				      <label class="col-sm-3 control-label">${con.columnname}：</label><div class="col-sm-9">
                                        				      <input class="laydate-icon form-control layer-date" name="${con.columnid}" id="${con.columnid}" placeholder="请选择${con.columnname}"  ></div></div>
                                        				  </c:when>
                                        				  <c:when test="${con.columntype == 8}">
                                        				      <div  vl2="${con.id}" class="form-group draggable dropped controlDiv" vl="9">
                                        				      <label class="col-sm-3 control-label">${con.columnname}：</label><div class="col-sm-9">
                                        				      <input class="laydate-icon form-control layer-date" name="${con.columnid}" id="${con.columnid}" placeholder="请选择${con.columnname}"  ></div></div>
                                        				  </c:when>
                                        				  <c:when test="${con.columntype == 3}">
                                        				       <div  vl2="${con.id}" class="form-group draggable dropped controlDiv" vl="7">
                                        				       <label class="col-sm-3 control-label">${con.columnname}：</label><div class="col-sm-9">
                                        				       <textarea class="form-control" name="${con.columnid}" id="${con.columnid}" placeholder="请输入${con.columnname}" maxlength="1000"></textarea></div></div>
                                        				  </c:when>
                                        				  <c:when test="${con.columntype == 4}">
                                        				        <%--<div  vl2="${con.id}" class="form-group draggable dropped controlDiv" vl="8">
                                        				        <label class="col-sm-3 control-label" >${con.columnname}：</label><div class="col-sm-9">
                                        				  	    <input id="${con.columnid}" name="${con.columnid}" maxlength="255" class="form-control" type="hidden" value=""><ol id="filesPreview${con.columnid}"><li style="list-style:none;padding-top:5px;">无</li></ol><a href="javascript:" onclick="filesFinderOpen();" class="btn btn-primary">添加</a>&nbsp;<a href="javascript:" onclick="filesDelAll();" class="btn btn-default">清除</a></div>
																<script type="text/javascript">
																function filesFinderOpen(){var date = new Date(), year = date.getFullYear(), month = (date.getMonth()+1)>9?date.getMonth()+1:"0"+(date.getMonth()+1);var url = "${basePath}/static/ckfinder/ckfinder.html?type=files&start=files:/common/upload/"+year+"/"+month+"/&action=js&func=filesSelectAction&thumbFunc=filesThumbSelectAction&cb=filesCallback&dts=0&sm=1";windowOpen(url,"文件管理",1000,700);}
																function filesSelectAction(fileUrl, data, allFiles){var url="", files=ckfinderAPI.getSelectedFiles();for(var i=0; i<files.length; i++){url += files[i].getUrl();if (i<files.length-1) url+="|";}$("#${con.columnid}").val($("#${con.columnid}").val()+($("#${con.columnid}").val(url)==""?url:"|"+url));filesPreview();}
																function filesThumbSelectAction(fileUrl, data, allFiles){var url="", files=ckfinderAPI.getSelectedFiles();for(var i=0; i<files.length; i++){url += files[i].getThumbnailUrl();if (i<files.length-1) url+="|";}$("#${con.columnid}").val($("#${con.columnid}").val()+($("#${con.columnid}").val(url)==""?url:"|"+url));filesPreview();}
																function filesCallback(api){ckfinderAPI = api;}
																function filesDel(obj){var url = $(obj).prev().attr("url");$("#${con.columnid}").val($("#${con.columnid}").val().replace("|"+url,"","").replace(url+"|","","").replace(url,"",""));filesPreview();}
																function filesDelAll(){$("#${con.columnid}").val("");filesPreview();}
																function filesPreview(){var li, urls = $("#${con.columnid}").val().split("|");$("#filesPreview${con.columnid}").children().remove();for (var i=0; i<urls.length; i++){if (urls[i]!=""){li = "<li><a vl=\""+urls[i]+"\" url=\""+urls[i]+"\" target=\"_blank\" onclick=\"fileDownLoads(this)\" >"+decodeURIComponent(urls[i].substring(urls[i].lastIndexOf("/")+1))+"</a>";li += "&nbsp;&nbsp;<a href=\"javascript:\" onclick=\"filesDel(this);\">×</a></li>";$("#filesPreview${con.columnid}").append(li);}}if ($("#filesPreview${con.columnid}").text() == ""){$("#filesPreview${con.columnid}").html("<li style=\'list-style:none;padding-top:5px;\'>无</li>");}}
																filesPreview();
																</script></div>--%>
															  <div vl2="${con.id}" class="form-group draggable dropped controlDiv" vl="8">
																  <label class="col-sm-3 control-label" >${con.columnname}：</label><div class="col-sm-9">
																  <input id="${con.columnid}" name="${con.columnid}" maxlength="255" class="form-control" type="hidden" value="">
																  <button type="button" class="btn btn-primary" onclick="commonFileUpload('${con.columnid}','muti')"><i class="glyphicon glyphicon-plus"></i>&nbsp;添加附件</button>
																  <ol style="line-height: 20px; padding: 10px; padding-left:40px;" id="filearea${con.columnid}"></ol></div>
																  <script type="text/javascript">
                                                                      $(function(){
                                                                          $("a[name=furl${con.columnid}]").live("click",function(){
                                                                              $(this).parent().remove();
                                                                              totalUrl("${con.columnid}");
                                                                          });
                                                                      });
                                                                      function commonFileUploadCallBack(id,url,fname){
                                                                          if(url!=null){
                                                                              $.each(url,function (i,n) {
                                                                                  $("#filearea"+id).append('<li class="inline_box"><a class="inline_two" href="javascript:" vl="'+n.fileUrl+'" onclick="commonFileDownLoad(this)">'+n.fileName+'</a> &nbsp; <a href="javascript:" name="furl'+id+'" vl="'+n.fileUrl+'"><i class="glyphicon glyphicon-remove text-danger"></i></a></li>');
                                                                              })
                                                                          }
                                                                          totalUrl(id);

                                                                      }
                                                                      function totalUrl(id) {
                                                                          var urls = "";
                                                                          $("#filearea"+id).find("a[name=furl"+id+"]").each(function(i,n){
                                                                              var obj = $(this);
                                                                              if(i==0){
                                                                                  urls+=obj.attr("vl");
                                                                              }else{
                                                                                  urls+=","+obj.attr("vl");
                                                                              }
                                                                          });
                                                                          $("#"+id).val(urls);
                                                                      }

																  </script></div>
                                        				  </c:when>
                                        				  <c:when test="${con.columntype == 5}">
                                        				      <div  vl2="${con.id}" class="form-group draggable dropped controlDiv" vl="3">
                                        				      <label class="col-sm-3 control-label">${con.columnname}：</label><div class="col-sm-9">
                                        				      <select class="form-control" name="${con.columnid}" id="${con.columnid}">
                                        				         <c:forEach items="${ fn:split(con.columnvalue, ',') }" var="va">
                                        				            <option value="${va}">${va}</option>
                                        				         </c:forEach>
                                        				      </select></div></div>
                                        				  </c:when>
                                        				  <c:when test="${con.columntype == 6}">
                                        				      <div  vl2="${con.id}" class="form-group draggable dropped controlDiv" vl="4">
                                        				      <label class="col-sm-3 control-label">${con.columnname}：</label><div class="col-sm-9">
                                        				          <c:forEach items="${ fn:split(con.columnvalue, ',') }" var="va" varStatus="n">
                                        				            <label class="radio-inline"><input type="radio" value="${va}" name="${con.columnid}" <c:if test="${n.count==1}">checked="checked"</c:if> >${va}</label>
                                        				          </c:forEach>
                                        				      </div></div>
                                        				  </c:when>
                                        				  <c:when test="${con.columntype == 7}">
                                        				      <div  vl2="${con.id}" class="form-group draggable dropped controlDiv" vl="5">
                                        				      <label class="col-sm-3 control-label">${con.columnname}：</label><div class="col-sm-9">
                                        				          <c:forEach items="${ fn:split(con.columnvalue, ',') }" var="va" varStatus="n">
                                        				              <label class="checkbox-inline"><input type="checkbox" value="${va}" name="${con.columnid}">${va}</label>
                                        				          </c:forEach>
                                        				      </div></div>
                                        				  </c:when>
															<c:when test="${con.columntype == 10}">
																<div  vl2="${con.id}" class="form-group draggable dropped controlDiv" vl="10">
																	<label class="col-sm-3 control-label"></label><div class="col-sm-9">${con.columnname}</div></div>
															</c:when>
                                        				</c:choose>
                                        		    </c:if>
                                                </c:forEach>
                                            </div>
                                            <div class="col-md-4 droppable sortable" name="ar">
                                                 <c:forEach items="${flowtemplate.templatecontrolList}" var="con" varStatus="n">
                                                  <c:if test="${con.columnlocate == 3}">
                                        				<c:choose>
                                        				  <c:when test="${con.columntype == 0}">
                                        				     <div  vl2="${con.id}" class="form-group draggable dropped controlDiv" vl="1">
                                        				     <label class="col-sm-3 control-label">${con.columnname}：</label><div class="col-sm-9">
                                        				     <input type="text" name="${con.columnid}" id="${con.columnid}" class="form-control" placeholder="请输入${con.columnname}" maxlength="1000"></div></div>
                                        				  </c:when>
                                        				  <c:when test="${con.columntype == 1}">
                                        				      <div  vl2="${con.id}" class="form-group draggable dropped controlDiv" vl="2">
                                        				      <label class="col-sm-3 control-label">${con.columnname}：</label><div class="col-sm-9">
                                        				      <input type="password" class="form-control" name="${con.columnid}" id="${con.columnid}" placeholder="请输入${con.columnname}"></div></div>
                                        				  </c:when>
                                        				  <c:when test="${con.columntype == 2}">
                                        				      <div  vl2="${con.id}" class="form-group draggable dropped controlDiv" vl="6">
                                        				      <label class="col-sm-3 control-label">${con.columnname}：</label><div class="col-sm-9">
                                        				      <input class="laydate-icon form-control layer-date" name="${con.columnid}" id="${con.columnid}" placeholder="请选择${con.columnname}"  ></div></div>
                                        				  </c:when>
                                        				  <c:when test="${con.columntype == 8}">
                                        				      <div  vl2="${con.id}" class="form-group draggable dropped controlDiv" vl="9">
                                        				      <label class="col-sm-3 control-label">${con.columnname}：</label><div class="col-sm-9">
                                        				      <input class="laydate-icon form-control layer-date" name="${con.columnid}" id="${con.columnid}" placeholder="请选择${con.columnname}"  ></div></div>
                                        				  </c:when>
                                        				  <c:when test="${con.columntype == 3}">
                                        				       <div  vl2="${con.id}" class="form-group draggable dropped controlDiv" vl="7">
                                        				       <label class="col-sm-3 control-label">${con.columnname}：</label><div class="col-sm-9">
                                        				       <textarea class="form-control" name="${con.columnid}" id="${con.columnid}" placeholder="请输入${con.columnname}" maxlength="1000"></textarea></div></div>
                                        				  </c:when>
                                        				  <c:when test="${con.columntype == 4}">
                                        				        <%--<div  vl2="${con.id}" class="form-group draggable dropped controlDiv" vl="8">
                                        				        <label class="col-sm-3 control-label" >${con.columnname}：</label><div class="col-sm-9">
                                        				  	    <input id="${con.columnid}" name="${con.columnid}" maxlength="255" class="form-control" type="hidden" value=""><ol id="filesPreview${con.columnid}"><li style="list-style:none;padding-top:5px;">无</li></ol><a href="javascript:" onclick="filesFinderOpen();" class="btn btn-primary">添加</a>&nbsp;<a href="javascript:" onclick="filesDelAll();" class="btn btn-default">清除</a></div>
																<script type="text/javascript">
																function filesFinderOpen(){var date = new Date(), year = date.getFullYear(), month = (date.getMonth()+1)>9?date.getMonth()+1:"0"+(date.getMonth()+1);var url = "${basePath}/static/ckfinder/ckfinder.html?type=files&start=files:/common/upload/"+year+"/"+month+"/&action=js&func=filesSelectAction&thumbFunc=filesThumbSelectAction&cb=filesCallback&dts=0&sm=1";windowOpen(url,"文件管理",1000,700);}
																function filesSelectAction(fileUrl, data, allFiles){var url="", files=ckfinderAPI.getSelectedFiles();for(var i=0; i<files.length; i++){url += files[i].getUrl();if (i<files.length-1) url+="|";}$("#${con.columnid}").val($("#${con.columnid}").val()+($("#${con.columnid}").val(url)==""?url:"|"+url));filesPreview();}
																function filesThumbSelectAction(fileUrl, data, allFiles){var url="", files=ckfinderAPI.getSelectedFiles();for(var i=0; i<files.length; i++){url += files[i].getThumbnailUrl();if (i<files.length-1) url+="|";}$("#${con.columnid}").val($("#${con.columnid}").val()+($("#${con.columnid}").val(url)==""?url:"|"+url));filesPreview();}
																function filesCallback(api){ckfinderAPI = api;}
																function filesDel(obj){var url = $(obj).prev().attr("url");$("#${con.columnid}").val($("#${con.columnid}").val().replace("|"+url,"","").replace(url+"|","","").replace(url,"",""));filesPreview();}
																function filesDelAll(){$("#${con.columnid}").val("");filesPreview();}
																function filesPreview(){var li, urls = $("#${con.columnid}").val().split("|");$("#filesPreview${con.columnid}").children().remove();for (var i=0; i<urls.length; i++){if (urls[i]!=""){li = "<li><a vl=\""+urls[i]+"\" url=\""+urls[i]+"\" target=\"_blank\" onclick=\"fileDownLoads(this)\" >"+decodeURIComponent(urls[i].substring(urls[i].lastIndexOf("/")+1))+"</a>";li += "&nbsp;&nbsp;<a href=\"javascript:\" onclick=\"filesDel(this);\">×</a></li>";$("#filesPreview${con.columnid}").append(li);}}if ($("#filesPreview${con.columnid}").text() == ""){$("#filesPreview${con.columnid}").html("<li style=\'list-style:none;padding-top:5px;\'>无</li>");}}
																filesPreview();
																</script></div>--%>
															  <div vl2="${con.id}" class="form-group draggable dropped controlDiv" vl="8">
																  <label class="col-sm-3 control-label" >${con.columnname}：</label><div class="col-sm-9">
																  <input id="${con.columnid}" name="${con.columnid}" maxlength="255" class="form-control" type="hidden" value="">
																  <button type="button" class="btn btn-primary" onclick="commonFileUpload('${con.columnid}','muti')"><i class="glyphicon glyphicon-plus"></i>&nbsp;添加附件</button>
																  <ol style="line-height: 20px; padding: 10px; padding-left:40px;" id="filearea${con.columnid}"></ol></div>
																  <script type="text/javascript">
                                                                      $(function(){
                                                                          $("a[name=furl${con.columnid}]").live("click",function(){
                                                                              $(this).parent().remove();
                                                                              totalUrl("${con.columnid}");
                                                                          });
                                                                      });
                                                                      function commonFileUploadCallBack(id,url,fname){
                                                                          if(url!=null){
                                                                              $.each(url,function (i,n) {
                                                                                  $("#filearea"+id).append('<li class="inline_box"><a class="inline_two" href="javascript:" vl="'+n.fileUrl+'" onclick="commonFileDownLoad(this)">'+n.fileName+'</a> &nbsp; <a href="javascript:" name="furl'+id+'" vl="'+n.fileUrl+'"><i class="glyphicon glyphicon-remove text-danger"></i></a></li>');
                                                                              })
                                                                          }
                                                                          totalUrl(id);

                                                                      }
                                                                      function totalUrl(id) {
                                                                          var urls = "";
                                                                          $("#filearea"+id).find("a[name=furl"+id+"]").each(function(i,n){
                                                                              var obj = $(this);
                                                                              if(i==0){
                                                                                  urls+=obj.attr("vl");
                                                                              }else{
                                                                                  urls+=","+obj.attr("vl");
                                                                              }
                                                                          });
                                                                          $("#"+id).val(urls);
                                                                      }

																  </script></div>
                                        				  </c:when>
                                        				  <c:when test="${con.columntype == 5}">
                                        				      <div  vl2="${con.id}" class="form-group draggable dropped controlDiv" vl="3">
                                        				      <label class="col-sm-3 control-label">${con.columnname}：</label><div class="col-sm-9">
                                        				      <select class="form-control" name="${con.columnid}" id="${con.columnid}">
                                        				         <c:forEach items="${ fn:split(con.columnvalue, ',') }" var="va">
                                        				            <option value="${va}">${va}</option>
                                        				         </c:forEach>
                                        				      </select></div></div>
                                        				  </c:when>
                                        				  <c:when test="${con.columntype == 6}">
                                        				      <div  vl2="${con.id}" class="form-group draggable dropped controlDiv" vl="4">
                                        				      <label class="col-sm-3 control-label">${con.columnname}：</label><div class="col-sm-9">
                                        				          <c:forEach items="${ fn:split(con.columnvalue, ',') }" var="va" varStatus="n">
                                        				            <label class="radio-inline"><input type="radio" value="${va}" name="${con.columnid}" <c:if test="${n.count==1}">checked="checked"</c:if> >${va}</label>
                                        				          </c:forEach>
                                        				      </div></div>
                                        				  </c:when>
                                        				  <c:when test="${con.columntype == 7}">
                                        				      <div  vl2="${con.id}" class="form-group draggable dropped controlDiv" vl="5">
                                        				      <label class="col-sm-3 control-label">${con.columnname}：</label><div class="col-sm-9">
                                        				          <c:forEach items="${ fn:split(con.columnvalue, ',') }" var="va" varStatus="n">
                                        				              <label class="checkbox-inline"><input type="checkbox" value="${va}" name="${con.columnid}">${va}</label>
                                        				          </c:forEach>
                                        				      </div></div>
                                        				  </c:when>
															<c:when test="${con.columntype == 10}">
																<div  vl2="${con.id}" class="form-group draggable dropped controlDiv" vl="10">
																	<label class="col-sm-3 control-label"></label><div class="col-sm-9">${con.columnname}</div></div>
															</c:when>
                                        				</c:choose>
                                        		    </c:if>
                                                </c:forEach>
                                            </div>
                                        </c:if>
                                        </div>
                                        <!-- <button type="submit" class="btn btn-warning" data-clipboard-text="testing" id="copy-to-clipboard">复制代码</button> -->
                                        <button type="button" class="btn btn-warning" data-clipboard-text="testing" id="savesort">保存</button>
                                    </div>
                                </div>
                            </div>
                        </div>
            		</div>
            	</div>
        	</div>

        </div>
        
    </div>
    
    
    <!-- 新增字段弹出层 -->
    
    <div class="modal fade" id="addField" tabindex="-1" role="dialog" aria-labelledby="fieldModalLabel" aria-hidden="true">
        <input id="controlid" name="controlid" type="hidden" value="" >
    	<div class="modal-dialog">
            <div class="modal-content">
            	<form class="form-horizontal m-t" id="commentForm2">
                <div class="modal-header">
                	<button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">关闭</span></button>
                	<h4 class="modal-title" id="fieldModalLabel">字段属性</h4>
                </div>
                <div class="modal-body">
                    <div class="row">
                        <div class="col-sm-12">
                            <form class="form-horizontal m-t" id="commentForm">
                                <div class="form-group">
                                    <label class="col-sm-3 control-label">字段名称：</label>
                                    <div class="col-sm-8">
                                        <input id="columnname" name="columnname" type="text" class="form-control" >
                                    </div>
                                </div>
                                <!-- <div class="form-group">
                                    <label class="col-sm-3 control-label">所属字段：</label>
                                    <div class="col-sm-8">
                                        <input id="columnid" name="columnid" type="text" class="form-control" >
                                    </div>
                                </div> -->
                                <div class="form-group">
                                    <label class="col-sm-3 control-label">类型：</label>
                                    <div class="col-sm-8">
                                        <select id="columntype" name="columntype" class="form-control" >
                                            <option value="0" vl="type0">文本控件</option>
                                            <option value="1" vl="type1">密码控件</option>
                                            <option value="2" vl="type2">时间控件（年月日 时分秒）</option>
                                            <option value="8" vl="type9">时间控件（年月日）</option>
                                            <option value="3" vl="type3">文本域控件</option>
                                            <option value="4" vl="type4">上传控件</option>
                                            <option value="5" vl="type6">下拉控件</option>
                                            <option value="6" vl="type7">单选控件</option>
                                            <option value="7" vl="type8">多选控件</option>
                                            <option value="10" vl="type10">纯文本控件</option>
                                        </select>
                                    </div>
                                </div>
                                <div class="form-group" style="display:none;">
                                    <label class="col-sm-3 control-label">选择框值：</label>
                                    <div class="col-sm-8">
                                    	<div class="input-group">
                                            <input id="checkboxVal" name="value" type="text" class="form-control" readonly />
                                            <div class="input-group-btn">
                                                <button data-toggle="dropdown" class="btn btn-white dropdown-toggle" type="button">操作 <span class="caret"></span>
                                                </button>
                                                <ul class="dropdown-menu pull-right">
                                                    <li><a href="javascript:" onclick="selectValue($(this).closest('.input-group-btn').prev('input').attr('vl'),$(this).closest('.input-group-btn').prev('input').val())">编辑</a>
                                                    </li>
                                                </ul>
                                            </div>
                                        </div>
                                    </div>
                                </div>
								<div class="form-group">
									<label class="col-sm-3 control-label">是否必填：</label>
									<div class="col-sm-8">
										<select id="valuerequire" name="valuerequire" class="form-control" >
											<option value="1" selected="selected">是</option>
											<option value="0">否</option>
										</select>
									</div>
								</div>
                                <div class="form-group">
                                    <div class="col-sm-4 col-sm-offset-3">
                                        <button class="btn btn-primary" id="saveTemplatecontrol" type="button">保存</button>
                                        <button class="btn btn-white" type="reset" id="reset">重置</button>
                                    </div>
                                </div>
                            </form>
                        </div>
                    </div>
    			</div>
    		</div>
    	</div>
    </div>
    <!-- end -->
    
    
    <!-- 下拉、单选、多选编辑层 -->
    <div class="modal fade" id="editValue" tabindex="-1" role="dialog" aria-labelledby="valueModalLabel" aria-hidden="true">
    	<div class="modal-dialog">
            <div class="modal-content">
            	<form class="form-horizontal m-t" id="commentForm">
                <div class="modal-header">
                	<button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">关闭</span></button>
                	<h4 class="modal-title" id="valueModalLabel"></h4>
                </div>
                <div class="modal-body">
                    <div class="row">
                        <div class="col-sm-12">
                            <form class="form-horizontal m-t" id="commentForm">
                                <div class="form-group">
                                    <table class="table table-hover" id="valueTable">
                                        <tbody>
                                            <tr class="first last">
                                                <td><input type="text" class="form-control" /></td>
                                                <td>
                                                	<a class="up" href="javascript:void(0)" onClick="moveUp(this)" title="上移"><i class="glyphicon glyphicon-arrow-up"></i></a>&nbsp;&nbsp;<a class="down" href="javascript:void(0)" onClick="moveDown(this)" title="下移"><i class="glyphicon glyphicon-arrow-down"></i></a>&nbsp;&nbsp;<a class="remove" href="javascript:void(0)" onClick="delValue(this)" title="删除"><i class="glyphicon glyphicon-remove"></i></a>
                                                </td>
                                            </tr>
                                        </tbody>
                                    </table>
                                </div>
                                <div class="form-group">
                                	<div class="col-sm-2 col-sm-offset-10">
                                        <button class="btn btn-primary" type="button" id="addValue">添加值</button>
                                    </div>
                                </div>
                            </form>
                        </div>
                    </div>
    			</div>
               	<div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                    <button type="button" class="btn btn-primary" id="valueSubmit">提交</button>
                </div>
    		</div>
    	</div>
    </div>
    
    
    <!-- 表单预览 -->
    <div class="modal fade" id="formShow" tabindex="-1" role="dialog" aria-hidden="true">
        <div class="modal-dialog" style="width:900px;">
            <div class="modal-content">
                <form class="form-horizontal m-t">
                <div class="modal-header">
                	<button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">关闭</span></button>
                	<h4 class="modal-title">表单预览</h4>
                </div>
                <div class="modal-body">
                    <div class="row">
                    </div>
                </div>
                <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-primary" id="formSubmit">保存</button>
                </div>
                </form>
            </div>
        </div>
    </div>
           	
    <!-- <script src="js/jquery.min.js?v=2.1.4"></script>
    <script src="js/bootstrap.min.js?v=3.3.6"></script>

    <script src="js/content.js?v=1.0.0"></script>
    
    <script src="js/plugins/validate/jquery.validate.min.js"></script>
    <script src="js/plugins/validate/messages_zh.min.js"></script>
    
    <script src="js/plugins/bootstrap-table/bootstrap-table.min.js"></script>
    <script src="js/plugins/bootstrap-table/bootstrap-table-mobile.min.js"></script>
    <script src="js/plugins/bootstrap-table/locale/bootstrap-table-zh-CN.min.js"></script>

    <script src="js/jquery.tablednd.js"></script>
    <script src="js/bootstrap-table-reorder-rows.js"></script>
    
    <script src="js/jquery-ui-1.10.4.min.js"></script>

    <script src="js/plugins/beautifyhtml/beautifyhtml.js"></script>
    
    <script src="js/plugins/layer/laydate/laydate.js"></script> -->
    
    <script>
		var $table = $("#processTable");
		var $remove = $("#remove");
		selections = [];
		$(function(){
			
			if("${tab}" != "" || "${tab}" !=null){
				//alert("${tab}");
				if("${tab}"=="1"){
					$(".nav-tabs ul").find("li").removeClass("active");
					$('#tabtitle2').addClass("active");
					$(".tab-pane").removeClass("active");
					$("#tab-2").addClass("active");
					loaddata();
					//$table.bootstrapTable('refresh', {url: "${ctx}/flow/flowtemplate/getTemplatecontrolList?id="+$("#flowtemplateid").val()});
				}else if("${tab}"=="2"){
					
					if("${flowtemplate.showcolumn}" != "" && "${flowtemplate.showcolumn}" !=null && "${flowtemplate.showcolumn}" !=0){
						$("#n-columns").val("${flowtemplate.showcolumn}");
					}
					
					$(".nav-tabs ul").find("li").removeClass("active");
					$('#tabtitle3').addClass("active");
					$(".tab-pane").removeClass("active");
					$("#tab-3").addClass("active");
					
					savesort();
					//loadhtml();
				}else{
					$(".nav-tabs ul").find("li").removeClass("active");
					$('#tabtitle1').addClass("active");
					$(".tab-pane").removeClass("active");
					$("#tab-1").addClass("active");
				}
			}
			
			
			$table.bootstrapTable('refresh',{url:""});
			$(".nav-tabs").find("li").each(function(i){
				$(this).on("click",function(){
					if($(this).hasClass("disabled")){
						$(this).find("a").attr("href","javascript:");
					}else{
						$(this).find("a").attr("href","#tab-"+ (i+1));
						location.href='${ctx}/flow/flowtemplate/form?id='+$("#flowtemplateid").val()+'&tab='+i;
					}
				});
			});
			
			
			$("#columntype").change(function(){
			    clearInput();
				var typeN = $(this).find("option:selected").attr("vl");
				if(typeN == "type6" || typeN == "type7" || typeN == "type8"){
					$(this).closest(".form-group").next(".form-group").slideDown();
					$(this).closest(".form-group").next(".form-group").find("input[type=text]").attr("vl",typeN);
				}else{
					$(this).closest(".form-group").next(".form-group").slideUp();
					$(this).closest(".form-group").next(".form-group").find("input[type=text]").attr("vl","");
				}
                if(typeN == "type4" || typeN == "type6" || typeN == "type7"|| typeN == "type10"){
                    $(this).closest(".form-group").next(".form-group").next(".form-group").slideUp();
                }else{
                    $(this).closest(".form-group").next(".form-group").next(".form-group").slideDown();
                }
			});
			
			//重置按钮
			$("#reset").click(function(){
				if($("#checkboxVal").closest(".form-group").is(":visible")){
					$("#checkboxVal").closest(".form-group").slideUp();
				}
				$('#commentForm2').data('bootstrapValidator').resetForm(true);
			});
			
			//添加值
			$("#addValue").click(function(){
				var html = '<tr class="last"><td><input type="text" class="form-control" /></td>'
                           +'<td><a class="up" href="javascript:void(0)" onClick="moveUp(this)" title="上移"><i class="glyphicon glyphicon-arrow-up"></i></a>&nbsp;&nbsp;'
                           +'<a class="down" href="javascript:void(0)" onClick="moveDown(this)" title="下移"><i class="glyphicon glyphicon-arrow-down"></i></a>&nbsp;&nbsp;'
						   +'<a class="remove" href="javascript:void(0)" onClick="delValue(this)" title="删除"><i class="glyphicon glyphicon-remove"></i></a></td></tr>';
				$("#valueTable").find("tr:last").after(html).removeClass("last");
			});
			//提交值
			$("#valueSubmit").click(function(){
				var value = "";
				$("#valueTable").find("tr").each(function(i){
					if( i != $("#valueTable").find("tr").length - 1){
						value += $(this).find("input").val() + ",";
					}else{
						value += $(this).find("input").val();
					}
				});
				$('#editValue').modal('hide');
				$("#checkboxVal").val(value);
				
			});
			
			setTimeout(function () {
				$table.bootstrapTable('resetView');
			}, 200);
			
			$("#add").click(function () {
				$('#commentForm2').data('bootstrapValidator').resetForm(true);
				$("#controlid").val("");
				$("#columntype").val(0);
	            $("#checkboxVal").val("");
	            $("#columntype").closest(".form-group").next(".form-group").slideUp();
				$("#addField").modal();	
			});	
			
			
			setup_draggable();

            $("#n-columns").on("change", function () {
                var v = $(this).val();
                if (v === "1") {
                    var $col = $('.form-body .col-md-12').toggle(true);
                    $('.form-body .col-md-6 .draggable').each(function (i, el) {
                        $(this).remove().appendTo($col);
                    })
					$(".form-body .col-md-4 .draggable").each(function (i, el) {
                        $(this).remove().appendTo($col);
                    });
                    $('.form-body .col-md-6').toggle(false);
					$('.form-body .col-md-4').toggle(false);
                } else if (v === "2") {
                    var $col = $('.form-body .col-md-6').toggle(true);
                    $(".form-body .col-md-12 .draggable").each(function (i, el) {
                        $(this).remove().appendTo(i % 2 ? $col[1] : $col[0]);
                    });
					$(".form-body .col-md-4 .draggable").each(function (i, el) {
                        $(this).remove().appendTo(i % 2 ? $col[1] : $col[0]);
                    });
                    $('.form-body .col-md-12').toggle(false);
					$('.form-body .col-md-4').toggle(false);
                }else{
					var $col = $('.form-body .col-md-4').toggle(true);
                    $(".form-body .col-md-12 .draggable").each(function (i, el) {
						var icol;
						switch (i % 3){
							case 0:
								icol = $col[0];
								break;
							case 1:
								icol = $col[1];
								break;
							case 2:
								icol = $col[2];
						}
                        $(this).remove().appendTo(icol);
                    });
					$(".form-body .col-md-6 .draggable").each(function (i, el) {
                        var icol;
						switch (i % 3){
							case 0:
								icol = $col[0];
								break;
							case 1:
								icol = $col[1];
								break;
							case 2:
								icol = $col[2];
						}
                        $(this).remove().appendTo(icol);
                    });
                    $('.form-body .col-md-12').toggle(false);
					$(".form-body .col-md-6").toggle(false);
				}
                $("#formBox").find(".form-group").removeAttr("style");
            });

            $("#copy-to-clipboard").on("click", function () {
                var $copy = $(".form-body").clone().appendTo(document.body);
                $copy.find(".tools, div:hidden").remove();
                $.each(["draggable", "droppable", "sortable", "dropped",
    "ui-sortable", "ui-draggable", "ui-droppable", "form-body"], function (i, c) {
                    $copy.find("." + c).removeClass(c).removeAttr("style");
                })
				//alert($copy.html());
                var html = html_beautify($copy.html());
				//console.log(html);
                $copy.remove();
				$("#formShow").on("show.bs.modal",function(){
					$(this).find(".modal-body").find(".row").html(html);
				});
				$("#formShow").modal("show");
                //$modal = get_modal(html).modal("show");
//                $modal.find(".btn").remove();
//                $modal.find(".modal-title").html("复制HTML代码");
//                $modal.find(":input:first").select().focus();

                return false;
            });
            
            $("#savesort").on("click", function () {
            	var v = $("#n-columns").val();
            	var $copy = $(".form-body").clone().appendTo(document.body);
                $copy.find(".tools, div:hidden").remove();
                $.each(["draggable", "droppable", "sortable", "dropped","ui-sortable", "ui-draggable", "ui-droppable", "form-body"], function (i, c) {
                    $copy.find("." + c).removeClass(c).removeAttr("style");
                });
    			//alert($copy.html());
                var html = html_beautify($copy.html());
    			//alert(html);
                var str ='';
                var fg = 0;
                $("#formBox").find('div[name=ar]').each(function (ii,nn) {  
                	var obj = $(this);
                	if($(obj).find('div.controlDiv').length > 0){
                		fg=fg+1;
                		$(obj).find('div.controlDiv').each(function (i,n) {  
                            str += $(this).attr("vl2") + ','+i+ ','+fg+" ";  
                        });
                	}
                });
            	$.post("${ctx}/flow/flowtemplate/saveTemplatecontrolSort",
            			{
            		       "flowtemplateid":$("#flowtemplateid").val(),
            		       "sortstr":str,
            		       "showcolumn":v,
            		       "htmlstr":html
            		    },function(data){
    				var jsonData = jQuery.parseJSON(data);
    				if(jsonData.status == 'y'){
    					layer.msg(jsonData.info, {icon: 1});
    				}else{
    					layer.msg(jsonData.info, {icon: 2});
    				}
    			});
            	$copy.remove();
            });
			
		});
		
		function savesort(){
				var v = $("#n-columns").val();
	        	var $copy = $(".form-body").clone().appendTo(document.body);
	            $copy.find(".tools, div:hidden").remove();
	            $.each(["draggable", "droppable", "sortable", "dropped","ui-sortable", "ui-draggable", "ui-droppable", "form-body"], function (i, c) {
	                $copy.find("." + c).removeClass(c).removeAttr("style");
	            });
	            var html = html_beautify($copy.html());
				//alert($copy.html());
	            var str ='';
	            var fg = 0;
	            $("#formBox").find('div[name=ar]').each(function (ii,nn) {  
	            	var obj = $(this);
	            	if($(obj).find('div.controlDiv').length > 0){
	            		fg=fg+1;
	            		$(obj).find('div.controlDiv').each(function (i,n) {  
	                        str += $(this).attr("vl2") + ','+i+ ','+fg+" ";  
	                    });
	            	}
	            });
	            if(str != ''){
	            	$.post("${ctx}/flow/flowtemplate/saveTemplatecontrolSort",
		        			{
		        		       "flowtemplateid":$("#flowtemplateid").val(),
		        		       "sortstr":str,
		        		       "showcolumn":v,
		        		       "htmlstr":html
		        		    },function(data){
						var jsonData = jQuery.parseJSON(data);
						if(jsonData.status == 'y'){
							//layer.msg(jsonData.info, {icon: 1});
						}else{
							layer.msg(jsonData.info, {icon: 2});
						}
					});
		        	$copy.remove();
	            }
			
		}
		
		//打开控件值编辑弹框
		function selectValue(type,val){
			$("#editValue").on('show.bs.modal',function(){
				var title;
				switch (type){
					case "3":
						title = "编辑下拉控件的值";
						break;
					case "4":
						title = "编辑单选控件的值";
						break;
					case "5":
						title = "编辑下拉控件的值";
						
				}
				if(val =="" || val === undefined || val == null){
					$("#valueTable").find("tr:gt(0)").remove();
					$("#valueTable").find("tr").find("input").val("");
				}else{
					var str = val.split(",");
					var html = "";
					for(var i=0; i<str.length; i++){
						html += '<tr><td><input type="text" class="form-control" value="'+ str[i] +'" /></td>'
                           +'<td><a class="up" href="javascript:void(0)" onClick="moveUp(this)" title="上移"><i class="glyphicon glyphicon-arrow-up"></i></a>&nbsp;&nbsp;'
                           +'<a class="down" href="javascript:void(0)" onClick="moveDown(this)" title="下移"><i class="glyphicon glyphicon-arrow-down"></i></a>&nbsp;&nbsp;'
						   +'<a class="remove" href="javascript:void(0)" onClick="delValue(this)" title="删除"><i class="glyphicon glyphicon-remove"></i></a></td></tr>';
					}
					$("#valueTable").empty().html(html);
					firstLast();
				}
				$("#valueModalLabel").html(title);
				
			});
			$("#editValue").modal();
		}
		//删除控件值
		function delValue(_this){
			if($("#valueTable").find("tr").length > 1){
				$(_this).closest("tr").remove();
				firstLast();
			}else{
				parent.layer.alert('至少保留一条数据');
			}
		}
		//上移
		function moveUp(_this){
			var $item = $(_this).closest("tr");
			if($item.hasClass("first")){
				return false;
			}else{
				$item.prev("tr").before($item);
				firstLast();
			}
		}
		//下移
		function moveDown(_this){
			var $item = $(_this).closest("tr");
			if($item.hasClass("last")){
				return false;
			}else{
				$item.next("tr").after($item);
				firstLast();
			}
		}
		//重置
		function firstLast(){
			$("#valueTable").find("tr").each(function(i) {
				if(i == 0){
					$(this).addClass("first");
				}else {
					$(this).removeClass("first");
				}
				if(i == ($("#valueTable").find("tr").length - 1)){
					$(this).addClass("last");
				}else{
					$(this).removeClass("last");
				}
			});
		}
		
		
		function typeFormatter(value, row, index){
			var val;
			switch (value){
				case "1":
					val = "文本控件";
					break;
				case "2":
					val = "密码控件";
					break;
				case "3":
					val = "下拉控件";
					break;
				case "4":
					val = "单选控件";
					break;
				case "5":
					val = "多选控件";
					break;
				case "6":
					val = "时间控件";
					break;
				case "7":
					val = "文本域控件";
					break;
				case "8":
					val = "上传控件";
					break;
				case "9":
					val = "纯文本控件";
					break;
			}
			return val;
		}
    	function operateFormatter(value, row, index) {
			return [
				'<a class="edit" href="javascript:void(0)" title="编辑">',
				'<i class="glyphicon glyphicon-pencil"></i>',
				'</a>&nbsp;&nbsp;&nbsp;&nbsp;',
				'<a class="remove" href="javascript:void(0)" title="删除">',
				'<i class="glyphicon glyphicon-remove"></i>',
				'</a>'
			].join('');
		}

		function clearInput(){
		    $("#valuerequire").val(1);
		}
		
		function getIdSelections() {
			return $.map($table.bootstrapTable('getSelections'), function (row) {
				return row.id
			});
		}
		function responseHandler(res) {
			console.log(res);
			//$.each(res.rows, function (i, row) {
//				row.state = $.inArray(row.id, selections) !== -1;
//			});
			return res;
		}
		
		window.operateEvents = {
			'click .edit': function (e, value, row, index) {
				alert('你点击的行信息: ' + JSON.stringify(row));
			},
			'click .remove': function (e, value, row, index) {
				parent.layer.confirm('确定删除此流程？', {
					btn: ['确定','取消'], //按钮
				}, function(){
					$table.bootstrapTable('remove', {
						field: 'id',
						values: [row.id]
					});
					parent.layer.closeAll();
				}, function(){
					return;
				});			
				
			}
		};
		
		
		var setup_draggable = function () {
            $(".draggable").draggable({
                appendTo: "body",
                helper: "clone"
            });
            $(".droppable").droppable({
                accept: ".draggable",
                helper: "clone",
                hoverClass: "droppable-active",
                drop: function (event, ui) {
                    $(".empty-form").remove();
                    var $orig = $(ui.draggable)
                    if (!$(ui.draggable).hasClass("dropped")) {
                        var $el = $orig
                            .clone()
                            .addClass("dropped")
                            .css({
                                "position": "static",
                                "left": null,
                                "right": null
                            })
                            .appendTo(this);

                        // update id
                        var id = $orig.find(":input").attr("id");

                        if (id) {
                            id = id.split("-").slice(0, -1).join("-") + "-" + (parseInt(id.split("-").slice(-1)[0]) + 1)

                            $orig.find(":input").attr("id", id);
                            $orig.find("label").attr("for", id);
                        }

                        // tools
                        /* $('<p class="tools col-sm-12 col-sm-offset-3">\
						<a class="edit-link">编辑<a> | \
						<a class="remove-link">移除</a></p>').appendTo($el); */
                    } else {
                        if ($(this)[0] != $orig.parent()[0]) {
                            var $el = $orig
                                .clone()
                                .css({
                                    "position": "static",
                                    "left": null,
                                    "right": null
                                })
                                .appendTo(this);
                            $orig.remove();
                        }
                    }
                }
            }).sortable();

        }

        var get_modal = function (content, type) {
			//return;
			var multiSelect = "";
			var placeholder = (type=="7")?content.find("textarea").attr("placeholder"):content.find("input").eq(0).attr("placeholder");
			if(type == "3"){
				content.find("option").each(function(i){
					if(i != content.find("option").length - 1){
						multiSelect += $.trim($(this).text()) + ","; 
					}else{
						multiSelect += $.trim($(this).text());
					}
				});
			}else if( type == "4"){
				content.find("input[type=radio]").each(function(i){
					if(i != content.find("input[type=radio]").length - 1){
						multiSelect += $.trim($(this).parent("label").text()) + ",";
					}else{
						multiSelect += $.trim($(this).parent("label").text());
					}
				});
			}else if( type == "5"){
				content.find("input[type=checkbox]").each(function(i){
					if(i != content.find("input[type=checkbox]").length - 1){
						multiSelect += $.trim($(this).parent("label").text()) + ",";
					}else{
						multiSelect += $.trim($(this).parent("label").text());
					}
				});
			}
            var modal = $('<div class="modal" style="overflow: auto;" tabindex="-1">\
			<div class="modal-dialog">\
				<div class="modal-content">\
					<div class="modal-header">\
						<a type="button" class="close"\
							data-dismiss="modal" aria-hidden="true">&times;</a>\
						<h4 class="modal-title">编辑</h4>\
					</div>\
					<div class="modal-body ui-front">\
						<form class="form-horizontal m-t">\
							<div class="form-group">\
								<label class="col-sm-3 control-label">字段名称：</label\>\
								<div class="col-sm-8">\
									<input name="name" type="text" class="form-control" value="'+ (content.find("label").eq(0).text()).replace(new RegExp(/(：)/g),"") +'" />\
								</div>\
							</div>\
							<div class="form-group">\
								<label class="col-sm-3 control-label">提示文字：</label>\
								<div class="col-sm-8">\
									<input name="tips" type="text" class="form-control" value="'+ placeholder +'" />\
								</div>\
							</div>\
							<div class="form-group" style="display:none;">\
								<label class="col-sm-3 control-label">说明文字：</label>\
								<div class="col-sm-8">\
									<input name="info" type="text" class="form-control" value="'+ content.find("span").text() +'" />\
								</div>\
							</div>\
							<div class="form-group" style="display:none;">\
								<label class="col-sm-3 control-label">选择框值：</label>\
								<div class="col-sm-8">\
									<div class="input-group">\
										<input id="checkboxVal" name="value" type="text" class="form-control" readonly value="'+ multiSelect +'" />\
										<div class="input-group-btn">\
											<button data-toggle="dropdown" class="btn btn-white dropdown-toggle" type="button">操作 <span class="caret"></span>\
											</button>\
											<ul class="dropdown-menu pull-right">\
												<li><a href="javascript:" onclick="selectValue($(this).closest(&quot;.input-group-btn&quot;).prev(&quot;input&quot;).attr(&quot;vl&quot;),$(this).closest(&quot;.input-group-btn&quot;).prev(&quot;input&quot;).val())">编辑</a>\
												</li>\
											</ul>\
										</div>\
									</div>\
								</div>\
							</div>\
						</form>\
					</div>\
					<div class="modal-footer">\
						<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>\
						<button type="button" class="btn btn-success">更新表单元素</button>\
					</div>\
				</div>\
			</div>\
			</div>').prependTo(document.body);

            return modal;
        };

        /* $(document).on("click", ".edit-link", function (ev) {
            var $el = $(this).parent().parent();
            var $el_copy = $el.clone();
            var $edit_btn = $el_copy.find(".edit-link").parent().remove();
			
            //var $modal = get_modal(html_beautify($el_copy.html())).modal("show");
			
			var $modal = get_modal($el_copy, $el.attr("vl")).on("show.bs.modal",function(){
				if($el.attr("vl") == "1"){
					$(this).find(".form-group").eq(2).show();
				}else if($el.attr("vl") == "3" || $el.attr("vl") == "4" || $el.attr("vl") == "5"){
					$(this).find(".form-group").eq(1).hide();
					$(this).find(".form-group").eq(3).show();
					$(this).find(".form-group").eq(3).find("input").attr("vl",$el.attr("vl"));
				}else if($el.attr("vl") == "9"){
					$(this).find(".form-group").eq(1).find("input").val($el_copy.find("p").text());
				}else if($el.attr("vl") == "8"){
					$(this).find(".form-group").eq(1).hide();
				}
			}).modal("show");
			
            $modal.find(":input:first").focus();
            $modal.find(".btn-success").click(function (ev2) {
				$el.find("label").eq(0).html($modal.find("input[type=text]").eq(0).val() + "：");
				if($el.attr("vl") == "1"){
					$el.find("input").attr("placeholder",$modal.find("input").eq(1).val());
					$el.find("span").html($modal.find("input").eq(2).val());
				}else if($el.attr("vl") == "2"){
					$el.find("input").attr("placeholder",$modal.find("input").eq(1).val());
				}else if($el.attr("vl") == "3" || $el.attr("vl") == "4" || $el.attr("vl") == "5"){
					var itemStr = $modal.find("input").eq(3).val();
					var itemArr = itemStr.split(",");
					if($el.attr("vl") == "3"){
						var selectHtml = "";
						for(var i=0; i<itemArr.length; i++){
							selectHtml += '<option>'+ itemArr[i] +'</option>'
						}
						$el.find("select").html(selectHtml);
					}
					if($el.attr("vl") == "4"){
						var selectHtml = "";
						for(var i=0; i<itemArr.length; i++){
							selectHtml += '<label class="radio-inline"><input type="radio" value="option'+ i +'" id="optionsRadios'+ i +'" name="optionsRadios">'+ itemArr[i] +'</label>';
						}
						$el.find("div.col-sm-9").html(selectHtml);
					}
					if($el.attr("vl") == "5"){
						var selectHtml = "";
						for(var i=0; i<itemArr.length; i++){
							selectHtml += '<label class="checkbox-inline"><input type="checkbox" value="option'+ i +'" id="inlineCheckbox'+ i +'">'+ itemArr[i] +'</label>';
						}
						$el.find("div.col-sm-9").html(selectHtml);
					}
				}else if($el.attr("vl") == "9"){
					$el.find("p").eq(0).html($modal.find("input").eq(1).val());
				}else if($el.attr("vl") == "6"){
					$el.find("input").attr("placeholder",$modal.find("input").eq(1).val());
				}else if($el.attr("vl") == "7"){
					$el.find("textarea").attr("placeholder",$modal.find("input").eq(1).val());
				}
                //var html = $modal.find("textarea").val();
//                if (!html) {
//                    $el.remove();
//                } else {
//                    $el.html(html);
//                    $edit_btn.appendTo($el);
//                }
                $modal.modal("hide");
                return false;
            })
        }); */

        /* $(document).on("click", ".remove-link", function (ev) {
            $(this).parent().parent().remove();
        }); */
        
        function loaddata(){
		    var url = "${ctx}/flow/flowtemplate/getTemplatecontrolList?id="+$("#flowtemplateid").val()
		    $('#processTable').bootstrapTable({
				url : url,//url默认走的是get
				method : 'post',
				striped : true,
				dataType: 'json',
				pagination : true,
				pageList : [ 10, 15, 20 ],
				pageSize : 10,
				pageNumber : 1,
				queryParamsType: "",//这里只是选择适合我后台的逻辑，可以选择传入页数和显示数量
				//queryParams : queryParams,
				//sidePagination : 'server',//设置为服务器端分页
				columns : [ {
					field : 'id',//返回数据对应的字段
					title : '序号'
				}, {
					field : 'columnname',
					title : '字段名称'
				}/* , {
					field : 'columnid',
					title : '所属字段'
				} */, {
					field : 'columntypename',
					title : '类型'
				}, {
					field : 'operate',
					title : '操作',
					events: operateEvents,
                    formatter: operateFormatter
				} ]
			});
		}
		function queryParams(params) {
			return {
				pageSize : params.pageSize,//键就是自己后台的参数
				page : params.pageNumber
			};
		}
		function operateFormatter(value, row, index) {
	        return [
	            '<a class="edit" href="javascript:void(0)" title="修改">',
	            '<i class="glyphicon glyphicon-pencil"></i>',
	            '</a>  ',
	            '<a class="remove" href="javascript:void(0)" title="删除">',
	            '<i class="glyphicon glyphicon-remove"></i>',
	            '</a>'
	        ].join('');
	    }

	    window.operateEvents = {
	        'click .edit': function (e, value, row, index) {
	            //alert('You click like action, row: ' + JSON.stringify(row));
	            //alert(row.id);
	            $("#controlid").val(row.id);
	            $("#columnname").val(row.columnname);
	            $("#columnid").val(row.columnid);
	            $("#columntype").val(row.columntype);
	            $("#columnvalue").val(row.columnvalue);
                $("#valuerequire").val(row.valuerequire);
	            if(row.columntype == "5" || row.columntype == "6" || row.columntype == "7" ){
	            	$("#columntype").closest(".form-group").next(".form-group").slideDown();
	            	$("#checkboxVal").val(row.columnvalue);
	            	//$("#columntype").closest(".form-group").next(".form-group").find("input[type=text]").attr("vl",typeN);
				}else{
					$("#columntype").closest(".form-group").next(".form-group").slideUp();
					$("#checkboxVal").val("");
					//$("#columntype").closest(".form-group").next(".form-group").find("input[type=text]").attr("vl","");
				}
                if(row.columntype == "4" || row.columntype == "5" || row.columntype == "6"|| row.columntype == "10" ){
                    $("#columntype").closest(".form-group").next(".form-group").next(".form-group").slideUp();
                    $("#valuerequire").val("1");
                }else{
                    $("#columntype").closest(".form-group").next(".form-group").next(".form-group").slideDown();
                    $("#valuerequire").val(row.valuerequire);
                }
	            $("#addField").modal();	
	        },
	        'click .remove': function (e, value, row, index) {
	        	
	        	$.post("${ctx}/flow/flowtemplate/deleteTemplatecontrol",
            			{
            		       "flowtemplateid":$("#flowtemplateid").val(),
            		       "templateidcontrolid":row.id
            		    },function(data){
					var jsonData = jQuery.parseJSON(data);
					if(jsonData.status == 'y'){
						layer.confirm(jsonData.info, {
							  btn: ['去设置模板','关闭'] //按钮
							}, function(){
								location.href='${ctx}/flow/flowtemplate/form?id='+$("#flowtemplateid").val()+'&tab=2';
							}, function(){
								$table.bootstrapTable('remove', {
					                field: 'id',
					                values: [row.id]
					            });
						});
					}else{
						layer.msg(jsonData.info, {icon: 2});
					}
				});
	            
	        }
	    };
	    
	    function loadhtml(){
	    	$.post("${ctx}/flow/flowtemplate/getTemplatecontrolList",{
				"id":$("#flowtemplateid").val()
		    },function(data){
				var jsonData = jQuery.parseJSON(data);
				console.log(jsonData);
				var html = "";
				for(var i=0; i<jsonData.length; i++){
					switch(jsonData[i].columntype){
						case "0":
						html += '<div  vl2="'+ jsonData[i].id +'" class="form-group draggable dropped controlDiv" vl="1">'
								+'<label class="col-sm-3 control-label">'+ jsonData[i].columnname +'：</label><div class="col-sm-9">'
								+'<input type="text" name="'+ jsonData[i].columnid +'" id="'+ jsonData[i].columnid +'" class="form-control" placeholder="请输入'+ jsonData[i].columnname +'" maxlength="1000"></div></div>';
								break;
						case "1":
						html += '<div  vl2="'+ jsonData[i].id +'" class="form-group draggable dropped controlDiv" vl="2">'
								+'<label class="col-sm-3 control-label">'+ jsonData[i].columnname +'：</label><div class="col-sm-9">'
								+'<input type="password" class="form-control" name="'+ jsonData[i].columnid +'" id="'+ jsonData[i].columnid +'" placeholder="请输入'+ jsonData[i].columnname +'"></div></div>';
								break;
						case "2":
						html += '<div  vl2="'+ jsonData[i].id +'" class="form-group draggable dropped controlDiv" vl="6">'
								+'<label class="col-sm-3 control-label">'+ jsonData[i].columnname +'：</label><div class="col-sm-9">'
								+'<input class="laydate-icon form-control layer-date" name="'+ jsonData[i].columnid +'" id="'+ jsonData[i].columnid +'" placeholder="请选择'+ jsonData[i].columnname +'" onclick="WdatePicker({dateFmt:\'yyyy-MM-dd HH:mm:ss\'});"></div></div>';
								break;
						case "8":
							html += '<div  vl2="'+ jsonData[i].id +'" class="form-group draggable dropped controlDiv" vl="9">'
									+'<label class="col-sm-3 control-label">'+ jsonData[i].columnname +'：</label><div class="col-sm-9">'
									+'<input class="laydate-icon form-control layer-date" name="'+ jsonData[i].columnid +'" id="'+ jsonData[i].columnid +'" placeholder="请选择'+ jsonData[i].columnname +'" onclick="WdatePicker({dateFmt:\'yyyy-MM-dd\'});"></div></div>';
									break;
						case "3":
						html += '<div  vl2="'+ jsonData[i].id +'" class="form-group draggable dropped controlDiv" vl="7">'
								+'<label class="col-sm-3 control-label">'+ jsonData[i].columnname +'：</label><div class="col-sm-9">'
								+'<textarea class="form-control" name="'+ jsonData[i].columnid +'" id="'+ jsonData[i].columnid +'" placeholder="请输入'+ jsonData[i].columnname +'" maxlength="1000"></textarea></div></div>';
								break;
						case "4":
						html += '<div  vl2="'+ jsonData[i].id +'" class="form-group draggable dropped controlDiv" vl="8">'
								+'<label class="col-sm-3 control-label" >'+ jsonData[i].columnname +'：</label><div class="col-sm-9">'
								+'<input id="files" name="files" maxlength="255" class="form-control" type="hidden" value=""><ol id="filesPreview"><li style="list-style:none;padding-top:5px;">无</li></ol><a href="javascript:" onclick="filesFinderOpen(this);" class="btn btn-primary">添加</a>&nbsp;<a href="javascript:" onclick="filesDelAll(this);" class="btn btn-default">清除</a></div></div>'
								break;
						case "5":
						var optionArr = "";
						var selectVal = jsonData[i].columnvalue;
						var selectValArr = selectVal.split(",");
						for(var j=0; j<selectValArr.length; j++){
							optionArr += '<option>'+ selectValArr[j] +'</option>';
						}
						html += '<div  vl2="'+ jsonData[i].id +'" class="form-group draggable dropped controlDiv" vl="3">'
								+'<label class="col-sm-3 control-label">'+ jsonData[i].columnname +'：</label><div class="col-sm-9">'
								+'<select class="form-control" name="'+ jsonData[i].columnid +'" id="'+ jsonData[i].columnid +'">'+ optionArr +'</select></div></div>';
								break;
						
						case "6":
						var radioArr = "";
						var radioVal = jsonData[i].columnvalue;
						var radioValArr = radioVal.split(",");
						for(var j=0; j<radioValArr.length; j++){
							if(j == 0){
								radioArr += '<label class="radio-inline"><input type="radio" value="'+ radioValArr[j] +'" checked="" name="'+ jsonData[i].columnid +'">'+ radioValArr[j] +'</label>'
							}else{
								radioArr += '<label class="radio-inline"><input type="radio" value="'+ radioValArr[j] +'" name="'+ jsonData[i].columnid +'">'+ radioValArr[j] +'</label>'
							}
						}
						html += '<div  vl2="'+ jsonData[i].id +'" class="form-group draggable dropped controlDiv" vl="4">'
								+'<label class="col-sm-3 control-label">'+ jsonData[i].columnname +'：</label><div class="col-sm-9">'+ radioArr +'</div></div>';
								break;
						case "7":
						var checkArr = "";
						var checkVal = jsonData[i].columnvalue;
						var checkValArr = checkVal.split(",");
						for(var j=0; j<checkValArr.length; j++){
							checkArr += '<label class="checkbox-inline"><input type="checkbox" value="'+ checkValArr[j] +'" name="'+ jsonData[i].columnid +'">'+ checkValArr[j] +'</label>'
						}
						html += '<div  vl2="'+ jsonData[i].id +'" class="form-group draggable dropped controlDiv" vl="5">'
								+'<label class="col-sm-3 control-label">'+ jsonData[i].columnname +'：</label><div class="col-sm-9">'+ checkArr +'</div></div>';
								break;			
					}
						
				}
				
				$("#formBox").find(".col-md-12").html(html);
				
				
				var v = "${flowtemplate.showcolumn}";
                if (v === "1") {
                    var $col = $("#formBox").find(".col-md-12").toggle(true);
                    $("#formBox").find('.col-md-6 .draggable').each(function (i, el) {
                        $(this).remove().appendTo($col);
                    })
					$("#formBox").find(".col-md-4 .draggable").each(function (i, el) {
                        $(this).remove().appendTo($col);
                    });
                    $("#formBox").find('.col-md-6').toggle(false);
                    $("#formBox").find('.col-md-4').toggle(false);
                } else if (v === "2") {
                    var $col = $("#formBox").find('.col-md-6').toggle(true);
                    $("#formBox").find(".col-md-12 .draggable").each(function (i, el) {
                        $(this).remove().appendTo(i % 2 ? $col[1] : $col[0]);
                    });
                    $("#formBox").find(".col-md-4 .draggable").each(function (i, el) {
                        $(this).remove().appendTo(i % 2 ? $col[1] : $col[0]);
                    });
                    $("#formBox").find('.col-md-12').toggle(false);
                    $("#formBox").find('.col-md-4').toggle(false);
                }else{
					var $col = $("#formBox").find('.col-md-4').toggle(true);
					$("#formBox").find(".col-md-12 .draggable").each(function (i, el) {
						var icol;
						switch (i % 3){
							case 0:
								icol = $col[0];
								break;
							case 1:
								icol = $col[1];
								break;
							case 2:
								icol = $col[2];
						}
                        $(this).remove().appendTo(icol);
                    });
					$("#formBox").find(".col-md-6 .draggable").each(function (i, el) {
                        var icol;
						switch (i % 3){
							case 0:
								icol = $col[0];
								break;
							case 1:
								icol = $col[1];
								break;
							case 2:
								icol = $col[2];
						}
                        $(this).remove().appendTo(icol);
                    });
					$("#formBox").find('.col-md-12').toggle(false);
					$("#formBox").find(".col-md-6").toggle(false);
				}
				
			});
	    }
    </script>

</body>

</html>