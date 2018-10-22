<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/static/commons/global.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>一币后台管理登录页面   |  HuoLi International</title>
    <link href='https://fonts.googleapis.com/css?family=Open+Sans:400,300,600,700' rel='stylesheet' type='text/css'>
    <link href="${ctx}/static/css/bootstrap.min.css" rel="stylesheet">
    <link href="${ctx}/static/css/nifty.min.css" rel="stylesheet">
    <link href="${ctx}/static/css/demo/nifty-demo-icons.min.css" rel="stylesheet">
    <link href="${ctx}/static/css/demo/nifty-demo.min.css" rel="stylesheet">
    <link href="${ctx}/static/plugins/magic-check/css/magic-check.min.css" rel="stylesheet">
    <link href="${ctx}/static/plugins/pace/pace.min.css" rel="stylesheet">
    <script src="${ctx}/static/plugins/pace/pace.min.js"></script>
    <script src="${ctx}/static/js/jquery-2.2.4.min.js"></script>
    <script src="${ctx}/static/js/bootstrap.min.js"></script>
    <script src="${ctx}/static/js/nifty.min.js"></script>
    <script src="${ctx}/static/js/demo/bg-images.js"></script>
	<script src="${ctx}/static/easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript">
		//防止在iframe中载入登录界面
        window.onload=function(){
            if (top != self){
                top.location.href = "${ctx}/login/login.do";
            }
        };

        // 登录
        function submitForm(){
            var formObj = $("#loginform");
            $.ajax({
                url : '${ctx}/login/doLogin.do',
                data : formObj.serialize(),
                type : "POST",
                //dataType : "json",
                success : function(result) {
                    if (result.success) {
                        window.location.href = '${ctx}/login/index.do';
                    }else{
                        $('#loginInfo').text(result.msg);
                    }
                },
                error:function(XMLHttpRequest, textStatus, errorThrown){
                    // 状态码
                    console.log(XMLHttpRequest.status);
                    // 状态
                    console.log(XMLHttpRequest.readyState);
                    // 错误信息
                    console.log(textStatus);
                }
            });
        }
	</script>
</head>

<body>
	<div id="container" class="cls-container">
		<div id="bg-overlay" class="bg-img" style="background-image: url(${ctx}/static/images/bg-img/bg-img-2.jpg);"> </div>
		<div class="cls-content">
		    <div class="cls-content-sm panel">
		        <div class="panel-body">
		            <div class="mar-ver pad-btm">
		                <h2 class="h3 mar-no">一币后台管理系统</h2>
		                <p class="text-muted">Sign In to your account</p>
		            </div>
		            <form method="post" id="loginform">
		                <div class="form-group">
		                    <input type="text" id="userAccount" name="userAccount" class="form-control" placeholder="Username" autofocus style="width: 100%">
		                </div>
		                <div class="form-group">
		                    <input type="password" id="userPassword" name="userPassword" class="form-control" placeholder="Password" style="width: 100%"> 
		                </div>
		                <div class="checkbox pad-btm">
							<span id="loginInfo" style="color: red;"></span>
		                </div>
		                <button class="btn btn-primary btn-lg btn-block" type="button" onclick="submitForm()">Sign In</button>
		            </form>
		        </div>
		
		        <div class="pad-all">
		        </div>
		    </div>
		</div>
		<div class="demo-bg">
		    <%--<div id="demo-bg-list">--%>
		        <%--<div class="demo-loading"><i class="psi-repeat-2"></i></div>--%>
		        <%--<img class="demo-chg-bg" src="${ctx}/static/images/bg-img/thumbs/bg-img-1.jpg" alt="Background Image">--%>
		        <%--<img class="demo-chg-bg " src="${ctx}/static/images/bg-img/thumbs/bg-img-2.jpg" alt="Background Image">--%>
		        <%--<img class="demo-chg-bg" src="${ctx}/static/images/bg-img/thumbs/bg-img-3.jpg" alt="Background Image">--%>
		        <%--<img class="demo-chg-bg" src="${ctx}/static/images/bg-img/thumbs/bg-img-4.jpg" alt="Background Image">--%>
		        <%--<img class="demo-chg-bg" src="${ctx}/static/images/bg-img/thumbs/bg-img-5.jpg" alt="Background Image">--%>
		        <%--<img class="demo-chg-bg" src="${ctx}/static/images/bg-img/thumbs/bg-img-6.jpg" alt="Background Image">--%>
		        <%--<img class="demo-chg-bg" src="${ctx}/static/images/bg-img/thumbs/bg-img-7.jpg" alt="Background Image">--%>
		    <%--</div>--%>
		</div>
	</div>
</body>
<!-- <script type="text/javascript">
function login(){
var userAccount=document.getElementById('userAccount').value;
var userPassword=document.getElementById('userPassword').value;
		$.post("user/login.action",{
			"userAccount" : userAccount,
			"userPassword" : userPassword
		},function(result){
			if(result=="true"){
				layer.msg('登录成功!',{icon:1,time:1000});
			}
			if(result=="false"){
				layer.msg('帐号或密码错误!',{icon:2,time:1000});
			}
		});
}
</script> -->
</html>
