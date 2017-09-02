<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>修改个人信息</title>
</head>
<body style="text-align:center;">
	<h1>修改个人信息</h1>
  	<form action="${pageContext.request.contextPath }/client/UpdateInfoServlet" method="post">
    	用户名：<input type="text" name="username" value="${user.username}"><br/>
    	密码：<input type="password" name="password" value="${user.password}"><br/>
    	电话：<input type="text" name="phone" value="${user.phone }"><br/>
    	手机：<input type="text" name="cellphone" value="${user.cellphone }"><br/>
    	邮箱：<input type="text" name="email" value="${user.email }"><br/>
    	住址：<input type="text" name="address" value="${user.address }"><p style="color:red;">多地址用逗号分隔</p><br/>
    	<input type="hidden" name="id" value="${user.id}" />
    	<input type="submit" value="修改">
    </form>
    <a href="${pageContext.request.contextPath}/client/head.jsp">返回</a>
  </body>
</html>