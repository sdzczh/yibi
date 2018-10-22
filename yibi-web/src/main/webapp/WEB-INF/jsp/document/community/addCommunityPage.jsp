<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/static/commons/global.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="renderer" content="webkit">
	<title>新增社群</title>
	<%@ include file="/static/commons/commoncss.jsp" %>
	<%@ include file="/static/commons/commonjs.jsp" %>

	<script type="text/javascript">
        $(function(){
            var id = '${communityId }';
            if(id != null){
                $('#dataForm').form('load', '${ctx}/community/loadForm.do?id=${communityId }');
            }
        });
	</script>
</head>
<body>
<form id="dataForm" method="post" action="${ctx}/community/saveOrUpdateCommunity.do"  enctype="multipart/form-data">
	<table class="grid">
		<tr>
			<td>社群类型</td>
			<td>
				<input type="hidden"  name="id" value="" />
				<select class="easyui-combobox" id="type" name="type">
					<option value="">--社群类型--</option>
					<option value="0">QQ群</option>
					<option value="1">微信号</option>
					<option value="2">公众号</option>
					<option value="3">微博</option>
				</select>
			</td>
			<td>二维码图片</td>
			<td>
				<input name="file" class="easyui-filebox" data-options="buttonText:'选择文件', accept:'	image/png' " style="width: 180px;"/>
			</td>
		</tr>
		<tr>
			<td>信息</td>
			<td>
				<input type="text" name="keyval" value="" class="easyui-textbox" data-options="required:true" style="width: 180px;"/>
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