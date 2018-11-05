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
.promise_box {
    width: 800px;
    height: 300px;
    display: block;
}
.promiseform2, .promiseform3, .promiseform4, .promiseform5, .promiseform6 {
    display: block;
}
</style>
</head>
<body class="" style="tab-interval:21pt;text-justify-trim:punctuation;" >

<div class="promise_box">
	
	<div class="promiseform4 hideScroll">
		<h3 class="text-center">免责声明</h3>
		<p style="line-height:30px;">本人<input type="text" readonly="readonly" style="width:100px;" id="var1" value="${userInfo.disclaimername}" />身份证号<input type="text" readonly="readonly" style="width:200px;" id="var2" value="${userInfo.disclaimerno}" />，于<input type="text" readonly="readonly" style="width:80px" id="var3" value="${userInfo.disclaimeryear}"/>年<input type="text" readonly="readonly" style="width:40px" id="var4" value="${userInfo.disclaimermonth}"/>月<input type="text" readonly="readonly" style="width:40px" id="var5" value="${userInfo.disclaimerday}" />日进入${fns:getByOfficeId(fns:getUserById(userInfo.createBy.id).company.id).name}。入职体检时查出<input type="text" readonly="readonly" style="width:250px" id="var6" value="${userInfo.disclaimerdisease}" />职业病。</p>
		<p>本人声明此职业病在入职前已经存在，如需离职对于此职业病和${fns:getByOfficeId(fns:getUserById(userInfo.createBy.id).company.id).name}不存在任何责任纠纷。</p>
	</div>
</div>



<script>
$(function(){
	if(navigator.userAgent.match(/(iPhone|iPod|Android|ios)/i)){//如果是移动端，就使用自适应大小弹窗
		width='auto';
		height='auto';
	}else{//如果是PC端，根据用户设置的width和height显示。
		width='700px';
		height='500px';
	}
	
	
	$("#cardSelect").find("label").find("a").on("click",function(){
		var obja = $(this);
		var id = obja.attr("vl");
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
		        
				 
				
			  }/* ,
			  btn2: function(index){
				 
				  
			  },
			  cancel: function(index){ 
				 
		       } */
		}); 
	});
	
});


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

</script>


</body>
</html>