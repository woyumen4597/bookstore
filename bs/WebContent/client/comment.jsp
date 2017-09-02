<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>用户评论界面</title>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath }/js/jquery-easyui-1.4.1/themes/default/easyui.css" />
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath }/js/jquery-easyui-1.4.1/themes/icon.css" />
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath }/css/taotao.css" />
<script type="text/javascript"
	src="${pageContext.request.contextPath }/js/jquery-easyui-1.4.1/jquery.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath }/js/jquery-easyui-1.4.1/jquery.easyui.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath }/js/jquery-easyui-1.4.1/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath }/js/common.js"></script>
<link
	href="${pageContext.request.contextPath }/js/kindeditor-4.1.10/themes/default/default.css"
	type="text/css" rel="stylesheet">
<script type="text/javascript" charset="utf-8"
	src="${pageContext.request.contextPath }/js/kindeditor-4.1.10/kindeditor-all-min.js"></script>
<script type="text/javascript" charset="utf-8"
	src="${pageContext.request.contextPath }/js/kindeditor-4.1.10/lang/zh_CN.js"></script>

</head>
<body>
	<div style="margin: 0 40%;">
		<p>${ book.name }</p>
		<img src="${book.image}"> <br />
	</div>
	<form action="${pageContext.request.contextPath }/client/CommentServlet?method=addComment"
	method="post" id="addCommentForm" style="margin: 0 30%;">
		<input type="hidden" value="${user.username}" name="username" />
		<input type="hidden" value="${book.id}" name="bookid" />
		<textarea style="width: 400px; height: 300px; visibility: hidden;"
			name="comment"></textarea>
		<button onclick="submitForm()" style="width: 50px; height: 30px;">提交</button>
		<button onclick="clearForm()" style="width: 50px; height: 30px;">重置</button>
	</form>
	<script type="text/javascript">
		var addCommentEditor;
		//页面初始化完毕后执行此方法
		$(function() {
			//创建富文本编辑器
			addCommentEditor = TAOTAO.createEditor("#addCommentForm [name=comment]");
 	});
		//提交表单
		function submitForm() {
			//同步文本框中的商品描述
			addCommentEditor.sync();
			$('#addCommentForm').submit();

			function clearForm() {
				$('#addCommentForm').form('reset');
				addCommentEditor.html('');
			}
		}
	</script>
</body>
</html>