﻿<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/static/commons/global.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="renderer" content="webkit">
	<title>图片管理</title>
	<%@ include file="/static/commons/commoncss.jsp" %>
	<%@ include file="/static/commons/commonjs.jsp" %>

</head>
<body>
<form id="dataForm" method="post" action="${ctx}/document/savePicture.do" enctype="multipart/form-data">
	<table class="grid">
		<tr>
			<td>图片名称</td>
			<td>
				<input type="text" name="name" value="" class="easyui-textbox" data-options="required:true" style="width: 180px;"/>
			</td>
			<td>图片描述</td>
			<td>
				<input type="text" name="remark" value="" class="easyui-textbox" data-options="" style="width: 180px;"/>
			</td>
		</tr>
		<tr>
			<td>图片上传</td>
			<td>
				<input name="file" class="easyui-filebox" data-options="buttonText:'选择文件', accept:'	image/png' " style="width: 180px;"/>
			</td>
			<td>图片使用状态</td>
			<td>
				<input type="text" name="state" value="" class="easyui-textbox" data-options="required:true" style="width: 180px;"/>
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