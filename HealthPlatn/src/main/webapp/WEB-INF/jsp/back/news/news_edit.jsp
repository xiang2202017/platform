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
		function refreshImg(){
			var url=$("#uploadimg").val();  
			var extend=url.substring(url.indexOf(".")+1);  
	        var ext=new Array("jpg","jpeg","png","gif","bmp");  
	        if(ext.toString().indexOf(extend)==-1){  
            	alert("您上传的格式不正确，仅支持jpg、jpeg、png、gif、bmp,请重新选择！");
            	return;
            }
            
            //$("#waitDiv").show();
            //上传文件到服务器
             var time = new Date().getTime();
            $.ajaxFileUpload({  
	        	url: '<%=basePath%>back/uploadMainImg.do?tm='+time, //后台提交地址 
                secureuri: false,//异步 "
	            fileElementId: "uploadimg",//上传控件ID  
	            dataType: "json",//返回的数据信息格式  
                type:"post", //如果带附加参数，请设置type  
	            success: function(data, status) {  
	            	if (data.msg == 'success') {  
	                	var attach= data.path;  
	                    $("#preImg").attr("src", attach); 
	                    $("#mainImgPath").val(attach); 
	                   // alert("上传成功");  
	                } else {  
	                    alert("服务器故障，稍后再试！");  
	                }
	                $("#waitDiv").hide(); 
	            }, 
	            error: function(data, status, e) {  
	                alert(e);
	                $("#waitDiv").hide(); 
	            }  
           });
           alert(3);
		}
		
		
	
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
				<h4 class="lighter">修改资讯</h4>
			</div>
			<div class="widget-body">
			 
			 
			 <div class="widget-main">
			 <div class="step-content row-fluid position-relative">

				<form action="back/updateNews" name="newsForm" id="newsForm" method="post">
					<div id="zhongxin">
					<textarea name="CONTENT" id="CONTENT" style="display:none" ></textarea>
					<input type="hidden" id="contentTxt" name="contentTxt"/>
					<table style="width:100%;" id="xtable">
						<tr>
							<td width="20%" align="right">资讯分类</td>
							<td>
								<input type="hidden" name="id" value="${pd.id }">
								<input type="hidden" name="clickNum" value="${pd.clickNum }">
								<select name="type" id="type">
									<option value="1" <c:if test="${pd.type == 1}">selected</c:if>>公司资讯</option>   
							        <option value="2" <c:if test="${pd.type == 2}">selected</c:if>>健康资讯</option>   
								</select>
							</td>
						</tr>
						<tr>
							<td align="right">标题</td>
							<td style="margin-top:0px;">
								<input type="text" name="title" id="title" value="${pd.title}" style="width:95%"/>
							</td>
						</tr>
						<tr>
							<td align="right">关键字</td>
							<td>
								 <input type="text" name="keywords" id="keywords" value="${pd.keywords}" style="width:95%"/>
							</td>
						</tr>
						<tr>
							<td align="right" valign="top">主图片</td>
							<td valign="middle">
								<img id="preImg" style="width:280px;height:200px"  alt="" src="${pd.imgPath}">
								<div id="uploadDiv">
									<input id="uploadimg" name="uploadimg" type="file" value="选择图片" onchange="refreshImg()" accept="image/*">
<!-- 									<input type="button" value="上传"> -->
									<input id="mainImgPath" name="mainImgPath" type="hidden">
									<label>选择图片尺寸为：100*50</label>
								</div>
								
							</td>
						</tr>
						<tr>
							<td></td>
							<td>
								<img id="imgPath" name="imgPath" alt="" src="">
							</td>
						</tr>
						<tr>
							<td align="right">作者</td>
							<td>
								 <input type="text" name="author" id="author" value="${pd.author }" style="width:95%"/>
							</td>
						</tr>
						<tr>
							<td align="right" valign="top">资讯内容</td>
							<td id="nr">
								<input type="hidden" id="contentInp" value="${pd.content }">
								 <script id="editor" type="text/plain" style="width:96%;height:259px;"></script>
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
			 </div><!--/widget-main-->
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
				var error = "";
				var title = $("#title").val();
				var keywords = $("#keywords").val();
				if(title.length == 0 ){
					error += "标题不能为空\n";
				}
				if(keywords.length == 0){
					error += "关键字不能为空\n";
				}
				//alert(error);
				if(error.length != 0){
					bootbox.alert(error);
					return;
				}
				
				var txt = UE.getEditor('editor').getContentTxt();
				$("#contentTxt").val(txt);
				$("#newsForm").submit();
			}
			
			function cancel(){
				history.back();
			}
		</script>
		
	</body>
</html>

