<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<script type="text/javascript">

	//搜索产品名
	function search(){
	
	}
	
	//产品菜单子菜单点击
	function openProductMenu(url){
		
	}

</script>

<!-- Section -->
<section id="content">
	<section class="section full-width-bg gray-bg">
		<div class="row">
		
			<!-- Sidebar -->
			<div class="col-lg-3 col-md-3 col-sm-4 sidebar">
				
				
				<!-- Top Rated Products -->
				<div class="sidebar-box white animate-onscroll">
					<ul class="shop-items-widget">
						<c:forEach items="${fpd.submenulist}" var="menu">
							<li>
								<div class="shop-item-content">
									<h6><a onclick="openProductMenu('${menu.menu_url}')">${menu.menu_name }</a></h6>
								</div>
							</li>
						</c:forEach>
						
					</ul>
					
				</div>
				<!-- /Instagram Photos -->						<!-- Top Rated Products -->
				<div class="sidebar-box white animate-onscroll">
					
					<h3>Featured products</h3>
					
					<ul class="shop-items-widget">
						
						<li>
							<div class="featured-image">
								<img src="img/shop/item4.jpg" alt="">
							</div>
							<div class="shop-item-content">
								<h6><a href="shop-productpage.html">Woo Album #1</a></h6>
								<span class="price">£9</span>
							</div>
						</li>
						
						<li>
							<div class="featured-image">
								<img src="img/shop/item6.jpg" alt="">
							</div>
							<div class="shop-item-content">
								<h6><a href="shop-productpage.html">Woo Ninja</a></h6>
								<span class="price">£20</span>
							</div>
						</li>
						
						<li>
							<div class="featured-image">
								<img src="img/shop/item7.jpg" alt="">
							</div>
							<div class="shop-item-content">
								<h6><a href="shop-productpage.html">Woo Logo</a></h6>
								<span class="price">£15</span>
							</div>
						</li>
						
					</ul>
					
				</div>
				<!-- /Top Rated Products -->						
				
			</div>
			<!-- /Sidebar -->
			
			<div class="col-lg-9 col-md-9 col-sm-8">
				<div class="row shop-items">
				
					<div class="shop-header animate-onscroll">
						<span>产品名：<input id="name" name="name"> <button onclick="search()">搜索</button></span>
					</div>
					
					<c:forEach items="${productList }" var="item">
						<div class="col-lg-4 col-md-4 col-sm-6 mix" data-dateorder="1" data-popularorder="2" data-avgratingorder="2" data-priceorder="2">
							<!-- Shop Item -->
							<div class="shop-item animate-onscroll">
								<div class="shop-image">
									<a >
										<div class="shop-featured-image">
											<img src="img/shop/item1.jpg" alt="">
										</div>
									</a>
								</div>
								<div class="shop-content">
									<h4><a >${item.name }</a></h4>
									<span class="price">${item.price }</span>
									<a href="#" class="button add-to-cart-button transparent">加入购物车</a>
									<a href="shop-productpage.html" class="button details-button button-arrow transparent">产品详情</a>
								</div>
								
							</div>
							<!-- /Shop Item -->
						
						</div>	
					</c:forEach>
				</div>
			</div>			
		</div>
	</section>
</section>