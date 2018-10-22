<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ include file="/static/commons/global.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="renderer" content="webkit">
    <title>银行卡管理</title>
    <%@ include file="/static/commons/commoncss.jsp" %>
    <%@ include file="/static/commons/commonjs.jsp" %>

    <script language="javascript">
        var dataGrid;
        $(function () {

            top.refreshdata = refreshdata;
            dataGrid = $("#dataGrid").datagrid({
                url: "${ctx}/bank/banlList.do",
                striped: true,
                singleSelect: true,
                rownumbers: true,
                pagination: true,
                fitColumns: true,
                idField: 'id',
                columns: [[{
                    width: 200,
                    title: "银行卡名",
                    field: "name",
                    width: 200
                }, {
                    title: "状态",
                    field: "state",
                    width: 200
                }, {
                    title: "管理员名字",
                    field: "operName",
                    width: 200
                }, {
                    title: "更新时间",
                    field: "updateTime",
                    width: 200,
                    formatter: function (value, row, index) {
                        if (value == null || value == '') {
                            return '';
                        }
                        var str = timestampToTime(value, true);
                        return str;
                    }
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
            top.showTopDialog("新增页面", "${ctx}/bank/addBankPage.do", "600", "220", "no");
        }

        function updateFun() {
            var rows = dataGrid.datagrid("getSelections");
            if (rows != "") {
                if (rows.length > 1) {
                    top.$.messager.alert("信息提示框", "只能选中某一行！");
                    dataGrid.datagrid("clearSelections");
                } else {
                    top.showTopDialog("编辑页面", "${ctx}/system/sysUser/updateUI.do?id=" + rows[0].id, "600", "220", "no");
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
                            url: "${ctx}/manager/deleteManager.do",
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
                    top.showTopDialog("授权页面", "${ctx}/manager/addRole.do?managerid=" + rows[0].id, "400", "400", "no");
                }
            } else {
                top.$.messager.alert("信息提示框", "必须选中某一行！");
            }
        }


        function updatepwdFun() {
            var rows = dataGrid.datagrid("getSelections");
            if (rows != "") {
                top.$.messager.confirm("确认对话框", "您是否要重置账户密码？", function (b) {
                    if (b) {
                        $.ajax({
                            type: "POST",
                            url: "${ctx}/system/sysUser/updatepwd.do",
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
    </script>
</head>
<body class="easyui-layout" data-options="fit:true,border:false">
<div id="toolbar" style="display: none;">
    <a href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-add'"
       onclick="addFun()">新增</a>


    <a href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-edit'"
       onclick="updateFun()">编辑</a>


    <a href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-remove'"
       onclick="deleteFun()">删除</a>


    <a href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-role'"
       onclick="shouquanFun()">用户授权</a>


    <a href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-user_key'"
       onclick="updatepwdFun()">密码重置</a>

</div>
<div data-options="region:'center',border:true">
    <div class="easyui-layout" data-options="fit:true">
        <div data-options="region:'north',border:false" style="height: 30px; overflow: hidden;background-color: #fff">
            <form id="searchForm">
                <input type="hidden" id="deptid" name="deptid" value=""/>
                <table>
                    <tr>
                        <th>用户姓名:</th>
                        <td>
                            <input type="text" id="username" name="username" value="${queryBean.username}"
                                   class="dfinput" style="width:160px;float: none;"/>
                        </td>
                        <th>登录名称:</th>
                        <td>
                            <input type="text" id="loginname" name="loginname" value="${queryBean.loginname}"
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