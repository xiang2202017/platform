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
<!--
	function getDetail(newsId){
		var urlstr = "<%=basePath%>web/newsDetail.do";
		$.ajax({
            url:urlstr,
            data:{"newsId":newsId},
            dataType:"html",
            type:"get",
            success:function(data){
            	alert(data);
                //div加载页面
                $("#listDiv").html(data);
            }
        });
	}
//-->
</script>

<h3 class="animate-onscroll no-margin-top">${title}</h3>
<!-- 资讯 -->
<c:forEach items="${fpd.newslist }" var="item">
	<div class="blog-post big animate-onscroll">
		
		<div class="post-image">
			<img src="img/blog/post1.jpg" alt="">
		</div>
		
		<h4 class="post-title"><a href="blog-single-sidebar.html">${item.title }</a></h4>
		
		<div class="post-meta">
			<span>${item.editime != null ? item.editime : item.creatime}</span>
		</div>
		
		<p>${item.remark }</p>
		
		<a style="cursor: pointer;" onclick="getDetail('${item.id}')"  class="button read-more-button big button-arrow">更多</a>
		
	</div>
</c:forEach>
<div class="pagination" style="float: right;padding-top: 0px;margin-top: 0px;">${page.frontPageStr}</div>

<!--

//-->
