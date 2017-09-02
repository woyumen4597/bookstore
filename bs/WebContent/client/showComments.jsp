<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBliC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>商品评论</title>
</head>
<body>
	<div style="margin: 0 40%;">
		<p>${ book.name }</p>
		<img src="${book.image}">
		<br/>
		<a href="${pageContext.request.contextPath }/client/CommentServlet?method=comment&bookid=${book.id}">我也要评论</a>
	</div>
	<br/><br/>
	<c:forEach items="${comments}" var="comment">
		<table border="1" style="margin: 0 40%;">
			<tr>
				<td>评论用户</td>
				<td>${comment.user_name }</td>
			</tr>
			<tr>
				<td>评论</td>
				<td>${comment.comment}</td>
			</tr>
			<tr>
				<td>评论时间</td>
				<td>${comment.create_time}</td>
			</tr>
		</table>
	</c:forEach>
</body>
</html>