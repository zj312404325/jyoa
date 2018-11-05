<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>8s检查管理</title>
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
        //图片上传回调函数
        function commonFileUploadCallBack(id,url,fname){
            $("#"+id).val(url);
            var selectstr="filecontent"+id;
            var htmlstr="";
            if(url!=''){
                htmlstr+='<li><a href="javascript:;" target="_blank" vl="'+url+'" onclick="commonFileDownLoad(this)"><span>'+fname+'</span></a> &nbsp; <a href="javascript:;" name="removeFile" vl="'+selectstr+'" vl1="'+id+'"><i class="glyphicon glyphicon-remove text-danger"></i></a></li>';
            }
            $("#"+selectstr).html(htmlstr);
        }

        //公司初始化输入内容
        function setType(type) {
            if(type=='0'){//常熟
                $("tr[name=changshu]").show();
                $("tr[name=ningbo]").hide();
            }else if(type=='1'){//宁波
                $("tr[name=ningbo]").show();
                $("tr[name=changshu]").hide();
            }
        }
        //清除表单数据
        function clearForm() {
            $("#inputForm input[type=text]").val("");
        }

        function setReadOnly() {
            $(".tbdisable input").attr("disabled","true");
            $(".tbdisable textarea").attr("disabled","true");
            $(".tbdisable button").attr("disabled","true");
            $(".tbdisable a[name=removeFile]").remove();
        }
		$(document).ready(function() {
		    if('${hygiene.id}'==''){//新增
                setType('0')
            }else{//编辑
                setType('${hygiene.type}')
                if('${tp}'=='0'){
                    setReadOnly();
                }
            }

            $('input[name=type]').on('ifChecked', function(event){
                var type=$(event.target).val();
                setType(type);
                clearForm();
            });

            $("button[name=uploadF]").click(function () {
                var id=$(this).attr("vl");
                commonFileUpload(id);
            });

            $(".filecontent").delegate("a[name=removeFile]","click",function () {
                var flc=$(this).attr("vl");
                var v=$(this).attr("vl1");
                $("#"+flc).html("");
                $("#"+v).val("");
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

            /*laydate({
                elem: '#id .mydate', //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
                event: 'focus' //响应事件。如果没有传入event，则按照默认的click
            });*/

            $("body").find(".mydate").each(function (i) {
                var id=$(this).attr("id");
                laydate({
                    elem: '#'+id, //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
                    event: 'focus' //响应事件。如果没有传入event，则按照默认的click
                });
            });
			
		});
	</script>
</head>
<body class="hideScroll">
		<form:form id="inputForm" modelAttribute="hygiene" action="${ctx}/oa/hygiene/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
		<%--<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
		   <tbody>
		 	</tbody>
		</table>--%>

			<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer tbdisable">
				<!-- <tr style="background:#EEEEEE; ">
                <td colspan="6" align="center">
                  <font size="12"><b>工厂8S检查记录表</b> </font>
                </td>
            </tr> -->
				<tr>
					<td colspan="6" align="right" style="background:#EEEEEE; ">
						<form:radiobuttons path="type" items="${fns:getDictList('oa_hygiene')}" itemLabel="label" itemValue="value" htmlEscape="false" class="i-checks "/>
					</td>
				</tr>
				<tr align="center">
					<td style="width: 100"><font color="red">*</font><b>检查组长</b></td><td style="width: 120"><form:input path="leader" htmlEscape="false" maxlength="20"    class="form-control required"/></td>
					<td style="width: 80"><font color="red">*</font><b>检查日期</b></td><td style="width: 120"><input id="checkdate" name="checkdate" type="text" maxlength="20" class="laydate-icon form-control layer-date mydate required" value="<fmt:formatDate value="${hygiene.checkdate}" pattern="yyyy-MM-dd"/>"/>
                    </td>
				</tr>
				<tr align="center">
					<td style="width: 100"><font color="red">*</font><b>检查成员</b></td><td style="width: 120" colspan="5"><form:input path="checkmember" htmlEscape="false" maxlength="50"    class="form-control required"/></td>
				</tr>

			</table>
			<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer tbdisable">
				<tr align="center" name="etr">
					<td>检查项目</td>
					<td>检查情况</td>
					<td>整改内容</td>
					<td width="110">整改期限</td>
					<td>复查结果</td>
					<td>备注</td>
					<td>图文附件</td>
				</tr>
				<tr align="center" name="etr">
					<td>厂区安全规范</td>
					<td><form:input path="cqaqsituation" htmlEscape="false" maxlength="100"    class="form-control"/></td>
					<td><form:input path="cqaqcontent" htmlEscape="false" maxlength="100"    class="form-control"/></td>
					<td><input id="cqaqdate" name="cqaqdate" type="text" maxlength="20" class="laydate-icon form-control layer-date mydate" value="<fmt:formatDate value="${hygiene.cqaqdate}" pattern="yyyy-MM-dd"/>"/>
						</td>
					<td><form:input path="cqaqresult" htmlEscape="false" maxlength="100"    class="form-control"/></td>
					<td><form:textarea path="cqaqcomment" htmlEscape="false" maxlength="100"    class="form-control"/></td>
					<td>
                        <input type="hidden" id="cqaqfile" name="cqaqfile" value="${hygiene.cqaqfile}"/>
                        <ol style="line-height: 20px; padding: 10px; padding-left:40px;" id="filecontentcqaqfile" class="filecontent">
                            <c:if test="${hygiene.cqaqfile!=null&&hygiene.cqaqfile!=''}">
                                <li><a href="javascript:;" target="_blank" vl="${hygiene.cqaqfile}" onclick="commonFileDownLoad(this)"><span>${fns:getFileName(hygiene.cqaqfile)}</span></a> &nbsp; <a href="javascript:;" name="removeFile" vl="filecontentcqaqfile" vl1="cqaqfile"><i class="glyphicon glyphicon-remove text-danger"></i></a></li>
                            </c:if>
                        </ol>
                        <button type="button" class="btn btn-primary" name="uploadF" vl="cqaqfile"><i class="glyphicon glyphicon-open"></i>&nbsp;上传</button>
                        </td>
				</tr>
				<tr align="center" name="etr">
					<td>厂区标语规范</td>
					<td><form:input path="cqbysituation" htmlEscape="false" maxlength="100"    class="form-control"/></td>
					<td><form:input path="cqbycontent" htmlEscape="false" maxlength="100"    class="form-control"/></td>
					<td><input id="cqbydate" name="cqbydate" type="text" maxlength="20" class="laydate-icon form-control layer-date mydate" value="<fmt:formatDate value="${hygiene.cqbydate}" pattern="yyyy-MM-dd"/>"/></td>
					<td><form:input path="cqbyresult" htmlEscape="false" maxlength="100"    class="form-control"/></td>
					<td><form:textarea path="cqbycomment" htmlEscape="false" maxlength="100"    class="form-control"/></td>
					<td>
                        <input type="hidden" id="cqbyfile" name="cqbyfile" value="${hygiene.cqbyfile}"/>
                        <ol style="line-height: 20px; padding: 10px; padding-left:40px;" id="filecontentcqbyfile" class="filecontent">
                            <c:if test="${hygiene.cqbyfile!=null&&hygiene.cqbyfile!=''}">
                                <li><a href="javascript:;" target="_blank" vl="${hygiene.cqbyfile}" onclick="commonFileDownLoad(this)"><span>${fns:getFileName(hygiene.cqbyfile)}</span></a> &nbsp; <a href="javascript:;" name="removeFile" vl="filecontentcqbyfile" vl1="cqbyfile"><i class="glyphicon glyphicon-remove text-danger"></i></a></li>
                            </c:if>
                        </ol>
                        <button type="button" class="btn btn-primary" name="uploadF" vl="cqbyfile"><i class="glyphicon glyphicon-open"></i>&nbsp;上传</button>
                        </td>
				</tr>
				<tr align="center" name="etr">
					<td>设备管理规范</td>
					<td><form:input path="sbglsituation" htmlEscape="false" maxlength="100"    class="form-control"/></td>
					<td><form:input path="sbglcontent" htmlEscape="false" maxlength="100"    class="form-control"/></td>
					<td><input id="sbgldate" name="sbgldate" type="text" maxlength="20" class="laydate-icon form-control layer-date mydate" value="<fmt:formatDate value="${hygiene.sbgldate}" pattern="yyyy-MM-dd"/>"/></td>
					<td><form:input path="sbglresult" htmlEscape="false" maxlength="100"    class="form-control"/></td>
					<td><form:textarea path="sbglcomment" htmlEscape="false" maxlength="100"    class="form-control"/></td>
					<td><input type="hidden" id="sbglfile" name="sbglfile" value="${hygiene.sbglfile}"/>
                        <ol style="line-height: 20px; padding: 10px; padding-left:40px;" id="filecontentsbglfile" class="filecontent">
                            <c:if test="${hygiene.sbglfile!=null&&hygiene.sbglfile!=''}">
                                <li><a href="javascript:;" target="_blank" vl="${hygiene.sbglfile}" onclick="commonFileDownLoad(this)"><span>${fns:getFileName(hygiene.sbglfile)}</span></a> &nbsp; <a href="javascript:;" name="removeFile" vl="filecontentsbglfile" vl1="sbglfile"><i class="glyphicon glyphicon-remove text-danger"></i></a></li>
                            </c:if>
                        </ol>
                        <button type="button" class="btn btn-primary" name="uploadF" vl="sbglfile"><i class="glyphicon glyphicon-open"></i>&nbsp;上传</button>
                    </td>
				</tr>
				<tr align="center" name="etr">
					<td>食品管理规范</td>
					<td><form:input path="spglsituation" htmlEscape="false" maxlength="100"    class="form-control"/></td>
					<td><form:input path="spglcontent" htmlEscape="false" maxlength="100"    class="form-control"/></td>
					<td><input id="spgldate" name="spgldate" type="text" maxlength="20" class="laydate-icon form-control layer-date mydate" value="<fmt:formatDate value="${hygiene.spgldate}" pattern="yyyy-MM-dd"/>"/></td>
					<td><form:input path="spglresult" htmlEscape="false" maxlength="100"    class="form-control"/></td>
					<td><form:textarea path="spglcomment" htmlEscape="false" maxlength="100"    class="form-control"/></td>
					<td><input type="hidden" id="spglfile" name="spglfile" value="${hygiene.spglfile}"/>
                        <ol style="line-height: 20px; padding: 10px; padding-left:40px;" id="filecontentspglfile" class="filecontent">
                            <c:if test="${hygiene.spglfile!=null&&hygiene.spglfile!=''}">
                                <li><a href="javascript:;" target="_blank" vl="${hygiene.spglfile}" onclick="commonFileDownLoad(this)"><span>${fns:getFileName(hygiene.spglfile)}</span></a> &nbsp; <a href="javascript:;" name="removeFile" vl="filecontentspglfile" vl1="spglfile"><i class="glyphicon glyphicon-remove text-danger"></i></a></li>
                            </c:if>
                        </ol>
                        <button type="button" class="btn btn-primary" name="uploadF" vl="spglfile"><i class="glyphicon glyphicon-open"></i>&nbsp;上传</button>
                    </td>
				</tr>
				<tr align="center" name="etr">
					<td>员工着装规范</td>
					<td><form:input path="ygzzsituation" htmlEscape="false" maxlength="100"    class="form-control"/></td>
					<td><form:input path="ygzzcontent" htmlEscape="false" maxlength="100"    class="form-control"/></td>
					<td><input id="ygzzdate" name="ygzzdate" type="text" maxlength="20" class="laydate-icon form-control layer-date mydate" value="<fmt:formatDate value="${hygiene.ygzzdate}" pattern="yyyy-MM-dd"/>"/></td>
					<td><form:input path="ygzzresult" htmlEscape="false" maxlength="100"    class="form-control"/></td>
					<td><form:textarea path="ygzzcomment" htmlEscape="false" maxlength="100"    class="form-control"/></td>
					<td><input type="hidden" id="ygzzfile" name="ygzzfile" value="${hygiene.ygzzfile}"/>
                        <ol style="line-height: 20px; padding: 10px; padding-left:40px;" id="filecontentygzzfile" class="filecontent">
                            <c:if test="${hygiene.ygzzfile!=null&&hygiene.ygzzfile!=''}">
                            <li><a href="javascript:;" target="_blank" vl="${hygiene.ygzzfile}" onclick="commonFileDownLoad(this)"><span>${fns:getFileName(hygiene.ygzzfile)}</span></a> &nbsp; <a href="javascript:;" name="removeFile" vl="filecontentygzzfile" vl1="ygzzfile"><i class="glyphicon glyphicon-remove text-danger"></i></a></li>
                            </c:if>
                        </ol>
                        <button type="button" class="btn btn-primary" name="uploadF" vl="ygzzfile"><i class="glyphicon glyphicon-open"></i>&nbsp;上传</button>
                    </td>
				</tr>
				<tr align="center" name="etr">
					<td>IT信息管理</td>
					<td><form:input path="itsituation" htmlEscape="false" maxlength="100"    class="form-control"/></td>
					<td><form:input path="itcontent" htmlEscape="false" maxlength="100"    class="form-control"/></td>
					<td><input id="itdate" name="itdate" type="text" maxlength="20" class="laydate-icon form-control layer-date mydate" value="<fmt:formatDate value="${hygiene.itdate}" pattern="yyyy-MM-dd"/>"/>
					<td><form:input path="itresult" htmlEscape="false" maxlength="100"    class="form-control"/></td>
					<td><form:textarea path="itcomment" htmlEscape="false" maxlength="100"    class="form-control"/></td>
					<td><input type="hidden" id="itfile" name="itfile" value="${hygiene.itfile}"/>
                        <ol style="line-height: 20px; padding: 10px; padding-left:40px;" id="filecontentitfile" class="filecontent">
                            <c:if test="${hygiene.itfile!=null&&hygiene.itfile!=''}">
                            <li><a href="javascript:;" target="_blank" vl="${hygiene.itfile}" onclick="commonFileDownLoad(this)"><span>${fns:getFileName(hygiene.itfile)}</span></a> &nbsp; <a href="javascript:;" name="removeFile" vl="filecontentitfile" vl1="itfile"><i class="glyphicon glyphicon-remove text-danger"></i></a></li>
                            </c:if>
                        </ol>
                        <button type="button" class="btn btn-primary" name="uploadF" vl="itfile"><i class="glyphicon glyphicon-open"></i>&nbsp;上传</button>
                    </td>
				</tr>
				<tr align="center" name="etr">
					<td>产品验收管理</td>
					<td><form:input path="cpyssituation" htmlEscape="false" maxlength="100"    class="form-control"/></td>
					<td><form:input path="cpyscontent" htmlEscape="false" maxlength="100"    class="form-control"/></td>
					<td><input id="cpysdate" name="cpysdate" type="text" maxlength="20" class="laydate-icon form-control layer-date mydate"value="<fmt:formatDate value="${hygiene.cpysdate}" pattern="yyyy-MM-dd"/>"/>
					<td><form:input path="cpysresult" htmlEscape="false" maxlength="100"    class="form-control"/></td>
					<td><form:textarea path="cpyscomment" htmlEscape="false" maxlength="100"    class="form-control"/></td>
					<td><input type="hidden" id="cpysfile" name="cpysfile" value="${hygiene.cpysfile}"/>
                        <ol style="line-height: 20px; padding: 10px; padding-left:40px;" id="filecontentcpysfile" class="filecontent">
                            <c:if test="${hygiene.cpysfile!=null&&hygiene.cpysfile!=''}">
                                <li><a href="javascript:;" target="_blank" vl="${hygiene.cpysfile}" onclick="commonFileDownLoad(this)"><span>${fns:getFileName(hygiene.cpysfile)}</span></a> &nbsp; <a href="javascript:;" name="removeFile" vl="filecontentcpysfile" vl1="cpysfile"><i class="glyphicon glyphicon-remove text-danger"></i></a></li>
                            </c:if>
                        </ol>
                        <button type="button" class="btn btn-primary" name="uploadF" vl="cpysfile"><i class="glyphicon glyphicon-open"></i>&nbsp;上传</button>
                    </td>
				</tr>
				<tr align="center" name="etr">
					<td>交接班情况检查</td>
					<td><form:input path="jjbsituation" htmlEscape="false" maxlength="100"    class="form-control"/></td>
					<td><form:input path="jjbcontent" htmlEscape="false" maxlength="100"    class="form-control"/></td>
					<td><input id="jjbdate" name="jjbdate" type="text" maxlength="20" class="laydate-icon form-control layer-date mydate" value="<fmt:formatDate value="${hygiene.jjbdate}" pattern="yyyy-MM-dd"/>"/></td>
					<td><form:input path="jjbresult" htmlEscape="false" maxlength="100"    class="form-control"/></td>
					<td><form:textarea path="jjbcomment" htmlEscape="false" maxlength="100"    class="form-control"/></td>
					<td><input type="hidden" id="jjbfile" name="jjbfile" value="${hygiene.jjbfile}"/>
                        <ol style="line-height: 20px; padding: 10px; padding-left:40px;" id="filecontentjjbfile" class="filecontent">
                            <c:if test="${hygiene.jjbfile!=null&&hygiene.jjbfile!=''}">
                                <li><a href="javascript:;" target="_blank" vl="${hygiene.jjbfile}" onclick="commonFileDownLoad(this)"><span>${fns:getFileName(hygiene.jjbfile)}</span></a> &nbsp; <a href="javascript:;" name="removeFile" vl="filecontentjjbfile" vl1="jjbfile"><i class="glyphicon glyphicon-remove text-danger"></i></a></li>
                            </c:if>
                        </ol>
                        <button type="button" class="btn btn-primary" name="uploadF" vl="jjbfile"><i class="glyphicon glyphicon-open"></i>&nbsp;上传</button>
                    </td>
				</tr>
				<tr align="center" name="etr">
					<td>生产作业规程规范</td>
					<td><form:input path="sczysituation" htmlEscape="false" maxlength="100"    class="form-control"/></td>
					<td><form:input path="sczycontent" htmlEscape="false" maxlength="100"    class="form-control"/></td>
					<td><input id="sczydate" name="sczydate" type="text" maxlength="20" class="laydate-icon form-control layer-date mydate" value="<fmt:formatDate value="${hygiene.sczydate}" pattern="yyyy-MM-dd"/>"/></td>
					<td><form:input path="sczyresult" htmlEscape="false" maxlength="100"    class="form-control"/></td>
					<td><form:textarea path="sczycomment" htmlEscape="false" maxlength="100"    class="form-control"/></td>
					<td><input type="hidden" id="sczyfile" name="sczyfile" value="${hygiene.sczyfile}"/>
                        <ol style="line-height: 20px; padding: 10px; padding-left:40px;" id="filecontentsczyfile" class="filecontent">
                            <c:if test="${hygiene.sczyfile!=null&&hygiene.sczyfile!=''}">
                                <li><a href="javascript:;" target="_blank" vl="${hygiene.sczyfile}" onclick="commonFileDownLoad(this)"><span>${fns:getFileName(hygiene.sczyfile)}</span></a> &nbsp; <a href="javascript:;" name="removeFile" vl="filecontentsczyfile" vl1="sczyfile"><i class="glyphicon glyphicon-remove text-danger"></i></a></li>
                            </c:if>
                        </ol>
                        <button type="button" class="btn btn-primary" name="uploadF" vl="sczyfile"><i class="glyphicon glyphicon-open"></i>&nbsp;上传</button>
                    </td>
				</tr>
				<tr align="center" name="etr">
					<td>收发业务流程规范</td>
					<td><form:input path="sfywsituation" htmlEscape="false" maxlength="100"    class="form-control"/></td>
					<td><form:input path="sfywcontent" htmlEscape="false" maxlength="100"    class="form-control"/></td>
					<td><input id="sfywdate" name="sfywdate" type="text" maxlength="20" class="laydate-icon form-control layer-date mydate" value="<fmt:formatDate value="${hygiene.sfywdate}" pattern="yyyy-MM-dd"/>"/></td>
					<td><form:input path="sfywresult" htmlEscape="false" maxlength="100"    class="form-control"/></td>
					<td><form:textarea path="sfywcomment" htmlEscape="false" maxlength="100"    class="form-control"/></td>
					<td><input type="hidden" id="sfywfile" name="sfywfile" value="${hygiene.sfywfile}"/>
                        <ol style="line-height: 20px; padding: 10px; padding-left:40px;" id="filecontentsfywfile" class="filecontent">
                            <c:if test="${hygiene.sfywfile!=null&&hygiene.sfywfile!=''}">
                                <li><a href="javascript:;" target="_blank" vl="${hygiene.sfywfile}" onclick="commonFileDownLoad(this)"><span>${fns:getFileName(hygiene.sfywfile)}</span></a> &nbsp; <a href="javascript:;" name="removeFile" vl="filecontentsfywfile" vl1="sfywfile"><i class="glyphicon glyphicon-remove text-danger"></i></a></li>
                            </c:if>
                        </ol>
                        <button type="button" class="btn btn-primary" name="uploadF" vl="sfywfile"><i class="glyphicon glyphicon-open"></i>&nbsp;上传</button>
                    </td>
				</tr>
				<tr align="center" name="etr">
					<td>车辆管理/停放规范</td>
					<td><form:input path="clglsituation" htmlEscape="false" maxlength="100"    class="form-control"/></td>
					<td><form:input path="clglcontent" htmlEscape="false" maxlength="100"    class="form-control"/></td>
					<td><input id="clgldate" name="clgldate" type="text" maxlength="20" class="laydate-icon form-control layer-date mydate" value="<fmt:formatDate value="${hygiene.clgldate}" pattern="yyyy-MM-dd"/>"/></td>
					<td><form:input path="clglresult" htmlEscape="false" maxlength="100"    class="form-control"/></td>
					<td><form:textarea path="clglcomment" htmlEscape="false" maxlength="100"    class="form-control"/></td>
					<td><input type="hidden" id="clglfile" name="clglfile" value="${hygiene.clglfile}"/>
                        <ol style="line-height: 20px; padding: 10px; padding-left:40px;" id="filecontentclglfile" class="filecontent">
                            <c:if test="${hygiene.clglfile!=null&&hygiene.clglfile!=''}">
                                <li><a href="javascript:;" target="_blank" vl="${hygiene.clglfile}" onclick="commonFileDownLoad(this)"><span>${fns:getFileName(hygiene.clglfile)}</span></a> &nbsp; <a href="javascript:;" name="removeFile" vl="filecontentclglfile" vl1="clglfile"><i class="glyphicon glyphicon-remove text-danger"></i></a></li>
                            </c:if>
                        </ol>
                        <button type="button" class="btn btn-primary" name="uploadF" vl="clglfile"><i class="glyphicon glyphicon-open"></i>&nbsp;上传</button>
                    </td>
				</tr>
				<tr align="center" name="etr">
					<td>外来人员管理流程</td>
					<td><form:input path="wlrysituation" htmlEscape="false" maxlength="100"    class="form-control"/></td>
					<td><form:input path="wlrycontent" htmlEscape="false" maxlength="100"    class="form-control"/></td>
					<td><input id="wlrydate" name="wlrydate" type="text" maxlength="20" class="laydate-icon form-control layer-date mydate" value="<fmt:formatDate value="${hygiene.wlrydate}" pattern="yyyy-MM-dd"/>"/></td>
					<td><form:input path="wlryresult" htmlEscape="false" maxlength="100"    class="form-control"/></td>
					<td><form:textarea path="wlrycomment" htmlEscape="false" maxlength="100"    class="form-control"/></td>
					<td><input type="hidden" id="wlryfile" name="wlryfile" value="${hygiene.wlryfile}"/>
                        <ol style="line-height: 20px; padding: 10px; padding-left:40px;" id="filecontentwlryfile" class="filecontent">
                            <c:if test="${hygiene.wlryfile!=null&&hygiene.wlryfile!=''}">
                                <li><a href="javascript:;" target="_blank" vl="${hygiene.wlryfile}" onclick="commonFileDownLoad(this)"><span>${fns:getFileName(hygiene.wlryfile)}</span></a> &nbsp; <a href="javascript:;" name="removeFile" vl="filecontentwlryfile" vl1="wlryfile"><i class="glyphicon glyphicon-remove text-danger"></i></a></li>
                            </c:if>
                        </ol>
                        <button type="button" class="btn btn-primary" name="uploadF" vl="wlryfile"><i class="glyphicon glyphicon-open"></i>&nbsp;上传</button>
                    </td>
				</tr>
				<tr align="center" name="etr">
					<td>人文精神面貌建设</td>
					<td><form:input path="rwjssituation" htmlEscape="false" maxlength="100"    class="form-control"/></td>
					<td><form:input path="rwjscontent" htmlEscape="false" maxlength="100"    class="form-control"/></td>
					<td><input id="rwjsdate" name="rwjsdate" type="text" maxlength="20" class="laydate-icon form-control layer-date mydate" value="<fmt:formatDate value="${hygiene.rwjsdate}" pattern="yyyy-MM-dd"/>"/></td>
					<td><form:input path="rwjsresult" htmlEscape="false" maxlength="100"    class="form-control"/></td>
					<td><form:textarea path="rwjscomment" htmlEscape="false" maxlength="100"    class="form-control"/></td>
					<td><input type="hidden" id="rwjsfile" name="rwjsfile" value="${hygiene.rwjsfile}"/>
                        <ol style="line-height: 20px; padding: 10px; padding-left:40px;" id="filecontentrwjsfile" class="filecontent">
                            <c:if test="${hygiene.rwjsfile!=null&&hygiene.rwjsfile!=''}">
                                <li><a href="javascript:;" target="_blank" vl="${hygiene.rwjsfile}" onclick="commonFileDownLoad(this)"><span>${fns:getFileName(hygiene.rwjsfile)}</span></a> &nbsp; <a href="javascript:;" name="removeFile" vl="filecontentrwjsfile" vl1="rwjsfile"><i class="glyphicon glyphicon-remove text-danger"></i></a></li>
                            </c:if>
                        </ol>
                        <button type="button" class="btn btn-primary" name="uploadF" vl="rwjsfile"><i class="glyphicon glyphicon-open"></i>&nbsp;上传</button>
                    </td>
				</tr>
			</table>
			<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer tbdisable">
				<tr align="center" name="etr">
					<td>检查项目</td>
					<td>检查区域</td>
					<td>合格</td>
					<td>不合格</td>
					<td>检查情况</td>
					<td>整改内容</td>
					<td width="110">整改期限</td>
					<td>复查结果</td>
					<td>备注</td>
					<td>图文附件</td>
				</tr>
				<tr align="center" name="etr">
					<td rowspan="33">环保/卫生</td>
				</tr>
				<tr align="center" name="changshu">
					<td>薄板镀锌线</td>
                    <td align="center"><input type="radio" class="i-checks form-control" name="bbdxqualified" <c:if test="${hygiene.bbdxqualified==null||hygiene.bbdxqualified==1}">checked="checked"</c:if> value="1"/></td>
					<td align="center"><input type="radio" class="i-checks form-control" name="bbdxqualified" <c:if test="${hygiene.bbdxqualified==0}">checked="checked"</c:if> value="0"/></td>
					<td><form:input path="bbdxsituation" htmlEscape="false" maxlength="100"    class="form-control"/></td>
					<td><form:input path="bbdxcontent" htmlEscape="false" maxlength="100"    class="form-control"/></td>
					<td><input id="bbdxdate" name="bbdxdate" type="text" maxlength="20" class="laydate-icon form-control layer-date mydate" value="<fmt:formatDate value="${hygiene.bbdxdate}" pattern="yyyy-MM-dd"/>"/></td>
					<td><form:input path="bbdxresult" htmlEscape="false" maxlength="100"    class="form-control"/></td>
					<td><form:textarea path="bbdxcomment" htmlEscape="false" maxlength="100"    class="form-control"/></td>
					<td><input type="hidden" id="bbdxfile" name="bbdxfile" value="${hygiene.bbdxfile}"/>
                        <ol style="line-height: 20px; padding: 10px; padding-left:40px;" id="filecontentbbdxfile" class="filecontent">
                            <c:if test="${hygiene.bbdxfile!=null&&hygiene.bbdxfile!=''}">
                                <li><a href="javascript:;" target="_blank" vl="${hygiene.bbdxfile}" onclick="commonFileDownLoad(this)"><span>${fns:getFileName(hygiene.bbdxfile)}</span></a> &nbsp; <a href="javascript:;" name="removeFile" vl="filecontentbbdxfile" vl1="bbdxfile"><i class="glyphicon glyphicon-remove text-danger"></i></a></li>
                            </c:if>
                        </ol>
                        <button type="button" class="btn btn-primary" name="uploadF" vl="bbdxfile"><i class="glyphicon glyphicon-open"></i>&nbsp;上传</button>
                    </td>
				</tr>
				<tr align="center" name="changshu">
					<td>薄板彩涂线</td>
                    <td align="center"><input type="radio" class="i-checks form-control" name="bbctqualified" <c:if test="${hygiene.bbctqualified==null||hygiene.bbctqualified==1}">checked="checked"</c:if> value="1"/></td>
                    <td align="center"><input type="radio" class="i-checks form-control" name="bbctqualified" <c:if test="${hygiene.bbctqualified==0}">checked="checked"</c:if> value="0"/></td>
					<td><form:input path="bbctsituation" htmlEscape="false" maxlength="100"    class="form-control"/></td>
					<td><form:input path="bbctcontent" htmlEscape="false" maxlength="100"    class="form-control"/></td>
					<td><input id="bbctdate" name="bbctdate" type="text" maxlength="20" class="laydate-icon form-control layer-date mydate" value="<fmt:formatDate value="${hygiene.bbctdate}" pattern="yyyy-MM-dd"/>"/></td>
					<td><form:input path="bbctresult" htmlEscape="false" maxlength="100"    class="form-control"/></td>
					<td><form:textarea path="bbctcomment" htmlEscape="false" maxlength="100"    class="form-control"/></td>
					<td><input type="hidden" id="bbctfile" name="bbctfile" value="${hygiene.bbctfile}"/>
                        <ol style="line-height: 20px; padding: 10px; padding-left:40px;" id="filecontentbbctfile" class="filecontent">
                            <c:if test="${hygiene.bbctfile!=null&&hygiene.bbctfile!=''}">
                                <li><a href="javascript:;" target="_blank" vl="${hygiene.bbctfile}" onclick="commonFileDownLoad(this)"><span>${fns:getFileName(hygiene.bbctfile)}</span></a> &nbsp; <a href="javascript:;" name="removeFile" vl="filecontentbbctfile" vl1="bbctfile"><i class="glyphicon glyphicon-remove text-danger"></i></a></li>
                            </c:if>
                        </ol>
                        <button type="button" class="btn btn-primary" name="uploadF" vl="bbctfile"><i class="glyphicon glyphicon-open"></i>&nbsp;上传</button>
                    </td>
				</tr>
				<tr align="center" name="changshu">
					<td>薄板公辅</td>
                    <td align="center"><input type="radio" class="i-checks form-control" name="bbgpqualified" <c:if test="${hygiene.bbgpqualified==null||hygiene.bbgpqualified==1}">checked="checked"</c:if> value="1"/></td>
                    <td align="center"><input type="radio" class="i-checks form-control" name="bbgpqualified" <c:if test="${hygiene.bbgpqualified==0}">checked="checked"</c:if> value="0"/></td>
					<td><form:input path="bbgpsituation" htmlEscape="false" maxlength="100"    class="form-control"/></td>
					<td><form:input path="bbgpcontent" htmlEscape="false" maxlength="100"    class="form-control"/></td>
					<td><input id="bbgpdate" name="bbgpdate" type="text" maxlength="20" class="laydate-icon form-control layer-date mydate" value="<fmt:formatDate value="${hygiene.bbgpdate}" pattern="yyyy-MM-dd"/>"/></td>
					<td><form:input path="bbgpresult" htmlEscape="false" maxlength="100"    class="form-control"/></td>
					<td><form:textarea path="bbgpcomment" htmlEscape="false" maxlength="100"    class="form-control"/></td>
					<td><input type="hidden" id="bbgpfile" name="bbgpfile" value="${hygiene.bbgpfile}"/>
                        <ol style="line-height: 20px; padding: 10px; padding-left:40px;" id="filecontentbbgpfile" class="filecontent">
                            <c:if test="${hygiene.bbgpfile!=null&&hygiene.bbgpfile!=''}">
                                <li><a href="javascript:;" target="_blank" vl="${hygiene.bbgpfile}" onclick="commonFileDownLoad(this)"><span>${fns:getFileName(hygiene.bbgpfile)}</span></a> &nbsp; <a href="javascript:;" name="removeFile" vl="filecontentbbgpfile" vl1="bbgpfile"><i class="glyphicon glyphicon-remove text-danger"></i></a></li>
                            </c:if>
                        </ol>
                        <button type="button" class="btn btn-primary" name="uploadF" vl="bbgpfile"><i class="glyphicon glyphicon-open"></i>&nbsp;上传</button>
                    </td>
				</tr>
				<tr align="center" name="changshu">
					<td>薄板大仓库</td>
                    <td align="center"><input type="radio" class="i-checks form-control" name="bbdckqualified" <c:if test="${hygiene.bbdckqualified==null||hygiene.bbdckqualified==1}">checked="checked"</c:if> value="1"/></td>
                    <td align="center"><input type="radio" class="i-checks form-control" name="bbdckqualified" <c:if test="${hygiene.bbdckqualified==0}">checked="checked"</c:if> value="0"/></td>
					<td><form:input path="bbdcksituation" htmlEscape="false" maxlength="100"    class="form-control"/></td>
					<td><form:input path="bbdckcontent" htmlEscape="false" maxlength="100"    class="form-control"/></td>
					<td><input id="bbdckdate" name="bbdckdate" type="text" maxlength="20" class="laydate-icon form-control layer-date mydate" value="<fmt:formatDate value="${hygiene.bbdckdate}" pattern="yyyy-MM-dd"/>"/></td>
					<td><form:input path="bbdckresult" htmlEscape="false" maxlength="100"    class="form-control"/></td>
					<td><form:textarea path="bbdckcomment" htmlEscape="false" maxlength="100"    class="form-control"/></td>
					<td><input type="hidden" id="bbdckfile" name="bbdckfile" value="${hygiene.bbdckfile}"/>
                        <ol style="line-height: 20px; padding: 10px; padding-left:40px;" id="filecontentbbdckfile" class="filecontent">
                            <c:if test="${hygiene.bbdckfile!=null&&hygiene.bbdckfile!=''}">
                                <li><a href="javascript:;" target="_blank" vl="${hygiene.bbdckfile}" onclick="commonFileDownLoad(this)"><span>${fns:getFileName(hygiene.bbdckfile)}</span></a> &nbsp; <a href="javascript:;" name="removeFile" vl="filecontentbbdckfile" vl1="bbdckfile"><i class="glyphicon glyphicon-remove text-danger"></i></a></li>
                            </c:if>
                        </ol>
                        <button type="button" class="btn btn-primary" name="uploadF" vl="bbdckfile"><i class="glyphicon glyphicon-open"></i>&nbsp;上传</button>
                    </td>
				</tr>
				<tr align="center" name="changshu">
					<td>薄板辅料库</td>
                    <td align="center"><input type="radio" class="i-checks form-control" name="bbflqualified" <c:if test="${hygiene.bbflqualified==null||hygiene.bbflqualified==1}">checked="checked"</c:if> value="1"/></td>
                    <td align="center"><input type="radio" class="i-checks form-control" name="bbflqualified" <c:if test="${hygiene.bbflqualified==0}">checked="checked"</c:if> value="0"/></td>
					<td><form:input path="bbflsituation" htmlEscape="false" maxlength="100"    class="form-control"/></td>
					<td><form:input path="bbflcontent" htmlEscape="false" maxlength="100"    class="form-control"/></td>
					<td><input id="bbfldate" name="bbfldate" type="text" maxlength="20" class="laydate-icon form-control layer-date mydate" value="<fmt:formatDate value="${hygiene.bbfldate}" pattern="yyyy-MM-dd"/>"/></td>
					<td><form:input path="bbflresult" htmlEscape="false" maxlength="100"    class="form-control"/></td>
					<td><form:textarea path="bbflcomment" htmlEscape="false" maxlength="100"    class="form-control"/></td>
					<td><input type="hidden" id="bbflfile" name="bbflfile" value="${hygiene.bbflfile}"/>
                        <ol style="line-height: 20px; padding: 10px; padding-left:40px;" id="filecontentbbflfile" class="filecontent">
                            <c:if test="${hygiene.bbflfile!=null&&hygiene.bbflfile!=''}">
                                <li><a href="javascript:;" target="_blank" vl="${hygiene.bbflfile}" onclick="commonFileDownLoad(this)"><span>${fns:getFileName(hygiene.bbflfile)}</span></a> &nbsp; <a href="javascript:;" name="removeFile" vl="filecontentbbflfile" vl1="bbflfile"><i class="glyphicon glyphicon-remove text-danger"></i></a></li>
                            </c:if>
                        </ol>
                        <button type="button" class="btn btn-primary" name="uploadF" vl="bbflfile"><i class="glyphicon glyphicon-open"></i>&nbsp;上传</button>
                    </td>
				</tr>
				<tr align="center" name="changshu">
					<td>薄板废料库</td>
                    <td align="center"><input type="radio" class="i-checks form-control" name="bbfeilqualified" <c:if test="${hygiene.bbfeilqualified==null||hygiene.bbfeilqualified==1}">checked="checked"</c:if> value="1"/></td>
                    <td align="center"><input type="radio" class="i-checks form-control" name="bbfeilqualified" <c:if test="${hygiene.bbfeilqualified==0}">checked="checked"</c:if> value="0"/></td>
					<td><form:input path="bbfeilsituation" htmlEscape="false" maxlength="100"    class="form-control"/></td>
					<td><form:input path="bbfeilcontent" htmlEscape="false" maxlength="100"    class="form-control"/></td>
					<td><input id="bbfeildate" name="bbfeildate" type="text" maxlength="20" class="laydate-icon form-control layer-date mydate" value="<fmt:formatDate value="${hygiene.bbfeildate}" pattern="yyyy-MM-dd"/>"/></td>
					<td><form:input path="bbfeilresult" htmlEscape="false" maxlength="100"    class="form-control"/></td>
					<td><form:textarea path="bbfeilcomment" htmlEscape="false" maxlength="100"    class="form-control"/></td>
					<td><input type="hidden" id="bbfeilfile" name="bbfeilfile" value="${hygiene.bbfeilfile}"/>
                        <ol style="line-height: 20px; padding: 10px; padding-left:40px;" id="filecontentbbfeilfile" class="filecontent">
                            <c:if test="${hygiene.bbfeilfile!=null&&hygiene.bbfeilfile!=''}">
                                <li><a href="javascript:;" target="_blank" vl="${hygiene.bbfeilfile}" onclick="commonFileDownLoad(this)"><span>${fns:getFileName(hygiene.bbfeilfile)}</span></a> &nbsp; <a href="javascript:;" name="removeFile" vl="filecontentbbfeilfile" vl1="bbfeilfile"><i class="glyphicon glyphicon-remove text-danger"></i></a></li>
                            </c:if>
                        </ol>
                        <button type="button" class="btn btn-primary" name="uploadF" vl="bbfeilfile"><i class="glyphicon glyphicon-open"></i>&nbsp;上传</button>
                    </td>
				</tr>
				<tr align="center" name="changshu">
					<td>薄板办公楼</td>
                    <td align="center"><input type="radio" class="i-checks form-control" name="bbbglqualified" <c:if test="${hygiene.bbbglqualified==null||hygiene.bbbglqualified==1}">checked="checked"</c:if> value="1"/></td>
                    <td align="center"><input type="radio" class="i-checks form-control" name="bbbglqualified" <c:if test="${hygiene.bbbglqualified==0}">checked="checked"</c:if> value="0"/></td>
					<td><form:input path="bbbglsituation" htmlEscape="false" maxlength="100"    class="form-control"/></td>
					<td><form:input path="bbbglcontent" htmlEscape="false" maxlength="100"    class="form-control"/></td>
					<td><input id="bbbgldate" name="bbbgldate" type="text" maxlength="20" class="laydate-icon form-control layer-date mydate" value="<fmt:formatDate value="${hygiene.bbbgldate}" pattern="yyyy-MM-dd"/>"/></td>
					<td><form:input path="bbbglresult" htmlEscape="false" maxlength="100"    class="form-control"/></td>
					<td><form:textarea path="bbbglcomment" htmlEscape="false" maxlength="100"    class="form-control"/></td>
					<td><input type="hidden" id="bbbglfile" name="bbbglfile" value="${hygiene.bbbglfile}"/>
                        <ol style="line-height: 20px; padding: 10px; padding-left:40px;" id="filecontentbbbglfile" class="filecontent">
                            <c:if test="${hygiene.bbbglfile!=null&&hygiene.bbbglfile!=''}">
                                <li><a href="javascript:;" target="_blank" vl="${hygiene.bbbglfile}" onclick="commonFileDownLoad(this)"><span>${fns:getFileName(hygiene.bbbglfile)}</span></a> &nbsp; <a href="javascript:;" name="removeFile" vl="filecontentbbbglfile" vl1="bbbglfile"><i class="glyphicon glyphicon-remove text-danger"></i></a></li>
                            </c:if>
                        </ol>
                        <button type="button" class="btn btn-primary" name="uploadF" vl="bbbglfile"><i class="glyphicon glyphicon-open"></i>&nbsp;上传</button>
                    </td>
				</tr>
				<tr align="center" name="changshu">
					<td>薄板道路</td>
                    <td align="center"><input type="radio" class="i-checks form-control" name="bbldqualified" <c:if test="${hygiene.bbldqualified==null||hygiene.bbldqualified==1}">checked="checked"</c:if> value="1"/></td>
                    <td align="center"><input type="radio" class="i-checks form-control" name="bbldqualified" <c:if test="${hygiene.bbldqualified==0}">checked="checked"</c:if> value="0"/></td>
					<td><form:input path="bbldsituation" htmlEscape="false" maxlength="100"    class="form-control"/></td>
					<td><form:input path="bbldcontent" htmlEscape="false" maxlength="100"    class="form-control"/></td>
					<td><input id="bblddate" name="bblddate" type="text" maxlength="20" class="laydate-icon form-control layer-date mydate" value="<fmt:formatDate value="${hygiene.bblddate}" pattern="yyyy-MM-dd"/>"/></td>
					<td><form:input path="bbldresult" htmlEscape="false" maxlength="100"    class="form-control"/></td>
					<td><form:textarea path="bbldcomment" htmlEscape="false" maxlength="100"    class="form-control"/></td>
					<td><input type="hidden" id="bbldfile" name="bbldfile" value="${hygiene.bbldfile}"/>
                        <ol style="line-height: 20px; padding: 10px; padding-left:40px;" id="filecontentbbldfile" class="filecontent">
                            <c:if test="${hygiene.bbldfile!=null&&hygiene.bbldfile!=''}">
                                <li><a href="javascript:;" target="_blank" vl="${hygiene.bbldfile}" onclick="commonFileDownLoad(this)"><span>${fns:getFileName(hygiene.bbldfile)}</span></a> &nbsp; <a href="javascript:;" name="removeFile" vl="filecontentbbldfile" vl1="bbldfile"><i class="glyphicon glyphicon-remove text-danger"></i></a></li>
                            </c:if>
                        </ol>
                        <button type="button" class="btn btn-primary" name="uploadF" vl="bbldfile"><i class="glyphicon glyphicon-open"></i>&nbsp;上传</button>
                    </td>
				</tr>
				<tr align="center" name="changshu">
					<td>酸洗产线</td>
                    <td align="center"><input type="radio" class="i-checks form-control" name="bbsxqualified" <c:if test="${hygiene.bbsxqualified==null||hygiene.bbsxqualified==1}">checked="checked"</c:if> value="1"/></td>
                    <td align="center"><input type="radio" class="i-checks form-control" name="bbsxqualified" <c:if test="${hygiene.bbsxqualified==0}">checked="checked"</c:if> value="0"/></td>
					<td><form:input path="bbsxsituation" htmlEscape="false" maxlength="100"    class="form-control"/></td>
					<td><form:input path="bbsxcontent" htmlEscape="false" maxlength="100"    class="form-control"/></td>
					<td><input id="bbsxdate" name="bbsxdate" type="text" maxlength="20" class="laydate-icon form-control layer-date mydate" value="<fmt:formatDate value="${hygiene.bbsxdate}" pattern="yyyy-MM-dd"/>"/></td>
					<td><form:input path="bbsxresult" htmlEscape="false" maxlength="100"    class="form-control"/></td>
					<td><form:textarea path="bbsxcomment" htmlEscape="false" maxlength="100"    class="form-control"/></td>
					<td><input type="hidden" id="bbsxfile" name="bbsxfile" value="${hygiene.bbsxfile}"/>
                        <ol style="line-height: 20px; padding: 10px; padding-left:40px;" id="filecontentbbsxfile" class="filecontent">
                            <c:if test="${hygiene.bbsxfile!=null&&hygiene.bbsxfile!=''}">
                                <li><a href="javascript:;" target="_blank" vl="${hygiene.bbsxfile}" onclick="commonFileDownLoad(this)"><span>${fns:getFileName(hygiene.bbsxfile)}</span></a> &nbsp; <a href="javascript:;" name="removeFile" vl="filecontentbbsxfile" vl1="bbsxfile"><i class="glyphicon glyphicon-remove text-danger"></i></a></li>
                            </c:if>
                        </ol>
                        <button type="button" class="btn btn-primary" name="uploadF" vl="bbsxfile"><i class="glyphicon glyphicon-open"></i>&nbsp;上传</button>
                    </td>
				</tr>
				<tr align="center" name="changshu">
					<td>1号轧机</td>
                    <td align="center"><input type="radio" class="i-checks form-control" name="bbyhzjqualified" <c:if test="${hygiene.bbyhzjqualified==null||hygiene.bbyhzjqualified==1}">checked="checked"</c:if> value="1"/></td>
                    <td align="center"><input type="radio" class="i-checks form-control" name="bbyhzjqualified" <c:if test="${hygiene.bbyhzjqualified==0}">checked="checked"</c:if> value="0"/></td>
					<td><form:input path="bbyhzjsituation" htmlEscape="false" maxlength="100"    class="form-control"/></td>
					<td><form:input path="bbyhzjcontent" htmlEscape="false" maxlength="100"    class="form-control"/></td>
					<td><input id="bbyhzjdate" name="bbyhzjdate" type="text" maxlength="20" class="laydate-icon form-control layer-date mydate" value="<fmt:formatDate value="${hygiene.bbyhzjdate}" pattern="yyyy-MM-dd"/>"/></td>
					<td><form:input path="bbyhzjresult" htmlEscape="false" maxlength="100"    class="form-control"/></td>
					<td><form:textarea path="bbyhzjcomment" htmlEscape="false" maxlength="100"    class="form-control"/></td>
					<td><input type="hidden" id="bbyhzjfile" name="bbyhzjfile" value="${hygiene.bbyhzjfile}"/>
                        <ol style="line-height: 20px; padding: 10px; padding-left:40px;" id="filecontentbbyhzjfile" class="filecontent">
                            <c:if test="${hygiene.bbyhzjfile!=null&&hygiene.bbyhzjfile!=''}">
                                <li><a href="javascript:;" target="_blank" vl="${hygiene.bbyhzjfile}" onclick="commonFileDownLoad(this)"><span>${fns:getFileName(hygiene.bbyhzjfile)}</span></a> &nbsp; <a href="javascript:;" name="removeFile" vl="filecontentbbyhzjfile" vl1="bbyhzjfile"><i class="glyphicon glyphicon-remove text-danger"></i></a></li>
                            </c:if>
                        </ol>
                        <button type="button" class="btn btn-primary" name="uploadF" vl="bbyhzjfile"><i class="glyphicon glyphicon-open"></i>&nbsp;上传</button>
                    </td>
				</tr>
				<tr align="center" name="changshu">
					<td>2号轧机</td>
                    <td align="center"><input type="radio" class="i-checks form-control" name="bbehzjqualified" <c:if test="${hygiene.bbehzjqualified==null||hygiene.bbehzjqualified==1}">checked="checked"</c:if> value="1"/></td>
                    <td align="center"><input type="radio" class="i-checks form-control" name="bbehzjqualified" <c:if test="${hygiene.bbehzjqualified==0}">checked="checked"</c:if> value="0"/></td>
					<td><form:input path="bbehzjsituation" htmlEscape="false" maxlength="100"    class="form-control"/></td>
					<td><form:input path="bbehzjcontent" htmlEscape="false" maxlength="100"    class="form-control"/></td>
					<td><input id="bbehzjdate" name="bbehzjdate" type="text" maxlength="20" class="laydate-icon form-control layer-date mydate" value="<fmt:formatDate value="${hygiene.bbehzjdate}" pattern="yyyy-MM-dd"/>"/></td>
					<td><form:input path="bbehzjresult" htmlEscape="false" maxlength="100"    class="form-control"/></td>
					<td><form:textarea path="bbehzjcomment" htmlEscape="false" maxlength="100"    class="form-control"/></td>
					<td><input type="hidden" id="bbehzjfile" name="bbehzjfile" value="${hygiene.bbehzjfile}"/>
                        <ol style="line-height: 20px; padding: 10px; padding-left:40px;" id="filecontentbbehzjfile" class="filecontent">
                            <c:if test="${hygiene.bbehzjfile!=null&&hygiene.bbehzjfile!=''}">
                                <li><a href="javascript:;" target="_blank" vl="${hygiene.bbehzjfile}" onclick="commonFileDownLoad(this)"><span>${fns:getFileName(hygiene.bbehzjfile)}</span></a> &nbsp; <a href="javascript:;" name="removeFile" vl="filecontentbbehzjfile" vl1="bbehzjfile"><i class="glyphicon glyphicon-remove text-danger"></i></a></li>
                            </c:if>
                        </ol>
                        <button type="button" class="btn btn-primary" name="uploadF" vl="bbehzjfile"><i class="glyphicon glyphicon-open"></i>&nbsp;上传</button>
                    </td>
				</tr>
				<tr align="center" name="changshu">
					<td>15万吨镀锌</td>
                    <td align="center"><input type="radio" class="i-checks form-control" name="bbywdxqualified" <c:if test="${hygiene.bbywdxqualified==null||hygiene.bbywdxqualified==1}">checked="checked"</c:if> value="1"/></td>
                    <td align="center"><input type="radio" class="i-checks form-control" name="bbywdxqualified" <c:if test="${hygiene.bbywdxqualified==0}">checked="checked"</c:if> value="0"/></td>
					<td><form:input path="bbywdxsituation" htmlEscape="false" maxlength="100"    class="form-control"/></td>
					<td><form:input path="bbywdxcontent" htmlEscape="false" maxlength="100"    class="form-control"/></td>
					<td><input id="bbywdxdate" name="bbywdxdate" type="text" maxlength="20" class="laydate-icon form-control layer-date mydate" value="<fmt:formatDate value="${hygiene.bbywdxdate}" pattern="yyyy-MM-dd"/>"/></td>
					<td><form:input path="bbywdxresult" htmlEscape="false" maxlength="100"    class="form-control"/></td>
					<td><form:textarea path="bbywdxcomment" htmlEscape="false" maxlength="100"    class="form-control"/></td>
					<td><input type="hidden" id="bbywdxfile" name="bbywdxfile" value="${hygiene.bbywdxfile}"/>
                        <ol style="line-height: 20px; padding: 10px; padding-left:40px;" id="filecontentbbywdxfile" class="filecontent">
                            <c:if test="${hygiene.bbywdxfile!=null&&hygiene.bbywdxfile!=''}">
                                <li><a href="javascript:;" target="_blank" vl="${hygiene.bbywdxfile}" onclick="commonFileDownLoad(this)"><span>${fns:getFileName(hygiene.bbywdxfile)}</span></a> &nbsp; <a href="javascript:;" name="removeFile" vl="filecontentbbywdxfile" vl1="bbywdxfile"><i class="glyphicon glyphicon-remove text-danger"></i></a></li>
                            </c:if>
                        </ol>
                        <button type="button" class="btn btn-primary" name="uploadF" vl="bbywdxfile"><i class="glyphicon glyphicon-open"></i>&nbsp;上传</button>
                    </td>
				</tr>
				<tr align="center" name="changshu">
					<td>25万吨镀锌</td>
                    <td align="center"><input type="radio" class="i-checks form-control" name="bbewdxqualified" <c:if test="${hygiene.bbewdxqualified==null||hygiene.bbewdxqualified==1}">checked="checked"</c:if> value="1"/></td>
                    <td align="center"><input type="radio" class="i-checks form-control" name="bbewdxqualified" <c:if test="${hygiene.bbewdxqualified==0}">checked="checked"</c:if> value="0"/></td>
					<td><form:input path="bbewdxsituation" htmlEscape="false" maxlength="100"    class="form-control"/></td>
					<td><form:input path="bbewdxcontent" htmlEscape="false" maxlength="100"    class="form-control"/></td>
					<td><input id="bbewdxdate" name="bbewdxdate" type="text" maxlength="20" class="laydate-icon form-control layer-date mydate" value="<fmt:formatDate value="${hygiene.bbewdxdate}" pattern="yyyy-MM-dd"/>"/></td>
					<td><form:input path="bbewdxresult" htmlEscape="false" maxlength="100"    class="form-control"/></td>
					<td><form:textarea path="bbewdxcomment" htmlEscape="false" maxlength="100"    class="form-control"/></td>
					<td><input type="hidden" id="bbewdxfile" name="bbewdxfile" value="${hygiene.bbewdxfile}"/>
                        <ol style="line-height: 20px; padding: 10px; padding-left:40px;" id="filecontentbbewdxfile" class="filecontent">
                            <c:if test="${hygiene.bbewdxfile!=null&&hygiene.bbewdxfile!=''}">
                                <li><a href="javascript:;" target="_blank" vl="${hygiene.bbewdxfile}" onclick="commonFileDownLoad(this)"><span>${fns:getFileName(hygiene.bbewdxfile)}</span></a> &nbsp; <a href="javascript:;" name="removeFile" vl="filecontentbbewdxfile" vl1="bbewdxfile"><i class="glyphicon glyphicon-remove text-danger"></i></a></li>
                            </c:if>
                        </ol>
                        <button type="button" class="btn btn-primary" name="uploadF" vl="bbewdxfile"><i class="glyphicon glyphicon-open"></i>&nbsp;上传</button>
                    </td>
				</tr>
				<tr align="center" name="changshu">
					<td>克罗德公辅</td>
                    <td align="center"><input type="radio" class="i-checks form-control" name="kldgpqualified" <c:if test="${hygiene.kldgpqualified==null||hygiene.kldgpqualified==1}">checked="checked"</c:if> value="1"/></td>
                    <td align="center"><input type="radio" class="i-checks form-control" name="kldgpqualified" <c:if test="${hygiene.kldgpqualified==0}">checked="checked"</c:if> value="0"/></td>
					<td><form:input path="kldgpsituation" htmlEscape="false" maxlength="100"    class="form-control"/></td>
					<td><form:input path="kldgpcontent" htmlEscape="false" maxlength="100"    class="form-control"/></td>
					<td><input id="kldgpdate" name="kldgpdate" type="text" maxlength="20" class="laydate-icon form-control layer-date mydate" value="<fmt:formatDate value="${hygiene.kldgpdate}" pattern="yyyy-MM-dd"/>"/></td>
					<td><form:input path="kldgpresult" htmlEscape="false" maxlength="100"    class="form-control"/></td>
					<td><form:input path="kldgpcomment" htmlEscape="false" maxlength="100"    class="form-control"/></td>
					<td><input type="hidden" id="kldgpfile" name="kldgpfile" value="${hygiene.kldgpfile}"/>
                        <ol style="line-height: 20px; padding: 10px; padding-left:40px;" id="filecontentkldgpfile" class="filecontent">
                            <c:if test="${hygiene.kldgpfile!=null&&hygiene.kldgpfile!=''}">
                                <li><a href="javascript:;" target="_blank" vl="${hygiene.kldgpfile}" onclick="commonFileDownLoad(this)"><span>${fns:getFileName(hygiene.kldgpfile)}</span></a> &nbsp; <a href="javascript:;" name="removeFile" vl="filecontentkldgpfile" vl1="kldgpfile"><i class="glyphicon glyphicon-remove text-danger"></i></a></li>
                            </c:if>
                        </ol>
                        <button type="button" class="btn btn-primary" name="uploadF" vl="kldgpfile"><i class="glyphicon glyphicon-open"></i>&nbsp;上传</button>
                    </td>
				</tr>
				<tr align="center" name="changshu">
					<td>克罗德大仓库</td>
                    <td align="center"><input type="radio" class="i-checks form-control" name="klddckqualified" <c:if test="${hygiene.klddckqualified==null||hygiene.klddckqualified==1}">checked="checked"</c:if> value="1"/></td>
                    <td align="center"><input type="radio" class="i-checks form-control" name="klddckqualified" <c:if test="${hygiene.klddckqualified==0}">checked="checked"</c:if> value="0"/></td>
					<td><form:input path="klddcksituation" htmlEscape="false" maxlength="100"    class="form-control"/></td>
					<td><form:input path="klddckcontent" htmlEscape="false" maxlength="100"    class="form-control"/></td>
					<td><input id="klddckdate" name="klddckdate" type="text" maxlength="20" class="laydate-icon form-control layer-date mydate" value="<fmt:formatDate value="${hygiene.klddckdate}" pattern="yyyy-MM-dd"/>"/></td>
					<td><form:input path="klddckresult" htmlEscape="false" maxlength="100"    class="form-control"/></td>
					<td><form:textarea path="klddckcomment" htmlEscape="false" maxlength="100"    class="form-control"/></td>
					<td><input type="hidden" id="klddckfile" name="klddckfile" value="${hygiene.klddckfile}"/>
                        <ol style="line-height: 20px; padding: 10px; padding-left:40px;" id="filecontentklddckfile" class="filecontent">
                            <c:if test="${hygiene.klddckfile!=null&&hygiene.klddckfile!=''}">
                                <li><a href="javascript:;" target="_blank" vl="${hygiene.klddckfile}" onclick="commonFileDownLoad(this)"><span>${fns:getFileName(hygiene.klddckfile)}</span></a> &nbsp; <a href="javascript:;" name="removeFile" vl="filecontentklddckfile" vl1="klddckfile"><i class="glyphicon glyphicon-remove text-danger"></i></a></li>
                            </c:if>
                        </ol>
                        <button type="button" class="btn btn-primary" name="uploadF" vl="klddckfile"><i class="glyphicon glyphicon-open"></i>&nbsp;上传</button>
                    </td>
				</tr>
				<tr align="center" name="changshu">
					<td>克罗德辅料库</td>
                    <td align="center"><input type="radio" class="i-checks form-control" name="kldflqualified" <c:if test="${hygiene.kldflqualified==null||hygiene.kldflqualified==1}">checked="checked"</c:if> value="1"/></td>
                    <td align="center"><input type="radio" class="i-checks form-control" name="kldflqualified" <c:if test="${hygiene.kldflqualified==0}">checked="checked"</c:if> value="0"/></td>
					<td><form:input path="kldflsituation" htmlEscape="false" maxlength="100"    class="form-control"/></td>
					<td><form:input path="kldflcontent" htmlEscape="false" maxlength="100"    class="form-control"/></td>
					<td><input id="kldfldate" name="kldfldate" type="text" maxlength="20" class="laydate-icon form-control layer-date mydate" value="<fmt:formatDate value="${hygiene.kldfldate}" pattern="yyyy-MM-dd"/>"/></td>
					<td><form:input path="kldflresult" htmlEscape="false" maxlength="100"    class="form-control"/></td>
					<td><form:textarea path="kldflcomment" htmlEscape="false" maxlength="100"    class="form-control"/></td>
					<td><input type="hidden" id="kldflfile" name="kldflfile" value="${hygiene.kldflfile}"/>
                        <ol style="line-height: 20px; padding: 10px; padding-left:40px;" id="filecontentkldflfile" class="filecontent">
                            <c:if test="${hygiene.kldflfile!=null&&hygiene.kldflfile!=''}">
                                <li><a href="javascript:;" target="_blank" vl="${hygiene.kldflfile}" onclick="commonFileDownLoad(this)"><span>${fns:getFileName(hygiene.kldflfile)}</span></a> &nbsp; <a href="javascript:;" name="removeFile" vl="filecontentkldflfile" vl1="kldflfile"><i class="glyphicon glyphicon-remove text-danger"></i></a></li>
                            </c:if>
                        </ol>
                        <button type="button" class="btn btn-primary" name="uploadF" vl="kldflfile"><i class="glyphicon glyphicon-open"></i>&nbsp;上传</button>
                    </td>
				</tr>
				<tr align="center" name="changshu">
					<td>克罗德废料库</td>
                    <td align="center"><input type="radio" class="i-checks form-control" name="kldfeilqualified" <c:if test="${hygiene.kldfeilqualified==null||hygiene.kldfeilqualified==1}">checked="checked"</c:if> value="1"/></td>
                    <td align="center"><input type="radio" class="i-checks form-control" name="kldfeilqualified" <c:if test="${hygiene.kldfeilqualified==0}">checked="checked"</c:if> value="0"/></td>
					<td><form:input path="kldfeilsituation" htmlEscape="false" maxlength="100"    class="form-control"/></td>
					<td><form:input path="kldfeilcontent" htmlEscape="false" maxlength="100"    class="form-control"/></td>
					<td><input id="kldfeildate" name="kldfeildate" type="text" maxlength="20" class="laydate-icon form-control layer-date mydate" value="<fmt:formatDate value="${hygiene.kldfeildate}" pattern="yyyy-MM-dd"/>"/></td>
					<td><form:input path="kldfeilresult" htmlEscape="false" maxlength="100"    class="form-control"/></td>
					<td><form:textarea path="kldfeilcomment" htmlEscape="false" maxlength="100"    class="form-control"/></td>
					<td><input type="hidden" id="kldfeilfile" name="kldfeilfile" value="${hygiene.kldfeilfile}"/>
                        <ol style="line-height: 20px; padding: 10px; padding-left:40px;" id="filecontentkldfeilfile" class="filecontent">
                            <c:if test="${hygiene.kldfeilfile!=null&&hygiene.kldfeilfile!=''}">
                                <li><a href="javascript:;" target="_blank" vl="${hygiene.kldfeilfile}" onclick="commonFileDownLoad(this)"><span>${fns:getFileName(hygiene.kldfeilfile)}</span></a> &nbsp; <a href="javascript:;" name="removeFile" vl="filecontentkldfeilfile" vl1="kldfeilfile"><i class="glyphicon glyphicon-remove text-danger"></i></a></li>
                            </c:if>
                        </ol>
                        <button type="button" class="btn btn-primary" name="uploadF" vl="kldfeilfile"><i class="glyphicon glyphicon-open"></i>&nbsp;上传</button>
                    </td>
				</tr>
				<tr align="center" name="changshu">
					<td>克罗德办公楼</td>
                    <td align="center"><input type="radio" class="i-checks form-control" name="kldbglqualified" <c:if test="${hygiene.kldbglqualified==null||hygiene.kldbglqualified==1}">checked="checked"</c:if> value="1"/></td>
                    <td align="center"><input type="radio" class="i-checks form-control" name="kldbglqualified" <c:if test="${hygiene.kldbglqualified==0}">checked="checked"</c:if> value="0"/></td>
					<td><form:input path="kldbglsituation" htmlEscape="false" maxlength="100"    class="form-control"/></td>
					<td><form:input path="kldbglcontent" htmlEscape="false" maxlength="100"    class="form-control"/></td>
					<td><input id="kldbgldate" name="kldbgldate" type="text" maxlength="20" class="laydate-icon form-control layer-date mydate" value="<fmt:formatDate value="${hygiene.kldbgldate}" pattern="yyyy-MM-dd"/>"/></td>
					<td><form:input path="kldbglresult" htmlEscape="false" maxlength="100"    class="form-control"/></td>
					<td><form:input path="kldbglcomment" htmlEscape="false" maxlength="100"    class="form-control"/></td>
					<td><input type="hidden" id="kldbglfile" name="kldbglfile" value="${hygiene.kldbglfile}"/>
                        <ol style="line-height: 20px; padding: 10px; padding-left:40px;" id="filecontentkldbglfile" class="filecontent">
                            <c:if test="${hygiene.kldbglfile!=null&&hygiene.kldbglfile!=''}">
                                <li><a href="javascript:;" target="_blank" vl="${hygiene.kldbglfile}" onclick="commonFileDownLoad(this)"><span>${fns:getFileName(hygiene.kldbglfile)}</span></a> &nbsp; <a href="javascript:;" name="removeFile" vl="filecontentkldbglfile" vl1="kldbglfile"><i class="glyphicon glyphicon-remove text-danger"></i></a></li>
                            </c:if>
                        </ol>
                        <button type="button" class="btn btn-primary" name="uploadF" vl="kldbglfile"><i class="glyphicon glyphicon-open"></i>&nbsp;上传</button>
                    </td>
				</tr>
				<tr align="center" name="changshu">
					<td>克罗德道路</td>
                    <td align="center"><input type="radio" class="i-checks form-control" name="klddlqualified" <c:if test="${hygiene.klddlqualified==null||hygiene.klddlqualified==1}">checked="checked"</c:if> value="1"/></td>
                    <td align="center"><input type="radio" class="i-checks form-control" name="klddlqualified" <c:if test="${hygiene.klddlqualified==0}">checked="checked"</c:if> value="0"/></td>
					<td><form:input path="klddlsituation" htmlEscape="false" maxlength="100"    class="form-control"/></td>
					<td><form:input path="klddlcontent" htmlEscape="false" maxlength="100"    class="form-control"/></td>
					<td><input id="klddldate" name="klddldate" type="text" maxlength="20" class="laydate-icon form-control layer-date mydate" value="<fmt:formatDate value="${hygiene.klddldate}" pattern="yyyy-MM-dd"/>"/></td>
					<td><form:input path="klddlresult" htmlEscape="false" maxlength="100"    class="form-control"/></td>
					<td><form:textarea path="klddlcomment" htmlEscape="false" maxlength="100"    class="form-control"/></td>
					<td><input type="hidden" id="klddlfile" name="klddlfile" value="${hygiene.klddlfile}"/>
                        <ol style="line-height: 20px; padding: 10px; padding-left:40px;" id="filecontentklddlfile" class="filecontent">
                            <c:if test="${hygiene.klddlfile!=null&&hygiene.klddlfile!=''}">
                                <li><a href="javascript:;" target="_blank" vl="${hygiene.klddlfile}" onclick="commonFileDownLoad(this)"><span>${fns:getFileName(hygiene.klddlfile)}</span></a> &nbsp; <a href="javascript:;" name="removeFile" vl="filecontentklddlfile" vl1="klddlfile"><i class="glyphicon glyphicon-remove text-danger"></i></a></li>
                            </c:if>
                        </ol>
                        <button type="button" class="btn btn-primary" name="uploadF" vl="klddlfile"><i class="glyphicon glyphicon-open"></i>&nbsp;上传</button>
                    </td>
				</tr>


				<tr align="center" name="ningbo">
					<td>浙华办公室</td>
                    <td align="center"><input type="radio" class="i-checks form-control" name="zhbglqualified" <c:if test="${hygiene.zhbglqualified==null||hygiene.zhbglqualified==1}">checked="checked"</c:if> value="1"/></td>
                    <td align="center"><input type="radio" class="i-checks form-control" name="zhbglqualified" <c:if test="${hygiene.zhbglqualified==0}">checked="checked"</c:if> value="0"/></td>
					<td><form:input path="zhbglsituation" htmlEscape="false" maxlength="100"    class="form-control"/></td>
					<td><form:input path="zhbglcontent" htmlEscape="false" maxlength="100"    class="form-control"/></td>
					<td><input id="zhbgldate" name="zhbgldate" type="text" maxlength="20" class="laydate-icon form-control layer-date mydate" value="<fmt:formatDate value="${hygiene.zhbgldate}" pattern="yyyy-MM-dd"/>"/></td>
					<td><form:input path="zhbglresult" htmlEscape="false" maxlength="100"    class="form-control"/></td>
					<td><form:textarea path="zhbglcomment" htmlEscape="false" maxlength="100"    class="form-control"/></td>
					<td><input type="hidden" id="zhbglfile" name="zhbglfile" value="${hygiene.zhbglfile}"/>
                        <ol style="line-height: 20px; padding: 10px; padding-left:40px;" id="filecontentzhbglfile" class="filecontent">
                            <c:if test="${hygiene.zhbglfile!=null&&hygiene.zhbglfile!=''}">
                                <li><a href="javascript:;" target="_blank" vl="${hygiene.zhbglfile}" onclick="commonFileDownLoad(this)"><span>${fns:getFileName(hygiene.zhbglfile)}</span></a> &nbsp; <a href="javascript:;" name="removeFile" vl="filecontentzhbglfile" vl1="zhbglfile"><i class="glyphicon glyphicon-remove text-danger"></i></a></li>
                            </c:if>
                        </ol>
                        <button type="button" class="btn btn-primary" name="uploadF" vl="zhbglfile"><i class="glyphicon glyphicon-open"></i>&nbsp;上传</button>
                    </td>
				</tr>
				<tr align="center" name="ningbo">
					<td>生产办公室</td>
                    <td align="center"><input type="radio" class="i-checks form-control" name="scbgsqualified" <c:if test="${hygiene.scbgsqualified==null||hygiene.scbgsqualified==1}">checked="checked"</c:if> value="1"/></td>
                    <td align="center"><input type="radio" class="i-checks form-control" name="scbgsqualified" <c:if test="${hygiene.scbgsqualified==0}">checked="checked"</c:if> value="0"/></td>
					<td><form:input path="scbgssituation" htmlEscape="false" maxlength="100"    class="form-control"/></td>
					<td><form:input path="scbgscontent" htmlEscape="false" maxlength="100"    class="form-control"/></td>
					<td><input id="scbgsdate" name="scbgsdate" type="text" maxlength="20" class="laydate-icon form-control layer-date mydate" value="<fmt:formatDate value="${hygiene.scbgsdate}" pattern="yyyy-MM-dd"/>"/></td>
					<td><form:input path="scbgsresult" htmlEscape="false" maxlength="100"    class="form-control"/></td>
					<td><form:textarea path="scbgscomment" htmlEscape="false" maxlength="100"    class="form-control"/></td>
					<td><input type="hidden" id="scbgsfile" name="scbgsfile" value="${hygiene.scbgsfile}"/>
                        <ol style="line-height: 20px; padding: 10px; padding-left:40px;" id="filecontentscbgsfile" class="filecontent">
                            <c:if test="${hygiene.scbgsfile!=null&&hygiene.scbgsfile!=''}">
                                <li><a href="javascript:;" target="_blank" vl="${hygiene.scbgsfile}" onclick="commonFileDownLoad(this)"><span>${fns:getFileName(hygiene.scbgsfile)}</span></a> &nbsp; <a href="javascript:;" name="removeFile" vl="filecontentscbgsfile" vl1="scbgsfile"><i class="glyphicon glyphicon-remove text-danger"></i></a></li>
                            </c:if>
                        </ol>
                        <button type="button" class="btn btn-primary" name="uploadF" vl="scbgsfile"><i class="glyphicon glyphicon-open"></i>&nbsp;上传</button>
                    </td>
				</tr>
				<tr align="center" name="ningbo">
					<td>直缝车间</td>
                    <td align="center"><input type="radio" class="i-checks form-control" name="zfcjqualified" <c:if test="${hygiene.zfcjqualified==null||hygiene.zfcjqualified==1}">checked="checked"</c:if> value="1"/></td>
                    <td align="center"><input type="radio" class="i-checks form-control" name="zfcjqualified" <c:if test="${hygiene.zfcjqualified==0}">checked="checked"</c:if> value="0"/></td>
					<td><form:input path="zfcjsituation" htmlEscape="false" maxlength="100"    class="form-control"/></td>
					<td><form:input path="zfcjcontent" htmlEscape="false" maxlength="100"    class="form-control"/></td>
					<td><input id="zfcjdate" name="zfcjdate" type="text" maxlength="20" class="laydate-icon form-control layer-date mydate" value="<fmt:formatDate value="${hygiene.zfcjdate}" pattern="yyyy-MM-dd"/>"/></td>
					<td><form:input path="zfcjresult" htmlEscape="false" maxlength="100"    class="form-control"/></td>
					<td><form:textarea path="zfcjcomment" htmlEscape="false" maxlength="100"    class="form-control"/></td>
					<td><input type="hidden" id="zfcjfile" name="zfcjfile" value="${hygiene.zfcjfile}"/>
                        <ol style="line-height: 20px; padding: 10px; padding-left:40px;" id="filecontentzfcjfile" class="filecontent">
                            <c:if test="${hygiene.zfcjfile!=null&&hygiene.zfcjfile!=''}">
                                <li><a href="javascript:;" target="_blank" vl="${hygiene.zfcjfile}" onclick="commonFileDownLoad(this)"><span>${fns:getFileName(hygiene.zfcjfile)}</span></a> &nbsp; <a href="javascript:;" name="removeFile" vl="filecontentzfcjfile" vl1="zfcjfile"><i class="glyphicon glyphicon-remove text-danger"></i></a></li>
                            </c:if>
                        </ol>
                        <button type="button" class="btn btn-primary" name="uploadF" vl="zfcjfile"><i class="glyphicon glyphicon-open"></i>&nbsp;上传</button>
                    </td>
				</tr>
				<tr align="center" name="ningbo">
					<td>螺旋车间</td>
                    <td align="center"><input type="radio" class="i-checks form-control" name="lxcjqualified" <c:if test="${hygiene.lxcjqualified==null||hygiene.lxcjqualified==1}">checked="checked"</c:if> value="1"/></td>
                    <td align="center"><input type="radio" class="i-checks form-control" name="lxcjqualified" <c:if test="${hygiene.lxcjqualified==0}">checked="checked"</c:if> value="0"/></td>
					<td><form:input path="lxcjsituation" htmlEscape="false" maxlength="100"    class="form-control"/></td>
					<td><form:input path="lxcjcontent" htmlEscape="false" maxlength="100"    class="form-control"/></td>
					<td><input id="lxcjdate" name="lxcjdate" type="text" maxlength="20" class="laydate-icon form-control layer-date mydate" value="<fmt:formatDate value="${hygiene.lxcjdate}" pattern="yyyy-MM-dd"/>"/></td>
					<td><form:input path="lxcjresult" htmlEscape="false" maxlength="100"    class="form-control"/></td>
					<td><form:textarea path="lxcjcomment" htmlEscape="false" maxlength="100"    class="form-control"/></td>
					<td><input type="hidden" id="lxcjfile" name="lxcjfile" value="${hygiene.lxcjfile}"/>
                        <ol style="line-height: 20px; padding: 10px; padding-left:40px;" id="filecontentlxcjfile" class="filecontent">
                            <c:if test="${hygiene.lxcjfile!=null&&hygiene.lxcjfile!=''}">
                                <li><a href="javascript:;" target="_blank" vl="${hygiene.lxcjfile}" onclick="commonFileDownLoad(this)"><span>${fns:getFileName(hygiene.lxcjfile)}</span></a> &nbsp; <a href="javascript:;" name="removeFile" vl="filecontentlxcjfile" vl1="lxcjfile"><i class="glyphicon glyphicon-remove text-danger"></i></a></li>
                            </c:if>
                        </ol>
                        <button type="button" class="btn btn-primary" name="uploadF" vl="lxcjfile"><i class="glyphicon glyphicon-open"></i>&nbsp;上传</button>
                    </td>
				</tr>
				<tr align="center" name="ningbo">
					<td>现货仓库</td>
                    <td align="center"><input type="radio" class="i-checks form-control" name="xhqualified" <c:if test="${hygiene.xhqualified==null||hygiene.xhqualified==1}">checked="checked"</c:if> value="1"/></td>
                    <td align="center"><input type="radio" class="i-checks form-control" name="xhqualified" <c:if test="${hygiene.xhqualified==0}">checked="checked"</c:if> value="0"/></td>
					<td><form:input path="xhsituation" htmlEscape="false" maxlength="100"    class="form-control"/></td>
					<td><form:input path="xhcontent" htmlEscape="false" maxlength="100"    class="form-control"/></td>
					<td><input id="xhdate" name="xhdate" type="text" maxlength="20" class="laydate-icon form-control layer-date mydate" value="<fmt:formatDate value="${hygiene.xhdate}" pattern="yyyy-MM-dd"/>"/></td>
					<td><form:input path="xhresult" htmlEscape="false" maxlength="100"    class="form-control"/></td>
					<td><form:textarea path="xhcomment" htmlEscape="false" maxlength="100"    class="form-control"/></td>
					<td><input type="hidden" id="xhfile" name="xhfile" value="${hygiene.xhfile}"/>
                        <ol style="line-height: 20px; padding: 10px; padding-left:40px;" id="filecontentxhfile" class="filecontent">
                            <c:if test="${hygiene.xhfile!=null&&hygiene.xhfile!=''}">
                                <li><a href="javascript:;" target="_blank" vl="${hygiene.xhfile}" onclick="commonFileDownLoad(this)"><span>${fns:getFileName(hygiene.xhfile)}</span></a> &nbsp; <a href="javascript:;" name="removeFile" vl="filecontentxhfile" vl1="xhfile"><i class="glyphicon glyphicon-remove text-danger"></i></a></li>
                            </c:if>
                        </ol>
                        <button type="button" class="btn btn-primary" name="uploadF" vl="xhfile"><i class="glyphicon glyphicon-open"></i>&nbsp;上传</button>
                    </td>
				</tr>
				<tr align="center" name="ningbo">
					<td>辅料仓库</td>
                    <td align="center"><input type="radio" class="i-checks form-control" name="flqualified" <c:if test="${hygiene.flqualified==null||hygiene.flqualified==1}">checked="checked"</c:if> value="1"/></td>
                    <td align="center"><input type="radio" class="i-checks form-control" name="flqualified" <c:if test="${hygiene.flqualified==0}">checked="checked"</c:if> value="0"/></td>
					<td><form:input path="flsituation" htmlEscape="false" maxlength="100"    class="form-control"/></td>
					<td><form:input path="flcontent" htmlEscape="false" maxlength="100"    class="form-control"/></td>
					<td><input id="fldate" name="fldate" type="text" maxlength="20" class="laydate-icon form-control layer-date mydate" value="<fmt:formatDate value="${hygiene.fldate}" pattern="yyyy-MM-dd"/>"/></td>
					<td><form:input path="flresult" htmlEscape="false" maxlength="100"    class="form-control"/></td>
					<td><form:textarea path="flcomment" htmlEscape="false" maxlength="100"    class="form-control"/></td>
					<td><input type="hidden" id="flfile" name="flfile" value="${hygiene.flfile}"/>
                        <ol style="line-height: 20px; padding: 10px; padding-left:40px;" id="filecontentflfile" class="filecontent">
                            <c:if test="${hygiene.flfile!=null&&hygiene.flfile!=''}">
                                <li><a href="javascript:;" target="_blank" vl="${hygiene.flfile}" onclick="commonFileDownLoad(this)"><span>${fns:getFileName(hygiene.flfile)}</span></a> &nbsp; <a href="javascript:;" name="removeFile" vl="filecontentflfile" vl1="flfile"><i class="glyphicon glyphicon-remove text-danger"></i></a></li>
                            </c:if>
                        </ol>
                        <button type="button" class="btn btn-primary" name="uploadF" vl="flfile"><i class="glyphicon glyphicon-open"></i>&nbsp;上传</button>
                    </td>
				</tr>
				<tr align="center" name="ningbo">
					<td>道路</td>
                    <td align="center"><input type="radio" class="i-checks form-control" name="roadqualified" <c:if test="${hygiene.roadqualified==null||hygiene.roadqualified==1}">checked="checked"</c:if> value="1"/></td>
                    <td align="center"><input type="radio" class="i-checks form-control" name="roadqualified" <c:if test="${hygiene.roadqualified==0}">checked="checked"</c:if> value="0"/></td>
					<td><form:input path="roadsituation" htmlEscape="false" maxlength="100"    class="form-control"/></td>
					<td><form:input path="roadcontent" htmlEscape="false" maxlength="100"    class="form-control"/></td>
					<td><input id="roaddate" name="roaddate" type="text" maxlength="20" class="laydate-icon form-control layer-date mydate" value="<fmt:formatDate value="${hygiene.roaddate}" pattern="yyyy-MM-dd"/>"/></td>
					<td><form:input path="roadresult" htmlEscape="false" maxlength="100"    class="form-control"/></td>
					<td><form:textarea path="roadcomment" htmlEscape="false" maxlength="100"    class="form-control"/></td>
					<td><input type="hidden" id="roadfile" name="roadfile" value="${hygiene.roadfile}"/>
                        <ol style="line-height: 20px; padding: 10px; padding-left:40px;" id="filecontentroadfile" class="filecontent">
                            <c:if test="${hygiene.roadfile!=null&&hygiene.roadfile!=''}">
                                <li><a href="javascript:;" target="_blank" vl="${hygiene.roadfile}" onclick="commonFileDownLoad(this)"><span>${fns:getFileName(hygiene.roadfile)}</span></a> &nbsp; <a href="javascript:;" name="removeFile" vl="filecontentroadfile" vl1="roadfile"><i class="glyphicon glyphicon-remove text-danger"></i></a></li>
                            </c:if>
                        </ol>
                        <button type="button" class="btn btn-primary" name="uploadF" vl="roadfile"><i class="glyphicon glyphicon-open"></i>&nbsp;上传</button>
                    </td>
				</tr>




				<tr align="center" name="etr">
					<td>宿舍</td>
                    <td align="center"><input type="radio" class="i-checks form-control" name="dormitoryqualified" <c:if test="${hygiene.dormitoryqualified==null||hygiene.dormitoryqualified==1}">checked="checked"</c:if> value="1"/></td>
                    <td align="center"><input type="radio" class="i-checks form-control" name="dormitoryqualified" <c:if test="${hygiene.dormitoryqualified==0}">checked="checked"</c:if> value="0"/></td>
					<td><form:input path="dormitorysituation" htmlEscape="false" maxlength="100"    class="form-control"/></td>
					<td><form:input path="dormitorycontent" htmlEscape="false" maxlength="100"    class="form-control"/></td>
					<td><input id="dormitorydate" name="dormitorydate" type="text" maxlength="20" class="laydate-icon form-control layer-date mydate" value="<fmt:formatDate value="${hygiene.dormitorydate}" pattern="yyyy-MM-dd"/>"/></td>
					<td><form:input path="dormitoryresult" htmlEscape="false" maxlength="100"    class="form-control"/></td>
					<td><form:textarea path="dormitorycomment" htmlEscape="false" maxlength="100"    class="form-control"/></td>
					<td><input type="hidden" id="dormitoryfile" name="dormitoryfile" value="${hygiene.dormitoryfile}"/>
                        <ol style="line-height: 20px; padding: 10px; padding-left:40px;" id="filecontentdormitoryfile" class="filecontent">
                            <c:if test="${hygiene.dormitoryfile!=null&&hygiene.dormitoryfile!=''}">
                                <li><a href="javascript:;" target="_blank" vl="${hygiene.dormitoryfile}" onclick="commonFileDownLoad(this)"><span>${fns:getFileName(hygiene.dormitoryfile)}</span></a> &nbsp; <a href="javascript:;" name="removeFile" vl="filecontentdormitoryfile" vl1="dormitoryfile"><i class="glyphicon glyphicon-remove text-danger"></i></a></li>
                            </c:if>
                        </ol>
                        <button type="button" class="btn btn-primary" name="uploadF" vl="dormitoryfile"><i class="glyphicon glyphicon-open"></i>&nbsp;上传</button>
                    </td>
				</tr>
				<tr align="center" name="etr">
					<td>食堂</td>
                    <td align="center"><input type="radio" class="i-checks form-control" name="canteenqualified" <c:if test="${hygiene.canteenqualified==null||hygiene.canteenqualified==1}">checked="checked"</c:if> value="1"/></td>
                    <td align="center"><input type="radio" class="i-checks form-control" name="canteenqualified" <c:if test="${hygiene.canteenqualified==0}">checked="checked"</c:if> value="0"/></td>
					<td><form:input path="canteensituation" htmlEscape="false" maxlength="100"    class="form-control"/></td>
					<td><form:input path="canteencontent" htmlEscape="false" maxlength="100"    class="form-control"/></td>
					<td><input id="canteendate" name="canteendate" type="text" maxlength="20" class="laydate-icon form-control layer-date mydate" value="<fmt:formatDate value="${hygiene.canteendate}" pattern="yyyy-MM-dd"/>"/></td>
					<td><form:input path="canteenresult" htmlEscape="false" maxlength="100"    class="form-control"/></td>
					<td><form:textarea path="canteencomment" htmlEscape="false" maxlength="100"    class="form-control"/></td>
					<td><input type="hidden" id="canteenfile" name="canteenfile" value="${hygiene.canteenfile}"/>
                        <ol style="line-height: 20px; padding: 10px; padding-left:40px;" id="filecontentcanteenfile" class="filecontent">
                            <c:if test="${hygiene.canteenfile!=null&&hygiene.canteenfile!=''}">
                                <li><a href="javascript:;" target="_blank" vl="${hygiene.canteenfile}" onclick="commonFileDownLoad(this)"><span>${fns:getFileName(hygiene.canteenfile)}</span></a> &nbsp; <a href="javascript:;" name="removeFile" vl="filecontentcanteenfile" vl1="canteenfile"><i class="glyphicon glyphicon-remove text-danger"></i></a></li>
                            </c:if>
                        </ol>
                        <button type="button" class="btn btn-primary" name="uploadF" vl="canteenfile"><i class="glyphicon glyphicon-open"></i>&nbsp;上传</button>
                    </td>
				</tr>
				<tr align="center" name="etr">
					<td>门卫</td>
                    <td align="center"><input type="radio" class="i-checks form-control" name="guardqualified" <c:if test="${hygiene.guardqualified==null||hygiene.guardqualified==1}">checked="checked"</c:if> value="1"/></td>
                    <td align="center"><input type="radio" class="i-checks form-control" name="guardqualified" <c:if test="${hygiene.guardqualified==0}">checked="checked"</c:if> value="0"/></td>
					<td><form:input path="guardsituation" htmlEscape="false" maxlength="100"    class="form-control"/></td>
					<td><form:input path="guardcontent" htmlEscape="false" maxlength="100"    class="form-control"/></td>
					<td><input id="guarddate" name="guarddate" type="text" maxlength="20" class="laydate-icon form-control layer-date mydate" value="<fmt:formatDate value="${hygiene.guarddate}" pattern="yyyy-MM-dd"/>"/></td>
					<td><form:input path="guardresult" htmlEscape="false" maxlength="100"    class="form-control"/></td>
					<td><form:textarea path="guardcomment" htmlEscape="false" maxlength="100"    class="form-control"/></td>
					<td><input type="hidden" id="guardfile" name="guardfile" value="${hygiene.guardfile}"/>
                        <ol style="line-height: 20px; padding: 10px; padding-left:40px;" id="filecontentguardfile" class="filecontent">
                            <c:if test="${hygiene.guardfile!=null&&hygiene.guardfile!=''}">
                                <li><a href="javascript:;" target="_blank" vl="${hygiene.guardfile}" onclick="commonFileDownLoad(this)"><span>${fns:getFileName(hygiene.guardfile)}</span></a> &nbsp; <a href="javascript:;" name="removeFile" vl="filecontentguardfile" vl1="guardfile"><i class="glyphicon glyphicon-remove text-danger"></i></a></li>
                            </c:if>
                        </ol>
                        <button type="button" class="btn btn-primary" name="uploadF" vl="guardfile"><i class="glyphicon glyphicon-open"></i>&nbsp;上传</button>
                    </td>
				</tr>
				<tr align="center" name="etr">
					<td>其他</td>
                    <td align="center"><input type="radio" class="i-checks form-control" name="otherqualified" <c:if test="${hygiene.otherqualified==null||hygiene.otherqualified==1}">checked="checked"</c:if> value="1"/></td>
                    <td align="center"><input type="radio" class="i-checks form-control" name="otherqualified" <c:if test="${hygiene.otherqualified==0}">checked="checked"</c:if> value="0"/></td>
					<td><form:input path="othersituation" htmlEscape="false" maxlength="100"    class="form-control"/></td>
					<td><form:input path="othercontent" htmlEscape="false" maxlength="100"    class="form-control"/></td>
					<td><input id="otherdate" name="otherdate" type="text" maxlength="20" class="laydate-icon form-control layer-date mydate" value="<fmt:formatDate value="${hygiene.otherdate}" pattern="yyyy-MM-dd"/>"/></td>
					<td><form:input path="otherresult" htmlEscape="false" maxlength="100"    class="form-control"/></td>
					<td><form:textarea path="othercomment" htmlEscape="false" maxlength="100"    class="form-control"/></td>
					<td><input type="hidden" id="otherfile" name="otherfile" value="${hygiene.otherfile}"/>
                        <ol style="line-height: 20px; padding: 10px; padding-left:40px;" id="filecontentotherfile" class="filecontent">
                            <c:if test="${hygiene.otherfile!=null&&hygiene.otherfile!=''}">
                                <li><a href="javascript:;" target="_blank" vl="${hygiene.otherfile}" onclick="commonFileDownLoad(this)"><span>${fns:getFileName(hygiene.otherfile)}</span></a> &nbsp; <a href="javascript:;" name="removeFile" vl="filecontentotherfile" vl1="otherfile"><i class="glyphicon glyphicon-remove text-danger"></i></a></li>
                            </c:if>
                        </ol>
                        <button type="button" class="btn btn-primary" name="uploadF" vl="otherfile"><i class="glyphicon glyphicon-open"></i>&nbsp;上传</button>
                    </td>
				</tr>

				<tr align="center" name="changshu">
					<td>厕所</td>
                    <td align="center"><input type="radio" class="i-checks form-control" name="cstoiletqualified" <c:if test="${hygiene.cstoiletqualified==null||hygiene.cstoiletqualified==1}">checked="checked"</c:if> value="1"/></td>
                    <td align="center"><input type="radio" class="i-checks form-control" name="cstoiletqualified" <c:if test="${hygiene.cstoiletqualified==0}">checked="checked"</c:if> value="0"/></td>
					<td><form:input path="cstoiletsituation" htmlEscape="false" maxlength="100"    class="form-control"/></td>
					<td><form:input path="cstoiletcontent" htmlEscape="false" maxlength="100"    class="form-control"/></td>
					<td><input id="cstoiletdate" name="cstoiletdate" type="text" maxlength="20" class="laydate-icon form-control layer-date mydate" value="<fmt:formatDate value="${hygiene.cstoiletdate}" pattern="yyyy-MM-dd"/>"/></td>
					<td><form:input path="cstoiletresult" htmlEscape="false" maxlength="100"    class="form-control"/></td>
					<td><form:textarea path="cstoiletcomment" htmlEscape="false" maxlength="100"    class="form-control"/></td>
					<td><input type="hidden" id="cstoiletfile" name="cstoiletfile" value="${hygiene.cstoiletfile}"/>
                        <ol style="line-height: 20px; padding: 10px; padding-left:40px;" id="filecontentcstoiletfile" class="filecontent">
                            <c:if test="${hygiene.cstoiletfile!=null&&hygiene.cstoiletfile!=''}">
                                <li><a href="javascript:;" target="_blank" vl="${hygiene.cstoiletfile}" onclick="commonFileDownLoad(this)"><span>${fns:getFileName(hygiene.cstoiletfile)}</span></a> &nbsp; <a href="javascript:;" name="removeFile" vl="filecontentcstoiletfile" vl1="cstoiletfile"><i class="glyphicon glyphicon-remove text-danger"></i></a></li>
                            </c:if>
                        </ol>
                        <button type="button" class="btn btn-primary" name="uploadF" vl="cstoiletfile"><i class="glyphicon glyphicon-open"></i>&nbsp;上传</button>
                    </td>
				</tr>

				<tr align="center" name="ningbo">
					<td>厕所</td>
                    <td align="center"><input type="radio" class="i-checks form-control" name="nbtoiletqualified" <c:if test="${hygiene.nbtoiletqualified==null||hygiene.nbtoiletqualified==1}">checked="checked"</c:if> value="1"/></td>
                    <td align="center"><input type="radio" class="i-checks form-control" name="nbtoiletqualified" <c:if test="${hygiene.nbtoiletqualified==0}">checked="checked"</c:if> value="0"/></td>
					<td><form:input path="nbtoiletsituation" htmlEscape="false" maxlength="100"    class="form-control"/></td>
					<td><form:input path="nbtoiletcontent" htmlEscape="false" maxlength="100"    class="form-control"/></td>
					<td><input id="nbtoiletdate" name="nbtoiletdate" type="text" maxlength="20" class="laydate-icon form-control layer-date mydate" value="<fmt:formatDate value="${hygiene.nbtoiletdate}" pattern="yyyy-MM-dd"/>"/></td>
					<td><form:input path="nbtoiletresult" htmlEscape="false" maxlength="100"    class="form-control"/></td>
					<td><form:textarea path="nbtoiletcomment" htmlEscape="false" maxlength="100"    class="form-control"/></td>
					<td><input type="hidden" id="nbtoiletfile" name="nbtoiletfile" value="${hygiene.nbtoiletfile}"/>
                        <ol style="line-height: 20px; padding: 10px; padding-left:40px;" id="filecontentnbtoiletfile" class="filecontent">
                            <c:if test="${hygiene.nbtoiletfile!=null&&hygiene.nbtoiletfile!=''}">
                                <li><a href="javascript:;" target="_blank" vl="${hygiene.nbtoiletfile}" onclick="commonFileDownLoad(this)"><span>${fns:getFileName(hygiene.nbtoiletfile)}</span></a> &nbsp; <a href="javascript:;" name="removeFile" vl="filecontentnbtoiletfile" vl1="nbtoiletfile"><i class="glyphicon glyphicon-remove text-danger"></i></a></li>
                            </c:if>
                        </ol>
                        <button type="button" class="btn btn-primary" name="uploadF" vl="nbtoiletfile"><i class="glyphicon glyphicon-open"></i>&nbsp;上传</button>
                    </td>
				</tr>

			</table>
			<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer tbdisable">
				<tr name="etr" height="80">
					<td><span style="float:left; display:inline-block; line-height:78px;">检查总结:</span><span style="float:left; width:80%"><form:textarea path="summary" htmlEscape="false" maxlength="100"    class="form-control"/></span></td>
				</tr>
			</table>
	</form:form>
</body>
</html>