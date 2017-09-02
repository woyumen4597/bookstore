<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>客户端显示订单</title>
     <script type="text/javascript">
		//处理取消订单
 	    function docancel(id,state){
			if(state=='0'){ //未发货
				window.location.href="${pageContext.request.contextPath}/client/ClientCancelOrderServlet?orderid="+id;
			}else{
				alert('货物已发货，无法取消!');
			}
		}
		//处理确认收货
		function doconfirm(id,state){
			if(state=='0'){ //未发货
				alert('货物未发货，无法收货!');
			}else if(state == '2'){
				alert('订单已取消，无法收货!');
			}else if(state == '3'){
				alert('订单已完成,无法操作!');
			}else{ //已发货
				alert('已确认收货!');
				window.location.href="${pageContext.request.contextPath }/client/ConfirmRecievedServlet?orderid="+id;
			}
		}
</script>
  </head>

  <body style="text-align:center;">
    <h2>订单列表</h2>
    <a href="${pageContext.request.contextPath }/client/ClientListOrderServlet?state=0&&userid=${user.id}">未发货订单</a>
    <a href="${pageContext.request.contextPath }/client/ClientListOrderServlet?state=1&&userid=${user.id}">已发货订单</a>
    <a href="${pageContext.request.contextPath }/client/ClientListOrderServlet?state=2&&userid=${user.id}">已取消订单</a>
    <a href="${pageContext.request.contextPath }/client/ClientListOrderServlet?state=3&&userid=${user.id}">已完成订单</a>
    <table width="65%" border="1" align="center" style="text-align: center;">
    	<tr>
    		<td>订单号</td>
    		<td>订单人</td>
    		<td>订单时间</td>
    		<td>订单总价</td>
    		<td>订单状态</td>
    		<td>操作</td>
    	</tr>
    	<c:forEach var="order" items="${orders }">
    		<tr>
    			<td>${order.id }</td>
	    		<td>${order.user.username }</td>
	    		<td>${order.ordertime }</td>
	    		<td>${order.price }</td>
	    		<c:if test="${order.state==0}">
	    			<td>未发货</td>
	    		</c:if>
	    		<c:if test="${order.state==1}">
	    			<td>已发货</td>
	    		</c:if>
	    		<c:if test="${order.state==2}">
	    			<td>已取消</td>
	    		</c:if>
	    		<c:if test="${order.state==3}">
	    			<td>已完成</td>
	    		</c:if>
	    		<!--  TODO
	 				根据订单状态决定是否提交请求(js实现) state == 0可以提交 否则弹出对话框
	 				-->
	    		<td>
	    			<a href="${pageContext.request.contextPath }/client/ClientOrderDetailServlet?orderid=${order.id}">查看明细</a>
	    			<a href="javascript:docancel('${order.id}','${order.state}')">取消</a><br/>
	    			<a href="javascript:doconfirm('${order.id}','${order.state}')">确认收货</a>
	    		</td>
    		</tr>
    	</c:forEach>
	</table>
  </body>

</html>
