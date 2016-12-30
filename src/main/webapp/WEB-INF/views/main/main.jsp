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
<meta name="author" content="jjf">

<link rel="stylesheet" href="${ctx}/static/css/reset.css">
<link rel="stylesheet" href="${ctx}/static/css/supersized.css">
<link rel="stylesheet" href="${ctx}/static/css/style.css">
<link rel="stylesheet" href="${ctx}/static/css/base/jquery-ui-1.9.2.custom.min.css">
<link rel="stylesheet" href="${ctx}/static/css/base/sweetalert.css">
<script src="${ctx}/static/js/jquery-1.8.2.min.js"></script>
<script src="${ctx}/static/js/supersized.3.2.7.min.js"></script>
<script src="${ctx}/static/js/supersized-init.js"></script>
<script src="${ctx}/static/js/scripts.js"></script>
<script src="http://static.geetest.com/static/tools/gt.js"></script>
<script src="${ctx}/static/js/ui/jquery-ui-1.9.2.custom.min.js"></script>
<script src="${ctx}/static/js/ui/sweetalert.min.js"></script>
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
.registion {
    width: 100px;
    right: 20px;
    position: absolute;
    top: 20px;
}
.register-modal {
    width:350px;
	height:auto;
    margin: 15% auto 0 auto;
}
</style>
</head>

<body>
<div id="load">
	<div class="registion"><span id="regist" style="cursor: pointer;">我要注册</span></div>
	<div class="page-container">
		<h1>登录</h1>
		<form action="${ctx}/verifyLoginManage/verifyLogin" method="post">
			<input type="text" name="username" class="username" id="username"
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
    </div>
    <div id="modal" class="register-modal" hidden>
    <h1>注册</h1>
    <form>
    	<input type="text" name="newName" class="username" id="newName" title=""
				placeholder="请输入您的用户名！"> 
		<input type="password" id="newPassword" title="密码必须6-12个字符"
				name="newPassword" class="password" placeholder="密码必须6-12个字符"> 			
        <input type="password" id="rePassword" title="两次输入密码要一致"
				name="rePassword" class="password" placeholder="请再次输入您的用户密码！"> 			
    <button id="register-button" type="submit" class="submit_button">注册</button>
    </form>
    </div>
</body>
<div style="text-align: center;"></div>
<script type="text/javascript">
var ctx = "${ctx}";
var userName = "${userName}";
var passWord = "${passWord}";
var code = "${code}";
var message ="${message}";
$(document).ready(function() {
	$("#regist").click(function(){
		$("#load").hide();
		$("#modal").show();
	})
	if(code =="0001"){
		$("#name").attr("placeholder",message);
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
	
	$("#register-button").click(function(e){
		
		if($("#newName").val()==""||$("#newPassword").val()==""||$("#rePassword").val()==""){
			e.preventDefault();
			swal("请先填写注册信息！");
			return;
		}else if( $("#rePassword").val() != $("#newPassword").val() ) {
				$("#rePassword").val("");
				$("#rePassword").attr("placeholder","两次密码输入不一致");
				e.preventDefault();
				return;
		}
		var data ={};
		$(data).attr("name",$("#newName").val());
		$(data).attr("passWord",$("#newPassword").val());
		$.ajax({
			url: ctx+"/main/registerUser",
			type:"post",
			contentType:"application/json;charset=utf-8",
			data:JSON.stringify(data),
			dataType:"json",
			async:false,
			success: function(data){  
				if(data && data.CODE && data.CODE == "SUCCESS"){
					e.preventDefault();
					 swal({
							title: "注册成功", 
							text: "您确定返回登录吗？", 
							type: "warning",
							showCancelButton: true,
							closeOnConfirm: true,
							confirmButtonText: "是的，我要登录",
							confirmButtonColor: "#ec6c62"
							}, function() {
								window.history.back(-1); 
							});
					
				}
	        },  
	        error: function(data){  
	        	swal("OMG!", "注册失败", "error");
	            e.preventDefault();
	        }  
			
		})
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
		$("#username").val(userName);
	}
	if(passWord != ""){
		$("#password").val(passWord);
	}
}
$("#newPassword").blur(function(){
	var obj = $("#newPassword");
	if(obj.val().length < 6 || obj.val().length > 12){
		obj.val("");
		obj.attr("placeholder","密码长度不符合要求");
	}
});

</script>
</html>
