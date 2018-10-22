<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8" />
		<title>公告</title>
		<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
		<style>
			*{margin: 0;padding: 0;border: 0;background: #fff;line-height: 1.5em;font-family: "Helvetica Neue", Helvetica, Arial, "PingFang SC", "Hiragino Sans GB", "Heiti SC", "Microsoft YaHei", "WenQuanYi Micro Hei", sans-serif;}
			body{width: 94%;margin: 3%;}
			div{width: 100%;}
			h2{font-size: 20px;color: #333;font-weight: 400;}
			span{font-size: 14px;color: #ccc;}
			p{font-size: 16px;color: #666;}
			img{max-width: 100%;margin: 10px auto;}
			.lishi{margin-bottom: 40px;}
			.lishi span{display: block;font-size: 18px;color: #333;margin: 40px 0 10px;line-height: 18px;border-left: 4px solid #4d94ff;padding-left: 10px;}
			.lishi a{display: block;width: 100%;height: 40px;line-height: 40px;overflow: hidden;font-size: 16px;color: #666;}
		</style>
	</head>
	<body>
		<div style="border-bottom: 1px solid #e5e5e5;padding-bottom: 10px;margin-bottom: 10px;">
			<h2>${notice.roundup}</h2>
			<span><fmt:formatDate value="${notice.createtime}" type="date" pattern="yyyy-MM-dd HH:mm"/></span>
		</div>
		<div>
			${notice.content}
		</div>
		
		<div class="lishi">
			<span>历史公告</span>
			<c:if test="${not empty url1}"><a href="${url1}">${notice1.title}</a></c:if>
			<c:if test="${not empty url2}"><a href="${url2}">${notice2.title}</a></c:if>
			<c:if test="${not empty url3}"><a href="${url3}">${notice3.title}</a></c:if>
			<c:if test="${not empty url4}"><a href="${url4}">${notice4.title}</a></c:if>
			<c:if test="${not empty url5}"><a href="${url5}">${notice5.title}</a></c:if>
		</div>
		

		
	</body>
</html>
