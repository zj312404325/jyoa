<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>流程申请</title>
	<meta name="decorator" content="default"/>
	<link rel="stylesheet" type="text/css" href="${ctxStatic}/bootstrapValidator/bootstrapValidator.min.css">
	<style>
		.has-feedback label~.form-control-feedback{ top:0;}
	</style>
    <script type="text/javascript" src="${ctxStatic}/bootstrapValidator/bootstrapValidator.min.js"></script>
	<script type="text/javascript">
		/* var validateForm;
		function doSubmit(){//回调函数，在编辑和保存动作时，供openDialog调用提交表单。
		  if(validateForm.form()){
			  $("#inputForm").submit();
			  return true;
		  }
	
		  return false;
		} */
		
		$(document).ready(function() {
			$('#inputForm').find('.layer-date').each(function(){
				$(this).datetimepicker({ 
					format: 'YYYY-MM-DD HH:mm:ss',  
			        locale: moment.locale('zh-cn')
					}).on('dp.hide',function(e) { 
						
					$('#inputForm').data('bootstrapValidator').updateStatus($(this).attr("name"), 'NOT_VALIDATED',null).validateField($(this).attr("name")); 
					});
			});
			
			$("#btnSubmit3").click(function(){
				var bootstrapValidator = $("#inputForm").data('bootstrapValidator');
				bootstrapValidator.validate();
				if(bootstrapValidator.isValid()){
					$('#flag').val('no');
					$("#comment").val('申请撤销');
					
					//拼装templatecontent
					var detailJsonArray = [];
					var controls='${controls}';
					var jsonData = jQuery.parseJSON(controls);
					//var validfield="";
					$.each(jsonData.lst1, function(i, n) {
						var detailJSON = '{';
						detailJSON = detailJSON + '"controlid":"'+ n.id+'",';
						detailJSON = detailJSON + '"columnname":"'+ n.columnname+'",';
						if(n.columntype=='4'){//上传空件
							detailJSON = detailJSON + '"columnvalue":"'+$("#"+n.columnid).val()+'",';
						}else if(n.columntype=='6'){//单选
							//console.log("================================="+n.columnid);
							detailJSON = detailJSON + '"columnvalue":"'+$('input:radio[name='+n.columnid+']:checked').val()+'",';
						}else if(n.columntype=='7'){//多选
							var chk_value =[]; 
							//console.log("================================="+n.columnid);
							$('input[name='+n.columnid+']:checked').each(function(){ 
								chk_value.push($(this).val()); 
							}); 
							detailJSON = detailJSON + '"columnvalue":"'+chk_value.join(',')+'",';
						}else{
							detailJSON = detailJSON + '"columnvalue":"'+$("#"+n.columnid).val()+'",';
						}
						detailJSON = detailJSON + '"columntype":"'+n.columntype+'",';
						detailJSON = detailJSON + '"columnlocate":"'+n.columnlocate+'",';
						detailJSON = detailJSON + '"columnsort":"'+n.columnsort+'"}';
						//validfield+=n.columnid+':'+'{validators: {notEmpty: {message: "请填写'+n.columnname+'名称！"}}},';
						detailJsonArray.push(detailJSON);
					});
					
					$("#detailJsonArray").val('['+detailJsonArray+']');
					$("#btnSubmit2").click();
				} 
			});
			
			$("#btnSubmit1").click(function(){
				var bootstrapValidator = $("#inputForm").data('bootstrapValidator');
				bootstrapValidator.validate();
				if(bootstrapValidator.isValid()){
					$('#flag').val('yes');
					
					//拼装templatecontent
					var detailJsonArray = [];
					var controls='${controls}';
					var jsonData = jQuery.parseJSON(controls);
					//var validfield="";
					$.each(jsonData.lst1, function(i, n) {
						var detailJSON = '{';
						detailJSON = detailJSON + '"controlid":"'+ n.id+'",';
						detailJSON = detailJSON + '"columnname":"'+ n.columnname+'",';
						if(n.columntype=='4'){//上传空件
							detailJSON = detailJSON + '"columnvalue":"'+$("#"+n.columnid).val()+'",';
						}else if(n.columntype=='6'){//单选
							//console.log("================================="+n.columnid);
							detailJSON = detailJSON + '"columnvalue":"'+$('input:radio[name='+n.columnid+']:checked').val()+'",';
						}else if(n.columntype=='7'){//多选
							var chk_value =[]; 
							//console.log("================================="+n.columnid);
							$('input[name='+n.columnid+']:checked').each(function(){ 
								chk_value.push($(this).val()); 
							}); 
							detailJSON = detailJSON + '"columnvalue":"'+chk_value.join(',')+'",';
						}else{
							detailJSON = detailJSON + '"columnvalue":"'+$("#"+n.columnid).val()+'",';
						}
						detailJSON = detailJSON + '"columntype":"'+n.columntype+'",';
						detailJSON = detailJSON + '"columnlocate":"'+n.columnlocate+'",';
						detailJSON = detailJSON + '"columnsort":"'+n.columnsort+'"}';
						//validfield+=n.columnid+':'+'{validators: {notEmpty: {message: "请填写'+n.columnname+'名称！"}}},';
						detailJsonArray.push(detailJSON);
					});
					
					$("#detailJsonArray").val('['+detailJsonArray+']');
					$("#btnSubmit2").click();
				} 
			});
			
			
			//验证
			$('#inputForm').bootstrapValidator({
				feedbackIcons: {
					valid: 'glyphicon glyphicon-ok',
					invalid: 'glyphicon glyphicon-remove',
					validating: 'glyphicon glyphicon-refresh'
				},
				fields: {
					//validfield
					${validfield}
				}
			}); 
			
			
			
			
			var flowapply = jQuery.parseJSON('${flowapply1}');
			$.each(flowapply,function(i,n){
				if(n.columntype==0){
					$("#"+n.controlid).val(n.columnvalue);
				}else if(n.columntype==1){
					$("#"+n.controlid).val(n.columnvalue);
				}else if(n.columntype==2){
					$("#"+n.controlid).val(n.columnvalue);
				}else if(n.columntype==3){
					$("#"+n.controlid).val(n.columnvalue);
				}else if(n.columntype==4){
					if(n.tmplatefile!=null){
						var html1='';
						html1+='<input id="'+n.controlid+'" name="'+n.controlid+'" type="hidden" value="'+n.columnvalue+'">';
						$.each(n.tmplatefile,function(j,m){
							html1+='<li>';
							html1+='<a vl="'+m.url+'" url="'+m.url+'" target="_blank" onclick="fileDownLoads(this)" >'+m.filename+'</a>&nbsp;&nbsp;<a href="javascript:" onclick="filesDel(this);">×</a>';
							html1+='</li>';
						});
						$('#filesPreview'+n.controlid).html(html1);
					}
				}else if(n.columntype==5){
					$("#"+n.controlid).val(n.columnvalue);
				}else if(n.columntype==6){
					$("input[type=radio][name="+n.controlid+"][value="+n.columnvalue+"]").attr("checked","checked");
				}else if(n.columntype==7){
					var array=[];
					array=n.columnvalue.split(',');
					$.each(array,function(i,k){
						$("input[type=checkbox][name="+n.controlid+"][value="+k+"]").attr("checked","checked");
					});
				}
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
	<form id="inputForm" action="${ctx}/flow/flowapply/saveAudit" method="post" class="form-horizontal">
	<input id="id" name="id" type="hidden" value="${flowapply.id }" />
	<input id="taskId" name="taskId" type="hidden" value="${flowapply.act.taskId }" />
	<input id="taskName" name="taskName" type="hidden" value="${flowapply.act.taskName }" />
	<input id="taskDefKey" name="taskDefKey" type="hidden" value="${flowapply.act.taskDefKey }" />
	<input id="procInsId" name="procInsId" type="hidden" value="${flowapply.act.procInsId }" />
	<input id="procDefId" name="procDefId" type="hidden" value="${flowapply.act.procDefId }" />
	<input id="flag" name="flag" type="hidden" value="${flowapply.act.flag }" />
	<input id="detailJsonArray" type="hidden" name="detailJsonArray"/>
	<input id="comment" type="hidden" name="comment" value="调整申请"/>
	<sys:message content="${message}"/>
		<div class="row">
		${flowapply.templatehtml}
		</div>
	
		<div class="form-actions">
				<c:if test="${flowapply.act.taskDefKey ne 'apply_end'}">
					<input id="btnSubmit1" class="btn btn-primary" type="button" value="提 交"/>&nbsp;
					<!-- <input id="btnSubmit" class="btn btn-inverse" type="submit" value="驳 回" onclick="$('#flag').val('no')"/>&nbsp; -->
					<input id="btnSubmit3" class="btn btn-inverse" type="button" value="撤 销"/>&nbsp;
					<input id="btnSubmit2" class="btn btn-primary" type="submit" style="display:none;"/>
				</c:if>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
		
		
	
	<act:flowChart procInsId="${flowapply.act.procInsId}"/>
	<act:histoicFlow procInsId="${flowapply.act.procInsId}"/>
	</form>
	
</div>
	</div>
	</div>
	
</body>
</html>

