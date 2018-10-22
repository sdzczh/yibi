<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/static/commons/global.jsp" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="renderer" content="webkit">
	<title>机器人管理</title>
	<%@ include file="/static/commons/commoncss.jsp" %>
	<%@ include file="/static/commons/commonjs.jsp" %>

	<script language="javascript">
        var dataGrid;
        $(function() {

            top.refreshdata = refreshdata;
            dataGrid = $("#dataGrid").datagrid({
                url : "${ctx}/robot/robotList.do",
                striped : true,
                singleSelect:true,
                rownumbers : true,
                pagination:true,
                fitColumns : true,
                idField : 'id',
                columns : [ [ {
                    width : 200,
                    title : "币种名称",
                    field : "coinName",
                    width : 200
                },{
                    title : "使用状态",
                    field : "state",
                    width : 200,
                    formatter: function (value, row, index) {
                        if(value == 0){
                            return '删除了';
                        }
                        if(value == 1){
                            return '使用';
                        }
                        if(value==null || value == ''){
                            return '';
                        }
                        return str;
                    }
                },{
                    title : "操作人",
                    field : "userName",
                    width : 200
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
            top.showTopDialog("新增页面", "${ctx}/robot/addRobotPage.do", "600", "620","no");
        }

        function lookFun() {
            var rows = dataGrid.datagrid("getSelections");
            if (rows!="") {
                if(rows.length>1){
                    top.$.messager.alert("信息提示框", "只能选中某一行！");
                    dataGrid.datagrid("clearSelections");
                }else{
                    top.showTopDialog("编辑页面", "${ctx}/robot/robotTaskListPage.do?id=" + rows[0].id, "1800", "620","no");
                }
            } else {
                top.$.messager.alert("信息提示框", "必须选中某一行！");
            }
        }

        function operRobot(type) {
            var rows = dataGrid.datagrid("getSelections");
            if (rows!="") {
                top.$.messager.confirm("确认对话框", "您是否要修改当前数据？", function(b) {
                    if(b){
                        $.ajax({
                            type: "POST",
                            url: "${ctx}/robot/operRobot.do",
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

        function deleteFun() {
            var rows = dataGrid.datagrid("getSelections");
            if (rows != "") {
                top.$.messager.confirm("确认对话框", "您是否要删除当前数据？", function (b) {
                    if (b) {
                        $.ajax({
                            type: "POST",
                            url: "${ctx}/robot/deleteRobot.do",
                            data: {
                                "id": rows[0].id
                            },
                            dataType: "json",
                            async: false,
                            success: function (result) {
                                top.$.messager.alert("信息提示框", result.msg);
                                refreshdata();
                            }
                        });
                    } else {

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
	<%--<a href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-add'" onclick="operRobot(1)">开启</a>--%>
	<%--<a href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-edit'" onclick="operRobot(0)">关闭</a>--%>
	<a href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-edit'" onclick="lookFun()">查看详情</a>
	<%--<a href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-remove'" onclick="deleteFun()">删除</a>--%>
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