<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
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

    <script language="javascript">

        var treeGrid;
        $(function () {
            top.refreshdata = refreshdata;
            treeGrid = $("#treeGrid").treegrid({
                url: "${ctx}/resource/list.do",
                striped: true,
                singleSelect: true,
                idField: "id",
                treeField: "name",
                parentField: "pid",
                fitColumns: true,
                border: false,
                columns: [[{
                    title: "资源名称",
                    field: "name",
                    width: 250
                },{
                    title: "资源访问url",
                    field: "url",
                    width: 300
                }, {
                    title: "资源描述",
                    field: "description",
                    width: 150
                },{
                    title: "资源类型",
                    field: "resourcetype",
                    width: 150,
                    formatter: function (value, row, index) {
                        var str ='';
                        if(value == 0){
                            str = '菜单';
                        }
                        if(value == 1){
                            str = '按钮';
                        }
                        return str;
                    }
                },  {
                    title: "排序",
                    field: "seq",
                    width: 200
                }

                ]],
                toolbar: "#toolbar"
            });
        });

        function refreshdata() {
            treeGrid.treegrid("reload");
            treeGrid.treegrid("clearSelections");
        }


        function addFun() {
            var rows = treeGrid.treegrid("getSelections");
            if (rows != "") {
                if (rows.length > 1) {
                    top.$.messager.alert("信息提示", "最多选中某一行！");
                    treeGrid.treegrid("clearSelections");
                } else {
                    top.showTopDialog("新增页面", "${ctx}/resource/resourceAdd.do?pid=" + rows[0].id, "600", "220", "no");
                }
            } else {
                top.$.messager.confirm("确认对话框", "您是否要添加顶级节点？", function (b) {
                    if (b) {
                        top.showTopDialog("新增页面", "${ctx}/resource/resourceAdd.do?pid=0", "600", "220", "no");
                    } else {

                    }
                });
            }
        }

        function updateFun() {
            var rows = treeGrid.treegrid("getSelections");
            if (rows != "") {
                if (rows.length > 1) {
                    top.$.messager.alert("信息提示框", "只能选中某一行！");
                    treeGrid.treegrid("clearSelections");
                } else {
                    top.showTopDialog("编辑页面", "${ctx}/resource/resourceUpdatePage.do?id=" + rows[0].id, "600", "220", "no");
                }
            } else {
                top.$.messager.alert("信息提示框", "必须选中某一行！");
            }
        }

        function deleteFun() {
            var rows = treeGrid.treegrid("getSelections");
            if (rows != "") {
                top.$.messager.confirm("确认对话框", "您是否要删除当前数据？", function (b) {
                    if (b) {
                        $.ajax({
                            type: "POST",
                            url: "${ctx}/resource/deleteResource.do",
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

</div>
<div data-options="region:'center',border:true">
    <table id="treeGrid" data-options="fit:true,border:false"></table>
</div>
</body>
</html>