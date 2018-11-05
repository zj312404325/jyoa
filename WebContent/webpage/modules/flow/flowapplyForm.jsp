<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>提交流程申请管理</title>
	<meta name="decorator" content="default"/>
	<link rel="stylesheet" type="text/css" href="${ctxStatic}/bootstrapValidator/bootstrapValidator.min.css">
	<style>
		.has-feedback label~.form-control-feedback{ top:0;}
	</style>
    <script type="text/javascript" src="${ctxStatic}/bootstrapValidator/bootstrapValidator.min.js"></script>
	<script type="text/javascript">
		/* function checkLayDate(_this){
			var name = $(_this).attr('name');
			$('#commentForm').data('bootstrapValidator').updateStatus(name, 'NOT_VALIDATED').validateField(name);
		} */	
		alert(1);
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
					//validfield
					${validfield}
				}
			});
			
			$('#commentForm').find('.layer-date').each(function(){
				
				$(this).datetimepicker({ 
					format: 'YYYY-MM-DD HH:mm:ss',  
			        locale: moment.locale('zh-cn'),
			        showClose: true
					}).on('dp.hide',function(e) { 
					$('#commentForm').data('bootstrapValidator').updateStatus($(this).attr("name"), 'NOT_VALIDATED',null).validateField($(this).attr("name")); 
					});
				
			});
			
			$("#saveTempleteContent").click(function(){
				var bootstrapValidator = $("#commentForm").data('bootstrapValidator');
				bootstrapValidator.validate();
				if(bootstrapValidator.isValid()){
					//验证通过
					var detailJsonArray = [];
					var controls='${controls}';
					var jsonData = jQuery.parseJSON(controls);
					//var validfield="";
					$.each(jsonData.lst, function(i, n) {
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
						//console.log(detailJSON);
						detailJsonArray.push(detailJSON);
					});
					$.post("${ctx}/flow/flowapply/save",
							{
						     "flowtemplateid":$("#flowtemplateid").val(),
						     "processkey":$("#processkey").val(),
						     "templatehtmlArea":$("#templatehtmlArea").html(),
						     "detailJsonArray":'['+detailJsonArray+']'
						    },function(data){
						var jsonData = jQuery.parseJSON(data);
						if(jsonData.status == 'y'){
							layer.msg(jsonData.info, {icon: 1});
							window.location.href="${ctx}/act/task/process/";
							/* $(".nav-tabs").find("li").removeClass("disabled");
							$("#flowtemplateid").val(jsonData.flowtemplateid);
							
							$('#processTable').attr("data-ajax","${ctx}/flow/flowtemplate/getTemplatecontrolList?id="+jsonData.flowtemplateid);
							$('#processTable').bootstrapTable(); */
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
			var delFlag = $(prefix+"_del");
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
<body class="hideScroll gray-bg  pace-done">
<div class="wrapper wrapper-content">
	<div class="ibox">
		<div class="ibox-title">
			<h5>请假申请 </h5>
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
		<form id="commentForm" action="${ctx}/flow/flowapply/save" method="post" class="form-horizontal">
		<input type="hidden" id="flowtemplateid" name="flowtemplateid" value="${flowtemplate.id }" />
		<input type="hidden" id="processkey" name="processkey" value="${processkey }" />
		<sys:message content="${message}"/>	
			<div class="row" id="templatehtmlArea">
			
			${flowtemplate.templatehtml }
			</div>
			<div class="form-actions">
				<input class="btn btn-primary" type="button" id="saveTempleteContent" name="saveTempleteContent" value="保 存">&nbsp;
				<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)">
			</div>
			</form>
		</div>
	</div>
</div>

</body>
</html>