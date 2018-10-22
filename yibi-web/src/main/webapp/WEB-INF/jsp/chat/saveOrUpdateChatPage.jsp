<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/static/commons/global.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="renderer" content="webkit">
	<title>聊天室管理</title>
	<%@ include file="/static/commons/commoncss.jsp" %>
	<%@ include file="/static/commons/commonjs.jsp" %>
	<script type="text/javascript">
        $(function(){
            var id = '${id }';
            if(id != null){
                $('#dataForm').form('load', '${ctx}/chat/loadChatForm.do?id=${id }');
            }
        });
	</script>

</head>
<body>
<form id="dataForm" method="post" action="${ctx}/chat/saveOrUpdateChat.do" enctype="multipart/form-data">
	<table class="grid">
		<tr>
			<td>聊天室名字</td>
			<td>
				<input type="hidden" id="" name="id" value="" />
				<input type="text" id="name" name="name" value="" class="enumberbox" data-options="required:true" style="width: 180px;"/>
			</td>
			<td>聊天室描述</td>
			<td>
				<input type="text" name="decription" value="" class="enumberbox" data-options="" style="width: 180px;"/>
			</td>
		</tr>
		<tr>
			<td>聊天室描述 0:聊天室 1：群组</td>
			<td>
				<input type="text" name="type" value="" class="enumberbox" data-options="" style="width: 180px;"/>
				<%--<select class="easyui-combobox" id="bannertype" name="bannertype">--%>
					<%--<option value="">--类型--</option>--%>
					<%--<option value="0">聊天室</option>--%>
					<%--<option value="1">群组</option>--%>
				<%--</select>--%>
			</td>
			<td>聊天室头像</td>
			<td>
				<input name="file" class="easyui-filebox" data-options="buttonText:'选择文件', accept:'	image/png' " style="width: 180px;"/>
			</td>
		</tr>
		<tr>
			<td>群组Id(群组Id不能修改)</td>
			<td>
				<input type="text" name="groupid" value="" class="enumberbox"  data-options="" style="width: 180px;"/>
			</td>
			<td>群主手机号</td>
			<td>
				<input type="text" name="phone" value="" class="enumberbox" placeholder="更新操作，此处不要填写内容" data-options="" style="width: 180px;"/>
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