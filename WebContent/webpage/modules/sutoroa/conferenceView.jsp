<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>每日团队风采管理</title>
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
		function setReadOnly() {
            $(".tbdisable input").attr("disabled","true");
            $(".tbdisable textarea").attr("disabled","true");
            $(".tbdisable button").attr("disabled","true");
        }
		$(document).ready(function() {
            setReadOnly();

		    $("#leaveMsgSub").click(function () {
                //验证
                var content=$("#leaveMsgContent").val();
                if(content==null||content.trim()==''){
                    layer.alert("内容不能为空！");
                    return;
                }
                if(content.length>200){
                    layer.alert("内容不能超过200字符！");
                    return;
                }
                $.post("${ctx}/sutoroa/leavemsg/saveAjax",{"content":content,"conferenceid":'${conference.id}'},function(data){
                    var jsonData = jQuery.parseJSON(data);
                    if(jsonData.status == 'y'){
                        location.reload();
                    }else{
                        layer.alert(jsonData.info,{icon: 2});
                    }
                });
            });

            $("#uploadF").click(function () {
                var id=$(this).attr("vl");
                commonFileUpload(id);
            });

            $("#filecontent").delegate("a[name=removeFile]","click",function () {
                $("#filecontent").html("");
                $("#teampic").val("");
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
                htmlstr+='<li><a href="javascript:;" target="_blank" vl="'+url+'" onclick="commonFileDownLoad(this)"><img src="'+url+'" width="120" height="120"/></a> &nbsp; <a href="javascript:;" name="removeFile"><i class="glyphicon glyphicon-remove text-danger"></i></a></li>';
            }
            $("#filecontent").html(htmlstr);
        }

        var leaveMsgDiv;
        function addLeavemsg(){
            //弹窗
            leaveMsgDiv=layer.open({
                type: 1,
                title: '添加留言',
                //skin: 'layui-layer-rim', //加上边框
                shadeClose: true,
                shade: 0.8,
                area : ['750px' , '300px'],
                offset : ['150px',''],
                content: $('#leaveMsgDiv')
            });
        }
	</script>
</head>
<body class="hideScroll">
		<form:form id="inputForm" modelAttribute="conference" action="${ctx}/sutoroa/conference/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<form:hidden path="category"/>
		<sys:message content="${message}"/>
		<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer tbdisable">
		   <tbody>
				<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>部门：</label></td>
					<td class="width-35">
						<form:input path="department" htmlEscape="false" maxlength="20"    class="form-control required"/>
					</td>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>团队：</label></td>
					<td class="width-35">
						<form:input path="team" htmlEscape="false" maxlength="20"    class="form-control required"/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>主持人：</label></td>
					<td class="width-35">
						<form:input path="compere" htmlEscape="false" maxlength="20"    class="form-control required"/>
					</td>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>上传人：</label></td>
					<td class="width-35">
						<form:input path="createusername" htmlEscape="false" maxlength="20"    class="form-control required"/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>应到人数：</label></td>
					<td class="width-35">
						<form:input path="shouldnumber" htmlEscape="false"    class="form-control required digits"/>
					</td>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>实到人数：</label></td>
					<td class="width-35">
						<form:input path="realnumber" htmlEscape="false"    class="form-control required digits"/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">工作经验：</label></td>
					<td class="width-35">
						<form:textarea path="experience" htmlEscape="false" rows="4" maxlength="200"    class="form-control "/>
					</td>
					<td class="width-15 active"><label class="pull-right">工作计划：</label></td>
					<td class="width-35">
						<form:textarea path="workplan" htmlEscape="false" rows="4" maxlength="200"    class="form-control "/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">今日建议与意见：</label></td>
					<td class="width-35">
						<form:textarea path="recommend" htmlEscape="false" rows="4" maxlength="200"    class="form-control "/>
					</td>
					<td class="width-15 active"><label class="pull-right">团队风采展示：</label></td>
					<td class="width-35">
						<button type="button" class="btn btn-primary" id="uploadF" vl="teampic"><i class="glyphicon glyphicon-open"></i>&nbsp;上传</button>
						<input type="hidden" id="teampic" name="teampic" value="${conference.teampic}" />
						<ol style="line-height: 20px; padding: 10px; padding-left:40px;" id="filecontent">
							<c:if test="${conference.teampic!=null&&conference.teampic!=''}">
								<li><a href="javascript:;" target="_blank" vl="${conference.teampic}" onclick="commonFileDownLoad(this)"><img src="${conference.teampic}" width="120" height="120"/></a> &nbsp; <a href="javascript:;" name="removeFile"><i class="glyphicon glyphicon-remove text-danger"></i></a></li>
							</c:if>
						</ol>
					</td>

				</tr>
		 	</tbody>
		</table>
		<div class="tabs-container" id="keyContent">
			<ul class="nav nav-tabs">
				<li class="active"><a data-toggle="tab" href="#tab-1" aria-expanded="true">留言</a>
				</li>
			</ul>
			<div class="tab-content">
				<div id="tab-1" class="tab-pane active">
					<div class="panel-body">
						<div class="row">
							<div class="col-sm-12" id="div_basic">
								<div class="row">
									<div class="col-sm-12">
										<div class="pull-left">
											<button type="button" class="btn btn-white btn-sm" data-toggle="tooltip" data-placement="left" onclick="addLeavemsg()" title="添加留言"><i class="fa fa-plus"></i> 添加留言</button>
										</div>
									</div>
								</div>
								<table class="table table-bordered table-hover">
									<tr>
										<th>内容</th>
										<th>创建者</th>
										<th>创建时间</th>
									</tr>
									<c:forEach items="${conference.leavemsgList}" var="leavemsg" varStatus="s">
										<tr>
											<td>${leavemsg.content}</td>
											<td>${leavemsg.createusername}</td>
											<td><fmt:formatDate value="${leavemsg.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
										</tr>
									</c:forEach>
								</table>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</form:form>

    <div id="leaveMsgDiv" style="display:none;">
        <table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
            <tbody>
            <tr>
                <td class="width-15 active"><label class="pull-right"><font color="red">*</font>内容：</label></td>
                <td class="width-35">
                    <textarea style="width: 90%; height: 100px; border-radius: 3px;" id="leaveMsgContent" maxlength="200"></textarea>
                </td>
            </tr>
            <tr>
                <td class="width-15 active"></td>
                <td class="width-35">
                    <input type="button" class="btn btn-primary" value="提交" id="leaveMsgSub" />
                </td>
            </tr>
            </tbody>
        </table>
    </div>
</body>
</html>