<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>登录(Login)</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="">
<meta name="author" content="">

<!-- CSS -->
<link rel="stylesheet" href="${ctx}/static/css/reset.css">
<link rel="stylesheet" href="${ctx}/static/css/supersized.css">
<link rel="stylesheet" href="${ctx}/static/css/style.css">


<!-- HTML5 shim, for IE6-8 support of HTML5 elements -->
<!--[if lt IE 9]>
            <script src="assets/js/html5.js"></script>
        <![endif]-->
<style type="text/css">
.Captcha {
	width: 290px;
	margin:0 auto;
	margin-top: 25px;
}

.show {
	display: block;
}

.hide {
	display: none;
}

#notice {
	color: red;
}
.error {
position:absolute;


}
</style>
</head>

<body>

	<div class="page-container">
		<h1>登录(Login)</h1>
		<form action="${ctx}/verifyLoginManage/verifyLogin" method="post">
			<input type="text" name="username" class="username" id="name"
				placeholder="请输入您的用户名！"> <input type="password" id="password"
				name="password" class="password" placeholder="请输入您的用户密码！"> 
<!-- 			<input	type="Captcha" class="Captcha" name="Captcha" placeholder="请输入验证码！"> -->
			<div id="embed-captcha" class="Captcha"></div>
    		<p id="wait" class="show">正在加载验证码......</p>
    		<p id="notice" class="hide">请先拖动验证码到相应位置</p>
			
			<button id="submit" type="submit" class="submit_button">登录</button>
			<div class="error">
				<span>+</span>
			</div>
		</form>
<!-- 		<div class="connect"> -->
<!-- 			<p>快捷</p> -->
<!-- 			<p> -->
<!-- 				<a class="facebook" href=""></a> <a class="twitter" href=""></a> -->
<!-- 			</p> -->
<!-- 		</div> -->
	</div>

	<!-- Javascript -->
	<script src="${ctx}/static/js/jquery-1.8.2.min.js"></script>
	<script src="${ctx}/static/js/supersized.3.2.7.min.js"></script>
	<script src="${ctx}/static/js/supersized-init.js"></script>
	<script src="${ctx}/static/js/scripts.js"></script>
	<script src="http://static.geetest.com/static/tools/gt.js"></script>

</body>
<div style="text-align: center;"></div>
<script type="text/javascript">
var ctx = "${ctx}";
var userName = "${userName}";
var passWord = "${passWord}";
var code = "${code}";
var message ="${message}";
$(document).ready(function() {
	if(code =="0001"){
		$("#name").attr("placeholder",message);
		alert(message);
	}else if(code == "0002"){
		$("#password").attr("placeholder",message);
	}
	$.ajax({
	    // 获取id，challenge，success（是否启用failback）
	    url: ctx+"/verifyLoginManage?t=" + (new Date()).getTime(), // 加随机数防止缓存
	    type: "get",
	    dataType: "json",
	    success: function (data) {
	        // 使用initGeetest接口
	        // 参数1：配置参数
	        // 参数2：回调，回调的第一个参数验证码对象，之后可以使用它做appendTo之类的事件
	        initGeetest({
	            gt: data.gt,
	            challenge: data.challenge,
	            product: "float", // 产品形式，包括：float，embed，popup。注意只对PC版验证码有效
	            offline: !data.success // 表示用户后台检测极验服务器是否宕机，一般不需要关注
	            // 更多配置参数请参见：http://www.geetest.com/install/sections/idx-client-sdk.html#config
	        }, handlerEmbed);
	    }
	});
});

var handlerEmbed = function (captchaObj) {
    $("#submit").click(function (e) {
        var validate = captchaObj.getValidate();
        if (!validate) {
            $("#notice")[0].className = "show";
            setTimeout(function () {
                $("#notice")[0].className = "hide";
            }, 2000);
            e.preventDefault();
        }
    });
    // 将验证码加到id为captcha的元素里，同时会有三个input的值：geetest_challenge, geetest_validate, geetest_seccode
    captchaObj.appendTo("#embed-captcha");
    captchaObj.onReady(function () {
        $("#wait")[0].className = "hide";
    });
    // 更多接口参考：http://www.geetest.com/install/sections/idx-client-sdk.html
};

function setValue(){
	if(userName !=""){
		$("#name").val(userName);
	}
	if(passWord != ""){
		$("#password").val(passWord);
	}
}

</script>
</html>
