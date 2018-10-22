<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/static/commons/global.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="renderer" content="webkit">
<title>机器人挂单统计管理</title>
	<%@ include file="/static/commons/commoncss.jsp" %>
	<%@ include file="/static/commons/commonjs.jsp" %>

<script language="javascript">
	var dataGrid;
	$(function() {
		
		top.refreshdata = refreshdata;
		dataGrid = $("#dataGrid").datagrid({
			url : "${ctx}/robotStatistics/robotStatisticsList.do",
			striped : true,
			singleSelect:true,
			rownumbers : true,
            pagination:true,
			fitColumns : true,
			idField : 'id',
			columns : [ [ {
				width : 200,
				title : "交易币种",
				field : "coinName",
				width : 200
			},{
                title : "机器人和机器人交易数量",
                field : "robotAndRobotDealNum",
                width : 200
            },{
                title : "机器人和机器人交易总额",
                field : "robotAndRobotDealAmt",
                width : 200
            },{
                title : "机器人和人交易数量",
                field : "robotAndPersonDealNum",
                width : 200
            },{
                title : "机器人和人交易总额",
                field : "robotAndPersonDealAmt",
                width : 200
            },{
                title : "人和人交易数量",
                field : "personAndPersonDealNum",
                width : 200
            },{
                title : "人和人交易总额",
                field : "personAndPersonDealAmt",
                width : 200
            },{
				title : "创建时间",
				field : "createTime",
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

    function doSearch(){
        $('#dataGrid').datagrid('load',{
            coinType: $('#coinType').val(),
        });
    }

</script>
</head>
<body class="easyui-layout" data-options="fit:true,border:false">
	<div id="toolbar" style="display: none;">
	</div>
	<div data-options="region:'center',border:true">
		<div class="easyui-layout" data-options="fit:true">

			<div data-options="region:'north',border:false" style="height: 30px; overflow: hidden;background-color: #fff">
				<form id="searchForm">
					<table>
						<tr>
							<th>币种类型</th>
							<td>
								<select class="easyui-combobox" id="coinType" name="coinType" style="width: 100px">
									<option value="">--币种类型--</option>
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