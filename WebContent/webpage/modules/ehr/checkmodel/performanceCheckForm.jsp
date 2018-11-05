<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<c:if test="${performanceCheck.category==null||performanceCheck.category==''||performanceCheck.category=='0'}"><c:set var="titlestr" value="绩效考核明细" /></c:if>
	<c:if test="${performanceCheck.category=='1'}"><c:set var="titlestr" value="转正考核" /></c:if>
	<title>${titlestr}管理</title>
	<meta name="decorator" content="default"/>
	<script src="${ctxStatic}/common/contabs.js"></script>
	<script type="text/javascript">
		var validateForm;
		function doSubmit(){//回调函数，在编辑和保存动作时，供openDialog调用提交表单。
			if(validateForm.form()){
				var valueArr= $(".tabs-container").find("tr").not(".error").find("input.digits");
				var sumValue=0;  
				   if(valueArr.size()>0||valueArr != null){  
				       for (var i=0;i<valueArr.size();i++ ){      
				               sumValue +=parseFloat(valueArr[i].value);
				               
				            }  
				       if(sumValue != 100){
		            	   	top.layer.alert("权重总和必须为100！",{icon:2});
		            	   	return; 
		               }
				              
				   }
				   if('${performanceCheck.category}'=='1'){
				       var fileurl=$("#fileurl").val();
				       if(fileurl==null||fileurl==''){
                           top.layer.alert("请上传转正报告！",{icon:2});
                           return;
                       }
				   }
			  $("#inputForm").submit();
			  return true;
		  }
	
		  return false;
		}
		function doSubmit1(){//回调函数，下一步跳转。
			  if(validateForm.form()){
				  var valueArr= $(".tabs-container").find("tr").not(".error").find("input.digits");
					var sumValue=0;  
					   if(valueArr.size()>0||valueArr != null){  
					       for (var i=0;i<valueArr.size();i++ ){      
					               sumValue +=parseFloat(valueArr[i].value);
					               
					            }  
					       if(sumValue != 100){
			            	   	top.layer.alert("权重总和必须为100！",{icon:2});
			            	   	return; 
			               }
					              
					   }
                  if('${performanceCheck.category}'=='1'){
                      var fileurl=$("#fileurl").val();
                      if(fileurl==null||fileurl==''){
                          top.layer.alert("请上传转正报告！",{icon:2});
                          return;
                      }
                  }
				  $("#inputForm").submit();
				  if('${performanceCheck.category}'==''||'${performanceCheck.category}'=='0'||'${performanceCheck.category}'=='null'){
                      var dataUrl = '/a/checkmodel/performanceInterview/performanceInterviewIndex?type=0';
                      addFrameItem(dataUrl,'performanceinterview','绩效考核面谈表');
				  }
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
			       if(sumValue > 100){
	            	   if(type=="number"){ 
	            		   top.layer.alert("自评总分不能超过100分！",{icon:2});
	            	   	$(obj).val("");
	            	   	return;
	            	   }
	            	   if(type=="digits"){
	            		   top.layer.alert("权重不能超过100！",{icon:2});
	            		   $(obj).val("");
		            	   	return; 
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
			var num1 = $(obj1).closest("tr").find("input.digits").val();
			var num2 = $(obj1).val();
			if(Number(num2) > Number(num1)){
				top.layer.alert("自评得分不能超过权重！",{icon:2});
				$(obj1).val("");
				
			}
		}
		
		
	</script>
</head>
<body class="hideScroll">
	<form:form id="inputForm" modelAttribute="performanceCheck" action="${ctx}/checkmodel/performanceCheck/save" method="post" class="form-horizontal">
		<input type="hidden" name="type" value="${type }" />
		<form:hidden path="id"/>
		<form:hidden path="category"/>
		<sys:message content="${message}"/>	
		<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer tbdisable">
		   <tbody>
			   <c:if test="${performanceCheck.category==null||performanceCheck.category==''||performanceCheck.category=='0'}">
				   <tr>
					   <td class="width-15 active"><label class="pull-right"><font color="red">*</font>年份：</label></td>
					   <td class="width-35">
						   <input id="checkyear" name="checkyear" type="text" maxlength="20" onclick="WdatePicker({skin:'default',dateFmt:'yyyy'})" class="laydate-icon form-control layer-date required"
								  value="${performanceCheck.checkyear}"/>
					   </td>
					   <td class="width-15 active"><label class="pull-right"><font color="red">*</font>季度：</label></td>
					   <td class="width-35">
						   <form:select path="checkquarter" class="form-control required">
							   <form:option value="" label=""/>
							   <form:options items="${fns:getDictList('checkQuarter')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
						   </form:select>
					   </td>
				   </tr>
			   </c:if>
			   <c:if test="${performanceCheck.category!=null&&performanceCheck.category=='1'}">
				   <tr>
					   <td class="width-15 active"><label class="pull-right"><font color="red">*</font>转正报告：</label></td>
					   <td class="width-35" colspan="3">
						   <button type="button" class="btn btn-primary" id="uploadF" vl="fileurl"><i class="glyphicon glyphicon-open"></i>&nbsp;上传</button>
						   <input type="hidden" id="fileurl" name="fileurl" value="${performanceCheck.fileurl}" />
						   <ol style="line-height: 20px; padding: 10px; padding-left:40px;" id="filecontent">
							   <c:if test="${performanceCheck.fileurl!=null&&performanceCheck.fileurl!=''}">
								   <li><a href="javascript:;" target="_blank" vl="${performanceCheck.fileurl}" onclick="commonFileDownLoad(this)"><span>${fns:getFileName(performanceCheck.fileurl)}</span></a> &nbsp; <a href="javascript:;" name="removeFile"><i class="glyphicon glyphicon-remove text-danger"></i></a></li>
							   </c:if>
						   </ol>
					   </td>
				   </tr>
			   </c:if>
				<tr>
					<td class="width-15 active"><label class="pull-right">计划改进：</label></td>
					<td class="width-35">
						<%-- <form:input path="centralplan" htmlEscape="false"    class="form-control "/> --%>
						<form:textarea path="centralplan" htmlEscape="false" rows="4" maxlength="500"    class="form-control "/>
					</td>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>自评得分：</label></td>
					<td class="width-35">
						<form:input path="selfscore" htmlEscape="false"    class="form-control required digits" readonly="true"/>
					</td>
				</tr>

			   <c:if test="${performanceCheck.category==null||performanceCheck.category==''||performanceCheck.category=='0'}">
				   <tr>
					   <td class="width-15 active"><label class="pull-right text-danger">说明：</label></td>
					   <td colspan="3" class="text-danger">权重之和为100，自评得分值不得超过权重。填写完成绩效考核表后，还必须填写绩效考核面谈表。</td>
				   </tr>
			   </c:if>
		 	</tbody>
		</table>
		<!-- 基本绩效考核指标start -->
		<div class="tabs-container">
            <ul class="nav nav-tabs">
				<c:if test="${performanceCheck.category==null||performanceCheck.category==''||performanceCheck.category=='0'}">
					<li class="active"><a data-toggle="tab" href="#tab-1" aria-expanded="true">绩效考核指标</a>
					</li>
					<%--<li class=""><a data-toggle="tab" href="#tab-2" aria-expanded="false">关键绩效考核指标</a>
					</li>--%>
				</c:if>
				<c:if test="${performanceCheck.category=='1'}">
					<li class="active"><a data-toggle="tab" href="#tab-1" aria-expanded="true">绩效考核指标</a>
					</li>
				</c:if>
            </ul>
            <div class="tab-content">
				<div id="tab-1" class="tab-pane active">
					<div class="panel-body">
						<a class="btn btn-white btn-sm" onclick="addRow('#performanceCheckDetailList', performanceCheckDetailRowIdx, performanceCheckDetailTpl);performanceCheckDetailRowIdx = performanceCheckDetailRowIdx + 1;" title="新增"><i class="fa fa-plus"></i> 新增</a>
						<table id="contentTable1" class="table table-striped table-bordered table-condensed dataTable tbdisable">
							<thead>
								<tr>
									<th class="hide"></th>
									<th>关键指标</th>
									<th>绩效衡量标准</th>
									<th>权重</th>
									<th>完成情况</th>
									<th>自评得分</th>
									<th width="10">&nbsp;</th>
								</tr>
							</thead>
							<tbody id="performanceCheckDetailList">
							</tbody>
						</table>
			<script type="text/template" id="performanceCheckDetailTpl">//<!--
				<tr id="performanceCheckDetailList{{idx}}">
					<td class="hide">
						<input id="performanceCheckDetailList{{idx}}_id" name="performanceCheckDetailList[{{idx}}].id" type="hidden" value="{{row.id}}"/>
						<input id="performanceCheckDetailList{{idx}}_delFlag" name="performanceCheckDetailList[{{idx}}].delFlag" type="hidden" value="0"/>
						<input id="performanceCheckDetailList{{idx}}_type" name="performanceCheckDetailList[{{idx}}].type" type="hidden" value="0"/>
					</td>
					
					<td>
						<input id="performanceCheckDetailList{{idx}}_kpi" name="performanceCheckDetailList[{{idx}}].kpi" type="text" value="{{row.kpi}}"    class="form-control required" maxlength=50/>
					</td>
					
					
					<td>
						<input id="performanceCheckDetailList{{idx}}_referencepoint" name="performanceCheckDetailList[{{idx}}].referencepoint" type="text" value="{{row.referencepoint}}"    class="form-control required" maxlength=100/>
					</td>
					
					
					<td>
						<input id="performanceCheckDetailList{{idx}}_weight" name="performanceCheckDetailList[{{idx}}].weight" type="text" value="{{row.weight}}"    class="form-control required digits" onblur="cv('digits',this)"/>
					</td>
					
					
					<td>
						<input id="performanceCheckDetailList{{idx}}_execution" name="performanceCheckDetailList[{{idx}}].execution" type="text" value="{{row.execution}}"    class="form-control "/>
					</td>
					
					
					<td>
						<input id="performanceCheckDetailList{{idx}}_selfscore" name="performanceCheckDetailList[{{idx}}].selfscore" type="text" value="{{row.selfscore}}"    class="form-control number" onblur="cv('number',this)"/>
					</td>
					
					<td class="text-center" width="10">
						{{#delBtn}}<span class="close" onclick="delRow(this, '#performanceCheckDetailList{{idx}}')" title="删除">&times;</span>{{/delBtn}}
					</td>
				</tr>//-->
			</script>
			<script type="text/javascript">
				var performanceCheckDetailRowIdx = 0, performanceCheckDetailTpl = $("#performanceCheckDetailTpl").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g,"");
				$(document).ready(function() {
					var data = ${fns:toJson(performanceCheck.performanceCheckDetailList)};
					for (var i=0; i<data.length; i++){
						addRow('#performanceCheckDetailList', performanceCheckDetailRowIdx, performanceCheckDetailTpl, data[i]);
						performanceCheckDetailRowIdx = performanceCheckDetailRowIdx + 1;
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
			<div id="tab-2" class="tab-pane">
				<div class="panel-body">
			<a class="btn btn-white btn-sm" onclick="addRow('#performanceCheckDetailListKey', performanceCheckDetailRowIdxKey, performanceCheckDetailTplKey);performanceCheckDetailRowIdxKey = performanceCheckDetailRowIdxKey + 1;" title="新增"><i class="fa fa-plus"></i> 新增</a>
			<table id="contentTable2" class="table table-striped table-bordered table-condensed dataTable tbdisable">
				<thead>
					<tr>
						<th class="hide"></th>
						<th>本期工作计划及指标</th>
						<th>绩效衡量标准</th>
						<th>权重</th>
						<th>完成情况</th>
						<th>自评得分</th>
						<th width="10">&nbsp;</th>
					</tr>
				</thead>
				<tbody id="performanceCheckDetailListKey">
				</tbody>
			</table>
			<script type="text/template" id="performanceCheckDetailTplKey">//<!--
				<tr id="performanceCheckDetailListKey{{idx}}">
					<td class="hide">
						<input id="performanceCheckDetailListKey{{idx}}_id" name="performanceCheckDetailListKey[{{idx}}].id" type="hidden" value="{{row.id}}"/>
						<input id="performanceCheckDetailListKey{{idx}}_delFlag" name="performanceCheckDetailListKey[{{idx}}].delFlag" type="hidden" value="0"/>
						<input id="performanceCheckDetailListKey{{idx}}_type" name="performanceCheckDetailListKey[{{idx}}].type" type="hidden" value="1"/>
					</td>
					
					<td>
						<input id="performanceCheckDetailListKey{{idx}}_kpi" name="performanceCheckDetailListKey[{{idx}}].kpi" type="text" value="{{row.kpi}}"    class="form-control required" maxlength=50/>
					</td>
					
					
					<td>
						<input id="performanceCheckDetailListKey{{idx}}_referencepoint" name="performanceCheckDetailListKey[{{idx}}].referencepoint" type="text" value="{{row.referencepoint}}"    class="form-control required" maxlength=100/>
					</td>
					
					
					<td>
						<input id="performanceCheckDetailListKey{{idx}}_weight" name="performanceCheckDetailListKey[{{idx}}].weight" type="text" value="{{row.weight}}"    class="form-control required digits" onblur="cv('digits',this)"/>
					</td>
					
					
					<td>
						<input id="performanceCheckDetailListKey{{idx}}_execution" name="performanceCheckDetailListKey[{{idx}}].execution" type="text" value="{{row.execution}}"    class="form-control "/>
					</td>
					
					
					<td>
						<input id="performanceCheckDetailListKey{{idx}}_selfscore" name="performanceCheckDetailListKey[{{idx}}].selfscore" type="text" value="{{row.selfscore}}"    class="form-control  number" onblur="cv('number',this)"/>
					</td>
					
					<td class="text-center" width="10">
						{{#delBtn}}<span class="close" onclick="delRow(this, '#performanceCheckDetailListKey{{idx}}')" title="删除">&times;</span>{{/delBtn}}
					</td>
				</tr>//-->
			</script>
			<script type="text/javascript">
				var performanceCheckDetailRowIdxKey = 0,performanceCheckDetailRowIdxKey = 0, performanceCheckDetailTplKey = $("#performanceCheckDetailTplKey").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g,"");
				$(document).ready(function() {
					var data = ${fns:toJson(performanceCheck.performanceCheckDetailListKey)};
					for (var i=0; i<data.length; i++){
						addRow('#performanceCheckDetailListKey', performanceCheckDetailRowIdxKey, performanceCheckDetailTplKey, data[i]);
						performanceCheckDetailRowIdxKey = performanceCheckDetailRowIdxKey + 1;
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
		<!-- 基本绩效考核指标end -->
		<!-- 关键绩效考核指标start -->
		<!-- <div class="tabs-container">
            <ul class="nav nav-tabs">
				
            </ul>
            <div class="tab-content">
				
		</div>
		</div> -->
		<!-- 关键绩效考核指标end -->
	</form:form>
</body>
</html>