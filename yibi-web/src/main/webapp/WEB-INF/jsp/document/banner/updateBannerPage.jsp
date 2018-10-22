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
    <script type="text/javascript">
        $(function(){
            var id = '${id }';
            if(id != null){
                $('#dataForm').form('load', '${ctx}/document/loadForm.do?id=${id }');
            }
        });
    </script>
</head>
<body>
<form id="dataForm" method="post" action="${ctx}/document/saveOrUpdateBanner.do"  enctype="multipart/form-data">
	<table class="grid">
		<tr>
			<td>Banner图片</td>
			<td>
				<input type="hidden"  name="id" value="" />
				<input name="file" class="easyui-filebox" data-options="buttonText:'选择文件', accept:'	image/png' " style="width: 180px;"/>
			</td>

			<td>使用状态  0:废弃 1:正常</td>
			<td>
				<input type="text" name="state" value=""  class="easyui-textbox" data-options="required:true" style="width: 180px;"/>
			</td>
		</tr>
		<tr>
			<td>跳转类型 0:app内，1:网站</td>
			<td>
				<input type="text" name="type" value=""  class="easyui-textbox" data-options="required:true" style="width: 180px;"/>
			</td>
			<td>跳转地址</td>
			<td>
				<input type="text" name="address" value="" class="easyui-textbox" data-options="required:true" style="width: 180px;"/>
			</td>
		</tr>
		<tr>

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