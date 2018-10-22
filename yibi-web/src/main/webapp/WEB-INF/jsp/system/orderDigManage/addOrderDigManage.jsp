<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/static/commons/global.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="renderer" content="webkit">
	<title>新增交易挖矿配置管理</title>
	<%@ include file="/static/commons/commoncss.jsp" %>
	<%@ include file="/static/commons/commonjs.jsp" %>
</head>
<body>
<form id="dataForm" method="post" action="${ctx}/orderDig/addOrderDig.do">
	<table class="grid">
		<tr>
			<td>交易币种</td>
			<td>
				<select class="easyui-combobox" id="ordercointype" name="ordercointype">
					<option value="">--交易币种--</option>
					<c:forEach var="coin" items="${coins }">
						<option value="${coin.cointype }">${coin.coinname }</option>
					</c:forEach>
				</select>
			</td>
			<td>交易类型</td>
			<td>
				<select class="easyui-combobox" id="ordertype" name="ordertype">
					<option value="">--交易类型--</option>
						<option value="0">市价交易</option>
						<option value="1">限价交易</option>
				</select>
			</td>
		</tr>

		<tr>
			<td>买方角色</td>
			<td>
				<select class="easyui-combobox" id="buyrole" name="buyrole">
					<option value="">--买方角色--</option>
					<option value="1">合伙人</option>
					<option value="2">普通用户</option>
				</select>
			</td>
			<td>卖方角色</td>
			<td>
				<select class="easyui-combobox" id="salerole" name="salerole">
					<option value="">--卖方角色--</option>
					<option value="1">合伙人</option>
					<option value="2">普通用户</option>
				</select>
			</td>
		</tr>

		<tr>
			<td>手续费费率</td>
			<td>
				<input type="text" name="feerate" value="" class="enumberbox"  data-options="required:true" style="width: 180px;"/>
			</td>
			<td>卖方返现率</td>
			<td>
				<input type="text" name="salecashback" value="" class="enumberbox"  data-options="required:true" style="width: 180px;"/>
			</td>
		</tr>
		<tr>
			<td>买方返现率</td>
			<td>
				<input type="text" name="buycashback" value="" class="enumberbox"  data-options="required:true" style="width: 180px;"/>
			</td>
			<td>卖方推荐人返现率</td>
			<td>
				<input type="text" name="salerefcashback" value="" class="enumberbox"  data-options="required:true" style="width: 180px;"/>
			</td>
		</tr>
		<tr>
			<td>买方推荐人返现率</td>
			<td>
				<input type="text" name="buyrefcashback" value="" class="enumberbox"  data-options="required:true" style="width: 180px;"/>
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