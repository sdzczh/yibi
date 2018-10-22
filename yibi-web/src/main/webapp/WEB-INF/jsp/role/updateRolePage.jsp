<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/static/commons/global.jsp" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="renderer" content="webkit">
	<title>系统角色管理</title>
	<%@ include file="/static/commons/commoncss.jsp" %>
	<%@ include file="/static/commons/commonjs.jsp" %>

</head>
<body>
<form id="dataForm" method="post" action="${ctx}/role/updateRole.do">
	<table class="grid">
		<tr>
			<td>角色代号</td>
			<td>
				<input type="hidden" id="" name="id" value="${role.id}" />
				<input type="text" name="rolecode" value="${role.rolecode}" class="easyui-textbox" data-options="required:true" style="width: 180px;"/>
			</td>
			<td>角色名称</td>
			<td>
				<input type="text" name="rolename" value="${role.rolename}" class="easyui-textbox" data-options="" style="width: 180px;"/>
			</td>
		</tr>
		<tr>
			<td>描述</td>
			<td>
				<input type="text" id="roledesc" name="roledesc" value="${role.roledesc}" class="easyui-textbox" style="width: 180px;" />
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