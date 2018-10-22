<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/static/commons/global.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="renderer" content="webkit">
<title>系统资源管理</title>
	<%@ include file="/static/commons/commoncss.jsp" %>
	<%@ include file="/static/commons/commonjs.jsp" %>

<script type="text/javascript">
	function submitForm() {

		var formObj = $("#dataForm");
		if (formObj.form("validate")) {
			$.ajax({
				url : "${ctx}/resource/add.do",
				data : formObj.serialize(),
				type : "POST",
				dataType : "json",
				success : function(result) {
					if (result.success == true) {
						top.$.messager.alert("信息提示框", result.msg);
						top.refreshdata();
						top.closeTopDialog();
					}else{
						top.$.messager.alert("信息提示框", result.msg);
					}
				}
			});

		}
	}
</script>
</head>
<body>
	<form id="dataForm" method="post">
		<table class="grid">
			<tr>
				<td>资源名称</td>
				<td>
					<input type="text" name="name" value="" class="easyui-textbox" data-options="required:true" style="width: 180px;"/>
				</td>
				<td>资源描述</td>
				<td>
					<input type="text" name="description" value="" class="easyui-textbox" data-options="" style="width: 180px;"/>
				</td>
			</tr>
			<tr>
				<td>上级资源</td>
				<td>
					<input type="hidden" id="citySelIdOne" name="pid" value="${resource.id}" />
					<input type="text" id="citySelNameOne" name="pmenuname" value="${resource.name}" class="easyui-textbox" style="width: 180px;" readonly="readonly" />
				</td>
				<td>资源类型</td>
				<td>
					<select id="resourcetype" name="resourcetype" class="easyui-combobox" data-options="required:true" style="width: 180px;" editable="false" >
						<option value="0">菜单</option>
						<option value="1">按钮</option>
					</select>

				</td>
			</tr>
			<tr>
				<td>资源路径</td>
				<td>
					<input type="text" name="url" value="" class="easyui-textbox"  style="width: 180px;" data-options="required:true"/>
				</td>
				<td>显示顺序</td>
				<td>
					<input type="text" name="seq" value="" class="easyui-numberbox" data-options="required:true,min:0,max:2000000000,precision:0" style="width: 180px;"/>
				</td>
			</tr>
			<tr>
				<td colspan="4" align="center">
					<a href="javascript:void(0)" class="easyui-linkbutton" style="width:80px" onclick="submitForm()">保存</a>
					<a href="javascript:void(0)" class="easyui-linkbutton" style="width:80px" onclick="top.closeTopDialog();">关闭</a>
				</td>
			</tr>
		</table>
	</form>
</body>
</html>