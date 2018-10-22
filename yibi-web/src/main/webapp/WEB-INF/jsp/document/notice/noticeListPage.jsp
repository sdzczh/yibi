<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/static/commons/global.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="renderer" content="webkit">
<title>图片管理</title>
	<%@ include file="/static/commons/commoncss.jsp" %>
	<%@ include file="/static/commons/commonjs.jsp" %>

	<script src="${cxt}/js/jquery.lightbox-0.5.js"></script>
<script language="javascript">

	var dataGrid;
	$(function() {
		
		top.refreshdata = refreshdata;
		dataGrid = $("#dataGrid").datagrid({
			url : "${ctx}/document/getNoticeList.do",
			striped : true,
			singleSelect:true,
			rownumbers : true,
            pagination:true,
			fitColumns : true,
			idField : 'id',
			columns : [ [ {
				width : 200,
				title : "标题",
				field : "title",
				width : 200
			},{
                title : "摘要",
                field : "roundup",
                width : 200
            },{
                title : "类型",
                field : "type",
                width : 200,
				formatter:function (value,row,index) {
					if (value == 0){
						return '公告';
					}
					if (value == 1){
                        return '关于';
					}
					if (value == 2){
                        return '2充值提现帮助文档';
					}
					if (value == 3){
                        return '费率';
					}
					if (value == 4){
                        return '算法';
                    }
					if (value == 5){
                        return '注册协议';
                    }
					if (value == 6){
                        return '汇率';
                    }
					if (value == 7){
                        return '公司介绍';
                    }
					if (value == 8){
                        return '8C2C帮助';
                    }
                    if(value==null || value == ''){
                        return '';
                    }

                }
            },{
				title : "图片",
				field : "pic",
				width : 200,
                formatter: function (value, row, index) {
                    if(value==null || value == ''){
                        return '';
                    }
                    return '<img src='+value+' height="100" width="100">';
                }
			},{
                title : "使用状态",
                field : "state",
                width : 200,
                formatter: function (value, row, index) {
                    if (value == 0){
                        return '无效';
                    }
                    if (value == 1){
                        return '有效';
                    }
                    if(value==null || value == ''){
                        return '';
                    }
                }
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
		top.showTopDialog("新增页面", "${ctx}/document/saveOrUpdateNoticePage.do", "600", "820","no");
	}

    function updateFun() {
        var rows = dataGrid.datagrid("getSelections");
        if (rows!="") {
            if(rows.length>1){
                top.$.messager.alert("信息提示框", "只能选中某一行！");
                dataGrid.datagrid("clearSelections");
            }else{
                top.showTopDialog("编辑页面", "${ctx}/document/saveOrUpdateNoticePage.do?id=" + rows[0].id, "600", "820","no");
            }
        } else {
            top.$.messager.alert("信息提示框", "必须选中某一行！");
        }
    }

    function deleteFun() {
        var rows = dataGrid.datagrid("getSelections");
        if (rows != "") {
            top.$.messager.confirm("确认对话框", "您是否要修改当前数据？", function (b) {
                if (b) {
                    $.ajax({
                        type: "POST",
                        url: "${ctx}/document/changeState.do",
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


    function doSearch(){
        $('#dataGrid').datagrid('load',{
            type: $('#type').val()
        });
    }

</script>
</head>
<body class="easyui-layout" data-options="fit:true,border:false">
	<div id="toolbar" style="display: none;">
			<a href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-add'" onclick="addFun()">新增</a>

			<a href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-remove'" onclick="updateFun()">修改</a>
			<a href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-remove'" onclick="deleteFun()">删除</a>
	</div>
	<div data-options="region:'center',border:true">
		<div class="easyui-layout" data-options="fit:true">
			<div data-options="region:'center',border:true">
				<div data-options="region:'north',border:false" style="height: 30px; overflow: hidden;background-color: #fff">
					<form id="searchForm" action="${ctx}/user/getDigcalInfoList.do">
						<input type="hidden" id="deptid" name="deptid" value="" />
						<table>
							<tr>
								<th>类型</th>
								<td>
									<select class="easyui-combobox" id="type" name="type">
										<option value="">--文档类型--</option>
										<option value="0">公告</option>
										<option value="1">关于</option>
										<option value="2">充值提现帮助文档</option>
										<option value="3">费率</option>
										<option value="4">算法</option>
										<option value="5">注册协议</option>
										<option value="6">汇率</option>
										<option value="7">公司介绍</option>
										<option value="8">C2C帮助</option>
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

				<table id="dataGrid" data-options="fit:true,border:false"></table>
			</div>
		</div>
	</div>
</body>
</html>