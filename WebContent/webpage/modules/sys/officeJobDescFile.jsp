<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>职务说明书管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		var validateForm;
		function doSubmit(){//回调函数，在编辑和保存动作时，供openDialog调用提交表单。
		  if(validateForm.form()){
              var index = layer.load(0, {shade: false});
              $.post("${ctx}/sys/office/saveFiles",{
                  'file1':$("#file1").val(),
                  'file2':$("#file2").val(),
                  'id':$("#id").val()
              },function(data){
                  layer.close(index);
                  var jsonData = jQuery.parseJSON(data);
                  if(jsonData.status == 'y'){
                      parent.layer.alert(jsonData.info, {icon: 1},function () {
                          //window.parent.refresh();
                          var target = window.parent.$('.J_iframe[data-id="${ctx}/sys/office/jobDescIndex"]');
                          var url = target.attr('src');
                          //显示loading提示
                          var loading = layer.load();
                          target.attr('src', url).load(function () {
                              //关闭loading提示
                              layer.close(loading);
                          });
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
            $("#uploadF2").click(function () {
                var id=$(this).attr("vl");
                commonFileUpload(id,"pdf");
            });

		    $("#file1area").delegate("a[name=removefile1]","click",function () {
				$("#file1area").html("");
				$("#file1").val("");
            });
            $("#file2area").delegate("a[name=removefile2]","click",function () {
                $("#file2area").html("");
                $("#file2").val("");
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
		});

        //图片上传回调函数
        function commonFileUploadCallBack(id,url,fname){
            $("#"+id).val(url);
            var htmlstr="";
            if(url!=''){
                htmlstr+='<li><a href="javascript:;" target="_blank" vl="'+url+'" onclick="commonFileDownLoad(this)"><span>'+fname+'</span></a> &nbsp; <a href="javascript:;" name="remove'+id+'"><i class="glyphicon glyphicon-remove text-danger"></i></a></li>';
            }
            $("#"+id+"area").html(htmlstr);
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
	<form:form id="inputForm" modelAttribute="office" action="${ctx}/sys/office/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
		   <tbody>
				<tr>
					<td class="width-15 active"><label class="pull-right">职务说明书：</label></td>
					<td class="width-35">
						<button type="button" class="btn btn-primary" id="uploadF" vl="file1"><i class="glyphicon glyphicon-open"></i>&nbsp;上传pdf文件</button>
						<input type="hidden" id="file1" name="file1" value="${office.file1}" />
						<ol style="line-height: 20px; padding: 10px; padding-left:40px;" id="file1area">
							<c:if test="${office.file1!=null&&office.file1!=''}">
								<li><a href="javascript:;" target="_blank" vl="${office.file1}" onclick="commonFileDownLoad(this)"><span>${fns:getFileName(office.file1)}</span></a> &nbsp; <a href="javascript:;" name="removefile1"><i class="glyphicon glyphicon-remove text-danger"></i></a></li>
							</c:if>
						</ol>
					</td>
		  		</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">绩效考核指标：</label></td>
					<td class="width-35">
						<button type="button" class="btn btn-primary" id="uploadF2" vl="file2"><i class="glyphicon glyphicon-open"></i>&nbsp;上传pdf文件</button>
						<input type="hidden" id="file2" name="file2" value="${office.file2}" />
						<ol style="line-height: 20px; padding: 10px; padding-left:40px;" id="file2area">
							<c:if test="${office.file2!=null&&office.file2!=''}">
								<li><a href="javascript:;" target="_blank" vl="${office.file2}" onclick="commonFileDownLoad(this)"><span>${fns:getFileName(office.file2)}</span></a> &nbsp; <a href="javascript:;" name="removefile2"><i class="glyphicon glyphicon-remove text-danger"></i></a></li>
							</c:if>
						</ol>
					</td>
				</tr>
		 	</tbody>
		</table>
	</form:form>
</body>
</html>