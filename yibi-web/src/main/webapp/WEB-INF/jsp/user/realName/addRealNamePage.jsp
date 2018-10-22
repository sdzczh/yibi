<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/static/commons/global.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="renderer" content="webkit">
	<title>手动实名</title>
	<%@ include file="/static/commons/commoncss.jsp" %>
	<%@ include file="/static/commons/commonjs.jsp" %>
</head>
<body>
<form id="dataForm" method="post" action="${ctx}/realName/addRealName.do"  enctype="multipart/form-data">
	<table class="grid">
		<tr>
			<td>手机号</td>
			<td>
				<input type="text" name="phone" value="" class="enumberbox" data-options="required:true" style="width: 180px;"/>
			</td>
			<td>用户名</td>
			<td>
				<input type="text" name="name" value="" class="enumberbox" placeholder="和身份证上的一致" data-options="required:true" style="width: 180px;"/>
			</td>
		</tr>

		<tr>
			<td>身份证号</td>
			<td>
				<input type="text" name="identificationnumber" value="" class="enumberbox" data-options="required:true" style="width: 180px;"/>
			</td>
			<td>性别</td>
			<td>
				<select class="easyui-combobox" id="sex" name="sex">
					<option value="">--性别--</option>
					<option value="m">男</option>
					<option value="f">女</option>
				</select>
			</td>
		</tr>

		<tr>
			<td>身份证正面</td>
			<td>
				<input name="frontFile" class="easyui-filebox" data-options="buttonText:'选择文件', accept:'	image/png' " style="width: 180px;"/>
			</td>
			<td>身份证反面</td>
			<td>
				<input name="backFile" class="easyui-filebox" data-options="buttonText:'选择文件', accept:'	image/png' " style="width: 180px;"/>
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