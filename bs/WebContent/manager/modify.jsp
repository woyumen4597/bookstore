<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>修改库存</title>
</head>
<body>
	<form action="${pageContext.request.contextPath }/manager/BookServlet?method=modify" method="post">
		库存: <input type="text" name="stock" value="${book.stock}" />
		<input type="hidden" name="bookid" value="${book.id}" />
		<input type="submit" value="修改" />
		<input type="reset" value="重置" />
	</form>
</body>
</html>