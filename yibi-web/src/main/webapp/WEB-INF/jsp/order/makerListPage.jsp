<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/static/commons/global.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="renderer" content="webkit">
<title>商家管理</title>
	<%@ include file="/static/commons/commoncss.jsp" %>
	<%@ include file="/static/commons/commonjs.jsp" %>

<script language="javascript">
	var dataGrid;
	$(function() {
		
		top.refreshdata = refreshdata;
		dataGrid = $("#dataGrid").datagrid({
			url : "${ctx}/order/getMakerList.do",
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
                title : "单价",
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
                title : "冻结数量",
                field : "frozen",
                width : 200
            },{
                title : "是否开始接单",
                field : "orderFlag",
                width : 200,
                formatter:function (value,row,index) {
                    if (value == 0){
                        return '不接单';
                    }
                    if (value == 1){
                        return '接单';
                    }
                }
            },{
                title : "状态",
                field : "state",
                width : 200,
				formatter:function (value,row,index) {
					if (value == 0){
					    return '未成交';
					}
					if (value == 1){
					    return '已成交';
					}
					if (value == 2){
					    return '已撤销';
					}
                }
            },{
                title : "类型",
                field : "type",
                width : 200,
                formatter:function (value,row,index) {
                    if (value == 0){
                        return '买入';
                    }
                    if (value == 1){
                        return '卖出';
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
                title : "订单号",
                field : "orderNum",
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

    function doSearch(){
        $('#dataGrid').datagrid('load',{
            phone: $('#phone').val(),
            type: $('#type').val(),
            orderFlag: $('#orderFlag').val(),
            state: $('#state').val(),
            coinType: $('#coinType').val(),
            orderNum: $('#orderNum').val()
        });
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
	</div>
	<div data-options="region:'center',border:true">
		<div class="easyui-layout" data-options="fit:true">

			<div data-options="region:'north',border:false" style="height: 30px; overflow: hidden;background-color: #fff">
				<form id="searchForm" action="${ctx}/order/getMakerList.do">
					<input type="hidden" id="deptid" name="deptid" value="" />
					<table>
						<tr>
							<th>账号</th>
							<td>
								<input type="text" id="phone" name="phone" value="" class="dfinput" style="width:160px;float: none;" />
							</td>
							<th>订单号</th>
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
							<th>交易方式</th>
							<td>
								<select class="easyui-combobox" id="type" name="type">
									<option value="">--交易方式--</option>
									<option value="0">买入</option>
									<option value="1">卖出</option>
								</select>
							</td>
							<th>是否开始接单</th>
							<td>
								<select class="easyui-combobox" id="orderFlag" name="orderFlag">
									<option value="">--是否开始接单--</option>
									<option value="0">不接单</option>
									<option value="1">接单</option>
								</select>
							</td>
							<th>状态</th>
							<td>
								<select class="easyui-combobox" id="state" name="state">
									<option value="">--状态--</option>
									<option value="0">未成交</option>
									<option value="1">已成交</option>
									<option value="2">已撤销</option>
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