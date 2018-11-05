<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>

<html>
<head>
	<title>入职</title>
	<meta name="decorator" content="default"/>
	<script src="${ctxStatic}/common/contabs.js"></script>
	<script type="text/javascript">
		var validateForm;
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

            laydate({
                elem: '#birthday', //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
                event: 'focus' //响应事件。如果没有传入event，则按照默认的click
            });
			
			//$("#resumeurl").attr("disabled",true);
			
			$("#sub").click(function(){
				if(validateForm.form()){
					  if($("#iscardurl").val() == ''){
						  layer.alert('请上传身份证!', {icon: 0, title:'警告'});
						  return false;
					  }
					  if($("#certificateurl").val() == ''){
						  layer.alert('请上传毕业证!', {icon: 0, title:'警告'});
						  return false;
					  }
					  if($("#leavingcertificate").val() == ''){
						  layer.alert('请上传离职证明!', {icon: 0, title:'警告'});
						  return false;
					  }
					  if($("#resumeurl").val() == ''){
						  layer.alert('请上传个人简历!', {icon: 0, title:'警告'});
						  return false;
					  }
                    if($("#bankcardurl").val() == ''){
                        layer.alert('请上传银行卡!', {icon: 0, title:'警告'});
                        return false;
                    }
                    if($("#healthurl").val() == ''){
                        layer.alert('请上传体检证明!', {icon: 0, title:'警告'});
                        return false;
                    }
					  
					  
					  var msg = "";
					  var detailForms = new Array();
					  detailForms =  $("#infotb").find("tr[name='membertr']").toArray();
					  if(detailForms.length > 0){
							var detailJsonArray = [];
							$.each(detailForms,function(i,item){
				    			var detailJSON = '{';
				    			detailJSON = detailJSON + '"sortno":"'+ i +'",';
				    			if($(this).find("input[name=relationship]").val() == null || $(this).find("input[name=relationship]").val() == ''){
				    				detailJSON = detailJSON + '"relationship":"",';
				    				msg = "家庭成员关系不能为空！";
				    				$(this).find("input[name=relationship]").focus();
				    				return false;
				    			}else{
				    				detailJSON = detailJSON + '"relationship":"'+ $(this).find("input[name=relationship]").val()+'",';
				    			}
				    			if($(this).find("input[name=memberfullname]").val() == null || $(this).find("input[name=memberfullname]").val() == ''){
				    				detailJSON = detailJSON + '"memberfullname":"",';
				    				msg = "家庭成员姓名不能为空！";
				    				$(this).find("input[name=memberfullname]").focus();
				    				return false;
				    			}else{
				    				detailJSON = detailJSON + '"memberfullname":"'+ $(this).find("input[name=memberfullname]").val()+'",';
				    			}
				    			if($(this).find("input[name=memberage]").val() == null || $(this).find("input[name=memberage]").val() == ''){
				    				detailJSON = detailJSON + '"memberage":"",';
				    			}else{
				    				detailJSON = detailJSON + '"memberage":"'+ $(this).find("input[name=memberage]").val()+'",';
				    			}
				    			if($(this).find("input[name=memberworkunit]").val() == null || $(this).find("input[name=memberworkunit]").val() ==''){
				    				detailJSON = detailJSON + '"memberworkunit":"",';
				    			}else{
				    				detailJSON = detailJSON + '"memberworkunit":"'+ $(this).find("input[name=memberworkunit]").val()+'",';
				    			}
				    			if($(this).find("input[name=membermobile]").val() == null || $(this).find("input[name=membermobile]").val() == ''){
				    				detailJSON = detailJSON + '"membermobile":"",';
				    				msg = "家庭成员联系电话不能为空！";
				    				$(this).find("input[name=memberfullname]").focus();
				    				return false;
				    			}else{
				    				detailJSON = detailJSON + '"membermobile":"'+ $(this).find("input[name=membermobile]").val()+'",';
				    			}
				    			if($(this).find("input[name=memberaddress]").val() == null || $(this).find("input[name=memberaddress]").val() == ''){
				    				detailJSON = detailJSON + '"memberaddress":""}';
				    			}else{
				    				detailJSON = detailJSON + '"memberaddress":"'+ $(this).find("input[name=memberaddress]").val()+'"}';
				    			}
				    			detailJsonArray.push(detailJSON);
				    		});
							if(msg != ""){
								layer.alert(msg, {icon: 0, title:'警告'});
								return false;
							}
							$("#detailJsonArray").val('['+detailJsonArray+']');
					  }	

					  //同意并继续
					  /*layer.open({
						    type: 1, 
						    btn:['同意并继续'],
						    btnAlign: 'c',
						    area: ["820px", "80%"],
						    title:"信息",
						    content: $(".promise_box"),
						    success: function (layero) {
						        var btn = layero.find('.layui-layer-btn');
						        btn.css('text-align', 'center');
						    },
						    yes:function(index, layero){
						    	if($(".promiseform1").is(":visible")){
						    		$(".promiseform1").hide();
						    		$(".promiseform2").show();
						    		return false;
						    	}
						    	if($(".promiseform2").is(":visible")){
						    		$(".promiseform2").hide();
						    		$(".promiseform3").show();
						    		return false;
						    	}
						    	if($(".promiseform3").is(":visible")){
						    		$(".promiseform3").hide();
						    		$(".promiseform4").show();
						    		return false;
						    	}
						    	if($(".promiseform4").is(":visible")){
						    		$(".promiseform4").hide();
						    		$(".promiseform5").show();
						    		return false;
						    	}
						    	if($(".promiseform5").is(":visible")){
						    		$(".promiseform5").hide();
						    		$(".promiseform6").show();
						    		return false;
						    	}
						    	if($(".promiseform6").is(":visible")){
						    		$("input[name=disclaimername]").val($("#var1").val());
						    		$("input[name=disclaimerno]").val($("#var2").val());
						    		$("input[name=disclaimeryear]").val($("#var3").val());
						    		$("input[name=disclaimermonth]").val($("#var4").val());
						    		$("input[name=disclaimerday]").val($("#var5").val());
						    		$("input[name=disclaimerdisease]").val($("#var6").val());

						    		<c:if test="${(departFile==null||departFile=='')&&(performFile==null||performFile=='')}">
                                    loading('正在提交，请稍等...');
                                    $("#inputForm").submit();
									</c:if>

                                    <c:if test="${(departFile!=null&&departFile!='')}">
                                    $(".promiseform6").hide();
                                    $(".promiseform7").show();
                                    return false;
                                    </c:if>

                                    <c:if test="${(departFile==null||departFile=='')&&(performFile!=null&&performFile!='')}">
                                    $(".promiseform6").hide();
                                    $(".promiseform8").show();
                                    return false;
                                    </c:if>
						    	}

                                <c:if test="${(departFile!=null&&departFile!='')}">
                                if($(".promiseform7").is(":visible")){
                                    <c:if test="${(performFile==null||performFile=='')}">
                                    loading('正在提交，请稍等...');
                                    $("#inputForm").submit();
                                    </c:if>

                                    <c:if test="${(performFile!=null&&performFile!='')}">
                                    $(".promiseform7").hide();
                                    $(".promiseform8").show();
                                    return false;
                                    </c:if>
                                }
                                </c:if>

                                <c:if test="${(performFile!=null&&performFile!='')}">
                                if($(".promiseform8").is(":visible")){
                                    loading('正在提交，请稍等...');
                                    $("#inputForm").submit();
                                }
                                </c:if>
						    }
						});*/

                    loading('正在提交，请稍等...');
                    $("#inputForm").submit();

					  return true;
				  }

					 
				});

            $("#sub2").click(function(){
                if(validateForm.form()){
                    if($("#iscardurl").val() == ''){
                        layer.alert('请上传身份证!', {icon: 0, title:'警告'});
                        return false;
                    }
                    if($("#certificateurl").val() == ''){
                        layer.alert('请上传毕业证!', {icon: 0, title:'警告'});
                        return false;
                    }
                    if($("#leavingcertificate").val() == ''){
                        layer.alert('请上传离职证明!', {icon: 0, title:'警告'});
                        return false;
                    }
                    if($("#resumeurl").val() == ''){
                        layer.alert('请上传个人简历!', {icon: 0, title:'警告'});
                        return false;
                    }
                    if($("#bankcardurl").val() == ''){
                        layer.alert('请上传银行卡!', {icon: 0, title:'警告'});
                        return false;
                    }
                    if($("#healthurl").val() == ''){
                        layer.alert('请上传体检证明!', {icon: 0, title:'警告'});
                        return false;
                    }


                    var msg = "";
                    var detailForms = new Array();
                    detailForms =  $("#infotb").find("tr[name='membertr']").toArray();
                    if(detailForms.length > 0){
                        var detailJsonArray = [];
                        $.each(detailForms,function(i,item){
                            var detailJSON = '{';
                            detailJSON = detailJSON + '"sortno":"'+ i +'",';
                            if($(this).find("input[name=relationship]").val() == null || $(this).find("input[name=relationship]").val() == ''){
                                detailJSON = detailJSON + '"relationship":"",';
                                msg = "家庭成员关系不能为空！";
                                $(this).find("input[name=relationship]").focus();
                                return false;
                            }else{
                                detailJSON = detailJSON + '"relationship":"'+ $(this).find("input[name=relationship]").val()+'",';
                            }
                            if($(this).find("input[name=memberfullname]").val() == null || $(this).find("input[name=memberfullname]").val() == ''){
                                detailJSON = detailJSON + '"memberfullname":"",';
                                msg = "家庭成员姓名不能为空！";
                                $(this).find("input[name=memberfullname]").focus();
                                return false;
                            }else{
                                detailJSON = detailJSON + '"memberfullname":"'+ $(this).find("input[name=memberfullname]").val()+'",';
                            }
                            if($(this).find("input[name=memberage]").val() == null || $(this).find("input[name=memberage]").val() == ''){
                                detailJSON = detailJSON + '"memberage":"",';
                            }else{
                                detailJSON = detailJSON + '"memberage":"'+ $(this).find("input[name=memberage]").val()+'",';
                            }
                            if($(this).find("input[name=memberworkunit]").val() == null || $(this).find("input[name=memberworkunit]").val() ==''){
                                detailJSON = detailJSON + '"memberworkunit":"",';
                            }else{
                                detailJSON = detailJSON + '"memberworkunit":"'+ $(this).find("input[name=memberworkunit]").val()+'",';
                            }
                            if($(this).find("input[name=membermobile]").val() == null || $(this).find("input[name=membermobile]").val() == ''){
                                detailJSON = detailJSON + '"membermobile":"",';
                                msg = "家庭成员联系电话不能为空！";
                                $(this).find("input[name=memberfullname]").focus();
                                return false;
                            }else{
                                detailJSON = detailJSON + '"membermobile":"'+ $(this).find("input[name=membermobile]").val()+'",';
                            }
                            if($(this).find("input[name=memberaddress]").val() == null || $(this).find("input[name=memberaddress]").val() == ''){
                                detailJSON = detailJSON + '"memberaddress":""}';
                            }else{
                                detailJSON = detailJSON + '"memberaddress":"'+ $(this).find("input[name=memberaddress]").val()+'"}';
                            }
                            detailJsonArray.push(detailJSON);
                        });
                        if(msg != ""){
                            layer.alert(msg, {icon: 0, title:'警告'});
                            return false;
                        }
                        $("#detailJsonArray").val('['+detailJsonArray+']');
                    }


                    loading('正在提交，请稍等...');
                    $("#inputForm").submit();

                    return true;
                }


            });


        });


		
		
		
     </script>
     
     
     <style>
@font-face{
font-family:"Times New Roman";
}

@font-face{
font-family:"宋体";
}

@font-face{
font-family:"Wingdings";
}

@font-face{
font-family:"Arial";
}

p.MsoNormal{
mso-style-name:正文;
mso-style-parent:"";
margin:0pt;
margin-bottom:.0001pt;
mso-pagination:none;
text-align:justify;
text-justify:inter-ideograph;
font-family:'Times New Roman';
font-size:10.5000pt;
mso-font-kerning:1.0000pt;
}

span.10{
font-family:'Times New Roman';
}

p.MsoHeader{
mso-style-name:页眉;
margin:0pt;
margin-bottom:.0001pt;
border-bottom:1.0000pt solid windowtext;
mso-border-bottom-alt:0.7500pt solid windowtext;
padding:0pt 0pt 1pt 0pt ;
layout-grid-mode:char;
mso-pagination:none;
text-align:center;
font-family:'Times New Roman';
font-size:9.0000pt;
mso-font-kerning:1.0000pt;
}

p.MsoFooter{
mso-style-name:页脚;
margin:0pt;
margin-bottom:.0001pt;
layout-grid-mode:char;
mso-pagination:none;
text-align:left;
font-family:'Times New Roman';
font-size:9.0000pt;
mso-font-kerning:1.0000pt;
}

span.msoIns{
mso-style-type:export-only;
mso-style-name:"";
text-decoration:underline;
text-underline:single;
color:blue;
}

span.msoDel{
mso-style-type:export-only;
mso-style-name:"";
text-decoration:line-through;
color:red;
}

table.MsoNormalTable{
mso-style-name:普通表格;
mso-style-parent:"";
mso-style-noshow:yes;
mso-tstyle-rowband-size:0;
mso-tstyle-colband-size:0;
mso-padding-alt:0.0000pt 5.4000pt 0.0000pt 5.4000pt;
mso-para-margin:0pt;
mso-para-margin-bottom:.0001pt;
mso-pagination:widow-orphan;
font-family:'Times New Roman';
font-size:10.0000pt;
mso-ansi-language:#0400;
mso-fareast-language:#0400;
mso-bidi-language:#0400;
}
@page{mso-page-border-surround-header:no;
	mso-page-border-surround-footer:no;}@page Section0{
margin-top:56.7000pt;
margin-bottom:28.3500pt;
margin-left:22.7000pt;
margin-right:19.8500pt;
size:595.3000pt 841.9000pt;
layout-grid:15.6000pt;
}
div.Section0{page:Section0;}
table.MsoNormalTable input[type=text] { border:none; outline:none; width:100%; line-height:20px; text-align:center;}
.footer_input {border:none; border-bottom:1px solid #999; outline:none; width:120px; line-height:20px; text-align:center;}
label.checked {color:#1ab394;}
label.checked a {color:#1ab394;}
</style>
</head>
<body class="gray-bg" style="tab-interval:21pt;text-justify-trim:punctuation;" >

<form:form id="inputForm" modelAttribute="userInfo" action="${ctx}/ehr/entry/save" method="post" class="form-horizontal">
<form:hidden path="id"/>
<input type="hidden" id="detailJsonArray" name="detailJsonArray" value="" />
	<input type="hidden" id="isehr" name="isehr" value="${isehr}" />
		<sys:message content="${message}"/>	
<div class="wrapper wrapper-content">
<div class="ibox">
<div class="ibox-title">
		<h5>入职登记</h5>
	</div>
    
    <div class="ibox-content">
		<!--StartFragment-->
		<div class="Section0" style="layout-grid:15.6000pt;">
			<table class="MsoNormalTable" id="infotb" style="border-collapse:collapse;width:552.6000pt;margin-left:4.6500pt;mso-table-layout-alt:fixed;mso-padding-alt:0.0000pt 5.4000pt 0.0000pt 5.4000pt ;">
			<tr style="height:28.3500pt;">
				<td width="736" valign="center" colspan="12" style="width:552.6000pt;padding:0.0000pt 5.4000pt 0.0000pt 5.4000pt ;border-left:1.0000pt solid windowtext;mso-border-left-alt:0.5000pt solid windowtext;border-right:1.0000pt solid windowtext;mso-border-right-alt:0.5000pt solid windowtext;border-top:1.0000pt solid windowtext;mso-border-top-alt:0.5000pt solid windowtext;border-bottom:1.0000pt solid windowtext;mso-border-bottom-alt:0.5000pt solid windowtext;background:rgb(255,250,245);">
					<p class="MsoNormal" align="center" style="mso-pagination:widow-orphan;text-align:center;">
						<b><span style="mso-spacerun:'yes';font-family:宋体;font-weight:bold;font-size:14.0000pt;mso-font-kerning:0.0000pt;">基&ensp;本&ensp;情&ensp;况</span></b><b><span style="font-family:宋体;font-weight:bold;font-size:14.0000pt;mso-font-kerning:0.0000pt;"><o:p></o:p></span></b>
					</p>
				</td>
			</tr>
			<tr style="height:28.3500pt;">
				<td width="98" valign="center" style="width:73.9500pt;padding:0.0000pt 5.4000pt 0.0000pt 5.4000pt ;border-left:1.0000pt solid windowtext;mso-border-left-alt:0.5000pt solid windowtext;border-right:1.0000pt solid windowtext;mso-border-right-alt:0.5000pt solid windowtext;border-top:31.8750pt none rgb(255,255,255);mso-border-top-alt:31.8750pt none rgb(255,255,255);border-bottom:1.0000pt solid windowtext;mso-border-bottom-alt:0.5000pt solid windowtext;">
					<p class="MsoNormal" align="center" style="mso-pagination:widow-orphan;text-align:center;">
						<span style="mso-spacerun:'yes';font-family:宋体;font-size:10.0000pt;mso-font-kerning:0.0000pt;"><font color="red">*</font>姓&ensp;&ensp;&ensp;&ensp;名</span><span style="font-family:宋体;font-size:10.0000pt;mso-font-kerning:0.0000pt;"><o:p></o:p></span>
					</p>
				</td>
				<td width="131" valign="center" colspan="3" style="width:98.2500pt;padding:0.0000pt 5.4000pt 0.0000pt 5.4000pt ;border-left:31.8750pt none rgb(255,255,255);mso-border-left-alt:31.8750pt none rgb(255,255,255);border-right:1.0000pt solid windowtext;mso-border-right-alt:0.5000pt solid windowtext;border-top:31.8750pt none rgb(255,255,255);mso-border-top-alt:31.8750pt none rgb(255,255,255);border-bottom:1.0000pt solid windowtext;mso-border-bottom-alt:0.5000pt solid windowtext;">
					<p class="MsoNormal" align="center" style="mso-pagination:widow-orphan;text-align:center;">
						<span style="font-family:宋体;font-size:10.0000pt;mso-font-kerning:0.0000pt;"><o:p><input id="fullname" name="fullname" class="form-control required" type="text" value="${userInfo.fullname}" maxlength="20" aria-required="true"><%-- <form:input path="fullname" htmlEscape="false" maxlength="20" class="form-control required"/> --%></o:p></span>
					</p>
				</td>
				<td width="68" valign="center" colspan="2" style="width:51.0000pt;padding:0.0000pt 5.4000pt 0.0000pt 5.4000pt ;border-left:31.8750pt none rgb(255,255,255);mso-border-left-alt:31.8750pt none rgb(255,255,255);border-right:1.0000pt solid windowtext;mso-border-right-alt:0.5000pt solid windowtext;border-top:31.8750pt none rgb(255,255,255);mso-border-top-alt:31.8750pt none rgb(255,255,255);border-bottom:1.0000pt solid windowtext;mso-border-bottom-alt:0.5000pt solid windowtext;">
					<p class="MsoNormal" align="center" style="mso-pagination:widow-orphan;text-align:center;">
						<span style="mso-spacerun:'yes';font-family:宋体;font-size:10.0000pt;mso-font-kerning:0.0000pt;">曾&ensp;用&ensp;名</span><span style="font-family:宋体;font-size:10.0000pt;mso-font-kerning:0.0000pt;"><o:p></o:p></span>
					</p>
				</td>
				<td width="109" valign="center" style="width:82.4000pt;padding:0.0000pt 5.4000pt 0.0000pt 5.4000pt ;border-left:31.8750pt none rgb(255,255,255);mso-border-left-alt:31.8750pt none rgb(255,255,255);border-right:1.0000pt solid windowtext;mso-border-right-alt:0.5000pt solid windowtext;border-top:31.8750pt none rgb(255,255,255);mso-border-top-alt:31.8750pt none rgb(255,255,255);border-bottom:1.0000pt solid windowtext;mso-border-bottom-alt:0.5000pt solid windowtext;">
					<p class="MsoNormal" align="center" style="mso-pagination:widow-orphan;text-align:center;">
						<span style="font-family:宋体;font-size:10.0000pt;mso-font-kerning:0.0000pt;"><o:p><form:input path="usedfullname" htmlEscape="false" maxlength="20" class="form-control"/></o:p></span>
					</p>
				</td>
				<td width="86" valign="center" style="width:64.6000pt;padding:0.0000pt 5.4000pt 0.0000pt 5.4000pt ;border-left:31.8750pt none rgb(255,255,255);mso-border-left-alt:31.8750pt none rgb(255,255,255);border-right:1.0000pt solid windowtext;mso-border-right-alt:0.5000pt solid windowtext;border-top:31.8750pt none rgb(255,255,255);mso-border-top-alt:31.8750pt none rgb(255,255,255);border-bottom:1.0000pt solid windowtext;mso-border-bottom-alt:0.5000pt solid windowtext;">
					<p class="MsoNormal" align="center" style="mso-pagination:widow-orphan;text-align:center;">
						<span style="mso-spacerun:'yes';font-family:宋体;font-size:10.0000pt;mso-font-kerning:0.0000pt;"><font color="red">*</font>性&ensp;&ensp;&ensp;&ensp;别</span><span style="font-family:宋体;font-size:10.0000pt;mso-font-kerning:0.0000pt;"><o:p></o:p></span>
					</p>
				</td>
				<td width="144" valign="center" colspan="3" style="width:108.4000pt;padding:0.0000pt 5.4000pt 0.0000pt 5.4000pt ;border-left:31.8750pt none rgb(255,255,255);mso-border-left-alt:31.8750pt none rgb(255,255,255);border-right:1.0000pt solid rgb(0,0,0);mso-border-right-alt:0.5000pt solid rgb(0,0,0);border-top:1.0000pt solid windowtext;mso-border-top-alt:0.5000pt solid windowtext;border-bottom:1.0000pt solid windowtext;mso-border-bottom-alt:0.5000pt solid windowtext;">
					<p class="MsoNormal" align="center" style="mso-pagination:widow-orphan;text-align:center;">
						<span style="font-family:宋体;font-size:10.0000pt;mso-font-kerning:0.0000pt;"><o:p>
							<%--<form:input path="sex" htmlEscape="false" maxlength="20" class="form-control required"/>--%>
							<form:select path="sex" class="form-control required" style="border:none;">
								<form:option value="" label=""/>
								<form:options items="${fns:getDictList('sex')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
							</form:select>
						</o:p>
						</span>
					</p>
				</td>
				<td width="98" valign="center" rowspan="4" style="width:74.0000pt;padding:0.0000pt 5.4000pt 0.0000pt 5.4000pt ;border-left:none;;mso-border-left-alt:none;;border-right:1.0000pt solid windowtext;mso-border-right-alt:0.5000pt solid windowtext;border-top:31.8750pt none rgb(255,255,255);mso-border-top-alt:31.8750pt none rgb(255,255,255);">
					<p class="MsoNormal" align="center" style="mso-pagination:widow-orphan;text-align:center;<c:if test="${userInfo.photo != '' && userInfo.photo != null}">display:none</c:if>" id="photoBox1">
						<span style="mso-spacerun:'yes';font-family:宋体;font-size:10.0000pt;mso-font-kerning:0.0000pt;">照片<br/><button type="button" class="btn btn-primary btn-sm" onclick="upload()" id="uploadPhoto" >上传照片</button></span><span style="font-family:宋体;font-size:10.0000pt;mso-font-kerning:0.0000pt;"><o:p></o:p></span>
					</p>
					<p class="MsoNormal" align="center" style="mso-pagination:widow-orphan;text-align:center; <c:if test="${userInfo.photo == '' || userInfo.photo == null}">display:none</c:if>" id="photoBox2">
						<a href="javascript:upload()" title="修改"><img src="${userInfo.photo}" id="hd" width="98" height="137" /></a>
					</p>
					<form:hidden path="photo"/>
				</td>
			</tr>
			<tr style="height:28.3500pt;">
				<td width="98" valign="center" style="width:73.9500pt;padding:0.0000pt 5.4000pt 0.0000pt 5.4000pt ;border-left:1.0000pt solid windowtext;mso-border-left-alt:0.5000pt solid windowtext;border-right:1.0000pt solid windowtext;mso-border-right-alt:0.5000pt solid windowtext;border-top:31.8750pt none rgb(255,255,255);mso-border-top-alt:31.8750pt none rgb(255,255,255);border-bottom:1.0000pt solid windowtext;mso-border-bottom-alt:0.5000pt solid windowtext;">
					<p class="MsoNormal" align="center" style="mso-pagination:widow-orphan;text-align:center;">
						<span style="mso-spacerun:'yes';font-family:宋体;font-size:10.0000pt;mso-font-kerning:0.0000pt;">身&ensp;&ensp;&ensp;&ensp;高</span><span style="font-family:宋体;font-size:10.0000pt;mso-font-kerning:0.0000pt;"><o:p></o:p></span>
					</p>
				</td>
				<td width="131" valign="center" colspan="3" style="width:98.2500pt;padding:0.0000pt 5.4000pt 0.0000pt 5.4000pt ;border-left:31.8750pt none rgb(255,255,255);mso-border-left-alt:31.8750pt none rgb(255,255,255);border-right:1.0000pt solid windowtext;mso-border-right-alt:0.5000pt solid windowtext;border-top:31.8750pt none rgb(255,255,255);mso-border-top-alt:31.8750pt none rgb(255,255,255);border-bottom:1.0000pt solid windowtext;mso-border-bottom-alt:0.5000pt solid windowtext;">
					<p class="MsoNormal" align="center" style="mso-pagination:widow-orphan;text-align:center;">
						<span style="font-family:宋体;font-size:10.0000pt;mso-font-kerning:0.0000pt;"><o:p><form:input path="bodyheight" htmlEscape="false" maxlength="20" class="form-control"/></o:p></span>
					</p>
				</td>
				<td width="68" valign="center" colspan="2" style="width:51.0000pt;padding:0.0000pt 5.4000pt 0.0000pt 5.4000pt ;border-left:31.8750pt none rgb(255,255,255);mso-border-left-alt:31.8750pt none rgb(255,255,255);border-right:1.0000pt solid windowtext;mso-border-right-alt:0.5000pt solid windowtext;border-top:31.8750pt none rgb(255,255,255);mso-border-top-alt:31.8750pt none rgb(255,255,255);border-bottom:1.0000pt solid windowtext;mso-border-bottom-alt:0.5000pt solid windowtext;">
					<p class="MsoNormal" align="center" style="mso-pagination:widow-orphan;text-align:center;">
						<span style="mso-spacerun:'yes';font-family:宋体;font-size:10.0000pt;mso-font-kerning:0.0000pt;">体&ensp;&ensp;&ensp;&ensp;重</span><span style="font-family:宋体;font-size:10.0000pt;mso-font-kerning:0.0000pt;"><o:p></o:p></span>
					</p>
				</td>
				<td width="109" valign="center" style="width:82.4000pt;padding:0.0000pt 5.4000pt 0.0000pt 5.4000pt ;border-left:31.8750pt none rgb(255,255,255);mso-border-left-alt:31.8750pt none rgb(255,255,255);border-right:1.0000pt solid windowtext;mso-border-right-alt:0.5000pt solid windowtext;border-top:31.8750pt none rgb(255,255,255);mso-border-top-alt:31.8750pt none rgb(255,255,255);border-bottom:1.0000pt solid windowtext;mso-border-bottom-alt:0.5000pt solid windowtext;">
					<p class="MsoNormal" align="center" style="mso-pagination:widow-orphan;text-align:center;">
						<span style="font-family:宋体;font-size:10.0000pt;mso-font-kerning:0.0000pt;"><o:p><form:input path="bodyweight" htmlEscape="false" maxlength="20" class="form-control"/></o:p></span>
					</p>
				</td>
				<td width="86" valign="center" style="width:64.6000pt;padding:0.0000pt 5.4000pt 0.0000pt 5.4000pt ;border-left:31.8750pt none rgb(255,255,255);mso-border-left-alt:31.8750pt none rgb(255,255,255);border-right:1.0000pt solid windowtext;mso-border-right-alt:0.5000pt solid windowtext;border-top:31.8750pt none rgb(255,255,255);mso-border-top-alt:31.8750pt none rgb(255,255,255);border-bottom:1.0000pt solid windowtext;mso-border-bottom-alt:0.5000pt solid windowtext;">
					<p class="MsoNormal" align="center" style="mso-pagination:widow-orphan;text-align:center;">
						<span style="mso-spacerun:'yes';font-family:宋体;font-size:10.0000pt;mso-font-kerning:0.0000pt;">血&ensp;&ensp;&ensp;&ensp;型</span><span style="font-family:宋体;font-size:10.0000pt;mso-font-kerning:0.0000pt;"><o:p></o:p></span>
					</p>
				</td>
				<td width="144" valign="center" colspan="3" style="width:108.4000pt;padding:0.0000pt 5.4000pt 0.0000pt 5.4000pt ;border-left:31.8750pt none rgb(255,255,255);mso-border-left-alt:31.8750pt none rgb(255,255,255);border-right:1.0000pt solid rgb(0,0,0);mso-border-right-alt:0.5000pt solid rgb(0,0,0);border-top:none;;mso-border-top-alt:0.5000pt solid windowtext;border-bottom:1.0000pt solid windowtext;mso-border-bottom-alt:0.5000pt solid windowtext;">
					<p class="MsoNormal" align="center" style="mso-pagination:widow-orphan;text-align:center;">
						<span style="font-family:宋体;font-size:10.0000pt;mso-font-kerning:0.0000pt;"><o:p><form:input path="blood" htmlEscape="false" maxlength="20" class="form-control"/></o:p></span>
					</p>
				</td>
			</tr>
			<tr style="height:28.3500pt;">
				<td width="98" valign="center" style="width:73.9500pt;padding:0.0000pt 5.4000pt 0.0000pt 5.4000pt ;border-left:1.0000pt solid windowtext;mso-border-left-alt:0.5000pt solid windowtext;border-right:1.0000pt solid windowtext;mso-border-right-alt:0.5000pt solid windowtext;border-top:31.8750pt none rgb(255,255,255);mso-border-top-alt:31.8750pt none rgb(255,255,255);border-bottom:1.0000pt solid windowtext;mso-border-bottom-alt:0.5000pt solid windowtext;">
					<p class="MsoNormal" align="center" style="mso-pagination:widow-orphan;text-align:center;">
						<span style="mso-spacerun:'yes';font-family:宋体;font-size:10.0000pt;mso-font-kerning:0.0000pt;">政治面貌</span><span style="font-family:宋体;font-size:10.0000pt;mso-font-kerning:0.0000pt;"><o:p></o:p></span>
					</p>
				</td>
				<td width="131" valign="center" colspan="3" style="width:98.2500pt;padding:0.0000pt 5.4000pt 0.0000pt 5.4000pt ;border-left:31.8750pt none rgb(255,255,255);mso-border-left-alt:31.8750pt none rgb(255,255,255);border-right:1.0000pt solid windowtext;mso-border-right-alt:0.5000pt solid windowtext;border-top:31.8750pt none rgb(255,255,255);mso-border-top-alt:31.8750pt none rgb(255,255,255);border-bottom:1.0000pt solid windowtext;mso-border-bottom-alt:0.5000pt solid windowtext;">
					<p class="MsoNormal" align="center" style="mso-pagination:widow-orphan;text-align:center;">
						<span style="font-family:宋体;font-size:10.0000pt;mso-font-kerning:0.0000pt;"><o:p><form:input path="political" htmlEscape="false" maxlength="20" class="form-control"/></o:p></span>
					</p>
				</td>
				<td width="68" valign="center" colspan="2" style="width:51.0000pt;padding:0.0000pt 5.4000pt 0.0000pt 5.4000pt ;border-left:31.8750pt none rgb(255,255,255);mso-border-left-alt:31.8750pt none rgb(255,255,255);border-right:1.0000pt solid windowtext;mso-border-right-alt:0.5000pt solid windowtext;border-top:31.8750pt none rgb(255,255,255);mso-border-top-alt:31.8750pt none rgb(255,255,255);border-bottom:1.0000pt solid windowtext;mso-border-bottom-alt:0.5000pt solid windowtext;">
					<p class="MsoNormal" align="center" style="mso-pagination:widow-orphan;text-align:center;">
						<span style="mso-spacerun:'yes';font-family:宋体;font-size:10.0000pt;mso-font-kerning:0.0000pt;"><font color="red">*</font>出身年月</span><span style="font-family:宋体;font-size:10.0000pt;mso-font-kerning:0.0000pt;"><o:p></o:p></span>
					</p>
				</td>
				<td width="109" valign="center" style="width:82.4000pt;padding:0.0000pt 5.4000pt 0.0000pt 5.4000pt ;border-left:31.8750pt none rgb(255,255,255);mso-border-left-alt:31.8750pt none rgb(255,255,255);border-right:1.0000pt solid windowtext;mso-border-right-alt:0.5000pt solid windowtext;border-top:31.8750pt none rgb(255,255,255);mso-border-top-alt:31.8750pt none rgb(255,255,255);border-bottom:1.0000pt solid windowtext;mso-border-bottom-alt:0.5000pt solid windowtext;">
					<p class="MsoNormal" align="center" style="mso-pagination:widow-orphan;text-align:center;">
						<span style="font-family:宋体;font-size:10.0000pt;mso-font-kerning:0.0000pt;"><o:p><%--<form:input path="birthday" htmlEscape="false" maxlength="20" class="form-control required"/>--%>
							<input id="birthday" name="birthday" type="text" maxlength="20"  class=" form-control layer-date required"
								   value="${userInfo.birthday}"/>
						</o:p></span>
					</p>
				</td>
				<td width="86" valign="center" style="width:64.6000pt;padding:0.0000pt 5.4000pt 0.0000pt 5.4000pt ;border-left:31.8750pt none rgb(255,255,255);mso-border-left-alt:31.8750pt none rgb(255,255,255);border-right:1.0000pt solid windowtext;mso-border-right-alt:0.5000pt solid windowtext;border-top:31.8750pt none rgb(255,255,255);mso-border-top-alt:31.8750pt none rgb(255,255,255);border-bottom:1.0000pt solid windowtext;mso-border-bottom-alt:0.5000pt solid windowtext;">
					<p class="MsoNormal" align="center" style="mso-pagination:widow-orphan;text-align:center;">
						<span style="mso-spacerun:'yes';font-family:宋体;font-size:10.0000pt;mso-font-kerning:0.0000pt;"><font color="red">*</font>身份证号</span><span style="font-family:宋体;font-size:10.0000pt;mso-font-kerning:0.0000pt;"><o:p></o:p></span>
					</p>
				</td>
				<td width="144" valign="center" colspan="3" style="width:108.4000pt;padding:0.0000pt 5.4000pt 0.0000pt 5.4000pt ;border-left:31.8750pt none rgb(255,255,255);mso-border-left-alt:31.8750pt none rgb(255,255,255);border-right:1.0000pt solid rgb(0,0,0);mso-border-right-alt:0.5000pt solid rgb(0,0,0);border-top:none;;mso-border-top-alt:0.5000pt solid windowtext;border-bottom:1.0000pt solid windowtext;mso-border-bottom-alt:0.5000pt solid windowtext;">
					<p class="MsoNormal" align="center" style="mso-pagination:widow-orphan;text-align:center;">
						<span style="font-family:宋体;font-size:10.0000pt;mso-font-kerning:0.0000pt;"><o:p><form:input path="idcardno" htmlEscape="false" maxlength="20" class="form-control required" style="padding:6px 0px;"/></o:p></span>
					</p>
				</td>
			</tr>
			<tr style="height:28.3500pt;">
				<td width="98" valign="center" style="width:73.9500pt;padding:0.0000pt 5.4000pt 0.0000pt 5.4000pt ;border-left:1.0000pt solid windowtext;mso-border-left-alt:0.5000pt solid windowtext;border-right:1.0000pt solid windowtext;mso-border-right-alt:0.5000pt solid windowtext;border-top:31.8750pt none rgb(255,255,255);mso-border-top-alt:31.8750pt none rgb(255,255,255);border-bottom:1.0000pt solid windowtext;mso-border-bottom-alt:0.5000pt solid windowtext;">
					<p class="MsoNormal" align="center" style="mso-pagination:widow-orphan;text-align:center;">
						<span style="mso-spacerun:'yes';font-family:宋体;font-size:10.0000pt;mso-font-kerning:0.0000pt;">籍&ensp;&ensp;&ensp;&ensp;贯</span><span style="font-family:宋体;font-size:10.0000pt;mso-font-kerning:0.0000pt;"><o:p></o:p></span>
					</p>
				</td>
				<td width="131" valign="center" colspan="3" style="width:98.2500pt;padding:0.0000pt 5.4000pt 0.0000pt 5.4000pt ;border-left:31.8750pt none rgb(255,255,255);mso-border-left-alt:31.8750pt none rgb(255,255,255);border-right:1.0000pt solid windowtext;mso-border-right-alt:0.5000pt solid windowtext;border-top:31.8750pt none rgb(255,255,255);mso-border-top-alt:31.8750pt none rgb(255,255,255);border-bottom:1.0000pt solid windowtext;mso-border-bottom-alt:0.5000pt solid windowtext;">
					<p class="MsoNormal" align="center" style="mso-pagination:widow-orphan;text-align:center;">
						<span style="font-family:宋体;font-size:10.0000pt;mso-font-kerning:0.0000pt;"><o:p><form:input path="origin" htmlEscape="false" maxlength="20" class="form-control"/></o:p></span>
					</p>
				</td>
				<td width="68" valign="center" colspan="2" style="width:51.0000pt;padding:0.0000pt 5.4000pt 0.0000pt 5.4000pt ;border-left:31.8750pt none rgb(255,255,255);mso-border-left-alt:31.8750pt none rgb(255,255,255);border-right:1.0000pt solid windowtext;mso-border-right-alt:0.5000pt solid windowtext;border-top:31.8750pt none rgb(255,255,255);mso-border-top-alt:31.8750pt none rgb(255,255,255);border-bottom:1.0000pt solid windowtext;mso-border-bottom-alt:0.5000pt solid windowtext;">
					<p class="MsoNormal" align="center" style="mso-pagination:widow-orphan;text-align:center;">
						<span style="mso-spacerun:'yes';font-family:宋体;font-size:10.0000pt;mso-font-kerning:0.0000pt;">民&ensp;&ensp;&ensp;&ensp;族</span><span style="font-family:宋体;font-size:10.0000pt;mso-font-kerning:0.0000pt;"><o:p></o:p></span>
					</p>
				</td>
				<td width="109" valign="center" style="width:82.4000pt;padding:0.0000pt 5.4000pt 0.0000pt 5.4000pt ;border-left:31.8750pt none rgb(255,255,255);mso-border-left-alt:31.8750pt none rgb(255,255,255);border-right:31.8750pt none rgb(255,255,255);mso-border-right-alt:31.8750pt none rgb(255,255,255);border-top:31.8750pt none rgb(255,255,255);mso-border-top-alt:31.8750pt none rgb(255,255,255);border-bottom:1.0000pt solid windowtext;mso-border-bottom-alt:0.5000pt solid windowtext;">
					<p class="MsoNormal" align="center" style="mso-pagination:widow-orphan;text-align:center;">
						<span style="font-family:宋体;font-size:10.0000pt;mso-font-kerning:0.0000pt;"><o:p><form:input path="nation" htmlEscape="false" maxlength="20" class="form-control"/></o:p></span>
					</p>
				</td>
				<td width="86" valign="center" style="width:64.6000pt;padding:0.0000pt 5.4000pt 0.0000pt 5.4000pt ;border-left:0.5000pt solid windowtext;mso-border-left-alt:0.5000pt solid windowtext;border-right:1.0000pt solid windowtext;mso-border-right-alt:0.5000pt solid windowtext;border-top:31.8750pt none rgb(255,255,255);mso-border-top-alt:31.8750pt none rgb(255,255,255);border-bottom:1.0000pt solid windowtext;mso-border-bottom-alt:0.5000pt solid windowtext;">
					<p class="MsoNormal" align="center" style="mso-pagination:widow-orphan;text-align:center;">
						<span style="mso-spacerun:'yes';font-family:宋体;font-size:10.0000pt;mso-font-kerning:0.0000pt;">宗教信仰</span><span style="font-family:宋体;font-size:10.0000pt;mso-font-kerning:0.0000pt;"><o:p></o:p></span>
					</p>
				</td>
				<td width="144" valign="center" colspan="3" style="width:108.4000pt;padding:0.0000pt 5.4000pt 0.0000pt 5.4000pt ;border-left:31.8750pt none rgb(255,255,255);mso-border-left-alt:31.8750pt none rgb(255,255,255);border-right:1.0000pt solid rgb(0,0,0);mso-border-right-alt:0.5000pt solid rgb(0,0,0);border-top:none;;mso-border-top-alt:0.5000pt solid windowtext;border-bottom:1.0000pt solid windowtext;mso-border-bottom-alt:0.5000pt solid windowtext;">
					<p class="MsoNormal" align="center" style="mso-pagination:widow-orphan;text-align:center;">
						<span style="font-family:宋体;font-size:10.0000pt;mso-font-kerning:0.0000pt;"><o:p><form:input path="religion" htmlEscape="false" maxlength="20" class="form-control"/></o:p></span>
					</p>
				</td>
			</tr>
			<tr style="height:28.3500pt;">
				<td width="98" valign="center" style="width:73.9500pt;padding:0.0000pt 5.4000pt 0.0000pt 5.4000pt ;border-left:1.0000pt solid windowtext;mso-border-left-alt:0.5000pt solid windowtext;border-right:1.0000pt solid windowtext;mso-border-right-alt:0.5000pt solid windowtext;border-top:31.8750pt none rgb(255,255,255);mso-border-top-alt:31.8750pt none rgb(255,255,255);border-bottom:1.0000pt solid windowtext;mso-border-bottom-alt:0.5000pt solid windowtext;">
					<p class="MsoNormal" align="center" style="mso-pagination:widow-orphan;text-align:center;">
						<span style="mso-spacerun:'yes';font-family:宋体;font-size:10.0000pt;mso-font-kerning:0.0000pt;">固定电话</span><span style="font-family:宋体;font-size:10.0000pt;mso-font-kerning:0.0000pt;"><o:p></o:p></span>
					</p>
				</td>
				<td width="131" valign="center" colspan="3" style="width:98.2500pt;padding:0.0000pt 5.4000pt 0.0000pt 5.4000pt ;border-left:31.8750pt none rgb(255,255,255);mso-border-left-alt:31.8750pt none rgb(255,255,255);border-right:1.0000pt solid windowtext;mso-border-right-alt:0.5000pt solid windowtext;border-top:31.8750pt none rgb(255,255,255);mso-border-top-alt:31.8750pt none rgb(255,255,255);border-bottom:1.0000pt solid windowtext;mso-border-bottom-alt:0.5000pt solid windowtext;">
					<p class="MsoNormal" align="center" style="mso-pagination:widow-orphan;text-align:center;">
						<span style="font-family:宋体;font-size:10.0000pt;mso-font-kerning:0.0000pt;"><o:p><form:input path="phone" htmlEscape="false" maxlength="20" class="form-control"/></o:p></span>
					</p>
				</td>
				<td width="68" valign="center" colspan="2" style="width:51.0000pt;padding:0.0000pt 5.4000pt 0.0000pt 5.4000pt ;border-left:31.8750pt none rgb(255,255,255);mso-border-left-alt:31.8750pt none rgb(255,255,255);border-right:1.0000pt solid windowtext;mso-border-right-alt:0.5000pt solid windowtext;border-top:31.8750pt none rgb(255,255,255);mso-border-top-alt:31.8750pt none rgb(255,255,255);border-bottom:1.0000pt solid windowtext;mso-border-bottom-alt:0.5000pt solid windowtext;">
					<p class="MsoNormal" align="center" style="mso-pagination:widow-orphan;text-align:center;">
						<span style="mso-spacerun:'yes';font-family:宋体;font-size:10.0000pt;mso-font-kerning:0.0000pt;"><font color="red">*</font>移动电话</span><span style="font-family:宋体;font-size:10.0000pt;mso-font-kerning:0.0000pt;"><o:p></o:p></span>
					</p>
				</td>
				<td width="109" valign="center" style="width:82.4000pt;padding:0.0000pt 5.4000pt 0.0000pt 5.4000pt ;border-left:31.8750pt none rgb(255,255,255);mso-border-left-alt:31.8750pt none rgb(255,255,255);border-right:1.0000pt solid windowtext;mso-border-right-alt:0.5000pt solid windowtext;border-top:31.8750pt none rgb(255,255,255);mso-border-top-alt:31.8750pt none rgb(255,255,255);border-bottom:1.0000pt solid windowtext;mso-border-bottom-alt:0.5000pt solid windowtext;">
					<p class="MsoNormal" align="center" style="mso-pagination:widow-orphan;text-align:center;">
						<span style="font-family:宋体;font-size:10.0000pt;mso-font-kerning:0.0000pt;"><o:p><form:input path="mobile" htmlEscape="false" maxlength="20" class="form-control required" style="padding:6px"/></o:p></span>
					</p>
				</td>
				<td width="86" valign="center" style="width:64.6000pt;padding:0.0000pt 5.4000pt 0.0000pt 5.4000pt ;border-left:31.8750pt none rgb(255,255,255);mso-border-left-alt:31.8750pt none rgb(255,255,255);border-right:1.0000pt solid windowtext;mso-border-right-alt:0.5000pt solid windowtext;border-top:31.8750pt none rgb(255,255,255);mso-border-top-alt:31.8750pt none rgb(255,255,255);border-bottom:1.0000pt solid windowtext;mso-border-bottom-alt:0.5000pt solid windowtext;">
					<p class="MsoNormal" align="center" style="mso-pagination:widow-orphan;text-align:center;">
						<span style="mso-spacerun:'yes';font-family:宋体;font-size:10.0000pt;mso-font-kerning:0.0000pt;">电子邮箱</span><span style="font-family:宋体;font-size:10.0000pt;mso-font-kerning:0.0000pt;"><o:p></o:p></span>
					</p>
				</td>
				<td width="243" valign="center" colspan="4" style="width:182.4000pt;padding:0.0000pt 5.4000pt 0.0000pt 5.4000pt ;border-left:31.8750pt none rgb(255,255,255);mso-border-left-alt:31.8750pt none rgb(255,255,255);border-right:1.0000pt solid windowtext;mso-border-right-alt:0.5000pt solid windowtext;border-top:none;;mso-border-top-alt:0.5000pt solid windowtext;border-bottom:1.0000pt solid windowtext;mso-border-bottom-alt:0.5000pt solid windowtext;">
					<p class="MsoNormal" align="center" style="text-align:center;">
						<span style="font-family:宋体;font-size:10.0000pt;mso-font-kerning:0.0000pt;"><o:p><form:input path="email" htmlEscape="false" maxlength="20" class="form-control email"/></o:p></span>
					</p>
				</td>
			</tr>
			<tr style="height:28.3500pt;">
				<td width="98" valign="center" style="width:73.9500pt;padding:0.0000pt 5.4000pt 0.0000pt 5.4000pt ;border-left:1.0000pt solid windowtext;mso-border-left-alt:0.5000pt solid windowtext;border-right:1.0000pt solid windowtext;mso-border-right-alt:0.5000pt solid windowtext;border-top:none;;mso-border-top-alt:0.5000pt solid windowtext;border-bottom:1.0000pt solid windowtext;mso-border-bottom-alt:0.5000pt solid windowtext;">
					<p class="MsoNormal" align="center" style="mso-pagination:widow-orphan;text-align:center;">
						<span style="mso-spacerun:'yes';font-family:宋体;font-size:10.0000pt;mso-font-kerning:0.0000pt;"><font color="red">*</font>最高学历</span><span style="font-family:宋体;font-size:10.0000pt;mso-font-kerning:0.0000pt;"><o:p></o:p></span>
					</p>
					<p class="MsoNormal" align="center" style="mso-pagination:widow-orphan;text-align:center;">
						<span style="mso-spacerun:'yes';font-family:宋体;font-size:10.0000pt;mso-font-kerning:0.0000pt;">（全日制）</span><span style="font-family:宋体;font-size:10.0000pt;mso-font-kerning:0.0000pt;"><o:p></o:p></span>
					</p>
				</td>
				<td width="131" valign="center" colspan="3" style="width:98.2500pt;padding:0.0000pt 5.4000pt 0.0000pt 5.4000pt ;border-left:none;;mso-border-left-alt:none;;border-right:1.0000pt solid windowtext;mso-border-right-alt:0.5000pt solid windowtext;border-top:none;;mso-border-top-alt:0.5000pt solid windowtext;border-bottom:1.0000pt solid windowtext;mso-border-bottom-alt:0.5000pt solid windowtext;">
					<p class="MsoNormal" align="center" style="text-align:center;">
						<span style="font-family:宋体;font-size:10.0000pt;mso-font-kerning:0.0000pt;">
							<o:p>
								<%--<form:input path="degree" htmlEscape="false" maxlength="20" class="form-control required"/>--%>
								<form:select path="degree" class="form-control required" style="padding:0;">
									<form:option value="" label=""/>
									<form:options items="${fns:getDictList('degree')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
								</form:select>
							</o:p>
						</span>
					</p>
				</td>
				<td width="68" valign="center" colspan="2" style="width:51.0000pt;padding:0.0000pt 5.4000pt 0.0000pt 5.4000pt ;border-left:none;;mso-border-left-alt:none;;border-right:1.0000pt solid windowtext;mso-border-right-alt:0.5000pt solid windowtext;border-top:none;;mso-border-top-alt:0.5000pt solid windowtext;border-bottom:1.0000pt solid windowtext;mso-border-bottom-alt:0.5000pt solid windowtext;">
					<p class="MsoNormal" align="center" style="text-align:center;">
						<span style="mso-spacerun:'yes';font-family:宋体;font-size:10.0000pt;mso-font-kerning:0.0000pt;"><font color="red">*</font>专&ensp;&ensp;&ensp;&ensp;业</span><span style="font-family:宋体;font-size:10.0000pt;mso-font-kerning:0.0000pt;"><o:p></o:p></span>
					</p>
				</td>
				<td width="109" valign="center" style="width:82.4000pt;padding:0.0000pt 5.4000pt 0.0000pt 5.4000pt ;border-left:none;;mso-border-left-alt:none;;border-right:1.0000pt solid windowtext;mso-border-right-alt:0.5000pt solid windowtext;border-top:none;;mso-border-top-alt:0.5000pt solid windowtext;border-bottom:1.0000pt solid windowtext;mso-border-bottom-alt:0.5000pt solid windowtext;">
					<p class="MsoNormal" align="center" style="text-align:center;">
						<span style="font-family:宋体;font-size:10.0000pt;mso-font-kerning:0.0000pt;"><o:p><form:input path="profession" htmlEscape="false" maxlength="20" class="form-control required"/></o:p></span>
					</p>
				</td>
				<td width="86" valign="center" style="width:64.6000pt;padding:0.0000pt 5.4000pt 0.0000pt 5.4000pt ;border-left:none;;mso-border-left-alt:none;;border-right:1.0000pt solid windowtext;mso-border-right-alt:0.5000pt solid windowtext;border-top:none;;mso-border-top-alt:0.5000pt solid windowtext;border-bottom:1.0000pt solid windowtext;mso-border-bottom-alt:0.5000pt solid windowtext;">
					<p class="MsoNormal" align="center" style="text-align:center;">
						<span style="mso-spacerun:'yes';font-family:宋体;font-size:10.0000pt;mso-font-kerning:0.0000pt;"><font color="red">*</font>毕业院校</span><span style="font-family:宋体;font-size:10.0000pt;mso-font-kerning:0.0000pt;"><o:p></o:p></span>
					</p>
				</td>
				<td width="243" valign="center" colspan="4" style="width:182.4000pt;padding:0.0000pt 5.4000pt 0.0000pt 5.4000pt ;border-left:none;;mso-border-left-alt:none;;border-right:1.0000pt solid windowtext;mso-border-right-alt:0.5000pt solid windowtext;border-top:none;;mso-border-top-alt:0.5000pt solid windowtext;border-bottom:1.0000pt solid windowtext;mso-border-bottom-alt:0.5000pt solid windowtext;">
					<p class="MsoNormal" align="center" style="mso-char-indent-count:4.9000;text-align:center;">
						<span style="font-family:宋体;font-size:10.0000pt;mso-font-kerning:0.0000pt;"><o:p><form:input path="college" htmlEscape="false" maxlength="20" class="form-control required"/></o:p></span>
					</p>
				</td>
			</tr>
			<tr style="height:28.3500pt;">
				<td width="98" valign="center" style="width:73.9500pt;padding:0.0000pt 5.4000pt 0.0000pt 5.4000pt ;border-left:1.0000pt solid windowtext;mso-border-left-alt:0.5000pt solid windowtext;border-right:1.0000pt solid windowtext;mso-border-right-alt:0.5000pt solid windowtext;border-top:31.8750pt none rgb(255,255,255);mso-border-top-alt:31.8750pt none rgb(255,255,255);border-bottom:1.0000pt solid windowtext;mso-border-bottom-alt:0.5000pt solid windowtext;">
					<p class="MsoNormal" align="center" style="mso-pagination:widow-orphan;text-align:center;">
						<span style="mso-spacerun:'yes';font-family:宋体;font-size:10.0000pt;mso-font-kerning:0.0000pt;">外语语种等级</span><span style="font-family:宋体;font-size:10.0000pt;mso-font-kerning:0.0000pt;"><o:p></o:p></span>
					</p>
				</td>
				<td width="638" valign="center" colspan="11" style="width:478.6500pt;padding:0.0000pt 5.4000pt 0.0000pt 5.4000pt ;border-left:31.8750pt none rgb(255,255,255);mso-border-left-alt:31.8750pt none rgb(255,255,255);border-right:1.0000pt solid windowtext;mso-border-right-alt:0.5000pt solid windowtext;border-top:31.8750pt none rgb(255,255,255);mso-border-top-alt:31.8750pt none rgb(255,255,255);border-bottom:1.0000pt solid windowtext;mso-border-bottom-alt:0.5000pt solid windowtext;">
					<p class="MsoNormal" align="center" style="mso-pagination:widow-orphan;text-align:center;">
						<span style="font-family:宋体;font-size:10.0000pt;mso-font-kerning:0.0000pt;"><o:p><form:input path="languages" htmlEscape="false" maxlength="20" class="form-control"/></o:p></span>
					</p>
				</td>
			</tr>
			<tr style="height:28.3500pt;">
				<td width="98" valign="center" style="width:73.9500pt;padding:0.0000pt 5.4000pt 0.0000pt 5.4000pt ;border-left:1.0000pt solid windowtext;mso-border-left-alt:0.5000pt solid windowtext;border-right:1.0000pt solid windowtext;mso-border-right-alt:0.5000pt solid windowtext;border-top:none;;mso-border-top-alt:0.5000pt solid windowtext;border-bottom:1.0000pt solid windowtext;mso-border-bottom-alt:0.5000pt solid windowtext;">
					<p class="MsoNormal" align="center" style="mso-pagination:widow-orphan;text-align:center;">
						<span style="mso-spacerun:'yes';font-family:宋体;font-size:10.0000pt;mso-font-kerning:0.0000pt;"><font color="red">*</font>婚姻状况</span><span style="font-family:宋体;font-size:10.0000pt;mso-font-kerning:0.0000pt;"><o:p></o:p></span>
					</p>
				</td>
				<td width="308" valign="center" colspan="6" style="width:231.6500pt;padding:0.0000pt 5.4000pt 0.0000pt 5.4000pt ;border-left:none;;mso-border-left-alt:none;;border-right:1.0000pt solid windowtext;mso-border-right-alt:0.5000pt solid windowtext;border-top:none;;mso-border-top-alt:0.5000pt solid windowtext;border-bottom:1.0000pt solid windowtext;mso-border-bottom-alt:0.5000pt solid windowtext;">
					<p class="MsoNormal" align="center" style="mso-char-indent-count:3.0000;text-align:center;">
						<span style="font-family:宋体;font-size:10.0000pt;mso-font-kerning:0.0000pt;"><o:p><form:input path="marriage" htmlEscape="false" maxlength="20" class="form-control required"/></o:p></span>
					</p>
				</td>
				<td width="97" valign="center" colspan="3" style="width:73.2500pt;padding:0.0000pt 5.4000pt 0.0000pt 5.4000pt ;border-left:none;;mso-border-left-alt:none;;border-right:1.0000pt solid windowtext;mso-border-right-alt:0.5000pt solid windowtext;border-top:1.0000pt solid windowtext;mso-border-top-alt:0.5000pt solid windowtext;border-bottom:1.0000pt solid windowtext;mso-border-bottom-alt:0.5000pt solid windowtext;">
					<p class="MsoNormal" align="center" style="text-align:center;">
						<span style="mso-spacerun:'yes';font-family:宋体;font-size:10.0000pt;mso-font-kerning:0.0000pt;"><font color="red">*</font>有无病史</span><span style="font-family:宋体;font-size:10.0000pt;mso-font-kerning:0.0000pt;"><o:p></o:p></span>
					</p>
				</td>
				<td width="231" valign="center" colspan="2" style="width:173.7500pt;padding:0.0000pt 5.4000pt 0.0000pt 5.4000pt ;border-left:none;;mso-border-left-alt:none;;border-right:1.0000pt solid windowtext;mso-border-right-alt:0.5000pt solid windowtext;border-top:1.0000pt solid windowtext;mso-border-top-alt:0.5000pt solid windowtext;border-bottom:1.0000pt solid windowtext;mso-border-bottom-alt:0.5000pt solid windowtext;">
					<p class="MsoNormal" align="center" style="text-align:center;">
						<span style="font-family:宋体;font-size:10.0000pt;mso-font-kerning:0.0000pt;"><o:p><form:input path="disease" htmlEscape="false" maxlength="20" class="form-control required"/></o:p></span>
					</p>
				</td>
			</tr>
			<%--<tr style="height:28.3500pt;">
				<td width="98" valign="center" style="width:73.9500pt;padding:0.0000pt 5.4000pt 0.0000pt 5.4000pt ;border-left:1.0000pt solid windowtext;mso-border-left-alt:0.5000pt solid windowtext;border-right:1.0000pt solid windowtext;mso-border-right-alt:0.5000pt solid windowtext;border-top:none;;mso-border-top-alt:0.5000pt solid windowtext;border-bottom:1.0000pt solid windowtext;mso-border-bottom-alt:0.5000pt solid windowtext;">
					<p class="MsoNormal" align="center" style="mso-pagination:widow-orphan;text-align:center;">
						<span style="mso-spacerun:'yes';font-family:宋体;font-size:10.0000pt;mso-font-kerning:0.0000pt;"><font color="red">*</font>目前劳动关系</span><span style="font-family:宋体;font-size:10.0000pt;mso-font-kerning:0.0000pt;"><o:p></o:p></span>
					</p>
				</td>
				<td width="638" valign="center" colspan="11" style="width:478.6500pt;padding:0.0000pt 5.4000pt 0.0000pt 5.4000pt ;border-left:none;;mso-border-left-alt:none;;border-right:1.0000pt solid windowtext;mso-border-right-alt:0.5000pt solid windowtext;border-top:none;;mso-border-top-alt:0.5000pt solid windowtext;border-bottom:1.0000pt solid windowtext;mso-border-bottom-alt:0.5000pt solid windowtext;">
					<p class="MsoNormal" style="mso-char-indent-count:1.5000;">
						<span style="font-family:宋体;font-size:10.0000pt;mso-font-kerning:0.0000pt;"><o:p><form:input path="workstatus" htmlEscape="false" maxlength="20" class="form-control required"/></o:p></span>
					</p>
				</td>
			</tr>--%>
			<tr style="height:28.3500pt;">
				<td width="98" valign="center" style="width:73.9500pt;padding:0.0000pt 5.4000pt 0.0000pt 5.4000pt ;border-left:1.0000pt solid windowtext;mso-border-left-alt:0.5000pt solid windowtext;border-right:1.0000pt solid windowtext;mso-border-right-alt:0.5000pt solid windowtext;border-top:none;;mso-border-top-alt:0.5000pt solid windowtext;border-bottom:1.0000pt solid windowtext;mso-border-bottom-alt:0.5000pt solid windowtext;">
					<p class="MsoNormal" style="mso-pagination:widow-orphan;">
						<span style="mso-spacerun:'yes';font-family:宋体;font-size:10.0000pt;mso-font-kerning:0.0000pt;"><font color="red">*</font>是否有亲友在本公司工作</span><span style="font-family:宋体;font-size:10.0000pt;mso-font-kerning:0.0000pt;"><o:p></o:p></span>
					</p>
				</td>
				<td width="308" valign="center" colspan="6" style="width:231.6500pt;padding:0.0000pt 5.4000pt 0.0000pt 5.4000pt ;border-left:none;;mso-border-left-alt:none;;border-right:1.0000pt solid windowtext;mso-border-right-alt:0.5000pt solid windowtext;border-top:none;;mso-border-top-alt:0.5000pt solid windowtext;border-bottom:1.0000pt solid windowtext;mso-border-bottom-alt:0.5000pt solid windowtext;">
					<p class="MsoNormal" style="mso-char-indent-count:3.0000;">
						<span style="font-family:宋体;font-size:10.0000pt;mso-font-kerning:0.0000pt;">
							<form:input path="isfamily" htmlEscape="false" maxlength="20" class="form-control required"/>
						</span>
					</p>
				</td>
				<td width="97" valign="center" colspan="2" style="width:72.7500pt;padding:0.0000pt 5.4000pt 0.0000pt 5.4000pt ;border-left:none;;mso-border-left-alt:none;;border-right:1.0000pt solid windowtext;mso-border-right-alt:0.5000pt solid windowtext;border-top:1.0000pt solid windowtext;mso-border-top-alt:0.5000pt solid windowtext;border-bottom:1.0000pt solid windowtext;mso-border-bottom-alt:0.5000pt solid windowtext;">
					<p class="MsoNormal" style="text-align:left;">
						<span style="mso-spacerun:'yes';font-family:宋体;font-size:10.0000pt;mso-font-kerning:0.0000pt;"><font color="red">*</font>是否有签署竞业禁止协议</span><span style="font-family:宋体;font-size:10.0000pt;mso-font-kerning:0.0000pt;"><o:p></o:p></span>
					</p>
				</td>
				<td width="232" valign="center" colspan="3" style="width:174.2500pt;padding:0.0000pt 5.4000pt 0.0000pt 5.4000pt ;border-left:none;;mso-border-left-alt:none;;border-right:1.0000pt solid windowtext;mso-border-right-alt:0.5000pt solid windowtext;border-top:1.0000pt solid windowtext;mso-border-top-alt:0.5000pt solid windowtext;border-bottom:1.0000pt solid windowtext;mso-border-bottom-alt:0.5000pt solid windowtext;">
					<p class="MsoNormal" style="mso-char-indent-count:3.0000;">
						<span style="font-family:宋体;font-size:10.0000pt;mso-font-kerning:0.0000pt;">
							<!-- <label class="radio-inline">
								<input type="radio" class="i-checks" name="isSign" id="" value="option1" checked="checked"> 是
							</label>
							<label class="radio-inline">
								<input type="radio" class="i-checks" name="isSign" id="" value="option2"> 否
							</label> -->
							<form:input path="iscompetition" htmlEscape="false" maxlength="20" class="form-control required"/>
						</span>
					</p>
				</td>
			</tr>
			<tr style="height:28.3500pt;">
				<td width="98" valign="center" style="width:73.9500pt;padding:0.0000pt 5.4000pt 0.0000pt 5.4000pt ;border-left:1.0000pt solid windowtext;mso-border-left-alt:0.5000pt solid windowtext;border-right:1.0000pt solid windowtext;mso-border-right-alt:0.5000pt solid windowtext;border-top:none;;mso-border-top-alt:0.5000pt solid windowtext;border-bottom:1.0000pt solid windowtext;mso-border-bottom-alt:0.5000pt solid windowtext;">
					<p class="MsoNormal" align="center" style="mso-pagination:widow-orphan;text-align:center;">
						<span style="mso-spacerun:'yes';font-family:宋体;font-size:10.0000pt;mso-font-kerning:0.0000pt;">上传证件</span><span style="font-family:宋体;font-size:10.0000pt;mso-font-kerning:0.0000pt;"><o:p></o:p></span>
					</p>
				</td>
				<td width="638" valign="center" colspan="11" style="width:478.6500pt;padding:0.0000pt 5.4000pt 0.0000pt 5.4000pt ;border-left:none;;mso-border-left-alt:none;;border-right:1.0000pt solid windowtext;mso-border-right-alt:0.5000pt solid windowtext;border-top:none;;mso-border-top-alt:0.5000pt solid windowtext;border-bottom:1.0000pt solid windowtext;mso-border-bottom-alt:0.5000pt solid windowtext;">
					<p class="MsoNormal" align="justify" style="text-align:justify;text-justify:inter-ideograph;" id="cardSelect">
						<span style="mso-spacerun:'yes';font-family:宋体;font-size:10.0000pt;mso-font-kerning:0.0000pt;" >
							<label class="checkbox-inline <c:if test="${userInfo.iscardurl != '' && userInfo.iscardurl != null}">checked</c:if>">
								<span class="text-danger">*</span>
								<c:if test="${userInfo.iscardurl != '' && userInfo.iscardurl != null}"><i class="glyphicon glyphicon-ok"></i></c:if>
								<a href="javascript:" vl="iscardurl" >身份证</a>
								<c:if test="${userInfo.iscardurl != '' && userInfo.iscardurl != null}"><i class="glyphicon glyphicon-remove-circle text-danger del"></i></c:if>
								<form:hidden path="iscardurl" />
							</label><label class="checkbox-inline <c:if test="${userInfo.certificateurl != '' && userInfo.certificateurl != null}">checked</c:if>">
								<span class="text-danger">*</span>
								<c:if test="${userInfo.certificateurl != '' && userInfo.certificateurl != null}"><i class="glyphicon glyphicon-ok"></i></c:if>
								<a href="javascript:" vl="certificateurl" >毕业证</a>
								<c:if test="${userInfo.certificateurl != '' && userInfo.certificateurl != null}"><i class="glyphicon glyphicon-remove-circle text-danger del"></i></c:if>
								<form:hidden path="certificateurl"/>
							</label><label class="checkbox-inline <c:if test="${userInfo.degreecertificateurl != '' && userInfo.degreecertificateurl != null}">checked</c:if>">
								<%--<span class="text-danger">*</span>--%>
								<c:if test="${userInfo.degreecertificateurl != '' && userInfo.degreecertificateurl != null}"><i class="glyphicon glyphicon-ok"></i></c:if>
								<a href="javascript:"  vl="degreecertificateurl">学位证</a>
								<c:if test="${userInfo.degreecertificateurl != '' && userInfo.degreecertificateurl != null}"><i class="glyphicon glyphicon-remove-circle text-danger del"></i></c:if>
								<form:hidden path="degreecertificateurl"/>
							</label><label class="checkbox-inline <c:if test="${userInfo.leavingcertificate != '' && userInfo.leavingcertificate != null}">checked</c:if>">
								<span class="text-danger">*</span>
								<c:if test="${userInfo.leavingcertificate != '' && userInfo.leavingcertificate != null}"><i class="glyphicon glyphicon-ok"></i></c:if>
								<a href="javascript:"  vl="leavingcertificate">离职证明</a>
								<c:if test="${userInfo.leavingcertificate != '' && userInfo.leavingcertificate != null}"><i class="glyphicon glyphicon-remove-circle text-danger del"></i></c:if>
								<form:hidden path="leavingcertificate"/>
							</label><label class="checkbox-inline <c:if test="${userInfo.resumeurl != '' && userInfo.resumeurl != null}">checked</c:if>">
								<span class="text-danger">*</span>
								<c:if test="${userInfo.resumeurl != '' && userInfo.resumeurl != null}"><i class="glyphicon glyphicon-ok"></i></c:if>
								<a href="javascript:"  vl="resumeurl">个人简历</a>
								<c:if test="${userInfo.resumeurl != '' && userInfo.resumeurl != null}"><i class="glyphicon glyphicon-remove-circle text-danger del"></i></c:if>
								<form:hidden path="resumeurl"/>
							</label><label class="checkbox-inline <c:if test="${userInfo.driverlicenseurl != '' && userInfo.driverlicenseurl != null}">checked</c:if>">
							<c:if test="${userInfo.driverlicenseurl != '' && userInfo.driverlicenseurl != null}"><i class="glyphicon glyphicon-ok"></i></c:if>
								<a href="javascript:"  vl="driverlicenseurl">驾照</a>
								<c:if test="${userInfo.driverlicenseurl != '' && userInfo.driverlicenseurl != null}"><i class="glyphicon glyphicon-remove-circle text-danger del"></i></c:if>
								<form:hidden path="driverlicenseurl"/>
							</label><label class="checkbox-inline <c:if test="${userInfo.qualificationcertificateurl != '' && userInfo.qualificationcertificateurl != null}">checked</c:if>">
							<c:if test="${userInfo.qualificationcertificateurl != '' && userInfo.qualificationcertificateurl != null}"><i class="glyphicon glyphicon-ok"></i></c:if>
								<a href="javascript:"  vl="qualificationcertificateurl">资格证</a>
								<c:if test="${userInfo.qualificationcertificateurl != '' && userInfo.qualificationcertificateurl != null}"><i class="glyphicon glyphicon-remove-circle text-danger del"></i></c:if>
								<form:hidden path="qualificationcertificateurl"/>
							</label>
							<label class="checkbox-inline <c:if test="${userInfo.bankcardurl != '' && userInfo.bankcardurl != null}">checked</c:if>">
								<span class="text-danger">*</span>
								<c:if test="${userInfo.bankcardurl != '' && userInfo.bankcardurl != null}"><i class="glyphicon glyphicon-ok"></i></c:if>
								<a href="javascript:" vl="bankcardurl" >银行卡</a>
								<c:if test="${userInfo.bankcardurl != '' && userInfo.bankcardurl != null}"><i class="glyphicon glyphicon-remove-circle text-danger del"></i></c:if>
								<form:hidden path="bankcardurl"/>
							</label>
							<label class="checkbox-inline <c:if test="${userInfo.healthurl != '' && userInfo.healthurl != null}">checked</c:if>">
								<span class="text-danger">*</span>
								<c:if test="${userInfo.healthurl != '' && userInfo.healthurl != null}"><i class="glyphicon glyphicon-ok"></i></c:if>
								<a href="javascript:" vl="healthurl" >体检证明</a>
								<c:if test="${userInfo.healthurl != '' && userInfo.healthurl != null}"><i class="glyphicon glyphicon-remove-circle text-danger del"></i></c:if>
								<form:hidden path="healthurl"/>
							</label>
						</span>
					</p>
				</td>
			</tr>
			<tr style="height:28.3500pt;">
				<td width="98" valign="center" style="width:73.9500pt;padding:0.0000pt 5.4000pt 0.0000pt 5.4000pt ;border-left:1.0000pt solid windowtext;mso-border-left-alt:0.5000pt solid windowtext;border-right:1.0000pt solid windowtext;mso-border-right-alt:0.5000pt solid windowtext;border-top:none;;mso-border-top-alt:0.5000pt solid windowtext;border-bottom:1.0000pt solid windowtext;mso-border-bottom-alt:0.5000pt solid windowtext;">
					<p class="MsoNormal" align="center" style="mso-pagination:widow-orphan;text-align:center;">
						<span style="mso-spacerun:'yes';font-family:宋体;font-size:10.0000pt;mso-font-kerning:0.0000pt;"><font color="red">*</font>现居住地</span><span style="font-family:宋体;font-size:10.0000pt;mso-font-kerning:0.0000pt;"><o:p></o:p></span>
					</p>
				</td>
				<td width="638" valign="center" colspan="11" style="width:478.6500pt;padding:0.0000pt 5.4000pt 0.0000pt 5.4000pt ;border-left:none;;mso-border-left-alt:none;;border-right:1.0000pt solid windowtext;mso-border-right-alt:0.5000pt solid windowtext;border-top:none;;mso-border-top-alt:0.5000pt solid windowtext;border-bottom:1.0000pt solid windowtext;mso-border-bottom-alt:0.5000pt solid windowtext;">
					<p class="MsoNormal" align="center" style="text-align:center;">
						<span style="font-family:宋体;font-size:10.0000pt;mso-font-kerning:0.0000pt;"><o:p><form:input path="address" htmlEscape="false" maxlength="50" class="form-control required" style="text-align:left"/></o:p></span>
					</p>
				</td>
			</tr>
			<tr style="height:28.3500pt;">
				<td width="736" valign="center" colspan="12" style="width:552.6000pt;padding:0.0000pt 5.4000pt 0.0000pt 5.4000pt ;border-left:1.0000pt solid windowtext;mso-border-left-alt:0.5000pt solid windowtext;border-right:1.0000pt solid windowtext;mso-border-right-alt:0.5000pt solid windowtext;border-top:none;;mso-border-top-alt:0.5000pt solid windowtext;border-bottom:1.0000pt solid windowtext;mso-border-bottom-alt:0.5000pt solid windowtext;background:rgb(255,204,153);">
					<p class="MsoNormal" align="center" style="mso-pagination:widow-orphan;text-align:center;">
						<b><span style="mso-spacerun:'yes';font-family:宋体;font-weight:bold;font-size:14.0000pt;mso-font-kerning:0.0000pt;">家&ensp;庭&ensp;成&ensp;员</span></b><b><span style="font-family:宋体;font-weight:bold;font-size:14.0000pt;mso-font-kerning:0.0000pt;"><o:p></o:p></span></b>
					</p>
				</td>
			</tr>
			<tr style="height:28.3500pt;">
				<td width="106" valign="center" colspan="2" style="width:79.5000pt;padding:0.0000pt 5.4000pt 0.0000pt 5.4000pt ;border-left:1.0000pt solid windowtext;mso-border-left-alt:0.5000pt solid windowtext;border-right:1.0000pt solid windowtext;mso-border-right-alt:0.5000pt solid windowtext;border-top:31.8750pt none rgb(255,255,255);mso-border-top-alt:31.8750pt none rgb(255,255,255);border-bottom:1.0000pt solid windowtext;mso-border-bottom-alt:0.5000pt solid windowtext;">
					<p class="MsoNormal" align="center" style="mso-pagination:widow-orphan;text-align:center;">
						<span style="mso-spacerun:'yes';font-family:宋体;font-size:10.0000pt;mso-font-kerning:0.0000pt;">关系</span><span style="font-family:宋体;font-size:10.0000pt;mso-font-kerning:0.0000pt;"><o:p></o:p></span>
					</p>
				</td>
				<td width="75" valign="center" style="width:56.7000pt;padding:0.0000pt 5.4000pt 0.0000pt 5.4000pt ;border-left:31.8750pt none rgb(255,255,255);mso-border-left-alt:31.8750pt none rgb(255,255,255);border-right:1.0000pt solid windowtext;mso-border-right-alt:0.5000pt solid windowtext;border-top:31.8750pt none rgb(255,255,255);mso-border-top-alt:31.8750pt none rgb(255,255,255);border-bottom:1.0000pt solid windowtext;mso-border-bottom-alt:0.5000pt solid windowtext;">
					<p class="MsoNormal" align="center" style="mso-pagination:widow-orphan;text-align:center;">
						<span style="mso-spacerun:'yes';font-family:宋体;font-size:10.0000pt;mso-font-kerning:0.0000pt;">姓名</span><span style="font-family:宋体;font-size:10.0000pt;mso-font-kerning:0.0000pt;"><o:p></o:p></span>
					</p>
				</td>
				<td width="76" valign="center" colspan="2" style="width:57.1500pt;padding:0.0000pt 5.4000pt 0.0000pt 5.4000pt ;border-left:31.8750pt none rgb(255,255,255);mso-border-left-alt:31.8750pt none rgb(255,255,255);border-right:1.0000pt solid windowtext;mso-border-right-alt:0.5000pt solid windowtext;border-top:31.8750pt none rgb(255,255,255);mso-border-top-alt:31.8750pt none rgb(255,255,255);border-bottom:1.0000pt solid windowtext;mso-border-bottom-alt:0.5000pt solid windowtext;">
					<p class="MsoNormal" align="center" style="mso-pagination:widow-orphan;text-align:center;">
						<span style="mso-spacerun:'yes';font-family:宋体;font-size:10.0000pt;mso-font-kerning:0.0000pt;">年龄</span><span style="font-family:宋体;font-size:10.0000pt;mso-font-kerning:0.0000pt;"><o:p></o:p></span>
					</p>
				</td>
				<td width="149" valign="center" colspan="2" style="width:112.2500pt;padding:0.0000pt 5.4000pt 0.0000pt 5.4000pt ;border-left:31.8750pt none rgb(255,255,255);mso-border-left-alt:31.8750pt none rgb(255,255,255);border-right:1.0000pt solid windowtext;mso-border-right-alt:0.5000pt solid windowtext;border-top:1.0000pt solid windowtext;mso-border-top-alt:0.5000pt solid windowtext;border-bottom:1.0000pt solid windowtext;mso-border-bottom-alt:0.5000pt solid windowtext;">
					<p class="MsoNormal" align="center" style="mso-pagination:widow-orphan;text-align:center;">
						<span style="mso-spacerun:'yes';font-family:宋体;font-size:10.0000pt;mso-font-kerning:0.0000pt;">工作单位及职务</span><span style="font-family:宋体;font-size:10.0000pt;mso-font-kerning:0.0000pt;"><o:p></o:p></span>
					</p>
				</td>
				<td width="97" valign="center" colspan="3" style="width:73.2500pt;padding:0.0000pt 5.4000pt 0.0000pt 5.4000pt ;border-left:31.8750pt none rgb(255,255,255);mso-border-left-alt:31.8750pt none rgb(255,255,255);border-right:1.0000pt solid windowtext;mso-border-right-alt:0.5000pt solid windowtext;border-top:1.0000pt solid windowtext;mso-border-top-alt:0.5000pt solid windowtext;border-bottom:1.0000pt solid windowtext;mso-border-bottom-alt:0.5000pt solid windowtext;">
					<p class="MsoNormal" align="center" style="mso-pagination:widow-orphan;text-align:center;">
						<span style="mso-spacerun:'yes';font-family:宋体;font-size:10.0000pt;mso-font-kerning:0.0000pt;">联系电话</span><span style="font-family:宋体;font-size:10.0000pt;mso-font-kerning:0.0000pt;"><o:p></o:p></span>
					</p>
				</td>
				<td width="231" valign="center" colspan="2" style="width:173.7500pt; position:relative; padding:0.0000pt 5.4000pt 0.0000pt 5.4000pt ;border-left:31.8750pt none rgb(255,255,255);mso-border-left-alt:31.8750pt none rgb(255,255,255);border-right:1.0000pt solid rgb(0,0,0);mso-border-right-alt:0.5000pt solid rgb(0,0,0);border-top:31.8750pt none rgb(255,255,255);mso-border-top-alt:31.8750pt none rgb(255,255,255);border-bottom:1.0000pt solid windowtext;mso-border-bottom-alt:0.5000pt solid windowtext;">
					<p class="MsoNormal" align="center" style="mso-pagination:widow-orphan;text-align:center;">
						<span style="mso-spacerun:'yes';font-family:宋体;font-size:10.0000pt;mso-font-kerning:0.0000pt;">详细住址</span><span style="font-family:宋体;font-size:10.0000pt;mso-font-kerning:0.0000pt;"><o:p></o:p></span>
					</p>
					<a id="addLine" href="javascript:" class="glyphicon glyphicon-plus" style="position:absolute; right:-20px; top:10px;"></a>
				</td>
			</tr>
			<c:if test="${userInfo != null && fn:length(userInfo.userMemberList) != 0}">
			<c:forEach items="${userInfo.userMemberList}" var="info">
			    <tr name="membertr" style="height:28.35pt"><td width="106" valign="center" colspan="2" style="width:79.5pt;padding:0 5.4pt 0 5.4pt;border-left:1pt solid windowtext;mso-border-left-alt:.5pt solid windowtext;border-right:1pt solid windowtext;mso-border-right-alt:.5pt solid windowtext;border-top:31.875pt none #fff;mso-border-top-alt:31.875pt none #fff;border-bottom:1pt solid windowtext;mso-border-bottom-alt:.5pt solid windowtext"><p class="MsoNormal" align="center" style="mso-pagination:widow-orphan;text-align:center"><span style="mso-spacerun:\yes\;font-family:宋体;font-size:10pt;mso-font-kerning:0"><input type="text" name="relationship" value="${info.relationship}" maxlength="10" /></span><span style="font-family:宋体;font-size:10pt;mso-font-kerning:0"><o:p></o:p></span></p></td>
				<td width="75" valign="center" style="width:56.7pt;padding:0 5.4pt 0 5.4pt;border-left:31.875pt none #fff;mso-border-left-alt:31.875pt none #fff;border-right:1pt solid windowtext;mso-border-right-alt:.5pt solid windowtext;border-top:31.875pt none #fff;mso-border-top-alt:31.875pt none #fff;border-bottom:1pt solid windowtext;mso-border-bottom-alt:.5pt solid windowtext"><p class="MsoNormal" align="center" style="mso-pagination:widow-orphan;text-align:center"><span style="font-family:宋体;font-size:10pt;mso-font-kerning:0"><o:p><input type="text" name="memberfullname" value="${info.memberfullname}" maxlength="10" /></o:p></span></p></td>
				<td width="76" valign="center" colspan="2" style="width:57.15pt;padding:0 5.4pt 0 5.4pt;border-left:31.875pt none #fff;mso-border-left-alt:31.875pt none #fff;border-right:1pt solid windowtext;mso-border-right-alt:.5pt solid windowtext;border-top:31.875pt none #fff;mso-border-top-alt:31.875pt none #fff;border-bottom:1pt solid windowtext;mso-border-bottom-alt:.5pt solid windowtext"><p class="MsoNormal" align="center" style="mso-pagination:widow-orphan;text-align:center"><span style="font-family:宋体;font-size:10pt;mso-font-kerning:0"><o:p><input type="text" name="memberage" value="${info.memberage}" maxlength="10" class="form-control digits" /></o:p></span></p></td>
				<td width="149" valign="center" colspan="2" style="width:112.25pt;padding:0 5.4pt 0 5.4pt;border-left:31.875pt none #fff;mso-border-left-alt:31.875pt none #fff;border-right:1pt solid windowtext;mso-border-right-alt:.5pt solid windowtext;border-top:none;mso-border-top-alt:.5pt solid windowtext;border-bottom:1pt solid windowtext;mso-border-bottom-alt:.5pt solid windowtext"><p class="MsoNormal" align="center" style="mso-pagination:widow-orphan;text-align:center"><span style="font-family:宋体;font-size:10pt;mso-font-kerning:0"><o:p><input type="text" name="memberworkunit" value="${info.memberworkunit}" maxlength="20" /></o:p></span></p></td>
				<td width="97" valign="center" colspan="3" style="width:73.25pt;padding:0 5.4pt 0 5.4pt;border-left:31.875pt none #fff;mso-border-left-alt:31.875pt none #fff;border-right:1pt solid windowtext;mso-border-right-alt:.5pt solid windowtext;border-top:none;mso-border-top-alt:.5pt solid windowtext;border-bottom:1pt solid windowtext;mso-border-bottom-alt:.5pt solid windowtext"><p class="MsoNormal" align="center" style="mso-pagination:widow-orphan;text-align:center"><span style="font-family:宋体;font-size:10pt;mso-font-kerning:0"><o:p><input type="text" name="membermobile" value="${info.membermobile}" maxlength="11" class="form-control phone"/></o:p></span></p></td>
				<td width="231" valign="center" colspan="2" style="width:173.75pt;position:relative;padding:0 5.4pt 0 5.4pt;border-left:31.875pt none #fff;mso-border-left-alt:31.875pt none #fff;border-right:1pt solid windowtext;mso-border-right-alt:.5pt solid windowtext;border-top:31.875pt none #fff;mso-border-top-alt:31.875pt none #fff;border-bottom:1pt solid windowtext;mso-border-bottom-alt:.5pt solid windowtext"><p class="MsoNormal" align="center" style="mso-pagination:widow-orphan;text-align:center"><span style="font-family:宋体;font-size:10pt;mso-font-kerning:0"><o:p><input type="text" name="memberaddress" value="${info.memberaddress}" maxlength="80" /></o:p></span></p><a href="javascript:" onclick="delline(this)" class="glyphicon glyphicon-remove text-danger" style="position:absolute;right:-20px;top:10px"></a></td></tr>
			</c:forEach>
			</c:if>
			<c:if test="${userInfo == null || fn:length(userInfo.userMemberList) == 0}">
				<tr name="membertr" style="height:28.35pt"><td width="106" valign="center" colspan="2" style="width:79.5pt;padding:0 5.4pt 0 5.4pt;border-left:1pt solid windowtext;mso-border-left-alt:.5pt solid windowtext;border-right:1pt solid windowtext;mso-border-right-alt:.5pt solid windowtext;border-top:31.875pt none #fff;mso-border-top-alt:31.875pt none #fff;border-bottom:1pt solid windowtext;mso-border-bottom-alt:.5pt solid windowtext"><p class="MsoNormal" align="center" style="mso-pagination:widow-orphan;text-align:center"><span style="mso-spacerun:\yes\;font-family:宋体;font-size:10pt;mso-font-kerning:0"><input type="text" name="relationship" value="" maxlength="10" /></span><span style="font-family:宋体;font-size:10pt;mso-font-kerning:0"><o:p></o:p></span></p></td>
				<td width="75" valign="center" style="width:56.7pt;padding:0 5.4pt 0 5.4pt;border-left:31.875pt none #fff;mso-border-left-alt:31.875pt none #fff;border-right:1pt solid windowtext;mso-border-right-alt:.5pt solid windowtext;border-top:31.875pt none #fff;mso-border-top-alt:31.875pt none #fff;border-bottom:1pt solid windowtext;mso-border-bottom-alt:.5pt solid windowtext"><p class="MsoNormal" align="center" style="mso-pagination:widow-orphan;text-align:center"><span style="font-family:宋体;font-size:10pt;mso-font-kerning:0"><o:p><input type="text" name="memberfullname" maxlength="10" /></o:p></span></p></td>
				<td width="76" valign="center" colspan="2" style="width:57.15pt;padding:0 5.4pt 0 5.4pt;border-left:31.875pt none #fff;mso-border-left-alt:31.875pt none #fff;border-right:1pt solid windowtext;mso-border-right-alt:.5pt solid windowtext;border-top:31.875pt none #fff;mso-border-top-alt:31.875pt none #fff;border-bottom:1pt solid windowtext;mso-border-bottom-alt:.5pt solid windowtext"><p class="MsoNormal" align="center" style="mso-pagination:widow-orphan;text-align:center"><span style="font-family:宋体;font-size:10pt;mso-font-kerning:0"><o:p><input type="text" name="memberage" maxlength="10" class="form-control digits"/></o:p></span></p></td>
				<td width="149" valign="center" colspan="2" style="width:112.25pt;padding:0 5.4pt 0 5.4pt;border-left:31.875pt none #fff;mso-border-left-alt:31.875pt none #fff;border-right:1pt solid windowtext;mso-border-right-alt:.5pt solid windowtext;border-top:none;mso-border-top-alt:.5pt solid windowtext;border-bottom:1pt solid windowtext;mso-border-bottom-alt:.5pt solid windowtext"><p class="MsoNormal" align="center" style="mso-pagination:widow-orphan;text-align:center"><span style="font-family:宋体;font-size:10pt;mso-font-kerning:0"><o:p><input type="text" name="memberworkunit" maxlength="20" /></o:p></span></p></td>
				<td width="97" valign="center" colspan="3" style="width:73.25pt;padding:0 5.4pt 0 5.4pt;border-left:31.875pt none #fff;mso-border-left-alt:31.875pt none #fff;border-right:1pt solid windowtext;mso-border-right-alt:.5pt solid windowtext;border-top:none;mso-border-top-alt:.5pt solid windowtext;border-bottom:1pt solid windowtext;mso-border-bottom-alt:.5pt solid windowtext"><p class="MsoNormal" align="center" style="mso-pagination:widow-orphan;text-align:center"><span style="font-family:宋体;font-size:10pt;mso-font-kerning:0"><o:p><input type="text" name="membermobile" maxlength="11" class="form-control phone" /></o:p></span></p></td>
				<td width="231" valign="center" colspan="2" style="width:173.75pt;position:relative;padding:0 5.4pt 0 5.4pt;border-left:31.875pt none #fff;mso-border-left-alt:31.875pt none #fff;border-right:1pt solid windowtext;mso-border-right-alt:.5pt solid windowtext;border-top:31.875pt none #fff;mso-border-top-alt:31.875pt none #fff;border-bottom:1pt solid windowtext;mso-border-bottom-alt:.5pt solid windowtext"><p class="MsoNormal" align="center" style="mso-pagination:widow-orphan;text-align:center"><span style="font-family:宋体;font-size:10pt;mso-font-kerning:0"><o:p><input type="text" name="memberaddress" maxlength="80" /></o:p></span></p><a href="javascript:" onclick="delline(this)" class="glyphicon glyphicon-remove text-danger" style="position:absolute;right:-20px;top:10px"></a></td></tr>
			</c:if>
			
				
			
			</table>
			<table class="MsoNormalTable" style="border-collapse:collapse;width:552.6000pt;margin-left:4.6500pt;mso-table-layout-alt:fixed;mso-padding-alt:0.0000pt 5.4000pt 0.0000pt 5.4000pt ;">
				<tr style="height:28.3500pt;">
					<td width="736" valign="center" colspan="12" style="width:552.6000pt;padding:0.0000pt 5.4000pt 0.0000pt 5.4000pt ;border-top:none;;mso-border-top-alt:0.5000pt solid windowtext;">
						<p class="MsoNormal" style="margin-left:45.0000pt;text-indent:-45.0000pt;mso-char-indent-count:-5.0000;mso-pagination:widow-orphan;">
							<span style="mso-spacerun:'yes';font-family:宋体;font-size:9.0000pt;mso-font-kerning:1.0000pt;">声明事项：上述所填事项</span><span style="mso-spacerun:'yes';font-family:宋体;font-size:9.0000pt;mso-font-kerning:1.0000pt;">均由本人亲自填写，</span><span style="mso-spacerun:'yes';font-family:宋体;font-size:9.0000pt;mso-font-kerning:1.0000pt;">均属事实，本人对此负责，如因伪造、虚报而给公司造成的任何损失由本人承担，</span><span style="mso-spacerun:'yes';font-family:Arial;font-size:9.0000pt;mso-font-kerning:1.0000pt;">如有不真实情况公司有权解除合同，且不支付补偿金</span><span style="font-family:宋体;mso-ascii-font-family:Arial;mso-hansi-font-family:Arial;mso-bidi-font-family:Arial;font-size:9.0000pt;mso-font-kerning:1.0000pt;">，</span><span style="mso-spacerun:'yes';font-family:宋体;font-size:9.0000pt;mso-font-kerning:1.0000pt;">在此授权公司对以上情况的真实性进行调查。</span><span style="font-family:宋体;font-size:10.0000pt;mso-font-kerning:0.0000pt;"><o:p></o:p></span>
						</p>
					</td>
				</tr>
			</table>
			<p class="MsoNormal" style="margin-left:52.5000pt;text-indent:-52.7000pt;mso-char-indent-count:-5.0000;">
				<b><span style="mso-spacerun:'yes';font-family:宋体;font-weight:bold;font-size:10.5000pt;mso-font-kerning:1.0000pt;"><o:p>&ensp;</o:p></span></b>
			</p>
			<p class="MsoNormal" style="margin-left:52.5000pt;text-indent:-52.7000pt;mso-char-indent-count:-5.0000;">
				<b><span style="mso-spacerun:'yes';font-family:宋体;font-weight:bold;font-size:10.5000pt;mso-font-kerning:1.0000pt;"><o:p>&ensp;</o:p></span></b>
			</p>
			<p class="MsoNormal" style="margin-left:52.5000pt;text-indent:-52.7000pt;mso-char-indent-count:-5.0000;">
				<b><span style="mso-spacerun:'yes';font-family:宋体;font-weight:bold;font-size:10.5000pt;mso-font-kerning:1.0000pt;"><font face="宋体">人事审核：</font></span></b><b><span style="mso-spacerun:'yes';font-family:宋体;font-weight:bold;font-size:10.5000pt;mso-font-kerning:1.0000pt;"><o:p></o:p></span></b>
			</p>
			<p class="MsoNormal" style="margin-left:52.5000pt;text-indent:-52.5000pt;mso-char-indent-count:-5.0000;">
				<span style="mso-spacerun:'yes';font-family:宋体;font-size:10.5000pt;mso-fopositionnt-kerning:1.0000pt;"><font face="宋体">岗位：</font><input type="text" readonly="readonly" name="position" value="${userInfo.position}" class="footer_input" /></span><span style="mso-spacerun:'yes';font-family:宋体;font-size:10.5000pt;mso-font-kerning:1.0000pt;">&ensp;</span><span style="mso-spacerun:'yes';font-family:宋体;font-size:10.5000pt;mso-font-kerning:1.0000pt;">&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;<font face="宋体">试用期：</font><input type="text" readonly="readonly" name="probationperiod" value="${userInfo.probationperiod}" class="footer_input" />&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;<font face="宋体">试用期工资：</font><font face="Times New Roman"><input type="text" readonly="readonly" name="probationperiodsalary" value="${userInfo.probationperiodsalary}" class="footer_input" /></font></span><span style="mso-spacerun:'yes';font-family:宋体;font-size:10.5000pt;mso-font-kerning:1.0000pt;">&ensp;&ensp;&ensp;</span><span style="mso-spacerun:'yes';font-family:宋体;font-size:10.5000pt;mso-font-kerning:1.0000pt;">&ensp;</span><span style="mso-spacerun:'yes';font-family:宋体;font-size:10.5000pt;mso-font-kerning:1.0000pt;">&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;</span><span style="mso-spacerun:'yes';font-family:宋体;font-size:5.0000pt;mso-font-kerning:1.0000pt;"><o:p></o:p></span>
			</p>
		</div>
		
		<c:if test="${userInfo == null || userInfo.ispermit != 1 }">
			<div class="row" style="padding-top:30px; padding-left:330px;">
				<c:if test="${isehr == 'true'}">
				<a href="javascript:" class="btn btn-primary" id="sub2" >确认修改</a>
				</c:if>
				<c:if test="${isehr != 'true'}">
					<a href="javascript:" class="btn btn-primary" id="sub" >下一步</a>
				</c:if>
			</div>
		</c:if>
		<!--EndFragment-->

	
	
	
	
	
	
	
	

	</div>
	</div>
</div>

<input type="hidden" name="disclaimername" value="${userInfo.disclaimername}" class="footer_input" />
<input type="hidden" name="disclaimerno" value="${userInfo.disclaimerno}" class="footer_input" />
<input type="hidden" name="disclaimeryear" value="${userInfo.disclaimeryear}" class="footer_input" />
<input type="hidden" name="disclaimermonth" value="${userInfo.disclaimermonth}" class="footer_input" />
<input type="hidden" name="disclaimerday" value="${userInfo.disclaimerday}" class="footer_input" />
<input type="hidden" name="disclaimerdisease" value="${userInfo.disclaimerdisease}" class="footer_input" />
</form:form>

<div class="promise_box">
	<div class="promiseform1 hideScroll">
		<h3 class="text-center">${fns:getUser().company.name}员工承诺书</h3>
		<p>我对${fns:getUser().company.name}(包括下属公司)的情况已知晓,我非常愿意成为${fns:getUser().company.name}的员工,为了以后更好地开展工作,本人作以下承诺：</p>
		<p>1、我已知晓本人的试用期限，同意在试用期内接受公司对我的试用考核，公司有权决定我是否被录用，如若试用期间本人能工作出色、独立胜任岗位并经部门负责人认可将直接或者提前转正。</p>
		<p>2、知晓我所在岗位的工作内容、工作条件、工作地点、劳动报酬、安全生产状况等情况，知晓所在岗位职业危害包括噪音、温度、危害气体对人体的危害，并知晓该岗位的安全防护措施。</p>
		<p>3、如被录用我向公司保证，我与其他单位无任何劳动合同关系，也没有和其他单位签订过竞业限制的条款（即用人单位对负有保守用人单位商业秘密的劳动者在劳动合同中约定，劳动者在终止或解除劳动合同后的一定期限内不得到有竞争关系的用人单位任职，也不得自己从事与原单位有竞争关系的经营、生产性业务）。因此产生的后果（包括给公司造成的经济损失），均由承诺人承担。</p>
		<p>4、在试用期的工作中认真学习和体现公司的经营理念、管理理念、服务理念和企业道德。</p>
		<p>5、在进入公司后的3个月时间内，因个人原因发生的意外所产生的一切费用将与公司无关。</p>
		<p>6、在公司服务期间将认真学习和遵守公司的各项规章制度，包括公司规定的各项保密制度。</p>
		<p>7、我是一个品行良好、遵纪守法的公民，从未受到过司法部门的处理（除一般交通违章外），也未受到原单位的行政处分（如有应讲明）。</p>
		<p>8、我身体健康（身心健康），无比较严重（医学上认为）的遗传疾病，也没患过比较严重的疾病（如传染病，如患过应讲明）。我珍爱自己的身体，对因自己过错而造成的人身伤害负全部责任。</p>
		<p>9、试用期按月向直接上级上报工作总结，对工作情况进行汇报，作自我评定，阐述不足和困难以及下一步努力方向。</p>
		<p>10、试用期满后，我如被录用，该承诺对我在公司期间始终有效，如不被录用，该承诺中与${fns:getUser().company.name}相关的内容将对我终身有效。</p>
		<p>11、我向公司提供的所有材料均真实有效，向公司陈述的事实和承诺均真实可信。</p>
		<p>12、乙方违反法律法规，违反公司规定和公司规章制度给甲方造成经济损失的，乙方应依照过错责任的大小承担实际经济损失。</p>
		<p>13、守规诚信</p>
		<p>严格遵守国家政策法规，严格遵守企业管理规章制度</p>
		<p>坚决维护公司利益，坚决与侵害公司利益的坏人坏事作斗争，坚决与损害公司形象的不良现象作斗争。</p>
		<p>不利用职务之便收受贿赂，或假公济私以公务之名报销各项费用或牟取私利。</p>
		<p>不侵占、挪用公司财产，爱护公司财物。</p>
		<p>不发布或传播虚假广告、行业信息，不发表易引人误解的言论或虚假宣传。</p>
		<p>严守公司机密(客户、人事、信息等)。</p>
		<p>不以公司名义擅自组织各种活动。时刻注意维护公司良好的品牌形象，不抵毁同行企业。</p>
		<p>诚信回报社会，力所能及的支持、参与公益或慈善事业。</p>
		<p>尊重个人、服务客户、追求卓越。</p>
		<p>14、经营管理</p>
		<p>认同公司发展战略，认同公司价值观，认同公司经营管理模式，拥护公司规章制度并积极传播，与公司同发展共成长。</p>
		<p>认真学习与领悟华冶企业文化，积极传播${fns:getUser().company.name}企业文化，正确引导舆论导向。打造一支凝聚力、创造力、执行力超强的${fns:getUser().company.name}队伍。</p>
		<p>未经公司董事会或股东会同意，不得利用职务之便擅自以单位或个人名义对外（单位或个人）提供担保，包括但不限于各种业务经营、资金、资产等方面的担保，若违规愿意承担相应的经济和法律责任。</p>
		<p>保证各项制度、政策的正确执行和各项经营管理指标的完成，对所管辖单位的经营管理风险、盈利或亏损，按公司相关规定承担经济及行政责任。</p>
		<p>不接受客户赠送礼品，无法回绝的情况下及时上交公司。</p>
		<p>与客户业务往来坚持公平、诚信、互惠互利的原则。</p>
		<p>用心服务客户，信守对客户的承诺。不以欺瞒、哄骗的态度向客户销售有质量缺陷、异议的产品。</p>
		<p>领导是宣传站，在日常工作中应对员工思想意识方面进行教育引导，发现问题及时予以疏导，情节严重应尽早汇报相关部门协调解决。员工出现违法违纪现象，主管承担连带责任。</p>
		<p>15、职业素养</p>
		<p>工作注重简单、高效，避免把简单问题复杂化。</p>
		<p>时时保持积极主动的心态，树立并传播${fns:getUser().company.name}主人翁意识。</p>
		<p>服从领导，尊重同事，鼓励下属，保持良好的团队协作精神。</p>
		<p>坚定终身学习的意识，适应企业快速发展，通过各种方式加强学习，提高自身素质与经营管理水平。</p>
		<p>树立正确的人生观、价值观，高尚的生活情趣和追求，崇尚节俭，反对生活奢侈、铺张浪费。</p>
		<p>培养良好的生活作风和生活方式，注重合理饮食、适量运动，控制并调节情绪，合理安排作息时间，爱惜身体，以良好的身心状态投入工作和生活。</p>
		<p>尊老爱幼，家庭和睦，均衡家庭和事业的关系。</p>
		<p>16、违规处罚</p>
		<p>经调查核实，若本人或下属员工违反国家政策法规或公司规章制度，我愿意接受公司按规定给予的行政和经济处分甚至除名，情节严重移交公安机关处理。</p>
	</div>
	<div class="promiseform2 hideScroll">
		<h3 class="text-center">职业道德承诺书</h3>
		<p>本人 ${fns:getUser().name} 作为${fns:getUser().company.name}的一员，自愿作出如下承诺：</p>
		<p>1、在职期间严格遵守公司各项规章制度。</p>
		<p>2、由于本人不能胜任岗位工作或者由于本人重大过失给公司造成较大损失的，本人自愿接受公司的调岗或调薪或按公司的规章制度进行的处罚，同意根据公司的考核结果作出的调岗调薪或其他处理。</p>
		<p>3、不利用职权收受贿赂和其他非法收入，不侵占公司的财产，不挪用公司资金或将公司资金借贷给他人，不用公司资产为自己或他人提供担保，一经查实有此情况的，本人愿意双倍退还公司损失或非法收入部分。</p>
		<p>4、不自营或参与经营与公司同类的业务或从事损害公司利益的活动，不从事跟公司业务无关的业务（转卖、介绍、替人担保等）。</p>
		<p>5、不得向与公司业务相同或相近的任何单位及其个人借款或借物品，不接受与公司业务相同或相近的任何单位或个人赠送的礼品、礼金（包括接受上述单位及其个人的请客吃饭），除工作需要经领导同意外。</p>
		<p>6、维护同事之间的正常工作伙伴关系，不发生任何超越非同事之间的伙伴关系的行为或传闻或有违道德的活动等。</p>
		<p>7、不利用职权之便，招聘亲朋好友进入公司。</p>
		<p>8、严格保密公司的各项资料，不擅自复印或传播。</p>
		<p>9、竭力维护公司的形象与声誉，对自身的形象、操守、行为负责，成为下属员工的典范及榜样，以身作则。</p>
	</div>
	<div class="promiseform3 hideScroll">
		<h3 class="text-center">职业危害因素告知书</h3>
		<p>根据《中华人民共和国职业病防治法》第三十条的规定，我公司应当将工作过程中可能产生的职业病危害及其后果、职业病防护措施等如实告知您，不得隐瞒或者欺骗。在劳动合同期间，您的工作岗位或发生变更的岗位存在职业病危害因素时，公司将重新告知并请您签署。</p>
		<p>您所在区域的岗位，存在职业病危害因素，如防护不当，该职业危害因素可能对您的身体造成一定程度的损害。在本岗位，公司已按照国家有关规定，对职业危害因素采取了职业病防护措施，对您发放合适的个人防护用品。</p>
		<p>根据《中华人民共和国职业病防治法》的规定，您有义务履行以下规定：自觉遵守用人单位制定的本岗位职业卫生操作规程和制度；正确使用职业防护设备和个人职业病防护用品；积极参加职业卫生知识培训；定期参加职业病健康体检；发现职业病危害隐患应当及时报告用人单位；树立自我保护意识，积极配合用人单位，避免职业病的发生。</p>
		<p>若因您不恰当履行前款规定的义务，导致本人或者他人损害并进而导致公司承担任何支付和补偿责任的，公司将有权追究您的个人责任。</p>
		<p>附表：</p>
		<p class="text-center">职业危害告知种类表</p>
		<table class="table table-bordered">
			<tr>
				<th>部门岗位</th>
				<th>职业危害种类</th>
				<th>可能导致的职业危害</th>
				<th>个人防护用品</th>
			</tr>
			<tr>
				<td>酸再生</td>
				<td>粉尘</td>
				<td>尘肺</td>
				<td>防护口罩</td>
			</tr>
			<tr>
				<td>轧机、锌锅工</td>
				<td>噪声</td>
				<td>噪声聋</td>
				<td>耳塞</td>
			</tr>
			<tr>
				<td>酸洗车间</td>
				<td>酸雾</td>
				<td>呼吸道</td>
				<td>防护口罩</td>
			</tr>
			<tr>
				<td>轧机出入口操作工</td>
				<td>X光射线</td>
				<td>X射线可能产生的危害</td>
				<td>按特种作业要求操作</td>
			</tr>
		</table>
	</div>
	<div class="promiseform4 hideScroll">
		<h3 class="text-center">免责声明</h3>
		<p style="line-height:30px;">本人<input type="text" style="width:100px;" id="var1" value="${userInfo.disclaimername}" />身份证号<input type="text" style="width:200px;" id="var2" value="${userInfo.disclaimerno}" />，于<input type="text" style="width:80px" id="var3" value="${userInfo.disclaimeryear}"/>年<input type="text" style="width:40px" id="var4" value="${userInfo.disclaimermonth}"/>月<input type="text" style="width:40px" id="var5" value="${userInfo.disclaimerday}" />日进入${fns:getUser().company.name}。入职体检时查出<input type="text" style="width:250px" id="var6" value="${userInfo.disclaimerdisease}" />职业病。</p>
		<p>本人声明此职业病在入职前已经存在，如需离职对于此职业病和${fns:getUser().company.name}不存在任何责任纠纷。</p>
		<p class="text-center">（无则留空不填）</p>
	</div>
	<div class="promiseform5 hideScroll">
		<h3 class="text-center">${fns:getUser().company.name}绩效合约</h3>
		<h4>声明</h4>
		<p>1、签订本绩效合同前，已与考核人进行了充分沟通，理解其对本岗位的管理意图并在此合同中体现；</p>
		<p>2、考核双方均充分了解本绩效合同的所有约定并达成共识；</p>
		<p>3、在考核人的指导和帮助下，我将认真履行本绩效合约并达成绩效指标，若未能完成考核指标，我自愿接受公司的调岗；</p>
		<p>4、我已清楚了解担任的岗位职责、工作规范及职业操守，若在在职期间出现违规现象，本人同意公司调岗和接受公司依据规章制度的规定所作出的处罚决定；</p>
		<p>5、严格遵守职业操守，不收受贿赂，否则双倍返还公司。</p>
		<p>6、因以上条款引起的调岗，本人同意变岗变薪、薪随岗变。</p>
		<p>&nbsp;</p>
		<p>1、绩效管理是${fns:getUser().company.name}实现规范化管理、贯彻落实公司核心价值，从而最终实现公司战略目标的重要手段。绩效合约是绩效管理的主要工具之一，通过层层签订绩效合约，公司层面的目标逐级分解、落实到每个岗位。依据签订的绩效合约，考核双方进行月度绩效回顾，及时发现可能存在的偏差并采取相应举措。</p>
		<p>2、考核指标的完成情况由${fns:getUser().company.name}行政人资中心、考核人共同确认。</p>
		<p>3、绩效考核指标及岗位职责请参阅职务说明书。签约责任人调动的情况须在到职后一个月内重新签订。</p>
		<p>4、被考核人对绩效合约考核结果有异议的应在3日内提出，所属事业中心行政人资经理予以协调解决。无法达成一致的，须报公司行政人资中心最终核定。逾期不提出的，视为对考核结果的确认。</p>
		<p>5、本文件绩效考核依据参照《${fns:getUser().company.name}薪酬绩效管理办法》执行。</p>
		<p>6、本合约及相关制度未尽事宜，双方协商解决。</p>
		<p>7、本合约自员工同意之日起生效。</p>
	</div>
	<div class="promiseform6 hideScroll">
		<h3 class="text-center">《员工手册》收阅确认书</h3>
		<p>声明：</p>
		<p>一、本人兹证明己收到${fns:getUser().company.name}《员工手册》一本；</p>
		<p>二、本人充分了解本人有义务遵守该手册中载明之各项准则；</p>
		<p>三、本人承认，凡属违反任何该手册要求的行为，自愿按照《${fns:getUser().company.name}奖惩制度》规定接受处罚；</p>
		<p>四、本人如对该手册所规定事项有任何疑问，将向部门负责人或行政人资部咨询；</p>
		<p>五、本人同意爱惜使用手册，并在离职时交还。</p>
	</div>
	<c:if test="${departFile!=null&&departFile!=''}">
		<div class="promiseform7 hideScroll" style="display:none;">
            <h3 class="text-center">《职务说明书》</h3>
			<iframe class="J_iframe" width="100%" height="450" name="iframe0"  src="${departFile}" frameborder="0" data-id="${ctx}/home" seamless></iframe>
		</div>
	</c:if>
	<c:if test="${performFile!=null&&performFile!=''}">
		<div class="promiseform8 hideScroll" style="display:none;">
            <h3 class="text-center">《绩效考核指标》</h3>
			<iframe class="J_iframe" width="100%" height="450" name="iframe1"  src="${performFile}" frameborder="0" data-id="${ctx}/home" seamless></iframe>
		</div>
	</c:if>
</div>



<script>
var obja;
function commonPreview(){
	 obja.prev("i").remove();
	 obja.next("i").remove();
	 obja.before('<i class="glyphicon glyphicon-ok"></i>');
	 obja.after('<i class="glyphicon glyphicon-remove-circle text-danger del"></i>');
	 obja.parent("label").addClass("checked");
}
$(function(){
	if(navigator.userAgent.match(/(iPhone|iPod|Android|ios)/i)){//如果是移动端，就使用自适应大小弹窗
		width='auto';
		height='auto';
	}else{//如果是PC端，根据用户设置的width和height显示。
		width='700px';
		height='500px';
	}
	
	
	$("#cardSelect").find("label").find("a").on("click",function(){
		obja = $(this);
		var id = obja.attr("vl");
        commonFileUpload(id);
		/*if(id == 'resumeurl'){
			// commonFinderOpen('resumeurl');
            commonFileUpload('resumeurl');
		}else{
			top.layer.open({
			    type: 2,  
			    area: [width, height],
			    title:"上传",
			    content: "${ctx}/sys/user/commonImageUploadInit" ,
			    btn: ['确定', '关闭'],
			    yes: function(index, layero){
			    	 var body = top.layer.getChildFrame('body', index);
			         var inputForm = body.find('#imgUrl');
			         if(inputForm.val()!=""){
			        	 obja.prev("i").remove();
			        	 obja.next("i").remove();
			        	 obja.before('<i class="glyphicon glyphicon-ok"></i>');
			        	 obja.after('<i class="glyphicon glyphicon-remove-circle text-danger del"></i>');
			        	 obja.parent("label").addClass("checked");
			        	 $("#"+id).val(inputForm.val());
			        	 top.layer.close(index);//关闭对话框。
			         }
			        
					 
					
				  }/!* ,
				  btn2: function(index){
					 
					  
				  },
				  cancel: function(index){ 
					 
			       } *!/
			}); 
		}*/
		
	});
	
	$("#cardSelect").delegate(".del","click",function(){
		$(this).prev().prev("i").remove();
		$(this).next("input[type=hidden]").val("");
		$(this).closest("label").removeClass("checked");
		$(this).remove();
	});
	
	$("#addLine").click(function(){
		$(this).closest("table").find("tr:last").after('<tr name="membertr" style="height:28.35pt"><td width="106" valign="center" colspan="2" style="width:79.5pt;padding:0 5.4pt 0 5.4pt;border-left:1pt solid windowtext;mso-border-left-alt:.5pt solid windowtext;border-right:1pt solid windowtext;mso-border-right-alt:.5pt solid windowtext;border-top:31.875pt none #fff;mso-border-top-alt:31.875pt none #fff;border-bottom:1pt solid windowtext;mso-border-bottom-alt:.5pt solid windowtext"><p class="MsoNormal" align="center" style="mso-pagination:widow-orphan;text-align:center"><span style="mso-spacerun:\'yes\';font-family:宋体;font-size:10pt;mso-font-kerning:0"><input type="text" name="relationship"  maxlength="10" /></span><span style="font-family:宋体;font-size:10pt;mso-font-kerning:0"><o:p></o:p></span></p></td>'
		+'<td width="75" valign="center" style="width:56.7pt;padding:0 5.4pt 0 5.4pt;border-left:31.875pt none #fff;mso-border-left-alt:31.875pt none #fff;border-right:1pt solid windowtext;mso-border-right-alt:.5pt solid windowtext;border-top:31.875pt none #fff;mso-border-top-alt:31.875pt none #fff;border-bottom:1pt solid windowtext;mso-border-bottom-alt:.5pt solid windowtext"><p class="MsoNormal" align="center" style="mso-pagination:widow-orphan;text-align:center"><span style="font-family:宋体;font-size:10pt;mso-font-kerning:0"><o:p><input type="text" name="memberfullname" maxlength="10" /></o:p></span></p></td>'
		+'<td width="76" valign="center" colspan="2" style="width:57.15pt;padding:0 5.4pt 0 5.4pt;border-left:31.875pt none #fff;mso-border-left-alt:31.875pt none #fff;border-right:1pt solid windowtext;mso-border-right-alt:.5pt solid windowtext;border-top:31.875pt none #fff;mso-border-top-alt:31.875pt none #fff;border-bottom:1pt solid windowtext;mso-border-bottom-alt:.5pt solid windowtext"><p class="MsoNormal" align="center" style="mso-pagination:widow-orphan;text-align:center"><span style="font-family:宋体;font-size:10pt;mso-font-kerning:0"><o:p><input type="text" name="memberage" maxlength="10" /></o:p></span></p></td>'
		+'<td width="149" valign="center" colspan="2" style="width:112.25pt;padding:0 5.4pt 0 5.4pt;border-left:31.875pt none #fff;mso-border-left-alt:31.875pt none #fff;border-right:1pt solid windowtext;mso-border-right-alt:.5pt solid windowtext;border-top:none;mso-border-top-alt:.5pt solid windowtext;border-bottom:1pt solid windowtext;mso-border-bottom-alt:.5pt solid windowtext"><p class="MsoNormal" align="center" style="mso-pagination:widow-orphan;text-align:center"><span style="font-family:宋体;font-size:10pt;mso-font-kerning:0"><o:p><input type="text" name="memberworkunit" maxlength="20" /></o:p></span></p></td>'
		+'<td width="97" valign="center" colspan="3" style="width:73.25pt;padding:0 5.4pt 0 5.4pt;border-left:31.875pt none #fff;mso-border-left-alt:31.875pt none #fff;border-right:1pt solid windowtext;mso-border-right-alt:.5pt solid windowtext;border-top:none;mso-border-top-alt:.5pt solid windowtext;border-bottom:1pt solid windowtext;mso-border-bottom-alt:.5pt solid windowtext"><p class="MsoNormal" align="center" style="mso-pagination:widow-orphan;text-align:center"><span style="font-family:宋体;font-size:10pt;mso-font-kerning:0"><o:p><input type="text" name="membermobile" maxlength="11" /></o:p></span></p></td>'
		+'<td width="231" valign="center" colspan="2" style="width:173.75pt;position:relative;padding:0 5.4pt 0 5.4pt;border-left:31.875pt none #fff;mso-border-left-alt:31.875pt none #fff;border-right:1pt solid windowtext;mso-border-right-alt:.5pt solid windowtext;border-top:31.875pt none #fff;mso-border-top-alt:31.875pt none #fff;border-bottom:1pt solid windowtext;mso-border-bottom-alt:.5pt solid windowtext"><p class="MsoNormal" align="center" style="mso-pagination:widow-orphan;text-align:center"><span style="font-family:宋体;font-size:10pt;mso-font-kerning:0"><o:p><input type="text" name="memberaddress" maxlength="80" /></o:p></span></p><a href="javascript:" onclick="delline(this)" class="glyphicon glyphicon-remove text-danger" style="position:absolute;right:-20px;top:10px"></a></td></tr>');
		
	}); 
});
function delline(obj){
	$(obj).closest("tr").remove();
}

function upload(){
	top.layer.open({
	    type: 2,  
	    area: [width, height],
	    title:"上传照片",
	    content: "${ctx}/sys/user/commonImageUploadInit" ,
	    btn: ['确定', '关闭'],
	    yes: function(index, layero){
	    	/*  var body = top.layer.getChildFrame('body', index);
	         var inputForm = body.find('#inputForm');
	         var top_iframe = top.getActiveTab().attr("name");//获取当前active的tab的iframe 
	         inputForm.attr("target",top_iframe);//表单提交成功后，从服务器返回的url在当前tab中展示
	         inputForm.validate();
	         if(inputForm.valid()){
	        	  loading("正在提交，请稍等...");
	        	  inputForm.submit();
	          }else{
		          return;
	          } */
	          var body = top.layer.getChildFrame('body', index);
		      var inputForm = body.find('#imgUrl');
		      if(inputForm.val() == "" || inputForm.val() == null){
		    	  $("#photoBox1").show();
		    	  $("#photoBox2").hide();
		      }else{
		    	  $("#photoBox2").show().find("img").attr("src",inputForm.val());
		    	  $("#photo").val(inputForm.val());
		    	  $("#photoBox1").hide();
		    	  top.layer.close(index);//关闭对话框。
		      }
			 
			
		  },
		  cancel: function(index){ 
	       }
	}); 
}
function commonFileUploadCallBack(id,url,fname){
    $("#"+id).val(url);
    if(url!=""){
        obja.prev("i").remove();
        obja.next("i").remove();
        obja.before('<i class="glyphicon glyphicon-ok"></i>');
        obja.after('<i class="glyphicon glyphicon-remove-circle text-danger del"></i>');
        obja.parent("label").addClass("checked");
        layer.closeAll();//关闭对话框。
    }
}
</script>


</body>
</html>