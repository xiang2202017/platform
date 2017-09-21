<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<script type="text/javascript" src="<%=basePath %>static/js/jquery.tips.js"></script>

<script type="text/javascript">
	
	//注册登录界面控制
// 	function toRegister(){
// 		$("#loginDIV,#registerDIV").toggle();
// 	}

	//登录
	function login(){
		if(loginCheck()){
			var no = $("#no").val();
			var pwd = $("#pw").val();
			$.ajax({
				type: "POST",
				url: "<%=basePath%>web/member/member_login",
				data: {no:no,pwd:pwd,tm:new Date().getTime()},
				dataType:'json',
				cache: false,
				success: function(data){
					alert(data);
					if("success" == data.result){
						toMainPage();
					}else if("usererror" == data.result){
						$("#no").tips({
							side : 1,
							msg : "用户名或密码有误",
							bg : '#FF5080',
							time : 15
						});
						$("#no").focus();
					}else if("apperror" == data.result){
						alert("程序发生未知错误");
					}else if(data.result == "roleerror"){
						alert("用户验证失败");
					}
				}
			});
		}
	}
	
	
	function loginCheck(){
	 	if($("#no").val() == ""){
	 		$("#no").tips({
				side : 1,
				msg : '会员号不得为空',
				bg : '#AE81FF',
				time : 3
			});

			$("#no").focus();
			return false;
	 	}
		if($("#pw").val() == ""){
			$("#pw").tips({
				side : 1,
				msg : '密码不得为空',
				bg : '#AE81FF',
				time : 3
			});

			$("#pw").focus();
			return false;
		}
		return true; 
	}
	
	
	//登录注册完成后跳转到会员主页面
	function toMainPage(){
		$.ajax({
            url:"<%=basePath%>web/member/toMemberMain",
            dataType:"html",
            type:"post",
            success:function(data){
                //div加载页面
                $("#mfDiv").html(data);
            }
        });
	}
	
	function openRegister(){
		$.ajax({
            url:"<%=basePath%>web/member/to_member_register",
            dataType:"html",
            data: {tm:new Date().getTime()},
            type:"post",
            success:function(data){
                //div加载页面
                $("#mfDiv").html(data);
            }
        });
	}
</script>

<div class="panel panel-default" style="width:100%; border: 1px gray solid ;text-align:center;">
								
	<div class="panel-body" style="width:50%;display:inline-block;">
		
		<h5>登录</h5>
		
		<label>会员号</label>
		<input id="no" name="no" type="text" placeholder="请输入会员号或手机号码" value="">
		<br><br>
		<label>密码</label>
		<input type="password" id="pw" name="password" placeholder="请输入密码" value="">
		<br><br>
	</div>
	<div class="align-center animate-onscroll">
		<input type="button" value="提交" onclick="login()">
		<a onclick="openRegister()">注册</a>
	</div>
</div>									
