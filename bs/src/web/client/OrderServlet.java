package web.client;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import service.impl.BusinessServiceImpl;
import domain.Cart;
import domain.User;
@WebServlet("/client/OrderServlet")
public class OrderServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try{
			User user = (User) request.getSession().getAttribute("user");
			if(user == null){
				request.setAttribute("message", "对不起，请先登录");
				request.getRequestDispatcher("/message.jsp").forward(request, response);
				return;
			}
			String address = request.getParameter("address");
			String bookid = request.getParameter("bookid");
			Cart cart = (Cart) request.getSession().getAttribute("cart");
			BusinessServiceImpl service = new BusinessServiceImpl();
			service.createSimpleOrder(cart,user,bookid,address);
			//service.createOrder(cart, user);
			request.setAttribute("message", "订单已生成");
			//request.getSession().removeAttribute("cart");//清空购物车，因为点购买后，如果不清空购物车，前端点击查看购物车又出现了
			request.getRequestDispatcher("/message.jsp").forward(request, response);
		}catch(Exception e){
			e.printStackTrace();
			request.setAttribute("message", e.getMessage());
			request.getRequestDispatcher("/message.jsp").forward(request, response);
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}

}
