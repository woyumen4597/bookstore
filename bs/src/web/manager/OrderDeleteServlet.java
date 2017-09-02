package web.manager;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import service.impl.BusinessServiceImpl;
@WebServlet("/manager/OrderDeleteServlet")
public class OrderDeleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		try {
			String orderid = request.getParameter("orderid");
			BusinessServiceImpl service = new BusinessServiceImpl();
			service.deleteOrder(orderid);
			request.getRequestDispatcher("/manager/ListOrderServlet").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("message", "删除订单失败");
			request.getRequestDispatcher("/message.jsp").forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
