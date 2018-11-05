<%@ page language="java" pageEncoding="UTF-8"%>  
<script type="text/javascript">
	  var fileid = "";
      function commonFinderOpen(id){
    	  fileid = id;
    	  var date = new Date(),
    	      year = date.getFullYear(),
    	      month = (date.getMonth()+1)>9?date.getMonth()+1:"0"+(date.getMonth()+1);
    	  var url = "${ctxStatic}/ckfinder/ckfinder.html?type=files&start=files:/common/upload/"+year+"/"+month+"/&action=js&func=commonSelectAction&thumbFunc=commonThumbSelectAction&cb=commonCallback&dts=0&sm=0";
    	  windowOpen(url,"文件管理",1000,700);
      }
      function commonSelectAction(fileUrl, data, allFiles){
    	  var url="", files=ckfinderAPI.getSelectedFiles();
    	  for(var i=0; i<files.length; i++){
    		  url += files[i].getUrl();
    		  if (i<files.length-1) url+="|";
    	  }
    	 // $("#"+fileid).val($("#"+fileid).val()+($("#"+fileid).val(url)==""?url:"|"+url));
    	  $("#"+fileid).val(url);
    	  commonPreview();
      }
      function commonThumbSelectAction(fileUrl, data, allFiles){
    	  /* var url="", files=ckfinderAPI.getSelectedFiles();
    	  for(var i=0; i<files.length; i++){
    		  url += files[i].getThumbnailUrl();if (i<files.length-1) url+="|";
    	  }
    	  $("#"+fileid).val($("#"+fileid).val()+($("#"+fileid).val(url)==""?url:"|"+url)); */
    	  commonPreview();
      }
      function commonCallback(api){
    	  ckfinderAPI = api;
      }
      /* function commonDel(obj){
    	  var url = $(obj).prev().attr("url");
    	  $("#"+fileid).val($("#"+fileid).val().replace("|"+url,"","").replace(url+"|","","").replace(url,"",""));
    	  commonPreview();
      }
      function commonDelAll(){
    	  $("#"+fileid).val("");
    	  commonPreview();
      } */
      function commonPreview(){
    	  //$("#"+fileid+"_image").attr("src",$("#"+fileid).val());
      }
      commonPreview();


      function commonFileUpload(id){
          <%--var url = "${ctxStatic}/ckfinder/ckfinder.html?type=files&start=files:/common/upload/"+year+"/"+month+"/&action=js&func=commonSelectAction&thumbFunc=commonThumbSelectAction&cb=commonCallback&dts=0&sm=0";--%>
          <%--windowOpen(url,"文件管理",1000,700);--%>
          layer.open({
              type: 2,
              title:"上传",
              shadeClose: true,
              shade: 0.8,
              area : ['400px' , 'auto'],
              offset : ['100px',''],
              content: '${ctx}/sys/user/uploadInit?id='+id
          });
      }

      function commonFileUpload(id,type){
          <%--var url = "${ctxStatic}/ckfinder/ckfinder.html?type=files&start=files:/common/upload/"+year+"/"+month+"/&action=js&func=commonSelectAction&thumbFunc=commonThumbSelectAction&cb=commonCallback&dts=0&sm=0";--%>
          <%--windowOpen(url,"文件管理",1000,700);--%>
          var width = 400;
          var height = 'auto';
          if(type == "muti"){
              width = 450;
              height = '300px';
          }
          layer.open({
              type: 2,
              title:"上传",
              shadeClose: true,
              shade: 0.8,
              area : [width+'px' , height],
              offset : ['100px',''],
              content: '${ctx}/sys/user/uploadInit?id='+id+'&type='+type
          });
      }
      function commonFileUpload(id,type,filetype){
          <%--var url = "${ctxStatic}/ckfinder/ckfinder.html?type=files&start=files:/common/upload/"+year+"/"+month+"/&action=js&func=commonSelectAction&thumbFunc=commonThumbSelectAction&cb=commonCallback&dts=0&sm=0";--%>
          <%--windowOpen(url,"文件管理",1000,700);--%>
          var width = 400;
          var height = 'auto';
          if(type == "muti"){
              width = 450;
              height = '300px';
          }
          layer.open({
              type: 2,
              title:"上传",
              shadeClose: true,
              shade: 0.8,
              area : [width+'px' , height],
              offset : ['100px',''],
              content: '${ctx}/sys/user/uploadInit?id='+id+'&type='+type+'&filetype='+filetype
          });
      }

      function commonFileUploadCallBack(id,url,fname){
          $("#"+id).val(url);
      }
</script>