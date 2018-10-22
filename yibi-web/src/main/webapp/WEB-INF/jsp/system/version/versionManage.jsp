<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/static/commons/global.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="renderer" content="webkit">
<title>app版本管理</title>
	<%@ include file="/static/commons/commoncss.jsp" %>
	<%@ include file="/static/commons/commonjs.jsp" %>

<script language="javascript">
	var dataGrid;
	$(function() {
		
		top.refreshdata = refreshdata;
		dataGrid = $("#dataGrid").datagrid({
			url : "${ctx}/versionManage/versionManageList.do",
			striped : true,
			singleSelect:true,
			rownumbers : true,
            pagination:true,
			fitColumns : true,
			idField : 'id',
			columns : [ [ {
				width : 200,
				title : "手机类型",
				field : "phonetype",
				width : 200,
                formatter: function (value,row,index) {
                    if (value == 1){
                        return 'android';
                    }
                    if (value == 2){
                        return 'ios';
                    }
                    if(value==null || value == ''){
                        return '';
                    }
                }
			},{
                title : "app版本号	",
                field : "appversion",
                width : 200,
            },{
				title : "类型	",
				field : "type",
				width : 200,
                formatter: function (value,row,index) {
                    if (value == 0){
                        return '提示更新一次';
                    }
                    if (value == 1){
                        return '每次提示更新';
                    }
                    if (value == 2){
                        return '强制更新';
                    }
                    if(value==null || value == ''){
                        return '';
                    }
                }
			},{
                title : "更新地址",
                field : "address",
                width : 200,
            },{
                title : "app大小,单位K",
                field : "size",
                width : 200
            },{
                title : "更新内容",
                field : "content",
                width : 200
            },{
                title : "状态",
                field : "state",
                width : 200,
				formatter: function (value,row,index) {
                    if (value == 0){
                        return '无效';
					}
                    if (value == 1){
                        return '有效';
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
		top.showTopDialog("新增页面", "${ctx}/versionManage/addVersionManagePage.do", "1000", "620","yes");
	}

	function updateFun() {
		var rows = dataGrid.datagrid("getSelections");
		if (rows!="") {
			if(rows.length>1){
				top.$.messager.alert("信息提示框", "只能选中某一行！");
				dataGrid.datagrid("clearSelections");
			}else{
				top.showTopDialog("编辑页面", "${ctx}/versionManage/updateVersionManagePage.do?id=" + rows[0].id, "1000", "620","yes");
			}
		} else {
			top.$.messager.alert("信息提示框", "必须选中某一行！");
		}
	}

	function deleteFun() {
		var rows = dataGrid.datagrid("getSelections");
		if (rows!="") {

			top.$.messager.confirm("确认对话框", "您是否要删除当前数据？", function(b) {
				if(b){
					$.ajax({
						type: "POST",
						url: "${ctx}/versionManage/deleteVersion.do",
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
            orderCoinType: $('#orderCoinType').val(),
            unitCoinType: $('#unitCoinType').val()
        });
    }
</script>
</head>
<body class="easyui-layout" data-options="fit:true,border:false">
	<div id="toolbar" style="display: none;">
			<a href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-add'" onclick="addFun()">新增</a>
			<a href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-edit'" onclick="updateFun()">编辑</a>
			<a href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-edit'" onclick="deleteFun()">删除</a>

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