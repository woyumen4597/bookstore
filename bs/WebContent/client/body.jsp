<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html>
<head>
<title>首页体</title>
<script type="text/javascript">
	function dobuy(id) {
		alert('加入购物车成功!');
		window.location.href = "${pageContext.request.contextPath }/client/BuyServlet?bookid="
				+ id;
	}
</script>
</head>

<body style="text-align: center;">
	<div id="content" style="margin: 0 auto; width: 840px;">
		<div id="category"
			style="float: left; width: 200px; border: 1px solid red; text-align: left; height: 300px;">
			分类列表：
			<ul>
				<c:forEach var="category" items="${categories }">
					<li><a
						href="${pageContext.request.contextPath }/client/IndexServlet?method=listBookWithCategory&category_id=${category.id}">${category.name }</a>
					</li>
				</c:forEach>
			</ul>
			<a style="margin-left: 40px;"
				href="${pageContext.request.contextPath }/client/IndexServlet?method=getAll">所有图书</a><br />
		</div>

		<div style="float: right">
			<%-- <a
				href="${pageContext.request.contextPath}/client/IndexServlet?method=sort&category_id=${category_id}">按销量排序</a> --%>
			<a href="${pageContext.request.contextPath }/client/SortServlet?key=book_sales&category_id=${param.category_id}&method=asc">销量升序 </a>
			<a href="${pageContext.request.contextPath }/client/SortServlet?key=book_price&category_id=${param.category_id}&method=asc">价格升序 </a>
			<a href="${pageContext.request.contextPath }/client/SortServlet?key=book_sales&category_id=${param.category_id}&method=desc">销量降序 </a>
			<a href="${pageContext.request.contextPath }/client/SortServlet?key=book_price&category_id=${param.category_id}&method=desc">价格降序 </a>
			<br /> <br />
			<form method="post"
				action="${pageContext.request.contextPath}/client/QueryServlet">
				<input type="text" name="keyword" /> <input type="submit"
					value="搜索" />
			</form>
		</div>
		<div id="bookandpage" style="float: left; margin-left: 30px;">
			<div id="books">
				<c:forEach var="book" items="${page.list }">
					<div id="book" style="height: 150; margin-top: 20px;">
						<div id="image" style="float: left;">
							<a
								href="${pageContext.request.contextPath }/client/InfoServlet?bookid=${book.id}"
								title="详情"><img src="${book.image}" height=150 width=100></a>
						</div>
						<div id="bookinfo" style="float: left; text-align: left;">
							<ul>
								<li>
								<c:if test="${book.name.length()>8}">
									${fn:substring(book.name,0,8)}..
								</c:if>
								</li>
								<li>作者：${book.author }</li>
								<li>售价：${book.price }</li>
								<li>库存：${book.stock }</li>
								<li>销量:${book.sales }</li>
								<li><a href="javascript:dobuy('${book.id}')">加入购物车</a></li>
							</ul>
						</div>
					</div>
					<div style="clear: both"></div>
					<!-- 做div浮动后，为了不影响后续页面的显示，在这里清楚浮动效果 -->
				</c:forEach>
			</div>

			<div style="clear: both"></div>
			<!-- 做div浮动后，为了不影响后续页面的显示，在这里清楚浮动效果 -->
			<div id="page" style="margin-top: 20px; text-align: center;">
				当前第[${page.pagenum }]页 &nbsp;&nbsp;
				<c:forEach var="pagenum" begin="${page.startpage }"
					end="${page.endpage }">
			    	[<a
						href="${pageContext.request.contextPath }/client/IndexServlet?method=${param.method }&pagenum=${pagenum}&category_id=${param.category_id}">${pagenum }</a>]
			    </c:forEach>
				&nbsp;&nbsp; 总共[${page.totalpage }]页，共[${page.totalrecord }]条记录
			</div>
		</div>
	</div>
</body>
</html>
