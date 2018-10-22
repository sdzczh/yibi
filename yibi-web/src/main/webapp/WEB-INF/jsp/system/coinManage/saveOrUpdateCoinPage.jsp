<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/static/commons/global.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
            var id = '${id }';
            if(id != null){
                $('#dataForm').form('load', '${ctx}/coinManage/loadForm.do?id=${id }');
            }
        });
    </script>
</head>
<body>
<form id="dataForm" method="post" action="${ctx}/coinManage/saveOrUpdateCoin.do">
	<table class="grid">
		<tr>
			<td>币种</td>
			<td>
				<input type="hidden"  name="id" value="" />

                <input type="text" name="cointype" value="" class="enumberbox" data-options="required:true" style="width: 180px;"/>

			</td>
			<td>币种名字</td>
			<td>
                <input type="text" name="coinname" value="" class="enumberbox" data-options="required:true" style="width: 180px;"/>
			</td>
		</tr>
		<tr>
			<td>中文名字</td>
			<td>
				<input type="text" name="cnname" value="" class="enumberbox" data-options="required:true" style="width: 180px;"/>
			</td>
			<td>币种描述</td>
			<td>
				<input type="text" name="description" value="" class="enumberbox" data-options="" style="width: 180px;"/>
			</td>
		</tr>
		<tr>
			<td>币种对应图标</td>
			<td>
				<input type="text" name="imgurl" value="" class="enumberbox" data-options="required:true" style="width: 180px;"/>
			</td>
			<td>c2c交易开关  0:关闭,1:开启</td>
			<td>
				<input type="text" name="c2conoff" value="" placeholder="0:关闭，1：开启" class="enumberbox" data-options="required:true" style="width: 180px;"/>
			</td>
		</tr>
		<tr>
			<td>现货转c2c</td>
			<td>
				<input type="text" name="spottoc2conoff" value="" placeholder="0:关闭，1：开启" class="enumberbox" data-options="required:true" style="width: 180px;"/>
			</td>
			<td>c2c转现货开关  0:关闭,1:开启</td>
			<td>
				<input type="text" name="c2ctospotonoff" value="" placeholder="0:关闭，1：开启" class="enumberbox" data-options="required:true" style="width: 180px;"/>
			</td>
		</tr>
		<tr>
			<td>现货充值费率</td>
			<td>
				<input type="text" name="rechspotrate" value="" class="enumberbox" data-options="required:true" style="width: 180px;"/>
			</td>
			<td>现货充值开关  0:关闭,1:开启</td>
			<td>
				<input type="text" name="rechspotonoff" value="" placeholder="0:关闭，1：开启" class="enumberbox" data-options="required:true" style="width: 180px;"/>
			</td>
		</tr>
		<tr>
			<td>现货提现开关  0:关闭,1:开启</td>
			<td>
				<input type="text" name="withspotonoff" value="" placeholder="0:关闭，1：开启" class="enumberbox" data-options="required:true" style="width: 180px;"/>
			</td>
			<td>提现矿工费率</td>
			<td>
				<input type="text" name="withspotrate" value="" class="enumberbox" data-options="" style="width: 180px;"/>
			</td>
		</tr>
		<tr>
			<td>c2c提现开关  0:关闭,1:开启</td>
			<td>
				<input type="text" name="withc2conoff" value="" placeholder="0:关闭，1：开启" class="enumberbox" data-options="required:true" style="width: 180px;"/>

			</td>
			<td>c2c交易保证金</td>
			<td>
				<input type="text" name="c2corderdeposit" value="" class="enumberbox" data-options="" style="width: 180px;"/>
			</td>
		</tr>
		<tr>
			<td>最高提现金额</td>
			<td>
				<input type="text" name="withamountmax" value="" class="enumberbox" data-options="required:true" style="width: 180px;"/>
			</td>
			<td>最低提现金额</td>
			<td>
				<input type="text" name="withamountmin" value="" class="enumberbox" data-options="" style="width: 180px;"/>
			</td>
		</tr>
		<tr>
			<td>每日最高提现次数</td>
			<td>
				<input type="text" name="withdrawcountmax" value="" class="enumberbox" data-options="required:true" style="width: 180px;"/>
			</td>
			<td>挖矿账户转现货</td>
			<td>
				<input type="text" name="digtospotonoff" value="" placeholder="0:关闭，1：开启" class="enumberbox" data-options="required:true" style="width: 180px;"/>
			</td>
		</tr>
        <tr>
            <td>挖矿账户提现到pc钱包</td>
            <td>
				<input type="text" name="digwithdrwaonoff" value="" placeholder="0:关闭，1：开启" class="enumberbox" data-options="required:true" style="width: 180px;"/>
            </td>
			<td>单笔红包最大金额</td>
			<td>
				<input type="text" name="redpacketmaxamtsingle" value="" class="enumberbox" data-options="" style="width: 180px;"/>
			</td>
        </tr>

		<tr>
			<td>每日转账最大金额</td>
			<td>
				<input type="text" name="transfermaxamtday" value="" class="enumberbox" data-options="required:true" style="width: 180px;"/>
			</td>
			<td>生成钱包地址url</td>
			<td>
				<input type="text" name="getAddress" value="" class="enumberbox" data-options="" style="width: 180px;"/>
			</td>
		</tr>
		<tr>
			<td>查询交易记录数url</td>
			<td>
				<input type="text" name="listTransactions" value="" class="enumberbox" data-options="required:true" style="width: 180px;"/>
			</td>
			<td>转账url</td>
			<td>
				<input type="text" name="transferAddress" value="" class="enumberbox" data-options="" style="width: 180px;"/>
			</td>
		</tr>
		<tr>
			<td>现货账户转余币宝开关  0:关闭,1:开启</td>
			<td>
				<input type="text" name="spottoyubionoff" value="" placeholder="0:关闭，1：开启" class="enumberbox" data-options="required:true" style="width: 180px;"/>
			</td>
			<td>余币宝转现货开关  0:关闭,1:开启</td>
			<td>
				<input type="text" name="yubitospotonoff" value="" placeholder="0:关闭，1：开启" class="enumberbox" data-options="required:true" style="width: 180px;"/>
			</td>
		</tr>
		<tr>
			<td>余币宝最低转入金额</td>
			<td>
				<input type="text" name="yubitransmin" value="" class="enumberbox" data-options="required:true" style="width: 180px;"/>
			</td>
			<td>余币宝最高转出金额</td>
			<td>
				<input type="text" name="yubitransmax" value="" class="enumberbox" data-options="" style="width: 180px;"/>
			</td>
		</tr>
		<tr>
			<td>余币宝日利率</td>
			<td>
				<input type="text" name="yubirate" value="" class="enumberbox" data-options="required:true" style="width: 180px;"/>
			</td>
			<td>自动充值数量</td>
			<td>
				<input type="text" name="autorechargeamt" value="" class="enumberbox" data-options="" style="width: 180px;"/>
			</td>
		</tr>

		<tr>
			<td>充值提示语</td>
			<td>
				<input type="text" name="rechargeinfo" value="" class="enumberbox" data-options="required:true" style="width: 180px;"/>
			</td>
			<td>提现提示语</td>
			<td>
				<input type="text" name="withdrawinfo" value="" class="enumberbox" data-options="" style="width: 180px;"/>
			</td>
		</tr>

		<tr>
			<td>余币宝最低产息限制</td>
			<td>
				<input type="text" name="yubiprofitminamt" value="" class="enumberbox" data-options="required:true" style="width: 180px;"/>
			</td>
			<td>挖矿开关  0:关闭,1:开启</td>
			<td>
				<input type="text" name="digonoff" value="" placeholder="0:关闭，1：开启" class="enumberbox" data-options="required:true" style="width: 180px;"/>
			</td>
		</tr>
		<tr>
			<td>我的钱包显示顺序</td>
			<td>
				<input type="text" name="seque" value=""  class="enumberbox" data-options="required:true" style="width: 180px;"/>
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