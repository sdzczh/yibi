<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
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
			url : "${ctx}/system/paramList.do",
			striped : true,
			singleSelect:true,
			rownumbers : true,
            pagination:true,
			fitColumns : true,
			idField : 'id',
			columns : [ [ {
				width : 200,
				title : "变量名",
				field : "keyname",
				width : 200
			},{
				title : "变量值",
				field : "keyval",
				width : 200
			},{
                title : "备注",
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
		top.showTopDialog("新增页面", "${ctx}/system/saveOrUpdateSystemParamPage.do", "600", "220","no");
	}

	function updateFun() {
		var rows = dataGrid.datagrid("getSelections");
		if (rows!="") {
			if(rows.length>1){
				top.$.messager.alert("信息提示框", "只能选中某一行！");
				dataGrid.datagrid("clearSelections");
			}else{
				top.showTopDialog("编辑页面", "${ctx}/system/saveOrUpdateSystemParamPage.do?id=" + rows[0].id, "600", "220","no");
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


			<a href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-remove'" onclick="deleteFun()">删除</a>
	</div>
	<div data-options="region:'center',border:true">
		<div class="easyui-layout" data-options="fit:true">
			<div data-options="region:'north',border:false" style="height: 30px; overflow: hidden;background-color: #fff">
				<form id="searchForm"  action="">
					<input type="hidden" id="deptid" name="deptid" value="" />
					<table>
						<tr>
							<th>备注</th>
							<td>
								<input type="text" id="remark" name="remark" value="" class="dfinput" style="width:160px;float: none;" />
							</td>
							<td>
								&nbsp;
								<a href="javascript:void(0);" onclick="doSearch()" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a>
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