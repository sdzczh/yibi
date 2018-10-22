﻿<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/static/commons/global.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="renderer" content="webkit">
<title>系统参数管理</title>
	<%@ include file="/static/commons/commoncss.jsp" %>
	<%@ include file="/static/commons/commonjs.jsp" %>

<script language="javascript">
	var dataGrid;
	$(function() {
		
		top.refreshdata = refreshdata;
		dataGrid = $("#dataGrid").datagrid({
			url : "${ctx}/document/bannerList.do",
			striped : true,
			singleSelect:true,
			rownumbers : true,
            pagination:true,
			fitColumns : true,
			idField : 'id',
			columns : [ [ {
				width : 200,
				title : "banner位置",
				field : "bannertype",
				width : 200,
				formatter: function (value, row, index) {
				    if (value == 0){
				        return '首页';
					}
				    if (value == 1){
				        return '生态';
					}
				    if (value == 2){
				        return '提升魂力';
					}
                    if(value==null || value == ''){
                        return '';
                    }
                }
			},{
				title : "图片地址",
				field : "imgpath",
				width : 200,
				height:300,
                formatter: function (value, row, index) {
                    if(value==null || value == ''){
                        return '';
                    }
                    return '<img src='+value+' height="100" width="200">';
                }
			},{
                title : "跳转类型",
                field : "type",
                width : 200,
				formatter: function (value, row, index) {
                    if (value == 0){
                        return '网站';
                    }
                    if (value == 1){
                        return '余币宝';
                    }
                    if (value == 2){
                        return '交易挖矿';
                    }
                    if (value == 3){
                        return '法币';
                    }
                    if (value == 4){
                        return '史莱克挖矿';
                    }
                    if (value == 5){
                        return '转盘';
                    }
                    if(value==null || value == ''){
                        return '';
                    }
                    return value;
                }
			},{
                title : "跳转地址",
                field : "address",
                width : 200
            },{
                title : "使用状态",
                field : "state",
                width : 200,
				formatter: function (value, row, index) {
                    if (value == 0){
                        return '废弃';
                    }
                    if (value == 1){
                        return '正常';
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
		top.showTopDialog("新增页面", "${ctx}/document/saveBannerPage.do", "800", "420","no");
	}

	function updateFun() {
		var rows = dataGrid.datagrid("getSelections");
		if (rows!="") {
			if(rows.length>1){
				top.$.messager.alert("信息提示框", "只能选中某一行！");
				dataGrid.datagrid("clearSelections");
			}else{
				top.showTopDialog("编辑页面", "${ctx}/document/updateBannerPage.do?id=" + rows[0].id, "800", "420","no");
			}
		} else {
			top.$.messager.alert("信息提示框", "必须选中某一行！");
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