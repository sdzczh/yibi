<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/static/commons/global.jsp" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="renderer" content="webkit">
	<title>机器人任务管理</title>
	<%@ include file="/static/commons/commoncss.jsp" %>
	<%@ include file="/static/commons/commonjs.jsp" %>

	<script language="javascript">
        var dataGrid;
        $(function() {

			var id = '<%=request.getAttribute("robotid")%>';
            top.refreshdata = refreshdata;
            dataGrid = $("#dataGrid").datagrid({
                url : "${ctx}/robot/robotTaskList.do?id="+id,
                striped : true,
                singleSelect:true,
                rownumbers : true,
                pagination:true,
                fitColumns : true,
                idField : 'id',
                columns : [ [ {
                    width : 200,
                    title : "币种名称",
                    field : "coinName",
                    width : 200
                },{
                    title : "开关状态",
                    field : "onoff",
                    width : 200,
                    formatter: function (value, row, index) {
                        if(value == 0){
                            return '关闭';
                        }
                        if(value == 1){
                            return '开启';
                        }
                        if(value==null || value == ''){
                            return '';
                        }
                        return str;
                    }
                },{
                    title : "操作类型",
                    field : "type",
                    width : 200,
                    formatter: function (value, row, index) {
                        if(value == 0){
                            return '买入挂单';
                        }
                        if(value == 1){
                            return '卖出挂单';
                        }
                        if(value == 2){
                            return '买入成交';
                        }
                        if(value == 3){
                            return '卖出成交';
                        }
                        if(value==null || value == ''){
                            return '';
                        }
                        return str;
                    }
                },{
                    title : "操作人",
                    field : "userName",
                    width : 200
                },{
                    title : "执行用户",
                    field : "phone",
                    width : 200
                },{
                    title : "时间间隔（秒）",
                    field : "timeInterval",
                    width : 200
                },{
                    title : "数量最大值",
                    field : "numberMax",
                    width : 200
                },{
                    title : "数量最小值",
                    field : "numberMin",
                    width : 200
                },{
                    title : "价格浮动区间上限",
                    field : "priceRadioMax",
                    width : 200

                },{
                    title : "价格浮动区间下限",
                    field : "priceRadioMin",
                    width : 200
                },{
                    title : "交易次数最大值",
                    field : "countMax",
                    width : 200
                },{
                    title : "交易次数最小值",
                    field : "countMin",
                    width : 200
                },{
                    title : "开始时间",
                    field : "startTime",
                    width : 200,
                    formatter: function (value, row, index) {
                        if(value==null || value == ''){
                            return '';
                        }
                        var str = timestampToTime(value,true);
                        return str;
                    }
                },{
                    title : "结束时间",
                    field : "endTime",
                    width : 200,
                    formatter: function (value, row, index) {
                        if(value==null || value == ''){
                            return '';
                        }
                        var str = timestampToTime(value,true);
                        return str;
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
        function operRobot(type) {
            var rows = dataGrid.datagrid("getSelections");
            if (rows!="") {
                top.$.messager.confirm("确认对话框", "您是否要修改当前数据？", function(b) {
                    if(b){
                        $.ajax({
                            type: "POST",
                            url: "${ctx}/robot/operTask.do",
                            data: {
                                "id" : rows[0].id,
                                "type":type
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

        function updateFun() {
            var rows = dataGrid.datagrid("getSelections");
            if (rows != "") {
                if (rows.length > 1) {
                    top.$.messager.alert("信息提示框", "只能选中某一行！");
                    dataGrid.datagrid("clearSelections");
                } else {
                    top.showTopDialog("编辑页面", "${ctx}/robot/updateTaskPage.do?id=" + rows[0].id+"&coinType="+rows[0].coinType, "900", "620", "no");
                }
            } else {
                top.$.messager.alert("信息提示框", "必须选中某一行！");
            }
        }

	</script>
</head>
<body class="easyui-layout" data-options="fit:true,border:false">
<div id="toolbar" style="display: none;">
	<a href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-add'" onclick="updateFun()">修改</a>

	<a href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-add'" onclick="operRobot(1)">开启</a>

	<a href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-edit'" onclick="operRobot(0)">关闭</a>

</div>
<div data-options="region:'center',border:true">
	<div class="easyui-layout" data-options="fit:true">
		<div data-options="region:'center',border:true">
			<table id="dataGrid" data-options="fit:true,border:false"></table>
		</div>
	</div>
</div>
</body>
</html>