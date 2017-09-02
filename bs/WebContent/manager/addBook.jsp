<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>添加图书</title>
</head>
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

<body>
	<form id="itemAddForm"
		action="${pageContext.request.contextPath }/manager/BookServlet?method=add"
		method="post" enctype="multipart/form-data">
		<table frame="border" width="50%">
			<tr>
				<td>图书名称</td>
				<td><input type="text" name="name"></td>
			</tr>
			<tr>
				<td>ISBN</td>
				<td><input type="text" name="id"></td>
			</tr>
			<tr>
				<td>作者</td>
				<td><input type="text" name="author"></td>
			</tr>
			<tr>
				<td>售价</td>
				<td><input type="text" name="price"></td>
			</tr>
			<tr>
				<td>图片</td>
				<td><a href="javascript:void(0)"
					class="easyui-linkbutton picFileUpload">上传图片</a> <input
					type="hidden" name="image" /></td>
			</tr>
			<tr>
				<td>图书描述</td>
				<td><textarea
						style="width: 400px; height: 300px; visibility: hidden;"
						name="desc"></textarea></td>
			</tr>
			<tr>
				<td>出版社</td>
				<td><input type="text" name="publisher" /></td>
			</tr>
			<tr>
				<td>出版时间</td>
				<td><input type="text" name="publish_time" /></td>
			</tr>
			<tr>
				<td>库存</td>
				<td><input type="text" name="stock" /></td>
			</tr>
			<tr>
				<td>所属分类</td>
				<td><select name="category_id">
						<c:forEach var="c" items="${categories }">
							<option value="${c.id }">${c.name }</option>
						</c:forEach>
				</select></td>
			</tr>
			<tr>
				<td><button onclick="submitForm()"
						style="width: 50px; height: 30px;">提交</button></td>
				<td><button onclick="clearForm()"
						style="width: 50px; height: 30px;">重置</button></td>
			</tr>
		</table>
	</form>
	<script type="text/javascript">
		var itemAddEditor;
		//页面初始化完毕后执行此方法
		$(function() {
			//创建富文本编辑器
			itemAddEditor = TAOTAO.createEditor("#itemAddForm [name=desc]");
			//初始化类目选择和图片上传器
			TAOTAO.init({
				fun : function(node) {
					//根据商品的分类id取商品 的规格模板，生成规格信息。第四天内容。
					TAOTAO.changeItemParam(node, "itemAddForm");
				}
			});
		});
		//提交表单
		function submitForm() {
			//有效性验证
			if (!$('#itemAddForm').form('validate')) {
				$.messager.alert('提示', '表单还未填写完成!');
				return;
			}
			//同步文本框中的商品描述
			itemAddEditor.sync();
			$('#itemAddForm').submit();

			function clearForm() {
				$('#itemAddForm').form('reset');
				itemAddEditor.html('');
			}
		}
	</script>
</body>
</html>
