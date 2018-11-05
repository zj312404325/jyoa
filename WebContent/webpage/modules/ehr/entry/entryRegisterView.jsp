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
<input type="hidden" id="id" name="id" value="${userInfo.id}" />
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
				<td width="736" valign="center" colspan="12" style="width:552.6000pt;padding:0.0000pt 5.4000pt 0.0000pt 5.4000pt ;border-left:1.0000pt solid windowtext;mso-border-left-alt:0.5000pt solid windowtext;border-right:1.0000pt solid windowtext;mso-border-right-alt:0.5000pt solid windowtext;border-top:1.0000pt solid windowtext;mso-border-top-alt:0.5000pt solid windowtext;border-bottom:1.0000pt solid windowtext;mso-border-bottom-alt:0.5000pt solid windowtext;background:rgb(255,204,153);">
					<p class="MsoNormal" align="center" style="mso-pagination:widow-orphan;text-align:center;">
						<b><span style="mso-spacerun:'yes';font-family:宋体;font-weight:bold;font-size:14.0000pt;mso-font-kerning:0.0000pt;">基&ensp;本&ensp;情&ensp;况</span></b><b><span style="font-family:宋体;font-weight:bold;font-size:14.0000pt;mso-font-kerning:0.0000pt;"><o:p></o:p></span></b>
					</p>
				</td>
			</tr>
			<tr style="height:28.3500pt;">
				<td width="98" valign="center" style="width:73.9500pt;padding:0.0000pt 5.4000pt 0.0000pt 5.4000pt ;border-left:1.0000pt solid windowtext;mso-border-left-alt:0.5000pt solid windowtext;border-right:1.0000pt solid windowtext;mso-border-right-alt:0.5000pt solid windowtext;border-top:31.8750pt none rgb(255,255,255);mso-border-top-alt:31.8750pt none rgb(255,255,255);border-bottom:1.0000pt solid windowtext;mso-border-bottom-alt:0.5000pt solid windowtext;">
					<p class="MsoNormal" align="center" style="mso-pagination:widow-orphan;text-align:center;">
						<span style="mso-spacerun:'yes';font-family:宋体;font-size:10.0000pt;mso-font-kerning:0.0000pt;">姓&ensp;&ensp;&ensp;&ensp;名</span><span style="font-family:宋体;font-size:10.0000pt;mso-font-kerning:0.0000pt;"><o:p></o:p></span>
					</p>
				</td>
				<td width="131" valign="center" colspan="3" style="width:98.2500pt;padding:0.0000pt 5.4000pt 0.0000pt 5.4000pt ;border-left:31.8750pt none rgb(255,255,255);mso-border-left-alt:31.8750pt none rgb(255,255,255);border-right:1.0000pt solid windowtext;mso-border-right-alt:0.5000pt solid windowtext;border-top:31.8750pt none rgb(255,255,255);mso-border-top-alt:31.8750pt none rgb(255,255,255);border-bottom:1.0000pt solid windowtext;mso-border-bottom-alt:0.5000pt solid windowtext;">
					<p class="MsoNormal" align="center" style="mso-pagination:widow-orphan;text-align:center;">
						<span style="font-family:宋体;font-size:10.0000pt;mso-font-kerning:0.0000pt;"><o:p>${userInfo.fullname}</o:p></span>
					</p>
				</td>
				<td width="68" valign="center" colspan="2" style="width:51.0000pt;padding:0.0000pt 5.4000pt 0.0000pt 5.4000pt ;border-left:31.8750pt none rgb(255,255,255);mso-border-left-alt:31.8750pt none rgb(255,255,255);border-right:1.0000pt solid windowtext;mso-border-right-alt:0.5000pt solid windowtext;border-top:31.8750pt none rgb(255,255,255);mso-border-top-alt:31.8750pt none rgb(255,255,255);border-bottom:1.0000pt solid windowtext;mso-border-bottom-alt:0.5000pt solid windowtext;">
					<p class="MsoNormal" align="center" style="mso-pagination:widow-orphan;text-align:center;">
						<span style="mso-spacerun:'yes';font-family:宋体;font-size:10.0000pt;mso-font-kerning:0.0000pt;">曾&ensp;用&ensp;名</span><span style="font-family:宋体;font-size:10.0000pt;mso-font-kerning:0.0000pt;"><o:p></o:p></span>
					</p>
				</td>
				<td width="109" valign="center" style="width:82.4000pt;padding:0.0000pt 5.4000pt 0.0000pt 5.4000pt ;border-left:31.8750pt none rgb(255,255,255);mso-border-left-alt:31.8750pt none rgb(255,255,255);border-right:1.0000pt solid windowtext;mso-border-right-alt:0.5000pt solid windowtext;border-top:31.8750pt none rgb(255,255,255);mso-border-top-alt:31.8750pt none rgb(255,255,255);border-bottom:1.0000pt solid windowtext;mso-border-bottom-alt:0.5000pt solid windowtext;">
					<p class="MsoNormal" align="center" style="mso-pagination:widow-orphan;text-align:center;">
						<span style="font-family:宋体;font-size:10.0000pt;mso-font-kerning:0.0000pt;"><o:p>${userInfo.usedfullname}</o:p></span>
					</p>
				</td>
				<td width="86" valign="center" style="width:64.6000pt;padding:0.0000pt 5.4000pt 0.0000pt 5.4000pt ;border-left:31.8750pt none rgb(255,255,255);mso-border-left-alt:31.8750pt none rgb(255,255,255);border-right:1.0000pt solid windowtext;mso-border-right-alt:0.5000pt solid windowtext;border-top:31.8750pt none rgb(255,255,255);mso-border-top-alt:31.8750pt none rgb(255,255,255);border-bottom:1.0000pt solid windowtext;mso-border-bottom-alt:0.5000pt solid windowtext;">
					<p class="MsoNormal" align="center" style="mso-pagination:widow-orphan;text-align:center;">
						<span style="mso-spacerun:'yes';font-family:宋体;font-size:10.0000pt;mso-font-kerning:0.0000pt;">性&ensp;&ensp;&ensp;&ensp;别</span><span style="font-family:宋体;font-size:10.0000pt;mso-font-kerning:0.0000pt;"><o:p></o:p></span>
					</p>
				</td>
				<td width="144" valign="center" colspan="3" style="width:108.4000pt;padding:0.0000pt 5.4000pt 0.0000pt 5.4000pt ;border-left:31.8750pt none rgb(255,255,255);mso-border-left-alt:31.8750pt none rgb(255,255,255);border-right:1.0000pt solid rgb(0,0,0);mso-border-right-alt:0.5000pt solid rgb(0,0,0);border-top:1.0000pt solid windowtext;mso-border-top-alt:0.5000pt solid windowtext;border-bottom:1.0000pt solid windowtext;mso-border-bottom-alt:0.5000pt solid windowtext;">
					<p class="MsoNormal" align="center" style="mso-pagination:widow-orphan;text-align:center;">
						<span style="font-family:宋体;font-size:10.0000pt;mso-font-kerning:0.0000pt;"><o:p>${fns:getDictLabel(userInfo.sex, 'sex', '')}</o:p></span>
					</p>
				</td>
				<td width="98" valign="center" rowspan="4" style="width:74.0000pt;padding:0.0000pt 5.4000pt 0.0000pt 5.4000pt ;border-left:none;;mso-border-left-alt:none;;border-right:1.0000pt solid windowtext;mso-border-right-alt:0.5000pt solid windowtext;border-top:31.8750pt none rgb(255,255,255);mso-border-top-alt:31.8750pt none rgb(255,255,255);">
					<p class="MsoNormal" align="center" style="mso-pagination:widow-orphan;text-align:center; <c:if test="${userInfo.photo == '' || userInfo.photo == null}">display:none</c:if>" id="photoBox2">
						<img src="${userInfo.photo}" width="98" height="137" />
					</p>
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
						<span style="font-family:宋体;font-size:10.0000pt;mso-font-kerning:0.0000pt;"><o:p>${userInfo.bodyheight}</o:p></span>
					</p>
				</td>
				<td width="68" valign="center" colspan="2" style="width:51.0000pt;padding:0.0000pt 5.4000pt 0.0000pt 5.4000pt ;border-left:31.8750pt none rgb(255,255,255);mso-border-left-alt:31.8750pt none rgb(255,255,255);border-right:1.0000pt solid windowtext;mso-border-right-alt:0.5000pt solid windowtext;border-top:31.8750pt none rgb(255,255,255);mso-border-top-alt:31.8750pt none rgb(255,255,255);border-bottom:1.0000pt solid windowtext;mso-border-bottom-alt:0.5000pt solid windowtext;">
					<p class="MsoNormal" align="center" style="mso-pagination:widow-orphan;text-align:center;">
						<span style="mso-spacerun:'yes';font-family:宋体;font-size:10.0000pt;mso-font-kerning:0.0000pt;">体&ensp;&ensp;&ensp;&ensp;重</span><span style="font-family:宋体;font-size:10.0000pt;mso-font-kerning:0.0000pt;"><o:p></o:p></span>
					</p>
				</td>
				<td width="109" valign="center" style="width:82.4000pt;padding:0.0000pt 5.4000pt 0.0000pt 5.4000pt ;border-left:31.8750pt none rgb(255,255,255);mso-border-left-alt:31.8750pt none rgb(255,255,255);border-right:1.0000pt solid windowtext;mso-border-right-alt:0.5000pt solid windowtext;border-top:31.8750pt none rgb(255,255,255);mso-border-top-alt:31.8750pt none rgb(255,255,255);border-bottom:1.0000pt solid windowtext;mso-border-bottom-alt:0.5000pt solid windowtext;">
					<p class="MsoNormal" align="center" style="mso-pagination:widow-orphan;text-align:center;">
						<span style="font-family:宋体;font-size:10.0000pt;mso-font-kerning:0.0000pt;"><o:p>${userInfo.bodyweight}</o:p></span>
					</p>
				</td>
				<td width="86" valign="center" style="width:64.6000pt;padding:0.0000pt 5.4000pt 0.0000pt 5.4000pt ;border-left:31.8750pt none rgb(255,255,255);mso-border-left-alt:31.8750pt none rgb(255,255,255);border-right:1.0000pt solid windowtext;mso-border-right-alt:0.5000pt solid windowtext;border-top:31.8750pt none rgb(255,255,255);mso-border-top-alt:31.8750pt none rgb(255,255,255);border-bottom:1.0000pt solid windowtext;mso-border-bottom-alt:0.5000pt solid windowtext;">
					<p class="MsoNormal" align="center" style="mso-pagination:widow-orphan;text-align:center;">
						<span style="mso-spacerun:'yes';font-family:宋体;font-size:10.0000pt;mso-font-kerning:0.0000pt;">血&ensp;&ensp;&ensp;&ensp;型</span><span style="font-family:宋体;font-size:10.0000pt;mso-font-kerning:0.0000pt;"><o:p></o:p></span>
					</p>
				</td>
				<td width="144" valign="center" colspan="3" style="width:108.4000pt;padding:0.0000pt 5.4000pt 0.0000pt 5.4000pt ;border-left:31.8750pt none rgb(255,255,255);mso-border-left-alt:31.8750pt none rgb(255,255,255);border-right:1.0000pt solid rgb(0,0,0);mso-border-right-alt:0.5000pt solid rgb(0,0,0);border-top:none;;mso-border-top-alt:0.5000pt solid windowtext;border-bottom:1.0000pt solid windowtext;mso-border-bottom-alt:0.5000pt solid windowtext;">
					<p class="MsoNormal" align="center" style="mso-pagination:widow-orphan;text-align:center;">
						<span style="font-family:宋体;font-size:10.0000pt;mso-font-kerning:0.0000pt;"><o:p>${userInfo.blood}</o:p></span>
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
						<span style="font-family:宋体;font-size:10.0000pt;mso-font-kerning:0.0000pt;"><o:p>${userInfo.political}</o:p></span>
					</p>
				</td>
				<td width="68" valign="center" colspan="2" style="width:51.0000pt;padding:0.0000pt 5.4000pt 0.0000pt 5.4000pt ;border-left:31.8750pt none rgb(255,255,255);mso-border-left-alt:31.8750pt none rgb(255,255,255);border-right:1.0000pt solid windowtext;mso-border-right-alt:0.5000pt solid windowtext;border-top:31.8750pt none rgb(255,255,255);mso-border-top-alt:31.8750pt none rgb(255,255,255);border-bottom:1.0000pt solid windowtext;mso-border-bottom-alt:0.5000pt solid windowtext;">
					<p class="MsoNormal" align="center" style="mso-pagination:widow-orphan;text-align:center;">
						<span style="mso-spacerun:'yes';font-family:宋体;font-size:10.0000pt;mso-font-kerning:0.0000pt;">出身年月</span><span style="font-family:宋体;font-size:10.0000pt;mso-font-kerning:0.0000pt;"><o:p></o:p></span>
					</p>
				</td>
				<td width="109" valign="center" style="width:82.4000pt;padding:0.0000pt 5.4000pt 0.0000pt 5.4000pt ;border-left:31.8750pt none rgb(255,255,255);mso-border-left-alt:31.8750pt none rgb(255,255,255);border-right:1.0000pt solid windowtext;mso-border-right-alt:0.5000pt solid windowtext;border-top:31.8750pt none rgb(255,255,255);mso-border-top-alt:31.8750pt none rgb(255,255,255);border-bottom:1.0000pt solid windowtext;mso-border-bottom-alt:0.5000pt solid windowtext;">
					<p class="MsoNormal" align="center" style="mso-pagination:widow-orphan;text-align:center;">
						<span style="font-family:宋体;font-size:10.0000pt;mso-font-kerning:0.0000pt;"><o:p>${userInfo.birthday}</o:p></span>
					</p>
				</td>
				<td width="86" valign="center" style="width:64.6000pt;padding:0.0000pt 5.4000pt 0.0000pt 5.4000pt ;border-left:31.8750pt none rgb(255,255,255);mso-border-left-alt:31.8750pt none rgb(255,255,255);border-right:1.0000pt solid windowtext;mso-border-right-alt:0.5000pt solid windowtext;border-top:31.8750pt none rgb(255,255,255);mso-border-top-alt:31.8750pt none rgb(255,255,255);border-bottom:1.0000pt solid windowtext;mso-border-bottom-alt:0.5000pt solid windowtext;">
					<p class="MsoNormal" align="center" style="mso-pagination:widow-orphan;text-align:center;">
						<span style="mso-spacerun:'yes';font-family:宋体;font-size:10.0000pt;mso-font-kerning:0.0000pt;">身份证号</span><span style="font-family:宋体;font-size:10.0000pt;mso-font-kerning:0.0000pt;"><o:p></o:p></span>
					</p>
				</td>
				<td width="144" valign="center" colspan="3" style="width:108.4000pt;padding:0.0000pt 5.4000pt 0.0000pt 5.4000pt ;border-left:31.8750pt none rgb(255,255,255);mso-border-left-alt:31.8750pt none rgb(255,255,255);border-right:1.0000pt solid rgb(0,0,0);mso-border-right-alt:0.5000pt solid rgb(0,0,0);border-top:none;;mso-border-top-alt:0.5000pt solid windowtext;border-bottom:1.0000pt solid windowtext;mso-border-bottom-alt:0.5000pt solid windowtext;">
					<p class="MsoNormal" align="center" style="mso-pagination:widow-orphan;text-align:center;">
						<span style="font-family:宋体;font-size:10.0000pt;mso-font-kerning:0.0000pt;"><o:p>${userInfo.idcardno}</o:p></span>
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
						<span style="font-family:宋体;font-size:10.0000pt;mso-font-kerning:0.0000pt;"><o:p>${userInfo.origin}</o:p></span>
					</p>
				</td>
				<td width="68" valign="center" colspan="2" style="width:51.0000pt;padding:0.0000pt 5.4000pt 0.0000pt 5.4000pt ;border-left:31.8750pt none rgb(255,255,255);mso-border-left-alt:31.8750pt none rgb(255,255,255);border-right:1.0000pt solid windowtext;mso-border-right-alt:0.5000pt solid windowtext;border-top:31.8750pt none rgb(255,255,255);mso-border-top-alt:31.8750pt none rgb(255,255,255);border-bottom:1.0000pt solid windowtext;mso-border-bottom-alt:0.5000pt solid windowtext;">
					<p class="MsoNormal" align="center" style="mso-pagination:widow-orphan;text-align:center;">
						<span style="mso-spacerun:'yes';font-family:宋体;font-size:10.0000pt;mso-font-kerning:0.0000pt;">民&ensp;&ensp;&ensp;&ensp;族</span><span style="font-family:宋体;font-size:10.0000pt;mso-font-kerning:0.0000pt;"><o:p></o:p></span>
					</p>
				</td>
				<td width="109" valign="center" style="width:82.4000pt;padding:0.0000pt 5.4000pt 0.0000pt 5.4000pt ;border-left:31.8750pt none rgb(255,255,255);mso-border-left-alt:31.8750pt none rgb(255,255,255);border-right:31.8750pt none rgb(255,255,255);mso-border-right-alt:31.8750pt none rgb(255,255,255);border-top:31.8750pt none rgb(255,255,255);mso-border-top-alt:31.8750pt none rgb(255,255,255);border-bottom:1.0000pt solid windowtext;mso-border-bottom-alt:0.5000pt solid windowtext;">
					<p class="MsoNormal" align="center" style="mso-pagination:widow-orphan;text-align:center;">
						<span style="font-family:宋体;font-size:10.0000pt;mso-font-kerning:0.0000pt;"><o:p>${userInfo.nation}</o:p></span>
					</p>
				</td>
				<td width="86" valign="center" style="width:64.6000pt;padding:0.0000pt 5.4000pt 0.0000pt 5.4000pt ;border-left:0.5000pt solid windowtext;mso-border-left-alt:0.5000pt solid windowtext;border-right:1.0000pt solid windowtext;mso-border-right-alt:0.5000pt solid windowtext;border-top:31.8750pt none rgb(255,255,255);mso-border-top-alt:31.8750pt none rgb(255,255,255);border-bottom:1.0000pt solid windowtext;mso-border-bottom-alt:0.5000pt solid windowtext;">
					<p class="MsoNormal" align="center" style="mso-pagination:widow-orphan;text-align:center;">
						<span style="mso-spacerun:'yes';font-family:宋体;font-size:10.0000pt;mso-font-kerning:0.0000pt;">宗教信仰</span><span style="font-family:宋体;font-size:10.0000pt;mso-font-kerning:0.0000pt;"><o:p></o:p></span>
					</p>
				</td>
				<td width="144" valign="center" colspan="3" style="width:108.4000pt;padding:0.0000pt 5.4000pt 0.0000pt 5.4000pt ;border-left:31.8750pt none rgb(255,255,255);mso-border-left-alt:31.8750pt none rgb(255,255,255);border-right:1.0000pt solid rgb(0,0,0);mso-border-right-alt:0.5000pt solid rgb(0,0,0);border-top:none;;mso-border-top-alt:0.5000pt solid windowtext;border-bottom:1.0000pt solid windowtext;mso-border-bottom-alt:0.5000pt solid windowtext;">
					<p class="MsoNormal" align="center" style="mso-pagination:widow-orphan;text-align:center;">
						<span style="font-family:宋体;font-size:10.0000pt;mso-font-kerning:0.0000pt;"><o:p>${userInfo.religion}</o:p></span>
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
						<span style="font-family:宋体;font-size:10.0000pt;mso-font-kerning:0.0000pt;"><o:p>${userInfo.phone}</o:p></span>
					</p>
				</td>
				<td width="68" valign="center" colspan="2" style="width:51.0000pt;padding:0.0000pt 5.4000pt 0.0000pt 5.4000pt ;border-left:31.8750pt none rgb(255,255,255);mso-border-left-alt:31.8750pt none rgb(255,255,255);border-right:1.0000pt solid windowtext;mso-border-right-alt:0.5000pt solid windowtext;border-top:31.8750pt none rgb(255,255,255);mso-border-top-alt:31.8750pt none rgb(255,255,255);border-bottom:1.0000pt solid windowtext;mso-border-bottom-alt:0.5000pt solid windowtext;">
					<p class="MsoNormal" align="center" style="mso-pagination:widow-orphan;text-align:center;">
						<span style="mso-spacerun:'yes';font-family:宋体;font-size:10.0000pt;mso-font-kerning:0.0000pt;">移动电话</span><span style="font-family:宋体;font-size:10.0000pt;mso-font-kerning:0.0000pt;"><o:p></o:p></span>
					</p>
				</td>
				<td width="109" valign="center" style="width:82.4000pt;padding:0.0000pt 5.4000pt 0.0000pt 5.4000pt ;border-left:31.8750pt none rgb(255,255,255);mso-border-left-alt:31.8750pt none rgb(255,255,255);border-right:1.0000pt solid windowtext;mso-border-right-alt:0.5000pt solid windowtext;border-top:31.8750pt none rgb(255,255,255);mso-border-top-alt:31.8750pt none rgb(255,255,255);border-bottom:1.0000pt solid windowtext;mso-border-bottom-alt:0.5000pt solid windowtext;">
					<p class="MsoNormal" align="center" style="mso-pagination:widow-orphan;text-align:center;">
						<span style="font-family:宋体;font-size:10.0000pt;mso-font-kerning:0.0000pt;"><o:p>${userInfo.mobile}</o:p></span>
					</p>
				</td>
				<td width="86" valign="center" style="width:64.6000pt;padding:0.0000pt 5.4000pt 0.0000pt 5.4000pt ;border-left:31.8750pt none rgb(255,255,255);mso-border-left-alt:31.8750pt none rgb(255,255,255);border-right:1.0000pt solid windowtext;mso-border-right-alt:0.5000pt solid windowtext;border-top:31.8750pt none rgb(255,255,255);mso-border-top-alt:31.8750pt none rgb(255,255,255);border-bottom:1.0000pt solid windowtext;mso-border-bottom-alt:0.5000pt solid windowtext;">
					<p class="MsoNormal" align="center" style="mso-pagination:widow-orphan;text-align:center;">
						<span style="mso-spacerun:'yes';font-family:宋体;font-size:10.0000pt;mso-font-kerning:0.0000pt;">电子邮箱</span><span style="font-family:宋体;font-size:10.0000pt;mso-font-kerning:0.0000pt;"><o:p></o:p></span>
					</p>
				</td>
				<td width="243" valign="center" colspan="4" style="width:182.4000pt;padding:0.0000pt 5.4000pt 0.0000pt 5.4000pt ;border-left:31.8750pt none rgb(255,255,255);mso-border-left-alt:31.8750pt none rgb(255,255,255);border-right:1.0000pt solid windowtext;mso-border-right-alt:0.5000pt solid windowtext;border-top:none;;mso-border-top-alt:0.5000pt solid windowtext;border-bottom:1.0000pt solid windowtext;mso-border-bottom-alt:0.5000pt solid windowtext;">
					<p class="MsoNormal" align="center" style="text-align:center;">
						<span style="font-family:宋体;font-size:10.0000pt;mso-font-kerning:0.0000pt;"><o:p>${userInfo.email}</o:p></span>
					</p>
				</td>
			</tr>
			<tr style="height:28.3500pt;">
				<td width="98" valign="center" style="width:73.9500pt;padding:0.0000pt 5.4000pt 0.0000pt 5.4000pt ;border-left:1.0000pt solid windowtext;mso-border-left-alt:0.5000pt solid windowtext;border-right:1.0000pt solid windowtext;mso-border-right-alt:0.5000pt solid windowtext;border-top:none;;mso-border-top-alt:0.5000pt solid windowtext;border-bottom:1.0000pt solid windowtext;mso-border-bottom-alt:0.5000pt solid windowtext;">
					<p class="MsoNormal" align="center" style="mso-pagination:widow-orphan;text-align:center;">
						<span style="mso-spacerun:'yes';font-family:宋体;font-size:10.0000pt;mso-font-kerning:0.0000pt;">最高学历</span><span style="font-family:宋体;font-size:10.0000pt;mso-font-kerning:0.0000pt;"><o:p></o:p></span>
					</p>
					<p class="MsoNormal" align="center" style="mso-pagination:widow-orphan;text-align:center;">
						<span style="mso-spacerun:'yes';font-family:宋体;font-size:10.0000pt;mso-font-kerning:0.0000pt;">（全日制）</span><span style="font-family:宋体;font-size:10.0000pt;mso-font-kerning:0.0000pt;"><o:p></o:p></span>
					</p>
				</td>
				<td width="131" valign="center" colspan="3" style="width:98.2500pt;padding:0.0000pt 5.4000pt 0.0000pt 5.4000pt ;border-left:none;;mso-border-left-alt:none;;border-right:1.0000pt solid windowtext;mso-border-right-alt:0.5000pt solid windowtext;border-top:none;;mso-border-top-alt:0.5000pt solid windowtext;border-bottom:1.0000pt solid windowtext;mso-border-bottom-alt:0.5000pt solid windowtext;">
					<p class="MsoNormal" align="center" style="text-align:center;">
						<span style="font-family:宋体;font-size:10.0000pt;mso-font-kerning:0.0000pt;"><o:p>${fns:getDictLabel(userInfo.degree, 'degree', '')}</o:p></span>
					</p>
				</td>
				<td width="68" valign="center" colspan="2" style="width:51.0000pt;padding:0.0000pt 5.4000pt 0.0000pt 5.4000pt ;border-left:none;;mso-border-left-alt:none;;border-right:1.0000pt solid windowtext;mso-border-right-alt:0.5000pt solid windowtext;border-top:none;;mso-border-top-alt:0.5000pt solid windowtext;border-bottom:1.0000pt solid windowtext;mso-border-bottom-alt:0.5000pt solid windowtext;">
					<p class="MsoNormal" align="center" style="text-align:center;">
						<span style="mso-spacerun:'yes';font-family:宋体;font-size:10.0000pt;mso-font-kerning:0.0000pt;">专&ensp;&ensp;&ensp;&ensp;业</span><span style="font-family:宋体;font-size:10.0000pt;mso-font-kerning:0.0000pt;"><o:p></o:p></span>
					</p>
				</td>
				<td width="109" valign="center" style="width:82.4000pt;padding:0.0000pt 5.4000pt 0.0000pt 5.4000pt ;border-left:none;;mso-border-left-alt:none;;border-right:1.0000pt solid windowtext;mso-border-right-alt:0.5000pt solid windowtext;border-top:none;;mso-border-top-alt:0.5000pt solid windowtext;border-bottom:1.0000pt solid windowtext;mso-border-bottom-alt:0.5000pt solid windowtext;">
					<p class="MsoNormal" align="center" style="text-align:center;">
						<span style="font-family:宋体;font-size:10.0000pt;mso-font-kerning:0.0000pt;"><o:p>${userInfo.profession}</o:p></span>
					</p>
				</td>
				<td width="86" valign="center" style="width:64.6000pt;padding:0.0000pt 5.4000pt 0.0000pt 5.4000pt ;border-left:none;;mso-border-left-alt:none;;border-right:1.0000pt solid windowtext;mso-border-right-alt:0.5000pt solid windowtext;border-top:none;;mso-border-top-alt:0.5000pt solid windowtext;border-bottom:1.0000pt solid windowtext;mso-border-bottom-alt:0.5000pt solid windowtext;">
					<p class="MsoNormal" align="center" style="text-align:center;">
						<span style="mso-spacerun:'yes';font-family:宋体;font-size:10.0000pt;mso-font-kerning:0.0000pt;">毕业院校</span><span style="font-family:宋体;font-size:10.0000pt;mso-font-kerning:0.0000pt;"><o:p></o:p></span>
					</p>
				</td>
				<td width="243" valign="center" colspan="4" style="width:182.4000pt;padding:0.0000pt 5.4000pt 0.0000pt 5.4000pt ;border-left:none;;mso-border-left-alt:none;;border-right:1.0000pt solid windowtext;mso-border-right-alt:0.5000pt solid windowtext;border-top:none;;mso-border-top-alt:0.5000pt solid windowtext;border-bottom:1.0000pt solid windowtext;mso-border-bottom-alt:0.5000pt solid windowtext;">
					<p class="MsoNormal" align="center" style="mso-char-indent-count:4.9000;text-align:center;">
						<span style="font-family:宋体;font-size:10.0000pt;mso-font-kerning:0.0000pt;"><o:p>${userInfo.college}</o:p></span>
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
						<span style="font-family:宋体;font-size:10.0000pt;mso-font-kerning:0.0000pt;"><o:p>${userInfo.languages}</o:p></span>
					</p>
				</td>
			</tr>
			<tr style="height:28.3500pt;">
				<td width="98" valign="center" style="width:73.9500pt;padding:0.0000pt 5.4000pt 0.0000pt 5.4000pt ;border-left:1.0000pt solid windowtext;mso-border-left-alt:0.5000pt solid windowtext;border-right:1.0000pt solid windowtext;mso-border-right-alt:0.5000pt solid windowtext;border-top:none;;mso-border-top-alt:0.5000pt solid windowtext;border-bottom:1.0000pt solid windowtext;mso-border-bottom-alt:0.5000pt solid windowtext;">
					<p class="MsoNormal" align="center" style="mso-pagination:widow-orphan;text-align:center;">
						<span style="mso-spacerun:'yes';font-family:宋体;font-size:10.0000pt;mso-font-kerning:0.0000pt;">婚姻状况</span><span style="font-family:宋体;font-size:10.0000pt;mso-font-kerning:0.0000pt;"><o:p></o:p></span>
					</p>
				</td>
				<td width="308" valign="center" colspan="6" style="width:231.6500pt;padding:0.0000pt 5.4000pt 0.0000pt 5.4000pt ;border-left:none;;mso-border-left-alt:none;;border-right:1.0000pt solid windowtext;mso-border-right-alt:0.5000pt solid windowtext;border-top:none;;mso-border-top-alt:0.5000pt solid windowtext;border-bottom:1.0000pt solid windowtext;mso-border-bottom-alt:0.5000pt solid windowtext;">
					<p class="MsoNormal" align="center" style="mso-char-indent-count:3.0000;text-align:center;">
						<span style="font-family:宋体;font-size:10.0000pt;mso-font-kerning:0.0000pt;"><o:p>${userInfo.marriage}</o:p></span>
					</p>
				</td>
				<td width="97" valign="center" colspan="3" style="width:73.2500pt;padding:0.0000pt 5.4000pt 0.0000pt 5.4000pt ;border-left:none;;mso-border-left-alt:none;;border-right:1.0000pt solid windowtext;mso-border-right-alt:0.5000pt solid windowtext;border-top:1.0000pt solid windowtext;mso-border-top-alt:0.5000pt solid windowtext;border-bottom:1.0000pt solid windowtext;mso-border-bottom-alt:0.5000pt solid windowtext;">
					<p class="MsoNormal" align="center" style="text-align:center;">
						<span style="mso-spacerun:'yes';font-family:宋体;font-size:10.0000pt;mso-font-kerning:0.0000pt;">有无病史</span><span style="font-family:宋体;font-size:10.0000pt;mso-font-kerning:0.0000pt;"><o:p></o:p></span>
					</p>
				</td>
				<td width="231" valign="center" colspan="2" style="width:173.7500pt;padding:0.0000pt 5.4000pt 0.0000pt 5.4000pt ;border-left:none;;mso-border-left-alt:none;;border-right:1.0000pt solid windowtext;mso-border-right-alt:0.5000pt solid windowtext;border-top:1.0000pt solid windowtext;mso-border-top-alt:0.5000pt solid windowtext;border-bottom:1.0000pt solid windowtext;mso-border-bottom-alt:0.5000pt solid windowtext;">
					<p class="MsoNormal" align="center" style="text-align:center;">
						<span style="font-family:宋体;font-size:10.0000pt;mso-font-kerning:0.0000pt;"><o:p>${userInfo.disease}</o:p></span>
					</p>
				</td>
			</tr>
			<%--<tr style="height:28.3500pt;">
				<td width="98" valign="center" style="width:73.9500pt;padding:0.0000pt 5.4000pt 0.0000pt 5.4000pt ;border-left:1.0000pt solid windowtext;mso-border-left-alt:0.5000pt solid windowtext;border-right:1.0000pt solid windowtext;mso-border-right-alt:0.5000pt solid windowtext;border-top:none;;mso-border-top-alt:0.5000pt solid windowtext;border-bottom:1.0000pt solid windowtext;mso-border-bottom-alt:0.5000pt solid windowtext;">
					<p class="MsoNormal" align="center" style="mso-pagination:widow-orphan;text-align:center;">
						<span style="mso-spacerun:'yes';font-family:宋体;font-size:10.0000pt;mso-font-kerning:0.0000pt;">目前劳动关系</span><span style="font-family:宋体;font-size:10.0000pt;mso-font-kerning:0.0000pt;"><o:p></o:p></span>
					</p>
				</td>
				<td width="638" valign="center" colspan="11" style="width:478.6500pt;padding:0.0000pt 5.4000pt 0.0000pt 5.4000pt ;border-left:none;;mso-border-left-alt:none;;border-right:1.0000pt solid windowtext;mso-border-right-alt:0.5000pt solid windowtext;border-top:none;;mso-border-top-alt:0.5000pt solid windowtext;border-bottom:1.0000pt solid windowtext;mso-border-bottom-alt:0.5000pt solid windowtext;">
					<p class="MsoNormal" style="mso-char-indent-count:1.5000;">
						<span style="font-family:宋体;font-size:10.0000pt;mso-font-kerning:0.0000pt;"><o:p>${userInfo.workstatus}</o:p></span>
					</p>
				</td>
			</tr>--%>
			<tr style="height:28.3500pt;">
				<td width="98" valign="center" style="width:73.9500pt;padding:0.0000pt 5.4000pt 0.0000pt 5.4000pt ;border-left:1.0000pt solid windowtext;mso-border-left-alt:0.5000pt solid windowtext;border-right:1.0000pt solid windowtext;mso-border-right-alt:0.5000pt solid windowtext;border-top:none;;mso-border-top-alt:0.5000pt solid windowtext;border-bottom:1.0000pt solid windowtext;mso-border-bottom-alt:0.5000pt solid windowtext;">
					<p class="MsoNormal"  style="mso-pagination:widow-orphan;">
						<span style="mso-spacerun:'yes';font-family:宋体;font-size:10.0000pt;mso-font-kerning:0.0000pt;">是否有亲友在本公司工作</span><span style="font-family:宋体;font-size:10.0000pt;mso-font-kerning:0.0000pt;"><o:p></o:p></span>
					</p>
				</td>
				<td width="308" valign="center" colspan="6" style="width:231.6500pt;padding:0.0000pt 5.4000pt 0.0000pt 5.4000pt ;border-left:none;;mso-border-left-alt:none;;border-right:1.0000pt solid windowtext;mso-border-right-alt:0.5000pt solid windowtext;border-top:none;;mso-border-top-alt:0.5000pt solid windowtext;border-bottom:1.0000pt solid windowtext;mso-border-bottom-alt:0.5000pt solid windowtext;">
					<p class="MsoNormal" align="center" style="mso-char-indent-count:3.0000;text-align:center;">
						<span style="font-family:宋体;font-size:10.0000pt;mso-font-kerning:0.0000pt;"><o:p>${userInfo.isfamily}</o:p></span>
					</p>
				</td>
				<td width="97" valign="center" colspan="2" style="width:72.7500pt;padding:0.0000pt 5.4000pt 0.0000pt 5.4000pt ;border-left:none;;mso-border-left-alt:none;;border-right:1.0000pt solid windowtext;mso-border-right-alt:0.5000pt solid windowtext;border-top:1.0000pt solid windowtext;mso-border-top-alt:0.5000pt solid windowtext;border-bottom:1.0000pt solid windowtext;mso-border-bottom-alt:0.5000pt solid windowtext;">
					<p class="MsoNormal" style="text-align:center;">
						<span style="mso-spacerun:'yes';font-family:宋体;font-size:10.0000pt;mso-font-kerning:0.0000pt;">是否有签署竞业禁止协议</span><span style="font-family:宋体;font-size:10.0000pt;mso-font-kerning:0.0000pt;"><o:p></o:p></span>
					</p>
				</td>
				<td width="232" valign="center" colspan="3" style="width:174.2500pt;padding:0.0000pt 5.4000pt 0.0000pt 5.4000pt ;border-left:none;;mso-border-left-alt:none;;border-right:1.0000pt solid windowtext;mso-border-right-alt:0.5000pt solid windowtext;border-top:1.0000pt solid windowtext;mso-border-top-alt:0.5000pt solid windowtext;border-bottom:1.0000pt solid windowtext;mso-border-bottom-alt:0.5000pt solid windowtext;">
					<p class="MsoNormal" align="center" style="mso-char-indent-count:3.0000;text-align:center;">
						<span style="font-family:宋体;font-size:10.0000pt;mso-font-kerning:0.0000pt;"><o:p>${userInfo.iscompetition}</o:p></span>
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
							<label class="checkbox-inline">
								<c:if test="${userInfo.iscardurl != '' && userInfo.iscardurl != null}">
								        <a href="javascript:" vl="${userInfo.iscardurl}" onclick="commonFileDownLoad(this)" ><i class="glyphicon glyphicon-save"></i>身份证</a>
								</c:if>
							</label><label class="checkbox-inline ">
								<c:if test="${userInfo.certificateurl != '' && userInfo.certificateurl != null}">
								  <a href="javascript:" vl="${userInfo.certificateurl}" onclick="commonFileDownLoad(this)" ><i class="glyphicon glyphicon-save"></i>毕业证</a>  
								</c:if>
							</label>
							   <c:if test="${userInfo.degreecertificateurl != '' && userInfo.degreecertificateurl != null}">
								   <label class="checkbox-inline ">
								<a href="javascript:" vl="${userInfo.degreecertificateurl}" onclick="commonFileDownLoad(this)" ><i class="glyphicon glyphicon-save"></i>学位证</a>
									   </label>
								</c:if>
							<label class="checkbox-inline ">
							  <c:if test="${userInfo.leavingcertificate != '' && userInfo.leavingcertificate != null}">
								<a href="javascript:" vl="${userInfo.leavingcertificate}" onclick="commonFileDownLoad(this)" ><i class="glyphicon glyphicon-save"></i>离职证明</a>
								</c:if>
							</label><label class="checkbox-inline ">
								<c:if test="${userInfo.resumeurl != '' && userInfo.resumeurl != null}">
								<a href="javascript:" vl="${userInfo.resumeurl}" onclick="commonFileDownLoad(this)" ><i class="glyphicon glyphicon-save"></i>个人简历</a>
								</c:if>
							</label>
							<c:if test="${userInfo.driverlicenseurl != '' && userInfo.driverlicenseurl != null}">
								<label class="checkbox-inline ">
									<a href="javascript:" vl="${userInfo.driverlicenseurl}" onclick="commonFileDownLoad(this)" ><i class="glyphicon glyphicon-save"></i>驾照</a>
								</label></c:if>
							<c:if test="${userInfo.qualificationcertificateurl != '' && userInfo.qualificationcertificateurl != null}">
							<label class="checkbox-inline">
								<a href="javascript:" vl="${userInfo.qualificationcertificateurl}" onclick="commonFileDownLoad(this)" ><i class="glyphicon glyphicon-save"></i>资格证</a>
							</label>
							</c:if>
							<label class="checkbox-inline">
								<c:if test="${userInfo.bankcardurl != '' && userInfo.bankcardurl != null}">
									<a href="javascript:" vl="${userInfo.bankcardurl}" onclick="commonFileDownLoad(this)" ><i class="glyphicon glyphicon-save"></i>银行卡</a>
								</c:if>
							</label><label class="checkbox-inline">
								<c:if test="${userInfo.healthurl != '' && userInfo.healthurl != null}">
									<a href="javascript:" vl="${userInfo.healthurl}" onclick="commonFileDownLoad(this)" ><i class="glyphicon glyphicon-save"></i>体检证明</a>
								</c:if>
							</label>
							
						</span>
					</p>
				</td>
			</tr>
			<tr style="height:28.3500pt;">
				<td width="98" valign="center" style="width:73.9500pt;padding:0.0000pt 5.4000pt 0.0000pt 5.4000pt ;border-left:1.0000pt solid windowtext;mso-border-left-alt:0.5000pt solid windowtext;border-right:1.0000pt solid windowtext;mso-border-right-alt:0.5000pt solid windowtext;border-top:none;;mso-border-top-alt:0.5000pt solid windowtext;border-bottom:1.0000pt solid windowtext;mso-border-bottom-alt:0.5000pt solid windowtext;">
					<p class="MsoNormal" align="center" style="mso-pagination:widow-orphan;text-align:center;">
						<span style="mso-spacerun:'yes';font-family:宋体;font-size:10.0000pt;mso-font-kerning:0.0000pt;">现居住地</span><span style="font-family:宋体;font-size:10.0000pt;mso-font-kerning:0.0000pt;"><o:p></o:p></span>
					</p>
				</td>
				<td width="638" valign="left" colspan="11" style="width:478.6500pt;padding:0.0000pt 5.4000pt 0.0000pt 5.4000pt ;border-left:none;;mso-border-left-alt:none;;border-right:1.0000pt solid windowtext;mso-border-right-alt:0.5000pt solid windowtext;border-top:none;;mso-border-top-alt:0.5000pt solid windowtext;border-bottom:1.0000pt solid windowtext;mso-border-bottom-alt:0.5000pt solid windowtext;">
					<p class="MsoNormal" align="left" style="text-align:left">
						<span style="font-family:宋体;font-size:10.0000pt;mso-font-kerning:0.0000pt;"><o:p>${userInfo.address}</o:p></span>
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
				</td>
			</tr>
			<c:if test="${userInfo != null && fn:length(userInfo.userMemberList) != 0}">
			<c:forEach items="${userInfo.userMemberList}" var="info">
			    <tr name="membertr" style="height:28.35pt"><td width="106" valign="center" colspan="2" style="width:79.5pt;padding:0 5.4pt 0 5.4pt;border-left:1pt solid windowtext;mso-border-left-alt:.5pt solid windowtext;border-right:1pt solid windowtext;mso-border-right-alt:.5pt solid windowtext;border-top:31.875pt none #fff;mso-border-top-alt:31.875pt none #fff;border-bottom:1pt solid windowtext;mso-border-bottom-alt:.5pt solid windowtext"><p class="MsoNormal" align="center" style="mso-pagination:widow-orphan;text-align:center"><span style="mso-spacerun:\yes\;font-family:宋体;font-size:10pt;mso-font-kerning:0">${info.relationship}</span><span style="font-family:宋体;font-size:10pt;mso-font-kerning:0"><o:p></o:p></span></p></td>
				<td width="75" valign="center" style="width:56.7pt;padding:0 5.4pt 0 5.4pt;border-left:31.875pt none #fff;mso-border-left-alt:31.875pt none #fff;border-right:1pt solid windowtext;mso-border-right-alt:.5pt solid windowtext;border-top:31.875pt none #fff;mso-border-top-alt:31.875pt none #fff;border-bottom:1pt solid windowtext;mso-border-bottom-alt:.5pt solid windowtext"><p class="MsoNormal" align="center" style="mso-pagination:widow-orphan;text-align:center"><span style="font-family:宋体;font-size:10pt;mso-font-kerning:0"><o:p>${info.memberfullname}</o:p></span></p></td>
				<td width="76" valign="center" colspan="2" style="width:57.15pt;padding:0 5.4pt 0 5.4pt;border-left:31.875pt none #fff;mso-border-left-alt:31.875pt none #fff;border-right:1pt solid windowtext;mso-border-right-alt:.5pt solid windowtext;border-top:31.875pt none #fff;mso-border-top-alt:31.875pt none #fff;border-bottom:1pt solid windowtext;mso-border-bottom-alt:.5pt solid windowtext"><p class="MsoNormal" align="center" style="mso-pagination:widow-orphan;text-align:center"><span style="font-family:宋体;font-size:10pt;mso-font-kerning:0"><o:p>${info.memberage}</o:p></span></p></td>
				<td width="149" valign="center" colspan="2" style="width:112.25pt;padding:0 5.4pt 0 5.4pt;border-left:31.875pt none #fff;mso-border-left-alt:31.875pt none #fff;border-right:1pt solid windowtext;mso-border-right-alt:.5pt solid windowtext;border-top:none;mso-border-top-alt:.5pt solid windowtext;border-bottom:1pt solid windowtext;mso-border-bottom-alt:.5pt solid windowtext"><p class="MsoNormal" align="center" style="mso-pagination:widow-orphan;text-align:center"><span style="font-family:宋体;font-size:10pt;mso-font-kerning:0"><o:p>${info.memberworkunit}</o:p></span></p></td>
				<td width="97" valign="center" colspan="3" style="width:73.25pt;padding:0 5.4pt 0 5.4pt;border-left:31.875pt none #fff;mso-border-left-alt:31.875pt none #fff;border-right:1pt solid windowtext;mso-border-right-alt:.5pt solid windowtext;border-top:none;mso-border-top-alt:.5pt solid windowtext;border-bottom:1pt solid windowtext;mso-border-bottom-alt:.5pt solid windowtext"><p class="MsoNormal" align="center" style="mso-pagination:widow-orphan;text-align:center"><span style="font-family:宋体;font-size:10pt;mso-font-kerning:0"><o:p>${info.membermobile}</o:p></span></p></td>
				<td width="231" valign="center" colspan="2" style="width:173.75pt;position:relative;padding:0 5.4pt 0 5.4pt;border-left:31.875pt none #fff;mso-border-left-alt:31.875pt none #fff;border-right:1pt solid windowtext;mso-border-right-alt:.5pt solid windowtext;border-top:31.875pt none #fff;mso-border-top-alt:31.875pt none #fff;border-bottom:1pt solid windowtext;mso-border-bottom-alt:.5pt solid windowtext"><p class="MsoNormal" align="center" style="mso-pagination:widow-orphan;text-align:center"><span style="font-family:宋体;font-size:10pt;mso-font-kerning:0"><o:p>${info.memberaddress}</o:p></span></p></td></tr>
			</c:forEach>
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
				<span style="mso-spacerun:'yes';font-family:宋体;font-size:10.5000pt;mso-fopositionnt-kerning:1.0000pt;"><font face="宋体">岗位：</font><input type="text" name="position" id="position" value="${userInfo.position}" class="footer_input" /></span><span style="mso-spacerun:'yes';font-family:宋体;font-size:10.5000pt;mso-font-kerning:1.0000pt;">&ensp;</span>
				<span style="mso-spacerun:'yes';font-family:宋体;font-size:10.5000pt;mso-font-kerning:1.0000pt;">&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;<font face="宋体">试用期：</font><input type="text" name="probationperiod" id="probationperiod" value="${userInfo.probationperiod}" class="footer_input" />&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;<font face="宋体">试用期工资：</font><font face="Times New Roman"><input type="text" name="probationperiodsalary" id="probationperiodsalary" value="${userInfo.probationperiodsalary}" class="footer_input" /></font></span><span style="mso-spacerun:'yes';font-family:宋体;font-size:10.5000pt;mso-font-kerning:1.0000pt;">&ensp;&ensp;&ensp;</span><span style="mso-spacerun:'yes';font-family:宋体;font-size:10.5000pt;mso-font-kerning:1.0000pt;">&ensp;</span><span style="mso-spacerun:'yes';font-family:宋体;font-size:10.5000pt;mso-font-kerning:1.0000pt;">&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;</span><span style="mso-spacerun:'yes';font-family:宋体;font-size:5.0000pt;mso-font-kerning:1.0000pt;"><o:p></o:p></span>
			</p>
		</div>
		
		<!-- <div class="row" style="padding-top:30px; padding-left:330px;">
			<a href="javascript:" class="btn btn-primary" id="sub" >审核并确定</a>
		</div> -->
		<!--EndFragment-->

	
	
	
	
	
	
	
	

	</div>
	</div>
</div>
</form:form>

<script>
$(function(){
	if(navigator.userAgent.match(/(iPhone|iPod|Android|ios)/i)){//如果是移动端，就使用自适应大小弹窗
		width='auto';
		height='auto';
	}else{//如果是PC端，根据用户设置的width和height显示。
		width='700px';
		height='500px';
	}
	
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
		      }
			 top.layer.close(index);//关闭对话框。
			
		  },
		  cancel: function(index){ 
	       }
	}); 
}

</script>


</body>
</html>