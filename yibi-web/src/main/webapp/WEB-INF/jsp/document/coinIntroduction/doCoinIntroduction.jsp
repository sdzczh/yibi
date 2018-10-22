<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/static/commons/global.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="renderer" content="webkit">
	<title>币种简介</title>
	<%@ include file="/static/commons/commoncss.jsp" %>
	<%@ include file="/static/commons/commonjs.jsp" %>
    <script type="text/javascript">
        $(function(){
            var id = '${coinIntroductionId }';
            if(id != null){
                $('#dataForm').form('load', '${ctx}/coinIntroduction/loadForm.do?id=${coinIntroductionId }');
            }
        });
    </script>
</head>
<body>
<form id="dataForm" method="post" action="${ctx}/coinIntroduction/operCoinIntroduction.do">
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
			<td>发行总量</td>
			<td>
				<input type="text" name="releasetotalamt" value="" class="enumberbox" data-options="required:true" style="width: 180px;"/>
			</td>
			<td>流通总量</td>
			<td>
				<input type="text" name="circulationtotalamt" value="" class="enumberbox" data-options="" style="width: 180px;"/>
			</td>
		</tr>
		<tr>
			<td>众筹价格</td>
			<td>
				<input type="text" name="crowdprice" value="" class="enumberbox" data-options="required:true" style="width: 180px;"/>
			</td>
			<td>白皮书</td>
			<td>
				<input type="text" name="whitepaper" value="" class="enumberbox" data-options="" style="width: 180px;"/>
			</td>
		</tr>
		<tr>
			<td>官网地址</td>
			<td>
				<input type="text" name="officialneturl" value="" class="enumberbox" data-options="required:true" style="width: 180px;"/>
			</td>
			<td>区块查询地址</td>
			<td>
				<input type="text" name="blockquery" value="" class="enumberbox" data-options="" style="width: 180px;"/>
			</td>
		</tr>

		<tr>
			<td>简介</td>
			<td>
				<input type="text" name="introduction" value="" class="enumberbox" data-options="required:true" style="width: 180px;"/>
			</td>
			<td>发行时间</td>
			<td>
				<input type="text" name="releasetime" value="" class="enumberbox" data-options="required:true" style="width: 180px;"/>
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