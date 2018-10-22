<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<%@ include file="/static/commons/global.jsp"%>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Error 404 | 火粒国际</title>


    <!--STYLESHEET-->
    <!--=================================================-->

    <!--Open Sans Font [ OPTIONAL ]-->
    <link href='https://fonts.googleapis.com/css?family=Open+Sans:400,300,600,700' rel='stylesheet' type='text/css'>


    <!--Bootstrap Stylesheet [ REQUIRED ]-->
    <link href="${ctx}/static/css/bootstrap.min.css" rel="stylesheet">


    <!--Nifty Stylesheet [ REQUIRED ]-->
    <link href="${ctx}/static/css/nifty.min.css" rel="stylesheet">


    <!--Nifty Premium Icon [ DEMONSTRATION ]-->
    <link href="${ctx}/static/css/demo/nifty-demo-icons.min.css" rel="stylesheet">


        



    
    <!--JAVASCRIPT-->
    <!--=================================================-->

    <!--Pace - Page Load Progress Par [OPTIONAL]-->
    <link href="${ctx}/static/plugins/pace/pace.min.css" rel="stylesheet">
    <script src="${ctx}/static/plugins/pace/pace.min.js"></script>


    <!--jQuery [ REQUIRED ]-->
    <script src="${ctx}/static/js/jquery-2.2.4.min.js"></script>


    <!--BootstrapJS [ RECOMMENDED ]-->
    <script src="${ctx}/static/js/bootstrap.min.js"></script>


    <!--NiftyJS [ RECOMMENDED ]-->
    <script src="${ctx}/static/js/nifty.min.js"></script>





        

</head>

<!--TIPS-->
<!--You may remove all ID or Class names which contain "demo-", they are only used for demonstration. -->

<body>
	<div id="container" class="cls-container">
		
		<!-- HEADER -->
		<!--===================================================-->
		<div class="cls-header">
		    <div class="cls-brand">
		        <a class="box-inline" href="index.html">
		            <!-- <img alt="Nifty Admin" src="images/logo.png" class="brand-icon"> -->
		            <span class="brand-title">HuoLi   <span class="text-thin">International</span></span>
		        </a>
		    </div>
		</div>
		
		<!-- CONTENT -->
		<!--===================================================-->
		<div class="cls-content">
		    <h1 class="error-code text-info">404</h1>
		    <p class="text-main text-semibold text-lg text-uppercase">Page Not Found!</p>
		    <div class="pad-btm text-muted">
		        Sorry, but the page you are looking for has not been found on our server.
		    </div>
		    <hr class="new-section-sm bord-no">
		    <div class="pad-top"><a class="btn-link" href="../user/index.action">返回首页</a></div>
		    <div class="pad-top"><a href="javascript:history.go(-1)" >点击返回上个页面</a> </div>
		</div>
		
		
	</div>
	<!--===================================================-->
	<!-- END OF CONTAINER -->


		</body>
</html>

