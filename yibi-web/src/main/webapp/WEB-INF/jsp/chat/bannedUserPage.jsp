<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/static/commons/global.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="renderer" content="webkit">
	<title>禁言用户</title>
	<%@ include file="/static/commons/commoncss.jsp" %>
	<%@ include file="/static/commons/commonjs.jsp" %>
</head>
<body>
<form id="dataForm" method="post" action="${ctx}/chat/bannedChatUser.do">
	<table class="grid">
		<tr>
			<td>禁言时间（单位：分钟）</td>
			<td>
				<input type="hidden"  name="id" value="${id }" />

                <input type="text" name="keyname" value="" class="easyui-textbox" data-options="required:true" style="width: 180px;"/>

			</td>
		</tr>
		<tr>
			<td colspan="4" align="center">
				<input type="submit" value="保存">
				<a href="javascript:void(0)" class="easyui-linkbutton" style="width:80px" onclick="top.closeTopDialog();">关闭</a>
			</td>
		</tr>
	</table>
</form>
</body>
</html>