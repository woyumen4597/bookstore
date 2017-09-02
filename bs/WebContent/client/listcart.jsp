<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>购物车显示列表</title>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery-easyui-1.4.1/jquery.min.js"></script>
<script type="text/javascript">
	function getElementsByClassName(n) {
		var classElements = [], allElements = document
				.getElementsByTagName('*');
		for (var i = 0; i < allElements.length; i++) {
			if (allElements[i].className == n) {
				classElements[classElements.length] = allElements[i];
			}
		}
		return classElements;
	}
	function makeNum() {
		var years = getElementsByClassName('number');
		for (var i = 0; i < years.length; i++) {
			var year = years[i];
			for (var j = 2; j <= 99; j++) {
				var option = document.createElement('option');
				option.value = j;
				option.innerText = j;
				year.appendChild(option);
			}
		}
	}
	function dobuy(index,bookid){
		var address = $('#addressSelect'+index+' option:selected') .val();//选中的值
		window.location.href="${pageContext.request.contextPath }/client/OrderServlet?address="+address+"&bookid="+bookid;
	}

</script>
</head>

<body style="text-align: center;" onload="makeNum()">
	<c:if test="${user == null }">
  		对不起，请先登录
  	</c:if>
	<c:if test="${user != null }">
		<h2>购物车列表</h2>
		<table width="40%" border="1" align="center"
			style="text-align: center;">
			<tr>
				<td>书名</td>
				<td>作者</td>
				<td>单价</td>
				<td>数量</td>
				<td>小计</td>
				<td>送货地址</td>
				<td>操作</td>
			</tr>
			<c:forEach var="me" items="${cart.map }" varStatus="status">
				<tr>
					<td>${me.value.book.name }</td>
					<td>${me.value.book.author }</td>
					<td>${me.value.book.price }</td>
					<td>${me.value.quantity }</td>
					<td>${me.value.price }</td>
					<td><select id="addressSelect${status.index}">
							<c:forEach items="${user.addresses}" var="address">
								<option>${address}</option>
							</c:forEach>
					</select></td>
					<td>
						<form method="post"
							action="${pageContext.request.contextPath}/client/modifyNumberServlet">
							<a
								href="${pageContext.request.contextPath}/client/modifyNumberServlet?method=delete&bookid=${me.value.book.id}">删除</a>
							<select class="number" name="number">
								<option>1</option>
							</select> <input type="hidden" name="bookid" value="${me.value.book.id }" />
							<input type="submit" value="修改数量" />
						</form>
						<!-- ${pageContext.request.contextPath }/client/OrderServlet -->
						<a href="javascript:dobuy('${status.index}','${me.value.book.id }');">购买</a>
					</td>
				</tr>
			</c:forEach>

			<tr>
				<td colspan="1">总价</td>
				<td colspan="5">${cart.price }</td>
			</tr>
		</table>

	</c:if>
</body>
</html>