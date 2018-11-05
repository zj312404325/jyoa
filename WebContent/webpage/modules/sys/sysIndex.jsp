<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>

<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="renderer" content="webkit">
    
    <title>${fns:getConfig('productName')}</title>

	<%@ include file="/webpage/include/head.jsp"%>
	<script src="${ctxStatic}/common/inspinia.js?v=3.2.0"></script>
	<script src="${ctxStatic}/common/contabs.js"></script> 
    <meta name="keywords" content="JeePlus快速开发平台">
    <meta name="description" content="JeePlus，采用spring mvc+mybatis+shiro+bootstrap，集成代码生成器的快速开发平台">
    <script type="text/javascript">
    if(navigator.appName == "Microsoft Internet Explorer" && navigator.appVersion .split(";")[1].replace(/[ ]/g,"")=="MSIE6.0" || navigator.appName == "Microsoft Internet Explorer" && navigator.appVersion .split(";")[1].replace(/[ ]/g,"")=="MSIE7.0" || navigator.appName == "Microsoft Internet Explorer" && navigator.appVersion .split(";")[1].replace(/[ ]/g,"")=="MSIE8.0" || navigator.appName == "Microsoft Internet Explorer" && navigator.appVersion .split(";")[1].replace(/[ ]/g,"")=="MSIE9.0")
	{
		location.href = '../../../webpage/error/ie.html';
	}
	$(document).ready(function() {
	    if('${taskcount}'!='0'){
            $('#${todotaskmenu}').html('(${taskcount})');
        }
        if('${ehrtodotasktotalcount}'!='0'){
            $('#${ehrtodotasktotalmenu}').html('(${ehrtodotasktotalcount})');
        }
        if('${posttodotasktotalcount}'!='0'){
            $('#${posttodotasktotalmenu}').html('(${posttodotasktotalcount})');
        }
        if('${todotasktotalcount}'!='0'){
            $('#${todotasktotalmenu}').html('(${todotasktotalcount})');
        }
        if('${zhuanzhengtaskcount}'!='0'){
            $('#${zhuanzhengtaskmenu}').html('(${zhuanzhengtaskcount})');
        }
        if('${xinzitaskcount}'!='0'){
            $('#${xinzitaskmenu}').html('(${xinzitaskcount})');
        }
        if('${jiangchengtaskcount}'!='0'){
            $('#${jiangchengtaskmenu}').html('(${jiangchengtaskcount})');
        }
        if('${postshenqingtaskcount}'!='0'){
            $('#${postshenqingtaskmenu}').html('(${postshenqingtaskcount})');
        }
        if('${postjiaojietaskcount}'!='0'){
            $('#${postjiaojietaskmenu}').html('(${postjiaojietaskcount})');
        }
        if('${leavejiaojietodotasktotalcount}'!='0'){
            $('#${leavejiaojietodotasktotalmenu}').html('(${leavejiaojietodotasktotalcount})');
            $('#${leavetodotasktotalmenu}').html('(${leavejiaojietodotasktotalcount})');
        }


		 if('${fns:getDictLabel(cookie.theme.value,'theme','默认主题')}' == '天蓝主题'){
			    // 蓝色主题
			        $("body").removeClass("skin-2");
			        $("body").removeClass("skin-3");
			        $("body").addClass("skin-1");
		 }else  if('${fns:getDictLabel(cookie.theme.value,'theme','默认主题')}' == '橙色主题'){
			    // 黄色主题
			        $("body").removeClass("skin-1");
			        $("body").removeClass("skin-2");
			        $("body").addClass("skin-3");
		 }else {
			 // 默认主题
			        $("body").removeClass("skin-2");
			        $("body").removeClass("skin-3");
			        $("body").removeClass("skin-1");
		 };
		 
		/// dwr.engine.setActiveReverseAjax(true);
		// dwr.engine._errorHandler = function(message, ex) {dwr.engine._debug("Error: " + ex.name + ", " + ex.message, true);}; 
		// StocksPusher.onLoad('${fns:getUser().id}');
	 });
	
	function showMsgCount(count){
		var jsonData = jQuery.parseJSON(count);
		if(jsonData.cnt1 != "-1"){$("span[name=num1]").html(jsonData.cnt1);$("li[name=area1]").remove();}
		if(jsonData.cnt2 != "-1"){
		    $("span[name=num2]").html(jsonData.cnt2);
            if(jsonData.cnt2!='0'){
                $('#${todotaskmenu}').html('('+jsonData.cnt2+')');
            }else{
                $('#${todotaskmenu}').html('');
            }
		}
		if(jsonData.cnt3 != "-1"){$("span[name=num3]").html(jsonData.cnt3);$("li[name=area3]").remove();}

        if(jsonData.ehrtodotasktotalcount != null){
            if(jsonData.ehrtodotasktotalcount != 0){
                $('#'+jsonData.ehrtodotasktotalmenu).html('('+jsonData.ehrtodotasktotalcount+')');
            }else{
                $('#'+jsonData.ehrtodotasktotalmenu).html('');
            }
        }
        if(jsonData.posttodotasktotalcount != null){
            if(jsonData.posttodotasktotalcount != 0){
                $('#'+jsonData.posttodotasktotalmenu).html('('+jsonData.posttodotasktotalcount+')');
            }else{
                $('#'+jsonData.posttodotasktotalmenu).html('');
            }
        }
        if(jsonData.todotasktotalcount != null){
            if(jsonData.todotasktotalcount != 0){
                $('#'+jsonData.todotasktotalmenu).html('('+jsonData.todotasktotalcount+')');
            }else{
                $('#'+jsonData.todotasktotalmenu).html('');
            }
        }
        if(jsonData.zhuanzhengtaskcount != null){
            if(jsonData.zhuanzhengtaskcount != 0){
                $('#'+jsonData.zhuanzhengtaskmenu).html('('+jsonData.zhuanzhengtaskcount+')');
            }else{
                $('#'+jsonData.zhuanzhengtaskmenu).html('');
            }
        }
        if(jsonData.xinzitaskcount != null){
            if(jsonData.xinzitaskcount != 0){
                $('#'+jsonData.xinzitaskmenu).html('('+jsonData.xinzitaskcount+')');
            }else{
                $('#'+jsonData.xinzitaskmenu).html('');
            }
        }
        if(jsonData.jiangchengtaskcount != null){
            if(jsonData.jiangchengtaskcount != 0){
                $('#'+jsonData.jiangchengtaskmenu).html('('+jsonData.jiangchengtaskcount+')');
            }else{
                $('#'+jsonData.jiangchengtaskmenu).html('');
            }
        }
        if(jsonData.postjiaojietaskcount != null){
            if(jsonData.postjiaojietaskcount != 0){
                $('#'+jsonData.postjiaojietaskmenu).html('('+jsonData.postjiaojietaskcount+')');
            }else{
                $('#'+jsonData.postjiaojietaskmenu).html('');
            }
        }
        if(jsonData.postshenqingtaskcount != null){
            if(jsonData.postshenqingtaskcount != 0){
                $('#'+jsonData.postshenqingtaskmenu).html('('+jsonData.postshenqingtaskcount+')');
            }else{
                $('#'+jsonData.postshenqingtaskmenu).html('');
            }
        }
        if(jsonData.leavejiaojietodotasktotalcount != null){
            if(jsonData.leavejiaojietodotasktotalcount != 0){
                $('#'+jsonData.leavejiaojietodotasktotalmenu).html('('+jsonData.leavejiaojietodotasktotalcount+')');
                $('#'+jsonData.leavetodotasktotalmenu).html('('+jsonData.leavejiaojietodotasktotalcount+')');
            }else{
                $('#'+jsonData.leavejiaojietodotasktotalmenu).html('');
                $('#'+jsonData.leavetodotasktotalmenu).html('('+jsonData.leavejiaojietodotasktotalcount+')');
            }
        }
		if(jsonData.msg != null && jsonData.msg != "" && jsonData.msg != "[]"){
			waitOpenwin(jsonData.msg);
		}
		
				/* if(count != 0){
					//改变头部的未读信息数目
					$("#msg").html('<img src="${ctx}/images_new/letter_icon.gif" title="未读消息"/><span style="color:#F00; font-weight:bold;">('+count+')</span>');
					//对于即时来的信息即时提醒
					$.jBox.messager('<a href="${ctx}/sms/smsSearch.htm" target=_blank>您有新短消息请,请查收!</a>', '消息提示',null,{height: 100});
				} */
			}
	var isWinOpen = false;
	function openwin(msg){
		isWinOpen = true;
		//var width=Math.round((window.screen.width-400)/2);
		//var height=Math.round((window.screen.height-200)/2);
		var width = Math.round(window.screen.width-300);
		var height= Math.round(window.screen.height-258);
		//var o = window.open('about:blank', 'newwindow', 'height=150, width=280, top='+height+',left='+width+',toolbar=no, menubar=no, scrollbars=no, resizable=false,location=no, status=no');
		//o.location.href='${ctx}/sys/user/remindWin?msg='+msg;
		var o = window.open('${ctx}/sys/user/remindWin?msg='+encodeURI(msg), 'newwindow', 'height=150, width=280, top='+height+',left='+width+',toolbar=no, menubar=no, scrollbars=no, resizable=false,location=no, status=no');
		if(o == null){
			alert("你收到的新消息提醒弹窗已被浏览器拦截，请设置为始终允许弹出！");
		}else{
			var loop = setInterval(function() { 
			    if(o.closed) {    
			    	isWinOpen = false;
			        clearInterval(loop);   
			    }    
			}, 1000);
		}
	}
	function waitOpenwin(msg){
		if(isWinOpen){
			setTimeout(function(){waitOpenwin(msg);}, 15000);
		}else{
			openwin(msg);
			return false;
		}
	}
	function refreshDataIndex(){
		$(".J_menuItem").each(function (index) {
		    if (!$(this).attr('data-index')) {
		        $(this).attr('data-index', index);
		    }
		});
	}
	function changeOaNotifyCount(id,type){
		if(type==0){//刷新传阅通知
			$.post('${basePath}/oanotifycount',{"id":id},function(data){
				var jsonData=jQuery.parseJSON(data);
				$("span[name=num1]").html(jsonData.notifycount);
				var html='';
				$.each(jsonData.lst,function(i,n){
					html+='<div>';
					html+='<a class="J_menuItem" href="${ctx}/oa/oaNotify/view?id='+n.id+'" onclick="changeOaNotifyCount(\''+n.id+'\',0)" >';
					html+='<i class="fa fa-envelope fa-fw"></i> '+n.title+'</a>';
					html+='<span class="pull-right text-muted small">'+n.millsecond+'前</span>';
					html+='</div>';
				});
				$("#area1Data").html(html);
				refreshDataIndex();
				$('.J_menuItem').on('click', menuItem);
			});
		}
		if(type==1){//刷新邮件通知
			$.post('${basePath}/mailcount',{"id":id},function(data){
				var jsonData=jQuery.parseJSON(data);
				$("span[name=num3]").html(jsonData.mailcount);
				var html='';
				$.each(jsonData.lst,function(i,n){
					html+='<li class="m-t-xs" name="area3"><div class="dropdown-messages-box"><a  href="#" onclick=\'top.openTab("${ctx}/iim/contact/index?name='+n.name+'","通讯录", false)\' class="pull-left">';
					if(n.photo==null||n.photo==''){
                    	html+='<img alt="image" class="img-circle" src="${basePath }/images/nohead.jpg">';
                    }else{
                    	html+='<img alt="image" class="img-circle" src="'+n.photo+'">';
                    }
					html+='</a><div class="media-body"><small class="pull-right">'+n.sendtime+'前</small><strong>'+n.name+'</strong>';
					html+='<a class="J_menuItem" href="${ctx}/iim/mailBox/detail?id='+n.id+'" onclick="changeOaNotifyCount(\''+n.id+'\',1)"> '+n.title+'</a>';
					html+='<br><small class="text-muted">'+n.sendtime1+'</small></div>';
                    html+='</li><li class="divider" name="area3"></li>';
				});
				html+='<li>';
				html+=' <div class="text-center link-block"><a class="J_menuItem" href="${ctx}/iim/mailBox/list?orderBy=sendtime desc"><i class="fa fa-envelope"></i> <strong> 查看所有邮件</strong></a></div></li>';
				$("#area3Data").html(html);
				refreshDataIndex();
				$('.J_menuItem').on('click', menuItem);
			});
		}
	}
	var i;
	function showimage(url){
	    $("#imageview").attr("src",url);
        isImgLoad(function(){
            layer.close(i);
            layer.open({
                type: 1,
                title: false,
                closeBtn: 1,
                area:['auto','auto'],
                skin: 'layui-layer-nobg', //没有背景色
                shadeClose: true,
                content: $('#imageview'),
                end:function () {
                    $("#imageview").attr("src","");
                    $("#imageview").height("");
                    $("#imageview").width("");
                }
            });
        });

    }

    var t_img; // 定时器
    var isLoad = true; // 控制变量

    // 判断图片加载状况，加载完成后回调
    //isImgLoad(function(){
    //    // 加载完成
    //});

    // 判断图片加载的函数
    function isImgLoad(callback){
        i = layer.load();
        // 注意我的图片类名都是cover，因为我只需要处理cover。其它图片可以不管。
        // 查找所有封面图，迭代处理
        $('#imageview').each(function(){
            // 找到为0就将isLoad设为false，并退出each
            if(this.height === 0){
                isLoad = false;
                return false;
            }
        });
        // 为true，没有发现为0的。加载完毕
        if(isLoad){
            clearTimeout(t_img); // 清除定时器
            // 回调函数
            callback();
            // 为false，因为找到了没有加载完成的图，将调用定时器递归
        }else{
            isLoad = true;
            t_img = setTimeout(function(){
                isImgLoad(callback); // 递归扫描
            },500); // 我这里设置的是500毫秒就扫描一次，可以自己调整
        }
    }
	</script>

</head>

<body class="fixed-sidebar full-height-layout gray-bg" onload="trainPlanTip()">
    <div id="wrapper">
        <!--左侧导航开始-->
        <nav class="navbar-default navbar-static-side" role="navigation">
            <div class="nav-close"><i class="fa fa-times-circle"></i>
            </div>
            <div class="sidebar-collapse">
                <ul class="nav" id="side-menu">
                    <li class="nav-header">
                        <div class="dropdown profile-element">
                        <a data-toggle="dropdown" class="dropdown-toggle" href="#">
                            <span><img alt="image" class="img-circle" style="height:64px;width:64px;" src="${basePath}${fns:getUser().photo }" /></span>
                            
                                <span class="clear">
                               <span class="block m-t-xs"><strong class="font-bold">${fns:getUser().name}</strong></span>
                               <%--<span class="text-muted text-xs block">${fns:getUser().roleNames}<b class="caret"></b></span>--%>
                                </span>
                            </a>
                            <ul class="dropdown-menu animated fadeInRight m-t-xs">
                                <li><a class="J_menuItem" href="${ctx}/sys/user/imageEdit"><span>修改头像</span></a>
                                </li>
                                <li><a class="J_menuItem" href="${ctx}/sys/user/modifyPwdByBtn"><span>修改密码</span></a>
                                </li>
                                <li><a class="J_menuItem" href="${ctx }/sys/user/info"><span>个人资料</span></a>
                                </li>
                                <li><a class="J_menuItem" href="${ctx }/iim/contact/index"><span>我的通讯录</span></a>
                                </li>
                                <li><a class="J_menuItem" href="${ctx }/iim/mailBox/list"><span>信箱</span></a>
                                </li> 
                                 <li class="divider"></li>
                                <li><a onclick="changeStyle()" href="#">切换到ACE模式</a>
                                </li> 
                                 
                                <li class="divider"></li>
                                <li><a href="${ctx}/logout">安全退出</a>
                                </li>
                            </ul>
                        </div>
                        <div class="logo-element">JY
                        </div>
                    </li>
     
                  <t:menu  menu="${fns:getTopMenu()}"></t:menu>
                  <%--<t:institution institution="${fns:getTopInstitutionMenu()}"></t:institution>--%>
                  <!-- <li><a class="J_menuItem" href="http://172.19.0.8/iOffice/bylaw/MyHtml.htm" data-index="46"><i class="fa fa-calendar"></i> <span class="nav-label">集团值班信息</span></a></li>
				  <li><a class="J_menuItem" href="http://172.19.0.128:8081/8sHtml.htm" data-index="47"><i class="fa fa-calendar"></i> <span class="nav-label">8S检查信息表</span></a></li>
				  <li><a class="J_menuItem" href="http://172.19.0.128:8081/mystruts/dbsum.action?param1=1" data-index="48"><i class="fa fa-calendar"></i> <span class="nav-label">倒班交接信息表</span></a></li>
				  <li class="">
				    <a href=""><i class="fa fa-hand-paper-o"></i><span class="nav-label">每日团队风采</span><span class="fa arrow"></span></a>
					    <ul class="nav nav-second-level collapse" aria-expanded="false" style="height: 0px;">
							<li><a class="J_menuItem" href="/a/tools/email" data-index="37"><i class="fa pencil"></i> <span class="nav-label">是是是</span></a></li>
						</ul>
				  </li> -->
				</ul>
            </div>
        </nav>
        <!--左侧导航结束-->
        <!--右侧部分开始-->
        <div id="page-wrapper" class="gray-bg dashbard-1">
            <div class="row border-bottom">
                <nav class="navbar navbar-static-top" role="navigation" style="margin-bottom: 0">
                    <div class="navbar-header"><a class="navbar-minimalize minimalize-styl-2 btn btn-primary " href="#"><i class="fa fa-bars"></i> </a>
                        <form role="search" class="navbar-form-custom" method="post" action="search_results.html">
                            <div class="form-group">
                                <!-- <input type="text" placeholder="请输入您需要查找的内容 …" class="form-control" name="top-search" id="top-search"> -->
                            </div>
                        </form>
                    </div>
                    <ul class="nav navbar-top-links navbar-right">
                    
                        <li class="dropdown">
                            <a class="dropdown-toggle count-info" data-toggle="dropdown" href="#" onclick="javascript:$('#newstips').hide();">
                                <i class="fa fa-bell"></i> <span class="label label-primary" name="num1">${count }</span>
                            </a>
                            
                            <div class="tooltip left fadeIn" id="newstips" style="opacity:0.8; left:-200px; top:10px;display: none;">
                                <div class="tooltip-arrow"></div>
                                <div class="tooltip-inner" style="padding:10px;">
                                    <span class="glyphicon glyphicon-hand-right"></span>&nbsp;&nbsp;收到新的消息，请注意查看！
                                </div>
                            </div>
                            
                            <ul class="dropdown-menu dropdown-alerts">
                                <li name="area1" id="area1Data">
                                
                                <c:forEach items="${page.list}" var="oaNotify">
                         
                                        <div>
                                        	   <a class="J_menuItem" href="${ctx}/oa/oaNotify/view?id=${oaNotify.id}" >
                                            	<i class="fa fa-envelope fa-fw"></i> ${fns:abbr(oaNotify.title,20)}
                                               </a>
                                            <span class="pull-right text-muted small">${fns:getTime(oaNotify.updateDate)}前</span>
                                        </div>
                                 
								</c:forEach>
                                   
                                </li>
                                <li class="divider" name="area1"></li>
                                <li>
                                    <div class="text-center link-block">
                                       您有<span name="num1">${count }</span>条未读消息 <a class="J_menuItem" href="${ctx }/oa/oaNotify/selfnoread ">
                                            <strong>查看所有 </strong>
                                            <i class="fa fa-angle-right"></i>
                                        </a>
                                    </div>
                                </li>
                            </ul>
                        </li>
                    	<li class="dropdown">
                            <%-- <a class="dropdown-toggle count-info J_menuItem" data-toggle="dropdown" href="${ctx}/act/task/todo/">
                                <i class="fa fa-bullhorn"></i><strong style="display:none;"> 代办任务</strong> <span class="label label-danger">${taskcount}</span>
                            </a> --%>
                            <a class="dropdown-toggle count-info" data-toggle="dropdown" href="#" onclick="javascript:$('#newstips').hide();">
                                <i class="fa fa-bullhorn"></i> <span class="label label-danger" name="num2">${taskcount}</span>
                            </a>
                            <ul class="dropdown-menu dropdown-alerts">
                                <li>
                                    <div class="text-center link-block">
                                       您有<span name="num2">${taskcount }</span>条待办任务 <a class="J_menuItem" href="${ctx}/leipiflow/leipiFlowApply/myLeipiTask/?type=0">
                                            <strong>查看所有 </strong>
                                            <i class="fa fa-angle-right"></i>
                                        </a>
                                    </div>
                                </li>
                            </ul>
                        </li>
                        <li class="dropdown">
                            <a class="dropdown-toggle count-info" data-toggle="dropdown" href="#">
                                <i class="fa fa-envelope"></i> <span class="label label-warning" name="num3" onclick="javascript:$('#newstips').hide();">${noReadCount}</span>
                            </a>
                            <ul class="dropdown-menu dropdown-messages" id="area3Data">
                            	 <c:forEach items="${mailPage.list}" var="mailBox">
	                                <li class="m-t-xs" name="area3">
	                                    <div class="dropdown-messages-box">
	                                        <a  href="#" onclick='top.openTab("${ctx}/iim/contact/index?name=${mailBox.sender.name }","通讯录", false)' class="pull-left">
	                                            
	                                            <c:choose>
													<c:when test="${mailBox.sender.photo==null||mailBox.sender.photo=='' }">
														<img alt="image" class="img-circle" src="${basePath }/images/nohead.jpg">
													</c:when>
													<c:otherwise>
														<img alt="image" class="img-circle" src="${basePath }/${mailBox.sender.photo }">
													</c:otherwise>
												</c:choose>
	                                        </a>
	                                        <div class="media-body">
	                                            <small class="pull-right">${fns:getTime(mailBox.sendtime)}前</small>
	                                            <strong>${mailBox.sender.name }</strong>
	                                            <a class="J_menuItem" href="${ctx}/iim/mailBox/detail?id=${mailBox.id}" onclick="changeOaNotifyCount('${mailBox.id}',1)"> ${fns:abbr(mailBox.mail.title,10)}</a>
	                                            <%-- <br>
	                                            <a class="J_menuItem" href="${ctx}/iim/mailBox/detail?id=${mailBox.id}" onclick="changeOaNotifyCount('${mailBox.id}',1)">
	                                             ${mailBox.mail.overview}
	                                            </a> --%>
	                                            <br>
	                                            <small class="text-muted">
	                                            <fmt:formatDate value="${mailBox.sendtime}" pattern="yyyy-MM-dd HH:mm:ss"/></small>
	                                        </div>
	                                    </div>
	                                </li>
	                                <li class="divider" name="area3"></li>
                                </c:forEach>
                                <li>
                                    <div class="text-center link-block">
                                        <a class="J_menuItem" href="${ctx}/iim/mailBox/list?orderBy=sendtime desc">
                                            <i class="fa fa-envelope"></i> <strong> 查看所有邮件</strong>
                                        </a>
                                    </div>
                                </li>
                            </ul>
                        </li>
                        
                      
                      <!-- 国际化功能预留接口 -->
                        <li class="dropdown">
							<%-- <a id="lang-switch" class="lang-selector dropdown-toggle" href="#" data-toggle="dropdown" aria-expanded="true">
								<span class="lang-selected">
										<img  class="lang-flag" src="${ctxStatic}/common/img/china.png" alt="中国">
										<span class="lang-id">中国</span>
										<span class="lang-name">中文</span>
									</span>
							</a>

							<!--Language selector menu-->
							<ul class="head-list dropdown-menu with-arrow">
								<li>
									<!--English-->
									<a class="lang-select">
										<img class="lang-flag" src="${ctxStatic}/common/img/china.png" alt="中国">
										<span class="lang-id">中国</span>
										<span class="lang-name">中文</span>
									</a>
								</li>
								<li>
									<!--English-->
									<a class="lang-select">
										<img class="lang-flag" src="${ctxStatic}/common/img/united-kingdom.png" alt="English">
										<span class="lang-id">EN</span>
										<span class="lang-name">English</span>
									</a>
								</li>
								<li>
									<!--France-->
									<a class="lang-select">
										<img class="lang-flag" src="${ctxStatic}/common/img/france.png" alt="France">
										<span class="lang-id">FR</span>
										<span class="lang-name">Français</span>
									</a>
								</li>
								<li>
									<!--Germany-->
									<a class="lang-select">
										<img class="lang-flag" src="${ctxStatic}/common/img/germany.png" alt="Germany">
										<span class="lang-id">DE</span>
										<span class="lang-name">Deutsch</span>
									</a>
								</li>
								<li>
									<!--Italy-->
									<a class="lang-select">
										<img class="lang-flag" src="${ctxStatic}/common/img/italy.png" alt="Italy">
										<span class="lang-id">IT</span>
										<span class="lang-name">Italiano</span>
									</a>
								</li>
								<li>
									<!--Spain-->
									<a class="lang-select">
										<img class="lang-flag" src="${ctxStatic}/common/img/spain.png" alt="Spain">
										<span class="lang-id">ES</span>
										<span class="lang-name">Español</span>
									</a>
								</li>
							</ul> --%>
						</li>
						
						
                    </ul>
                </nav>
            </div>
            <div class="row content-tabs">
                <button class="roll-nav roll-left J_tabLeft"><i class="fa fa-backward"></i>
                </button>
                <nav class="page-tabs J_menuTabs">
                    <div class="page-tabs-content">
                        <a href="javascript:;" class="active J_menuTab" data-id="${ctx}/home">首页</a>
                    </div>
                </nav>
                <button class="roll-nav roll-right J_tabRight"><i class="fa fa-forward"></i>
                </button>
                <div class="btn-group roll-nav roll-right">
                    <button class="dropdown J_tabClose"  data-toggle="dropdown">关闭操作<span class="caret"></span>

                    </button>
                    <ul role="menu" class="dropdown-menu dropdown-menu-right">
                        <li class="J_tabShowActive"><a>定位当前选项卡</a>
                        </li>
                        <li class="divider"></li>
                        <li class="J_tabCloseAll"><a>关闭全部选项卡</a>
                        </li>
                        <li class="J_tabCloseOther"><a>关闭其他选项卡</a>
                        </li>
                    </ul>
                </div>
                <a href="${ctx}/logout" class="roll-nav roll-right J_tabExit"><i class="fa fa fa-sign-out"></i> 退出</a>
            </div>
            <div class="row J_mainContent" id="content-main">
                <iframe class="J_iframe" name="iframe0" width="100%" height="100%" src="${ctx}/home" frameborder="0" data-id="${ctx}/home" seamless></iframe>
            </div>
            <div class="footer">
                <%--<a class="J_menuItem" href="http://www.jinying365.com" data-index="666">http://www.jinying365.com</a>--%> <%--&copy; 2018-2025--%>
                    <marquee style="width:100%;" ><strong class="text-danger" id="systemNotificationArea">${systemNotification}</strong></marquee>


            </div>
        </div>
        <!--右侧部分结束-->
       
       
    </div>

    <img src="" id="imageview" style="max-width:800px; max-height: 800px;display: none" />

    <%--<div class="bg_blank" style="width: 100%; height: 100%; position: absolute; top:0; left: 0; z-index: 99999998; background-color: rgba(0,0,0,0.5);">

        <div class="lay_box" style="width: 20%; height: 20%; background-color: #fff; position: absolute; left: 40%; top:20%; z-index: 99999999">
            <div class="lay_title clearfix" style="line-height: 40px; border-bottom:1px solid #ddd; padding: 0 10px;"><span style="float: left;">提示</span><a style="float: right; font-size: 30px;" href="#">&times</a></div>
            <div class="lay_con" style="padding: 10px;">111111</div>
            <div class="lay_btn"><a href="javascript:" class="btn"></a> </div>
        </div>
    </div>--%>
    <div class="modal fade" tabindex="-1" role="dialog" id="myModal">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title">提示</h4>
                </div>
                <div class="modal-body">
                    <p>您的密码超过30天未修改，请及时修改！</p>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                    <%--<button type="button" class="btn btn-primary">确定</button>--%>
                </div>
            </div><!-- /.modal-content -->
        </div><!-- /.modal-dialog -->
    </div>

</body>

<!-- 语言切换插件，为国际化功能预留插件 -->
<script type="text/javascript">

$(document).ready(function(){
    if("${pwdchange}" == 'true'){
        $('#myModal').modal();
    }

    $('<audio id="chatAudio"><source src="${ctxStatic}/MP3/notify.ogg" type="audio/ogg"><source src="${ctxStatic}/MP3/notify.mp3" type="audio/mpeg"><source src="${ctxStatic}/MP3/notify.wav" type="audio/wav"></audio>').appendTo('body');
	$("a.lang-select").click(function(){
		$(".lang-selected").find(".lang-flag").attr("src",$(this).find(".lang-flag").attr("src"));
		$(".lang-selected").find(".lang-flag").attr("alt",$(this).find(".lang-flag").attr("alt"));
		$(".lang-selected").find(".lang-id").text($(this).find(".lang-id").text());
		$(".lang-selected").find(".lang-name").text($(this).find(".lang-name").text());

	});


});

function changeStyle(){
   $.get('${pageContext.request.contextPath}/theme/ace?url='+window.top.location.href,function(result){   window.location.reload();});
}

</script>



<!-- 即时聊天插件  开始-->
<link href="${ctxStatic}/layer-v2.3/layim/layui/css/layui.css" type="text/css" rel="stylesheet"/>
<script type="text/javascript">
var currentId = '${fns:getUser().loginName}';
var currentName = '${fns:getUser().name}';
var currentFace ='${fns:getUser().photo}';
var url="${ctx}";
var static_url="${ctxStatic}";
var basePath="${basePath}";
var wsServer = 'ws://'+window.document.domain+':8668';

</script>
<!--webscoket接口  -->
<script src="${ctxStatic}/layer-v2.3/layim/layui/layui.js"></script>

<script src="${ctxStatic}/layer-v2.3/layim/layim.js"></script>
<!-- 即时聊天插件 结束 -->
<style>
/*签名样式*/
.layim-sign-box{
	width:95%
}
.layim-sign-hide{
  border:none;background-color:rgba(255, 255, 255, 0);
}
</style>

<script>
    function trainPlanTip(){
        //提示
        $.post("${ctx}/trainPlanTip","",function () {

        });
    }
</script>
</html>