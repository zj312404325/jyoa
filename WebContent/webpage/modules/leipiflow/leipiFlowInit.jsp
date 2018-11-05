<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>模板创建管理</title>
	<meta name="decorator" content="default"/>
	<%-- <link rel="stylesheet" type="text/css" href="${ctxStatic}/bootstrapValidator/bootstrapValidator.min.css">
    <script type="text/javascript" src="${ctxStatic}/bootstrapValidator/bootstrapValidator.min.js"></script> --%>
	
	<link rel="shortcut icon" href="favicon.ico">
    <link href="${ctxStatic}/flowdesign/css/font-awesome.css?v=4.4.0" rel="stylesheet">

    <link href="${ctxStatic}/flowdesign/css/animate.css" rel="stylesheet">
    <link href="${ctxStatic}/flowdesign/css/style.css?v=4.1.0" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="${ctxStatic}/flowdesign/css/flowdesign.css">
    <!--select 2-->
	<link rel="stylesheet" type="text/css" href="${ctxStatic}/flowdesign/js/jquery.multiselect2side/css/jquery.multiselect2side.css"/>
</head>


<body class="gray-bg">
    <div class="row wrapper border-bottom white-bg page-heading">
        <div class="col-sm-4">
            <h2>流程设计</h2>
            <ol class="breadcrumb">
                <li>
                    主页
                </li>
                <li>
                    流程管理
                </li>
                <li>
                    <strong>流程设计</strong>
                </li>
            </ol>
        </div>
        <div class="col-sm-8">
            <div class="title-action">
                <a href="javascript:" class="btn btn-success" id="leipi_save">保存设计</a>
                <a href="javascript:" class="btn btn-danger" id="leipi_clear">清空连接</a>
            </div>
        </div>
    </div>
    
    <!-- Modal -->
<div id="alertModal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
<div class="modal-dialog">
<div class="modal-content">
  <div class="modal-header">
    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
    <h3>消息提示</h3>
  </div>
  <div class="modal-body">
    <p>提示内容</p>
  </div>
  <div class="modal-footer">
    <button class="btn btn-primary" data-dismiss="modal" aria-hidden="true">我知道了</button>
  </div>
</div>
</div>
</div>

<!-- attributeModal -->
<div id="attributeModal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
<div class="modal-dialog" style="width:800px">
<div class="modal-content">
  <div class="modal-body" style=""><!-- body --></div>
  <div class="modal-footer" style="padding:10px;">
   <!--  <a href="#" class="btn btn-danger" data-dismiss="modal" aria-hidden="true"><i class="glyphicon glyphicon-remove icon-white"></i>关闭</a> -->
    <!---->
  </div>
</div>
</div>
</div>


<!--contextmenu div-->
<div id="processMenu" style="display:none;">
  <ul>
    <!--li id="pm_begin"><i class="icon-play"></i>&nbsp;<span class="_label">设为第一步</span></li-->
    <!--li id="pm_addson"><i class="icon-plus"></i>&nbsp;<span class="_label">添加子步骤</span></li-->
    <!--li id="pm_copy"><i class="icon-check"></i>&nbsp;<span class="_label">复制</span></li-->
    <li id="pmAttribute"><i class="glyphicon glyphicon-cog"></i>&nbsp;<span class="_label">属性</span></li>
    <!-- <li id="pmForm"><i class="glyphicon glyphicon-th"></i>&nbsp;<span class="_label">表单字段</span></li> -->
    <!-- <li id="pmJudge"><i class="glyphicon glyphicon-share-alt"></i>&nbsp;<span class="_label">转出条件</span></li> -->
    <!-- <li id="pmSetting"><i class="glyphicon glyphicon-wrench"></i>&nbsp;<span class="_label">样式</span></li> -->
    <li id="pmDelete"><i class="glyphicon glyphicon-trash"></i>&nbsp;<span class="_label">删除</span></li>
    
  </ul>
</div>
<div id="canvasMenu" style="display:none;">
  <ul>
    <li id="cmSave"><i class="glyphicon glyphicon-ok"></i>&nbsp;<span class="_label">保存设计</span></li>
    <li id="cmAdd"><i class="glyphicon glyphicon-plus"></i>&nbsp;<span class="_label">添加步骤</span></li>
    <li id="cmRefresh"><i class="glyphicon glyphicon-refresh"></i>&nbsp;<span class="_label">刷新 F5</span></li>
    <!--li id="cmPaste"><i class="icon-share"></i>&nbsp;<span class="_label">粘贴</span></li-->
    <!--<li id="cmHelp"><i class="icon-search"></i>&nbsp;<span class="_label">帮助</span></li>-->
  </ul>
</div>
<!--end div--> 
   <div class="container mini-layout" id="flowdesign_canvas">
    <!--div class="process-step btn" style="left: 189px; top: 340px;"><span class="process-num badge badge-inverse"><i class="icon-star icon-white"></i>3</span> 步骤3</div-->
</div> <!-- /container --> 
           	
  
    
     <!-- 全局js -->
    <!--<script src="js/jquery.min.js?v=2.1.4"></script>-->
	<script src="${ctxStatic}/flowdesign/js/jquery-1.7.2.min.js"></script>
    <!--<script src="js/bootstrap.min.js?v=3.3.6"></script>-->
	<script src="${ctxStatic}/flowdesign/js/bootstrap.min_old.js"></script>
	<script src="${ctxStatic}/flowdesign/js/jquery-ui-1.10.4.min.js"></script>
    <!-- 自定义js -->
    <script src="${ctxStatic}/flowdesign/js/content.js?v=1.0.0"></script>
    
    <!--select 2-->
	<script type="text/javascript" src="${ctxStatic}/flowdesign/js/jquery.multiselect2side/js/jquery.multiselect2side.js?2025" ></script>
	<script src="${ctxStatic}/flowdesign/js/jquery.jsPlumb-1.3.16-all-min.js"></script>
    <script src="${ctxStatic}/flowdesign/js/jquery.contextmenu.r2.js"></script>
    <script src="${ctxStatic}/flowdesign/js/leipi.flowdesign.v3.js"></script>
	<script type="text/javascript">
	var the_flow_id = '${flowid}';

	/*页面回调执行    callbackSuperDialog
	    if(window.ActiveXObject){ //IE  
	        window.returnValue = globalValue
	    }else{ //非IE  
	        if(window.opener) {  
	            window.opener.callbackSuperDialog(globalValue) ;  
	        }
	    }  
	    window.close();
	*/
	function callbackSuperDialog(selectValue){
	     var aResult = selectValue.split('@leipi@');
	     $('#'+window._viewField).val(aResult[0]);
	     $('#'+window._hidField).val(aResult[1]);
	    //document.getElementById(window._hidField).value = aResult[1];
	    
	}
	/**
	 * 弹出窗选择用户部门角色
	 * showModalDialog 方式选择用户
	 * URL 选择器地址
	 * viewField 用来显示数据的ID
	 * hidField 隐藏域数据ID
	 * isOnly 是否只能选一条数据
	 * dialogWidth * dialogHeight 弹出的窗口大小
	 */
	function superDialog(URL,viewField,hidField,isOnly,dialogWidth,dialogHeight)
	{
	    dialogWidth || (dialogWidth = 620)
	    ,dialogHeight || (dialogHeight = 520)
	    ,loc_x = 500
	    ,loc_y = 40
	    ,window._viewField = viewField
	    ,window._hidField= hidField;
	    // loc_x = document.body.scrollLeft+event.clientX-event.offsetX;
	    //loc_y = document.body.scrollTop+event.clientY-event.offsetY;
	    if(window.ActiveXObject){ //IE  
	        var selectValue = window.showModalDialog(URL,self,"edge:raised;scroll:1;status:0;help:0;resizable:1;dialogWidth:"+dialogWidth+"px;dialogHeight:"+dialogHeight+"px;dialogTop:"+loc_y+"px;dialogLeft:"+loc_x+"px");
	        if(selectValue){
	            callbackSuperDialog(selectValue);
	        }
	    }else{  //非IE 
	        var selectValue = window.open(URL, 'newwindow','height='+dialogHeight+',width='+dialogWidth+',top='+loc_y+',left='+loc_x+',toolbar=no,menubar=no,scrollbars=no, resizable=no,location=no, status=no');  
	    
	    }
	}




	$(function(){
	    var alertModal = $('#alertModal'),attributeModal =  $("#attributeModal");
	    //消息提示
	    mAlert = function(messages,s)
	    { 
	        if(!messages) messages = "";
	        if(!s) s = 2000;
	        alertModal.find(".modal-body").html(messages);
	        alertModal.modal('toggle');
	        setTimeout(function(){alertModal.modal("hide")},s);
	    }

	    //属性设置
	    attributeModal.on("hidden", function() {
	        $(this).removeData("modal");//移除数据，防止缓存
	    });
	    ajaxModal = function(url,fn)
	    {
	        url += url.indexOf('?') ? '&' : '?';
	        url += '_t='+ new Date().getTime();
			//alert(url);
			console.log(url);
	        attributeModal.find(".modal-body").html('<img src="../img/loading.gif"/>');
	        attributeModal.modal({
	            remote:url
	        });
	        
	        //加载完成执行
	        if(fn)
	        {
	            attributeModal.on('shown.bs.modal',fn);
	        }

	      
	    }
	    //刷新页面
	    function page_reload()
	    {
	        location.reload();
	    }

	 
	    
	    /*
	    php 命名习惯 单词用下划线_隔开
	    js 命名习惯：首字母小写 + 其它首字线大写
	    */
	    /*步骤数据*/
	    //var processData = {"total":5,"list":[{"id":"61","flow_id":"4","process_name":"\u53d1\u8d77\u7533\u8bf7","process_to":"63,64","icon":"glyphicon-ok","style":"width:121px;height:30px;color:#0e76a8;left:193px;top:132px;"},{"id":"62","flow_id":"4","process_name":"\u5ba1\u62792","process_to":"65","icon":"glyphicon-star","style":"width:120px;height:30px;color:#0e76a8;left:486px;top:337px;"},{"id":"63","flow_id":"4","process_name":"\u5feb\u6377\u5ba1\u6279","process_to":"65","icon":"glyphicon-star","style":"width:120px;height:30px;color:#0e76a8;left:193px;top:472px;"},{"id":"64","flow_id":"4","process_name":"\u5ba1\u62791","process_to":"62,65","icon":"glyphicon-star","style":"width:120px;height:30px;color:#ff66b5;left:486px;top:137px;"},{"id":"65","flow_id":"4","process_name":"\u5f52\u6863\u6574\u7406\u4eba","process_to":"","icon":"glyphicon-star","style":"width:120px;height:30px;color:#0e76a8;left:738px;top:472px;"}]};
		//var processData={"total":3,"list":[{"id":"6644e2e9ccd745ccbfde909db16e26c5","flow_id":"7d747955788b4e6b881e5de374d9da4d","process_name":"","process_to":"","icon":"glyphicon-ok","style":"width:121px;height:41px;color:#0e76a8;left:440px;top:313px;"},{"id":"12fe34560ec44f9db50eedb4f771c790","flow_id":"7d747955788b4e6b881e5de374d9da4d","process_name":"","process_to":"","icon":"glyphicon-ok","style":"width:121px;height:41px;color:#0e76a8;left:144px;top:180px;"},{"id":"f630437a99cd4866bb6b82e2608c3ed5","flow_id":"7d747955788b4e6b881e5de374d9da4d","process_name":"","process_to":"","icon":"glyphicon-ok","style":"width:121px;height:41px;color:#0e76a8;left:444px;top:201px;"}]}
		var processData=${process_data};
		console.log(processData);
		/*创建流程设计器*/
	    var _canvas = $("#flowdesign_canvas").Flowdesign({
	                      "processData":processData
	                      /*,mtAfterDrop:function(params)
	                      {
	                          //alert("连接："+params.sourceId +" -> "+ params.targetId);
	                      }*/
	                      /*画面右键*/
	                      ,canvasMenus:{
	                        "cmAdd": function(t) {
	                            var mLeft = $("#jqContextMenu").css("left"),mTop = $("#jqContextMenu").css("top");
								//alert(1);
								/*重要提示 start*/
								//alert("这里使用ajax提交，请参考官网示例，可使用Fiddler软件抓包获取返回格式");
								/*重要提示 end */

	                            var url = "${ctx}/leipiflow/leipiFlowProcess/saveProcess";
	                            $.post(url,{"flow_id":the_flow_id,"left":mLeft,"top":mTop},function(data){
	                            	 if(!data.status)
	                                 {
	                                     mAlert(data.msg);
	                                 }else if(!_canvas.addProcess(data.info))//添加
	                                {
	                                	 mAlert("添加失败");
	                                }
	                               
	                            },'json');
								/* var data = {"id":"61","flow_id":"4","process_name":"\u53d1\u8d77\u7533\u8bf7","process_to":"63,64","icon":"glyphicon-ok","style":"width:121px;color:#0e76a8;left:"+ mLeft +";top:"+ mTop +";"};
								_canvas.addProcess(data); */

	                        },
	                        "cmSave": function(t) {
	                            var processInfo = _canvas.getProcessInfo();//连接信息
								console.log(processInfo);

								/*重要提示 start*/
								//alert("这里使用ajax提交，请参考官网示例，可使用Fiddler软件抓包获取返回格式");
								/*重要提示 end */
								
								var url = "${ctx}/leipiflow/leipiFlowProcess/saveDesign";
	                            $.post(url,{"flow_id":the_flow_id,"process_info":processInfo},function(data){
	                                mAlert(data.msg);
	                            },'json');
	                        },
	                         //刷新
	                        "cmRefresh":function(t){
	                            location.reload();//_canvas.refresh();
	                        },
	                        /*"cmPaste": function(t) {
	                            var pasteId = _canvas.paste();//右键当前的ID
	                            if(pasteId<=0)
	                            {
	                              alert("你未复制任何步骤");
	                              return ;
	                            }
	                            alert("粘贴:" + pasteId);
	                        },*/
	                        "cmHelp": function(t) {
	                            mAlert('敬请期待',20000);
	                        }
	                       
	                      }
	                      /*步骤右键*/
	                      ,processMenus: {
	                          /*
	                          "pmBegin":function(t)
	                          {
	                              var activeId = _canvas.getActiveId();//右键当前的ID
	                              alert("设为第一步:"+activeId);
	                          },
	                          "pmAddson":function(t)//添加子步骤
	                          {
	                                var activeId = _canvas.getActiveId();//右键当前的ID
	                          },
	                          "pmCopy":function(t)
	                          {
	                              //var activeId = _canvas.getActiveId();//右键当前的ID
	                              _canvas.copy();//右键当前的ID
	                              alert("复制成功");
	                          },*/
	                          "pmDelete":function(t)
	                          {
	                              if(confirm("你确定删除步骤吗？"))
	                              {
	                                    var activeId = _canvas.getActiveId();//右键当前的ID

										/*重要提示 start*/
										//alert("这里使用ajax提交，请参考官网示例，可使用Fiddler软件抓包获取返回格式");
										/*重要提示 end */

	                                    //var url = "/index.php?s=/Flowdesign/delete_process.html";
										var url = "${ctx}/leipiflow/leipiFlowProcess/delProcess";
	                                    $.post(url,{"flow_id":the_flow_id,"process_id":activeId},function(data){
	                                        if(data.status==1)
	                                        {
	                                            //清除步骤
	                                            _canvas.delProcess(activeId);
	                                            //清除连接   暂时先保存设计 + 刷新 完成
	                                            var processInfo = _canvas.getProcessInfo();//连接信息
	                                            var url = "${ctx}/leipiflow/leipiFlowProcess/saveDesign";
	                                            $.post(url,{"flow_id":the_flow_id,"process_info":processInfo},function(data){
	                                                location.reload();
	                                            },'json');
	                                            
	                                        }
	                                        mAlert(data.msg);
	                                    },'json');
	                              }
	                          },
	                          "pmAttribute":function(t)
	                          {
	                              var activeId = _canvas.getActiveId();//右键当前的ID
								  /*重要提示 start*/
								  //alert(activeId);
									//alert("这里要使用程序处理，并非简单html页面，如果无法显示，请建立虚拟站点");
									/*重要提示 end */
	                             //var url = "/index.php?s=/Flowdesign/attribute/id/"+activeId+".html";
								 // var url = '${ctxStatic}/flowdesign/attribute.html?id='+activeId;
								 var url = "${ctx}/leipiflow/leipiFlowProcess/goDesignAttribute?id="+activeId;
								  //alert(url);
	                              ajaxModal(url,function(){
	                                //alert('加载完成执行')
	                              });
	                          },
	                          "pmForm": function(t) {
	                                var activeId = _canvas.getActiveId();//右键当前的ID

									/*重要提示 start*/
									//alert("这里使用ajax提交，请参考官网示例，可使用Fiddler软件抓包获取返回格式");
									/*重要提示 end */

	                                var url = "/index.php?s=/Flowdesign/attribute/op/form/id/"+activeId+".html";
	                                ajaxModal(url,function(){
	                                    //alert('加载完成执行')
	                                });
	                          },
	                          "pmJudge": function(t) {
	                                var activeId = _canvas.getActiveId();//右键当前的ID

									/*重要提示 start*/
									//alert("这里使用ajax提交，请参考官网示例，可使用Fiddler软件抓包获取返回格式");
									/*重要提示 end */

	                                var url = "/index.php?s=/Flowdesign/attribute/op/judge/id/"+activeId+".html";
	                                ajaxModal(url,function(){
	                                    //alert('加载完成执行')
	                                });
	                          },
	                          "pmSetting": function(t) {
	                                var activeId = _canvas.getActiveId();//右键当前的ID

									/*重要提示 start*/
									//alert("这里要使用程序处理，并非简单html页面，如果无法显示，请建立虚拟站点");
									/*重要提示 end */
									
	                                //var url = "/index.php?s=/Flowdesign/attribute/op/style/id/"+activeId+".html";
									var url = 'Public/js/flowdesign/attribute.html?id='+activeId;
	                                ajaxModal(url,function(){
	                                    //alert('加载完成执行')
	                                });
	                          }
	                      }
	                      ,fnRepeat:function(){
	                        //alert("步骤连接重复1");//可使用 jquery ui 或其它方式提示
	                        mAlert("步骤连接重复了，请重新连接");
	                        
	                      }
	                      ,fnClick:function(){
	                          var activeId = _canvas.getActiveId();
	                          mAlert("查看步骤信息 " + activeId);
	                      }
	                      ,fnDbClick:function(){
	                          //和 pmAttribute 一样
	                          var activeId = _canvas.getActiveId();//右键当前的ID

							  /*重要提示 start*/
								//alert("这里使用ajax提交，请参考官网示例，可使用Fiddler软件抓包获取返回格式");
								/*重要提示 end */

	                             /*  var url = "/index.php?s=/Flowdesign/attribute/id/"+activeId+".html";
	                              ajaxModal(url,function(){
	                                //alert('加载完成执行')
	                              }); */
								var url = "${ctx}/leipiflow/leipiFlowProcess/goDesignAttribute?id="+activeId;
								  //alert(url);
	                            ajaxModal(url,function(){
	                              //alert('加载完成执行')
	                            });
	                      }
	                  });

	    

	    
	    /*保存*/
	    $("#leipi_save").bind('click',function(){
	        var processInfo = _canvas.getProcessInfo();//连接信息
	        console.log(processInfo);

			/*重要提示 start*/
			//alert("这里使用ajax提交，请参考官网示例，可使用Fiddler软件抓包获取返回格式");
			/*重要提示 end */


	       var url = "${ctx}/leipiflow/leipiFlowProcess/saveDesign";
	        $.post(url,{"flow_id":the_flow_id,"process_info":processInfo},function(data){
	            mAlert(data.msg);
	        },'json');
	    });
	    /*清除*/
	    $("#leipi_clear").bind('click',function(){
	        if(_canvas.clear())
	        {
	            //alert("清空连接成功");
	            mAlert("清空连接成功，你可以重新连接");
	        }else
	        {
	            //alert("清空连接失败");
	            mAlert("清空连接失败");
	        }
	    });


	  
	});

 
</script>
</body>

</html>