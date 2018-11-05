<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>

<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="renderer" content="webkit">
	
    <title>${fns:getConfig('productName')}</title>

	<%@ include file="/webpage/include/acehead.jsp"%>
	<script src="${ctxStatic}/common/inspinia-ace.js?v=3.2.0"></script>
	<script src="${ctxStatic}/common/contabs.js"></script> 

	<script>
	if(navigator.appName == "Microsoft Internet Explorer" && navigator.appVersion .split(";")[1].replace(/[ ]/g,"")=="MSIE6.0" || navigator.appName == "Microsoft Internet Explorer" && navigator.appVersion .split(";")[1].replace(/[ ]/g,"")=="MSIE7.0" || navigator.appName == "Microsoft Internet Explorer" && navigator.appVersion .split(";")[1].replace(/[ ]/g,"")=="MSIE8.0" || navigator.appName == "Microsoft Internet Explorer" && navigator.appVersion .split(";")[1].replace(/[ ]/g,"")=="MSIE9.0")
	{
		location.href = '../../../webpage/error/ie.html';
	}
	</script>
</head>

<body class="no-skin" onload="trainPlanTip()">
		<!-- #section:basics/navbar.layout -->
		<div id="navbar" class="navbar navbar-default">
			<script type="text/javascript">
				try{ace.settings.check('navbar' , 'fixed')}catch(e){}
			</script>

			<div class="navbar-container" id="navbar-container">
				<!-- #section:basics/sidebar.mobile.toggle -->
				<button type="button" class="navbar-toggle menu-toggler pull-left" id="menu-toggler" data-target="#sidebar">
					<span class="sr-only">Toggle sidebar</span>

					<span class="icon-bar"></span>

					<span class="icon-bar"></span>

					<span class="icon-bar"></span>
				</button>

				<!-- /section:basics/sidebar.mobile.toggle -->
				<div class="navbar-header pull-left">
					<!-- #section:basics/navbar.layout.brand -->
					<a href="#" class="navbar-brand">
						<small>
							<i class="fa fa-leaf"></i>
							航天龙梦信息系统
						</small>
					</a>

					<!-- /section:basics/navbar.layout.brand -->

					<!-- #section:basics/navbar.toggle -->

					<!-- /section:basics/navbar.toggle -->
				</div>

				<!-- #section:basics/navbar.dropdown -->
				<div class="navbar-buttons navbar-header pull-right" role="navigation">
					<ul class="nav ace-nav">
						<li class="grey">
							<%-- <a id="lang-switch" class="lang-selector dropdown-toggle" href="#" data-toggle="dropdown" aria-expanded="true">
								<span class="lang-selected">
										<img  class="lang-flag" src="${ctxStatic}/common/img/china.png" alt="中国">
										<span class="lang-id">中国</span>
										<span class="lang-name">中文</span>
									</span>
							</a>

							<ul class="dropdown-menu-right dropdown-navbar dropdown-menu dropdown-caret dropdown-close">
								<li class="dropdown-header">
									<i class="ace-icon fa fa-check"></i>
									选择国家语言
								</li>

								<li class="dropdown-content">
									<ul class="dropdown-menu dropdown-navbar">
										<li>
											<a href="#" class="lang-select">
												<img class="lang-flag" src="${ctxStatic}/common/img/china.png" alt="中国">
												<span class="lang-id">中国</span>
												<span class="lang-name">中文</span>
											</a>
										</li>

										<li>
											<a href="#" class="lang-select">
												<img class="lang-flag" src="${ctxStatic}/common/img/united-kingdom.png" alt="English">
												<span class="lang-id">EN</span>
												<span class="lang-name">English</span>
											</a>
										</li>
										
										<li>
											<a href="#" class="lang-select">
												<img class="lang-flag" src="${ctxStatic}/common/img/france.png" alt="France">
												<span class="lang-id">FR</span>
												<span class="lang-name">Français</span>
											</a>
										</li>
										<li>
											<a href="#" class="lang-select">
												<img class="lang-flag" src="${ctxStatic}/common/img/germany.png" alt="Germany">
												<span class="lang-id">DE</span>
												<span class="lang-name">Deutsch</span>
											</a>
										</li>
										<li>
											<a href="#" class="lang-select">
												<img class="lang-flag" src="${ctxStatic}/common/img/italy.png" alt="Italy">
												<span class="lang-id">IT</span>
												<span class="lang-name">Italiano</span>
											</a>
										</li>
										<li>
											<a href="#" class="lang-select">
												<img class="lang-flag" src="${ctxStatic}/common/img/spain.png" alt="Spain">
												<span class="lang-id">ES</span>
												<span class="lang-name">Español</span>
											</a>
										</li>
									</ul>
								</li>

								<li class="dropdown-footer">
								</li> 
							</ul>--%>
						</li>
						
						

						<li class="purple">
							<a data-toggle="dropdown" class="dropdown-toggle" href="#" onclick="javascript:$('#newstips').hide();">
								<i class="ace-icon fa fa-bell icon-animated-bell"></i>
								<span class="badge badge-important" name="num1" >${count }</span>
							</a>
							
							<div class="tooltip left fadeIn" id="newstips" style="opacity:0.8; left:-205px; top:3px;display: none;">
                                <div class="tooltip-arrow"></div>
                                <div class="tooltip-inner" style="padding:10px;">
                                    <span class="glyphicon glyphicon-hand-right"></span>&nbsp;&nbsp;收到新的消息，请注意查看！
                                </div>
                            </div>

							<ul class="dropdown-menu-right dropdown-navbar navbar-pink dropdown-menu dropdown-caret dropdown-close">
								<li class="dropdown-header">
									<i class="ace-icon fa fa-exclamation-triangle"></i>
									<span name="num1">${count }</span> 条未读消息
								</li>

								<li class="dropdown-content">
									<ul class="dropdown-menu dropdown-navbar navbar-pink" name="area1" id="area1Data">
									
										  <c:forEach items="${page.list}" var="oaNotify">
										  	<li>
					                            <a class="J_menuItem" href="${ctx}/oa/oaNotify/view?id=${oaNotify.id}" onclick="changeOaNotifyCount('${oaNotify.id}',0)">
					                                        <div class="clearfix">
					                                            <i class="fa fa-envelope fa-fw"></i> ${fns:abbr(oaNotify.title,15)}
					                                            <span class="pull-right text-muted small">${fns:getTime(oaNotify.updateDate)}前</span>
					                                        </div>
					                             </a>
					                        </li>
										</c:forEach>
									</ul>
								</li>

								<li class="dropdown-footer">
									 <a class="J_menuItem" href="${ctx }/oa/oaNotify/selfnoread ">
										查看所有
										<i class="ace-icon fa fa-arrow-right"></i>
									</a>
								</li>
							</ul>
						</li>
						
						<li class="grey">
							<a data-toggle="dropdown" class="dropdown-toggle" href="#" onclick="javascript:$('#newstips').hide();">
								<i class="ace-icon fa fa-bullhorn icon-animated-vertical"></i>
								<span class="badge badge-important" name="num2">${taskcount }</span>
							</a>

							<ul class="dropdown-menu-right dropdown-navbar navbar-grey dropdown-menu dropdown-caret dropdown-close">
								<li class="dropdown-header">
									<i class="ace-icon fa fa-exclamation-triangle"></i>
									<span name="num2">${taskcount }</span> 条待办任务
								</li>

								<li class="dropdown-footer">
									 <a class="J_menuItem" href="${ctx}/leipiflow/leipiFlowApply/myLeipiTask/?type=0">
										查看所有
										<i class="ace-icon fa fa-arrow-right"></i>
									</a>
								</li>
							</ul>
						</li>

						<li class="green">
							<a data-toggle="dropdown" class="dropdown-toggle" href="#" onclick="javascript:$('#newstips').hide();">
								<i class="ace-icon fa fa-envelope icon-animated-bell"></i>
								<span class="badge badge-success" name="num3">${noReadCount}</span>
							</a>

							<ul class="dropdown-menu-right dropdown-navbar dropdown-menu dropdown-caret dropdown-close">
								<li class="dropdown-header">
									<i class="ace-icon fa fa-envelope-o"></i>
									<span name="num3">${noReadCount}</span> 未读邮件
								</li>

								<li class="dropdown-content">
									<ul class="dropdown-menu dropdown-navbar" id="area3Data">
										 <c:forEach items="${mailPage.list}" var="mailBox">
			                               	<li name="area3">
											<div class="clearfix" style="font-size:12px;">
												<c:choose>
													<c:when test="${mailBox.sender.photo==null||mailBox.sender.photo=='' }">
														<img src="${basePath }/images/nohead.jpg" class="msg-photo" alt="${mailBox.sender.name }的邮件" />
													</c:when>
													<c:otherwise>
														<img src="${basePath}${mailBox.sender.photo }" class="msg-photo" alt="${mailBox.sender.name }的邮件" />
													</c:otherwise>
												</c:choose>
												
												<span class="msg-body">
													<span class="msg-title">
														<span class="blue">${mailBox.sender.name }:</span><a class="J_menuItem" href="${ctx}/iim/mailBox/detail?id=${mailBox.id}" onclick="changeOaNotifyCount('${mailBox.id}',1)">
			                                             ${fns:abbr(mailBox.mail.title,20)}
			                                            </a>
													</span>

													<span class="msg-time">
														<i class="ace-icon fa fa-clock-o"></i>
														<span><fmt:formatDate value="${mailBox.sendtime}" pattern="yyyy-MM-dd HH:mm:ss"/></span>
													</span>
												</span>
											</div>
										</li>
		                                </c:forEach>
									

									</ul>
								</li>


							 	<li class="dropdown-footer">
		                              <a class="J_menuItem" href="${ctx}/iim/mailBox/list?orderBy=sendtime desc">
		                                       	查看所有邮件<i class="ace-icon fa fa-arrow-right"></i>
		                              </a>
		                      	</li>
							</ul>
						</li>

						<!-- #section:basics/navbar.user_menu -->
						<li class="light-blue">
							<a data-toggle="dropdown" href="#" class="dropdown-toggle">
								<img class="nav-user-photo" src="${basePath}${fns:getUser().photo}" alt="Jason's Photo" />
								<span class="user-info">
									<small>欢迎,</small>
									${fns:getUser().name}
								</span>

								<i class="ace-icon fa fa-caret-down"></i>
							</a>

							<ul class="user-menu dropdown-menu-right dropdown-menu dropdown-yellow dropdown-caret dropdown-close">
								<li>
									<a class="J_menuItem" href="${ctx}/sys/user/imageEdit">
										<i class="ace-icon fa fa-cog"></i>
										<span>修改头像</span>
									</a>
								</li>
								<li><a class="J_menuItem" href="${ctx}/sys/user/modifyPwdByBtn">
										<i class="ace-icon fa fa-lock"></i>
									&nbsp;<span>修改密码</span>
								    </a>
								</li>

								<li>
									<a class="J_menuItem" href="${ctx }/sys/user/info">
										<i class="ace-icon fa fa-user"></i>
										<span>个人资料</span>
									</a>
								</li>

                                <li>
                                	<a class="J_menuItem" href="${ctx }/iim/contact/index">
                                	<i class="ace-icon fa fa-indent"></i>
										<span>我的通讯录</span>
                                	</a>
                                </li>
                                <li>
                                	<a class="J_menuItem" href="${ctx }/iim/mailBox/list">
                                		<i class="ace-icon fa fa-inbox"></i>
										<span>信箱</span></a>
                                </li> 
                                 <li class="divider"></li>
                                <li><a href="#" onclick="changeStyle()">
                                	<i class="ace-icon fa  fa-mail-reply"></i>
                                	切换到经典模式</a>
                                </li> 


								<li class="divider"></li>

								<li>
									<a class="J_menuItem" href="${ctx}/logout">
										<i class="ace-icon fa fa-power-off"></i>
										安全退出
									</a>
								</li>
							</ul>
						</li>

						<!-- /section:basics/navbar.user_menu -->
					</ul>
				</div>

				<!-- /section:basics/navbar.dropdown -->
			</div><!-- /.navbar-container -->
		</div>

		<!-- /section:basics/navbar.layout -->
		<div class="main-container" id="main-container">
			<script type="text/javascript">
				try{ace.settings.check('main-container' , 'fixed')}catch(e){}
			</script>

			<!-- #section:basics/sidebar -->
			<div id="sidebar" class="sidebar                  responsive">
				<script type="text/javascript">
					try{ace.settings.check('sidebar' , 'fixed')}catch(e){}
				</script>

				<div class="sidebar-shortcuts" id="sidebar-shortcuts">
					<div class="sidebar-shortcuts-large" id="sidebar-shortcuts-large">
						<button class="btn btn-success">
							<i class="ace-icon fa fa-signal"></i>
						</button>

						<button class="btn btn-info">
							<i class="ace-icon fa fa-pencil"></i>
						</button>

						<!-- #section:basics/sidebar.layout.shortcuts -->
						<button class="btn btn-warning">
							<i class="ace-icon fa fa-users"></i>
						</button>

						<button class="btn btn-danger">
							<i class="ace-icon fa fa-cogs"></i>
						</button>

						<!-- /section:basics/sidebar.layout.shortcuts -->
					</div>

					<div class="sidebar-shortcuts-mini" id="sidebar-shortcuts-mini">
						<span class="btn btn-success"></span>

						<span class="btn btn-info"></span>

						<span class="btn btn-warning"></span>

						<span class="btn btn-danger"></span>
					</div>
				</div><!-- /.sidebar-shortcuts -->
					 <ul class="nav nav-list">
						 <t:aceMenu  menu="${fns:getTopMenu()}"></t:aceMenu>
						 <%--<t:aceinstitution institution="${fns:getTopInstitutionMenu()}"></t:aceinstitution>--%>
						  <!-- <li><a class="J_menuItem" href="http://172.19.0.8/iOffice/bylaw/MyHtml.htm" data-index="71"><i class="menu-icon fa fa-calendar"></i><span class="menu-text">集团值班信息</span></a><b class="arrow"></b></li>
						  <li><a class="J_menuItem" href="http://172.19.0.128:8081/8sHtml.htm" data-index="47"><i class="menu-icon fa fa-calendar"></i> <span class="menu-text">8S检查信息表</span></a></li>
						  <li><a class="J_menuItem" href="http://172.19.0.128:8081/mystruts/dbsum.action?param1=1" data-index="48"><i class="menu-icon fa fa-calendar"></i> <span class="menu-text">倒班交接信息表</span></a></li>
						  <li class="">
						    <a href="" class="dropdown-toggle"><i class="menu-icon fa fa-hand-paper-o"></i><span class="menu-text">每日团队风采</span><b class="arrow fa fa-angle-down"></b></a><b class="arrow"></b>
						    <ul class="submenu nav-hide" style="display: none;">
						      <li><a class="J_menuItem" href="/a/tools/email" data-index="65"><i class="menu-icon fa null"></i><span class="menu-text">666</span></a><b class="arrow"></b></li>
						    </ul>
						  </li> -->
					  </ul>
						 
				<!-- #section:basics/sidebar.layout.minimize -->
				<div class="sidebar-toggle sidebar-collapse" id="sidebar-collapse">
					<i class="ace-icon fa fa-angle-double-left" data-icon1="ace-icon fa fa-angle-double-left" data-icon2="ace-icon fa fa-angle-double-right"></i>
				</div>

				<!-- /section:basics/sidebar.layout.minimize -->
				<script type="text/javascript">
					try{ace.settings.check('sidebar' , 'collapsed')}catch(e){}
				</script>
			</div>

			<!-- /section:basics/sidebar -->
			<div class="main-content">
				<div class="main-content-inner">
					<!-- #section:basics/content.breadcrumbs -->
					<div class="breadcrumbs" id="breadcrumbs">
				  <div class="content-tabs">
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
					</div>

			<div class="J_mainContent"  id="content-main">
             <iframe class="J_iframe" name="iframe0" width="100%" height="100%" src="${ctx}/home" frameborder="0" data-id="${ctx}/home" seamless></iframe>
            </div>
            </div>
            
            
            </div>
            
            <div class="footer">
				<div class="footer-inner">
					<!-- #section:basics/footer -->
					<div class="footer-content">
						<marquee style="width:100%;" ><strong class="text-danger" id="systemNotificationArea">${systemNotification}</strong></marquee>
						<%--<span class="bigger-120">
							&lt;%&ndash;<span class="blue bolder">JINYING365</span>&ndash;%&gt;
							 &copy; 2018-2025
						</span>

						&nbsp; &nbsp;--%>
						<!-- <span class="action-buttons">
							<a href="#">
								<i class="ace-icon fa fa-twitter-square light-blue bigger-150"></i>
							</a>

							<a href="#">
								<i class="ace-icon fa fa-facebook-square text-primary bigger-150"></i>
							</a>

							<a href="#">
								<i class="ace-icon fa fa-rss-square orange bigger-150"></i>
							</a>
						</span> -->
					</div>

					<!-- /section:basics/footer -->
				</div>
			</div>

			<a href="#" id="btn-scroll-up" class="pull-left btn-scroll-up btn btn-sm btn-inverse">
				<i class="ace-icon fa fa-angle-double-up icon-only bigger-110"></i>
			</a>
			
            </div>




		<img src="" id="imageview" style="max-width:800px; max-height: 800px;display: none" />

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

        $('<audio id="chatAudio"><source src="${ctxStatic}/MP3/notify.ogg" type="audio/ogg"><source src="${ctxStatic}/MP3/notify.mp3" type="audio/mpeg"><source src="${ctxStatic}/MP3/notify.wav" type="audio/wav"></audio>').appendTo('body');
		$("a.lang-select").click(function(){
			$(".lang-selected").find(".lang-flag").attr("src",$(this).find(".lang-flag").attr("src"));
			$(".lang-selected").find(".lang-flag").attr("alt",$(this).find(".lang-flag").attr("alt"));
			$(".lang-selected").find(".lang-id").text($(this).find(".lang-id").text());
			$(".lang-selected").find(".lang-name").text($(this).find(".lang-name").text());
	
		});
	
		 /*dwr.engine.setActiveReverseAjax(true);
		 dwr.engine._errorHandler = function(message, ex) {dwr.engine._debug("Error: " + ex.name + ", " + ex.message, true);}; 
		 StocksPusher.onLoad('${fns:getUser().id}');*/
	});

	
	function showMsgCount(count){
		var jsonData = jQuery.parseJSON(count);
		if(jsonData.cnt1 != "-1"){$("span[name=num1]").html(jsonData.cnt1);$("ul[name=area1]").remove();}
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

		if(jsonData.msg != null && jsonData.msg != ""&& jsonData.msg != "[]"){
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
		var o = window.open('${ctx}/sys/user/remindWin?msg='+encodeURI(msg), 'newwindow', 'height=150, width=280, top='+height+',left='+width+',toolbar=no, menubar=no, scrollbars=no, resizable=false,location=no, status=no')
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
	function changeStyle(){
		   $.get('${pageContext.request.contextPath}/theme/default?url='+window.top.location.href,function(result){ window.location.reload();  });
		  

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
			//indextype=1表示第二套null为第一套
			$.post('${basePath}/oanotifycount',{"id":id,"indextype":"1"},function(data){
				var jsonData=jQuery.parseJSON(data);
				$("span[name=num1]").html(jsonData.notifycount);
				var html='';
				$.each(jsonData.lst,function(i,n){
					html+='<li>';
					html+='<a class="J_menuItem" href="${ctx}/oa/oaNotify/view?id='+n.id+'" onclick="changeOaNotifyCount(\''+n.id+'\',0)" >';
					html+='<div class="clearfix"><i class="fa fa-envelope fa-fw"></i> '+n.title;
					html+='<span class="pull-right text-muted small">'+n.millsecond+'前</span></div></a>';
					html+='</li>';
				});
				$("#area1Data").html(html);
				refreshDataIndex();
				$('.J_menuItem').on('click', menuItem);
			});
		}
		if(type==1){//刷新邮件通知
			$.post('${basePath}/mailcount',{"id":id,"indextype":"1"},function(data){
				var jsonData=jQuery.parseJSON(data);
				$("span[name=num3]").html(jsonData.mailcount);
				var html='';
				$.each(jsonData.lst,function(i,n){
                    html+='<li name="area3"><div class="clearfix" style="font-size:12px;">';
                    if(n.photo==null||n.photo==''){
                    	html+='<img src="${basePath }/images/nohead.jpg" class="msg-photo" alt="'+n.name+'的邮件" />';
                    }else{
                    	html+='<img src="'+n.photo+'" class="msg-photo" alt="'+n.name+'的邮件" />';
                    }
                    html+='<span class="msg-body"><span class="msg-title">';
					html+='<span class="blue">'+n.name+':</span><a class="J_menuItem" href="${ctx}/iim/mailBox/detail?id='+n.id+'" onclick="changeOaNotifyCount(\''+n.id+'\',1)">'+n.title+'</a>';
					html+='</span><span class="msg-time"><i class="ace-icon fa fa-clock-o"></i>';
					html+='<span>'+n.sendtime1+'</span></span></span></div></li>';
				});
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
    <%-- <script src="${ctxStatic}/layer-v2.3/layim/layer.min.js"></script>
    <script src="${ctxStatic}/layer-v2.3/layim/layim.js"></script> --%>
    <!--webscoket接口  -->
	<script src="${ctxStatic}/layer-v2.3/layim/layui/layui.js"></script>
	
	<script src="${ctxStatic}/layer-v2.3/layim/layim.js"></script>
	<!-- 即时聊天插件 结束 -->
<script>
    function trainPlanTip(){
        //提示
        $.post("${ctx}/trainPlanTip","",function () {

        });
    }
</script>
</html>