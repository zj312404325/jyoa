<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>周报管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		var validateForm;
		function doSubmit(){//回调函数，在编辑和保存动作时，供openDialog调用提交表单。
		  if(validateForm.form()){
		      if(validScore()){
                  $("#inputForm").submit();
                  return true;
			  }
		  }
	
		  return false;
		}
		//考核得分不能超过100
		function validScore(){
		    var score=$("#score").val();
		    if(score!=null&&score!=''){
		        if(score>100){
                    layer.alert("考核得分不能超过100",{icon: 2});
		            return false;
				}
			}
			return true;
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
	<form:form id="inputForm" modelAttribute="weeklyReport" action="${ctx}/checkmodel/reports/save" method="post" class="form-horizontal">
		<input type="hidden" name="type" value="${type }" />
		<form:hidden path="id"/>
		<sys:message content="${message}"/>	
		<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
		   <tbody>
			   <tr>
				   <td class="width-15 active"><label class="pull-right"><font color="red">*</font>周报标题：</label></td>
				   <%--<td class="width-35">
					   <form:input path="title" htmlEscape="false"    class="form-control required"/>
				   </td>--%>
				   <td class="width-35">
					   <input id="title" name="title" type="text"  class="form-control"
							  value="${weeklyReport.title}" disabled="disabled"/>
				   </td>
			   </tr>
		 	</tbody>
		</table>
		<!-- 上周工作start -->
		<div class="tabs-container">
            <ul class="nav nav-tabs">
				<li class="active">
					<a data-toggle="tab" href="#tab-1" aria-expanded="true">上周工作</a>
				</li>
            </ul>
            <div class="tab-content">
				<div id="tab-1" class="tab-pane active">
			<table id="contentTable1" class="table table-striped table-bordered table-condensed">
				<thead>
					<tr>
						<th class="hide"></th>
						<th width="40">项目</th>
						<th width="60">任务类型</th>
						<th width="220">任务描述</th>
						<th width="220">进展情况描述</th>
						<th width="60">任务状态</th>
						<th width="20">花费时间(天)</th>
						<th width="10">&nbsp;</th>
					</tr>
				</thead>
				<tbody id="weeklyReportDetailList">
				</tbody>
			</table>
			<script type="text/template" id="weeklyReportDetailTpl">//<!--
				<tr id="weeklyReportDetailList{{idx}}">
					<td class="hide">
						<input id="weeklyReportDetailList{{idx}}_id" name="weeklyReportDetailList[{{idx}}].id" type="hidden" value="{{row.id}}"/>
						<input id="weeklyReportDetailList{{idx}}_delFlag" name="weeklyReportDetailList[{{idx}}].delFlag" type="hidden" value="0"/>
						<input id="weeklyReportDetailList{{idx}}_type" name="weeklyReportDetailList[{{idx}}].type" type="hidden" value="0"/>
					</td>

					<td width="40">
						<input id="weeklyReportDetailList{{idx}}_project" name="weeklyReportDetailList[{{idx}}].project" type="text" value="{{row.project}}"  disabled="disabled"  class="form-control required" maxlength=50/>
					</td>

					<td width="60">
						<input id="weeklyReportDetailList{{idx}}_taskType" name="weeklyReportDetailList[{{idx}}].tasktype" type="text" value="{{row.tasktype}}"  disabled="disabled"  class="form-control required" maxlength=50/>
					</td>

					<td width="220">
						<input id="weeklyReportDetailList{{idx}}_taskDesc" name="weeklyReportDetailList[{{idx}}].taskdesc" type="text" value="{{row.taskdesc}}"  readonly  class="form-control required" maxlength=50 onmouseover="this.title=this.value"/>
					</td>


					<td width="220">
						<input id="weeklyReportDetailList{{idx}}_content" name="weeklyReportDetailList[{{idx}}].content" type="text" value="{{row.content}}"  readonly  class="form-control required" maxlength=100 onmouseover="this.title=this.value"/>
					</td>


					<td width="60">
						<input id="weeklyReportDetailList{{idx}}_taskStatus" name="weeklyReportDetailList[{{idx}}].taskstatus" type="text" value="{{row.taskstatus}}"  disabled="disabled"  class="form-control required " maxlength=50/>
					</td>


					<td width="20">
						<input id="weeklyReportDetailList{{idx}}_costDays" name="weeklyReportDetailList[{{idx}}].costdays" type="text" value="{{row.costdays}}"  disabled="disabled"  class="form-control required digits" onblur="cv('digits',this)"/>
					</td>

					<td class="text-center" width="10">

					</td>
				</tr>//-->
			</script>
			<script type="text/javascript">
				var weeklyReportDetailRowIdx = 0, weeklyReportDetailTpl = $("#weeklyReportDetailTpl").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g,"");
				$(document).ready(function() {
					var data = ${fns:toJson(weeklyReport.weeklyReportDetailList)};
					for (var i=0; i<data.length; i++){
						addRow('#weeklyReportDetailList', weeklyReportDetailRowIdx, weeklyReportDetailTpl, data[i]);
                        weeklyReportDetailRowIdx = weeklyReportDetailRowIdx + 1;
					}
				});
			</script>
			</div>
			</div>
		</div>
		<!-- 上周工作end -->
		
		<!-- 本周工作start -->
		<div class="tabs-container">
			<ul class="nav nav-tabs">
				<li class="active">
					<a data-toggle="tab" href="#tab-2" aria-expanded="true">本周计划任务</a>
				</li>
			</ul>
			<div class="tab-content">
			<div id="tab-2" class="tab-pane active">
			<table id="contentTable2" class="table table-striped table-bordered table-condensed">
				<thead>
					<tr>
						<th class="hide"></th>
						<th width="40">项目</th>
						<th width="60">任务类型</th>
						<th width="220">任务描述</th>
						<th width="220">计划进行任务方向</th>
						<th width="60">任务状态</th>
						<th width="20">花费时间(天)</th>
						<th width="10">&nbsp;</th>
					</tr>
				</thead>
				<tbody id="weeklyReportDetailListKey">
				</tbody>
			</table>
			<script type="text/template" id="weeklyReportDetailTplKey">//<!--
				<tr id="weeklyReportDetailListKey{{idx}}">
					<td class="hide">
						<input id="weeklyReportDetailListKey{{idx}}_id" name="weeklyReportDetailListKey[{{idx}}].id" type="hidden" value="{{row.id}}"/>
						<input id="weeklyReportDetailListKey{{idx}}_delFlag" name="weeklyReportDetailListKey[{{idx}}].delFlag" type="hidden" value="0"/>
						<input id="weeklyReportDetailListKey{{idx}}_type" name="weeklyReportDetailListKey[{{idx}}].type" type="hidden" value="1"/>
					</td>

					<td width="40">
						<input id="weeklyReportDetailListKey{{idx}}_project" name="weeklyReportDetailListKey[{{idx}}].project" type="text" value="{{row.project}}"  disabled="disabled"  class="form-control required" maxlength=50/>
					</td>

					<td width="60">
						<input id="weeklyReportDetailListKey{{idx}}_taskType" name="weeklyReportDetailListKey[{{idx}}].tasktype" type="text" value="{{row.tasktype}}"  disabled="disabled"  class="form-control required" maxlength=50/>
					</td>

					<td width="220">
						<input id="weeklyReportDetailListKey{{idx}}_taskDesc" name="weeklyReportDetailListKey[{{idx}}].taskdesc" type="text" value="{{row.taskdesc}}"  readonly  class="form-control required" maxlength=50 onmouseover="this.title=this.value"/>
					</td>


					<td width="220">
						<input id="weeklyReportDetailListKey{{idx}}_content" name="weeklyReportDetailListKey[{{idx}}].content" type="text" value="{{row.content}}"  readonly  class="form-control required" maxlength=100 onmouseover="this.title=this.value"/>
					</td>


					<td width="60">
						<input id="weeklyReportDetailListKey{{idx}}_taskStatus" name="weeklyReportDetailListKey[{{idx}}].taskstatus" type="text" value="{{row.taskstatus}}"  disabled="disabled"  class="form-control required " maxlength=50/>
					</td>


					<td width="20">
						<input id="weeklyReportDetailListKey{{idx}}_costDays" name="weeklyReportDetailListKey[{{idx}}].costdays" type="text" value="{{row.costdays}}"  disabled="disabled"  class="form-control required digits" onblur="cv('digits',this)"/>
					</td>
					
					<td class="text-center" width="10">
						
					</td>
				</tr>//-->
			</script>
			<script type="text/javascript">
				var weeklyReportDetailRowIdxKey = 0,weeklyReportDetailRowIdxKey = 0, weeklyReportDetailTplKey = $("#weeklyReportDetailTplKey").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g,"");
				$(document).ready(function() {
					var data = ${fns:toJson(weeklyReport.weeklyReportDetailListKey)};
					for (var i=0; i<data.length; i++){
						addRow('#weeklyReportDetailListKey', weeklyReportDetailRowIdxKey, weeklyReportDetailTplKey, data[i]);
						weeklyReportDetailRowIdxKey = weeklyReportDetailRowIdxKey + 1;
					}



					
				});

                function compare(obj1){
                    var num1 = $(obj1).closest("tr").find("td:eq(2)").find("input[text]").val();
                    var num2 = $(obj1).val();
                    if(Number(num2) > Number(num1)){
                        top.layer.alert("考核得分不能超过权重！",{icon:2});
                        $(obj1).val("");
                        $('#score').val(0);

                    }
                }

                function cv(obj){
                    compare(obj);
                    var valueArr= $("#weeklyReportDetailList").find("tr").find("td:last").prev("td").find("input[type=text]");
					console.log(valueArr);
                    var sumValue=0;
                    if(valueArr.size()>0||valueArr != null){
                        for (var i=0;i<valueArr.size();i++ )
                        {
                            //alert(valueArr[i].value);
                            if(isNaN(valueArr[i].value)||valueArr[i].value==null||valueArr[i].value==""){
                                sumValue +=0;
                            }
                            else{
                                //alert(valueArr[i].value);
                                sumValue +=parseFloat(valueArr[i].value);

                            }

                        }
                        if(sumValue > 100){
                                top.layer.alert("考核总分不能超过100分！",{icon:2});
                                $(obj).val("");
                            	$('#score').val(0);
                                return;
                            }
                        }

                        if(sumValue>0){
                            $('#score').val(parseInt(sumValue));
                        }
                        else{
                            $('#score').val(0);
                        }
                }

			</script>
			</div>
		</div>
		</div>
		<!-- 本周工作end -->

	</form:form>
</body>
</html>