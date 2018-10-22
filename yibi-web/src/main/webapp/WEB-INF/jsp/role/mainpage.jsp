<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ include file="/static/commons/global.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="renderer" content="webkit">
    <title>系统角色管理</title>
    <%@ include file="/static/commons/commoncss.jsp" %>
    <%@ include file="/static/commons/commonjs.jsp" %>

    <script language="javascript">
        var dataGrid;
        $(function () {

            top.refreshdata = refreshdata;
            dataGrid = $("#dataGrid").datagrid({
                url: "${ctx}/role/list.do",
                striped: true,
                singleSelect: true,
                rownumbers: true,
                fitColumns: true,
                idField: 'id',
                columns: [[{
                    width: 200,
                    title: "角色名",
                    field: "rolename",
                    width: 200
                }, {
                    title: "角色编号",
                    field: "rolecode",
                    width: 200
                }, {
                    title: "角色描述",
                    field: "roledesc",
                    width: 200
                }, {
                    title: "顺序",
                    field: "ordernum",
                    width: 200
                }
                ]],
                toolbar: "#toolbar"
            });
        });

        function refreshdata() {
            dataGrid.datagrid("reload");
            dataGrid.datagrid("clearSelections");
        }


        function addFun() {
            top.showTopDialog("新增页面", "${ctx}/role/addRolePage.do", "600", "220", "no");
        }

        function updateFun() {
            var rows = dataGrid.datagrid("getSelections");
            if (rows != "") {
                if (rows.length > 1) {
                    top.$.messager.alert("信息提示框", "只能选中某一行！");
                    dataGrid.datagrid("clearSelections");
                } else {
                    top.showTopDialog("编辑页面", "${ctx}/role/updateRolePage.do?id=" + rows[0].id, "600", "220", "no");
                }
            } else {
                top.$.messager.alert("信息提示框", "必须选中某一行！");
            }
        }

        function deleteFun() {
            var rows = dataGrid.datagrid("getSelections");
            if (rows != "") {
                if (rows[0].id == 1) {
                    top.$.messager.alert("信息提示框", "不可删除开发管理员");
                    return;
                }
                top.$.messager.confirm("确认对话框", "您是否要删除当前数据？", function (b) {
                    if (b) {
                        $.ajax({
                            type: "POST",
                            url: "${ctx}/role/deleteRole.do",
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

        function shouquanFun() {
            var rows = dataGrid.datagrid("getSelections");
            if (rows != "") {
                if (rows.length > 1) {
                    top.$.messager.alert("信息提示框", "只能选中某一行！");
                    dataGrid.datagrid("clearSelections");
                } else {
                    top.showTopDialog("授权页面", "${ctx}/role/addResource.do?roleid=" + rows[0].id, "400", "400", "no");
                }
            } else {
                top.$.messager.alert("信息提示框", "必须选中某一行！");
            }
        }

    </script>
</head>
<body class="easyui-layout" data-options="fit:true,border:false">
<div id="toolbar" style="display: none;">
    <shiro:hasPermission name="/role_mainpage_add">
    <a href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-add'"
       onclick="addFun()">新增</a>
    </shiro:hasPermission>
    <a href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-edit'"
       onclick="updateFun()">编辑</a>

    <a href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-remove'"
       onclick="deleteFun()">删除</a>


    <a href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-role'"
       onclick="shouquanFun()">资源授权</a>


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