<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/static/commons/global.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="renderer" content="webkit">
	<title>魂力管理</title>
	<%@ include file="/static/commons/commoncss.jsp" %>
	<%@ include file="/static/commons/commonjs.jsp" %>
</head>
<body>
<form id="dataForm" method="post" action="${ctx}/robot/addRobot.do">
	<table class="grid">
		<tr>
			<td>选择新增机器人的币种</td>
			<td>
				<select class="easyui-combobox" id="coinType" name="coinType" style="width: 120px;">
					<option value="">--币种--</option>
					<c:forEach var="coin" items="${coins }">
						<option value="${coin.cointype }">${coin.coinname }</option>
					</c:forEach>
				</select>

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