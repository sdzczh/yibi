<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/static/commons/global.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="renderer" content="webkit">
	<title>新增挖矿基数</title>
	<%@ include file="/static/commons/commoncss.jsp" %>
	<%@ include file="/static/commons/commonjs.jsp" %>
</head>
<body>
<form id="dataForm" method="post" action="${ctx}/digBase/addDigBase.do">
	<table class="grid">
		<tr>
			<td>币种</td>
			<td>
				<select class="easyui-combobox" id="cointype" name="cointype" style="width: 120px">
					<option value="">--币种--</option>
					<c:forEach var="coin" items="${coins }">
						<option value="${coin.cointype }">${coin.coinname }</option>
					</c:forEach>
				</select>
			</td>
			<td>第一级基数</td>
			<td>
				<input type="text" name="rate0" placeholder="每一百魂力分的币" value="" class="enumberbox" data-options="" style="width: 180px;"/>
			</td>

		</tr>
		<tr>
			<td>第二级基数</td>
			<td>
				<input type="text" name="rate1" placeholder="每一百魂力分的币" value="" class="enumberbox" data-options="" style="width: 180px;"/>
			</td>
			<td>第三级基数</td>
			<td>
				<input type="text" name="rate2" placeholder="每一百魂力分的币" value="" class="enumberbox" data-options="" style="width: 180px;"/>
			</td>
		</tr>
		<tr>
			<td>第四级基数</td>
			<td>
				<input type="text" name="rate3" placeholder="每一百魂力分的币" value="" class="enumberbox" data-options="" style="width: 180px;"/>
			</td>
			<td>第五级基数</td>
			<td>
				<input type="text" name="rate4" placeholder="每一百魂力分的币" value="" class="enumberbox" data-options="" style="width: 180px;"/>
			</td>
		</tr>
		<tr>
			<td>第六级基数</td>
			<td>
				<input type="text" name="rate5" placeholder="每一百魂力分的币" value="" class="enumberbox" data-options="" style="width: 180px;"/>
			</td>
			<td>第七级基数</td>
			<td>
				<input type="text" name="rate6" placeholder="每一百魂力分的币" value="" class="enumberbox" data-options="" style="width: 180px;"/>
			</td>
		</tr>
		<tr>
			<td>第八级基数</td>
			<td>
				<input type="text" name="rate7" placeholder="每一百魂力分的币" value="" class="enumberbox" data-options="" style="width: 180px;"/>
			</td>
			<td>第九级基数</td>
			<td>
				<input type="text" name="rate8" placeholder="每一百魂力分的币" value="" class="enumberbox" data-options="" style="width: 180px;"/>
			</td>
		</tr>
		<tr>
			<td>第十级基数</td>
			<td>
				<input type="text" name="rate9" placeholder="每一百魂力分的币" value="" class="enumberbox" data-options="" style="width: 180px;"/>
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