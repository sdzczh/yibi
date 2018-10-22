<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/static/commons/global.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="renderer" content="webkit">
<title>文档管理</title>
	<%@ include file="/static/commons/commoncss.jsp" %>
	<%@ include file="/static/commons/commonjs.jsp" %>

	<script src="${cxt}/js/jquery.lightbox-0.5.js"></script>
<script language="javascript">

	var dataGrid;
	$(function() {
		
		top.refreshdata = refreshdata;
		dataGrid = $("#dataGrid").datagrid({
			url : "${ctx}/docManage/docList.do",
			striped : true,
			singleSelect:true,
			fitColumns : true,
			idField : 'id',
			columns : [ [ {
				width : 200,
				title : "类型",
				field : "type",
                width : 200,
				formatter:function (value,row,index) {
					if (value == 0){
						return '费率';
					}
					if (value == 1){
                        return '交易挖矿';
					}
					if (value == 2){
                        return '余币宝';
					}
					if (value == 3){
                        return '挖矿秘籍';
					}
					if (value == 4){
                        return '矿区介绍';
                    }
					if (value == 5){
                        return '魂力规则';
                    }
					if (value == 6){
                        return '注册协议';
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
		top.showTopDialog("新增页面", "${ctx}/docManage/addDocPage.do", "600", "820","no");
	}

    function updateFun() {
        var rows = dataGrid.datagrid("getSelections");
        if (rows!="") {
            if(rows.length>1){
                top.$.messager.alert("信息提示框", "只能选中某一行！");
                dataGrid.datagrid("clearSelections");
            }else{
                top.showTopDialog("编辑页面", "${ctx}/docManage/updateDocPage.do?id=" + rows[0].id, "600", "820","no");
            }
        } else {
            top.$.messager.alert("信息提示框", "必须选中某一行！");
        }
    }

    function deleteFun() {
        var rows = dataGrid.datagrid("getSelections");
        if (rows != "") {
            top.$.messager.confirm("确认对话框", "您是否要删除当前数据？", function (b) {
                if (b) {
                    $.ajax({
                        type: "POST",
                        url: "${ctx}/docManage/deleteDoc.do",
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
            type: $('#type').val()
        });
    }

</script>
</head>
<body class="easyui-layout" data-options="fit:true,border:false">
	<div id="toolbar" style="display: none;">
			<a href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-add'" onclick="addFun()">新增</a>

			<a href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-remove'" onclick="updateFun()">修改</a>
			<a href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-remove'" onclick="deleteFun()">删除</a>
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