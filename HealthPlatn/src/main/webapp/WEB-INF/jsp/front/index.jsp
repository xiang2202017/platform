<%@page import="com.health.entity.system.FMenu"%>
<%@page import="java.util.List"%>
<%@page import="com.health.system.util.Const"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	List<FMenu> fmenulist = (List<FMenu>)session.getAttribute(Const.SESSION_FRONT_MENULIST);
%>
<!DOCTYPE html>

<html>

	<head>
		
		<!-- Meta Tags -->
		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width, initial-scale=1">
		
		<!-- Title -->
		<title>主页</title>
		
		<!-- Favicon -->
		<link rel="shortcut icon" type="image/x-icon" href="<%=basePath %>static/front_UI/img/favicon.ico">
		
		<!-- Stylesheets -->
		<link href="<%=basePath %>static/front_UI/css/bootstrap.min.css" rel="stylesheet" type="text/css">
		<link href="<%=basePath %>static/front_UI/css/fontello.css" rel="stylesheet" type="text/css">
		<link href="<%=basePath %>static/front_UI/css/flexslider.css" rel="stylesheet" type="text/css">
		<link href="<%=basePath %>static/front_UI/js/revolution-slider/css/settings.css" rel="stylesheet" type="text/css" media="screen" />
		<link href="<%=basePath %>static/front_UI/css/owl.carousel.css" rel="stylesheet" type="text/css">
		<link href="<%=basePath %>static/front_UI/css/responsive-calendar.css" rel="stylesheet" type="text/css">
		<link href="<%=basePath %>static/front_UI/css/chosen.css" rel="stylesheet" type="text/css">
		<link href="<%=basePath %>static/front_UI/jackbox/css/jackbox.min.css" rel="stylesheet" type="text/css" />
		<link href="<%=basePath %>static/front_UI/css/cloud-zoom.css" rel="stylesheet" type="text/css" />
		<link href="<%=basePath %>static/front_UI/css/style.css" rel="stylesheet" type="text/css">

		<!--[if lt IE 9]>
            <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
			<link href="<%=basePath %>static/front_UI/css/jackbox-ie8.css" rel="stylesheet" type="text/css" />
			<link rel="stylesheet" href="<%=basePath %>static/front_UI/css/ie.css">
        <![endif]-->
		
		<!--[if gt IE 8]>
			<link href="<%=basePath %>static/front_UI/css/jackbox-ie9.css" rel="stylesheet" type="text/css" />
		<![endif]-->
		
		<!--[if IE 7]>
			<link rel="stylesheet" href="<%=basePath %>static/front_UI/css/fontello-ie7.css">
		<![endif]-->
		
		<style type="text/css">
			.no-fouc {display:none;}
	  	</style>
		
		<!-- jQuery -->
		<script src="<%=basePath %>static/front_UI/js/jquery-1.11.0.min.js"></script>
		<script src="<%=basePath %>static/front_UI/js/jquery-ui-1.10.4.min.js"></script>
		
		<!-- Preloader -->
		<script src="<%=basePath %>static/front_UI/js/jquery.queryloader2.min.js"></script>
		
		<script type="text/javascript">
		$('html').addClass('no-fouc');
		
		$(document).ready(function(){
			
			$('html').show();
			
			var window_w = $(window).width();
			var window_h = $(window).height();
			var window_s = $(window).scrollTop();
			
			$("body").queryLoader2({
				backgroundColor: '#f2f4f9',
				barColor: '#63b2f5',
				barHeight: 4,
				percentage:false,
				deepSearch:true,
				minimumTime:1000,
				onComplete: function(){
					
					$('.animate-onscroll').filter(function(index){
					
						return this.offsetTop < (window_s + window_h);
						
					}).each(function(index, value){
						
						var el = $(this);
						var el_y = $(this).offset().top;
						
						if((window_s) > el_y){
							$(el).addClass('animated fadeInDown').removeClass('animate-onscroll');
							setTimeout(function(){
								$(el).css('opacity','1').removeClass('animated fadeInDown');
							},2000);
						}
						
					});
					
				}
			});
			
		});
		
		//打开菜单
		function openMenu(menuUrl,menuId){
			alert(menuId);
			$("#menuId").val(menuId);
			var urlstr = "<%=basePath%>"+menuUrl;
			//+"?menuId="+menuId+"&menutype=1&tm="+new Date().getTime();
			//alert(urlstr);
			$("#menuForm").attr("action",urlstr); 
			$("#menuForm").submit();
// 		
		}
		
		</script>
		
	</head>
	
	<body class="sticky-header-on tablet-sticky-header">
	
	<div id="fb-root"></div>
	<script>(function(d, s, id) {
// 	  var js, fjs = d.getElementsByTagName(s)[0];
// 	  if (d.getElementById(id)) return;
// 	  js = d.createElement(s); js.id = id;
// 	  js.src = "//connect.facebook.net/en_US/all.js#xfbml=1";
// 	  fjs.parentNode.insertBefore(js, fjs);
// 	}(document, 'script', 'facebook-jssdk'));
	</script>		
		<!-- Container -->
		<div class="container">
			
			
			<!-- Header -->
			<header id="header" class="animate-onscroll">
				<!-- Main Header -->
				<div id="main-header">
					<div class="container">
					<div class="row">
						<!-- Logo -->
						<div id="logo" class="col-lg-3 col-md-3 col-sm-3">
							<a href="main-v1.html"><img src="static/front_UI/img/logo.png" alt="Logo"></a>
						</div>
						<!-- /Logo -->
						<!-- Main Quote -->
						<div class="col-lg-5 col-md-4 col-sm-4">
							<blockquote>Nam elit agna,enderit sit amet, tinciunt ac,<br>viverra sed, nulla..</blockquote>
						</div>
						<!-- /Main Quote -->
					</div>
					</div>
				</div>
				<!-- /Main Header -->
				<!-- Lower Header -->
				<div id="lower-header">
					<div class="container">
					<div id="menu-button">
						<div>
						<span></span>
						<span></span>
						<span></span>
						</div>
						<span>菜单</span>
					</div>
					<form id="menuForm" style="display: none;" method="post">
						<input type="hidden" id="menuId" name="menuId">
					</form>
					<ul id="navigation">
						<c:forEach items="<%=fmenulist %>" var="menu">
							<li id="${menu.menu_id }">
							<a style="cursor:pointer;" onclick="openMenu('${menu.menu_url}','${menu.menu_id }')" >
								<span>${menu.menu_name }</span>
							</a>
							<ul>
								<c:if test="${menu.subMenu != null }">
									<c:forEach items="${menu.subMenu}" var="sub">
										<li id="${sub.menu_id }">
											<a style="cursor:pointer;" onclick="openMenu('${menu.menu_url}','${menu.menu_id }')">
												${sub.menu_name }
											</a>
										</li>
									</c:forEach>
								</c:if>
							</ul>
							</li>
						</c:forEach>
						<!-- Donate -->
						<li class="donate-button ">
							<a href="#">购物车</a>
						</li>
						<!-- /Donate -->
					</ul>
					</div>
				</div>
				<!-- /Lower Header -->
			</header>
			<!-- /Header -->

					
			<!-- center -->
			<div id="mfDiv">
				<jsp:include page="${fpd.pagePath }"></jsp:include>
<!-- 				<iframe name="mainFrame" id="mainFrame" frameborder="0" src="<%=basePath %>web/midIndex.do" style="margin:0 auto;width:100%;height:100%;" ></iframe> -->
			</div>
			<!-- center -->

			<!-- /Footer -->
			<footer>
				<!-- Lower Footer -->
				<div id="lower-footer">
					<div class="row">
						<div class=" animate-onscroll">
							<p class="copyright" align="center">© 2014 Candidate. All Rights Reserved.More Templates <a href="http://www.cssmoban.com/" target="_blank" title="模板之家">模板之家</a> - Collect from <a href="http://www.cssmoban.com/" title="网页模板" target="_blank">网页模板</a></p>
						</div>
					</div>
				</div>
				<!-- /Lower Footer -->
			</footer>
			<!-- /Footer -->
			
			
			
			<!-- Back To Top -->
			<a href="#" id="button-to-top"><i class="icons icon-up-dir"></i></a>
		
		</div>
		<!-- /Container -->	
	
		<!-- JavaScript -->
		
		<!-- Bootstrap -->
		<script type="text/javascript" src="<%=basePath %>static/front_UI/js/bootstrap.min.js"></script>
		
		<!-- Modernizr -->
		<script type="text/javascript" src="<%=basePath %>static/front_UI/js/modernizr.js"></script>
		
		<!-- Sliders/Carousels -->
		<script type="text/javascript" src="<%=basePath %>static/front_UI/js/jquery.flexslider-min.js"></script>
		<script type="text/javascript" src="<%=basePath %>static/front_UI/js/owl.carousel.min.js"></script>
		
		<!-- Revolution Slider  -->
		<script type="text/javascript" src="<%=basePath %>static/front_UI/js/revolution-slider/js/jquery.themepunch.plugins.min.js"></script>
		<script type="text/javascript" src="<%=basePath %>static/front_UI/js/revolution-slider/js/jquery.themepunch.revolution.min.js"></script>
		
		<!-- Calendar -->
		<script type="text/javascript" src="<%=basePath %>static/front_UI/js/responsive-calendar.min.js"></script>
		
		<!-- Raty -->
		<script type="text/javascript" src="<%=basePath %>static/front_UI/js/jquery.raty.min.js"></script>
		
		<!-- Chosen -->
		<script type="text/javascript" src="<%=basePath %>static/front_UI/js/chosen.jquery.min.js"></script>
		
		<!-- jFlickrFeed -->
		<script type="text/javascript" src="<%=basePath %>static/front_UI/js/jflickrfeed.min.js"></script>
		
		<!-- InstaFeed -->
		<script type="text/javascript" src="<%=basePath %>static/front_UI/js/instafeed.min.js"></script>
		
		<!-- Twitter -->
		<script type="text/javascript" src="<%=basePath %>static/front_UI/php/twitter/jquery.tweet.js"></script>
		
		<!-- MixItUp -->
		<script type="text/javascript" src="<%=basePath %>static/front_UI/js/jquery.mixitup.js"></script>
		
		<!-- JackBox -->
		<script type="text/javascript" src="<%=basePath %>static/front_UI/jackbox/js/jackbox-packed.min.js"></script>
		
		<!-- CloudZoom -->
		<script type="text/javascript" src="<%=basePath %>static/front_UI/js/zoomsl-3.0.min.js"></script>
		
		<!-- Main Script -->
		<script type="text/javascript" src="<%=basePath %>static/front_UI/js/script.js"></script>
		
		
		<!--[if lt IE 9]>
			<script type="text/javascript" src="<%=basePath %>static/front_UI/js/jquery.placeholder.js"></script>
			<script type="text/javascript" src="<%=basePath %>static/front_UI/js/script_ie.js"></script>
		<![endif]-->
		
		
	</body>

</html>