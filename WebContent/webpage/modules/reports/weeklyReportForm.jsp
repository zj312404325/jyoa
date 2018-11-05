<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>周报明细管理</title>
	<meta name="decorator" content="default"/>
	<script src="${ctxStatic}/common/contabs.js"></script>
	<script type="text/javascript">
		var validateForm;
		function doSubmit(){//回调函数，在编辑和保存动作时，供openDialog调用提交表单。
			if(validateForm.form()){
			  $("#inputForm").submit();
			  return true;
		  }
	
		  return false;
		}
		function doSubmit1(){//回调函数，下一步跳转。
			  if(validateForm.form()){
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
            //$(".tbdisable input").attr("disabled","true");
            $(".tbdisable input").attr("readonly","readonly");
            $(".tbdisable textarea").attr("disabled","true");
            $(".tbdisable button").attr("disabled","true");
            $(".tbdisable select").attr("disabled","true");
            $(".tbdisable a[name=removeFile]").remove();
        }

		$(document).ready(function() {
            if('${bhv}'=='1'){
                setReadOnly();
            }

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
				cv("number",obj);
			}else if(delFlag.val() == "0"){
				delFlag.val("1");
				$(obj).html("&divide;").attr("title", "撤销删除");
				$(obj).parent().parent().addClass("error");
				var delPoint = $(obj).closest("tr").find("input.number").val();
				$("#selfscore").val(Number($("#selfscore").val() - delPoint));
			}else if(delFlag.val() == "1"){
				delFlag.val("0");
				$(obj).html("&times;").attr("title", "删除");
				$(obj).parent().parent().removeClass("error");
				var delPoint = $(obj).closest("tr").find("input.number").val();
				$("#selfscore").val(Number($("#selfscore").val()) + Number(delPoint));
			}
			
		}
		function cv(type,obj){
			compare(obj);
			var valueArr= $(".tabs-container").find("tr").not(".error").find("input."+type);
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

			   }  
			   if(type=="number"){
				   
				   if(sumValue>0){  
				        $('#selfscore').val(parseInt(sumValue));  
				   }  
				   else{  
				       $('#selfscore').val(0);  
				   }  
			   }
		}
		
		
		function compare(obj1){
			var num1 = $(obj1).closest("tr").find("input.number").val();
            var reg = /^[1-9]\d*\.[5]$|0\.[5]$|^[1-9]\d*$/;

            if('' != num1.replace(reg,'')){
                num1 = num1.match(reg) == null  ?  '' : num1.match(reg);
            }

            if (num1 === '') {
                top.layer.alert("天数必须是0.5的整数倍！",{icon:2});
            }
		}
		
		
	</script>
</head>
<body class="hideScroll">
	<form:form id="inputForm" modelAttribute="weeklyReport" action="${ctx}/checkmodel/reports/save" method="post" class="form-horizontal">
		<input type="hidden" name="type" value="${type }" />
		<form:hidden path="id"/>
		<%--<form:hidden path="category"/>--%>
		<sys:message content="${message}"/>	
		<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer tbdisable">
		   <tbody>
			   <tr>
				   <td class="width-15 active"><label class="pull-right"><font color="red">*</font>周报标题：</label></td>
				   <td class="width-35">
					   <form:input path="title" htmlEscape="false"    class="form-control required"/>
				   </td>
			   </tr>

			   <tr>
				   <td class="width-15 active"><label class="pull-right text-danger">说明：</label></td>
				   <td colspan="3" class="text-danger">请填写具体工作内容，并附上工时。</td>
			   </tr>

		 	</tbody>
		</table>
		<!-- 上周工作start -->
		<div class="tabs-container">
            <ul class="nav nav-tabs">
				<li class="active"><a data-toggle="tab" href="#tab-1" aria-expanded="true">上周工作</a>
				</li>
            </ul>
            <div class="tab-content">
				<div id="tab-1" class="tab-pane active">
					<div class="panel-body">
						<a class="btn btn-white btn-sm" onclick="addRow('#weeklyReportDetailList', weeklyReportDetailRowIdx, weeklyReportDetailTpl);weeklyReportDetailRowIdx = weeklyReportDetailRowIdx + 1;" title="新增"><i class="fa fa-plus"></i> 新增</a>
						<table id="contentTable1" class="table table-striped table-bordered table-condensed dataTable tbdisable">
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
						<input id="weeklyReportDetailList{{idx}}_project" name="weeklyReportDetailList[{{idx}}].project" type="text" value="{{row.project}}"    class="form-control required" maxlength=50/>
					</td>

					<td width="60">
						<input id="weeklyReportDetailList{{idx}}_taskType" name="weeklyReportDetailList[{{idx}}].tasktype" type="text" value="{{row.tasktype}}"    class="form-control required" maxlength=50 list="browsers"/>
						<datalist id="browsers">
							<option value="计划内">
							<option value="计划外">
						</datalist>
					</td>

					<td width="220">
						<input id="weeklyReportDetailList{{idx}}_taskDesc" name="weeklyReportDetailList[{{idx}}].taskdesc" type="text" value="{{row.taskdesc}}"    class="form-control required" maxlength=50 onmouseover="this.title=this.value"/>
					</td>


					<td width="220">
						<input id="weeklyReportDetailList{{idx}}_content" name="weeklyReportDetailList[{{idx}}].content" type="text" value="{{row.content}}"    class="form-control required" maxlength=100 onmouseover="this.title=this.value"/>
					</td>


					<td width="60">
						<input id="weeklyReportDetailList{{idx}}_taskStatus" name="weeklyReportDetailList[{{idx}}].taskstatus" type="text" value="{{row.taskstatus}}"    class="form-control required " maxlength=50 list="status"/>
						<datalist id="status">
							<option value="完成">
							<option value="进行中">
							<option value="暂停执行">
						</datalist>
					</td>


					<td width="20">
						<input id="weeklyReportDetailList{{idx}}_costDays" name="weeklyReportDetailList[{{idx}}].costdays" type="text" value="{{row.costdays}}"    class="form-control required number" onblur="cv('digits',this)"/>
					</td>

					<td class="text-center" width="10">
						{{#delBtn}}<span class="close" onclick="delRow(this, '#weeklyReportDetailList{{idx}}')" title="删除">&times;</span>{{/delBtn}}
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

                    if('${bhv}'=='1'){
                        setReadOnly();
                        $("a[title='新增']").remove();
                        $("span.close").remove();
                    }
				});
			</script>
				</div>
			</div>
		</div>
		</div>
		<!--上周工作 End-->

		<!--本周工作 start-->
		<div class="tabs-container">
			<ul class="nav nav-tabs">
				<li class="active"><a data-toggle="tab" href="#tab-2" aria-expanded="true">本周计划任务</a>
				</li>
			</ul>
				<div class="panel-body active">
			<a class="btn btn-white btn-sm" onclick="addRow('#weeklyReportDetailListKey', weeklyReportDetailRowIdxKey, weeklyReportDetailTplKey);weeklyReportDetailRowIdxKey = weeklyReportDetailRowIdxKey + 1;" title="新增"><i class="fa fa-plus"></i> 新增</a>
			<table id="contentTable2" class="table table-striped table-bordered table-condensed dataTable tbdisable">
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
						<input id="weeklyReportDetailListKey{{idx}}_type" name="weeklyReportDetailListKey[{{idx}}].type" type="hidden" value="0"/>
					</td>

					<td width="40">
						<input id="weeklyReportDetailListKey{{idx}}_project" name="weeklyReportDetailListKey[{{idx}}].project" type="text" value="{{row.project}}"    class="form-control required" maxlength=50/>
					</td>

					<td width="60">
						<input id="weeklyReportDetailListKey{{idx}}_taskType" name="weeklyReportDetailListKey[{{idx}}].tasktype" type="text" value="{{row.tasktype}}"    class="form-control required" maxlength=50 list="browsers1"/>
						<datalist id="browsers1">
							<option value="上级分配">
							<option value="自主计划">
						</datalist>
					</td>

					<td width="220">
						<input id="weeklyReportDetailListKey{{idx}}_taskDesc" name="weeklyReportDetailListKey[{{idx}}].taskdesc" type="text" value="{{row.taskdesc}}"    class="form-control required" maxlength=50 onmouseover="this.title=this.value"/>
					</td>


					<td width="220">
						<input id="weeklyReportDetailListKey{{idx}}_content" name="weeklyReportDetailListKey[{{idx}}].content" type="text" value="{{row.content}}"    class="form-control required" maxlength=100 onmouseover="this.title=this.value"/>
					</td>


					<td width="60">
						<input id="weeklyReportDetailListKey{{idx}}_taskStatus" name="weeklyReportDetailListKey[{{idx}}].taskstatus" type="text" value="{{row.taskstatus}}"    class="form-control required " maxlength=50 list="status1"/>
						<datalist id="status1">
							<option value="继续上周">
							<option value="开始">
						</datalist>
					</td>


					<td width="20">
						<input id="weeklyReportDetailListKey{{idx}}_costDays" name="weeklyReportDetailListKey[{{idx}}].costdays" type="text" value="{{row.costdays}}"    class="form-control required number" onblur="cv('digits',this)"/>
					</td>

					<td class="text-center" width="10">
						{{#delBtn}}<span class="close" onclick="delRow(this, '#weeklyReportDetailListKey{{idx}}')" title="删除">&times;</span>{{/delBtn}}
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

                    if('${bhv}'=='1'){
                        setReadOnly();
                        $("a[title='新增']").remove();
                        $("span.close").remove();
                    }
				});
			</script>
		</div>
		</div>
		<!--本周工作 End-->
	</form:form>
</body>
</html>