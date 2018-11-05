<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>离职审计管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		var validateForm;
		function doSubmit(){//回调函数，在编辑和保存动作时，供openDialog调用提交表单。
		  if(validateForm.form()){
              var leaveurl=$("#leaveurl").val();
              if(leaveurl==null||leaveurl==''){
                  layer.alert("请上传离职审计报告！",{icon:2});
                  return;
              }
			  $("#inputForm").submit();
			  return true;
		  }
	
		  return false;
		}
        //图片上传回调函数
        function commonFileUploadCallBack(id,url,fname){
            $("#"+id).val(url);
            var htmlstr="";
            if(url!=''){
                htmlstr+='<li><a href="javascript:;" target="_blank" vl="'+url+'" onclick="commonFileDownLoad(this)"><span>'+fname+'</span></a> &nbsp; <a href="javascript:;" name="removeFile"><i class="glyphicon glyphicon-remove text-danger"></i></a></li>';
            }
            $("#filecontent").html(htmlstr);
        }

        function setReadOnly() {
            $(".tbdisable input").attr("disabled","true");
            $(".tbdisable textarea").attr("disabled","true");
            $(".tbdisable button").attr("disabled","true");
            $(".tbdisable select").attr("disabled","true");
            $(".tbdisable a[name=removeFile]").remove();
        }
		$(document).ready(function() {
		    if('${leaveaudit.officeid}'!=''){
                setstation();
			}

			$("#stationid").change(function () {
                var stationname=$(this).find("option:selected").text();
			    if(stationname!='请选择'){
                    $("#stationname").val(stationname);
				}
            });

            $("#uploadF").click(function () {
                var id=$(this).attr("vl");
                commonFileUpload(id);
            });

            $("#filecontent").delegate("a[name=removeFile]","click",function () {
                $("#filecontent").html("");
                $("#leaveurl").val("");
            })

            if('${leaveaudit.bhv}'=='1'){
                setReadOnly();
            }

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
			            elem: '#entrydate', //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
			            event: 'focus' //响应事件。如果没有传入event，则按照默认的click
			        });
					laydate({
			            elem: '#leavedate', //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
			            event: 'focus' //响应事件。如果没有传入event，则按照默认的click
			        });
		});

        function callback(){
            if($("#officeidId").val() != ''){
                $.post("${ctx}/sys/office/getPostsByOfficeId",
                    {
                        "officeid":$("#officeidId").val()
                    },function(data){
                        var jsonData = jQuery.parseJSON(data);
                        var htmlStr = '<option value="">请选择</option>';
                        if(jsonData.status == 'y'){

                            $.each(jsonData.postlist,function(i,n){  //遍历键值对
                                htmlStr+='<option value="'+n.id+'">'+n.name+'</option>';
                            })
                        }
                        $("#stationid").html(htmlStr);
                    });
            }

        }

        function setstation(){
            var officeid='${leaveaudit.officeid}'
            $.post("${ctx}/sys/office/getPostsByOfficeId",
                {
                    "officeid":officeid
                },function(data){
                    var jsonData = jQuery.parseJSON(data);
                    var htmlStr = '<option value="">请选择</option>';
                    if(jsonData.status == 'y'){
                        $.each(jsonData.postlist,function(i,n){  //遍历键值对
                            htmlStr+='<option value="'+n.id+'">'+n.name+'</option>';
                        })
                    }
                    $("#stationid").html(htmlStr);
                    $("#stationid").val('${leaveaudit.stationid}');
                });
        }
	</script>
</head>
<body class="hideScroll">
		<form:form id="inputForm" modelAttribute="leaveaudit" action="${ctx}/ehr/leaveaudit/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>	
		<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer tbdisable">
		   <tbody>
				<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>离职人姓名：</label></td>
					<td class="width-35">
						<%--<form:input path="leaver" htmlEscape="false" maxlength="20"    class="form-control required"/>--%>
						<sys:treeselect id="leaverid" name="leaverid" value="${leaveaudit.leaverid}" labelName="leaver" labelValue="${leaveaudit.leaver}"
										title="用户" url="/sys/office/treeData?type=3" cssClass="form-control required" allowClear="true" notAllowSelectParent="true"/>
					</td>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>离职人部门：</label></td>
					<td class="width-35">
						<%--<form:input path="officename" htmlEscape="false" maxlength="20"    class="form-control required"/>--%>
						<sys:treeselectCallBackFun id="officeid" name="officeid" value="${leaveaudit.officeid}" labelName="officename" labelValue="${leaveaudit.officename}"
										title="部门" url="/sys/office/treeData?type=2"  cssClass="form-control required" allowClear="true" />
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">离职人岗位：</label></td>
					<td class="width-35">
						<form:hidden path="stationname" htmlEscape="false"  class="form-control "/>
						<select id="stationid" name="stationid" class="form-control valid" aria-invalid="false">
							<option value="">请选择</option>
							<%--<c:forEach items="${user.office.postList}" var="p">
								<option value="${p.id}" <c:if test="${user.stationType == p.id}">selected="selected"</c:if> >${p.postname}</option>
							</c:forEach>--%>
						</select>
					</td>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>入职日期：</label></td>
					<td class="width-35">
						<input id="entrydate" name="entrydate" type="text" maxlength="20" class="laydate-icon form-control layer-date required"
							value="<fmt:formatDate value="${leaveaudit.entrydate}" pattern="yyyy-MM-dd"/>"/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>离职日期：</label></td>
					<td class="width-35">
						<input id="leavedate" name="leavedate" type="text" maxlength="20" class="laydate-icon form-control layer-date required"
							value="<fmt:formatDate value="${leaveaudit.leavedate}" pattern="yyyy-MM-dd"/>"/>
					</td>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>离职审计报告：</label></td>
					<td class="width-35">
						<%--<form:hidden id="leaveurl" path="leaveurl" htmlEscape="false" maxlength="2000" class="form-control"/>
						<sys:ckfinder input="leaveurl" type="files" uploadPath="/ehr/leaveaudit" selectMultiple="true"/>--%>
						<button type="button" class="btn btn-primary" id="uploadF" vl="leaveurl"><i class="glyphicon glyphicon-open"></i>&nbsp;上传</button>
						<input type="hidden" id="leaveurl" name="leaveurl" value="${leaveaudit.leaveurl}" />
						<ol style="line-height: 20px; padding: 10px; padding-left:40px;" id="filecontent">
							<c:if test="${leaveaudit.leaveurl!=null&&leaveaudit.leaveurl!=''}">
								<li><a href="javascript:;" target="_blank" vl="${leaveaudit.leaveurl}" onclick="commonFileDownLoad(this)"><span>${fns:getFileName(leaveaudit.leaveurl)}</span></a> &nbsp; <a href="javascript:;" name="removeFile"><i class="glyphicon glyphicon-remove text-danger"></i></a></li>
							</c:if>
						</ol>
					</td>
				</tr>
		 	</tbody>
		</table>
	</form:form>
</body>
</html>