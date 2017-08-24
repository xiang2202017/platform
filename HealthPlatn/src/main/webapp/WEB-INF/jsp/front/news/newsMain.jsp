<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html lang="en">
<head>
	<base href="<%=basePath%>">
	<link rel="stylesheet" href="static/css/ace.min.css" />
	<link href="static/css/bootstrap.min.css" rel="stylesheet" />
	
	<script type="text/javascript">
	$(function(){
		var urlstr = "<%=basePath%>"+ $("#firstInp").val();
		$("#urlInp").val(urlstr);
	});
	
	function openNewsMenu(menuUrl){
		var urlstr = "<%=basePath%>"+menuUrl;
		$("#urlInp").val(urlstr);	//分页时用
		alert(123+$("#urlInp").val());
		$.ajax({
            url:urlstr,
            dataType:"html",
            type:"get",
            success:function(data){
                //div加载页面
                $("#listDiv").html(data);
            }
        });
	}
	
	</script>
</head>
<body>
<input id="urlInp" type="hidden">
<section id="content">	
			
			<!-- Section -->
			<section class="section full-width-bg gray-bg">
				
				<div class="row">
				
					<div id="listDiv" class="col-lg-9 col-md-9 col-sm-8 col-lg-push-3 col-md-push-3 col-sm-push-4">
						<jsp:include page="newsList.jsp"></jsp:include>
					</div>
					
					<!-- Sidebar -->
					<div class="col-lg-3 col-md-3 col-sm-4 col-lg-pull-9 col-md-pull-9 col-sm-pull-8 sidebar">
						<c:forEach items="${fpd.submenulist}" var="menu" varStatus="status">
							<c:if test="${status.count==1}">
								<input id="firstInp" type="hidden" value="${menu.menu_url}">
							</c:if>
							<div class="banner-wrapper">
								<a class="banner animate-onscroll" style="cursor: pointer;" onclick="openNewsMenu('${menu.menu_url}')">
									<h4>${menu.menu_name }</h4>
								</a>
							</div>
						</c:forEach>
					</div>
					<!-- /Sidebar -->
				
				</div>
				
			</section>
			<!-- /Section -->
		
		</section>
	
	<!-- basic scripts -->
		<!-- 引入 -->
		<script src="static/js/bootstrap.min.js"></script>
		<script src="static/js/ace-elements.min.js"></script>
		<script src="static/js/ace.min.js"></script>
</body>
</html>
