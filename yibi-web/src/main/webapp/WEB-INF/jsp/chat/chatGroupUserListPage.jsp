<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/static/commons/global.jsp" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="renderer" content="webkit">
	<title>机器人任务管理</title>
	<%@ include file="/static/commons/commoncss.jsp" %>
	<%@ include file="/static/commons/commonjs.jsp" %>

	<script language="javascript">
        var chatId = '<%=request.getAttribute("chatId")%>';
        var dataGrid;
        $(function() {
			var chatId = '<%=request.getAttribute("chatId")%>';
            top.refreshdata = refreshdata;
            dataGrid = $("#dataGrid").datagrid({
                url : "${ctx}/chat/chatGroupUserList.do?chatId="+chatId,
                striped : true,
                singleSelect:true,
                rownumbers : true,
                pagination:true,
                fitColumns : true,
                idField : 'id',
                columns : [ [ {
                    width : 200,
                    title : "账户",
                    field : "phone",
                    width : 200,
                    formatter: function (value, row, index) {
                        if (value != null){return value;}
                        if(value==null || value == ''){
                            return '没有此账号，虚假用户';
                        }
                    }
                },{
                    title : "所属群组名称",
                    field : "name",
                    width : 200
                },{
                    title : "成员角色",
                    field : "role",
                    width : 200,
                    formatter: function (value, row, index) {
                        if(value == 0){
                            return '群主';
                        }
                        if(value == 1){
                            return '管理员';
                        }
                        if(value == 2){
                            return '成员';
                        }
                        if(value==null || value == ''){
                            return '';
                        }
                        return str;
                    }
                },{
                    title : "发言状态",
                    field : "state",
                    width : 200,
                    formatter: function (value, row, index) {
                        if(value == 0){
                            return '禁言';
                        }
                        if(value == 1){
                            return '正常';
                        }
                        if(value == 2){
                            return '免打扰';
                        }
                        if(value==null || value == ''){
                            return '';
                        }
                        return str;
                    }
                },{
                    title : "更新时间",
                    field : "updateTime",
                    width : 200,
                    formatter: function (value, row, index) {
                        if(value==null || value == ''){
                            return '';
                        }
                        var str = timestampToTime(value,true);
                        return str;
                    }
                }
                ] ],
                toolbar : "#toolbar"
            });
        });

        function refreshdata() {
            dataGrid.datagrid("reload");
            dataGrid.datagrid("clearSelections");
        }

        function addFun() {
            top.showTopDialog("新增页面", "${ctx}/chat/addChatUserPage.do?chatId=${chatId }", "600", "220", "no");
        }

        function deleteUser() {
            var rows = dataGrid.datagrid("getSelections");
            if (rows!="") {
                top.$.messager.confirm("确认对话框", "您是否要移除群聊？", function(b) {
                    if(b){
                        $.ajax({
                            type: "POST",
                            url: "${ctx}/chat/leaveChatGroup.do",
                            data: {
                                "id" : rows[0].id,
                                "type":type
                            },
                            dataType: "json",
                            async:false ,
                            success: function(result) {
                                top.$.messager.alert("信息提示框", result.msg);
                                refreshdata();
                            }
                        });
                    }else{

                    }
                });

            } else {
                top.$.messager.alert("信息提示框", "至少选中某一行！");
            }
        }

        function permit() {
            var rows = dataGrid.datagrid("getSelections");
            if (rows!="") {
                top.$.messager.confirm("确认对话框", "您是否要恢复正常发言？", function(b) {
                    if(b){
                        $.ajax({
                            type: "POST",
                            url: "${ctx}/chat/permit.do",
                            data: {
                                "id" : rows[0].id,
                                "type":type
                            },
                            dataType: "json",
                            async:false ,
                            success: function(result) {
                                top.$.messager.alert("信息提示框", result.msg);
                                refreshdata();
                            }
                        });
                    }else{

                    }
                });

            } else {
                top.$.messager.alert("信息提示框", "至少选中某一行！");
            }
        }

        function banned() {
            var rows = dataGrid.datagrid("getSelections");
            if (rows != "") {
                if (rows.length > 1) {
                    top.$.messager.alert("信息提示框", "只能选中某一行！");
                    dataGrid.datagrid("clearSelections");
                } else {
                    top.showTopDialog("编辑页面", "${ctx}/chat/bannedChatUserPage.do?id=" + rows[0].id, "600", "620", "no");
                }
            } else {
                top.$.messager.alert("信息提示框", "必须选中某一行！");
            }
        }

        function doSearch(){
            $('#dataGrid').datagrid('load',{
                phone: $('#phone').val(),
                remark: $('#remark').val(),
                chatId: $('#chatId').val()
            });
        }

	</script>
</head>
<body class="easyui-layout" data-options="fit:true,border:false">
<div id="toolbar" style="display: none;">
	<a href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-add'" onclick="addFun()">加入群聊</a>

	<a href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-add'" onclick="deleteUser()">移除群聊</a>

	<a href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-edit'" onclick="banned()">禁言</a>
	<a href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-edit'" onclick="permit()">解禁</a>

</div>
<div data-options="region:'center',border:true">
	<div class="easyui-layout" data-options="fit:true">
		<div data-options="region:'north',border:false" style="height: 30px; overflow: hidden;background-color: #fff">
			<form id="searchForm">
				<input type="hidden" id="chatId" name="chatId" value="${chatId}"/>
				<table>
					<tr>
						<th>用户账号:</th>
						<td>
							<input type="text" id="phone" name="phone" value=""
								   class="dfinput" style="width:160px;float: none;"/>
						</td>
						<th>昵称:</th>
						<td>
							<input type="text" id="remark" name="remark" value=""
								   class="dfinput" style="width:160px;float: none;"/>
						</td>
						<td>
							&nbsp;
							<a href="javascript:void(0);" onclick="doSearch()" class="easyui-linkbutton"
							   data-options="iconCls:'icon-search'">查询</a>
						</td>
					</tr>
				</table>
			</form>
		</div>

		<div data-options="region:'center',border:true">
			<table id="dataGrid" data-options="fit:true,border:false"></table>
		</div>
	</div>
</div>
</body>
</html>