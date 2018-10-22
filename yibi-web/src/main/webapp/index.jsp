<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE HTML>
<html>
<head>
    <title></title>
</head>
<body>
<%
    response.sendRedirect("${ctx}/login/login.do");
%>
</body>
</html>
