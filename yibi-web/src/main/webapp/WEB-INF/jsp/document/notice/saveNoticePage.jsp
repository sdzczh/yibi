<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/static/commons/global.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="renderer" content="webkit">
	<title>图片管理</title>
	<%@ include file="/static/commons/commoncss.jsp" %>
	<%@ include file="/static/commons/commonjs.jsp" %>
	<link rel="stylesheet" type="text/css" href="${ctx}/static/css/bootstrap.min.css" />
	<script type="text/javascript" src="${ctx}/static/js/wangEditor.min.js"></script>
	<script type="text/javascript" src="${ctx}/static/js/layer.js"></script>
	<script type="text/javascript" src="${ctx}/static/js/jquery-2.2.4.min.js"></script>
</head>
<body>
<article class="page-container">
	<form  id="noticeForm" method="post" enctype="multipart/form-data" action="${ctx}/document/saveNotice.do">
		<div class="row cl" style="margin: 20px;">
			<span class="c-red">*</span>标题：

			<input type="text" class="form-control"   id="title" name="title">
		</div>
		<div class="row cl" style="margin: 20px;">
			<span class="c-red">*</span>图片：
			<input type="file" class="form-control"   id="file" name="file">
		</div>
		<div class="row cl" style="margin: 20px;">
			<span class="c-red">*</span>摘要：
			<input type="text" class="form-control"   id="roundup" name="roundup">
		</div>
		<div class="row cl" style="margin: 20px;">
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
		</div>
		<div class="row cl" style="margin: 20px;">
			<select class="easyui-combobox" id="state" name="state">
				<option value="">--文档类型--</option>
				<option value="0">无效</option>
				<option value="1">有效</option>
			</select>
		</div>
		<div class="row cl" style="margin: 20px;">
			<span class="c-red">*</span>内容：
				<div id="edit">
				</div>
		</div>


		<center>	 <input class="btn btn-primary radius" type="button" value="&nbsp;&nbsp;提交&nbsp;&nbsp;" onclick="subm()"> </center>
		</div>
	</form>
</article>


<!--请在下方写此页面业务相关的脚本-->
<script type="text/javascript">
    var E = window.wangEditor;
    var editor = new E('#edit');

    editor.customConfig.uploadImgShowBase64 = true;   // 使用 base64 保存图片

    editor.customConfig.uploadFileName = 'myFileName';

    editor.customConfig.uploadImgHeaders = {
        'Accept' : 'multipart/form-data'
    };

    // 隐藏“网络图片”tab
    editor.customConfig.showLinkImg = false;
    editor.customConfig.menus = [
        'head',  // 标题
        'bold',  // 粗体
        'italic',  // 斜体
        'underline',  // 下划线
        'foreColor',  // 文字颜色
        'backColor',  // 背景颜色
        'list',  // 列表
        'justify',  // 对齐方式
        'quote',  // 引用
        'image',  // 插入图片
        'table',  // 表格
        'undo',  // 撤销
        'redo'  // 重复
    ];
    editor.create()
</script>

<script>
    function subm(){

        var title = document.getElementById('title').value;
        var roundup = document.getElementById('roundup').value;
        var type = document.getElementById('type').value;
        var state = document.getElementById('state').value;
        var content = editor.txt.html();

        if(title==""||content==""||roundup==""){
            layer.msg('请把内容填写完整！',{icon:2,time:1000});
            return false;
        }
        var formData = new FormData($( "#noticeForm" )[0]);
        formData.append("content",content);
        $.ajax({
            type : "post",
            url : "${ctx}/document/saveNotice.do",
            data : formData,
            processData : false,  //必须false才会避开jQuery对 formdata 的默认处理
            contentType : false,  //必须false才会自动加上正确的Content-Type
            success : function(result) {

                layer.msg(result,{icon:1,time:1000});
            },
            error : function(data) {
                layer.msg(data,{icon:2,time:1000});
            }
        });
    }
</script>
<!--/请在上方写此页面业务相关的脚本-->
</body>

</html>