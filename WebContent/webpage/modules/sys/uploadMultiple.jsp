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
      <style>
          table.table > tbody > tr > td { border:none !important;}
      </style>
	<script>
    //判断文件是否存在
    function validFileUrlExist() {
        var flag = true;
        var fileurl=$("input[name=fileUploade]").val();
        if(fileurl==''||fileurl===undefined){
            flag = false;
        }
        return flag;
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
	                    var url=jsonData.fileList;
                        var name = jsonData.filename;
                        window.parent.commonFileUploadCallBack("${id}",url,name);
	                    window.parent.layer.closeAll();
	                }else{  
	                	//window.parent.layer.closeAll();
	                	window.parent.layer.alert(jsonData.info,0);
	                }
	            },error:function(){
                    alert("上传文件大小不能大于10M！");
                }
	        };    
	        form.ajaxSubmit(options);  
		});

		$("#addFile").click(function(){
		    $(this).closest("table").find("tr:last").after('<tr><td><input type="file" name="fileUploade"  style=" border:1px solid skyblue;" /></td><td><a href="javascript:" onclick="delFile(this)"><i class="glyphicon glyphicon-remove text-danger"></i></a></td></tr>')
        });
		
	});

    function delFile(obj) {
        $(obj).closest("tr").remove();
    }
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
    <form onsubmit="return vd();" id="fff" name="fileForm" action="" method="post" enctype="multipart/form-data">
        <table class="table" style="border:none" id="fileTable">
            <tr>
                <td><input type="file" name="fileUploade" style=" border:1px solid skyblue;" /></td>
                <td><a href="javascript:" id="addFile"><i class="glyphicon glyphicon-plus"></i></a></td>
            </tr>

        </table>
        <div class="form-group text-right">
            <button type="submit" id="up" class="btn btn-success"><i class="glyphicon glyphicon-open"></i>&nbsp;点击上传</button>
        </div>

    </form>

   </div>
  </body>
</html>
