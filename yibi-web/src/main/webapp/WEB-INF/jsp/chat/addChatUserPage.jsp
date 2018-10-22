<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/static/commons/global.jsp" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="renderer" content="webkit">
	<title>新增群成员管理</title>
	<%@ include file="/static/commons/commoncss.jsp" %>
	<%@ include file="/static/commons/commonjs.jsp" %>

</head>
<body>
<form id="dataForm" method="post" action="${ctx}/chat/addChatUser.do">
	<table class="grid">
		<tr>
			<td>手机号</td>
			<td>
				<input type="hidden" value="${chatId }" name="talkgroupid">
				<input type="text" name="phone" value="" class="easyui-textbox" data-options="required:true" style="width: 180px;"/>
			</td>
			<td>用户角色</td>
			<td>
				<select class="easyui-combobox" id="role" name="role">
					<option value="">--用户角色--</option>
					<option value="1">管理员</option>
					<option value="2">成员</option>
				</select>
			</td>
		</tr>
		<tr>
			<td>发言状态</td>
			<td>
				<select class="easyui-combobox" id="state" name="state">
					<option value="">--发言状态--</option>
					<option value="0">禁言</option>
					<option value="1">正常</option>
					<option value="2">免打扰</option>
				</select>
			</td>
			<td>描述</td>
			<td>
				<input type="text" id="roledesc" name="roledesc" value="" class="easyui-textbox" style="width: 180px;" />
			</td>
		</tr>
		<tr>
			<td>发言状态</td>
			<td>
				<select class="easyui-combobox" id="state" name="state">
					<option value="">--发言状态--</option>
					<option value="0">禁言</option>
					<option value="1">正常</option>
					<option value="2">免打扰</option>
				</select>
			</td>
			<td>是否免打扰</td>
			<td>
				<select class="easyui-combobox" id="isMuted" name="isMuted">
					<option value="0">否</option>
					<option value="1">免打扰</option>
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