<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script language="javascript" type="text/javascript" src="/static/jquery/jquery-2.1.1.min.js" charset="utf-8"></script>
<script language="javascript" type="text/javascript" src="/static/jquery/jquery.form.js" charset="utf-8"></script>
<%@ include file="/webpage/include/taglib.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
      <link href="${ctxStatic}/bootstrap/3.3.4/css_default/bootstrap.min.css" type="text/css" rel="stylesheet" />
      <script src="${ctxStatic}/bootstrap/3.3.4/js/bootstrap.min.js"  type="text/javascript"></script>
      <link href="${ctxStatic}/awesome/4.4/css/font-awesome.min.css" rel="stylesheet" />
      <link href="${ctxStatic}/common/css/style.css?v=3.2.0" type="text/css" rel="stylesheet" />
	<script>
    function getPhotoSize(obj) {
        var photoExt = obj.value.substr(obj.value.lastIndexOf(".")).toLowerCase();//获得文件后缀名
        if (photoExt != '.pdf') {
            alert("请上传pdf文件!");
            obj.value = "";
        }
    }
    //判断文件是否存在
    function validFileUrlExist() {
        var fileurl=$("#fileUploade").val();
        if(fileurl==''){
            return false;
        }
        return true;
    }
	$(function(){
		$("#up").click(function(e){
            e.preventDefault();
            if(!validFileUrlExist()){
                alert("请上传文件！");
                return;
            }
            /* var data = $("#fff").submit();
            var json = jQuery.parseJSON(data);
            console.log(data); */

			var form = $("form[name=fileForm]");  
	        var options  = {    
	            url:'${ctx}/sys/user/upload',
	            type:'post',    
	            success:function(data)    
	            {    
	            	var jsonData = jQuery.parseJSON(data);
	                if(jsonData.status == 'y'){  
	                    var url = jsonData.url;
                        var name = jsonData.filename;
                        //window.parent.layer.alert(url,1);
	                    //window.parent.$("#${id}").attr("src","${basePath}"+url);
	                    //window.parent.$("#${id}").val(url);
                        window.parent.commonFileUploadCallBack("${id}",url,name);
	                    window.parent.layer.closeAll();
	                }else{  
	                	window.parent.layer.closeAll();
	                	window.parent.layer.alert(jsonData.info,0);
	                }
	            }    
	        };    
	        form.ajaxSubmit(options);  
		});
		
	});
	
		function vd(){
			if(document.getElementById("fileUploade").value == "")
			{
				return false;
			}
		}
	</script>
  </head>
  
  <body>
  <div style="padding:10px;">
    <form class="form-inline" onsubmit="return vd();" id="fff" name="fileForm" action="" method="post" enctype="multipart/form-data">
        <div class="form-group" style="float:left;">
            <input type="file" onchange="getPhotoSize(this)" accept="application/pdf" name="fileUploade" id="fileUploade" style=" border:1px solid skyblue; height:34px; margin-right:10px;" />
        </div>
        <button type="submit" id="up" class="btn btn-success" style="float:left;"><i class="glyphicon glyphicon-open"></i>&nbsp;点击上传</button>

    </form>

   </div>
  </body>
</html>
