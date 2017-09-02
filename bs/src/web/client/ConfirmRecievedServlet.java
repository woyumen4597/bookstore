package web.client;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import service.impl.BusinessServiceImpl;

@WebServlet("/client/ConfirmRecievedServlet")
public class ConfirmRecievedServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String orderid = request.getParameter("orderid");
		try {
			BusinessServiceImpl service = new BusinessServiceImpl();
			service.confirmReceived(orderid);
			request.setAttribute("message", "确认收货成功");
			request.getRequestDispatcher("/message.jsp").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("message", "确认收货失败");
			request.getRequestDispatcher("/message.jsp").forward(request, response);

		}
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
