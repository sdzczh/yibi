<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/static/commons/global.jsp" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="renderer" content="webkit">
	<title>系统管理员管理</title>
	<%@ include file="/static/commons/commoncss.jsp" %>
	<%@ include file="/static/commons/commonjs.jsp" %>

</head>
<body>
<form id="dataForm" method="post" action="${ctx}/manager/addManager.do">
	<table class="grid">
		<tr>
			<td>管理员账号</td>
			<td>
				<input type="text" name="useraccount" value="" class="easyui-textbox" data-options="required:true" style="width: 180px;"/>
			</td>
			<td>管理员密码</td>
			<td>
				<input type="text" name="userpassword" value="" class="easyui-textbox" data-options="" style="width: 180px;"/>
			</td>
		</tr>
		<tr>
			<td>管理员名字</td>
			<td>
				<input type="text" id="username" name="username" value="" class="easyui-textbox" style="width: 180px;" />
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