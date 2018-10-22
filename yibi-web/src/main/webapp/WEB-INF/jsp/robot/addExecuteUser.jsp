<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/static/commons/global.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="renderer" content="webkit">
	<title>新增机器人任务执行用户</title>
	<%@ include file="/static/commons/commoncss.jsp" %>
	<%@ include file="/static/commons/commonjs.jsp" %>
</head>
<body>
<form id="dataForm" method="post" action="${ctx}/taskUser/addTaskUser.do">
	<p>执行用户设置的交易密码，必须和后台默认的交易密码一致，不知道密码的可以联系管理员</p>
	<table class="grid">
		<tr>
			<td>输手机号</td>
			<td>
                <input type="text" name="phone" value="" class="easyui-textbox" data-options="required:true" style="width: 180px;"/>
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