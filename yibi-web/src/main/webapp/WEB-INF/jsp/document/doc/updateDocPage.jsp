<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/static/commons/global.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="renderer" content="webkit">
	<title>修改文档</title>
	<%@ include file="/static/commons/commoncss.jsp" %>
	<%@ include file="/static/commons/commonjs.jsp" %>
	<link rel="stylesheet" type="text/css" href="${ctx}/static/css/bootstrap.min.css" />
	<script type="text/javascript" src="${ctx}/static/js/wangEditor.min.js"></script>
	<script type="text/javascript" src="${ctx}/static/js/layer.js"></script>
	<script type="text/javascript" src="${ctx}/static/js/jquery-2.2.4.min.js"></script>
</head>
<body>
<article class="page-container">
	<form  id="noticeForm" enctype="multipart/form-data">
		<div class="row cl" style="margin: 20px;">
			<span class="c-red">*</span>类型：
            <input type="hidden" id="id" name="id" value="${doc.id }" />
			<input type="text" class="form-control"   id="type" name="title" value="${doc.type }" readonly="readonly">
		</div>
		<div class="row cl" style="margin: 20px;">
			<span class="c-red">*</span>内容：
				<div id="edit">
					${doc.content}
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

        var id = document.getElementById('id').value;
        var type = document.getElementById('type').value;
        var content = editor.txt.html();

        if(content==""){
            layer.msg('请把内容填写完整！',{icon:2,time:1000});
            return false;
        }
        var formData = new FormData($( "#noticeForm" )[0]);
        formData.append("content",content);
        $.ajax({
            type : "post",
            url : "${ctx}/docManage/updateDoc.do",
            data : {'id':id,'type':type,'content':content},
            success : function(result) {
                layer.msg('更新成功!',{icon:1,time:1000});
            },
            error : function(data) {
                layer.msg('更新失败!',{icon:2,time:1000});
            }
        });
    }
</script>
<!--/请在上方写此页面业务相关的脚本-->
</body>

</html>