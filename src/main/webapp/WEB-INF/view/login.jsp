<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" import="java.util.*" %>
<%--<%@ page isELIgnored="false"%>--%>

<!DOCTYPE html>
<html>
<head>
	<title></title>
	<link rel="stylesheet" type="text/css" href="https://cdn.bootcss.com/bootstrap/3.3.0/css/bootstrap.css">
	<link rel="stylesheet" type="text/css" href="css/wangEditor.css">
	<link rel="stylesheet" type="text/css" href="css/base.css">
	<link rel="stylesheet" type="text/css" href="css/login.css">
</head>
<body>
<%@ include file="header.jsp" %>

<div class="container">
	<!-- 中间主体板块 -->
	<div class="main w clearfix">
		<div class="buttons clearfix">
			<a href="#" id="login-button" class="selected col-xs-6 col-md-6"><span class="glyphicon glyphicon-user"></span>&nbsp;登录</a>
			<a href="#" id="register-button" class="unselected col-xs-6 col-md-6"><span class="glyphicon glyphicon-pencil"></span>&nbsp;注册</a>
		</div>
		<div class="contents">
			<div id="login-area">
				<form action="login.do" method="post">
					<div class="error-message">${error}</div>
					<div class="email col-xs-12 col-md-12">
						<label>邮箱&nbsp;</label>
						<input id="login-email" type="text" name="email" value="${email}" class="form-control" required>
					</div>
					<div class="password col-xs-12 col-md-12">
						<label>密码&nbsp;</label>
						<input type="password" name="password" required class="form-control">
					</div>
					<div class="col-xs-12 col-md-12">
					<button id="login-submit" class="col-xs-12 col-md-12 btn-success">立即登录</button>
					<button type="button" id="forget-password" class="col-xs-12 col-md-12">忘记密码</button>
					</div>
				</form>
			</div>
			<div id="register-area">
				<form action="register.do" method="post">
					<div id="error-message" class="error-message">${error}</div>
					<div class="email col-xs-12 col-md-12">
						邮箱&nbsp;
						<input type="text" name="email" value="${email}" id="email" required class="form-control">
					</div>
					<div class="password col-xs-12 col-md-12">
						密码&nbsp;
						<input type="password" name="password" id="password" required class="form-control">
					</div>
					<div class="password relative clearfix col-xs-12 col-md-12">
						<label>重复密码&nbsp;</label>
						<input type="password" name="repassword" id="repassword" required class="form-control">
					</div>
					<div class="col-xs-12 col-md-12">
						<button id="register-submit" class="col-xs-12 col-md-12 btn-success">立即注册</button>
					</div>
				</form>
			</div>
		</div>
	</div><!-- 主体结束 -->
</div>


<%@ include file="footer.jsp" %>
<script type="text/javascript" src="https://cdn.bootcss.com/jquery/3.2.1/jquery.min.js"></script>
<script type="text/javascript" src="js/base.js"></script>
<script type="text/javascript">
	$(function(){
		var loginButton = $("#login-button");
		var registerButton = $("#register-button");
		var lArea = $("#login-area");
		var rArea = $("#register-area");
		rArea.hide();

		loginButton.click(function(){
			loginButton.addClass("selected");
			loginButton.removeClass("unselected");
			registerButton.addClass("unselected");
			registerButton.removeClass("selected");
			lArea.show();
			rArea.hide();
		});

		registerButton.click(function(){
			registerButton.addClass("selected");
			registerButton.removeClass("unselected");
			loginButton.addClass("unselected");
			loginButton.removeClass("selected");
			lArea.hide();
			rArea.show();
		});

        if(location.href.indexOf("#register")>=0){
            registerButton.click();
        }else{
            loginButton.click();
        }

        //根据是否是注册跳回来，切换到注册页面
        var hideInfo = "${register}";
        if(hideInfo!=null && hideInfo!=""){
            registerButton.click();
        }


		//输入校验
        //校验邮箱
        $("#email").blur(function() {
            var value = $(this).val();
            if (!/^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\.[a-zA-Z0-9_-]{2,3}){1,2})$/.test(value)) {
                $("#error-message").text("邮箱格式错误啦~");
            }else{
                $("#error-message").text("");
            }
        });

		//忘记密码
        $("#forget-password").click(function(){
            //alert($("#login-email").val());
            $.ajax({
                type:"GET",
                url:"forgetPassword.do",
                data:{email:$("#login-email").val()},
                success:function(response,status,xhr){
                    location.href="afterForgetPassword.do";
                }
            });
        });
	});


</script>
</body>
</html>

