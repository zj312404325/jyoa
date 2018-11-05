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
		    $("#uploadF").click(function () {
                var id=$(this).attr("vl");
                commonFileUpload(id);
            });

		    $("#filecontent").delegate("a[name=removeFile]","click",function () {
				$("#filecontent").html("");
				$("#fileurl").val("");
            })

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
			            elem: '#fileyear', //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
			            event: 'focus' //响应事件。如果没有传入event，则按照默认的click
			        });
					laydate({
			            elem: '#effectivedate', //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
			            event: 'focus' //响应事件。如果没有传入event，则按照默认的click
			        });
					laydate({
			            elem: '#expirydate', //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
			            event: 'focus' //响应事件。如果没有传入event，则按照默认的click
			        });
		});

        //图片上传回调函数
        function commonFileUploadCallBack(id,url,fname){
            $("#"+id).val(url);
            var htmlstr="";
            if(url!=''){
                htmlstr+='<li><a href="javascript:;" target="_blank" vl="'+url+'" onclick="commonFileDownLoad(this)"><span>'+fname+'</span></a> &nbsp; <a href="javascript:;" name="removeFile"><i class="glyphicon glyphicon-remove text-danger"></i></a></li>';
            }
            $("#filecontent").html(htmlstr);
        }

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
	<form:form id="inputForm" modelAttribute="fileManagement" action="${ctx}/filemanagement/fileManagement/saveAuthor" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>	
		<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
		   <tbody>
				<%--<tr>
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
						&lt;%&ndash;<form:hidden id="fileurl" path="fileurl" htmlEscape="false" maxlength="2000" class="form-control"/>
						<sys:ckfinderhead input="fileurl" type="files" uploadPath="/filemanagement/fileManagement" selectMultiple="false"/>&ndash;%&gt;
						<button type="button" class="btn btn-primary" id="uploadF" vl="fileurl"><i class="glyphicon glyphicon-open"></i>&nbsp;上传</button>
						<input type="hidden" id="fileurl" name="fileurl" value="${fileManagement.fileurl}" />
						<ol style="line-height: 20px; padding: 10px; padding-left:40px;" id="filecontent">
							<c:if test="${fileManagement.fileurl!=null&&fileManagement.fileurl!=''}">
								<li><a href="javascript:;" target="_blank" vl="${fileManagement.fileurl}" onclick="commonFileDownLoad(this)"><span>${fileManagement.filename}</span></a> &nbsp; <a href="javascript:;" name="removeFile"><i class="glyphicon glyphicon-remove text-danger"></i></a></li>
							</c:if>
						</ol>
					</td>
		  		</tr>--%>
				<tr>
					<td class="width-15 active"><label class="pull-right">授权：</label></td>
					<td class="width-35">
						<span id="userarea" ><sys:treeselect id="receiveuser" name="receiveuserids" value="${fileManagement.receiveuserids}" labelName="receiveusernames" labelValue="${fileManagement.receiveusernames}"
							title="用户" url="/sys/office/treeData?type=3" isAll="true" cssClass="form-control" notAllowSelectParent="true" checked="true"/></span>
					</td>
				</tr>
		 	</tbody>
		</table>


		
	</form:form>
</body>
</html>