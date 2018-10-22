<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/static/commons/global.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="renderer" content="webkit">
<title>余币宝收益管理</title>
	<%@ include file="/static/commons/commoncss.jsp" %>
	<%@ include file="/static/commons/commonjs.jsp" %>

<script language="javascript">
	var dataGrid;
	$(function() {
		
		top.refreshdata = refreshdata;
		dataGrid = $("#dataGrid").datagrid({
			url : "${ctx}/order/getSurplusCoinList.do",
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
                title : "本金",
                field : "principle",
                width : 200
            },{
                title : "收益率",
                field : "rate",
                width : 200
            },{
                title : "收益",
                field : "interest",
                width : 200
            },{
                title : "全部收益",
                field : "yubiTotalProfit",
                width : 200
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
    function updateFun() {
        var rows = dataGrid.datagrid("getSelections");
        if (rows!="") {
            if(rows.length>1){
                top.$.messager.alert("信息提示框", "只能选中某一行！");
                dataGrid.datagrid("clearSelections");
            }else{
                top.showTopDialog("编辑页面", "${ctx}/user/updateDigcalPage.do?id=" + rows[0].id, "600", "220","no");
            }
        } else {
            top.$.messager.alert("信息提示框", "必须选中某一行！");
        }
    }

    function doSearch(){
        $('#dataGrid').datagrid('load',{
            phone: $('#phone').val(),
            userName: $('#username').val(),
            coinType: $('#coinType').val()
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
				<form id="searchForm" action="${ctx}/order/getSurplusCoinList.do">
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
							<th>币种类型</th>
							<td>
								<select class="easyui-combobox" id="coinType" name="coinType">
									<option value="">--币种--</option>
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