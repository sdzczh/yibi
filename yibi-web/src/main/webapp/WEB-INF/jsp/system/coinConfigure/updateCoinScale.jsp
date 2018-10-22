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
            var id = '${coinScale.id}';
            if(id != null){
                $('#dataForm').form('load', '${ctx}/coin/loadForm.do?id=${coinScale.id}');
            }
        });
    </script>
</head>
<body>
<form id="dataForm" method="post" action="${ctx}/coin/updateCoinScale.do">
	<table class="grid">
		<tr>
			<td>交易币种</td>
			<td>
				<input type="hidden" id="" name="id" value="" />

                <input type="text" name="ordercointype" value="" class="enumberbox" placeholder="输入位数（整数）" data-options="required:true" style="width: 180px;" readonly="readonly"/>

			</td>
			<td>计价币种</td>
			<td>
                <input type="text" name="unitcointype" value="" class="enumberbox" placeholder="输入位数（整数）" data-options="required:true" style="width: 180px;" readonly="readonly"/>
			</td>
		</tr>
		<tr>
			<td>交易价格、总金额小数位数</td>
			<td>
				<input type="text" name="orderamtpricescale" value="" class="enumberbox" placeholder="输入位数（整数）" data-options="required:true" style="width: 180px;"/>
			</td>
			<td>现货页面计价币可用资产显示小数位数</td>
			<td>
				<input type="text" name="availofspotunitscale" value="" class="enumberbox" placeholder="输入位数（整数）" data-options="" style="width: 180px;"/>
			</td>
		</tr>
		<tr>
			<td>现货页面交易币可用资产显示小数位数</td>
			<td>
				<input type="text" name="availofspotorderscale" value="" class="enumberbox" placeholder="输入位数（整数）" data-options="required:true" style="width: 180px;"/>
			</td>
			<td>行情的最新价格折合人民币小数位数</td>
			<td>
				<input type="text" name="marketpriceofcnyscale" value="" class="enumberbox" placeholder="输入位数（整数）" data-options="" style="width: 180px;"/>
			</td>
		</tr>
		<tr>
			<td>行情交易量小数位数</td>
			<td>
				<input type="text" name="markettradenumscale" value="" class="enumberbox" placeholder="输入位数（整数）" data-options="required:true" style="width: 180px;"/>
			</td>
			<td>K线图价格小数位数</td>
			<td>
				<input type="text" name="klinepricescale" value="" class="enumberbox" placeholder="输入位数（整数）" data-options="" style="width: 180px;"/>
			</td>
		</tr>
		<tr>
			<td>计算时小数位数，以及我的资产显示小数位数</td>
			<td>
				<input type="text" name="calculscale" value="" class="enumberbox" placeholder="输入位数（整数）" data-options="required:true" style="width: 180px;"/>
			</td>
			<td>可用资产折合人民币显示小数位数</td>
			<td>
				<input type="text" name="availofcnyscale" value="" class="enumberbox" placeholder="输入位数（整数）" data-options="" style="width: 180px;"/>
			</td>
		</tr>
		<tr>
			<td>余币宝计算小数位数</td>
			<td>
				<input type="text" name="yubiscale" value="" class="enumberbox" placeholder="输入位数（整数）" data-options="required:true" style="width: 180px;"/>
			</td>
			<td>法币交易价格小数位数</td>
			<td>
				<input type="text" name="c2cpricescale" value="" class="enumberbox" placeholder="输入位数（整数）" data-options="" style="width: 180px;"/>
			</td>
		</tr>
		<tr>
			<td>法币交易数量小数位数</td>
			<td>
				<input type="text" name="c2cnumscale" value="" class="enumberbox" placeholder="输入位数（整数）" data-options="required:true" style="width: 180px;"/>
			</td>
			<td>法币交易总额小数位数</td>
			<td>
				<input type="text" name="c2ctotalamtscale" value="" class="enumberbox" placeholder="输入位数（整数）" data-options="" style="width: 180px;"/>
			</td>
		</tr>
		<tr>
			<td>最低现货交易额限制</td>
			<td>
				<input type="text" name="minspottransamt" value="" class="enumberbox"  data-options="required:true" style="width: 180px;"/>
			</td>
			<td>最低现货交易数量限制</td>
			<td>
				<input type="text" name="minspottransnum" value="" class="enumberbox"  data-options="" style="width: 180px;"/>
			</td>
		</tr>
		<tr>
			<td>最低法币交易额限制</td>
			<td>
				<input type="text" name="minc2ctransamt" value="" class="enumberbox"  data-options="required:true" style="width: 180px;"/>
			</td>
			<td>最低提现数量限制</td>
			<td>
				<input type="text" name="minwithdrawnum" value="" class="enumberbox"  data-options="" style="width: 180px;"/>
			</td>
		</tr>
        <tr>
            <td>交易量小数位数</td>
            <td>
                <input type="text" name="orderamtamountscale" value="" class="enumberbox" placeholder="输入位数（整数）" data-options="" style="width: 180px;"/>
            </td>
			<td>提现最小位数</td>
			<td>
				<input type="text" name="withdrawScale" value="" class="enumberbox" placeholder="输入位数（整数）" data-options="" style="width: 180px;"/>
			</td>

        </tr>
		<tr>
			<td>市价买入最低金额</td>
			<td>
				<input type="text" name="marketbuyminamt" value="" class="enumberbox" placeholder="" data-options="required:true" style="width: 180px;"/>
			</td>
			<td>市价卖出最低数量</td>
			<td>
				<input type="text" name="marketsaleminnum" value="" class="enumberbox" placeholder="" data-options="required:true" style="width: 180px;"/>
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