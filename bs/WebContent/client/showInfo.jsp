<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBtdC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>显示书籍信息</title>
<script type="text/javascript">
	function dobuy(id) {
		alert('加入购物车成功!');
		window.location.href = "${pageContext.request.contextPath }/client/BuyServlet?bookid="
				+ id;
	}
</script>
</head>
<body>
	<table style="margin: 0 40%;" border="1">
		<tr>
			<td>书名：</td>
			<td>${book.name }</td>
		</tr>
		<tr>
			<td>封面：</td>
			<td><img src="${book.image}" height=150 width=100></td>
		</tr>
		<tr>
			<td>出版社：</td>
			<td>${detail.publisher }</td>
		</tr>
		<tr>
			<td>出版时间：</td>
			<td>${detail.publish_time }</td>
		</tr>
		<tr>
			<td>简介:</td>
			<td>${detail.desc }</td>
		</tr>
		<tr>
			<td>操作:</td>
			<td>
				<a href="javascript:dobuy('${book.id}')">加入购物车</a>
				<a href="${pageContext.request.contextPath }/client/CommentServlet?method=getAll&bookid=${book.id}">查看评论</a>
			</td>
		</tr>
	</table>

</body>
</html>