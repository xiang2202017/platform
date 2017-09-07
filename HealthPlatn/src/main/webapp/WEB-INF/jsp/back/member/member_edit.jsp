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
	
	
		<style type="text/css">
		#uploadDiv{float: left;width: 100%;}
		#waitDiv{position: absolute;z-index: 999999;width:100%; height:100%;}
		#dialog-add,#dialog-message,#dialog-comment{width:100%; height:100%; position:fixed; top:0px; z-index:99999999; display:none;}
		.commitopacity{position:absolute; width:100%; height:700px; background:#7f7f7f; filter:alpha(opacity=50); -moz-opacity:0.5; -khtml-opacity: 0.5; opacity: 0.5; top:0px; z-index:99999;}
		.commitbox{width:100%; margin:0px auto; position:absolute; top:120px; z-index:99999;}
		.commitbox_inner{width:96%; height:255px;  margin:6px auto; background:#efefef; border-radius:5px;}
		.commitbox_top{width:100%; height:250px; margin-bottom:10px; padding-top:10px; background:#FFF; border-radius:5px; box-shadow:1px 1px 3px #e8e8e8;}
		.commitbox_top textarea{width:95%; height:195px; display:block; margin:0px auto; border:0px;}
		.commitbox_cen{width:95%; height:40px; padding-top:10px;}
		.commitbox_cen div.left{float:left;background-size:15px; background-position:0px 3px; padding-left:18px; color:#f77500; font-size:16px; line-height:27px;}
		.commitbox_cen div.left img{width:30px;}
		.commitbox_cen div.right{float:right; margin-top:7px;}
		.commitbox_cen div.right span{cursor:pointer;}
		.commitbox_cen div.right span.save{border:solid 1px #c7c7c7; background:#6FB3E0; border-radius:3px; color:#FFF; padding:5px 10px;}
		.commitbox_cen div.right span.quxiao{border:solid 1px #f77400; background:#f77400; border-radius:3px; color:#FFF; padding:4px 9px;}
		</style>
	
	<script type="text/javascript">
		
		
	
	</script>
	
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
			<div class="widget-header widget-header-blue widget-header-flat wi1dget-header-large">
				<h4 class="lighter">修改产品</h4>
			</div>
			<div class="widget-body">
			 
			 
				<div class="widget-main">
								<div class="step-content row-fluid position-relative">

									<form action="<%=basePath%>back/updateMember" name="memberForm" id="memberForm" method="post">
										<div id="zhongxin">
											<table style="width:100%;" id="xtable">
												<tr>
													<td align="right">会员编号</td>
													<td style="margin-top:0px;">
													   <input type="hidden" name="id" value="${pd.id }">
														<input type="text" name="memberNo"  readonly="readonly" id="memberNo" value="${pd.memberNo}"/>
													</td>
												</tr>
												<tr>
													<td align="right">会员姓名</td>
													<td style="margin-top:0px;">
														<input type="text" name="memberName" id="memberName" readonly="readonly" value="${pd.memberName}"/>
													</td>
												</tr>
												
												<tr>
													<td align="right">密码</td>
													<td>
														 <input type="text" readonly="readonly" name="password" id="password" value="${pd.password}"/>
													</td>
												</tr>
												
												<tr>
													<td width="20%" align="right">会员分类</td>
													<td>
														<select name="memberType" id="memberType">
															<c:forEach items="${typeList }" var="item">
																<option value="${item.id }" <c:if test="${pd.memberType == item.id}">selected</c:if>>${item.name }</option>     
															</c:forEach>
														</select>
													</td>
												</tr>
												
												<tr>
													<td align="right">身份证号</td>
													<td>
														 <input type="text" readonly="readonly" name="idcardNo" id="idcardNo" value="${pd.idcardNo}"/>
													</td>
												</tr>
												<tr>
													<td align="right">手机号码</td>
													<td>
														 <input type="text" readonly="readonly" name="phone" id="phone" value="${pd.phone}"/>
													</td>
												</tr>
												<tr>
													<td align="right">地址</td>
													<td>
														 <input type="text" readonly="readonly" name="address" id="address" value="${pd.address}"/>
													</td>
												</tr>
												<tr>
													<td align="right">公司</td>
													<td>
														 <input type="text" readonly="readonly" name="company" id="company" value="${pd.company}"/>
													</td>
												</tr>
												
												<tr>
												<td align="right">会员状态</td>
												<td>
													 <select name="status" id="status">
														<option value="1" <c:if test="${pd.status == '1'}">selected</c:if>>正常</option>   
														<option value="2" <c:if test="${pd.status == '2'}">selected</c:if>>已过期</option>
													</select>
												</td>
												</tr>
												
												<tr>
												<td align="right">过期时间</td>
												<td>
													  <input type="text" name="expireDate" id="expireDate" value="${pd.expireDate}"/>
												</td>
												</tr>
												
												<tr>
												<td></td>
												<td id="nr" align="right">
														<button type="button" onclick="formSubmit()">保存</button>
														<button type="reset" onclick="cancel()">取消</button>
												</td>
											</tr>
											</table>
										</div>
									</form>



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
			window.UEDITOR_HOME_URL = "/xxxx/xxxx/";
			$(function() {
				var contentv = $("#contentInp").val();
				 UE.delEditor('editor');
				 var ue = UE.getEditor('editor');
				 
			 	 ue.ready(function() {//编辑器初始化完成再赋值  
		            ue.setContent(contentv);  //赋值给UEditor  
		         });
			});
			
			function formSubmit(){
				$("#memberForm").submit();
			}
			
			function cancel(){
				history.back();
			}
		</script>
		
	</body>
</html>

