<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/static/commons/global.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="renderer" content="webkit">
<title>充值管理</title>
	<%@ include file="/static/commons/commoncss.jsp" %>
	<%@ include file="/static/commons/commonjs.jsp" %>

<script language="javascript">
	var dataGrid;
	$(function() {
		
		top.refreshdata = refreshdata;
		dataGrid = $("#dataGrid").datagrid({
			url : "${ctx}/order/getSpotList.do",
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
                title : "交易币种",
                field : "orderCoinName",
                width : 200
            },{
                title : "计较币种",
                field : "unitCoinName",
                width : 200
            },{
                title : "类型",
                field : "type",
                width : 200,
				formatter: function (value,row,index) {
					if (value == 0){
					    return '买入';
					}
					if (value == 1){
					    return '卖出';
					}
                }
            },{
                title : "交易类型",
                field : "orderType",
                width : 200,
                formatter: function (value,row,index) {
                    if (value == 0){
                        return '市价交易';
                    }
                    if (value == 1){
                        return '限价交易';
                    }
                }
            },{
                title : "价格",
                field : "price",
                width : 200
            },{
                title : "数量",
                field : "amount",
                width : 200
            },{
                title : "剩余数量",
                field : "remain",
                width : 200
            },{
                title : "交易订单号",
                field : "orderNum",
                width : 200
            },{
                title : "杠杆挂单标识",
                field : "levFlag",
                width : 200,
                formatter: function (value,row,index) {
                    if (value == 0){
                        return '普通挂单';
                    }
                    if (value == 1){
                        return '杠杆挂单';
                    }
                }
            },{
                title : "订单状态",
                field : "state",
                width : 200,
                formatter: function (value, row, index) {
                    if(value == 0){
                        return '未成交';
                    }
                    if(value == 1){
                        return '已支成交';
                    }
                    if(value == 2){
                        return '交易撤销';
                    }
                    if(value == 3){
                        return '交易失败';
                    }
                    if(value==null || value == ''){
                        return '订单状态异常';
                    }
                }
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

    function doSearch(){
        $('#dataGrid').datagrid('load',{
            phone: $('#phone').val(),
            orderCoinType: $('#orderCoinType').val(),
            unitCoinType: $('#unitCoinType').val(),
            type: $('#type').val(),
            orderType: $('#orderType').val(),
            levFlag: $('#levFlag').val(),
            state: $('#state').val()
        });
    }

    function deleteFun() {
        var rows = dataGrid.datagrid("getSelections");
        if (rows != "") {
            top.$.messager.confirm("确认对话框", "您是否要撤销当前数据？", function (b) {
                if (b) {
                    $.ajax({
                        type: "POST",
                        url: "${ctx}/order/updateState.do",
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

</script>

	<script type="text/javascript">
        $(function(){
            $('#dataForm').form('load', '${ctx}/user/loadForm.do');
        });
	</script>
</head>
<body class="easyui-layout" data-options="fit:true,border:false">
	<div id="toolbar" style="display: none;">
		<a href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-remove'"
		   onclick="deleteFun()">撤销交易</a>
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
							<th>交易币种</th>
							<td>
								<select class="easyui-combobox" id="orderCoinType" name="orderCoinType">
									<option value="">--交易币种--</option>
									<c:forEach var="coin" items="${coins }">
										<option value="${coin.cointype }">${coin.coinname }</option>
									</c:forEach>
								</select>
							</td>
							<th>计价币种</th>
							<td>
								<select class="easyui-combobox" id="unitCoinType" name="unitCoinType">
									<option value="">--计价币种--</option>
									<c:forEach var="coin" items="${coins }">
										<option value="${coin.cointype }">${coin.coinname }</option>
									</c:forEach>
								</select>
							</td>
							<th>订单状态</th>
							<td>
								<select class="easyui-combobox" id="state" name="state">
									<option value="">--订单状态--</option>
									<option value="0">未成交</option>
									<option value="1">已成交</option>
									<option value="2">交易撤销</option>
									<option value="3">交易失败</option>
								</select>
							</td>
							<th>类型</th>
							<td>
								<select class="easyui-combobox" id="type" name="type">
									<option value="">--类型--</option>
									<option value="0">买入</option>
									<option value="1">卖出</option>
								</select>
							</td>
							<th>交易类型</th>
							<td>
								<select class="easyui-combobox" id="orderType" name="orderType">
									<option value="">--交易类型--</option>
									<option value="0">市阶交易</option>
									<option value="1">限价交易</option>
								</select>
							</td>
							<th>杠杆挂单标识</th>
							<td>
								<select class="easyui-combobox" id="levFlag" name="levFlag">
									<option value="">--杠杆挂单标识--</option>
									<option value="0">普通挂单</option>
									<option value="1">杠杆挂单</option>
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