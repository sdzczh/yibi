<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/static/commons/global.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="renderer" content="webkit">
<title>聊天室管理</title>
	<%@ include file="/static/commons/commoncss.jsp" %>
	<%@ include file="/static/commons/commonjs.jsp" %>

<script language="javascript">
	var dataGrid;
	$(function() {
		
		top.refreshdata = refreshdata;
		dataGrid = $("#dataGrid").datagrid({
			url : "${ctx}/chat/talkGroupList.do",
			striped : true,
			singleSelect:true,
			rownumbers : true,
            pagination:true,
			fitColumns : true,
			idField : 'id',
			columns : [ [ {
				width : 200,
				title : "聊天室名字",
				field : "name",
				width : 200
			},{
                width : 200,
                title : "聊天室描述",
                field : "decription",
                width : 200
            },{
				title : "聊天室头像",
				field : "imgurl",
				width : 200,
				height:300,
                formatter: function (value, row, index) {
                    if(value==null || value == ''){
                        return '';
                    }
                    return '<img src='+value+' height="100" width="200">';
                }
			},{
                title : "群组类型",
                field : "type",
                width : 200,
				formatter: function (value, row, index) {
                    if (value == 0){
                        return '聊天室';
                    }
                    if (value == 1){
                        return '群组';
                    }
                    if(value==null || value == ''){
                        return '';
                    }
                }
			},{
				title : "更新时间",
				field : "updatetime",
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
		top.showTopDialog("新增页面", "${ctx}/chat/saveOrUpdateChatPage.do", "800", "420","no");
	}

	function updateFun() {
		var rows = dataGrid.datagrid("getSelections");
		if (rows!="") {
			if(rows.length>1){
				top.$.messager.alert("信息提示框", "只能选中某一行！");
				dataGrid.datagrid("clearSelections");
			}else{
				top.showTopDialog("编辑页面", "${ctx}/chat/saveOrUpdateChatPage.do?id=" + rows[0].id, "800", "420","no");
			}
		} else {
			top.$.messager.alert("信息提示框", "必须选中某一行！");
		}
	}
	function LookFun() {
		var rows = dataGrid.datagrid("getSelections");
		if (rows!="") {
			if(rows.length>1){
				top.$.messager.alert("信息提示框", "只能选中某一行！");
				dataGrid.datagrid("clearSelections");
			}else{
				top.showTopDialog("编辑页面", "${ctx}/chat/chatGroupUserListPage.do?id=" + rows[0].id, "1200", "820","no");
			}
		} else {
			top.$.messager.alert("信息提示框", "必须选中某一行！");
		}
	}

    function deleteFun() {
        var rows = dataGrid.datagrid("getSelections");
        if (rows!="") {
            top.$.messager.confirm("确认对话框", "您是否要解散群组？", function(b) {
                if(b){
                    $.ajax({
                        type: "POST",
                        url: "${ctx}/chat/deleteChatGroup.do",
                        data: {
                            "id" : rows[0].id
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

    function doSearch(){
        $('#dataGrid').datagrid('load',{
            remark: $('#remark').val()
        });
    }
	
</script>
</head>
<body class="easyui-layout" data-options="fit:true,border:false">
	<div id="toolbar" style="display: none;">
			<a href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-add'" onclick="addFun()">新增</a>


			<a href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-edit'" onclick="updateFun()">编辑</a>
			<a href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-edit'" onclick="deleteFun()">解散</a>
			<a href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-edit'" onclick="LookFun()">查看成员</a>

	</div>
	<div data-options="region:'center',border:true">
		<div class="easyui-layout" data-options="fit:true">
			<div data-options="region:'center',border:true">
				<table id="dataGrid" data-options="fit:true,border:false"></table>
			</div>
		</div>
	</div>
</body>
</html>