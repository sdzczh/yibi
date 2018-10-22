<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!doctype html>
<html>
<head>
  <meta charset="utf-8">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width,initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no" />
<meta content="telephone=no" name="format-detection" />
<!-- uc强制竖屏 -->
<meta name="screen-orientation" content="portrait">

<!-- UC强制全屏 --> 
<meta name="full-screen" content="yes">

<!-- UC应用模式 --> 
<meta name="browsermode" content="application">

<!-- QQ强制竖屏 -->
<meta name="x5-orientation" content="portrait">

<!-- QQ强制全屏 -->
<meta name="x5-fullscreen" content="true">

<!-- QQ应用模式 -->
<meta name="x5-page-mode" content="app">
  <title>一币注册</title>
  <meta name="renderer" content="webkit">
  <meta http-equiv="Cache-Control" content="no-siteapp"/>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/amazeui.min.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/app.css">
</head>
<body>
<div class="am-g">
	<!-- LOGO -->
	<div class="am-u-sm-12 am-text-center" style="margin-top: 20%">
		 <img src="http://img.yb.link/fai/eTr/pictureTr/201807280151012770.jpg" style="WIDTH: 50px;!important;">
		<h2 style="margin-top: 1em; !important;">一币注册</h2>
	</div>
	
	<div style="position: fixed;top: 0;width: 100%;height: 50px;background: #828282;z-index: 99;opacity: 0.9">
    <p style="font-size: 16px;line-height: 50px;float: left;margin: 0 0 0 10px;color: #FFFFFF">一币APP下载</p>
    <a href="https://www.yb.link/xiazhai.html" target="_black" style="float: right;display: inline-block;font-size: 14px;width: 80px;height: 40px;margin: 5px 20px 0 0;background-color: #4D94FF;color: #FFFFFF;border-radius: 8px;text-align: center;line-height: 40px;">点击下载</a>
    </div>
	
	<!-- 登陆框 -->
	<div class="am-u-sm-11 am-u-sm-centered">
	<form class="am-form">
	  <fieldset class="myapp-login-form am-form-set">
		<div class="am-form-group am-form-icon">
			<i class="am-icon-user"></i>
			<input type="text" class="myapp-login-input-text am-form-field" placeholder="请输入您的手机号" id="phone">
		</div>
	    <div class="am-form-group am-form-icon">
			<i class="am-icon-lock"></i>
			<input type="password" class="myapp-login-input-text am-form-field" placeholder="6-18位数字与字母组合密码" id="password">
		</div>
	    <div class="am-form-group am-form-icon">
			<i class="am-icon-user-plus"></i>
			<input type="text" class="myapp-login-input-text am-form-field" value="${phone }" placeholder="邀请人帐号" id="referPhone" readonly="readonly">
		</div>
	    <div class="am-form-group am-form-icon">
			<i class="am-icon-user-plus"></i>
			<input type="text" id="VerificationCode" name="VerificationCode" class="myapp-login-input-text am-form-field" placeholder="请输入验证码" style="width: 60%;float: left;margin-top: -1px">
			<a  onclick="getCode()" >
				<img id="randCodeImage" alt="获取验证码"  src="" width="100" height="40"/></a></div>
	    <div class="am-form-group am-form-icon">
			<i class="am-icon-tablet"></i>
			<input type="hidden" name="smsCodeId" id="smsCodeId">
			<input type="text" class="myapp-login-input-text am-form-field"  placeholder="手机验证码" style="width:60%;float: left;margin-top: -2px" id="code">
			<input class="myapp-login-form-submit am-btn am-btn-primary am-btn-block " style="width: 40%" type="button" id="smsbtn" value="获取短信验证码" onclick="getSmsCode(this)" />
		</div>
		<input id="checkbox" name="checkbox" type="checkbox"> 我已阅读并同意<a href="${pageContext.request.contextPath}/web/doc/6.action">《一币用户使用协议》</a>
	  </fieldset>
	  <a onclick="login()" class="myapp-login-form-submit am-btn am-btn-primary am-btn-block ">注 册</a>
	</form><br><br><br>
	</div>

</div>
<script src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
<script src="${pageContext.request.contextPath}/js/amazeui.min.js"></script>
<script src="${pageContext.request.contextPath}/js/app.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/layer.js"></script>
</body>

<script type="text/javascript">
    //重新获得验证码
    function getCode(){
        var phone = $("#phone").val();
        if(!phone){
            layer.msg("请输入手机号！", {icon:2,time:1000});
            return false;
        }
        if(!(/^1\d{10}$/.test(phone))){
            layer.msg("请输入正确的手机号!",{icon:2,time:1000});
            return false;
        }
        var rad = Math.floor(Math.random() * Math.pow(10, 8));
        $("#randCodeImage").attr("src","${pageContext.request.contextPath }/verificationCode/generate.action?phone="+phone+"&ra="+rad);
    }
</script>

<script type="text/javascript">
	var countdown=60; 
	function settime(obj) { 
	    if (countdown == 0) { 
	        obj.removeAttribute("disabled");    
	        obj.value="取验证码"; 
	        countdown = 60; 
	        return;
	    } else { 
	        obj.setAttribute("disabled", true); 
	        obj.value="重新发送(" + countdown + ")"; 
	        countdown--; 
	    } 
		setTimeout(function() {  settime(obj); } ,1000); 
	}
	function getSmsCode(obj){
		$("#code").val("");
		var phone = $("#phone").val();
		if(!phone){
			layer.msg("请输入手机号！", {icon:2,time:1000});
			return false;
		}
		if(!(/^1\d{10}$/.test(phone))){ 
			layer.msg("请输入正确的手机号!",{icon:2,time:1000});
	        return false; 
	    }
        var password=$("#password").val();
        if(!password){
            layer.msg("请输入密码!",{icon:2,time:1000});
            return false;
        }
        var verificationCode=$("#VerificationCode").val();
        if (verificationCode == null || verificationCode == ""){
            layer.msg("请输入验证码！", {icon:2,time:1000});
            return false;
        }

		$.post("${pageContext.request.contextPath}/web/smscode.action",
	 		{"phone":phone,
			 "type" : 1,
			 "VerificationCode" :verificationCode
			},
	 		function(result){
	 			var code = result.code;
		 		if(code==10000){
		 			settime(obj);
		 			$("#smsbtn").attr("disabled","disabled");
					$("#smsCodeId").val(result.data.codeId);
				}
				else if(code==70004){
					layer.msg("验证码错误！", {icon:2,time:1000});
                    $("#VerificationCode").val("");
                    getCode();
					return false;
				}
				else if(code==20005){
					layer.msg("手机号已注册！", {icon:2,time:1000});
					return false;
				}
				else if(code==70001){
					layer.msg("功能已关闭！", {icon:2,time:1000});
					return false;
				}
				else{
					layer.msg("验证码获取失败！", {icon:2,time:1000});
					return false;
				}
		  });
	}
	function login(){
		var s = $("#checkbox").prop("checked");
		var phone=$("#phone").val();
		var password=$("#password").val();
		var code = $("#code").val();
		var referPhone=$("#referPhone").val();
		var codeId=$("#smsCodeId").val();
	
		if(!phone){
			layer.msg("请输入手机号!",{icon:2,time:1000});
			return false;
		}
		if(!password){
			layer.msg("请输入密码!",{icon:2,time:1000});
			return false;
		}
		if(!code){
			layer.msg("请输入验证码!",{icon:2,time:1000});
			return false;
		}
		
		if(!codeId){
			layer.msg("请先获取验证码!",{icon:2,time:1000});
			return false;
		}

		
		if(!(/^1\d{10}$/.test(phone))){ 
			layer.msg("请输入正确的手机号!",{icon:2,time:1000});
	        return false; 
	    } 
		if(!(/^\d{6}$/.test(code))){ 
			layer.msg("请输入正确的验证码!",{icon:2,time:1000});
	        return false; 
	    } 
		if(!(/^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,18}$/.test(password))){ 
			layer.msg("请输入正确的密码!",{icon:2,time:1000});
	        return false; 
	    } 
		if(!s){
			layer.msg("请勾选注册协议!",{icon:2,time:1000});
	        return false; 
		}
		 $.post("${pageContext.request.contextPath}/web/submitRegister.action",
		 		{"phone":phone,
				 "userPassword" : password,
				 "code" : code,
				 "codeId" : codeId,
				 "referPhone" : referPhone},
		 		function(result){
		 			var code = result.code;
		 			
			 		if(code==10000){
						layer.msg("注册成功!", {icon:1,time:1000, });
						setTimeout(function(){window.location.reload();},2000);
					}else if(code==30003){
						layer.msg("验证码错误!", {icon:2,time:1000});
						return false;
					}else if(code==30004||code == 30005){
						layer.msg("验证码已过期!", {icon:2,time:1000});
						return false;
					}else if(code==30006){
						layer.msg("推荐人手机号不存在!", {icon:2,time:1000});
						return false;
					}else if(code == 30007){
						layer.msg("推荐人手机号已冻结!", {icon:2,time:1000});
						return false;
					}else if(code == 30008){
						layer.msg("推荐人手机号注销!", {icon:2,time:1000});
						return false;
					}else if(code == 20005){
						layer.msg("帐号已存在!", {icon:2,time:1000});
						return false;
					}else{
						layer.msg("注册失败!", {icon:2,time:1000});
						return false;
					}
					
			  });
	}
	
</script>
</html>