package web.manager;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import service.impl.BusinessServiceImpl;
@WebServlet("/manager/ConfirmOrderServlet")
public class ConfirmOrderServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try{
			String orderid = request.getParameter("orderid");
			BusinessServiceImpl service = new BusinessServiceImpl();
			service.confirmOrder(orderid);
			request.setAttribute("message", "订单已置为发货状态，请及时配送");
			request.getRequestDispatcher("/message.jsp").forward(request, response);
		} catch(Exception e){
			e.printStackTrace();
			request.setAttribute("message", "确认失败");
			request.getRequestDispatcher("/message.jsp").forward(request, response);
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}

}
