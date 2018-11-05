<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>平台管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		var validateForm;
		function doSubmit(){//回调函数，在编辑和保存动作时，供openDialog调用提交表单。
		  if(validateForm.form()){
              var index = layer.load(0, {shade: false});
              $.post("${ctx}/sys/institution/saveContent",{
                  'fileurl':$("#fileurl").val(),
                  'iid':$("#iid").val()
              },function(data){
                  layer.close(index);
                  var jsonData = jQuery.parseJSON(data);
                  if(jsonData.status == 'y'){//alert(jsonData.info);

                      parent.layer.alert(jsonData.info, {icon: 1},function () {
                          parent.layer.closeAll();
                      });

                  }else{
                      layer.alert(jsonData.info,0);
                  }
              });
			  //return true;
		  }
	
		  return false;
		}
		$(document).ready(function() {
		    $("#uploadF").click(function () {
                var id=$(this).attr("vl");
                commonFileUpload(id,"pdf");
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
	<form:form id="inputForm" modelAttribute="institution" action="" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<input type="hidden" id="iid" value="${institution.id}"/>
		<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
		   <tbody>
				<tr>
					<td class="width-15 active"><label class="pull-right">文件路径：</label></td>
					<td class="width-35">
						<button type="button" class="btn btn-primary" id="uploadF" vl="fileurl"><i class="glyphicon glyphicon-open"></i>&nbsp;上传pdf文件</button>
						<input type="hidden" id="fileurl" name="fileurl" value="${institution.fileurl}" />
						<ol style="line-height: 20px; padding: 10px; padding-left:40px;" id="filecontent">
							<c:if test="${institution.fileurl!=null&&institution.fileurl!=''}">
								<li><a href="javascript:;" target="_blank" vl="${institution.fileurl}" onclick="commonFileDownLoad(this)"><span>${fns:getFileName(institution.fileurl)}</span></a> &nbsp; <a href="javascript:;" name="removeFile"><i class="glyphicon glyphicon-remove text-danger"></i></a></li>
							</c:if>
						</ol>
					</td>
		  		</tr>
		 	</tbody>
		</table>
	</form:form>
</body>
</html>