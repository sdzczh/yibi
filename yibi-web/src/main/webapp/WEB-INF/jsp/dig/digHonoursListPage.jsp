<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/static/commons/global.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="renderer" content="webkit">
<title>挖矿分区管理</title>
	<%@ include file="/static/commons/commoncss.jsp" %>
	<%@ include file="/static/commons/commonjs.jsp" %>

<script language="javascript">
	var dataGrid;
	$(function() {
		
		top.refreshdata = refreshdata;
		dataGrid = $("#dataGrid").datagrid({
			url : "${ctx}/dig/digHonoursList.do",
			striped : true,
			singleSelect:true,
			rownumbers : true,
            pagination:true,
			fitColumns : true,
			idField : 'id',
			columns : [ [ {
				width : 200,
				title : "矿场名字",
				field : "minename",
				width : 200
			},{
				title : "矿场背景图",
				field : "minepicurl",
				width : 200
			},{
                title : "角色名字",
                field : "rolename",
                width : 200
            },{
                title : "角色等级",
                field : "rolegrade",
                width : 200
            },{
                title : "魂力最小值",
                field : "soulminforce",
                width : 200
            },{
                title : "魂力最大值",
                field : "soulmaxforce",
                width : 200
            },{
                title : "角色图标地址",
                field : "rolepicurl",
                width : 200
			},{
                title : "可挖的币种类型",
                field : "cointype",
                width : 200,
				formatter:function (value,row,index) {
                    var htmlObj = $.ajax({
                        url : "${ctx}/dig/queryCoinNameByCoinTypes.do",
                        type : "post",
                        async : false,
                        data : {coinTypes:value}
                    });
                    var text = htmlObj.responseText;
                    if(text==null || text == ''){
                        return '无可挖币种';
                    }
                    return text;

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
		top.showTopDialog("新增页面", "${ctx}/dig/saveOrUpdateDigHonoursPage.do", "900", "620","no");
	}

	function updateFun() {
		var rows = dataGrid.datagrid("getSelections");
		if (rows!="") {
			if(rows.length>1){
				top.$.messager.alert("信息提示框", "只能选中某一行！");
				dataGrid.datagrid("clearSelections");
			}else{
				top.showTopDialog("编辑页面", "${ctx}/dig/saveOrUpdateDigHonoursPage.do?id=" + rows[0].id, "900", "620","no");
			}
		} else {
			top.$.messager.alert("信息提示框", "必须选中某一行！");
		}
	}

	function deleteFun() {
		var rows = dataGrid.datagrid("getSelections");
		if (rows!="") {
			if(rows[0].id==1){
				top.$.messager.alert("信息提示框", "不可删除开发管理员");
				return;
			}
			top.$.messager.confirm("确认对话框", "您是否要删除当前数据？", function(b) {
				if(b){
					$.ajax({
						type: "POST",
						url: "${ctx}/system/deleteSystemParam.do",
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