<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/static/commons/global.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="renderer" content="webkit">
<title>管理员信息</title>
	<%@ include file="/static/commons/commoncss.jsp"%>
	<%@ include file="/static/commons/commonjs.jsp"%>
</head>
<body>
	<form id="dataForm" method="post">
		<table class="grid">
			<tr>
				<td>用户账号</td>
				<td>
					<input type="text" name="useraccount" value="${manager.useraccount}" class="easyui-textbox" data-options="required:true" style="width: 180px;" readonly="readonly"/>
				</td>
				<td>用户姓名</td>
				<td>
					<input type="text" name="username" value="${manager.username}" class="easyui-textbox" data-options="required:true" style="width: 180px;" readonly="readonly"/>
				</td>
			</tr>

		</table>
	</form>
</body>
</html>