<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/static/commons/global.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="renderer" content="webkit">
<title>提现管理</title>
	<%@ include file="/static/commons/commoncss.jsp" %>
	<%@ include file="/static/commons/commonjs.jsp" %>

<script language="javascript">
	var dataGrid;
	$(function() {
		
		top.refreshdata = refreshdata;
		dataGrid = $("#dataGrid").datagrid({
			url : "${ctx}/finance/getWithdrawList.do",
			striped : true,
			singleSelect:true,
			rownumbers : true,
            pagination:true,
			fitColumns : true,
			idField : 'id',
			columns : [ [ {
				width : 200,
				title : "账号",
				field : "phone",
				width : 200,
                formatter: function (value, row, index) {
                    if(value==null || value == ''){
                        return '无此用户';
                    }
                    return value;
                }
			},{
				title : "用户姓名",
				field : "userName",
				width : 200
			},{
                title : "类型",
                field : "type",
                width : 200,
                formatter: function (value, row, index) {

                    if(value == 0){
                        return '普通提现';
                    }
                    if(value == 1){
                        return '提现到现货账户';
                    }
                    return value;
                }
            },{
                title : "提现账户类型",
                field : "accountType",
                width : 200,
                formatter: function (value, row, index) {

                    if(value == 0){
                        return 'C2C';
                    }
                    if(value == 1){
                        return '现货';
                    }
                    if(value == 2){
                        return '挖矿';
                    }
                    if(value == 3){
                        return '杠杆';
                    }
                    if(value == 4){
                        return '余币包';
                    }
                    return value;
                }
            },{
                title : "付款钱包地址",
                field : "payAddress",
                width : 200
            },{
                title : "提现数量",
                field : "amount",
                width : 200
            },{
                title : "手续费",
                field : "fee",
                width : 200
            },{
                title : "剩余数量",
                field : "remain",
                width : 200
            },{
                title : "订单状态",
                field : "state",
                width : 200,
                formatter: function (value, row, index) {
                    if(value == 0){
                        return '未处理';
                    }
                    if(value == 1){
                        return '已完成';
                    }
                    if(value == 2){
                        return '已撤销';
                    }
                    if(value==null || value == ''){
                        return '订单状态异常';
                    }
                }
            },{
                title : "币种类型",
                field : "coinName",
                width : 200,
                formatter: function (value, row, index) {
                    if(value==null || value == ''){
                        return '无此币种';
                    }
                    return value;
                }
            },{
                title : "提现订单号",
                field : "orderNum",
                width : 200
            },{
                title : "操作人姓名",
                field : "manageName",
                width : 200
            },{
				title : "最后更新时间",
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
    function updateWithdrawState(type) {
        var rows = dataGrid.datagrid("getSelections");
        if (rows!="") {
            top.$.messager.confirm("确认对话框", "您是否要操作当前提现订单？", function(b) {
                if(b){
                    $.ajax({
                        type: "POST",
                        url: "${ctx}/finance/updateWithdrawState.do",
                        data: {
                            "id" : rows[0].id,
							"type" : type
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
            phone: $('#phone').val(),
            userName: $('#username').val(),
            orderNum: $('#orderNum').val(),
            coinType: $('#coinType').val(),
            state: $('#state').val(),
            accountType: $('#accountType').val()
        });
    }

</script>

</head>
<body class="easyui-layout" data-options="fit:true,border:false">
	<div id="toolbar" style="display: none;">
		<a href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-remove'" onclick="updateWithdrawState(1)">确认提现</a>
		<a href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-remove'" onclick="updateWithdrawState(2)">撤销提现</a>
	</div>
	<div data-options="region:'center',border:true">
		<div class="easyui-layout" data-options="fit:true">

			<div data-options="region:'north',border:false" style="height: 30px; overflow: hidden;background-color: #fff">
				<form id="searchForm" action="${ctx}/user/getDigcalInfoList.do">
					<input type="hidden" id="deptid" name="deptid" value="" />
					<table>
						<tr>
							<th>账号</th>
							<td>
								<input type="text" id="phone" name="phone" value="" class="dfinput" style="width:160px;float: none;" />
							</td>
							<th>姓名</th>
							<td>
								<input type="text" id="username" name="username" value="" class="dfinput" style="width:160px;float: none;" />
							</td>
							<th>订单编号</th>
							<td>
								<input type="text" id="orderNum" name="orderNum" value="" class="dfinput" style="width:160px;float: none;" />
							</td>
							<th>币种类型</th>
							<td>
								<select class="easyui-combobox" id="coinType" name="coinType">
									<option value="">--币种--</option>
									<c:forEach var="coin" items="${coins }">
										<option value="${coin.cointype }">${coin.coinname }</option>
									</c:forEach>
								</select>
							</td>
							<th>订单状态</th>
							<td>
								<select class="easyui-combobox" id="state" name="state">
									<option value="">--订单状态--</option>
									<option value="0">未处理</option>
									<option value="1">已完成</option>
									<option value="2">已撤销</option>
								</select>
							</td>
							<th>账户类型</th>
							<td>
								<select class="easyui-combobox" id="accountType" name="accountType">
									<option value="">--账户类型--</option>
									<option value="0">C2C</option>
									<option value="1">现货</option>
									<option value="2">挖矿</option>
									<option value="3">杠杆</option>
									<option value="4">余币包</option>
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