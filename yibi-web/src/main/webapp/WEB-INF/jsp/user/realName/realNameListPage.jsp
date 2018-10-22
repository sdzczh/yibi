<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/static/commons/global.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="renderer" content="webkit">
<title>实名认证管理</title>
	<%@ include file="/static/commons/commoncss.jsp" %>
	<%@ include file="/static/commons/commonjs.jsp" %>

<script language="javascript">
	var dataGrid;
	$(function() {
		
		top.refreshdata = refreshdata;
		dataGrid = $("#dataGrid").datagrid({
			url : "${ctx}/realName/getRealNameList.do",
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
				field : "name",
				width : 200
			},{
                title : "身份证号",
                field : "identificationNumber",
                width : 200
            },{
                title : "认证状态",
                field : "state",
                width : 200,
                formatter: function (value, row, index) {

                    if(value == -1){
                        return '未认证';
					}
                    if(value == 0){
                        return '认证中';
					}
                    if(value == 1){
                        return '认证通过';
					}
                    if(value == 2){
                        return '认证不通过';
                    }
                    if(value == 3){
                        return '年龄不合法';
                    }
                    if(value == 4){
                        return '身份证号已存在';
                    }
                    if(value==null || value == ''){
                        return '';
                    }
                }
            },{
                title : "证件照正面图片",
                field : "idCardFrontPic",
                width : 200,
                formatter: function (value, row, index) {
                    if(value==null || value == ''){
                        return '';
                    }
                    return '<img src='+value+' height="100" width="100">';
                }
            },{
                title : "认证过程中拍摄的人像正面照图片",
                field : "idCardBackPic",
                width : 200,
                formatter: function (value, row, index) {
                    if(value==null || value == ''){
                        return '';
                    }
                    return '<img src='+value+' height="100" width="100">';
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
		top.showTopDialog("新增页面", "${ctx}/realName/addRealNamePage.do", "600", "220","no");
	}

    function doSearch(){
        $('#dataGrid').datagrid('load',{
            phone: $('#phone').val(),
            state: $('#state').val()
        });
    }

</script>
</head>
<body class="easyui-layout" data-options="fit:true,border:false">
	<div id="toolbar" style="display: none;">
		<a href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-remove'" onclick="addFun()">手动实名认证</a>
	</div>
	<div data-options="region:'center',border:true">
		<div class="easyui-layout" data-options="fit:true">

			<div data-options="region:'north',border:false" style="height: 30px; overflow: hidden;background-color: #fff">
				<form id="searchForm" action="${ctx}/system/paramListByCondition.do">
					<input type="hidden" id="deptid" name="deptid" value="" />
					<table>
						<tr>
							<th>账号</th>
							<td>
								<input type="text" id="phone" name="phone" value="" class="dfinput" style="width:160px;float: none;" />
							</td>

							<th>认证状态</th>
							<td>
								<select class="easyui-combobox" id="state" name="state">
									<option value="">--认证状态--</option>
									<option value="-1">未认证</option>
									<option value="0">认证中</option>
									<option value="1">认证通过</option>
									<option value="2">认证不通过</option>
									<option value="3">年龄不合法</option>
									<option value="4">身份证号已存在</option>
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