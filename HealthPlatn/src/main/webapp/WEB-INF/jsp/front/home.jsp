<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>

<html>

	<head>
		
		<!-- Meta Tags -->
		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width, initial-scale=1">
		
		<!-- Title -->
		<title>主页</title>
		
		
		
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
			<link href="css/jackbox-ie8.css" rel="stylesheet" type="text/css" />
			<link rel="stylesheet" href="css/ie.css">
        <![endif]-->
		
		<!--[if gt IE 8]>
			<link href="css/jackbox-ie9.css" rel="stylesheet" type="text/css" />
		<![endif]-->
		
		<!--[if IE 7]>
			<link rel="stylesheet" href="css/fontello-ie7.css">
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
// 			window.onload=function(){
// 			      parent.setiframe();
// 			};
		
// 		$('html').addClass('no-fouc');
		
// 		$(document).ready(function(){
			
// 			$('html').show();
			
// 			var window_w = $(window).width();
// 			var window_h = $(window).height();
// 			var window_s = $(window).scrollTop();
			
// 			$("body").queryLoader2({
// 				backgroundColor: '#f2f4f9',
// 				barColor: '#63b2f5',
// 				barHeight: 4,
// 				percentage:false,
// 				deepSearch:true,
// 				minimumTime:1000,
// 				onComplete: function(){
					
// 					$('.animate-onscroll').filter(function(index){
					
// 						return this.offsetTop < (window_s + window_h);
						
// 					}).each(function(index, value){
						
// 						var el = $(this);
// 						var el_y = $(this).offset().top;
						
// 						if((window_s) > el_y){
// 							$(el).addClass('animated fadeInDown').removeClass('animate-onscroll');
// 							setTimeout(function(){
// 								$(el).css('opacity','1').removeClass('animated fadeInDown');
// 							},2000);
// 						}
						
// 					});
					
// 				}
// 			});
			
// 		});
		</script>
		
		<style type="text/css">
		
		</style>
	</head>
	
	<body class="sticky-header-on tablet-sticky-header" style="padding-top:-60px;" >
	<!-- Container -->
<!-- 	<div class="container"> -->
		<section id="content">
			
			<!-- Section -->
			<section class="section full-width-bg">
				
				<div class="row">
				
					<div class="col-lg-12 col-md-12 col-sm-12">
						
						<!-- Revolution Slider -->
						<div class="tp-banner-container main-revolution">
						
							<span class="Apple-tab-span"></span>
 
							<div class="tp-banner">
								
								<ul>
									<li data-transition="papercut" data-slotamount="7">
										<img src="<%=basePath %>static/front_UI/img/slide1.jpg" alt="">
										<div class="tp-caption"  data-x="100" data-y="115" data-speed="700" data-start="1000" data-easing="easeOutBack"><h2>Unity<br>Liberty<br>Solidarity</h2></div>
										<div class="tp-caption"  data-x="100" data-y="310" data-speed="500" data-start="1200" data-easing="easeOutBack"><a href="#" class="button big">Find out more</a></div>
									</li>
									
									<li data-transition="papercut" data-slotamount="7">
										<img src="<%=basePath %>static/front_UI/img/slide2.jpg" alt="">
										<div class="tp-caption align-center" data-x="center" data-y="135" data-speed="700" data-start="1000" data-easing="easeOutBack"><h4 class="great-vibes">it’s time for changes</h4></div>
										<div class="tp-caption align-center" data-x="center" data-y="220" data-speed="500" data-start="1200" data-easing="easeOutBack"><h2>Building Our Future Together!</h2></div>
										<div class="tp-caption align-center" data-x="center" data-y="300" data-speed="300" data-start="1400"><a href="#" class="button big button-arrow">Get Involved</a></div>
									</li>
									
									<li data-transition="papercut" data-slotamount="7">
										<img src="<%=basePath %>static/front_UI/img/slide3.jpg" alt="">
										<div class="tp-caption align-right" data-x="right" data-hoffset="-100" data-y="150" data-speed="700" data-start="1000" data-easing="easeOutBack"><h2>10 YEARS OF EXPERIENCE</h2></div>
										<div class="tp-caption align-right" data-x="right" data-hoffset="-100" data-y="225" data-speed="500" data-start="1200" data-easing="easeOutBack"><p>Mauris fermentum dictum magna. Sed laoreet aliquam leo. Ut tellus dolor,<br> dapibus eget, elementum vel, cursus eleifend, elit. </p></div>
										<div class="tp-caption align-right" data-x="right" data-hoffset="-100" data-y="305" data-speed="300" data-start="1400"><a href="#" class="button big button-arrow">More Info</a></div>
									</li>
								</ul>
								
							</div>
						 
						</div>
						<!-- /Revolution Slider -->
						
					</div>
				</div>	
				
				<!-- 公司简介 -->
				<div class="row">
						
					<div class="col-lg-12 col-md-12 col-sm-12">
				
						<h3 class="animate-onscroll no-margin-top">公司简介</h3>
						
						<p class="animate-onscroll">    Vivamus eget nibh. Etiam cursus leo vel metus. Nulla facilisi. Aenean nec eros. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Suspendisse sollicitudin velit sed leo. Ut pharetra augue nec augue. Nam elit agna, endrerit sit amet, tincidunt ac, viverra sed, nulla. Donec porta diam eu massa. </p>
						
						<p class="animate-onscroll">    Quisque diam lorem, interdum vitae, dapibus ac, scelerisque vitae, pede. Donec eget tellus non erat lacinia fermentum. Donec in velit vel ipsum auctor pulvinar. Vestibulum iaculis lacinia est. Proin dictum elementum velit. Fusce euismod consequat ante. Lorem ipsum dolor sit amet, consectetuer adipis. Mauris accumsan nulla vel diam. Sed in lacus ut enim adipiscing aliquet.</p>
						
						<p class="animate-onscroll">    Vestibulum sed ante. Donec sagittis euismod purus.Sed ut perspiciatis sit voluptatem accusantium doloremque laudantium, totam rem aperiam,eaque ipsa quae ab illo inventore veritatis et quasi architecto beatae vitae dicta sunt explicabo. Nemo enim ipsam voluptatem quia voluptas sit aspernatur aut odit aut fugit, sed quia consequuntur magni dolores eos qui ratione voluptatem sequi nesciunt. </p>
					
					</div>
							
				</div>
			</section>
			<!-- selection -->
			
			<!-- Section -->
			<section class="section full-width-bg gray-bg">
				
				<div class="row">
				
					<div class="col-lg-9 col-md-9 col-sm-8">
						
						<h3 class="animate-onscroll no-margin-top">公司资讯</h3>
						<!-- 公司资讯 -->
						<c:forEach items="${fpd.comNews }" var="item">
							<div class="blog-post big animate-onscroll">
								
								<div class="post-image">
									<img src="img/blog/post1.jpg" alt="">
								</div>
								
								<h4 class="post-title"><a href="blog-single-sidebar.html">${item.title }</a></h4>
								
								<div class="post-meta">
									<span>${item.editime != null ? item.editime : item.creatime}</span>
								</div>
								
								<p>${item.remark }</p>
								
								<a href="blog-single-sidebar.html" class="button read-more-button big button-arrow">更多</a>
								
							</div>
						</c:forEach>
						<!-- /Blog Post -->						
					</div>
					
					
					
					<!-- Sidebar -->
					<div class="col-lg-3 col-md-3 col-sm-4 sidebar">
						
												<!-- Upcoming Events -->
						<div class="sidebar-box white animate-onscroll">
							<h3>Upcoming Events</h3>
							<ul class="upcoming-events">
							
								<!-- Event -->
								<li>
									<div class="date">
										<span>
											<span class="day">25</span>
											<span class="month">DEC</span>
										</span>
									</div>
									
									<div class="event-content">
										<h6><a href="event-post-v2.html">Sed in lacus ut enim</a></h6>
										<ul class="event-meta">
											<li><i class="icons icon-clock"></i> 4:00 pm - 6:00 pm</li>
											<li><i class="icons icon-location"></i> 340 W 50th St.New York</li>
										</ul>
									</div>
								</li>
								<!-- /Event -->
								
								<!-- Event -->
								<li>
									<div class="date">
										<span>
											<span class="day">5</span>
											<span class="month">JAN</span>
										</span>
									</div>
									
									<div class="event-content">
										<h6><a href="event-post-v2.html">Sed in lacus ut enim</a></h6>
										<ul class="event-meta">
											<li><i class="icons icon-clock"></i> 4:00 pm - 6:00 pm</li>
											<li><i class="icons icon-location"></i> 340 W 50th St.New York</li>
										</ul>
									</div>
								</li>
								<!-- /Event -->
								
								<!-- Event -->
								<li>
									<div class="date">
										<span>
											<span class="day">8</span>
											<span class="month">JAN</span>
										</span>
									</div>
									
									<div class="event-content">
										<h6><a href="event-post-v2.html">Sed in lacus ut enim</a></h6>
										<ul class="event-meta">
											<li><i class="icons icon-clock"></i> 4:00 pm - 6:00 pm</li>
											<li><i class="icons icon-location"></i> 340 W 50th St.New York</li>
										</ul>
									</div>
								</li>
								<!-- /Event -->
								
							</ul>
							<a href="#" class="button transparent button-arrow">More events</a>
						</div>
						<!-- /Upcoming Events -->						
					</div>
					<!-- /Sidebar -->
					
				</div>
				
				
				
				<div class="row no-margin-bottom">
				
					
					<div class="col-lg-12 col-md-12 col-sm-12">
						
												
						<!-- Owl Carousel -->
						<div class="owl-carousel-container">
							
							<div class="owl-header">
								
								<h3 class="animate-onscroll">健康资讯</h3>
								
								<div class="carousel-arrows animate-onscroll">
									<span class="left-arrow"><i class="icons icon-left-dir"></i></span>
									<span class="right-arrow"><i class="icons icon-right-dir"></i></span>
								</div>
								
							</div>
							
							<div class="owl-carousel" data-max-items="3">
								<c:forEach items="${fpd.healthNews }" var="item">
								<!-- Owl Item -->
								<div>
									
									<!-- Blog Post -->
									<div class="blog-post animate-onscroll">
										
										<div class="post-image">
											<img src="img/blog/post2.jpg" alt="">
										</div>
										
										<h4 class="post-title"><a href="blog-single-sidebar.html">${item.title }</a></h4>
										
										<div class="post-meta">
											<span>${item.creatime }</span>
										</div>
										
										<p>${item.remark }</p>
										
										<a href="blog-single-sidebar.html" class="button read-more-button big button-arrow">更多</a>
										
									</div>
									<!-- /Blog Post -->
									
								</div>
								<!-- /Owl Item -->
								</c:forEach>
								
							</div>
						
						</div>
						<!-- /Owl Carousel -->						
					</div>
					
					
				
				</div>
				
				
			</section>
			<!-- /Section -->
		
		</section>

			
			<!-- Back To Top -->
			<a href="#" id="button-to-top"><i class="icons icon-up-dir"></i></a>
		
<!-- 		</div> -->
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
			<script type="text/javascript" src="js/jquery.placeholder.js"></script>
			<script type="text/javascript" src="js/script_ie.js"></script>
		<![endif]-->
		
		
	</body>

</html>