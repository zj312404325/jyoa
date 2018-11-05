<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>机构管理</title>
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
			$("#name").focus();
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

			if("${office.parent}"==''){
                 $("#type").val('1');
                 $("#officetype option[value='1']").attr("selected", "selected");
                 //$("#officetype").attr("disabled",true);
			}else if("${office.parent.type}"=='1'){
                $("#type").val('2');
                $("#officetype option[value='2']").attr("selected", "selected");
                //$("#officetype").attr("disabled",true);
            }else if("${office.parent.type}"=='2'){
                /*$("#type").val('3');
                $("#officetype option[value='3']").attr("selected", "selected");
                $("#officetype").attr("disabled",true);*/
                $("#type").val('2');
                $("#officetype option[value='2']").attr("selected", "selected");
                //$("#officetype").attr("disabled",true);
            }

		});
		function callback(){
		    if($("#officeId").val() != ''){
                var oid = $("#officeId").val();
                $.post("${ctx}/sys/office/getOfficeByid",{'id':oid},function(data){
                    var jsonData = jQuery.parseJSON(data);
                    if(jsonData.status == 'y'){
                        var officetype = jsonData.type;
                        if(officetype == '1'){
                            $("#type").val('2');
                            $("#officetype option[value='2']").attr("selected", "selected");
                            //$("#officetype").attr("disabled",true);
                        }else if(officetype == '2'){
                            $("#type").val('2');
                            $("#officetype option[value='2']").attr("selected", "selected");
                            //$("#officetype").attr("disabled",true);
                            /*$("#type").val('3');
                            $("#officetype option[value='3']").attr("selected", "selected");
                            $("#officetype").attr("disabled",true);*/
                        }
                        /*else if(officetype == '3'){
                            $("#type").val('');
                            $("#officeId").val("");
                            $("#officeName").val("");
                            alert("请选择公司或部门作为上级机构");
                        }*/else{
                            $("#type").val('1');
                            $("#officetype option[value='1']").attr("selected", "selected");
                            //$("#officetype").attr("disabled",true);
                        }
                    }else{
                        layer.alert(jsonData.info,{icon: 2});
                    }
                });

			}

		}
	</script>
</head>
<body class="hideScroll">
	<form:form id="inputForm" modelAttribute="office" action="${ctx}/sys/office/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
		<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
		   <tbody>
		      <tr>
		         <td class="width-15 active"><label class="pull-right">上级机构:</label></td>
		         <td class="width-35">
					 <c:if test="${office.id == null}">
					 <sys:treeselectCallBackFun id="office" name="parent.id" value="${office.parent.id}" labelName="parent.name" labelValue="${office.parent.name}"
					title="机构" url="/sys/office/treeData"  cssClass="form-control" allowClear="${office.currentUser.admin}"/>
					 </c:if>
					 <c:if test="${office.id != null}">
						 <sys:treeselectCallBackFun id="office" name="parent.id" value="${office.parent.id}" labelName="parent.name" labelValue="${office.parent.name}"
													title="机构" url="/sys/office/treeData"  cssClass="form-control" allowClear="${office.currentUser.admin}" />
					 </c:if>
				 </td>
		         <td  class="width-15"  class="active"><label class="pull-right"><font color="red">*</font>归属区域:</label></td>
		         <td class="width-35"><sys:treeselect id="area" name="area.id" value="${office.area.id}" labelName="area.name" labelValue="${office.area.name}"
					title="区域" url="/sys/area/treeData" cssClass="form-control required"/></td>
		      </tr>
		       <tr>
		         <td class="width-15 active"><label class="pull-right"><font color="red">*</font>机构名称:</label></td>
		         <td class="width-35"><form:input path="oname" htmlEscape="false" maxlength="50" class="form-control required"/></td>
				   <td  class="width-15"><label class="pull-right"><font color="red">*</font>区域显示:</label></td>
				   <td class="width-35" >
					   <form:radiobuttons path="isshowarea" items="${fns:getDictList('show_hide')}" itemLabel="label" itemValue="value" htmlEscape="false" class="required i-checks "/>
					   <span class="help-inline">“显示”代表区域显示在机构名称后面，“隐藏”则表示不显示区域</span>
				   </td>

		      </tr>
		       <tr>
		         <td class="width-15 active"><label class="pull-right">机构类型:</label></td>
		         <td class="width-35"><form:hidden path="type"/><form:select path="officetype" class="form-control">
					<form:options items="${fns:getDictList('sys_office_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
					</form:select></td>
				   <td  class="width-15"  class="active"><label class="pull-right">机构编码:</label></td>
				   <td class="width-35"><form:input path="code" htmlEscape="false" maxlength="50" class="form-control"/></td>

		      </tr>
		      <tr>
		         <td class="width-15 active"><label class="pull-right">是否可用:</label></td>
		         <td class="width-35"><form:select path="useable" class="form-control">
					<form:options items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
					</form:select>
					<span class="help-inline">“是”代表此机构正常运营，“否”则表示此机构停止运营</span></td>
				  <td  class="width-15"  class="active"><label class="pull-right">机构级别:</label></td>
				  <td class="width-35"><form:select path="grade" class="form-control">
					  <form:options items="${fns:getDictList('sys_office_grade')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				  </form:select></td>

		      </tr>
		      <tr>
				  <td class="width-15 active"  class="active"><label class="pull-right">主负责人:</label></td>
				  <td class="width-35"><sys:treeselect id="primaryPerson" name="primaryPerson.id" value="${office.primaryPerson.id}" labelName="office.primaryPerson.name" labelValue="${office.primaryPerson.name}"
													   title="用户" url="/sys/office/treeData?type=3" cssClass="form-control" allowClear="true" notAllowSelectParent="true"/></td>
		         <%--<td class="width-15"><label class="pull-right">副负责人:</label></td>
		         <td class="width-35"><sys:treeselect id="deputyPerson" name="deputyPerson.id" value="${office.deputyPerson.id}" labelName="office.deputyPerson.name" labelValue="${office.deputyPerson.name}"
					title="用户" url="/sys/office/treeData?type=3" cssClass="form-control" allowClear="true" notAllowSelectParent="true"/></td>--%>
				  <td class="width-15"><label class="pull-right">邮箱:</label></td>
				  <td class="width-35"><form:input path="email" htmlEscape="false" maxlength="50" cssClass="form-control" /></td>
		      </tr>
		      <tr>
		         <td class="width-15 active"><label class="pull-right">邮政编码:</label></td>
		         <td class="width-35"><form:input path="zipCode" htmlEscape="false" maxlength="50" cssClass="form-control" /></td>
		         <%--<td  class="width-15" class="active"><label class="pull-right">负责人:</label></td>
		         <td class="width-35"><form:input path="master" htmlEscape="false" maxlength="50" cssClass="form-control" /></td>--%>
				  <td class="width-15"  class="active"><label class="pull-right">联系地址:</label></td>
				  <td class="width-35"><form:input path="address" htmlEscape="false" maxlength="50" cssClass="form-control" /></td>
		      </tr>
		      <tr>
		         <td class="width-15 active"><label class="pull-right">电话:</label></td>
		         <td class="width-35"><form:input path="phone" htmlEscape="false" maxlength="50" cssClass="form-control" /></td>
		         <td  class="width-15"  class="active"><label class="pull-right">传真:</label></td>
		         <td class="width-35"><form:input path="fax" htmlEscape="false" maxlength="50" cssClass="form-control" /></td>
		      </tr>
			  <%--<tr>

				  <td  class="width-15 active" class="active"><label class="pull-right">岗位:</label></td>
				  <td class="width-35" colspan="3">
                      <form:checkboxes path="postIdList" items="${allPosts}" itemLabel="postname" itemValue="id" htmlEscape="false" cssClass="i-checks required"/>
                      <label id="postIdList-error" class="error" for="postIdList"></label></td>
			  </tr>--%>
		      <tr>

		         <td  class="width-15 active" class="active"><label class="pull-right">备注:</label></td>
		         <td class="width-35" colspan="3"><form:textarea path="remarks" htmlEscape="false" rows="3" maxlength="200" class="form-control"/></td>
		      </tr>
	      </tbody>
	      </table>
	</form:form>
</body>
</html>