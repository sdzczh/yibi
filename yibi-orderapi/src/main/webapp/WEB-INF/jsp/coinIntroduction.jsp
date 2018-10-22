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
  <meta name="renderer" content="webkit">
  <meta http-equiv="Cache-Control" content="no-siteapp"/>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/amazeui.min.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/app.css">
  <style type="text/css">
  	body {background-color: #131F30}
  	p.lef-sp{color:#657BA8;font-family: PingFangSC-Regular;}
  	h2,p.rig-sp{color:#FFFFFF;font-family: PingFangSC-Regular;}
  	tr.bord
	  {
	  border: 1px solid black;
	  }
	

	  .demo_line_02{  
		    height: 1px;  
		    border-top: 1px solid #171D26;  
		    text-align: center;  
		}  
		
	table {
	 table-layout:fixed;  
	  margin-top: 20px;
	  
	  margin-bottom: 20px;
	  margin-left: 10px;
	  }
	  
	  </style>
</head>
<body >
<div class = "center">
	<table  width="95%">
       <tbody>
       	<tr>
       		<td colspan="2"><h2>${coin.coinname }</h2></td>
       	</tr>
         <tr>
           <td width="20%"  align="left"><p class="lef-sp">发行时间</p></td>
           <td width="70%" align="right"><p class="rig-sp">
           <c:if test="${empty coin.releasetime }">--</c:if>
           <c:if test="${not empty coin.releasetime }">${coin.releasetime }</c:if>
           </p>
           </td>
         </tr>
         <tr>
           <td width="20%"  align="left"><p class="lef-sp">发行总量</p> </td>
           <td width="70%" align="right"><p class="rig-sp">
           <c:if test="${empty coin.releasetotalamt }">--</c:if>
           <c:if test="${not empty coin.releasetotalamt }">${coin.releasetotalamt }</c:if>
           </p></td>
         </tr>
         <tr>
           <td width="20%"  align="left"><p class="lef-sp">流通总量</p> </td>
           <td width="70%" align="right"><p class="rig-sp">
           <c:if test="${empty coin.circulationtotalamt }">--</c:if>
           <c:if test="${not empty coin.circulationtotalamt }">${coin.circulationtotalamt }</c:if>
           </p></td>
         </tr>
         <tr>
           <td width="20%"  align="left"><p class="lef-sp">众筹价格</p> </td>
           <td width="70%" align="right"><p class="rig-sp">
           <c:if test="${empty coin.crowdprice }">--</c:if>
           <c:if test="${not empty coin.crowdprice }">${coin.crowdprice }</c:if>
           </p></td>
         </tr>
         <tr>
           <td width="20%"  align="left"><p class="lef-sp">白皮书</p> </td>
           <td width="70%" align="right"><p class="rig-sp">${coin.whitepaper }
           <c:if test="${empty coin.whitepaper }">--</c:if>
           <c:if test="${not empty coin.whitepaper }">${coin.whitepaper }</c:if></p>
           </td>
         </tr>
         <tr>
           <td width="20%"  align="left"><p class="lef-sp">官网</p> </td>
           <td width="70%" align="right"><p class="rig-sp " >
           <c:if test="${empty coin.officialneturl }">--</c:if>
           <c:if test="${not empty coin.officialneturl }">${coin.officialneturl }</c:if>
           </p></td>
         </tr>
         <tr>
           <td width="20%"  align="left"><p class="lef-sp">区块查询</p> </td>
           <td width="70%" align="right"><p class="rig-sp" >
           <c:if test="${empty coin.blockquery }">--</c:if>
           <c:if test="${not empty coin.blockquery }">${coin.blockquery }</c:if>
           </p></td>
         </tr>
         <tr >
			<td colspan="2" >
				<div class="demo_line_02"></div>  
			</td>
		</tr>
         <tr >
			<td colspan="2" style="word-wrap:break-word; word-break:break-all;">
		
				<h2 class="rig-sp">简介</h2>
				<p class="rig-sp">
					 ${coin.introduction }
				</p>
			</td>
		</tr>
        </tbody>
      </table>
		

</div>
<script src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
<script src="${pageContext.request.contextPath}/js/amazeui.min.js"></script>
<script src="${pageContext.request.contextPath}/js/app.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/layer.js"></script>
</body>

</html>