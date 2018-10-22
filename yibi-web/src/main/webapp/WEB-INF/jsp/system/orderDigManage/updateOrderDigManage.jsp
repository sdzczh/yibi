<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/static/commons/global.jsp" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="renderer" content="webkit">
	<title>币种配置管理</title>
	<%@ include file="/static/commons/commoncss.jsp" %>
	<%@ include file="/static/commons/commonjs.jsp" %>
    <script type="text/javascript">
        $(function(){
            var id = '${orderDigId }';
            if(id != null){
                $('#dataForm').form('load', '${ctx}/orderDig/loadOrderDigForm.do?id=${orderDigId }');
            }
        });
    </script>
</head>
<body>
<form id="dataForm" method="post" action="${ctx}/orderDig/updateOrderDig.do">
	<table class="grid">
		<tr>
			<td>交易币种</td>
			<td>
				<input type="hidden" id="" name="id" value="" />
				<input type="text" name="ordercointype" value="" class="enumberbox" readonly="readonly"  data-options="required:true" style="width: 180px;"/>
			</td>
			<td>交易类型 0:市价交易，1:限价交易</td>
			<td>
				<input type="text" name="ordertype" value="" class="enumberbox" placeholder="0:市价交易，1：限价交易 "  data-options="required:true" style="width: 180px;"/>
			</td>
		</tr>

		<tr>
			<td>买方角色 1:合伙人，2:普通用户</td>
			<td>
				<input type="text" name="buyrole" value="" class="enumberbox" placeholder="1:合伙人，2:普通用户"  data-options="required:true" style="width: 180px;"/>
			</td>
			<td>卖方角色 1:合伙人，2:普通用户</td>
			<td>
				<input type="text" name="salerole" value="" class="enumberbox" placeholder="1:合伙人，2:普通用户"  data-options="required:true" style="width: 180px;"/>
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