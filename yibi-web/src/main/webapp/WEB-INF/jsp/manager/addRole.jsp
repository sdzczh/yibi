<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/static/commons/global.jsp" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="renderer" content="webkit">
	<title>系统管理员管理</title>
	<%@ include file="/static/commons/commoncss.jsp" %>
	<%@ include file="/static/commons/commonjs.jsp" %>

<script type="text/javascript">
	var easyuitreeOne;
	$(function() {
		easyuitreeOne = $("#easyuitreeOne").tree({
            url:'${ctx}/manager/getRoleTree.do?managerid=${managerid}',
			checkbox : true,
			cascadeCheck: false,
			onCheck : function (node, checked) {  
            	if (checked) {  
                    var parentNode = $("#easyuitreeOne").tree('getParent', node.target);  
                    if (parentNode != null) {  
                        $("#easyuitreeOne").tree('check', parentNode.target);  
                    }  
                } else {  
                    var childNode = $("#easyuitreeOne").tree('getChildren', node.target);  
                    if (childNode.length > 0) {  
                        for (var i = 0; i < childNode.length; i++) {  
                            $("#easyuitreeOne").tree('uncheck', childNode[i].target);  
                        }  
                    }  
				}  
			}
		});

	});

	function submitForm() {
		var arr = [];
		var checkeds = $('#easyuitreeOne').tree('getChecked', 'checked');
		for (var i = 0; i < checkeds.length; i++) {
			arr.push(checkeds[i].id);
		}
		$("#roleArr").val(arr.join(','));
		var formObj = $('#dataForm');
		if (formObj.form('validate')) {
			$.ajax({
				url : "${ctx}/manager/doAddRole.do",
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
	<div class="easyui-layout" data-options="fit:true,border:false">
		<div data-options="region:'center',border:false" title="" style="padding: 3px;">
			<form id="dataForm" method="post">
				<input type="hidden" id="managerid" name="managerid" value="${managerid}" />
				<input type="hidden" id="roleArr" name="roleArr"/>
				<table class="grid">
					<tr>
						<td>
							<ul id="easyuitreeOne" class="easyui-tree"></ul>
						</td>
					</tr>
					<tr>
						<td align="center">
							<a href="javascript:void(0)" class="easyui-linkbutton" style="width:80px" onclick="submitForm()">保存</a>
						</td>
					</tr>
				</table>
			</form>
		</div>
	</div>
</body>
</html>