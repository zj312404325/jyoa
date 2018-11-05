<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>传阅分组管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		/* var validateForm;
		function doSubmit(){//回调函数，在编辑和保存动作时，供openDialog调用提交表单。
		  if(validateForm.form()){
			  $("#inputForm").submit();
			  return true;
		  }
	
		  return false;
		} */
		function sub(index){
			if($.trim($("#groupname").val())==''){
				layer.msg("请填写组名！", {icon: 2});
				return false;
			}
			if($.trim($("#oagroupId").val())==''){
				layer.msg("组员不能为空！", {icon: 2});
				return false;
			}
			//var bootstrapValidator = $("#commentForm22").data('bootstrapValidator');
			//bootstrapValidator.validate();
			//if(bootstrapValidator.isValid()){
				$.post("${ctx}/oa/oaNotify/saveOaGroup",
						{
					     "groupname":$("#groupname").val(),
					     "oagroupId":$("#oagroupId").val(),
					     "oagroupName":$("#oagroupName").val()
					    },function(data){
					var jsonData = jQuery.parseJSON(data);
					if(jsonData.status == 'y'){
						window.parent.layer.msg(jsonData.info, {icon: 1});
						window.parent.$("#typeID").append('<li class="v3s" id="'+$("#oagroupId").val()+'" vl="'+$("#oagroupName").val()+'">'+$("#groupname").val()+'<b class="glyphicon glyphicon-remove" vl="'+jsonData.id+'"></b></li>');
						window.parent.$("#select01").jQSelect({ id: "typeIDVal",vl: "typeIDVl" });
						window.parent.layer.close(index);
						 
					}else{
						layer.msg(jsonData.info, {icon: 2});
					}
				});
			//}
		}
		$(document).ready(function() {
			/* validateForm = $("#inputForm").validate({
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
			 */
			
			if("${ids}" != ''){
				var idsArr = "${ids}".split(",");
				var namesArr = "${names}".split(",");
				var htmlStr = '';
				
				$.each(idsArr, function(i,val){
				    htmlStr+='<tr name="recordtr" vl1="'+idsArr[i]+'" vl2="'+namesArr[i]+'"><td>'+namesArr[i]+'</td><td><a class="remove" name="delrecord" href="javascript:void(0)" title="删除"><i class="glyphicon glyphicon-remove"></i></a></td></tr>';
				});
				$("#recordArea").html(htmlStr);
			}
			 
			/* //验证
			$('#commentForm22').bootstrapValidator({
				feedbackIcons: {
					valid: 'glyphicon glyphicon-ok',
					invalid: 'glyphicon glyphicon-remove',
					validating: 'glyphicon glyphicon-refresh'
				},
				fields: {
					groupname: {
						validators: {
							notEmpty: {
								message: '请填写组名！'
							}
						}
					}
				}
			}); */
			
			 
			 $("a[name=delrecord]").live("click",function(){
					$(this).parent().parent().remove();
					resetRecord();
				});
			 
		});
		//新添人员后回调
		function callback(){
			
			if($("#oagroupId").val() != ''){
				var idsArr = $("#oagroupId").val().split(",");
				var namesArr = $("#oagroupName").val().split(",");
				var htmlStr = '';
				
				$.each(idsArr, function(i,val){
				    htmlStr+='<tr name="recordtr" vl1="'+idsArr[i]+'" vl2="'+namesArr[i]+'"><td>'+namesArr[i]+'</td><td><a class="remove" name="delrecord" href="javascript:void(0)" title="删除"><i class="glyphicon glyphicon-remove"></i></a></td></tr>';
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
			$("#oagroupId").val(ids.join(",").replace(/u_/ig,""));
			$("#oagroupName").val(names.join(","));
		}
		
		
	</script>
</head>
<body class="hideScroll">
<form class="form-horizontal m-t" id="commentForm22">
		<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
		   <tbody>
		      <tr>
		         <td  class="width-15 active">	<label class="pull-right"><font color="red">*</font>组名：</label></td>
		         <td class="width-35" ><input type="text" id="groupname" name="groupname" htmlEscape="false" maxlength="200" class="form-control"/></td>
		      </tr>
		   </tbody>
		</table>
		<table id="contentTable" class="table table-striped table-bordered table-hover table-condensed dataTables-example dataTable">
			<span class="pull-right" style="padding-bottom: 5px;"><sys:treeselectcallback id="oagroup" name="oagroupids" value="${ids}" labelName="oagroupnames" labelValue="${names}"
		title="用户" url="/sys/office/treeData?type=3" cssClass="form-control required" notAllowSelectParent="true" checked="true"/></span>
			<thead>
				<tr>
					<th>姓名</th>
					<th>&nbsp;</th>
				</tr>
			</thead>
			<tbody id="recordArea">
			</tbody>
		</table>
</form>		
</body>
</html>