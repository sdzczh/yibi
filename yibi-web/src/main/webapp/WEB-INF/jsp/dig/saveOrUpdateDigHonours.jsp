<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/static/commons/global.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="renderer" content="webkit">
	<title>挖矿分区管理</title>
	<%@ include file="/static/commons/commoncss.jsp" %>
	<%@ include file="/static/commons/commonjs.jsp" %>
    <script type="text/javascript">
        $(function(){
            var id = '${id }';
            if(id != null){
                $('#dataForm').form('load', '${ctx}/dig/loadForm.do?id=${id }');
            }
        });
    </script>
</head>
<body>
<form id="dataForm" method="post" action="${ctx}/dig/saveOrUpdateDigHonours.do" enctype="multipart/form-data">
	<table class="grid">
		<tr>
			<td>矿场名字</td>
			<td>
				<input type="hidden"  name="id" value="" />

                <input type="text" name="minename" value="" class="easyui-textbox" data-options="required:true" style="width: 180px;"/>

			</td>
			<td>矿场的背景图</td>
			<td>
				<input name="mineFile" class="easyui-filebox" data-options="buttonText:'选择文件', accept:'	image/png' " style="width: 180px;"/>
			</td>
		</tr>
		<tr>
			<td>角色名字</td>
			<td>
                <input type="text" name="rolename" value="" class="easyui-textbox" data-options="required:true" style="width: 180px;"/>

			</td>
			<td>角色等级</td>
			<td>
                <input type="text" name="rolegrade" value="" class="easyui-textbox" data-options="required:true" style="width: 180px;"/>
			</td>
		</tr>
		<tr>
			<td>魂力最小值</td>
			<td>
                <input type="text" name="soulminforce" value="" class="easyui-textbox" data-options="required:true" style="width: 180px;"/>

			</td>
			<td>魂力最大值</td>
			<td>
                <input type="text" name="soulmaxforce" value="" class="easyui-textbox" data-options="required:true" style="width: 180px;"/>
			</td>
		</tr>
		<tr>
			<td>角色图标</td>
			<td>
				<input name="honoursFile" class="easyui-filebox" data-options="buttonText:'选择文件', accept:'	image/png' " style="width: 180px;"/>
			</td>
			<td>选择币种</td>
			<td>
				<c:forEach var="coin" items="${coins }" varStatus="status">
					<c:set var="coinname" value="${coin.coinname }" />
					<input <c:forEach var="coinName" items="${coinNames }"> <c:if test="${coinName ==coinname }">checked="checked"</c:if></c:forEach> type="checkbox" name="coinType" value="${coin.cointype }" >${coin.coinname }
					<c:if test="${status.count%5==0}" >
						<br>
					</c:if>
				</c:forEach>
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