<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>值班表管理</title>
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
		//公司初始化输入内容
		function setCompany(type) {
            if(type=='1'){
                $(".a1").show();
				$(".ggsyzx").show();
                $(".tdsyzx").hide();
                $(".jgsyzx").hide();
                $(".znzx").hide();
                $(".dbtdsy").hide();
                $(".dbggsy").hide();
                $(".zba2").show();
                $("#showtype").hide();
            }else if(type=='2'){
                $(".a1").show();
                $(".ggsyzx").hide();
                $(".tdsyzx").show();
                $(".jgsyzx").hide();
                $(".znzx").hide();
                $(".dbtdsy").hide();
                $(".dbggsy").hide();
                $(".zba2").show();
                $("#showtype").hide();
			}else if(type=='3'){
                $(".a1").show();
                $(".ggsyzx").hide();
                $(".tdsyzx").hide();
                $(".jgsyzx").show();
                $(".znzx").hide();
                $(".dbtdsy").hide();
                $(".dbggsy").hide();
                $(".zba2").show();
                $("#showtype").hide();
            }else if(type=='4'){
                $(".a1").hide();
                $(".ggsyzx").hide();
                $(".tdsyzx").hide();
                $(".jgsyzx").hide();
                $(".znzx").show();
                $(".dbtdsy").hide();
                $(".dbggsy").hide();
                $(".zba2").hide();
                $("#showtype").show();
            }else if(type=='5'){
                $(".ggsyzx").hide();
                $(".tdsyzx").hide();
                $(".jgsyzx").hide();
                $(".znzx").hide();
                $(".dbtdsy").show();
                $(".dbggsy").hide();
                $(".zba2").hide();
                $("#showtype").hide();
            }else if(type=='6'){
                $(".ggsyzx").hide();
                $(".tdsyzx").hide();
                $(".jgsyzx").hide();
                $(".znzx").hide();
                $(".dbtdsy").hide();
                $(".dbggsy").show();
                $(".zba2").hide();
                $("#showtype").hide();
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
        }
		$(document).ready(function() {
            //查看是设置输入框未不可编辑
            if('${type}'=='0'){
                setReadOnly();
            }
		    //公司选择
            $('input[name=company]').on('ifChecked', function(event){
                var type=$(event.target).val();
                setCompany(type);
                clearForm();
            });

		    //值班倒班
			if('${tbType}'=='1'){//值班
			    $(".zb").show();
			    $(".db").hide();
                //编辑记录时公司初始化
                if('${duty.id}'!=''){//修改
                    setCompany('${duty.company}');
                }else{//新增
                    setCompany('1');
                }
			}else{//倒班
                $(".zb").hide();
                $(".db").show();
                //编辑记录时公司初始化
                if('${duty.id}'!=''){//修改
                    setCompany('${duty.company}');
                }else{//新增
                    setCompany('5');
                }
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
			            elem: '#startdate', //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
                        istime: true,
                        format: 'YYYY-MM-DD hh:mm:ss',
						event: 'focus' //响应事件。如果没有传入event，则按照默认的click
			        });
					laydate({
			            elem: '#enddate', //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
                        istime: true,
						format: 'YYYY-MM-DD hh:mm:ss',
						event: 'focus' //响应事件。如果没有传入event，则按照默认的click
			        });
		});
	</script>
</head>
<body class="hideScroll">
		<form:form id="inputForm" modelAttribute="duty" action="${ctx}/oa/duty/save?tbType=${tbType}" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>	
		<%--<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
		   <tbody>
				<tr>
					<td class="width-15 active"><label class="pull-right">公司：</label></td>
					<td class="width-35">
						<c:if test="${tbType=='1'}">
							<form:radiobuttons path="company" items="${fns:getDictList('oa_company_duty')}" itemLabel="label" itemValue="value" htmlEscape="false" class="i-checks "/>
						</c:if>
						<c:if test="${tbType=='2'}">
							<form:radiobuttons path="company" items="${fns:getDictList('oa_company_dbduty')}" itemLabel="label" itemValue="value" htmlEscape="false" class="i-checks "/>
						</c:if>
					</td>
					<td class="width-15 active"><label class="pull-right">开始时间：</label></td>
					<td class="width-35">
						<input id="startdate" name="startdate" type="text" maxlength="20" class="laydate-icon form-control layer-date "
							value="<fmt:formatDate value="${duty.startdate}" pattern="yyyy-MM-dd HH:mm:ss"/>"/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">结束时间：</label></td>
					<td class="width-35">
						<input id="enddate" name="enddate" type="text" maxlength="20" class="laydate-icon form-control layer-date "
							value="<fmt:formatDate value="${duty.enddate}" pattern="yyyy-MM-dd HH:mm:ss"/>"/>
					</td>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>值班人：</label></td>
					<td class="width-35">
						<form:input path="dutier" htmlEscape="false" maxlength="20"    class="form-control required"/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>部门：</label></td>
					<td class="width-35">
						<form:input path="department" htmlEscape="false" maxlength="20"    class="form-control required"/>
					</td>
					<td class="width-15 active"><label class="pull-right">职务：</label></td>
					<td class="width-35">
						<form:input path="post" htmlEscape="false" maxlength="20"    class="form-control "/>
					</td>
				</tr>
				<tr class="db">
					<td class="width-15 active"><label class="pull-right">白夜班：</label></td>
					<td class="width-35">
						<form:radiobuttons path="worktime" items="${fns:getDictList('oa_worktime')}" itemLabel="label" itemValue="value" htmlEscape="false" class="i-checks "/>
					</td>
					<td class="width-15 active"><label class="pull-right">交接人：</label></td>
					<td class="width-35" colspan="3">
						<form:input path="handover" htmlEscape="false" maxlength="20"    class="form-control "/>
					</td>
				</tr>
				<tr class="zb ggsyzx">
					<td class="width-15 active"><label class="pull-right">浙华办公楼存在问题：</label></td>
					<td class="width-35">
						<form:input path="zhehuaproblem" htmlEscape="false" maxlength="200"    class="form-control "/>
					</td>
					<td class="width-15 active"><label class="pull-right">浙华办公楼处理结果：</label></td>
					<td class="width-35">
						<form:input path="zhehuaresult" htmlEscape="false" maxlength="200"    class="form-control "/>
					</td>
				</tr>
				<tr class="zb ggsyzx">
					<td class="width-15 active"><label class="pull-right">北门保安室存在问题：</label></td>
					<td class="width-35">
						<form:input path="beimenproblem" htmlEscape="false" maxlength="200"    class="form-control "/>
					</td>
					<td class="width-15 active"><label class="pull-right">北门保安室处理结果：</label></td>
					<td class="width-35">
						<form:input path="beimenresult" htmlEscape="false" maxlength="200"    class="form-control "/>
					</td>
				</tr>
				<tr class="zb ggsyzx">
					<td class="width-15 active"><label class="pull-right">生产办公室存在问题：</label></td>
					<td class="width-35">
						<form:input path="shengchanproblem" htmlEscape="false" maxlength="200"    class="form-control "/>
					</td>
					<td class="width-15 active"><label class="pull-right">生产办公室处理结果：</label></td>
					<td class="width-35">
						<form:input path="shengchanresult" htmlEscape="false" maxlength="200"    class="form-control "/>
					</td>
				</tr>
				<tr class="zb ggsyzx">
					<td class="width-15 active"><label class="pull-right">直缝车间存在问题：</label></td>
					<td class="width-35">
						<form:input path="zfroomproblem" htmlEscape="false" maxlength="200"    class="form-control "/>
					</td>
					<td class="width-15 active"><label class="pull-right">直缝车间处理结果：</label></td>
					<td class="width-35">
						<form:input path="zfroomresult" htmlEscape="false" maxlength="200"    class="form-control "/>
					</td>
				</tr>
				<tr class="zb ggsyzx">
					<td class="width-15 active"><label class="pull-right">螺旋车间处理结果：</label></td>
					<td class="width-35">
						<form:input path="lxroomresult" htmlEscape="false" maxlength="200"    class="form-control "/>
					</td>
					<td class="width-15 active"><label class="pull-right">螺旋车间存在问题：</label></td>
					<td class="width-35">
						<form:input path="lxroomproblem" htmlEscape="false" maxlength="200"    class="form-control "/>
					</td>
				</tr>
				<tr class="zb ggsyzx">
					<td class="width-15 active"><label class="pull-right">防腐车间存在问题：</label></td>
					<td class="width-35">
						<form:input path="ffroomproblem" htmlEscape="false" maxlength="200"    class="form-control "/>
					</td>
					<td class="width-15 active"><label class="pull-right">防腐车间处理结果：</label></td>
					<td class="width-35">
						<form:input path="ffroomresult" htmlEscape="false" maxlength="200"    class="form-control "/>
					</td>
				</tr>
				<tr class="zb ggsyzx">
					<td class="width-15 active"><label class="pull-right">现货仓库存在问题：</label></td>
					<td class="width-35">
						<form:input path="xhstoreproblem" htmlEscape="false" maxlength="200"    class="form-control "/>
					</td>
					<td class="width-15 active"><label class="pull-right">现货仓库处理结果：</label></td>
					<td class="width-35">
						<form:input path="xhstoreresult" htmlEscape="false" maxlength="200"    class="form-control "/>
					</td>
				</tr>
				<tr class="zb ggsyzx">
					<td class="width-15 active"><label class="pull-right">机修电工房存在问题：</label></td>
					<td class="width-35">
						<form:input path="dgroomproblem" htmlEscape="false" maxlength="200"    class="form-control "/>
					</td>
					<td class="width-15 active"><label class="pull-right">机修电工房处理结果：</label></td>
					<td class="width-35">
						<form:input path="dgroomresult" htmlEscape="false" maxlength="200"    class="form-control "/>
					</td>
				</tr>
				<tr class="zb ggsyzx">
					<td class="width-15 active"><label class="pull-right">收发辅料仓库存在问题：</label></td>
					<td class="width-35">
						<form:input path="sfflstoreproblem" htmlEscape="false" maxlength="200"    class="form-control "/>
					</td>
					<td class="width-15 active"><label class="pull-right">收发辅料仓库处理结果：</label></td>
					<td class="width-35">
						<form:input path="sfflstoreresult" htmlEscape="false" maxlength="200"    class="form-control "/>
					</td>
				</tr>
				<tr class="zb tdsyzx">
					<td class="width-15 active"><label class="pull-right">薄板办公楼存在问题：</label></td>
					<td class="width-35">
						<form:input path="bobanofficeproblem" htmlEscape="false" maxlength="200"    class="form-control "/>
					</td>
					<td class="width-15 active"><label class="pull-right">薄板办公楼处理结果：</label></td>
					<td class="width-35">
						<form:input path="bobanofficeresult" htmlEscape="false" maxlength="200"    class="form-control "/>
					</td>
				</tr>
				<tr class="zb tdsyzx">
					<td class="width-15 active"><label class="pull-right">监控室存在问题：</label></td>
					<td class="width-35">
						<form:input path="monitorroomproblem" htmlEscape="false" maxlength="200"    class="form-control "/>
					</td>
					<td class="width-15 active"><label class="pull-right">监控室处理结果：</label></td>
					<td class="width-35">
						<form:input path="monitorroomresult" htmlEscape="false" maxlength="200"    class="form-control "/>
					</td>
				</tr>
				<tr class="zb tdsyzx">
					<td class="width-15 active"><label class="pull-right">克罗德办公楼存在问题：</label></td>
					<td class="width-35">
						<form:input path="kldofficeproblem" htmlEscape="false" maxlength="200"    class="form-control "/>
					</td>
					<td class="width-15 active"><label class="pull-right">克罗德办公楼处理结果：</label></td>
					<td class="width-35">
						<form:input path="kldofficeresult" htmlEscape="false" maxlength="200"    class="form-control "/>
					</td>
				</tr>
				<tr class="zb tdsyzx">
					<td class="width-15 active"><label class="pull-right">薄板镀锌线存在问题：</label></td>
					<td class="width-35">
						<form:input path="aduxinproblem" htmlEscape="false" maxlength="200"    class="form-control "/>
					</td>
					<td class="width-15 active"><label class="pull-right">薄板镀锌线处理结果：</label></td>
					<td class="width-35">
						<form:input path="aduxinresult" htmlEscape="false" maxlength="200"    class="form-control "/>
					</td>
				</tr>
				<tr class="zb tdsyzx">
					<td class="width-15 active"><label class="pull-right">薄板彩涂线存在问题：</label></td>
					<td class="width-35">
						<form:input path="atucaiproblem" htmlEscape="false" maxlength="200"    class="form-control "/>
					</td>
					<td class="width-15 active"><label class="pull-right">薄板彩涂线处理结果：</label></td>
					<td class="width-35">
						<form:input path="atucairesult" htmlEscape="false" maxlength="200"    class="form-control "/>
					</td>
				</tr>
				<tr class="zb tdsyzx">
					<td class="width-15 active"><label class="pull-right">酸洗产线存在问题：</label></td>
					<td class="width-35">
						<form:input path="bsuanxiproblem" htmlEscape="false" maxlength="200"    class="form-control "/>
					</td>
					<td class="width-15 active"><label class="pull-right">酸洗产线处理结果：</label></td>
					<td class="width-35">
						<form:input path="bsuanxiresult" htmlEscape="false" maxlength="200"    class="form-control "/>
					</td>
				</tr>
				<tr class="zb tdsyzx">
					<td class="width-15 active"><label class="pull-right">1#轧机存在问题：</label></td>
					<td class="width-35">
						<form:input path="bzhaji1problem" htmlEscape="false" maxlength="200"    class="form-control "/>
					</td>
					<td class="width-15 active"><label class="pull-right">1#轧机处理结果：</label></td>
					<td class="width-35">
						<form:input path="bzhaji1result" htmlEscape="false" maxlength="200"    class="form-control "/>
					</td>
				</tr>
				<tr class="zb tdsyzx">
					<td class="width-15 active"><label class="pull-right">2#轧机存在问题：</label></td>
					<td class="width-35">
						<form:input path="bzhaji2problem" htmlEscape="false" maxlength="200"    class="form-control "/>
					</td>
					<td class="width-15 active"><label class="pull-right">2#轧机处理结果：</label></td>
					<td class="width-35">
						<form:input path="bzhaji2result" htmlEscape="false" maxlength="200"    class="form-control "/>
					</td>
				</tr>
				<tr class="zb tdsyzx">
					<td class="width-15 active"><label class="pull-right">15WT镀锌存在问题：</label></td>
					<td class="width-35">
						<form:input path="cduxin15problem" htmlEscape="false" maxlength="200"    class="form-control "/>
					</td>
					<td class="width-15 active"><label class="pull-right">15WT镀锌处理结果：</label></td>
					<td class="width-35">
						<form:input path="cduxin15result" htmlEscape="false" maxlength="200"    class="form-control "/>
					</td>
				</tr>
				<tr class="zb tdsyzx">
					<td class="width-15 active"><label class="pull-right">25WT镀锌存在问题：</label></td>
					<td class="width-35">
						<form:input path="cduxin25problem" htmlEscape="false" maxlength="200"    class="form-control "/>
					</td>
					<td class="width-15 active"><label class="pull-right">25WT镀锌处理结果：</label></td>
					<td class="width-35">
						<form:input path="cduxin25result" htmlEscape="false" maxlength="200"    class="form-control "/>
					</td>
				</tr>
				&lt;%&ndash;<tr>
					<td class="width-15 active"><label class="pull-right">镀铝锌存在问题：</label></td>
					<td class="width-35">
						<form:input path="cdulvxinproblem" htmlEscape="false" maxlength="200"    class="form-control "/>
					</td>
					<td class="width-15 active"><label class="pull-right">镀铝锌处理结果：</label></td>
					<td class="width-35">
						<form:input path="cdulvxinresult" htmlEscape="false" maxlength="200"    class="form-control "/>
					</td>
				</tr>&ndash;%&gt;
				<tr class="zb tdsyzx">
					<td class="width-15 active"><label class="pull-right">薄板大仓库存在问题：</label></td>
					<td class="width-35">
						<form:input path="astoreproblem" htmlEscape="false" maxlength="200"    class="form-control "/>
					</td>
					<td class="width-15 active"><label class="pull-right">薄板大仓库处理结果：</label></td>
					<td class="width-35">
						<form:input path="astoreresult" htmlEscape="false" maxlength="200"    class="form-control "/>
					</td>
				</tr>
				<tr class="zb tdsyzx">
					<td class="width-15 active"><label class="pull-right">克罗德大仓库存在问题：</label></td>
					<td class="width-35">
						<form:input path="cstoreproblem" htmlEscape="false" maxlength="200"    class="form-control "/>
					</td>
					<td class="width-15 active"><label class="pull-right">克罗德大仓库处理结果：</label></td>
					<td class="width-35">
						<form:input path="cstoreresult" htmlEscape="false" maxlength="200"    class="form-control "/>
					</td>
				</tr>
				<tr class="zb tdsyzx">
					<td class="width-15 active"><label class="pull-right">薄板辅料仓库存在问题：</label></td>
					<td class="width-35">
						<form:input path="aflstoreproblem" htmlEscape="false" maxlength="200"    class="form-control "/>
					</td>
					<td class="width-15 active"><label class="pull-right">薄板辅料仓库处理结果：</label></td>
					<td class="width-35">
						<form:input path="aflstoreresult" htmlEscape="false" maxlength="200"    class="form-control "/>
					</td>
				</tr>
				<tr class="zb tdsyzx">
					<td class="width-15 active"><label class="pull-right">克罗德辅料仓库存在问题：</label></td>
					<td class="width-35">
						<form:input path="bflstoreproblem" htmlEscape="false" maxlength="200"    class="form-control "/>
					</td>
					<td class="width-15 active"><label class="pull-right">克罗德辅料仓库处理结果：</label></td>
					<td class="width-35">
						<form:input path="bflstoreresult" htmlEscape="false" maxlength="200"    class="form-control "/>
					</td>
				</tr>
				&lt;%&ndash;<tr>
					<td class="width-15 active"><label class="pull-right">cflstoreproblem：</label></td>
					<td class="width-35">
						<form:input path="cflstoreproblem" htmlEscape="false" maxlength="200"    class="form-control "/>
					</td>
					<td class="width-15 active"><label class="pull-right">cflstoreresult：</label></td>
					<td class="width-35">
						<form:input path="cflstoreresult" htmlEscape="false" maxlength="200"    class="form-control "/>
					</td>
				</tr>&ndash;%&gt;
				<tr class="zb jgsyzx">
					<td class="width-15 active"><label class="pull-right">综合办公楼存在问题：</label></td>
					<td class="width-35">
						<form:input path="officeproblem" htmlEscape="false" maxlength="200"    class="form-control "/>
					</td>
					<td class="width-15 active"><label class="pull-right">综合办公楼处理结果：</label></td>
					<td class="width-35">
						<form:input path="officeresult" htmlEscape="false" maxlength="200"    class="form-control "/>
					</td>
				</tr>
				<tr class="zb jgsyzx">
					<td class="width-15 active"><label class="pull-right">机房存在问题：</label></td>
					<td class="width-35">
						<form:input path="machineroomproblem" htmlEscape="false" maxlength="200"    class="form-control "/>
					</td>
					<td class="width-15 active"><label class="pull-right">机房处理结果：</label></td>
					<td class="width-35">
						<form:input path="machineroomresult" htmlEscape="false" maxlength="200"    class="form-control "/>
					</td>
				</tr>
				<tr class="zb jgsyzx">
					<td class="width-15 active"><label class="pull-right">辅料仓库存在问题：</label></td>
					<td class="width-35">
						<form:input path="flstoreproblem" htmlEscape="false" maxlength="200"    class="form-control "/>
					</td>
					<td class="width-15 active"><label class="pull-right">辅料仓库处理结果：</label></td>
					<td class="width-35">
						<form:input path="flstoreresult" htmlEscape="false" maxlength="200"    class="form-control "/>
					</td>
				</tr>
				<tr class="zb jgsyzx">
					<td class="width-15 active"><label class="pull-right">厂区及车间存在问题：</label></td>
					<td class="width-35">
						<form:input path="factoryworkproblem" htmlEscape="false" maxlength="200"    class="form-control "/>
					</td>
					<td class="width-15 active"><label class="pull-right">厂区及车间处理结果：</label></td>
					<td class="width-35">
						<form:input path="factoryworkresult" htmlEscape="false" maxlength="200"    class="form-control "/>
					</td>
				</tr>
				<tr class="zb jgsyzx">
					<td class="width-15 active"><label class="pull-right">配电间存在问题：</label></td>
					<td class="width-35">
						<form:input path="powerroomproblem" htmlEscape="false" maxlength="200"    class="form-control "/>
					</td>
					<td class="width-15 active"><label class="pull-right">配电间处理结果：</label></td>
					<td class="width-35">
						<form:input path="powerroomresult" htmlEscape="false" maxlength="200"    class="form-control "/>
					</td>
				</tr>
				<tr class="zb jgsyzx">
					<td class="width-15 active"><label class="pull-right">保管仓库存在问题：</label></td>
					<td class="width-35">
						<form:input path="bgstoreproblem" htmlEscape="false" maxlength="200"    class="form-control "/>
					</td>
					<td class="width-15 active"><label class="pull-right">保管仓库处理结果：</label></td>
					<td class="width-35">
						<form:input path="bgstoreresult" htmlEscape="false" maxlength="200"    class="form-control "/>
					</td>
				</tr>
				<tr class="zb jgsyzx">
					<td class="width-15 active"><label class="pull-right">余料仓库存在问题：</label></td>
					<td class="width-35">
						<form:input path="ylstoreproblem" htmlEscape="false" maxlength="200"    class="form-control "/>
					</td>
					<td class="width-15 active"><label class="pull-right">余料仓库处理结果：</label></td>
					<td class="width-35">
						<form:input path="ylstoreresult" htmlEscape="false" maxlength="200"    class="form-control "/>
					</td>
				</tr>
				<tr class="zb znzx">
					<td class="width-15 active"><label class="pull-right">涂镀事业中心存在问题：</label></td>
					<td class="width-35">
						<form:input path="tdbsproblem" htmlEscape="false" maxlength="200"    class="form-control "/>
					</td>
					<td class="width-15 active"><label class="pull-right">涂镀事业中心处理结果：</label></td>
					<td class="width-35">
						<form:input path="tdbsresult" htmlEscape="false" maxlength="200"    class="form-control "/>
					</td>
				</tr>
				<tr class="zb znzx">
					<td class="width-15 active"><label class="pull-right">钢管事业中心存在问题：</label></td>
					<td class="width-35">
						<form:input path="ggbsproblem" htmlEscape="false" maxlength="200"    class="form-control "/>
					</td>
					<td class="width-15 active"><label class="pull-right">钢管事业中心处理结果：</label></td>
					<td class="width-35">
						<form:input path="ggbsresult" htmlEscape="false" maxlength="200"    class="form-control "/>
					</td>
				</tr>
				<tr class="zb znzx">
					<td class="width-15 active"><label class="pull-right">加工事业中心存在问题：</label></td>
					<td class="width-35">
						<form:input path="jgbsproblem" htmlEscape="false" maxlength="200"    class="form-control "/>
					</td>
					<td class="width-15 active"><label class="pull-right">加工事业中心处理结果：</label></td>
					<td class="width-35">
						<form:input path="jgbsresult" htmlEscape="false" maxlength="200"    class="form-control "/>
					</td>
				</tr>

				<tr class="zb a1">
					<td class="width-15 active"><label class="pull-right">食堂存在问题：</label></td>
					<td class="width-35">
						<form:input path="eatroomproblem" htmlEscape="false" maxlength="200"    class="form-control "/>
					</td>
					<td class="width-15 active"><label class="pull-right">食堂处理结果：</label></td>
					<td class="width-35">
						<form:input path="eatroomresult" htmlEscape="false" maxlength="200"    class="form-control "/>
					</td>
				</tr>
				<tr class="zb a1">
					<td class="width-15 active"><label class="pull-right">宿舍存在问题：</label></td>
					<td class="width-35">
						<form:input path="dormitoryproblem" htmlEscape="false" maxlength="200"    class="form-control "/>
					</td>
					<td class="width-15 active"><label class="pull-right">宿舍处理结果：</label></td>
					<td class="width-35">
						<form:input path="dormitoryresult" htmlEscape="false" maxlength="200"    class="form-control "/>
					</td>
				</tr>
				<tr class="zb">
					<td class="width-15 active"><label class="pull-right">门卫存在问题：</label></td>
					<td class="width-35">
						<form:input path="guardproblem" htmlEscape="false" maxlength="200"    class="form-control "/>
					</td>
					<td class="width-15 active"><label class="pull-right">门卫处理结果：</label></td>
					<td class="width-35">
						<form:input path="guardresult" htmlEscape="false" maxlength="200"    class="form-control "/>
					</td>
				</tr>

				<tr class="db dbtdsy">
					<td class="width-15 active"><label class="pull-right">酸洗生产情况：</label></td>
					<td class="width-35">
						<form:input path="sxsc" htmlEscape="false" maxlength="200"    class="form-control "/>
					</td>
					<td class="width-15 active"><label class="pull-right">酸洗品质情况：</label></td>
					<td class="width-35">
						<form:input path="sxpz" htmlEscape="false" maxlength="200"    class="form-control "/>
					</td>
				</tr>
				<tr class="db dbtdsy">
					<td class="width-15 active"><label class="pull-right">酸洗设备：</label></td>
					<td class="width-35">
						<form:input path="sxsb" htmlEscape="false" maxlength="200"    class="form-control "/>
					</td>
					<td class="width-15 active"><label class="pull-right">酸洗安全：</label></td>
					<td class="width-35">
						<form:input path="sxaq" htmlEscape="false" maxlength="200"    class="form-control "/>
					</td>
				</tr>
				<tr class="db dbtdsy">
					<td class="width-15 active"><label class="pull-right">酸洗人员：</label></td>
					<td class="width-35">
						<form:input path="sxry" htmlEscape="false" maxlength="200"    class="form-control "/>
					</td>
					<td class="width-15 active"><label class="pull-right">酸洗其他：</label></td>
					<td class="width-35">
						<form:input path="sxqt" htmlEscape="false" maxlength="200"    class="form-control "/>
					</td>
				</tr>
				<tr class="db dbtdsy">
					<td class="width-15 active"><label class="pull-right">酸洗处理结果：</label></td>
					<td class="width-35">
						<form:input path="sxresult" htmlEscape="false" maxlength="200"    class="form-control "/>
					</td>
					<td class="width-15 active"><label class="pull-right">轧机生产情况：</label></td>
					<td class="width-35">
						<form:input path="zjsc" htmlEscape="false" maxlength="200"    class="form-control "/>
					</td>
				</tr>
				<tr class="db dbtdsy">
					<td class="width-15 active"><label class="pull-right">轧机品质情况：</label></td>
					<td class="width-35">
						<form:input path="zjpz" htmlEscape="false" maxlength="200"    class="form-control "/>
					</td>
					<td class="width-15 active"><label class="pull-right">轧机设备：</label></td>
					<td class="width-35">
						<form:input path="zjsb" htmlEscape="false" maxlength="200"    class="form-control "/>
					</td>
				</tr>
				<tr class="db dbtdsy">
					<td class="width-15 active"><label class="pull-right">轧机安全：</label></td>
					<td class="width-35">
						<form:input path="zjaq" htmlEscape="false" maxlength="200"    class="form-control "/>
					</td>
					<td class="width-15 active"><label class="pull-right">轧机人员：</label></td>
					<td class="width-35">
						<form:input path="zjry" htmlEscape="false" maxlength="200"    class="form-control "/>
					</td>
				</tr>
				<tr class="db dbtdsy">
					<td class="width-15 active"><label class="pull-right">轧机其他：</label></td>
					<td class="width-35">
						<form:input path="zjqt" htmlEscape="false" maxlength="200"    class="form-control "/>
					</td>
					<td class="width-15 active"><label class="pull-right">轧机处理结果：</label></td>
					<td class="width-35">
						<form:input path="zjresult" htmlEscape="false" maxlength="200"    class="form-control "/>
					</td>
				</tr>
				<tr class="db dbtdsy">
					<td class="width-15 active"><label class="pull-right">新轧机生产情况：</label></td>
					<td class="width-35">
						<form:input path="xzjsc" htmlEscape="false" maxlength="200"    class="form-control "/>
					</td>
					<td class="width-15 active"><label class="pull-right">新轧机品质情况：</label></td>
					<td class="width-35">
						<form:input path="xzjpz" htmlEscape="false" maxlength="200"    class="form-control "/>
					</td>
				</tr>
				<tr class="db dbtdsy">
					<td class="width-15 active"><label class="pull-right">新轧机设备：</label></td>
					<td class="width-35">
						<form:input path="xzjsb" htmlEscape="false" maxlength="200"    class="form-control "/>
					</td>
					<td class="width-15 active"><label class="pull-right">新轧机安全：</label></td>
					<td class="width-35">
						<form:input path="xzjaq" htmlEscape="false" maxlength="200"    class="form-control "/>
					</td>
				</tr>
				<tr class="db dbtdsy">
					<td class="width-15 active"><label class="pull-right">新轧机人员：</label></td>
					<td class="width-35">
						<form:input path="xzjry" htmlEscape="false" maxlength="200"    class="form-control "/>
					</td>
					<td class="width-15 active"><label class="pull-right">新轧机其他：</label></td>
					<td class="width-35">
						<form:input path="xzjqt" htmlEscape="false" maxlength="200"    class="form-control "/>
					</td>
				</tr>
				<tr class="db dbtdsy">
					<td class="width-15 active"><label class="pull-right">新轧机处理结果：</label></td>
					<td class="width-35">
						<form:input path="xzjresult" htmlEscape="false" maxlength="200"    class="form-control "/>
					</td>
					<td class="width-15 active"><label class="pull-right">25万吨镀锌生产情况：</label></td>
					<td class="width-35">
						<form:input path="wddxsc" htmlEscape="false" maxlength="200"    class="form-control "/>
					</td>
				</tr>
				<tr class="db dbtdsy">
					<td class="width-15 active"><label class="pull-right">25万吨镀锌品质情况：</label></td>
					<td class="width-35">
						<form:input path="wddxpz" htmlEscape="false" maxlength="200"    class="form-control "/>
					</td>
					<td class="width-15 active"><label class="pull-right">25万吨镀锌设备：</label></td>
					<td class="width-35">
						<form:input path="wddxsb" htmlEscape="false" maxlength="200"    class="form-control "/>
					</td>
				</tr>
				<tr class="db dbtdsy">
					<td class="width-15 active"><label class="pull-right">25万吨镀锌安全：</label></td>
					<td class="width-35">
						<form:input path="wddxaq" htmlEscape="false" maxlength="200"    class="form-control "/>
					</td>
					<td class="width-15 active"><label class="pull-right">25万吨镀锌人员：</label></td>
					<td class="width-35">
						<form:input path="wddxry" htmlEscape="false" maxlength="200"    class="form-control "/>
					</td>
				</tr>
				<tr class="db dbtdsy">
					<td class="width-15 active"><label class="pull-right">25万吨镀锌其他：</label></td>
					<td class="width-35">
						<form:input path="wddxqt" htmlEscape="false" maxlength="200"    class="form-control "/>
					</td>
					<td class="width-15 active"><label class="pull-right">25万吨镀锌处理结果：</label></td>
					<td class="width-35">
						<form:input path="wddxresult" htmlEscape="false" maxlength="200"    class="form-control "/>
					</td>
				</tr>
				<tr class="db dbtdsy">
					<td class="width-15 active"><label class="pull-right">15万吨镀铝锌生产情况：</label></td>
					<td class="width-35">
						<form:input path="wddlxsc" htmlEscape="false" maxlength="200"    class="form-control "/>
					</td>
					<td class="width-15 active"><label class="pull-right">15万吨镀铝锌品质情况：</label></td>
					<td class="width-35">
						<form:input path="wddlxpz" htmlEscape="false" maxlength="200"    class="form-control "/>
					</td>
				</tr>
				<tr class="db dbtdsy">
					<td class="width-15 active"><label class="pull-right">15万吨镀铝锌设备：</label></td>
					<td class="width-35">
						<form:input path="wddlxsb" htmlEscape="false" maxlength="200"    class="form-control "/>
					</td>
					<td class="width-15 active"><label class="pull-right">15万吨镀铝锌安全：</label></td>
					<td class="width-35">
						<form:input path="wddlxaq" htmlEscape="false" maxlength="200"    class="form-control "/>
					</td>
				</tr>
				<tr class="db dbtdsy">
					<td class="width-15 active"><label class="pull-right">15万吨镀铝锌人员：</label></td>
					<td class="width-35">
						<form:input path="wddlxry" htmlEscape="false" maxlength="200"    class="form-control "/>
					</td>
					<td class="width-15 active"><label class="pull-right">15万吨镀铝锌其他：</label></td>
					<td class="width-35">
						<form:input path="wddlxqt" htmlEscape="false" maxlength="200"    class="form-control "/>
					</td>
				</tr>
				<tr class="db dbtdsy">
					<td class="width-15 active"><label class="pull-right">15万吨镀铝锌处理结果：</label></td>
					<td class="width-35">
						<form:input path="wddlxresult" htmlEscape="false" maxlength="200"    class="form-control "/>
					</td>
					<td class="width-15 active"><label class="pull-right">薄板镀锌生产情况：</label></td>
					<td class="width-35">
						<form:input path="bbdxsc" htmlEscape="false" maxlength="200"    class="form-control "/>
					</td>
				</tr>
				<tr class="db dbtdsy">
					<td class="width-15 active"><label class="pull-right">薄板镀锌品质情况：</label></td>
					<td class="width-35">
						<form:input path="bbdxpz" htmlEscape="false" maxlength="200"    class="form-control "/>
					</td>
					<td class="width-15 active"><label class="pull-right">薄板镀锌设备：</label></td>
					<td class="width-35">
						<form:input path="bbdxsb" htmlEscape="false" maxlength="200"    class="form-control "/>
					</td>
				</tr>
				<tr class="db dbtdsy">
					<td class="width-15 active"><label class="pull-right">薄板镀锌安全：</label></td>
					<td class="width-35">
						<form:input path="bbdxaq" htmlEscape="false" maxlength="200"    class="form-control "/>
					</td>
					<td class="width-15 active"><label class="pull-right">薄板镀锌人员：</label></td>
					<td class="width-35">
						<form:input path="bbdxry" htmlEscape="false" maxlength="200"    class="form-control "/>
					</td>
				</tr>
				<tr class="db dbtdsy">
					<td class="width-15 active"><label class="pull-right">薄板镀锌其他：</label></td>
					<td class="width-35">
						<form:input path="bbdxqt" htmlEscape="false" maxlength="200"    class="form-control "/>
					</td>
					<td class="width-15 active"><label class="pull-right">薄板镀锌处理结果：</label></td>
					<td class="width-35">
						<form:input path="bbdxresult" htmlEscape="false" maxlength="200"    class="form-control "/>
					</td>
				</tr>
				<tr class="db dbtdsy">
					<td class="width-15 active"><label class="pull-right">彩涂生产情况：</label></td>
					<td class="width-35">
						<form:input path="ctsc" htmlEscape="false" maxlength="200"    class="form-control "/>
					</td>
					<td class="width-15 active"><label class="pull-right">彩涂品质情况：</label></td>
					<td class="width-35">
						<form:input path="ctpz" htmlEscape="false" maxlength="200"    class="form-control "/>
					</td>
				</tr>
				<tr class="db dbtdsy">
					<td class="width-15 active"><label class="pull-right">彩涂设备：</label></td>
					<td class="width-35">
						<form:input path="ctsb" htmlEscape="false" maxlength="200"    class="form-control "/>
					</td>
					<td class="width-15 active"><label class="pull-right">彩涂安全：</label></td>
					<td class="width-35">
						<form:input path="ctaq" htmlEscape="false" maxlength="200"    class="form-control "/>
					</td>
				</tr>
				<tr class="db dbtdsy">
					<td class="width-15 active"><label class="pull-right">彩涂人员：</label></td>
					<td class="width-35">
						<form:input path="ctry" htmlEscape="false" maxlength="200"    class="form-control "/>
					</td>
					<td class="width-15 active"><label class="pull-right">彩涂其他：</label></td>
					<td class="width-35">
						<form:input path="ctqt" htmlEscape="false" maxlength="200"    class="form-control "/>
					</td>
				</tr>
				<tr class="db dbtdsy">
					<td class="width-15 active"><label class="pull-right">彩涂处理结果：</label></td>
					<td class="width-35">
						<form:input path="ctresult" htmlEscape="false" maxlength="200"    class="form-control "/>
					</td>
					<td class="width-15 active"><label class="pull-right"></label></td>
					<td class="width-35">
					</td>
				</tr>

				<tr class="db dbggsy">
					<td class="width-15 active"><label class="pull-right">直缝车间生产情况：</label></td>
					<td class="width-35" colspan="3">
						<form:input path="zfcjsc" htmlEscape="false" maxlength="200"    class="form-control "/>
					</td>
				</tr>
				<tr class="db dbggsy">
					<td class="width-15 active"><label class="pull-right">直缝车间品质情况：</label></td>
					<td class="width-35">
						<form:input path="zfcjpz" htmlEscape="false" maxlength="200"    class="form-control "/>
					</td>
					<td class="width-15 active"><label class="pull-right">直缝车间设备：</label></td>
					<td class="width-35">
						<form:input path="zfcjsb" htmlEscape="false" maxlength="200"    class="form-control "/>
					</td>
				</tr>
				<tr class="db dbggsy">
					<td class="width-15 active"><label class="pull-right">直缝车间安全：</label></td>
					<td class="width-35">
						<form:input path="zfcjaq" htmlEscape="false" maxlength="200"    class="form-control "/>
					</td>
					<td class="width-15 active"><label class="pull-right">直缝车间人员：</label></td>
					<td class="width-35">
						<form:input path="zfcjry" htmlEscape="false" maxlength="200"    class="form-control "/>
					</td>
				</tr>
				<tr class="db dbggsy">
					<td class="width-15 active"><label class="pull-right">直缝车间其他：</label></td>
					<td class="width-35">
						<form:input path="zfcjqt" htmlEscape="false" maxlength="200"    class="form-control "/>
					</td>
					<td class="width-15 active"><label class="pull-right">直缝车间处理结果：</label></td>
					<td class="width-35">
						<form:input path="zfcjresult" htmlEscape="false" maxlength="200"    class="form-control "/>
					</td>
				</tr>
				<tr class="db dbggsy">
					<td class="width-15 active"><label class="pull-right">螺旋车间生产情况：</label></td>
					<td class="width-35">
						<form:input path="lxcjsc" htmlEscape="false" maxlength="200"    class="form-control "/>
					</td>
					<td class="width-15 active"><label class="pull-right">螺旋车间品质情况：</label></td>
					<td class="width-35">
						<form:input path="lxcjpz" htmlEscape="false" maxlength="200"    class="form-control "/>
					</td>
				</tr>
				<tr class="db dbggsy">
					<td class="width-15 active"><label class="pull-right">螺旋车间设备：</label></td>
					<td class="width-35">
						<form:input path="lxcjsb" htmlEscape="false" maxlength="200"    class="form-control "/>
					</td>
					<td class="width-15 active"><label class="pull-right">螺旋车间安全：</label></td>
					<td class="width-35">
						<form:input path="lxcjaq" htmlEscape="false" maxlength="200"    class="form-control "/>
					</td>
				</tr>
				<tr class="db dbggsy">
					<td class="width-15 active"><label class="pull-right">螺旋车间人员：</label></td>
					<td class="width-35">
						<form:input path="lxcjry" htmlEscape="false" maxlength="200"    class="form-control "/>
					</td>
					<td class="width-15 active"><label class="pull-right">螺旋车间其他：</label></td>
					<td class="width-35">
						<form:input path="lxcjqt" htmlEscape="false" maxlength="200"    class="form-control "/>
					</td>
				</tr>
				<tr class="db dbggsy">
					<td class="width-15 active"><label class="pull-right">螺旋车间处理结果：</label></td>
					<td class="width-35">
						<form:input path="lxcjresult" htmlEscape="false" maxlength="200"    class="form-control "/>
					</td>
					<td class="width-15 active"><label class="pull-right">防腐车间生产情况：</label></td>
					<td class="width-35">
						<form:input path="ffcjsc" htmlEscape="false" maxlength="200"    class="form-control "/>
					</td>
				</tr>
				<tr class="db dbggsy">
					<td class="width-15 active"><label class="pull-right">防腐车间品质情况：</label></td>
					<td class="width-35">
						<form:input path="ffcjpz" htmlEscape="false" maxlength="200"    class="form-control "/>
					</td>
					<td class="width-15 active"><label class="pull-right">防腐车间设备：</label></td>
					<td class="width-35">
						<form:input path="ffcjsb" htmlEscape="false" maxlength="200"    class="form-control "/>
					</td>
				</tr>
				<tr class="db dbggsy">
					<td class="width-15 active"><label class="pull-right">防腐车间安全：</label></td>
					<td class="width-35">
						<form:input path="ffcjaq" htmlEscape="false" maxlength="200"    class="form-control "/>
					</td>
					<td class="width-15 active"><label class="pull-right">防腐车间人员：</label></td>
					<td class="width-35">
						<form:input path="ffcjry" htmlEscape="false" maxlength="200"    class="form-control "/>
					</td>
				</tr>
				<tr class="db dbggsy">
					<td class="width-15 active"><label class="pull-right">防腐车间其他：</label></td>
					<td class="width-35">
						<form:input path="ffcjqt" htmlEscape="false" maxlength="200"    class="form-control "/>
					</td>
					<td class="width-15 active"><label class="pull-right">防腐车间处理结果：</label></td>
					<td class="width-35">
						<form:input path="ffcjresult" htmlEscape="false" maxlength="200"    class="form-control "/>
					</td>
				</tr>
				<tr class="db dbggsy">
					<td class="width-15 active"><label class="pull-right">现货仓库生产情况：</label></td>
					<td class="width-35">
						<form:input path="xhcksc" htmlEscape="false" maxlength="200"    class="form-control "/>
					</td>
					<td class="width-15 active"><label class="pull-right">现货仓库品质情况：</label></td>
					<td class="width-35">
						<form:input path="xhckpz" htmlEscape="false" maxlength="200"    class="form-control "/>
					</td>
				</tr>
				<tr class="db dbggsy">
					<td class="width-15 active"><label class="pull-right">现货仓库设备：</label></td>
					<td class="width-35">
						<form:input path="xhcksb" htmlEscape="false" maxlength="200"    class="form-control "/>
					</td>
					<td class="width-15 active"><label class="pull-right">现货仓库安全：</label></td>
					<td class="width-35">
						<form:input path="xhckaq" htmlEscape="false" maxlength="200"    class="form-control "/>
					</td>
				</tr>
				<tr class="db dbggsy">
					<td class="width-15 active"><label class="pull-right">现货仓库人员：</label></td>
					<td class="width-35">
						<form:input path="xhckry" htmlEscape="false" maxlength="200"    class="form-control "/>
					</td>
					<td class="width-15 active"><label class="pull-right">现货仓库其他：</label></td>
					<td class="width-35">
						<form:input path="xhckqt" htmlEscape="false" maxlength="200"    class="form-control "/>
					</td>
				</tr>
				<tr class="db dbggsy">
					<td class="width-15 active"><label class="pull-right">现货仓库处理结果：</label></td>
					<td class="width-35">
						<form:input path="xhckresult" htmlEscape="false" maxlength="200"    class="form-control "/>
					</td>
					<td class="width-15 active"><label class="pull-right">机修电工房生产情况：</label></td>
					<td class="width-35">
						<form:input path="jxdgsc" htmlEscape="false" maxlength="200"    class="form-control "/>
					</td>
				</tr>
				<tr class="db dbggsy">
					<td class="width-15 active"><label class="pull-right">机修电工房品质情况：</label></td>
					<td class="width-35">
						<form:input path="jxdgpz" htmlEscape="false" maxlength="200"    class="form-control "/>
					</td>
					<td class="width-15 active"><label class="pull-right">机修电工房设备：</label></td>
					<td class="width-35">
						<form:input path="jxdgsb" htmlEscape="false" maxlength="200"    class="form-control "/>
					</td>
				</tr>
				<tr class="db dbggsy">
					<td class="width-15 active"><label class="pull-right">机修电工房安全：</label></td>
					<td class="width-35">
						<form:input path="jxdgaq" htmlEscape="false" maxlength="200"    class="form-control "/>
					</td>
					<td class="width-15 active"><label class="pull-right">机修电工房人员：</label></td>
					<td class="width-35">
						<form:input path="jxdgry" htmlEscape="false" maxlength="200"    class="form-control "/>
					</td>
				</tr>
				<tr class="db dbggsy">
					<td class="width-15 active"><label class="pull-right">机修电工房其他：</label></td>
					<td class="width-35">
						<form:input path="jxdgqt" htmlEscape="false" maxlength="200"    class="form-control "/>
					</td>
					<td class="width-15 active"><label class="pull-right">机修电工房处理结果：</label></td>
					<td class="width-35">
						<form:input path="jxdgresult" htmlEscape="false" maxlength="200"    class="form-control "/>
					</td>
				</tr>

				<tr class="db">
					<td class="width-15 active"><label class="pull-right">其他：</label></td>
					<td class="width-35" colspan="3">
						<form:input path="theothers" htmlEscape="false" maxlength="200"    class="form-control "/>
					</td>
				</tr>
				<tr class="db">
					<td class="width-15 active"><label class="pull-right">办公楼：</label></td>
					<td class="width-35">
						<form:input path="bglpro" htmlEscape="false" maxlength="200"    class="form-control "/>
					</td>
					<td class="width-15 active"><label class="pull-right">办公楼处理结果：</label></td>
					<td class="width-35">
						<form:input path="bglres" htmlEscape="false" maxlength="200"    class="form-control "/>
					</td>
				</tr>
				<tr class="db">
					<td class="width-15 active"><label class="pull-right">品质化验：</label></td>
					<td class="width-35">
						<form:input path="bzhypro" htmlEscape="false" maxlength="200"    class="form-control "/>
					</td>
					<td class="width-15 active"><label class="pull-right">品质化验处理结果：</label></td>
					<td class="width-35">
						<form:input path="bzhyres" htmlEscape="false" maxlength="200"    class="form-control "/>
					</td>
				</tr>
				<tr class="db">
					<td class="width-15 active"><label class="pull-right">收发辅料：</label></td>
					<td class="width-35">
						<form:input path="sfflpro" htmlEscape="false" maxlength="200"    class="form-control "/>
					</td>
					<td class="width-15 active"><label class="pull-right">收发辅料处理结果：</label></td>
					<td class="width-35">
						<form:input path="sfflres" htmlEscape="false" maxlength="200"    class="form-control "/>
					</td>
				</tr>
				<tr class="db">
					<td class="width-15 active"><label class="pull-right">公辅：</label></td>
					<td class="width-35">
						<form:input path="gfpro" htmlEscape="false" maxlength="200"    class="form-control "/>
					</td>
					<td class="width-15 active"><label class="pull-right">公辅处理结果：</label></td>
					<td class="width-35">
						<form:input path="gfres" htmlEscape="false" maxlength="200"    class="form-control "/>
					</td>
				</tr>
				<tr class="db">
					<td class="width-15 active"><label class="pull-right">宿舍：</label></td>
					<td class="width-35">
						<form:input path="sshpro" htmlEscape="false" maxlength="200"    class="form-control "/>
					</td>
					<td class="width-15 active"><label class="pull-right">宿舍处理结果：</label></td>
					<td class="width-35">
						<form:input path="sshres" htmlEscape="false" maxlength="200"    class="form-control "/>
					</td>
				</tr>
				<tr class="db">
					<td class="width-15 active"><label class="pull-right">食堂：</label></td>
					<td class="width-35">
						<form:input path="shtpro" htmlEscape="false" maxlength="200"    class="form-control "/>
					</td>
					<td class="width-15 active"><label class="pull-right">食堂处理结果：</label></td>
					<td class="width-35">
						<form:input path="shtres" htmlEscape="false" maxlength="200"    class="form-control "/>
					</td>
				</tr>
				<tr class="db">
					<td class="width-15 active"><label class="pull-right">门卫：</label></td>
					<td class="width-35">
						<form:input path="mwpro" htmlEscape="false" maxlength="200"    class="form-control "/>
					</td>
					<td class="width-15 active"><label class="pull-right">门卫处理结果：</label></td>
					<td class="width-35">
						<form:input path="mwres" htmlEscape="false" maxlength="200"    class="form-control "/>
					</td>
				</tr>
				<tr class="db">
					<td class="width-15 active"><label class="pull-right">围墙周边：</label></td>
					<td class="width-35">
						<form:input path="wqzbpro" htmlEscape="false" maxlength="200"    class="form-control "/>
					</td>
					<td class="width-15 active"><label class="pull-right">围墙周边处理结果：</label></td>
					<td class="width-35">
						<form:input path="wqzbres" htmlEscape="false" maxlength="200"    class="form-control "/>
					</td>
				</tr>
				<tr class="db">
					<td class="width-15 active"><label class="pull-right">检修项目情况：</label></td>
					<td class="width-35">
						<form:input path="jxxmpro" htmlEscape="false" maxlength="200"    class="form-control "/>
					</td>
					<td class="width-15 active"><label class="pull-right">检修项目情况处理结果：</label></td>
					<td class="width-35">
						<form:input path="jxxmres" htmlEscape="false" maxlength="200"    class="form-control "/>
					</td>
				</tr>
				<tr class="db">
					<td class="width-15 active"><label class="pull-right">其他交接：</label></td>
					<td class="width-35" colspan="3">
						<form:input path="qtjj" htmlEscape="false" maxlength="200"    class="form-control "/>
					</td>
				</tr>
				<tr class="zb ggsyzx">
					<td class="width-15 active"><label class="pull-right">备注：</label></td>
					<td class="width-35">
						<form:input path="memo" htmlEscape="false" maxlength="200"    class="form-control "/>
					</td>
					<td class="width-15 active"></td>
					<td class="width-35" ></td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">补充总结：</label></td>
					<td class="width-35">
						<form:input path="summary" htmlEscape="false" maxlength="200"    class="form-control "/>
					</td>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>处理人确认：</label></td>
					<td class="width-35">
						<form:input path="signature" htmlEscape="false" maxlength="20"    class="form-control required"/>
					</td>
				</tr>
		 	</tbody>
		</table>--%>
			<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer tbdisable">
				<tr style="background:#EEEEEE; ">
					<td colspan="6" align="center" id="tle">
						<font size="5">
							<c:if test="${tbType=='1'}">值 班 记 录 表</c:if><c:if test="${tbType=='2'}">生产基地倒班交接</c:if></b>
							<c:if test="${tbType=='1'}"><div id="showtype" <c:if test="${tbType=='1'&&(duty.company!='4')}">style="display:none;"</c:if> ><b>(职能中心总经理填写)</b></div></c:if>
						</font>
					</td>
				</tr>
				<tr>
					<td colspan="6" align="right" >
						<c:if test="${tbType=='1'}">
							<form:radiobuttons path="company" items="${fns:getDictList('oa_company_duty')}" itemLabel="label" itemValue="value" htmlEscape="false" class="i-checks "/>
						</c:if>
						<c:if test="${tbType=='2'}">
							<form:radiobuttons path="company" items="${fns:getDictList('oa_company_dbduty')}" itemLabel="label" itemValue="value" htmlEscape="false" class="i-checks "/>
						</c:if>
					</td>
				</tr>
				<tr>
					<%--<td colspan="6" align="right">
						<font size="3"><b>日期：</b>
							<input type="text" id="date" name="duty.date" readonly="readonly" value="" style="width:70"/>
							<img onclick="WdatePicker({el:'date'})" src="${basePath}My97DatePicker/skin/datePicker.gif" width="16" height="22" style="vertical-align: middle; "></img>
							<input type="text" id = "hourFrom" name = "duty.hourFrom" value="" style="width:20">点
							<input type="text" id = "miniuteFrom" name = "duty.miniuteFrom" value="" style="width:20">分至
							<input type="text" id = "hourTo" name = "duty.hourTo" value="" style="width:20">点
							<input type="text" id = "miniuteTo" name = "duty.miniuteTo" value="" style="width:20">分</font>

					</td>--%>
					<td class="width-15 active"><label class="pull-right">开始时间：</label></td>
					<td class="width-35" colspan="2">
						<input id="startdate" name="startdate" type="text" maxlength="20" class="laydate-icon form-control layer-date "
							   value="<fmt:formatDate value="${duty.startdate}" pattern="yyyy-MM-dd HH:mm:ss"/>"/>
					</td>
					<td class="width-15 active"><label class="pull-right">结束时间：</label></td>
					<td class="width-35" colspan="2">
						<input id="enddate" name="enddate" type="text" maxlength="20" class="laydate-icon form-control layer-date "
							   value="<fmt:formatDate value="${duty.enddate}" pattern="yyyy-MM-dd HH:mm:ss"/>"/>
					</td>
				</tr>
				<tr align="center">
					<td class="width-15 active" ><label class="pull-right"><font color="red">*</font>值班人：</label></td><td colspan="5"><form:input path="dutier" htmlEscape="false" maxlength="20"    class="form-control required"/></td>
				</tr>
				<tr align="center">
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>部门：</label></td>
					<td class="width-35" colspan="2">
						<form:input path="department" htmlEscape="false" maxlength="20"    class="form-control required"/>
					</td>
					<td class="width-15 active"><label class="pull-right">职务：</label></td>
					<td class="width-35" colspan="2">
						<form:input path="post" htmlEscape="false" maxlength="20"    class="form-control "/>
					</td>
				</tr>
			</table>

			<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer tbdisable">
				<!-- 涂镀事业中心生产基地倒班 -->
				<tr align="left" name="etr" class="db">
					<td class="width-15 active"><label class="pull-right">白夜班：</label></td>
					<td class="width-35" colspan="4">
						<form:radiobuttons path="worktime" items="${fns:getDictList('oa_worktime')}" itemLabel="label" itemValue="value" htmlEscape="false" class="i-checks "/>
					</td>
					<td class="width-15 active"><label class="pull-right">交接人：</label></td>
					<td class="width-35" colspan="2">
						<form:input path="handover" htmlEscape="false" maxlength="20"    class="form-control "/>
					</td>
				</tr>
				<tr align="center" name="etr" class="db">
					<td style="font-weight:bold;">生产车间</td>
					<td style="font-weight:bold;">生产情况</td>
					<td style="font-weight:bold;">品质情况</td>
					<td style="font-weight:bold;">设备（机械/电气）</td>
					<td style="font-weight:bold;">安全</td>
					<td style="font-weight:bold;">人员</td>
					<td style="font-weight:bold;">其他</td>
					<td style="font-weight:bold;" >处理结果</td>
				</tr>
				<tr align="center" name="etr1" class="db dbtdsy">
					<td style="font-weight:bold; " width="120">酸洗</td>
					<td><form:input path="sxsc" htmlEscape="false" maxlength="200"    class="form-control "/></td>
					<td><form:input path="sxpz" htmlEscape="false" maxlength="200"    class="form-control "/></td>
					<td><form:input path="sxsb" htmlEscape="false" maxlength="200"    class="form-control "/></td>
					<td><form:input path="sxaq" htmlEscape="false" maxlength="200"    class="form-control "/></td>
					<td><form:input path="sxry" htmlEscape="false" maxlength="200"    class="form-control "/></td>
					<td><form:input path="sxqt" htmlEscape="false" maxlength="200"    class="form-control "/></td>
					<td><form:input path="sxresult" htmlEscape="false" maxlength="200"    class="form-control "/></td>
				</tr>
				<tr align="center" name="etr1" class="db dbtdsy">
					<td style="font-weight:bold; ">轧机</td>
					<td><form:input path="zjsc" htmlEscape="false" maxlength="200"    class="form-control "/></td>
					<td><form:input path="zjpz" htmlEscape="false" maxlength="200"    class="form-control "/></td>
					<td><form:input path="zjsb" htmlEscape="false" maxlength="200"    class="form-control "/></td>
					<td><form:input path="zjaq" htmlEscape="false" maxlength="200"    class="form-control "/></td>
					<td><form:input path="zjry" htmlEscape="false" maxlength="200"    class="form-control "/></td>
					<td><form:input path="zjqt" htmlEscape="false" maxlength="200"    class="form-control "/></td>
					<td><form:input path="zjresult" htmlEscape="false" maxlength="200"    class="form-control "/></td>
				</tr>
				<tr align="center" name="etr1" class="db dbtdsy">
					<td style="font-weight:bold; ">新轧机</td>
					<td><form:input path="xzjsc" htmlEscape="false" maxlength="200"    class="form-control "/></td>
					<td><form:input path="xzjpz" htmlEscape="false" maxlength="200"    class="form-control "/></td>
					<td><form:input path="xzjsb" htmlEscape="false" maxlength="200"    class="form-control "/></td>
					<td><form:input path="xzjaq" htmlEscape="false" maxlength="200"    class="form-control "/></td>
					<td><form:input path="xzjry" htmlEscape="false" maxlength="200"    class="form-control "/></td>
					<td><form:input path="xzjqt" htmlEscape="false" maxlength="200"    class="form-control "/></td>
					<td><form:input path="xzjresult" htmlEscape="false" maxlength="200"    class="form-control "/></td>
				</tr>
				<tr align="center" name="etr1" class="db dbtdsy">
					<td style="font-weight:bold; ">25万吨镀锌</td>
					<td><form:input path="wddxsc" htmlEscape="false" maxlength="200"    class="form-control "/></td>
					<td><form:input path="wddxpz" htmlEscape="false" maxlength="200"    class="form-control "/></td>
					<td><form:input path="wddxsb" htmlEscape="false" maxlength="200"    class="form-control "/></td>
					<td><form:input path="wddxaq" htmlEscape="false" maxlength="200"    class="form-control "/></td>
					<td><form:input path="wddxry" htmlEscape="false" maxlength="200"    class="form-control "/></td>
					<td><form:input path="wddxqt" htmlEscape="false" maxlength="200"    class="form-control "/></td>
					<td><form:input path="wddxresult" htmlEscape="false" maxlength="200"    class="form-control "/></td>
				</tr>
				<tr align="center" name="etr1" class="db dbtdsy">
					<td style="font-weight:bold; ">15万吨镀铝锌</td>
					<td><form:input path="wddlxsc" htmlEscape="false" maxlength="200"    class="form-control "/></td>
					<td><form:input path="wddlxpz" htmlEscape="false" maxlength="200"    class="form-control "/></td>
					<td><form:input path="wddlxsb" htmlEscape="false" maxlength="200"    class="form-control "/></td>
					<td><form:input path="wddlxaq" htmlEscape="false" maxlength="200"    class="form-control "/></td>
					<td><form:input path="wddlxry" htmlEscape="false" maxlength="200"    class="form-control "/></td>
					<td><form:input path="wddlxqt" htmlEscape="false" maxlength="200"    class="form-control "/></td>
					<td><form:input path="wddlxresult" htmlEscape="false" maxlength="200"    class="form-control "/></td>
				</tr>
				<tr align="center" name="etr1" class="db dbtdsy">
					<td style="font-weight:bold; ">薄板镀锌</td>
					<td><form:input path="bbdxsc" htmlEscape="false" maxlength="200"    class="form-control "/></td>
					<td><form:input path="bbdxpz" htmlEscape="false" maxlength="200"    class="form-control "/></td>
					<td><form:input path="bbdxsb" htmlEscape="false" maxlength="200"    class="form-control "/></td>
					<td><form:input path="bbdxaq" htmlEscape="false" maxlength="200"    class="form-control "/></td>
					<td><form:input path="bbdxry" htmlEscape="false" maxlength="200"    class="form-control "/></td>
					<td><form:input path="bbdxqt" htmlEscape="false" maxlength="200"    class="form-control "/></td>
					<td><form:input path="bbdxresult" htmlEscape="false" maxlength="200"    class="form-control "/></td>
				</tr>
				<tr align="center" name="etr1" class="db dbtdsy">
					<td style="font-weight:bold; ">彩涂</td>
					<td><form:input path="ctsc" htmlEscape="false" maxlength="200"    class="form-control "/></td>
					<td><form:input path="ctpz" htmlEscape="false" maxlength="200"    class="form-control "/></td>
					<td><form:input path="ctsb" htmlEscape="false" maxlength="200"    class="form-control "/></td>
					<td><form:input path="ctaq" htmlEscape="false" maxlength="200"    class="form-control "/></td>
					<td><form:input path="ctry" htmlEscape="false" maxlength="200"    class="form-control "/></td>
					<td><form:input path="ctqt" htmlEscape="false" maxlength="200"    class="form-control "/></td>
					<td><form:input path="ctresult" htmlEscape="false" maxlength="200"    class="form-control "/></td>
				</tr>

				<tr align="center" name="etr2" class="db dbggsy">
					<td style="font-weight:bold; " width="120">直缝车间</td>
					<td><form:input path="zfcjsc" htmlEscape="false" maxlength="200"    class="form-control "/></td>
					<td><form:input path="zfcjpz" htmlEscape="false" maxlength="200"    class="form-control "/></td>
					<td><form:input path="zfcjsb" htmlEscape="false" maxlength="200"    class="form-control "/></td>
					<td><form:input path="zfcjaq" htmlEscape="false" maxlength="200"    class="form-control "/></td>
					<td><form:input path="zfcjry" htmlEscape="false" maxlength="200"    class="form-control "/></td>
					<td><form:input path="zfcjqt" htmlEscape="false" maxlength="200"    class="form-control "/></td>
					<td><form:input path="zfcjresult" htmlEscape="false" maxlength="200"    class="form-control "/></td>
				</tr>
				<tr align="center" name="etr2" class="db dbggsy">
					<td style="font-weight:bold; ">螺旋车间</td>
					<td><form:input path="lxcjsc" htmlEscape="false" maxlength="200"    class="form-control "/></td>
					<td><form:input path="lxcjpz" htmlEscape="false" maxlength="200"    class="form-control "/></td>
					<td><form:input path="lxcjsb" htmlEscape="false" maxlength="200"    class="form-control "/></td>
					<td><form:input path="lxcjaq" htmlEscape="false" maxlength="200"    class="form-control "/></td>
					<td><form:input path="lxcjry" htmlEscape="false" maxlength="200"    class="form-control "/></td>
					<td><form:input path="lxcjqt" htmlEscape="false" maxlength="200"    class="form-control "/></td>
					<td><form:input path="lxcjresult" htmlEscape="false" maxlength="200"    class="form-control "/></td>
				</tr>
				<tr align="center" name="etr2" class="db dbggsy">
					<td style="font-weight:bold; ">防腐车间</td>
					<td><form:input path="ffcjsc" htmlEscape="false" maxlength="200"    class="form-control "/></td>
					<td><form:input path="ffcjpz" htmlEscape="false" maxlength="200"    class="form-control "/></td>
					<td><form:input path="ffcjsb" htmlEscape="false" maxlength="200"    class="form-control "/></td>
					<td><form:input path="ffcjaq" htmlEscape="false" maxlength="200"    class="form-control "/></td>
					<td><form:input path="ffcjry" htmlEscape="false" maxlength="200"    class="form-control "/></td>
					<td><form:input path="ffcjqt" htmlEscape="false" maxlength="200"    class="form-control "/></td>
					<td><form:input path="ffcjresult" htmlEscape="false" maxlength="200"    class="form-control "/></td>
				</tr>
				<tr align="center" name="etr2" class="db dbggsy">
					<td style="font-weight:bold; ">现货仓库</td>
					<td><form:input path="xhcksc" htmlEscape="false" maxlength="200"    class="form-control "/></td>
					<td><form:input path="xhckpz" htmlEscape="false" maxlength="200"    class="form-control "/></td>
					<td><form:input path="xhcksb" htmlEscape="false" maxlength="200"    class="form-control "/></td>
					<td><form:input path="xhckaq" htmlEscape="false" maxlength="200"    class="form-control "/></td>
					<td><form:input path="xhckry" htmlEscape="false" maxlength="200"    class="form-control "/></td>
					<td><form:input path="xhckqt" htmlEscape="false" maxlength="200"    class="form-control "/></td>
					<td><form:input path="xhckresult" htmlEscape="false" maxlength="200"    class="form-control "/></td>
				</tr>
				<tr align="center" name="etr2"  class="db dbggsy">
					<td style="font-weight:bold; ">机修、电工房</td>
					<td><form:input path="jxdgsc" htmlEscape="false" maxlength="200"    class="form-control "/></td>
					<td><form:input path="jxdgpz" htmlEscape="false" maxlength="200"    class="form-control "/></td>
					<td><form:input path="jxdgsb" htmlEscape="false" maxlength="200"    class="form-control "/></td>
					<td><form:input path="jxdgaq" htmlEscape="false" maxlength="200"    class="form-control "/></td>
					<td><form:input path="jxdgry" htmlEscape="false" maxlength="200"    class="form-control "/></td>
					<td><form:input path="jxdgqt" htmlEscape="false" maxlength="200"    class="form-control "/></td>
					<td><form:input path="jxdgresult" htmlEscape="false" maxlength="200"    class="form-control "/></td>
				</tr>

				<tr align="center" name="etr" class="db">
					<td style="font-weight:bold;">其他</td>
					<td colspan="7"><form:input path="theothers" htmlEscape="false" maxlength="200"    class="form-control "/></td>
				</tr>
				<tr align="center" name="etr" class="db">
					<td style="font-weight:bold; ">办公楼</td>
					<td colspan="6"><form:input path="bglpro" htmlEscape="false" maxlength="200"    class="form-control "/></td>
					<td colspan="1"><form:input path="bglres" htmlEscape="false" maxlength="200"    class="form-control "/></td>
				</tr>
				<tr align="center" name="etr" class="db">
					<td style="font-weight:bold; ">品质化验</td>
					<td colspan="6"><form:input path="bzhypro" htmlEscape="false" maxlength="200"    class="form-control "/></td>
					<td colspan="1"><form:input path="bzhyres" htmlEscape="false" maxlength="200"    class="form-control "/></td>
				</tr>
				<tr align="center" name="etr" class="db">
					<td style="font-weight:bold; ">收发辅料</td>
					<td colspan="6"><form:input path="sfflpro" htmlEscape="false" maxlength="200"    class="form-control "/></td>
					<td colspan="1"><form:input path="sfflres" htmlEscape="false" maxlength="200"    class="form-control "/></td>
				</tr>
				<tr align="center" name="etr" class="db">
					<td style="font-weight:bold; ">公辅</td>
					<td colspan="6"><form:input path="gfpro" htmlEscape="false" maxlength="200"    class="form-control "/></td>
					<td colspan="1"><form:input path="gfres" htmlEscape="false" maxlength="200"    class="form-control "/></td>
				</tr>
				<tr align="center" name="etr" class="db">
					<td style="font-weight:bold; ">宿舍</td>
					<td colspan="6"><form:input path="sshpro" htmlEscape="false" maxlength="200"    class="form-control "/></td>
					<td colspan="1"><form:input path="sshres" htmlEscape="false" maxlength="200"    class="form-control "/></td>
				</tr>
				<tr align="center" name="etr" class="db">
					<td style="font-weight:bold; ">食堂</td>
					<td colspan="6"><form:input path="shtpro" htmlEscape="false" maxlength="200"    class="form-control "/></td>
					<td colspan="1"><form:input path="shtres" htmlEscape="false" maxlength="200"    class="form-control "/></td>
				</tr>
				<tr align="center" name="etr" class="db">
					<td style="font-weight:bold; ">门卫</td>
					<td colspan="6"><form:input path="mwpro" htmlEscape="false" maxlength="200"    class="form-control "/></td>
					<td colspan="1"><form:input path="mwres" htmlEscape="false" maxlength="200"    class="form-control "/></td>
				</tr>
				<tr align="center" name="etr" class="db">
					<td style="font-weight:bold; ">围墙周边</td>
					<td colspan="6"><form:input path="wqzbpro" htmlEscape="false" maxlength="200"    class="form-control "/></td>
					<td colspan="1"><form:input path="wqzbres" htmlEscape="false" maxlength="200"    class="form-control "/></td>
				</tr>
				<tr align="center" name="etr" class="db">
					<td style="font-weight:bold; ">检修/项目情况</td>
					<td colspan="6"><form:input path="jxxmpro" htmlEscape="false" maxlength="200"    class="form-control "/></td>
					<td colspan="1"><form:input path="jxxmres" htmlEscape="false" maxlength="200"    class="form-control "/></td>
				</tr>
				<tr align="center" name="etr" class="db">
					<td style="font-weight:bold;">其他交接</td>
					<td colspan="7"><form:input path="qtjj" htmlEscape="false" maxlength="200"    class="form-control "/></td>
				</tr>


				<!-- 职能中心 -->
				<tr style="background:#EEEEEE; " id="d1" class="zb znzx">
					<td colspan="8" align="center">
						<font size="+2"><b>各事业中心值班情况记录</b> </font>
					</td>
				</tr>

				<tr align="center" id="d2" class="zb znzx">
					<td><b>类别</b></td>
					<td colspan="4"><b>存在问题</b></td>
					<td colspan="2"><b>处理结果</b></td>
				</tr>
				<tr align="center" id="d3" class="zb znzx">
					<td ><b>涂镀事业中心</b></td>
					<td colspan="4"><form:textarea path="tdbsproblem" htmlEscape="false" maxlength="200"    class="form-control "/></td>
					<td colspan="2"><form:textarea path="tdbsresult" htmlEscape="false" maxlength="200"    class="form-control "/></td>
				</tr>
				<tr align="center" id="d4" class="zb znzx">
					<td><b>钢管事业中心</b></td>
					<td colspan="4"><form:textarea path="ggbsproblem" htmlEscape="false" maxlength="200"    class="form-control "/></td>
					<td colspan="2"><form:textarea path="ggbsresult" htmlEscape="false" maxlength="200"    class="form-control "/></td>
				</tr>
				<tr align="center" id="d5" class="zb znzx">
					<td><b>加工事业中心</b></td>
					<td colspan="4"><form:textarea path="jgbsproblem" htmlEscape="false" maxlength="200"    class="form-control "/></td>
					<td colspan="2"><form:textarea path="jgbsresult" htmlEscape="false" maxlength="200"    class="form-control "/></td>
				</tr>

				<tr style="background:#EEEEEE; " id="common0" class="zb a1">
					<td colspan="8" align="center">
						<font size="4"><b>巡查内容记录</b> </font>
					</td>
				</tr>
				<tr align="center" id="common1" class="zb zba2">
					<td><b>区域</b></td><td><b>巡查项目</b></td>
					<td colspan="4"><b>存在问题</b></td>
					<td colspan="2"><b>处理结果</b></td>
				</tr>

				<!-- 钢管事业中心 -->
				<tr align="center" id="a1" class="zb ggsyzx">
					<td rowspan="3"><b>办公区域</b></td>
					<td style="width:80"><b>浙华办公楼</b></td>
					<td colspan="4"><form:input path="zhehuaproblem" htmlEscape="false" maxlength="200"    class="form-control "/></td>
					<td colspan="2"><form:input path="zhehuaresult" htmlEscape="false" maxlength="200"    class="form-control "/></td>
				</tr>
				<tr align="center" id="a2" class="zb ggsyzx">
					<td><b>北门保安室</b></td>
					<td colspan="4"><form:input path="beimenproblem" htmlEscape="false" maxlength="200"    class="form-control "/></td>
					<td colspan="2"><form:input path="beimenresult" htmlEscape="false" maxlength="200"    class="form-control "/></td>
				</tr>
				<tr align="center" id="a3" class="zb ggsyzx">
					<td><b>生产办公室</b></td>
					<td colspan="4"><form:input path="shengchanproblem" htmlEscape="false" maxlength="200"    class="form-control "/></td>
					<td colspan="2"><form:input path="shengchanresult" htmlEscape="false" maxlength="200"    class="form-control "/></td>
				</tr>
				<tr align="center" id="a4" class="zb ggsyzx">
					<td rowspan="6"><b>生产区域<br/>（含室外）</b></td>
					<td><b>直缝车间</b></td>
					<td colspan="4"><form:input path="zfroomproblem" htmlEscape="false" maxlength="200"    class="form-control "/></td>
					<td colspan="2"><form:input path="zfroomresult" htmlEscape="false" maxlength="200"    class="form-control "/></td>
				</tr>
				<tr align="center" id="a5" class="zb ggsyzx">
					<td><b>螺旋车间</b></td>
					<td colspan="4"><form:input path="lxroomresult" htmlEscape="false" maxlength="200"    class="form-control "/></td>
					<td colspan="2"><form:input path="lxroomproblem" htmlEscape="false" maxlength="200"    class="form-control "/></td>
				</tr>
				<tr align="center" id="a6" class="zb ggsyzx">
					<td><b>防腐车间</b></td>
					<td colspan="4"><form:input path="ffroomproblem" htmlEscape="false" maxlength="200"    class="form-control "/></td>
					<td colspan="2"><form:input path="ffroomresult" htmlEscape="false" maxlength="200"    class="form-control "/></td>
				</tr>
				<tr align="center" id="a7" class="zb ggsyzx">
					<td><b>现货仓库</b></td>
					<td colspan="4"><form:input path="xhstoreproblem" htmlEscape="false" maxlength="200"    class="form-control "/></td>
					<td colspan="2"><form:input path="xhstoreresult" htmlEscape="false" maxlength="200"    class="form-control "/></td>
				</tr>
				<tr align="center" id="a8" class="zb ggsyzx">
					<td><b>机修、电工房</b></td>
					<td colspan="4"><form:input path="dgroomproblem" htmlEscape="false" maxlength="200"    class="form-control "/></td>
					<td colspan="2"><form:input path="dgroomresult" htmlEscape="false" maxlength="200"    class="form-control "/></td>
				</tr>
				<tr align="center" id="a9" class="zb ggsyzx">
					<td><b>收发、辅料仓库</b></td>
					<td colspan="4"><form:input path="sfflstoreproblem" htmlEscape="false" maxlength="200"    class="form-control "/></td>
					<td colspan="2"><form:input path="sfflstoreresult" htmlEscape="false" maxlength="200"    class="form-control "/></td>
				</tr>


				<!-- 加工事业中心 -->

				<tr align="center" id="b1" class="zb jgsyzx">
					<td rowspan="3" style="width="100"><b>办公楼<br/>（含室外）</b></td>
					<td><b>综合办公楼</b></td>
					<td colspan="4"><form:input path="officeproblem" htmlEscape="false" maxlength="200"    class="form-control "/></td>
					<td colspan="2"><form:input path="officeresult" htmlEscape="false" maxlength="200"    class="form-control "/></td>
				</tr>
				<tr align="center" id="b2" class="zb jgsyzx">
					<td><b>机房</b></td>
					<td colspan="4"><form:input path="machineroomproblem" htmlEscape="false" maxlength="200"    class="form-control "/></td>
					<td colspan="2"><form:input path="machineroomresult" htmlEscape="false" maxlength="200"    class="form-control "/></td>
				</tr>
				<tr align="center" id="b3" class="zb jgsyzx">
					<td><b>辅料仓库</b></td>
					<td colspan="4"><form:input path="flstoreproblem" htmlEscape="false" maxlength="200"    class="form-control "/></td>
					<td colspan="2"><form:input path="flstoreresult" htmlEscape="false" maxlength="200"    class="form-control "/></td>
				</tr>
				<tr align="center" id="b4" class="zb jgsyzx">
					<td rowspan="4"><b>生产区域<br/>（含室外）</b></td>
					<td><b>厂区及车间</b></td>
					<td colspan="4"><form:input path="factoryworkproblem" htmlEscape="false" maxlength="200"    class="form-control "/></td>
					<td colspan="2"><form:input path="factoryworkresult" htmlEscape="false" maxlength="200"    class="form-control "/></td>
				</tr>
				<tr align="center" id="b5" class="zb jgsyzx">
					<td><b>配电间</b></td>
					<td colspan="4"><form:input path="powerroomproblem" htmlEscape="false" maxlength="200"    class="form-control "/></td>
					<td colspan="2"><form:input path="powerroomresult" htmlEscape="false" maxlength="200"    class="form-control "/></td>
				</tr>
				<tr align="center" id="b6" class="zb jgsyzx">
					<td><b>保管仓库</b></td>
					<td colspan="4"><form:input path="bgstoreproblem" htmlEscape="false" maxlength="200"    class="form-control "/></td>
					<td colspan="2"><form:input path="bgstoreresult" htmlEscape="false" maxlength="200"    class="form-control "/></td>
				</tr>
				<tr align="center" id="b7" class="zb jgsyzx">
					<td><b>余料仓库</b></td>
					<td colspan="4"><form:input path="ylstoreproblem" htmlEscape="false" maxlength="200"    class="form-control "/></td>
					<td colspan="2"><form:input path="ylstoreresult" htmlEscape="false" maxlength="200"    class="form-control "/></td>
				</tr>


				<!-- 涂镀事业中心 -->

				<tr align="center" id="c1" class="zb tdsyzx">
					<td rowspan="3"><b>办公楼  <br/></b></td>
					<td><b>薄板办公楼</b></td>
					<td colspan="4"><form:input path="bobanofficeproblem" htmlEscape="false" maxlength="200"    class="form-control "/></td>
					<td colspan="2"><form:input path="bobanofficeresult" htmlEscape="false" maxlength="200"    class="form-control "/></td>
				</tr>
				<tr align="center" id="c2" class="zb tdsyzx">
					<td><b>监控室</b></td>
					<td colspan="4"><form:input path="monitorroomproblem" htmlEscape="false" maxlength="200"    class="form-control "/></td>
					<td colspan="2"><form:input path="monitorroomresult" htmlEscape="false" maxlength="200"    class="form-control "/></td>
				</tr>
				<tr align="center" id="c3" class="zb tdsyzx">
					<td><b>克罗德办公楼</b></td>
					<td colspan="4"><form:input path="kldofficeproblem" htmlEscape="false" maxlength="200"    class="form-control "/></td>
					<td colspan="2"><form:input path="kldofficeresult" htmlEscape="false" maxlength="200"    class="form-control "/></td>
				</tr>
				<tr align="center" id="c4" class="zb tdsyzx">
					<td rowspan="7"><b>生产区域</b></td>
					<td><b>薄板镀锌线</b></td>
					<td colspan="4"><form:input path="aduxinproblem" htmlEscape="false" maxlength="200"    class="form-control "/></td>
					<td colspan="2"><form:input path="aduxinresult" htmlEscape="false" maxlength="200"    class="form-control "/></td>
				</tr>
				<tr align="center" id="c5" class="zb tdsyzx">
					<td><b>薄板彩涂线</b></td>
					<td colspan="4"><form:input path="atucaiproblem" htmlEscape="false" maxlength="200"    class="form-control "/></td>
					<td colspan="2"><form:input path="atucairesult" htmlEscape="false" maxlength="200"    class="form-control "/></td>
				</tr>
				<tr align="center" id="c6" class="zb tdsyzx">
					<td><b>酸洗产线</b></td>
					<td colspan="4"><form:input path="bsuanxiproblem" htmlEscape="false" maxlength="200"    class="form-control "/></td>
					<td colspan="2"><form:input path="bsuanxiresult" htmlEscape="false" maxlength="200"    class="form-control "/></td>
				</tr>
				<tr align="center" id="c7" class="zb tdsyzx">
					<td><b>1#轧机</b></td>
					<td colspan="4"><form:input path="bzhaji1problem" htmlEscape="false" maxlength="200"    class="form-control "/></td>
					<td colspan="2"><form:input path="bzhaji1result" htmlEscape="false" maxlength="200"    class="form-control "/></td>
				</tr>
				<tr align="center" id="c8" class="zb tdsyzx">
					<td><b>2#轧机</b></td>
					<td colspan="4"><form:input path="bzhaji2problem" htmlEscape="false" maxlength="200"    class="form-control "/></td>
					<td colspan="2"><form:input path="bzhaji2result" htmlEscape="false" maxlength="200"    class="form-control "/></td>
				</tr>
				<tr align="center" id="c9" class="zb tdsyzx">
					<td><b>15WT镀锌</b></td>
					<td colspan="4"><form:input path="cduxin15problem" htmlEscape="false" maxlength="200"    class="form-control "/></td>
					<td colspan="2"><form:input path="cduxin15result" htmlEscape="false" maxlength="200"    class="form-control "/></td>
				</tr>
				<tr align="center" id="c10" class="zb tdsyzx">
					<td><b>25WT镀锌</b></td>
					<td colspan="4"><form:input path="cduxin25problem" htmlEscape="false" maxlength="200"    class="form-control "/></td>
					<td colspan="2"><form:input path="cduxin25result" htmlEscape="false" maxlength="200"    class="form-control "/></td>
				</tr>
				<tr align="center" id="c11" class="zb tdsyzx">
					<td rowspan="4"><b>仓库</b></td>
					<td><b>薄板大仓库</b></td>
					<td colspan="4"><form:input path="astoreproblem" htmlEscape="false" maxlength="200"    class="form-control "/></td>
					<td colspan="2"><form:input path="astoreresult" htmlEscape="false" maxlength="200"    class="form-control "/></td>
				</tr>
				<tr align="center" id="c12" class="zb tdsyzx">
					<td><b>克罗德大仓库</b></td>
					<td colspan="4"><form:input path="cstoreproblem" htmlEscape="false" maxlength="200"    class="form-control "/></td>
					<td colspan="2"><form:input path="cstoreresult" htmlEscape="false" maxlength="200"    class="form-control "/></td>
				</tr>
				<tr align="center" id="c13" class="zb tdsyzx">
					<td><b>薄板辅料仓库</b></td>
					<td colspan="4"><form:input path="aflstoreproblem" htmlEscape="false" maxlength="200"    class="form-control "/></td>
					<td colspan="2"><form:input path="aflstoreresult" htmlEscape="false" maxlength="200"    class="form-control "/></td>
				</tr>
				<tr align="center" id="c14" class="zb tdsyzx">
					<td><b>克罗德辅料仓库</b></td>
					<td colspan="4"><form:input path="bflstoreproblem" htmlEscape="false" maxlength="200"    class="form-control "/></td>
					<td colspan="2"><form:input path="bflstoreresult" htmlEscape="false" maxlength="200"    class="form-control "/></td>
				</tr>



				<tr align="center" id="common2" class="zb a1">
					<td rowspan="3"><b>生活区域</b></td>
					<td><b>食堂</b></td>
					<td colspan="4"><form:input path="eatroomproblem" htmlEscape="false" maxlength="200"    class="form-control "/></td>
					<td colspan="2"><form:input path="eatroomresult" htmlEscape="false" maxlength="200"    class="form-control "/></td>
				</tr>
				<tr align="center" class="zb a1">
					<td><b>宿舍</b></td>
					<td colspan="4"><form:input path="dormitoryproblem" htmlEscape="false" maxlength="200"    class="form-control "/></td>
					<td colspan="2"><form:input path="dormitoryresult" htmlEscape="false" maxlength="200"    class="form-control "/></td>
				</tr>
				<tr align="center" class="zb">
					<td><b>门卫</b></td>
					<td colspan="4"><form:input path="guardproblem" htmlEscape="false" maxlength="200"    class="form-control "/></td>
					<td colspan="2"><form:input path="guardresult" htmlEscape="false" maxlength="200"    class="form-control "/></td>
				</tr>


			</table>

			<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer tbdisable">
				<tr class="zb ggsyzx">
					<td rowspan="2" align="center" colspan="2"><b>备注</b></td>
					<td colspan="4"><form:textarea path="memo" htmlEscape="false" maxlength="200"    class="form-control "/></td>
				</tr>
			</table>

			<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer tbdisable">
				<tr>
					<td rowspan="2" align="center" colspan="2"><b>补充总结</b></td>
					<td colspan="4"><form:textarea path="summary" htmlEscape="false" maxlength="200"    class="form-control "/></td>
				</tr>
				<tr>
					<td colspan="3" align="right"><b><font color="red">*</font>处理人确认：</b></td>
					<td align="center"><form:input path="signature" htmlEscape="false" maxlength="20"    class="form-control required"/></td>
				</tr>
				<tr>
					<td colspan="6">
						<font size="2">
							<b> 具体要求：</b><br/>
							1、值班记录是值班人员工作状态的真实记录，应在巡视后填写；<br/>
							2、值班记录各项内容需如实填写和不得空漏；<br/>
							3、有特殊情况或突发事件的，需第一时间汇报直属领导及基地负责人以便进行妥善处理；<br/>
							4、违反以上任一要求的，严肃考核。
						</font>
					</td>
				</tr>
			</table>
		</form:form>
</body>
</html>