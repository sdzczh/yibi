<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/static/commons/global.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="renderer" content="webkit">
	<title>系统配置管理</title>
	<%@ include file="/static/commons/commoncss.jsp" %>
	<%@ include file="/static/commons/commonjs.jsp" %>
</head>
<body>
<form id="dataForm" method="post" action="${ctx}/document/saveOrUpdateBanner.do"  enctype="multipart/form-data">
	<table class="grid">
		<tr>
			<td>banner位置</td>
			<td>
				<input type="hidden"  name="id" value="" />
				<select class="easyui-combobox" id="bannertype" name="bannertype">
					<option value="">--位置--</option>
					<option value="0">首页</option>
					<option value="1">生态</option>
					<option value="2">提升魂力</option>
				</select>
			</td>
			<td>Banner图片</td>
			<td>
				<input name="file" class="easyui-filebox" data-options="buttonText:'选择文件', accept:'	image/png' " style="width: 180px;"/>
			</td>
		</tr>
		<tr>
			<td>跳转类型</td>
			<td>
				<select class="easyui-combobox" id="type" name="type">
					<option value="">--跳转类型--</option>
					<option value="0">网站</option>
					<option value="1">余币宝</option>
					<option value="2">交易挖矿</option>
					<option value="3">法币</option>
					<option value="4">史莱克挖矿</option>
					<option value="5">转盘</option>
				</select>
			</td>
			<td>跳转地址</td>
			<td>
				<input type="text" name="address" value="" class="easyui-textbox" data-options="required:true" style="width: 180px;"/>
			</td>
		</tr>
		<tr>
			<td>使用状态</td>
			<td>
				<select class="easyui-combobox" id="state" name="state">
					<option value="">--状态--</option>
					<option value="0">废弃</option>
					<option value="1">正常</option>
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