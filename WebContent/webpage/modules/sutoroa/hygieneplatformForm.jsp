<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>8s检查表管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		var validateForm;
		function doSubmit(){//回调函数，在编辑和保存动作时，供openDialog调用提交表单。
		  if(validateForm.form()){
              //拼凑detail
              var detaljson=getDetailJson();
              $("#detailJson").val(detaljson);

              //拼凑attachjson
              var attachjson=getAttachJson();
              $("#attachJson").val(attachjson);

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

        function setReadOnly() {
            $(".tbdisable input").attr("disabled","true");
            $(".tbdisable textarea").attr("disabled","true");
            $(".tbdisable button").attr("disabled","true");
        }

		$(document).ready(function() {
            //编辑初始化
            editInit();

            //查看是设置输入框未不可编辑
			if('${type}'=='0'){
                setReadOnly();
			}

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
            laydate({
                elem: '#checkdate', //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
                event: 'focus' //响应事件。如果没有传入event，则按照默认的click
            });
		});

        function editInit(){
            if('${hygieneplatform.id}'!=''){//编辑
                //初始化明细
                <c:forEach var="hygieneplatformdepart" items="${hygieneplatform.hygieneplatformdepartList}" varStatus="status">
                $.each($("input[name=dp${hygieneplatformdepart.sort}]"),function(i,n){
                    if(i==0){
                        $(this).val("${hygieneplatformdepart.departname}");
                    }
                    if(i==1){
                        $(this).val("${hygieneplatformdepart.morningrecord}");
                    }
                    if(i==2){
                        $(this).val("${hygieneplatformdepart.weekrecord}");
                    }
                    if(i==3){
                        $(this).val("${hygieneplatformdepart.whzrr}");
                    }
                    if(i==4){
                        $(this).val("${hygieneplatformdepart.wfbxp}");
                    }
                    if(i==5){
                        $(this).val("${hygieneplatformdepart.keepclear}");
                    }
                    if(i==6){
                        $(this).val("${hygieneplatformdepart.msb}");
                    }
                    if(i==7){
                        $(this).val("${hygieneplatformdepart.msh}");
                    }
                    if(i==8){
                        $(this).val("${hygieneplatformdepart.kq}");
                    }
                    if(i==9){
                        $(this).val("${hygieneplatformdepart.wjzl}");
                    }
                    if(i==10){
                        $(this).val("${hygieneplatformdepart.mrbx}");
                    }
                    if(i==11){
                        $(this).val("${hygieneplatformdepart.srwp}");
                    }
                    if(i==12){
                        $(this).val("${hygieneplatformdepart.qjp}");
                    }
                    if(i==13){
                        $(this).val("${hygieneplatformdepart.dmgj}");
                    }
                    if(i==14){
                        $(this).val("${hygieneplatformdepart.tzzx}");
                    }
                    if(i==15){
                        $(this).val("${hygieneplatformdepart.yb}");
                    }
                    if(i==16){
                        $(this).val("${hygieneplatformdepart.wjggj}");
                    }
                    if(i==17){
                        $(this).val("${hygieneplatformdepart.wjgbs}");
                    }
                    if(i==18){
                        $(this).val("${hygieneplatformdepart.wjgbf}");
                    }
                    if(i==19){
                        $(this).val("${hygieneplatformdepart.wjjbs}");
                    }
                    if(i==20){
                        $(this).val("${hygieneplatformdepart.wjjml}");
                    }
                    if(i==21){
                        $(this).val("${hygieneplatformdepart.mcgj}");
                    }
                    if(i==22){
                        $(this).val("${hygieneplatformdepart.ctgj}");
                    }
                    if(i==23){
                        $(this).val("${hygieneplatformdepart.dngj}");
                    }
                    if(i==24){
                        $(this).val("${hygieneplatformdepart.dnx}");
                    }
                    if(i==25){
                        $(this).val("${hygieneplatformdepart.dnzl}");
                    }
                    if(i==26){
                        $(this).val("${hygieneplatformdepart.hhxx}");
                    }
                    if(i==27){
                        $(this).val("${hygieneplatformdepart.hys}");
                    }
                    if(i==28){
                        $(this).val("${hygieneplatformdepart.gztd}");
                    }
                    if(i==29){
                        $(this).val("${hygieneplatformdepart.yzzj}");
                    }
                    if(i==30){
                        $(this).val("${hygieneplatformdepart.ttwy}");
                    }
                    if(i==31){
                        $(this).val("${hygieneplatformdepart.gzsj}");
                    }
                    if(i==32){
                        $(this).val("${hygieneplatformdepart.lts}");
                    }
                    if(i==33){
                        $(this).val("${hygieneplatformdepart.xblk}");
                    }
                    if(i==34){
                        $(this).val("${hygieneplatformdepart.wrgm}");
                    }
                    if(i==35){
                        $(this).val("${hygieneplatformdepart.xxsd}");
                    }
                    if(i==36){
                        $(this).val("${hygieneplatformdepart.dwsb}");
                    }
                    if(i==37){
                        $(this).val("${hygieneplatformdepart.gsyj}");
                    }
                    if(i==38){
                        $(this).val("${hygieneplatformdepart.xbgm}");
                    }
                    if(i==39){
                        $(this).val("${hygieneplatformdepart.wjgs}");
                    }
                    if(i==40){
                        $(this).val("${hygieneplatformdepart.wrdy}");
                    }
                    if(i==41){
                        $(this).val("${hygieneplatformdepart.ktsd}");
                    }
                    if(i==42){
                        $(this).val("${hygieneplatformdepart.wpsh}");
                    }
                    if(i==43){
                        $(this).val("${hygieneplatformdepart.jdpx}");
                    }
                    if(i==44){
                        $(this).val("${hygieneplatformdepart.bmpx}");
                    }
                    if(i==45){
                        $(this).val("${hygieneplatformdepart.score}");
                    }
                });
                </c:forEach>

                //初始化附件
                <c:forEach var="attachment" items="${hygieneplatform.attachmentList}" varStatus="status">
                if("${attachment.url}"!=''){
                    var id="attachment${attachment.sort}";
                    var obj=$("#attachment${attachment.sort}");
                    obj.val("${attachment.url}");
                    var selectstr="filecontent"+id;
                    var htmlstr='<li><a href="javascript:;" target="_blank" vl="${attachment.url}" onclick="commonFileDownLoad(this)"><span>${attachment.filename}</span></a> &nbsp; <a href="javascript:;" name="removeFile" vl="'+selectstr+'" vl1="'+id+'"><i class="glyphicon glyphicon-remove text-danger"></i></a></li>';
                    $("#"+selectstr).html(htmlstr);
                }
                </c:forEach>
            }
        }

        function getDetailJson(){
            var jsonArray=[];
            jsonArray=getJsonTemp($("input[name=dp1]"),jsonArray);
            jsonArray=getJsonTemp($("input[name=dp2]"),jsonArray);
            jsonArray=getJsonTemp($("input[name=dp3]"),jsonArray);
            jsonArray=getJsonTemp($("input[name=dp4]"),jsonArray);
            jsonArray=getJsonTemp($("input[name=dp5]"),jsonArray);
            jsonArray=getJsonTemp($("input[name=dp6]"),jsonArray);
            jsonArray=getJsonTemp($("input[name=dp7]"),jsonArray);
            jsonArray=getJsonTemp($("input[name=dp8]"),jsonArray);
            jsonArray=getJsonTemp($("input[name=dp9]"),jsonArray);
            jsonArray=getJsonTemp($("input[name=dp10]"),jsonArray);
            return "["+jsonArray.join(",")+"]";
        }

        function getAttachJson(){
            var jsonArray=[];
            jsonArray=getAttachJsonTemp($("input[name=attachment]"),jsonArray);
            return "["+jsonArray.join(",")+"]";
        }

        function getAttachJsonTemp(obj,jsonArray){
            var jsonTemp="";
            $.each(obj,function(i,n){
                jsonTemp='{"url":'+'"'+$(this).val()+'"}';
                jsonArray.push(jsonTemp);
            });
            return jsonArray;
        }

        function getJsonTemp(obj,jsonArray){
            var jsonTemp="{";
            $.each(obj,function(i,n){
                if(i==0){
                    jsonTemp+='"departname":'+'"'+$(this).val()+'",';
                }
                if(i==1){
                    jsonTemp+='"morningrecord":'+'"'+$(this).val()+'",';
                }
                if(i==2){
                    jsonTemp+='"weekrecord":'+'"'+$(this).val()+'",';
                }
                if(i==3){
                    jsonTemp+='"whzrr":'+'"'+$(this).val()+'",';
                }
                if(i==4){
                    jsonTemp+='"wfbxp":'+'"'+$(this).val()+'",';
                }
                if(i==5){
                    jsonTemp+='"keepclear":'+'"'+$(this).val()+'",';
                }
                if(i==6){
                    jsonTemp+='"msb":'+'"'+$(this).val()+'",';
                }
                if(i==7){
                    jsonTemp+='"msh":'+'"'+$(this).val()+'",';
                }
                if(i==8){
                    jsonTemp+='"kq":'+'"'+$(this).val()+'",';
                }
                if(i==9){
                    jsonTemp+='"wjzl":'+'"'+$(this).val()+'",';
                }
                if(i==10){
                    jsonTemp+='"mrbx":'+'"'+$(this).val()+'",';
                }
                if(i==11){
                    jsonTemp+='"srwp":'+'"'+$(this).val()+'",';
                }
                if(i==12){
                    jsonTemp+='"qjp":'+'"'+$(this).val()+'",';
                }
                if(i==13){
                    jsonTemp+='"dmgj":'+'"'+$(this).val()+'",';
                }
                if(i==14){
                    jsonTemp+='"tzzx":'+'"'+$(this).val()+'",';
                }
                if(i==15){
                    jsonTemp+='"yb":'+'"'+$(this).val()+'",';
                }
                if(i==16){
                    jsonTemp+='"wjggj":'+'"'+$(this).val()+'",';
                }
                if(i==17){
                    jsonTemp+='"wjgbs":'+'"'+$(this).val()+'",';
                }
                if(i==18){
                    jsonTemp+='"wjgbf":'+'"'+$(this).val()+'",';
                }
                if(i==19){
                    jsonTemp+='"wjjbs":'+'"'+$(this).val()+'",';
                }
                if(i==20){
                    jsonTemp+='"wjjml":'+'"'+$(this).val()+'",';
                }
                if(i==21){
                    jsonTemp+='"mcgj":'+'"'+$(this).val()+'",';
                }
                if(i==22){
                    jsonTemp+='"ctgj":'+'"'+$(this).val()+'",';
                }
                if(i==23){
                    jsonTemp+='"dngj":'+'"'+$(this).val()+'",';
                }
                if(i==24){
                    jsonTemp+='"dnx":'+'"'+$(this).val()+'",';
                }
                if(i==25){
                    jsonTemp+='"dnzl":'+'"'+$(this).val()+'",';
                }
                if(i==26){
                    jsonTemp+='"hhxx":'+'"'+$(this).val()+'",';
                }
                if(i==27){
                    jsonTemp+='"hys":'+'"'+$(this).val()+'",';
                }
                if(i==28){
                    jsonTemp+='"gztd":'+'"'+$(this).val()+'",';
                }
                if(i==29){
                    jsonTemp+='"yzzj":'+'"'+$(this).val()+'",';
                }
                if(i==30){
                    jsonTemp+='"ttwy":'+'"'+$(this).val()+'",';
                }
                if(i==31){
                    jsonTemp+='"gzsj":'+'"'+$(this).val()+'",';
                }
                if(i==32){
                    jsonTemp+='"lts":'+'"'+$(this).val()+'",';
                }
                if(i==33){
                    jsonTemp+='"xblk":'+'"'+$(this).val()+'",';
                }
                if(i==34){
                    jsonTemp+='"wrgm":'+'"'+$(this).val()+'",';
                }
                if(i==35){
                    jsonTemp+='"xxsd":'+'"'+$(this).val()+'",';
                }
                if(i==36){
                    jsonTemp+='"dwsb":'+'"'+$(this).val()+'",';
                }
                if(i==37){
                    jsonTemp+='"gsyj":'+'"'+$(this).val()+'",';
                }
                if(i==38){
                    jsonTemp+='"xbgm":'+'"'+$(this).val()+'",';
                }
                if(i==39){
                    jsonTemp+='"wjgs":'+'"'+$(this).val()+'",';
                }
                if(i==40){
                    jsonTemp+='"wrdy":'+'"'+$(this).val()+'",';
                }
                if(i==41){
                    jsonTemp+='"ktsd":'+'"'+$(this).val()+'",';
                }
                if(i==42){
                    jsonTemp+='"wpsh":'+'"'+$(this).val()+'",';
                }
                if(i==43){
                    jsonTemp+='"jdpx":'+'"'+$(this).val()+'",';
                }
                if(i==44){
                    jsonTemp+='"bmpx":'+'"'+$(this).val()+'",';
                }
                if(i==45){
                    jsonTemp+='"score":'+'"'+$(this).val()+'"';
                }
            });
            jsonTemp+='}';
            jsonArray.push(jsonTemp);
            return jsonArray;
        }
	</script>
</head>
<body class="hideScroll">
		<form:form id="inputForm" modelAttribute="hygieneplatform" action="${ctx}/oa/hygieneplatform/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<form:hidden path="detailJson"/>
		<form:hidden path="attachJson"/>
		<sys:message content="${message}"/>
		<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer tbdisable">
		   <tbody>
				<%--<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>检查人：</label></td>
					<td class="width-35">
						<form:input path="checkuser" htmlEscape="false" maxlength="20"    class="form-control required"/>
					</td>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>日期：</label></td>
					<td class="width-35">
						<form:input path="checkdate" htmlEscape="false"    class="form-control required"/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>地点：</label></td>
					<td class="width-35">
						<form:input path="address" htmlEscape="false" maxlength="30"    class="form-control required"/>
					</td>
					<td class="width-15 active"></td>
		   			<td class="width-35" ></td>
		  		</tr>--%>
				<tr>
					<td style="width:100px"><font color="red">*</font>检查人</td><td colspan="3" width="400">
					<form:input path="checkuser" htmlEscape="false" maxlength="20"    class="form-control required"/></td><td width="100"><font color="red">*</font>地点</td>
					<td colspan="4" width="280"><form:input path="address" htmlEscape="false" maxlength="30"    class="form-control required"/></td><td><font color="red">*</font>日期</td><td colspan="5" width="350"><input id="checkdate" name="checkdate" type="text" maxlength="20" class="laydate-icon form-control layer-date required" value="${hygieneplatform.checkdate}" />
					</td>
				</tr>
				<tr>
					<td rowspan="2">序号</td><td rowspan="2" style="width:100px">检查项目</td><td rowspan="2">权重</td><td rowspan="2">检查内容（每发现一处即为0分）</td><td colspan="10">部门得分</td><td rowspan="2">图文附件</td>
				</tr>
				<tr>
					<td><input type="text" name="dp1" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp2" value="" style="width:50px;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp3" value="" style="width:50px;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp4" value="" style="width:50px;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp5" value="" style="width:50px;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp6" value="" style="width:50px;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp7" value="" style="width:50px;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp8" value="" style="width:50px;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp9" value="" style="width:50px;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp10" value="" style="width:50px;height:100%;border:0px;"/></td>
				</tr>
				<tr>
					<td rowspan="4">整理（15分）</td><td rowspan="4">思想统一
					纪律严明</td><td>5</td><td>每日晨会记录</td>
					<td><input type="text" name="dp1" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp2" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp3" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp4" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp5" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp6" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp7" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp8" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp9" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp10" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td width="60">
						<input type="hidden" id="attachment1" name="attachment" value=""/>
						<ol style="line-height: 20px; padding: 10px; padding-left:40px;" id="filecontentattachment1" class="filecontent">
						</ol>
						<button type="button" class="btn btn-primary" name="uploadF" vl="attachment1"><i class="glyphicon glyphicon-open"></i>&nbsp;上传</button>
					</td>
				</tr>
				<tr>
					<td>5</td><td>每周例会会议记录</td>
					<td><input type="text" name="dp1" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp2" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp3" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp4" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp5" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp6" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp7" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp8" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp9" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp10" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td>
						<input type="hidden" id="attachment2" name="attachment" value=""/>
						<ol style="line-height: 20px; padding: 10px; padding-left:40px;" id="filecontentattachment2" class="filecontent">
						</ol>
						<button type="button" class="btn btn-primary" name="uploadF" vl="attachment2"><i class="glyphicon glyphicon-open"></i>&nbsp;上传</button>
					</td>
				</tr>
				<tr>
					<td>3</td><td>相关物品应标识，有维护责任人</td>
					<td><input type="text" name="dp1" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp2" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp3" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp4" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp5" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp6" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp7" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp8" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp9" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp10" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td>
						<input type="hidden" id="attachment3" name="attachment" value=""/>
						<ol style="line-height: 20px; padding: 10px; padding-left:40px;" id="filecontentattachment3" class="filecontent">
						</ol>
						<button type="button" class="btn btn-primary" name="uploadF" vl="attachment3"><i class="glyphicon glyphicon-open"></i>&nbsp;上传</button></td>
				</tr>
				<tr>
					<td>2</td><td>无非必需品</td>
					<td><input type="text" name="dp1" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp2" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp3" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp4" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp5" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp6" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp7" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp8" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp9" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp10" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="hidden" id="attachment4" name="attachment" value=""/>
						<ol style="line-height: 20px; padding: 10px; padding-left:40px;" id="filecontentattachment4" class="filecontent">
						</ol>
						<button type="button" class="btn btn-primary" name="uploadF" vl="attachment4"><i class="glyphicon glyphicon-open"></i>&nbsp;上传</button></td>
				</tr>


				<tr>
					<td rowspan="7">整顿（15分）</td><td rowspan="7">规范管理
					专业精神</td><td>2</td><td>各办公用品目视化放置（定位、定量），必须定期进行整理，并保持干净</td>
					<td><input type="text" name="dp1" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp2" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp3" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp4" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp5" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp6" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp7" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp8" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp9" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp10" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="hidden" id="attachment5" name="attachment" value=""/>
						<ol style="line-height: 20px; padding: 10px; padding-left:40px;" id="filecontentattachment5" class="filecontent">
						</ol>
						<button type="button" class="btn btn-primary" name="uploadF" vl="attachment5"><i class="glyphicon glyphicon-open"></i>&nbsp;上传</button></td>
				</tr>
				<tr>
					<td>2</td><td>应有人员去向目视板</td>
					<td><input type="text" name="dp1" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp2" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp3" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp4" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp5" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp6" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp7" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp8" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp9" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp10" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="hidden" id="attachment6" name="attachment" value=""/>
						<ol style="line-height: 20px; padding: 10px; padding-left:40px;" id="filecontentattachment6" class="filecontent">
						</ol>
						<button type="button" class="btn btn-primary" name="uploadF" vl="attachment6"><i class="glyphicon glyphicon-open"></i>&nbsp;上传</button></td>
				</tr>
				<tr>
					<td>3</td><td>考核表应及时更新，并目视化</td>
					<td><input type="text" name="dp1" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp2" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp3" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp4" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp5" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp6" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp7" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp8" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp9" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp10" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="hidden" id="attachment7" name="attachment" value=""/>
						<ol style="line-height: 20px; padding: 10px; padding-left:40px;" id="filecontentattachment7" class="filecontent">
						</ol>
						<button type="button" class="btn btn-primary" name="uploadF" vl="attachment7"><i class="glyphicon glyphicon-open"></i>&nbsp;上传</button></td>
				</tr>
				<tr>
					<td>2</td><td>每周考勤是否有迟到早退旷工现象</td>
					<td><input type="text" name="dp1" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp2" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp3" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp4" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp5" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp6" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp7" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp8" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp9" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp10" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="hidden" id="attachment8" name="attachment" value=""/>
						<ol style="line-height: 20px; padding: 10px; padding-left:40px;" id="filecontentattachment8" class="filecontent">
						</ol>
						<button type="button" class="btn btn-primary" name="uploadF" vl="attachment8"><i class="glyphicon glyphicon-open"></i>&nbsp;上传</button></td>
				</tr>
				<tr>
					<td>2</td><td>文件、资料整齐放置不得凌乱，超过一周的资料存档</td>
					<td><input type="text" name="dp1" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp2" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp3" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp4" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp5" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp6" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp7" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp8" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp9" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp10" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="hidden" id="attachment9" name="attachment" value=""/>
						<ol style="line-height: 20px; padding: 10px; padding-left:40px;" id="filecontentattachment9" class="filecontent">
						</ol>
						<button type="button" class="btn btn-primary" name="uploadF" vl="attachment9"><i class="glyphicon glyphicon-open"></i>&nbsp;上传</button></td>
				</tr>
				<tr>
					<td>2</td><td>非每日必需品不得存放在办公台上</td>
					<td><input type="text" name="dp1" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp2" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp3" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp4" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp5" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp6" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp7" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp8" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp9" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp10" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="hidden" id="attachment10" name="attachment" value=""/>
						<ol style="line-height: 20px; padding: 10px; padding-left:40px;" id="filecontentattachment10" class="filecontent">
						</ol>
						<button type="button" class="btn btn-primary" name="uploadF" vl="attachment10"><i class="glyphicon glyphicon-open"></i>&nbsp;上传</button></td>
				</tr>
				<tr>
					<td>2</td><td>私人物品应分开、整得摆放一处</td>
					<td><input type="text" name="dp1" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp2" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp3" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp4" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp5" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp6" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp7" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp8" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp9" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp10" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="hidden" id="attachment11" name="attachment" value=""/>
						<ol style="line-height: 20px; padding: 10px; padding-left:40px;" id="filecontentattachment11" class="filecontent">
						</ol>
						<button type="button" class="btn btn-primary" name="uploadF" vl="attachment11"><i class="glyphicon glyphicon-open"></i>&nbsp;上传</button></td>
				</tr>



				<tr>
					<td rowspan="16">清扫、清洁(24分）</td><td rowspan="2">台下、地面</td><td>2</td><td>除清洁用具外不得放置任何物品</td>
					<td><input type="text" name="dp1" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp2" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp3" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp4" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp5" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp6" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp7" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp8" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp9" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp10" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="hidden" id="attachment12" name="attachment" value=""/>
						<ol style="line-height: 20px; padding: 10px; padding-left:40px;" id="filecontentattachment12" class="filecontent">
						</ol>
						<button type="button" class="btn btn-primary" name="uploadF" vl="attachment12"><i class="glyphicon glyphicon-open"></i>&nbsp;上传</button></td>
				</tr>
				<tr>
					<td>2</td><td>地面保持干净，无垃圾、无污迹及纸屑等</td>
					<td><input type="text" name="dp1" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp2" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp3" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp4" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp5" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp6" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp7" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp8" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp9" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp10" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="hidden" id="attachment13" name="attachment" value=""/>
						<ol style="line-height: 20px; padding: 10px; padding-left:40px;" id="filecontentattachment13" class="filecontent">
						</ol>
						<button type="button" class="btn btn-primary" name="uploadF" vl="attachment13"><i class="glyphicon glyphicon-open"></i>&nbsp;上传</button></td>
				</tr>
				<tr>
					<td rowspan="2">办公椅</td><td>2</td><td>人离开办公桌后，办公椅应推至桌下，且应紧挨办公桌平行放置</td>
					<td><input type="text" name="dp1" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp2" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp3" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp4" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp5" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp6" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp7" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp8" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp9" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp10" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="hidden" id="attachment14" name="attachment" value=""/>
						<ol style="line-height: 20px; padding: 10px; padding-left:40px;" id="filecontentattachment14" class="filecontent">
						</ol>
						<button type="button" class="btn btn-primary" name="uploadF" vl="attachment14"><i class="glyphicon glyphicon-open"></i>&nbsp;上传</button></td>
				</tr>
				<tr>
					<td>2</td><td>椅背上不允许摆放衣服和其它物品</td>
					<td><input type="text" name="dp1" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp2" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp3" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp4" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp5" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp6" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp7" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp8" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp9" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp10" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="hidden" id="attachment15" name="attachment" value=""/>
						<ol style="line-height: 20px; padding: 10px; padding-left:40px;" id="filecontentattachment15" class="filecontent">
						</ol>
						<button type="button" class="btn btn-primary" name="uploadF" vl="attachment15"><i class="glyphicon glyphicon-open"></i>&nbsp;上传</button></td>
				</tr>


				<tr>
					<td rowspan="5">文件柜</td><td>1</td><td>应保持柜面干净、无灰尘</td>
					<td><input type="text" name="dp1" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp2" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp3" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp4" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp5" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp6" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp7" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp8" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp9" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp10" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="hidden" id="attachment16" name="attachment" value=""/>
						<ol style="line-height: 20px; padding: 10px; padding-left:40px;" id="filecontentattachment16" class="filecontent">
						</ol>
						<button type="button" class="btn btn-primary" name="uploadF" vl="attachment16"><i class="glyphicon glyphicon-open"></i>&nbsp;上传</button></td>
				</tr>
				<tr>
					<td>1</td><td>柜外应有标识，且标识应统一</td>
					<td><input type="text" name="dp1" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp2" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp3" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp4" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp5" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp6" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp7" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp8" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp9" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp10" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="hidden" id="attachment17" name="attachment" value=""/>
						<ol style="line-height: 20px; padding: 10px; padding-left:40px;" id="filecontentattachment17" class="filecontent">
						</ol>
						<button type="button" class="btn btn-primary" name="uploadF" vl="attachment17"><i class="glyphicon glyphicon-open"></i>&nbsp;上传</button></td>
				</tr>
				<tr>
					<td>1</td><td>柜内文件（或物品）摆放整齐，并分类摆放</td>
					<td><input type="text" name="dp1" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp2" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp3" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp4" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp5" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp6" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp7" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp8" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp9" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp10" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="hidden" id="attachment18" name="attachment" value=""/>
						<ol style="line-height: 20px; padding: 10px; padding-left:40px;" id="filecontentattachment18" class="filecontent">
						</ol>
						<button type="button" class="btn btn-primary" name="uploadF" vl="attachment18"><i class="glyphicon glyphicon-open"></i>&nbsp;上传</button></td>
				</tr>
				<tr>
					<td>1</td><td>文件夹上要标识，同一部门的文件夹外侧的标识应统一</td>
					<td><input type="text" name="dp1" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp2" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp3" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp4" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp5" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp6" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp7" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp8" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp9" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp10" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="hidden" id="attachment19" name="attachment" value=""/>
						<ol style="line-height: 20px; padding: 10px; padding-left:40px;" id="filecontentattachment19" class="filecontent">
						</ol>
						<button type="button" class="btn btn-primary" name="uploadF" vl="attachment19"><i class="glyphicon glyphicon-open"></i>&nbsp;上传</button></td>
				</tr>
				<tr>
					<td>1</td><td>文件夹内必须有文件目录</td>
					<td><input type="text" name="dp1" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp2" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp3" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp4" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp5" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp6" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp7" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp8" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp9" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp10" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="hidden" id="attachment20" name="attachment" value=""/>
						<ol style="line-height: 20px; padding: 10px; padding-left:40px;" id="filecontentattachment20" class="filecontent">
						</ol>
						<button type="button" class="btn btn-primary" name="uploadF" vl="attachment20"><i class="glyphicon glyphicon-open"></i>&nbsp;上传</button></td>
				</tr>

				<tr>
					<td rowspan="2">门、窗墙壁</td><td>2</td><td>保持门、窗干净、无灰尘、无蛛网</td>
					<td><input type="text" name="dp1" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp2" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp3" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp4" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp5" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp6" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp7" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp8" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp9" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp10" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="hidden" id="attachment21" name="attachment" value=""/>
						<ol style="line-height: 20px; padding: 10px; padding-left:40px;" id="filecontentattachment21" class="filecontent">
						</ol>
						<button type="button" class="btn btn-primary" name="uploadF" vl="attachment21"><i class="glyphicon glyphicon-open"></i>&nbsp;上传</button></td>
				</tr>
				<tr>
					<td>2</td><td>窗台上无杂物</td>
					<td><input type="text" name="dp1" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp2" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp3" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp4" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp5" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp6" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp7" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp8" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp9" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp10" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="hidden" id="attachment22" name="attachment" value=""/>
						<ol style="line-height: 20px; padding: 10px; padding-left:40px;" id="filecontentattachment22" class="filecontent">
						</ol>
						<button type="button" class="btn btn-primary" name="uploadF" vl="attachment22"><i class="glyphicon glyphicon-open"></i>&nbsp;上传</button></td>
				</tr>


				<tr>
					<td rowspan="3">电脑、复印机、传真、电话等</td><td>2</td><td>应保持干净，无灰尘、无污迹</td>
					<td><input type="text" name="dp1" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp2" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp3" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp4" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp5" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp6" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp7" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp8" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp9" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp10" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="hidden" id="attachment23" name="attachment" value=""/>
						<ol style="line-height: 20px; padding: 10px; padding-left:40px;" id="filecontentattachment23" class="filecontent">
						</ol>
						<button type="button" class="btn btn-primary" name="uploadF" vl="attachment23"><i class="glyphicon glyphicon-open"></i>&nbsp;上传</button></td>
				</tr>
				<tr>
					<td>2</td><td>电脑线应束起来，避免凌乱</td>
					<td><input type="text" name="dp1" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp2" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp3" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp4" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp5" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp6" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp7" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp8" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp9" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp10" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="hidden" id="attachment24" name="attachment" value=""/>
						<ol style="line-height: 20px; padding: 10px; padding-left:40px;" id="filecontentattachment24" class="filecontent">
						</ol>
						<button type="button" class="btn btn-primary" name="uploadF" vl="attachment24"><i class="glyphicon glyphicon-open"></i>&nbsp;上传</button></td>
				</tr>
				<tr>
					<td>2</td><td>电脑内不得保存与工作和岗位学习无关的软件、资料</td>
					<td><input type="text" name="dp1" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp2" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp3" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp4" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp5" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp6" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp7" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp8" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp9" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp10" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="hidden" id="attachment25" name="attachment" value=""/>
						<ol style="line-height: 20px; padding: 10px; padding-left:40px;" id="filecontentattachment25" class="filecontent">
						</ol>
						<button type="button" class="btn btn-primary" name="uploadF" vl="attachment25"><i class="glyphicon glyphicon-open"></i>&nbsp;上传</button></td>
				</tr>

				<tr>
					<td rowspan="1">花卉</td><td>2</td><td>花卉应新鲜，花盆内不得有烟蒂、茶渣等杂物</td>
					<td><input type="text" name="dp1" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp2" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp3" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp4" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp5" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp6" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp7" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp8" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp9" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp10" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="hidden" id="attachment26" name="attachment" value=""/>
						<ol style="line-height: 20px; padding: 10px; padding-left:40px;" id="filecontentattachment26" class="filecontent">
						</ol>
						<button type="button" class="btn btn-primary" name="uploadF" vl="attachment26"><i class="glyphicon glyphicon-open"></i>&nbsp;上传</button></td>
				</tr>
				<tr>
					<td rowspan="1">其他</td><td>2</td><td>会议室、茶水间等公共区域的整洁</td>
					<td><input type="text" name="dp1" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp2" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp3" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp4" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp5" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp6" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp7" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp8" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp9" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp10" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="hidden" id="attachment27" name="attachment" value=""/>
						<ol style="line-height: 20px; padding: 10px; padding-left:40px;" id="filecontentattachment27" class="filecontent">
						</ol>
						<button type="button" class="btn btn-primary" name="uploadF" vl="attachment27"><i class="glyphicon glyphicon-open"></i>&nbsp;上传</button></td>
				</tr>

				<tr>
					<td rowspan="6">素养（14分）</td><td rowspan="6">创造纪律良好的工作场所；

					培养各种良好的礼节
					；
					养成遵守集体决定事项的习惯
					；
					积极维权，勇于担当、保提升斗力</td><td>3</td><td>工作态度是否良好（有无谈天、离岗、怠工、看小说、呆坐、打磕睡、吃零食、玩游戏、听音乐、上娱乐或聊天网站现象）</td>
					<td><input type="text" name="dp1" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp2" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp3" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp4" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp5" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp6" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp7" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp8" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp9" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp10" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="hidden" id="attachment28" name="attachment" value=""/>
						<ol style="line-height: 20px; padding: 10px; padding-left:40px;" id="filecontentattachment28" class="filecontent">
						</ol>
						<button type="button" class="btn btn-primary" name="uploadF" vl="attachment28"><i class="glyphicon glyphicon-open"></i>&nbsp;上传</button></td>
				</tr>
				<tr>
					<td>3</td><td>是否保持衣着整洁、仪表端庄（领带、胸针、着装、发饰等，参考行为规范）</td>
					<td><input type="text" name="dp1" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp2" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp3" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp4" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp5" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp6" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp7" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp8" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp9" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp10" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="hidden" id="attachment29" name="attachment" value=""/>
						<ol style="line-height: 20px; padding: 10px; padding-left:40px;" id="filecontentattachment29" class="filecontent">
						</ol>
						<button type="button" class="btn btn-primary" name="uploadF" vl="attachment29"><i class="glyphicon glyphicon-open"></i>&nbsp;上传</button></td>
				</tr>
				<tr>
					<td>2</td><td>谈吐文雅、举止规范有礼貌。</td>
					<td><input type="text" name="dp1" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp2" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp3" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp4" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp5" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp6" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp7" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp8" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp9" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp10" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="hidden" id="attachment30" name="attachment" value=""/>
						<ol style="line-height: 20px; padding: 10px; padding-left:40px;" id="filecontentattachment30" class="filecontent">
						</ol>
						<button type="button" class="btn btn-primary" name="uploadF" vl="attachment30"><i class="glyphicon glyphicon-open"></i>&nbsp;上传</button></td>
				</tr>
				<tr>
					<td>2</td><td>工作时间不做与工作无关的事。</td>
					<td><input type="text" name="dp1" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp2" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp3" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp4" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp5" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp6" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp7" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp8" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp9" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp10" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="hidden" id="attachment31" name="attachment" value=""/>
						<ol style="line-height: 20px; padding: 10px; padding-left:40px;" id="filecontentattachment31" class="filecontent">
						</ol>
						<button type="button" class="btn btn-primary" name="uploadF" vl="attachment31"><i class="glyphicon glyphicon-open"></i>&nbsp;上传</button></td>
				</tr>
				<tr>
					<td>2</td><td>不在工作场所争吵、打闹、抽烟、聊天</td>
					<td><input type="text" name="dp1" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp2" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp3" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp4" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp5" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp6" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp7" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp8" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp9" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp10" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="hidden" id="attachment32" name="attachment" value=""/>
						<ol style="line-height: 20px; padding: 10px; padding-left:40px;" id="filecontentattachment32" class="filecontent">
						</ol>
						<button type="button" class="btn btn-primary" name="uploadF" vl="attachment32"><i class="glyphicon glyphicon-open"></i>&nbsp;上传</button></td>
				</tr>
				<tr>
					<td>2</td><td>下班时将工作现场整理后离开。</td>
					<td><input type="text" name="dp1" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp2" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp3" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp4" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp5" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp6" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp7" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp8" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp9" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp10" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="hidden" id="attachment33" name="attachment" value=""/>
						<ol style="line-height: 20px; padding: 10px; padding-left:40px;" id="filecontentattachment33" class="filecontent">
						</ol>
						<button type="button" class="btn btn-primary" name="uploadF" vl="attachment33"><i class="glyphicon glyphicon-open"></i>&nbsp;上传</button></td>
				</tr>



				<tr>
					<td rowspan="6">安全（16分）</td><td rowspan="6">资产安全
					信息安全
					资料安全</td><td>3</td><td>办公室无人时应关好门</td>
					<td><input type="text" name="dp1" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp2" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp3" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp4" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp5" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp6" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp7" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp8" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp9" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp10" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="hidden" id="attachment34" name="attachment" value=""/>
						<ol style="line-height: 20px; padding: 10px; padding-left:40px;" id="filecontentattachment34" class="filecontent">
						</ol>
						<button type="button" class="btn btn-primary" name="uploadF" vl="attachment34"><i class="glyphicon glyphicon-open"></i>&nbsp;上传</button></td>
				</tr>
				<tr>
					<td>3</td><td>信息系统，人走后（或无人时）应关闭或自动锁定</td>
					<td><input type="text" name="dp1" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp2" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp3" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp4" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp5" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp6" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp7" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp8" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp9" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp10" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="hidden" id="attachment35" name="attachment" value=""/>
						<ol style="line-height: 20px; padding: 10px; padding-left:40px;" id="filecontentattachment35" class="filecontent">
						</ol>
						<button type="button" class="btn btn-primary" name="uploadF" vl="attachment35"><i class="glyphicon glyphicon-open"></i>&nbsp;上传</button></td>
				</tr>
				<tr>
					<td>2</td><td>对外申报系统是否下载存档申报资料并打印归档</td>
					<td><input type="text" name="dp1" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp2" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp3" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp4" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp5" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp6" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp7" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp8" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp9" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp10" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="hidden" id="attachment36" name="attachment" value=""/>
						<ol style="line-height: 20px; padding: 10px; padding-left:40px;" id="filecontentattachment36" class="filecontent">
						</ol>
						<button type="button" class="btn btn-primary" name="uploadF" vl="attachment36"><i class="glyphicon glyphicon-open"></i>&nbsp;上传</button></td>
				</tr>
				<tr>
					<td>2</td><td>是否正常使用公司邮箱公司电话公司传真</td>
					<td><input type="text" name="dp1" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp2" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp3" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp4" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp5" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp6" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp7" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp8" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp9" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp10" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="hidden" id="attachment37" name="attachment" value=""/>
						<ol style="line-height: 20px; padding: 10px; padding-left:40px;" id="filecontentattachment37" class="filecontent">
						</ol>
						<button type="button" class="btn btn-primary" name="uploadF" vl="attachment37"><i class="glyphicon glyphicon-open"></i>&nbsp;上传</button></td>
				</tr>
				<tr>
					<td>3</td><td>下班关好门窗、空调电脑等电源。</td>
					<td><input type="text" name="dp1" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp2" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp3" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp4" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp5" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp6" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp7" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp8" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp9" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp10" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="hidden" id="attachment38" name="attachment" value=""/>
						<ol style="line-height: 20px; padding: 10px; padding-left:40px;" id="filecontentattachment38" class="filecontent">
						</ol>
						<button type="button" class="btn btn-primary" name="uploadF" vl="attachment38"><i class="glyphicon glyphicon-open"></i>&nbsp;上传</button></td>
				</tr>
				<tr>
					<td>3</td><td>文件柜有锁具及专人保管;商业资料不得随意乱放，超过一周应归档案室</td>
					<td><input type="text" name="dp1" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp2" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp3" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp4" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp5" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp6" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp7" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp8" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp9" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp10" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="hidden" id="attachment39" name="attachment" value=""/>
						<ol style="line-height: 20px; padding: 10px; padding-left:40px;" id="filecontentattachment39" class="filecontent">
						</ol>
						<button type="button" class="btn btn-primary" name="uploadF" vl="attachment39"><i class="glyphicon glyphicon-open"></i>&nbsp;上传</button></td>
				</tr>


				<tr>
					<td rowspan="3">节约
						（6分）</td><td rowspan="3">以自已就是主人的心态对待企业的资源</td><td>2</td><td>无人时须关闭电源</td>
					<td><input type="text" name="dp1" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp2" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp3" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp4" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp5" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp6" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp7" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp8" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp9" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp10" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="hidden" id="attachment40" name="attachment" value=""/>
						<ol style="line-height: 20px; padding: 10px; padding-left:40px;" id="filecontentattachment40" class="filecontent">
						</ol>
						<button type="button" class="btn btn-primary" name="uploadF" vl="attachment40"><i class="glyphicon glyphicon-open"></i>&nbsp;上传</button></td>
				</tr>
				<tr>
					<td>2</td><td>空调温度设定不符节能要求</td>
					<td><input type="text" name="dp1" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp2" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp3" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp4" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp5" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp6" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp7" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp8" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp9" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp10" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="hidden" id="attachment41" name="attachment" value=""/>
						<ol style="line-height: 20px; padding: 10px; padding-left:40px;" id="filecontentattachment41" class="filecontent">
						</ol>
						<button type="button" class="btn btn-primary" name="uploadF" vl="attachment41"><i class="glyphicon glyphicon-open"></i>&nbsp;上传</button></td>
				</tr>

				<tr>
					<td>2</td><td>物品坏了及时维修（或申报维修）</td>
					<td><input type="text" name="dp1" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp2" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp3" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp4" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp5" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp6" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp7" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp8" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp9" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp10" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="hidden" id="attachment42" name="attachment" value=""/>
						<ol style="line-height: 20px; padding: 10px; padding-left:40px;" id="filecontentattachment42" class="filecontent">
						</ol>
						<button type="button" class="btn btn-primary" name="uploadF" vl="attachment42"><i class="glyphicon glyphicon-open"></i>&nbsp;上传</button></td>
				</tr>

				<tr>
					<td rowspan="2">学习（10分）</td><td rowspan="2">培训</td><td>5</td><td>有季度培训计划且按计划执行</td>
					<td><input type="text" name="dp1" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp2" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp3" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp4" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp5" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp6" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp7" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp8" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp9" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp10" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="hidden" id="attachment43" name="attachment" value=""/>
						<ol style="line-height: 20px; padding: 10px; padding-left:40px;" id="filecontentattachment43" class="filecontent">
						</ol>
						<button type="button" class="btn btn-primary" name="uploadF" vl="attachment43"><i class="glyphicon glyphicon-open"></i>&nbsp;上传</button></td>
				</tr>
				<tr>
					<td>5</td><td>部门组织培训每月2次以上</td>
					<td><input type="text" name="dp1" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp2" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp3" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp4" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp5" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp6" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp7" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp8" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp9" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp10" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="hidden" id="attachment44" name="attachment" value=""/>
						<ol style="line-height: 20px; padding: 10px; padding-left:40px;" id="filecontentattachment44" class="filecontent">
						</ol>
						<button type="button" class="btn btn-primary" name="uploadF" vl="attachment44"><i class="glyphicon glyphicon-open"></i>&nbsp;上传</button></td>
				</tr>
				<tr>
					<td colspan="4">总分</td>
					<td><input type="text" name="dp1" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp2" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp3" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp4" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp5" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp6" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp7" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp8" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp9" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td><input type="text" name="dp10" value="" style="width:100%;height:100%;border:0px;"/></td>
					<td></td>
				</tr>
		 	</tbody>
		</table>
	</form:form>
</body>
</html>