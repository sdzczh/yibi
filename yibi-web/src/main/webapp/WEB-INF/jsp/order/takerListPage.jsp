<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/static/commons/global.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="renderer" content="webkit">
<title>普通用户交易管理</title>
	<%@ include file="/static/commons/commoncss.jsp" %>
	<%@ include file="/static/commons/commonjs.jsp" %>

<script language="javascript">
	var dataGrid;
	$(function() {
		
		top.refreshdata = refreshdata;
		dataGrid = $("#dataGrid").datagrid({
			url : "${ctx}/order/getTakerList.do",
			striped : true,
			singleSelect:true,
			rownumbers : true,
            pagination:true,
			fitColumns : true,
			idField : 'id',
			columns : [ [ {
                width : 200,
                title : "普通用户账号",
                field : "takerPhone",
                width : 200,
                formatter: function (value, row, index) {
                    if(value==null || value == ''){
                        return '无此用户';
                    }
                    return value;
                }
            },{
                width : 200,
                title : "商家账号",
                field : "makerPhone",
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
                title : "成交额",
                field : "total",
                width : 200
            },{
                title : "状态",
                field : "state",
                width : 200,
				formatter:function (value,row,index) {
					if (value == 0){
					    return '代付款';
					}
					if (value == 1){
					    return '待确认';
					}
					if (value == 2){
					    return '冻结';
					}
					if (value == 3){
					    return '已完成';
					}
					if (value == 4){
					    return '已取消';
					}
					if (value == 5){
					    return '超时取消';
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
                title : "参考号",
                field : "flagNum",
                width : 200
            },{
                title : "备注",
                field : "remark",
                width : 200,
				formatter :function (value, row, index) {
                    if(value==null || value == ''){
                        return '';
                    }
                    value = "'"+value+"'"
                    return '<p onclick="showRemark('+value+')">'+value+'</p>';

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
            type: $('#type').val(),
            flagNum: $('#flagNum').val(),
            state: $('#state').val(),
            coinType: $('#coinType').val(),
            orderNum: $('#orderNum').val()
        });
    }

    function cancelTaker() {
        var rows = dataGrid.datagrid("getSelections");
        if (rows != "") {
            top.$.messager.confirm("确认对话框", "您是否要撤销当前订单？", function (b) {
                if (b) {
                    $.ajax({
                        type: "POST",
                        url: "${ctx}/order/cancelTaker.do",
                        data: {
                            "id": rows[0].id,
                            "state":rows[0].state
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
    function confirmTaker() {
        var rows = dataGrid.datagrid("getSelections");
        if (rows != "") {
            top.$.messager.confirm("确认对话框", "您是否要确认当前订单？", function (b) {
                if (b) {
                    $.ajax({
                        type: "POST",
                        url: "${ctx}/order/confirmTaker.do",
                        data: {
                            "id": rows[0].id,
							"state":rows[0].state
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

    function showRemark(value) {
        $.messager.show({
            title:'查看具体的备注信息',
            msg:''+value,
            showType:'show',
            showSpeed :100,
            timeout : 3000,
            style:{
                right:'',
                top:document.body.scrollTop+document.documentElement.scrollTop,
                bottom:''
            }
        });

    }

</script>

</head>
<body class="easyui-layout" data-options="fit:true,border:false">
	<div id="toolbar" style="display: none;">
		<a href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-remove'"
		   onclick="cancelTaker()">撤销</a>
		<a href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-remove'"
		   onclick="confirmTaker()">确认</a>
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
							<th>参考号</th>
							<td>
								<input type="text" id="flagNum" name="flagNum" value="" class="dfinput" style="width:160px;float: none;" />
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
							<th>状态</th>
							<td>
								<select class="easyui-combobox" id="state" name="state">
									<option value="">--状态--</option>
									<option value="0">待付款</option>
									<option value="1">待确认</option>
									<option value="2">冻结</option>
									<option value="3">已完成</option>
									<option value="4">已取消</option>
									<option value="5">超时取消</option>
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