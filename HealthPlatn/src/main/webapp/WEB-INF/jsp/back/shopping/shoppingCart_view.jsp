<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html lang="en">
	<head>
	<base href="<%=basePath%>">
	<!-- jsp文件头和头部 -->
	<%@ include file="../top.jsp"%> 
	</head> 
<body>
	
<div id="waitDiv" style="display: none;background-color: gray;">
	<img src="<%=basePath%>static/img/wait.gif"/>
</div>

<div class="container-fluid" id="main-container">



<div id="page-content" class="clearfix">
						
  <div class="row-fluid">


 	<div class="span12">
		<div class="widget-box">
			<div
				class="widget-header widget-header-blue widget-header-flat wi1dget-header-large"
				style="min-height:38px;height: 38px; line-height: 38px;padding-right: 8px">
				<h4 class="lighter">购物车商品详情</h4>
				<a class="lighter" style="cursor: pointer; padding-right: 8px;"
					onclick="returntoList()">返回</a>
			</div>
			<div class="widget-body">
			 
			 
				<div class="widget-main">
								<div class="step-content row-fluid position-relative">

									<div id="zhongxin">
										<table style="width:100%;" id="xtable">
											<tr>
												<td align="right">会员号</td>
												<td style="margin-top:0px;">
												   <input type="hidden" name="id" value="${pd.id }">
													<input type="text" name="memberNo"  readonly="readonly" id="memberNo" value="${pd.memberNo}"/>
												</td>
											</tr>
											<tr>
												<td align="right">手机号</td>
												<td style="margin-top:0px;">
													<input type="text" name="phone" id="phone" readonly="readonly" value="${pd.phone}"/>
												</td>
											</tr>
											<tr>
												<td align="right">商品名</td>
												<td style="margin-top:0px;">
													<input type="text" readonly="readonly" value="${pd.productName}"/>
												</td>
											</tr>
											<tr>
												<td align="right">单价</td>
												<td style="margin-top:0px;">
													<input type="text" readonly="readonly" value="${pd.price}"/>
												</td>
											</tr>
											<tr>
												<td align="right">数量</td>
												<td style="margin-top:0px;">
													<input type="text" readonly="readonly" value="${pd.num}"/>
												</td>
											</tr>
											<tr>
												<td align="right">商品状态</td>
												<td style="margin-top:0px;">
													<input type="text" readonly="readonly" value="${pd.status}"/>
												</td>
											</tr>
											<tr>
												<td align="right">加入时间</td>
												<td style="margin-top:0px;">
													<input type="text" name="createTime" id="createTime" readonly="readonly" value="${pd.createTime}"/>
												</td>
											</tr>
										</table>
									</div>

								</div>
							</div>
							<!--/widget-main-->
			</div><!--/widget-body-->
		</div>
	</div>
 
 
 
	<!-- PAGE CONTENT ENDS HERE -->
  </div><!--/row-->
	
</div><!--/#page-content-->
</div><!--/.fluid-container#main-container-->
		
		<!-- 返回顶部  -->
		<a href="#" id="btn-scroll-up" class="btn btn-small btn-inverse">
			<i class="icon-double-angle-up icon-only"></i>
		</a>
		
		<!-- 引入 -->
		<script type="text/javascript">window.jQuery || document.write("<script src='static/js/jquery-1.7.2.js'>\x3C/script>");</script>
		<script type="text/javascript" src="static/js/ajaxfileupload.js"></script>
		<script src="static/js/bootstrap.min.js"></script>
		<script src="static/js/ace-elements.min.js"></script>
		<script src="static/js/ace.min.js"></script>
		<!-- 引入 -->
		
		<!-- 编辑框-->
		<script type="text/javascript" charset="utf-8">window.UEDITOR_HOME_URL = "<%=path%>/plugins/ueditor/";</script>
		<script type="text/javascript" charset="utf-8" src="plugins/ueditor/ueditor.config.js"></script>
		<script type="text/javascript" charset="utf-8" src="plugins/ueditor/ueditor.all.js"></script>
		<script type="text/javascript" charset="utf-8" src="plugins/ueditor/ueditor.parse.js"></script>
		<!-- 编辑框-->
		
		<!--提示框-->
		<script type="text/javascript" src="static/js/jquery.tips.js"></script>
		<script type="text/javascript" src="static/js/bootbox.js"></script>
		<!--引入属于此页面的js -->
		<script type="text/javascript" src="static/js/myjs/toolEmail.js"></script>	
		<script type="text/javascript" src="static/js/myjs/image.js"></script>	
		<script type="text/javascript">
			$(top.hangge());
	
			function returntoList() {
				history.back();
			}
		</script>
		
	</body>
</html>

