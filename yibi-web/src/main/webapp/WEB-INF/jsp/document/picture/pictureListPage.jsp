<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/static/commons/global.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="renderer" content="webkit">
<title>图片管理</title>
	<%@ include file="/static/commons/commoncss.jsp" %>
	<%@ include file="/static/commons/commonjs.jsp" %>

	<script src="${cxt}/js/jquery.lightbox-0.5.js"></script>
<script language="javascript">

	var dataGrid;
	$(function() {
		
		top.refreshdata = refreshdata;
		dataGrid = $("#dataGrid").datagrid({
			url : "${ctx}/document/getPictureList.do",
			striped : true,
			singleSelect:true,
			rownumbers : true,
            pagination:true,
			fitColumns : true,
			idField : 'id',
			columns : [ [ {
				width : 200,
				title : "图片名称",
				field : "name",
				width : 200
			},{
				title : "图片地址",
				field : "address",
				width : 200,
                formatter: function (value, row, index) {
                    if(value==null || value == ''){
                        return '';
                    }
                    return '<img src='+value+' height="100" width="100">';
                }
			},{
                title : "使用状态",
                field : "state",
                width : 200
            },{
                title : "图片描述",
                field : "remark",
                width : 200
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
		top.showTopDialog("新增页面", "${ctx}/document/savePicturePage.do", "600", "220","no");
	}

	function deleteFun() {
		var rows = dataGrid.datagrid("getSelections");
		if (rows!="") {
			if(rows[0].id==0){
				top.$.messager.alert("信息提示框", "图片来源未知，请勿删除");
				return;
			}
			top.$.messager.confirm("确认对话框", "您是否要删除当前数据？", function(b) {
				if(b){
					$.ajax({
						type: "POST",
						url: "${ctx}/document/deletePicture.do",
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

</script>
</head>
<body class="easyui-layout" data-options="fit:true,border:false">
	<div id="toolbar" style="display: none;">
			<a href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-add'" onclick="addFun()">新增</a>

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