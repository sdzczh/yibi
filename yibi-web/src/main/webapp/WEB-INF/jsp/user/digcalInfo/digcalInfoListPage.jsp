<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/static/commons/global.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="renderer" content="webkit">
<title>魂力管理</title>
	<%@ include file="/static/commons/commoncss.jsp" %>
	<%@ include file="/static/commons/commonjs.jsp" %>

<script language="javascript">
	var dataGrid;
	$(function() {
		
		top.refreshdata = refreshdata;
		dataGrid = $("#dataGrid").datagrid({
			url : "${ctx}/user/getDigcalInfoList.do",
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
				width : 200
			},{
				title : "用户姓名",
				field : "userName",
				width : 200
			},{
                title : "算力",
                field : "digCalcul",
                width : 200
            },{
                title : "挖矿状态",
                field : "digFlag",
                width : 200,
                formatter: function (value, row, index) {
                    if(value == 0){
                        return '否';
                    }
                    if(value == 1){
                        return '是';
                    }
                    if(value==null || value == ''){
                        return '';
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
            username: $('#username').val(),
            minDigCalcul: $('#minDigCalcul').val(),
            maxDigCalcul: $('#maxDigCalcul').val()
        });
    }

</script>
</head>
<body class="easyui-layout" data-options="fit:true,border:false">
	<div id="toolbar" style="display: none;">
		<a href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-remove'" onclick="updateFun()">修改魂力</a>
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
							<th>魂力最小值</th>
							<td>
								<input type="text" id="minDigCalcul" name="minDigCalcul" value="" class="dfinput" style="width:160px;float: none;" />
							</td>
							<th>魂力最大值</th>
							<td>
								<input type="text" id="maxDigCalcul" name="maxDigCalcul" value="" class="dfinput" style="width:160px;float: none;" />
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