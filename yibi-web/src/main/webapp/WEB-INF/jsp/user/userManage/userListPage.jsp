<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/static/commons/global.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="renderer" content="webkit">
<title>会员管理</title>
	<%@ include file="/static/commons/commoncss.jsp" %>
	<%@ include file="/static/commons/commonjs.jsp" %>

<script language="javascript">
	var dataGrid;
	$(function() {
		
		top.refreshdata = refreshdata;
		dataGrid = $("#dataGrid").datagrid({
			url : "${ctx}/user/getUserList.do",
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
				field : "username",
				width : 200
			},{
                title : "昵称",
                field : "nickname",
                width : 200
            },{
                title : "用户状态",
                field : "state",
                width : 200,
                formatter: function (value, row, index) {
                    if(value==null || value == ''){
                        return '';
                    }
                    if(value == 1){
                        return '有效';
					}
                    if(value == 2){
                        return '冻结';
                    }
                    if(value == 3){
                        return '注销';
                    }
                }
            },{
                title : "身份证号",
                field : "idcard",
                width : 200
			},{
				title : "最后登录时间",
				field : "logintime",
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


	function doFun() {
		top.showTopDialog("新增页面", "${ctx}/document/savePicturePage.do", "600", "220","no");
	}


    function updateFun() {
        var rows = dataGrid.datagrid("getSelections");
        if (rows!="") {
            if(rows.length>1){
                top.$.messager.alert("信息提示框", "只能选中某一行！");
                dataGrid.datagrid("clearSelections");
            }else{
                top.showTopDialog("编辑页面", "${ctx}/system/saveOrUpdateSystemParamPage.do?id=" + rows[0].id, "600", "220","no");
            }
        } else {
            top.$.messager.alert("信息提示框", "必须选中某一行！");
        }
    }


	function FreezingUser() {
		var rows = dataGrid.datagrid("getSelections");
		if (rows!="") {
			if(rows[0].id==0){
				top.$.messager.alert("信息提示框", "选中一个用户");
				return;
			}
			top.$.messager.confirm("确认对话框", "您是否要冻结当前用户？", function(b) {
				if(b){
					$.ajax({
						type: "POST",
						url: "${ctx}/user/freezingUser.do",
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

    function ThawUser() {
        var rows = dataGrid.datagrid("getSelections");
        if (rows!="") {
            if(rows[0].id==0){
                top.$.messager.alert("信息提示框", "选中一个用户");
                return;
            }
            top.$.messager.confirm("确认对话框", "您是否要解冻当前用户？", function(b) {
                if(b){
                    $.ajax({
                        type: "POST",
                        url: "${ctx}/user/thawUser.do",
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

    function ResetLoginPwd() {
        var rows = dataGrid.datagrid("getSelections");
        if (rows!="") {
            if(rows[0].id==0){
                top.$.messager.alert("信息提示框", "选中一个用户");
                return;
            }
            top.$.messager.confirm("确认对话框", "您是否要重置当前用户的登录密码？", function(b) {
                if(b){
                    $.ajax({
                        type: "POST",
                        url: "${ctx}/user/ResetLoginPwd.do",
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
            phone: $('#phone').val(),
            username: $('#username').val()
        });
    }

</script>
</head>
<body class="easyui-layout" data-options="fit:true,border:false">
	<div id="toolbar" style="display: none;">
		<%--<a href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-add'" onclick="addFun()">钱包余额</a>--%>
		<%--<a href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-add'" onclick="addFun()">币种流水</a>--%>
		<%--<a href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-add'" onclick="addFun()">魂力值</a>--%>
		<%--<a href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-add'" onclick="addFun()">魂力流水</a>--%>
		<%--<a href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-add'" onclick="addFun()">转账记录</a>--%>
		<%--<a href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-add'" onclick="addFun()">资金划转记录</a>--%>
		<%--<a href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-add'" onclick="addFun()">充值记录</a>--%>
		<%--<a href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-add'" onclick="addFun()">提现记录</a>--%>
		<%--<a href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-add'" onclick="addFun()">委托管理</a>--%>
		<%--<a href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-add'" onclick="addFun()">现货成交记录</a>--%>
			<a href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-remove'" onclick="FreezingUser()">冻结用户</a>
			<a href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-remove'" onclick="ThawUser()">解冻用户</a>
			<a href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-add'" onclick="ResetLoginPwd()">重置登录密码</a>
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
							<th>姓名</th>
							<td>
								<input type="text" id="username" name="username" value="" class="dfinput" style="width:160px;float: none;" />
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