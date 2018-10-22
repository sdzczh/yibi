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
                $('#dataForm').form('load', '${ctx}/system/loadForm.do?id=${id }');
            }
        });
    </script>
</head>
<body>
<form id="dataForm" method="post" action="${ctx}/system/saveOrUpdateSystemParam.do">
	<table class="grid">
		<tr>
			<td>变量名</td>
			<td>
				<input type="hidden"  name="id" value="" />

                <input type="text" name="keyname" value="" class="easyui-textbox" data-options="required:true" style="width: 180px;"/>

			</td>
			<td>变量值</td>
			<td>
                <input type="text" name="keyval" value="" class="easyui-textbox" data-options="required:true" style="width: 180px;"/>
			</td>
		</tr>
		<tr>
			<td>备注</td>
			<td>
				<input type="text" name="remark" value="" class="easyui-textbox" data-options="required:true" style="width: 180px;"/>
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