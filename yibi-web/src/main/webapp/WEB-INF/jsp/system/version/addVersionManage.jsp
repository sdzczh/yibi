<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/static/commons/global.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="renderer" content="webkit">
	<title>新增版本管理</title>
	<%@ include file="/static/commons/commoncss.jsp" %>
	<%@ include file="/static/commons/commonjs.jsp" %>
</head>
<body>
<form id="dataForm" method="post" action="${ctx}/versionManage/addVersionPage.do">
	<table class="grid">
		<tr>
			<td>手机类型</td>
			<td>
				<select class="easyui-combobox" id="phonetype" name="phonetype">
					<option value="1">--android--</option>
					<option value="2">--ios--</option>
				</select>
			</td>
			<td>app版本号</td>
			<td>
				<input type="text" name="appversion" value="" placeholder="版本号必须是整数" class="enumberbox" data-options="required:true" style="width: 180px;"/>
			</td>
		</tr>
		<tr>
			<td>类型</td>
			<td>
				<select class="easyui-combobox" id="type" name="type">
					<option value="0">--提示更新一次--</option>
					<option value="1">--每次提示更新--</option>
					<option value="2">--强制更新--</option>
				</select>
			</td>
			<td>更新地址</td>
			<td>
				<input type="text" name="address" value="" class="enumberbox" data-options="" style="width: 180px;"/>
			</td>
		</tr>
		<tr>
			<td>app大小</td>
			<td>
				<input type="text" name="size" value="" placeholder="app大小,单位K" class="enumberbox"  data-options="required:true" style="width: 180px;"/>
			</td>
			<td>hash验证值</td>
			<td>
				<input type="text" name="verificate" value="" class="enumberbox" placeholder="随意输入数字和英文" data-options="required:true" style="width: 180px;"/>
			</td>
		</tr>
		<tr>
			<td>更新内容</td>
			<td>
				<input type="text" name="content" value="" placeholder="此版本更新的内容" class="enumberbox"  data-options="required:true" style="width: 180px;"/>
			</td>
			<td>显示给用户的版本号</td>
			<td>
				<input type="text" name="minversion" value="" class="enumberbox" placeholder="可以带有小数点" data-options="required:true" style="width: 180px;"/>
			</td>
		</tr>
		<tr>
			<td>使用状态</td>
			<td>
				<select class="easyui-combobox" id="state" name="state">
					<option value="1">--有效--</option>
					<option value="0">--无效--</option>
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