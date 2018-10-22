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
            var id = '${orderManageId }';
            if(id != null){
                $('#dataForm').form('load', '${ctx}/orderManage/loadOrderManageForm.do?id=${orderManageId }');
            }
        });
    </script>
</head>
<body>
<form id="dataForm" method="post" action="${ctx}/orderManage/updateOrderManage.do">
	<table class="grid">
		<tr>
			<td>交易币种</td>
			<td>
				<input type="hidden" id="" name="id" value="" />

                <input type="text" name="ordercointype" value="" class="enumberbox" placeholder="输入币种类型数字代号" data-options="required:true" style="width: 180px;" readonly="readonly"/>

			</td>
			<td>计价币种</td>
			<td>
                <input type="text" name="unitcointype" value="" class="enumberbox" placeholder="输入币种类型数字代号" data-options="required:true" style="width: 180px;" readonly="readonly"/>
			</td>
		</tr>
		<tr>
			<td>开关  0:关闭，1开启</td>
			<td>
				<input type="text" name="onoff" value="" class="enumberbox" placeholder="0：关闭，1开启" data-options="required:true" style="width: 180px;"/>
			</td>
			<td>OKCoin是否提供接口 0:不提供 1:提供</td>
			<td>
				<input type="text" name="okcoinflag" value="" class="enumberbox" placeholder="0：不提供，1：提供" data-options="" style="width: 180px;"/>
			</td>
		</tr>
		<tr>
			<td>限价推荐人费率</td>
			<td>
				<input type="text" name="referrate" value="" class="enumberbox" placeholder="输入位数（整数）" data-options="required:true" style="width: 180px;"/>
			</td>
			<td>限价平台费率</td>
			<td>
				<input type="text" name="performrate" value="" class="enumberbox" placeholder="输入位数（整数）" data-options="" style="width: 180px;"/>
			</td>
		</tr>

		<tr>
			<td>市价平台费率</td>
			<td>
				<input type="text" name="marketpPerformRate" value="" class="enumberbox"  data-options="required:true" style="width: 180px;"/>
			</td>
			<td>市价推荐人费率</td>
			<td>
				<input type="text" name="marketReferRate" value="" class="enumberbox" placeholder="0:不提供，1:提供" data-options="required:true" style="width: 180px;"/>
			</td>
		</tr>

		<tr>
			<td>行情顺序</td>
			<td>
				<input type="text" name="marketseque" value="" class="enumberbox"  data-options="required:true" style="width: 180px;"/>
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