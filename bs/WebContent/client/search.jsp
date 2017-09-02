<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Cache-Control" content="max-age=300" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
</head>
<body style="margin: 0 40%;">
	<c:if test="${result.recordCount==0}">
		<div style="color: red;">抱歉，没有找到相应的书籍!</div>
		<br/>
	</c:if>
	<div>
		<span>&nbsp;&nbsp;&nbsp;&nbsp;共${result.pageCount}页</span>
	</div>
	<div>
		<ul>
			<c:forEach items="${result.bookList}" var="book">
				<div class="p-img">
					<a target="_blank"
						href="${pageContext.request.contextPath }/client/InfoServlet?bookid=${book.id}">
						<img width="160" height="160" src="${book.image}" />
					</a>
				</div>
				<div class="p-name">
					<a target="_blank"
						href="${pageContext.request.contextPath }/client/InfoServlet?bookid=${book.id}">
						${book.name} </a>
				</div>
				<div>
					<i>价格：</i> <strong>￥<fmt:formatNumber groupingUsed="false"
							maxFractionDigits="2" minFractionDigits="2"
							value="${book.price }" /></strong>
				</div>
				<div>
					<i>销量:</i> <strong>${book.sales}</strong>
				</div>
				<div>
					<i>分类:</i> <strong>${book.category_name}</strong>
				</div>
				<div>
					<span><span><span>&nbsp;</span></span></span>
				</div>
			</c:forEach>
		</ul>
	</div>
	<div id="page" style="margin-top: 20px; text-align: center;">
				当前第[${result.curPage }]页 &nbsp;&nbsp;
				<c:forEach var="pagenum" begin="1"
					end="${result.pageCount }">
			    	[<a
						href="${pageContext.request.contextPath }/client/QueryServlet?pagenum=${pagenum}&keyword=${param.keyword}">${pagenum }</a>]
			    </c:forEach>
				&nbsp;&nbsp; 总共[${result.pageCount}]页，共[${result.recordCount }]条记录
			</div>
</body>
</html>