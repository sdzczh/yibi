<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/static/commons/global.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="renderer" content="webkit">
<title>挖矿基数管理</title>
	<%@ include file="/static/commons/commoncss.jsp" %>
	<%@ include file="/static/commons/commonjs.jsp" %>

<script language="javascript">
	var dataGrid;
	$(function() {
		
		top.refreshdata = refreshdata;
		dataGrid = $("#dataGrid").datagrid({
			url : "${ctx}/digBase/digBaseList.do",
			striped : true,
			singleSelect:true,
			rownumbers : true,
            pagination:true,
			fitColumns : true,
			idField : 'id',
			columns : [ [ {
				width : 200,
				title : "币种名字",
				field : "coinName",
				width : 200
			},{
                title : "第一级基数	",
                field : "rate0",
                width : 200,
            },{
                title : "第二级基数	",
                field : "rate1",
                width : 200,
            },{
                title : "第三级基数	",
                field : "rate2",
                width : 200,
            },{
                title : "第四级基数	",
                field : "rate3",
                width : 200,
            },{
                title : "第五级基数	",
                field : "rate4",
                width : 200,
            },{
                title : "第六级基数	",
                field : "rate5",
                width : 200,
            },{
                title : "第七级基数	",
                field : "rate6",
                width : 200,
            },{
                title : "第八级基数	",
                field : "rate7",
                width : 200,
            },{
                title : "第九级基数	",
                field : "rate8",
                width : 200,
                formatter: function (value, row, index) {
                    if(value==null || value == ''){
                        return '';
                    }
                    return value;
                }
            },{
                title : "第十级基数	",
                field : "rate9",
                width : 200,
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
		top.showTopDialog("新增页面", "${ctx}/digBase/addDigBasePage.do", "1000", "620","yes");
	}

	function updateFun() {
		var rows = dataGrid.datagrid("getSelections");
		if (rows!="") {
			if(rows.length>1){
				top.$.messager.alert("信息提示框", "只能选中某一行！");
				dataGrid.datagrid("clearSelections");
			}else{
				top.showTopDialog("编辑页面", "${ctx}/digBase/updateDigBasePage.do?id=" + rows[0].id, "1000", "620","yes");
			}
		} else {
			top.$.messager.alert("信息提示框", "必须选中某一行！");
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