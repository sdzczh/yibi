<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/static/commons/global.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="renderer" content="webkit">
<title>交易参数管理</title>
	<%@ include file="/static/commons/commoncss.jsp" %>
	<%@ include file="/static/commons/commonjs.jsp" %>

<script language="javascript">
	var dataGrid;
	$(function() {
		
		top.refreshdata = refreshdata;
		dataGrid = $("#dataGrid").datagrid({
			url : "${ctx}/orderManage/orderManageList.do",
			striped : true,
			singleSelect:true,
			rownumbers : true,
            pagination:true,
			fitColumns : true,
			idField : 'id',
			columns : [ [ {
				width : 200,
				title : "交易币种",
				field : "orderCoinName",
				width : 200
			},{
                title : "计价币种	",
                field : "unitCoinName",
                width : 200,
            },{
				title : "平台费率	",
				field : "performRate",
				width : 200,
			},{
                title : "推荐人费率",
                field : "referRate",
                width : 200,
            },{
                title : "OKCoin是否提供接口",
                field : "okcoinFlag",
                width : 200,
                formatter: function (value,row,index) {
                    if (value == 0){
                        return 'OKCoin不提供接口';
                    }
                    if (value == 1){
                        return 'OKCoin提供接口';
                    }
                    if(value==null || value == ''){
                        return '';
                    }
                }
            },{
                title : "开关",
                field : "onoff",
                width : 200,
				formatter: function (value,row,index) {
                    if (value == 0){
                        return '关闭';
					}
                    if (value == 1){
                        return '开启';
					}
                    if(value==null || value == ''){
                        return '';
                    }
                }
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
		top.showTopDialog("新增页面", "${ctx}/orderManage/saveOrderManagePage.do", "1000", "620","yes");
	}

	function updateFun() {
		var rows = dataGrid.datagrid("getSelections");
		if (rows!="") {
			if(rows.length>1){
				top.$.messager.alert("信息提示框", "只能选中某一行！");
				dataGrid.datagrid("clearSelections");
			}else{
				top.showTopDialog("编辑页面", "${ctx}/orderManage/updateOrderManagePage.do?id=" + rows[0].id, "1000", "620","yes");
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
						url: "${ctx}/coinManage/deleteCoin.do",
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

	</div>
	<div data-options="region:'center',border:true">
		<div class="easyui-layout" data-options="fit:true">
			<div data-options="region:'north',border:false" style="height: 30px; overflow: hidden;background-color: #fff">
				<form id="searchForm">
					<input type="hidden" id="deptid" name="deptid" value="" />
					<table>
						<tr>
							<th>交易币种:</th>
							<td>
								<select class="easyui-combobox" id="orderCoinType" name="orderCoinType">
									<option value="">--交易币种--</option>
									<c:forEach var="coin" items="${coins }">
										<option value="${coin.cointype }">${coin.coinname }</option>
									</c:forEach>
								</select>
							</td>
							<th>计价币种:</th>
							<td>
								<select class="easyui-combobox" id="unitCoinType" name="unitCoinType">
									<option value="">--计价币种--</option>
									<c:forEach var="coin" items="${coins }">
										<option value="${coin.cointype }">${coin.coinname }</option>
									</c:forEach>
								</select>
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